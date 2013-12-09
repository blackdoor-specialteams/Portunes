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
		System.out.println("password1hash " + Misc.getHexBytes(Hash.getSHA256("password1".getBytes("UTF-8")),""));
		System.out.println("password1stretch " + Misc.getHexBytes(Hash.getStretchedSHA256("password1".getBytes("UTF-8"), salt, AuthServer.stretchLength),""));
		System.out.println("salt "+Misc.getHexBytes(salt, ""));
		AuthClient client = new AuthClient("localhost", 1234);
		Request req = new CHECK("jim", Hash.getSHA256("password".getBytes("UTF-8")));
		StopWatch time = new StopWatch(true);
		Request rep = client.exchange(req);
		System.out.println("exchange time: " + time.checkMS() + "ms");
		System.out.println(rep);
	}

}
