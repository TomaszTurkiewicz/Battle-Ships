package com.example.ships;

class BattleFieldPlayerOneSingleton {

    Ship[][] battleFieldPlayerOneArray =new Ship[10][10];

    private static final BattleFieldPlayerOneSingleton ourInstance = new BattleFieldPlayerOneSingleton();

    static BattleFieldPlayerOneSingleton getInstance() {
        return ourInstance;
    }

    Ship ship0 = new Ship(0,false);
    private BattleFieldPlayerOneSingleton() {
        for (int i=0;i<10;i++){
            for (int j=0;j<10;j++){
                battleFieldPlayerOneArray[i][j]=ship0;
            }
        }
    }

    public void storeBattleField(Ship[][]battleFieldPlayerOneArray){
        this.battleFieldPlayerOneArray=battleFieldPlayerOneArray;
    }
}
