package com.example.ships;

class BattleFieldPlayerOneSingleton {
    private static final BattleFieldPlayerOneSingleton ourInstance = new BattleFieldPlayerOneSingleton();

    Ship[][] battleField = new Ship[10][10];

    static BattleFieldPlayerOneSingleton getInstance() {
        return ourInstance;
    }

    private BattleFieldPlayerOneSingleton() {
        for (int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                battleField[i][j].setNumberOfMasts(0);
                battleField[i][j].setShipNumber(0);
                battleField[i][j].setShip(false);
            }}
    }

    public void storeBattleField(Ship[][] battleFieldPlayerOneArray){
        this.battleField=battleFieldPlayerOneArray;
    }
}