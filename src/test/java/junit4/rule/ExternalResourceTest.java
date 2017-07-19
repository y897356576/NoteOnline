package junit4.rule;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.ExternalResource;

/**
 * Created by 石头 on 2017/7/19.
 */
public class ExternalResourceTest {

    //ExternalResource是TemporaryFolder的父类，主要用于在测试之前创建资源，并在测试完成后销毁。
    @ClassRule
    public static ExternalResource external = new ExternalResource() {
        @Override
        protected void before() throws Throwable {
            System.out.println("Preparing test data.");
            System.out.println("Test data is Ready!");
        }

        @Override
        protected void after() {
            System.out.println("Cleaning test data.");
        }
    };

    @Test
    public void method1() {
        System.out.println("Test Method first executing...");
    }

    @Test
    public void method2() {
        System.out.println("Test Method second executing...");
    }

    @Test
    public void method3() {
        System.out.println("Test Method thrid executing...");
    }

}
