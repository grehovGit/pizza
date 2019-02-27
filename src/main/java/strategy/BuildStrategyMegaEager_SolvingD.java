package strategy;

import builder.PizzaBuilder;
import buildstep.BuildStep;
import buildstep.RecursiveCalcRatingEagerUseFirstPlaceStep_SolvingC;
import buildstep.RecursiveCalcRatingMegaEagerPlaceStep_SolvingD;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BuildStrategyMegaEager_SolvingD implements BuildStrategy {
    private PizzaBuilder pizzaBuilder;

    @Override
    public void build() {
        BuildStep recursiveCalcRatingStep = new RecursiveCalcRatingMegaEagerPlaceStep_SolvingD(this.pizzaBuilder);
        recursiveCalcRatingStep.makeStep();
    }
}
