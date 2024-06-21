package com.teachmeskills.lesson39;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.Arrays;
import java.util.List;

@Configuration
public class SpringConfiguration {
    @Bean("task10")
    @Scope("prototype")
    public Task createTask(){
        Task task = new Task();
        task.setName("Task1");
        return task;
    }

    @Bean("listSubTask")
    public List<SubTask> getListSubTask(){
        return Arrays.asList(getSubTask1(),getSubTask2());
    }

    @Bean("subTask11")
    @Scope("prototype")
    public SubTask getSubTask1(){
        SubTask subTask = new SubTask();
        subTask.setName("Subtask1.1");
        return subTask;
    }

    @Bean("subTask22")
    @Scope("prototype")
    public SubTask getSubTask2(){
        SubTask subTask = new SubTask();
        subTask.setName("Subtask1.2");
        return subTask;
    }

    @Bean("subTask33")
    @Scope("prototype")
    public SubTask getSubTask3(){
        SubTask subTask = new SubTask();
        subTask.setName("Subtask1.3");
        return subTask;
    }
}
