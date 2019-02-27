package strategy;

import builder.PizzaBuilder;
import buildstep.BuildStep;
import buildstep.RecursiveCalcRatingFullStep;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BuildStrategyOptimize implements BuildStrategy {
    private PizzaBuilder pizzaBuilder;

    @Override
    public void build() {
        BuildStep recursiveCalcRatingStep = new RecursiveCalcRatingFullStep(this.pizzaBuilder);
        recursiveCalcRatingStep.makeStep();
    }
}
