/**
 * 
 */
package auth;

/**
 * @author kAG0
 *
 */
public class Resolver {

	/**
	 * 
	 */
	public Resolver() {
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
	
	public byte[] getUserHash(String userName) throws UserNotFoundException{
		return null; //TODO
	}
	/**
	 * parses request and returns a Request that is the same as request but with
	 * the reply data member appropriately filled.
	 * @param request the request to handle
	 * @return same as request but with the reply data member appropriately filled.
	 */
	public Request resolve(Request request) throws UserNotFoundException{
		switch(request.operation){
			case ADD:
				
				break;
			case REMOVE:
				break;
			case CHECK:
				break;
			case CHANGENAME:
				break;
			case CHANGEPASSWORD:
				break;
			case GETINFO:
				break;
			case SETADMIN:
				break;
			case LIST:
				break;
			case HISTORY:
				break;
		}
		return request;
	}
	
	/**
	 * note: this exception should only be thrown as part of the authentication process
	 * when handling requests like CHECK the response field of the request should just be null
	 * @author kAG0
	 *
	 */
	public static class UserNotFoundException extends Exception{
		private String username;
		UserNotFoundException(String userName){
			super(userName +" not found in database.");
			username = userName;
		}		
		public String getUserName(){
			return username;
		}
	}
}
