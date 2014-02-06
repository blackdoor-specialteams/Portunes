package cfg;

import blackdoor.util.SHE;

public class Settings {
	public class SQLDatabaseSettings{
		public static final String serverAddress = "localhost";
		public static final int PORT = 3306;
		public static final String DATABASE = "Portunes";
		public static final String USERNAME = "portunes";
		public static final String PASSWORD = "drowssap";
	}
	public class AuthServerSettings{
		public static final String greeting = "Portumnes";
		public static final int saltLength = 32;
		public static final int passLength = 32;
		public static final int stretchLength = 90500;// 90500 yields between 1/2s
														// and 1s on 2.4ghz
														// sandyBridge i7
														// 4500 yields about 100ms
	}
	public static class Ticket{
		public static final long SESSON_DURATION = 260;
		public static final byte[] TICKET_KEY = new byte[SHE.BLOCKSIZE];
	}
}
