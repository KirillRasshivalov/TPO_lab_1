package domain.model;

import algo.domain.enums.Concept;
import algo.domain.enums.RealizationType;
import algo.domain.enums.Species;
import algo.domain.model.Creature;
import algo.domain.model.Realization;
import algo.domain.model.Transformation;
import algo.domain.services.CreatureFactory;
import algo.domain.services.TransformationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

public class BoundaryTests {
    private TransformationService transformationService;

    @BeforeEach
    void setUp() {
        transformationService = new TransformationService();
    }

    @Test
    @DisplayName("Осознание после трансформации")
    void testRealizationAfterTransformation() {
        Creature whale = CreatureFactory.createWhale();
        Transformation transformation = transformationService
                .startTransformation(whale, Species.NOT_WHALE);
        transformationService.completeTransformation(transformation);
        Realization lateRealization = new Realization(
                Concept.SELF_IDENTITY,
                RealizationType.DEEP
        );
        whale.getConsciousness().addRealization(lateRealization);
        assertEquals(1, whale.getConsciousness().getRealizationHistory().size());
    }

    @Test
    @DisplayName("Множественные трансформации")
    void testMultipleTransformations() {
        Creature creature = CreatureFactory.createWhale();
        Transformation trans1 = transformationService
                .startTransformation(creature, Species.NOT_WHALE);
        transformationService.completeTransformation(trans1);
        assertEquals(Species.NOT_WHALE, creature.getSpecies());
        Transformation trans2 = transformationService
                .startTransformation(creature, Species.WHALE);
        assertNotNull(trans2);
        assertEquals(Species.NOT_WHALE, trans2.getFromSpecies());
        assertEquals(Species.WHALE, trans2.getToSpecies());
        transformationService.completeTransformation(trans2);
        assertEquals(Species.WHALE, creature.getSpecies());
    }

    @Test
    @DisplayName("Осознание без временного контекста")
    void testRealizationWithoutTimeContext() {
        Creature whale = CreatureFactory.createWhale();
        Instant beforeRealization = Instant.now();
        whale.getConsciousness().addRealization(new Realization(
                Concept.EXISTENCE,
                RealizationType.DEEP
        ));
        assertNotNull(whale.getConsciousness().getLastRealizationTime());
        assertTrue(whale.getConsciousness().getLastRealizationTime()
                .isAfter(beforeRealization));
    }
}
