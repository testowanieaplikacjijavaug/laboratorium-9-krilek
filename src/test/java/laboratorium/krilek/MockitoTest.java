package laboratorium.krilek;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class MockitoTest {

    @Test
    public void mockitoTest() {
        List<Double> mock = mock(List.class);
        Double firstVal = 0.1;
        Double secondVal = 0.2;
        when(mock.get(0))
                .thenReturn(firstVal);
        when(mock.get(1))
                .thenReturn(secondVal);

        final Double result1 = mock.get(0);
        final Double result2 = mock.get(1);

        assertEquals(firstVal, result1);
        assertEquals(secondVal, result2);

        verify(mock).get(0);
        verify(mock).get(1);
    }
}
