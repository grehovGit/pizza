package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Pizza {
    public static final int WIDTH_INDEX = 1;
    public static final int HEIGHT_INDEX = 0;

    public static final int MIN_EACH_INGR_PER_SLICE_INDEX = 2;
    public static final int MAX_CELLS_SLICE_INDEX = 3;
    int width, height;
    int[][] cells;

    public void placeSlice(Slice slice) {
        for (int i = 0; i < slice.getWidth(); ++i) {
            for (int j = 0; j < slice.getHeight(); ++j) {
                this.cells[slice.getXTopLeft() + i][slice.getYTopLeft() + j] += slice.getNumber();
            }
        }
    }

    public void removeSlice(Slice slice) {
        for (int i = 0; i < slice.getWidth(); ++i) {
            for (int j = 0; j < slice.getHeight(); ++j) {
                this.cells[slice.getXTopLeft() + i][slice.getYTopLeft() + j] -= slice.getNumber();
            }
        }
    }



    @Override
    public String toString() {
        return "Pizza{" +
            "height=" + height +
            ", width=" + width +
            ", cells=" + printCells() +
            '}';
    }

    private String printCells() {
        StringBuilder lines = new StringBuilder();
        lines.append(System.lineSeparator());
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                lines.append(cells[j][i]);
                lines.append("\t");
            }
            lines.append(System.lineSeparator());
        }
        return lines.toString();
    }

    public boolean canPlace(Slice newSlice) {
        int height = newSlice.getHeight();
        int width = newSlice.getWidth();
        int xtop = newSlice.getXTopLeft();
        int ytop = newSlice.getYTopLeft();

        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {
                if (cells[xtop + i][ytop + j] > 2)
                    return false;
            }
        }
        return true;
    }
}
