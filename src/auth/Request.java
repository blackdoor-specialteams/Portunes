/**
 * 
 */
package auth;

import java.io.Serializable;
import java.net.SocketAddress;

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
	
}
