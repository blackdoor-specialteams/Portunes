/**
 * 
 */
package auth;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
//import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
//import java.net.SocketException;
import java.security.SecureRandom;
//import java.util.ArrayList;
//import java.util.Arrays;
import java.util.Scanner;

import javax.xml.bind.DatatypeConverter;
//import java.util.UUID;

//import auth.AuthRequest.Operation;
import auth.Resolver.UserNotFoundException;
//import util.Hash;
import util.Misc;
import util.SHE;
import util.Crypto.EncryptionResult;

/**
 * @author kAG0 Server which communicates with AuthClient by giving a session
 *         key to client and receiving/replying to client request with encrypted
 *         messages. Each connection is opened in a new thread and each thread
 *         handles requests by talking to a SQL db.
 */
public class AuthServer {
	private int port = 1234;
	private ServerSocket serverSocket;
	private boolean running = true;
	// TODO put in actual DB
	// TODO replace all authmanager stuff with nemo's Resolver(or whatever)
	// private AuthManager authManager; //replace this with a SQL db manager
	private Resolver authManager;
	public static final String greeting = "Portumnes";
	public static final int saltLength = 32;
	public static final int passLength = 32;
	public static final int stretchLength = 90500;// 90500 yields between 1/2s
													// and 1s on 2.4ghz
													// sandyBridge i7
													// 4500 yields about 100ms

	/**
	 * @param args
	 *            valid args are -port port#, -db userDB-file-location
	 */
	public static void main(String[] args) {
		AuthServer server = new AuthServer();

		for (int i = 0; i < args.length; i++) {
			if (args[i].equals("-port")) {
				server.setPort(Integer.parseInt(args[++i]));
			} else if (args[i].equalsIgnoreCase("-db")) {
				server.createManager(args[++i]);
			} else if (args[i].equalsIgnoreCase("-help")) {
				System.out
						.println("	-db <database>	set database for the server to reference.");
				System.out.println("	-port <port number>	");
				System.out
						.println("					set local port for the server to listen on.");
			} else
				System.err.println("invalid argument:" + args[i]);
		}

		server.start();

	}

	AuthServer() {
	}

	/**
	 * creates a new authentication manager with new database and default
	 * settings
	 */
	public void createManager() {
		authManager = new Resolver();// AuthManager();
	}

	/**
	 * creates a new authentication manager with database from specified file
	 * 
	 * @param DBFile
	 */
	public void createManager(String DBFile) {
		// try {
		//authManager = new Resolver(DBFile);
		// } catch (IOException e) {
		// System.err.println("specified file is corrupted or not a UserDB");
		// e.printStackTrace();
		// System.out.println("server will now quit.");
		// System.exit(-1);
		// }
	}

	/**
	 * start the server will listen on port 1234 if a port has not been
	 * specified already
	 */
	public void start() {
		if (authManager == null) {
			authManager = new Resolver();// AuthManager();
		}
		CLI cli = new CLI(this);
		cli.start();
		listen(port);
		acceptConnections();
	}

	/**
	 * save userDB to a file
	 * 
	 * @param location
	 *            location to save file
	 */
	// public void save(String location) {
	// try {
	// authManager.save(location);
	// } catch (IOException e) {
	// System.out.println(e.getMessage());
	// System.err.println("save location not valid, database not saved");
	// }
	// }

	private void acceptConnections() {
		Socket socket = null;
		while (running) {
			try {
				socket = serverSocket.accept();
				System.out.println("connection accepted from "
						+ socket.getRemoteSocketAddress());
			} catch (IOException e) {
				System.err.println("Unable to accept connection on port "
						+ port);
				e.printStackTrace();
			}
			AuthConnectionHandler handler = new AuthConnectionHandler(socket,
					authManager);
			handler.start();
		}
	}

	private void listen(int port) {
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("listenting on port: " + port + ".");
		} catch (IOException e) {
			System.err.println("Could not listen on port: " + port + ".");
			// e.printStackTrace();
			System.err.println("Server will now quit.");
			System.exit(-1);
		}
	}

	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @param port
	 *            the port to set
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * @return the running
	 */
	public boolean isRunning() {
		return running;
	}

	/**
	 * @param running
	 *            the running to set
	 */
	public void setRunning(boolean running) {
		this.running = running;
	}

	/**
	 * a command line interface for the server, supports save and exit commands
	 * 
	 * @author kAG0
	 * 
	 */
	class CLI extends Thread {
		AuthServer server;

		CLI(AuthServer server) {
			this.server = server;
		}

		public void run() {
			Scanner keyboard = new Scanner(System.in);
			String input;
			System.out.println("exit to quit, save to save");
			while (true) {
				input = keyboard.nextLine();
				if (input.equalsIgnoreCase("exit")) {
					server.setRunning(false);
					keyboard.close();
					System.exit(1);
				}
				// else if (input.equalsIgnoreCase("save")) {
				// System.out.println("enter location to save DB");
				// String location = keyboard.nextLine();
				// System.out.println(location);
				// server.save(location);
				// }
				else
					System.out.println("Invalid Command");
			}
		}
	}

	/**
	 * a multi-threaded connection handler for incoming connections
	 * 
	 * @author kAG0
	 * 
	 */
	class AuthConnectionHandler extends Thread {
		private Socket socket;
		private SocketAddress remoteAddress;
		// private AuthManager manager;// to be changed out for Resolver
		private Resolver resolver;
		// private OutputStream outputBuffer;
		private ObjectOutput outputObject;
		private InputStream inputBuffer;
		private ObjectInput inputObject;
		private String userName;
		private byte[] seshKey;

		AuthConnectionHandler(Socket socket, Resolver manager) {
			this.socket = socket;
			this.resolver = manager;
		}

		public void run() {
			// System.out.println("herp derp, I should reply");
			openSocketInput();
			openSocketOutput();
			if (recieveGreeting()) {
				thing1();
			} else {
				try {
					closeSocket();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

		private void thing1() {
			try {
				try {
					handshake();
					Request request = recieveRequest();
					if (request == null)
						throw new IOException("Request not recieved.");
					request.origin = socket.getInetAddress();
					Request reply = resolver.resolve(request);
					sendReply(reply);
					//closeSocket();
				} catch (UserNotFoundException e) {
					System.err.println("Authentication on authority of "
							+ e.getUserName() + " failed.");
				}

			} catch (IOException e) {
				e.printStackTrace();
				//System.err.print(e);
				;
			}finally{
				try {
					closeSocket();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		private boolean recieveGreeting() {
			boolean goodGreet = false;
			try {
				String greeting = (String) inputObject.readObject();
				goodGreet = greeting.contains(AuthServer.greeting); // .equalsIgnoreCase(AuthServer.greeting);
				if (!goodGreet)
					return goodGreet;
				userName = greeting.replace(AuthServer.greeting, "");
				// userName = (String) inputObject.readObject();
				return true;
			} catch (ClassNotFoundException | IOException e) {
				System.err.println("Probelm encountered with greeting.");
				return false;
			}

		}

		private Object decryptComm(byte[] seshKey, EncryptionResult comm)
				throws IOException, ClassNotFoundException {
			byte[] out = SHE.doSHE(comm.getOutput(), seshKey, comm.getIv())
					.getOutput();
			ByteArrayInputStream byteInS = new ByteArrayInputStream(out);
			ObjectInputStream objInS = new ObjectInputStream(byteInS);
			Object reply = objInS.readObject();
			objInS.close();
			return reply;
		}

		// private byte[] sendCredentials(){
		// byte[] credentials = new byte[saltLength + passLength];
		// SecureRandom random = new SecureRandom();
		// byte[] SK = new byte[passLength];
		// random.nextBytes(SK);
		// System.arraycopy(resolverInstance.getUserSalt(userName), 0,
		// credentials, 0, saltLength);
		// System.arraycopy(Misc.XOR(resolverInstance.getUserHash(userName),
		// SK), 0, credentials, saltLength, passLength);
		// outputObject.writeObject(credentials);
		// return credentials;
		// }
		/**
		 * 
		 * @return the session key for this session
		 * @throws UserNotFoundException
		 */
		private byte[] handshake() throws IOException,
				UserNotFoundException {
			seshKey = new byte[32];
			new SecureRandom().nextBytes(seshKey);
			byte[] credentials = new byte[saltLength + passLength];
			byte[] uSalt;
			byte[] uHash;
			try {
				uSalt = resolver.getUserSalt(userName);
				uHash = resolver.getUserHash(userName);
				//System.out.println(DatatypeConverter.printHexBinary(uHash) + "\n" +
				//		DatatypeConverter.printHexBinary(uSalt));//TODO
			} catch (UserNotFoundException e) {
				e.printStackTrace();
				outputObject.writeObject(null);
				throw new UserNotFoundException(e.getUserName());
			}
			System.arraycopy(uSalt, 0, credentials, 0, saltLength);
			System.arraycopy(Misc.XOR(seshKey, uHash), 0, credentials,
					saltLength, passLength);
			outputObject.writeObject(credentials);
			return seshKey;
		}

		private void openSocketInput() {
			try {
				remoteAddress = socket.getRemoteSocketAddress();
				//System.out.println(remoteAddress);
				// inputObject = new ObjectInputStream(socket.getInputStream());
				inputBuffer = new BufferedInputStream(socket.getInputStream());
				inputObject = new ObjectInputStream(inputBuffer);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		private void openSocketOutput() {
			try {
				outputObject = new ObjectOutputStream(socket.getOutputStream());
				// outputBuffer = new
				// BufferedOutputStream(socket.getOutputStream());
				// outputObject = new ObjectOutputStream(outputBuffer);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		private Request recieveRequest() {
			Request request = null;
			EncryptionResult in = null;
			try {
				in = (EncryptionResult) inputObject.readObject();
				request = (Request) decryptComm(seshKey, in);
			} catch (ClassNotFoundException | IOException e2) {
				System.err.println(e2);
			}
			return request;
		}

		// private void sendReply(AuthReply reply) {
		// try {
		// outputObject.writeObject(reply);
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }
		private void sendReply(Request reply) {
			try {
				outputObject.writeObject(SHE.doSHE(Misc.serialize(reply),
						seshKey));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		private void closeSocket() throws IOException {
			System.out.println("attempting to close conneciton from "
					+ socket.getRemoteSocketAddress() + " on "
					+ socket.getLocalPort());
			try {
				inputObject.close();
				inputBuffer.close();
				outputObject.close();
				socket.close();
			} catch (NullPointerException e) {
				System.err
						.println("Couldn't close connections. Was the connection reset?");
			}
		}
	}
}
