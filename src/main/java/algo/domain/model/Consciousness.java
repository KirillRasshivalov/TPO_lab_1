package algo.domain.model;

import algo.domain.enums.Concept;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class Consciousness {

    private boolean isAware;
    private List<Realization> realizationHistory;
    private double awarenessLevel;
    private Instant lastRealizationTime;

    public Consciousness() {
        this.isAware = false;
        this.realizationHistory = new ArrayList<>();
        this.awarenessLevel = 0.0;
    }

    public void addRealization(Realization realization) {
        realizationHistory.add(realization);
        this.lastRealizationTime = Instant.now();
        this.isAware = true;
    }

    public boolean hasRealized(Concept concept) {
        return realizationHistory.stream()
                .anyMatch(r -> r.getConcept().equals(concept));
    }
}
