package auth;

import java.io.Serializable;

public class CHANGENAME extends Request implements Serializable {
	String name;
	boolean reply;//true if users "name" field successfully changed
	public CHANGENAME(String userName, String newName) {
		super(Operation.CHANGENAME);
		username=userName;
		name = newName;
		//this.operation = Operation.CHANGENAME;
	}

}
