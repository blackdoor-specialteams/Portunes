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

import util.Watch;

/**
 * @author kAG0
 *
 */
public class TestResolver {

	/**
	 * 
	 */
	public TestResolver() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * 
	 * @param userName
	 * @return the salt of userName, if userName DNE then return null
	 */
	public byte[] getUserSalt(String userName){
		return null;//TODO
	}
	
	public byte[] getUserHash(String userName){
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
