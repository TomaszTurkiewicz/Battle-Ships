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

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private TextView userName;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private int numberOfUsers;
    private int positionRanking;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    List<User> list = new ArrayList<>();


    private ImageButton accountBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userName=findViewById(R.id.userName);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        accountBtn=findViewById(R.id.accountButton);

        if(firebaseUser != null && firebaseUser.isEmailVerified()){
            userName.setText(firebaseUser.getEmail());
            accountBtn.setBackgroundColor(Color.RED);
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference=firebaseDatabase.getReference("User");
        }else{
            userName.setText("niezalogowany");

        }







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

    public void networkGame(View view) {
    }

    public void bluetooth(View view) {
    }

    public void onClickSignIn(View view) {
        Intent intent = new Intent(getApplicationContext(),SignInActivity.class);
        startActivity(intent);
        finish();
    }
// TODO przenieść sortowanie do aktywności (po wygranej)
    public void sort(View view) {
        updateRanking();

    }


    private void updateRanking() {
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    numberOfUsers = (int) dataSnapshot.getChildrenCount();
                    Ranking ranking = new Ranking(numberOfUsers);

                    for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                        User user = postSnapshot.getValue(User.class);
                        list.add(user);
                    }

                    for(int i=0;i<list.size();i++){
                        ranking.addUsers(list.get(i));
                    }

                    ranking.sortRanking();
                   ranking.setPosition();

                  for(int i=0;i<numberOfUsers;i++){
                       databaseReference.child(ranking.getRanking(i).getId()).child("position").setValue(ranking.getRanking(i).getPosition());
                   }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }



}
