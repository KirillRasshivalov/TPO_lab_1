package algo.domain.model;

import algo.domain.enums.TimePressure;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.Instant;

@Getter @Setter
public class TimeContext {
    private Instant referenceTime;
    private Duration availableTime;
    private TimePressure pressure;

    public TimeContext(Duration availableTime) {
        this.referenceTime = Instant.now();
        this.availableTime = availableTime;
        this.pressure = calculatePressure();
    }

    private TimePressure calculatePressure() {
        if (availableTime.toMinutes() < 1) return TimePressure.CRITICAL;
        if (availableTime.toMinutes() < 5) return TimePressure.HIGH;
        if (availableTime.toMinutes() < 15) return TimePressure.MEDIUM;
        return TimePressure.LOW;
    }

    public boolean isTimeRunningOut() {
        return Duration.between(referenceTime, Instant.now()).compareTo(availableTime) > 0;
    }
}
