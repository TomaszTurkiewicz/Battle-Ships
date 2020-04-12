package com.example.ships;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Shader;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.ships.classes.BattleFieldForDataBase;
import com.example.ships.classes.FightIndex;
import com.example.ships.classes.GameDifficulty;
import com.example.ships.classes.TileDrawable;
import com.example.ships.classes.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MultiplayerActivity extends AppCompatActivity implements View.OnTouchListener{

    private static String TAG = "NOTIFICATION TAG";
    private static String FCM_API="https://fcm.googleapis.com/fcm/send";
    private static String TOPIC;
    private static String NOTIFICATION_MESSAGE;
    private static String NOTIFICATION_TITLE;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReferenceMy, databaseReferenceFight, databaseReferenceOpponent;
    private String userID;
    private User user = new User();
    private BattleFieldForDataBase battleFieldForDataBaseMy = new BattleFieldForDataBase();
    private BattleFieldForDataBase battleFieldForDataBaseOpponent = new BattleFieldForDataBase();
    private Handler mHandler = new Handler();
    private Handler mHandler2 = new Handler();
    private int deelay = 1000;
    private int shipFourMastsCounter,
            shipThreeMastsCounterFirst, shipThreeMastsCounterSecond,
            shipTwoMastsCounterFirst, shipTwoMastsCounterSecond, shipTwoMastsCounterThird,
            shipOneMastsCounterFirst, shipOneMastsCounterSecond, shipOneMastsCounterThird, shipOneMastsCounterFourth;
    private boolean battleFieldsSet;
    private TextView turnTextView;
    private ImageButton leaveButton;
    private boolean enableTouchListener;
    private GridLayout layoutOpponent, layoutMy;

    private int height, width;
    int[]locationLayout = new int [2];
    private int battleFieldOpponent[][] = new int[10][10];
    private final static int BATTLE_CELL = 0;
    private final static int WATER = 1;
    private final static int SHIP_RED = 2;
    private final static int SHIP_BROWN = 3;
    private TextView tv;
    private String serverKey= "key=" + "AAAAUhITVm0:APA91bGLIOR5L7HQyh64ejoejk-nQFBWP9RxDqtzzjoSXCmROqs7JO_uDDyuW5VuTfJBxtKY_RG8q5_CnpKJsN3qHtVvgiAkuDM2J9T68mk0LzKCcRKgRbj3DQ-A1a8uzZ07wz8OlirQ";
    private String contentType= "application/json";
    private boolean battleFieldUpToDate;
    private ConstraintLayout mainLayout;
    private LinearLayout linearLayoutLettersMy, linearLayoutNumbersMy, linearLayoutLettersOpponent, linearLayoutNumbersOpponent;
    private int marginTop;
    private int marginLeft;
    private LinearLayout fourMasts,threeMastsFirst, threeMastsSecond, twoMastsFirst,twoMastsSecond,twoMastsThird,oneMastsFirst,oneMastsSecond,oneMastsThird,oneMastsFourth;
    private int marginLeftForShips;
    private int marginDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_multiplayer);
        mainLayout=findViewById(R.id.multiplayerActivityLayout);
        layoutMy=findViewById(R.id.gridLayoutMultiplayerBattleMy);
        layoutOpponent=findViewById(R.id.gridLayoutMultiplayerBattleOpponent);
        linearLayoutLettersMy=findViewById(R.id.linearLayoutMultiplayerActivityLettersMy);
        linearLayoutLettersOpponent=findViewById(R.id.linearLayoutMultiplayerActivityLettersOpponent);
        linearLayoutNumbersMy=findViewById(R.id.linearLayoutMultiplayerActivityNumbersMy);
        linearLayoutNumbersOpponent=findViewById(R.id.linearLayoutMultiplayerActivityNumbersOpponent);
        fourMasts=findViewById(R.id.linearLayoutMultiplayerShipFourMasts);
        threeMastsFirst=findViewById(R.id.linearLayoutMultiplayerShipThreeMastsFirst);
        threeMastsSecond=findViewById(R.id.linearLayoutMultiplayerShipThreeMastsSecond);
        twoMastsFirst=findViewById(R.id.linearLayoutMultiplayerShipTwoMastsFirst);
        twoMastsSecond=findViewById(R.id.linearLayoutMultiplayerShipTwoMastsSecond);
        twoMastsThird=findViewById(R.id.linearLayoutMultiplayerShipTwoMastsThird);
        oneMastsFirst=findViewById(R.id.linearLayoutMultiplayerShipOneMastsFirst);
        oneMastsSecond=findViewById(R.id.linearLayoutMultiplayerShipOneMastsSecond);
        oneMastsThird=findViewById(R.id.linearLayoutMultiplayerShipOneMastsThird);
        oneMastsFourth=findViewById(R.id.linearLayoutMultiplayerShipOneMastsFourth);
        leaveButton = findViewById(R.id.leaveMultiplayer);
        leaveButton.setBackgroundResource(R.drawable.leave);
        turnTextView=findViewById(R.id.turn);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        userID = firebaseUser.getUid();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReferenceMy=firebaseDatabase.getReference("User").child(userID);
        leaveButton.setVisibility(View.GONE);
        enableTouchListener=false;
        battleFieldsSet=false;
        battleFieldUpToDate=false;

        SharedPreferences sp = getSharedPreferences("VALUES", Activity.MODE_PRIVATE);
        int square = sp.getInt("square",-1);
        int screenWidth = sp.getInt("width",-1);
        int screenHeight = sp.getInt("height",-1);
        int screenWidthOffSet = sp.getInt("widthOffSet",-1);
        int screenHeightOffSet = sp.getInt("heightOffSet",-1);
        float textSize = (square*9)/10;
        marginTop = 4*square;
        marginLeft = screenWidth-screenWidthOffSet-14*square;
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

        leaveButton.setLayoutParams(params);
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

        marginDown = screenHeight-screenHeightOffSet-2*square;

        if(((screenWidth-screenWidthOffSet)/square)%2==0){
            marginLeftForShips=(screenWidth-screenWidthOffSet)/2-15*square;
        }else{
            marginLeftForShips=(screenWidth-screenWidthOffSet-square)/2-15*square;
        }

        ConstraintSet set = new ConstraintSet();
        set.clone(mainLayout);

        set.connect(leaveButton.getId(),ConstraintSet.TOP,mainLayout.getId(),ConstraintSet.TOP,square);
        set.connect(leaveButton.getId(),ConstraintSet.LEFT,mainLayout.getId(),ConstraintSet.LEFT,square);

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

        set.applyTo(mainLayout);

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

        game.run();
        checkGameIndex.run();

    }


    private Runnable checkGameIndex = new Runnable() {
        @Override
        public void run() {
            databaseReferenceMy.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    user = dataSnapshot.getValue(User.class);

                    if(user.getIndex().getGameIndex().isEmpty()){
                        mHandler2.removeCallbacks(checkGameIndex);

                        finish();
                    }else{
                        mHandler2.postDelayed(checkGameIndex,deelay);
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    };


    private Runnable game = new Runnable() {
        @Override
        public void run() {
            if(!battleFieldsSet) {
                createFields();
            }else{
                fight();
            }
        }
    };

    private void fight() {

        databaseReferenceMy.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                if(user.getIndex().getOpponent().isEmpty()){

                    finish();
                }else{
                    makeMove();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void makeMove() {

        databaseReferenceFight.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String turn = (String) dataSnapshot.child("turn").getValue();
                if(user.getId().equals(turn)){
                    turnTextView.setText("MY MOVE");
                    battleFieldForDataBaseMy = dataSnapshot.child(user.getId()).getValue(BattleFieldForDataBase.class);
                    battleFieldForDataBaseMy.listToField();
                    showOpponentBattleField();
                    hideBattleFiledAvailableMy();
                    enableTouchListener=true;
                }
                else{
                    turnTextView.setText("NOT MY MOVE");
                    String winner = (String) dataSnapshot.child("winner").getValue();

                    if(user.getIndex().getOpponent().equals(winner)){
                        databaseReferenceFight.removeValue();
                        FightIndex fightIndex = new FightIndex();
                        databaseReferenceMy.child("index").setValue(fightIndex);
                        databaseReferenceOpponent.child("index").setValue(fightIndex);
                        Intent intent = new Intent(MultiplayerActivity.this,WinPlayerTwo.class);
                        startActivity(intent);
                        finish();
                    }else{
                        battleFieldForDataBaseMy = dataSnapshot.child(user.getId()).getValue(BattleFieldForDataBase.class);
                        battleFieldForDataBaseMy.listToField();
                        hideBattleFieldOpponent();
                        showMyBattleField();
                        mHandler.postDelayed(game, deelay);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void hideBattleFieldOpponent() {
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

    private void hideBattleFiledAvailableMy() {
        for(int i =0;i<10;i++){
            for(int j = 0; j<10;j++){
                //jest statek i został trafiony
                if(battleFieldForDataBaseMy.showBattleField().getBattleField(i,j).isShip()
                        && battleFieldForDataBaseMy.showBattleField().getBattleField(i,j).isHit()){
                    displayShipCellHidden((TextView) layoutMy.getChildAt(10*i+j));
                }

                // woda i została trafiony
                else if(!battleFieldForDataBaseMy.showBattleField().getBattleField(i,j).isShip()
                        && battleFieldForDataBaseMy.showBattleField().getBattleField(i,j).isHit()){
                    displayWaterCellHidden((TextView) layoutMy.getChildAt(10*i+j));
                }

                // jest statek i nie został trafiony
                else if(battleFieldForDataBaseMy.showBattleField().getBattleField(i,j).isShip()
                        &&!battleFieldForDataBaseMy.showBattleField().getBattleField(i,j).isHit()){
                    displayWidmoShipHidden((TextView) layoutMy.getChildAt(10*i+j));
                }
                // nie ma statku i nie został trafiony
                else if(!battleFieldForDataBaseMy.showBattleField().getBattleField(i,j).isShip()
                        &&!battleFieldForDataBaseMy.showBattleField().getBattleField(i,j).isHit()){
                    displayBattleCellHidden((TextView) layoutMy.getChildAt(10*i+j));
                }

                else;
            }
        }
    }


    private void createFields() {
        hideBattleFieldOpponent();
        hideBattleFiledAvailableMy();
        turnTextView.setText("WAITING FOR OPPONENT");
        databaseReferenceMy.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);

                if(user.getIndex().getOpponent().isEmpty()){

                    finish();
                }else {
                    databaseReferenceOpponent=firebaseDatabase.getReference("User").child(user.getIndex().getOpponent());
                    databaseReferenceFight = firebaseDatabase.getReference("Battle").child(user.getIndex().getGameIndex());
                    leaveButton.setVisibility(View.VISIBLE);
                    databaseReferenceFight.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (!dataSnapshot.exists()) {
                                askForCreatingGame();

                                databaseReferenceFight.child("turn").setValue(user.getId());
                                databaseReferenceFight.child("winner").setValue("");
                                databaseReferenceFight.child("ready").setValue(false);
                            } else {
                                if(!dataSnapshot.child(userID).exists()){
                                    askForCreatingGame();

                                }else{
                                    if(!battleFieldUpToDate){
                                        battleFieldForDataBaseMy = dataSnapshot.child(user.getId()).getValue(BattleFieldForDataBase.class);
                                        battleFieldForDataBaseMy.listToField();
                                        battleFieldUpToDate=true;
                                    }
                                    hideBattleFiledAvailableMy();
                                    if(dataSnapshot.child(userID).child("difficulty").child("set").getValue().equals(false)){
                                        chooseDifficulty(dataSnapshot);
                                    }else{
                                        if(dataSnapshot.child(user.getIndex().getOpponent()).exists()){
                                            if(dataSnapshot.child(user.getIndex().getOpponent()).child("difficulty").exists()) {
                                                battleFieldForDataBaseMy = dataSnapshot.child(user.getId()).getValue(BattleFieldForDataBase.class);
                                                battleFieldForDataBaseOpponent = dataSnapshot.child(user.getIndex().getOpponent()).getValue(BattleFieldForDataBase.class);
                                                battleFieldForDataBaseOpponent.listToField();
                                                battleFieldForDataBaseMy.listToField();
                                                if(battleFieldForDataBaseMy.isCreated() && battleFieldForDataBaseOpponent.isCreated() &&
                                                        battleFieldForDataBaseMy.getDifficulty().isSet() && battleFieldForDataBaseOpponent.getDifficulty().isSet()) {
                                                        databaseReferenceFight.child("ready").setValue(true);
                                                        battleFieldsSet = true;
                                                        initializeOpponentArrayBattleField();
                                                        checkShipCounters();
                                                        hideBattleFieldOpponent();
                                                        updateShipsHit();
                                                        mHandler.postDelayed(game, deelay);
                                                    }else{
                                                    mHandler.postDelayed(game, deelay);
                                                    }
                                                }else{
                                                mHandler.postDelayed(game,deelay);
                                            }
                                        }else{
                                            mHandler.postDelayed(game,deelay);
                                        }
                                    }
                                }
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void askForCreatingGame() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MultiplayerActivity.this);
        builder.setCancelable(true);
        builder.setTitle("CREATE BATTLE FIELD");
        builder.setMessage("How would you like to do it?");
        builder.setPositiveButton("BY MYSELF", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                GameDifficulty.getInstance().setMultiplayerMode(true);
                Intent intent = new Intent(MultiplayerActivity.this, CreateBattleField.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("RANDOM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                battleFieldForDataBaseMy.create();
                battleFieldUpToDate=true;
                databaseReferenceFight.child(user.getId()).setValue(battleFieldForDataBaseMy);                                int noOfGames = user.getNoOfGames();
                noOfGames = noOfGames+1;
                user.setNoOfGames(noOfGames);
                databaseReferenceMy.setValue(user);
                mHandler.postDelayed(game,deelay);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();


    }

    private void showOpponentBattleField() {
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

    private void initializeOpponentArrayBattleField() {
        for(int i = 0; i<10;i++){
            for(int j=0; j<10;j++){
                if(battleFieldForDataBaseOpponent.showBattleField().getBattleField(i,j).isHit()&&
                battleFieldForDataBaseOpponent.showBattleField().getBattleField(i,j).isShip()){
                    battleFieldOpponent[i][j]=SHIP_RED;
                } else if(battleFieldForDataBaseOpponent.showBattleField().getBattleField(i,j).isHit()&&
                        !battleFieldForDataBaseOpponent.showBattleField().getBattleField(i,j).isShip()){
                    battleFieldOpponent[i][j]=WATER;
                }else{
                    battleFieldOpponent[i][j]=BATTLE_CELL;
                }



            }
        }
    }

    private void checkShipCounters() {
        shipFourMastsCounter=0;
        shipThreeMastsCounterFirst=0;
        shipThreeMastsCounterSecond=0;
        shipTwoMastsCounterFirst=0;
        shipTwoMastsCounterSecond=0;
        shipTwoMastsCounterThird=0;
        shipOneMastsCounterFirst=0;
        shipOneMastsCounterSecond=0;
        shipOneMastsCounterThird=0;
        shipOneMastsCounterFourth=0;

        for(int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                if(battleFieldForDataBaseOpponent.showBattleField().getBattleField(i,j).isShip()&&
                battleFieldForDataBaseOpponent.showBattleField().getBattleField(i,j).isHit()){
                    int numberOfMasts = battleFieldForDataBaseOpponent.showBattleField().getBattleField(i,j).getNumberOfMasts();
                    int shipNumber = battleFieldForDataBaseOpponent.showBattleField().getBattleField(i,j).getShipNumber();
                    updateCounters(numberOfMasts,shipNumber);
                }
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
                if(battleFieldForDataBaseOpponent.showBattleField().getBattleField(i,j).getShipNumber()==shipNumber&&
                        battleFieldForDataBaseOpponent.showBattleField().getBattleField(i,j).getNumberOfMasts()==numberOfMasts){
                    battleFieldOpponent[i][j]=SHIP_BROWN;
                }
            }
        }
    }

    private void chooseDifficulty(DataSnapshot dataSnapshot) {

        AlertDialog.Builder builder = new AlertDialog.Builder(MultiplayerActivity.this);
        builder.setCancelable(true);
        builder.setTitle("Difficulty");
        builder.setMessage("choose game difficulty");
        builder.setPositiveButton("NORMAL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                databaseReferenceFight.child(userID).child("difficulty").child("easy").setValue(false);
                databaseReferenceFight.child(userID).child("difficulty").child("set").setValue(true);
                mHandler.postDelayed(game,deelay);
            }
        });
        builder.setNegativeButton("EASY", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                databaseReferenceFight.child(userID).child("difficulty").child("easy").setValue(true);
                databaseReferenceFight.child(userID).child("difficulty").child("set").setValue(true);
                mHandler.postDelayed(game,deelay);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void showMyBattleField() {
        for(int i =0;i<10;i++){
            for(int j = 0; j<10;j++){
                //jest statek i został trafiony
                if(battleFieldForDataBaseMy.showBattleField().getBattleField(i,j).isShip()
                        &&battleFieldForDataBaseMy.showBattleField().getBattleField(i,j).isHit()){
                    displayShipCell((TextView) layoutMy.getChildAt(10*i+j));
                }

                // woda i została trafiony
                else if(!battleFieldForDataBaseMy.showBattleField().getBattleField(i,j).isShip()
                        &&battleFieldForDataBaseMy.showBattleField().getBattleField(i,j).isHit()){
                    displayWaterCell((TextView) layoutMy.getChildAt(10*i+j));
                }

                // jest statek i nie został trafiony
                else if(battleFieldForDataBaseMy.showBattleField().getBattleField(i,j).isShip()
                        &&!battleFieldForDataBaseMy.showBattleField().getBattleField(i,j).isHit()){
                    displayWidmoShip((TextView) layoutMy.getChildAt(10*i+j));
                }
                // nie ma statku i nie został trafiony
                else if(!battleFieldForDataBaseMy.showBattleField().getBattleField(i,j).isShip()
                        &&!battleFieldForDataBaseMy.showBattleField().getBattleField(i,j).isHit()){
                    displayBattleCell((TextView) layoutMy.getChildAt(10*i+j));
                }
                else;
            }
        }
    }

    private void displayWidmoShip(TextView textView) {
        textView.setBackground(getResources().getDrawable(R.drawable.battle_cell_widmo_ship_x));
    }

    private void displayWaterCell(TextView textView) {
        textView.setBackground(getResources().getDrawable(R.drawable.water_cell_x));
    }


    private void displayBattleCell(TextView textView) {
        textView.setBackground(getResources().getDrawable(R.drawable.battle_cell_x));
    }

    private void displayShipCell(TextView textView) {
            textView.setBackground(getResources().getDrawable(R.drawable.battle_cell_ship_sunk_x));
    }


    private void updateShipsHit() {

        if(shipFourMastsCounter==0){
        }else{
            if(battleFieldForDataBaseMy.getDifficulty().isEasy()){
                for(int i=0;i<shipFourMastsCounter;i++){
                    fourMasts.getChildAt(i).setBackground(getDrawable(R.drawable.battle_cell_ship_sunk_x));
                }
            }else{
                if(shipFourMastsCounter==4){
                    for(int i=0;i<shipFourMastsCounter;i++){
                        fourMasts.getChildAt(i).setBackground(getDrawable(R.drawable.battle_cell_ship_sunk_x));
                    }
                }
            }
        }

        if(shipThreeMastsCounterFirst==0){
        }else{
            if(battleFieldForDataBaseMy.getDifficulty().isEasy()){
                for(int i=0;i<shipThreeMastsCounterFirst;i++){
                    threeMastsFirst.getChildAt(i).setBackground(getDrawable(R.drawable.battle_cell_ship_sunk_x));
                }
            }else{
                if(shipThreeMastsCounterFirst==3){
                    for(int i=0;i<shipThreeMastsCounterFirst;i++){
                        threeMastsFirst.getChildAt(i).setBackground(getDrawable(R.drawable.battle_cell_ship_sunk_x));
                    }
                }
            }
        }

        if(shipThreeMastsCounterSecond==0){
        }else{
            if(battleFieldForDataBaseMy.getDifficulty().isEasy()){
                for(int i=0;i<shipThreeMastsCounterSecond;i++){
                    threeMastsSecond.getChildAt(i).setBackground(getDrawable(R.drawable.battle_cell_ship_sunk_x));
                }
            }else{
                if(shipThreeMastsCounterSecond==3){
                    for(int i=0;i<shipThreeMastsCounterSecond;i++){
                        threeMastsSecond.getChildAt(i).setBackground(getDrawable(R.drawable.battle_cell_ship_sunk_x));
                    }
                }
            }
        }

        if(shipTwoMastsCounterFirst==0){
        }else{
            if(battleFieldForDataBaseMy.getDifficulty().isEasy()){
                for(int i=0;i<shipTwoMastsCounterFirst;i++){
                    twoMastsFirst.getChildAt(i).setBackground(getDrawable(R.drawable.battle_cell_ship_sunk_x));
                }
            }else{
                if(shipTwoMastsCounterFirst==2){
                    for(int i=0;i<shipTwoMastsCounterFirst;i++){
                        twoMastsFirst.getChildAt(i).setBackground(getDrawable(R.drawable.battle_cell_ship_sunk_x));
                    }
                }
            }
        }

        if(shipTwoMastsCounterSecond==0){
        }else{
            if(battleFieldForDataBaseMy.getDifficulty().isEasy()){
                for(int i=0;i<shipTwoMastsCounterSecond;i++){
                    twoMastsSecond.getChildAt(i).setBackground(getDrawable(R.drawable.battle_cell_ship_sunk_x));
                }
            }else{
                if(shipTwoMastsCounterSecond==2){
                    for(int i=0;i<shipTwoMastsCounterSecond;i++){
                        twoMastsSecond.getChildAt(i).setBackground(getDrawable(R.drawable.battle_cell_ship_sunk_x));
                    }
                }
            }
        }

        if(shipTwoMastsCounterThird==0){
        }else{
            if(battleFieldForDataBaseMy.getDifficulty().isEasy()){
                for(int i=0;i<shipTwoMastsCounterThird;i++){
                    twoMastsThird.getChildAt(i).setBackground(getDrawable(R.drawable.battle_cell_ship_sunk_x));
                }
            }else{
                if(shipTwoMastsCounterThird==2){
                    for(int i=0;i<shipTwoMastsCounterThird;i++){
                        twoMastsThird.getChildAt(i).setBackground(getDrawable(R.drawable.battle_cell_ship_sunk_x));
                    }
                }
            }
        }

        if(shipOneMastsCounterFirst==0){
        }else{
            oneMastsFirst.getChildAt(0).setBackground(getDrawable(R.drawable.battle_cell_ship_sunk_x));
        }

        if(shipOneMastsCounterSecond==0){
        }else{
            oneMastsSecond.getChildAt(0).setBackground(getDrawable(R.drawable.battle_cell_ship_sunk_x));
        }

        if(shipOneMastsCounterThird==0){
        }else{
            oneMastsThird.getChildAt(0).setBackground(getDrawable(R.drawable.battle_cell_ship_sunk_x));
        }

        if(shipOneMastsCounterFourth==0){
        }else{
            oneMastsFourth.getChildAt(0).setBackground(getDrawable(R.drawable.battle_cell_ship_sunk_x));
        }
    }

    public void leaveMultiplayer(View view) {
        mHandler.removeCallbacks(game);
        databaseReferenceOpponent.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User opponent=dataSnapshot.getValue(User.class);

                AlertDialog.Builder builder = new AlertDialog.Builder(MultiplayerActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Leaving game");
                builder.setMessage("Do you want to quit game?"+"\n"+opponent.getName()+" will get 50 points for nothing"+"\n"+"You will lose 50 points");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int score = opponent.getScore();
                        score = score+50;
                        opponent.setScore(score);
                        opponent.setIndex(new FightIndex());
                        databaseReferenceOpponent.setValue(opponent);
                        int myScore = user.getScore();
                        myScore=myScore-50;
                        user.setScore(myScore);
                        user.setIndex(new FightIndex());
                        databaseReferenceMy.setValue(user);
                        databaseReferenceFight.removeValue();
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
                        showOpponentBattleField();
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
                       showOpponentBattleField();
                    }
                    break;
                case MotionEvent.ACTION_UP:

                    if(x>=0&&x<=9&&y>=0&&y<=9){
                        if(battleFieldOpponent[y][x]==BATTLE_CELL) {
                            hitCell(y, x);

                        }else
                            showOpponentBattleField();
                    }else;

                    break;
            }
        }else;
        layoutOpponent.invalidate();
        return true;




    }

    private void hitCell(int x, int y) {

        databaseReferenceMy.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                if(user.getIndex().getGameIndex().isEmpty()){

                    finish();
                }else{

                    battleFieldForDataBaseOpponent.showBattleField().getBattleField(x,y).setHit(true);
                    databaseReferenceFight.child(user.getIndex().getOpponent()).setValue(battleFieldForDataBaseOpponent);
                    if(battleFieldForDataBaseOpponent.showBattleField().getBattleField(x,y).isShip()){
                        battleFieldOpponent[x][y]=SHIP_RED;
                        updateCounters(battleFieldForDataBaseOpponent.showBattleField().getBattleField(x,y).getNumberOfMasts(),
                                battleFieldForDataBaseOpponent.showBattleField().getBattleField(x,y).getShipNumber());
                        updateShipsHit();
                        showOpponentBattleField();
                        if(myWin()){
                            databaseReferenceFight.child("winner").setValue(user.getId());
                            int score = user.getScore();
                            if(battleFieldForDataBaseMy.getDifficulty().isEasy()){
                                score = score+5;
                            }else{
                                score = score+50;
                            }
                            user.setScore(score);
                            databaseReferenceMy.setValue(user);

                            Intent intent = new Intent(MultiplayerActivity.this,WinPlayerOne.class);
                            startActivity(intent);
                            finish();
                        }

                    }else{
                        battleFieldOpponent[x][y]=WATER;
                        showOpponentBattleField();
                        enableTouchListener=false;
                        databaseReferenceFight.child("turn").setValue(user.getIndex().getOpponent());

                        TOPIC = "/topics/"+ user.getIndex().getOpponent();

                        NOTIFICATION_TITLE = "Your move";
          //              NOTIFICATION_MESSAGE = "Your move";

                        JSONObject notification = new JSONObject();
                        JSONObject notificationBody = new JSONObject();

                        try{
                            notificationBody.put("title",NOTIFICATION_TITLE);
          //                  notificationBody.put("message",NOTIFICATION_MESSAGE);

                            notification.put("to",TOPIC);
                            notification.put("notification",notificationBody);
          //                  notification.put("data",notificationBody);
                        } catch (JSONException e){
                            Log.e(TAG,"onCreate: "+e.getMessage());
                        }
                        sendNotification(notification);

                        mHandler.postDelayed(game,deelay);

                    }



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void sendNotification(JSONObject notification) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(FCM_API, notification,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG, "onResponse: " + response.toString());

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MultiplayerActivity.this, "Request error", Toast.LENGTH_LONG).show();
                        Log.i(TAG, "onErrorResponse: Didn't work");
                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", serverKey);
                params.put("Content-Type", contentType);
                return params;
            }
        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);



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

    private void displayShipCellHidden(TextView TextView){
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            TextView.setBackgroundDrawable(getResources().getDrawable(R.drawable.battle_cell_ship_sunk_x_hidden));
        } else {
            TextView.setBackground(getResources().getDrawable(R.drawable.battle_cell_ship_sunk_x_hidden));
        }
    }
    private void displayWaterCellHidden(TextView TextView){
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            TextView.setBackgroundDrawable(getResources().getDrawable(R.drawable.water_cell_x_hidden));
        } else {
            TextView.setBackground(getResources().getDrawable(R.drawable.water_cell_x_hidden));
        }

    }

    private void displayWidmoShipHidden(TextView TextView){
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            TextView.setBackgroundDrawable(getResources().getDrawable(R.drawable.battle_cell_widmo_ship_x_hidden));
        } else {
            TextView.setBackground(getResources().getDrawable(R.drawable.battle_cell_widmo_ship_x_hidden));
        }
    }

    private void displayBattleCellHidden(TextView TextView){
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            TextView.setBackgroundDrawable(getResources().getDrawable(R.drawable.battle_cell_x_hidden));
        } else {
            TextView.setBackground(getResources().getDrawable(R.drawable.battle_cell_x_hidden));
        }
    }
}
// TODO surrender and leave button