package echo;

import java.util.HashMap;

public class SafeTable extends HashMap<String, String> {

    // singleton residing in static memory
    private static SafeTable instance;

    // ctr
    public SafeTable() {
        super();
    }

    public static synchronized SafeTable getInstance() {
        if (instance == null) {
            instance = new SafeTable();
        }
        return instance;
    }

    @Override
    public synchronized String get(Object key) {
        return super.get(key);
    }

    @Override
    public synchronized String put(String request, String response) {
        return super.put(request, response);
    }
}
