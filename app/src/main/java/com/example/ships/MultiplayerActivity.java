package com.example.ships;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplayer);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        userID = firebaseUser.getUid();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReferenceMy=firebaseDatabase.getReference("User").child(userID);

        prepareFields.run();

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
