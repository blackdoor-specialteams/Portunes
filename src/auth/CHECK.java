package auth;

import java.io.Serializable;

public class CHECK extends Request implements Serializable {
	boolean reply;//true if user with specified password exists in db
	public CHECK(String userName, byte[] userPassword) {
		super(Operation.CHECK); //this.operation = Operation.CHECK;
		username= userName;
		userPW = userPassword;
	}
	@Override
	public void setReply(Object reply) {
		Boolean bool = (Boolean) reply;
		this.reply = bool.booleanValue();
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
		return super.toString() + "\nCHECK [reply=" + reply + "]";
	}

}
