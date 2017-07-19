package junit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertTrue;

/**
 * Created by 石头 on 2017/7/18.
 */
@RunWith(JUnit4.class)
public class TimeOutTest {

    private String dateReg;
    private Pattern pattern;

    @Before
    public void init() {
        dateReg="^\\d{4}(\\-\\d{1,2}){2}";
        pattern=Pattern.compile(dateReg);
    } // timeout 测试是指在指定时间内就正确

    @Test(timeout=1)
    public void verifyReg() {
        try {
//            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Matcher matcher=pattern.matcher("2017-7-11");
        boolean isValid=matcher.matches(); //  静态导入功能
        assertTrue("pattern is not match", isValid);
    }

}
