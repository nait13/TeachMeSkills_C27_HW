package com.teachmeskills.lesson39;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

public class Task {

    private String name;

    @Autowired
    @Qualifier("listSubTask")
    private List<SubTask> listSubTask;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SubTask> getListSubTask() {
        return listSubTask;
    }

    public void setListSubTask(List<SubTask> listSubTask) {
        this.listSubTask = listSubTask;
    }

    public void executeSubTask() {
        System.out.println("Executing [TASK] " + name);
        if (listSubTask != null) {
            for (SubTask task : listSubTask) {
                task.execute();
            }
        }
    }
}
