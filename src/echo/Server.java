package echo;

import java.util.*;
import java.io.*;
import java.net.*;

public class Server {

    protected ServerSocket mySocket;
    protected int myPort;
    public static boolean DEBUG = true;
    protected Class<?> handlerType;

    public Server(int port, String handlerType) {
        try {
            myPort = port;
            mySocket = new ServerSocket(myPort);
            this.handlerType = (Class.forName(handlerType));
        } catch(Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        } // catch
    }


    public void listen() {
        while(true) {
            try {
                // accept a connection
                Socket clientSocket = mySocket.accept();

                // make handler
                RequestHandler handler = makeHandler(clientSocket);

                // start handler in its own thread
                Thread handlerThread = new Thread(handler);
                handlerThread.start();

            } catch (IOException e) {
                System.err.println("Error accepting connection: " + e.getMessage());
                break;
            }
        } // while
    }

    public RequestHandler makeHandler(Socket s) {
        try {
            // handler = a new instance of handlerType
            // use: try { handlerType.getDeclaredConstructor().newInstance() } catch ...
            // set handler's socket to s
            RequestHandler handler = (RequestHandler) handlerType.getDeclaredConstructor(Socket.class).newInstance(s);
            // return handler
            handler.setSocket(s);
            return handler;
        } catch(Exception e) {
            System.err.println("Error creating handler: " + e.getMessage());
            return null;
        }
    }



    public static void main(String[] args) {
        int port = 5555;
        String service = "echo.RequestHandler";
        if (1 <= args.length) {
            service = args[0];
        }
        if (2 <= args.length) {
            port = Integer.parseInt(args[1]);
        }
        Server server = new Server(port, service);
        if (DEBUG) System.out.println("Server listening at port " + port);
        server.listen();
    }
}