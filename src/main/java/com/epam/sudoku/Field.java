package com.epam.sudoku;

import java.util.*;

public class Field {
    final int ROWS = 3;
    final int COLS = 3;
    int[][] field;

    public int[][] getField() {
        return field;
    }

    public boolean isFieldIsFull() {
        LinkedList<Integer> fieldInList = getListOfField();
        if (fieldInList.size() < ROWS * COLS) return false;

        for (int i = 1; i <= ROWS * COLS; i++) {
            if (fieldInList.contains(i)) continue;
            else return false;
        }
        return true;
    }

    public Set<Integer> getMissingItems() {
        if (isFieldIsFull()) return Collections.EMPTY_SET;
        Set<Integer> result = new HashSet<>();
        for (int i = 1; i <= ROWS * COLS; i++) {
            if (getListOfField().contains(i)) continue;
            else result.add(i);
        }
        return result;
    }


    public void setField(int[][] field) {
        if (field.length != ROWS) throw new IllegalArgumentException("Wrond field size. Must be " + ROWS + "x" + COLS);
        for (int i = 0; i < ROWS; i++) {
            if (field[i].length != COLS)
                throw new IllegalArgumentException("Wrond field size. Must be " + ROWS + "x" + COLS);
        }
        this.field = field;
    }

    public Field() {
        this.field = new int[ROWS][COLS];
    }

    LinkedList<Integer> getListOfField() {
        LinkedList<Integer> result = new LinkedList<>();
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                result.add(field[i][j]);
            }
        }
        return result;
    }

    public boolean isCorrect() {
        HashSet<Integer> setOfNumbersInField = new HashSet<>();
        for (Integer i : getListOfField()) {
            if (i != 0 && setOfNumbersInField.contains(i)) return false;
            else setOfNumbersInField.add(i);
        }
        return true;
    }

}
