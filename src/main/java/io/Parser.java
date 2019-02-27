package io;

import model.Pizza;
import model.Slice;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Parser {

    public static Pizza getMainEntity(String[] mainEntityPlan) {
        Pizza pizza = new Pizza(
            Integer.parseInt(mainEntityPlan[Pizza.WIDTH_INDEX]),
            Integer.parseInt(mainEntityPlan[Pizza.HEIGHT_INDEX]),
            new int[Integer.parseInt(mainEntityPlan[Pizza.WIDTH_INDEX])][Integer.parseInt(mainEntityPlan[Pizza.HEIGHT_INDEX])]);
        System.out.println(pizza);
        return pizza;
    }

    public static Pizza fillPizzaCells(LinkedList<String> project, Pizza pizza) {
        int j = 0;
        for (String row : project) {
            int i = 0;
            for (int val : Arrays.stream(row.split("")).mapToInt(s -> s.equals("T") ? 1 : 2).toArray()) {
                pizza.getCells()[i++][j] = val;
            }
            j++;
        }

        System.out.println(pizza);

        return pizza;
    }


    public static List<String> exportResult(Set<Slice> maxState) {
        LinkedList<String> result = maxState.stream()
            .map(slice ->
                "" + slice.getYTopLeft()
                    + " "
                    + slice.getXTopLeft()
                    + " "
                    + (slice.getYTopLeft() + slice.getHeight() - 1)
                    + " "
                    + (slice.getXTopLeft() + slice.getWidth() - 1))
            .collect(Collectors.toCollection(() -> new LinkedList<>()));
        result.push(String.valueOf(maxState.size()));
        return result;
    }
}
