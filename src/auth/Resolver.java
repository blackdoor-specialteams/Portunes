/**
 * 
 */
package auth;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.security.SecureRandom;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

//import com.mysql.jdbc.*;



import util.Hash;
import util.Misc;
import util.Watch;

/**
 * @author kAG0
 *
 */
public class Resolver {
	private Connection connection = null;
	private static final String serverAddress = "vodkapi.dyndns.info";
	private static final int PORT = 3306;
	private static final String DATABASE = "Portunes";
	private static final String USERNAME = "nate";
	private static final String PASSWORD = "pass";
	/**
	 * notes:
	 * working with IPv4 addresses in mySQL:
	 * 	store ip's as unsigned 4 byte int's
	 * 	use the mySQL built in functions INET_ATON() and INET_NTOA() in your queries
	 * 	ATON converts things from the form '192.168.1.1' to an integer
	 * 	NTOA converts from an integer to the above form.
	 *
	 */
	/**
	 * 
	 */
	public Resolver() {
		// TODO Auto-generated constructor stub
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private Connection getConnection(String sqlUserName, String sqlUserPassword) throws SQLException{
		return DriverManager.getConnection("jdbc:mysql://" + serverAddress + ":" + PORT + "/" + DATABASE, sqlUserName, sqlUserPassword);
	}
	
	public void connect() throws SQLException{
		if(connection == null)
			connection = getConnection(USERNAME, PASSWORD);
	}
	public void disconnect() throws SQLException{
		connection.close();
		connection = null;
	}
	
	/**
	 * 
	 * @param userName
	 * @return the salt of userName, if userName DNE then return null
	 * @throws IOException 
	 */
	public byte[] getUserSalt(String userName) throws UserNotFoundException, IOException{
		//Connection connection;
		//System.out.println("getting salt");
		try {
			if(connection == null)
				connect();//connection = getConnection(USERNAME, PASSWORD);
			String query = "SELECT salt FROM User WHERE userName = '" + userName + "';";
			Statement stmt = (Statement) connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			if(rs.next())
			{
				byte[] salt = rs.getBytes("salt");
				//System.out.println(Misc.getHexBytes(salt, ""));
				return salt;
			}else
				throw new UserNotFoundException(userName);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IOException("Problem getting user salt from DB.");
		}
		
		
		//return null;//TODO
	}
	
	public byte[] getUserHash(String userName) throws UserNotFoundException, IOException{
		//Connection connection;
		System.out.println("getting hash");
		try {
			if(connection == null)
				connect();//connection = getConnection(USERNAME, PASSWORD);
			String query = "SELECT password FROM User WHERE userName = '" + userName + "';";
			Statement stmt = connection.createStatement();
			//System.out.println(query);
			ResultSet rs = stmt.executeQuery(query);
			if(rs.next())
			{
				return rs.getBytes("password");
			}else
				throw new UserNotFoundException(userName);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IOException("Problem getting user hash from DB.");
		}
	}
	/**
	 * parses request and returns a Request that is the same as request but with
	 * the reply data member appropriately filled.
	 * @param request the request to handle
	 * @return same as request but with the reply data member appropriately filled.
	 */
	public Request resolve(Request request) throws UserNotFoundException{
		//connection = getConnection(user, pass);
		//Statement stmt = connection.createStatement();
		/*
		 * maybe do the following only in the cases where it's needed
		 if(request.admin){
			if(!isValidUser(request.adminName, request.getAuthUserPW()))
				//return null or something
		}*/
		switch(request.operation){
		// In each switch statement make query = to something different depending on what we want to query
			case ADD: 
				request.setReply(add((ADD) request));
				break;
			case REMOVE:
				if(isValidAdmin(request.username, request.adminName, request.adminPW))
					request.setReply(removeUser((REMOVE) request));
				else request.setReply(false);
				break;
			case CHECK:
				request.setReply(isValidUser(request.username, request.userPW));
				//TODO if !admin request, call recordLogin
				if(!request.admin)
					//recordLogin(request.origin, request.username);
				break;
			case CHANGENAME://TODO
				if(!request.admin){
					if(isValidUser(request.username, request.userPW)){
						request.setReply(changeName((CHANGENAME) request));
					}
				}
				else if	(isValidAdmin(request.username, request.adminName, request.adminPW)){
					request.setReply(changeName((CHANGENAME) request));
				}
				break;
			case CHANGEPASSWORD://TODO
				if(!request.admin){
					if(isValidUser(request.username, request.userPW)){
						request.setReply(changePass((CHANGENAME) request));
					}
				}
				else if	(isValidAdmin(request.username, request.adminName, request.adminPW)){
					request.setReply(changePass((CHANGENAME) request));
				}
				break;
			case GETINFO://TODO
				request.setReply(getInfo((GETINFO) request));
				break;
			case SETADMIN:
				if(!isValidAdmin(request.username, request.adminName, request.adminPW))
					break;
				// get the newAdminName
				// SQL INSERT newAdminName and yeah
				request.setReply(makeAdmin(request.adminName, request.username)); // true if the newAdminName has been made an administrator of userName
				break;
			case LIST://TODO
				request.setReply(listUsers((GETINFO) request));
				break;
			case HISTORY://TODO
				request.setReply(getHistory((HISTORY) request));
				break;
			case CHECKADMIN:
				if(isAdmin(request.username, request.userPW)){
					request.setReply(true);
					recordLogin(request.origin, request.username);
				}
				else request.setReply(false);
				break;
		}
		
		return request;
	}
	
	private boolean makeAdmin(String adminName, String userName){
//		"INSERT INTO Admin values(" + adminName + "," + userName + ");"
		
		try {
			connect();
			Statement stmt = connection.createStatement();
			stmt.executeUpdate("INSERT INTO Admin values(" + adminName + "," + userName + ");");
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	private boolean add(ADD request){
		byte[] salt = new byte[AuthServer.saltLength];
		new SecureRandom().nextBytes(salt);
		String query_create = "INSERT INTO User values(" + request.username + "," + request.name +
				", 0x" + Misc.getHexBytes(Hash.getStretchedSHA256(request.userPW, salt, AuthServer.stretchLength), "") + 
				", 0x" + Misc.getHexBytes(salt, "") + ");";
		String query_history = "INSERT INTO History(length, lastLoginIndex, userName) values(" +
				"6"/*<<history length*/ + ", 0, " + request.username + ");";
		// sql insert statement
		try {
			if(connection == null)
				connect();
			connection.setAutoCommit(false);

			Statement stmt_create = connection.createStatement();
			Statement stmt_history = connection.createStatement();
			
			stmt_create.executeUpdate(query_create);
			stmt_history.executeUpdate(query_history);
			if(!makeAdmin(request.adminName, request.username))
				throw new SQLException();
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			if(connection != null){
				try {
					connection.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			return false;
		}finally{
			try {
				if(connection != null)
					connection.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return true;
//		"INSERT INTO User values(" + userName + "," + name +
//				", 0x" + Misc.getHexBytes(stretchedPassword, "") + 
//				", 0x" + Misc.getHexBytes(salt, "") + ");"
		
//		"INSERT INTO History(length, lastLoginIndex, userName) values(" +
//				historyLength + ", 0, " + userName + ");"
	}
	
	private boolean removeUser(REMOVE request){
		String query = "DELETE User, LogIn, History, Admin FROM User, LogIn, History, Admin " +
				"WHERE User.userName = '" + request.username + "' AND User.userName = " +
				"History.userName AND (User.userName = Admin.userName OR User.userName = Admin.adminName) " +
				"AND LogIn.hid = History.hid ;";
		try {
			if(connection == null)
				connect();

			Statement stmt = connection.createStatement();
			
			stmt.executeUpdate(query);
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
		//get the info on what to delete
		//delete: DELETE FROM ___ WHERE user = what we want to delete
	}
	
	private boolean changeName(CHANGENAME request){
		//get the name we have to change
		//UPDATE tablename SET name = "newname" WHERE name = "oldname" AND password ="password" AND so on...
		String query = "UPDATE User SET userName = '" + request.name + "' WHERE userName = '" +request.username+ "';";
		try {
			if (connection == null)
				connect();
			Statement stmt = connection.createStatement();
			stmt.executeUpdate(query);
		} catch (SQLException e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	private boolean changePass(CHANGEPASS request){
		// get the password we have to change
		// UPDATE tablename SET password = "newpassword" WHERE name = "name" AND password ="oldpassword" AND so on... 
		String query = "UPDATE USER SET password = '" + request.userPW + "' WHERE userName = '" + request.username +"';";
		try {
			if (connection == null)
				connect();
			Statement stmt = connection.createStatement();
			stmt.executeUpdate(query);
		} catch (SQLException e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	private Map<String, Object> getInfo(GETINFO request){
		// SELECT * FROM table WHERE user ="username" AND etc.
		Map<String, Object> reply = new HashMap();
		String query1 = "SELECT * FROM History h JOIN LogIn l USING(hid) WHERE h.userName = '"+request.username
				+"' AND l.index = (h.lastLoginIndex - "+ request.time+") MOD h.length ;"; // the history on the user with a specific username
		int hid, ip, month, day, year, hours, minutes;
		
		try {
			if(connection == null)
				connect();
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query1);
			if(rs.next()){
				hid = rs.getInt("hid");
				ip = rs.getInt("ip");
				month = rs.getInt("month");
				day = rs.getInt("day");
				year = rs.getInt("year");
				hours = rs.getInt("hours");
				minutes = rs.getInt("minutes");
				reply.put("HID", hid);
				reply.put("IP", ip);
				reply.put("MONTH", month);
				reply.put("DAY", day);
				reply.put("YEAR", year);
				reply.put("HOURS", hours);
				reply.put("MINUTES",minutes);
			}
			return reply;
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	private List<Map<String, Object>> getHistory(HISTORY request){
		// get username
		// SELECT allPreviousLogins FROM table WHERE user = "username"
		
		return null;
	}
	private List<Map<String, Object>> listUsers(GETINFO request){
		// ASSUME ITS ADMIN get admin name and pword
		// SELECT allPreviousLogins FROM table WHERE adminname = "admin name" AND etc.
		return null;
	}

	
	public boolean recordLogin(InetAddress origin, String userName) {
		boolean ret = false;
		Watch time = new Watch();
		String incQuery = "UPDATE LogIn SET lastLoginIndex = (lastLoginIndex + 1) MOD length WHERE userName = '" + userName + "';";
		String query = "INSERT INTO LogIn(hid, ip, month, day, year, index, hours, minutes)" +
				" SELECT h.hid, INET_ATON('" + origin.getHostAddress() + "'), " 
					+ time.getMonth() + ", " + time.getDate() + ", " + time.getYear() + 
					", h.lastLoginIndex MOD h.length, '" + time.getHours() + "', '" + time.getMinutes() + "' " +
				"FROM History h" +
				"WHERE h.userName = '" + userName + "';";
		try {
			connect();
			connection.setAutoCommit(false);
			Statement stmt = connection.createStatement();
			stmt.executeUpdate(incQuery);
			stmt.executeUpdate(query);
			connection.commit();
			ret = true;
		} catch (SQLException e) {
			e.printStackTrace();
			ret = false;
			try {
				if(connection != null)
					connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}finally{
			if(connection != null)
				try {
					connection.setAutoCommit(true);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return ret;
	}
	
	private boolean isValidUser(String userName2Check, byte[] password){
		String queryValidUser = "SELECT * FROM User WHERE userName = '"+ userName2Check+"';";

		try {
			if(connection == null)
				connect();
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(queryValidUser);
			if(rs.next()){
				byte[] storedPW = rs.getBytes("password"); // get password for userName from db
				byte[] salt = rs.getBytes("salt");
				byte[] computedPW = Hash.getStretchedSHA256(password, salt, AuthServer.stretchLength);
				System.out.println(DatatypeConverter.printHexBinary(password) +"\nserver " + DatatypeConverter.printHexBinary(storedPW) + "\n" +
						"compu " + DatatypeConverter.printHexBinary(computedPW) + "\n" +
						DatatypeConverter.printHexBinary(password) + " " + DatatypeConverter.printHexBinary(salt));
				return Arrays.equals(computedPW, storedPW);
			}
			//else
				//return false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;

	}
	
	private boolean isAdmin(String adminName, byte[] password){
		String query = "SELECT * " +
				"FROM Admin JOIN User ON adminName = userName " +
				"WHERE adminName = '" + adminName + "' ";
		try {
			connect();
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			if(rs.next()){
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	private boolean isValidAdmin(String userName, String adminName, byte[] adminPW){
		String query1 = "SELECT userName FROM User WHERE userName = '" +userName+"';";// query that gets username of a specific user
		String query2 = "SELECT adminName FROM Admin WHERE adminName = '" +adminName+ "' AND userName = '"+ userName+"' ;";// query that gets the admin name of a specific user
		String userName2Check = "";
		String adminName2Check = "";
		byte[] storedPW = null;
		byte[] salt = null;
		try{
			connect();
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query1);
			Statement stmt2 = connection.createStatement();
			ResultSet rs2 = stmt2.executeQuery(query2);// gets the admin name if it exists from the query
			
			if(rs.next())
				userName2Check = rs.getString("userName");
			
			if( userName.equals(userName2Check)/* userName is in db */ && isValidUser(adminName, adminPW) ){
				if(rs2.next())
					adminName2Check = rs2.getString("adminName");
				if(adminName.equals(adminName2Check) /* adminName is set as an admin of userName in db */)
					storedPW = rs2.getBytes("password"); // get password for userName from db
					salt = rs2.getBytes("salt");
					return Arrays.equals(Hash.getStretchedSHA256(adminPW, salt, AuthServer.stretchLength), storedPW);
			}
			else
				return false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
			//if adminname is and admin of a user who is an admin of userName return true
			// potential exemplary query here
	}
	
	/**
	 * note: this exception should only be thrown as part of the authentication process
	 * when handling requests like CHECK the response field of the request should just be null
	 * @author kAG0
	 *
	 */
	public static class UserNotFoundException extends Exception{
		private String username;
		UserNotFoundException(String userName){
			super(userName +" not found in database.");
			username = userName;
		}		
		public String getUserName(){
			return username;
		}
	}
}
