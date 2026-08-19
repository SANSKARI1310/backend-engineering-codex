package org.example.service;

import org.example.util.TaskUtil;
import org.example.repository.TaskRepository;
import java.util.Optional;
import java.util.List;
import java.io.IOException;
public class TaskService {
    public static Optional<TaskUtil> getTask(String taskId)
    {
       return TaskRepository.findTask(taskId);
    }
    public static boolean checkExistingId(String taskId)
    {
        return TaskRepository.checkId(taskId);
    }
    public static void addTask(TaskUtil task)throws IOException
    {
        TaskRepository.createTask(task);
    }
    public static boolean deleteTask(String taskId)throws IOException
    {
        return TaskRepository.deleteTask(taskId);
    }
    public static void updateTask(TaskUtil existedTask,TaskUtil updatedTask)throws IOException
    {
        TaskRepository.updateTask(existedTask,updatedTask);
    }
    public static List<TaskUtil> reqAllTasks()
    {
        return TaskRepository.getAllTasks();
    }


}
