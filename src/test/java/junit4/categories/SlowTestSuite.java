package junit4.categories;

import org.junit.experimental.categories.Categories;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by fengxx on 2016-10-14.
 */
@RunWith(Categories.class)
@Categories.IncludeCategory(SlowTests.class)
@Categories.ExcludeCategory(FastTests.class)
@Suite.SuiteClasses({ A.class, B.class })    // Note that Categories is a kind of Suite
public class SlowTestSuite {
    // Will run A.b , but not A.a  B.c
}
