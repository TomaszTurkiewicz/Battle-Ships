package com.example.ships.singletons;

import com.example.ships.classes.BattleField;

public class BattleFieldPlayerTwoSingleton {

    private static final BattleFieldPlayerTwoSingleton ourInstance = new BattleFieldPlayerTwoSingleton();

    public BattleField battleField = new BattleField();

   public static BattleFieldPlayerTwoSingleton getInstance() {
        return ourInstance;
    }

    private BattleFieldPlayerTwoSingleton() {
        for (int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                battleField.putShipOnBattleFieldSpace(0,0,false,i,j);
            }}
    }


    public void storeOneCell(BattleField battleField, int i, int j){
        this.battleField.battleField[i][j]=battleField.battleField[i][j];
    }

}
