package com.teachmeskills.lesson39;

import org.springframework.beans.factory.annotation.Autowired;

public class SubTask {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void execute() {
        System.out.println("  Executing [SUBTASK]: " + name);
    }
}
