package auth;

import java.io.Serializable;

public class REMOVE extends Request implements Serializable {

	boolean reply;//true if user successfully removed from db
	//also needs userPW or (adminName and adminPW) to be set
	public REMOVE(String userName) {
		super(Operation.REMOVE); //this.operation = Operation.REMOVE;
		username = userName;
	}
	@Override
	public void setReply(Object reply) {
		Boolean bool = (Boolean) reply;
		this.reply = bool.booleanValue();
	}
	public String getOpUserName(){
		return username;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return super.toString() + "\nREMOVE [reply=" + reply + "]";
	}

}
