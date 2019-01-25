import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.BeanUtil;
import com.stone.common.model.DataStatus;
import model.User;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.beans.BeanUtils;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.lang.Exception;
import java.lang.reflect.*;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.io.File;

import java.math.BigDecimal;

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
    public void test() {
        String s = null;
        System.out.println("String:" + s);

        /*String s = "{\"results\":[{\"id\":\"FF32BA9A-CBA2-4086-B1D6-089F04293550\",\"user\":null,\"userId\":\"3b36ce84ba8c4efc9bf259a3d696d558\",\"loanId\":\"54A6D7F3-FADF-457C-9E1A-E7CD788AF527\",\"bidMethod\":\"MANUAL\",\"amount\":9400,\"rate\":2400,\"duration\":{\"years\":0,\"months\":0,\"days\":1,\"totalDays\":1,\"totalMonths\":0},\"repayMethod\":\"BulletRepayment\",\"status\":\"OVERDUE\",\"submitTime\":1527227920000,\"updateTime\":null,\"creditAssignId\":null,\"originalAmount\":null,\"purpose\":null,\"source\":\"WEB\",\"timeSettled\":null,\"version\":null,\"amountInterest\":null,\"remark\":null,\"welfareAmount\":null,\"salesNo\":null,\"orderId\":null,\"loanTitle\":\"杞﹁\uE746杞︾垎鑳�,\"serial\":null,\"userLoginName\":\"an004\",\"repayments\":null,\"loanAmount\":10000,\"riskGrade\":null,\"userName\":null,\"serverTime\":null,\"productKey\":null,\"ticket_status\":null,\"ticket\":null,\"amountAddInterest\":null,\"key\":null,\"code\":null,\"loanInvest\":true,\"assignInvest\":false,\"investAmount\":9400},{\"id\":\"9D79070F-DA7B-4653-B09D-9BF8FC241D31\",\"user\":null,\"userId\":\"3b36ce84ba8c4efc9bf259a3d696d558\",\"loanId\":\"54A6D7F3-FADF-457C-9E1A-E7CD788AF527\",\"bidMethod\":\"MANUAL\",\"amount\":100,\"rate\":2400,\"duration\":{\"years\":0,\"months\":0,\"days\":1,\"totalDays\":1,\"totalMonths\":0},\"repayMethod\":\"BulletRepayment\",\"status\":\"OVERDUE\",\"submitTime\":1526881642000,\"updateTime\":null,\"creditAssignId\":null,\"originalAmount\":null,\"purpose\":null,\"source\":\"WEB\",\"timeSettled\":null,\"version\":null,\"amountInterest\":null,\"remark\":null,\"welfareAmount\":null,\"salesNo\":null,\"orderId\":null,\"loanTitle\":\"杞﹁\uE746杞︾垎鑳�,\"serial\":null,\"userLoginName\":\"an004\",\"repayments\":null,\"loanAmount\":10000,\"riskGrade\":null,\"userName\":null,\"serverTime\":null,\"productKey\":null,\"ticket_status\":null,\"ticket\":null,\"amountAddInterest\":null,\"key\":null,\"code\":null,\"loanInvest\":true,\"assignInvest\":false,\"investAmount\":100}],\"totalSize\":8,\"nowdate\":null,\"totalAmount\":null,\"listMobile\":null,\"validNumber\":0}";
        System.out.println(s);
        JSONObject json = JSONObject.parseObject(s);*/
        //JSONObject rsp = JSON.parseObject(s);

    }

    @Test
    public void test_27() {
        List<String> strList = new ArrayList<String>(){{
            add("a");
            add("b");
            add("c");
        }};
        String[] strArr = new String[]{"1", "2", "3"};
        Collections.addAll(strList, strArr);
        strList.addAll(Arrays.asList(strArr));
    }

    @Test
    public void test_26() {
        String s = "1,2,3,,,4,5,,";
        System.out.println(Arrays.toString(s.split(",")));
        System.out.println(Arrays.toString(s.split(",", -1)));
    }

    @Test
    public void test_25() {
        List<String> list = new ArrayList<String>(){{
            add("1"); add("2"); add("3");
        }};

        /*for (String s : list) {
            System.out.println("element:" + s);
            list.remove(s);
        }*/

        Iterator<String> i = list.iterator();
        String s;
        while(i.hasNext()) {
            s = i.next();
            System.out.println("element:" + s);
            i.remove();
        }

        System.out.println("size:" + list.size());
    }

    @Test
    public void test_24() {
        String a1 = "1279";
        String a2 = "1279";
        System.out.println(a1 == a2);
        String s1 = "123";
        String s2 = "1a";
        System.out.println(s1.compareTo(s2));
        System.out.println('2' - 'a');
    }

    @Test
    public void test_23() {
        int[] ints = new int[100];
        int init = 0x25c8;
        for (int i = 0; i < 100; i++) {
            ints[i] = init + i;
        }
        for (int i = 0; i < ints.length; i++) {
            System.out.println(Integer.toHexString(ints[i]) + ":" + new String(ints, i, 1));
        }
    }

    @Test
    public void test_22() {
        List<Object> objs = new ArrayList<>();
        Object obj = new Object();
        System.out.println("-----1:" + obj);
        objs.add(obj);
        obj = new Object();
        System.out.println("-----2:" + obj);
        objs.add(obj);
        for (Object o : objs) {
            System.out.println("---for:" + o);
        }
    }

    @Test
    public void test_21() {
        DecimalFormat df = new DecimalFormat("0.00");
        System.out.println(df.format(123.456D));

        System.out.println(String.format("%02d", 1));
    }

    @Test
    public void test_20() {
        String pattern = "20[0-9][0-9](0[1-9]|1[0-2])?";
        System.out.println(Pattern.matches(pattern, "209901"));
    }

    @Test
    public void test_19() {
        switch (1) {
            case 0: System.out.println("result: 0"); break;
            case 1: System.out.println("result: 1");
            case 2: System.out.println("result: 2");
            case 3: System.out.println("result: 3");
            default: System.out.println("result: default"); break;
        }
    }


    @Test
    public void test_18() {
        String a = (String) null;
        System.out.println(a.toString());
    }


    @Test
    public void test_17() {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse("2016-02-28"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()));
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - 1);
        System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()));
    }


    @Test
    public void test_16() {
        List<String> list = new ArrayList<String>() {{
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
        json.put("1", "A");
        json.put("2", "B");
        json.put("3", "C");
        json.put("4", "D");
        json.put("5", "E");
        System.out.println(JSONObject.toJSONString(json));

        String str = "{3:\"C\",2:\"B\",1:\"A\",5:\"E\",4:\"D\"}";
        JSONObject jsonObj = JSONObject.parseObject(str, JSONObject.class);
        System.out.println(jsonObj + ";" + json.get("1"));
    }


    @Test
    public void test_13() {
        Integer[] ints = new Integer[]{1, 3, 2, 5, 4, 6, 8, 7, 5, 0, -1};
        Integer[] leftArrs = Arrays.copyOfRange(ints, 0, ints.length / 2);
        Integer[] rightArrs = Arrays.copyOfRange(ints, ints.length / 2, ints.length);
        System.out.println(Arrays.toString(leftArrs));
        System.out.println(Arrays.toString(rightArrs));
    }


    @Test
    //java获取路径
    public void test_12() throws InterruptedException, ClassNotFoundException {
        System.out.println(getClass().getResource("/").getFile());
        System.out.println(getClass().getResource("/").getPath());
        System.out.println(System.getProperty("user.dir"));
//        System.out.println(request.getRequestURL());
//        System.out.println(request.getRequestURI());
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
        Object obj = applicationContext.getBean("user");
        System.out.println("1:" + obj);
        test_9_assist(obj);
        TimeUnit.SECONDS.sleep(1);
        System.out.println("3:" + obj);
        User user = (User) obj;
        obj = null;
        System.out.println("4:" + obj);
        System.out.println("5:" + user);
    }

    private static void test_9_assist(Object obj) {
        obj = new User();   //传过来的obj的内容为堆中obj对象的地址，将obj重新赋值是将地址覆盖掉了，对堆中的对象无影响
        System.out.println("2:" + obj);
    }


    @Test
    public void test_8() {
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

    private String test_8_assist1() {
        String str = "1";
        try {
            return str;
        } catch (Exception e) {
            str = "2";
            return str;
        } finally {
            str = "3";
            return "3.1";
        }
    }

    private User test_8_assist2() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/spring.xml");
        User user = (User) applicationContext.getBean("user");
        try {
            user.setUserName("test1");
            return user;
        } catch (Exception e) {
            user.setUserName("test2");
            return user;
        } finally {
            user.setUserName("test3");
        }
    }


    @Test
    public void test_7() {
        String[] strArr = {"1", "2", "3"};
        System.out.println(Arrays.toString(strArr));
        this.test_7_assist(strArr);
        System.out.println(Arrays.toString(strArr));
    }

    private void test_7_assist(String[] strArr) {
        strArr[2] = "4";
    }


    @Test
    public void test_6() {
        List<Integer> l1 = new ArrayList<Integer>() {{
            add(1);
            add(2);
            add(3);
            add(1);
        }};
        List<Integer> l2 = new ArrayList<Integer>() {{
            add(4);
            add(2);
            add(3);
        }};
        Set<Integer> i1 = new HashSet<Integer>();
        i1.addAll(l1);
        l1.removeAll(l2);
        System.out.println(l1.size());
    }


    @Test
    public void test_5() {
        try {
            Date date = new Date();
            System.out.println("1:" + date.getTime());
            this.test_5_assist(date);
            Thread.sleep(1000);
            System.out.println("2:" + date.getTime());
        } catch (Exception e) {
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
    public void test_4() {
        System.out.println(StringUtils.leftPad("1", 4, "0"));
        System.out.println(StringUtils.rightPad("1", 4, "0"));
    }


    @Test
    public void test_3() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/spring.xml");
        User user = (User) applicationContext.getBean("user");
        System.out.println("User:" + user);
    }


    @Test
    public void test_2() {
        List<String> list = new ArrayList<String>() {{
            add("1");
            add("2");
            add("3");
        }};
        String[] objArr = list.toArray(new String[list.size()]);
        list = Arrays.asList(objArr);
        String s = StringUtils.join(list.toArray(new String[list.size()]), "','");
        System.out.println(s);
        System.out.println(s.length());
    }


    @Test
    //java反射
    public void test_1() {
        try {
            Class cla = Class.forName("model.User");
            Object obj = cla.newInstance();
            if (obj instanceof User) {
                System.out.println(" this obj is instanceof User");
                System.out.println((User) obj);
            }

            System.out.println("packageName:" + cla.getPackage().getName());
            System.out.println("modifiers:" + Modifier.toString(cla.getModifiers()));
            System.out.println("className:" + cla.getSimpleName());
            System.out.println("extends:" + cla.getSuperclass().getSimpleName());

            System.out.println("\n --------------------- \n");

            Constructor[] constructors = cla.getConstructors();
            for (Constructor constructorf : constructors) {
                System.out.println("ConstructParamTypes:" + Arrays.toString(constructorf.getParameterTypes()));
            }
            Constructor construct = cla.getConstructor();
            System.out.println("constructModifier:" + Modifier.toString(construct.getModifiers()));
            System.out.println("constructName:" + construct.getName());
            Class[] params = construct.getParameterTypes();
            String paramStr = "";
            if (params != null) {
                for (Class param : params) {
                    paramStr += "," + param.getSimpleName();
                }
            }
            System.out.println("constructParams" + paramStr);

            System.out.println("\n --------------------- \n");

            Method[] methods = cla.getMethods();
            if (methods != null) {
                for (Method method : methods) {
                    String paramTypes = "";
                    Type[] paramTypeArr = method.getGenericParameterTypes();
                    for (Type type : paramTypeArr) {
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

        } catch (Exception e) {
            System.out.println("Exception : " + e);
        }
    }

}
