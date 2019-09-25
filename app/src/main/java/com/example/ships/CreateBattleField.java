package com.example.ships;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class CreateBattleField extends AppCompatActivity {

    BattleField battleFieldPlayerCreateBattleFieldActivity = new BattleField();
    TextView[][] TextViewArrayActivityCreateBattleField = new TextView[10][10];
    TextView[] FourMastsShip = new TextView[4];
    TextView[] ThreeMastsShip = new TextView[3];
    TextView[] TwoMastsShip = new TextView[2];
    TextView[] OneMastsShip = new TextView[1];
    TextView FourMastsCounter;
    TextView ThreeMastsCounter;
    TextView TwoMastsCounter;
    TextView OneMastsCounter;
    private int fourMastsCounter;
    private int threeMastsCounter1;
    private int threeMastsCounter2;
    private int twoMastsCounter1;
    private int twoMastsCounter2;
    private int twoMastsCounter3;
    private int oneMastsCounter1;
    private int oneMastsCounter2;
    private int oneMastsCounter3;
    private int oneMastsCounter4;
    int leftFourMasts;
    int leftThreeMasts;
    int leftTwoMasts;
    int leftOneMasts;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_battle_field);

        initializeBattleFieldActivityRandomGamePlayerOne(TextViewArrayActivityCreateBattleField);
        FourMastsCounter = (TextView)findViewById(R.id.fourMastsCounterTextView);
        ThreeMastsCounter = (TextView)findViewById(R.id.threeMastsCounterTextView);
        TwoMastsCounter = (TextView)findViewById(R.id.twoMastsCounterTextView);
        OneMastsCounter = (TextView)findViewById(R.id.oneMastsCounterTextView);
        initializeFourMastsShipTextView(FourMastsShip);
        initializeThreeMastsShipTextView(ThreeMastsShip);
        initializeTwoMastsShipTextView(TwoMastsShip);
        initializeOneMastsShipTextView(OneMastsShip);

//
//        battleFieldPlayerCreateBattleFieldActivity.battleField[0][0].setShip(true);
//        battleFieldPlayerCreateBattleFieldActivity.battleField[0][0].setNumberOfMasts(1);
//        battleFieldPlayerCreateBattleFieldActivity.battleField[0][0].setShipNumber(1);
//
//        battleFieldPlayerCreateBattleFieldActivity.battleField[0][3].setShip(true);
//        battleFieldPlayerCreateBattleFieldActivity.battleField[0][3].setNumberOfMasts(1);
//        battleFieldPlayerCreateBattleFieldActivity.battleField[0][3].setShipNumber(3);
//
//        battleFieldPlayerCreateBattleFieldActivity.battleField[1][3].setShip(true);
//        battleFieldPlayerCreateBattleFieldActivity.battleField[1][3].setNumberOfMasts(1);
//        battleFieldPlayerCreateBattleFieldActivity.battleField[1][3].setShipNumber(2);

        checkShipsOnBattleField(battleFieldPlayerCreateBattleFieldActivity);
        checkLeftShips();
        updateTextViewCounters();

    }

    private void checkLeftShips() {
        leftFourMasts=1-(fourMastsShipBoolean() ? 1 : 0);
        leftThreeMasts=2-((threeMastsShip1Boolean()?1:0)+(threeMastsShip2Boolean()?1:0));
        leftTwoMasts=3-((twoMastsShip1Boolean()?1:0)+(twoMastsShip2Boolean()?1:0)+(twoMastsShip3Boolean()?1:0));
        leftOneMasts=4-((oneMastsShip1Boolean()?1:0)+(oneMastsShip2Boolean()?1:0)+(oneMastsShip3Boolean()?1:0)+(oneMastsShip4Boolean()?1:0));
    }

    private void updateTextViewCounters() {
        FourMastsCounter.setText(String.valueOf(leftFourMasts));
        ThreeMastsCounter.setText(String.valueOf(leftThreeMasts));
        TwoMastsCounter.setText(String.valueOf(leftTwoMasts));
        OneMastsCounter.setText(String.valueOf(leftOneMasts));

    }


    private void checkShipsOnBattleField(BattleField battleFieldPlayerCreateBattleFieldActivity) {
        fourMastsCounter=0;
        threeMastsCounter1=0;
        threeMastsCounter2=0;
        twoMastsCounter1=0;
        twoMastsCounter2=0;
        twoMastsCounter3=0;
        oneMastsCounter1=0;
        oneMastsCounter2=0;
        oneMastsCounter3=0;
        oneMastsCounter4=0;
        for(int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                if(battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].isShip()){
                    if(battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].getNumberOfMasts()==4){
                        fourMastsCounter++;
                    }
                    else if(battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].getNumberOfMasts()==3&&
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].getShipNumber()==1){
                        threeMastsCounter1++;
                    }
                    else if(battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].getNumberOfMasts()==3&&
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].getShipNumber()==2){
                        threeMastsCounter2++;
                    }
                    else if(battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].getNumberOfMasts()==2&&
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].getShipNumber()==1){
                        twoMastsCounter1++;
                    }
                    else if(battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].getNumberOfMasts()==2&&
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].getShipNumber()==2){
                        twoMastsCounter2++;
                    }
                    else if(battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].getNumberOfMasts()==2&&
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].getShipNumber()==3){
                        twoMastsCounter3++;
                    }
                    else if(battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].getNumberOfMasts()==1&&
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].getShipNumber()==1){
                        oneMastsCounter1++;
                    }
                    else if(battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].getNumberOfMasts()==1&&
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].getShipNumber()==2){
                        oneMastsCounter2++;
                    }
                    else if(battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].getNumberOfMasts()==1&&
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].getShipNumber()==3){
                        oneMastsCounter3++;
                    }
                    else if(battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].getNumberOfMasts()==1&&
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].getShipNumber()==4){
                        oneMastsCounter4++;
                    }else;
                }else;
            }
        }
    }

    private void initializeOneMastsShipTextView(TextView[] oneMastsShip) {
        oneMastsShip[0]=(TextView)findViewById(R.id.playerCellGameCreateBattleFieldOneMasts1);
    }

    private void initializeTwoMastsShipTextView(TextView[] twoMastsShip) {
        twoMastsShip[0]=(TextView)findViewById(R.id.playerCellGameCreateBattleFieldTwoMasts1);
        twoMastsShip[1]=(TextView)findViewById(R.id.playerCellGameCreateBattleFieldTwoMasts2);
    }

    private void initializeThreeMastsShipTextView(TextView[] threeMastsShip) {
        threeMastsShip[0]=(TextView)findViewById(R.id.playerCellGameCreateBattleFieldThreeMasts1);
        threeMastsShip[1]=(TextView)findViewById(R.id.playerCellGameCreateBattleFieldThreeMasts2);
        threeMastsShip[2]=(TextView)findViewById(R.id.playerCellGameCreateBattleFieldThreeMasts3);
    }

    private void initializeFourMastsShipTextView(TextView[] fourMastsShip) {
        fourMastsShip[0]=(TextView)findViewById(R.id.playerCellGameCreateBattleFieldFourMasts1);
        fourMastsShip[1]=(TextView)findViewById(R.id.playerCellGameCreateBattleFieldFourMasts2);
        fourMastsShip[2]=(TextView)findViewById(R.id.playerCellGameCreateBattleFieldFourMasts3);
        fourMastsShip[3]=(TextView)findViewById(R.id.playerCellGameCreateBattleFieldFourMasts4);
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

        textViewArrayActivityCreateBattleField[8][0]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_9x1);
        textViewArrayActivityCreateBattleField[8][1]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_9x2);
        textViewArrayActivityCreateBattleField[8][2]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_9x3);
        textViewArrayActivityCreateBattleField[8][3]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_9x4);
        textViewArrayActivityCreateBattleField[8][4]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_9x5);
        textViewArrayActivityCreateBattleField[8][5]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_9x6);
        textViewArrayActivityCreateBattleField[8][6]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_9x7);
        textViewArrayActivityCreateBattleField[8][7]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_9x8);
        textViewArrayActivityCreateBattleField[8][8]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_9x9);
        textViewArrayActivityCreateBattleField[8][9]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_9x10);

        textViewArrayActivityCreateBattleField[9][0]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_10x1);
        textViewArrayActivityCreateBattleField[9][1]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_10x2);
        textViewArrayActivityCreateBattleField[9][2]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_10x3);
        textViewArrayActivityCreateBattleField[9][3]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_10x4);
        textViewArrayActivityCreateBattleField[9][4]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_10x5);
        textViewArrayActivityCreateBattleField[9][5]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_10x6);
        textViewArrayActivityCreateBattleField[9][6]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_10x7);
        textViewArrayActivityCreateBattleField[9][7]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_10x8);
        textViewArrayActivityCreateBattleField[9][8]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_10x9);
        textViewArrayActivityCreateBattleField[9][9]=(TextView)findViewById(R.id.playerCellGameCreateBattleField_10x10);
    }

    public void onClickBack(View view) {
    }

    public void onClickStartGame(View view) {
        Intent intent = new Intent(getApplicationContext(),ChooseGameLevel.class);
        startActivity(intent);
        finish();
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
    private boolean fourMastsShipBoolean(){
        return fourMastsCounter==4;
    }
    private boolean threeMastsShip1Boolean(){
        return threeMastsCounter1==3;
    }
    private boolean threeMastsShip2Boolean(){
        return threeMastsCounter2==3;
    }
    private boolean twoMastsShip1Boolean(){
        return twoMastsCounter1==2;
    }
    private boolean twoMastsShip2Boolean(){
        return twoMastsCounter2==2;
    }
    private boolean twoMastsShip3Boolean(){
        return twoMastsCounter3==2;
    }
    private boolean oneMastsShip1Boolean(){
        return oneMastsCounter1==1;
    }
    private boolean oneMastsShip2Boolean(){
        return oneMastsCounter2==1;
    }
    private boolean oneMastsShip3Boolean(){
        return oneMastsCounter3==1;
    }
    private boolean oneMastsShip4Boolean(){
        return oneMastsCounter4==1;
    }
}
