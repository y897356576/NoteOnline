package junit4;

import junit4.ExceptionTest;
import junit4.TimeOutTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by 石头 on 2017/7/18.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        ExceptionTest.class,
        TimeOutTest.class
})
public class SuiteTest {
    //SuiteClasses参数中的测试类一起运行
}
