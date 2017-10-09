import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import model.User;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.lang.reflect.*;
import java.security.SecureRandom;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by 石头 on 2017/6/22.
 */
@RunWith(JUnit4.class)
//@ContextConfiguration(locations = {"classpath:spring.xml"})
public class Test_A {

    public ApplicationContext applicationContext;

    @Before
    public void beforeMethod() {
//        applicationContext = new ClassPathXmlApplicationContext("/spring.xml");
    }


    @Test
    public void test_16() {
        List<String> list = new ArrayList<String>(){{
            add("1");
            add("2");
            add("3");
            add("4");
            add("5");
        }};

        while (list.size() >= 2) {
            System.out.println(Arrays.toString(list.subList(0, 2).toArray()));
            list.removeAll(list.subList(0, 2));
        }
        if (!list.isEmpty()) {
            System.out.println(Arrays.toString(list.toArray()));
        }
    }


    @Test
    public void test_15() {
        String str = "{'fhm':'0000', 'data':[{data1: 'content1', 'data2': 'content2'}, {dataA: 'contentA'}]}";
        String str1 = "{fhm:\"0000\", data:null}";
        JSONObject obj = JSONObject.parseObject(str1);
        System.out.println(obj.get("fhm"));
        JSONArray arr = obj.getJSONArray("data");
        System.out.println(arr);
    }


    @Test
    public void test_14() {
        JSONObject json = new JSONObject();
        json.put("1","A");
        json.put("2","B");
        json.put("3","C");
        json.put("4","D");
        json.put("5","E");
        System.out.println(JSONObject.toJSONString(json));

        String str = "{3:\"C\",2:\"B\",1:\"A\",5:\"E\",4:\"D\"}";
        JSONObject jsonObj = JSONObject.parseObject(str, JSONObject.class);
        System.out.println(jsonObj + ";" + json.get("1"));
    }


    @Test
    public void test_13() {
        Integer[] ints = new Integer[]{1, 3, 2, 5, 4, 6, 8, 7, 5, 0, -1};
        Integer[] leftArrs = Arrays.copyOfRange(ints, 0, ints.length/2);
        Integer[] rightArrs = Arrays.copyOfRange(ints, ints.length/2, ints.length);
        System.out.println(Arrays.toString(leftArrs));
        System.out.println(Arrays.toString(rightArrs));
    }


    @Test
    public void test_12() throws InterruptedException, ClassNotFoundException {
//        User.doPrint();
//        System.out.println(new User().getClass().getName());
//        Class.forName("model.User");
//        User user = new User();
//        System.out.println("User:" + user);
//        user = new User();
//        System.out.println("User:" + user);
    }


    @Test
    public void test_11() throws InterruptedException {
        Map<String, String> map = new HashMap<>();
        System.out.println(map.put("1", "a"));
        System.out.println(map.put("1", "b"));
    }


    @Test
    public void test_10() throws InterruptedException {
        User user0 = new User();
        User user1 = user0; //此处赋的值为引用
        User user2 = user1;
        User user3 = user2;
        user3 = null;
        System.out.println("User0:" + user0);
        System.out.println("User1:" + user1);
        System.out.println("User2:" + user2);
        System.out.println("User3:" + user3);
    }


    @Test
    public void test_9() throws InterruptedException {
        Object obj =  applicationContext.getBean("user");
        System.out.println("1:" + obj);
        test_9_assist(obj);
        TimeUnit.SECONDS.sleep(1);
        System.out.println("3:" + obj);
        User user = (User) obj;
        obj = null;
        System.out.println("4:" + obj);
        System.out.println("5:" + user);
    }
    private static void test_9_assist(Object obj){
        obj = new User();   //传过来的obj的内容为堆中obj对象的地址，将obj重新赋值是将地址覆盖掉了，对堆中的对象无影响
        System.out.println("2:" + obj);
    }


    @Test
    public void test_8(){
        Integer i = this.test_8_assist0();
        System.out.println("int : " + i);
        String str = this.test_8_assist1();
        System.out.println("str : " + str);
        User user = this.test_8_assist2();
        System.out.println("userName : " + user.getUserName());
    }
    private int test_8_assist0() {
        int x = 1;
        try {
            x++;
            return x;
        } finally {
            ++x;
            /*在try语句中，在执行return语句时，要返回的结果已经准备好了，就在此时，程序转到finally执行了。
            在转去之前，try中先把要返回的结果存放到不同于x的局部变量中去，执行完finally之后，在从中取出返回结果，
            因此，即使finally中对变量x进行了改变，但是不会影响返回结果。
            它应该使用栈保存返回值。*/
        }
    }
    private String test_8_assist1(){
        String str = "1";
        try{
            return str;
        } catch (Exception e){
            str = "2";
            return str;
        } finally {
            str = "3";
            return "3.1";
        }
    }
    private User test_8_assist2(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/spring.xml");
        User user = (User) applicationContext.getBean("user");
        try{
            user.setUserName("test1");
            return user;
        } catch (Exception e){
            user.setUserName("test2");
            return user;
        } finally {
            user.setUserName("test3");
        }
    }


    @Test
    public void test_7(){
        String[] strArr = {"1", "2", "3"};
        System.out.println(Arrays.toString(strArr));
        this.test_7_assist(strArr);
        System.out.println(Arrays.toString(strArr));
    }
    private void test_7_assist(String[] strArr){
        strArr[2] = "4";
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
            Class cla = Class.forName("model.User");
            Object obj = cla.newInstance();
            if(obj instanceof User) {
                System.out.println(" this obj is instanceof User");
                System.out.println((User)obj);
            }

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
