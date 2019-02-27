package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Slice implements Comparable<Slice> {
    public static final int WIDTH_INDEX = 1;
    public static final int HEIGHT_INDEX = 0;
    int width, height, xTopLeft, yTopLeft, number;

    public boolean isValidCapacity(int maxCellsAmount) {
        return width * height <= maxCellsAmount;
    }

    public int getCapacity() {
        return width * height;
    }

    public void resetSize(){
        setWidth(1);
        setHeight(1);
    }

    public boolean enoughIngrdients(Pizza pizza, int minIngredientsNumber) {
        int mushroomCount = 0;
        int tomatoCount = 0;
        int [][] cells = pizza.getCells();

        for (int i = 0; i < getWidth(); ++i) {
            for (int j = 0; j < getHeight(); ++j) {
                if (cells[getXTopLeft() + i][getYTopLeft() + j] == 1) ++tomatoCount;
                else if (cells[getXTopLeft() + i][getYTopLeft() + j] == 2) ++mushroomCount;

                if (mushroomCount >= minIngredientsNumber && tomatoCount >= minIngredientsNumber)
                    return true;
            }
        }
        return false;
    }

    @Override
    public int compareTo(Slice o) {
        return this.getNumber() - o.getNumber();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Slice slice = (Slice) o;

        if (width != slice.width) return false;
        if (height != slice.height) return false;
        if (xTopLeft != slice.xTopLeft) return false;
        return yTopLeft == slice.yTopLeft;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + width;
        result = 31 * result + height;
        result = 31 * result + xTopLeft;
        result = 31 * result + yTopLeft;
        return result;
    }
}
