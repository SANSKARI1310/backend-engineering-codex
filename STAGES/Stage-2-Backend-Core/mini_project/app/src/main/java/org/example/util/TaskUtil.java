package org.example.util;

public class TaskUtil {
    private String taskId;
    private String response; 
    public TaskUtil()
    {}
    public TaskUtil(String taskId,String response)
    {
        this.taskId=taskId;
        this.response=response;
    }
    public String getTaskId()
    {
        return taskId;
    }
    public void setTaskId(String taskId)
    {
        this.taskId=taskId;
    }
    public String getResponse()
    {
        return response;
    }
    public void setResponse(String response)
    {
        this.response=response;
    }
}
