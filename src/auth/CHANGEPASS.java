package auth;

import java.io.Serializable;
import java.util.Arrays;

public class CHANGEPASS extends Request implements Serializable {
	byte[] newPass;
	boolean reply;//true if password successfully changed for user
	//also needs userPW or (adminName and adminPW) to be set
	public CHANGEPASS(String userName, byte[] newPass) {
		super(Operation.CHANGEPASSWORD);
		username=userName;
		this.newPass = newPass;
		//this.operation = Operation.CHANGEPASSWORD;
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
		return super.toString() + "\nCHANGEPASS [newPass=" + Arrays.toString(newPass) + ", reply="
				+ reply + "]";
	}

}
