package agui;

import java.io.UnsupportedEncodingException;

import util.*;
import auth.*;
import auth.Resolver.UserNotFoundException;

public class User {
	private String username;
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
			System.out.println("changing name about to go");
			req.adminName = session.getName();
			req.adminPW = session.getPassHash();
			req.admin = true;
			req = (CHANGENAME) portclient.exchange(req);
			System.out.println("mission possible is go");
		} catch (UserNotFoundException e) {
			e.printStackTrace();
		}
		return req.reply;
	}

	public boolean Changepass(String newpass) {
		CHANGEPASS req = null;
		try {
			System.out.println("about to get that passowrd hashed");
			req = new CHANGEPASS(username, Hash.getSHA256(newpass.getBytes("UTF-8")));
			System.out.println("hash of password worked");
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
		try {
			req.adminName = session.getName();
			req.adminPW = session.getPassHash();
			req.admin = true;
			req = (REMOVE) portclient.exchange(req);
		} catch (UserNotFoundException e) {
			e.printStackTrace();
		}
		return req.reply;
	}

}
