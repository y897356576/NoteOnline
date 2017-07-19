package junit4.rule;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.fail;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;

public class ExpectedExceptionSecTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void shouldThrow() {
        TestThing testThing = new TestThing();
        thrown.expect(NotFoundException.class);
        thrown.expectMessage(startsWith("some Message"));
        thrown.expect(hasProperty("response", hasProperty("status", is(404))));
        testThing.chuck();
    }

    @Test
    public void expectProperties() {
        thrown.expect(hasProperty("response", hasProperty("status", Matchers.is(404))));
        throw new NotFoundException("", Response.status(Response.Status.NOT_FOUND).build());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void empty() {
        new ArrayList<Object>().get(0);
    }

    @Test
    public void testExceptionMessage() {
        try {
            new ArrayList<Object>().get(0);
            fail("Expected an IndexOutOfBoundsException to be thrown");
        } catch (IndexOutOfBoundsException anIndexOutOfBoundsException) {
            assertThat(anIndexOutOfBoundsException.getMessage(), is("Index: 0, Size: 0"));
        }
    }

    private class TestThing {
        public void chuck() {
            Response response = Response.status(Status.NOT_FOUND).entity("Resource not found").build();
            throw new NotFoundException("some Message", response);
        }
    }
}