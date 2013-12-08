package agui;

import auth.*;

public class RunAdminGUI {
	final static private String servername = "vodkapi.dyndns";
	final static private int serverport = 3306;
	public static AuthClient myclient;

	public static void main(String[] args) {

		myclient = new AuthClient(servername, serverport);

		Alogin login = new Alogin(myclient);

		if (login.open()) {
			AHome home = new AHome(myclient);
			home.open();
		}
	}
}
