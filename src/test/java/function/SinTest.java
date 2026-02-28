package function;

import algo.function.Sin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class SinTest {

    private Sin sinCalculator;
    private static final double DELTA = 1e-10;
    private static final double PI = Math.PI;

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
        assertEquals(0.0, sinCalculator.sin(0, 15), DELTA);
        assertEquals(1.0, sinCalculator.sin(PI / 2, 15), DELTA);
        assertEquals(0.0, sinCalculator.sin(PI, 15), DELTA);
        assertEquals(-1.0, sinCalculator.sin(PI + PI / 2, 15), DELTA);
    }

    @Test
    @DisplayName("Неверные значения на входе функции.")
    void testIncorrectInput() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {sinCalculator.sin(0, -1);});
        assertThrows(IllegalArgumentException.class, () -> {sinCalculator.sin(0, -10);});
        assertThrows(IllegalArgumentException.class, () -> {sinCalculator.sin(0, -100);});
        assertThrows(IllegalArgumentException.class, () -> {sinCalculator.sin(0, -1000);});
        assertThrows(IllegalArgumentException.class, () -> {sinCalculator.sin(0, -10000);});
        assertEquals("Неверные аргументы на входе функции.", exception.getMessage());
    }

    @Test
    @DisplayName("Нестандартные значения на вход функции.")
    void testComplicatedInput() {
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            double x = random.nextDouble();
            assertEquals(Math.sin(x), sinCalculator.sin(x, 30), DELTA);
        }
    }

    @Test
    @DisplayName("Симмметричность фнукции (sin(-x) = -sin(x)).")
    void testSymmetricInput() {
        Random rand = new Random();
        for (int i = 0; i < 10; i++) {
            double x = rand.nextDouble();
            assertEquals(sinCalculator.sin(-x, 15), -1 * sinCalculator.sin(x, 15), DELTA);
        }
    }

    @Test
    @DisplayName("Периодичности.")
    void testPeriod() {
        double x = 0.7;
        assertEquals(sinCalculator.sin(x, 20), sinCalculator.sin(x + 2 * PI, 20), DELTA);
    }

    @Test
    @DisplayName("Сходимость ряда.")
    void testConvergence() {
        Random rand = new Random();
        double x = rand.nextDouble();
        double etalon = Math.sin(x);
        double firstEp = sinCalculator.sin(x, 5);
        double secondEp = sinCalculator.sin(x, 10);
        double thirdEp = sinCalculator.sin(x, 15);
        double fourthEp = sinCalculator.sin(x, 20);
        double difference1 = etalon - firstEp;
        double difference2 = etalon - secondEp;
        double difference3 = etalon - thirdEp;
        double difference4 = etalon - fourthEp;
        assertTrue(Math.abs(difference1) >= Math.abs(difference2));
        assertTrue(Math.abs(difference2) >= Math.abs(difference3));
        assertTrue(Math.abs(difference3) >= Math.abs(difference4));
    }
}
