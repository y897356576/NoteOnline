package com.stone.demo.reflect;

import com.stone.core.model.User;

import java.lang.reflect.*;
import java.util.Arrays;

/**
 * Created by 石头 on 2017/8/1.
 */
public class ReflectCase {

    public static void main(String[] args) {

        try{
            Class cla = Class.forName("com.stone.core.model.User");
            Object obj = cla.newInstance();
            if(obj instanceof User) {
                System.out.println(" this obj is instanceof User");
                System.out.println(obj);
            }

            System.out.println("packageName:" + cla.getPackage().getName());
            System.out.println("className:" + cla.getSimpleName());
            System.out.println("modifiers:" + Modifier.toString(cla.getModifiers()));
            System.out.println("extends:" + cla.getSuperclass().getSimpleName());

            System.out.println("\n --------------------- \n");

            Constructor[] constructors = cla.getConstructors();
            for(Constructor constructorf : constructors){
                System.out.println("ConstructParamTypes:" + Arrays.toString(constructorf.getParameterTypes()));
            }
            Constructor construct = cla.getConstructor();
            System.out.println("constructModifier:" + Modifier.toString(construct.getModifiers()));
            System.out.println("constructName:" + construct.getName());
            Class[] params = construct.getParameterTypes();
            String paramStr = "";
            if(params!=null){
                for(Class param : params){
                    paramStr += "," + param.getSimpleName();
                }
            }
            System.out.println("constructParams" +paramStr);

            System.out.println("\n --------------------- \n");

            Method[] methods =  cla.getMethods();
            if(methods != null){
                for(Method method : methods){
                    String paramTypes = "";
                    Type[] paramTypeArr = method.getGenericParameterTypes();
                    for(Type type : paramTypeArr){
                        paramTypes += type + ",";
                    }
                    System.out.println(method.getName() + "--" + paramTypes);
                }
            }

            System.out.println("\n --------------------- \n");

            Object user_1 = cla.newInstance();
            Field field = cla.getDeclaredField("userName");
            field.setAccessible(true);  //去掉private限制
            field.set(user_1, "testUserName");
            System.out.print("Field_userName:" + field.get(user_1));

            System.out.println("\n --------------------- \n");

            Object user_2 = cla.newInstance();
            Method method = cla.getDeclaredMethod("setUserName", String.class);
            method.invoke(user_2, "testUserName");
            method = cla.getDeclaredMethod("getUserName");
            System.out.println("Function_getUserName:" + method.invoke(user_2));

        } catch (Exception e){
            System.out.println("Exception : " + e);
        }
    }

}
