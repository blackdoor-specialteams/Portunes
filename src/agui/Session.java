package agui;

import java.io.UnsupportedEncodingException;

import util.Hash;
import auth.*;
import auth.Resolver.UserNotFoundException;

public class Session {
	private String adminname;
	private String adminpass;
	private boolean authorized;

	public Session() {
		adminname = "";
		adminpass = "";
		authorized = false;
	}

	public void Authorize(AuthClient sessionclient, String user, String pass) {
		CHECKADMIN loginattempt = null;
		try {
			loginattempt = new CHECKADMIN(user, Hash.getSHA256(pass.getBytes("UTF-8")));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			loginattempt = (CHECKADMIN) sessionclient.exchange(loginattempt);
		} catch (UserNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(loginattempt.reply){
			adminname = user;
			adminpass = pass;
			authorized = true;
		}
	}
	
	public boolean isAuthorized(){
		return authorized;
	}
	public String getName(){
		return adminname;
	}
	public String getPass(){
		return adminname;
	} 
}
