package com.devone.hamzafetuga.viva;



public class MalariaDrugs {

    public static int FANTERM = 0;
    public static int COMBISUNATE = 1;
    public static int VOATHER = 2;
    public static int AMATEM = 3;
    public static int ARTEMEFAN = 4;
    public static int ARENA = 5;
    public static int LUMARTEM = 6;

    public static Drug[] malariaDrugs = {

            new Drug(
                    "Fanterm DS tab",
                    new int[] {1,2,3,4,5},
                    new int[] {8,6,8,6},
                    5
            ),

            new Drug(
                    "Combisunate DS tab",
                    new int[] {6,7,8,9},
                    new int[] {150,150,150},
                    4
            ),

            new Drug(
                    "Voather forte tab",
                    new int[] {1,2,3,4,5},
                    new int[] {8,8,6,8},
                    5
            ),

            new Drug(
                    "Amatem forte tab",
                    new int[] {9,8,7},
                    new int[] {5,5},
                    3
            ),

            new Drug(
                    "Artemefan tab",
                    new int[] {4,4,3,2,1},
                    new int[] {6,8,6,8},
                    5
            ),
            new Drug(
                    "Arena plus forte tab",
                    new int[] {8,2,3,1},
                    new int[] {6,8,6},
                    4
            ),
            new Drug(
                    "Lumartem tab",
                    new int[] {3,5,3,3},
                    new int[] {8,6,6},
                    4
            )
    };
}
