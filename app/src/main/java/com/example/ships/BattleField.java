package com.example.ships;

import java.util.Random;

public class BattleField {
    Ship[][]battleField = new Ship[10][10];

    boolean position;

    public Ship getBattleField(int i, int j) {
        return battleField[i][j];
    }
    
    public void storeBattleField(){
        BattleFieldPlayerOneSingleton.getInstance().storeBattleField(battleField);
    }

    public BattleField() {
        for (int i=0;i<10;i++){
            for(int j=0;j<10;j++){

            }
        }
    }

    void readFromSingleton(){
        for(int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                battleField[i][j]=BattleFieldPlayerOneSingleton.getInstance().battleFieldPlayerOneArray[i][j];
            }
        }
    }

    void createFleet(){
        putShipWithFourCells();
        putShipWithThreeCells();
        putShipWithThreeCells();
        putShipWithTwoCells();
        putShipWithTwoCells();
        putShipWithTwoCells();
        putShipWithOneCell();
        putShipWithOneCell();
        putShipWithOneCell();
        putShipWithOneCell();
    }

    private void putShipWithOneCell() {
        Random random = new Random();
        int position = random.nextInt(999);
        int i = position/100;
        int j = position%10;
        if(i==0&&j==0){
            if((!battleField[i][j].isShip)&&
                    (!battleField[i][j + 1].isShip)&&
                    (!battleField[i + 1][j].isShip)&&
                    (!battleField[i + 1][j + 1].isShip))
            {
                putShipWithOneCellsNow(i,j);
            }
            else{
                putShipWithOneCell();
            }
        }
        else if ((i>0)&&(i<9)&&j==0){
            if((!battleField[i][j].isShip)&&
                    (!battleField[i][j + 1].isShip)&&
                    (!battleField[i + 1][j].isShip)&&
                    (!battleField[i + 1][j + 1].isShip)&&
                    (!battleField[i - 1][j].isShip)&&
                    (!battleField[i - 1][j + 1].isShip))
            {
                putShipWithOneCellsNow(i,j);
            }
            else{
                putShipWithOneCell();
            }
        }
        else if (i==9&&j==0){
            if((!battleField[i][j].isShip)&&
                    (!battleField[i][j + 1].isShip)&&
                    (!battleField[i - 1][j].isShip)&&
                    (!battleField[i - 1][j + 1].isShip))
            {
                putShipWithOneCellsNow(i,j);
            }
            else{
                putShipWithOneCell();
            }

        }
        else if (i==0&&((j>0)&&(j<9))){
            if((!battleField[i][j-1].isShip)&&
                    (!battleField[i][j].isShip)&&
                    (!battleField[i][j + 1].isShip)&&
                    (!battleField[i + 1][j-1].isShip)&&
                    (!battleField[i + 1][j].isShip)&&
                    (!battleField[i + 1][j + 1].isShip))
            {
                putShipWithOneCellsNow(i,j);
            }
            else{
                putShipWithOneCell();
            }

        }
        else if (((i>0)&&(i<9))&&((j>0)&&(j<9))){
            if((!battleField[i][j-1].isShip)&&
                    (!battleField[i][j].isShip)&&
                    (!battleField[i][j + 1].isShip)&&
                    (!battleField[i + 1][j-1].isShip)&&
                    (!battleField[i + 1][j].isShip)&&
                    (!battleField[i + 1][j + 1].isShip)&&
                    (!battleField[i - 1][j-1].isShip)&&
                    (!battleField[i - 1][j].isShip)&&
                    (!battleField[i - 1][j + 1].isShip))
            {
                putShipWithOneCellsNow(i,j);
            }
            else{
                putShipWithOneCell();
            }

        }
        else if (i==9&&((j>0)&&(j<9))){
            if((!battleField[i][j-1].isShip)&&
                    (!battleField[i][j].isShip)&&
                    (!battleField[i][j + 1].isShip)&&
                    (!battleField[i - 1][j-1].isShip)&&
                    (!battleField[i - 1][j].isShip)&&
                    (!battleField[i - 1][j + 1].isShip))
            {
                putShipWithOneCellsNow(i,j);
            }
            else{
                putShipWithOneCell();
            }

        }
        else if (i==0&&j==9){
            if((!battleField[i][j-1].isShip)&&
                    (!battleField[i][j].isShip)&&
                    (!battleField[i + 1][j-1].isShip)&&
                    (!battleField[i + 1][j].isShip))
            {
                putShipWithOneCellsNow(i,j);
            }
            else{
                putShipWithOneCell();
            }

        }
        else if (((i>0)&&(i<9))&&(j==9)){
            if((!battleField[i][j-1].isShip)&&
                    (!battleField[i][j].isShip)&&
                    (!battleField[i + 1][j-1].isShip)&&
                    (!battleField[i + 1][j].isShip)&&
                    (!battleField[i - 1][j-1].isShip)&&
                    (!battleField[i - 1][j].isShip))
            {
                putShipWithOneCellsNow(i,j);
            }
            else{
                putShipWithOneCell();
            }

        }
        else if ((i==9)&&(j==8)){
            if((!battleField[i][j-1].isShip)&&
                    (!battleField[i][j].isShip)&&
                    (!battleField[i][j + 1].isShip)&&
                    (!battleField[i - 1][j-1].isShip)&&
                    (!battleField[i - 1][j].isShip)&&
                    (!battleField[i - 1][j + 1].isShip))
            {
                putShipWithOneCellsNow(i,j);
            }
            else{
                putShipWithOneCell();
            }
        }
        else putShipWithOneCell();

    }

    private void putShipWithOneCellsNow(int i, int j) {

    }


    private void putShipWithTwoCells() {
        position = choosePosition();
        if(position){
            putShipWithTwoCellHorizontally();
        }
        else{
            putShipWithTwoCellsVertically();
        }
    }

    private void putShipWithTwoCellsVertically() {
        Random random = new Random();
        int position = random.nextInt(999);
        int i = position/100;
        int j = position%10;
        if(i==0&&j==0){
            if(!battleField[i][j].isShip&&
                    !battleField[i+1][j].isShip&&
                    !battleField[i+2][j].isShip&&
                    !battleField[i][j+1].isShip&&
                    !battleField[i+1][j+1].isShip&&
                    !battleField[i+2][j+1].isShip){
                putShipWithTwoCellsVerticallyNow(i,j);
            }
            else{
                putShipWithTwoCellsVertically();
            }
        }
        else if(i>0&&i<8&&j==0){
            if(!battleField[i-1][j].isShip&&
                    !battleField[i][j].isShip&&
                    !battleField[i+1][j].isShip&&
                    !battleField[i+2][j].isShip&&
                    !battleField[i-1][j+1].isShip&&
                    !battleField[i][j+1].isShip&&
                    !battleField[i+1][j+1].isShip&&
                    !battleField[i+2][j+1].isShip){
                putShipWithTwoCellsVerticallyNow(i,j);
            }
            else{
                putShipWithTwoCellsVertically();
            }
        }
        else if(i==8&&j==0){
            if(!battleField[i-1][j].isShip&&
                    !battleField[i][j].isShip&&
                    !battleField[i+1][j].isShip&&
                    !battleField[i-1][j+1].isShip&&
                    !battleField[i][j+1].isShip&&
                    !battleField[i+1][j+1].isShip){
                putShipWithTwoCellsVerticallyNow(i,j);
            }
            else{
                putShipWithTwoCellsVertically();
            }
        }
        else if(i==0&&j>0&&j<9){
            if(!battleField[i][j].isShip&&
                    !battleField[i+1][j].isShip&&
                    !battleField[i+2][j].isShip&&
                    !battleField[i][j-1].isShip&&
                    !battleField[i+1][j-1].isShip&&
                    !battleField[i+2][j-1].isShip&&
                    !battleField[i][j+1].isShip&&
                    !battleField[i+1][j+1].isShip&&
                    !battleField[i+2][j+1].isShip){
                putShipWithTwoCellsVerticallyNow(i,j);
            }
            else{
                putShipWithTwoCellsVertically();
            }
        }
        else if(i>0&&i<8&&j>0&&j<9){
            if(!battleField[i-1][j].isShip&&
                    !battleField[i][j].isShip&&
                    !battleField[i+1][j].isShip&&
                    !battleField[i+2][j].isShip&&
                    !battleField[i-1][j-1].isShip&&
                    !battleField[i][j-1].isShip&&
                    !battleField[i+1][j-1].isShip&&
                    !battleField[i+2][j-1].isShip&&
                    !battleField[i-1][j+1].isShip&&
                    !battleField[i][j+1].isShip&&
                    !battleField[i+1][j+1].isShip&&
                    !battleField[i+2][j+1].isShip){
                putShipWithTwoCellsVerticallyNow(i,j);
            }
            else{
                putShipWithTwoCellsVertically();
            }
        }
        else if(i==8&&j>0&&j<9){
            if(!battleField[i-1][j].isShip&&
                    !battleField[i][j].isShip&&
                    !battleField[i+1][j].isShip&&
                    !battleField[i-1][j-1].isShip&&
                    !battleField[i][j-1].isShip&&
                    !battleField[i+1][j-1].isShip&&
                    !battleField[i-1][j+1].isShip&&
                    !battleField[i][j+1].isShip&&
                    !battleField[i+1][j+1].isShip){
                putShipWithTwoCellsVerticallyNow(i,j);
            }
            else{
                putShipWithTwoCellsVertically();
            }
        }
        else if(i==0&&j==9){
            if(!battleField[i][j].isShip&&
                    !battleField[i+1][j].isShip&&
                    !battleField[i+2][j].isShip&&
                    !battleField[i][j-1].isShip&&
                    !battleField[i+1][j-1].isShip&&
                    !battleField[i+2][j-1].isShip){
                putShipWithTwoCellsVerticallyNow(i,j);
            }
            else{
                putShipWithTwoCellsVertically();
            }
        }
        else if(i>0&&i<8&&j==9){
            if(!battleField[i-1][j].isShip&&
                    !battleField[i][j].isShip&&
                    !battleField[i+1][j].isShip&&
                    !battleField[i+2][j].isShip&&
                    !battleField[i-1][j-1].isShip&&
                    !battleField[i][j-1].isShip&&
                    !battleField[i+1][j-1].isShip&&
                    !battleField[i+2][j-1].isShip){
                putShipWithTwoCellsVerticallyNow(i,j);
            }
            else{
                putShipWithTwoCellsVertically();
            }
        }
        else if(i==8&&j==9){
            if(!battleField[i-1][j].isShip&&
                    !battleField[i][j].isShip&&
                    !battleField[i+1][j].isShip&&
                    !battleField[i-1][j-1].isShip&&
                    !battleField[i][j-1].isShip&&
                    !battleField[i+1][j-1].isShip){
                putShipWithTwoCellsVerticallyNow(i,j);
            }
            else{
                putShipWithTwoCellsVertically();
            }
        }
        else putShipWithTwoCellsVertically();
    }

    private void putShipWithTwoCellsVerticallyNow(int i, int j) {

    }


    private void putShipWithTwoCellHorizontally() {
        Random random = new Random();
        int position = random.nextInt(999);
        int i = position/100;
        int j = position%10;
        if(i==0&&j==0){
            if((!battleField[i][j].isShip)&&
                    (!battleField[i][j + 1].isShip)&&
                    (!battleField[i][j + 2].isShip)&&
                    (!battleField[i + 1][j].isShip)&&
                    (!battleField[i + 1][j + 1].isShip)&&
                    (!battleField[i + 1][j + 2].isShip))
            {
                putShipWithTwoCellsHorizontallyNow(i,j);
            }
            else{
                putShipWithTwoCellHorizontally();
            }
        }
        else if ((i>0)&&(i<9)&&j==0){
            if((!battleField[i][j].isShip)&&
                    (!battleField[i][j + 1].isShip)&&
                    (!battleField[i][j + 2].isShip)&&
                    (!battleField[i + 1][j].isShip)&&
                    (!battleField[i + 1][j + 1].isShip)&&
                    (!battleField[i + 1][j + 2].isShip)&&
                    (!battleField[i - 1][j].isShip)&&
                    (!battleField[i - 1][j + 1].isShip)&&
                    (!battleField[i - 1][j + 2].isShip))
            {
                putShipWithTwoCellsHorizontallyNow(i,j);
            }
            else{
                putShipWithTwoCellHorizontally();
            }
        }
        else if (i==9&&j==0){
            if((!battleField[i][j].isShip)&&
                    (!battleField[i][j + 1].isShip)&&
                    (!battleField[i][j + 2].isShip)&&
                    (!battleField[i - 1][j].isShip)&&
                    (!battleField[i - 1][j + 1].isShip)&&
                    (!battleField[i - 1][j + 2].isShip))
            {
                putShipWithTwoCellsHorizontallyNow(i,j);
            }
            else{
                putShipWithTwoCellHorizontally();
            }

        }
        else if (i==0&&((j>0)&&(j<8))){
            if(        (!battleField[i][j-1].isShip)&&
                    (!battleField[i][j].isShip)&&
                    (!battleField[i][j + 1].isShip)&&
                    (!battleField[i][j + 2].isShip)&&
                    (!battleField[i + 1][j-1].isShip)&&
                    (!battleField[i + 1][j].isShip)&&
                    (!battleField[i + 1][j + 1].isShip)&&
                    (!battleField[i + 1][j + 2].isShip))
            {
                putShipWithTwoCellsHorizontallyNow(i,j);
            }
            else{
                putShipWithTwoCellHorizontally();
            }

        }
        else if (((i>0)&&(i<9))&&((j>0)&&(j<8))){
            if((!battleField[i][j-1].isShip)&&
                    (!battleField[i][j].isShip)&&
                    (!battleField[i][j + 1].isShip)&&
                    (!battleField[i][j + 2].isShip)&&
                    (!battleField[i + 1][j-1].isShip)&&
                    (!battleField[i + 1][j].isShip)&&
                    (!battleField[i + 1][j + 1].isShip)&&
                    (!battleField[i + 1][j + 2].isShip)&&
                    (!battleField[i - 1][j-1].isShip)&&
                    (!battleField[i - 1][j].isShip)&&
                    (!battleField[i - 1][j + 1].isShip)&&
                    (!battleField[i - 1][j + 2].isShip))
            {
                putShipWithTwoCellsHorizontallyNow(i,j);
            }
            else{
                putShipWithTwoCellHorizontally();
            }

        }
        else if (i==9&&((j>0)&&(j<8))){
            if((!battleField[i][j-1].isShip)&&
                    (!battleField[i][j].isShip)&&
                    (!battleField[i][j + 1].isShip)&&
                    (!battleField[i][j + 2].isShip)&&
                    (!battleField[i - 1][j-1].isShip)&&
                    (!battleField[i - 1][j].isShip)&&
                    (!battleField[i - 1][j + 1].isShip)&&
                    (!battleField[i - 1][j + 2].isShip))
            {
                putShipWithTwoCellsHorizontallyNow(i,j);
            }
            else{
                putShipWithTwoCellHorizontally();
            }

        }
        else if (i==0&&j==8){
            if((!battleField[i][j-1].isShip)&&
                    (!battleField[i][j].isShip)&&
                    (!battleField[i][j + 1].isShip)&&
                    (!battleField[i + 1][j-1].isShip)&&
                    (!battleField[i + 1][j].isShip)&&
                    (!battleField[i + 1][j + 1].isShip))
            {
                putShipWithTwoCellsHorizontallyNow(i,j);
            }
            else{
                putShipWithTwoCellHorizontally();
            }

        }
        else if (((i>0)&&(i<9))&&(j==8)){
            if((!battleField[i][j-1].isShip)&&
                    (!battleField[i][j].isShip)&&
                    (!battleField[i][j + 1].isShip)&&
                    (!battleField[i + 1][j-1].isShip)&&
                    (!battleField[i + 1][j].isShip)&&
                    (!battleField[i + 1][j + 1].isShip)&&
                    (!battleField[i - 1][j-1].isShip)&&
                    (!battleField[i - 1][j].isShip)&&
                    (!battleField[i - 1][j + 1].isShip))
            {
                putShipWithTwoCellsHorizontallyNow(i,j);
            }
            else{
                putShipWithTwoCellHorizontally();
            }

        }
        else if ((i==9)&&(j==8)){
            if((!battleField[i][j-1].isShip)&&
                    (!battleField[i][j].isShip)&&
                    (!battleField[i][j + 1].isShip)&&
                    (!battleField[i - 1][j-1].isShip)&&
                    (!battleField[i - 1][j].isShip)&&
                    (!battleField[i - 1][j + 1].isShip))
            {
                putShipWithTwoCellsHorizontallyNow(i,j);
            }
            else{
                putShipWithTwoCellHorizontally();
            }
        }
        else putShipWithTwoCellHorizontally();

    }

    private void putShipWithTwoCellsHorizontallyNow(int i, int j) {

    }

    private void putShipWithThreeCells() {
        position = choosePosition();
        if(position){
            putShipWithThreeCellHorizontally();
        }
        else{
            putShipWithThreeCellsVertically();
        }
    }

    private void putShipWithThreeCellsVertically() {
        Random random = new Random();
        int position = random.nextInt(999);
        int i = position/100;
        int j = position%10;
        if(i==0&&j==0){
            if(!battleField[i][j].isShip&&
            !battleField[i+1][j].isShip&&
            !battleField[i+2][j].isShip&&
            !battleField[i+3][j].isShip&&
            !battleField[i][j+1].isShip&&
                    !battleField[i+1][j+1].isShip&&
                    !battleField[i+2][j+1].isShip&&
                    !battleField[i+3][j+1].isShip){
                putShipWithThreeCellsVerticallyNow(i,j);
            }
            else{
                putShipWithThreeCellsVertically();
            }
        }
        else if(i>0&&i<7&&j==0){
            if(!battleField[i-1][j].isShip&&
                    !battleField[i][j].isShip&&
                    !battleField[i+1][j].isShip&&
                    !battleField[i+2][j].isShip&&
                    !battleField[i+3][j].isShip&&
                    !battleField[i-1][j+1].isShip&&
                    !battleField[i][j+1].isShip&&
                    !battleField[i+1][j+1].isShip&&
                    !battleField[i+2][j+1].isShip&&
                    !battleField[i+3][j+1].isShip){
                putShipWithThreeCellsVerticallyNow(i,j);
            }
            else{
                putShipWithThreeCellsVertically();
            }
        }
        else if(i==7&&j==0){
            if(!battleField[i-1][j].isShip&&
                    !battleField[i][j].isShip&&
                    !battleField[i+1][j].isShip&&
                    !battleField[i+2][j].isShip&&
                    !battleField[i-1][j+1].isShip&&
                    !battleField[i][j+1].isShip&&
                    !battleField[i+1][j+1].isShip&&
                    !battleField[i+2][j+1].isShip){
                putShipWithThreeCellsVerticallyNow(i,j);
            }
            else{
                putShipWithThreeCellsVertically();
            }
        }
        else if(i==0&&j>0&&j<9){
            if(!battleField[i][j].isShip&&
                    !battleField[i+1][j].isShip&&
                    !battleField[i+2][j].isShip&&
                    !battleField[i+3][j].isShip&&
                    !battleField[i][j-1].isShip&&
                    !battleField[i+1][j-1].isShip&&
                    !battleField[i+2][j-1].isShip&&
                    !battleField[i+3][j-1].isShip&&
                    !battleField[i][j+1].isShip&&
                    !battleField[i+1][j+1].isShip&&
                    !battleField[i+2][j+1].isShip&&
                    !battleField[i+3][j+1].isShip){
                putShipWithThreeCellsVerticallyNow(i,j);
            }
            else{
                putShipWithThreeCellsVertically();
            }
        }
        else if(i>0&&i<7&&j>0&&j<9){
            if(!battleField[i-1][j].isShip&&
                    !battleField[i][j].isShip&&
                    !battleField[i+1][j].isShip&&
                    !battleField[i+2][j].isShip&&
                    !battleField[i+3][j].isShip&&
                    !battleField[i-1][j-1].isShip&&
                    !battleField[i][j-1].isShip&&
                    !battleField[i+1][j-1].isShip&&
                    !battleField[i+2][j-1].isShip&&
                    !battleField[i+3][j-1].isShip&&
                    !battleField[i-1][j+1].isShip&&
                    !battleField[i][j+1].isShip&&
                    !battleField[i+1][j+1].isShip&&
                    !battleField[i+2][j+1].isShip&&
                    !battleField[i+3][j+1].isShip){
                putShipWithThreeCellsVerticallyNow(i,j);
            }
            else{
                putShipWithThreeCellsVertically();
            }
        }
        else if(i==7&&j>0&&j<9){
            if(!battleField[i-1][j].isShip&&
                    !battleField[i][j].isShip&&
                    !battleField[i+1][j].isShip&&
                    !battleField[i+2][j].isShip&&
                    !battleField[i-1][j-1].isShip&&
                    !battleField[i][j-1].isShip&&
                    !battleField[i+1][j-1].isShip&&
                    !battleField[i+2][j-1].isShip&&
                    !battleField[i-1][j+1].isShip&&
                    !battleField[i][j+1].isShip&&
                    !battleField[i+1][j+1].isShip&&
                    !battleField[i+2][j+1].isShip){
                putShipWithThreeCellsVerticallyNow(i,j);
            }
            else{
                putShipWithThreeCellsVertically();
            }
        }
        else if(i==0&&j==9){
            if(!battleField[i][j].isShip&&
                    !battleField[i+1][j].isShip&&
                    !battleField[i+2][j].isShip&&
                    !battleField[i+3][j].isShip&&
                    !battleField[i][j-1].isShip&&
                    !battleField[i+1][j-1].isShip&&
                    !battleField[i+2][j-1].isShip&&
                    !battleField[i+3][j-1].isShip){
                putShipWithThreeCellsVerticallyNow(i,j);
            }
            else{
                putShipWithThreeCellsVertically();
            }
        }
        else if(i>0&&i<7&&j==9){
            if(!battleField[i-1][j].isShip&&
                    !battleField[i][j].isShip&&
                    !battleField[i+1][j].isShip&&
                    !battleField[i+2][j].isShip&&
                    !battleField[i+3][j].isShip&&
                    !battleField[i-1][j-1].isShip&&
                    !battleField[i][j-1].isShip&&
                    !battleField[i+1][j-1].isShip&&
                    !battleField[i+2][j-1].isShip&&
                    !battleField[i+3][j-1].isShip){
                putShipWithThreeCellsVerticallyNow(i,j);
            }
            else{
                putShipWithThreeCellsVertically();
            }
        }
        else if(i==7&&j==9){
            if(!battleField[i-1][j].isShip&&
                    !battleField[i][j].isShip&&
                    !battleField[i+1][j].isShip&&
                    !battleField[i+2][j].isShip&&
                    !battleField[i-1][j-1].isShip&&
                    !battleField[i][j-1].isShip&&
                    !battleField[i+1][j-1].isShip&&
                    !battleField[i+2][j-1].isShip){
                putShipWithThreeCellsVerticallyNow(i,j);
            }
            else{
                putShipWithThreeCellsVertically();
            }
        }
        else putShipWithThreeCellsVertically();
    }

    private void putShipWithThreeCellsVerticallyNow(int i, int j) {

    }

    private void putShipWithThreeCellHorizontally() {
        Random random = new Random();
        int position = random.nextInt(999);
        int i = position/100;
        int j = position%10;
        if(i==0&&j==0){
            if((!battleField[i][j].isShip)&&
                    (!battleField[i][j + 1].isShip)&&
                    (!battleField[i][j + 2].isShip)&&
                    (!battleField[i][j + 3].isShip)&&
                    (!battleField[i + 1][j].isShip)&&
                    (!battleField[i + 1][j + 1].isShip)&&
                    (!battleField[i + 1][j + 2].isShip)&&
                    (!battleField[i + 1][j + 3].isShip))
            {
            putShipWithThreeCellsHorizontallyNow(i,j);
            }
            else{
                putShipWithThreeCellHorizontally();
            }
        }
        else if ((i>0)&&(i<9)&&j==0){
            if((!battleField[i][j].isShip)&&
                    (!battleField[i][j + 1].isShip)&&
                    (!battleField[i][j + 2].isShip)&&
                    (!battleField[i][j + 3].isShip)&&
                    (!battleField[i + 1][j].isShip)&&
                    (!battleField[i + 1][j + 1].isShip)&&
                    (!battleField[i + 1][j + 2].isShip)&&
                    (!battleField[i + 1][j + 3].isShip)&&
                    (!battleField[i - 1][j].isShip)&&
                    (!battleField[i - 1][j + 1].isShip)&&
                    (!battleField[i - 1][j + 2].isShip)&&
                    (!battleField[i - 1][j + 3].isShip))
            {
            putShipWithThreeCellsHorizontallyNow(i,j);
            }
            else{
                putShipWithThreeCellHorizontally();
            }
        }
        else if (i==9&&j==0){
            if((!battleField[i][j].isShip)&&
                    (!battleField[i][j + 1].isShip)&&
                    (!battleField[i][j + 2].isShip)&&
                    (!battleField[i][j + 3].isShip)&&
                    (!battleField[i - 1][j].isShip)&&
                    (!battleField[i - 1][j + 1].isShip)&&
                    (!battleField[i - 1][j + 2].isShip)&&
                    (!battleField[i - 1][j + 3].isShip))
            {
                putShipWithThreeCellsHorizontallyNow(i,j);
            }
            else{
                putShipWithThreeCellHorizontally();
            }

        }
        else if (i==0&&((j>0)&&(j<7))){
            if(        (!battleField[i][j-1].isShip)&&
                    (!battleField[i][j].isShip)&&
                    (!battleField[i][j + 1].isShip)&&
                    (!battleField[i][j + 2].isShip)&&
                    (!battleField[i][j + 3].isShip)&&
                    (!battleField[i + 1][j-1].isShip)&&
                    (!battleField[i + 1][j].isShip)&&
                    (!battleField[i + 1][j + 1].isShip)&&
                    (!battleField[i + 1][j + 2].isShip)&&
                    (!battleField[i + 1][j + 3].isShip))
            {
                putShipWithThreeCellsHorizontallyNow(i,j);
            }
            else{
                putShipWithThreeCellHorizontally();
            }

        }
        else if (((i>0)&&(i<9))&&((j>0)&&(j<7))){
            if((!battleField[i][j-1].isShip)&&
                    (!battleField[i][j].isShip)&&
                    (!battleField[i][j + 1].isShip)&&
                    (!battleField[i][j + 2].isShip)&&
                    (!battleField[i][j + 3].isShip)&&
                    (!battleField[i + 1][j-1].isShip)&&
                    (!battleField[i + 1][j].isShip)&&
                    (!battleField[i + 1][j + 1].isShip)&&
                    (!battleField[i + 1][j + 2].isShip)&&
                    (!battleField[i + 1][j + 3].isShip)&&
                    (!battleField[i - 1][j-1].isShip)&&
                    (!battleField[i - 1][j].isShip)&&
                    (!battleField[i - 1][j + 1].isShip)&&
                    (!battleField[i - 1][j + 2].isShip)&&
                    (!battleField[i - 1][j + 3].isShip))
            {
                putShipWithThreeCellsHorizontallyNow(i,j);
            }
            else{
                putShipWithThreeCellHorizontally();
            }

        }
        else if (i==9&&((j>0)&&(j<7))){
            if((!battleField[i][j-1].isShip)&&
                    (!battleField[i][j].isShip)&&
                    (!battleField[i][j + 1].isShip)&&
                    (!battleField[i][j + 2].isShip)&&
                    (!battleField[i][j + 3].isShip)&&
                    (!battleField[i - 1][j-1].isShip)&&
                    (!battleField[i - 1][j].isShip)&&
                    (!battleField[i - 1][j + 1].isShip)&&
                    (!battleField[i - 1][j + 2].isShip)&&
                    (!battleField[i - 1][j + 3].isShip))
            {
                putShipWithThreeCellsHorizontallyNow(i,j);
            }
            else{
                putShipWithThreeCellHorizontally();
            }

        }
        else if (i==0&&j==7){
            if((!battleField[i][j-1].isShip)&&
                    (!battleField[i][j].isShip)&&
                    (!battleField[i][j + 1].isShip)&&
                    (!battleField[i][j + 2].isShip)&&
                    (!battleField[i + 1][j-1].isShip)&&
                    (!battleField[i + 1][j].isShip)&&
                    (!battleField[i + 1][j + 1].isShip)&&
                    (!battleField[i + 1][j + 2].isShip))
            {
                putShipWithThreeCellsHorizontallyNow(i,j);
            }
            else{
                putShipWithThreeCellHorizontally();
            }

        }
        else if (((i>0)&&(i<9))&&(j==7)){
            if((!battleField[i][j-1].isShip)&&
                    (!battleField[i][j].isShip)&&
                    (!battleField[i][j + 1].isShip)&&
                    (!battleField[i][j + 2].isShip)&&
                    (!battleField[i + 1][j-1].isShip)&&
                    (!battleField[i + 1][j].isShip)&&
                    (!battleField[i + 1][j + 1].isShip)&&
                    (!battleField[i + 1][j + 2].isShip)&&
                    (!battleField[i - 1][j-1].isShip)&&
                    (!battleField[i - 1][j].isShip)&&
                    (!battleField[i - 1][j + 1].isShip)&&
                    (!battleField[i - 1][j + 2].isShip))
            {
                putShipWithThreeCellsHorizontallyNow(i,j);
            }
            else{
                putShipWithThreeCellHorizontally();
            }

        }
        else if ((i==9)&&(j==7)){
            if((!battleField[i][j-1].isShip)&&
                    (!battleField[i][j].isShip)&&
                    (!battleField[i][j + 1].isShip)&&
                    (!battleField[i][j + 2].isShip)&&
                    (!battleField[i - 1][j-1].isShip)&&
                    (!battleField[i - 1][j].isShip)&&
                    (!battleField[i - 1][j + 1].isShip)&&
                    (!battleField[i - 1][j + 2].isShip))
            {
                putShipWithThreeCellsHorizontallyNow(i,j);
            }
            else{
                putShipWithThreeCellHorizontally();
            }
        }
        else putShipWithThreeCellHorizontally();
    }

    private void putShipWithFourCells() { 
        position = choosePosition();
        if (position){
           putShipWithFourCellsHorizontally();
        }
        else{
            putShipWithFourCellsVertically();
        }
    }

    private void putShipWithFourCellsVertically() {
        Random random = new Random();
        int position = random.nextInt(999);
        int i = position/100;
        int j = position%10;
        if (i<7){

        }
        else{
            putShipWithFourCellsVertically();
        }
    }

    private boolean choosePosition() {
        Random random = new Random();
        return random.nextInt(1000)%2==0;
    }

    private void putShipWithFourCellsHorizontally() {
        Random random = new Random();
        int position = random.nextInt(999);
        int i = position/100;
        int j = position%10;
        if (j<7){

        }
        else{
            putShipWithFourCellsHorizontally();
        }
    }

    private void putShipWithThreeCellsHorizontallyNow(int i, int j){

    }

    void eraseBattleField(){
        for (int i = 0; i<10; i++){
            for (int j = 0; i<10; i++){

            }
        }
    }
}



