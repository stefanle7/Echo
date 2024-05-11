package echo;

import java.net.Socket;

public class SecurityProxy extends ProxyHandler {
    SafeTable users;
    Boolean loggedIn = false;

    // ctr
    public SecurityProxy(Socket s) {
        super(s);
        users = SafeTable.getInstance();
    }

    @Override
    protected String response(String request) throws Exception {
        // breaks up the "new user password" into 3 array substrings (s = whitespace, + = 1 or more)
        String[] cmmd = request.split("\\s+");
        String answer = "";

        // checking if the first command is either new or login
        if (cmmd[0].equalsIgnoreCase("new")) {

            // first, check for existing users
            if (users.get(cmmd[1]) == null) {
                users.put(cmmd[1], cmmd[2]);
                answer = "ACCOUNT CREATED";
                shutDown();
            } else {
                answer = "ERROR: USER ALREADY EXISTS";
            }

        } else if (cmmd[0].equalsIgnoreCase("login")) {

            // log in to existing user
            String storedPassword = users.get(cmmd[1]);
            if (storedPassword != null && storedPassword.equals(cmmd[2])) {
                loggedIn = true;
                answer = "LOGIN SUCCESSFUL";
            } else {
                answer = "ERROR: INVALID LOGIN";
            }

        } else { // else, pass it down to a peer to see if this command is for something else
            if (loggedIn) {
                try {
                    answer = super.response(request);
                } catch (Exception e) {
                    answer = "ERROR: " + e.getMessage();
                }
            } else {
                answer = "ERROR: YOU MUST LOGIN FIRST";
            }
        }
        return answer;
    }
}
