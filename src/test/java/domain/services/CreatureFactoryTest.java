package domain.services;

import algo.domain.enums.Species;
import algo.domain.enums.State;
import algo.domain.model.Creature;
import algo.domain.services.CreatureFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CreatureFactoryTest {
    @Test
    @DisplayName("Создание кита через фабрику")
    void testCreateWhale() {
        Creature whale = CreatureFactory.createWhale();
        assertNotNull(whale.getId());
        assertEquals(Species.WHALE, whale.getSpecies());
        assertEquals(State.BEING, whale.getState());
        assertNotNull(whale.getCreatedAt());
        assertNotNull(whale.getConsciousness());
    }

    @Test
    @DisplayName("Создание трансформирующегося существа")
    void testCreateTransformingCreature() {
        Creature transforming = CreatureFactory.createTransformingCreature();
        assertEquals(Species.WHALE, transforming.getSpecies());
        assertEquals(State.TRANSFORMING, transforming.getState());
    }

    @Test
    @DisplayName("У всех существ уникальные ID")
    void testUniqueIds() {
        Creature creature1 = CreatureFactory.createWhale();
        Creature creature2 = CreatureFactory.createWhale();
        assertNotEquals(creature1.getId(), creature2.getId());
    }
}
