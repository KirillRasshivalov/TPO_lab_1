package domain.services;

import algo.domain.enums.Concept;
import algo.domain.enums.RealizationType;
import algo.domain.enums.Species;
import algo.domain.enums.State;
import algo.domain.model.*;
import algo.domain.services.CreatureFactory;
import algo.domain.services.TransformationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TransformationTest {

    private TransformationService transformationService;

    @BeforeEach
    void setUp() {
        transformationService = new TransformationService();
    }

    @Test
    @DisplayName("Полный цикл: от осознания до трансформации")
    void testFullCycle() {
        Creature whale = CreatureFactory.createWhale();
        Realization realization1 = new Realization(
                Concept.SELF_IDENTITY,
                RealizationType.INITIAL
        );
        whale.getConsciousness().addRealization(realization1);
        assertTrue(whale.getConsciousness().hasRealized(Concept.SELF_IDENTITY));
        Transformation transformation = transformationService
                .startTransformation(whale, Species.NOT_WHALE);
        assertEquals(State.TRANSFORMING, whale.getState());
        Realization realization2 = new Realization(
                Concept.TRANSFORMATION,
                RealizationType.ACCEPTANCE
        );
        whale.getConsciousness().addRealization(realization2);
        transformationService.completeTransformation(transformation);
        assertEquals(Species.NOT_WHALE, whale.getSpecies());
        assertEquals(State.CEASED, whale.getState());
        assertNotNull(whale.getTransformedAt());
        Realization realization3 = new Realization(
                Concept.CESSATION,
                RealizationType.DEEP
        );
        whale.getConsciousness().addRealization(realization3);
        assertEquals(3, whale.getConsciousness().getRealizationHistory().size());
    }

    @Test
    @DisplayName("Сценарий с ограниченным временем")
    void testTimeConstrained() {
        Creature whale = CreatureFactory.createWhale();
        TimeContext timeContext = new TimeContext(
                Duration.ofSeconds(1)
        );
        whale.getConsciousness().addRealization(new Realization(
                Concept.SELF_IDENTITY,
                RealizationType.INITIAL
        ));
        assertFalse(timeContext.isTimeRunningOut());
        Transformation transformation = transformationService
                .startTransformation(whale, Species.NOT_WHALE);
        transformationService.completeTransformation(transformation);
        assertTrue(whale.getTransformedAt().isBefore(timeContext.getReferenceTime().plus(timeContext.getAvailableTime())
        ));
    }
}
