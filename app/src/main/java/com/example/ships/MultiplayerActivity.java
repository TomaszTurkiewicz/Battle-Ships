package com.example.ships;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
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

public class MultiplayerActivity extends AppCompatActivity {

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
    private TextView[][] textViewArrayActivityMultiplayerOpponent = new TextView[10][10];
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        battleFieldsSet=false;
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

        textViewArrayActivityMultiplayerOpponent[0][0]=findViewById(R.id.OpponentMultiplayerCellGame_1x1);
        textViewArrayActivityMultiplayerOpponent[0][1]=findViewById(R.id.OpponentMultiplayerCellGame_1x2);
        textViewArrayActivityMultiplayerOpponent[0][2]=findViewById(R.id.OpponentMultiplayerCellGame_1x3);
        textViewArrayActivityMultiplayerOpponent[0][3]=findViewById(R.id.OpponentMultiplayerCellGame_1x4);
        textViewArrayActivityMultiplayerOpponent[0][4]=findViewById(R.id.OpponentMultiplayerCellGame_1x5);
        textViewArrayActivityMultiplayerOpponent[0][5]=findViewById(R.id.OpponentMultiplayerCellGame_1x6);
        textViewArrayActivityMultiplayerOpponent[0][6]=findViewById(R.id.OpponentMultiplayerCellGame_1x7);
        textViewArrayActivityMultiplayerOpponent[0][7]=findViewById(R.id.OpponentMultiplayerCellGame_1x8);
        textViewArrayActivityMultiplayerOpponent[0][8]=findViewById(R.id.OpponentMultiplayerCellGame_1x9);
        textViewArrayActivityMultiplayerOpponent[0][9]=findViewById(R.id.OpponentMultiplayerCellGame_1x10);

        textViewArrayActivityMultiplayerOpponent[1][0]=findViewById(R.id.OpponentMultiplayerCellGame_2x1);
        textViewArrayActivityMultiplayerOpponent[1][1]=findViewById(R.id.OpponentMultiplayerCellGame_2x2);
        textViewArrayActivityMultiplayerOpponent[1][2]=findViewById(R.id.OpponentMultiplayerCellGame_2x3);
        textViewArrayActivityMultiplayerOpponent[1][3]=findViewById(R.id.OpponentMultiplayerCellGame_2x4);
        textViewArrayActivityMultiplayerOpponent[1][4]=findViewById(R.id.OpponentMultiplayerCellGame_2x5);
        textViewArrayActivityMultiplayerOpponent[1][5]=findViewById(R.id.OpponentMultiplayerCellGame_2x6);
        textViewArrayActivityMultiplayerOpponent[1][6]=findViewById(R.id.OpponentMultiplayerCellGame_2x7);
        textViewArrayActivityMultiplayerOpponent[1][7]=findViewById(R.id.OpponentMultiplayerCellGame_2x8);
        textViewArrayActivityMultiplayerOpponent[1][8]=findViewById(R.id.OpponentMultiplayerCellGame_2x9);
        textViewArrayActivityMultiplayerOpponent[1][9]=findViewById(R.id.OpponentMultiplayerCellGame_2x10);

        textViewArrayActivityMultiplayerOpponent[2][0]=findViewById(R.id.OpponentMultiplayerCellGame_3x1);
        textViewArrayActivityMultiplayerOpponent[2][1]=findViewById(R.id.OpponentMultiplayerCellGame_3x2);
        textViewArrayActivityMultiplayerOpponent[2][2]=findViewById(R.id.OpponentMultiplayerCellGame_3x3);
        textViewArrayActivityMultiplayerOpponent[2][3]=findViewById(R.id.OpponentMultiplayerCellGame_3x4);
        textViewArrayActivityMultiplayerOpponent[2][4]=findViewById(R.id.OpponentMultiplayerCellGame_3x5);
        textViewArrayActivityMultiplayerOpponent[2][5]=findViewById(R.id.OpponentMultiplayerCellGame_3x6);
        textViewArrayActivityMultiplayerOpponent[2][6]=findViewById(R.id.OpponentMultiplayerCellGame_3x7);
        textViewArrayActivityMultiplayerOpponent[2][7]=findViewById(R.id.OpponentMultiplayerCellGame_3x8);
        textViewArrayActivityMultiplayerOpponent[2][8]=findViewById(R.id.OpponentMultiplayerCellGame_3x9);
        textViewArrayActivityMultiplayerOpponent[2][9]=findViewById(R.id.OpponentMultiplayerCellGame_3x10);

        textViewArrayActivityMultiplayerOpponent[3][0]=findViewById(R.id.OpponentMultiplayerCellGame_4x1);
        textViewArrayActivityMultiplayerOpponent[3][1]=findViewById(R.id.OpponentMultiplayerCellGame_4x2);
        textViewArrayActivityMultiplayerOpponent[3][2]=findViewById(R.id.OpponentMultiplayerCellGame_4x3);
        textViewArrayActivityMultiplayerOpponent[3][3]=findViewById(R.id.OpponentMultiplayerCellGame_4x4);
        textViewArrayActivityMultiplayerOpponent[3][4]=findViewById(R.id.OpponentMultiplayerCellGame_4x5);
        textViewArrayActivityMultiplayerOpponent[3][5]=findViewById(R.id.OpponentMultiplayerCellGame_4x6);
        textViewArrayActivityMultiplayerOpponent[3][6]=findViewById(R.id.OpponentMultiplayerCellGame_4x7);
        textViewArrayActivityMultiplayerOpponent[3][7]=findViewById(R.id.OpponentMultiplayerCellGame_4x8);
        textViewArrayActivityMultiplayerOpponent[3][8]=findViewById(R.id.OpponentMultiplayerCellGame_4x9);
        textViewArrayActivityMultiplayerOpponent[3][9]=findViewById(R.id.OpponentMultiplayerCellGame_4x10);

        textViewArrayActivityMultiplayerOpponent[4][0]=findViewById(R.id.OpponentMultiplayerCellGame_5x1);
        textViewArrayActivityMultiplayerOpponent[4][1]=findViewById(R.id.OpponentMultiplayerCellGame_5x2);
        textViewArrayActivityMultiplayerOpponent[4][2]=findViewById(R.id.OpponentMultiplayerCellGame_5x3);
        textViewArrayActivityMultiplayerOpponent[4][3]=findViewById(R.id.OpponentMultiplayerCellGame_5x4);
        textViewArrayActivityMultiplayerOpponent[4][4]=findViewById(R.id.OpponentMultiplayerCellGame_5x5);
        textViewArrayActivityMultiplayerOpponent[4][5]=findViewById(R.id.OpponentMultiplayerCellGame_5x6);
        textViewArrayActivityMultiplayerOpponent[4][6]=findViewById(R.id.OpponentMultiplayerCellGame_5x7);
        textViewArrayActivityMultiplayerOpponent[4][7]=findViewById(R.id.OpponentMultiplayerCellGame_5x8);
        textViewArrayActivityMultiplayerOpponent[4][8]=findViewById(R.id.OpponentMultiplayerCellGame_5x9);
        textViewArrayActivityMultiplayerOpponent[4][9]=findViewById(R.id.OpponentMultiplayerCellGame_5x10);

        textViewArrayActivityMultiplayerOpponent[5][0]=findViewById(R.id.OpponentMultiplayerCellGame_6x1);
        textViewArrayActivityMultiplayerOpponent[5][1]=findViewById(R.id.OpponentMultiplayerCellGame_6x2);
        textViewArrayActivityMultiplayerOpponent[5][2]=findViewById(R.id.OpponentMultiplayerCellGame_6x3);
        textViewArrayActivityMultiplayerOpponent[5][3]=findViewById(R.id.OpponentMultiplayerCellGame_6x4);
        textViewArrayActivityMultiplayerOpponent[5][4]=findViewById(R.id.OpponentMultiplayerCellGame_6x5);
        textViewArrayActivityMultiplayerOpponent[5][5]=findViewById(R.id.OpponentMultiplayerCellGame_6x6);
        textViewArrayActivityMultiplayerOpponent[5][6]=findViewById(R.id.OpponentMultiplayerCellGame_6x7);
        textViewArrayActivityMultiplayerOpponent[5][7]=findViewById(R.id.OpponentMultiplayerCellGame_6x8);
        textViewArrayActivityMultiplayerOpponent[5][8]=findViewById(R.id.OpponentMultiplayerCellGame_6x9);
        textViewArrayActivityMultiplayerOpponent[5][9]=findViewById(R.id.OpponentMultiplayerCellGame_6x10);

        textViewArrayActivityMultiplayerOpponent[6][0]=findViewById(R.id.OpponentMultiplayerCellGame_7x1);
        textViewArrayActivityMultiplayerOpponent[6][1]=findViewById(R.id.OpponentMultiplayerCellGame_7x2);
        textViewArrayActivityMultiplayerOpponent[6][2]=findViewById(R.id.OpponentMultiplayerCellGame_7x3);
        textViewArrayActivityMultiplayerOpponent[6][3]=findViewById(R.id.OpponentMultiplayerCellGame_7x4);
        textViewArrayActivityMultiplayerOpponent[6][4]=findViewById(R.id.OpponentMultiplayerCellGame_7x5);
        textViewArrayActivityMultiplayerOpponent[6][5]=findViewById(R.id.OpponentMultiplayerCellGame_7x6);
        textViewArrayActivityMultiplayerOpponent[6][6]=findViewById(R.id.OpponentMultiplayerCellGame_7x7);
        textViewArrayActivityMultiplayerOpponent[6][7]=findViewById(R.id.OpponentMultiplayerCellGame_7x8);
        textViewArrayActivityMultiplayerOpponent[6][8]=findViewById(R.id.OpponentMultiplayerCellGame_7x9);
        textViewArrayActivityMultiplayerOpponent[6][9]=findViewById(R.id.OpponentMultiplayerCellGame_7x10);

        textViewArrayActivityMultiplayerOpponent[7][0]=findViewById(R.id.OpponentMultiplayerCellGame_8x1);
        textViewArrayActivityMultiplayerOpponent[7][1]=findViewById(R.id.OpponentMultiplayerCellGame_8x2);
        textViewArrayActivityMultiplayerOpponent[7][2]=findViewById(R.id.OpponentMultiplayerCellGame_8x3);
        textViewArrayActivityMultiplayerOpponent[7][3]=findViewById(R.id.OpponentMultiplayerCellGame_8x4);
        textViewArrayActivityMultiplayerOpponent[7][4]=findViewById(R.id.OpponentMultiplayerCellGame_8x5);
        textViewArrayActivityMultiplayerOpponent[7][5]=findViewById(R.id.OpponentMultiplayerCellGame_8x6);
        textViewArrayActivityMultiplayerOpponent[7][6]=findViewById(R.id.OpponentMultiplayerCellGame_8x7);
        textViewArrayActivityMultiplayerOpponent[7][7]=findViewById(R.id.OpponentMultiplayerCellGame_8x8);
        textViewArrayActivityMultiplayerOpponent[7][8]=findViewById(R.id.OpponentMultiplayerCellGame_8x9);
        textViewArrayActivityMultiplayerOpponent[7][9]=findViewById(R.id.OpponentMultiplayerCellGame_8x10);

        textViewArrayActivityMultiplayerOpponent[8][0]=findViewById(R.id.OpponentMultiplayerCellGame_9x1);
        textViewArrayActivityMultiplayerOpponent[8][1]=findViewById(R.id.OpponentMultiplayerCellGame_9x2);
        textViewArrayActivityMultiplayerOpponent[8][2]=findViewById(R.id.OpponentMultiplayerCellGame_9x3);
        textViewArrayActivityMultiplayerOpponent[8][3]=findViewById(R.id.OpponentMultiplayerCellGame_9x4);
        textViewArrayActivityMultiplayerOpponent[8][4]=findViewById(R.id.OpponentMultiplayerCellGame_9x5);
        textViewArrayActivityMultiplayerOpponent[8][5]=findViewById(R.id.OpponentMultiplayerCellGame_9x6);
        textViewArrayActivityMultiplayerOpponent[8][6]=findViewById(R.id.OpponentMultiplayerCellGame_9x7);
        textViewArrayActivityMultiplayerOpponent[8][7]=findViewById(R.id.OpponentMultiplayerCellGame_9x8);
        textViewArrayActivityMultiplayerOpponent[8][8]=findViewById(R.id.OpponentMultiplayerCellGame_9x9);
        textViewArrayActivityMultiplayerOpponent[8][9]=findViewById(R.id.OpponentMultiplayerCellGame_9x10);

        textViewArrayActivityMultiplayerOpponent[9][0]=findViewById(R.id.OpponentMultiplayerCellGame_10x1);
        textViewArrayActivityMultiplayerOpponent[9][1]=findViewById(R.id.OpponentMultiplayerCellGame_10x2);
        textViewArrayActivityMultiplayerOpponent[9][2]=findViewById(R.id.OpponentMultiplayerCellGame_10x3);
        textViewArrayActivityMultiplayerOpponent[9][3]=findViewById(R.id.OpponentMultiplayerCellGame_10x4);
        textViewArrayActivityMultiplayerOpponent[9][4]=findViewById(R.id.OpponentMultiplayerCellGame_10x5);
        textViewArrayActivityMultiplayerOpponent[9][5]=findViewById(R.id.OpponentMultiplayerCellGame_10x6);
        textViewArrayActivityMultiplayerOpponent[9][6]=findViewById(R.id.OpponentMultiplayerCellGame_10x7);
        textViewArrayActivityMultiplayerOpponent[9][7]=findViewById(R.id.OpponentMultiplayerCellGame_10x8);
        textViewArrayActivityMultiplayerOpponent[9][8]=findViewById(R.id.OpponentMultiplayerCellGame_10x9);
        textViewArrayActivityMultiplayerOpponent[9][9]=findViewById(R.id.OpponentMultiplayerCellGame_10x10);

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

        showShipsHit();
        databaseReferenceFight.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String turn = (String) dataSnapshot.child("turn").getValue();
                if(user.getId().equals(turn)){
                    turnTextView.setText("MY MOVE");
                    battleFieldForDataBaseMy = dataSnapshot.child(user.getId()).getValue(BattleFieldForDataBase.class);
                    battleFieldForDataBaseMy.listToField();



                    readClicable();
                    showMyBattleField();
                    showOpponentBattleField();

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
                        showOpponentBattleField();
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

    private void showShipsHit() {

    }

    private void disableClickable() {
        for(int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                textViewArrayActivityMultiplayerOpponent[i][j].setClickable(false);
            }
        }
    }

    private void readClicable() {
        for(int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                textViewArrayActivityMultiplayerOpponent[i][j]
                        .setClickable(!battleFieldForDataBaseOpponent
                                .showBattleField()
                                .getBattleField(i,j)
                                .isHit());
            }
        }
    }



    private void displayShipCellRed(TextView textView) {
        textView.setBackground(getResources().getDrawable(R.drawable.red_ship));
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
                                disableClickable();

                                if (battleFieldForDataBaseMy.isCreated() && battleFieldForDataBaseOpponent.isCreated()&&
                                battleFieldForDataBaseMy.getDifficulty().isSet()&&battleFieldForDataBaseOpponent.getDifficulty().isSet()) {
                                    battleFieldsSet=true;
                                    checkShipCounters();
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
                    if(numberOfMasts==4){
                        shipFourMastsCounter++;
                    }else if(numberOfMasts==3){
                        if(shipNumber==1){
                            shipThreeMastsCounterFirst++;
                        }else if(shipNumber==2){
                            shipThreeMastsCounterSecond++;
                        }else;

                    }else if(numberOfMasts==2){
                        if(shipNumber==1){
                            shipTwoMastsCounterFirst++;
                        }else if(shipNumber==2){
                            shipTwoMastsCounterSecond++;
                        }else if(shipNumber==3){
                            shipTwoMastsCounterThird++;
                        }else;


                    }else if(numberOfMasts==1){
                        if(shipNumber==1){
                            shipOneMastsCounterFirst++;
                        }else if(shipNumber==2){
                            shipOneMastsCounterSecond++;
                        }else if(shipNumber==3){
                            shipOneMastsCounterThird++;
                        }else if(shipNumber==4){
                            shipOneMastsCounterFourth++;
                        }else;


                    }else;
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

    private void showOpponentBattleField() {
        for(int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                //jest statek i został trafiony
                if(battleFieldForDataBaseOpponent.showBattleField().getBattleField(i,j).isShip()
                        &&battleFieldForDataBaseOpponent.showBattleField().getBattleField(i,j).isHit()){

                    if(zatopiony(battleFieldForDataBaseOpponent.showBattleField().getBattleField(i,j).getNumberOfMasts(),
                    battleFieldForDataBaseOpponent.showBattleField().getBattleField(i,j).getShipNumber())){
                        displayShipCell(textViewArrayActivityMultiplayerOpponent[i][j]);
                    }else{
                    displayShipCellRed(textViewArrayActivityMultiplayerOpponent[i][j]);
                    }
                }

                // woda i została trafiony
                else if(!battleFieldForDataBaseOpponent.showBattleField().getBattleField(i,j).isShip()
                        &&battleFieldForDataBaseOpponent.showBattleField().getBattleField(i,j).isHit()){
                    displayWaterCell(textViewArrayActivityMultiplayerOpponent[i][j]);
                }

                else {
                    displayBattleCell(textViewArrayActivityMultiplayerOpponent[i][j]);
                }
            }
        }
    }

    private boolean zatopiony(int nOmasts, int sNumber) {
        int counter=0;
        for(int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                if(battleFieldForDataBaseOpponent.showBattleField().getBattleField(i,j).getNumberOfMasts()==nOmasts
                        &&battleFieldForDataBaseOpponent.showBattleField().getBattleField(i,j).getShipNumber()==sNumber
                        &&battleFieldForDataBaseOpponent.showBattleField().getBattleField(i,j).isHit()){
                    counter++;
                }else;
            }
        }return counter==nOmasts;
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



    private void shoot(int i, int j) {

        databaseReferenceMy.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                if(user.getIndex().getOpponent().isEmpty()){
                    Intent intent = new Intent(MultiplayerActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    textViewArrayActivityMultiplayerOpponent[i][j].setClickable(false);
                    battleFieldForDataBaseOpponent.showBattleField().getBattleField(i,j).setHit(true);

                    if(battleFieldForDataBaseOpponent.showBattleField().getBattleField(i,j).isShip()){
                        showOpponentBattleField();
                        databaseReferenceFight.child(user.getIndex().getOpponent()).setValue(battleFieldForDataBaseOpponent);
                        checkShipCounters();
                        updateShipsHit();
                        if(battleFieldForDataBaseOpponent.showBattleField().allShipsHit()){

                            //TODO wykasować grę i nabić punkty
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
                        displayWaterCell(textViewArrayActivityMultiplayerOpponent[i][j]);
                        disableClickable();
                        databaseReferenceFight.child(user.getIndex().getOpponent()).setValue(battleFieldForDataBaseOpponent);

                        databaseReferenceFight.child("turn").setValue(user.getIndex().getOpponent());

                        mHandler.postDelayed(game, deelay);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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

    public void clickMeCellGame_1x1(View view) {
        shoot(0,0);
    }

    public void clickMeCellGame_1x2(View view) {
        shoot(0,1);
    }

    public void clickMeCellGame_1x3(View view) {
        shoot(0,2);
    }

    public void clickMeCellGame_1x4(View view) {
        shoot(0,3);
    }

    public void clickMeCellGame_1x5(View view) {
        shoot(0,4);
    }

    public void clickMeCellGame_1x6(View view) {
        shoot(0,5);
    }

    public void clickMeCellGame_1x7(View view) {
        shoot(0,6);
    }

    public void clickMeCellGame_1x8(View view) {
        shoot(0,7);
    }

    public void clickMeCellGame_1x9(View view) {
        shoot(0,8);
    }

    public void clickMeCellGame_1x10(View view) {
        shoot(0,9);
    }

    public void clickMeCellGame_2x1(View view) {
        shoot(1,0);
    }

    public void clickMeCellGame_2x2(View view) {
        shoot(1,1);
    }

    public void clickMeCellGame_2x3(View view) {
        shoot(1,2);
    }

    public void clickMeCellGame_2x4(View view) {
        shoot(1,3);
    }

    public void clickMeCellGame_2x5(View view) {
        shoot(1,4);
    }

    public void clickMeCellGame_2x6(View view) {
        shoot(1,5);
    }

    public void clickMeCellGame_2x7(View view) {
        shoot(1,6);
    }

    public void clickMeCellGame_2x8(View view) {
        shoot(1,7);
    }

    public void clickMeCellGame_2x9(View view) {
        shoot(1,8);
    }

    public void clickMeCellGame_2x10(View view) {
        shoot(1,9);
    }

    public void clickMeCellGame_3x1(View view) {
        shoot(2,0);
    }

    public void clickMeCellGame_3x2(View view) {
        shoot(2,1);
    }

    public void clickMeCellGame_3x3(View view) {
        shoot(2,2);
    }

    public void clickMeCellGame_3x4(View view) {
        shoot(2,3);
    }

    public void clickMeCellGame_3x5(View view) {
        shoot(2,4);
    }

    public void clickMeCellGame_3x6(View view) {
        shoot(2,5);
    }

    public void clickMeCellGame_3x7(View view) {
        shoot(2,6);
    }

    public void clickMeCellGame_3x8(View view) {
        shoot(2,7);
    }

    public void clickMeCellGame_3x9(View view) {
        shoot(2,8);
    }

    public void clickMeCellGame_3x10(View view) {
        shoot(2,9);
    }

    public void clickMeCellGame_4x1(View view) {
        shoot(3,0);
    }

    public void clickMeCellGame_4x2(View view) {
        shoot(3,1);
    }

    public void clickMeCellGame_4x3(View view) {
        shoot(3,2);
    }

    public void clickMeCellGame_4x4(View view) {
        shoot(3,3);
    }

    public void clickMeCellGame_4x5(View view) {
        shoot(3,4);
    }

    public void clickMeCellGame_4x6(View view) {
        shoot(3,5);
    }

    public void clickMeCellGame_4x7(View view) {
        shoot(3,6);
    }

    public void clickMeCellGame_4x8(View view) {
        shoot(3,7);
    }

    public void clickMeCellGame_4x9(View view) {
        shoot(3,8);
    }

    public void clickMeCellGame_4x10(View view) {
        shoot(3,9);
    }

    public void clickMeCellGame_5x1(View view) {
        shoot(4,0);
    }

    public void clickMeCellGame_5x2(View view) {
        shoot(4,1);
    }

    public void clickMeCellGame_5x3(View view) {
        shoot(4,2);
    }

    public void clickMeCellGame_5x4(View view) {
        shoot(4,3);
    }

    public void clickMeCellGame_5x5(View view) {
        shoot(4,4);
    }

    public void clickMeCellGame_5x6(View view) {
        shoot(4,5);
    }

    public void clickMeCellGame_5x7(View view) {
        shoot(4,6);
    }

    public void clickMeCellGame_5x8(View view) {
        shoot(4,7);
    }

    public void clickMeCellGame_5x9(View view) {
        shoot(4,8);
    }

    public void clickMeCellGame_5x10(View view) {
        shoot(4,9);
    }

    public void clickMeCellGame_6x1(View view) {
        shoot(5,0);
    }

    public void clickMeCellGame_6x2(View view) {
        shoot(5,1);
    }

    public void clickMeCellGame_6x3(View view) {
        shoot(5,2);
    }

    public void clickMeCellGame_6x4(View view) {
        shoot(5,3);
    }

    public void clickMeCellGame_6x5(View view) {
        shoot(5,4);
    }

    public void clickMeCellGame_6x6(View view) {
        shoot(5,5);
    }

    public void clickMeCellGame_6x7(View view) {
        shoot(5,6);
    }

    public void clickMeCellGame_6x8(View view) {
        shoot(5,7);
    }

    public void clickMeCellGame_6x9(View view) {
        shoot(5,8);
    }

    public void clickMeCellGame_6x10(View view) {
        shoot(5,9);
    }

    public void clickMeCellGame_7x1(View view) {
        shoot(6,0);
    }

    public void clickMeCellGame_7x2(View view) {
        shoot(6,1);
    }

    public void clickMeCellGame_7x3(View view) {
        shoot(6,2);
    }

    public void clickMeCellGame_7x4(View view) {
        shoot(6,3);
    }

    public void clickMeCellGame_7x5(View view) {
        shoot(6,4);
    }

    public void clickMeCellGame_7x6(View view) {
        shoot(6,5);
    }

    public void clickMeCellGame_7x7(View view) {
        shoot(6,6);
    }

    public void clickMeCellGame_7x8(View view) {
        shoot(6,7);
    }

    public void clickMeCellGame_7x9(View view) {
        shoot(6,8);
    }

    public void clickMeCellGame_7x10(View view) {
        shoot(6,9);
    }

    public void clickMeCellGame_8x1(View view) {
        shoot(7,0);
    }

    public void clickMeCellGame_8x2(View view) {
        shoot(7,1);
    }

    public void clickMeCellGame_8x3(View view) {
        shoot(7,2);
    }

    public void clickMeCellGame_8x4(View view) {
        shoot(7,3);
    }

    public void clickMeCellGame_8x5(View view) {
        shoot(7,4);
    }

    public void clickMeCellGame_8x6(View view) {
        shoot(7,5);
    }

    public void clickMeCellGame_8x7(View view) {
        shoot(7,6);
    }

    public void clickMeCellGame_8x8(View view) {
        shoot(7,7);
    }

    public void clickMeCellGame_8x9(View view) {
        shoot(7,8);
    }

    public void clickMeCellGame_8x10(View view) {
        shoot(7,9);
    }

    public void clickMeCellGame_9x1(View view) {
        shoot(8,0);
    }

    public void clickMeCellGame_9x2(View view) {
        shoot(8,1);
    }

    public void clickMeCellGame_9x3(View view) {
        shoot(8,2);
    }

    public void clickMeCellGame_9x4(View view) {
        shoot(8,3);
    }

    public void clickMeCellGame_9x5(View view) {
        shoot(8,4);
    }

    public void clickMeCellGame_9x6(View view) {
        shoot(8,5);
    }

    public void clickMeCellGame_9x7(View view) {
        shoot(8,6);
    }

    public void clickMeCellGame_9x8(View view) {
        shoot(8,7);
    }

    public void clickMeCellGame_9x9(View view) {
        shoot(8,8);
    }

    public void clickMeCellGame_9x10(View view) {
        shoot(8,9);
    }

    public void clickMeCellGame_10x1(View view) {
        shoot(9,0);
    }

    public void clickMeCellGame_10x2(View view) {
        shoot(9,1);
    }

    public void clickMeCellGame_10x3(View view) {
        shoot(9,2);
    }

    public void clickMeCellGame_10x4(View view) {
        shoot(9,3);
    }

    public void clickMeCellGame_10x5(View view) {
        shoot(9,4);
    }

    public void clickMeCellGame_10x6(View view) {
        shoot(9,5);
    }

    public void clickMeCellGame_10x7(View view) {
        shoot(9,6);
    }

    public void clickMeCellGame_10x8(View view) {
        shoot(9,7);
    }

    public void clickMeCellGame_10x9(View view) {
        shoot(9,8);
    }

    public void clickMeCellGame_10x10(View view) {
        shoot(9,9);
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
}
