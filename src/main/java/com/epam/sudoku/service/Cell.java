package com.epam.sudoku.service;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class Cell {
    int row;
    int column;

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
