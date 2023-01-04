package com.real.testDemo;

import com.real.annotation.DemoAnnotation;

import java.lang.reflect.Method;

/**
 * 注解实践demo
 *
 * 具体实现逻辑 通过获得类的class对象（即类属性信息的对象）判断是否含有此注解，并对其进行指定操作；
 *
 *
 * @author wangbing
 * @Date 2023/1/4
 */
public class AnnotationDemo {
    public static void main(String[] args) throws NoSuchMethodException, NoSuchFieldException {
//        Class<?> fileDemo = Class.forName("com.real.testDemo.AnnotationDemo");
        Class<?> fileDemo = AnnotationDemo.class;
        Method getAnnotationMethod = fileDemo.getMethod("getAnnotation", int.class);
        if (getAnnotationMethod.isAnnotationPresent(DemoAnnotation.class)) {
            System.out.println(getAnnotationMethod.getAnnotation(DemoAnnotation.class).value());

        }
    }

    @DemoAnnotation("DemoAnnotation被调用")
    public static void getAnnotation(int i){
        System.out.println("调用getAnnotation方法。。。");
    }
}
