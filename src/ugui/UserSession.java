package ugui;

import java.io.UnsupportedEncodingException;

import util.Hash;
import auth.*;
import auth.Resolver.UserNotFoundException;

public class UserSession {
	private String username;
	private String userpassword;
	private boolean authorized;
	private byte[] userhashpassword;

	public UserSession() {
		username = "";
		userpassword = "";
		authorized = false;
	}

	public void Authorize(AuthClient sessionclient, String user, String pass) {
		CHECK loginattempt = null;
		try {
			loginattempt = new CHECK(user, Hash.getSHA256(pass.getBytes("UTF-8")));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			loginattempt = (CHECK) sessionclient.exchange(loginattempt);
		} catch (UserNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(loginattempt.reply){
			
			try {
				userhashpassword = Hash.getSHA256(pass.getBytes("UTF-8"));
				username = user;
				authorized = true;
			} catch (UnsupportedEncodingException e) {
				System.out.println("failed to authorize and add session");
				e.printStackTrace();
			}
			
		}
	}
	
	public boolean isAuthorized(){
		return authorized;
	}
	public byte[] getPassHash(){
		return userhashpassword;
	}
	public String getName(){
		return username;
	}
	public String getPass(){
		return username;
	} 
}
