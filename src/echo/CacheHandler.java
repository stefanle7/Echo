package echo;

import java.net.Socket;

public class CacheHandler extends ProxyHandler {
    // static cache variable to store all the information in one spot
    private static SafeTable cache = SafeTable.getInstance();

    // ctr
    public CacheHandler(Socket s) {
        super(s);
    }
    public CacheHandler() {
        super();
    }

    // response method overridden from ProxyHandler
    @Override
    protected String response(String request) throws Exception {
        // Request was previously made and stored in cache
        if (cache.containsKey(request)) {
            System.out.println("Message in cache: " + request);
            return cache.get(request);
        } else {
            System.out.println("Request not found, forwarding to peer: " + request);
            // If not cached, forward the request to the peer
            String response = super.response(request);
            // cache request and response
            cache.put(request, response);
            return response;
        }
    }
}
