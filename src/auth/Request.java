/**
 * 
 */
package auth;

import java.io.Serializable;
import java.net.SocketAddress;
import java.util.Arrays;

/**
 * @author kAG0
 * An abstract Request operation. All requests inherit from Request and all have 
 * the data members in Request (some un-needed passwords may be null however).
 * A true value in the admin field indicates that this request should do a session
 * key exchange with the sever based on adminPW rather than userPW.
 */
public abstract class Request implements Serializable {
	public enum Operation{
		ADD, REMOVE, CHECK, CHANGENAME, CHANGEPASSWORD, GETINFO, SETADMIN, LIST, HISTORY
	}
	public boolean admin;
	public SocketAddress origin;
	public Operation operation;
	public String username;
	public String adminName;
	public byte[] userPW;
	public byte[] adminPW;
	Request(Operation operation){
		this.operation = operation;
	}
	public abstract void setReply(Object reply);
	
	//TODO
	//these getname/password methods should be implemented so that Resolver/server can 
	//call them and get info without having to look at the details of the Request
	public String getAuthUserName(){
		//if(admin){
			return adminName;
		//}
			//return username;
	}
	//TODO
	public byte[] getAuthUserPW(){
		return adminPW;
	}
	//TODO
	public String getOpUserName(){
		return username;
	}
	//TODO
	public byte[] getOpUserPW(){
		return userPW;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Request [admin=" + admin + ", origin=" + origin
				+ ", operation=" + operation + ", username=" + username
				+ ", adminName=" + adminName + ", userPW="
				+ Arrays.toString(userPW) + ", adminPW="
				+ Arrays.toString(adminPW) + "]";
	}
	
}
