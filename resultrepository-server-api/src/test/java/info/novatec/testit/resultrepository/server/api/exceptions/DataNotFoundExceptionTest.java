package info.novatec.testit.resultrepository.server.api.exceptions;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;


public class DataNotFoundExceptionTest {

    @Test
    public void testIdMessageFormat() {
        DataNotFoundException cut = new DataNotFoundException(String.class, 42L);
        assertThat(cut.getMessage()).isEqualTo("No data of type String was found for ID: 42");
    }

    @Test
    public void testValuesMessageFormat() {
        DataNotFoundException cut = new DataNotFoundException(String.class, "foo", "bar");
        assertThat(cut.getMessage()).isEqualTo("No data of type String was found for: [foo, bar]");
    }

}
