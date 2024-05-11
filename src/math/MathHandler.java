package math;

import echo.*;
import java.net.*;

public class MathHandler extends RequestHandler {

    public MathHandler(Socket sock) {
        super(sock);
    }

    @Override
    protected String response(String msg) throws Exception {
        String[] parts = msg.split(" ");
        String operation = parts[0];
        double result = Double.parseDouble(parts[1]);

        for (int i = 2; i < parts.length; i++) {
            double num = Double.parseDouble(parts[i]);
            switch (operation) {
                case "add":
                    result += num;
                    break;
                case "sub":
                    result -= num;
                    break;
                case "mul":
                    result *= num;
                    break;
                case "div":
                    if (num == 0) throw new IllegalArgumentException("Division by zero is invalid");
                    result /= num;
                    break;
                default:
                    throw new Exception("Invalid operation");
            }
        }
        return String.valueOf(result);
    }
}
