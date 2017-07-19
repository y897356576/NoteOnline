package junit4.rule;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

import static org.hamcrest.Matchers.hasProperty;

/**
 * Created by 石头 on 2017/7/18.
 */
public class ExpectedExceptionTest {

    @Rule
    public ExpectedException exp = ExpectedException.none();

    @Test
    public void expectException() {
        exp.expect(IndexOutOfBoundsException.class);
        throw new IndexOutOfBoundsException("Exception method.");
    }

    @Test
    public void expectMessage() {
        exp.expectMessage("Hello World");
        throw new RuntimeException("Hello World will throw exception.");
    }

    @Test
    public void expectProperties() {
        exp.expect(hasProperty("response", hasProperty("status", Matchers.is(404))));
        throw new NotFoundException("", Response.status(Response.Status.NOT_FOUND).build());
    }

    @Test
    public void expectCourse() {
        exp.expectCause(new BaseMatcher<IllegalArgumentException>() {

            public boolean matches(Object item) {
                return item instanceof IllegalArgumentException;
            }

            public void describeTo(Description description) {
                description.appendText("Expected Cause Error.");
            }

        });

        Throwable cause = new IllegalArgumentException("Cause Test.");
        throw new RuntimeException(cause);
    }

}
