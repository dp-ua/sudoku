package com.epam.sudoku;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class SolveFinder {

    public List<Field> getSolvedFields(Field field) {
        List<Field> result = new ArrayList<>();
        //simple solutions at first
        Field simpleSolution = trySimpleSolutionIterations(field);
        if (isFieldSolved(simpleSolution)) result.add(simpleSolution);

        // TODO: 8/20/2019 2wayFinder use here


        return result;
    }

    private List<Integer> getFullListOfSolutions() {
        List<Integer> result = new ArrayList<>();
        for (int i = 1; i < 10; i++)
            result.add(i);
        return result;
    }

    List<Integer> getSimpleSolutionsForCell(Field field, int row, int column) {
        List<Integer> result = getFullListOfSolutions();
        for (Integer i : field.getNumbersForRow(row)) result.remove(i);
        for (Integer i : field.getNumbersForColumn(column)) result.remove(i);
        for (Integer i : field.getNumbersForSquare(row, column)) result.remove(i);
        return result;
    }

    Pair<Boolean, Integer> getSolutionForCell(Field field, int row, int column) {
        if (field.isEmptyCell(row, column)) {
            List<Integer> solutionsForCell = getSimpleSolutionsForCell(field, row, column);
            if (solutionsForCell.size() == 1) return new Pair<>(true, solutionsForCell.get(0));
            if (solutionsForCell.size() == 0) throw new RuntimeException("No solutions for this field["+row+","+column+"], sudoku is wrong");
        }
        return new Pair<>(false, 0);
    }

    private Field trySimpleSolutionIterations(Field field) {
        Field workField = field.clone();
        while (true) {
            int countSolvedCells = 0;
            for (int i = 0; i < 9; i++)
                for (int j = 0; j < 9; j++) {
                    Pair<Boolean, Integer> solutionForCell = getSolutionForCell(workField, i, j);
                    if (solutionForCell.getKey()) {
                        workField.setCell(i, j, solutionForCell.getValue());
                        countSolvedCells++;
                    }
                }
            if (countSolvedCells == 0) break;
        }
        return workField;
    }


    boolean isFieldSolved(Field field) {
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++)
                if (field.isEmptyCell(i, j)) return false;
        return true;
    }
}
