package com.epam.sudoku;

import org.junit.Test;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class FieldTest {

    @Test
    public void getListOfField() {
    }

    @Test(expected = IllegalArgumentException.class)
    public void setField_Error() {
        Field field = new Field();
        int[][] settingField = new int[][]{
                {1, 2, 3, 5},
                {4, 5, 6},
                {7, 8, 9}};
        field.setField(settingField);
        settingField = new int[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9},
                {7, 8, 9}};
        field.setField(settingField);
    }

    @Test
    public void setField_Correct() {
        Field field = new Field();
        int[][] settingField = new int[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}};
        field.setField(settingField);
        assertArrayEquals(settingField, field.getField());
    }

    @Test
    public void isCorrect() {
        Field field = new Field();
        field.setField(new int[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}});
        assertEquals(true, field.isCorrect());
        field.setField(new int[][]{
                {1, 2, 0},
                {4, 5, 0},
                {7, 8, 0}});
        assertEquals(true, field.isCorrect());
        field.setField(new int[3][3]);
        assertEquals(true, field.isCorrect());
        field.setField(new int[][]{
                {1, 2, 3},
                {5, 5, 6},
                {7, 8, 9}});
        assertEquals(false, field.isCorrect());


    }

    @Test
    public void isFieldIsFull() {
        Field field = new Field();
        field.setField(new int[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}});
        assertEquals(true, field.isFieldIsFull());
        field.setField(new int[][]{
                {1, 2, 0},
                {4, 5, 0},
                {7, 8, 0}});
        assertEquals(false, field.isFieldIsFull());
        field.setField(new int[3][3]);
        assertEquals(false, field.isFieldIsFull());
    }

    @Test
    public void getMissingItems() {
        Field field = new Field();
        Set<Integer> expect = Collections.<Integer>emptySet();

        field.setField(new int[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}});
        assertEquals(expect, field.getMissingItems());

        expect = new HashSet<>();
        expect.add(3);
        expect.add(6);
        expect.add(9);
        field.setField(new int[][]{
                {1, 2, 0},
                {4, 5, 0},
                {7, 8, 0}});
        assertEquals(expect, field.getMissingItems());

        field.setField(new int[3][3]);
        expect = new HashSet<>();
        expect.add(1);
        expect.add(2);
        expect.add(3);
        expect.add(4);
        expect.add(5);
        expect.add(6);
        expect.add(7);
        expect.add(8);
        expect.add(9);
        assertEquals(expect, field.getMissingItems());

    }
}