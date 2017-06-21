import model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.test.context.ContextConfiguration;

import java.lang.reflect.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 16-11-28
 * Time: 下午2:11
 * To change this template use File | Settings | File Templates.
 */
@RunWith(JUnit4.class)
@ContextConfiguration(locations = {"classpath:spring*.xml"})
public class Test_A {

    @Test
    public void test_7(){
        /*String s = ",,,1,,,";
        String[] arr = s.split(",");
        System.out.println("length:" + arr.length);*/

        System.out.println(UUID.randomUUID());
    }

    @Test
    public void test_6(){
        List<Integer> l1 = new ArrayList<Integer>(){{add(1);add(2);add(3);add(1);}};
        List<Integer> l2 = new ArrayList<Integer>(){{add(4);add(2);add(3);}};
        Set<Integer> i1 = new HashSet<Integer>();
        i1.addAll(l1);
        l1.removeAll(l2);
        System.out.println(l1.size());
    }

    @Test
    public void test_5(){
        try{
            Date date = new Date();
            System.out.println("1:" + date.getTime());
            this.test_5_assist(date);
            Thread.sleep(1000);
            System.out.println("2:" + date.getTime());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void test_5_assist(Date date) throws InterruptedException {
        Thread.sleep(2005);
//        date = new Date();
        date.setTime(new Date().getTime());
        System.out.println("3:" + date.getTime());
    }

    @Test
    public void test_4(){
        System.out.println(StringUtils.leftPad("1",4,"0"));
        System.out.println(StringUtils.rightPad("1",4,"0"));
    }

    @Test
    public void test_3(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/spring.xml");
        User user = (User) applicationContext.getBean("user");
        System.out.println("User:" + user);
    }

    @Test
    public void test_2(){
        List<String> list = new ArrayList<String>(){{add("1");add("2");add("3");}};
        String[] objArr = list.toArray(new String[list.size()]);
        list = Arrays.asList(objArr);
        String s = StringUtils.join(list.toArray(new String[list.size()]), ",");
        System.out.println(s);
        System.out.println(s.length());
    }


    @Test
    //java反射
    public void test_1(){
        try{
            Class cla = Class.forName("cms.kernel.entity.User");
            System.out.println("packageName:" + cla.getPackage().getName());
            System.out.println("modifiers:" + Modifier.toString(cla.getModifiers()));
            System.out.println("className:" + cla.getSimpleName());
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
