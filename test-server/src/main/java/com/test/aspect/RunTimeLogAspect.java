package com.test.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RunTimeLogAspect {

    /**
     * 定义切入点：对要拦截的方法进行定义与限制，如包、类
     * <p>
     * 1、execution(public * *(..)) 任意的公共方法
     * 2、execution（* set*（..）） 以set开头的所有的方法
     * 3、execution（* com.lingyejun.annotation.LoggerApply.*（..））com.lingyejun.annotation.LoggerApply这个类里的所有的方法
     * 4、execution（* com.lingyejun.annotation.*.*（..））com.lingyejun.annotation包下的所有的类的所有的方法
     * 5、execution（* com.lingyejun.annotation..*.*（..））com.lingyejun.annotation包及子包下所有的类的所有的方法
     * 6、execution(* com.lingyejun.annotation..*.*(String,?,Long)) com.lingyejun.annotation包及子包下所有的类的有三个参数，第一个参数为String类型，第二个参数为任意类型，第三个参数为Long类型的方法
     * 7、execution(@annotation(com.lingyejun.annotation.Lingyejun))
     */
    @Pointcut("@annotation(com.test.aspect.annotation.RunTimeLog)")
    private void pointcut() {
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        try {
            return joinPoint.proceed();
        } finally {
            System.out.println("run time: " + (System.currentTimeMillis() - start) + "ms");
        }
    }
}
