package algo.domain.model;

import algo.domain.enums.Concept;
import algo.domain.enums.RealizationType;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter @Setter
public class Realization {
    private UUID id;
    private Concept concept;
    private Instant realizationTime;
    private RealizationType type;
    private String details;

    public Realization(Concept concept, RealizationType type) {
        this.id = UUID.randomUUID();
        this.concept = concept;
        this.realizationTime = Instant.now();
        this.type = type;
    }
}
