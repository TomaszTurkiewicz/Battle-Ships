package com.example.ships;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class CreateBattleField extends AppCompatActivity {

    BattleField battleFieldPlayerCreateBattleFieldActivity = new BattleField();
    TextView[][] TextViewArrayActivityCreateBattleField = new TextView[10][10];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_battle_field);

        initializeBattleFieldActivityRandomGamePlayerOne(TextViewArrayActivityCreateBattleField);
    }

    private void initializeBattleFieldActivityRandomGamePlayerOne(TextView[][] textViewArrayActivityCreateBattleField) {
        textViewArrayActivityCreateBattleField[0][0]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_1x1);
        textViewArrayActivityCreateBattleField[0][1]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_1x2);
        textViewArrayActivityCreateBattleField[0][2]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_1x3);
        textViewArrayActivityCreateBattleField[0][3]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_1x4);
        textViewArrayActivityCreateBattleField[0][4]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_1x5);
        textViewArrayActivityCreateBattleField[0][5]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_1x6);
        textViewArrayActivityCreateBattleField[0][6]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_1x7);
        textViewArrayActivityCreateBattleField[0][7]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_1x8);
        textViewArrayActivityCreateBattleField[0][8]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_1x9);
        textViewArrayActivityCreateBattleField[0][9]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_1x10);

        textViewArrayActivityCreateBattleField[1][0]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_2x1);
        textViewArrayActivityCreateBattleField[1][1]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_2x2);
        textViewArrayActivityCreateBattleField[1][2]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_2x3);
        textViewArrayActivityCreateBattleField[1][3]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_2x4);
        textViewArrayActivityCreateBattleField[1][4]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_2x5);
        textViewArrayActivityCreateBattleField[1][5]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_2x6);
        textViewArrayActivityCreateBattleField[1][6]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_2x7);
        textViewArrayActivityCreateBattleField[1][7]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_2x8);
        textViewArrayActivityCreateBattleField[1][8]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_2x9);
        textViewArrayActivityCreateBattleField[1][9]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_2x10);

        textViewArrayActivityCreateBattleField[2][0]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_3x1);
        textViewArrayActivityCreateBattleField[2][1]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_3x2);
        textViewArrayActivityCreateBattleField[2][2]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_3x3);
        textViewArrayActivityCreateBattleField[2][3]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_3x4);
        textViewArrayActivityCreateBattleField[2][4]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_3x5);
        textViewArrayActivityCreateBattleField[2][5]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_3x6);
        textViewArrayActivityCreateBattleField[2][6]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_3x7);
        textViewArrayActivityCreateBattleField[2][7]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_3x8);
        textViewArrayActivityCreateBattleField[2][8]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_3x9);
        textViewArrayActivityCreateBattleField[2][9]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_3x10);

        textViewArrayActivityCreateBattleField[3][0]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_4x1);
        textViewArrayActivityCreateBattleField[3][1]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_4x2);
        textViewArrayActivityCreateBattleField[3][2]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_4x3);
        textViewArrayActivityCreateBattleField[3][3]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_4x4);
        textViewArrayActivityCreateBattleField[3][4]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_4x5);
        textViewArrayActivityCreateBattleField[3][5]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_4x6);
        textViewArrayActivityCreateBattleField[3][6]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_4x7);
        textViewArrayActivityCreateBattleField[3][7]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_4x8);
        textViewArrayActivityCreateBattleField[3][8]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_4x9);
        textViewArrayActivityCreateBattleField[3][9]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_4x10);

        textViewArrayActivityCreateBattleField[4][0]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_5x1);
        textViewArrayActivityCreateBattleField[4][1]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_5x2);
        textViewArrayActivityCreateBattleField[4][2]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_5x3);
        textViewArrayActivityCreateBattleField[4][3]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_5x4);
        textViewArrayActivityCreateBattleField[4][4]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_5x5);
        textViewArrayActivityCreateBattleField[4][5]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_5x6);
        textViewArrayActivityCreateBattleField[4][6]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_5x7);
        textViewArrayActivityCreateBattleField[4][7]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_5x8);
        textViewArrayActivityCreateBattleField[4][8]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_5x9);
        textViewArrayActivityCreateBattleField[4][9]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_5x10);

        textViewArrayActivityCreateBattleField[5][0]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_6x1);
        textViewArrayActivityCreateBattleField[5][1]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_6x2);
        textViewArrayActivityCreateBattleField[5][2]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_6x3);
        textViewArrayActivityCreateBattleField[5][3]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_6x4);
        textViewArrayActivityCreateBattleField[5][4]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_6x5);
        textViewArrayActivityCreateBattleField[5][5]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_6x6);
        textViewArrayActivityCreateBattleField[5][6]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_6x7);
        textViewArrayActivityCreateBattleField[5][7]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_6x8);
        textViewArrayActivityCreateBattleField[5][8]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_6x9);
        textViewArrayActivityCreateBattleField[5][9]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_6x10);

        textViewArrayActivityCreateBattleField[6][0]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_7x1);
        textViewArrayActivityCreateBattleField[6][1]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_7x2);
        textViewArrayActivityCreateBattleField[6][2]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_7x3);
        textViewArrayActivityCreateBattleField[6][3]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_7x4);
        textViewArrayActivityCreateBattleField[6][4]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_7x5);
        textViewArrayActivityCreateBattleField[6][5]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_7x6);
        textViewArrayActivityCreateBattleField[6][6]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_7x7);
        textViewArrayActivityCreateBattleField[6][7]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_7x8);
        textViewArrayActivityCreateBattleField[6][8]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_7x9);
        textViewArrayActivityCreateBattleField[6][9]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_7x10);

        textViewArrayActivityCreateBattleField[7][0]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_8x1);
        textViewArrayActivityCreateBattleField[7][1]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_8x2);
        textViewArrayActivityCreateBattleField[7][2]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_8x3);
        textViewArrayActivityCreateBattleField[7][3]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_8x4);
        textViewArrayActivityCreateBattleField[7][4]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_8x5);
        textViewArrayActivityCreateBattleField[7][5]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_8x6);
        textViewArrayActivityCreateBattleField[7][6]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_8x7);
        textViewArrayActivityCreateBattleField[7][7]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_8x8);
        textViewArrayActivityCreateBattleField[7][8]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_8x9);
        textViewArrayActivityCreateBattleField[7][9]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_8x10);

        //todo dokończyć inicjalizację tablicy
    }

    public void onClickBack(View view) {
    }

    public void onClickStartGame(View view) {
        Intent intent = new Intent(getApplicationContext(),ChooseGameLevel.class);
        startActivity(intent);
    }

    public void onClickputOneMastsShip(View view) {
    }

    public void onClickputTwoMastsShip(View view) {
    }

    public void onClickputThreeMastsShip(View view) {
    }

    public void onClickputFourMastsShip(View view) {
    }

    public void clickCreateBattleField_1x1(View view) {
    }

    public void clickCreateBattleField_1x2(View view) {
    }

    public void clickCreateBattleField_1x3(View view) {
    }

    public void clickCreateBattleField_1x4(View view) {
    }

    public void clickCreateBattleField_1x5(View view) {
    }

    public void clickCreateBattleField_1x6(View view) {
    }

    public void clickCreateBattleField_1x7(View view) {
    }

    public void clickCreateBattleField_1x8(View view) {
    }

    public void clickCreateBattleField_1x9(View view) {
    }

    public void clickCreateBattleField_1x10(View view) {
    }

    public void clickCreateBattleField_2x1(View view) {
    }

    public void clickCreateBattleField_2x2(View view) {
    }

    public void clickCreateBattleField_2x3(View view) {
    }

    public void clickCreateBattleField_2x4(View view) {
    }

    public void clickCreateBattleField_2x5(View view) {
    }

    public void clickCreateBattleField_2x6(View view) {
    }

    public void clickCreateBattleField_2x7(View view) {
    }

    public void clickCreateBattleField_2x8(View view) {
    }

    public void clickCreateBattleField_2x9(View view) {
    }

    public void clickCreateBattleField_2x10(View view) {
    }

    public void clickCreateBattleField_3x1(View view) {
    }

    public void clickCreateBattleField_3x2(View view) {
    }

    public void clickCreateBattleField_3x3(View view) {
    }

    public void clickCreateBattleField_3x4(View view) {
    }

    public void clickCreateBattleField_3x5(View view) {
    }

    public void clickCreateBattleField_3x6(View view) {
    }

    public void clickCreateBattleField_3x7(View view) {
    }

    public void clickCreateBattleField_3x8(View view) {
    }

    public void clickCreateBattleField_3x9(View view) {
    }

    public void clickCreateBattleField_3x10(View view) {
    }

    public void clickCreateBattleField_4x1(View view) {
    }

    public void clickCreateBattleField_4x2(View view) {
    }

    public void clickCreateBattleField_4x3(View view) {
    }

    public void clickCreateBattleField_4x4(View view) {
    }

    public void clickCreateBattleField_4x5(View view) {
    }

    public void clickCreateBattleField_4x6(View view) {
    }

    public void clickCreateBattleField_4x7(View view) {
    }

    public void clickCreateBattleField_4x8(View view) {
    }

    public void clickCreateBattleField_4x9(View view) {
    }

    public void clickCreateBattleField_4x10(View view) {
    }

    public void clickCreateBattleField_5x1(View view) {
    }

    public void clickCreateBattleField_5x2(View view) {
    }

    public void clickCreateBattleField_5x3(View view) {
    }

    public void clickCreateBattleField_5x4(View view) {
    }

    public void clickCreateBattleField_5x5(View view) {
    }

    public void clickCreateBattleField_5x6(View view) {
    }

    public void clickCreateBattleField_5x7(View view) {
    }

    public void clickCreateBattleField_5x8(View view) {
    }

    public void clickCreateBattleField_5x9(View view) {
    }

    public void clickCreateBattleField_5x10(View view) {
    }

    public void clickCreateBattleField_6x1(View view) {
    }

    public void clickCreateBattleField_6x2(View view) {
    }

    public void clickCreateBattleField_6x3(View view) {
    }

    public void clickCreateBattleField_6x4(View view) {
    }

    public void clickCreateBattleField_6x5(View view) {
    }

    public void clickCreateBattleField_6x6(View view) {
    }

    public void clickCreateBattleField_6x7(View view) {
    }

    public void clickCreateBattleField_6x8(View view) {
    }

    public void clickCreateBattleField_6x9(View view) {
    }

    public void clickCreateBattleField_6x10(View view) {
    }

    public void clickCreateBattleField_7x1(View view) {
    }

    public void clickCreateBattleField_7x2(View view) {
    }

    public void clickCreateBattleField_7x3(View view) {
    }

    public void clickCreateBattleField_7x4(View view) {
    }

    public void clickCreateBattleField_7x5(View view) {
    }

    public void clickCreateBattleField_7x6(View view) {
    }

    public void clickCreateBattleField_7x7(View view) {
    }

    public void clickCreateBattleField_7x8(View view) {
    }

    public void clickCreateBattleField_7x9(View view) {
    }

    public void clickCreateBattleField_7x10(View view) {
    }

    public void clickCreateBattleField_8x1(View view) {
    }

    public void clickCreateBattleField_8x2(View view) {
    }

    public void clickCreateBattleField_8x3(View view) {
    }

    public void clickCreateBattleField_8x4(View view) {
    }

    public void clickCreateBattleField_8x5(View view) {
    }

    public void clickCreateBattleField_8x6(View view) {
    }

    public void clickCreateBattleField_8x7(View view) {
    }

    public void clickCreateBattleField_8x8(View view) {
    }

    public void clickCreateBattleField_8x9(View view) {
    }

    public void clickCreateBattleField_8x10(View view) {
    }

    public void clickCreateBattleField_9x1(View view) {
    }

    public void clickCreateBattleField_9x2(View view) {
    }

    public void clickCreateBattleField_9x3(View view) {
    }

    public void clickCreateBattleField_9x4(View view) {
    }

    public void clickCreateBattleField_9x5(View view) {
    }

    public void clickCreateBattleField_9x6(View view) {
    }

    public void clickCreateBattleField_9x7(View view) {
    }

    public void clickCreateBattleField_9x8(View view) {
    }

    public void clickCreateBattleField_9x9(View view) {
    }

    public void clickCreateBattleField_9x10(View view) {
    }

    public void clickCreateBattleField_10x1(View view) {
    }

    public void clickCreateBattleField_10x2(View view) {
    }

    public void clickCreateBattleField_10x3(View view) {
    }

    public void clickCreateBattleField_10x4(View view) {
    }

    public void clickCreateBattleField_10x5(View view) {
    }

    public void clickCreateBattleField_10x6(View view) {
    }

    public void clickCreateBattleField_10x7(View view) {
    }

    public void clickCreateBattleField_10x8(View view) {
    }

    public void clickCreateBattleField_10x9(View view) {
    }

    public void clickCreateBattleField_10x10(View view) {
    }
}
