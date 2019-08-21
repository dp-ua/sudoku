package com.epam.sudoku;

import com.epam.sudoku.exceptions.WrongSudokuException;
import com.epam.sudoku.service.*;
import javafx.util.Pair;

import java.util.*;

public class SolutionSeeker {

    public Set<Field> getSolvedFields(Field field) throws WrongSudokuException {
        Set<Field> result = new HashSet<>();
        List<Field> workListOfFields = new ArrayList<>();
        Field trySimpleSolution = getFieldWithSimpleSolution(field);

        if (isFieldSolved(trySimpleSolution)) result.add(trySimpleSolution);

        else {
            workListOfFields.add(trySimpleSolution);
            while (true) {
                boolean isProgress = false;
                Set<Field> generatedFields = new HashSet<>();
                for (Field f : workListOfFields) {
                    try {
                        Field tryToSolveField = getFieldWithSimpleSolution(f);
                        if (isFieldSolved(tryToSolveField)) result.add(tryToSolveField);
                        else {
                            Set<Field> generate = generateFieldsWithFilledAllTwoWayCells(tryToSolveField);
                            if (generate.size() > 1) {
                                isProgress = true;
                                generatedFields.addAll(generate);
                            }
                        }
                    } catch (WrongSudokuException e) {
                        //this way was bad. Ignore it
                    }


                }
                workListOfFields.clear();
                workListOfFields.addAll(generatedFields);
                if (!isProgress) break;
            }

            // TODO: 8/21/2019 find two way solutions
//            Set<Field> solves = getSolvedField(result);
        }

        return result;
    }


    private Set<Field> generateFieldsWithFilledAllTwoWayCells(Field field) {
        Set<Field> result = new HashSet<>();
        List<Field> workList = new ArrayList<>();
        workList.add(field);

        while (workList.size() > 0) {
            Field f = workList.get(0);
            workList.remove(f);
            Map<Cell, Pair<Integer, Integer>> cellsWithSolutions;
            try {
                cellsWithSolutions = getAllCellsWithTwoSolutions(f);
                Pair<Cell, Cell> pairForFill = getPairOfCellForPriorityFilling(cellsWithSolutions);
                if (pairForFill != null) {
                    Pair<Integer, Integer> values = cellsWithSolutions.get(pairForFill.getKey());
                    Field variantOne = f.clone();
                    Field variantTwo = f.clone();
                    variantOne.setCell(pairForFill.getKey(), values.getKey());
                    variantOne.setCell(pairForFill.getValue(), values.getValue());
                    variantTwo.setCell(pairForFill.getKey(), values.getValue());
                    variantTwo.setCell(pairForFill.getValue(), values.getKey());
                    workList.add(variantOne);
                    workList.add(variantTwo);
                } else result.add(f);
            } catch (WrongSudokuException e) {
                //It can be wrong. Ignore that. Bad way will remove itself without consequences
            }
        }
        return result;
    }

    private Pair<Cell, Cell> getPairOfCellForPriorityFilling(Map<Cell, Pair<Integer, Integer>> mapOfCombinations) {
        TreeMap<Integer, Pair<Cell, Cell>> result = new TreeMap<>();
        List<Cell> cells = new ArrayList<>();
        cells.addAll(mapOfCombinations.keySet());
        Map<Cell, Pair<Integer, Integer>> workMap = new HashMap<>(mapOfCombinations);
        while (cells.size() > 1) {
            Cell cell = cells.get(0);
            Pair<Integer, Integer> valuePair = workMap.get(cell);
            cells.remove(0);
            workMap.remove(cell);
            for (Map.Entry<Cell, Pair<Integer, Integer>> entry : mapOfCombinations.entrySet())
                if (!workMap.containsKey(entry.getKey()) && valuePair.equals(entry.getValue())) {
                    if (cell != entry.getKey())
                        result.put(cell.getNumberOfMatchesOption(entry.getKey()), new Pair<Cell, Cell>(cell, entry.getKey()));
                    workMap.remove(entry.getKey());
                }
        }
        for (int i = 3; i >= 0; i--) {
            if (result.containsKey(i)) return result.get(i);
        }
        return null;
    }

    private Map<Cell, Pair<Integer, Integer>> getAllCellsWithTwoSolutions(Field field) throws WrongSudokuException {
        Map<Cell, Pair<Integer, Integer>> result = new HashMap<>();

        for (Cell cell : field.cellSet()) {
            List<Integer> solutions = getSolutionsForCell(field, cell);
            if (solutions.size() == 2) {
                Pair<Integer, Integer> pairOfValues = new Pair<>(solutions.get(0), solutions.get(1));
                result.put(cell, pairOfValues);
            }
        }
        return result;
    }

    private Set<Field> getSolvedField(List<Field> fields) {
        Set<Field> result = Collections.EMPTY_SET;
        for (Field f : fields)
            if (isFieldSolved(f)) result.add(f);
        return result;
    }


    private List<Integer> getFullListOfSolutions() {
        List<Integer> result = new ArrayList<>();
        for (int i = 1; i < 10; i++)
            result.add(i);
        return result;
    }

    List<Integer> getSimpleSolutionsForCell(Field field, Cell cell) {
        List<Integer> result = getFullListOfSolutions();
        result.removeAll(field.getNumbersForRow(cell.getRow()));
        result.removeAll(field.getNumbersForColumn(cell.getColumn()));
        result.removeAll(field.getNumbersForSquare(cell.getRow(), cell.getColumn()));
        return result;
    }

    List<Integer> getSolutionsForCell(Field field, Cell cell) throws WrongSudokuException {
        if (field.isEmptyCell(cell)) {
            List<Integer> solutionsForCell = getSimpleSolutionsForCell(field, cell);
            if (solutionsForCell.size() == 0)
                throw new WrongSudokuException("No solutions for this field[" + cell.getRow() + "," + cell.getColumn() + "], sudoku is wrong");
            return solutionsForCell;
        }
        return Collections.emptyList();
    }


    private Field getFieldWithSimpleSolution(Field field) throws WrongSudokuException {
        Field workField = field.clone();
        while (true) {
            int countSolvedCells = 0;
            for (Cell cell : workField.cellSet()) {
                List<Integer> solutionsForCell = getSolutionsForCell(workField, cell);
                if (solutionsForCell.size() == 1) {
                    workField.setCell(cell, solutionsForCell.get(0));
                    countSolvedCells++;
                }
            }
            if (countSolvedCells == 0) break;
        }
        return workField;
    }


    boolean isFieldSolved(Field field) {
        for (Cell cell : field.cellSet()) {
            if (field.isEmptyCell(cell)) return false;
        }
        return true;
    }

}
