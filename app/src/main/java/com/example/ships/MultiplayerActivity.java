package com.example.ships;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ships.classes.BattleFieldForDataBase;
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
    private DatabaseReference databaseReferenceMy, databaseReferenceFight;
    private String userID;
    private User user = new User();
    private BattleFieldForDataBase battleFieldForDataBaseMy = new BattleFieldForDataBase();
    private BattleFieldForDataBase battleFieldForDataBaseOpponent = new BattleFieldForDataBase();
    private Handler mHandler = new Handler();
    private int deelay = 1000;
    private TextView[][] textViewArrayActivityMultiplayerMe = new TextView[10][10];
    private TextView[][] textViewArrayActivityMultiplayerOpponent = new TextView[10][10];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplayer);
        initializeTextViews();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        userID = firebaseUser.getUid();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReferenceMy=firebaseDatabase.getReference("User").child(userID);

        prepareFields.run();

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

// TODO finish this first




    }

    private Runnable prepareFields = new Runnable() {
        @Override
        public void run() {
            createFields();
        }
    };

    private void createFields() {
        databaseReferenceMy.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                databaseReferenceFight=firebaseDatabase.getReference("Battle").child(user.getIndex().getGameIndex());
                databaseReferenceFight.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (!dataSnapshot.exists()){
                            battleFieldForDataBaseMy.create();
                            databaseReferenceFight.child(user.getId()).setValue(battleFieldForDataBaseMy);
                            databaseReferenceFight.child(user.getIndex().getOpponent()).setValue(battleFieldForDataBaseOpponent);
                            mHandler.postDelayed(prepareFields,deelay);
                        }else{

                            battleFieldForDataBaseMy=dataSnapshot.child(user.getId()).getValue(BattleFieldForDataBase.class);
                            battleFieldForDataBaseOpponent=dataSnapshot.child(user.getIndex().getOpponent()).getValue(BattleFieldForDataBase.class);

                            if(!battleFieldForDataBaseMy.isCreated()){
                                battleFieldForDataBaseMy.create();
                                databaseReferenceFight.child(user.getId()).setValue(battleFieldForDataBaseMy);
                            }

                            if(battleFieldForDataBaseMy.isCreated()&&battleFieldForDataBaseOpponent.isCreated()){
                                // TODO game start here;
                            }else{
                                mHandler.postDelayed(prepareFields,deelay);
                            }

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void clickMeCellGame_1x1(View view) {
    }

    public void clickMeCellGame_1x2(View view) {
    }

    public void clickMeCellGame_1x3(View view) {
    }

    public void clickMeCellGame_1x4(View view) {
    }

    public void clickMeCellGame_1x5(View view) {
    }

    public void clickMeCellGame_1x6(View view) {
    }

    public void clickMeCellGame_1x7(View view) {
    }

    public void clickMeCellGame_1x8(View view) {
    }

    public void clickMeCellGame_1x9(View view) {
    }

    public void clickMeCellGame_1x10(View view) {
    }

    public void clickMeCellGame_2x1(View view) {
    }

    public void clickMeCellGame_2x2(View view) {
    }

    public void clickMeCellGame_2x3(View view) {
    }

    public void clickMeCellGame_2x4(View view) {
    }

    public void clickMeCellGame_2x5(View view) {
    }

    public void clickMeCellGame_2x6(View view) {
    }

    public void clickMeCellGame_2x7(View view) {
    }

    public void clickMeCellGame_2x8(View view) {
    }

    public void clickMeCellGame_2x9(View view) {
    }

    public void clickMeCellGame_2x10(View view) {
    }

    public void clickMeCellGame_3x1(View view) {
    }

    public void clickMeCellGame_3x2(View view) {
    }

    public void clickMeCellGame_3x3(View view) {
    }

    public void clickMeCellGame_3x4(View view) {
    }

    public void clickMeCellGame_3x5(View view) {
    }

    public void clickMeCellGame_3x6(View view) {
    }

    public void clickMeCellGame_3x7(View view) {
    }

    public void clickMeCellGame_3x8(View view) {
    }

    public void clickMeCellGame_3x9(View view) {
    }

    public void clickMeCellGame_3x10(View view) {
    }

    public void clickMeCellGame_4x1(View view) {
    }

    public void clickMeCellGame_4x2(View view) {
    }

    public void clickMeCellGame_4x3(View view) {
    }

    public void clickMeCellGame_4x4(View view) {
    }

    public void clickMeCellGame_4x5(View view) {
    }

    public void clickMeCellGame_4x6(View view) {
    }

    public void clickMeCellGame_4x7(View view) {
    }

    public void clickMeCellGame_4x8(View view) {
    }

    public void clickMeCellGame_4x9(View view) {
    }

    public void clickMeCellGame_4x10(View view) {
    }

    public void clickMeCellGame_5x1(View view) {
    }

    public void clickMeCellGame_5x2(View view) {
    }

    public void clickMeCellGame_5x3(View view) {
    }

    public void clickMeCellGame_5x4(View view) {
    }

    public void clickMeCellGame_5x5(View view) {
    }

    public void clickMeCellGame_5x6(View view) {
    }

    public void clickMeCellGame_5x7(View view) {
    }

    public void clickMeCellGame_5x8(View view) {
    }

    public void clickMeCellGame_5x9(View view) {
    }

    public void clickMeCellGame_5x10(View view) {
    }

    public void clickMeCellGame_6x1(View view) {
    }

    public void clickMeCellGame_6x2(View view) {
    }

    public void clickMeCellGame_6x3(View view) {
    }

    public void clickMeCellGame_6x4(View view) {
    }

    public void clickMeCellGame_6x5(View view) {
    }

    public void clickMeCellGame_6x6(View view) {
    }

    public void clickMeCellGame_6x7(View view) {
    }

    public void clickMeCellGame_6x8(View view) {
    }

    public void clickMeCellGame_6x9(View view) {
    }

    public void clickMeCellGame_6x10(View view) {
    }

    public void clickMeCellGame_7x1(View view) {
    }

    public void clickMeCellGame_7x2(View view) {
    }

    public void clickMeCellGame_7x3(View view) {
    }

    public void clickMeCellGame_7x4(View view) {
    }

    public void clickMeCellGame_7x5(View view) {
    }

    public void clickMeCellGame_7x6(View view) {
    }

    public void clickMeCellGame_7x7(View view) {
    }

    public void clickMeCellGame_7x8(View view) {
    }

    public void clickMeCellGame_7x9(View view) {
    }

    public void clickMeCellGame_7x10(View view) {
    }

    public void clickMeCellGame_8x1(View view) {
    }

    public void clickMeCellGame_8x2(View view) {
    }

    public void clickMeCellGame_8x3(View view) {
    }

    public void clickMeCellGame_8x4(View view) {
    }

    public void clickMeCellGame_8x5(View view) {
    }

    public void clickMeCellGame_8x6(View view) {
    }

    public void clickMeCellGame_8x7(View view) {
    }

    public void clickMeCellGame_8x8(View view) {
    }

    public void clickMeCellGame_8x9(View view) {
    }

    public void clickMeCellGame_8x10(View view) {
    }

    public void clickMeCellGame_9x1(View view) {
    }

    public void clickMeCellGame_9x2(View view) {
    }

    public void clickMeCellGame_9x3(View view) {
    }

    public void clickMeCellGame_9x4(View view) {
    }

    public void clickMeCellGame_9x5(View view) {
    }

    public void clickMeCellGame_9x6(View view) {
    }

    public void clickMeCellGame_9x7(View view) {
    }

    public void clickMeCellGame_9x8(View view) {
    }

    public void clickMeCellGame_9x9(View view) {
    }

    public void clickMeCellGame_9x10(View view) {
    }

    public void clickMeCellGame_10x1(View view) {
    }

    public void clickMeCellGame_10x2(View view) {
    }

    public void clickMeCellGame_10x3(View view) {
    }

    public void clickMeCellGame_10x4(View view) {
    }

    public void clickMeCellGame_10x5(View view) {
    }

    public void clickMeCellGame_10x6(View view) {
    }

    public void clickMeCellGame_10x7(View view) {
    }

    public void clickMeCellGame_10x8(View view) {
    }

    public void clickMeCellGame_10x9(View view) {
    }

    public void clickMeCellGame_10x10(View view) {
    }
}
