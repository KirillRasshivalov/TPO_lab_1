package domain.model;

import algo.domain.enums.TimePressure;
import algo.domain.model.TimeContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TimeContextTests {
    @Test
    @DisplayName("Создание временного контекста")
    void testTimeContextCreation() {
        Duration availableTime = Duration.ofMinutes(10);
        TimeContext timeContext = new TimeContext(availableTime);
        assertNotNull(timeContext.getReferenceTime());
        assertEquals(availableTime, timeContext.getAvailableTime());
    }

    @ParameterizedTest
    @DisplayName("Расчет временного давления")
    @CsvSource({
            "30, LOW",
            "10, MEDIUM",
            "3, HIGH",
            "0, CRITICAL"
    })
    void testTimePressure(long minutes, TimePressure expected) {
        Duration duration = Duration.ofMinutes(minutes);
        TimeContext timeContext = new TimeContext(duration);
        assertEquals(expected, timeContext.getPressure());
    }

    @Test
    @DisplayName("Проверка истечения времени")
    void testTimeRunningOut() throws InterruptedException {
        Duration shortTime = Duration.ofMillis(100);
        TimeContext timeContext = new TimeContext(shortTime);
        assertFalse(timeContext.isTimeRunningOut());
        Thread.sleep(150);
        assertTrue(timeContext.isTimeRunningOut());
    }
}
