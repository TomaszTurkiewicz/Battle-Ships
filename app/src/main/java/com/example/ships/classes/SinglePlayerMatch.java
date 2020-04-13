package com.example.ships.classes;

import java.util.Arrays;
import java.util.List;

public class SinglePlayerMatch {
    private int difficulty;
    private boolean game;
    private List<Ship> battleFieldListMy;
    private List<Ship> battleFieldListOpponent;
    private boolean myTurn;
    private int positionI;
    private int positionJ;
    private boolean newShoot;
    private int direction;
    private int x;
    private int y;

    public SinglePlayerMatch() {
        this.difficulty=-1;
        this.game=false;
        this.myTurn=false;
        this.battleFieldListMy =  Arrays.asList(new Ship[100]);
        this.battleFieldListOpponent = Arrays.asList(new Ship[100]);
        this.positionI=-1;
        this.positionJ=-1;
        this.newShoot=false;
        this.direction=0;
        this.x=-1;
        this.y=-1;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getPositionI() {
        return positionI;
    }

    public void setPositionI(int positionI) {
        this.positionI = positionI;
    }

    public int getPositionJ() {
        return positionJ;
    }

    public void setPositionJ(int positionJ) {
        this.positionJ = positionJ;
    }

    public boolean isNewShoot() {
        return newShoot;
    }

    public void setNewShoot(boolean newShoot) {
        this.newShoot = newShoot;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public boolean isMyTurn() {
        return myTurn;
    }

    public void setBattleFieldListMy(List<Ship> battleFieldListMy) {
        this.battleFieldListMy = battleFieldListMy;
    }

    public void setBattleFieldListOpponent(List<Ship> battleFieldListOpponent) {
        this.battleFieldListOpponent = battleFieldListOpponent;
    }

    public void setMyTurn(boolean myTurn) {
        this.myTurn = myTurn;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public boolean isGame() {
        return game;
    }

    public void setGame(boolean game) {
        this.game = game;
    }

    public List<Ship> getBattleFieldListMy() {
        return battleFieldListMy;
    }


    public void setBattleFieldListMyFromArray(BattleField battleFieldMy) {
        for(int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                this.battleFieldListMy.set(i*10+j,battleFieldMy.getBattleField(i,j));
            }
        }
    }



    public List<Ship> getBattleFieldListOpponent() {
        return battleFieldListOpponent;
    }

    public void setBattleFieldListOpponentFromArray(BattleField battleFieldOpponent) {
        for(int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                this.battleFieldListOpponent.set(i*10+j,battleFieldOpponent.getBattleField(i,j));
            }
        }
    }

}
