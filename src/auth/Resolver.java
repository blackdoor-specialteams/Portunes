/**
 * 
 */
package auth;

import java.net.InetAddress;
import java.net.SocketAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;

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
		switch(request.operation){
			case ADD:
				
				break;
			case REMOVE:
				break;
			case CHECK:
				break;
			case CHANGENAME:
				break;
			case CHANGEPASSWORD:
				break;
			case GETINFO:
				break;
			case SETADMIN:
				break;
			case LIST:
				break;
			case HISTORY:
				break;
		}
		return request;
	}
	
	public boolean recordLogin(InetAddress origin, String userName) {
		
	}
	
	private boolean isValidUser(String userName, byte[] password){
		if( /* userName is in db*/ ){
			byte[] salt;  //TODO get salt for userName from db
			byte[] storedPW;  //TODO get saved password for userName from db	
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
