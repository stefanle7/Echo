package echo;

import java.net.Socket;

public class ProxyHandler extends RequestHandler {

    protected Correspondent peer;

    public ProxyHandler(Socket s) { super(s); }
    public ProxyHandler() { super(); }

    public void initPeer(String host, int port) {
        peer = new Correspondent();
        peer.requestConnection(host, port);
    }

    protected String response(String msg) throws Exception {
        // forward msg to peer
        peer.send(msg);
        // return peer's response
        String response = peer.receive();
        return response;
    }

    protected void shutDown() {
        super.shutDown(); // sets active to false, etc.
        peer.send("quit");
        peer.close();
    }
}