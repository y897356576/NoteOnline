package junit4.theories;

import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

/**
 * Created by 石头 on 2017/7/18.
 */
@RunWith(Theories.class)
public class TheoriesTest {

    //Theories不再需要使用带有参数的Constructor而是接受有参的测试方法，修饰的注解也从@Test变成了@Theory；
    //参数的提供则由@Parameterized.Parameters变成了使用@DataPoint或者@Datapoints来修饰的变量。

    @DataPoint
    public static String nameValue1 = "Tony";
    @DataPoint
    public static String nameValue2 = "Jim";
    @DataPoint
    public static int ageValue1 = 10;
    @DataPoint
    public static int ageValue2 = 20;

    @Theory
    public void testMethod(String name, int age) {
        System.out.println(String.format("%s's age is %s", name, age));
    }
}
