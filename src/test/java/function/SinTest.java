package function;

import algo.function.Sin;
import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import com.code_intelligence.jazzer.junit.FuzzTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class SinTest {

    private static final double DELTA = 1e-10;
    private static final double PI = Math.PI;


    @Test
    @DisplayName("Значения из кружка.")
    void testBasicSin() {
        assertEquals(0.0, Sin.sin(0), DELTA);
        assertEquals(1.0, Sin.sin(PI / 2), DELTA);
        assertEquals(0.0, Sin.sin(PI), DELTA);
        assertEquals(-1.0, Sin.sin(PI + PI / 2), DELTA);
        assertEquals(0.0, Sin.sin(2 * PI), DELTA);
    }

    @Test
    @DisplayName("Проверка на то что наша функция действительно периодична и тесты выше достаточные.")
    void testCorrectRealisation() {
        assertEquals(Sin.sin(0), Sin.sin(0 + 2 * PI), DELTA);
        assertEquals(Sin.sin(0), Sin.sin(0 - 2 * PI), DELTA);
        assertEquals(Sin.sin(PI / 2), Sin.sin(PI / 2 + 2 * PI), DELTA);
        assertEquals(Sin.sin(PI / 2), Sin.sin(PI / 2 - 2 * PI), DELTA);
        assertEquals(Sin.sin(PI), Sin.sin(PI + PI * 2), DELTA);
        assertEquals(Sin.sin(PI), Sin.sin(PI - PI * 2), DELTA);
        assertEquals(Sin.sin((3 * PI) / 2), Sin.sin((3 * PI) / 2 + 2 * PI), DELTA);
        assertEquals(Sin.sin((3 * PI) / 2), Sin.sin((3 * PI) / 2 - 2 * PI), DELTA);
        assertEquals(Sin.sin(2 * PI), Sin.sin(2 * PI + 2 * PI), DELTA);
        assertEquals(Sin.sin(2 * PI), Sin.sin(2 * PI - 2 * PI), DELTA);
    }

    @Test
    @DisplayName("Симмметричность фнукции (sin(-x) = -sin(x)).")
    void testSymmetricInput() {
        double x1 = 10;
        assertEquals(Sin.sin(-x1), -1 * Sin.sin(x1), DELTA);
        double x2 = -10;
        assertEquals(Sin.sin(-x2), -1 * Sin.sin(x2), DELTA);
        double x3 = 0.5;
        assertEquals(Sin.sin(-x3), -1 * Sin.sin(x3), DELTA);
        double x4 = -0.5;
        assertEquals(Sin.sin(-x4), -1 * Sin.sin(x4), DELTA);
    }

    @FuzzTest()
    @DisplayName("Тест с помощью фаззера")
    void fuzzSin(FuzzedDataProvider data) {
        double x = data.consumeDouble();
        double result = Sin.sin(x);

        if (Double.isFinite(result)) {
            assertTrue((result >= 0 ? result : result * -1) <= 1.0 + DELTA,
                    String.format("Sin value %f out of bounds for x=%f", result, x));
            assertEquals(Sin.etalon_sinus(x), result, 1e-5);
        }
    }
}
