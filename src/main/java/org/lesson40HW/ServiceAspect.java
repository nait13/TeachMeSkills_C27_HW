package org.lesson40HW;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class ServiceAspect {

    @Before("@annotation(org.lesson40HW.AspectAnnotation)")
    public void checkName(JoinPoint joinPoint){
        if(joinPoint.getArgs()[0].equals("Oleg")){
            System.out.println("Before: Hello Oleg");
        }else {
            System.out.println("AOP:  Not oleg!");
        }
    }
}
