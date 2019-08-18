package com.example.ships;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.Random;

public class RandomGameBattle extends AppCompatActivity {

    private Handler mHandler = new Handler();

    BattleField battleFieldPlayerOneActivityRandomGame = new BattleField();
    BattleField battleFieldPlayerTwoActivityRandomGame = new BattleField();

    TextView[][] TextViewArrayActivityRandomGamePlayerOne = new TextView[10][10];
    TextView[][] TextViewArrayActivityRandomGamePlayerTwo = new TextView[10][10];

    TextView[] ShipFourMasts = new TextView[4];
    int ShipFourMastsCounter = 0;
    TextView[] ShipThreeMastsFirst = new TextView[3];
    int ShipThreeMastsCounterFirst = 0;
    TextView[] ShipThreeMastsSecond = new TextView[3];
    int ShipThreeMastsCounterSecond = 0;
    TextView[] ShipTwoMastsFirst = new TextView[2];
    int ShipTwoMastsCounterFirst = 0;
    TextView[] ShipTwoMastsSecond = new TextView[2];
    int ShipTwoMastsCounterSecond = 0;
    TextView[] ShipTwoMastsThird = new TextView[2];
    int ShipTwoMastsCounterThird = 0;
    TextView[] ShipOneMastsFirst = new TextView[1];
    TextView[] ShipOneMastsSecond = new TextView[1];
    TextView[] ShipOneMastsThird = new TextView[1];
    TextView[] ShipOneMastsFourth = new TextView[1];
    boolean playerOneCounter;
    boolean playerTwoCounter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_random_game_battle);

        initializeShips();
        initializeBattleFieldActivityRandomGamePlayerOne(TextViewArrayActivityRandomGamePlayerOne);
        initializeBattleFieldActivityRandomGamePlayerTwo(TextViewArrayActivityRandomGamePlayerTwo);
        battleFieldPlayerOneActivityRandomGame = BattleFieldPlayerOneSingleton.getInstance().readBattleField();
        battleFieldPlayerTwoActivityRandomGame = BattleFieldPlayerTwoSingleton.getInstance().readBattleField();
        displayBattleFieldActivityRandomGamePlayerOne(TextViewArrayActivityRandomGamePlayerOne, battleFieldPlayerOneActivityRandomGame);
 //       displayBattleFieldActivityRandomGamePlayerTwo(TextViewArrayActivityRandomGamePlayerTwo, battleFieldPlayerTwoActivityRandomGame);

        game.run();

    }


    private boolean zatopiony(int noOfMasts, int shipNo){
        int counter=0;
        for(int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                if(battleFieldPlayerTwoActivityRandomGame.battleField[i][j].getNumberOfMasts()==noOfMasts
                        &&battleFieldPlayerTwoActivityRandomGame.battleField[i][j].getShipNumber()==shipNo
                &&battleFieldPlayerTwoActivityRandomGame.battleField[i][j].isHit()){
                    counter++;
                }else;
            }
        }return counter==noOfMasts;
    }

    private void initializeShips() {
        ShipFourMasts[0]=findViewById(R.id.FourCellShip1);
        ShipFourMasts[1]=findViewById(R.id.FourCellShip2);
        ShipFourMasts[2]=findViewById(R.id.FourCellShip3);
        ShipFourMasts[3]=findViewById(R.id.FourCellShip4);

        ShipThreeMastsFirst[0]=findViewById(R.id.ThreeCellShip11);
        ShipThreeMastsFirst[1]=findViewById(R.id.ThreeCellShip12);
        ShipThreeMastsFirst[2]=findViewById(R.id.ThreeCellShip13);
        ShipThreeMastsSecond[0]=findViewById(R.id.ThreeCellShip21);
        ShipThreeMastsSecond[1]=findViewById(R.id.ThreeCellShip22);
        ShipThreeMastsSecond[2]=findViewById(R.id.ThreeCellShip23);

        ShipTwoMastsFirst[0]=findViewById(R.id.TwoCellShip11);
        ShipTwoMastsFirst[1]=findViewById(R.id.TwoCellShip12);
        ShipTwoMastsSecond[0]=findViewById(R.id.TwoCellShip21);
        ShipTwoMastsSecond[1]=findViewById(R.id.TwoCellShip22);
        ShipTwoMastsThird[0]=findViewById(R.id.TwoCellShip31);
        ShipTwoMastsThird[1]=findViewById(R.id.TwoCellShip32);

        ShipOneMastsFirst[0]=findViewById(R.id.OneCellShip1);
        ShipOneMastsSecond[0]=findViewById(R.id.OneCellShip2);
        ShipOneMastsThird[0]=findViewById(R.id.OneCellShip3);
        ShipOneMastsFourth[0]=findViewById(R.id.OneCellShip4);
    }
    //TODO opisać planszę

    private Runnable game = new Runnable() {
        @Override
        public void run() {
            if(!battleFieldPlayerTwoActivityRandomGame.allShipsHit()&&!battleFieldPlayerOneActivityRandomGame.allShipsHit()) //game
            {
            battle();
            }
            else if (battleFieldPlayerTwoActivityRandomGame.allShipsHit()&&!battleFieldPlayerOneActivityRandomGame.allShipsHit())      // allShipsHit player
            {
                     mHandler.removeCallbacks(game);
                Intent intent = new Intent(getApplicationContext(),WinPlayerOne.class);
                startActivity(intent);
                finish();
            }
            else if (!battleFieldPlayerTwoActivityRandomGame.allShipsHit()&&battleFieldPlayerOneActivityRandomGame.allShipsHit())     // allShipsHit computer
                {
                    mHandler.removeCallbacks(game);
                    Intent intent = new Intent(getApplicationContext(),WinPlayerTwo.class);
                    startActivity(intent);
                    finish();
            }
            else;
        }
    };

    public void battle(){

        if(playerOneCounter&&!playerTwoCounter){
            readClicable();
            showBattleFieldAvailablePlayerTwo();
            hideBattleFiledAvailablePlayerOne();
        }
        else if(playerTwoCounter&&!playerOneCounter){
            hideBattleFiledAvailablePlayerTwo();
            showBattleFieldAvailablePlayerOne();
            shoot();
        }
        else{
            playerOneCounter=true;
            playerTwoCounter=false;
        }
        mHandler.postDelayed(game,1000);
    }

    private void showBattleFieldAvailablePlayerOne() {
        for(int i =0;i<10;i++){
            for(int j = 0; j<10;j++){
                //jest statek i został trafiony
                if(battleFieldPlayerOneActivityRandomGame.getBattleField(i,j).isShip()
                        &&battleFieldPlayerOneActivityRandomGame.getBattleField(i,j).isHit()){
                    final int sdk = android.os.Build.VERSION.SDK_INT;
                    if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        TextViewArrayActivityRandomGamePlayerOne[i][j].setBackgroundDrawable(getResources().getDrawable(R.drawable.ship_cell));
                    } else {
                        TextViewArrayActivityRandomGamePlayerOne[i][j].setBackground(getResources().getDrawable(R.drawable.ship_cell));
                    }
                }

                // woda i została trafiony
                else if(!battleFieldPlayerOneActivityRandomGame.getBattleField(i,j).isShip()
                        &&battleFieldPlayerOneActivityRandomGame.getBattleField(i,j).isHit()){
                    final int sdk = android.os.Build.VERSION.SDK_INT;
                    if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        TextViewArrayActivityRandomGamePlayerOne[i][j].setBackgroundDrawable(getResources().getDrawable(R.drawable.water_cell));
                    } else {
                        TextViewArrayActivityRandomGamePlayerOne[i][j].setBackground(getResources().getDrawable(R.drawable.water_cell));
                    }
                }

                // jest statek i nie został trafiony
                else if(battleFieldPlayerOneActivityRandomGame.getBattleField(i,j).isShip()
                &&!battleFieldPlayerOneActivityRandomGame.getBattleField(i,j).isHit()){
                    final int sdk = android.os.Build.VERSION.SDK_INT;
                    if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        TextViewArrayActivityRandomGamePlayerOne[i][j].setBackgroundDrawable(getResources().getDrawable(R.drawable.widmo_ship_cell));
                    } else {
                        TextViewArrayActivityRandomGamePlayerOne[i][j].setBackground(getResources().getDrawable(R.drawable.widmo_ship_cell));
                    }
                }
                // nie ma statku i nie został trafiony
                else if(!battleFieldPlayerOneActivityRandomGame.getBattleField(i,j).isShip()
                        &&!battleFieldPlayerOneActivityRandomGame.getBattleField(i,j).isHit()){
                    final int sdk = android.os.Build.VERSION.SDK_INT;
                    if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        TextViewArrayActivityRandomGamePlayerOne[i][j].setBackgroundDrawable(getResources().getDrawable(R.drawable.battle_cell));
                    } else {
                        TextViewArrayActivityRandomGamePlayerOne[i][j].setBackground(getResources().getDrawable(R.drawable.battle_cell));
                    }
                }

                else;
            }
        }
    }

    private void hideBattleFiledAvailablePlayerOne() {
        for(int i =0;i<10;i++){
            for(int j = 0; j<10;j++){
                //jest statek i został trafiony
                if(battleFieldPlayerOneActivityRandomGame.getBattleField(i,j).isShip()
                        &&battleFieldPlayerOneActivityRandomGame.getBattleField(i,j).isHit()){
                    final int sdk = android.os.Build.VERSION.SDK_INT;
                    if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        TextViewArrayActivityRandomGamePlayerOne[i][j].setBackgroundDrawable(getResources().getDrawable(R.drawable.ship_cell_hidden));
                    } else {
                        TextViewArrayActivityRandomGamePlayerOne[i][j].setBackground(getResources().getDrawable(R.drawable.ship_cell_hidden));
                    }
                }

                // woda i została trafiony
                else if(!battleFieldPlayerOneActivityRandomGame.getBattleField(i,j).isShip()
                        &&battleFieldPlayerOneActivityRandomGame.getBattleField(i,j).isHit()){
                    final int sdk = android.os.Build.VERSION.SDK_INT;
                    if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        TextViewArrayActivityRandomGamePlayerOne[i][j].setBackgroundDrawable(getResources().getDrawable(R.drawable.water_cell_hiden));
                    } else {
                        TextViewArrayActivityRandomGamePlayerOne[i][j].setBackground(getResources().getDrawable(R.drawable.water_cell_hiden));
                    }
                }

                // jest statek i nie został trafiony
                else if(battleFieldPlayerOneActivityRandomGame.getBattleField(i,j).isShip()
                        &&!battleFieldPlayerOneActivityRandomGame.getBattleField(i,j).isHit()){
                    final int sdk = android.os.Build.VERSION.SDK_INT;
                    if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        TextViewArrayActivityRandomGamePlayerOne[i][j].setBackgroundDrawable(getResources().getDrawable(R.drawable.widmo_ship_hidden));
                    } else {
                        TextViewArrayActivityRandomGamePlayerOne[i][j].setBackground(getResources().getDrawable(R.drawable.widmo_ship_hidden));
                    }
                }
                // nie ma statku i nie został trafiony
                else if(!battleFieldPlayerOneActivityRandomGame.getBattleField(i,j).isShip()
                        &&!battleFieldPlayerOneActivityRandomGame.getBattleField(i,j).isHit()){
                    final int sdk = android.os.Build.VERSION.SDK_INT;
                    if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        TextViewArrayActivityRandomGamePlayerOne[i][j].setBackgroundDrawable(getResources().getDrawable(R.drawable.battle_cell_hidden));
                    } else {
                        TextViewArrayActivityRandomGamePlayerOne[i][j].setBackground(getResources().getDrawable(R.drawable.battle_cell_hidden));
                    }
                }

                else;
            }
        }

    }

    private void hideBattleFiledAvailablePlayerTwo() {
        for(int i =0;i<10;i++){
            for(int j = 0; j<10;j++){
                //jest statek i został trafiony
                if(battleFieldPlayerTwoActivityRandomGame.getBattleField(i,j).isShip()
                        &&battleFieldPlayerTwoActivityRandomGame.getBattleField(i,j).isHit()){
                    final int sdk = android.os.Build.VERSION.SDK_INT;
                    if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        TextViewArrayActivityRandomGamePlayerTwo[i][j].setBackgroundDrawable(getResources().getDrawable(R.drawable.ship_cell_hidden));
                    } else {
                        TextViewArrayActivityRandomGamePlayerTwo[i][j].setBackground(getResources().getDrawable(R.drawable.ship_cell_hidden));
                    }
                }

                // woda i został trafiony
                else if(!battleFieldPlayerTwoActivityRandomGame.getBattleField(i,j).isShip()
                        &&battleFieldPlayerTwoActivityRandomGame.getBattleField(i,j).isHit()){
                    final int sdk = android.os.Build.VERSION.SDK_INT;
                    if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        TextViewArrayActivityRandomGamePlayerTwo[i][j].setBackgroundDrawable(getResources().getDrawable(R.drawable.water_cell_hiden));
                    } else {
                        TextViewArrayActivityRandomGamePlayerTwo[i][j].setBackground(getResources().getDrawable(R.drawable.water_cell_hiden));
                    }
                }

                // został trafiony
                else if(!battleFieldPlayerTwoActivityRandomGame.getBattleField(i,j).isHit()){
                    final int sdk = android.os.Build.VERSION.SDK_INT;
                    if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        TextViewArrayActivityRandomGamePlayerTwo[i][j].setBackgroundDrawable(getResources().getDrawable(R.drawable.battle_cell_hidden));
                    } else {
                        TextViewArrayActivityRandomGamePlayerTwo[i][j].setBackground(getResources().getDrawable(R.drawable.battle_cell_hidden));
                    }
                }
                else;
            }
        }

    }

    private void showBattleFieldAvailablePlayerTwo() {
        for(int i =0;i<10;i++){
            for(int j = 0; j<10;j++){
                //jest statek i został trafiony
                if(battleFieldPlayerTwoActivityRandomGame.getBattleField(i,j).isShip()
                        &&battleFieldPlayerTwoActivityRandomGame.getBattleField(i,j).isHit()){

                    if(zatopiony(battleFieldPlayerTwoActivityRandomGame.battleField[i][j].getNumberOfMasts(),battleFieldPlayerTwoActivityRandomGame.battleField[i][j].getShipNumber())){
                        final int sdk = android.os.Build.VERSION.SDK_INT;
                        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                            TextViewArrayActivityRandomGamePlayerTwo[i][j].setBackgroundDrawable(getResources().getDrawable(R.drawable.ship_cell));
                        } else {
                            TextViewArrayActivityRandomGamePlayerTwo[i][j].setBackground(getResources().getDrawable(R.drawable.ship_cell));
                        }
                    }
                    else{

                    final int sdk = android.os.Build.VERSION.SDK_INT;
                    if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        TextViewArrayActivityRandomGamePlayerTwo[i][j].setBackgroundDrawable(getResources().getDrawable(R.drawable.red_ship));
                    } else {
                        TextViewArrayActivityRandomGamePlayerTwo[i][j].setBackground(getResources().getDrawable(R.drawable.red_ship));
                    }
                }
                }

                // woda i została trafiony
                else if(!battleFieldPlayerTwoActivityRandomGame.getBattleField(i,j).isShip()
                        &&battleFieldPlayerTwoActivityRandomGame.getBattleField(i,j).isHit()){
                    final int sdk = android.os.Build.VERSION.SDK_INT;
                    if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        TextViewArrayActivityRandomGamePlayerTwo[i][j].setBackgroundDrawable(getResources().getDrawable(R.drawable.water_cell));
                    } else {
                        TextViewArrayActivityRandomGamePlayerTwo[i][j].setBackground(getResources().getDrawable(R.drawable.water_cell));
                    }
                }

                    // nie ma statku
                else if(!battleFieldPlayerTwoActivityRandomGame.getBattleField(i,j).isHit()){
                    final int sdk = android.os.Build.VERSION.SDK_INT;
                    if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        TextViewArrayActivityRandomGamePlayerTwo[i][j].setBackgroundDrawable(getResources().getDrawable(R.drawable.battle_cell));
                    } else {
                        TextViewArrayActivityRandomGamePlayerTwo[i][j].setBackground(getResources().getDrawable(R.drawable.battle_cell));
                    }
                }
                else;
            }
        }
    }


    private void shoot(){
        Random random = new Random();
        int shoot = random.nextInt(999);
        int i = shoot / 100;
        int j = shoot % 10;
            if(battleFieldPlayerOneActivityRandomGame.getBattleField(i,j).isHit()){
                shoot();
            }
            else{
                if(battleFieldPlayerOneActivityRandomGame.getBattleField(i,j).isShip()){
                    final int sdk = android.os.Build.VERSION.SDK_INT;
                    if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        TextViewArrayActivityRandomGamePlayerOne[i][j].setBackgroundDrawable(getResources().getDrawable(R.drawable.ship_cell));
                    } else {
                        TextViewArrayActivityRandomGamePlayerOne[i][j].setBackground(getResources().getDrawable(R.drawable.ship_cell));
                    }
                    battleFieldPlayerOneActivityRandomGame.battleField[i][j].setHit(true);
                }else{
                    final int sdk = android.os.Build.VERSION.SDK_INT;
                    if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        TextViewArrayActivityRandomGamePlayerOne[i][j].setBackgroundDrawable(getResources().getDrawable(R.drawable.water_cell));
                    } else {
                        TextViewArrayActivityRandomGamePlayerOne[i][j].setBackground(getResources().getDrawable(R.drawable.water_cell));
                    }
                    battleFieldPlayerOneActivityRandomGame.battleField[i][j].setHit(true);
                    playerTwoCounter=false;
                    playerOneCounter=true;
                }
            }

    }

    private void initializeBattleFieldActivityRandomGamePlayerTwo(TextView[][] textViewArrayActivityRandomGame) {
        textViewArrayActivityRandomGame[0][0] = findViewById(R.id.player2CellGame_1x1);
        textViewArrayActivityRandomGame[0][1] = findViewById(R.id.player2CellGame_1x2);
        textViewArrayActivityRandomGame[0][2] = findViewById(R.id.player2CellGame_1x3);
        textViewArrayActivityRandomGame[0][3] = findViewById(R.id.player2CellGame_1x4);
        textViewArrayActivityRandomGame[0][4] = findViewById(R.id.player2CellGame_1x5);
        textViewArrayActivityRandomGame[0][5] = findViewById(R.id.player2CellGame_1x6);
        textViewArrayActivityRandomGame[0][6] = findViewById(R.id.player2CellGame_1x7);
        textViewArrayActivityRandomGame[0][7] = findViewById(R.id.player2CellGame_1x8);
        textViewArrayActivityRandomGame[0][8] = findViewById(R.id.player2CellGame_1x9);
        textViewArrayActivityRandomGame[0][9] = findViewById(R.id.player2CellGame_1x10);

        textViewArrayActivityRandomGame[1][0] = findViewById(R.id.player2CellGame_2x1);
        textViewArrayActivityRandomGame[1][1] = findViewById(R.id.player2CellGame_2x2);
        textViewArrayActivityRandomGame[1][2] = findViewById(R.id.player2CellGame_2x3);
        textViewArrayActivityRandomGame[1][3] = findViewById(R.id.player2CellGame_2x4);
        textViewArrayActivityRandomGame[1][4] = findViewById(R.id.player2CellGame_2x5);
        textViewArrayActivityRandomGame[1][5] = findViewById(R.id.player2CellGame_2x6);
        textViewArrayActivityRandomGame[1][6] = findViewById(R.id.player2CellGame_2x7);
        textViewArrayActivityRandomGame[1][7] = findViewById(R.id.player2CellGame_2x8);
        textViewArrayActivityRandomGame[1][8] = findViewById(R.id.player2CellGame_2x9);
        textViewArrayActivityRandomGame[1][9] = findViewById(R.id.player2CellGame_2x10);

        textViewArrayActivityRandomGame[2][0] = findViewById(R.id.player2CellGame_3x1);
        textViewArrayActivityRandomGame[2][1] = findViewById(R.id.player2CellGame_3x2);
        textViewArrayActivityRandomGame[2][2] = findViewById(R.id.player2CellGame_3x3);
        textViewArrayActivityRandomGame[2][3] = findViewById(R.id.player2CellGame_3x4);
        textViewArrayActivityRandomGame[2][4] = findViewById(R.id.player2CellGame_3x5);
        textViewArrayActivityRandomGame[2][5] = findViewById(R.id.player2CellGame_3x6);
        textViewArrayActivityRandomGame[2][6] = findViewById(R.id.player2CellGame_3x7);
        textViewArrayActivityRandomGame[2][7] = findViewById(R.id.player2CellGame_3x8);
        textViewArrayActivityRandomGame[2][8] = findViewById(R.id.player2CellGame_3x9);
        textViewArrayActivityRandomGame[2][9] = findViewById(R.id.player2CellGame_3x10);

        textViewArrayActivityRandomGame[3][0] = findViewById(R.id.player2CellGame_4x1);
        textViewArrayActivityRandomGame[3][1] = findViewById(R.id.player2CellGame_4x2);
        textViewArrayActivityRandomGame[3][2] = findViewById(R.id.player2CellGame_4x3);
        textViewArrayActivityRandomGame[3][3] = findViewById(R.id.player2CellGame_4x4);
        textViewArrayActivityRandomGame[3][4] = findViewById(R.id.player2CellGame_4x5);
        textViewArrayActivityRandomGame[3][5] = findViewById(R.id.player2CellGame_4x6);
        textViewArrayActivityRandomGame[3][6] = findViewById(R.id.player2CellGame_4x7);
        textViewArrayActivityRandomGame[3][7] = findViewById(R.id.player2CellGame_4x8);
        textViewArrayActivityRandomGame[3][8] = findViewById(R.id.player2CellGame_4x9);
        textViewArrayActivityRandomGame[3][9] = findViewById(R.id.player2CellGame_4x10);

        textViewArrayActivityRandomGame[4][0] = findViewById(R.id.player2CellGame_5x1);
        textViewArrayActivityRandomGame[4][1] = findViewById(R.id.player2CellGame_5x2);
        textViewArrayActivityRandomGame[4][2] = findViewById(R.id.player2CellGame_5x3);
        textViewArrayActivityRandomGame[4][3] = findViewById(R.id.player2CellGame_5x4);
        textViewArrayActivityRandomGame[4][4] = findViewById(R.id.player2CellGame_5x5);
        textViewArrayActivityRandomGame[4][5] = findViewById(R.id.player2CellGame_5x6);
        textViewArrayActivityRandomGame[4][6] = findViewById(R.id.player2CellGame_5x7);
        textViewArrayActivityRandomGame[4][7] = findViewById(R.id.player2CellGame_5x8);
        textViewArrayActivityRandomGame[4][8] = findViewById(R.id.player2CellGame_5x9);
        textViewArrayActivityRandomGame[4][9] = findViewById(R.id.player2CellGame_5x10);

        textViewArrayActivityRandomGame[5][0] = findViewById(R.id.player2CellGame_6x1);
        textViewArrayActivityRandomGame[5][1] = findViewById(R.id.player2CellGame_6x2);
        textViewArrayActivityRandomGame[5][2] = findViewById(R.id.player2CellGame_6x3);
        textViewArrayActivityRandomGame[5][3] = findViewById(R.id.player2CellGame_6x4);
        textViewArrayActivityRandomGame[5][4] = findViewById(R.id.player2CellGame_6x5);
        textViewArrayActivityRandomGame[5][5] = findViewById(R.id.player2CellGame_6x6);
        textViewArrayActivityRandomGame[5][6] = findViewById(R.id.player2CellGame_6x7);
        textViewArrayActivityRandomGame[5][7] = findViewById(R.id.player2CellGame_6x8);
        textViewArrayActivityRandomGame[5][8] = findViewById(R.id.player2CellGame_6x9);
        textViewArrayActivityRandomGame[5][9] = findViewById(R.id.player2CellGame_6x10);

        textViewArrayActivityRandomGame[6][0] = findViewById(R.id.player2CellGame_7x1);
        textViewArrayActivityRandomGame[6][1] = findViewById(R.id.player2CellGame_7x2);
        textViewArrayActivityRandomGame[6][2] = findViewById(R.id.player2CellGame_7x3);
        textViewArrayActivityRandomGame[6][3] = findViewById(R.id.player2CellGame_7x4);
        textViewArrayActivityRandomGame[6][4] = findViewById(R.id.player2CellGame_7x5);
        textViewArrayActivityRandomGame[6][5] = findViewById(R.id.player2CellGame_7x6);
        textViewArrayActivityRandomGame[6][6] = findViewById(R.id.player2CellGame_7x7);
        textViewArrayActivityRandomGame[6][7] = findViewById(R.id.player2CellGame_7x8);
        textViewArrayActivityRandomGame[6][8] = findViewById(R.id.player2CellGame_7x9);
        textViewArrayActivityRandomGame[6][9] = findViewById(R.id.player2CellGame_7x10);

        textViewArrayActivityRandomGame[7][0] = findViewById(R.id.player2CellGame_8x1);
        textViewArrayActivityRandomGame[7][1] = findViewById(R.id.player2CellGame_8x2);
        textViewArrayActivityRandomGame[7][2] = findViewById(R.id.player2CellGame_8x3);
        textViewArrayActivityRandomGame[7][3] = findViewById(R.id.player2CellGame_8x4);
        textViewArrayActivityRandomGame[7][4] = findViewById(R.id.player2CellGame_8x5);
        textViewArrayActivityRandomGame[7][5] = findViewById(R.id.player2CellGame_8x6);
        textViewArrayActivityRandomGame[7][6] = findViewById(R.id.player2CellGame_8x7);
        textViewArrayActivityRandomGame[7][7] = findViewById(R.id.player2CellGame_8x8);
        textViewArrayActivityRandomGame[7][8] = findViewById(R.id.player2CellGame_8x9);
        textViewArrayActivityRandomGame[7][9] = findViewById(R.id.player2CellGame_8x10);

        textViewArrayActivityRandomGame[8][0] = findViewById(R.id.player2CellGame_9x1);
        textViewArrayActivityRandomGame[8][1] = findViewById(R.id.player2CellGame_9x2);
        textViewArrayActivityRandomGame[8][2] = findViewById(R.id.player2CellGame_9x3);
        textViewArrayActivityRandomGame[8][3] = findViewById(R.id.player2CellGame_9x4);
        textViewArrayActivityRandomGame[8][4] = findViewById(R.id.player2CellGame_9x5);
        textViewArrayActivityRandomGame[8][5] = findViewById(R.id.player2CellGame_9x6);
        textViewArrayActivityRandomGame[8][6] = findViewById(R.id.player2CellGame_9x7);
        textViewArrayActivityRandomGame[8][7] = findViewById(R.id.player2CellGame_9x8);
        textViewArrayActivityRandomGame[8][8] = findViewById(R.id.player2CellGame_9x9);
        textViewArrayActivityRandomGame[8][9] = findViewById(R.id.player2CellGame_9x10);

        textViewArrayActivityRandomGame[9][0] = findViewById(R.id.player2CellGame_10x1);
        textViewArrayActivityRandomGame[9][1] = findViewById(R.id.player2CellGame_10x2);
        textViewArrayActivityRandomGame[9][2] = findViewById(R.id.player2CellGame_10x3);
        textViewArrayActivityRandomGame[9][3] = findViewById(R.id.player2CellGame_10x4);
        textViewArrayActivityRandomGame[9][4] = findViewById(R.id.player2CellGame_10x5);
        textViewArrayActivityRandomGame[9][5] = findViewById(R.id.player2CellGame_10x6);
        textViewArrayActivityRandomGame[9][6] = findViewById(R.id.player2CellGame_10x7);
        textViewArrayActivityRandomGame[9][7] = findViewById(R.id.player2CellGame_10x8);
        textViewArrayActivityRandomGame[9][8] = findViewById(R.id.player2CellGame_10x9);
        textViewArrayActivityRandomGame[9][9] = findViewById(R.id.player2CellGame_10x10);
    }

    private void displayBattleFieldActivityRandomGamePlayerOne(TextView[][] TextViewArrayActivityRandomGame, BattleField battleFieldPlayerOneActivityRandomGame) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (battleFieldPlayerOneActivityRandomGame.getBattleField(i, j).isShip()) {
        //            TextViewArrayActivityRandomGame[i][j].setBackgroundColor(getResources().getColor(R.color.widmoShip));
                    final int sdk = android.os.Build.VERSION.SDK_INT;
                    if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        TextViewArrayActivityRandomGame[i][j].setBackgroundDrawable(getResources().getDrawable(R.drawable.widmo_ship_cell));
                    } else {
                        TextViewArrayActivityRandomGame[i][j].setBackground(getResources().getDrawable(R.drawable.widmo_ship_cell));
                    }
                }
            }
        }
    }

    private void displayBattleFieldActivityRandomGamePlayerTwo(TextView[][] TextViewArrayActivityRandomGame, BattleField battleFieldPlayerOneActivityRandomGame) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (battleFieldPlayerOneActivityRandomGame.getBattleField(i, j).isShip()) {
         //           TextViewArrayActivityRandomGame[i][j].setBackgroundColor(getResources().getColor(R.color.widmoShip));
                    final int sdk = android.os.Build.VERSION.SDK_INT;
                    if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        TextViewArrayActivityRandomGame[i][j].setBackgroundDrawable(getResources().getDrawable(R.drawable.widmo_ship_cell));
                    } else {
                        TextViewArrayActivityRandomGame[i][j].setBackground(getResources().getDrawable(R.drawable.widmo_ship_cell));
                    }
                }
            }
        }
    }

    private void initializeBattleFieldActivityRandomGamePlayerOne(TextView[][] textViewArrayActivityRandomGame) {
        textViewArrayActivityRandomGame[0][0] = findViewById(R.id.playerCellGame_1x1);
        textViewArrayActivityRandomGame[0][1] = findViewById(R.id.playerCellGame_1x2);
        textViewArrayActivityRandomGame[0][2] = findViewById(R.id.playerCellGame_1x3);
        textViewArrayActivityRandomGame[0][3] = findViewById(R.id.playerCellGame_1x4);
        textViewArrayActivityRandomGame[0][4] = findViewById(R.id.playerCellGame_1x5);
        textViewArrayActivityRandomGame[0][5] = findViewById(R.id.playerCellGame_1x6);
        textViewArrayActivityRandomGame[0][6] = findViewById(R.id.playerCellGame_1x7);
        textViewArrayActivityRandomGame[0][7] = findViewById(R.id.playerCellGame_1x8);
        textViewArrayActivityRandomGame[0][8] = findViewById(R.id.playerCellGame_1x9);
        textViewArrayActivityRandomGame[0][9] = findViewById(R.id.playerCellGame_1x10);

        textViewArrayActivityRandomGame[1][0] = findViewById(R.id.playerCellGame_2x1);
        textViewArrayActivityRandomGame[1][1] = findViewById(R.id.playerCellGame_2x2);
        textViewArrayActivityRandomGame[1][2] = findViewById(R.id.playerCellGame_2x3);
        textViewArrayActivityRandomGame[1][3] = findViewById(R.id.playerCellGame_2x4);
        textViewArrayActivityRandomGame[1][4] = findViewById(R.id.playerCellGame_2x5);
        textViewArrayActivityRandomGame[1][5] = findViewById(R.id.playerCellGame_2x6);
        textViewArrayActivityRandomGame[1][6] = findViewById(R.id.playerCellGame_2x7);
        textViewArrayActivityRandomGame[1][7] = findViewById(R.id.playerCellGame_2x8);
        textViewArrayActivityRandomGame[1][8] = findViewById(R.id.playerCellGame_2x9);
        textViewArrayActivityRandomGame[1][9] = findViewById(R.id.playerCellGame_2x10);

        textViewArrayActivityRandomGame[2][0] = findViewById(R.id.playerCellGame_3x1);
        textViewArrayActivityRandomGame[2][1] = findViewById(R.id.playerCellGame_3x2);
        textViewArrayActivityRandomGame[2][2] = findViewById(R.id.playerCellGame_3x3);
        textViewArrayActivityRandomGame[2][3] = findViewById(R.id.playerCellGame_3x4);
        textViewArrayActivityRandomGame[2][4] = findViewById(R.id.playerCellGame_3x5);
        textViewArrayActivityRandomGame[2][5] = findViewById(R.id.playerCellGame_3x6);
        textViewArrayActivityRandomGame[2][6] = findViewById(R.id.playerCellGame_3x7);
        textViewArrayActivityRandomGame[2][7] = findViewById(R.id.playerCellGame_3x8);
        textViewArrayActivityRandomGame[2][8] = findViewById(R.id.playerCellGame_3x9);
        textViewArrayActivityRandomGame[2][9] = findViewById(R.id.playerCellGame_3x10);

        textViewArrayActivityRandomGame[3][0] = findViewById(R.id.playerCellGame_4x1);
        textViewArrayActivityRandomGame[3][1] = findViewById(R.id.playerCellGame_4x2);
        textViewArrayActivityRandomGame[3][2] = findViewById(R.id.playerCellGame_4x3);
        textViewArrayActivityRandomGame[3][3] = findViewById(R.id.playerCellGame_4x4);
        textViewArrayActivityRandomGame[3][4] = findViewById(R.id.playerCellGame_4x5);
        textViewArrayActivityRandomGame[3][5] = findViewById(R.id.playerCellGame_4x6);
        textViewArrayActivityRandomGame[3][6] = findViewById(R.id.playerCellGame_4x7);
        textViewArrayActivityRandomGame[3][7] = findViewById(R.id.playerCellGame_4x8);
        textViewArrayActivityRandomGame[3][8] = findViewById(R.id.playerCellGame_4x9);
        textViewArrayActivityRandomGame[3][9] = findViewById(R.id.playerCellGame_4x10);

        textViewArrayActivityRandomGame[4][0] = findViewById(R.id.playerCellGame_5x1);
        textViewArrayActivityRandomGame[4][1] = findViewById(R.id.playerCellGame_5x2);
        textViewArrayActivityRandomGame[4][2] = findViewById(R.id.playerCellGame_5x3);
        textViewArrayActivityRandomGame[4][3] = findViewById(R.id.playerCellGame_5x4);
        textViewArrayActivityRandomGame[4][4] = findViewById(R.id.playerCellGame_5x5);
        textViewArrayActivityRandomGame[4][5] = findViewById(R.id.playerCellGame_5x6);
        textViewArrayActivityRandomGame[4][6] = findViewById(R.id.playerCellGame_5x7);
        textViewArrayActivityRandomGame[4][7] = findViewById(R.id.playerCellGame_5x8);
        textViewArrayActivityRandomGame[4][8] = findViewById(R.id.playerCellGame_5x9);
        textViewArrayActivityRandomGame[4][9] = findViewById(R.id.playerCellGame_5x10);

        textViewArrayActivityRandomGame[5][0] = findViewById(R.id.playerCellGame_6x1);
        textViewArrayActivityRandomGame[5][1] = findViewById(R.id.playerCellGame_6x2);
        textViewArrayActivityRandomGame[5][2] = findViewById(R.id.playerCellGame_6x3);
        textViewArrayActivityRandomGame[5][3] = findViewById(R.id.playerCellGame_6x4);
        textViewArrayActivityRandomGame[5][4] = findViewById(R.id.playerCellGame_6x5);
        textViewArrayActivityRandomGame[5][5] = findViewById(R.id.playerCellGame_6x6);
        textViewArrayActivityRandomGame[5][6] = findViewById(R.id.playerCellGame_6x7);
        textViewArrayActivityRandomGame[5][7] = findViewById(R.id.playerCellGame_6x8);
        textViewArrayActivityRandomGame[5][8] = findViewById(R.id.playerCellGame_6x9);
        textViewArrayActivityRandomGame[5][9] = findViewById(R.id.playerCellGame_6x10);

        textViewArrayActivityRandomGame[6][0] = findViewById(R.id.playerCellGame_7x1);
        textViewArrayActivityRandomGame[6][1] = findViewById(R.id.playerCellGame_7x2);
        textViewArrayActivityRandomGame[6][2] = findViewById(R.id.playerCellGame_7x3);
        textViewArrayActivityRandomGame[6][3] = findViewById(R.id.playerCellGame_7x4);
        textViewArrayActivityRandomGame[6][4] = findViewById(R.id.playerCellGame_7x5);
        textViewArrayActivityRandomGame[6][5] = findViewById(R.id.playerCellGame_7x6);
        textViewArrayActivityRandomGame[6][6] = findViewById(R.id.playerCellGame_7x7);
        textViewArrayActivityRandomGame[6][7] = findViewById(R.id.playerCellGame_7x8);
        textViewArrayActivityRandomGame[6][8] = findViewById(R.id.playerCellGame_7x9);
        textViewArrayActivityRandomGame[6][9] = findViewById(R.id.playerCellGame_7x10);

        textViewArrayActivityRandomGame[7][0] = findViewById(R.id.playerCellGame_8x1);
        textViewArrayActivityRandomGame[7][1] = findViewById(R.id.playerCellGame_8x2);
        textViewArrayActivityRandomGame[7][2] = findViewById(R.id.playerCellGame_8x3);
        textViewArrayActivityRandomGame[7][3] = findViewById(R.id.playerCellGame_8x4);
        textViewArrayActivityRandomGame[7][4] = findViewById(R.id.playerCellGame_8x5);
        textViewArrayActivityRandomGame[7][5] = findViewById(R.id.playerCellGame_8x6);
        textViewArrayActivityRandomGame[7][6] = findViewById(R.id.playerCellGame_8x7);
        textViewArrayActivityRandomGame[7][7] = findViewById(R.id.playerCellGame_8x8);
        textViewArrayActivityRandomGame[7][8] = findViewById(R.id.playerCellGame_8x9);
        textViewArrayActivityRandomGame[7][9] = findViewById(R.id.playerCellGame_8x10);

        textViewArrayActivityRandomGame[8][0] = findViewById(R.id.playerCellGame_9x1);
        textViewArrayActivityRandomGame[8][1] = findViewById(R.id.playerCellGame_9x2);
        textViewArrayActivityRandomGame[8][2] = findViewById(R.id.playerCellGame_9x3);
        textViewArrayActivityRandomGame[8][3] = findViewById(R.id.playerCellGame_9x4);
        textViewArrayActivityRandomGame[8][4] = findViewById(R.id.playerCellGame_9x5);
        textViewArrayActivityRandomGame[8][5] = findViewById(R.id.playerCellGame_9x6);
        textViewArrayActivityRandomGame[8][6] = findViewById(R.id.playerCellGame_9x7);
        textViewArrayActivityRandomGame[8][7] = findViewById(R.id.playerCellGame_9x8);
        textViewArrayActivityRandomGame[8][8] = findViewById(R.id.playerCellGame_9x9);
        textViewArrayActivityRandomGame[8][9] = findViewById(R.id.playerCellGame_9x10);

        textViewArrayActivityRandomGame[9][0] = findViewById(R.id.playerCellGame_10x1);
        textViewArrayActivityRandomGame[9][1] = findViewById(R.id.playerCellGame_10x2);
        textViewArrayActivityRandomGame[9][2] = findViewById(R.id.playerCellGame_10x3);
        textViewArrayActivityRandomGame[9][3] = findViewById(R.id.playerCellGame_10x4);
        textViewArrayActivityRandomGame[9][4] = findViewById(R.id.playerCellGame_10x5);
        textViewArrayActivityRandomGame[9][5] = findViewById(R.id.playerCellGame_10x6);
        textViewArrayActivityRandomGame[9][6] = findViewById(R.id.playerCellGame_10x7);
        textViewArrayActivityRandomGame[9][7] = findViewById(R.id.playerCellGame_10x8);
        textViewArrayActivityRandomGame[9][8] = findViewById(R.id.playerCellGame_10x9);
        textViewArrayActivityRandomGame[9][9] = findViewById(R.id.playerCellGame_10x10);
    }



    void showAndSaveOneCellPlayerTwo(int i, int j){

            TextViewArrayActivityRandomGamePlayerTwo[i][j].setClickable(false);
            if (battleFieldPlayerTwoActivityRandomGame.getBattleField(i, j).isShip()) {
                if(zatopiony(battleFieldPlayerTwoActivityRandomGame.battleField[i][j].getNumberOfMasts(),battleFieldPlayerTwoActivityRandomGame.battleField[i][j].getShipNumber())){
                    final int sdk = android.os.Build.VERSION.SDK_INT;
                    if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        TextViewArrayActivityRandomGamePlayerTwo[i][j].setBackgroundDrawable(getResources().getDrawable(R.drawable.ship_cell));
                    } else {
                        TextViewArrayActivityRandomGamePlayerTwo[i][j].setBackground(getResources().getDrawable(R.drawable.ship_cell));
                    }
                }
                else{
                    final int sdk = android.os.Build.VERSION.SDK_INT;
                    if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        TextViewArrayActivityRandomGamePlayerTwo[i][j].setBackgroundDrawable(getResources().getDrawable(R.drawable.red_ship));
                    } else {
                        TextViewArrayActivityRandomGamePlayerTwo[i][j].setBackground(getResources().getDrawable(R.drawable.red_ship));
                    }
                }
                showShipHit(i,j);
            } else {
                final int sdk = android.os.Build.VERSION.SDK_INT;
                if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    TextViewArrayActivityRandomGamePlayerTwo[i][j].setBackgroundDrawable(getResources().getDrawable(R.drawable.water_cell));
                } else {
                    TextViewArrayActivityRandomGamePlayerTwo[i][j].setBackground(getResources().getDrawable(R.drawable.water_cell));
                }
                playerOneCounter=false;
                playerTwoCounter=true;
            }
            battleFieldPlayerTwoActivityRandomGame.battleField[i][j].setHit(true);
            BattleFieldPlayerTwoSingleton.getInstance().storeOneCell(battleFieldPlayerTwoActivityRandomGame, i, j);

            disableClickable();

    }

    void setShipHit(TextView[]textViews, int noOfMasts){
        for(int i = 0;i<noOfMasts;i++){
            textViews[i].setBackgroundColor(getResources().getColor(R.color.ship));
        }
    }

    private void showShipHit(int i, int j) {
        int numberOfMasts = battleFieldPlayerTwoActivityRandomGame.getBattleField(i,j).getNumberOfMasts();
        int Shipnumber = battleFieldPlayerTwoActivityRandomGame.getBattleField(i,j).getShipNumber();
        if(numberOfMasts==4){
            if(ShipFourMastsCounter==3){
                setShipHit(ShipFourMasts,4);
            }else;
            ShipFourMastsCounter++;
        }
        else if(numberOfMasts==3){
            if(Shipnumber==1){
                if(ShipThreeMastsCounterFirst==2) {
                    setShipHit(ShipThreeMastsFirst,3);
                }else;
                ShipThreeMastsCounterFirst++;
            }
            else if(Shipnumber==2){
                if(ShipThreeMastsCounterSecond==2) {
                    setShipHit(ShipThreeMastsSecond,3);
                }else;
                ShipThreeMastsCounterSecond++;
            }
            else;
        }
        else if(numberOfMasts==2){
                if(Shipnumber==1){
                    if(ShipTwoMastsCounterFirst==1) {
                        setShipHit(ShipTwoMastsFirst,2);
                    }
                    else;
                    ShipTwoMastsCounterFirst++;
                }
                else if(Shipnumber==2){
                    if(ShipTwoMastsCounterSecond==1) {
                        setShipHit(ShipTwoMastsSecond,2);
                    }else;
                    ShipTwoMastsCounterSecond++;
                }
                else if(Shipnumber==3){
                    if(ShipTwoMastsCounterThird==1) {
                        setShipHit(ShipTwoMastsThird,2);
                    }
                    else;
                    ShipTwoMastsCounterThird++;
                }
                else;
        }
        else if(numberOfMasts==1){
            if(Shipnumber==1){
                ShipOneMastsFirst[0].setBackgroundColor(getResources().getColor(R.color.ship));
            }
            else if(Shipnumber==2){
                ShipOneMastsSecond[0].setBackgroundColor(getResources().getColor(R.color.ship));
            }
            else if(Shipnumber==3){
                ShipOneMastsThird[0].setBackgroundColor(getResources().getColor(R.color.ship));
            }
            else if(Shipnumber==4){
                ShipOneMastsFourth[0].setBackgroundColor(getResources().getColor(R.color.ship));
            }
            else;

        }
        else;
    }

    private void disableClickable() {
        for(int i = 0;i<10;i++){
            for(int j=0;j<10;j++){
                TextViewArrayActivityRandomGamePlayerTwo[i][j].setClickable(false);
            }
        }
    }

    private void readClicable() {
        for(int i = 0;i<10;i++){
            for(int j=0;j<10;j++){
                TextViewArrayActivityRandomGamePlayerTwo[i][j].setClickable(!BattleFieldPlayerTwoSingleton.getInstance().battleField.battleField[i][j].isHit());
            }
        }
    }


    public void clickPlayer2CellGame_1x1(View view) {
        showAndSaveOneCellPlayerTwo(0,0);
    }

    public void clickPlayer2CellGame_1x2(View view) {
        showAndSaveOneCellPlayerTwo(0,1);
    }

    public void clickPlayer2CellGame_1x3(View view) {
        showAndSaveOneCellPlayerTwo(0,2);
    }

    public void clickPlayer2CellGame_1x4(View view) {
        showAndSaveOneCellPlayerTwo(0,3);
    }

    public void clickPlayer2CellGame_1x5(View view) {
        showAndSaveOneCellPlayerTwo(0,4);
    }

    public void clickPlayer2CellGame_1x6(View view) {
        showAndSaveOneCellPlayerTwo(0,5);
    }

    public void clickPlayer2CellGame_1x7(View view) {
        showAndSaveOneCellPlayerTwo(0,6);
    }

    public void clickPlayer2CellGame_1x8(View view) {
        showAndSaveOneCellPlayerTwo(0,7);
    }

    public void clickPlayer2CellGame_1x9(View view) {
        showAndSaveOneCellPlayerTwo(0,8);
    }

    public void clickPlayer2CellGame_1x10(View view) {
        showAndSaveOneCellPlayerTwo(0,9);
    }

    public void clickPlayer2CellGame_2x1(View view) {
        showAndSaveOneCellPlayerTwo(1,0);
    }

    public void clickPlayer2CellGame_2x2(View view) {
        showAndSaveOneCellPlayerTwo(1,1);
    }

    public void clickPlayer2CellGame_2x3(View view) {
        showAndSaveOneCellPlayerTwo(1,2);
    }

    public void clickPlayer2CellGame_2x4(View view) {
        showAndSaveOneCellPlayerTwo(1,3);
    }

    public void clickPlayer2CellGame_2x5(View view) {
        showAndSaveOneCellPlayerTwo(1,4);
    }

    public void clickPlayer2CellGame_2x6(View view) {
        showAndSaveOneCellPlayerTwo(1,5);
    }

    public void clickPlayer2CellGame_2x7(View view) {
        showAndSaveOneCellPlayerTwo(1,6);
    }

    public void clickPlayer2CellGame_2x8(View view) {
        showAndSaveOneCellPlayerTwo(1,7);
    }

    public void clickPlayer2CellGame_2x9(View view) {
        showAndSaveOneCellPlayerTwo(1,8);
    }

    public void clickPlayer2CellGame_2x10(View view) {
        showAndSaveOneCellPlayerTwo(1,9);
    }

    public void clickPlayer2CellGame_3x1(View view) {
        showAndSaveOneCellPlayerTwo(2,0);
    }

    public void clickPlayer2CellGame_3x2(View view) {
        showAndSaveOneCellPlayerTwo(2,1);
    }

    public void clickPlayer2CellGame_3x3(View view) {
        showAndSaveOneCellPlayerTwo(2,2);
    }

    public void clickPlayer2CellGame_3x4(View view) {
        showAndSaveOneCellPlayerTwo(2,3);
    }

    public void clickPlayer2CellGame_3x5(View view) {
        showAndSaveOneCellPlayerTwo(2,4);
    }

    public void clickPlayer2CellGame_3x6(View view) {
        showAndSaveOneCellPlayerTwo(2,5);
    }

    public void clickPlayer2CellGame_3x7(View view) {
        showAndSaveOneCellPlayerTwo(2,6);
    }

    public void clickPlayer2CellGame_3x8(View view) {
        showAndSaveOneCellPlayerTwo(2,7);
    }

    public void clickPlayer2CellGame_3x9(View view) {
        showAndSaveOneCellPlayerTwo(2,8);
    }

    public void clickPlayer2CellGame_3x10(View view) {
        showAndSaveOneCellPlayerTwo(2,9);
    }

    public void clickPlayer2CellGame_4x1(View view) {
        showAndSaveOneCellPlayerTwo(3,0);
    }

    public void clickPlayer2CellGame_4x2(View view) {
        showAndSaveOneCellPlayerTwo(3,1);
    }

    public void clickPlayer2CellGame_4x3(View view) {
        showAndSaveOneCellPlayerTwo(3,2);
    }

    public void clickPlayer2CellGame_4x4(View view) {
        showAndSaveOneCellPlayerTwo(3,3);
    }

    public void clickPlayer2CellGame_4x5(View view) {
        showAndSaveOneCellPlayerTwo(3,4);
    }

    public void clickPlayer2CellGame_4x6(View view) {
        showAndSaveOneCellPlayerTwo(3,5);
    }

    public void clickPlayer2CellGame_4x7(View view) {
        showAndSaveOneCellPlayerTwo(3,6);
    }

    public void clickPlayer2CellGame_4x8(View view) {
        showAndSaveOneCellPlayerTwo(3,7);
    }

    public void clickPlayer2CellGame_4x9(View view) {
        showAndSaveOneCellPlayerTwo(3,8);
    }

    public void clickPlayer2CellGame_4x10(View view) {
        showAndSaveOneCellPlayerTwo(3,9);
    }

    public void clickPlayer2CellGame_5x1(View view) {
        showAndSaveOneCellPlayerTwo(4,0);
    }

    public void clickPlayer2CellGame_5x2(View view) {
        showAndSaveOneCellPlayerTwo(4,1);
    }

    public void clickPlayer2CellGame_5x3(View view) {
        showAndSaveOneCellPlayerTwo(4,2);
    }

    public void clickPlayer2CellGame_5x4(View view) {
        showAndSaveOneCellPlayerTwo(4,3);
    }

    public void clickPlayer2CellGame_5x5(View view) {
        showAndSaveOneCellPlayerTwo(4,4);
    }

    public void clickPlayer2CellGame_5x6(View view) {
        showAndSaveOneCellPlayerTwo(4,5);
    }

    public void clickPlayer2CellGame_5x7(View view) {
        showAndSaveOneCellPlayerTwo(4,6);
    }

    public void clickPlayer2CellGame_5x8(View view) {
        showAndSaveOneCellPlayerTwo(4,7);
    }

    public void clickPlayer2CellGame_5x9(View view) {
        showAndSaveOneCellPlayerTwo(4,8);
    }

    public void clickPlayer2CellGame_5x10(View view) {
        showAndSaveOneCellPlayerTwo(4,9);
    }

    public void clickPlayer2CellGame_6x1(View view) {
        showAndSaveOneCellPlayerTwo(5,0);
    }

    public void clickPlayer2CellGame_6x2(View view) {
        showAndSaveOneCellPlayerTwo(5,1);
    }

    public void clickPlayer2CellGame_6x3(View view) {
        showAndSaveOneCellPlayerTwo(5,2);
    }

    public void clickPlayer2CellGame_6x4(View view) {
        showAndSaveOneCellPlayerTwo(5,3);
    }

    public void clickPlayer2CellGame_6x5(View view) {
        showAndSaveOneCellPlayerTwo(5,4);
    }

    public void clickPlayer2CellGame_6x6(View view) {
        showAndSaveOneCellPlayerTwo(5,5);
    }

    public void clickPlayer2CellGame_6x7(View view) {
        showAndSaveOneCellPlayerTwo(5,6);
    }

    public void clickPlayer2CellGame_6x8(View view) {
        showAndSaveOneCellPlayerTwo(5,7);
    }

    public void clickPlayer2CellGame_6x9(View view) {
        showAndSaveOneCellPlayerTwo(5,8);
    }

    public void clickPlayer2CellGame_6x10(View view) {
        showAndSaveOneCellPlayerTwo(5,9);
    }

    public void clickPlayer2CellGame_7x1(View view) {
        showAndSaveOneCellPlayerTwo(6,0);
    }

    public void clickPlayer2CellGame_7x2(View view) {
        showAndSaveOneCellPlayerTwo(6,1);
    }

    public void clickPlayer2CellGame_7x3(View view) {
        showAndSaveOneCellPlayerTwo(6,2);
    }

    public void clickPlayer2CellGame_7x4(View view) {
        showAndSaveOneCellPlayerTwo(6,3);
    }

    public void clickPlayer2CellGame_7x5(View view) {
        showAndSaveOneCellPlayerTwo(6,4);
    }

    public void clickPlayer2CellGame_7x6(View view) {
        showAndSaveOneCellPlayerTwo(6,5);
    }

    public void clickPlayer2CellGame_7x7(View view) {
        showAndSaveOneCellPlayerTwo(6,6);
    }

    public void clickPlayer2CellGame_7x8(View view) {
        showAndSaveOneCellPlayerTwo(6,7);
    }

    public void clickPlayer2CellGame_7x9(View view) {
        showAndSaveOneCellPlayerTwo(6,8);
    }

    public void clickPlayer2CellGame_7x10(View view) {
        showAndSaveOneCellPlayerTwo(6,9);
    }

    public void clickPlayer2CellGame_8x1(View view) {
        showAndSaveOneCellPlayerTwo(7,0);
    }

    public void clickPlayer2CellGame_8x2(View view) {
        showAndSaveOneCellPlayerTwo(7,1);
    }

    public void clickPlayer2CellGame_8x3(View view) {
        showAndSaveOneCellPlayerTwo(7,2);
    }

    public void clickPlayer2CellGame_8x4(View view) {
        showAndSaveOneCellPlayerTwo(7,3);
    }

    public void clickPlayer2CellGame_8x5(View view) {
        showAndSaveOneCellPlayerTwo(7,4);
    }

    public void clickPlayer2CellGame_8x6(View view) {
        showAndSaveOneCellPlayerTwo(7,5);
    }

    public void clickPlayer2CellGame_8x7(View view) {
        showAndSaveOneCellPlayerTwo(7,6);
    }

    public void clickPlayer2CellGame_8x8(View view) {
        showAndSaveOneCellPlayerTwo(7,7);
    }

    public void clickPlayer2CellGame_8x9(View view) {
        showAndSaveOneCellPlayerTwo(7,8);
    }

    public void clickPlayer2CellGame_8x10(View view) {
        showAndSaveOneCellPlayerTwo(7,9);
    }

    public void clickPlayer2CellGame_9x1(View view) {
        showAndSaveOneCellPlayerTwo(8,0);
    }

    public void clickPlayer2CellGame_9x2(View view) {
        showAndSaveOneCellPlayerTwo(8,1);
    }

    public void clickPlayer2CellGame_9x3(View view) {
        showAndSaveOneCellPlayerTwo(8,2);
    }

    public void clickPlayer2CellGame_9x4(View view) {
        showAndSaveOneCellPlayerTwo(8,3);
    }

    public void clickPlayer2CellGame_9x5(View view) {
        showAndSaveOneCellPlayerTwo(8,4);
    }

    public void clickPlayer2CellGame_9x6(View view) {
        showAndSaveOneCellPlayerTwo(8,5);
    }

    public void clickPlayer2CellGame_9x7(View view) {
        showAndSaveOneCellPlayerTwo(8,6);
    }

    public void clickPlayer2CellGame_9x8(View view) {
        showAndSaveOneCellPlayerTwo(8,7);
    }

    public void clickPlayer2CellGame_9x9(View view) {
        showAndSaveOneCellPlayerTwo(8,8);
    }

    public void clickPlayer2CellGame_9x10(View view) {
        showAndSaveOneCellPlayerTwo(8,9);
    }

    public void clickPlayer2CellGame_10x1(View view) {
        showAndSaveOneCellPlayerTwo(9,0);
    }

    public void clickPlayer2CellGame_10x2(View view) {
        showAndSaveOneCellPlayerTwo(9,1);
    }

    public void clickPlayer2CellGame_10x3(View view) {
        showAndSaveOneCellPlayerTwo(9,2);
    }

    public void clickPlayer2CellGame_10x4(View view) {
        showAndSaveOneCellPlayerTwo(9,3);
    }

    public void clickPlayer2CellGame_10x5(View view) {
        showAndSaveOneCellPlayerTwo(9,4);
    }

    public void clickPlayer2CellGame_10x6(View view) {
        showAndSaveOneCellPlayerTwo(9,5);
    }

    public void clickPlayer2CellGame_10x7(View view) {
        showAndSaveOneCellPlayerTwo(9,6);
    }

    public void clickPlayer2CellGame_10x8(View view) {
        showAndSaveOneCellPlayerTwo(9,7);
    }

    public void clickPlayer2CellGame_10x9(View view) {
        showAndSaveOneCellPlayerTwo(9,8);
    }

    public void clickPlayer2CellGame_10x10(View view) {
        showAndSaveOneCellPlayerTwo(9,9);
    }
}
