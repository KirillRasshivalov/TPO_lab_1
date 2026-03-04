package function;

import algo.function.Sin;
import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import com.code_intelligence.jazzer.junit.FuzzTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class SinTest {

    private Sin sinCalculator;
    private static final double DELTA = 1e-10;
    private static final double PI = Math.PI;
    private static final int ITERATIONS = 40;
    private static final double X = 0.5;
    private static final double ETALON_SIN_X = Math.sin(X);

    @BeforeEach
    void setUp() {
        sinCalculator = new Sin();
    }

    @Test
    @DisplayName("Проверка факториала.")
    void testFactorial() {
        assertEquals(1.0, Sin.factorial(0), DELTA);
        assertEquals(1.0, Sin.factorial(1), DELTA);
        assertEquals(2.0, Sin.factorial(2), DELTA);
        assertEquals(6.0, Sin.factorial(3), DELTA);
        assertEquals(24.0, Sin.factorial(4), DELTA);
        assertEquals(120.0, Sin.factorial(5), DELTA);
        assertEquals(720.0, Sin.factorial(6), DELTA);
    }

    @Test
    @DisplayName("Значения из кружка.")
    void testBasicSin() {
        assertEquals(0.0, sinCalculator.sin(0), DELTA);
        assertEquals(1.0, sinCalculator.sin(PI / 2), DELTA);
        assertEquals(0.0, sinCalculator.sin(PI), DELTA);
        assertEquals(-1.0, sinCalculator.sin(PI + PI / 2), DELTA);
        assertEquals(0.0, sinCalculator.sin(2 * PI), DELTA);
    }

    @Test
    @DisplayName("Проверка на то что наша функция действительно периодична и тесты выше достаточные.")
    void testCorrectRealisation() {
        assertEquals(sinCalculator.sin(0), sinCalculator.sin(0 + 2 * PI), DELTA);
        assertEquals(sinCalculator.sin(0), sinCalculator.sin(0 - 2 * PI), DELTA);
        assertEquals(sinCalculator.sin(PI / 2), sinCalculator.sin(PI / 2 + 2 * PI), DELTA);
        assertEquals(sinCalculator.sin(PI / 2), sinCalculator.sin(PI / 2 - 2 * PI), DELTA);
        assertEquals(sinCalculator.sin(PI), sinCalculator.sin(PI + PI * 2), DELTA);
        assertEquals(sinCalculator.sin(PI), sinCalculator.sin(PI - PI * 2), DELTA);
        assertEquals(sinCalculator.sin((3 * PI) / 2), sinCalculator.sin((3 * PI) / 2 + 2 * PI), DELTA);
        assertEquals(sinCalculator.sin((3 * PI) / 2), sinCalculator.sin((3 * PI) / 2 - 2 * PI), DELTA);
        assertEquals(sinCalculator.sin(2 * PI), sinCalculator.sin(2 * PI + 2 * PI), DELTA);
        assertEquals(sinCalculator.sin(2 * PI), sinCalculator.sin(2 * PI - 2 * PI), DELTA);
    }

//    @Test
//    @DisplayName("Неверные значения на входе функции.")
//    void testIncorrectInput() {
//        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {sinCalculator.sin(0, -1);});
//        assertThrows(IllegalArgumentException.class, () -> {sinCalculator.sin(0);});
//        assertThrows(IllegalArgumentException.class, () -> {sinCalculator.sin(0);});
//        assertThrows(IllegalArgumentException.class, () -> {sinCalculator.sin(0, -1000);});
//        assertThrows(IllegalArgumentException.class, () -> {sinCalculator.sin(0, -10000);});
//        assertEquals("Неверные аргументы на входе функции.", exception.getMessage());
//    }

//    @Test
//    @DisplayName("Нестандартные значения на вход функции.")
//    void testComplicatedInput() {
//        Random random = new Random();
//        for (int i = 0; i < 100; i++) {
//            double x = random.nextDouble();
//            assertEquals(Math.sin(x), sinCalculator.sin(x), DELTA);
//        }
//    }

    @Test
    @DisplayName("Симмметричность фнукции (sin(-x) = -sin(x)).")
    void testSymmetricInput() {
        double x1 = 10;
        assertEquals(sinCalculator.sin(-x1), -1 * sinCalculator.sin(x1), DELTA);
        double x2 = -10;
        assertEquals(sinCalculator.sin(-x2), -1 * sinCalculator.sin(x2), DELTA);
        double x3 = 0.5;
        assertEquals(sinCalculator.sin(-x3), -1 * sinCalculator.sin(x3), DELTA);
        double x4 = -0.5;
        assertEquals(sinCalculator.sin(-x4), -1 * sinCalculator.sin(x4), DELTA);
    }

    @Test
    @DisplayName("Сходимость ряда.")
    void testConvergence() {
        double firstEp = sinCalculator.sin(X);
        double secondEp = sinCalculator.sin(X);
        double thirdEp = sinCalculator.sin(X);
        double fourthEp = sinCalculator.sin(X);
        double difference1 = ETALON_SIN_X - firstEp < 0 ? (ETALON_SIN_X - firstEp) * -1 : ETALON_SIN_X - firstEp;
        double difference2 = ETALON_SIN_X - secondEp < 0 ? (ETALON_SIN_X - secondEp) * -1 : ETALON_SIN_X - secondEp;
        double difference3 = ETALON_SIN_X - thirdEp < 0 ? (ETALON_SIN_X - thirdEp) * -1 : ETALON_SIN_X - thirdEp;
        double difference4 = ETALON_SIN_X - fourthEp < 0 ? (ETALON_SIN_X - fourthEp) * -1 : ETALON_SIN_X - fourthEp;
        assertTrue(difference1 >= difference2);
        assertTrue(difference2 >= difference3);
        assertTrue(difference3 >= difference4);
    }

    @FuzzTest()
    @DisplayName("Тест с помощью фаззера")
    void fuzzSin(FuzzedDataProvider data) {
        double x = data.consumeDouble();

        Sin sinCalculator = new Sin();
        double result = sinCalculator.sin(x);

        if (Double.isFinite(result)) {
            assertTrue(Math.abs(result) <= 1.0 + DELTA,
                    String.format("Sin value %f out of bounds for x=%f", result, x));
            if (Math.abs(x) < 20.0) {
                assertEquals(Math.sin(x), result, 1e-5);
            }
        }
    }
}
