package auth;

import java.io.Serializable;

public class CHECK extends Request implements Serializable {
	boolean reply;//true if user with specified password exists in db
	public CHECK(String userName, byte[] userPassword) {
		super(Operation.CHECK); //this.operation = Operation.CHECK;
		username= userName;
		userPW = userPassword;
	}

}
