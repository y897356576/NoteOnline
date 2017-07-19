package junit4;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Created by 石头 on 2017/7/18.
 */
@RunWith(JUnit4.class) //测试运行器，JUnit 为单元测试提供了默认的测试运行器，但是没有限制必须使用默认的运行器，可以自定义运行器；
public class ExceptionTest {

    @Ignore("Ignore this method")
    @Test
    public void doIgnoreTest() {
        System.out.println("Error : this method should be Ignore!");
    }

    @Test(expected = NullPointerException.class)
    public void doExceptionTest() {
        throw new NullPointerException();
    }

}
