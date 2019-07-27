package com.example.ships;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class RandomGame extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_game);

        BattleField battleFieldPlayerOneActivityRandom = new BattleField();

        TextView[][] TextViewArray = new TextView[10][10];
        initializeBattleField(TextViewArray);
        battleFieldPlayerOneActivityRandom.createFleet();


        displayBattleFieldPlayerOne(TextViewArray,battleFieldPlayerOneActivityRandom);

    }





    private void displayBattleFieldPlayerOne(TextView[][] TextViewArray, BattleField battleFieldPlayerOneActivityRandom) {
        for(int i=0;i<10;i++){
         for(int j=0;j<10;j++){
        if(battleFieldPlayerOneActivityRandom.getBattleField(i,j).isShip==true){
            TextViewArray[i][j].setBackgroundColor(getResources().getColor(R.color.ship));
        }}}
    }

    private void initializeBattleField(TextView[][] textViewArray) {
        textViewArray[0][0]=findViewById(R.id.playerCell_1x1);
        textViewArray[0][1]=findViewById(R.id.playerCell_1x2);
        textViewArray[0][2]=findViewById(R.id.playerCell_1x3);
        textViewArray[0][3]=findViewById(R.id.playerCell_1x4);
        textViewArray[0][4]=findViewById(R.id.playerCell_1x5);
        textViewArray[0][5]=findViewById(R.id.playerCell_1x6);
        textViewArray[0][6]=findViewById(R.id.playerCell_1x7);
        textViewArray[0][7]=findViewById(R.id.playerCell_1x8);
        textViewArray[0][8]=findViewById(R.id.playerCell_1x9);
        textViewArray[0][9]=findViewById(R.id.playerCell_1x10);

        textViewArray[1][0]=findViewById(R.id.playerCell_2x1);
        textViewArray[1][1]=findViewById(R.id.playerCell_2x2);
        textViewArray[1][2]=findViewById(R.id.playerCell_2x3);
        textViewArray[1][3]=findViewById(R.id.playerCell_2x4);
        textViewArray[1][4]=findViewById(R.id.playerCell_2x5);
        textViewArray[1][5]=findViewById(R.id.playerCell_2x6);
        textViewArray[1][6]=findViewById(R.id.playerCell_2x7);
        textViewArray[1][7]=findViewById(R.id.playerCell_2x8);
        textViewArray[1][8]=findViewById(R.id.playerCell_2x9);
        textViewArray[1][9]=findViewById(R.id.playerCell_2x10);

        textViewArray[2][0]=findViewById(R.id.playerCell_3x1);
        textViewArray[2][1]=findViewById(R.id.playerCell_3x2);
        textViewArray[2][2]=findViewById(R.id.playerCell_3x3);
        textViewArray[2][3]=findViewById(R.id.playerCell_3x4);
        textViewArray[2][4]=findViewById(R.id.playerCell_3x5);
        textViewArray[2][5]=findViewById(R.id.playerCell_3x6);
        textViewArray[2][6]=findViewById(R.id.playerCell_3x7);
        textViewArray[2][7]=findViewById(R.id.playerCell_3x8);
        textViewArray[2][8]=findViewById(R.id.playerCell_3x9);
        textViewArray[2][9]=findViewById(R.id.playerCell_3x10);

        textViewArray[3][0]=findViewById(R.id.playerCell_4x1);
        textViewArray[3][1]=findViewById(R.id.playerCell_4x2);
        textViewArray[3][2]=findViewById(R.id.playerCell_4x3);
        textViewArray[3][3]=findViewById(R.id.playerCell_4x4);
        textViewArray[3][4]=findViewById(R.id.playerCell_4x5);
        textViewArray[3][5]=findViewById(R.id.playerCell_4x6);
        textViewArray[3][6]=findViewById(R.id.playerCell_4x7);
        textViewArray[3][7]=findViewById(R.id.playerCell_4x8);
        textViewArray[3][8]=findViewById(R.id.playerCell_4x9);
        textViewArray[3][9]=findViewById(R.id.playerCell_4x10);

        textViewArray[4][0]=findViewById(R.id.playerCell_5x1);
        textViewArray[4][1]=findViewById(R.id.playerCell_5x2);
        textViewArray[4][2]=findViewById(R.id.playerCell_5x3);
        textViewArray[4][3]=findViewById(R.id.playerCell_5x4);
        textViewArray[4][4]=findViewById(R.id.playerCell_5x5);
        textViewArray[4][5]=findViewById(R.id.playerCell_5x6);
        textViewArray[4][6]=findViewById(R.id.playerCell_5x7);
        textViewArray[4][7]=findViewById(R.id.playerCell_5x8);
        textViewArray[4][8]=findViewById(R.id.playerCell_5x9);
        textViewArray[4][9]=findViewById(R.id.playerCell_5x10);

        textViewArray[5][0]=findViewById(R.id.playerCell_6x1);
        textViewArray[5][1]=findViewById(R.id.playerCell_6x2);
        textViewArray[5][2]=findViewById(R.id.playerCell_6x3);
        textViewArray[5][3]=findViewById(R.id.playerCell_6x4);
        textViewArray[5][4]=findViewById(R.id.playerCell_6x5);
        textViewArray[5][5]=findViewById(R.id.playerCell_6x6);
        textViewArray[5][6]=findViewById(R.id.playerCell_6x7);
        textViewArray[5][7]=findViewById(R.id.playerCell_6x8);
        textViewArray[5][8]=findViewById(R.id.playerCell_6x9);
        textViewArray[5][9]=findViewById(R.id.playerCell_6x10);

        textViewArray[6][0]=findViewById(R.id.playerCell_7x1);
        textViewArray[6][1]=findViewById(R.id.playerCell_7x2);
        textViewArray[6][2]=findViewById(R.id.playerCell_7x3);
        textViewArray[6][3]=findViewById(R.id.playerCell_7x4);
        textViewArray[6][4]=findViewById(R.id.playerCell_7x5);
        textViewArray[6][5]=findViewById(R.id.playerCell_7x6);
        textViewArray[6][6]=findViewById(R.id.playerCell_7x7);
        textViewArray[6][7]=findViewById(R.id.playerCell_7x8);
        textViewArray[6][8]=findViewById(R.id.playerCell_7x9);
        textViewArray[6][9]=findViewById(R.id.playerCell_7x10);

        textViewArray[7][0]=findViewById(R.id.playerCell_8x1);
        textViewArray[7][1]=findViewById(R.id.playerCell_8x2);
        textViewArray[7][2]=findViewById(R.id.playerCell_8x3);
        textViewArray[7][3]=findViewById(R.id.playerCell_8x4);
        textViewArray[7][4]=findViewById(R.id.playerCell_8x5);
        textViewArray[7][5]=findViewById(R.id.playerCell_8x6);
        textViewArray[7][6]=findViewById(R.id.playerCell_8x7);
        textViewArray[7][7]=findViewById(R.id.playerCell_8x8);
        textViewArray[7][8]=findViewById(R.id.playerCell_8x9);
        textViewArray[7][9]=findViewById(R.id.playerCell_8x10);

        textViewArray[8][0]=findViewById(R.id.playerCell_9x1);
        textViewArray[8][1]=findViewById(R.id.playerCell_9x2);
        textViewArray[8][2]=findViewById(R.id.playerCell_9x3);
        textViewArray[8][3]=findViewById(R.id.playerCell_9x4);
        textViewArray[8][4]=findViewById(R.id.playerCell_9x5);
        textViewArray[8][5]=findViewById(R.id.playerCell_9x6);
        textViewArray[8][6]=findViewById(R.id.playerCell_9x7);
        textViewArray[8][7]=findViewById(R.id.playerCell_9x8);
        textViewArray[8][8]=findViewById(R.id.playerCell_9x9);
        textViewArray[8][9]=findViewById(R.id.playerCell_9x10);

        textViewArray[9][0]=findViewById(R.id.playerCell_10x1);
        textViewArray[9][1]=findViewById(R.id.playerCell_10x2);
        textViewArray[9][2]=findViewById(R.id.playerCell_10x3);
        textViewArray[9][3]=findViewById(R.id.playerCell_10x4);
        textViewArray[9][4]=findViewById(R.id.playerCell_10x5);
        textViewArray[9][5]=findViewById(R.id.playerCell_10x6);
        textViewArray[9][6]=findViewById(R.id.playerCell_10x7);
        textViewArray[9][7]=findViewById(R.id.playerCell_10x8);
        textViewArray[9][8]=findViewById(R.id.playerCell_10x9);
        textViewArray[9][9]=findViewById(R.id.playerCell_10x10);
    }


    public void startGame(View view) {
        Intent intent = new Intent(getApplicationContext(),RandomGameBattle.class);
        startActivity(intent);
        finish();
    }

    public void back(View view) {
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void reDo(View view) {
        Intent intent = new Intent(getApplicationContext(),RandomGame.class);
        startActivity(intent);
        finish();
    }
}