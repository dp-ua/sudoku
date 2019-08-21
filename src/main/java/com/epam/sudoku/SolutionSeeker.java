package com.epam.sudoku;

import com.epam.sudoku.exceptions.WrongSudokuException;
import com.epam.sudoku.service.CellWithValue;
import javafx.util.Pair;

import java.util.*;

public class SolutionSeeker {

    public List<Field> getSolvedFields(Field field) {
        List<Field> result = new ArrayList<>();
        //simple solutions at first
        Field simpleSolution;
        try {


            simpleSolution = getFieldWithSimpleSolution(field);
            if (isFieldSolved(simpleSolution)) result.add(simpleSolution);
//            else {
//                Set<Field> fields = getAllSolutions(field);
//                for (Field f : fields) {
//                    if (isFieldSolved(f)) result.add(f);
//                }
//            }
        } catch (WrongSudokuException e) {
            System.out.println(e.getMessage());
        }

        return result;

    }

    private Map<CellWithValue, Field> generateTwoWaySolutionsFields(Field field) {
        Map<CellWithValue, Field> result = new HashMap<>();
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++) {
                List<Integer> solutionsForCell = getSimpleSolutionsForCell(field, i, j);
                if (solutionsForCell.size() == 2) {
                    Field oneNewWay = field.clone();
                    Field twoNewWay = field.clone();

                    oneNewWay.setCell(i, j, solutionsForCell.get(0));
                    twoNewWay.setCell(i, j, solutionsForCell.get(1));
                    result.put(new CellWithValue(i, j, solutionsForCell.get(0)), oneNewWay);
                    result.put(new CellWithValue(i, j, solutionsForCell.get(1)), twoNewWay);
                }

            }
        return result;
    }


    private Set<Field> getAllSolutions(Field field) {
        Map<CellWithValue, Field> result = new HashMap<>();
        Set<CellWithValue> badCellChange = new HashSet<>();

        result.put(new CellWithValue(0, 0, 0), field.clone());

        while (true || result.size() > 0) {
            Set<Field> tempField = new HashSet<>();
            tempField.addAll(result.values());
            int countGenerator = 0;
            int passCount = 0;
            for (Field workField : tempField) {
                passCount++;
                if (isFieldSolved(workField)) continue;

                Field intermediateField;
                try {
                    intermediateField = getFieldWithSimpleSolution(workField);
                    if (isFieldSolved(intermediateField)) {
//                        result.add(intermediateField);
//                        return result;
                    } else {
                        Map<CellWithValue, Field> twoWayFields = generateTwoWaySolutionsFields(workField);
                        countGenerator++;
//                        result.addAll(twoWayFields);
                        break;
                    }
                } catch (WrongSudokuException e) {
                    result.remove(workField);
                }


            }
            if (countGenerator == 0) break;
        }
        return Collections.EMPTY_SET;
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

    List<Integer> getSolutionsForCell(Field field, int row, int column) throws WrongSudokuException {
        if (field.isEmptyCell(row, column)) {
            List<Integer> solutionsForCell = getSimpleSolutionsForCell(field, row, column);
            if (solutionsForCell.size() == 0)
                throw new WrongSudokuException("No solutions for this field[" + row + "," + column + "], sudoku is wrong");
            return solutionsForCell;
        }
        return Collections.emptyList();
    }

    Pair<Boolean, Integer> getSolutionForCell(Field field, int row, int column) throws WrongSudokuException {
        if (field.isEmptyCell(row, column)) {
            List<Integer> solutionsForCell = getSimpleSolutionsForCell(field, row, column);
            if (solutionsForCell.size() == 1) return new Pair<>(true, solutionsForCell.get(0));
            if (solutionsForCell.size() == 0)
                throw new WrongSudokuException("No solutions for this field[" + row + "," + column + "], sudoku is wrong");
        }
        return new Pair<>(false, 0);
    }


    private Field getFieldWithSimpleSolution(Field field) throws WrongSudokuException {
        Field workField = field.clone();
        while (true) {
            int countSolvedCells = 0;
            for (int i = 0; i < 9; i++)
                for (int j = 0; j < 9; j++) {
                    List<Integer> solutionsForCell = getSolutionsForCell(workField, i, j);
                    if (solutionsForCell.size() == 1) {
                        workField.setCell(i, j, solutionsForCell.get(0));
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
