package org.example.handler;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import org.example.service.TaskService;
import org.example.util.TaskUtil;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
public class TaskHandler {
    public static final ObjectMapper mapper = new ObjectMapper();
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
                Optional<TaskUtil> task=TaskService.getTask(taskId);
                if(task.isPresent())
                {
                    response=mapper.writeValueAsString(task.get());
                    statusCode=200;
                }
                else
                    {   response = "{\"error\":\"Task not found\"}";
                        statusCode = 404;
                    }
            }
            else
                response=mapper.writeValueAsString(TaskService.reqAllTasks());
        }
        else if(method.equals("POST"))
        {
            try{
                String body = new String(exchange.getRequestBody().readAllBytes());
                TaskUtil taskUtil=mapper.readValue(body,TaskUtil.class);
                if(taskUtil.getTaskId() == null || taskUtil.getTaskId().isBlank())
                {
                    response = "{\"error\":\"taskId is required\"}";
                    statusCode = 400;
                }
                else if(taskUtil.getResponse() == null || taskUtil.getResponse().isBlank())
                {
                    response = "{\"error\":\"response is required\"}";
                    statusCode = 400;
                }
                else
                {
                    boolean exists = TaskService.checkExistingId(taskUtil.getTaskId());
                    if(exists)
                    {
                        response = "{\"error\":\"Task ID already exists\"}";
                        statusCode = 409;
                    }
                    else
                    {
                        TaskService.addTask(taskUtil);
                        response = mapper.writeValueAsString(taskUtil);
                        statusCode=201;
                    }
                }
            }
            catch(JsonProcessingException e)
            {
                response = "{\"error\":\"Invalid JSON\"}";
                statusCode = 400;
            }
        }
        else if(method.equals("DELETE"))
        {
              String path = exchange.getRequestURI().getPath();
              String[] parts = path.split("/");
              if(parts.length == 3)   
            {
                String taskId = parts[2];
                boolean removed = TaskService.deleteTask(taskId);
                if(removed)
                {
                    response = "{\"message\":\"Task " + taskId + " deleted\"}";
                    statusCode = 200;
                }
                else
                {
                    response = "{\"error\":\"Task not found\"}";
                    statusCode = 404;
                    }
            }
            else
            {
                response = "{\"error\":\"Task ID is required\"}";
                statusCode = 400;
            }   
        }
        else if(method.equals("PUT"))
        {
            String path = exchange.getRequestURI().getPath();
            String[] parts = path.split("/");
            if(parts.length==3)
            {
                String taskId=parts[2];
                try{
                    String body = new String(exchange.getRequestBody().readAllBytes());
                    Optional<TaskUtil> task=TaskService.getTask(taskId);
                    if(task.isPresent())
                    {
                        TaskUtil updatedTask = mapper.readValue(body,TaskUtil.class);
                        if(updatedTask.getResponse() == null ||
                        updatedTask.getResponse().isBlank())
                        {
                            response = "{\"error\":\"response is required\"}";
                            statusCode = 400;
                        }
                        else
                        {
                            TaskUtil existingTask = task.get();
                            TaskService.updateTask(existingTask,updatedTask);
                            response=mapper.writeValueAsString(existingTask);
                            statusCode=200;
                        }
                    }
                    else
                        {   response = "{\"error\":\"Task not found\"}";
                            statusCode = 404;
                        }
                }
                catch(JsonProcessingException e)
                {
                    response = "{\"error\":\"Invalid JSON\"}";
                    statusCode = 400;
                }
            }
        }
        else
        {
            statusCode=405;
            response = "{\"error\":\"Method not allowed\"}";
        }
        byte[] responseBytes = response.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(statusCode, responseBytes.length);
        exchange.getResponseBody().write(responseBytes);
        exchange.getResponseBody().close();

    }
}

