package com.epam.sudoku.service;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Cell {
    int row;
    int column;

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public Cell(int row, int column) {
        this.row = row;
        this.column = column;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return getRow() == cell.getRow() &&
                getColumn() == cell.getColumn();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRow(), getColumn());
    }

    public Set<CellOption> getOptions() {
        Set<CellOption> result = new HashSet<>();
        result.add(new CellOption(Condition.ROW, String.valueOf(row)));
        result.add(new CellOption(Condition.COLUMN, String.valueOf(column)));
        result.add(new CellOption(Condition.SQUARE, Field.getNameForSquare(row, column)));
        return result;
    }

    public int getNumberOfMatchesOption(Cell cell) {
        Set<CellOption> cellOption = cell.getOptions();
        int matches = 0;
        for (CellOption option : getOptions()) {
            if (cellOption.contains(option)) matches++;
        }
        return matches;
    }


}
