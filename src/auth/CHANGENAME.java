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
		return super.toString() + "\nCHANGENAME [name=" + name + ", reply=" + reply + "]";
	}

}
