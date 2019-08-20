package com.epam.sudoku;

import javafx.util.Pair;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Field {
    int[][] field;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Field field1 = (Field) o;
        return Arrays.equals(field, field1.field);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(field);
    }

    @Override
    public String toString() {
        StringBuffer out = new StringBuffer();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                out.append(field[i][j]).append(" ");
            }
            out.append("\n");
        }
        return out.toString();
    }

    public Field(int[][] field) {
        this.field = field;
        checkField();
    }

    public Field clone() {
        int[][] newField = field;
        return new Field(newField);
    }

    private void checkField() {
        // TODO: 8/20/2019 check the correctness of the field for the correctness of all data
    }

    private void checkCellRowCoordinates(int row) {
        if (row < 0 || row > 8) throw new IllegalArgumentException("Row must be in the range [0-8] ");
    }

    private void checkCellColumnCoordinates(int column) {
        if (column < 0 || column > 8) throw new IllegalArgumentException("Column must be in the range [0-8]");
    }

    private void checkValue(int value) {
        if (value < 1 || value > 9) throw new IllegalArgumentException("Wrong Value. It must be in the range [1-9]");
    }

    private void checkCellCoordinates(int row, int column) {
        checkCellRowCoordinates(row);
        checkCellColumnCoordinates(column);
    }


    public void setCell(int row, int column, int value) {
        checkCellCoordinates(row, column);
        checkValue(value);
        field[row][column] = value;
    }


    public boolean isNotEmptyCell(int row, int column) {
        return field[row][column] != 0;
    }

    public boolean isEmptyCell(int row, int column) {
        return field[row][column] == 0;
    }

    public Set<Integer> getNumbersForRow(int row) {
        checkCellRowCoordinates(row);
        Set<Integer> result = new HashSet<>();
        for (int i = 0; i < 9; i++)
            if (isNumberGoodForAddToSet(result, row, i)) result.add(field[row][i]);
        return result;
    }

    public Set<Integer> getNumbersForColumn(int column) {
        checkCellColumnCoordinates(column);
        Set<Integer> result = new HashSet<>();
        for (int i = 0; i < 9; i++) {
            if (isNumberGoodForAddToSet(result, i, column)) result.add(field[i][column]);
        }
        return result;
    }

    public Set<Integer> getNumbersForSquare(int row, int column) {
        checkCellCoordinates(row, column);
        Pair<Integer, Integer> squareStartCoordinates = getStartCoordinatesForSquare(row, column);
        Set<Integer> result = new HashSet<>();
        for (int i = squareStartCoordinates.getKey(); i < squareStartCoordinates.getKey() + 3; i++)
            for (int j = squareStartCoordinates.getValue(); j < squareStartCoordinates.getValue() + 3; j++)
                if (isNumberGoodForAddToSet(result, i, j)) result.add(field[i][j]);
        return result;
    }

    private boolean isNumberGoodForAddToSet(Set<Integer> set, int row, int column) {
        if (isNotEmptyCell(row, column))
            if (set.contains(field[row][column])) throw new RuntimeException("Sudoku is wrong");
            else return true;
        return false;
    }

    private Pair<Integer, Integer> getStartCoordinatesForSquare(int row, int column) {
        int cellRow = (row / 3) * 3;
        int cellColum = (column / 3) * 3;
        return new Pair<>(cellRow, cellColum);
    }

}
