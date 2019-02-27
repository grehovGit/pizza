package builder;

import buildstep.BuildStep;
import buildstep.VisualizeResultStep;
import io.InputOutput;
import io.Parser;
import lombok.Data;
import model.Pizza;
import model.Slice;
import strategy.BuildStrategy;
import strategy.BuildStrategyAproximateUseFirstPlace_SolvingC;
import strategy.BuildStrategyMegaEager_SolvingD;

import java.util.*;

@Data
public class PizzaBuilder {
    private int maxRate = Integer.MIN_VALUE;
    private String maxKey = "";
    private Set<Slice> maxState = new TreeSet<>();
    private Pizza pizza;
    public static Map<String, Integer> calculatedStates = new HashMap<>(10000000);

    int minIngredientNmber;
    int maxCellsSlice;

    public static void main(String[] args) {
        LinkedList<String> pizzaPlan = InputOutput.load("C://testPizza/pizzaStart.txt");
        PizzaBuilder pizzaBuilder = new PizzaBuilder(pizzaPlan);
        BuildStrategy solvingStrategy = pizzaBuilder.getStratgey();
        solvingStrategy.build();
        BuildStep visualization = new VisualizeResultStep();
        visualization.makeStep();

        InputOutput.export(Parser.exportResult(pizzaBuilder.getMaxState()), "C://testPizza/pizza_result.txt");

        System.out.println("Max State:" + pizzaBuilder.getMaxState());
        System.out.println("Max Rate:" + pizzaBuilder.getMaxRate());
        System.out.println("Max Key:" + pizzaBuilder.getMaxKey());
    }

    public PizzaBuilder(LinkedList<String> project) {
        String[] pizzaPlan = project.poll().split(" ");
        this.pizza = Parser.getMainEntity(pizzaPlan);
        this.pizza = Parser.fillPizzaCells(project, pizza);

        this.minIngredientNmber = Integer.parseInt(pizzaPlan[Pizza.MIN_EACH_INGR_PER_SLICE_INDEX]);
        this.maxCellsSlice = Integer.parseInt(pizzaPlan[Pizza.MAX_CELLS_SLICE_INDEX]);
    }

    private BuildStrategy getStratgey()
    {
//        return new BuildStrategyOptimize(this);
//        return new BuildStrategyAproximate(this);
//        return new BuildStrategyAproximateUseFirstPlace_SolvingC(this);
        return new BuildStrategyMegaEager_SolvingD(this);
    }
}
