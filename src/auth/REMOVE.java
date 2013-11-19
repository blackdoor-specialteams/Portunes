package auth;

import java.io.Serializable;

public class REMOVE extends Request implements Serializable {

	boolean reply;//true if user successfully removed from db
	//also needs userPW or (adminName and adminPW) to be set
	public REMOVE(String userName) {
		super(Operation.REMOVE); //this.operation = Operation.REMOVE;
		username = userName;
	}

}
