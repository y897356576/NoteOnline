package junit4.categories;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import static org.junit.Assert.fail;

/**
 * Created by fengxx on 2016-10-14.
 */
public class A {

    @Test
    public void a() {
        fail("something was failed");
    }

    @Category(SlowTests.class)
    @Test
    public void b() {
        System.out.println("method A.b called");
    }
}
