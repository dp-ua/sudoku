package com.epam.sudoku.service;

import com.epam.sudoku.SolutionSeeker;

import java.util.Objects;

public class CellWithValue {
    Cell cell;
    int value;

    public int getValue() {
        return value;
    }

    public CellWithValue(Cell cell, int value) {
        this.cell = cell;
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CellWithValue that = (CellWithValue) o;
        return getValue() == that.getValue() &&
                Objects.equals(cell, that.cell);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cell, getValue());
    }
}
