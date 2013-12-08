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

//import com.mysql.jdbc.Statement;


import util.Hash;

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
		
		switch(request.operation){
		// In each switch statement make query = to something different depending on what we want to query
			case ADD:
				//get the user name and password
				//sql insert statement
				break;
			case REMOVE:
				//get the info on what to delete
				//delete: DELETE FROM ___ WHERE user = what we want to delete
				break;
			case CHECK:
				if( isValidUser(USERNAME, PASSWORD))
					request.setReply(true);
				else
					request.setReply(false);
				break;
			case CHANGENAME:
				//get the name we have to change
				//UPDATE tablename
				//SET name = "newname"
				//WHERE name = "oldname" AND password ="password" AND so on...
				break;
			case CHANGEPASSWORD:
				// get the password we have to change
				// UPDATE tablename
				// SET password = "newpassword"
				// WHERE name = "name" AND password ="oldpassword" AND so on... 
				break;
			case GETINFO:
				// SELECT * FROM table WHERE user ="username" AND etc.
				break;
			case SETADMIN:
				// get the newAdminName
				// SQL INSERT newAdminName and yeah
				request.setReply(true); // true if the newAdminName has been made an administrator of userName
				break;
			case LIST:
				// ASSUME ITS ADMIN get admin name and pword
				// SELECT allPreviousLogins FROM table WHERE adminname = "admin name" AND etc.
				break;
			case HISTORY:
				// get username
				// SELECT allPreviousLogins FROM table WHERE user = "username"
				break;
		}
		ResultSet rs = stmt.executeQuery(query);
		connection.close();
		return request;
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
