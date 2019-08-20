package com.epam.sudoku;

public class App {

    Field field;
    SolveFinder solveFinder = new SolveFinder();

    public static void main(String[] args) {
        App app = new App();
        app.init();
        System.out.println("Start field:");
        System.out.println(app.field.toString());

        System.out.println("Solves:");
        for (Field f : app.solveFinder.getSolvedFields(app.field)) {
            System.out.println(f.toString());
            System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++");
        }

    }

    void init() {
        int[][] field1 = new int[][]{
                {5, 3, 0, 0, 7, 0, 0, 0, 0},
                {6, 0, 0, 1, 9, 5, 0, 0, 0},
                {0, 9, 8, 0, 0, 0, 0, 6, 0},
                {8, 0, 0, 0, 6, 0, 0, 0, 3},
                {4, 0, 0, 8, 0, 3, 0, 0, 1},
                {7, 0, 0, 0, 2, 0, 0, 0, 6},
                {0, 6, 0, 0, 0, 0, 2, 8, 0},
                {0, 0, 0, 4, 1, 9, 0, 0, 5},
                {0, 0, 0, 0, 8, 0, 0, 7, 9}
        };

        int[][] field2 = new int[][]{
                {2,0,0,5,9,0,6,0,7},
                {0,7,1,0,0,0,3,0,4},
                {0,0,0,1,0,7,2,0,0},
                {8,4,0,0,6,0,1,0,0},
                {0,9,0,4,0,8,0,6,0},
                {0,0,5,0,7,0,0,9,8},
                {0,0,9,3,0,2,0,0,0},
                {5,0,3,0,0,0,8,7,0},
                {6,0,4,0,5,1,0,0,3}
        };

                int[][] field3 = new int[][]{
        {0,6,0,0,8,0,0,3,0},
        {0,9,7,3,0,4,5,8,0},
        {3,0,0,5,6,9,0,0,2},
        {0,1,9,0,0,0,8,2,0},
        {0,0,0,0,0,0,0,0,0},
        {0,3,5,0,0,0,7,6,0},
        {9,0,0,4,5,8,0,0,7},
        {0,4,8,1,0,6,3,5,0},
        {0,5,0,0,9,0,0,4,0}
                };
//                solution:
//                5 6 1 2 8 7 9 3 4
//        2 9 7 3 1 4 5 8 6
//        3 8 4 5 6 9 1 7 2
//        4 1 9 6 7 5 8 2 3
//        6 7 2 8 3 1 4 9 5
//        8 3 5 9 4 2 7 6 1
//        9 2 3 4 5 8 6 1 7
//        7 4 8 1 2 6 3 5 9
//        1 5 6 7 9 3 2 4 8



                int[][] field4 = new int[][]{
        {1,0,8,0,9,0,3,0,5},
        {2,6,0,0,0,0,0,8,1},
        {0,0,0,0,0,0,0,0,0},
        {0,0,0,8,0,6,0,0,0},
        {0,3,6,9,0,7,4,2,0},
        {0,0,0,2,0,4,0,0,0},
        {0,0,0,0,0,0,0,0,0},
        {7,9,0,0,0,0,0,5,3},
        {8,0,5,0,7,0,9,0,4}
        };

//        int[][] fieldTemplate = new int[][]{
//        {,,,,,,,,},
//        {,,,,,,,,},
//        {,,,,,,,,},
//        {,,,,,,,,},
//        {,,,,,,,,},
//        {,,,,,,,,},
//        {,,,,,,,,},
//        {,,,,,,,,},
//        {,,,,,,,,}
//        };

        field = new Field(field4);
    }


}
