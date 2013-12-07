package auth;

import java.io.UnsupportedEncodingException;

import auth.Resolver.UserNotFoundException;
import util.Hash;
import util.Watch;
import util.Watch.StopWatch;

public class Test {

	public static void main(String[] args) throws UserNotFoundException, UnsupportedEncodingException {
		AuthClient client = new AuthClient("localhost", 1234);
		Request req = new CHECK("Bob", Hash.getSHA256("password".getBytes("UTF-8")));
		StopWatch time = new StopWatch(true);
		Request rep = client.exchange(req);
		System.out.println("exchange time: " + time.checkMS() + "ms");
		System.out.println(rep);
	}

}
