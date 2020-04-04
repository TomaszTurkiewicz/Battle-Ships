package com.example.ships;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ships.classes.BattleField;
import com.example.ships.classes.PointIJ;
import com.example.ships.singletons.BattleFieldPlayerOneSingleton;

//TODO change layout... constraint
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
    int choosenShip;
    int shipNumberFlag;
    PointIJ firstPointMastsShip = new PointIJ();
    PointIJ secondPointMastsShip = new PointIJ();
    int shipNumber;
    Button startButton;





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
        startButton=findViewById(R.id.playButton);
            updateScreen();

    }

    private void disableOneMastsTextView(){
        setGrey(OneMastsShip,0);
        OneMastsShip[0].setClickable(false);
        choosenShip=0;
    }

    private void disableTwoMastsTextView(){
        setGrey(TwoMastsShip,0);
        setGrey(TwoMastsShip,1);
        TwoMastsShip[0].setClickable(false);
        TwoMastsShip[1].setClickable(false);
        choosenShip=0;
    }

    private void disableThreeMastsTextView(){
        setGrey(ThreeMastsShip,0);
        setGrey(ThreeMastsShip,1);
        setGrey(ThreeMastsShip,2);
        ThreeMastsShip[0].setClickable(false);
        ThreeMastsShip[1].setClickable(false);
        ThreeMastsShip[2].setClickable(false);
        choosenShip=0;
    }

    private void disableFourMastsTextView(){
        setGrey(FourMastsShip,0);
        setGrey(FourMastsShip,1);
        setGrey(FourMastsShip,2);
        setGrey(FourMastsShip,3);
        FourMastsShip[0].setClickable(false);
        FourMastsShip[1].setClickable(false);
        FourMastsShip[2].setClickable(false);
        FourMastsShip[3].setClickable(false);
        choosenShip=0;
    }

    private void updateScreen(){
        checkShipsOnBattleField(battleFieldPlayerCreateBattleFieldActivity);
        checkLeftShips();
        updateTextViewCounters();
        updateTextViewCountersShips();
        updateClicableBattleField();
        updateStartButton();
    }

    private void updateStartButton() {
        if(leftFourMasts==0&&leftThreeMasts==0&&leftTwoMasts==0&&leftOneMasts==0){
            startButton.setClickable(true);
            startButton.setVisibility(View.VISIBLE);
        }
        else{
            startButton.setClickable(false);
            startButton.setVisibility(View.GONE);

        }
    }

    private void updateBattleField() {
        for(int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                if(battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].isShip()){
                    setShipColor(TextViewArrayActivityCreateBattleField,i,j);
                }else{
                    setNoShipColor(TextViewArrayActivityCreateBattleField,i,j);
                }
            }
        }
    }

    private void setNoShipColor(TextView[][] TextView, int i, int j) {
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            TextView[i][j].setBackgroundDrawable(getResources().getDrawable(R.drawable.battle_cell));
        } else {
            TextView[i][j].setBackground(getResources().getDrawable(R.drawable.battle_cell));
        }
    }

    private void setShipColor(TextView[][] TextView, int i, int j) {
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            TextView[i][j].setBackgroundDrawable(getResources().getDrawable(R.drawable.ship_cell));
        } else {
            TextView[i][j].setBackground(getResources().getDrawable(R.drawable.ship_cell));
        }
    }

    private void setGreyColor(TextView[][] TextView, int i, int j) {
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            TextView[i][j].setBackgroundDrawable(getResources().getDrawable(R.drawable.battle_cell_hidden));
        } else {
            TextView[i][j].setBackground(getResources().getDrawable(R.drawable.battle_cell_hidden));
        }
    }

    private void updateTextViewCountersShips() {
        updateTextViewFourMastsShipCounter();
        updateTextViewThreeMastsShipCounter();
        updateTextViewTwoMastsShipCounter();
        updateTextViewOneMastsShipCounter();
    }

    private void updateTextViewOneMastsShipCounter() {
        if(leftOneMasts==0){
            disableOneMastsTextView();
        }else{
            enableOneMastsTextView();
        }

    }

    private void enableOneMastsTextView() {
        setBlank(OneMastsShip,0);
        OneMastsShip[0].setClickable(true);
    }

    private void updateTextViewTwoMastsShipCounter() {
        if(leftTwoMasts==0){
            disableTwoMastsTextView();
        }else{
            enableTwoMastsTextView();
        }
    }

    private void enableTwoMastsTextView() {
        setBlank(TwoMastsShip,0);
        setBlank(TwoMastsShip,1);
        TwoMastsShip[0].setClickable(true);
        TwoMastsShip[1].setClickable(true);
    }

    private void updateTextViewThreeMastsShipCounter() {
        if(leftThreeMasts==0){
            disableThreeMastsTextView();
        }else{
            enableThreeMastsTextView();
        }
    }

    private void enableThreeMastsTextView() {

        if(choosenShip==3){
        setRed(ThreeMastsShip,0);
        setRed(ThreeMastsShip,1);
        setRed(ThreeMastsShip,2);
        ThreeMastsShip[0].setClickable(true);
        ThreeMastsShip[1].setClickable(true);
        ThreeMastsShip[2].setClickable(true);
    }else{
            setBlank(ThreeMastsShip,0);
            setBlank(ThreeMastsShip,1);
            setBlank(ThreeMastsShip,2);
            ThreeMastsShip[0].setClickable(true);
            ThreeMastsShip[1].setClickable(true);
            ThreeMastsShip[2].setClickable(true);
        }
    }

    private void updateTextViewFourMastsShipCounter() {
        if(leftFourMasts==0){
            disableFourMastsTextView();
        }else{
            enableFourMastsTextView();
        }
    }

    private void enableFourMastsTextView() {
        setBlank(FourMastsShip,0);
        setBlank(FourMastsShip,1);
        setBlank(FourMastsShip,2);
        setBlank(FourMastsShip,3);
        FourMastsShip[0].setClickable(true);
        FourMastsShip[1].setClickable(true);
        FourMastsShip[2].setClickable(true);
        FourMastsShip[3].setClickable(true);
    }

    private void setBlank (TextView[] TextView, int i){
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            TextView[i].setBackgroundDrawable(getResources().getDrawable(R.drawable.battle_cell));
        } else {
            TextView[i].setBackground(getResources().getDrawable(R.drawable.battle_cell));
        }
    }

    private void setRed (TextView[] TextView, int i){
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            TextView[i].setBackgroundDrawable(getResources().getDrawable(R.drawable.red_ship));
        } else {
            TextView[i].setBackground(getResources().getDrawable(R.drawable.red_ship));
        }
    }

    private void setGrey (TextView[] TextView, int i){
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            TextView[i].setBackgroundDrawable(getResources().getDrawable(R.drawable.widmo_ship_cell));
        } else {
            TextView[i].setBackground(getResources().getDrawable(R.drawable.widmo_ship_cell));
        }
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
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void onClickStartGame(View view) {
        BattleFieldPlayerOneSingleton.getInstance().storeBattleField(battleFieldPlayerCreateBattleFieldActivity);
        Intent intent = new Intent(getApplicationContext(),ChooseGameLevel.class);
        startActivity(intent);
        finish();
    }

    public void onClickputOneMastsShip(View view) {
        deleteUncomplitedShips();
        choosenShip=1;
        checkShipsOnBattleField(battleFieldPlayerCreateBattleFieldActivity);
        if(!oneMastsShip1Boolean()){
            shipNumberFlag=1;
        }
        else{
            if(!oneMastsShip2Boolean()){
                shipNumberFlag=2;
            }
            else{
                if(!oneMastsShip3Boolean()){
                    shipNumberFlag=3;
                }else{
                    if(!oneMastsShip4Boolean()){
                        shipNumberFlag=4;
                    }
                }
            }
        }
        updateActiveTextViewFourMastsShipCounter(choosenShip);
    }

    public void onClickputTwoMastsShip(View view) {
        deleteUncomplitedShips();
        choosenShip=2;
        checkShipsOnBattleField(battleFieldPlayerCreateBattleFieldActivity);
        if(!twoMastsShip1Boolean()){
            shipNumberFlag=1;
        }
        else{
            if(!twoMastsShip2Boolean()){
                shipNumberFlag=2;
            }else{
                if(!twoMastsShip3Boolean()){
                    shipNumberFlag=3;
                }
            }
        }
        updateActiveTextViewFourMastsShipCounter(choosenShip);
    }

    public void onClickputThreeMastsShip(View view) {
        deleteUncomplitedShips();
        choosenShip=3;
        checkShipsOnBattleField(battleFieldPlayerCreateBattleFieldActivity);
        if(!threeMastsShip1Boolean()){
            shipNumberFlag=1;

        }else{
            if(!threeMastsShip2Boolean()){
                shipNumberFlag=2;
            }
            else;
        }
        updateActiveTextViewFourMastsShipCounter(choosenShip);
    }

    public void onClickputFourMastsShip(View view) {
        deleteUncomplitedShips();
        choosenShip=4;
        shipNumberFlag=1;
        updateActiveTextViewFourMastsShipCounter(choosenShip);
    }

    private void updateActiveTextViewFourMastsShipCounter(int choosen) {
        if( choosen==4){
            activeFourMasts();
        } else if (choosen==3){
            activeThreeMasts();
        }else if (choosen==2){
            activeTwoMasts();
        }else if (choosen==1){
            activeOneMasts();
        }else if(choosen==5){
            activeNone();
        }
    }

    private void activeNone() {
        if(leftOneMasts!=0){
            setBlankOneMasts();
        }
        if(leftFourMasts!=0){
            setBlankFourMasts();
        }else;
        if(leftThreeMasts!=0){
            setBlankThreeMasts();
        }else;
        if(leftTwoMasts!=0){
            setBlankTwoMasts();
        }else;
    }

    private void activeOneMasts() {
        setRedOneMasts();
        if(leftFourMasts!=0){
            setBlankFourMasts();
        }else;
        if(leftThreeMasts!=0){
            setBlankThreeMasts();
        }else;
        if(leftTwoMasts!=0){
            setBlankTwoMasts();
        }else;
    }

    private void setRedOneMasts() {
        setRed(OneMastsShip,0);
    }

    private void activeTwoMasts() {
        setRedTwoMasts();
        if(leftFourMasts!=0){
            setBlankFourMasts();
        }else;
        if(leftThreeMasts!=0){
            setBlankThreeMasts();
        }else;
        if(leftOneMasts!=0){
            setBlankOneMasts();
        }
    }

    private void setRedTwoMasts() {
        setRed(TwoMastsShip,0);
        setRed(TwoMastsShip,1);
    }

    private void activeThreeMasts() {
        setRedThreeMasts();
        if(leftFourMasts!=0){
            setBlankFourMasts();
        }else;
        if (leftTwoMasts!=0){
            setBlankTwoMasts();
        }else;
        if (leftOneMasts!=0){
            setBlankOneMasts();
        }else;
    }

    private void setBlankFourMasts() {
        setBlank(FourMastsShip,0);
        setBlank(FourMastsShip,1);
        setBlank(FourMastsShip,2);
        setBlank(FourMastsShip,3);
    }

    private void setRedThreeMasts() {
        setRed(ThreeMastsShip,0);
        setRed(ThreeMastsShip,1);
        setRed(ThreeMastsShip,2);
    }

    private void activeFourMasts() {
        setRedFourMasts();
        if(leftThreeMasts!=0){
            setBlankThreeMasts();
        }else;
        if(leftTwoMasts!=0){
            setBlankTwoMasts();
        }else;
        if(leftOneMasts!=0){
            setBlankOneMasts();
        }else;
    }

    private void setBlankOneMasts() {
        setBlank(OneMastsShip,0);
    }

    private void setBlankTwoMasts() {
        setBlank(TwoMastsShip,0);
        setBlank(TwoMastsShip,1);
    }

    private void setBlankThreeMasts() {
        setBlank(ThreeMastsShip,0);
        setBlank(ThreeMastsShip,1);
        setBlank(ThreeMastsShip,2);
    }

    private void setRedFourMasts() {
        setRed(FourMastsShip,0);
        setRed(FourMastsShip,1);
        setRed(FourMastsShip,2);
        setRed(FourMastsShip,3);
    }

    public void putShip(int i, int j, int numberOfMasts){
        if(numberOfMasts==1||numberOfMasts==2||numberOfMasts==3||numberOfMasts==4){
            if(numberOfMasts==4){
                checkShipsOnBattleField(battleFieldPlayerCreateBattleFieldActivity);
                if(fourMastsCounter==0){
                    battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setNumberOfMasts(numberOfMasts);
                    battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShipNumber(1);
                    battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShip(true);
                    firstPointMastsShip.setI(i);
                    firstPointMastsShip.setJ(j);
                    checkShipsOnBattleField(battleFieldPlayerCreateBattleFieldActivity);
                    setShipColor(TextViewArrayActivityCreateBattleField,i,j);
                    TextViewArrayActivityCreateBattleField[i][j].setClickable(false);
                    //                    updateBattleField();
                }else if(fourMastsCounter==1){
                    if(conditionIJ(i,j,numberOfMasts)) {
                        battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setNumberOfMasts(numberOfMasts);
                        battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShipNumber(1);
                        battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShip(true);
                        secondPointMastsShip.setI(i);
                        secondPointMastsShip.setJ(j);
                        checkShipsOnBattleField(battleFieldPlayerCreateBattleFieldActivity);
                        setShipColor(TextViewArrayActivityCreateBattleField,i,j);
                        TextViewArrayActivityCreateBattleField[i][j].setClickable(false);
//                        updateBattleField();
                    }
                }
                else if (fourMastsCounter==2){
                    if(firstPointMastsShip.getI()== secondPointMastsShip.getI()){
                        if(conditionI(i,j,numberOfMasts)) {
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setNumberOfMasts(numberOfMasts);
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShipNumber(1);
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShip(true);
                            checkShipsOnBattleField(battleFieldPlayerCreateBattleFieldActivity);
                            setShipColor(TextViewArrayActivityCreateBattleField,i,j);
                            TextViewArrayActivityCreateBattleField[i][j].setClickable(false);
                            //                            updateBattleField();
                        }
                    }else if(firstPointMastsShip.getJ()== secondPointMastsShip.getJ()){
                        if(conditionJ(i,j,numberOfMasts)) {
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setNumberOfMasts(numberOfMasts);
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShipNumber(1);
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShip(true);
                            checkShipsOnBattleField(battleFieldPlayerCreateBattleFieldActivity);
                            setShipColor(TextViewArrayActivityCreateBattleField,i,j);
                            TextViewArrayActivityCreateBattleField[i][j].setClickable(false);
//                            updateBattleField();

                        }
                    }
                }
                else if(fourMastsCounter==3){
                    if(firstPointMastsShip.getI()== secondPointMastsShip.getI()){
                        if(conditionI(i,j,numberOfMasts)) {
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setNumberOfMasts(numberOfMasts);
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShipNumber(1);
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShip(true);
                            updateScreen();
                            updateActiveTextViewFourMastsShipCounter(5);
                            choosenShip=0;
  //                          updateClicableBattleField();

                        }
                    }else if(firstPointMastsShip.getJ()== secondPointMastsShip.getJ()){
                        if(conditionJ(i,j,numberOfMasts)) {
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setNumberOfMasts(numberOfMasts);
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShipNumber(1);
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShip(true);
                            updateScreen();
                            updateActiveTextViewFourMastsShipCounter(5);
                            choosenShip=0;
 //                           updateClicableBattleField();
                        }
                    }

                }
                else;
            }
            else if(numberOfMasts==3){
                if (shipNumberFlag == 1) {
                    if(threeMastsCounter1==0){
                        battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setNumberOfMasts(numberOfMasts);
                        battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShipNumber(shipNumberFlag);
                        battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShip(true);
                        firstPointMastsShip.setI(i);
                        firstPointMastsShip.setJ(j);
                        checkShipsOnBattleField(battleFieldPlayerCreateBattleFieldActivity);
                        setShipColor(TextViewArrayActivityCreateBattleField,i,j);
                        TextViewArrayActivityCreateBattleField[i][j].setClickable(false);
//                        updateBattleField();
                    }
                    else if(threeMastsCounter1==1){
                        if(conditionIJ(i,j,numberOfMasts)) {
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setNumberOfMasts(numberOfMasts);
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShipNumber(shipNumberFlag);
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShip(true);
                            secondPointMastsShip.setI(i);
                            secondPointMastsShip.setJ(j);
                            checkShipsOnBattleField(battleFieldPlayerCreateBattleFieldActivity);
                            setShipColor(TextViewArrayActivityCreateBattleField,i,j);
                            TextViewArrayActivityCreateBattleField[i][j].setClickable(false);
//                            updateBattleField();
                        }
                    }else{
                        if(firstPointMastsShip.getI()== secondPointMastsShip.getI()){
                            if(conditionI(i,j,numberOfMasts)) {
                                battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setNumberOfMasts(numberOfMasts);
                                battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShipNumber(shipNumberFlag);
                                battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShip(true);
                                updateScreen();
                                updateActiveTextViewFourMastsShipCounter(5);
                                choosenShip=0;
   //                             updateClicableBattleField();
                            }
                        }else if(firstPointMastsShip.getJ()== secondPointMastsShip.getJ()){
                            if(conditionJ(i,j,numberOfMasts)) {
                                battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setNumberOfMasts(numberOfMasts);
                                battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShipNumber(shipNumberFlag);
                                battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShip(true);
                                updateScreen();
                                updateActiveTextViewFourMastsShipCounter(5);
                                choosenShip=0;
   //                             updateClicableBattleField();
                            }
                        }

                    }
                }else if(shipNumberFlag==2) {
                    if (threeMastsCounter2 == 0) {
                        battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setNumberOfMasts(numberOfMasts);
                        battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShipNumber(shipNumberFlag);
                        battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShip(true);
                        firstPointMastsShip.setI(i);
                        firstPointMastsShip.setJ(j);
                        checkShipsOnBattleField(battleFieldPlayerCreateBattleFieldActivity);
                        setShipColor(TextViewArrayActivityCreateBattleField,i,j);
                        TextViewArrayActivityCreateBattleField[i][j].setClickable(false);
                        //                        updateBattleField();
                    } else if (threeMastsCounter2 == 1) {
                        if (conditionIJ(i, j, numberOfMasts)) {
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setNumberOfMasts(numberOfMasts);
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShipNumber(shipNumberFlag);
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShip(true);
                            secondPointMastsShip.setI(i);
                            secondPointMastsShip.setJ(j);
                            checkShipsOnBattleField(battleFieldPlayerCreateBattleFieldActivity);
                            setShipColor(TextViewArrayActivityCreateBattleField,i,j);
                            TextViewArrayActivityCreateBattleField[i][j].setClickable(false);
 //                           updateBattleField();
                        }
                    } else {
                        if (firstPointMastsShip.getI() == secondPointMastsShip.getI()) {
                            if (conditionI(i, j, numberOfMasts)) {
                                battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setNumberOfMasts(numberOfMasts);
                                battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShipNumber(shipNumberFlag);
                                battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShip(true);
                                updateScreen();
                                updateActiveTextViewFourMastsShipCounter(5);
                                choosenShip=0;
    //                            updateClicableBattleField();
                            }
                        } else if (firstPointMastsShip.getJ() == secondPointMastsShip.getJ()) {
                            if (conditionJ(i, j, numberOfMasts)) {
                                battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setNumberOfMasts(numberOfMasts);
                                battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShipNumber(shipNumberFlag);
                                battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShip(true);
                                updateScreen();
                                updateActiveTextViewFourMastsShipCounter(5);
                                choosenShip=0;
      //                          updateClicableBattleField();
                            }
                        }
                    }
                }
            }
            else if(numberOfMasts==2){
                if(shipNumberFlag==1){

                    if(twoMastsCounter1==0){
                        battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setNumberOfMasts(numberOfMasts);
                        battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShipNumber(shipNumberFlag);
                        battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShip(true);
                        checkShipsOnBattleField(battleFieldPlayerCreateBattleFieldActivity);
                        setShipColor(TextViewArrayActivityCreateBattleField,i,j);
                        TextViewArrayActivityCreateBattleField[i][j].setClickable(false);
 //                       updateBattleField();
                    }
                    else {
                        if (conditionIJ(i, j, numberOfMasts)) {
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setNumberOfMasts(numberOfMasts);
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShipNumber(shipNumberFlag);
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShip(true);
                            updateScreen();
                            updateActiveTextViewFourMastsShipCounter(5);
                            choosenShip=0;
//                            updateClicableBattleField();
                        }
                    }

                }else if(shipNumberFlag==2){

                    if(twoMastsCounter2==0){
                        battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setNumberOfMasts(numberOfMasts);
                        battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShipNumber(shipNumberFlag);
                        battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShip(true);
                        checkShipsOnBattleField(battleFieldPlayerCreateBattleFieldActivity);
                        setShipColor(TextViewArrayActivityCreateBattleField,i,j);
                        TextViewArrayActivityCreateBattleField[i][j].setClickable(false);
 //                       updateBattleField();
                    }
                    else {
                        if (conditionIJ(i, j, numberOfMasts)) {
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setNumberOfMasts(numberOfMasts);
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShipNumber(shipNumberFlag);
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShip(true);
                            updateScreen();
                            updateActiveTextViewFourMastsShipCounter(5);
                            choosenShip=0;
 //                           updateClicableBattleField();
                        }
                    }

                }else if(shipNumberFlag==3){

                    if(twoMastsCounter3==0){
                        battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setNumberOfMasts(numberOfMasts);
                        battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShipNumber(shipNumberFlag);
                        battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShip(true);
                        checkShipsOnBattleField(battleFieldPlayerCreateBattleFieldActivity);
                        setShipColor(TextViewArrayActivityCreateBattleField,i,j);
                        TextViewArrayActivityCreateBattleField[i][j].setClickable(false);
 //                       updateBattleField();
                    }
                    else {
                        if (conditionIJ(i, j, numberOfMasts)) {
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setNumberOfMasts(numberOfMasts);
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShipNumber(shipNumberFlag);
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShip(true);
                            updateScreen();
                            updateActiveTextViewFourMastsShipCounter(5);
                            choosenShip=0;
 //                           updateClicableBattleField();
                        }
                    }

                }else;
            }
            else if(numberOfMasts==1){

                battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setNumberOfMasts(numberOfMasts);
                battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShipNumber(shipNumberFlag);
                battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShip(true);
                updateScreen();
                updateActiveTextViewFourMastsShipCounter(5);
                choosenShip=0;
 //               updateClicableBattleField();
            }
            else;
        }else if(numberOfMasts==5){
            makeFieldZero(i,j);
            deleteUncomplitedShips();
        }
    }

    private void updateClicableBattleField() {
        for(int i=0;i<10;i++){
            for(int j=0;j<10;j++){

                if(i==0&&j==0){
                    if(battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].isShip()||
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i+1][j].isShip()||
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j+1].isShip()||
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i+1][j+1].isShip()){
                        TextViewArrayActivityCreateBattleField[i][j].setClickable(false);
                        if(battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].isShip()){
                        setShipColor(TextViewArrayActivityCreateBattleField,i,j);
                        }else{
                            setGreyColor(TextViewArrayActivityCreateBattleField,i,j);
                        }
                    } else{
                        setNoShipColor(TextViewArrayActivityCreateBattleField,i,j);
                        TextViewArrayActivityCreateBattleField[i][j].setClickable(true);

                    }
                }
                else if((i > 0) && (i < 9) && j == 0){
                    if(battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].isShip()||
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i-1][j].isShip()||
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i+1][j].isShip()||
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j+1].isShip()||
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i-1][j+1].isShip()||
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i+1][j+1].isShip()){
                        TextViewArrayActivityCreateBattleField[i][j].setClickable(false);
                        if(battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].isShip()){
                            setShipColor(TextViewArrayActivityCreateBattleField,i,j);
                        }else{
                            setGreyColor(TextViewArrayActivityCreateBattleField,i,j);
                        }
                    }else{
                        setNoShipColor(TextViewArrayActivityCreateBattleField,i,j);
                        TextViewArrayActivityCreateBattleField[i][j].setClickable(true);
                    }
                }
                else if(i==9&&j==0){
                    if(battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].isShip()||
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i-1][j].isShip()||
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j+1].isShip()||
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i-1][j+1].isShip()){
                        TextViewArrayActivityCreateBattleField[i][j].setClickable(false);
                        if(battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].isShip()){
                            setShipColor(TextViewArrayActivityCreateBattleField,i,j);
                        }else{
                            setGreyColor(TextViewArrayActivityCreateBattleField,i,j);
                        }
                    }else{
                        setNoShipColor(TextViewArrayActivityCreateBattleField,i,j);
                        TextViewArrayActivityCreateBattleField[i][j].setClickable(true);
                    }
                }
                else if (i == 0 && ((j > 0) && (j < 9))){
                    if(battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].isShip()||
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i+1][j].isShip()||
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j+1].isShip()||
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i+1][j+1].isShip()||
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j-1].isShip()||
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i+1][j-1].isShip()){
                        TextViewArrayActivityCreateBattleField[i][j].setClickable(false);
                        if(battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].isShip()){
                            setShipColor(TextViewArrayActivityCreateBattleField,i,j);
                        }else{
                            setGreyColor(TextViewArrayActivityCreateBattleField,i,j);
                        }
                    }else{
                        setNoShipColor(TextViewArrayActivityCreateBattleField,i,j);
                        TextViewArrayActivityCreateBattleField[i][j].setClickable(true);
                    }
                }
                else if (((i > 0) && (i < 9)) && ((j > 0) && (j < 9))){
                    if(battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].isShip()||
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i-1][j].isShip()||
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i+1][j].isShip()||
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j+1].isShip()||
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i-1][j+1].isShip()||
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i+1][j+1].isShip()||
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j-1].isShip()||
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i-1][j-1].isShip()||
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i+1][j-1].isShip()){
                        TextViewArrayActivityCreateBattleField[i][j].setClickable(false);
                        if(battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].isShip()){
                            setShipColor(TextViewArrayActivityCreateBattleField,i,j);
                        }else{
                            setGreyColor(TextViewArrayActivityCreateBattleField,i,j);
                        }
                    }else{
                        setNoShipColor(TextViewArrayActivityCreateBattleField,i,j);
                        TextViewArrayActivityCreateBattleField[i][j].setClickable(true);
                    }
                }
                else if (i == 9 && ((j > 0) && (j < 9))) {
                    if(battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].isShip()||
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i-1][j].isShip()||
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j+1].isShip()||
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i-1][j+1].isShip()||
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j-1].isShip()||
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i-1][j-1].isShip()){
                        TextViewArrayActivityCreateBattleField[i][j].setClickable(false);
                        if(battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].isShip()){
                            setShipColor(TextViewArrayActivityCreateBattleField,i,j);
                        }else{
                            setGreyColor(TextViewArrayActivityCreateBattleField,i,j);
                        }
                    }else{
                        setNoShipColor(TextViewArrayActivityCreateBattleField,i,j);
                        TextViewArrayActivityCreateBattleField[i][j].setClickable(true);
                    }
                }
                else if (i == 0 && j == 9) {
                    if(battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].isShip()||
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i+1][j].isShip()||
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j-1].isShip()||
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i+1][j-1].isShip()){
                        TextViewArrayActivityCreateBattleField[i][j].setClickable(false);
                        if(battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].isShip()){
                            setShipColor(TextViewArrayActivityCreateBattleField,i,j);
                        }else{
                            setGreyColor(TextViewArrayActivityCreateBattleField,i,j);
                        }
                    }else{
                        setNoShipColor(TextViewArrayActivityCreateBattleField,i,j);
                        TextViewArrayActivityCreateBattleField[i][j].setClickable(true);
                    }
                }
                else if (((i > 0) && (i < 9)) && (j == 9)) {
                    if(battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].isShip()||
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i-1][j].isShip()||
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i+1][j].isShip()||
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j-1].isShip()||
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i-1][j-1].isShip()||
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i+1][j-1].isShip()){
                        TextViewArrayActivityCreateBattleField[i][j].setClickable(false);
                        if(battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].isShip()){
                            setShipColor(TextViewArrayActivityCreateBattleField,i,j);
                        }else{
                            setGreyColor(TextViewArrayActivityCreateBattleField,i,j);
                        }
                    }else{
                        setNoShipColor(TextViewArrayActivityCreateBattleField,i,j);
                        TextViewArrayActivityCreateBattleField[i][j].setClickable(true);
                    }
                }
                else if ((i == 9) && (j == 9)){
                    if(battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].isShip()||
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i-1][j].isShip()||
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j-1].isShip()||
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i-1][j-1].isShip()){
                        TextViewArrayActivityCreateBattleField[i][j].setClickable(false);
                        if(battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].isShip()){
                            setShipColor(TextViewArrayActivityCreateBattleField,i,j);
                        }else{
                            setGreyColor(TextViewArrayActivityCreateBattleField,i,j);
                        }
                    }else{
                        setNoShipColor(TextViewArrayActivityCreateBattleField,i,j);
                        TextViewArrayActivityCreateBattleField[i][j].setClickable(true);
                    }
                }
                else;
            }
        }
    }

    public boolean conditionIJ (int i, int j, int numberOfMasts) {
        if(i==0&&j==0){
            return battleFieldPlayerCreateBattleFieldActivity.battleField[i+1][j].getNumberOfMasts()==numberOfMasts||
                    battleFieldPlayerCreateBattleFieldActivity.battleField[i][j+1].getNumberOfMasts()==numberOfMasts;
        }
        else if((i > 0) && (i < 9) && j == 0){
           return battleFieldPlayerCreateBattleFieldActivity.battleField[i-1][j].getNumberOfMasts()==numberOfMasts||
                    battleFieldPlayerCreateBattleFieldActivity.battleField[i+1][j].getNumberOfMasts()==numberOfMasts||
                    battleFieldPlayerCreateBattleFieldActivity.battleField[i][j+1].getNumberOfMasts()==numberOfMasts;
        }
        else if(i==9&&j==0){
          return  battleFieldPlayerCreateBattleFieldActivity.battleField[i-1][j].getNumberOfMasts()==numberOfMasts||
                    battleFieldPlayerCreateBattleFieldActivity.battleField[i][j+1].getNumberOfMasts()==numberOfMasts;
        } else if (i == 0 && ((j > 0) && (j < 9))){
            return battleFieldPlayerCreateBattleFieldActivity.battleField[i+1][j].getNumberOfMasts()==numberOfMasts||
                    battleFieldPlayerCreateBattleFieldActivity.battleField[i][j-1].getNumberOfMasts()==numberOfMasts||
                    battleFieldPlayerCreateBattleFieldActivity.battleField[i][j+1].getNumberOfMasts()==numberOfMasts;
        }
         else if (((i > 0) && (i < 9)) && ((j > 0) && (j < 9))){
             return battleFieldPlayerCreateBattleFieldActivity.battleField[i-1][j].getNumberOfMasts()==numberOfMasts||
                     battleFieldPlayerCreateBattleFieldActivity.battleField[i+1][j].getNumberOfMasts()==numberOfMasts||
                     battleFieldPlayerCreateBattleFieldActivity.battleField[i][j-1].getNumberOfMasts()==numberOfMasts||
                     battleFieldPlayerCreateBattleFieldActivity.battleField[i][j+1].getNumberOfMasts()==numberOfMasts;
        }
         else if (i == 9 && ((j > 0) && (j < 9))) {
            return battleFieldPlayerCreateBattleFieldActivity.battleField[i-1][j].getNumberOfMasts()==numberOfMasts||
                    battleFieldPlayerCreateBattleFieldActivity.battleField[i][j-1].getNumberOfMasts()==numberOfMasts||
                    battleFieldPlayerCreateBattleFieldActivity.battleField[i][j+1].getNumberOfMasts()==numberOfMasts;
        }
         else if (i == 0 && j == 9) {
            return battleFieldPlayerCreateBattleFieldActivity.battleField[i+1][j].getNumberOfMasts()==numberOfMasts||
                    battleFieldPlayerCreateBattleFieldActivity.battleField[i][j-1].getNumberOfMasts()==numberOfMasts;
        }
         else if (((i > 0) && (i < 9)) && (j == 9)) {
            return battleFieldPlayerCreateBattleFieldActivity.battleField[i-1][j].getNumberOfMasts()==numberOfMasts||
                    battleFieldPlayerCreateBattleFieldActivity.battleField[i+1][j].getNumberOfMasts()==numberOfMasts||
                    battleFieldPlayerCreateBattleFieldActivity.battleField[i][j-1].getNumberOfMasts()==numberOfMasts;
        }
         else if ((i == 9) && (j == 9)){
             return battleFieldPlayerCreateBattleFieldActivity.battleField[i-1][j].getNumberOfMasts()==numberOfMasts||
                     battleFieldPlayerCreateBattleFieldActivity.battleField[i][j-1].getNumberOfMasts()==numberOfMasts;
        }

        return false;
    }

    public boolean conditionI (int i, int j, int numberOfMasts) {
        if(j==0){
            return battleFieldPlayerCreateBattleFieldActivity.battleField[i][j+1].getNumberOfMasts()==numberOfMasts;
        }
        else if ((j > 0) && (j < 9)){
            return battleFieldPlayerCreateBattleFieldActivity.battleField[i][j-1].getNumberOfMasts()==numberOfMasts||
                    battleFieldPlayerCreateBattleFieldActivity.battleField[i][j+1].getNumberOfMasts()==numberOfMasts;
        }
        else if (j == 9) {
            return battleFieldPlayerCreateBattleFieldActivity.battleField[i][j-1].getNumberOfMasts()==numberOfMasts;
        }
        return false;
    }

    public boolean conditionJ (int i, int j, int numberOfMasts) {
        if(i==0){
            return battleFieldPlayerCreateBattleFieldActivity.battleField[i+1][j].getNumberOfMasts()==numberOfMasts;
        }
        else if(i > 0 && i < 9){
            return battleFieldPlayerCreateBattleFieldActivity.battleField[i-1][j].getNumberOfMasts()==numberOfMasts||
                    battleFieldPlayerCreateBattleFieldActivity.battleField[i+1][j].getNumberOfMasts()==numberOfMasts;
        }
        else if(i==9){
            return  battleFieldPlayerCreateBattleFieldActivity.battleField[i-1][j].getNumberOfMasts()==numberOfMasts;
        }
        return false;
    }

    public void clickCreateBattleField_1x1(View view) {
        putShip(0,0,choosenShip);
    }

    public void clickCreateBattleField_1x2(View view) {
        putShip(0,1,choosenShip);
    }

    public void clickCreateBattleField_1x3(View view) {
        putShip(0,2,choosenShip);
    }

    public void clickCreateBattleField_1x4(View view) {
        putShip(0,3,choosenShip);
    }

    public void clickCreateBattleField_1x5(View view) {
        putShip(0,4,choosenShip);
    }

    public void clickCreateBattleField_1x6(View view) {
        putShip(0,5,choosenShip);
    }

    public void clickCreateBattleField_1x7(View view) {
        putShip(0,6,choosenShip);
    }

    public void clickCreateBattleField_1x8(View view) {
        putShip(0,7,choosenShip);
    }

    public void clickCreateBattleField_1x9(View view) {
        putShip(0,8,choosenShip);
    }

    public void clickCreateBattleField_1x10(View view) {
        putShip(0,9,choosenShip);
    }

    public void clickCreateBattleField_2x1(View view) {
        putShip(1,0,choosenShip);
    }

    public void clickCreateBattleField_2x2(View view) {
        putShip(1,1,choosenShip);
    }

    public void clickCreateBattleField_2x3(View view) {
        putShip(1,2,choosenShip);
    }

    public void clickCreateBattleField_2x4(View view) {
        putShip(1,3,choosenShip);
    }

    public void clickCreateBattleField_2x5(View view) {
        putShip(1,4,choosenShip);
    }

    public void clickCreateBattleField_2x6(View view) {
        putShip(1,5,choosenShip);
    }

    public void clickCreateBattleField_2x7(View view) {
        putShip(1,6,choosenShip);
    }

    public void clickCreateBattleField_2x8(View view) {
        putShip(1,7,choosenShip);
    }

    public void clickCreateBattleField_2x9(View view) {
        putShip(1,8,choosenShip);
    }

    public void clickCreateBattleField_2x10(View view) {
        putShip(1,9,choosenShip);
    }

    public void clickCreateBattleField_3x1(View view) {
        putShip(2,0,choosenShip);
    }

    public void clickCreateBattleField_3x2(View view) {
        putShip(2,1,choosenShip);
    }

    public void clickCreateBattleField_3x3(View view) {
        putShip(2,2,choosenShip);
    }

    public void clickCreateBattleField_3x4(View view) {
        putShip(2,3,choosenShip);
    }

    public void clickCreateBattleField_3x5(View view) {
        putShip(2,4,choosenShip);
    }

    public void clickCreateBattleField_3x6(View view) {
        putShip(2,5,choosenShip);
    }

    public void clickCreateBattleField_3x7(View view) {
        putShip(2,6,choosenShip);
    }

    public void clickCreateBattleField_3x8(View view) {
        putShip(2,7,choosenShip);
    }

    public void clickCreateBattleField_3x9(View view) {
        putShip(2,8,choosenShip);
    }

    public void clickCreateBattleField_3x10(View view) {
        putShip(2,9,choosenShip);
    }

    public void clickCreateBattleField_4x1(View view) {
        putShip(3,0,choosenShip);
    }

    public void clickCreateBattleField_4x2(View view) {
        putShip(3,1,choosenShip);
    }

    public void clickCreateBattleField_4x3(View view) {
        putShip(3,2,choosenShip);
    }

    public void clickCreateBattleField_4x4(View view) {
        putShip(3,3,choosenShip);
    }

    public void clickCreateBattleField_4x5(View view) {
        putShip(3,4,choosenShip);
    }

    public void clickCreateBattleField_4x6(View view) {
        putShip(3,5,choosenShip);
    }

    public void clickCreateBattleField_4x7(View view) {
        putShip(3,6,choosenShip);
    }

    public void clickCreateBattleField_4x8(View view) {
        putShip(3,7,choosenShip);
    }

    public void clickCreateBattleField_4x9(View view) {
        putShip(3,8,choosenShip);
    }

    public void clickCreateBattleField_4x10(View view) {
        putShip(3,9,choosenShip);
    }

    public void clickCreateBattleField_5x1(View view) {
        putShip(4,0,choosenShip);
    }

    public void clickCreateBattleField_5x2(View view) {
        putShip(4,1,choosenShip);
    }

    public void clickCreateBattleField_5x3(View view) {
        putShip(4,2,choosenShip);
    }

    public void clickCreateBattleField_5x4(View view) {
        putShip(4,3,choosenShip);
    }

    public void clickCreateBattleField_5x5(View view) {
        putShip(4,4,choosenShip);
    }

    public void clickCreateBattleField_5x6(View view) {
        putShip(4,5,choosenShip);
    }

    public void clickCreateBattleField_5x7(View view) {
        putShip(4,6,choosenShip);
    }

    public void clickCreateBattleField_5x8(View view) {
        putShip(4,7,choosenShip);
    }

    public void clickCreateBattleField_5x9(View view) {
        putShip(4,8,choosenShip);
    }

    public void clickCreateBattleField_5x10(View view) {
        putShip(4,9,choosenShip);
    }

    public void clickCreateBattleField_6x1(View view) {
        putShip(5,0,choosenShip);
    }

    public void clickCreateBattleField_6x2(View view) {
        putShip(5,1,choosenShip);
    }

    public void clickCreateBattleField_6x3(View view) {
        putShip(5,2,choosenShip);
    }

    public void clickCreateBattleField_6x4(View view) {
        putShip(5,3,choosenShip);
    }

    public void clickCreateBattleField_6x5(View view) {
        putShip(5,4,choosenShip);
    }

    public void clickCreateBattleField_6x6(View view) {
        putShip(5,5,choosenShip);
    }

    public void clickCreateBattleField_6x7(View view) {
        putShip(5,6,choosenShip);
    }

    public void clickCreateBattleField_6x8(View view) {
        putShip(5,7,choosenShip);
    }

    public void clickCreateBattleField_6x9(View view) {
        putShip(5,8,choosenShip);
    }

    public void clickCreateBattleField_6x10(View view) {
        putShip(5,9,choosenShip);
    }

    public void clickCreateBattleField_7x1(View view) {
        putShip(6,0,choosenShip);
    }

    public void clickCreateBattleField_7x2(View view) {
        putShip(6,1,choosenShip);
    }

    public void clickCreateBattleField_7x3(View view) {
        putShip(6,2,choosenShip);
    }

    public void clickCreateBattleField_7x4(View view) {
        putShip(6,3,choosenShip);
    }

    public void clickCreateBattleField_7x5(View view) {
        putShip(6,4,choosenShip);
    }

    public void clickCreateBattleField_7x6(View view) {
        putShip(6,5,choosenShip);
    }

    public void clickCreateBattleField_7x7(View view) {
        putShip(6,6,choosenShip);
    }

    public void clickCreateBattleField_7x8(View view) {
        putShip(6,7,choosenShip);
    }

    public void clickCreateBattleField_7x9(View view) {
        putShip(6,8,choosenShip);
    }

    public void clickCreateBattleField_7x10(View view) {
        putShip(6,9,choosenShip);
    }

    public void clickCreateBattleField_8x1(View view) {
        putShip(7,0,choosenShip);
    }

    public void clickCreateBattleField_8x2(View view) {
        putShip(7,1,choosenShip);
    }

    public void clickCreateBattleField_8x3(View view) {
        putShip(7,2,choosenShip);
    }

    public void clickCreateBattleField_8x4(View view) {
        putShip(7,3,choosenShip);
    }

    public void clickCreateBattleField_8x5(View view) {
        putShip(7,4,choosenShip);
    }

    public void clickCreateBattleField_8x6(View view) {
        putShip(7,5,choosenShip);
    }

    public void clickCreateBattleField_8x7(View view) {
        putShip(7,6,choosenShip);
    }

    public void clickCreateBattleField_8x8(View view) {
        putShip(7,7,choosenShip);
    }

    public void clickCreateBattleField_8x9(View view) {
        putShip(7,8,choosenShip);
    }

    public void clickCreateBattleField_8x10(View view) {
        putShip(7,9,choosenShip);
    }

    public void clickCreateBattleField_9x1(View view) {
        putShip(8,0,choosenShip);
    }

    public void clickCreateBattleField_9x2(View view) {
        putShip(8,1,choosenShip);
    }

    public void clickCreateBattleField_9x3(View view) {
        putShip(8,2,choosenShip);
    }

    public void clickCreateBattleField_9x4(View view) {
        putShip(8,3,choosenShip);
    }

    public void clickCreateBattleField_9x5(View view) {
        putShip(8,4,choosenShip);
    }

    public void clickCreateBattleField_9x6(View view) {
        putShip(8,5,choosenShip);
    }

    public void clickCreateBattleField_9x7(View view) {
        putShip(8,6,choosenShip);
    }

    public void clickCreateBattleField_9x8(View view) {
        putShip(8,7,choosenShip);
    }

    public void clickCreateBattleField_9x9(View view) {
        putShip(8,8,choosenShip);
    }

    public void clickCreateBattleField_9x10(View view) {
        putShip(8,9,choosenShip);
    }

    public void clickCreateBattleField_10x1(View view) {
        putShip(9,0,choosenShip);
    }

    public void clickCreateBattleField_10x2(View view) {
        putShip(9,1,choosenShip);
    }

    public void clickCreateBattleField_10x3(View view) {
        putShip(9,2,choosenShip);
    }

    public void clickCreateBattleField_10x4(View view) {
        putShip(9,3,choosenShip);
    }

    public void clickCreateBattleField_10x5(View view) {
        putShip(9,4,choosenShip);
    }

    public void clickCreateBattleField_10x6(View view) {
        putShip(9,5,choosenShip);
    }

    public void clickCreateBattleField_10x7(View view) {
        putShip(9,6,choosenShip);
    }

    public void clickCreateBattleField_10x8(View view) {
        putShip(9,7,choosenShip);
    }

    public void clickCreateBattleField_10x9(View view) {
        putShip(9,8,choosenShip);
    }

    public void clickCreateBattleField_10x10(View view) {
        putShip(9,9,choosenShip);
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

    public void onClickReset(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(CreateBattleField.this);
        builder.setCancelable(true);
        builder.setTitle("RESETING");
        builder.setMessage("Do you want to reset ships?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                for(int i=0;i<10;i++){
                    for(int j=0;j<10;j++){
                        battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setNumberOfMasts(0);
                        battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShipNumber(0);
                        battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShip(false);
                        TextViewArrayActivityCreateBattleField[i][j].setClickable(true);
                    }
                }
                choosenShip=0;
                updateScreen();


            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // do nothing
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    public void deleteUncomplitedShips(){
        checkShipsOnBattleField(battleFieldPlayerCreateBattleFieldActivity);
        if(fourMastsCounter>0&&fourMastsCounter<4){
            for(int i=0;i<10;i++){
                for(int j=0;j<10;j++){
                    if(battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].isShip()&&
                    battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].getNumberOfMasts()==4){
                        makeFieldZero(i,j);
                    }
                }
            }
        }else;
        if(threeMastsCounter1>0&&threeMastsCounter1<3){
            for(int i=0;i<10;i++){
                for(int j=0;j<10;j++){
                    if(battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].isShip()&&
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].getNumberOfMasts()==3&&
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].getShipNumber()==1){
                        makeFieldZero(i,j);
                    }
                }
            }
        }else;
        if(threeMastsCounter2>0&&threeMastsCounter2<3){
            for(int i=0;i<10;i++){
                for(int j=0;j<10;j++){
                    if(battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].isShip()&&
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].getNumberOfMasts()==3&&
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].getShipNumber()==2){
                        makeFieldZero(i,j);
                    }
                }
            }
        }else;
        if(twoMastsCounter1==1){
            for(int i=0;i<10;i++){
                for(int j=0;j<10;j++){
                    if(battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].isShip()&&
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].getNumberOfMasts()==2&&
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].getShipNumber()==1){
                        makeFieldZero(i,j);
                    }
                }
            }
        }else;
        if(twoMastsCounter2==1){
            for(int i=0;i<10;i++){
                for(int j=0;j<10;j++){
                    if(battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].isShip()&&
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].getNumberOfMasts()==2&&
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].getShipNumber()==2){
                        makeFieldZero(i,j);
                    }
                }
            }
        }else;
        if(twoMastsCounter3==1){
            for(int i=0;i<10;i++){
                for(int j=0;j<10;j++){
                    if(battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].isShip()&&
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].getNumberOfMasts()==2&&
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].getShipNumber()==3){
                        makeFieldZero(i,j);
                    }
                }
            }
        }else;
       updateScreen();
    }

    private void makeFieldZero(int i, int j) {
        battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setNumberOfMasts(0);
        battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShipNumber(0);
        battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShip(false);
    }

    public void onClickDelete(View view) {
        choosenShip=5;
        for(int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                TextViewArrayActivityCreateBattleField[i][j].setClickable(true);
            }
        }
    }
}
