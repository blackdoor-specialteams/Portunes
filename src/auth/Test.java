package auth;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;

import javax.xml.bind.DatatypeConverter;

import auth.Resolver.UserNotFoundException;
import util.Hash;
import util.Misc;
import util.Watch;
import util.Watch.StopWatch;

public class Test {

	public static void main(String[] args) throws UserNotFoundException, UnsupportedEncodingException {
		byte[] ugh = new byte[10];
		new SecureRandom().nextBytes(ugh);
		System.out.println(DatatypeConverter.printHexBinary(ugh) + "\n" + Misc.getHexBytes(ugh, ""));
//		StopWatch time = new StopWatch(false);
//		double total = 0;
//		int i = 0;
//		while(i++ < 100){
//			time.mark();
//			Hash.getStretchedSHA256("".getBytes(), "".getBytes(), AuthServer.stretchLength);
//			total += time.checkMS();
//		}
//		System.out.println(total / --i + " " + i);
		
		
		System.out.println(DatatypeConverter.printHexBinary(Hash.getStretchedSHA256("password".getBytes("UTF-8"), new byte[]{(byte)0x47,(byte)0x1F,(byte)0xDA,(byte)0x38,(byte)0xE8,(byte)0x32,(byte)0x9E,(byte)0x68,(byte)0x4C,(byte)0xC1,(byte)0x2C,(byte)0xA6,(byte)0x95,(byte)0x11,(byte)0x0A,(byte)0x7D,(byte)0x17,(byte)0x43,(byte)0xB9,(byte)0x2E,(byte)0x3F,(byte)0x70,(byte)0x1A,(byte)0xEC,(byte)0x41,(byte)0x62,(byte)0x95,(byte)0x27,(byte)0xE0,(byte)0x55,(byte)0xC4,(byte)0x49}, AuthServer.stretchLength)));
		byte[] salt = new byte[]{(byte) 0xf3, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, };
		System.out.println(Misc.getHexBytes("password".getBytes("UTF-8"), ""));
		System.out.println("passwordhash " + DatatypeConverter.printHexBinary((Hash.getSHA256("password".getBytes("UTF-8")))));
		System.out.println("passwordstretch " + DatatypeConverter.printHexBinary(Hash.getStretchedSHA256("password".getBytes("UTF-8"), salt, AuthServer.stretchLength)));
		
		byte[] hashPW = Hash.getSHA256("password1".getBytes("UTF-8"));
		System.out.println("password1hash " + DatatypeConverter.printHexBinary(hashPW));
		
		System.out.println("password1stretch " + DatatypeConverter.printHexBinary(Hash.getStretchedSHA256(Hash.getSHA256("password1".getBytes("UTF-8")), salt, AuthServer.stretchLength)));
		
		System.out.println("salt "+ Misc.getHexBytes(salt, ""));
		AuthClient client = new AuthClient("localhost", 1234);
		Request req = new CHECK("jane", Hash.getSHA256("password".getBytes("UTF-8")));
		Request rep = client.exchange(req);
		System.out.println(rep);
		Request req3 = new CHECKADMIN("bobh", hashPW);
		System.out.println(client.exchange(req3));
		//Request req1 = new ADD("wasd", "alice cooper", Hash.getSHA256("wasd".getBytes("UTF-8")), "bobh", Hash.getSHA256("password1".getBytes("UTF-8")));
		//System.out.println(client.exchange(req1));
		Request req2 = new HISTORY("jane");
		req2.admin=false;
		req2.userPW =  Hash.getSHA256("passwordz".getBytes("UTF-8"));
		System.out.println(client.exchange(req2));
	}

}
