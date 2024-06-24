package org.lesson40HW;

public class MyClass {

    @AspectAnnotation
    public void Method1(String s){
        System.out.println("Method N1 arg: " + s);
    }

    public void Method2(String s){
        System.out.println("Method N2 arg: " + s);
    }
}
