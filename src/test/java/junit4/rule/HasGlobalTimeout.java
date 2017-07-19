package junit4.rule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TestRule;
import org.junit.rules.Timeout;
import org.junit.runners.model.TestTimedOutException;

import java.util.concurrent.TimeUnit;

/**
 * Created by fengxiangxiang on 2016-10-14.
 */
public class HasGlobalTimeout {
    public static String log;

    @Rule
    public TestRule globalTimeout = new Timeout(3, TimeUnit.SECONDS);

    @Test
    public void testInfiniteLoop1() {
        log += "ran1";
        for(;;) {}
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testInfiniteLoop2() {
        thrown.expect(TestTimedOutException.class);
        log += "ran2";
        for(;;) {}
    }
}