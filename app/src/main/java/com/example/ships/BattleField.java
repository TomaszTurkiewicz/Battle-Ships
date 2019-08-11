package com.example.ships;

import java.util.Random;

public class BattleField {
    Ship[][] battleField = new Ship[10][10];

    boolean position;


    public BattleField() {
        initializeBattleShipField();
    }

    private void initializeBattleShipField() {
        battleField[0][0] = new Ship(0, 0, false, false);
        battleField[0][1] = new Ship(0, 0, false, false);
        battleField[0][2] = new Ship(0, 0, false, false);
        battleField[0][3] = new Ship(0, 0, false, false);
        battleField[0][4] = new Ship(0, 0, false, false);
        battleField[0][5] = new Ship(0, 0, false, false);
        battleField[0][6] = new Ship(0, 0, false, false);
        battleField[0][7] = new Ship(0, 0, false, false);
        battleField[0][8] = new Ship(0, 0, false, false);
        battleField[0][9] = new Ship(0, 0, false, false);

        battleField[1][0] = new Ship(0, 0, false, false);
        battleField[1][1] = new Ship(0, 0, false, false);
        battleField[1][2] = new Ship(0, 0, false, false);
        battleField[1][3] = new Ship(0, 0, false, false);
        battleField[1][4] = new Ship(0, 0, false, false);
        battleField[1][5] = new Ship(0, 0, false, false);
        battleField[1][6] = new Ship(0, 0, false, false);
        battleField[1][7] = new Ship(0, 0, false, false);
        battleField[1][8] = new Ship(0, 0, false, false);
        battleField[1][9] = new Ship(0, 0, false, false);

        battleField[2][0] = new Ship(0, 0, false, false);
        battleField[2][1] = new Ship(0, 0, false, false);
        battleField[2][2] = new Ship(0, 0, false, false);
        battleField[2][3] = new Ship(0, 0, false, false);
        battleField[2][4] = new Ship(0, 0, false, false);
        battleField[2][5] = new Ship(0, 0, false, false);
        battleField[2][6] = new Ship(0, 0, false, false);
        battleField[2][7] = new Ship(0, 0, false, false);
        battleField[2][8] = new Ship(0, 0, false, false);
        battleField[2][9] = new Ship(0, 0, false, false);

        battleField[3][0] = new Ship(0, 0, false, false);
        battleField[3][1] = new Ship(0, 0, false, false);
        battleField[3][2] = new Ship(0, 0, false, false);
        battleField[3][3] = new Ship(0, 0, false, false);
        battleField[3][4] = new Ship(0, 0, false, false);
        battleField[3][5] = new Ship(0, 0, false, false);
        battleField[3][6] = new Ship(0, 0, false, false);
        battleField[3][7] = new Ship(0, 0, false, false);
        battleField[3][8] = new Ship(0, 0, false, false);
        battleField[3][9] = new Ship(0, 0, false, false);

        battleField[4][0] = new Ship(0, 0, false, false);
        battleField[4][1] = new Ship(0, 0, false, false);
        battleField[4][2] = new Ship(0, 0, false, false);
        battleField[4][3] = new Ship(0, 0, false, false);
        battleField[4][4] = new Ship(0, 0, false, false);
        battleField[4][5] = new Ship(0, 0, false, false);
        battleField[4][6] = new Ship(0, 0, false, false);
        battleField[4][7] = new Ship(0, 0, false, false);
        battleField[4][8] = new Ship(0, 0, false, false);
        battleField[4][9] = new Ship(0, 0, false, false);

        battleField[5][0] = new Ship(0, 0, false, false);
        battleField[5][1] = new Ship(0, 0, false, false);
        battleField[5][2] = new Ship(0, 0, false, false);
        battleField[5][3] = new Ship(0, 0, false, false);
        battleField[5][4] = new Ship(0, 0, false, false);
        battleField[5][5] = new Ship(0, 0, false, false);
        battleField[5][6] = new Ship(0, 0, false, false);
        battleField[5][7] = new Ship(0, 0, false, false);
        battleField[5][8] = new Ship(0, 0, false, false);
        battleField[5][9] = new Ship(0, 0, false, false);

        battleField[6][0] = new Ship(0, 0, false, false);
        battleField[6][1] = new Ship(0, 0, false, false);
        battleField[6][2] = new Ship(0, 0, false, false);
        battleField[6][3] = new Ship(0, 0, false, false);
        battleField[6][4] = new Ship(0, 0, false, false);
        battleField[6][5] = new Ship(0, 0, false, false);
        battleField[6][6] = new Ship(0, 0, false, false);
        battleField[6][7] = new Ship(0, 0, false, false);
        battleField[6][8] = new Ship(0, 0, false, false);
        battleField[6][9] = new Ship(0, 0, false, false);

        battleField[7][0] = new Ship(0, 0, false, false);
        battleField[7][1] = new Ship(0, 0, false, false);
        battleField[7][2] = new Ship(0, 0, false, false);
        battleField[7][3] = new Ship(0, 0, false, false);
        battleField[7][4] = new Ship(0, 0, false, false);
        battleField[7][5] = new Ship(0, 0, false, false);
        battleField[7][6] = new Ship(0, 0, false, false);
        battleField[7][7] = new Ship(0, 0, false, false);
        battleField[7][8] = new Ship(0, 0, false, false);
        battleField[7][9] = new Ship(0, 0, false, false);

        battleField[8][0] = new Ship(0, 0, false, false);
        battleField[8][1] = new Ship(0, 0, false, false);
        battleField[8][2] = new Ship(0, 0, false, false);
        battleField[8][3] = new Ship(0, 0, false, false);
        battleField[8][4] = new Ship(0, 0, false, false);
        battleField[8][5] = new Ship(0, 0, false, false);
        battleField[8][6] = new Ship(0, 0, false, false);
        battleField[8][7] = new Ship(0, 0, false, false);
        battleField[8][8] = new Ship(0, 0, false, false);
        battleField[8][9] = new Ship(0, 0, false, false);

        battleField[9][0] = new Ship(0, 0, false, false);
        battleField[9][1] = new Ship(0, 0, false, false);
        battleField[9][2] = new Ship(0, 0, false, false);
        battleField[9][3] = new Ship(0, 0, false, false);
        battleField[9][4] = new Ship(0, 0, false, false);
        battleField[9][5] = new Ship(0, 0, false, false);
        battleField[9][6] = new Ship(0, 0, false, false);
        battleField[9][7] = new Ship(0, 0, false, false);
        battleField[9][8] = new Ship(0, 0, false, false);
        battleField[9][9] = new Ship(0, 0, false, false);

    }

    public Ship getBattleField(int i, int j) {
        return battleField[i][j];
    }

    void createFleet() {
        putShipWithFourCells(4, 1, true);
        putShipWithThreeCells(3,1,true);
        putShipWithThreeCells(3,2,true);
        putShipWithTwoCells(2,1,true);
        putShipWithTwoCells(2,2,true);
        putShipWithTwoCells(2,3,true);
        putShipWithOneCell(1,1,true);
        putShipWithOneCell(1,2,true);
        putShipWithOneCell(1,3,true);
        putShipWithOneCell(1,4,true);
    }

    private void putShipWithOneCell(int numberOfMasts,int shipNumber, boolean isShip) {
        Random random = new Random();
        int position = random.nextInt(999);
        int i = position / 100;
        int j = position % 10;
        if (i == 0 && j == 0) {
            if ((!battleField[i][j].isShip()) &&
                    (!battleField[i][j + 1].isShip()) &&
                    (!battleField[i + 1][j].isShip()) &&
                    (!battleField[i + 1][j + 1].isShip())) {
                putShipWithOneCellsNow(numberOfMasts,shipNumber,isShip,i, j);
            } else {
                putShipWithOneCell( numberOfMasts, shipNumber, isShip);
            }
        } else if ((i > 0) && (i < 9) && j == 0) {
            if ((!battleField[i][j].isShip()) &&
                    (!battleField[i][j + 1].isShip()) &&
                    (!battleField[i + 1][j].isShip()) &&
                    (!battleField[i + 1][j + 1].isShip()) &&
                    (!battleField[i - 1][j].isShip()) &&
                    (!battleField[i - 1][j + 1].isShip())) {
                putShipWithOneCellsNow(numberOfMasts,shipNumber,isShip,i, j);
            } else {
                putShipWithOneCell(numberOfMasts, shipNumber, isShip);
            }
        } else if (i == 9 && j == 0) {
            if ((!battleField[i][j].isShip()) &&
                    (!battleField[i][j + 1].isShip()) &&
                    (!battleField[i - 1][j].isShip()) &&
                    (!battleField[i - 1][j + 1].isShip())) {
                putShipWithOneCellsNow(numberOfMasts,shipNumber,isShip,i, j);
            } else {
                putShipWithOneCell(numberOfMasts, shipNumber, isShip);
            }

        } else if (i == 0 && ((j > 0) && (j < 9))) {
            if ((!battleField[i][j - 1].isShip()) &&
                    (!battleField[i][j].isShip()) &&
                    (!battleField[i][j + 1].isShip()) &&
                    (!battleField[i + 1][j - 1].isShip()) &&
                    (!battleField[i + 1][j].isShip()) &&
                    (!battleField[i + 1][j + 1].isShip())) {
                putShipWithOneCellsNow(numberOfMasts,shipNumber,isShip,i, j);
            } else {
                putShipWithOneCell(numberOfMasts, shipNumber, isShip);
            }

        } else if (((i > 0) && (i < 9)) && ((j > 0) && (j < 9))) {
            if ((!battleField[i][j - 1].isShip()) &&
                    (!battleField[i][j].isShip()) &&
                    (!battleField[i][j + 1].isShip()) &&
                    (!battleField[i + 1][j - 1].isShip()) &&
                    (!battleField[i + 1][j].isShip()) &&
                    (!battleField[i + 1][j + 1].isShip()) &&
                    (!battleField[i - 1][j - 1].isShip()) &&
                    (!battleField[i - 1][j].isShip()) &&
                    (!battleField[i - 1][j + 1].isShip())) {
                putShipWithOneCellsNow(numberOfMasts,shipNumber,isShip,i, j);
            } else {
                putShipWithOneCell(numberOfMasts, shipNumber, isShip);
            }

        } else if (i == 9 && ((j > 0) && (j < 9))) {
            if ((!battleField[i][j - 1].isShip()) &&
                    (!battleField[i][j].isShip()) &&
                    (!battleField[i][j + 1].isShip()) &&
                    (!battleField[i - 1][j - 1].isShip()) &&
                    (!battleField[i - 1][j].isShip()) &&
                    (!battleField[i - 1][j + 1].isShip())) {
                putShipWithOneCellsNow(numberOfMasts,shipNumber,isShip,i, j);
            } else {
                putShipWithOneCell(numberOfMasts, shipNumber, isShip);
            }

        } else if (i == 0 && j == 9) {
            if ((!battleField[i][j - 1].isShip()) &&
                    (!battleField[i][j].isShip()) &&
                    (!battleField[i + 1][j - 1].isShip()) &&
                    (!battleField[i + 1][j].isShip())) {
                putShipWithOneCellsNow(numberOfMasts,shipNumber,isShip,i, j);
            } else {
                putShipWithOneCell(numberOfMasts, shipNumber, isShip);
            }

        } else if (((i > 0) && (i < 9)) && (j == 9)) {
            if ((!battleField[i][j - 1].isShip()) &&
                    (!battleField[i][j].isShip()) &&
                    (!battleField[i + 1][j - 1].isShip()) &&
                    (!battleField[i + 1][j].isShip()) &&
                    (!battleField[i - 1][j - 1].isShip()) &&
                    (!battleField[i - 1][j].isShip())) {
                putShipWithOneCellsNow(numberOfMasts,shipNumber,isShip,i, j);
            } else {
                putShipWithOneCell(numberOfMasts, shipNumber, isShip);
            }

        } else if ((i == 9) && (j == 9)) {
            if ((!battleField[i][j - 1].isShip()) &&
                    (!battleField[i][j].isShip()) &&
                    (!battleField[i][j + 1].isShip()) &&
                    (!battleField[i - 1][j - 1].isShip()) &&
                    (!battleField[i - 1][j].isShip()) &&
                    (!battleField[i - 1][j + 1].isShip())) {
                putShipWithOneCellsNow(numberOfMasts,shipNumber,isShip,i, j);
            } else {
                putShipWithOneCell(numberOfMasts, shipNumber, isShip);
            }
        } else putShipWithOneCell(numberOfMasts, shipNumber, isShip);

    }

    private void putShipWithOneCellsNow(int numberOfMasts, int shipNumber, boolean isShip, int i, int j) {
        putShipOnBattleFieldSpace(numberOfMasts,shipNumber,isShip,i,j);
    }

    private void putShipWithTwoCells(int numberOfMasts, int shipNumber, boolean isShip) {
        position = choosePosition();
        if (position) {
            putShipWithTwoCellHorizontally( numberOfMasts, shipNumber, isShip);
        } else {
            putShipWithTwoCellsVertically(numberOfMasts, shipNumber, isShip);
        }
    }

    private void putShipWithTwoCellsVertically(int numberOfMasts, int shipNumber, boolean isShip) {
        Random random = new Random();
        int position = random.nextInt(999);
        int i = position / 100;
        int j = position % 10;
        if (i == 0 && j == 0) {
            if (!battleField[i][j].isShip() &&
                    !battleField[i + 1][j].isShip() &&
                    !battleField[i + 2][j].isShip() &&
                    !battleField[i][j + 1].isShip() &&
                    !battleField[i + 1][j + 1].isShip() &&
                    !battleField[i + 2][j + 1].isShip()) {
                putShipWithTwoCellsVerticallyNow(numberOfMasts, shipNumber, isShip,i, j);
            } else {
                putShipWithTwoCellsVertically(numberOfMasts, shipNumber, isShip);
            }
        } else if (i > 0 && i < 8 && j == 0) {
            if (!battleField[i - 1][j].isShip() &&
                    !battleField[i][j].isShip() &&
                    !battleField[i + 1][j].isShip() &&
                    !battleField[i + 2][j].isShip() &&
                    !battleField[i - 1][j + 1].isShip() &&
                    !battleField[i][j + 1].isShip() &&
                    !battleField[i + 1][j + 1].isShip() &&
                    !battleField[i + 2][j + 1].isShip()) {
                putShipWithTwoCellsVerticallyNow(numberOfMasts, shipNumber, isShip,i, j);
            } else {
                putShipWithTwoCellsVertically(numberOfMasts, shipNumber, isShip);
            }
        } else if (i == 8 && j == 0) {
            if (!battleField[i - 1][j].isShip() &&
                    !battleField[i][j].isShip() &&
                    !battleField[i + 1][j].isShip() &&
                    !battleField[i - 1][j + 1].isShip() &&
                    !battleField[i][j + 1].isShip() &&
                    !battleField[i + 1][j + 1].isShip()) {
                putShipWithTwoCellsVerticallyNow(numberOfMasts, shipNumber, isShip,i, j);
            } else {
                putShipWithTwoCellsVertically(numberOfMasts, shipNumber, isShip);
            }
        } else if (i == 0 && j > 0 && j < 9) {
            if (!battleField[i][j].isShip() &&
                    !battleField[i + 1][j].isShip() &&
                    !battleField[i + 2][j].isShip() &&
                    !battleField[i][j - 1].isShip() &&
                    !battleField[i + 1][j - 1].isShip() &&
                    !battleField[i + 2][j - 1].isShip() &&
                    !battleField[i][j + 1].isShip() &&
                    !battleField[i + 1][j + 1].isShip() &&
                    !battleField[i + 2][j + 1].isShip()) {
                putShipWithTwoCellsVerticallyNow(numberOfMasts, shipNumber, isShip,i, j);
            } else {
                putShipWithTwoCellsVertically(numberOfMasts, shipNumber, isShip);
            }
        } else if (i > 0 && i < 8 && j > 0 && j < 9) {
            if (!battleField[i - 1][j].isShip() &&
                    !battleField[i][j].isShip() &&
                    !battleField[i + 1][j].isShip() &&
                    !battleField[i + 2][j].isShip() &&
                    !battleField[i - 1][j - 1].isShip() &&
                    !battleField[i][j - 1].isShip() &&
                    !battleField[i + 1][j - 1].isShip() &&
                    !battleField[i + 2][j - 1].isShip() &&
                    !battleField[i - 1][j + 1].isShip() &&
                    !battleField[i][j + 1].isShip() &&
                    !battleField[i + 1][j + 1].isShip() &&
                    !battleField[i + 2][j + 1].isShip()) {
                putShipWithTwoCellsVerticallyNow(numberOfMasts, shipNumber, isShip,i, j);
            } else {
                putShipWithTwoCellsVertically(numberOfMasts, shipNumber, isShip);
            }
        } else if (i == 8 && j > 0 && j < 9) {
            if (!battleField[i - 1][j].isShip() &&
                    !battleField[i][j].isShip() &&
                    !battleField[i + 1][j].isShip() &&
                    !battleField[i - 1][j - 1].isShip() &&
                    !battleField[i][j - 1].isShip() &&
                    !battleField[i + 1][j - 1].isShip() &&
                    !battleField[i - 1][j + 1].isShip() &&
                    !battleField[i][j + 1].isShip() &&
                    !battleField[i + 1][j + 1].isShip()) {
                putShipWithTwoCellsVerticallyNow(numberOfMasts, shipNumber, isShip,i, j);
            } else {
                putShipWithTwoCellsVertically(numberOfMasts, shipNumber, isShip);
            }
        } else if (i == 0 && j == 9) {
            if (!battleField[i][j].isShip() &&
                    !battleField[i + 1][j].isShip() &&
                    !battleField[i + 2][j].isShip() &&
                    !battleField[i][j - 1].isShip() &&
                    !battleField[i + 1][j - 1].isShip() &&
                    !battleField[i + 2][j - 1].isShip()) {
                putShipWithTwoCellsVerticallyNow(numberOfMasts, shipNumber, isShip,i, j);
            } else {
                putShipWithTwoCellsVertically(numberOfMasts, shipNumber, isShip);
            }
        } else if (i > 0 && i < 8 && j == 9) {
            if (!battleField[i - 1][j].isShip() &&
                    !battleField[i][j].isShip() &&
                    !battleField[i + 1][j].isShip() &&
                    !battleField[i + 2][j].isShip() &&
                    !battleField[i - 1][j - 1].isShip() &&
                    !battleField[i][j - 1].isShip() &&
                    !battleField[i + 1][j - 1].isShip() &&
                    !battleField[i + 2][j - 1].isShip()) {
                putShipWithTwoCellsVerticallyNow(numberOfMasts, shipNumber, isShip,i, j);
            } else {
                putShipWithTwoCellsVertically(numberOfMasts, shipNumber, isShip);
            }
        } else if (i == 8 && j == 9) {
            if (!battleField[i - 1][j].isShip() &&
                    !battleField[i][j].isShip() &&
                    !battleField[i + 1][j].isShip() &&
                    !battleField[i - 1][j - 1].isShip() &&
                    !battleField[i][j - 1].isShip() &&
                    !battleField[i + 1][j - 1].isShip()) {
                putShipWithTwoCellsVerticallyNow(numberOfMasts, shipNumber, isShip,i, j);
            } else {
                putShipWithTwoCellsVertically(numberOfMasts, shipNumber, isShip);
            }
        } else putShipWithTwoCellsVertically(numberOfMasts, shipNumber, isShip);
    }

    private void putShipWithTwoCellsVerticallyNow(int numberOfMasts, int shipNumber, boolean isShip, int i, int j) {
        putShipOnBattleFieldSpace(numberOfMasts,shipNumber,isShip,i,j);
        putShipOnBattleFieldSpace(numberOfMasts,shipNumber,isShip,i+1,j);
    }

    private void putShipWithTwoCellHorizontally(int numberOfMasts, int shipNumber, boolean isShip) {
        Random random = new Random();
        int position = random.nextInt(999);
        int i = position / 100;
        int j = position % 10;
        if (i == 0 && j == 0) {
            if ((!battleField[i][j].isShip()) &&
                    (!battleField[i][j + 1].isShip()) &&
                    (!battleField[i][j + 2].isShip()) &&
                    (!battleField[i + 1][j].isShip()) &&
                    (!battleField[i + 1][j + 1].isShip()) &&
                    (!battleField[i + 1][j + 2].isShip())) {
                putShipWithTwoCellsHorizontallyNow(numberOfMasts, shipNumber, isShip,i, j);
            } else {
                putShipWithTwoCellHorizontally(numberOfMasts, shipNumber, isShip);
            }
        } else if ((i > 0) && (i < 9) && j == 0) {
            if ((!battleField[i][j].isShip()) &&
                    (!battleField[i][j + 1].isShip()) &&
                    (!battleField[i][j + 2].isShip()) &&
                    (!battleField[i + 1][j].isShip()) &&
                    (!battleField[i + 1][j + 1].isShip()) &&
                    (!battleField[i + 1][j + 2].isShip()) &&
                    (!battleField[i - 1][j].isShip()) &&
                    (!battleField[i - 1][j + 1].isShip()) &&
                    (!battleField[i - 1][j + 2].isShip())) {
                putShipWithTwoCellsHorizontallyNow(numberOfMasts, shipNumber, isShip,i, j);
            } else {
                putShipWithTwoCellHorizontally(numberOfMasts, shipNumber, isShip);
            }
        } else if (i == 9 && j == 0) {
            if ((!battleField[i][j].isShip()) &&
                    (!battleField[i][j + 1].isShip()) &&
                    (!battleField[i][j + 2].isShip()) &&
                    (!battleField[i - 1][j].isShip()) &&
                    (!battleField[i - 1][j + 1].isShip()) &&
                    (!battleField[i - 1][j + 2].isShip())) {
                putShipWithTwoCellsHorizontallyNow(numberOfMasts, shipNumber, isShip,i, j);
            } else {
                putShipWithTwoCellHorizontally(numberOfMasts, shipNumber, isShip);
            }

        } else if (i == 0 && ((j > 0) && (j < 8))) {
            if ((!battleField[i][j - 1].isShip()) &&
                    (!battleField[i][j].isShip()) &&
                    (!battleField[i][j + 1].isShip()) &&
                    (!battleField[i][j + 2].isShip()) &&
                    (!battleField[i + 1][j - 1].isShip()) &&
                    (!battleField[i + 1][j].isShip()) &&
                    (!battleField[i + 1][j + 1].isShip()) &&
                    (!battleField[i + 1][j + 2].isShip())) {
                putShipWithTwoCellsHorizontallyNow(numberOfMasts, shipNumber, isShip,i, j);
            } else {
                putShipWithTwoCellHorizontally(numberOfMasts, shipNumber, isShip);
            }

        } else if (((i > 0) && (i < 9)) && ((j > 0) && (j < 8))) {
            if ((!battleField[i][j - 1].isShip()) &&
                    (!battleField[i][j].isShip()) &&
                    (!battleField[i][j + 1].isShip()) &&
                    (!battleField[i][j + 2].isShip()) &&
                    (!battleField[i + 1][j - 1].isShip()) &&
                    (!battleField[i + 1][j].isShip()) &&
                    (!battleField[i + 1][j + 1].isShip()) &&
                    (!battleField[i + 1][j + 2].isShip()) &&
                    (!battleField[i - 1][j - 1].isShip()) &&
                    (!battleField[i - 1][j].isShip()) &&
                    (!battleField[i - 1][j + 1].isShip()) &&
                    (!battleField[i - 1][j + 2].isShip())) {
                putShipWithTwoCellsHorizontallyNow(numberOfMasts, shipNumber, isShip,i, j);
            } else {
                putShipWithTwoCellHorizontally(numberOfMasts, shipNumber, isShip);
            }

        } else if (i == 9 && ((j > 0) && (j < 8))) {
            if ((!battleField[i][j - 1].isShip()) &&
                    (!battleField[i][j].isShip()) &&
                    (!battleField[i][j + 1].isShip()) &&
                    (!battleField[i][j + 2].isShip()) &&
                    (!battleField[i - 1][j - 1].isShip()) &&
                    (!battleField[i - 1][j].isShip()) &&
                    (!battleField[i - 1][j + 1].isShip()) &&
                    (!battleField[i - 1][j + 2].isShip())) {
                putShipWithTwoCellsHorizontallyNow(numberOfMasts, shipNumber, isShip,i, j);
            } else {
                putShipWithTwoCellHorizontally(numberOfMasts, shipNumber, isShip);
            }

        } else if (i == 0 && j == 8) {
            if ((!battleField[i][j - 1].isShip()) &&
                    (!battleField[i][j].isShip()) &&
                    (!battleField[i][j + 1].isShip()) &&
                    (!battleField[i + 1][j - 1].isShip()) &&
                    (!battleField[i + 1][j].isShip()) &&
                    (!battleField[i + 1][j + 1].isShip())) {
                putShipWithTwoCellsHorizontallyNow(numberOfMasts, shipNumber, isShip,i, j);
            } else {
                putShipWithTwoCellHorizontally(numberOfMasts, shipNumber, isShip);
            }

        } else if (((i > 0) && (i < 9)) && (j == 8)) {
            if ((!battleField[i][j - 1].isShip()) &&
                    (!battleField[i][j].isShip()) &&
                    (!battleField[i][j + 1].isShip()) &&
                    (!battleField[i + 1][j - 1].isShip()) &&
                    (!battleField[i + 1][j].isShip()) &&
                    (!battleField[i + 1][j + 1].isShip()) &&
                    (!battleField[i - 1][j - 1].isShip()) &&
                    (!battleField[i - 1][j].isShip()) &&
                    (!battleField[i - 1][j + 1].isShip())) {
                putShipWithTwoCellsHorizontallyNow(numberOfMasts, shipNumber, isShip,i, j);
            } else {
                putShipWithTwoCellHorizontally(numberOfMasts, shipNumber, isShip);
            }

        } else if ((i == 9) && (j == 8)) {
            if ((!battleField[i][j - 1].isShip()) &&
                    (!battleField[i][j].isShip()) &&
                    (!battleField[i][j + 1].isShip()) &&
                    (!battleField[i - 1][j - 1].isShip()) &&
                    (!battleField[i - 1][j].isShip()) &&
                    (!battleField[i - 1][j + 1].isShip())) {
                putShipWithTwoCellsHorizontallyNow(numberOfMasts, shipNumber, isShip, i, j);
            } else {
                putShipWithTwoCellHorizontally(numberOfMasts, shipNumber, isShip);
            }
        } else putShipWithTwoCellHorizontally(numberOfMasts, shipNumber, isShip);

    }

    private void putShipWithTwoCellsHorizontallyNow(int numberOfMasts, int shipNumber, boolean isShip, int i, int j) {
        putShipOnBattleFieldSpace(numberOfMasts,shipNumber,isShip,i,j);
        putShipOnBattleFieldSpace(numberOfMasts,shipNumber,isShip,i,j+1);
    }

    private void putShipWithThreeCells(int numberOfMasts, int shipNumber, boolean isShip) {
        position = choosePosition();
        if (position) {
            putShipWithThreeCellHorizontally(numberOfMasts,shipNumber,isShip);
        } else {
            putShipWithThreeCellsVertically(numberOfMasts,shipNumber,isShip);
        }
    }

    private void putShipWithThreeCellsVertically(int numberOfMasts, int shipNumber, boolean isShip) {
        Random random = new Random();
        int position = random.nextInt(999);
        int i = position / 100;
        int j = position % 10;
        if (i == 0 && j == 0) {
            if (!battleField[i][j].isShip() &&
                    !battleField[i + 1][j].isShip() &&
                    !battleField[i + 2][j].isShip() &&
                    !battleField[i + 3][j].isShip() &&
                    !battleField[i][j + 1].isShip() &&
                    !battleField[i + 1][j + 1].isShip() &&
                    !battleField[i + 2][j + 1].isShip() &&
                    !battleField[i + 3][j + 1].isShip()) {
                putShipWithThreeCellsVerticallyNow(numberOfMasts, shipNumber, isShip,i, j);
            } else {
                putShipWithThreeCellsVertically( numberOfMasts, shipNumber, isShip);
            }
        } else if (i > 0 && i < 7 && j == 0) {
            if (!battleField[i - 1][j].isShip() &&
                    !battleField[i][j].isShip() &&
                    !battleField[i + 1][j].isShip() &&
                    !battleField[i + 2][j].isShip() &&
                    !battleField[i + 3][j].isShip() &&
                    !battleField[i - 1][j + 1].isShip() &&
                    !battleField[i][j + 1].isShip() &&
                    !battleField[i + 1][j + 1].isShip() &&
                    !battleField[i + 2][j + 1].isShip() &&
                    !battleField[i + 3][j + 1].isShip()) {
                putShipWithThreeCellsVerticallyNow(numberOfMasts, shipNumber, isShip,i, j);
            } else {
                putShipWithThreeCellsVertically(numberOfMasts, shipNumber, isShip);
            }
        } else if (i == 7 && j == 0) {
            if (!battleField[i - 1][j].isShip() &&
                    !battleField[i][j].isShip() &&
                    !battleField[i + 1][j].isShip() &&
                    !battleField[i + 2][j].isShip() &&
                    !battleField[i - 1][j + 1].isShip() &&
                    !battleField[i][j + 1].isShip() &&
                    !battleField[i + 1][j + 1].isShip() &&
                    !battleField[i + 2][j + 1].isShip()) {
                putShipWithThreeCellsVerticallyNow(numberOfMasts, shipNumber, isShip,i, j);
            } else {
                putShipWithThreeCellsVertically(numberOfMasts, shipNumber, isShip);
            }
        } else if (i == 0 && j > 0 && j < 9) {
            if (!battleField[i][j].isShip() &&
                    !battleField[i + 1][j].isShip() &&
                    !battleField[i + 2][j].isShip() &&
                    !battleField[i + 3][j].isShip() &&
                    !battleField[i][j - 1].isShip() &&
                    !battleField[i + 1][j - 1].isShip() &&
                    !battleField[i + 2][j - 1].isShip() &&
                    !battleField[i + 3][j - 1].isShip() &&
                    !battleField[i][j + 1].isShip() &&
                    !battleField[i + 1][j + 1].isShip() &&
                    !battleField[i + 2][j + 1].isShip() &&
                    !battleField[i + 3][j + 1].isShip()) {
                putShipWithThreeCellsVerticallyNow(numberOfMasts, shipNumber, isShip,i, j);
            } else {
                putShipWithThreeCellsVertically(numberOfMasts, shipNumber, isShip);
            }
        } else if (i > 0 && i < 7 && j > 0 && j < 9) {
            if (!battleField[i - 1][j].isShip() &&
                    !battleField[i][j].isShip() &&
                    !battleField[i + 1][j].isShip() &&
                    !battleField[i + 2][j].isShip() &&
                    !battleField[i + 3][j].isShip() &&
                    !battleField[i - 1][j - 1].isShip() &&
                    !battleField[i][j - 1].isShip() &&
                    !battleField[i + 1][j - 1].isShip() &&
                    !battleField[i + 2][j - 1].isShip() &&
                    !battleField[i + 3][j - 1].isShip() &&
                    !battleField[i - 1][j + 1].isShip() &&
                    !battleField[i][j + 1].isShip() &&
                    !battleField[i + 1][j + 1].isShip() &&
                    !battleField[i + 2][j + 1].isShip() &&
                    !battleField[i + 3][j + 1].isShip()) {
                putShipWithThreeCellsVerticallyNow(numberOfMasts, shipNumber, isShip,i, j);
            } else {
                putShipWithThreeCellsVertically(numberOfMasts, shipNumber, isShip);
            }
        } else if (i == 7 && j > 0 && j < 9) {
            if (!battleField[i - 1][j].isShip() &&
                    !battleField[i][j].isShip() &&
                    !battleField[i + 1][j].isShip() &&
                    !battleField[i + 2][j].isShip() &&
                    !battleField[i - 1][j - 1].isShip() &&
                    !battleField[i][j - 1].isShip() &&
                    !battleField[i + 1][j - 1].isShip() &&
                    !battleField[i + 2][j - 1].isShip() &&
                    !battleField[i - 1][j + 1].isShip() &&
                    !battleField[i][j + 1].isShip() &&
                    !battleField[i + 1][j + 1].isShip() &&
                    !battleField[i + 2][j + 1].isShip()) {
                putShipWithThreeCellsVerticallyNow(numberOfMasts, shipNumber, isShip,i, j);
            } else {
                putShipWithThreeCellsVertically(numberOfMasts, shipNumber, isShip);
            }
        } else if (i == 0 && j == 9) {
            if (!battleField[i][j].isShip() &&
                    !battleField[i + 1][j].isShip() &&
                    !battleField[i + 2][j].isShip() &&
                    !battleField[i + 3][j].isShip() &&
                    !battleField[i][j - 1].isShip() &&
                    !battleField[i + 1][j - 1].isShip() &&
                    !battleField[i + 2][j - 1].isShip() &&
                    !battleField[i + 3][j - 1].isShip()) {
                putShipWithThreeCellsVerticallyNow(numberOfMasts, shipNumber, isShip,i, j);
            } else {
                putShipWithThreeCellsVertically(numberOfMasts, shipNumber, isShip);
            }
        } else if (i > 0 && i < 7 && j == 9) {
            if (!battleField[i - 1][j].isShip() &&
                    !battleField[i][j].isShip() &&
                    !battleField[i + 1][j].isShip() &&
                    !battleField[i + 2][j].isShip() &&
                    !battleField[i + 3][j].isShip() &&
                    !battleField[i - 1][j - 1].isShip() &&
                    !battleField[i][j - 1].isShip() &&
                    !battleField[i + 1][j - 1].isShip() &&
                    !battleField[i + 2][j - 1].isShip() &&
                    !battleField[i + 3][j - 1].isShip()) {
                putShipWithThreeCellsVerticallyNow(numberOfMasts, shipNumber, isShip,i, j);
            } else {
                putShipWithThreeCellsVertically(numberOfMasts, shipNumber, isShip);
            }
        } else if (i == 7 && j == 9) {
            if (!battleField[i - 1][j].isShip() &&
                    !battleField[i][j].isShip() &&
                    !battleField[i + 1][j].isShip() &&
                    !battleField[i + 2][j].isShip() &&
                    !battleField[i - 1][j - 1].isShip() &&
                    !battleField[i][j - 1].isShip() &&
                    !battleField[i + 1][j - 1].isShip() &&
                    !battleField[i + 2][j - 1].isShip()) {
                putShipWithThreeCellsVerticallyNow(numberOfMasts, shipNumber, isShip, i, j);
            } else {
                putShipWithThreeCellsVertically(numberOfMasts, shipNumber, isShip);
            }
        } else putShipWithThreeCellsVertically(numberOfMasts, shipNumber, isShip);
    }

    private void putShipWithThreeCellsVerticallyNow(int numberOfMasts, int shipNumber, boolean isShip, int i, int j) {
        putShipOnBattleFieldSpace(numberOfMasts,shipNumber,isShip,i,j);
        putShipOnBattleFieldSpace(numberOfMasts,shipNumber,isShip,i+1,j);
        putShipOnBattleFieldSpace(numberOfMasts,shipNumber,isShip,i+2,j);
    }

    private void putShipWithThreeCellHorizontally(int numberOfMasts, int shipNumber, boolean isShip) {
        Random random = new Random();
        int position = random.nextInt(999);
        int i = position / 100;
        int j = position % 10;
        if (i == 0 && j == 0) {
            if ((!battleField[i][j].isShip()) &&
                    (!battleField[i][j + 1].isShip()) &&
                    (!battleField[i][j + 2].isShip()) &&
                    (!battleField[i][j + 3].isShip()) &&
                    (!battleField[i + 1][j].isShip()) &&
                    (!battleField[i + 1][j + 1].isShip()) &&
                    (!battleField[i + 1][j + 2].isShip()) &&
                    (!battleField[i + 1][j + 3].isShip())) {
                putShipWithThreeCellsHorizontallyNow(numberOfMasts, shipNumber, isShip,i, j);
            } else {
                putShipWithThreeCellHorizontally( numberOfMasts, shipNumber, isShip);
            }
        } else if ((i > 0) && (i < 9) && j == 0) {
            if ((!battleField[i][j].isShip()) &&
                    (!battleField[i][j + 1].isShip()) &&
                    (!battleField[i][j + 2].isShip()) &&
                    (!battleField[i][j + 3].isShip()) &&
                    (!battleField[i + 1][j].isShip()) &&
                    (!battleField[i + 1][j + 1].isShip()) &&
                    (!battleField[i + 1][j + 2].isShip()) &&
                    (!battleField[i + 1][j + 3].isShip()) &&
                    (!battleField[i - 1][j].isShip()) &&
                    (!battleField[i - 1][j + 1].isShip()) &&
                    (!battleField[i - 1][j + 2].isShip()) &&
                    (!battleField[i - 1][j + 3].isShip())) {
                putShipWithThreeCellsHorizontallyNow(numberOfMasts, shipNumber, isShip,i, j);
            } else {
                putShipWithThreeCellHorizontally(numberOfMasts, shipNumber, isShip);
            }
        } else if (i == 9 && j == 0) {
            if ((!battleField[i][j].isShip()) &&
                    (!battleField[i][j + 1].isShip()) &&
                    (!battleField[i][j + 2].isShip()) &&
                    (!battleField[i][j + 3].isShip()) &&
                    (!battleField[i - 1][j].isShip()) &&
                    (!battleField[i - 1][j + 1].isShip()) &&
                    (!battleField[i - 1][j + 2].isShip()) &&
                    (!battleField[i - 1][j + 3].isShip())) {
                putShipWithThreeCellsHorizontallyNow(numberOfMasts, shipNumber, isShip,i, j);
            } else {
                putShipWithThreeCellHorizontally(numberOfMasts, shipNumber, isShip);
            }

        } else if (i == 0 && ((j > 0) && (j < 7))) {
            if ((!battleField[i][j - 1].isShip()) &&
                    (!battleField[i][j].isShip()) &&
                    (!battleField[i][j + 1].isShip()) &&
                    (!battleField[i][j + 2].isShip()) &&
                    (!battleField[i][j + 3].isShip()) &&
                    (!battleField[i + 1][j - 1].isShip()) &&
                    (!battleField[i + 1][j].isShip()) &&
                    (!battleField[i + 1][j + 1].isShip()) &&
                    (!battleField[i + 1][j + 2].isShip()) &&
                    (!battleField[i + 1][j + 3].isShip())) {
                putShipWithThreeCellsHorizontallyNow(numberOfMasts, shipNumber, isShip,i, j);
            } else {
                putShipWithThreeCellHorizontally(numberOfMasts, shipNumber, isShip);
            }

        } else if (((i > 0) && (i < 9)) && ((j > 0) && (j < 7))) {
            if ((!battleField[i][j - 1].isShip()) &&
                    (!battleField[i][j].isShip()) &&
                    (!battleField[i][j + 1].isShip()) &&
                    (!battleField[i][j + 2].isShip()) &&
                    (!battleField[i][j + 3].isShip()) &&
                    (!battleField[i + 1][j - 1].isShip()) &&
                    (!battleField[i + 1][j].isShip()) &&
                    (!battleField[i + 1][j + 1].isShip()) &&
                    (!battleField[i + 1][j + 2].isShip()) &&
                    (!battleField[i + 1][j + 3].isShip()) &&
                    (!battleField[i - 1][j - 1].isShip()) &&
                    (!battleField[i - 1][j].isShip()) &&
                    (!battleField[i - 1][j + 1].isShip()) &&
                    (!battleField[i - 1][j + 2].isShip()) &&
                    (!battleField[i - 1][j + 3].isShip())) {
                putShipWithThreeCellsHorizontallyNow(numberOfMasts, shipNumber, isShip,i, j);
            } else {
                putShipWithThreeCellHorizontally(numberOfMasts, shipNumber, isShip);
            }

        } else if (i == 9 && ((j > 0) && (j < 7))) {
            if ((!battleField[i][j - 1].isShip()) &&
                    (!battleField[i][j].isShip()) &&
                    (!battleField[i][j + 1].isShip()) &&
                    (!battleField[i][j + 2].isShip()) &&
                    (!battleField[i][j + 3].isShip()) &&
                    (!battleField[i - 1][j - 1].isShip()) &&
                    (!battleField[i - 1][j].isShip()) &&
                    (!battleField[i - 1][j + 1].isShip()) &&
                    (!battleField[i - 1][j + 2].isShip()) &&
                    (!battleField[i - 1][j + 3].isShip())) {
                putShipWithThreeCellsHorizontallyNow(numberOfMasts, shipNumber, isShip,i, j);
            } else {
                putShipWithThreeCellHorizontally(numberOfMasts, shipNumber, isShip);
            }

        } else if (i == 0 && j == 7) {
            if ((!battleField[i][j - 1].isShip()) &&
                    (!battleField[i][j].isShip()) &&
                    (!battleField[i][j + 1].isShip()) &&
                    (!battleField[i][j + 2].isShip()) &&
                    (!battleField[i + 1][j - 1].isShip()) &&
                    (!battleField[i + 1][j].isShip()) &&
                    (!battleField[i + 1][j + 1].isShip()) &&
                    (!battleField[i + 1][j + 2].isShip())) {
                putShipWithThreeCellsHorizontallyNow(numberOfMasts, shipNumber, isShip,i, j);
            } else {
                putShipWithThreeCellHorizontally(numberOfMasts, shipNumber, isShip);
            }

        } else if (((i > 0) && (i < 9)) && (j == 7)) {
            if ((!battleField[i][j - 1].isShip()) &&
                    (!battleField[i][j].isShip()) &&
                    (!battleField[i][j + 1].isShip()) &&
                    (!battleField[i][j + 2].isShip()) &&
                    (!battleField[i + 1][j - 1].isShip()) &&
                    (!battleField[i + 1][j].isShip()) &&
                    (!battleField[i + 1][j + 1].isShip()) &&
                    (!battleField[i + 1][j + 2].isShip()) &&
                    (!battleField[i - 1][j - 1].isShip()) &&
                    (!battleField[i - 1][j].isShip()) &&
                    (!battleField[i - 1][j + 1].isShip()) &&
                    (!battleField[i - 1][j + 2].isShip())) {
                putShipWithThreeCellsHorizontallyNow(numberOfMasts, shipNumber, isShip,i, j);
            } else {
                putShipWithThreeCellHorizontally(numberOfMasts, shipNumber, isShip);
            }

        } else if ((i == 9) && (j == 7)) {
            if ((!battleField[i][j - 1].isShip()) &&
                    (!battleField[i][j].isShip()) &&
                    (!battleField[i][j + 1].isShip()) &&
                    (!battleField[i][j + 2].isShip()) &&
                    (!battleField[i - 1][j - 1].isShip()) &&
                    (!battleField[i - 1][j].isShip()) &&
                    (!battleField[i - 1][j + 1].isShip()) &&
                    (!battleField[i - 1][j + 2].isShip())) {
                putShipWithThreeCellsHorizontallyNow(numberOfMasts, shipNumber, isShip,i, j);
            } else {
                putShipWithThreeCellHorizontally(numberOfMasts, shipNumber, isShip);
            }
        } else putShipWithThreeCellHorizontally(numberOfMasts, shipNumber, isShip);
    }

    private void putShipWithFourCells(int numberOfMasts, int shipNumber, boolean isShip) {
        position = choosePosition();
        if (position) {
            putShipWithFourCellsHorizontally(numberOfMasts, shipNumber, isShip);
        } else {
            putShipWithFourCellsVertically(numberOfMasts, shipNumber, isShip);
        }
    }

    private void putShipWithFourCellsVertically(int numberOfMasts, int shipNumber, boolean isShip) {
        Random random = new Random();
        int position = random.nextInt(999);
        int i = position / 100;
        int j = position % 10;
        if (i < 7) {
            putShipOnBattleFieldSpace(numberOfMasts,shipNumber,isShip,i,j);
            putShipOnBattleFieldSpace(numberOfMasts,shipNumber,isShip,i+1,j);
            putShipOnBattleFieldSpace(numberOfMasts,shipNumber,isShip,i+2,j);
            putShipOnBattleFieldSpace(numberOfMasts,shipNumber,isShip,i+3,j);
        } else {
            putShipWithFourCellsVertically(numberOfMasts, shipNumber, isShip);
        }
    }

    private boolean choosePosition() {
        Random random = new Random();
        return random.nextInt(1000) % 2 == 0;
    }

    private void putShipWithFourCellsHorizontally(int numberOfMasts, int shipNumber, boolean isShip) {
        Random random = new Random();
        int position = random.nextInt(999);
        int i = position / 100;
        int j = position % 10;
        if (j < 7) {
            putShipOnBattleFieldSpace(numberOfMasts,shipNumber,isShip,i,j);
            putShipOnBattleFieldSpace(numberOfMasts,shipNumber,isShip,i,j+1);
            putShipOnBattleFieldSpace(numberOfMasts,shipNumber,isShip,i,j+2);
            putShipOnBattleFieldSpace(numberOfMasts,shipNumber,isShip,i,j+3);

        } else {
            putShipWithFourCellsHorizontally(numberOfMasts, shipNumber, isShip);
        }
    }

    private void putShipWithThreeCellsHorizontallyNow(int numberOfMasts, int shipNumber, boolean isShip, int i, int j) {
        putShipOnBattleFieldSpace(numberOfMasts,shipNumber,isShip,i,j);
        putShipOnBattleFieldSpace(numberOfMasts,shipNumber,isShip,i,j+1);
        putShipOnBattleFieldSpace(numberOfMasts,shipNumber,isShip,i,j+2);


    }

    public void putShipOnBattleFieldSpace(int numberOfMasts, int shipNumber, boolean isShip, int i,int j){
        battleField[i][j].setNumberOfMasts(numberOfMasts);
        battleField[i][j].setShipNumber(shipNumber);
        battleField[i][j].setShip(isShip);
    }

    public void storeBattleField(){
        BattleFieldPlayerOneSingleton.getInstance().storeBattleField(battleField);
    }
    void readFromSingleton(){
        for(int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                battleField[i][j]=BattleFieldPlayerOneSingleton.getInstance().battleField[i][j];
            }
        }
    }

    void eraseBattleField(){
        for (int i = 0; i<10; i++){
            for (int j = 0; i<10; i++){

            }
        }
    }
}



