package ugui;

import auth.*;

public class RunUserGui {
	private static final String serverAddress = "localhost";
	private static final int PORT = 1234;
	public static AuthClient myclient;
	public static UserSession mysession;

	public static void main(String[] args) {

		myclient = new AuthClient(serverAddress, PORT);
		mysession = new UserSession();
		
		ULogin login = new ULogin(myclient,mysession);
		login.open();

		if(mysession.isAuthorized()) {
			System.out.println("User is so Authorized that it hurts.");
		}
	}
}
