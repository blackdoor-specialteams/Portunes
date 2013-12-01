package auth;

import util.Hash;

public class Test {

	public static void main(String[] args) {
		AuthClient client = new AuthClient("localhost", 1234);
		Request req = new CHECK("bob", Hash.getSHA256("password".getBytes()));
		Request rep = client.exchange(req);
		System.out.println(rep);

	}

}
