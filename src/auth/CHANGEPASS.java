package auth;

import java.io.Serializable;

public class CHANGEPASS extends Request implements Serializable {
	byte[] newPass;
	boolean reply;//true if password successfully changed for user
	public CHANGEPASS(String userName, byte[] newPass) {
		super(Operation.CHANGEPASSWORD);
		username=userName;
		this.newPass = newPass;
		//this.operation = Operation.CHANGEPASSWORD;
	}

}