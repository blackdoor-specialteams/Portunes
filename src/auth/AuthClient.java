/**
 * 
 */
package auth;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.xml.bind.DatatypeConverter;

import auth.AuthRequest.CSHI;
import auth.AuthRequest.Operation;
//import auth.User.UserRight;
import util.Crypto.EncryptionResult;
import util.Hash;
import util.Misc;
import util.SHE;
import auth.Resolver.UserNotFoundException;

/**
 * @author kAG0
 * API for a remote client to authenticate, add, remove or modify users in a database.
 * Now with many more operations!
 * All communication is encrypted!
 */
public class AuthClient {
	private String server;
	private int port;
	private Socket socket;
	//private OutputStream outputBuffer;
	private ObjectOutput outputObject;
	private InputStream inputBuffer;
	private ObjectInput inputObject;
	public static final String greeting = AuthServer.greeting;
	public static final int saltLength = AuthServer.saltLength;
	public static final int passLength = AuthServer.passLength;
	public static final int stretchLength = AuthServer.stretchLength;
	
//	AuthClient(){
//		server = null;
//		port = 0;
//		socket = null;
//		outputBuffer = null;
//		outputObject = null;
//		inputBuffer = null;
//		inputObject = null;
//	}
	
	public AuthClient(String server, int port){
		this.server = server;
		this.port = port;
		//openSocket();
	}
	
	//we dont Reeeeealy need the following. CJ will just compose requests in the gui
//	/**
//	 * check if there exists a user with given username and password in the database
//	 * @param userName
//	 * @param password
//	 * @return true if user exists with given username and password, else false
//	 */
//	public boolean checkUser(String userName, String password){
//		Request request = new CHECK(userName, Hash.getSHA256(password.getBytes()));
//		request.admin = false;
//		int id = request.getID();
//		Request reply = null;
//		reply = exchange(request);
//		if(reply == null){
//			System.err.println("Reply from server not recieved.");
//			return false;
//		}
//			
//		if(reply.getId() == id){
//			return reply.isOperationCompleted();
//		}
//		else System.err.println("id of reply does not match id of sent request.");
//		return false;
//	}
//	
//	/**
//	 * change a user's password. The current password must be known.
//	 * @param userName
//	 * @param oldPassword
//	 * @param newPassword
//	 * @return true if password has been changed, else false
//	 */
//	public boolean changePassword(String userName, String oldPassword, String newPassword){
//		AuthRequest request = new AuthRequest(Operation.CHANGEPASSWORD);
//		request.setUserName(userName);
//		request.setPasswordHash(Hash(oldPassword));
//		request.setNewPasswordHash(Hash(newPassword));
//		request.setIndicator(CSHI.NORMAL);
//		int id = request.getID();
//		AuthReply reply = null;
//		reply = exchange(request);
//		if(reply == null){
//			System.err.println("Reply from server not recieved.");
//			return false;
//		}
//		if(reply.getId() == id){
//			return reply.isOperationCompleted();
//		}
//		else System.err.println("id of reply does not match id of sent request.");
//		return false;
//	}
//	
//	/**
//	 * add a user to the database under the authority of authUser
//	 * @param userName
//	 * @param password
//	 * @param rights rights that the new user should have
//	 * @param authUserName
//	 * @param authPassword
//	 * @return true if user has been created, else false
//	 */
//	public boolean addUser(String userName, String password,UserRight[] rights, String authUserName, String authPassword) {
//		AuthRequest request = new AuthRequest(Operation.ADD);
//		request.setUserName(userName);
//		request.setPasswordHash(Hash(password));
//		request.setRights(rights);
//		request.setAuthUserName(authUserName);
//		request.setAuthPasswordHash(Hash(authPassword));
//		request.setIndicator(CSHI.AUTH);
//		int id = request.getID();
//		AuthReply reply = null;
//		reply = exchange(request);
//		if(reply == null){
//			System.err.println("Reply from server not recieved.");
//			return false;
//		}
//		if(reply.getId() == id){
//			return reply.isOperationCompleted();
//		}
//		else System.err.println("id of reply does not match id of sent request.");
//		return false;
//	}
//	
//	/**
//	 * Remove user with userName. only users with REMOVE rights may remove users, however users may remove themselves without remove rights.
//	 * @param userName
//	 * @param authUserName authUserName should be the same as userName if user wants to remove themselves
//	 * @param authPassword
//	 * @return true if user has been removed, else false
//	 */
//	public boolean removeUser(String userName, String authUserName, String authPassword) {
//		AuthRequest request = new AuthRequest(Operation.REMOVE);
//		request.setUserName(userName);
//		request.setAuthUserName(authUserName);
//		request.setAuthPasswordHash(Hash(authPassword));
//		request.setIndicator(CSHI.AUTH);
//		int id = request.getID();
//		AuthReply reply = null;
//		reply = exchange(request);
//		if(reply == null){
//			System.err.println("Reply from server not recieved.");
//			return false;
//		}
//		if(reply.getId() == id){
//			return reply.isOperationCompleted();
//		}
//		else System.err.println("id of reply does not match id of sent request.");
//		return false;
//	}
//	
//	/**
//	 * change user's name. this also changes the key under which the user is found in the database
//	 * @param oldUserName
//	 * @param newUserName
//	 * @param authUserName
//	 * @param authPassword
//	 * @return true if name has been changed, else false
//	 */
//	public boolean changeUserName(String oldUserName, String newUserName, String authUserName, String authPassword){
//		AuthRequest request = new AuthRequest(Operation.CHANGENAME);
//		request.setUserName(oldUserName);
//		request.setNewUserName(newUserName);
//		request.setAuthUserName(authUserName);
//		request.setAuthPasswordHash(Hash(authPassword));
//		request.setIndicator(CSHI.AUTH);
//		int id = request.getID();
//		AuthReply reply = null;
//		reply = exchange(request);
//		if(reply == null){
//			System.err.println("Reply from server not recieved.");
//			return false;
//		}
//		if(reply.getId() == id){
//			return reply.isOperationCompleted();
//		}
//		else System.err.println("id of reply does not match id of sent request.");
//		return false;
//	}
	
	private void sendGreeting(String userName) throws IOException{
		
		outputObject.writeObject(greeting+userName);
		//outputObject.writeObject(userName);
		//TODO add username to greeting
	}
	
	/**
	 * a simple clean method that handles all networking
	 * 
	 * @param request
	 *            the request to be sent to the server
	 * @return returns the server's reply to request
	 * @throws UserNotFoundException
	 */
	public Request exchange(Request request) throws UserNotFoundException {
		try {
			openSocketOutput();
			openSocketInput();
		} catch (Exception e1) {
			return null;
		}
		try {
			sendGreeting(request.getAuthUserName());
			byte[] salt = new byte[saltLength];
			byte[] HSPSK = new byte[passLength];
			byte[] HSP = null;
			byte[] SK = null;
			byte[] credentials = null;
			Object cred = inputObject.readObject();
			if (cred == null) {
				throw new UserNotFoundException(request.getAuthUserName());
			}
			credentials = (byte[]) cred;

			System.arraycopy(credentials, 0, salt, 0, saltLength);

			System.arraycopy(credentials, saltLength, HSPSK, 0, passLength);
			
			//if (request.admin) {
				HSP = Hash.getStretchedSHA256(request.getAuthUserPW(), salt,
						stretchLength);
			//} else
			//	HSP = Hash.getStretchedSHA256(request, salt,
			//			AuthServer.stretchLength);
			SK = Misc.XOR(HSP, HSPSK);
			System.out.println("client\n" +
								"hash " + DatatypeConverter.printHexBinary(HSP) + "\n" +
								"salt " + DatatypeConverter.printHexBinary(salt));

			outputObject.writeObject(SHE.doSHE(Misc.serialize(request), SK,null));// send encrypted request


			request = reciveReply(SK);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Server probably disconnected unexpectedly");
			e.printStackTrace();
		} finally {
			try {
				closeSocket();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return request;
	}
	
		
	private void openSocketOutput() throws Exception{
		try {
			socket = new Socket(server, port);
			System.out.println("connected to " + server + ":" + port);
			outputObject = new ObjectOutputStream(socket.getOutputStream());
			//outputBuffer = new BufferedOutputStream(socket.getOutputStream());
			//outputObject = new ObjectOutputStream(outputBuffer);
			
		}catch(SocketException e){
			System.err.println("SocketException: " + e.getMessage());
			throw new Exception(e.getMessage());
		} catch (UnknownHostException e) {
			//e.printStackTrace();
			System.err.println("could not find " + server);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void openSocketInput() throws Exception{
		try {
			inputBuffer = new BufferedInputStream(socket.getInputStream());
			inputObject = new ObjectInputStream(inputBuffer);
		} catch(SocketException e){
			System.err.println("SocketException: " + e.getMessage());
			throw new Exception(e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void sendRequest(Request request) throws IOException{
		outputObject.writeObject(request);
	}
	private Object decryptComm(byte[] seshKey, EncryptionResult comm) throws IOException, ClassNotFoundException{
		byte[] out = SHE.doSHE(comm.getOutput(), seshKey, comm.getIv()).getOutput();
		ByteArrayInputStream byteInS = new ByteArrayInputStream(out);
		ObjectInputStream objInS = new ObjectInputStream(byteInS);
		Object reply = objInS.readObject();
		objInS.close();
		return reply;
	}
	private Request reciveReply(byte[] sessionKey) throws ClassNotFoundException, IOException{
		if(inputObject != null){
			Request reply = (Request) decryptComm(sessionKey, (EncryptionResult) inputObject.readObject());
			return reply;
		}
		else return null;
	}
	private void closeSocket() throws IOException{
		try{
			inputObject.close();
			inputBuffer.close();
			outputObject.close();
			socket.close();
		}catch(NullPointerException e){
			System.err.println("Couldn't close connections. Was the connection reset?");
		}
	}

}
