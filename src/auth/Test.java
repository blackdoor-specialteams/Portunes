package auth;

import java.io.UnsupportedEncodingException;

import javax.xml.bind.DatatypeConverter;

import auth.Resolver.UserNotFoundException;
import util.Hash;
import util.Misc;
import util.Watch;
import util.Watch.StopWatch;

public class Test {

	public static void main(String[] args) throws UserNotFoundException, UnsupportedEncodingException {

		byte[] salt = new byte[]{(byte) 0xf3, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, };
		System.out.println(Misc.getHexBytes("password".getBytes("UTF-8"), ""));
		System.out.println("passwordhash " + DatatypeConverter.printHexBinary((Hash.getSHA256("password".getBytes("UTF-8")))));
		System.out.println("passwordstretch " + DatatypeConverter.printHexBinary(Hash.getStretchedSHA256("password".getBytes("UTF-8"), salt, AuthServer.stretchLength)));
		
		byte[] hashPW = Hash.getSHA256("password1".getBytes("UTF-8"));
		System.out.println("password1hash " + DatatypeConverter.printHexBinary(hashPW));
		System.out.println("password1stretch " + DatatypeConverter.printHexBinary(Hash.getStretchedSHA256(Hash.getSHA256("password1".getBytes("UTF-8")), salt, AuthServer.stretchLength)));
		System.out.println("salt "+ Misc.getHexBytes(salt, ""));
		AuthClient client = new AuthClient("localhost", 1234);
		Request req = new CHECK("bobh", hashPW);
		//StopWatch time = new StopWatch(true);
		Request rep = client.exchange(req);
		//System.out.println("exchange time: " + time.checkMS() + "ms");
		System.out.println(rep);
		Request req1 = new ADD("jane", "alice cooper", Hash.getSHA256("password".getBytes("UTF-8")), "bobh", Hash.getSHA256("password1".getBytes("UTF-8")));
		System.out.println(client.exchange(req1));
		Request req2 = new CHECKADMIN("bobh", hashPW);
		System.out.println(client.exchange(req2));
	}

}
