package com.example.ships;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class RandomGameBattle extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_game_battle);

        BattleField battleFieldPlayerOneActivityRandomGame = new BattleField();
        TextView[][] TextViewArrayActivityRandomGame = new TextView[10][10];
        initializeBattleFieldActivityRandomGame(TextViewArrayActivityRandomGame);
        
        displayBattleFieldPlayerOneActivityRandomGame(TextViewArrayActivityRandomGame,battleFieldPlayerOneActivityRandomGame);
    }

    private void displayBattleFieldPlayerOneActivityRandomGame(TextView[][] TextViewArrayActivityRandomGame, BattleField battleFieldPlayerOneActivityRandomGame) {
        for(int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                if(battleFieldPlayerOneActivityRandomGame.getBattleField(i,j).isShip()){
                    TextViewArrayActivityRandomGame[i][j].setBackgroundColor(getResources().getColor(R.color.ship));
                }}}
    }

    private void initializeBattleFieldActivityRandomGame(TextView[][] textViewArrayActivityRandomGame) {
        textViewArrayActivityRandomGame[0][0]=findViewById(R.id.playerCellGame_1x1);
        textViewArrayActivityRandomGame[0][1]=findViewById(R.id.playerCellGame_1x2);
        textViewArrayActivityRandomGame[0][2]=findViewById(R.id.playerCellGame_1x3);
        textViewArrayActivityRandomGame[0][3]=findViewById(R.id.playerCellGame_1x4);
        textViewArrayActivityRandomGame[0][4]=findViewById(R.id.playerCellGame_1x5);
        textViewArrayActivityRandomGame[0][5]=findViewById(R.id.playerCellGame_1x6);
        textViewArrayActivityRandomGame[0][6]=findViewById(R.id.playerCellGame_1x7);
        textViewArrayActivityRandomGame[0][7]=findViewById(R.id.playerCellGame_1x8);
        textViewArrayActivityRandomGame[0][8]=findViewById(R.id.playerCellGame_1x9);
        textViewArrayActivityRandomGame[0][9]=findViewById(R.id.playerCellGame_1x10);

        textViewArrayActivityRandomGame[1][0]=findViewById(R.id.playerCellGame_2x1);
        textViewArrayActivityRandomGame[1][1]=findViewById(R.id.playerCellGame_2x2);
        textViewArrayActivityRandomGame[1][2]=findViewById(R.id.playerCellGame_2x3);
        textViewArrayActivityRandomGame[1][3]=findViewById(R.id.playerCellGame_2x4);
        textViewArrayActivityRandomGame[1][4]=findViewById(R.id.playerCellGame_2x5);
        textViewArrayActivityRandomGame[1][5]=findViewById(R.id.playerCellGame_2x6);
        textViewArrayActivityRandomGame[1][6]=findViewById(R.id.playerCellGame_2x7);
        textViewArrayActivityRandomGame[1][7]=findViewById(R.id.playerCellGame_2x8);
        textViewArrayActivityRandomGame[1][8]=findViewById(R.id.playerCellGame_2x9);
        textViewArrayActivityRandomGame[1][9]=findViewById(R.id.playerCellGame_2x10);

        textViewArrayActivityRandomGame[2][0]=findViewById(R.id.playerCellGame_3x1);
        textViewArrayActivityRandomGame[2][1]=findViewById(R.id.playerCellGame_3x2);
        textViewArrayActivityRandomGame[2][2]=findViewById(R.id.playerCellGame_3x3);
        textViewArrayActivityRandomGame[2][3]=findViewById(R.id.playerCellGame_3x4);
        textViewArrayActivityRandomGame[2][4]=findViewById(R.id.playerCellGame_3x5);
        textViewArrayActivityRandomGame[2][5]=findViewById(R.id.playerCellGame_3x6);
        textViewArrayActivityRandomGame[2][6]=findViewById(R.id.playerCellGame_3x7);
        textViewArrayActivityRandomGame[2][7]=findViewById(R.id.playerCellGame_3x8);
        textViewArrayActivityRandomGame[2][8]=findViewById(R.id.playerCellGame_3x9);
        textViewArrayActivityRandomGame[2][9]=findViewById(R.id.playerCellGame_3x10);

        textViewArrayActivityRandomGame[3][0]=findViewById(R.id.playerCellGame_4x1);
        textViewArrayActivityRandomGame[3][1]=findViewById(R.id.playerCellGame_4x2);
        textViewArrayActivityRandomGame[3][2]=findViewById(R.id.playerCellGame_4x3);
        textViewArrayActivityRandomGame[3][3]=findViewById(R.id.playerCellGame_4x4);
        textViewArrayActivityRandomGame[3][4]=findViewById(R.id.playerCellGame_4x5);
        textViewArrayActivityRandomGame[3][5]=findViewById(R.id.playerCellGame_4x6);
        textViewArrayActivityRandomGame[3][6]=findViewById(R.id.playerCellGame_4x7);
        textViewArrayActivityRandomGame[3][7]=findViewById(R.id.playerCellGame_4x8);
        textViewArrayActivityRandomGame[3][8]=findViewById(R.id.playerCellGame_4x9);
        textViewArrayActivityRandomGame[3][9]=findViewById(R.id.playerCellGame_4x10);

        textViewArrayActivityRandomGame[4][0]=findViewById(R.id.playerCellGame_5x1);
        textViewArrayActivityRandomGame[4][1]=findViewById(R.id.playerCellGame_5x2);
        textViewArrayActivityRandomGame[4][2]=findViewById(R.id.playerCellGame_5x3);
        textViewArrayActivityRandomGame[4][3]=findViewById(R.id.playerCellGame_5x4);
        textViewArrayActivityRandomGame[4][4]=findViewById(R.id.playerCellGame_5x5);
        textViewArrayActivityRandomGame[4][5]=findViewById(R.id.playerCellGame_5x6);
        textViewArrayActivityRandomGame[4][6]=findViewById(R.id.playerCellGame_5x7);
        textViewArrayActivityRandomGame[4][7]=findViewById(R.id.playerCellGame_5x8);
        textViewArrayActivityRandomGame[4][8]=findViewById(R.id.playerCellGame_5x9);
        textViewArrayActivityRandomGame[4][9]=findViewById(R.id.playerCellGame_5x10);

        textViewArrayActivityRandomGame[5][0]=findViewById(R.id.playerCellGame_6x1);
        textViewArrayActivityRandomGame[5][1]=findViewById(R.id.playerCellGame_6x2);
        textViewArrayActivityRandomGame[5][2]=findViewById(R.id.playerCellGame_6x3);
        textViewArrayActivityRandomGame[5][3]=findViewById(R.id.playerCellGame_6x4);
        textViewArrayActivityRandomGame[5][4]=findViewById(R.id.playerCellGame_6x5);
        textViewArrayActivityRandomGame[5][5]=findViewById(R.id.playerCellGame_6x6);
        textViewArrayActivityRandomGame[5][6]=findViewById(R.id.playerCellGame_6x7);
        textViewArrayActivityRandomGame[5][7]=findViewById(R.id.playerCellGame_6x8);
        textViewArrayActivityRandomGame[5][8]=findViewById(R.id.playerCellGame_6x9);
        textViewArrayActivityRandomGame[5][9]=findViewById(R.id.playerCellGame_6x10);

        textViewArrayActivityRandomGame[6][0]=findViewById(R.id.playerCellGame_7x1);
        textViewArrayActivityRandomGame[6][1]=findViewById(R.id.playerCellGame_7x2);
        textViewArrayActivityRandomGame[6][2]=findViewById(R.id.playerCellGame_7x3);
        textViewArrayActivityRandomGame[6][3]=findViewById(R.id.playerCellGame_7x4);
        textViewArrayActivityRandomGame[6][4]=findViewById(R.id.playerCellGame_7x5);
        textViewArrayActivityRandomGame[6][5]=findViewById(R.id.playerCellGame_7x6);
        textViewArrayActivityRandomGame[6][6]=findViewById(R.id.playerCellGame_7x7);
        textViewArrayActivityRandomGame[6][7]=findViewById(R.id.playerCellGame_7x8);
        textViewArrayActivityRandomGame[6][8]=findViewById(R.id.playerCellGame_7x9);
        textViewArrayActivityRandomGame[6][9]=findViewById(R.id.playerCellGame_7x10);

        textViewArrayActivityRandomGame[7][0]=findViewById(R.id.playerCellGame_8x1);
        textViewArrayActivityRandomGame[7][1]=findViewById(R.id.playerCellGame_8x2);
        textViewArrayActivityRandomGame[7][2]=findViewById(R.id.playerCellGame_8x3);
        textViewArrayActivityRandomGame[7][3]=findViewById(R.id.playerCellGame_8x4);
        textViewArrayActivityRandomGame[7][4]=findViewById(R.id.playerCellGame_8x5);
        textViewArrayActivityRandomGame[7][5]=findViewById(R.id.playerCellGame_8x6);
        textViewArrayActivityRandomGame[7][6]=findViewById(R.id.playerCellGame_8x7);
        textViewArrayActivityRandomGame[7][7]=findViewById(R.id.playerCellGame_8x8);
        textViewArrayActivityRandomGame[7][8]=findViewById(R.id.playerCellGame_8x9);
        textViewArrayActivityRandomGame[7][9]=findViewById(R.id.playerCellGame_8x10);

        textViewArrayActivityRandomGame[8][0]=findViewById(R.id.playerCellGame_9x1);
        textViewArrayActivityRandomGame[8][1]=findViewById(R.id.playerCellGame_9x2);
        textViewArrayActivityRandomGame[8][2]=findViewById(R.id.playerCellGame_9x3);
        textViewArrayActivityRandomGame[8][3]=findViewById(R.id.playerCellGame_9x4);
        textViewArrayActivityRandomGame[8][4]=findViewById(R.id.playerCellGame_9x5);
        textViewArrayActivityRandomGame[8][5]=findViewById(R.id.playerCellGame_9x6);
        textViewArrayActivityRandomGame[8][6]=findViewById(R.id.playerCellGame_9x7);
        textViewArrayActivityRandomGame[8][7]=findViewById(R.id.playerCellGame_9x8);
        textViewArrayActivityRandomGame[8][8]=findViewById(R.id.playerCellGame_9x9);
        textViewArrayActivityRandomGame[8][9]=findViewById(R.id.playerCellGame_9x10);

        textViewArrayActivityRandomGame[9][0]=findViewById(R.id.playerCellGame_10x1);
        textViewArrayActivityRandomGame[9][1]=findViewById(R.id.playerCellGame_10x2);
        textViewArrayActivityRandomGame[9][2]=findViewById(R.id.playerCellGame_10x3);
        textViewArrayActivityRandomGame[9][3]=findViewById(R.id.playerCellGame_10x4);
        textViewArrayActivityRandomGame[9][4]=findViewById(R.id.playerCellGame_10x5);
        textViewArrayActivityRandomGame[9][5]=findViewById(R.id.playerCellGame_10x6);
        textViewArrayActivityRandomGame[9][6]=findViewById(R.id.playerCellGame_10x7);
        textViewArrayActivityRandomGame[9][7]=findViewById(R.id.playerCellGame_10x8);
        textViewArrayActivityRandomGame[9][8]=findViewById(R.id.playerCellGame_10x9);
        textViewArrayActivityRandomGame[9][9]=findViewById(R.id.playerCellGame_10x10);
    }

}
