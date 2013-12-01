package auth;

import auth.Resolver.UserNotFoundException;
import util.Hash;
import util.Watch;
import util.Watch.StopWatch;

public class Test {

	public static void main(String[] args) throws UserNotFoundException {
		AuthClient client = new AuthClient("localhost", 1234);
		Request req = new CHECK("Bob", Hash.getSHA256("password".getBytes()));
		StopWatch time = new StopWatch(true);
		Request rep = client.exchange(req);
		System.out.println(time.checkMS());
		System.out.println(rep);
	}

}
