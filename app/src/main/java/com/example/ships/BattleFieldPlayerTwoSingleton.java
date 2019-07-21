package com.example.ships;

class BattleFieldPlayerTwoSingleton {
    Ship[][] battleFieldPlayerTwoArray =new Ship[10][10];
    private static final BattleFieldPlayerTwoSingleton ourInstance = new BattleFieldPlayerTwoSingleton();

    static BattleFieldPlayerTwoSingleton getInstance() {
        return ourInstance;
    }


    Ship ship0 = new Ship(0,false);
    private BattleFieldPlayerTwoSingleton() {
        for (int i=0;i<10;i++){
            for (int j=0;j<10;j++){
                battleFieldPlayerTwoArray[i][j]=ship0;
            }
        }
    }

    public void storeBattleField(Ship[][]battleFieldPlayerOneArray){
        this.battleFieldPlayerTwoArray=battleFieldPlayerOneArray;
    }
}
