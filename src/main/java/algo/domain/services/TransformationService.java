package algo.domain.services;

import algo.domain.enums.Species;
import algo.domain.enums.State;
import algo.domain.model.Creature;
import algo.domain.model.Transformation;
import algo.domain.model.TransformationStage;

public class TransformationService {
    public Transformation startTransformation(Creature creature, Species targetSpecies) {
        if (creature.getSpecies() == targetSpecies) {
            throw new IllegalStateException("Существо уже " + targetSpecies);
        }
        Transformation transformation = new Transformation(
                creature, creature.getSpecies(), targetSpecies
        );
        creature.setState(State.TRANSFORMING);
        transformation.getStages().add(new TransformationStage("начальное осознание"));
        transformation.getStages().add(new TransformationStage("процесс осознания"));
        transformation.getStages().add(new TransformationStage("процесс переосознания себя"));
        return transformation;
    }

    public void completeTransformation(Transformation transformation) {
        transformation.complete();
        transformation.getCreature().setSpecies(transformation.getToSpecies());
        transformation.getCreature().setState(State.CEASED);
    }
}
