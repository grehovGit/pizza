package buildstep;

import builder.PizzaBuilder;
import model.Pizza;
import model.Slice;

import java.util.Map;
import java.util.TreeSet;

public class RecursiveCalcRatingFullStep implements BuildStep {
    PizzaBuilder pizzaBuilder;
    private Pizza pizza;
    private Map<String, Integer> calculatedStates;
    private TreeSet<Slice> placedSlices = new TreeSet<>();
    private int startSliceNumber = 3;

    public RecursiveCalcRatingFullStep(PizzaBuilder pizzaBuilder) {
        this.pizza = pizzaBuilder.getPizza();
        this.calculatedStates = PizzaBuilder.calculatedStates;
        this.pizzaBuilder = pizzaBuilder;
    }

    @Override
    public void makeStep() {
        rate(this.placedSlices, 0);
    }

    private void rate(TreeSet<Slice> slices, int currentRate) {
        Slice slice = new Slice(1, 1, 0, 0, startSliceNumber++);
        slices.add(slice);

        while(inPizza(slice)) {
            while(nextSize(slice)) {
                CalcPizzaRatingStep calcBuildingRatingStep = new CalcPizzaRatingStep(
                    pizzaBuilder,
                    slice,
                    placedSlices,
                    currentRate);
                calcBuildingRatingStep.makeStep();
                int newRate = calcBuildingRatingStep.getCurrentRateAfter();

                if (newRate >= currentRate) {
                    pizza.placeSlice(slice);
                    System.out.println(pizza);

                    rate(slices, newRate);

                    pizza.removeSlice(slice);
                    System.out.println(pizza);
                }

            };
            slice.resetSize();
            moveSlice(slice);
        }
        slices.remove(slice);
    }

    private boolean inPizza(Slice slice){
        return slice.getXTopLeft() + slice.getWidth() <= this.pizza.getWidth() &&
            slice.getYTopLeft() + slice.getHeight() <= this.pizza.getHeight();
    }

    private boolean isInEndOfRow(Slice slice){
        return slice.getXTopLeft() + slice.getWidth() == this.pizza.getWidth();
    }

    private void moveToNextRow(Slice slice) {
        slice.setXTopLeft(0);
        slice.setYTopLeft(slice.getYTopLeft() + 1);
    }

    private void moveNext(Slice slice) {
        slice.setXTopLeft(slice.getXTopLeft() + 1);
    }

    private void moveSlice(Slice slice) {
        if (isInEndOfRow(slice)) {
            moveToNextRow(slice);
        } else {
            moveNext(slice);
        }
    }

    private boolean nextSize(Slice slice) {
        for (int i = slice.getWidth(); i < pizza.getWidth() - slice.getXTopLeft() + 1; ++i) {
            for (int j = slice.getHeight(); j < pizza.getHeight() - slice.getYTopLeft() + 1; ++j) {
                Slice newSlice = Slice
                    .builder()
                    .width(i)
                    .height(j)
                    .xTopLeft(slice.getXTopLeft())
                    .yTopLeft(slice.getYTopLeft())
                    .build();
                if (!pizza.canPlace(newSlice)) return false;
                if (!newSlice.isValidCapacity(pizzaBuilder.getMaxCellsSlice())) return false;
                if (newSlice.enoughIngrdients(pizza, pizzaBuilder.getMinIngredientNmber())) {
                    slice.setWidth(newSlice.getWidth());
                    slice.setHeight(newSlice.getHeight());
                    return true;
                }
            }
        }
        return false;
    }
}
