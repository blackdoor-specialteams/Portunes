package auth;

import java.io.Serializable;

public class TICKET extends Request implements Serializable {
	public byte[] reply;//returns an AuthTicket for the user if a user with username and userPW exists in the database, otherwise returns random bytes
	public TICKET(String userName, byte[] userPassword) {
		super(Operation.CHECK); //this.operation = Operation.CHECK;
		username= userName;
		userPW = userPassword;
		this.admin = false;
	}
	@Override
	public void setReply(Object reply) {
		//Boolean bool = (Boolean) reply;
		this.reply = (byte[]) reply;
	}
	public String getOpUserName(){
		return username;
	}
	public byte[] getOpUserPW(){
		return userPW;
	}
	public String getAuthUserName(){
		if(admin)
			return adminName;
		return username;
	}
	public byte[] getAuthUserPW(){
		if(admin)
			return adminPW;
		return userPW;
	}
	
	public byte[] getReply(){
		return reply;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return super.toString() + "\nCHECK [reply=" + reply + "]";
	}

}
