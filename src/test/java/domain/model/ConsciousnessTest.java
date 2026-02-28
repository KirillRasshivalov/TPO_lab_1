package domain.model;

import algo.domain.enums.Concept;
import algo.domain.enums.RealizationType;
import algo.domain.model.Consciousness;
import algo.domain.model.Creature;
import algo.domain.model.Realization;
import algo.domain.services.CreatureFactory;
import algo.domain.services.TransformationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.*;

public class ConsciousnessTest {
    private Creature creature;
    private Consciousness consciousness;

    @BeforeEach
    void setUp() {
        creature = CreatureFactory.createWhale();
        consciousness = creature.getConsciousness();
    }

    @Test
    @DisplayName("Новое сознание не имеет осознаний")
    void testNewConsciousness() {
        assertFalse(consciousness.isAware());
        assertTrue(consciousness.getRealizationHistory().isEmpty());
        assertEquals(0.0, consciousness.getAwarenessLevel());
    }

    @Test
    @DisplayName("Добавление осознания")
    void testAddRealization() {
        Realization realization = new Realization(
                Concept.SELF_IDENTITY,
                RealizationType.INITIAL
        );
        consciousness.addRealization(realization);
        assertTrue(consciousness.isAware());
        assertEquals(1, consciousness.getRealizationHistory().size());
        assertNotNull(consciousness.getLastRealizationTime());
        assertTrue(consciousness.hasRealized(Concept.SELF_IDENTITY));
    }

    @ParameterizedTest
    @DisplayName("Проверка осознания различных концептов")
    @EnumSource(Concept.class)
    void testRealizationOfConcepts(Concept concept) {
        Realization realization = new Realization(
                concept, RealizationType.DEEP
        );
        consciousness.addRealization(realization);
        assertTrue(consciousness.hasRealized(concept));
    }

    @Test
    @DisplayName("Множественные осознания")
    void testMultipleRealizations() {
        for (Concept concept : Concept.values()) {
            Realization realization = new Realization(
                    concept, RealizationType.ACCEPTANCE
            );
            consciousness.addRealization(realization);
        }
        assertEquals(Concept.values().length, consciousness.getRealizationHistory().size());
    }
}
