package algo.domain.services;

import algo.domain.enums.Species;
import algo.domain.enums.State;
import algo.domain.model.Creature;

public class CreatureFactory {

    public static Creature createWhale() {
        Creature creature = new Creature();
        creature.setSpecies(Species.WHALE);
        creature.setState(State.BEING);
        return creature;
    }

    public static Creature createTransformingCreature() {
        Creature creature = new Creature();
        creature.setSpecies(Species.WHALE);
        creature.setState(State.TRANSFORMING);
        return creature;
    }
}
