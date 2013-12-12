package auth;

import java.io.Serializable;
import java.util.Arrays;

public class SETADMIN extends Request implements Serializable {

	public String newAdminName;
	boolean reply;//true if newAdminName has been made an administrator of userName
	public byte[] newAdminPassword;//not actually needed?
	public SETADMIN(String userName, String newAdminName, byte[] newAdminPassword, String adminName, byte[] adminPassword) {
		super(Operation.SETADMIN);
		username = userName;
		this.adminName = adminName;
		adminPW = adminPassword;
		this.admin = true;
		this.newAdminPassword = newAdminPassword;
		this.newAdminName = newAdminName;
		//this.operation = Operation.SETADMIN;
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
		return super.toString() + "\nSETADMIN [newAdminName=" + newAdminName + ", reply=" + reply
				+ ", newAdminPassword=" + Arrays.toString(newAdminPassword)
				+ "]";
	}

}
