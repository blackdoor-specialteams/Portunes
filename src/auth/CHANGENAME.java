package auth;

import java.io.Serializable;

public class CHANGENAME extends Request implements Serializable {
	String name;
	boolean reply;//true if users "name" field successfully changed
	//also needs the userpassword or (adminName and adminpassword) to be set.
	public CHANGENAME(String userName, String newName) {
		super(Operation.CHANGENAME);
		username=userName;
		name = newName;
		//this.operation = Operation.CHANGENAME;
	}

}
