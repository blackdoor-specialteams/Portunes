package auth;

public class CHECKADMIN extends CHECK {

	public CHECKADMIN(String adminName, byte[] adminPassword) {
		super(adminName, adminPassword);
		// TODO Auto-generated constructor stub
		this.admin = true;
		this.operation = operation.CHECKADMIN;
	}

}
