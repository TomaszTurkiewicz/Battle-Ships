package com.example.ships;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ships.classes.BattleFieldForDataBase;
import com.example.ships.classes.Difficulty;
import com.example.ships.classes.FightIndex;
import com.example.ships.classes.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MultiplayerActivity extends AppCompatActivity implements View.OnTouchListener{

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReferenceMy, databaseReferenceFight, databaseReferenceOpponent;
    private String userID;
    private User user = new User();
    private BattleFieldForDataBase battleFieldForDataBaseMy = new BattleFieldForDataBase();
    private BattleFieldForDataBase battleFieldForDataBaseOpponent = new BattleFieldForDataBase();
    private Handler mHandler = new Handler();
    private int deelay = 1000;
    private TextView[][] textViewArrayActivityMultiplayerMe = new TextView[10][10];
    private TextView[] ShipFourMasts = new TextView[4];
    private TextView[] ShipThreeMastsFirst = new TextView[3];
    private TextView[] ShipThreeMastsSecond = new TextView[3];
    private TextView[] ShipTwoMastsFirst = new TextView[2];
    private TextView[] ShipTwoMastsSecond = new TextView[2];
    private TextView[] ShipTwoMastsThird = new TextView[2];
    private TextView[] ShipOneMastsFirst = new TextView[1];
    private TextView[] ShipOneMastsSecond = new TextView[1];
    private TextView[] ShipOneMastsThird = new TextView[1];
    private TextView[] ShipOneMastsFourth = new TextView[1];
    private int shipFourMastsCounter,
            shipThreeMastsCounterFirst, shipThreeMastsCounterSecond,
            shipTwoMastsCounterFirst, shipTwoMastsCounterSecond, shipTwoMastsCounterThird,
            shipOneMastsCounterFirst, shipOneMastsCounterSecond, shipOneMastsCounterThird, shipOneMastsCounterFourth;
    private boolean battleFieldsSet;
    private TextView turnTextView;
    private ImageButton leaveButton;
    private boolean enableTouchListener;
    private TextView tv1,tv2,tv11;
    private GridLayout layout;
    int[]location1 = new int[2];
    int[]location2 = new int[2];
    int[]location11 = new int[2];
    private int height, width;
    int[]locationLayout = new int [2];
    private int battleFieldOpponent[][] = new int[10][10];
    private final static int BATTLE_CELL = 0;
    private final static int WATER = 1;
    private final static int SHIP_RED = 2;
    private final static int SHIP_BROWN = 3;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_multiplayer);
        initializeTextViews();
        leaveButton = findViewById(R.id.leaveMultiplayer);
        turnTextView=findViewById(R.id.turn);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        userID = firebaseUser.getUid();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReferenceMy=firebaseDatabase.getReference("User").child(userID);
        leaveButton.setVisibility(View.GONE);
        enableTouchListener=false;
        battleFieldsSet=false;
        tv1=findViewById(R.id.OpponentMultiplayerCellGame_1x1);
        tv2=findViewById(R.id.OpponentMultiplayerCellGame_1x2);
        tv11=findViewById(R.id.OpponentMultiplayerCellGame_2x1);
        layout = findViewById(R.id.tableLayoutOpponentMultiplayerBattleField);

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

        game.run();

    }

    private void initializeTextViews() {
        textViewArrayActivityMultiplayerMe[0][0]=findViewById(R.id.playerMyMultiplayerCellGame_1x1);
        textViewArrayActivityMultiplayerMe[0][1]=findViewById(R.id.playerMyMultiplayerCellGame_1x2);
        textViewArrayActivityMultiplayerMe[0][2]=findViewById(R.id.playerMyMultiplayerCellGame_1x3);
        textViewArrayActivityMultiplayerMe[0][3]=findViewById(R.id.playerMyMultiplayerCellGame_1x4);
        textViewArrayActivityMultiplayerMe[0][4]=findViewById(R.id.playerMyMultiplayerCellGame_1x5);
        textViewArrayActivityMultiplayerMe[0][5]=findViewById(R.id.playerMyMultiplayerCellGame_1x6);
        textViewArrayActivityMultiplayerMe[0][6]=findViewById(R.id.playerMyMultiplayerCellGame_1x7);
        textViewArrayActivityMultiplayerMe[0][7]=findViewById(R.id.playerMyMultiplayerCellGame_1x8);
        textViewArrayActivityMultiplayerMe[0][8]=findViewById(R.id.playerMyMultiplayerCellGame_1x9);
        textViewArrayActivityMultiplayerMe[0][9]=findViewById(R.id.playerMyMultiplayerCellGame_1x10);

        textViewArrayActivityMultiplayerMe[1][0]=findViewById(R.id.playerMyMultiplayerCellGame_2x1);
        textViewArrayActivityMultiplayerMe[1][1]=findViewById(R.id.playerMyMultiplayerCellGame_2x2);
        textViewArrayActivityMultiplayerMe[1][2]=findViewById(R.id.playerMyMultiplayerCellGame_2x3);
        textViewArrayActivityMultiplayerMe[1][3]=findViewById(R.id.playerMyMultiplayerCellGame_2x4);
        textViewArrayActivityMultiplayerMe[1][4]=findViewById(R.id.playerMyMultiplayerCellGame_2x5);
        textViewArrayActivityMultiplayerMe[1][5]=findViewById(R.id.playerMyMultiplayerCellGame_2x6);
        textViewArrayActivityMultiplayerMe[1][6]=findViewById(R.id.playerMyMultiplayerCellGame_2x7);
        textViewArrayActivityMultiplayerMe[1][7]=findViewById(R.id.playerMyMultiplayerCellGame_2x8);
        textViewArrayActivityMultiplayerMe[1][8]=findViewById(R.id.playerMyMultiplayerCellGame_2x9);
        textViewArrayActivityMultiplayerMe[1][9]=findViewById(R.id.playerMyMultiplayerCellGame_2x10);

        textViewArrayActivityMultiplayerMe[2][0]=findViewById(R.id.playerMyMultiplayerCellGame_3x1);
        textViewArrayActivityMultiplayerMe[2][1]=findViewById(R.id.playerMyMultiplayerCellGame_3x2);
        textViewArrayActivityMultiplayerMe[2][2]=findViewById(R.id.playerMyMultiplayerCellGame_3x3);
        textViewArrayActivityMultiplayerMe[2][3]=findViewById(R.id.playerMyMultiplayerCellGame_3x4);
        textViewArrayActivityMultiplayerMe[2][4]=findViewById(R.id.playerMyMultiplayerCellGame_3x5);
        textViewArrayActivityMultiplayerMe[2][5]=findViewById(R.id.playerMyMultiplayerCellGame_3x6);
        textViewArrayActivityMultiplayerMe[2][6]=findViewById(R.id.playerMyMultiplayerCellGame_3x7);
        textViewArrayActivityMultiplayerMe[2][7]=findViewById(R.id.playerMyMultiplayerCellGame_3x8);
        textViewArrayActivityMultiplayerMe[2][8]=findViewById(R.id.playerMyMultiplayerCellGame_3x9);
        textViewArrayActivityMultiplayerMe[2][9]=findViewById(R.id.playerMyMultiplayerCellGame_3x10);

        textViewArrayActivityMultiplayerMe[3][0]=findViewById(R.id.playerMyMultiplayerCellGame_4x1);
        textViewArrayActivityMultiplayerMe[3][1]=findViewById(R.id.playerMyMultiplayerCellGame_4x2);
        textViewArrayActivityMultiplayerMe[3][2]=findViewById(R.id.playerMyMultiplayerCellGame_4x3);
        textViewArrayActivityMultiplayerMe[3][3]=findViewById(R.id.playerMyMultiplayerCellGame_4x4);
        textViewArrayActivityMultiplayerMe[3][4]=findViewById(R.id.playerMyMultiplayerCellGame_4x5);
        textViewArrayActivityMultiplayerMe[3][5]=findViewById(R.id.playerMyMultiplayerCellGame_4x6);
        textViewArrayActivityMultiplayerMe[3][6]=findViewById(R.id.playerMyMultiplayerCellGame_4x7);
        textViewArrayActivityMultiplayerMe[3][7]=findViewById(R.id.playerMyMultiplayerCellGame_4x8);
        textViewArrayActivityMultiplayerMe[3][8]=findViewById(R.id.playerMyMultiplayerCellGame_4x9);
        textViewArrayActivityMultiplayerMe[3][9]=findViewById(R.id.playerMyMultiplayerCellGame_4x10);

        textViewArrayActivityMultiplayerMe[4][0]=findViewById(R.id.playerMyMultiplayerCellGame_5x1);
        textViewArrayActivityMultiplayerMe[4][1]=findViewById(R.id.playerMyMultiplayerCellGame_5x2);
        textViewArrayActivityMultiplayerMe[4][2]=findViewById(R.id.playerMyMultiplayerCellGame_5x3);
        textViewArrayActivityMultiplayerMe[4][3]=findViewById(R.id.playerMyMultiplayerCellGame_5x4);
        textViewArrayActivityMultiplayerMe[4][4]=findViewById(R.id.playerMyMultiplayerCellGame_5x5);
        textViewArrayActivityMultiplayerMe[4][5]=findViewById(R.id.playerMyMultiplayerCellGame_5x6);
        textViewArrayActivityMultiplayerMe[4][6]=findViewById(R.id.playerMyMultiplayerCellGame_5x7);
        textViewArrayActivityMultiplayerMe[4][7]=findViewById(R.id.playerMyMultiplayerCellGame_5x8);
        textViewArrayActivityMultiplayerMe[4][8]=findViewById(R.id.playerMyMultiplayerCellGame_5x9);
        textViewArrayActivityMultiplayerMe[4][9]=findViewById(R.id.playerMyMultiplayerCellGame_5x10);

        textViewArrayActivityMultiplayerMe[5][0]=findViewById(R.id.playerMyMultiplayerCellGame_6x1);
        textViewArrayActivityMultiplayerMe[5][1]=findViewById(R.id.playerMyMultiplayerCellGame_6x2);
        textViewArrayActivityMultiplayerMe[5][2]=findViewById(R.id.playerMyMultiplayerCellGame_6x3);
        textViewArrayActivityMultiplayerMe[5][3]=findViewById(R.id.playerMyMultiplayerCellGame_6x4);
        textViewArrayActivityMultiplayerMe[5][4]=findViewById(R.id.playerMyMultiplayerCellGame_6x5);
        textViewArrayActivityMultiplayerMe[5][5]=findViewById(R.id.playerMyMultiplayerCellGame_6x6);
        textViewArrayActivityMultiplayerMe[5][6]=findViewById(R.id.playerMyMultiplayerCellGame_6x7);
        textViewArrayActivityMultiplayerMe[5][7]=findViewById(R.id.playerMyMultiplayerCellGame_6x8);
        textViewArrayActivityMultiplayerMe[5][8]=findViewById(R.id.playerMyMultiplayerCellGame_6x9);
        textViewArrayActivityMultiplayerMe[5][9]=findViewById(R.id.playerMyMultiplayerCellGame_6x10);

        textViewArrayActivityMultiplayerMe[6][0]=findViewById(R.id.playerMyMultiplayerCellGame_7x1);
        textViewArrayActivityMultiplayerMe[6][1]=findViewById(R.id.playerMyMultiplayerCellGame_7x2);
        textViewArrayActivityMultiplayerMe[6][2]=findViewById(R.id.playerMyMultiplayerCellGame_7x3);
        textViewArrayActivityMultiplayerMe[6][3]=findViewById(R.id.playerMyMultiplayerCellGame_7x4);
        textViewArrayActivityMultiplayerMe[6][4]=findViewById(R.id.playerMyMultiplayerCellGame_7x5);
        textViewArrayActivityMultiplayerMe[6][5]=findViewById(R.id.playerMyMultiplayerCellGame_7x6);
        textViewArrayActivityMultiplayerMe[6][6]=findViewById(R.id.playerMyMultiplayerCellGame_7x7);
        textViewArrayActivityMultiplayerMe[6][7]=findViewById(R.id.playerMyMultiplayerCellGame_7x8);
        textViewArrayActivityMultiplayerMe[6][8]=findViewById(R.id.playerMyMultiplayerCellGame_7x9);
        textViewArrayActivityMultiplayerMe[6][9]=findViewById(R.id.playerMyMultiplayerCellGame_7x10);

        textViewArrayActivityMultiplayerMe[7][0]=findViewById(R.id.playerMyMultiplayerCellGame_8x1);
        textViewArrayActivityMultiplayerMe[7][1]=findViewById(R.id.playerMyMultiplayerCellGame_8x2);
        textViewArrayActivityMultiplayerMe[7][2]=findViewById(R.id.playerMyMultiplayerCellGame_8x3);
        textViewArrayActivityMultiplayerMe[7][3]=findViewById(R.id.playerMyMultiplayerCellGame_8x4);
        textViewArrayActivityMultiplayerMe[7][4]=findViewById(R.id.playerMyMultiplayerCellGame_8x5);
        textViewArrayActivityMultiplayerMe[7][5]=findViewById(R.id.playerMyMultiplayerCellGame_8x6);
        textViewArrayActivityMultiplayerMe[7][6]=findViewById(R.id.playerMyMultiplayerCellGame_8x7);
        textViewArrayActivityMultiplayerMe[7][7]=findViewById(R.id.playerMyMultiplayerCellGame_8x8);
        textViewArrayActivityMultiplayerMe[7][8]=findViewById(R.id.playerMyMultiplayerCellGame_8x9);
        textViewArrayActivityMultiplayerMe[7][9]=findViewById(R.id.playerMyMultiplayerCellGame_8x10);

        textViewArrayActivityMultiplayerMe[8][0]=findViewById(R.id.playerMyMultiplayerCellGame_9x1);
        textViewArrayActivityMultiplayerMe[8][1]=findViewById(R.id.playerMyMultiplayerCellGame_9x2);
        textViewArrayActivityMultiplayerMe[8][2]=findViewById(R.id.playerMyMultiplayerCellGame_9x3);
        textViewArrayActivityMultiplayerMe[8][3]=findViewById(R.id.playerMyMultiplayerCellGame_9x4);
        textViewArrayActivityMultiplayerMe[8][4]=findViewById(R.id.playerMyMultiplayerCellGame_9x5);
        textViewArrayActivityMultiplayerMe[8][5]=findViewById(R.id.playerMyMultiplayerCellGame_9x6);
        textViewArrayActivityMultiplayerMe[8][6]=findViewById(R.id.playerMyMultiplayerCellGame_9x7);
        textViewArrayActivityMultiplayerMe[8][7]=findViewById(R.id.playerMyMultiplayerCellGame_9x8);
        textViewArrayActivityMultiplayerMe[8][8]=findViewById(R.id.playerMyMultiplayerCellGame_9x9);
        textViewArrayActivityMultiplayerMe[8][9]=findViewById(R.id.playerMyMultiplayerCellGame_9x10);

        textViewArrayActivityMultiplayerMe[9][0]=findViewById(R.id.playerMyMultiplayerCellGame_10x1);
        textViewArrayActivityMultiplayerMe[9][1]=findViewById(R.id.playerMyMultiplayerCellGame_10x2);
        textViewArrayActivityMultiplayerMe[9][2]=findViewById(R.id.playerMyMultiplayerCellGame_10x3);
        textViewArrayActivityMultiplayerMe[9][3]=findViewById(R.id.playerMyMultiplayerCellGame_10x4);
        textViewArrayActivityMultiplayerMe[9][4]=findViewById(R.id.playerMyMultiplayerCellGame_10x5);
        textViewArrayActivityMultiplayerMe[9][5]=findViewById(R.id.playerMyMultiplayerCellGame_10x6);
        textViewArrayActivityMultiplayerMe[9][6]=findViewById(R.id.playerMyMultiplayerCellGame_10x7);
        textViewArrayActivityMultiplayerMe[9][7]=findViewById(R.id.playerMyMultiplayerCellGame_10x8);
        textViewArrayActivityMultiplayerMe[9][8]=findViewById(R.id.playerMyMultiplayerCellGame_10x9);
        textViewArrayActivityMultiplayerMe[9][9]=findViewById(R.id.playerMyMultiplayerCellGame_10x10);

        ShipFourMasts[0]=findViewById(R.id.FourCellShip1MultiplayerActivity);
        ShipFourMasts[1]=findViewById(R.id.FourCellShip2MultiplayerActivity);
        ShipFourMasts[2]=findViewById(R.id.FourCellShip3MultiplayerActivity);
        ShipFourMasts[3]=findViewById(R.id.FourCellShip4MultiplayerActivity);

        ShipThreeMastsFirst[0]=findViewById(R.id.ThreeCellShip11MultiplayerActivity);
        ShipThreeMastsFirst[1]=findViewById(R.id.ThreeCellShip12MultiplayerActivity);
        ShipThreeMastsFirst[2]=findViewById(R.id.ThreeCellShip13MultiplayerActivity);
        ShipThreeMastsSecond[0]=findViewById(R.id.ThreeCellShip21MultiplayerActivity);
        ShipThreeMastsSecond[1]=findViewById(R.id.ThreeCellShip22MultiplayerActivity);
        ShipThreeMastsSecond[2]=findViewById(R.id.ThreeCellShip23MultiplayerActivity);

        ShipTwoMastsFirst[0]=findViewById(R.id.TwoCellShip11MultiplayerActivity);
        ShipTwoMastsFirst[1]=findViewById(R.id.TwoCellShip12MultiplayerActivity);
        ShipTwoMastsSecond[0]=findViewById(R.id.TwoCellShip21MultiplayerActivity);
        ShipTwoMastsSecond[1]=findViewById(R.id.TwoCellShip22MultiplayerActivity);
        ShipTwoMastsThird[0]=findViewById(R.id.TwoCellShip31MultiplayerActivity);
        ShipTwoMastsThird[1]=findViewById(R.id.TwoCellShip32MultiplayerActivity);

        ShipOneMastsFirst[0]=findViewById(R.id.OneCellShip1MultiplayerActivity);
        ShipOneMastsSecond[0]=findViewById(R.id.OneCellShip2MultiplayerActivity);
        ShipOneMastsThird[0]=findViewById(R.id.OneCellShip3MultiplayerActivity);
        ShipOneMastsFourth[0]=findViewById(R.id.OneCellShip4MultiplayerActivity);
    }

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
                    Intent intent = new Intent(MultiplayerActivity.this, MainActivity.class);
                    startActivity(intent);
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



                    // TODO napierdalaj przeciwnika i zapisuj do bazy.
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
                        //TODO wyświtlanie moje pola bitwy
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

    private void hideBattleFiledAvailableMy() {
        for(int i =0;i<10;i++){
            for(int j = 0; j<10;j++){
                //jest statek i został trafiony
                if(battleFieldForDataBaseMy.showBattleField().getBattleField(i,j).isShip()
                        && battleFieldForDataBaseMy.showBattleField().getBattleField(i,j).isHit()){
                    displayShipCellHidden(textViewArrayActivityMultiplayerMe,i,j);
                }

                // woda i została trafiony
                else if(!battleFieldForDataBaseMy.showBattleField().getBattleField(i,j).isShip()
                        && battleFieldForDataBaseMy.showBattleField().getBattleField(i,j).isHit()){
                    displayWaterCellHidden(textViewArrayActivityMultiplayerMe,i,j);
                }

                // jest statek i nie został trafiony
                else if(battleFieldForDataBaseMy.showBattleField().getBattleField(i,j).isShip()
                        &&!battleFieldForDataBaseMy.showBattleField().getBattleField(i,j).isHit()){
                    displayWidmoShipHidden(textViewArrayActivityMultiplayerMe,i,j);
                }
                // nie ma statku i nie został trafiony
                else if(!battleFieldForDataBaseMy.showBattleField().getBattleField(i,j).isShip()
                        &&!battleFieldForDataBaseMy.showBattleField().getBattleField(i,j).isHit()){
                    displayBattleCellHidden(textViewArrayActivityMultiplayerMe,i,j);
                }

                else;
            }
        }
    }


    private void createFields() {
        turnTextView.setText("WAITING FOR OPPONENT");
        databaseReferenceMy.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);

                if(user.getIndex().getOpponent().isEmpty()){
                    Intent intent = new Intent(MultiplayerActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {

                    databaseReferenceOpponent=firebaseDatabase.getReference("User").child(user.getIndex().getOpponent());

                    databaseReferenceFight = firebaseDatabase.getReference("Battle").child(user.getIndex().getGameIndex());
                    leaveButton.setVisibility(View.VISIBLE);
                    databaseReferenceFight.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (!dataSnapshot.exists()) {
                                battleFieldForDataBaseMy.create();
                                int noOfGames = user.getNoOfGames();
                                noOfGames = noOfGames+1;
                                user.setNoOfGames(noOfGames);

                                chooseDifficulty();

                                databaseReferenceMy.setValue(user);
                                databaseReferenceFight.child(user.getId()).setValue(battleFieldForDataBaseMy);
                                databaseReferenceFight.child(user.getIndex().getOpponent()).setValue(battleFieldForDataBaseOpponent);
                                databaseReferenceFight.child("turn").setValue(user.getId());
                                databaseReferenceFight.child("winner").setValue("");
                                mHandler.postDelayed(game, deelay);
                            } else {
                                battleFieldForDataBaseMy = dataSnapshot.child(user.getId()).getValue(BattleFieldForDataBase.class);

                                if (!battleFieldForDataBaseMy.isCreated()) {
                                    battleFieldForDataBaseMy.create();

                                    databaseReferenceFight.child(user.getId()).setValue(battleFieldForDataBaseMy);
                                    int noOfGames = user.getNoOfGames();
                                    noOfGames = noOfGames+1;
                                    user.setNoOfGames(noOfGames);
                                    databaseReferenceMy.setValue(user);

                                    chooseDifficulty();



                                }else{
                                    battleFieldForDataBaseMy.listToField();

                                }
                                // pokaż moje statki
                                showMyBattleField();
                                battleFieldForDataBaseOpponent = dataSnapshot.child(user.getIndex().getOpponent()).getValue(BattleFieldForDataBase.class);
                                battleFieldForDataBaseOpponent.listToField();

                                if (battleFieldForDataBaseMy.isCreated() && battleFieldForDataBaseOpponent.isCreated()&&
                                battleFieldForDataBaseMy.getDifficulty().isSet()&&battleFieldForDataBaseOpponent.getDifficulty().isSet()) {
                                    battleFieldsSet=true;

                                    initializeOpponentArrayBattleField();

                                    checkShipCounters();

                                    showOpponentBattleField();

                                    updateShipsHit();


                                    mHandler.postDelayed(game, deelay);

                                } else {
                                    mHandler.postDelayed(game, deelay);
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

    private void showOpponentBattleField() {
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

    private void chooseDifficulty() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MultiplayerActivity.this);
        builder.setCancelable(true);
        builder.setTitle("Difficulty");
        builder.setMessage("choose game difficulty");
        builder.setPositiveButton("NORMAL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                battleFieldForDataBaseMy.setDifficulty(new Difficulty(false,true));
                databaseReferenceFight.child(user.getId()).setValue(battleFieldForDataBaseMy);
            }
        });
        builder.setNegativeButton("EASY", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                    battleFieldForDataBaseMy.setDifficulty(new Difficulty(true,true));
                databaseReferenceFight.child(user.getId()).setValue(battleFieldForDataBaseMy);
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
                    displayShipCell(textViewArrayActivityMultiplayerMe[i][j]);
                }

                // woda i została trafiony
                else if(!battleFieldForDataBaseMy.showBattleField().getBattleField(i,j).isShip()
                        &&battleFieldForDataBaseMy.showBattleField().getBattleField(i,j).isHit()){
                    displayWaterCell(textViewArrayActivityMultiplayerMe[i][j]);
                }

                // jest statek i nie został trafiony
                else if(battleFieldForDataBaseMy.showBattleField().getBattleField(i,j).isShip()
                        &&!battleFieldForDataBaseMy.showBattleField().getBattleField(i,j).isHit()){
                    displayWidmoShip(textViewArrayActivityMultiplayerMe[i][j]);
                }
                // nie ma statku i nie został trafiony
                else if(!battleFieldForDataBaseMy.showBattleField().getBattleField(i,j).isShip()
                        &&!battleFieldForDataBaseMy.showBattleField().getBattleField(i,j).isHit()){
                    displayBattleCell(textViewArrayActivityMultiplayerMe[i][j]);
                }
                else;
            }
        }
    }

    private void displayWidmoShip(TextView textView) {
        textView.setBackground(getResources().getDrawable(R.drawable.widmo_ship_cell));
    }

    private void displayWaterCell(TextView textView) {
        textView.setBackground(getResources().getDrawable(R.drawable.water_cell));
    }


    private void displayBattleCell(TextView textView) {
        textView.setBackground(getResources().getDrawable(R.drawable.battle_cell));
    }

    private void displayShipCell(TextView textView) {
            textView.setBackground(getResources().getDrawable(R.drawable.ship_cell));
    }


    private void updateShipsHit() {

        if(shipFourMastsCounter==0){
        }else{
            if(battleFieldForDataBaseMy.getDifficulty().isEasy()){
                for(int i=0;i<shipFourMastsCounter;i++){
                    ShipFourMasts[i].setBackground(getDrawable(R.drawable.ship_cell));
                }
            }else{
                if(shipFourMastsCounter==4){
                    for(int i=0;i<shipFourMastsCounter;i++){
                        ShipFourMasts[i].setBackground(getDrawable(R.drawable.ship_cell));
                    }
                }
            }
        }

        if(shipThreeMastsCounterFirst==0){
        }else{
            if(battleFieldForDataBaseMy.getDifficulty().isEasy()){
                for(int i=0;i<shipThreeMastsCounterFirst;i++){
                    ShipThreeMastsFirst[i].setBackground(getDrawable(R.drawable.ship_cell));
                }
            }else{
                if(shipThreeMastsCounterFirst==3){
                    for(int i=0;i<shipThreeMastsCounterFirst;i++){
                        ShipThreeMastsFirst[i].setBackground(getDrawable(R.drawable.ship_cell));
                    }
                }
            }
        }

        if(shipThreeMastsCounterSecond==0){
        }else{
            if(battleFieldForDataBaseMy.getDifficulty().isEasy()){
                for(int i=0;i<shipThreeMastsCounterSecond;i++){
                    ShipThreeMastsSecond[i].setBackground(getDrawable(R.drawable.ship_cell));
                }
            }else{
                if(shipThreeMastsCounterSecond==3){
                    for(int i=0;i<shipThreeMastsCounterSecond;i++){
                        ShipThreeMastsSecond[i].setBackground(getDrawable(R.drawable.ship_cell));
                    }
                }
            }
        }

        if(shipTwoMastsCounterFirst==0){
        }else{
            if(battleFieldForDataBaseMy.getDifficulty().isEasy()){
                for(int i=0;i<shipTwoMastsCounterFirst;i++){
                    ShipTwoMastsFirst[i].setBackground(getDrawable(R.drawable.ship_cell));
                }
            }else{
                if(shipTwoMastsCounterFirst==2){
                    for(int i=0;i<shipTwoMastsCounterFirst;i++){
                        ShipTwoMastsFirst[i].setBackground(getDrawable(R.drawable.ship_cell));
                    }
                }
            }
        }

        if(shipTwoMastsCounterSecond==0){
        }else{
            if(battleFieldForDataBaseMy.getDifficulty().isEasy()){
                for(int i=0;i<shipTwoMastsCounterSecond;i++){
                    ShipTwoMastsSecond[i].setBackground(getDrawable(R.drawable.ship_cell));
                }
            }else{
                if(shipTwoMastsCounterSecond==2){
                    for(int i=0;i<shipTwoMastsCounterSecond;i++){
                        ShipTwoMastsSecond[i].setBackground(getDrawable(R.drawable.ship_cell));
                    }
                }
            }
        }

        if(shipTwoMastsCounterThird==0){
        }else{
            if(battleFieldForDataBaseMy.getDifficulty().isEasy()){
                for(int i=0;i<shipTwoMastsCounterThird;i++){
                    ShipTwoMastsThird[i].setBackground(getDrawable(R.drawable.ship_cell));
                }
            }else{
                if(shipTwoMastsCounterThird==2){
                    for(int i=0;i<shipTwoMastsCounterThird;i++){
                        ShipTwoMastsThird[i].setBackground(getDrawable(R.drawable.ship_cell));
                    }
                }
            }
        }

        if(shipOneMastsCounterFirst==0){
        }else{
            ShipOneMastsFirst[shipOneMastsCounterFirst-1].setBackground(getDrawable(R.drawable.ship_cell));
        }

        if(shipOneMastsCounterSecond==0){
        }else{
            ShipOneMastsSecond[shipOneMastsCounterSecond-1].setBackground(getDrawable(R.drawable.ship_cell));
        }

        if(shipOneMastsCounterThird==0){
        }else{
            ShipOneMastsThird[shipOneMastsCounterThird-1].setBackground(getDrawable(R.drawable.ship_cell));
        }

        if(shipOneMastsCounterFourth==0){
        }else{
            ShipOneMastsFourth[shipOneMastsCounterFourth-1].setBackground(getDrawable(R.drawable.ship_cell));
        }
    }

    public void leaveMultiplayer(View view) {

        databaseReferenceOpponent.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User opponent=dataSnapshot.getValue(User.class);
                int score = opponent.getScore();
                score = score+50;
                opponent.setScore(score);
                opponent.setIndex(new FightIndex());
                databaseReferenceOpponent.setValue(opponent);
                user.setIndex(new FightIndex());
                databaseReferenceMy.setValue(user);
                databaseReferenceFight.removeValue();
                Intent intent = new Intent(MultiplayerActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
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
                        showOpponentBattleField();
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
        layout.invalidate();
        return true;




    }

    private void hitCell(int x, int y) {

        databaseReferenceMy.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                if(user.getIndex().getOpponent().isEmpty()){
                    Intent intent = new Intent(MultiplayerActivity.this, MainActivity.class);
                    startActivity(intent);
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
                        mHandler.postDelayed(game,deelay);

                    }



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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
}
