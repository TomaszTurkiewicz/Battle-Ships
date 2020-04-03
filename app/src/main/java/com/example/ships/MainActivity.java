package com.example.ships;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ships.classes.FightIndex;
import com.example.ships.classes.GameDifficulty;
import com.example.ships.classes.User;
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
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReferenceMy, databaseReferenceOpponent;
    private Button multiplayerBtn;

    private String userID;
    private String newUserName;
    private User user = new User();
    private User opponentUser = new User();
    private ImageButton accountBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userName=findViewById(R.id.userName);
        loggedIn=findViewById(R.id.loggedIn);


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        accountBtn=findViewById(R.id.accountButton);

        multiplayerBtn=findViewById(R.id.multiplayer);

        if(firebaseUser != null && firebaseUser.isEmailVerified()){
            loggedIn.setText("Zalogowany jako: ");
            accountBtn.setBackgroundColor(Color.RED);
            userID = firebaseUser.getUid();
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReferenceMy=firebaseDatabase.getReference("User").child(userID);

            databaseReferenceMy.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (dataSnapshot.exists()) {
                        user=dataSnapshot.getValue(User.class);
                        userName.setText(user.getName());
                    } else {

                        userName.setText(firebaseUser.getEmail());
                        user.setId(userID);
                        user.setName(firebaseUser.getEmail());
                        user.setEmail(firebaseUser.getEmail());
                        user.setNoOfGames(0);
                        user.setScore(0);
                        user.setIndex(new FightIndex());
                        databaseReferenceMy.setValue(user);

                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });


            userName.setClickable(true);
            userName.setOnClickListener(v->{
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("NEW USER NAME");

                final EditText input = new EditText(this);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                builder.setPositiveButton("OK", (dialog, which) ->
                {
                    newUserName=input.getText().toString();
                    user.setName(newUserName);
                    databaseReferenceMy.setValue(user);
                    userName.setText(user.getName());

                });
                builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
                builder.show();
            });

            multiplayerBtn.setVisibility(View.VISIBLE);
            multiplayerBtn.setClickable(true);
            multiplayerBtn.setOnClickListener(v->{

                databaseReferenceMy.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.exists()) {
                            user=dataSnapshot.getValue(User.class);

                            if(user.getIndex().getOpponent().isEmpty()){
                                Intent intent = new Intent(getApplicationContext(),ChooseOpponent.class);
                                startActivity(intent);
                                finish();
                            }else{
                                databaseReferenceOpponent=firebaseDatabase.getReference("User").child(user.getIndex().getOpponent());
                                databaseReferenceOpponent.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        if(dataSnapshot.exists()){
                                            opponentUser=dataSnapshot.getValue(User.class);
                                            if(user.getIndex().isAccepted()&&opponentUser.getIndex().isAccepted()){
                                                Toast.makeText(MainActivity.this,"You can fight",Toast.LENGTH_LONG).show();

                                                    Intent intent = new Intent(MainActivity.this,MultiplayerActivity.class);
                                                    startActivity(intent);
                                                    finish();


                                            }else if(user.getIndex().isAccepted()&&!opponentUser.getIndex().isAccepted()){
                                                Toast.makeText(MainActivity.this,"You invited him",Toast.LENGTH_LONG).show();


                                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                                builder.setCancelable(true);
                                                builder.setTitle("Waiting");
                                                builder.setMessage("Do you want to wait for accept from: "+"\n"+opponentUser.getName());
                                                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        // do nothing
                                                    }
                                                });
                                                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        user.getIndex().setOpponent("");
                                                        user.getIndex().setAccepted(false);
                                                        opponentUser.getIndex().setOpponent("");
                                                        opponentUser.getIndex().setAccepted(false);
                                                        databaseReferenceMy.setValue(user);
                                                        databaseReferenceOpponent.setValue(opponentUser);
                                                    }
                                                });
                                                AlertDialog dialog = builder.create();
                                                dialog.show();


                                            }else if(!user.getIndex().isAccepted()&&opponentUser.getIndex().isAccepted()){
                                                Toast.makeText(MainActivity.this,"You have to accept",Toast.LENGTH_LONG).show();

                                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                                builder.setCancelable(true);
                                                builder.setTitle("Accepting");
                                                builder.setMessage("Do you want to fight with: "+"\n"+opponentUser.getName());
                                                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        user.getIndex().setAccepted(true);
                                                        user.getIndex().setGameIndex(opponentUser.getId()+user.getId());
                                                        opponentUser.getIndex().setGameIndex(opponentUser.getId()+user.getId());
                                                        databaseReferenceMy.setValue(user);
                                                        databaseReferenceOpponent.setValue(opponentUser);
                                                    }
                                                });
                                                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        user.getIndex().setOpponent("");
                                                        opponentUser.getIndex().setOpponent("");
                                                        opponentUser.getIndex().setAccepted(false);
                                                        databaseReferenceMy.setValue(user);
                                                        databaseReferenceOpponent.setValue(opponentUser);
                                                    }
                                                });
                                                AlertDialog dialog = builder.create();
                                                dialog.show();
                                            }else;
                                        }
                                        else{
                                            user.getIndex().setAccepted(false);
                                            user.getIndex().setOpponent("");
                                            user.getIndex().setGameIndex("");
                                            databaseReferenceMy.setValue(user);
                                        }
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                    }
                                });
                            }
                        } else ;
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });






            });



        }else{
            loggedIn.setText("niezalogowany");
            userName.setClickable(false);
            multiplayerBtn.setVisibility(View.GONE);
            multiplayerBtn.setClickable(false);
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



// TODO change app life cycle (just finish() instead of intent new Intent
// TODO Red dot next to multiplayer button when someone invited me or made a move
// TODO change creating own battle field to constraint layout
// TODO create creating own battle field for multiplayer
// TODO change choose difficulty activity for singleplayer (random plus not random in one activity) get rid off two buttons from main activity
// TODO drawable for battle fields...
// TODO change buttons styles...
