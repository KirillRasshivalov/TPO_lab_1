package algo.domain.model;

import algo.domain.enums.Species;
import algo.domain.enums.TransformationStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter @Setter
public class Transformation {
    private UUID id;
    private Creature creature;
    private Species fromSpecies;
    private Species toSpecies;
    private Instant startTime;
    private Instant endTime;
    private TransformationStatus status;
    private List<TransformationStage> stages;

    public Transformation(Creature creature, Species fromSpecies, Species toSpecies) {
        this.id = UUID.randomUUID();
        this.creature = creature;
        this.fromSpecies = fromSpecies;
        this.toSpecies = toSpecies;
        this.startTime = Instant.now();
        this.status = TransformationStatus.IN_PROGRESS;
        this.stages = new ArrayList<>();
    }

    public Duration getDuration() {
        if (endTime == null) return Duration.between(startTime, Instant.now());
        return Duration.between(startTime, endTime);
    }

    public void complete() {
        this.endTime = Instant.now();
        this.status = TransformationStatus.COMPLETED;
        creature.setTransformedAt(endTime);
    }
}
