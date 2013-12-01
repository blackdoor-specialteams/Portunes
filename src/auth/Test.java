package auth;

import auth.Resolver.UserNotFoundException;
import util.Hash;

public class Test {

	public static void main(String[] args) throws UserNotFoundException {
		AuthClient client = new AuthClient("localhost", 1234);
		Request req = new CHECK("sally", Hash.getSHA256("password".getBytes()));
		Request rep = client.exchange(req);
		System.out.println(rep);

	}

}
