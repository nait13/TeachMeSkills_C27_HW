package com.teachmeskills.lesson39;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Runner {
    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);
        Task t = context.getBean("task10", Task.class);
        t.executeSubTask();

        ApplicationContext context1 = new ClassPathXmlApplicationContext("app-context-xml.xml");
        Task task1 = context1.getBean("task1", Task.class);
        task1.executeSubTask();

    }
}
