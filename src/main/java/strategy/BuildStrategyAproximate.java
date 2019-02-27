package strategy;

import builder.PizzaBuilder;
import buildstep.BuildStep;
import buildstep.RecursiveCalcRatingEagerStep;
import buildstep.RecursiveCalcRatingFullStep;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BuildStrategyAproximate implements BuildStrategy {
    private PizzaBuilder pizzaBuilder;

    @Override
    public void build() {
        BuildStep recursiveCalcRatingStep = new RecursiveCalcRatingEagerStep(this.pizzaBuilder);
        recursiveCalcRatingStep.makeStep();
    }
}
