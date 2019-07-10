package hr.fer.zemris.java.webserver;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * Http server used for serving smartScript scripts and other html files over http
 * @author Matija
 *
 */
public class SmartHttpServer {
	
	private static char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXVZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
	
	private String address;
	private String domainName;
	private int port;
	private int workerThreads;
	private int sessionTimeout;
	private Map<String, String> mimeTypes = new HashMap<String, String>();
	private ServerThread serverThread;
	private ExecutorService threadPool;
	private Path documentRoot;
	
	private Map<String,IWebWorker> workersMap = new HashMap<String, IWebWorker>();
	
	private Map<String, SessionMapEntry> sessions = new HashMap<String, SmartHttpServer.SessionMapEntry>();
	private Random sessionRandom = new Random();

	public static void main(String[] args) {
		if(args.length!=1) {
			System.err.println("Expected exactly one argument! (Path to the config file)");
			return;
		}
		
		try {
			new SmartHttpServer(args[0]).start();
			
		} catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			System.err.println(e.getCause() + " -> " + e.getMessage());
		}
	}

	/**
	 * Creates and initialises the http server
	 * @param configFileName configuration file name (path to the config file)
	 * @throws IOException if and I/O error occurs
	 * @throws ClassNotFoundException if a worker class is not found
	 * @throws InstantiationException if a worker class cannot be instantiated
	 * @throws IllegalAccessException if a worker class cannot be accessed
	 */
	public SmartHttpServer(String configFileName) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		Path propPath = Paths.get(configFileName);

		InputStream propIn = Files.newInputStream(propPath);
		Properties prop = new Properties();
		prop.load(propIn);
		propIn.close();

		this.address = Objects.requireNonNull(prop.getProperty("server.address"));
		this.domainName = Objects.requireNonNull(prop.getProperty("server.domainName"));
		this.port = Integer.parseInt(Objects.requireNonNull(prop.getProperty("server.port")));
		this.workerThreads = Integer.parseInt(Objects.requireNonNull(prop.getProperty("server.workerThreads")));
		this.sessionTimeout = Integer.parseInt(Objects.requireNonNull(prop.getProperty("session.timeout")));
		this.documentRoot = Paths.get(Objects.requireNonNull(prop.getProperty("server.documentRoot"))).toFile()
				.getCanonicalFile().toPath();

		Path mimePropPath = Paths.get(Objects.requireNonNull(prop.getProperty("server.mimeConfig")));
		Path workersPropPath = Paths.get(Objects.requireNonNull(prop.getProperty("server.workers")));

		InputStream mimePropIn = Files.newInputStream(mimePropPath);
		InputStream workersPropIn = Files.newInputStream(workersPropPath);
		Properties mimeProp = new Properties();
		Properties workersProp = new Properties();
		mimeProp.load(mimePropIn);
		workersProp.load(workersPropIn);
		workersPropIn.close();
		mimePropIn.close();

		for (var entry : mimeProp.entrySet()) {
			this.mimeTypes.put(entry.getKey().toString(), entry.getValue().toString());
		}
		for (var entry : workersProp.entrySet()) {
			String path = entry.getKey().toString();
			String fqcn = entry.getValue().toString();
			
			Class<?> referenceToClass = this.getClass().getClassLoader().loadClass(fqcn);
			@SuppressWarnings("deprecation")
			Object newObject = referenceToClass.newInstance();
			IWebWorker iww = (IWebWorker)newObject;
			workersMap.put(path, iww);
		}

		this.serverThread = new ServerThread();

		sessionCleaner();
	}

	/**
	 * Scheduled cleaning of the unused sessions (too old sessions)
	 */
	private void sessionCleaner() {
		Timer t = new Timer(true);
		t.schedule(new TimerTask() {
			@Override
			public void run() {
				System.out.println("Cleaning...");
				var it = sessions.entrySet().iterator();
				while(it.hasNext()) {
					var session = it.next();
					if(session.getValue().validUntil < System.currentTimeMillis()) {
						it.remove();
					}
				}
			}
		}, 5 * 60 * 1000, 5 * 60 * 1000); //every 5 minutes
	}

	/**
	 * Starts the server
	 */
	protected synchronized void start() {
		if(threadPool==null) 
			threadPool = Executors.newFixedThreadPool(this.workerThreads);
		if (!serverThread.isRunning())
			serverThread.start();
	}

	/**
	 * Stops the server
	 */
	protected synchronized void stop() {
		serverThread.stopRunning();
		threadPool.shutdown();
	}

	/**
	 * Thread used for serving server requests
	 * @author Matija
	 *
	 */
	protected class ServerThread extends Thread {

		private AtomicBoolean running = new AtomicBoolean(false);

		@Override
		public void run() {
			running.set(true);
			try {
				ServerSocket serverSocket = new ServerSocket();
				serverSocket.bind(new InetSocketAddress(InetAddress.getByName(address), port));
				while (running.get()) {
					Socket client = serverSocket.accept();
					ClientWorker cw = new ClientWorker(client);
					threadPool.submit(cw);
				}
				serverSocket.close();
			} catch (IOException e) {
				System.err.println("Error: " + e.getMessage());
			}
		}

		/**
		 * Returns true iff thread is running
		 * @return true iff thread is running
		 */
		public boolean isRunning() {
			return running.get();
		}

		/**
		 * Stops the thread from running
		 */
		public void stopRunning() {
			running.set(false);
		}

	}

	/**
	 * Worker used for serving a single server request
	 * @author Matija
	 *
	 */
	private class ClientWorker implements Runnable, IDispatcher {
		private Socket csocket;
		private PushbackInputStream istream;
		private OutputStream ostream;
		private String version;
		private String method;
		private String host;
		private Map<String, String> params = new HashMap<String, String>();
		private Map<String, String> tempParams = new HashMap<String, String>();
		private Map<String, String> permPrams = new HashMap<String, String>();
		private List<RCCookie> outputCookies = new ArrayList<RequestContext.RCCookie>();
		private String SID;
		
		private RequestContext context;

		/**
		 * Creates and initialises a worker for serving on the given socket
		 * @param csocket socket on which to communicate with the client
		 */
		public ClientWorker(Socket csocket) {
			super();
			this.csocket = csocket;
		}

		@Override
		public void run() {
			try {

				istream = new PushbackInputStream(new BufferedInputStream(csocket.getInputStream()));
				ostream = new BufferedOutputStream(csocket.getOutputStream());

				byte[] request = readRequest(istream);
				if (request == null) {
					sendError(ostream, 400, "Bad request");
					return;
				}
				String requestStr = new String(request, StandardCharsets.US_ASCII);

				// Extract (method, requestedPath, version) from firstLine
				List<String> headers = extractHeaders(requestStr);
				String[] firstLine = headers.isEmpty() ? null : headers.get(0).split(" ");
				if (firstLine == null || firstLine.length != 3) {
					sendError(ostream, 400, "Bad request");
					return;
				}
				this.method = firstLine[0];
				String requestedPathString = firstLine[1];
				this.version = firstLine[2];

				// if method not GET or version not HTTP/1.0 or HTTP/1.1 return response status
				// 400
				if (!method.equals("GET") || !version.equals("HTTP/1.0") && !version.equals("HTTP/1.1")) {
					sendError(ostream, 400, "Bad request");
					return;
				}

				// Go through headers, and if there is header “Host: xxx”, assign host property
				// to trimmed value after “Host:”; else, set it to server’s domainName
				// If xxx is of form some-name:number, just remember “some-name”-part
				for (String s : headers) {
					if (s.startsWith("Host:")) {
						String[] split = s.split(":");
						String host = split[1].trim();
						if (host.contains(":")) {
							this.host = host.split(":")[0].trim();
						} else {
							this.host = host;
						}
						break;
					}
				}
				if (this.host == null) {
					this.host = SmartHttpServer.this.domainName;
				}
				
				//check session
				checkSession(headers);

				String path;
				String paramString;
				String[] splitPath = requestedPathString.split("\\?");
				path = splitPath[0];
				paramString = splitPath.length == 1 ? null : splitPath[1];
				parseParameters(paramString);
				
				this.context = new RequestContext(ostream, params, permPrams, outputCookies, tempParams, SID, this);
				
				internalDispatchRequest(path, true);

				istream.close();
				ostream.close();

			} catch (Exception ex) {
				throw new RuntimeException(ex.getMessage());
			}
		}

		/**
		 * Check whether the session is valid. If it is not, assigns the current request
		 * to the new session.
		 * @param headers headers of the request
		 */
		private synchronized void checkSession(List<String> headers) {
			String sidCandidate = null;
			
			for(String line : headers) {
				if(!line.startsWith("Cookie:")) continue;
				String[] cookies = line.split(":")[1].trim().split(";");
				for(String cookie : cookies) {
					String[] cookieSplit = cookie.trim().split("=");
					if(cookieSplit[0].equals("sid")) {
						sidCandidate = cookieSplit[1].substring(1, cookieSplit[1].length()-1);
					}
				}
			}
			
			SessionMapEntry sme = null;
			if(sidCandidate!=null) {
				sme = SmartHttpServer.this.sessions.get(sidCandidate);
				if(sme==null || !sme.host.equals(this.host)) {
					sidCandidate=null;
				} else {
					if(sme.validUntil >= System.currentTimeMillis()) {
						sme.validUntil = System.currentTimeMillis() + sessionTimeout*1000;
						this.SID = sidCandidate;
					} else {
						sidCandidate = null;
					}
				}
			}
			
			if(sidCandidate == null) {
				String uniqueSid = "";
				for(int i=0; i<20; i++) { //20 => sid length
					uniqueSid += Character.toString(alphabet[sessionRandom.nextInt(alphabet.length)]);
				}
				sme = new SessionMapEntry();
				sme.sid = uniqueSid;
				sme.validUntil = System.currentTimeMillis() + sessionTimeout*1000;
				sme.map = new ConcurrentHashMap<>();
				sme.host = host;
				sessions.put(uniqueSid, sme);
				
				outputCookies.add(new RCCookie("sid", uniqueSid, null, host, "/"));
				
				this.SID = uniqueSid;
			}
			
			this.permPrams = sme.map;
		}

		/**
		 * Parse parameters given in the url
		 * @param paramString string containing the parameters from the url
		 */
		private void parseParameters(String paramString) {
			if(paramString==null) return;
			String[] split = paramString.split("&");

			for (String p : split) {
				String[] param = p.split("=");
				this.params.put(param[0], param[1]);
			}
		}

		/**
		 * Reads the request from the input stream and returns it as a byte array
		 * @param is input stream form which to read the request
		 * @return the request
		 * @throws IOException
		 */
		private byte[] readRequest(InputStream is) throws IOException {

			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			int state = 0;
			l: while (true) {
				int b = is.read();
				if (b == -1)
					return null;
				if (b != 13) {
					bos.write(b);
				}
				switch (state) {
				case 0:
					if (b == 13) {
						state = 1;
					} else if (b == 10)
						state = 4;
					break;
				case 1:
					if (b == 10) {
						state = 2;
					} else
						state = 0;
					break;
				case 2:
					if (b == 13) {
						state = 3;
					} else
						state = 0;
					break;
				case 3:
					if (b == 10) {
						break l;
					} else
						state = 0;
					break;
				case 4:
					if (b == 10) {
						break l;
					} else
						state = 0;
					break;
				}
			}
			return bos.toByteArray();

		}

		/**
		 * Sends an error to the user
		 * @param cos output stream to the user
		 * @param statusCode error status code
		 * @param statusText error status text
		 * @throws IOException
		 */
		private void sendError(OutputStream cos, int statusCode, String statusText) throws IOException {

			cos.write(("HTTP/1.1 " + statusCode + " " + statusText + "\r\n" + "Server: simple java server\r\n"
					+ "Content-Type: text/plain;charset=UTF-8\r\n" + "Content-Length: 0\r\n" + "Connection: close\r\n"
					+ "\r\n").getBytes(StandardCharsets.US_ASCII));
			cos.flush();

		}

		/**
		 * Extracts header lines from the request
		 * @param requestHeader http header
		 * @return
		 */
		private List<String> extractHeaders(String requestHeader) {
			List<String> headers = new ArrayList<String>();
			String currentLine = null;
			for (String s : requestHeader.split("\n")) {
				if (s.isEmpty())
					break;
				char c = s.charAt(0);
				if (c == 9 || c == 32) {
					currentLine += s;
				} else {
					if (currentLine != null) {
						headers.add(currentLine);
					}
					currentLine = s;
				}
			}
			if (!currentLine.isEmpty()) {
				headers.add(currentLine);
			}
			return headers;
		}

		public void internalDispatchRequest(String urlPath, boolean directCall) throws Exception {
			
			if((urlPath.equals("/private") || urlPath.startsWith("/private/")) && directCall) {
				sendError(ostream, 404, "Not found");
				return;
			}
			
			if(urlPath.startsWith("/ext/")) {
				String fqcn = "hr.fer.zemris.java.webserver.workers." + urlPath.substring(5);
				
				Class<?> referenceToClass = this.getClass().getClassLoader().loadClass(fqcn);
				@SuppressWarnings("deprecation")
				Object newObject = referenceToClass.newInstance();
				IWebWorker iww = (IWebWorker)newObject;
				iww.processRequest(context);
				return;
			}
			
			if(workersMap.containsKey(urlPath)) {
				workersMap.get(urlPath).processRequest(context);
				return;
			}
			
			Path requestedPath = SmartHttpServer.this.documentRoot.resolve(Paths.get(urlPath.substring(1)));
			if (!requestedPath.normalize().startsWith(SmartHttpServer.this.documentRoot)) {
				sendError(ostream, 403, "Forbidden");
				return;
			}

			if (!Files.exists(requestedPath) || !Files.isRegularFile(requestedPath)
					|| !Files.isReadable(requestedPath)) {
				sendError(ostream, 404, "Not found");
				return;
			}
			
			String extension = null;
			int lastDot = requestedPath.getFileName().toString().lastIndexOf(".");
			extension = lastDot != -1 ? requestedPath.getFileName().toString().substring(lastDot).replace(".", "") : null;
			
			if("smscr".equals(extension)) {
				
				context.setMimeType("text/html");
				
				String lines = Files.readString(requestedPath);
				new SmartScriptEngine(new SmartScriptParser(lines).getDocumentNode(), context).execute();
				
			} else {

				String mime = null;
				for (var m : SmartHttpServer.this.mimeTypes.entrySet()) {
					if (m.getKey().equals(extension)) {
						mime = m.getValue();
						break;
					}
				}
				mime = mime == null ? "application/octet-stream" : mime;

				
				context.setContentLength(Files.size(requestedPath));
				context.setMimeType(mime);

				InputStream is = new BufferedInputStream(Files.newInputStream(requestedPath));
				byte[] buff = new byte[1024];
				while (true) {
					int r = is.read(buff);
					if (r < 1)
						break;
					context.write(buff, 0, r);
				}
			
			}
		}

		@Override
		public void dispatchRequest(String urlPath) throws Exception {
			internalDispatchRequest(urlPath, false);
		}

	}
	
	private static class SessionMapEntry {
		@SuppressWarnings("unused")
		String sid;
		String host;
		long validUntil;
		Map<String,String> map;
	}
	
}
