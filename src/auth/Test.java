package auth;

import java.io.UnsupportedEncodingException;

import auth.Resolver.UserNotFoundException;
import util.Hash;
import util.Misc;
import util.Watch;
import util.Watch.StopWatch;

public class Test {

	public static void main(String[] args) throws UserNotFoundException, UnsupportedEncodingException {
		System.out.println("asdf");
		int last = 0;
		int it = 1;
		StopWatch t = new StopWatch(false);
		byte[] salt = Hash.getSHA256("potato".getBytes());
		byte[] key = Hash.getSHA256(salt);
		while(last < 500){
			t.mark();
			Hash.getStretchedSHA256(key, salt, it++);
			last = (int) t.checkMS();
			System.out.println(it + " : " +last);
			
		}
//		salt = new byte[]{(byte) 0xf3, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, };
//		System.out.println(Misc.getHexBytes("password".getBytes("UTF-8"), ""));
//		System.out.println("passwordhash " + Misc.getHexBytes(Hash.getSHA256("password".getBytes("UTF-8")),""));
//		System.out.println("passwordstretch " + Misc.getHexBytes(Hash.getStretchedSHA256("password".getBytes("UTF-8"), salt, AuthServer.stretchLength),""));
//		System.out.println("password1hash " + Misc.getHexBytes(Hash.getSHA256("password1".getBytes("UTF-8")),""));
//		System.out.println("password1stretch " + Misc.getHexBytes(Hash.getStretchedSHA256("password1".getBytes("UTF-8"), salt, AuthServer.stretchLength),""));
//		System.out.println("salt "+Misc.getHexBytes(salt, ""));
//		AuthClient client = new AuthClient("localhost", 1234);
//		Request req = new CHECK("Bobh", Hash.getSHA256("password".getBytes("UTF-8")));
//		StopWatch time = new StopWatch(true);
//		Request rep = client.exchange(req);
//		System.out.println("exchange time: " + time.checkMS() + "ms");
//		System.out.println(rep);
	}

}
