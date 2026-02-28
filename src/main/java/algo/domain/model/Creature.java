package algo.domain.model;

import algo.domain.enums.Species;
import algo.domain.enums.State;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
public class Creature {
    private UUID id;
    private Species species;
    private Consciousness consciousness;
    private Instant createdAt;
    private Instant transformedAt;
    private State state;

    public Creature() {
        this.id = UUID.randomUUID();
        this.createdAt = Instant.now();
        this.consciousness = new Consciousness();
        this.state = State.UNKNOWN;
    }

}
