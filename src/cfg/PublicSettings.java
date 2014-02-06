package cfg;

/**
 * General public settings
 * @author nfischer3
 *
 */
public class PublicSettings {
	
	public class AuthServerSettings{
		public static final String greeting = "Portumnes";
		public static final int saltLength = 32;
		public static final int passLength = 32;
		public static final int stretchLength = 90500;// 90500 yields between 1/2s
														// and 1s on 2.4ghz
														// sandyBridge i7
														// 4500 yields about 100ms
	}
	
}
