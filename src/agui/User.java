package agui;

import java.io.UnsupportedEncodingException;

import util.Hash;
import auth.*;
import auth.Resolver.UserNotFoundException;

public class User {
	private String username;
	private String password;
	private AuthClient portclient;
	private Session session;

	public User(AuthClient a, Session b) {
		portclient = a;
		session = b;
	}

	public void setUName(String a) {
		username = a;
	}
	public String getUname(){
		return username;
	}

	public boolean Changename(String newname) {
		CHANGENAME req = null;
		req = new CHANGENAME(username, newname);
		try {
			req.adminName = session.getName();
			req.adminPW = session.getPassHash();
			req.admin = true;
			req = (CHANGENAME) portclient.exchange(req);
		} catch (UserNotFoundException e) {
			e.printStackTrace();
		}
		return req.reply;
	}

	public boolean Changepass(String newpass) {
		CHANGEPASS req = null;
		try {
			req = new CHANGEPASS(username, Hash.getSHA256(newpass
					.getBytes("UTF-8")));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		try {
			req.adminName = session.getName();
			req.adminPW = session.getPassHash();
			req.admin = true;
			req = (CHANGEPASS) portclient.exchange(req);
		} catch (UserNotFoundException e) {
			e.printStackTrace();
		}
		return req.reply;
	}
	
	public boolean Remove() {
		REMOVE req = null;
		req = new REMOVE(username);
		System.out.println("right before");
		try {
			req.adminName = session.getName();
			req.adminPW = session.getPassHash();
			req.admin = true;
			req = (REMOVE) portclient.exchange(req);
			System.out.println("trying it out");
		} catch (UserNotFoundException e) {
			e.printStackTrace();
		}
		return req.reply;
	}

}
