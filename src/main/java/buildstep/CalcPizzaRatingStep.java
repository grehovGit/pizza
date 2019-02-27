package buildstep;

import builder.PizzaBuilder;
import lombok.Data;
import model.Pizza;
import model.Slice;

import java.util.Map;
import java.util.TreeSet;

@Data
public class CalcPizzaRatingStep implements BuildStep {
    private Pizza pizza;
    private Map<String, Integer> calculatedStates;
    private TreeSet<Slice> placedSlices;
    private Slice nextSlice;
    private PizzaBuilder pizzaBuilder;
    private int currentRateBefore;
    private int currentRateAfter;

    public CalcPizzaRatingStep(
        PizzaBuilder pizzaBuilder,
        Slice nextBuilding,
        TreeSet placedBuildings,
        int currentRate) {
        this.pizzaBuilder = pizzaBuilder;
        this.pizza = pizzaBuilder.getPizza();
        this.calculatedStates = PizzaBuilder.calculatedStates;
        this.placedSlices = placedBuildings;
        this.nextSlice = nextBuilding;
        this.currentRateBefore = currentRate;
    }

    @Override
    public void makeStep() {
        String currentState = this.getNewState();
        Integer rate = calculatedStates.get(currentState);

        if (rate == null) {
            rate = currentRateBefore + calculateNextSliceRate();
        }

        remeberState(currentState, rate);

        if (rate > this.pizzaBuilder.getMaxRate()) {
            this.pizzaBuilder.setMaxRate(rate);
            this.pizzaBuilder.setMaxKey(currentState);
            this.updateMaxState();
        }
        this.currentRateAfter = rate;
    }

    private void updateMaxState() {
        TreeSet<Slice> maxState = new TreeSet<>();
        placedSlices.forEach(building -> {
            maxState.add(Slice.builder()
                .width(building.getWidth())
                .height(building.getHeight())
                .xTopLeft(building.getXTopLeft())
                .yTopLeft(building.getYTopLeft())
                .number(building.getNumber())
                .build());});
        this.pizzaBuilder.setMaxState(maxState);
    }

    private int calculateNextSliceRate() {
        if (pizza.canPlace(nextSlice)) {
            return nextSlice.getCapacity();
        }
        return -1;
    }

    private String getNewState() {
        return  placedSlices.toString();
    }

    private void remeberState(String state, int rate) {
        this.calculatedStates.put(state, rate);
    }
}
