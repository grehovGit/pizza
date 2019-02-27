package strategy;

import builder.PizzaBuilder;
import buildstep.BuildStep;
import buildstep.RecursiveCalcRatingEagerUseFirstPlaceStep_SolvingC;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BuildStrategyAproximateUseFirstPlace_SolvingC implements BuildStrategy {
    private PizzaBuilder pizzaBuilder;

    @Override
    public void build() {
        BuildStep recursiveCalcRatingStep = new RecursiveCalcRatingEagerUseFirstPlaceStep_SolvingC(this.pizzaBuilder);
        recursiveCalcRatingStep.makeStep();
    }
}
