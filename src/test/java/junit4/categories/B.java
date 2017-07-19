package junit4.categories;

import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * Created by fengxx on 2016-10-14.
 */
@Category({SlowTests.class, FastTests.class})
public class B {

    @Test
    public void c() {
        System.out.println("method B.c called");
    }
}
