package org.example.handler;
import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.util.TaskUtil;

public class TaskHandler {
    
    private static final ObjectMapper mapper = new ObjectMapper();
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
                TaskUtil taskUtil=new TaskUtil(taskId,"Task "+taskId+" is completed");
                response=mapper.writeValueAsString(taskUtil);
            }
            else
                response="All tasks are completed";
        }
        else if(method.equals("POST"))
        {
            String body = new String(exchange.getRequestBody().readAllBytes());
            TaskUtil taskUtil=mapper.readValue(body,TaskUtil.class);
            System.out.println(taskUtil.getTaskId());
            response = mapper.writeValueAsString(taskUtil);
            System.out.println(response);
            statusCode=201;
        }
        else if(method.equals("DELETE"))
        {
            response = "/TASK IS DELETED";
            statusCode=200;
        }
        else
            statusCode=405;
        byte[] responseBytes = response.getBytes();
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(statusCode, responseBytes.length);
        exchange.getResponseBody().write(responseBytes);
        exchange.getResponseBody().close();

    }
}

