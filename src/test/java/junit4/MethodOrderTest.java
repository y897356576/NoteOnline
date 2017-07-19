package junit4;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * Created by bruceLi on 10/13/2016.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING) //按方法名字母顺序执行
//@FixMethodOrder(MethodSorters.JVM) //按方法定义顺序执行
//@FixMethodOrder(MethodSorters.DEFAULT) //默认的顺序
public class MethodOrderTest {

    @Test
    public void testB() {
        System.out.println("second");
    }

    @Test
    public void testC() {
        System.out.println("third");
    }

    @Test
    public void testA() {
        System.out.println("first");
    }


}
