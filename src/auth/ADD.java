package auth;

import java.io.Serializable;

public class ADD extends Request implements Serializable {
	public String name;
	public boolean reply; //true if user successfully added to db
	public ADD(String userName, String name, byte[] pass, String adminName, byte[] adminPass){
		super(Operation.ADD);
		this.admin = true;
		username = userName;
		this.adminName = adminName;
		userPW = pass;
		adminPW = adminPass;
		this.name = name;
		//this.operation = Operation.ADD;
	}
	@Override
	public void setReply(Object reply) {
		Boolean bool = (Boolean) reply;
		this.reply = bool.booleanValue();
	}
	
	public String getAuthUserName(){
		return adminName;
	}

	public byte[] getAuthUserPW(){
		return adminPW;
	}

	public String getOpUserName(){
		return username;
	}

	public byte[] getOpUserPW(){
		return userPW;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		return super.toString() + "\nADD [name=" + name + ", reply=" + reply + "]";
	}
	
	
}
