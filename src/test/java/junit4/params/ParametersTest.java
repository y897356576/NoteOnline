package junit4.params;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;

/**
 * Created by 石头 on 2017/7/18.
 */
@RunWith(Parameterized.class)
public class ParametersTest {
    private String dateReg;
    private Pattern pattern; // 数据成员变量
    private String phrase;
    private boolean expect;   // 使用数据的构造函数

    //数据供给方法（静态，用 @Parameter 注释，返回类型为 Collection）
    @Parameters
    public static Collection dateFeed() {
        List list = Arrays.asList(new Object[][] {
                {"2010-1-1",true},
                {"2010-123-1",true},
                {"2010-123-1",false},
                {"2010-12-34",false}
        });
        return list;
    }

    public ParametersTest(String phrase, Boolean expect) {
        super();
        this.phrase = phrase;
        this.expect = expect;
    }

    @Before
    public void init() {
        dateReg="^\\d{4}(\\-\\d{1,2}){2}";
        pattern=Pattern.compile(dateReg);
    }   // 测试方法

    @Test
    public void verifyDate() {
        Matcher matcher=pattern.matcher(phrase);
        boolean isValid=matcher.matches();
        assertEquals(String.format("pattern is not match, Content: %s", phrase), expect, isValid);
    }
}