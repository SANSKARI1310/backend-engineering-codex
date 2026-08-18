package org.example.handler;
import java.io.IOException;
import com.sun.net.httpserver.HttpExchange;
public class UserHandler {
    public static void handle(HttpExchange exchange)throws IOException
    {
        String response ="";
        String path = exchange.getRequestURI().getPath();
        int statusCode=0;
        if(path.equals("/user"))
        {
            statusCode=200;
            response="User";
        }

        exchange.sendResponseHeaders(statusCode, response.length());
        exchange.getResponseBody().write(response.getBytes());
        exchange.getResponseBody().close();
    }
}

