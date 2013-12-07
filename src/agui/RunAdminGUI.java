package agui;

import auth.*;

public class RunAdminGUI {
	final static private String servername = "localhost";
	final static private int serverport = 30; 
	public static AuthClient myclient;
	
	public static void main(String[] args) {
		
		myclient = new AuthClient(servername,serverport);
		try {
			Alogin login = new Alogin(myclient);
			login.open();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
