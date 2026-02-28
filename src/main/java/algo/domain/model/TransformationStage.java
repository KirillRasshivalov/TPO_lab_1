package algo.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Getter @Setter
public class TransformationStage {
    private String name;
    private Instant startTime;
    private Instant endTime;
    private Map<String, Object> parameters;

    public TransformationStage(String name) {
        this.name = name;
        this.startTime = Instant.now();
        this.parameters = new HashMap<>();
    }

    public void complete() {
        this.endTime = Instant.now();
    }
}
