package auth;

import java.io.Serializable;

public class ADD extends Request implements Serializable {
	public String name;
	public boolean reply; //true if user successfully added to db
	ADD(String userName, String name, byte[] pass, String adminName, byte[] adminPass){
		super(Operation.ADD);
		this.admin = true;
		username = userName;
		this.adminName = adminName;
		userPW = pass;
		adminPW = adminPass;
		this.name = name;
		//this.operation = Operation.ADD;
	}
}
