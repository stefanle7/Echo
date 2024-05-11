package echo;

import java.net.Socket;

public class ProxyServer extends Server {

    String peerHost;
    int peerPort;

    public ProxyServer(int port, String service, String peerHost, int peerPort) {
        super(port, service);
        this.peerHost = peerHost;
        this.peerPort = peerPort;
    }

    @Override
    public RequestHandler makeHandler(Socket s) {
        try {
            RequestHandler handler = super.makeHandler(s);
            ((ProxyHandler)handler).initPeer(peerHost, peerPort);
            return handler;
        } catch (Exception e) {
            System.err.println("Error creating ProxyHandler: " + e.getMessage());
            return null;
        }
    }

    public static void main(String[] args) {
        int port = 6666;
        int peerPort = 5555;
        String peerHost = "localhost";
        String service = "echo.ProxyHandler";

        if (1 <= args.length) {
            service = args[0];
        }
        if (2 <= args.length) {
            peerPort = Integer.parseInt(args[1]);
        }
        if (3 <= args.length) {
            port = Integer.parseInt(args[2]);
        }
        if (4 <= args.length) {
            peerHost = args[3];
        }
        Server server = new ProxyServer(port, service, peerHost, peerPort);
        server.listen();
    }
}
