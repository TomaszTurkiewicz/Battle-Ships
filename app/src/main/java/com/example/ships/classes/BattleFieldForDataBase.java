package com.example.ships.classes;

import java.util.Arrays;
import java.util.List;

public class BattleFieldForDataBase {
    private List<Ship> battleField = Arrays.asList(new Ship[100]);

    public BattleFieldForDataBase(List<Ship> battleField) {
        this.battleField = battleField;
    }

    public BattleFieldForDataBase() {
        for(int i=0;i<100;i++){
        this.battleField.set(i,new Ship(0,0,false,false,false));
    }
    }

    public List<Ship> getBattleField() {
        return battleField;
    }

    public void setBattleField(List<Ship> battleField) {
        this.battleField = battleField;
    }
}
