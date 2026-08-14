package org.example.handler;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;
public class TaskHandler {
    public static void handle(HttpExchange exchange)throws IOException
    {
        String response ="";
        String method = exchange.getRequestMethod();
        int statusCode=0;
        if(method.equals("GET"))
        {
            statusCode=200;
            String path = exchange.getRequestURI().getPath();
            String[] parts = path.split("/");
            if(parts.length==3)
            {
                String taskId=parts[2];
                response="Task "+taskId+" is completed";
            }
            else
                response="All tasks are completed";
        }
        else
            statusCode=405;
        exchange.sendResponseHeaders(statusCode, response.length());
        exchange.getResponseBody().write(response.getBytes());
        exchange.getResponseBody().close();

    }
}
