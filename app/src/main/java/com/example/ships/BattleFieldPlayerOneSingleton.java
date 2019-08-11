package com.example.ships;

class BattleFieldPlayerOneSingleton {
    private static final BattleFieldPlayerOneSingleton ourInstance = new BattleFieldPlayerOneSingleton();

    BattleField battleField = new BattleField();

    static BattleFieldPlayerOneSingleton getInstance() {
        return ourInstance;
    }

    private BattleFieldPlayerOneSingleton() {
        for (int i=0;i<10;i++){
            for(int j=0;j<10;j++){
        battleField.putShipOnBattleFieldSpace(0,0,false,i,j);
            }}
    }

    public void storeBattleField(BattleField battleField){
        this.battleField=battleField;
    }

    public BattleField readBattleField(){

        return BattleFieldPlayerOneSingleton.getInstance().battleField;

    }
}