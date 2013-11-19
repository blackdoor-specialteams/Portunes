/**
 * 
 */
package auth;

import java.io.Serializable;

/**
 * @author kAG0
 *
 */
public abstract class Request implements Serializable {
	public enum Operation{
		ADD, REMOVE, CHECK, CHANGENAME, CHANGEPASSWORD, GETINFO, SETADMIN, LIST, HISTORY
	}
	public Operation operation;
	public String username;
	public String admin;
	public byte[] userPW;
	public byte[] adminPW;
	Request(Operation operation){
		this.operation = operation;
	}
	
}
