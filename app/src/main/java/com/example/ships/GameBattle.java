package com.example.ships;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Shader;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.example.ships.classes.BattleField;
import com.example.ships.classes.GameDifficulty;
import com.example.ships.classes.TileDrawable;
import com.example.ships.classes.User;
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
    private ConstraintLayout mainLayout;

     private GridLayout layoutOpponent, layoutMy;

     private int height, width;

    private int battleFieldOpponent[][] = new int[10][10];
    private final static int BATTLE_CELL = 0;
    private final static int WATER = 1;
    private final static int SHIP_RED = 2;
    private final static int SHIP_BROWN = 3;

    private int shipFourMastsCounter = 0;
    private int shipThreeMastsCounterFirst = 0;
    private int shipThreeMastsCounterSecond = 0;
    private int shipTwoMastsCounterFirst = 0;
    private int shipTwoMastsCounterSecond = 0;
    private int shipTwoMastsCounterThird = 0;
    private int shipOneMastsCounterFirst = 0;
    private int shipOneMastsCounterSecond = 0;
    private int shipOneMastsCounterThird = 0;
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
    private LinearLayout linearLayoutLettersMy, linearLayoutNumbersMy, linearLayoutLettersOpponent, linearLayoutNumbersOpponent;
    int[]locationLayout = new int [2];
    private LinearLayout fourMasts,threeMastsFirst, threeMastsSecond, twoMastsFirst,twoMastsSecond,twoMastsThird,oneMastsFirst,oneMastsSecond,oneMastsThird,oneMastsFourth;



    ArrayList<Integer>ShootTable = new ArrayList<>();

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String userID;
    private TextView tv;
    private ImageButton surrender, leave;
    private int marginTop;
    private int marginLeft;
    private int marginLeftForShips;
    private int marginDown;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        getWindow().getDecorView().setSystemUiVisibility(flags);
        final View decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener(){

            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if((visibility&View.SYSTEM_UI_FLAG_FULLSCREEN)==0){
                    decorView.setSystemUiVisibility(flags);
                }
            }
        });

        setContentView(R.layout.activity_random_game_battle);
        mainLayout = findViewById(R.id.randomGameActivityLayout);
        surrender = findViewById(R.id.surrenderSinglePlayer);
        surrender.setBackgroundResource(R.drawable.leave);
        leave=findViewById(R.id.leaveSinglePlayer);
        leave.setBackgroundResource(R.drawable.back);
        layoutMy=findViewById(R.id.gridLayoutBattleMy);
        linearLayoutLettersMy=findViewById(R.id.LinearLayoutGameBattleActivityLettersMy);
        linearLayoutNumbersMy=findViewById(R.id.LinearLayoutGameBattleActivityNumbersMy);
        layoutOpponent = findViewById(R.id.gridLayoutBattleOpponent);
        linearLayoutLettersOpponent=findViewById(R.id.linearLayoutSingleBattleLetterOpponent);
        linearLayoutNumbersOpponent=findViewById(R.id.linearLayoutSingleBattleNumbersOpponent);
        fourMasts=findViewById(R.id.linearLayoutSingleBattleShipFourMasts);
        threeMastsFirst=findViewById(R.id.linearLayoutSingleBattleShipThreeMastsFirst);
        threeMastsSecond=findViewById(R.id.linearLayoutSingleBattleShipThreeMastsSecond);
        twoMastsFirst=findViewById(R.id.linearLayoutSingleBattleShipTwoMastsFirst);
        twoMastsSecond=findViewById(R.id.linearLayoutSingleBattleShipTwoMastsSecond);
        twoMastsThird=findViewById(R.id.linearLayoutSingleBattleShipTwoMastsThird);
        oneMastsFirst=findViewById(R.id.linearLayoutSingleBattleShipOneMastsFirst);
        oneMastsSecond=findViewById(R.id.linearLayoutSingleBattleShipOneMastsSecond);
        oneMastsThird=findViewById(R.id.linearLayoutSingleBattleShipOneMastsThird);
        oneMastsFourth=findViewById(R.id.linearLayoutSingleBattleShipOneMastsFourth);
        SharedPreferences sp = getSharedPreferences("VALUES", Activity.MODE_PRIVATE);
        int square = sp.getInt("square",-1);
        int screenWidth = sp.getInt("width",-1);
        int screenHeight = sp.getInt("height",-1);
        int screenWidthOffSet = sp.getInt("widthOffSet",-1);
        int screenHeightOffSet = sp.getInt("heightOffSet",-1);
        float textSize = (square*9)/10;

        mainLayout.setBackground(new TileDrawable(getDrawable(R.drawable.background_x), Shader.TileMode.REPEAT,square));


        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(2*square,2*square);
        ConstraintLayout.LayoutParams params1 = new ConstraintLayout.LayoutParams(10*square,10*square);
        ConstraintLayout.LayoutParams params2 = new ConstraintLayout.LayoutParams(10*square,square);
        ConstraintLayout.LayoutParams params3 = new ConstraintLayout.LayoutParams(square,10*square);
        ConstraintLayout.LayoutParams params4 = new ConstraintLayout.LayoutParams(10*square,10*square);
        ConstraintLayout.LayoutParams params5 = new ConstraintLayout.LayoutParams(10*square,square);
        ConstraintLayout.LayoutParams params6 = new ConstraintLayout.LayoutParams(square,10*square);
        ConstraintLayout.LayoutParams params7 = new ConstraintLayout.LayoutParams(4*square,square);
        ConstraintLayout.LayoutParams params8 = new ConstraintLayout.LayoutParams(3*square,square);
        ConstraintLayout.LayoutParams params9 = new ConstraintLayout.LayoutParams(3*square,square);
        ConstraintLayout.LayoutParams params10 = new ConstraintLayout.LayoutParams(2*square,square);
        ConstraintLayout.LayoutParams params11 = new ConstraintLayout.LayoutParams(2*square,square);
        ConstraintLayout.LayoutParams params12 = new ConstraintLayout.LayoutParams(2*square,square);
        ConstraintLayout.LayoutParams params13 = new ConstraintLayout.LayoutParams(square,square);
        ConstraintLayout.LayoutParams params14 = new ConstraintLayout.LayoutParams(square,square);
        ConstraintLayout.LayoutParams params15 = new ConstraintLayout.LayoutParams(square,square);
        ConstraintLayout.LayoutParams params16 = new ConstraintLayout.LayoutParams(square,square);
        ConstraintLayout.LayoutParams params17 = new ConstraintLayout.LayoutParams(2*square,2*square);

        surrender.setLayoutParams(params);
        layoutMy.setLayoutParams(params1);
        linearLayoutLettersMy.setLayoutParams(params2);
        linearLayoutNumbersMy.setLayoutParams(params3);
        layoutOpponent.setLayoutParams(params4);
        linearLayoutLettersOpponent.setLayoutParams(params5);
        linearLayoutNumbersOpponent.setLayoutParams(params6);
        fourMasts.setLayoutParams(params7);
        threeMastsFirst.setLayoutParams(params8);
        threeMastsSecond.setLayoutParams(params9);
        twoMastsFirst.setLayoutParams(params10);
        twoMastsSecond.setLayoutParams(params11);
        twoMastsThird.setLayoutParams(params12);
        oneMastsFirst.setLayoutParams(params13);
        oneMastsSecond.setLayoutParams(params14);
        oneMastsThird.setLayoutParams(params15);
        oneMastsFourth.setLayoutParams(params16);
        leave.setLayoutParams(params17);
        for(int i = 0; i<10; i++){
            TextView tv = (TextView)linearLayoutLettersMy.getChildAt(i);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
        }

        for(int i = 0; i<10; i++){
            TextView tv = (TextView)linearLayoutNumbersMy.getChildAt(i);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
        }

        for(int i = 0; i<10; i++){
            TextView tv = (TextView)linearLayoutLettersOpponent.getChildAt(i);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
        }
        for(int i = 0; i<10; i++){
            TextView tv = (TextView)linearLayoutNumbersOpponent.getChildAt(i);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
        }
        marginTop = 4*square;
        marginLeft = screenWidth-screenWidthOffSet-13*square;
        marginDown = screenHeight-screenHeightOffSet-2*square;

        if(((screenWidth-screenWidthOffSet)/square)%2==0){
            marginLeftForShips=(screenWidth-screenWidthOffSet)/2-15*square;
        }else{
            marginLeftForShips=(screenWidth-screenWidthOffSet-square)/2-15*square;
        }

        ConstraintSet set = new ConstraintSet();
        set.clone(mainLayout);
        set.connect(surrender.getId(),ConstraintSet.TOP,mainLayout.getId(),ConstraintSet.TOP,square);
        set.connect(surrender.getId(),ConstraintSet.LEFT,mainLayout.getId(),ConstraintSet.LEFT,square);

        set.connect(layoutMy.getId(),ConstraintSet.TOP,mainLayout.getId(),ConstraintSet.TOP,4*square);
        set.connect(layoutMy.getId(),ConstraintSet.LEFT,mainLayout.getId(),ConstraintSet.LEFT,5*square);

        set.connect(linearLayoutLettersMy.getId(),ConstraintSet.BOTTOM,layoutMy.getId(),ConstraintSet.TOP,0);
        set.connect(linearLayoutLettersMy.getId(),ConstraintSet.LEFT,layoutMy.getId(),ConstraintSet.LEFT,0);

        set.connect(linearLayoutNumbersMy.getId(),ConstraintSet.TOP,layoutMy.getId(),ConstraintSet.TOP,0);
        set.connect(linearLayoutNumbersMy.getId(),ConstraintSet.RIGHT,layoutMy.getId(),ConstraintSet.LEFT,0);

        set.connect(layoutOpponent.getId(),ConstraintSet.TOP,mainLayout.getId(),ConstraintSet.TOP,marginTop);
        set.connect(layoutOpponent.getId(),ConstraintSet.LEFT,mainLayout.getId(),ConstraintSet.LEFT,marginLeft);

        set.connect(linearLayoutLettersOpponent.getId(),ConstraintSet.BOTTOM,layoutOpponent.getId(),ConstraintSet.TOP,0);
        set.connect(linearLayoutLettersOpponent.getId(),ConstraintSet.LEFT,layoutOpponent.getId(),ConstraintSet.LEFT,0);

        set.connect(linearLayoutNumbersOpponent.getId(),ConstraintSet.TOP,layoutOpponent.getId(),ConstraintSet.TOP,0);
        set.connect(linearLayoutNumbersOpponent.getId(),ConstraintSet.RIGHT,layoutOpponent.getId(),ConstraintSet.LEFT,0);

        set.connect(fourMasts.getId(),ConstraintSet.TOP,mainLayout.getId(),ConstraintSet.TOP,marginDown);
        set.connect(fourMasts.getId(),ConstraintSet.LEFT,mainLayout.getId(),ConstraintSet.LEFT,marginLeftForShips);

        set.connect(threeMastsFirst.getId(),ConstraintSet.TOP,fourMasts.getId(),ConstraintSet.TOP,0);
        set.connect(threeMastsFirst.getId(),ConstraintSet.LEFT,fourMasts.getId(),ConstraintSet.RIGHT,square);

        set.connect(threeMastsSecond.getId(),ConstraintSet.TOP,threeMastsFirst.getId(),ConstraintSet.TOP,0);
        set.connect(threeMastsSecond.getId(),ConstraintSet.LEFT,threeMastsFirst.getId(),ConstraintSet.RIGHT,square);

        set.connect(twoMastsFirst.getId(),ConstraintSet.TOP,threeMastsSecond.getId(),ConstraintSet.TOP,0);
        set.connect(twoMastsFirst.getId(),ConstraintSet.LEFT,threeMastsSecond.getId(),ConstraintSet.RIGHT,square);

        set.connect(twoMastsSecond.getId(),ConstraintSet.TOP,twoMastsFirst.getId(),ConstraintSet.TOP,0);
        set.connect(twoMastsSecond.getId(),ConstraintSet.LEFT,twoMastsFirst.getId(),ConstraintSet.RIGHT,square);

        set.connect(twoMastsThird.getId(),ConstraintSet.TOP,twoMastsSecond.getId(),ConstraintSet.TOP,0);
        set.connect(twoMastsThird.getId(),ConstraintSet.LEFT,twoMastsSecond.getId(),ConstraintSet.RIGHT,square);

        set.connect(oneMastsFirst.getId(),ConstraintSet.TOP,twoMastsThird.getId(),ConstraintSet.TOP,0);
        set.connect(oneMastsFirst.getId(),ConstraintSet.LEFT,twoMastsThird.getId(),ConstraintSet.RIGHT,square);

        set.connect(oneMastsSecond.getId(),ConstraintSet.TOP,oneMastsFirst.getId(),ConstraintSet.TOP,0);
        set.connect(oneMastsSecond.getId(),ConstraintSet.LEFT,oneMastsFirst.getId(),ConstraintSet.RIGHT,square);

        set.connect(oneMastsThird.getId(),ConstraintSet.TOP,oneMastsSecond.getId(),ConstraintSet.TOP,0);
        set.connect(oneMastsThird.getId(),ConstraintSet.LEFT,oneMastsSecond.getId(),ConstraintSet.RIGHT,square);

        set.connect(oneMastsFourth.getId(),ConstraintSet.TOP,oneMastsThird.getId(),ConstraintSet.TOP,0);
        set.connect(oneMastsFourth.getId(),ConstraintSet.LEFT,oneMastsThird.getId(),ConstraintSet.RIGHT,square);

        set.connect(leave.getId(),ConstraintSet.TOP,mainLayout.getId(),ConstraintSet.TOP,marginDown-2*square);
        set.connect(leave.getId(),ConstraintSet.LEFT,mainLayout.getId(),ConstraintSet.LEFT,square);

        set.applyTo(mainLayout);

        enableTouchListener=false;
        initializeTable(ShootTable);






        height=square;
        width=square;

        layoutOpponent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                layoutOpponent.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                layoutOpponent.getLocationOnScreen(locationLayout);

            }
        });
        layoutOpponent.setOnTouchListener(this);

       if(GameDifficulty.getInstance().getRandom()) {
           battleFieldMeActivityRandomGame.createFleet();
       }else{
           battleFieldMeActivityRandomGame = BattleFieldPlayerOneSingleton.getInstance().readBattleField();
       }

        battleFieldOpponentActivityRandomGame.createFleet();


        displayBattleFieldActivityRandomGamePlayerOne(layoutMy, battleFieldMeActivityRandomGame);


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
                    layoutOpponent.getChildAt(i*10+j).setBackground(getDrawable(R.drawable.battle_cell_ship_sunk_x));
                }
                else if(battleFieldOpponent[i][j]==SHIP_RED){
                    layoutOpponent.getChildAt(i*10+j).setBackground(getDrawable(R.drawable.battle_cell_ship_normal_x));
                }else if(battleFieldOpponent[i][j]==WATER){
                    layoutOpponent.getChildAt(i*10+j).setBackground(getDrawable(R.drawable.water_cell_x));
                }else if(battleFieldOpponent[i][j]==BATTLE_CELL){
                    layoutOpponent.getChildAt(i*10+j).setBackground(getDrawable(R.drawable.battle_cell_x));
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
                    displayShipCell((TextView) layoutMy.getChildAt(10*i+j));
                }

                // woda i została trafiony
                else if(!battleFieldMeActivityRandomGame.getBattleField(i,j).isShip()
                        && battleFieldMeActivityRandomGame.getBattleField(i,j).isHit()){
                    displayWaterCell((TextView) layoutMy.getChildAt(10*i+j));
                }

                // jest statek i nie został trafiony
                else if(battleFieldMeActivityRandomGame.getBattleField(i,j).isShip()
                &&!battleFieldMeActivityRandomGame.getBattleField(i,j).isHit()){
                    displayWidmoShip((TextView) layoutMy.getChildAt(10*i+j));
                }
                // nie ma statku i nie został trafiony
                else if(!battleFieldMeActivityRandomGame.getBattleField(i,j).isShip()
                        &&!battleFieldMeActivityRandomGame.getBattleField(i,j).isHit()){
                    displayBattleCell((TextView) layoutMy.getChildAt(10*i+j));
                }
                else;
            }
        }
    }

    private void displayBattleCell(TextView textView) {
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            textView.setBackgroundDrawable(getResources().getDrawable(R.drawable.battle_cell_x));
        } else {
            textView.setBackground(getResources().getDrawable(R.drawable.battle_cell_x));
        }
    }

    private void displayWidmoShip(TextView textView){
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            textView.setBackgroundDrawable(getResources().getDrawable(R.drawable.battle_cell_widmo_ship_x));
        } else {
            textView.setBackground(getResources().getDrawable(R.drawable.battle_cell_widmo_ship_x));
        }
    }

    private void displayWaterCell(TextView textView) {
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            textView.setBackgroundDrawable(getResources().getDrawable(R.drawable.water_cell_x));
        } else {
            textView.setBackground(getResources().getDrawable(R.drawable.water_cell_x));
        }
    }

    private void displayShipCell(TextView textView) {
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            textView.setBackgroundDrawable(getResources().getDrawable(R.drawable.battle_cell_ship_sunk_x));
        } else {
            textView.setBackground(getResources().getDrawable(R.drawable.battle_cell_ship_sunk_x));
        }
    }

    private void displayShipCellHidden(TextView textView){
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            textView.setBackgroundDrawable(getResources().getDrawable(R.drawable.battle_cell_ship_sunk_x_hidden));
        } else {
            textView.setBackground(getResources().getDrawable(R.drawable.battle_cell_ship_sunk_x_hidden));
        }
    }

    private void displayWaterCellHidden(TextView textView){
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            textView.setBackgroundDrawable(getResources().getDrawable(R.drawable.water_cell_x_hidden));
        } else {
            textView.setBackground(getResources().getDrawable(R.drawable.water_cell_x_hidden));
        }

    }

    private void displayWidmoShipHidden(TextView textView){
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            textView.setBackgroundDrawable(getResources().getDrawable(R.drawable.battle_cell_widmo_ship_x_hidden));
        } else {
            textView.setBackground(getResources().getDrawable(R.drawable.battle_cell_widmo_ship_x_hidden));
        }
    }

    private void displayBattleCellHidden(TextView textView){
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            textView.setBackgroundDrawable(getResources().getDrawable(R.drawable.battle_cell_x_hidden));
        } else {
            textView.setBackground(getResources().getDrawable(R.drawable.battle_cell_x_hidden));
        }
    }

    private void hideBattleFiledAvailablePlayerOne() {
        for(int i =0;i<10;i++){
            for(int j = 0; j<10;j++){
                //jest statek i został trafiony
                if(battleFieldMeActivityRandomGame.getBattleField(i,j).isShip()
                        && battleFieldMeActivityRandomGame.getBattleField(i,j).isHit()){
                    displayShipCellHidden((TextView) layoutMy.getChildAt(10*i+j));
                }

                // woda i została trafiony
                else if(!battleFieldMeActivityRandomGame.getBattleField(i,j).isShip()
                        && battleFieldMeActivityRandomGame.getBattleField(i,j).isHit()){
                    displayWaterCellHidden((TextView) layoutMy.getChildAt(10*i+j));
                }

                // jest statek i nie został trafiony
                else if(battleFieldMeActivityRandomGame.getBattleField(i,j).isShip()
                        &&!battleFieldMeActivityRandomGame.getBattleField(i,j).isHit()){
                        displayWidmoShipHidden((TextView) layoutMy.getChildAt(10*i+j));
                }
                // nie ma statku i nie został trafiony
                else if(!battleFieldMeActivityRandomGame.getBattleField(i,j).isShip()
                        &&!battleFieldMeActivityRandomGame.getBattleField(i,j).isHit()){
                        displayBattleCellHidden((TextView) layoutMy.getChildAt(10*i+j));
                }

                else;
            }
        }

    }

    private void hideBattleFiledAvailablePlayerTwo() {

        for(int i =0;i<10;i++){
            for(int j = 0; j<10;j++){
                if(battleFieldOpponent[i][j]==SHIP_BROWN){
                    layoutOpponent.getChildAt(i*10+j).setBackground(getDrawable(R.drawable.battle_cell_ship_sunk_x_hidden));
                }
                else if(battleFieldOpponent[i][j]==SHIP_RED){
                    layoutOpponent.getChildAt(i*10+j).setBackground(getDrawable(R.drawable.battle_cell_ship_sunk_x_hidden));
                }else if(battleFieldOpponent[i][j]==WATER){
                    layoutOpponent.getChildAt(i*10+j).setBackground(getDrawable(R.drawable.water_cell_x_hidden));
                }else{
                    layoutOpponent.getChildAt(i*10+j).setBackground(getDrawable(R.drawable.battle_cell_x_hidden));
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
                    displayShipCellHit((TextView) layoutMy.getChildAt(10*i+j));
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
                    displayWaterCellHit((TextView) layoutMy.getChildAt(10*i+j));
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

    private void displayWaterCellHit(TextView textView) {
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            textView.setBackgroundDrawable(getResources().getDrawable(R.drawable.water_cell_x_hit));
        } else {
            textView.setBackground(getResources().getDrawable(R.drawable.water_cell_x_hit));
        }
    }

    private void displayShipCellHit(TextView textView) {
        final int sdk = Build.VERSION.SDK_INT;
        if(sdk < Build.VERSION_CODES.JELLY_BEAN) {
            textView.setBackgroundDrawable(getResources().getDrawable(R.drawable.ship_cell_x_hit));
        } else {
            textView.setBackground(getResources().getDrawable(R.drawable.ship_cell_x_hit));
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
                            displayShipCellHit((TextView) layoutMy.getChildAt(10*x+j));
                //            displayShipCellHit(TextViewArrayActivityRandomGameMe, x, j);
                            ShootTable.remove(Integer.valueOf(x * 10 + j));
                            battleFieldMeActivityRandomGame.battleField[x][j].setHit(true);
                            if (zatopiony2(battleFieldMeActivityRandomGame.getBattleField(i, j).getNumberOfMasts(), battleFieldMeActivityRandomGame.getBattleField(i, j).getShipNumber())) {
                                newShoot = true;
                            } else {
                                x = x - 1;
                            }
                        } else {
                            displayWaterCellHit((TextView) layoutMy.getChildAt(10*x+j));
                  //          displayWaterCellHit(TextViewArrayActivityRandomGameMe, x, j);
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
                                    displayShipCellHit((TextView) layoutMy.getChildAt(10*x+j));
                        //            displayShipCellHit(TextViewArrayActivityRandomGameMe, x, j);
                                    ShootTable.remove(Integer.valueOf(x * 10 + j));
                                    battleFieldMeActivityRandomGame.battleField[x][j].setHit(true);
                                    if (zatopiony2(battleFieldMeActivityRandomGame.getBattleField(i, j).getNumberOfMasts(), battleFieldMeActivityRandomGame.getBattleField(i, j).getShipNumber())) {
                                        newShoot = true;
                                    } else {
                                        x = x - 1;
                                    }
                                } else {
                                    displayWaterCellHit((TextView) layoutMy.getChildAt(10*x+j));
//                                    displayWaterCellHit(TextViewArrayActivityRandomGameMe, x, j);
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
                                    displayShipCellHit((TextView) layoutMy.getChildAt(10*x+j));
            //                        displayShipCellHit(TextViewArrayActivityRandomGameMe, x, j);
                                    ShootTable.remove(Integer.valueOf(x * 10 + j));
                                    battleFieldMeActivityRandomGame.battleField[x][j].setHit(true);
                                    if (zatopiony2(battleFieldMeActivityRandomGame.getBattleField(i, j).getNumberOfMasts(), battleFieldMeActivityRandomGame.getBattleField(i, j).getShipNumber())) {
                                        newShoot = true;
                                    } else {
                                        x = x - 1;
                                    }
                                } else {
                                    displayWaterCellHit((TextView) layoutMy.getChildAt(10*x+j));
            //                        displayWaterCellHit(TextViewArrayActivityRandomGameMe, x, j);
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
                                    displayShipCellHit((TextView) layoutMy.getChildAt(10*x+j));
    //                                displayShipCellHit(TextViewArrayActivityRandomGameMe, x, j);
                                    ShootTable.remove(Integer.valueOf(x * 10 + j));
                                    battleFieldMeActivityRandomGame.battleField[x][j].setHit(true);
                                    if (zatopiony2(battleFieldMeActivityRandomGame.getBattleField(i, j).getNumberOfMasts(), battleFieldMeActivityRandomGame.getBattleField(i, j).getShipNumber())) {
                                        newShoot = true;
                                    } else {
                                        x = x - 1;
                                    }
                                } else {
                                    displayWaterCellHit((TextView) layoutMy.getChildAt(10*x+j));
 //                                   displayWaterCellHit(TextViewArrayActivityRandomGameMe, x, j);
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
                            displayShipCellHit((TextView) layoutMy.getChildAt(10*x+j));
//                            displayShipCellHit(TextViewArrayActivityRandomGameMe,x,j);
                            ShootTable.remove(Integer.valueOf(x*10+j));
                            battleFieldMeActivityRandomGame.battleField[x][j].setHit(true);
                            if(zatopiony2(battleFieldMeActivityRandomGame.getBattleField(i,j).getNumberOfMasts(), battleFieldMeActivityRandomGame.getBattleField(i,j).getShipNumber())){
                                newShoot=true;
                            }
                            else{
                                x=x+1;
                            }
                        }else{
                            displayWaterCellHit((TextView) layoutMy.getChildAt(10*x+j));
//                            displayWaterCellHit(TextViewArrayActivityRandomGameMe,x,j);
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
                                    displayShipCellHit((TextView) layoutMy.getChildAt(10*x+j));
                        //            displayShipCellHit(TextViewArrayActivityRandomGameMe,x,j);
                                    ShootTable.remove(Integer.valueOf(x*10+j));
                                    battleFieldMeActivityRandomGame.battleField[x][j].setHit(true);
                                    if(zatopiony2(battleFieldMeActivityRandomGame.getBattleField(i,j).getNumberOfMasts(), battleFieldMeActivityRandomGame.getBattleField(i,j).getShipNumber())){
                                        newShoot=true;
                                    }
                                    else{
                                        x=x+1;
                                    }
                                }else{
                                    displayWaterCellHit((TextView) layoutMy.getChildAt(10*x+j));
//                                    displayWaterCellHit(TextViewArrayActivityRandomGameMe,x,j);
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
                                    displayShipCellHit((TextView) layoutMy.getChildAt(10*x+j));
//                                    displayShipCellHit(TextViewArrayActivityRandomGameMe, x, j);
                                    ShootTable.remove(Integer.valueOf(x * 10 + j));
                                    battleFieldMeActivityRandomGame.battleField[x][j].setHit(true);
                                    if (zatopiony2(battleFieldMeActivityRandomGame.getBattleField(i, j).getNumberOfMasts(), battleFieldMeActivityRandomGame.getBattleField(i, j).getShipNumber())) {
                                        newShoot = true;
                                    } else {
                                        x = x + 1;
                                    }
                                } else {
                                    displayWaterCellHit((TextView) layoutMy.getChildAt(10*x+j));
//                                    displayWaterCellHit(TextViewArrayActivityRandomGameMe, x, j);
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
                                    displayShipCellHit((TextView) layoutMy.getChildAt(10*x+j));
//                                    displayShipCellHit(TextViewArrayActivityRandomGameMe,x,j);
                                    ShootTable.remove(Integer.valueOf(x*10+j));
                                    battleFieldMeActivityRandomGame.battleField[x][j].setHit(true);
                                    if(zatopiony2(battleFieldMeActivityRandomGame.getBattleField(i,j).getNumberOfMasts(), battleFieldMeActivityRandomGame.getBattleField(i,j).getShipNumber())){
                                        newShoot=true;
                                    }
                                    else{
                                        x=x+1;
                                    }
                                }else{
                                    displayWaterCellHit((TextView) layoutMy.getChildAt(10*x+j));
//                                    displayWaterCellHit(TextViewArrayActivityRandomGameMe,x,j);
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
                            displayShipCellHit((TextView) layoutMy.getChildAt(10*i+y));
//                            displayShipCellHit(TextViewArrayActivityRandomGameMe, i, y);
                            ShootTable.remove(Integer.valueOf(i * 10 + y));
                            battleFieldMeActivityRandomGame.battleField[i][y].setHit(true);
                            if (zatopiony2(battleFieldMeActivityRandomGame.getBattleField(i, j).getNumberOfMasts(), battleFieldMeActivityRandomGame.getBattleField(i, j).getShipNumber())) {
                                newShoot = true;
                            } else {
                                y = y - 1;
                            }
                        } else {
                            displayWaterCellHit((TextView) layoutMy.getChildAt(10*i+y));
//                            displayWaterCellHit(TextViewArrayActivityRandomGameMe, i, y);
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
                                    displayShipCellHit((TextView) layoutMy.getChildAt(10*i+y));
         //                           displayShipCellHit(TextViewArrayActivityRandomGameMe, i, y);
                                    ShootTable.remove(Integer.valueOf(i * 10 + y));
                                    battleFieldMeActivityRandomGame.battleField[i][y].setHit(true);
                                    if (zatopiony2(battleFieldMeActivityRandomGame.getBattleField(i, j).getNumberOfMasts(), battleFieldMeActivityRandomGame.getBattleField(i, j).getShipNumber())) {
                                        newShoot = true;
                                    } else {
                                        y = y - 1;
                                    }
                                } else {
                                    displayWaterCellHit((TextView) layoutMy.getChildAt(10*i+y));
//                                    displayWaterCellHit(TextViewArrayActivityRandomGameMe, i, y);
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
                                    displayShipCellHit((TextView) layoutMy.getChildAt(10*i+y));
//                                    displayShipCellHit(TextViewArrayActivityRandomGameMe, i, y);
                                    ShootTable.remove(Integer.valueOf(i * 10 + y));
                                    battleFieldMeActivityRandomGame.battleField[i][y].setHit(true);
                                    if (zatopiony2(battleFieldMeActivityRandomGame.getBattleField(i, j).getNumberOfMasts(), battleFieldMeActivityRandomGame.getBattleField(i, j).getShipNumber())) {
                                        newShoot = true;
                                    } else {
                                        y = y - 1;
                                    }
                                } else {
                                    displayWaterCellHit((TextView) layoutMy.getChildAt(10*i+y));
//                                    displayWaterCellHit(TextViewArrayActivityRandomGameMe, i, y);
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
                                    displayShipCellHit((TextView) layoutMy.getChildAt(10*i+y));
//                                    displayShipCellHit(TextViewArrayActivityRandomGameMe, i, y);
                                    ShootTable.remove(Integer.valueOf(i * 10 + y));
                                    battleFieldMeActivityRandomGame.battleField[i][y].setHit(true);
                                    if (zatopiony2(battleFieldMeActivityRandomGame.getBattleField(i, j).getNumberOfMasts(), battleFieldMeActivityRandomGame.getBattleField(i, j).getShipNumber())) {
                                        newShoot = true;
                                    } else {
                                        y = y - 1;
                                    }
                                } else {
                                    displayWaterCellHit((TextView) layoutMy.getChildAt(10*i+y));
//                                    displayWaterCellHit(TextViewArrayActivityRandomGameMe, i, y);
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
                            displayShipCellHit((TextView) layoutMy.getChildAt(10*i+y));
//                            displayShipCellHit(TextViewArrayActivityRandomGameMe,i,y);
                            ShootTable.remove(Integer.valueOf(i*10+y));
                            battleFieldMeActivityRandomGame.battleField[i][y].setHit(true);
                            if(zatopiony2(battleFieldMeActivityRandomGame.getBattleField(i,j).getNumberOfMasts(), battleFieldMeActivityRandomGame.getBattleField(i,j).getShipNumber())){
                                newShoot=true;
                            }
                            else{
                                y=y+1;
                            }
                        }else{
                            displayWaterCellHit((TextView) layoutMy.getChildAt(10*i+y));
//                            displayWaterCellHit(TextViewArrayActivityRandomGameMe,i,y);
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
                                     displayShipCellHit((TextView) layoutMy.getChildAt(10*i+y));
//                                     displayShipCellHit(TextViewArrayActivityRandomGameMe,i,y);
                                     ShootTable.remove(Integer.valueOf(i*10+y));
                                     battleFieldMeActivityRandomGame.battleField[i][y].setHit(true);
                                     if(zatopiony2(battleFieldMeActivityRandomGame.getBattleField(i,j).getNumberOfMasts(), battleFieldMeActivityRandomGame.getBattleField(i,j).getShipNumber())){
                                         newShoot=true;
                                     }
                                     else{
                                         y=y+1;
                                     }
                                 }else{
                                     displayWaterCellHit((TextView) layoutMy.getChildAt(10*i+y));
//                                     displayWaterCellHit(TextViewArrayActivityRandomGameMe,i,y);
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
                                     displayShipCellHit((TextView) layoutMy.getChildAt(10*i+y));
//                                     displayShipCellHit(TextViewArrayActivityRandomGameMe,i,y);
                                     ShootTable.remove(Integer.valueOf(i*10+y));
                                     battleFieldMeActivityRandomGame.battleField[i][y].setHit(true);
                                     if(zatopiony2(battleFieldMeActivityRandomGame.getBattleField(i,j).getNumberOfMasts(), battleFieldMeActivityRandomGame.getBattleField(i,j).getShipNumber())){
                                         newShoot=true;
                                     }
                                     else{
                                         y=y+1;
                                     }
                                 }else{
                                     displayWaterCellHit((TextView) layoutMy.getChildAt(10*i+y));
//                                     displayWaterCellHit(TextViewArrayActivityRandomGameMe,i,y);
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
                                     displayShipCellHit((TextView) layoutMy.getChildAt(10*i+y));
//                                     displayShipCellHit(TextViewArrayActivityRandomGameMe,i,y);
                                     ShootTable.remove(Integer.valueOf(i*10+y));
                                     battleFieldMeActivityRandomGame.battleField[i][y].setHit(true);
                                     if(zatopiony2(battleFieldMeActivityRandomGame.getBattleField(i,j).getNumberOfMasts(), battleFieldMeActivityRandomGame.getBattleField(i,j).getShipNumber())){
                                         newShoot=true;
                                     }
                                     else{
                                         y=y+1;
                                     }
                                 }else{
                                     displayWaterCellHit((TextView) layoutMy.getChildAt(10*i+y));
//                                     displayWaterCellHit(TextViewArrayActivityRandomGameMe,i,y);
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

    private void displayBattleFieldActivityRandomGamePlayerOne(GridLayout gridLayout, BattleField battleFieldPlayerOneActivityRandomGame) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (battleFieldPlayerOneActivityRandomGame.getBattleField(i, j).isShip()) {

                    final int sdk = android.os.Build.VERSION.SDK_INT;
                    if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        gridLayout.getChildAt(10*i+j).setBackgroundDrawable(getResources().getDrawable(R.drawable.battle_cell_widmo_ship_x));
                    } else {
                        gridLayout.getChildAt(10*i+j).setBackground(getResources().getDrawable(R.drawable.battle_cell_widmo_ship_x));
                    }
                }
            }
        }
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(enableTouchListener) {
            final int X = (int) event.getRawX();
            final int Y = (int) event.getRawY();
            int x = (X - locationLayout[0]) / width;
            int y = (Y - locationLayout[1]) / height;
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:

                    if(battleFieldOpponent[y][x]==BATTLE_CELL){
                        for (int i = 0; i < 10; i++) {
                            for (int j = 0; j < 10; j++) {
                                if (x == j || y == i) {
                                    if(battleFieldOpponent[i][j]==BATTLE_CELL){
                                        tv = (TextView) layoutOpponent.getChildAt(10*i+j);
                                        tv.setBackground(getDrawable(R.drawable.battle_cell_x_green_field));
                                    }else if(battleFieldOpponent[i][j]==WATER){
                                        tv = (TextView) layoutOpponent.getChildAt(10*i+j);
                                        tv.setBackground(getDrawable(R.drawable.water_cell_x_green_field));
                                    }else{
                                        tv = (TextView) layoutOpponent.getChildAt(10*i+j);
                                        tv.setBackground(getDrawable(R.drawable.ship_cell_x_green_field));
                                    }
                                }
                            }
                        }
                    }else {
                        for (int i = 0; i < 10; i++) {
                            for (int j = 0; j < 10; j++) {
                                if (x == j || y == i) {
                                    if(battleFieldOpponent[i][j]==BATTLE_CELL){
                                        tv = (TextView) layoutOpponent.getChildAt(10*i+j);
                                        tv.setBackground(getDrawable(R.drawable.battle_cell_x_red_field));
                                    }else if(battleFieldOpponent[i][j]==WATER){
                                        tv = (TextView) layoutOpponent.getChildAt(10*i+j);
                                        tv.setBackground(getDrawable(R.drawable.water_cell_x_red_field));
                                    }else{
                                        tv = (TextView) layoutOpponent.getChildAt(10*i+j);
                                        tv.setBackground(getDrawable(R.drawable.ship_cell_x_red_field));
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
                                            tv = (TextView) layoutOpponent.getChildAt(10*i+j);
                                            tv.setBackground(getDrawable(R.drawable.battle_cell_x_green_field));
                                        }else if(battleFieldOpponent[i][j]==WATER){
                                            tv = (TextView) layoutOpponent.getChildAt(10*i+j);
                                            tv.setBackground(getDrawable(R.drawable.water_cell_x_green_field));
                                        }else{
                                            tv = (TextView) layoutOpponent.getChildAt(10*i+j);
                                            tv.setBackground(getDrawable(R.drawable.ship_cell_x_green_field));
                                        }
                                    }
                                }
                            }
                        }else {
                            for (int i = 0; i < 10; i++) {
                                for (int j = 0; j < 10; j++) {
                                    if (x == j || y == i) {
                                        if(battleFieldOpponent[i][j]==BATTLE_CELL){
                                            tv = (TextView) layoutOpponent.getChildAt(10*i+j);
                                            tv.setBackground(getDrawable(R.drawable.battle_cell_x_red_field));
                                        }else if(battleFieldOpponent[i][j]==WATER){
                                            tv = (TextView) layoutOpponent.getChildAt(10*i+j);
                                            tv.setBackground(getDrawable(R.drawable.water_cell_x_red_field));
                                        }else{
                                            tv = (TextView) layoutOpponent.getChildAt(10*i+j);
                                            tv.setBackground(getDrawable(R.drawable.ship_cell_x_red_field));
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
        layoutOpponent.invalidate();
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
                    fourMasts.getChildAt(i).setBackground(getDrawable(R.drawable.battle_cell_ship_sunk_x));
                }
            }
            if(!(shipThreeMastsCounterFirst==0)){
                for(int i=0;i<shipThreeMastsCounterFirst;i++){
                    threeMastsFirst.getChildAt(i).setBackground(getDrawable(R.drawable.battle_cell_ship_sunk_x));
                }
            }
            if(!(shipThreeMastsCounterSecond==0)){
                for(int i=0;i<shipThreeMastsCounterSecond;i++){
                    threeMastsSecond.getChildAt(i).setBackground(getDrawable(R.drawable.battle_cell_ship_sunk_x));
                }
            }
            if(!(shipTwoMastsCounterFirst==0)){
                for(int i=0;i<shipTwoMastsCounterFirst;i++){
                    twoMastsFirst.getChildAt(i).setBackground(getDrawable(R.drawable.battle_cell_ship_sunk_x));
                }
            }
            if(!(shipTwoMastsCounterSecond==0)){
                for(int i=0;i<shipTwoMastsCounterSecond;i++){
                    twoMastsSecond.getChildAt(i).setBackground(getDrawable(R.drawable.battle_cell_ship_sunk_x));
                }
            }
            if(!(shipTwoMastsCounterThird==0)){
                for(int i=0;i<shipTwoMastsCounterThird;i++){
                    twoMastsThird.getChildAt(i).setBackground(getDrawable(R.drawable.battle_cell_ship_sunk_x));
                }
            }
            if(!(shipOneMastsCounterFirst==0)){
                    oneMastsFirst.getChildAt(0).setBackground(getDrawable(R.drawable.battle_cell_ship_sunk_x));
            }
            if(!(shipOneMastsCounterSecond==0)){
                oneMastsSecond.getChildAt(0).setBackground(getDrawable(R.drawable.battle_cell_ship_sunk_x));
            }
            if(!(shipOneMastsCounterThird==0)){
                oneMastsThird.getChildAt(0).setBackground(getDrawable(R.drawable.battle_cell_ship_sunk_x));
            }
            if(!(shipOneMastsCounterFourth==0)){
                oneMastsFourth.getChildAt(0).setBackground(getDrawable(R.drawable.battle_cell_ship_sunk_x));
            }
        }
        else{
            if(shipFourMastsCounter==4){
                for(int i=0;i<shipFourMastsCounter;i++){
                    fourMasts.getChildAt(i).setBackground(getDrawable(R.drawable.battle_cell_ship_sunk_x));
                }
            }
            if(shipThreeMastsCounterFirst==3){
                for(int i=0;i<shipThreeMastsCounterFirst;i++){
                    threeMastsFirst.getChildAt(i).setBackground(getDrawable(R.drawable.battle_cell_ship_sunk_x));
                }
            }
            if(shipThreeMastsCounterSecond==3){
                for(int i=0;i<shipThreeMastsCounterSecond;i++){
                    threeMastsSecond.getChildAt(i).setBackground(getDrawable(R.drawable.battle_cell_ship_sunk_x));
                }
            }
            if(shipTwoMastsCounterFirst==2){
                for(int i=0;i<shipTwoMastsCounterFirst;i++){
                    twoMastsFirst.getChildAt(i).setBackground(getDrawable(R.drawable.battle_cell_ship_sunk_x));
                }
            }
            if(shipTwoMastsCounterSecond==2){
                for(int i=0;i<shipTwoMastsCounterSecond;i++){
                    twoMastsSecond.getChildAt(i).setBackground(getDrawable(R.drawable.battle_cell_ship_sunk_x));
                }
            }
            if(shipTwoMastsCounterThird==2){
                for(int i=0;i<shipTwoMastsCounterThird;i++){
                    twoMastsThird.getChildAt(i).setBackground(getDrawable(R.drawable.battle_cell_ship_sunk_x));
                }
            }
            if(shipOneMastsCounterFirst==1){
                oneMastsFirst.getChildAt(0).setBackground(getDrawable(R.drawable.battle_cell_ship_sunk_x));
            }
            if(shipOneMastsCounterSecond==1){
                oneMastsSecond.getChildAt(0).setBackground(getDrawable(R.drawable.battle_cell_ship_sunk_x));
            }
            if(shipOneMastsCounterThird==1){
                oneMastsThird.getChildAt(0).setBackground(getDrawable(R.drawable.battle_cell_ship_sunk_x));
            }
            if(shipOneMastsCounterFourth==1){
                oneMastsFourth.getChildAt(0).setBackground(getDrawable(R.drawable.battle_cell_ship_sunk_x));
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

    public void surrenderSinglePlayer(View view) {
        if (loggedIn) {
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    User user = dataSnapshot.getValue(User.class);
                    int minusPoints;
                    if(level==0){
                        minusPoints=1;
                    }else if(level==2){
                        minusPoints=10;
                    }else if(level==3){
                        minusPoints=100;
                    }else
                        minusPoints=0;

                    AlertDialog.Builder builder = new AlertDialog.Builder(GameBattle.this);
                    builder.setCancelable(true);
                    builder.setTitle("Leaving game");
                    builder.setMessage("Do you want to quit game?"+"\n"+"You will lose "+minusPoints+" points");
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int myScore = user.getScore();
                            myScore=myScore-minusPoints;
                            user.setScore(myScore);
                            databaseReference.setValue(user);
                            finish();
                        }
                    });
                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mHandler.postDelayed(game,deelay);
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });

        }
        else {

            AlertDialog.Builder builder = new AlertDialog.Builder(GameBattle.this);
            builder.setCancelable(true);
            builder.setTitle("Leaving game");
            builder.setMessage("Do you want to quit game?");
            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mHandler.postDelayed(game,deelay);
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    public void leaveMainMenuOnClick(View view) {
        leaveGame();
    }

    @Override
    public void onBackPressed() {
        leaveGame();
    }

    private void leaveGame() {
        AlertDialog.Builder builder = new AlertDialog.Builder(GameBattle.this);
        builder.setCancelable(true);
        builder.setTitle("Leaving game");
        builder.setMessage("Do you want to go back to main menu?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mHandler.postDelayed(game,deelay);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
// TODO use SinglePlayerMatch instead of two battle fields :..(
// TODO surrender and leave button (leave has to save game state if logged in)