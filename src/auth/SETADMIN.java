package auth;

import java.io.Serializable;

public class SETADMIN extends Request implements Serializable {

	public String newAdminName;
	boolean reply;//true if newAdminName has been made an administrator of userName
	public byte[] newAdminPassword;
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

}
