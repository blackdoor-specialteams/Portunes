package agui;

import auth.*;

public class RunAdminGUI {

	public static void main(String[] args) {
		try {
			Alogin login = new Alogin();
			login.open();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
