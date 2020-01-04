package com.example.ships;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {
    private TextView userName;
    private TextView loggedIn;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private int numberOfUsers;
    private int positionRanking;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private DatabaseReference databaseReferenceUsers;
    private String userID;



    private ImageButton accountBtn;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userName=findViewById(R.id.userName);
        loggedIn=findViewById(R.id.loggedIn);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        accountBtn=findViewById(R.id.accountButton);

        if(firebaseUser != null && firebaseUser.isEmailVerified()){
            loggedIn.setText("Zalogowany jako: ");
            accountBtn.setBackgroundColor(Color.RED);
            userID = firebaseUser.getUid();
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference=firebaseDatabase.getReference("User").child(userID);
            databaseReferenceUsers=firebaseDatabase.getReference("User");
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (dataSnapshot.exists()) {
                        showData(dataSnapshot);
                    } else {
                        userName.setText(firebaseUser.getEmail());

                        databaseReferenceUsers.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                                numberOfUsers= (int) dataSnapshot1.getChildrenCount();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError1) {

                            }
                        });

                        User user = new User();
                        user.setId(userID);
                        user.setName(firebaseUser.getEmail());
                        user.setEmail(firebaseUser.getEmail());
                        user.setNoOfGames(0);
                        user.setScore(0);
                        user.setPosition(numberOfUsers+1);

                        databaseReference.setValue(user);

                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }else{
            loggedIn.setText("niezalogowany");
        }
    }

    private void showData(DataSnapshot dataSnapshot) {
        name = (String) dataSnapshot.child("name").getValue();
        userName.setText(name);
    }




    public void randomGame(View view) {
        GameDifficulty.getInstance().setRandom(true);
        Intent intent = new Intent(getApplicationContext(),ChooseGameLevel.class);
        startActivity(intent);
        finish();
    }

    public void notRandomGame(View view) {
        GameDifficulty.getInstance().setRandom(false);
        Intent intent = new Intent(getApplicationContext(),CreateBattleField.class);
        startActivity(intent);
        finish();
    }


    public void onClickSignIn(View view) {
        Intent intent = new Intent(getApplicationContext(),SignInActivity.class);
        startActivity(intent);
        finish();
    }


    public void ranking(View view) {
        Intent intent = new Intent(getApplicationContext(),Scores.class);
        startActivity(intent);
        finish();
    }
}
