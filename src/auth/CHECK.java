package auth;

import java.io.Serializable;

public class CHECK extends Request implements Serializable {
	boolean reply;//true if user with specified password exists in db
	public CHECK() {
		super(Operation.CHECK); //this.operation = Operation.CHECK;
	}

}
