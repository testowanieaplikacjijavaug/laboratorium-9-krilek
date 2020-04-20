package laboratorium.krilek;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.easymock.EasyMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EasyMockTest {

    @Test
    public void easyMockTest() {
        List<Double> mock = EasyMock.mock(List.class);
        Double firstVal = 0.1;
        Double secondVal = 0.2;
        expect(mock.get(0))
                .andReturn(firstVal);
        expect(mock.get(1))
                .andReturn(secondVal);

        replay(mock);
        final Double result1 = mock.get(0);
        final Double result2 = mock.get(1);

        assertEquals(firstVal, result1);
        assertEquals(secondVal, result2);

        verify(mock);
    }
}
