/**
 * 
 */
package auth;

import java.net.InetAddress;
import java.net.SocketAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

//import com.mysql.jdbc.Statement;


import util.Hash;
import util.Misc;

/**
 * @author kAG0
 *
 */
public class Resolver {
	private Connection connection = null;
	private static final String serverAddress = "localhost";
	private static final int port = 1234;
	private static final String database = "database";
	private static String query = "The current query is dicks";
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
	}
	
	private Connection getConnection(String sqlUserName, String sqlUserPassword) throws SQLException{
		return DriverManager.getConnection("jdbc:mysql://" + serverAddress + ":" + port + "/" + database, sqlUserName, sqlUserPassword);
	}
	
	/**
	 * 
	 * @param userName
	 * @return the salt of userName, if userName DNE then return null
	 */
	public byte[] getUserSalt(String userName){
		return null;//TODO
	}
	
	public byte[] getUserHash(String userName) throws UserNotFoundException{
		return null; //TODO
	}
	/**
	 * parses request and returns a Request that is the same as request but with
	 * the reply data member appropriately filled.
	 * @param request the request to handle
	 * @return same as request but with the reply data member appropriately filled.
	 */
	public Request resolve(Request request) throws UserNotFoundException{
		connection = getConnection(user, pass);
		Statement stmt = connection.createStatement();
		/*
		 * maybe do the following only in the cases where it's needed
		 if(request.admin){
			if(!isValidUser(request.adminName, request.getAuthUserPW()))
				//return null or something
		}*/
		switch(request.operation){
		// In each switch statement make query = to something different depending on what we want to query
			case ADD: //TODO
				request.setReply(add((ADD) request));
				break;
			case REMOVE: //TODO
				request.setReply(removeUser( (REMOVE) request));
				break;
			case CHECK:
				if( isValidUser(USERNAME, PASSWORD))
					request.setReply(true);
				else
					request.setReply(false);
				//TODO if !admin request, call recordLogin
				break;
			case CHANGENAME://TODO
				request.setReply(changeName((CHANGENAME) request));
				break;
			case CHANGEPASSWORD://TODO
				request.setReply(changePass((CHANGEPASS) request));
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
		}
		ResultSet rs = stmt.executeQuery(query);
		connection.close();
		return request;
	}
	
	private boolean makeAdmin(String adminName, String userName){
//		"INSERT INTO Admin values(" + adminName + "," + userName + ");"
		return true;
	}
	private boolean add(ADD request){
		//get the user name and password
		// sql insert statement
//		"INSERT INTO User values(" + userName + "," + name +
//				", 0x" + Misc.getHexBytes(stretchedPassword, "") + 
//				", 0x" + Misc.getHexBytes(salt, "") + ");"
		makeAdmin(request.adminName, request.username);
//		"INSERT INTO History(length, lastLoginIndex, userName) values(" +
//				historyLength + ", 0, " + userName + ");"
		return true;
	}
	private boolean changeName(CHANGENAME request){
		//get the name we have to change
		//UPDATE tablename
		//SET name = "newname"
		//WHERE name = "oldname" AND password ="password" AND so on...
		return true;
	}
	private boolean changePass(CHANGEPASS request){
		// get the password we have to change
		// UPDATE tablename
		// SET password = "newpassword"
		// WHERE name = "name" AND password ="oldpassword" AND so on... 
		return true;
	}
	private Map<String, Object> getInfo(GETINFO request){
		// SELECT * FROM table WHERE user ="username" AND etc.
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
	private boolean removeUser(REMOVE request){
		if(!isValidAdmin(request.username, request.adminName, request.adminPW))
			break;
		//get the info on what to delete
		//delete: DELETE FROM ___ WHERE user = what we want to delete
	}
	
	public boolean recordLogin(InetAddress origin, String userName) {
		
		return true; // returns true for funsies
	}
	
	private boolean isValidUser(String userName2Check, byte[] password){
		//do i need to create a new connection or how does i do this
		String queryValidUser = "SELECT * FROM User WHERE userName = "+ userName2Check+";";
		ResultSet rs = stmt.executeQuery(queryValidUser);
		rs.next();
		String name = rs.getString("userName");
		if( userName2Check.equalsIgnoreCase(name) ) // check to see if userName2Check is in db
		{
			byte[] storedPW = rs.getBytes("userPass"); // get password for userName from db
			byte[] salt = rs.getBytes("salt");  // get salt for userName from db
			return Arrays.equals(Hash.getStretchedSHA256(password, salt, AuthServer.stretchLength), storedPW);
		}
		return false;
	}
	
	private boolean isValidAdmin(String userName, String adminName, byte[] adminPW){
		if( /* userName is in db */ && isValidUser(adminName, adminPW) ){
			if( /* adminName is set as an admin of userName in db */)
				return true;
		}
		//if adminname is and admin of a user who is an admin of userName return true
		// potential exemplary query here
		return false;
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
