package domain.model;

import algo.domain.enums.Species;
import algo.domain.enums.State;
import algo.domain.enums.TransformationStatus;
import algo.domain.model.Creature;
import algo.domain.model.Transformation;
import algo.domain.model.TransformationStage;
import algo.domain.services.CreatureFactory;
import algo.domain.services.TransformationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class TransformationTests {

    private Creature creature;
    private TransformationService transformationService;

    @BeforeEach
    void setUp() {
        creature = CreatureFactory.createWhale();
        transformationService = new TransformationService();
    }

    @Test
    @DisplayName("Начало трансформации")
    void testStartTransformation() {
        Transformation transformation = transformationService
                .startTransformation(creature, Species.NOT_WHALE);
        assertNotNull(transformation.getId());
        assertEquals(creature, transformation.getCreature());
        assertEquals(Species.WHALE, transformation.getFromSpecies());
        assertEquals(Species.NOT_WHALE, transformation.getToSpecies());
        assertEquals(TransformationStatus.IN_PROGRESS,
                transformation.getStatus());
        assertEquals(State.TRANSFORMING, creature.getState());
        assertFalse(transformation.getStages().isEmpty());
    }

    @Test
    @DisplayName("Завершение трансформации")
    void testCompleteTransformation() {
        Transformation transformation = transformationService
                .startTransformation(creature, Species.NOT_WHALE);
        transformationService.completeTransformation(transformation);
        assertEquals(TransformationStatus.COMPLETED,
                transformation.getStatus());
        assertEquals(Species.NOT_WHALE, creature.getSpecies());
        assertEquals(State.CEASED, creature.getState());
        assertNotNull(transformation.getEndTime());
        assertNotNull(creature.getTransformedAt());
    }

    @Test
    @DisplayName("Нельзя трансформироваться в тот же вид")
    void testTransformationToSameSpecies() {
        assertThrows(IllegalStateException.class, () -> {
            transformationService.startTransformation(creature, Species.WHALE);
        });
    }

    @Test
    @DisplayName("Длительность трансформации")
    void testTransformationDuration() throws InterruptedException {
        Transformation transformation = transformationService.startTransformation(creature, Species.NOT_WHALE);
        Duration initialDuration = transformation.getDuration();
        Thread.sleep(100);
        Duration afterSleepDuration = transformation.getDuration();
        assertTrue(afterSleepDuration.compareTo(initialDuration) > 0);
        transformation.complete();
        Duration finalDuration = transformation.getDuration();
        Thread.sleep(100);
        assertEquals(finalDuration, transformation.getDuration());
    }

    @Test
    @DisplayName("Этапы трансформации")
    void testTransformationStages() {
        Transformation transformation = transformationService
                .startTransformation(creature, Species.NOT_WHALE);
        assertEquals(3, transformation.getStages().size());
        TransformationStage stage1 = transformation.getStages().get(0);
        assertEquals("начальное осознание", stage1.getName());
        assertNotNull(stage1.getStartTime());
        assertNull(stage1.getEndTime());
        stage1.complete();
        assertNotNull(stage1.getEndTime());
    }
}
