package echo;

import java.net.Socket;

public class RequestHandler extends Correspondent implements Runnable {
    protected boolean active; // response can set to false to terminate thread
    public RequestHandler(Socket s) {
        super(s);
        active = true;
    }
    public RequestHandler() {
        super();
        active = true;
    }
    // override in a subclass:
    protected String response(String msg) throws Exception {
        return "echo: " + msg;
    }
    // any housekeeping can be done by an override of this:
    protected void shutDown() {
        if (Server.DEBUG) System.out.println("handler shutting down");

        // added this in class but it shuts down the process after every echo call so I commented it out myself
        active = false;
    }
    public void run() {
        while(active) {
            try {
                // receive request
                String request = receive();

                // sends received to server
                if(Server.DEBUG) System.out.println("received: " + request);

                if(request.equals("quit")) {
                    shutDown();
                    break;
                }
                // send response
                String response = response(request);
                send(response);

                // sends sending to server
                if(Server.DEBUG) System.out.println("sending: " + response);

                // thread sleep
                Thread.sleep(1000);
            } catch(Exception e) {
                send(e.getMessage() + "... ending session");
                break;
            }
        }
        // close
        close();
    }
}