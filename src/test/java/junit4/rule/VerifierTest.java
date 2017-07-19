package junit4.rule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Verifier;

import static org.junit.Assert.fail;

/**
 * Created by 石头 on 2017/7/19.
 */
public class VerifierTest {

    String result = "";

    //Verifier是ErrorCollector的父类，可以在测试执行完成之后做一些校验，以验证测试结果是不是正确。
    @Rule
    public Verifier verifier = new Verifier() {
        //当测试执行完之后会调用verify方法验证结果，抛出异常表明测试失败
        @Override
        protected void verify() throws Throwable {
            if (!"Success".equals(result)) {
                fail("Test Fail.");
            }
        }
    };

    @Test
    public void testVerifier() {
        result = "Fail";
    }

}
