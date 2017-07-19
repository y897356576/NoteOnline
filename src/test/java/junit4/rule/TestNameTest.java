package junit4.rule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

/**
 * Created by 石头 on 2017/7/19.
 */
public class TestNameTest {

    @Rule
    public TestName testName = new TestName();

    @Test
    public void testName_1() {
        //打印出测试方法的名字testTestName
        System.out.println(testName.getMethodName());
    }

    @Test
    public void testName_2() {
        System.out.println(testName.getMethodName());
    }

}
