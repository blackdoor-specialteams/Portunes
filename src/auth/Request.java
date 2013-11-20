/**
 * 
 */
package auth;

import java.io.Serializable;
import java.net.SocketAddress;

/**
 * @author kAG0
 *
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
