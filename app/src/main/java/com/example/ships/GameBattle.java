package com.example.ships;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ships.classes.BattleField;
import com.example.ships.classes.GameDifficulty;
import com.example.ships.singletons.BattleFieldPlayerOneSingleton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

public class GameBattle extends AppCompatActivity implements View.OnTouchListener{

    private Handler mHandler = new Handler();
    private BattleField battleFieldMeActivityRandomGame = new BattleField();
     private BattleField battleFieldOpponentActivityRandomGame = new BattleField();

     private TextView tv1,tv2,tv11;
     private GridLayout layout;
     int[]location1 = new int[2];
     int[]location2 = new int[2];
     int[]location11 = new int[2];
     int[]locationLayout = new int [2];
     private int height, width;

    private TextView[][] TextViewArrayActivityRandomGameMe = new TextView[10][10];

    private int battleFieldOpponent[][] = new int[10][10];
    private final static int BATTLE_CELL = 0;
    private final static int WATER = 1;
    private final static int SHIP_RED = 2;
    private final static int SHIP_BROWN = 3;

    private TextView[] ShipFourMasts = new TextView[4];
    private int shipFourMastsCounter = 0;
    private TextView[] ShipThreeMastsFirst = new TextView[3];
    private int shipThreeMastsCounterFirst = 0;
    private TextView[] ShipThreeMastsSecond = new TextView[3];
    private int shipThreeMastsCounterSecond = 0;
    private TextView[] ShipTwoMastsFirst = new TextView[2];
    private int shipTwoMastsCounterFirst = 0;
    private TextView[] ShipTwoMastsSecond = new TextView[2];
    private int shipTwoMastsCounterSecond = 0;
    private TextView[] ShipTwoMastsThird = new TextView[2];
    private int shipTwoMastsCounterThird = 0;
    private TextView[] ShipOneMastsFirst = new TextView[1];
    private int shipOneMastsCounterFirst = 0;
    private TextView[] ShipOneMastsSecond = new TextView[1];
    private int shipOneMastsCounterSecond = 0;
    private TextView[] ShipOneMastsThird = new TextView[1];
    private int shipOneMastsCounterThird = 0;
    private TextView[] ShipOneMastsFourth = new TextView[1];
    private int shipOneMastsCounterFourth = 0;
    private boolean myTurn;
    private int level = GameDifficulty.getInstance().getLevel();
    private boolean newShoot=true;
    private int positionI;
    private int positionJ;
    private int direction;
    private int x;
    private int y;
    private boolean enableTouchListener;
    private boolean loggedIn;
    private long noOfGames;
    private long score;
    private int deelay=1000;




    ArrayList<Integer>ShootTable = new ArrayList<>();

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String userID;
    private TextView tv;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_random_game_battle);

        enableTouchListener=false;
        initializeTable(ShootTable);

        initializeShips();
        initializeBattleFieldActivityRandomGamePlayerOne(TextViewArrayActivityRandomGameMe);

        tv1=findViewById(R.id.player2CellGame_1x1);
        tv2=findViewById(R.id.player2CellGame_1x2);
        tv11=findViewById(R.id.player2CellGame_2x1);
        layout = findViewById(R.id.tableLayoutPlayer2BattleField);
        tv1.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                tv1.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                tv1.getLocationOnScreen(location1);
            }
        });
        tv2.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                tv2.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                tv2.getLocationOnScreen(location2);
            }
        });
        tv11.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                tv11.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                tv11.getLocationOnScreen(location11);
                height=location11[1]-location1[1];
                width= location2[0]-location1[0];
            }
        });
        layout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                layout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                layout.getLocationOnScreen(locationLayout);

            }
        });
        layout.setOnTouchListener(this);

       if(GameDifficulty.getInstance().getRandom()) {
           battleFieldMeActivityRandomGame.createFleet();
       }else{
           battleFieldMeActivityRandomGame = BattleFieldPlayerOneSingleton.getInstance().readBattleField();
       }

        battleFieldOpponentActivityRandomGame.createFleet();


        displayBattleFieldActivityRandomGamePlayerOne(TextViewArrayActivityRandomGameMe, battleFieldMeActivityRandomGame);


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        if (firebaseAuth.getCurrentUser() != null) {
            if(firebaseAuth.getCurrentUser().isEmailVerified()){
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                userID = firebaseUser.getUid();
                loggedIn = true;
                databaseReference=firebaseDatabase.getReference("User").child(userID);

            }else{
                loggedIn = false;
            }

        }
        if (loggedIn) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                noOfGames= (Long) dataSnapshot.child("noOfGames").getValue();
                noOfGames = noOfGames+1;
                databaseReference.child("noOfGames").setValue(noOfGames);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
        game.run();
    }

    private void initializeTable(ArrayList<Integer> shootTable) {
        for(int i=0;i<100;i++){
            shootTable.add(i);
        }
    }

    private boolean zatopiony2(int noOfMasts, int shipNo){
        int counter=0;
        for(int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                if(battleFieldMeActivityRandomGame.battleField[i][j].getNumberOfMasts()==noOfMasts
                        && battleFieldMeActivityRandomGame.battleField[i][j].getShipNumber()==shipNo
                        && battleFieldMeActivityRandomGame.battleField[i][j].isHit()){
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
    //TODO wyczyścić kod

    private Runnable game = new Runnable() {
        @Override
        public void run() {
                play();

        }
    };

    private void play() {
        if(!myWin()&&!battleFieldMeActivityRandomGame.allShipsHit()) //game
        {
            battle();
        }
        else if (myWin()&&!battleFieldMeActivityRandomGame.allShipsHit())      // allShipsHit player
        {

            if(loggedIn) {
                updateRanking();

            }
            Intent intent = new Intent(getApplicationContext(),WinPlayerOne.class);
            startActivity(intent);
            finish();
        }
        else if (!myWin()&& battleFieldMeActivityRandomGame.allShipsHit())     // allShipsHit computer
        {

            Intent intent = new Intent(getApplicationContext(),WinPlayerTwo.class);
            startActivity(intent);
            finish();
        }
        else;
    }

    private void updateRanking() {


        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                score= (Long) dataSnapshot.child("score").getValue();
                addPoints();
                databaseReference.child("score").setValue(score);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void addPoints() {

        if(level==0){
            score = score+1;
        }else if(level==2){
            score = score+10;
        }else if(level==3){
            score = score+100;
        }else;

    }

    public void battle(){

        if(myTurn){

           pokazStatki();

            hideBattleFiledAvailablePlayerOne();
            enableTouchListener=true;
        }
        else{
            hideBattleFiledAvailablePlayerTwo();
            showBattleFieldAvailablePlayerOne();
            shoot();
            mHandler.postDelayed(game,deelay);
        }
    }

    private void pokazStatki() {

        for(int i =0;i<10;i++){
            for(int j = 0; j<10;j++){
                if(battleFieldOpponent[i][j]==SHIP_BROWN){
                    layout.getChildAt(i*10+j).setBackground(getDrawable(R.drawable.ship_cell));
                }
                else if(battleFieldOpponent[i][j]==SHIP_RED){
                    layout.getChildAt(i*10+j).setBackground(getDrawable(R.drawable.red_ship));
                }else if(battleFieldOpponent[i][j]==WATER){
                    layout.getChildAt(i*10+j).setBackground(getDrawable(R.drawable.water_cell));
                }else if(battleFieldOpponent[i][j]==BATTLE_CELL){
                    layout.getChildAt(i*10+j).setBackground(getDrawable(R.drawable.battle_cell));
                }else;

            }
        }

    }

    private void showBattleFieldAvailablePlayerOne() {
        for(int i =0;i<10;i++){
            for(int j = 0; j<10;j++){
                //jest statek i został trafiony
                if(battleFieldMeActivityRandomGame.getBattleField(i,j).isShip()
                        && battleFieldMeActivityRandomGame.getBattleField(i,j).isHit()){
                    displayShipCell(TextViewArrayActivityRandomGameMe,i,j);
                }

                // woda i została trafiony
                else if(!battleFieldMeActivityRandomGame.getBattleField(i,j).isShip()
                        && battleFieldMeActivityRandomGame.getBattleField(i,j).isHit()){
                    displayWaterCell(TextViewArrayActivityRandomGameMe,i,j);
                }

                // jest statek i nie został trafiony
                else if(battleFieldMeActivityRandomGame.getBattleField(i,j).isShip()
                &&!battleFieldMeActivityRandomGame.getBattleField(i,j).isHit()){
                    displayWidmoShip(TextViewArrayActivityRandomGameMe,i,j);
                }
                // nie ma statku i nie został trafiony
                else if(!battleFieldMeActivityRandomGame.getBattleField(i,j).isShip()
                        &&!battleFieldMeActivityRandomGame.getBattleField(i,j).isHit()){
                    displayBattleCell(TextViewArrayActivityRandomGameMe,i,j);
                }
                else;
            }
        }
    }

    private void displayBattleCell(TextView[][] TextView, int i, int j) {
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            TextView[i][j].setBackgroundDrawable(getResources().getDrawable(R.drawable.battle_cell));
        } else {
            TextView[i][j].setBackground(getResources().getDrawable(R.drawable.battle_cell));
        }
    }

    private void displayWidmoShip(TextView[][] TextView, int i, int j){
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            TextView[i][j].setBackgroundDrawable(getResources().getDrawable(R.drawable.widmo_ship_cell));
        } else {
            TextView[i][j].setBackground(getResources().getDrawable(R.drawable.widmo_ship_cell));
        }
    }

    private void displayWaterCell(TextView[][] TextView, int i, int j) {
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            TextView[i][j].setBackgroundDrawable(getResources().getDrawable(R.drawable.water_cell));
        } else {
            TextView[i][j].setBackground(getResources().getDrawable(R.drawable.water_cell));
        }
    }

    private void displayShipCell(TextView[][] TextView, int i, int j) {
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            TextView[i][j].setBackgroundDrawable(getResources().getDrawable(R.drawable.ship_cell));
        } else {
            TextView[i][j].setBackground(getResources().getDrawable(R.drawable.ship_cell));
        }
    }

    private void displayShipCellHidden(TextView[][] TextView, int i, int j){
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            TextView[i][j].setBackgroundDrawable(getResources().getDrawable(R.drawable.ship_cell_hidden));
        } else {
            TextView[i][j].setBackground(getResources().getDrawable(R.drawable.ship_cell_hidden));
        }
    }

    private void displayWaterCellHidden(TextView[][] TextView,int i, int j){
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            TextView[i][j].setBackgroundDrawable(getResources().getDrawable(R.drawable.water_cell_hiden));
        } else {
            TextView[i][j].setBackground(getResources().getDrawable(R.drawable.water_cell_hiden));
        }

    }

    private void displayWidmoShipHidden(TextView[][] TextView,int i, int j){
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            TextView[i][j].setBackgroundDrawable(getResources().getDrawable(R.drawable.widmo_ship_hidden));
        } else {
            TextView[i][j].setBackground(getResources().getDrawable(R.drawable.widmo_ship_hidden));
        }
    }

    private void displayBattleCellHidden(TextView[][] TextView,int i, int j){
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            TextView[i][j].setBackgroundDrawable(getResources().getDrawable(R.drawable.battle_cell_hidden));
        } else {
            TextView[i][j].setBackground(getResources().getDrawable(R.drawable.battle_cell_hidden));
        }
    }

    private void hideBattleFiledAvailablePlayerOne() {
        for(int i =0;i<10;i++){
            for(int j = 0; j<10;j++){
                //jest statek i został trafiony
                if(battleFieldMeActivityRandomGame.getBattleField(i,j).isShip()
                        && battleFieldMeActivityRandomGame.getBattleField(i,j).isHit()){
                    displayShipCellHidden(TextViewArrayActivityRandomGameMe,i,j);
                }

                // woda i została trafiony
                else if(!battleFieldMeActivityRandomGame.getBattleField(i,j).isShip()
                        && battleFieldMeActivityRandomGame.getBattleField(i,j).isHit()){
                    displayWaterCellHidden(TextViewArrayActivityRandomGameMe,i,j);
                }

                // jest statek i nie został trafiony
                else if(battleFieldMeActivityRandomGame.getBattleField(i,j).isShip()
                        &&!battleFieldMeActivityRandomGame.getBattleField(i,j).isHit()){
                        displayWidmoShipHidden(TextViewArrayActivityRandomGameMe,i,j);
                }
                // nie ma statku i nie został trafiony
                else if(!battleFieldMeActivityRandomGame.getBattleField(i,j).isShip()
                        &&!battleFieldMeActivityRandomGame.getBattleField(i,j).isHit()){
                        displayBattleCellHidden(TextViewArrayActivityRandomGameMe,i,j);
                }

                else;
            }
        }

    }

    private void hideBattleFiledAvailablePlayerTwo() {

        for(int i =0;i<10;i++){
            for(int j = 0; j<10;j++){
                if(battleFieldOpponent[i][j]==SHIP_BROWN){
                    layout.getChildAt(i*10+j).setBackground(getDrawable(R.drawable.ship_cell_hidden));
                }
                else if(battleFieldOpponent[i][j]==SHIP_RED){
                    layout.getChildAt(i*10+j).setBackground(getDrawable(R.drawable.ship_cell_hidden));
                }else if(battleFieldOpponent[i][j]==WATER){
                    layout.getChildAt(i*10+j).setBackground(getDrawable(R.drawable.water_cell_hiden));
                }else{
                    layout.getChildAt(i*10+j).setBackground(getDrawable(R.drawable.battle_cell_hidden));
                }

            }
        }

    }

    private void shoot() {
        if (newShoot) {
            Random random = new Random();
            int shoot = random.nextInt(ShootTable.size()-1);
            int i=ShootTable.get(shoot)/10;
            int j=ShootTable.get(shoot)%10;
            if (battleFieldMeActivityRandomGame.getBattleField(i, j).isHit()) {
                ShootTable.remove(Integer.valueOf(i*10+j));
                shoot();
            } else {
                if(level==3&&checkCell(i,j)){
                    ShootTable.remove(Integer.valueOf(i*10+j));
                   shoot();
                }
                else{
                if (battleFieldMeActivityRandomGame.getBattleField(i, j).isShip()) {
                    displayShipCellHit(TextViewArrayActivityRandomGameMe,i,j);
                    ShootTable.remove(Integer.valueOf(i*10+j));
                    battleFieldMeActivityRandomGame.battleField[i][j].setHit(true);
                    if((level==2||level==3)&&!zatopiony2(battleFieldMeActivityRandomGame.getBattleField(i,j).getNumberOfMasts(), battleFieldMeActivityRandomGame.getBattleField(i,j).getShipNumber()))
                    {
                    positionI=i;
                    positionJ=j;
                    newShoot=false;
                    direction=1;
                    x=i-1;
                    }else;

                } else {
                    displayWaterCellHit(TextViewArrayActivityRandomGameMe,i,j);
                    ShootTable.remove(Integer.valueOf(i*10+j));
                    battleFieldMeActivityRandomGame.battleField[i][j].setHit(true);
                    myTurn=true;

                }
            }
            }
        } else
            {
                dobijShip(positionI,positionJ);
        }
    }

    private void displayWaterCellHit(TextView[][] TextView, int i, int j) {
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            TextView[i][j].setBackgroundDrawable(getResources().getDrawable(R.drawable.water_cell_hit));
        } else {
            TextView[i][j].setBackground(getResources().getDrawable(R.drawable.water_cell_hit));
        }
    }

    private void displayShipCellHit(TextView[][] TextView, int i, int j) {
        final int sdk = Build.VERSION.SDK_INT;
        if(sdk < Build.VERSION_CODES.JELLY_BEAN) {
            TextView[i][j].setBackgroundDrawable(getResources().getDrawable(R.drawable.ship_cell_hit));
        } else {
            TextView[i][j].setBackground(getResources().getDrawable(R.drawable.ship_cell_hit));
        }
    }

    private boolean checkCell(int i, int j) {
        for(int i1=0;i1<10;i1++){
            for(int j1=0;j1<10;j1++){
                battleFieldMeActivityRandomGame.battleField[i1][j1].setShipHit(
                        battleFieldMeActivityRandomGame.battleField[i1][j1].isShip()&&
                                battleFieldMeActivityRandomGame.battleField[i1][j1].isHit());
            }
        }


        if (i == 0 && j == 0) {
            if (battleFieldMeActivityRandomGame.battleField[i][j + 1].getIsShipHit() ||
                    battleFieldMeActivityRandomGame.battleField[i + 1][j].getIsShipHit()||
                    battleFieldMeActivityRandomGame.battleField[i + 1][j + 1].getIsShipHit()) {
                return true;
            } else {
                return false;
            }
        } else if ((i > 0) && (i < 9) && j == 0) {
            if (battleFieldMeActivityRandomGame.battleField[i][j + 1].getIsShipHit() ||
                    battleFieldMeActivityRandomGame.battleField[i + 1][j].getIsShipHit() ||
                    battleFieldMeActivityRandomGame.battleField[i + 1][j + 1].getIsShipHit() ||
                    battleFieldMeActivityRandomGame.battleField[i - 1][j].getIsShipHit() ||
                    battleFieldMeActivityRandomGame.battleField[i - 1][j + 1].getIsShipHit()) {
                return true;
            } else {
                return false;
            }
        } else if (i == 9 && j == 0) {
            if (battleFieldMeActivityRandomGame.battleField[i][j + 1].getIsShipHit() ||
                    battleFieldMeActivityRandomGame.battleField[i - 1][j].getIsShipHit() ||
                    battleFieldMeActivityRandomGame.battleField[i - 1][j + 1].getIsShipHit()) {
                return true;
            } else {
                return false;
            }

        } else if (i == 0 && ((j > 0) && (j < 9))) {
            if (battleFieldMeActivityRandomGame.battleField[i][j - 1].getIsShipHit()||
                    battleFieldMeActivityRandomGame.battleField[i][j + 1].getIsShipHit()||
                    battleFieldMeActivityRandomGame.battleField[i + 1][j - 1].getIsShipHit()||
                    battleFieldMeActivityRandomGame.battleField[i + 1][j].getIsShipHit()||
                    battleFieldMeActivityRandomGame.battleField[i + 1][j + 1].getIsShipHit()) {
                return true;
            } else {
                return false;
            }

        } else if (((i > 0) && (i < 9)) && ((j > 0) && (j < 9))) {
            if (battleFieldMeActivityRandomGame.battleField[i][j - 1].getIsShipHit()||
                    battleFieldMeActivityRandomGame.battleField[i][j + 1].getIsShipHit()||
                    battleFieldMeActivityRandomGame.battleField[i + 1][j - 1].getIsShipHit()||
                    battleFieldMeActivityRandomGame.battleField[i + 1][j].getIsShipHit()||
                    battleFieldMeActivityRandomGame.battleField[i + 1][j + 1].getIsShipHit()||
                    battleFieldMeActivityRandomGame.battleField[i - 1][j - 1].getIsShipHit()||
                    battleFieldMeActivityRandomGame.battleField[i - 1][j].getIsShipHit()||
                    battleFieldMeActivityRandomGame.battleField[i - 1][j + 1].getIsShipHit()) {
                return true;
            } else {
                return false;
            }

        } else if (i == 9 && ((j > 0) && (j < 9))) {
            if (battleFieldMeActivityRandomGame.battleField[i][j - 1].getIsShipHit()||
                    battleFieldMeActivityRandomGame.battleField[i][j + 1].getIsShipHit()||
                    battleFieldMeActivityRandomGame.battleField[i - 1][j - 1].getIsShipHit()||
                    battleFieldMeActivityRandomGame.battleField[i - 1][j].getIsShipHit()||
                    battleFieldMeActivityRandomGame.battleField[i - 1][j + 1].getIsShipHit()) {
                return true;
            } else {
                return false;
            }

        } else if (i == 0 && j == 9) {
            if (battleFieldMeActivityRandomGame.battleField[i][j - 1].getIsShipHit()||
                    battleFieldMeActivityRandomGame.battleField[i + 1][j - 1].getIsShipHit()||
                    battleFieldMeActivityRandomGame.battleField[i + 1][j].getIsShipHit()) {
                return true;
            } else {
                return false;
            }

        } else if (((i > 0) && (i < 9)) && (j == 9)) {
            if (battleFieldMeActivityRandomGame.battleField[i][j - 1].getIsShipHit()||
                    battleFieldMeActivityRandomGame.battleField[i + 1][j - 1].getIsShipHit()||
                    battleFieldMeActivityRandomGame.battleField[i + 1][j].getIsShipHit()||
                    battleFieldMeActivityRandomGame.battleField[i - 1][j - 1].getIsShipHit()||
                    battleFieldMeActivityRandomGame.battleField[i - 1][j].getIsShipHit()) {
                return true;
            } else {
                return false;
            }

        } else if ((i == 9) && (j == 9)) {
            if (battleFieldMeActivityRandomGame.battleField[i][j - 1].getIsShipHit()||
                    battleFieldMeActivityRandomGame.battleField[i][j + 1].getIsShipHit()||
                    battleFieldMeActivityRandomGame.battleField[i - 1][j - 1].getIsShipHit()||
                    battleFieldMeActivityRandomGame.battleField[i - 1][j].getIsShipHit()||
                    battleFieldMeActivityRandomGame.battleField[i - 1][j + 1].getIsShipHit()) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    private void dobijShip(int i, int j) {
        if(direction==1){
            if(x>=0){
                if(!battleFieldMeActivityRandomGame.battleField[x][j].isHit()){
                    if(x==0) {
                        if (battleFieldMeActivityRandomGame.battleField[x][j].isShip()) {
                            displayShipCellHit(TextViewArrayActivityRandomGameMe, x, j);
                            ShootTable.remove(Integer.valueOf(x * 10 + j));
                            battleFieldMeActivityRandomGame.battleField[x][j].setHit(true);
                            if (zatopiony2(battleFieldMeActivityRandomGame.getBattleField(i, j).getNumberOfMasts(), battleFieldMeActivityRandomGame.getBattleField(i, j).getShipNumber())) {
                                newShoot = true;
                            } else {
                                x = x - 1;
                            }
                        } else {
                            displayWaterCellHit(TextViewArrayActivityRandomGameMe, x, j);
                            ShootTable.remove(Integer.valueOf(x * 10 + j));
                            battleFieldMeActivityRandomGame.battleField[x][j].setHit(true);
                            myTurn=true;
                            direction = 2;
                            x = i + 1;
                        }
                    }
                    else{
                        if(j==0){
                            if((battleFieldMeActivityRandomGame.battleField[x-1][j].isHit()&&
                                    battleFieldMeActivityRandomGame.battleField[x-1][j].isShip())||
                                    (battleFieldMeActivityRandomGame.battleField[x-1][j+1].isHit()&&
                                            battleFieldMeActivityRandomGame.battleField[x-1][j+1].isShip())){
                                direction = 2;
                                x = i + 1;
                            }else{
                                if (battleFieldMeActivityRandomGame.battleField[x][j].isShip()) {
                                    displayShipCellHit(TextViewArrayActivityRandomGameMe, x, j);
                                    ShootTable.remove(Integer.valueOf(x * 10 + j));
                                    battleFieldMeActivityRandomGame.battleField[x][j].setHit(true);
                                    if (zatopiony2(battleFieldMeActivityRandomGame.getBattleField(i, j).getNumberOfMasts(), battleFieldMeActivityRandomGame.getBattleField(i, j).getShipNumber())) {
                                        newShoot = true;
                                    } else {
                                        x = x - 1;
                                    }
                                } else {
                                    displayWaterCellHit(TextViewArrayActivityRandomGameMe, x, j);
                                    ShootTable.remove(Integer.valueOf(x * 10 + j));
                                    battleFieldMeActivityRandomGame.battleField[x][j].setHit(true);
                                    myTurn=true;
                                    direction = 2;
                                    x = i + 1;
                                }
                            }

                        }
                        else if(j==9){
                            if((battleFieldMeActivityRandomGame.battleField[x-1][j-1].isHit()&&
                                    battleFieldMeActivityRandomGame.battleField[x-1][j-1].isShip())||
                                    (battleFieldMeActivityRandomGame.battleField[x-1][j].isHit()&&
                                    battleFieldMeActivityRandomGame.battleField[x-1][j].isShip())){
                                direction = 2;
                                x = i + 1;
                            }else{
                                if (battleFieldMeActivityRandomGame.battleField[x][j].isShip()) {
                                    displayShipCellHit(TextViewArrayActivityRandomGameMe, x, j);
                                    ShootTable.remove(Integer.valueOf(x * 10 + j));
                                    battleFieldMeActivityRandomGame.battleField[x][j].setHit(true);
                                    if (zatopiony2(battleFieldMeActivityRandomGame.getBattleField(i, j).getNumberOfMasts(), battleFieldMeActivityRandomGame.getBattleField(i, j).getShipNumber())) {
                                        newShoot = true;
                                    } else {
                                        x = x - 1;
                                    }
                                } else {
                                    displayWaterCellHit(TextViewArrayActivityRandomGameMe, x, j);
                                    ShootTable.remove(Integer.valueOf(x * 10 + j));
                                    battleFieldMeActivityRandomGame.battleField[x][j].setHit(true);
                                    myTurn=true;
                                    direction = 2;
                                    x = i + 1;
                                }
                            }
                        }
                        else{
                            if((battleFieldMeActivityRandomGame.battleField[x-1][j-1].isHit()&&
                                    battleFieldMeActivityRandomGame.battleField[x-1][j-1].isShip())||
                                    (battleFieldMeActivityRandomGame.battleField[x-1][j].isHit()&&
                                            battleFieldMeActivityRandomGame.battleField[x-1][j].isShip())||
                                    (battleFieldMeActivityRandomGame.battleField[x-1][j+1].isHit()&&
                                            battleFieldMeActivityRandomGame.battleField[x-1][j+1].isShip())){
                                direction = 2;
                                x = i + 1;
                            }else{
                                if (battleFieldMeActivityRandomGame.battleField[x][j].isShip()) {
                                    displayShipCellHit(TextViewArrayActivityRandomGameMe, x, j);
                                    ShootTable.remove(Integer.valueOf(x * 10 + j));
                                    battleFieldMeActivityRandomGame.battleField[x][j].setHit(true);
                                    if (zatopiony2(battleFieldMeActivityRandomGame.getBattleField(i, j).getNumberOfMasts(), battleFieldMeActivityRandomGame.getBattleField(i, j).getShipNumber())) {
                                        newShoot = true;
                                    } else {
                                        x = x - 1;
                                    }
                                } else {
                                    displayWaterCellHit(TextViewArrayActivityRandomGameMe, x, j);
                                    ShootTable.remove(Integer.valueOf(x * 10 + j));
                                    battleFieldMeActivityRandomGame.battleField[x][j].setHit(true);
                                    myTurn=true;
                                    direction = 2;
                                    x = i + 1;
                                }
                            }

                        }
                    }
                }
                else{
                    direction=2;
                    x=i+1;
                }
            }
            else{
                direction=2;
                x=i+1;
            }
        }

        else if(direction==2){
            if(x<=9){
                if(!battleFieldMeActivityRandomGame.battleField[x][j].isHit()){
                    if(x==9){
                        if(battleFieldMeActivityRandomGame.battleField[x][j].isShip()){
                            displayShipCellHit(TextViewArrayActivityRandomGameMe,x,j);
                            ShootTable.remove(Integer.valueOf(x*10+j));
                            battleFieldMeActivityRandomGame.battleField[x][j].setHit(true);
                            if(zatopiony2(battleFieldMeActivityRandomGame.getBattleField(i,j).getNumberOfMasts(), battleFieldMeActivityRandomGame.getBattleField(i,j).getShipNumber())){
                                newShoot=true;
                            }
                            else{
                                x=x+1;
                            }
                        }else{
                            displayWaterCellHit(TextViewArrayActivityRandomGameMe,x,j);
                            ShootTable.remove(Integer.valueOf(x*10+j));
                            battleFieldMeActivityRandomGame.battleField[x][j].setHit(true);
                            myTurn=true;
                            direction=3;
                            y=j-1;
                        }
                    }else {
                        if(j==0){
                            if((battleFieldMeActivityRandomGame.battleField[x+1][j].isHit()&&
                                            battleFieldMeActivityRandomGame.battleField[x+1][j].isShip())||
                                    (battleFieldMeActivityRandomGame.battleField[x+1][j+1].isHit()&&
                                            battleFieldMeActivityRandomGame.battleField[x+1][j+1].isShip())){
                                direction=3;
                                y=j-1;
                            }else{
                                if(battleFieldMeActivityRandomGame.battleField[x][j].isShip()){
                                    displayShipCellHit(TextViewArrayActivityRandomGameMe,x,j);
                                    ShootTable.remove(Integer.valueOf(x*10+j));
                                    battleFieldMeActivityRandomGame.battleField[x][j].setHit(true);
                                    if(zatopiony2(battleFieldMeActivityRandomGame.getBattleField(i,j).getNumberOfMasts(), battleFieldMeActivityRandomGame.getBattleField(i,j).getShipNumber())){
                                        newShoot=true;
                                    }
                                    else{
                                        x=x+1;
                                    }
                                }else{
                                    displayWaterCellHit(TextViewArrayActivityRandomGameMe,x,j);
                                    ShootTable.remove(Integer.valueOf(x*10+j));
                                    battleFieldMeActivityRandomGame.battleField[x][j].setHit(true);
                                    myTurn=true;
                                    direction=3;
                                    y=j-1;
                            }
                            }
                        }else if(j==9) {
                            if ((battleFieldMeActivityRandomGame.battleField[x + 1][j].isHit() &&
                                    battleFieldMeActivityRandomGame.battleField[x + 1][j].isShip()) ||
                                    (battleFieldMeActivityRandomGame.battleField[x + 1][j - 1].isHit() &&
                                            battleFieldMeActivityRandomGame.battleField[x + 1][j - 1].isShip())) {
                                direction = 3;
                                y = j - 1;
                            } else {
                                if (battleFieldMeActivityRandomGame.battleField[x][j].isShip()) {
                                    displayShipCellHit(TextViewArrayActivityRandomGameMe, x, j);
                                    ShootTable.remove(Integer.valueOf(x * 10 + j));
                                    battleFieldMeActivityRandomGame.battleField[x][j].setHit(true);
                                    if (zatopiony2(battleFieldMeActivityRandomGame.getBattleField(i, j).getNumberOfMasts(), battleFieldMeActivityRandomGame.getBattleField(i, j).getShipNumber())) {
                                        newShoot = true;
                                    } else {
                                        x = x + 1;
                                    }
                                } else {
                                    displayWaterCellHit(TextViewArrayActivityRandomGameMe, x, j);
                                    ShootTable.remove(Integer.valueOf(x * 10 + j));
                                    battleFieldMeActivityRandomGame.battleField[x][j].setHit(true);
                                    myTurn=true;
                                    direction = 3;
                                    y = j - 1;
                                }
                            }
                        }else{
                            if((battleFieldMeActivityRandomGame.battleField[x+1][j-1].isHit()&&
                                    battleFieldMeActivityRandomGame.battleField[x+1][j-1].isShip())||
                                    (battleFieldMeActivityRandomGame.battleField[x+1][j].isHit()&&
                                    battleFieldMeActivityRandomGame.battleField[x+1][j].isShip())||
                                    (battleFieldMeActivityRandomGame.battleField[x+1][j+1].isHit()&&
                                            battleFieldMeActivityRandomGame.battleField[x+1][j+1].isShip())){
                                direction=3;
                                y=j-1;
                            }else{
                                if(battleFieldMeActivityRandomGame.battleField[x][j].isShip()){
                                    displayShipCellHit(TextViewArrayActivityRandomGameMe,x,j);
                                    ShootTable.remove(Integer.valueOf(x*10+j));
                                    battleFieldMeActivityRandomGame.battleField[x][j].setHit(true);
                                    if(zatopiony2(battleFieldMeActivityRandomGame.getBattleField(i,j).getNumberOfMasts(), battleFieldMeActivityRandomGame.getBattleField(i,j).getShipNumber())){
                                        newShoot=true;
                                    }
                                    else{
                                        x=x+1;
                                    }
                                }else{
                                    displayWaterCellHit(TextViewArrayActivityRandomGameMe,x,j);
                                    ShootTable.remove(Integer.valueOf(x*10+j));
                                    battleFieldMeActivityRandomGame.battleField[x][j].setHit(true);
                                    myTurn=true;
                                    direction=3;
                                    y=j-1;
                                }
                            }
                        }
                    }
                }else{
                    direction=3;
                    y=j-1;
                }
            }
            else{
                direction=3;
                y=j-1;
            }
        }
        else if(direction==3){
            if(y>=0){
                if(!battleFieldMeActivityRandomGame.battleField[i][y].isHit()){
                    if (y == 0) {
                        if (battleFieldMeActivityRandomGame.battleField[i][y].isShip()) {
                            displayShipCellHit(TextViewArrayActivityRandomGameMe, i, y);
                            ShootTable.remove(Integer.valueOf(i * 10 + y));
                            battleFieldMeActivityRandomGame.battleField[i][y].setHit(true);
                            if (zatopiony2(battleFieldMeActivityRandomGame.getBattleField(i, j).getNumberOfMasts(), battleFieldMeActivityRandomGame.getBattleField(i, j).getShipNumber())) {
                                newShoot = true;
                            } else {
                                y = y - 1;
                            }
                        } else {
                            displayWaterCellHit(TextViewArrayActivityRandomGameMe, i, y);
                            ShootTable.remove(Integer.valueOf(i * 10 + y));
                            battleFieldMeActivityRandomGame.battleField[i][y].setHit(true);
                            myTurn=true;
                            direction = 4;
                            y = j + 1;
                        }
                    }
                    else{
                        if(i==0){
                            if((battleFieldMeActivityRandomGame.battleField[i][y-1].isShip()&&
                                    battleFieldMeActivityRandomGame.battleField[i][y-1].isHit())||
                                    (battleFieldMeActivityRandomGame.battleField[i+1][y-1].isShip()&&
                                            battleFieldMeActivityRandomGame.battleField[i+1][y-1].isHit())){
                                direction = 4;
                                y = j + 1;
                            }else{
                                if (battleFieldMeActivityRandomGame.battleField[i][y].isShip()) {
                                    displayShipCellHit(TextViewArrayActivityRandomGameMe, i, y);
                                    ShootTable.remove(Integer.valueOf(i * 10 + y));
                                    battleFieldMeActivityRandomGame.battleField[i][y].setHit(true);
                                    if (zatopiony2(battleFieldMeActivityRandomGame.getBattleField(i, j).getNumberOfMasts(), battleFieldMeActivityRandomGame.getBattleField(i, j).getShipNumber())) {
                                        newShoot = true;
                                    } else {
                                        y = y - 1;
                                    }
                                } else {
                                    displayWaterCellHit(TextViewArrayActivityRandomGameMe, i, y);
                                    ShootTable.remove(Integer.valueOf(i * 10 + y));
                                    battleFieldMeActivityRandomGame.battleField[i][y].setHit(true);
                                    myTurn=true;
                                    direction = 4;
                                    y = j + 1;
                                }
                            }
                        }
                        else if(i==9){
                            if((battleFieldMeActivityRandomGame.battleField[i][y-1].isShip()&&
                                    battleFieldMeActivityRandomGame.battleField[i][y-1].isHit())||
                                    (battleFieldMeActivityRandomGame.battleField[i-1][y-1].isShip()&&
                                            battleFieldMeActivityRandomGame.battleField[i-1][y-1].isHit())){
                                direction = 4;
                                y = j + 1;
                            }else{
                                if (battleFieldMeActivityRandomGame.battleField[i][y].isShip()) {
                                    displayShipCellHit(TextViewArrayActivityRandomGameMe, i, y);
                                    ShootTable.remove(Integer.valueOf(i * 10 + y));
                                    battleFieldMeActivityRandomGame.battleField[i][y].setHit(true);
                                    if (zatopiony2(battleFieldMeActivityRandomGame.getBattleField(i, j).getNumberOfMasts(), battleFieldMeActivityRandomGame.getBattleField(i, j).getShipNumber())) {
                                        newShoot = true;
                                    } else {
                                        y = y - 1;
                                    }
                                } else {
                                    displayWaterCellHit(TextViewArrayActivityRandomGameMe, i, y);
                                    ShootTable.remove(Integer.valueOf(i * 10 + y));
                                    battleFieldMeActivityRandomGame.battleField[i][y].setHit(true);
                                    myTurn=true;
                                    direction = 4;
                                    y = j + 1;
                                }
                            }
                        }
                        else{
                            if((battleFieldMeActivityRandomGame.battleField[i-1][y-1].isShip()&&
                                    battleFieldMeActivityRandomGame.battleField[i-1][y-1].isHit())||
                                    (battleFieldMeActivityRandomGame.battleField[i][y-1].isShip()&&
                                    battleFieldMeActivityRandomGame.battleField[i][y-1].isHit())||
                                    (battleFieldMeActivityRandomGame.battleField[i+1][y-1].isShip()&&
                                            battleFieldMeActivityRandomGame.battleField[i+1][y-1].isHit())){
                                direction = 4;
                                y = j + 1;
                            }else{
                                if (battleFieldMeActivityRandomGame.battleField[i][y].isShip()) {
                                    displayShipCellHit(TextViewArrayActivityRandomGameMe, i, y);
                                    ShootTable.remove(Integer.valueOf(i * 10 + y));
                                    battleFieldMeActivityRandomGame.battleField[i][y].setHit(true);
                                    if (zatopiony2(battleFieldMeActivityRandomGame.getBattleField(i, j).getNumberOfMasts(), battleFieldMeActivityRandomGame.getBattleField(i, j).getShipNumber())) {
                                        newShoot = true;
                                    } else {
                                        y = y - 1;
                                    }
                                } else {
                                    displayWaterCellHit(TextViewArrayActivityRandomGameMe, i, y);
                                    ShootTable.remove(Integer.valueOf(i * 10 + y));
                                    battleFieldMeActivityRandomGame.battleField[i][y].setHit(true);
                                    myTurn=true;
                                    direction = 4;
                                    y = j + 1;
                                }
                            }
                        }

                    }
                }else{
                        direction=4;
                        y=j+1;
                    }
            } else{
                direction=4;
                y=j+1;
            }
        }
        else if(direction==4){
            if(y<=9){
                if(!battleFieldMeActivityRandomGame.battleField[i][y].isHit()){
                    if(y==9){
                        if(battleFieldMeActivityRandomGame.battleField[i][y].isShip()){
                            displayShipCellHit(TextViewArrayActivityRandomGameMe,i,y);
                            ShootTable.remove(Integer.valueOf(i*10+y));
                            battleFieldMeActivityRandomGame.battleField[i][y].setHit(true);
                            if(zatopiony2(battleFieldMeActivityRandomGame.getBattleField(i,j).getNumberOfMasts(), battleFieldMeActivityRandomGame.getBattleField(i,j).getShipNumber())){
                                newShoot=true;
                            }
                            else{
                                y=y+1;
                            }
                        }else{
                            displayWaterCellHit(TextViewArrayActivityRandomGameMe,i,y);
                            ShootTable.remove(Integer.valueOf(i*10+y));
                            battleFieldMeActivityRandomGame.battleField[i][y].setHit(true);
                            myTurn=true;
                            direction=5;
                        }
                    }else{
                         if(i==0){
                             if((battleFieldMeActivityRandomGame.battleField[i][y+1].isShip()&&
                                     battleFieldMeActivityRandomGame.battleField[i][y+1].isHit())||
                                     (battleFieldMeActivityRandomGame.battleField[i+1][y+1].isShip()&&
                                             battleFieldMeActivityRandomGame.battleField[i+1][y+1].isHit())) {
                                 direction=5;
                             }else{
                                 if(battleFieldMeActivityRandomGame.battleField[i][y].isShip()){
                                     displayShipCellHit(TextViewArrayActivityRandomGameMe,i,y);
                                     ShootTable.remove(Integer.valueOf(i*10+y));
                                     battleFieldMeActivityRandomGame.battleField[i][y].setHit(true);
                                     if(zatopiony2(battleFieldMeActivityRandomGame.getBattleField(i,j).getNumberOfMasts(), battleFieldMeActivityRandomGame.getBattleField(i,j).getShipNumber())){
                                         newShoot=true;
                                     }
                                     else{
                                         y=y+1;
                                     }
                                 }else{
                                     displayWaterCellHit(TextViewArrayActivityRandomGameMe,i,y);
                                     ShootTable.remove(Integer.valueOf(i*10+y));
                                     battleFieldMeActivityRandomGame.battleField[i][y].setHit(true);
                                     myTurn=true;
                                     direction=5;
                                 }

                             }

                             }
                         else if (i==9){
                             if((battleFieldMeActivityRandomGame.battleField[i][y+1].isShip()&&
                                     battleFieldMeActivityRandomGame.battleField[i][y+1].isHit())||
                                     (battleFieldMeActivityRandomGame.battleField[i-1][y+1].isShip()&&
                                             battleFieldMeActivityRandomGame.battleField[i-1][y+1].isHit())) {
                                 direction=5;
                             }else{
                                 if(battleFieldMeActivityRandomGame.battleField[i][y].isShip()){
                                     displayShipCellHit(TextViewArrayActivityRandomGameMe,i,y);
                                     ShootTable.remove(Integer.valueOf(i*10+y));
                                     battleFieldMeActivityRandomGame.battleField[i][y].setHit(true);
                                     if(zatopiony2(battleFieldMeActivityRandomGame.getBattleField(i,j).getNumberOfMasts(), battleFieldMeActivityRandomGame.getBattleField(i,j).getShipNumber())){
                                         newShoot=true;
                                     }
                                     else{
                                         y=y+1;
                                     }
                                 }else{
                                     displayWaterCellHit(TextViewArrayActivityRandomGameMe,i,y);
                                     ShootTable.remove(Integer.valueOf(i*10+y));
                                     battleFieldMeActivityRandomGame.battleField[i][y].setHit(true);
                                     myTurn=true;
                                     direction=5;
                                 }

                             }

                         }
                         else{
                             if((battleFieldMeActivityRandomGame.battleField[i+1][y+1].isShip()&&
                                     battleFieldMeActivityRandomGame.battleField[i+1][y+1].isHit())||
                                     (battleFieldMeActivityRandomGame.battleField[i][y+1].isShip()&&
                                     battleFieldMeActivityRandomGame.battleField[i][y+1].isHit())||
                                     (battleFieldMeActivityRandomGame.battleField[i-1][y+1].isShip()&&
                                             battleFieldMeActivityRandomGame.battleField[i-1][y+1].isHit())) {
                                 direction=5;
                             }else{
                                 if(battleFieldMeActivityRandomGame.battleField[i][y].isShip()){
                                     displayShipCellHit(TextViewArrayActivityRandomGameMe,i,y);
                                     ShootTable.remove(Integer.valueOf(i*10+y));
                                     battleFieldMeActivityRandomGame.battleField[i][y].setHit(true);
                                     if(zatopiony2(battleFieldMeActivityRandomGame.getBattleField(i,j).getNumberOfMasts(), battleFieldMeActivityRandomGame.getBattleField(i,j).getShipNumber())){
                                         newShoot=true;
                                     }
                                     else{
                                         y=y+1;
                                     }
                                 }else{
                                     displayWaterCellHit(TextViewArrayActivityRandomGameMe,i,y);
                                     ShootTable.remove(Integer.valueOf(i*10+y));
                                     battleFieldMeActivityRandomGame.battleField[i][y].setHit(true);
                                     myTurn=true;
                                     direction=5;
                                 }

                             }


                         }
                    }
                }else{
                    direction=5;
                }
            }
            else{
                direction=5;
            }
        }
else
    newShoot=true;
    }

    private void displayBattleFieldActivityRandomGamePlayerOne(TextView[][] TextViewArrayActivityRandomGame, BattleField battleFieldPlayerOneActivityRandomGame) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (battleFieldPlayerOneActivityRandomGame.getBattleField(i, j).isShip()) {

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

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(enableTouchListener) {
            final int X = (int) event.getRawX();
            final int Y = (int) event.getRawY();
            int x = (X - location1[0]) / width;
            int y = (Y - location1[1]) / height;
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:

                    if(battleFieldOpponent[y][x]==BATTLE_CELL){
                        for (int i = 0; i < 10; i++) {
                            for (int j = 0; j < 10; j++) {
                                if (x == j || y == i) {
                                    if(battleFieldOpponent[i][j]==BATTLE_CELL){
                                        tv = (TextView) layout.getChildAt(10*i+j);
                                        tv.setBackground(getDrawable(R.drawable.battle_cell_green_field));
                                    }else if(battleFieldOpponent[i][j]==WATER){
                                        tv = (TextView) layout.getChildAt(10*i+j);
                                        tv.setBackground(getDrawable(R.drawable.water_cell_green_field));
                                    }else{
                                        tv = (TextView) layout.getChildAt(10*i+j);
                                        tv.setBackground(getDrawable(R.drawable.ship_cell_green_field));
                                    }
                                }
                            }
                        }
                    }else {
                        for (int i = 0; i < 10; i++) {
                            for (int j = 0; j < 10; j++) {
                                if (x == j || y == i) {
                                    if(battleFieldOpponent[i][j]==BATTLE_CELL){
                                        tv = (TextView) layout.getChildAt(10*i+j);
                                        tv.setBackground(getDrawable(R.drawable.battle_cell_red_field));
                                    }else if(battleFieldOpponent[i][j]==WATER){
                                        tv = (TextView) layout.getChildAt(10*i+j);
                                        tv.setBackground(getDrawable(R.drawable.water_cell_red_field));
                                    }else{
                                        tv = (TextView) layout.getChildAt(10*i+j);
                                        tv.setBackground(getDrawable(R.drawable.ship_cell_red_field));
                                    }


                                }
                            }
                        }
                    }
                    break;

                case MotionEvent.ACTION_MOVE:

                    if(x>=0&&x<=9&&y>=0&&y<=9){
                        pokazStatki();
                        if(battleFieldOpponent[y][x]==BATTLE_CELL){
                            for (int i = 0; i < 10; i++) {
                                for (int j = 0; j < 10; j++) {
                                    if (x == j || y == i) {
                                        if(battleFieldOpponent[i][j]==BATTLE_CELL){
                                            tv = (TextView) layout.getChildAt(10*i+j);
                                            tv.setBackground(getDrawable(R.drawable.battle_cell_green_field));
                                        }else if(battleFieldOpponent[i][j]==WATER){
                                            tv = (TextView) layout.getChildAt(10*i+j);
                                            tv.setBackground(getDrawable(R.drawable.water_cell_green_field));
                                        }else{
                                            tv = (TextView) layout.getChildAt(10*i+j);
                                            tv.setBackground(getDrawable(R.drawable.ship_cell_green_field));
                                        }
                                    }
                                }
                            }
                        }else {
                            for (int i = 0; i < 10; i++) {
                                for (int j = 0; j < 10; j++) {
                                    if (x == j || y == i) {
                                        if(battleFieldOpponent[i][j]==BATTLE_CELL){
                                            tv = (TextView) layout.getChildAt(10*i+j);
                                            tv.setBackground(getDrawable(R.drawable.battle_cell_red_field));
                                        }else if(battleFieldOpponent[i][j]==WATER){
                                            tv = (TextView) layout.getChildAt(10*i+j);
                                            tv.setBackground(getDrawable(R.drawable.water_cell_red_field));
                                        }else{
                                            tv = (TextView) layout.getChildAt(10*i+j);
                                            tv.setBackground(getDrawable(R.drawable.ship_cell_red_field));
                                        }
                                        
                                    }
                                }
                            }
                        }
                    }else{
                        pokazStatki();
                    }
                    break;
                case MotionEvent.ACTION_UP:

                    if(x>=0&&x<=9&&y>=0&&y<=9){
                        if(battleFieldOpponent[y][x]==BATTLE_CELL) {
                            hitCell(y, x);

                        }else
                        pokazStatki();
                    }else;

                    break;
            }
        }else;
        layout.invalidate();
        return true;
    }

    private void hitCell(int x, int y) {
        battleFieldOpponentActivityRandomGame.getBattleField(x,y).setHit(true);
        if(battleFieldOpponentActivityRandomGame.getBattleField(x,y).isShip()){
            battleFieldOpponent[x][y]=SHIP_RED;
            updateCounters(battleFieldOpponentActivityRandomGame.getBattleField(x,y).getNumberOfMasts(),
                    battleFieldOpponentActivityRandomGame.getBattleField(x,y).getShipNumber());
            showCounters();
            if(myWin()){
                mHandler.postDelayed(game,deelay);
            }
            pokazStatki();
        }else{
            battleFieldOpponent[x][y]=WATER;
            pokazStatki();
            myTurn=false;
            enableTouchListener=false;
            mHandler.postDelayed(game,deelay);
        }

    }

    private void showCounters() {
        if (level==0){
            if(!(shipFourMastsCounter==0)){
                for(int i=0;i<shipFourMastsCounter;i++){
                    ShipFourMasts[i].setBackground(getDrawable(R.drawable.ship_cell));
                }
            }
            if(!(shipThreeMastsCounterFirst==0)){
                for(int i=0;i<shipThreeMastsCounterFirst;i++){
                    ShipThreeMastsFirst[i].setBackground(getDrawable(R.drawable.ship_cell));
                }
            }
            if(!(shipThreeMastsCounterSecond==0)){
                for(int i=0;i<shipThreeMastsCounterSecond;i++){
                    ShipThreeMastsSecond[i].setBackground(getDrawable(R.drawable.ship_cell));
                }
            }
            if(!(shipTwoMastsCounterFirst==0)){
                for(int i=0;i<shipTwoMastsCounterFirst;i++){
                    ShipTwoMastsFirst[i].setBackground(getDrawable(R.drawable.ship_cell));
                }
            }
            if(!(shipTwoMastsCounterSecond==0)){
                for(int i=0;i<shipTwoMastsCounterSecond;i++){
                    ShipTwoMastsSecond[i].setBackground(getDrawable(R.drawable.ship_cell));
                }
            }
            if(!(shipTwoMastsCounterThird==0)){
                for(int i=0;i<shipTwoMastsCounterThird;i++){
                    ShipTwoMastsThird[i].setBackground(getDrawable(R.drawable.ship_cell));
                }
            }
            if(!(shipOneMastsCounterFirst==0)){
                    ShipOneMastsFirst[0].setBackground(getDrawable(R.drawable.ship_cell));
            }
            if(!(shipOneMastsCounterSecond==0)){
                ShipOneMastsSecond[0].setBackground(getDrawable(R.drawable.ship_cell));
            }
            if(!(shipOneMastsCounterThird==0)){
                ShipOneMastsThird[0].setBackground(getDrawable(R.drawable.ship_cell));
            }
            if(!(shipOneMastsCounterFourth==0)){
                ShipOneMastsFourth[0].setBackground(getDrawable(R.drawable.ship_cell));
            }
        }
        else{
            if(shipFourMastsCounter==4){
                for(int i=0;i<shipFourMastsCounter;i++){
                    ShipFourMasts[i].setBackground(getDrawable(R.drawable.ship_cell));
                }
            }
            if(shipThreeMastsCounterFirst==3){
                for(int i=0;i<shipThreeMastsCounterFirst;i++){
                    ShipThreeMastsFirst[i].setBackground(getDrawable(R.drawable.ship_cell));
                }
            }
            if(shipThreeMastsCounterSecond==3){
                for(int i=0;i<shipThreeMastsCounterSecond;i++){
                    ShipThreeMastsSecond[i].setBackground(getDrawable(R.drawable.ship_cell));
                }
            }
            if(shipTwoMastsCounterFirst==2){
                for(int i=0;i<shipTwoMastsCounterFirst;i++){
                    ShipTwoMastsFirst[i].setBackground(getDrawable(R.drawable.ship_cell));
                }
            }
            if(shipTwoMastsCounterSecond==2){
                for(int i=0;i<shipTwoMastsCounterSecond;i++){
                    ShipTwoMastsSecond[i].setBackground(getDrawable(R.drawable.ship_cell));
                }
            }
            if(shipTwoMastsCounterThird==2){
                for(int i=0;i<shipTwoMastsCounterThird;i++){
                    ShipTwoMastsThird[i].setBackground(getDrawable(R.drawable.ship_cell));
                }
            }
            if(shipOneMastsCounterFirst==1){
                ShipOneMastsFirst[0].setBackground(getDrawable(R.drawable.ship_cell));
            }
            if(shipOneMastsCounterSecond==1){
                ShipOneMastsSecond[0].setBackground(getDrawable(R.drawable.ship_cell));
            }
            if(shipOneMastsCounterThird==1){
                ShipOneMastsThird[0].setBackground(getDrawable(R.drawable.ship_cell));
            }
            if(shipOneMastsCounterFourth==1){
                ShipOneMastsFourth[0].setBackground(getDrawable(R.drawable.ship_cell));
            }
        }

    }

    private void updateCounters(int numberOfMasts, int shipNumber) {
        int number = 10*numberOfMasts+shipNumber;

        switch (number){
            case 41:
                shipFourMastsCounter++;
                if(shipFourMastsCounter==4){
                    updateBattleField(numberOfMasts,shipNumber);
                }
                break;
            case 31:
                shipThreeMastsCounterFirst++;
                if(shipThreeMastsCounterFirst==3){
                    updateBattleField(numberOfMasts,shipNumber);
                }
                break;
            case 32:
                shipThreeMastsCounterSecond++;
                if(shipThreeMastsCounterSecond==3){
                    updateBattleField(numberOfMasts,shipNumber);
                }
                break;
            case 21:
                shipTwoMastsCounterFirst++;
                if(shipTwoMastsCounterFirst==2){
                    updateBattleField(numberOfMasts,shipNumber);
                }
                break;
            case 22:
                shipTwoMastsCounterSecond++;
                if(shipTwoMastsCounterSecond==2){
                    updateBattleField(numberOfMasts,shipNumber);
                }
                break;
            case 23:
                shipTwoMastsCounterThird++;
                if(shipTwoMastsCounterThird==2){
                    updateBattleField(numberOfMasts,shipNumber);
                }
                break;
            case 11:
                shipOneMastsCounterFirst++;
                if(shipOneMastsCounterFirst==1){
                    updateBattleField(numberOfMasts,shipNumber);
                }
                break;
            case 12:
                shipOneMastsCounterSecond++;
                if(shipOneMastsCounterSecond==1){
                    updateBattleField(numberOfMasts,shipNumber);
                }
                break;
            case 13:
                shipOneMastsCounterThird++;
                if(shipOneMastsCounterThird==1){
                    updateBattleField(numberOfMasts,shipNumber);
                }
                break;
            case 14:
                shipOneMastsCounterFourth++;
                if(shipOneMastsCounterFourth==1){
                    updateBattleField(numberOfMasts,shipNumber);
                }
                break;
            default:
        }




    }

    private void updateBattleField(int numberOfMasts, int shipNumber) {
        for(int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                if(battleFieldOpponentActivityRandomGame.getBattleField(i,j).getShipNumber()==shipNumber&&
                battleFieldOpponentActivityRandomGame.getBattleField(i,j).getNumberOfMasts()==numberOfMasts){
                    battleFieldOpponent[i][j]=SHIP_BROWN;
                }
            }
        }
    }

    private boolean myWin(){
        return counterSum()==20;
    }

    private int counterSum() {
        return shipFourMastsCounter+
                shipThreeMastsCounterFirst+
                shipThreeMastsCounterSecond+
                shipTwoMastsCounterFirst+
                shipTwoMastsCounterSecond+
                shipTwoMastsCounterThird+
                shipOneMastsCounterFirst+
                shipOneMastsCounterSecond+
                shipOneMastsCounterThird+
                shipOneMastsCounterFourth;
    }

}
