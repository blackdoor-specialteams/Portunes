/**
 * 
 */
package auth;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.Hash;
import util.Watch;

/**
 * @author kAG0
 *
 */
public class TestResolver {
	
	public static final String bobPW = "password";
	public static final byte[] bobSalt = {(byte) 0xd9, (byte)0xca, (byte)0xb0, (byte)0x44, (byte)0x51, (byte)0x80, (byte)0xd5, (byte)0x18, (byte)0x9d, (byte)0x0b, (byte)0x36, (byte)0x87, (byte)0xda, (byte)0x5a, (byte)0xb7, (byte)0x2e, (byte)0xbb, (byte)0xb6, (byte)0x63, (byte)0xb3, (byte)0x15, (byte)0xc5, (byte)0xd8, (byte)0x24, (byte)0xb8, (byte)0xdc, (byte)0x09, (byte)0xe6, (byte)0x7b, (byte)0xd8, (byte)0xb2, (byte)0x40};
	/**
	 * 
	 */
	public TestResolver() {
		// TODO Auto-generated constructor stub
	}
	//db should be replaced with some way to specify a database
	public TestResolver(String db) {
		// TODO Auto-generated constructor stub
	}
	/**
	 * 
	 * @param userName
	 * @return the salt of userName, if userName DNE then return null
	 */
	public byte[] getUserSalt(String userName){
		//if(userName.equalsIgnoreCase("bob"))
			return bobSalt;
		//return null;//TODO
	}
	
	public byte[] getUserHash(String userName){
		if(userName.equalsIgnoreCase("bob"))
			return Hash.getStretchedSHA256(Hash.getSHA256("password".getBytes()), bobSalt, AuthServer.stretchLength);
		return null; //TODO
	}
	/**
	 * parses request and returns a Request that is the same as request but with
	 * the reply data member appropriately filled.
	 * @param request the request to handle
	 * @return same as request but with the reply data member appropriately filled.
	 * @throws UnknownHostException 
	 */
	public Request resolve(Request request) throws UnknownHostException{
		request.getClass().cast(request);
		switch(request.operation){
			case ADD:
				request.setReply(true);
				break;
			case REMOVE:
				request.setReply(true);
				break;
			case CHECK:
				request.setReply(true);
				break;
			case CHANGENAME:
				request.setReply(true);
				break;
			case CHANGEPASSWORD:
				request.setReply(true);
				break;
			case GETINFO:
				Map<String, Object> reply = new HashMap();
				reply.put("IP", InetAddress.getLocalHost());
				reply.put("TIME", new Watch());
				request.setReply(reply);
				break;
			case SETADMIN:
				request.setReply(true);
				break;
			case LIST:
				List<Map<String, Object>> reply2 = new ArrayList();
				reply2.add((Map<String, Object>) new HashMap().put("IP", InetAddress.getLocalHost()));
				reply2.get(0).put("TIME", new Watch());
				reply2.add(new HashMap());
				reply2.get(1).put("IP", InetAddress.getByAddress(new byte[]{1,2,3,4}));
				reply2.get(1).put("TIME", new Watch(1993, 02, 13));
				request.setReply(reply2);
				break;
			case HISTORY:
				List<Map<String, Object>> reply3 = new ArrayList();
				reply3.add((Map<String, Object>) new HashMap().put("IP", InetAddress.getLocalHost()));
				reply3.get(0).put("TIME", new Watch());
				reply3.add(new HashMap());
				reply3.get(1).put("IP", InetAddress.getByAddress(new byte[]{1,2,3,4}));
				reply3.get(1).put("TIME", new Watch(1993, 02, 13));
				request.setReply(reply3);
				break;
		}
		return request;
	}

}
