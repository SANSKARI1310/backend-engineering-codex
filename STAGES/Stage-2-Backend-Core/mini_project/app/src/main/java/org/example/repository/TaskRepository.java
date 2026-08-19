package org.example.repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.example.util.TaskUtil;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
public class TaskRepository {

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final String filePath ="src/main/java/org/example/resources/tasks.json";
    private static final List<TaskUtil> tasks = new ArrayList<>();
    public static void loadTasks() throws IOException
    {
            File file = new File(filePath);
            if(file.exists())
            {
                List<TaskUtil> taskLoad= mapper.readValue(file ,new TypeReference<List<TaskUtil>>(){});
                tasks.addAll(taskLoad);
            }
    }
    public static void saveTasks() throws IOException
    {
        File file = new File(filePath);
        mapper.writeValue(file, tasks);
    }
    public static Optional<TaskUtil> findTask(String taskId)
    {
       return tasks.stream().filter(t->t.getTaskId().equals(taskId)).findFirst();
    }
    public static boolean checkId(String taskId)
    {
        return tasks.stream().anyMatch(t -> t.getTaskId().equals(taskId));
    }
    public static void createTask(TaskUtil task)throws IOException
    {
        tasks.add(task);
        saveTasks();
    }
    public static boolean deleteTask(String taskId)throws IOException
    {
        boolean res= tasks.removeIf(
                    task -> task.getTaskId().equals(taskId));
        saveTasks();
        return res;

    }
    public static void updateTask(TaskUtil existedTask,TaskUtil updatedTask)throws IOException
    {
         existedTask.setResponse(updatedTask.getResponse());
         saveTasks();
    }
    public static List<TaskUtil> getAllTasks()
    {
        return tasks;
    }




}
