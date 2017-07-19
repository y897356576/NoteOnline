package junit4.theories;

import org.junit.Assume;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

/**
 * Created by fengxiangxiang on 2016-10-14.
 */
@RunWith(Theories.class)
public class TheoriesAndAssumeTest {

    @DataPoints
    public static int[] integers() {
//        return new int[]{-1, -10, -1234567, 1, 10, 1234567};
        return new int[]{-1, -10, -1234567};
    }

    @DataPoints
    public static int[] ints = new int[]{1, 10, 1234567};

    @Theory
    public void a_plus_b_is_greater_than_a_and_greater_than_b(Integer a, Integer b) {
        Assume.assumeTrue(a >0 && b > 0 );  //假设，条件成立则向下执行
        System.out.println(String.format("%d>0 and %d>0", a, b));
    }

    @Theory
    public void addition_is_commutative(Integer a, Integer b) {
        System.out.println("a:" + a + "; b:" + b);
    }
}
