package junit4.params;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertTrue;

/**
 * Created by 石头 on 2017/7/18.
 */
@RunWith(Parameterized.class)
public class ParameterTest {
    private String dateReg;
    private Pattern pattern; // 数据成员变量
    private String phrase;

    //数据供给方法（静态，用 @Parameter 注释，返回类型为 Collection）
    @Parameterized.Parameters
    public static Collection dateFeed() {
        return new ArrayList(){{
            add("2010-1-1");
            add("2010-10-1");
            add("2010-123-1");
            add("2010-12-34");
        }};

        /*return Arrays.asList(new Object[] {
                "2010-1-1",
                "2010-10-1",
                "2010-123-1",
                "2010-12-34"
        });*/
    }

    public ParameterTest(String phrase) {
        super();
        this.phrase = phrase;
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
        assertTrue(String.format("pattern is not match, Content: %s", phrase), isValid);
    }
}