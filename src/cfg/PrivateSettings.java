package cfg;

import blackdoor.util.SHE;

/**
 * contains settings for the server and server side classes
 * this class should NEVER EVER be built into a public library or client program
 * @author nfischer3
 *
 */
public class PrivateSettings {
	public static class TicketSettings{
		public static final long SESSON_DURATION = 260;
		public static final byte[] TICKET_KEY = new byte[SHE.BLOCKSIZE];
	}
	public class SQLDatabaseSettings{
		public static final String serverAddress = "localhost";
		public static final int PORT = 3306;
		public static final String DATABASE = "Portunes";
		public static final String USERNAME = "portunes";
		public static final String PASSWORD = "drowssap";
	}
}
