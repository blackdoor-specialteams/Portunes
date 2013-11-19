package auth;

import java.io.Serializable;

public class REMOVE extends Request implements Serializable {

	boolean reply;//true if user successfully removed from db
	public REMOVE() {
		super(Operation.REMOVE); //this.operation = Operation.REMOVE;
	}

}
