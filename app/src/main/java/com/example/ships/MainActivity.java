package com.example.ships;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Shader;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.example.ships.classes.FightIndex;
import com.example.ships.classes.TileDrawable;
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
    private DatabaseReference databaseReferenceMy, databaseReferenceOpponent,databaseReferenceBattle;
    private Button multiplayerBtn, singlePlayerBtn, ranking;
    private ConstraintLayout constraintLayout;
    private String userID;
    private String newUserName;
    private User user = new User();
    private User opponentUser = new User();
    private ImageButton accountBtn;
    private ImageView redDotMultiplayerIV;
    private Handler mHandler = new Handler();
    private int deelay = 1000;
    private boolean logIn;
    private int square;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        constraintLayout = findViewById(R.id.mainActivityLayout);
        userName=findViewById(R.id.userName);
        loggedIn=findViewById(R.id.loggedIn);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        accountBtn=findViewById(R.id.accountButton);
        redDotMultiplayerIV = findViewById(R.id.redDotMultiplayer);
        redDotMultiplayerIV.setVisibility(View.GONE);
        singlePlayerBtn = findViewById(R.id.singlePlayer);
        multiplayerBtn=findViewById(R.id.multiplayer);
        ranking=findViewById(R.id.ranking);
        multiplayerBtn.setText("MULTI PLAYER");
        accountBtn.setBackgroundResource(R.drawable.account_box);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        int devWidth = width/32;
        int devHeight = height/19;

        if(devWidth>devHeight){
            square=devHeight;
        }else{
            square=devWidth;
        }

        int widthOffSet = width%square;
        int heightOffSet = height%square;

        constraintLayout.setBackground(new TileDrawable(getDrawable(R.drawable.background_x), Shader.TileMode.REPEAT,square));

        ConstraintSet set = new ConstraintSet();

        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(8*square,2*square);
        ConstraintLayout.LayoutParams params1 = new ConstraintLayout.LayoutParams(8*square,2*square);
        ConstraintLayout.LayoutParams params2 = new ConstraintLayout.LayoutParams(8*square,2*square);
        ConstraintLayout.LayoutParams params3 = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ConstraintLayout.LayoutParams params4 = new ConstraintLayout.LayoutParams(2*square,2*square);
        loggedIn.setLayoutParams(params3);
        loggedIn.setTextSize(TypedValue.COMPLEX_UNIT_PX,square);
        userName.setTextSize(TypedValue.COMPLEX_UNIT_PX,square);
        singlePlayerBtn.setLayoutParams(params);
        multiplayerBtn.setLayoutParams(params1);
        ranking.setLayoutParams(params2);
        accountBtn.setLayoutParams(params4);

        set.clone(constraintLayout);
        set.connect(singlePlayerBtn.getId(),ConstraintSet.TOP,constraintLayout.getId(),ConstraintSet.TOP,8*square);
        set.connect(singlePlayerBtn.getId(),ConstraintSet.START,constraintLayout.getId(),ConstraintSet.START,5*square);

        set.connect(multiplayerBtn.getId(),ConstraintSet.TOP,constraintLayout.getId(),ConstraintSet.TOP,8*square);
        set.connect(multiplayerBtn.getId(),ConstraintSet.START,constraintLayout.getId(),ConstraintSet.START,width-widthOffSet-13*square);

        set.connect(ranking.getId(),ConstraintSet.TOP,constraintLayout.getId(),ConstraintSet.TOP,height-heightOffSet-4*square);
        set.connect(ranking.getId(),ConstraintSet.START,constraintLayout.getId(),ConstraintSet.START,width-widthOffSet-9*square);

        set.connect(loggedIn.getId(),ConstraintSet.TOP,constraintLayout.getId(),ConstraintSet.TOP,square);
        set.connect(loggedIn.getId(),ConstraintSet.START,constraintLayout.getId(),ConstraintSet.START,square);

        set.connect(accountBtn.getId(),ConstraintSet.TOP,constraintLayout.getId(),ConstraintSet.TOP,square);
        set.connect(accountBtn.getId(),ConstraintSet.START,constraintLayout.getId(),ConstraintSet.START,width-widthOffSet-3*square);

        set.applyTo(constraintLayout);





    }

    @Override
    protected void onStart() {
        super.onStart();
        if(firebaseUser != null && firebaseUser.isEmailVerified()){





            logIn=true;
            loggedIn.setText("Zalogowany jako: ");
            accountBtn.setBackgroundResource(R.drawable.account_box_red_pen);
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
            checkMyOpponentAndMove.run();
            multiplayerBtn.setVisibility(View.VISIBLE);
            multiplayerBtn.setClickable(true);
            multiplayerBtn.setOnClickListener(v->{

                databaseReferenceMy.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.exists()) {
                            user=dataSnapshot.getValue(User.class);

                            if(user.getIndex().getOpponent().isEmpty()){
                                mHandler.removeCallbacks(checkMyOpponentAndMove);
                                Intent intent = new Intent(getApplicationContext(),ChooseOpponent.class);
                                startActivity(intent);


                            }else{
                                databaseReferenceOpponent=firebaseDatabase.getReference("User").child(user.getIndex().getOpponent());
                                databaseReferenceOpponent.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        if(dataSnapshot.exists()){
                                            opponentUser=dataSnapshot.getValue(User.class);
                                            if(user.getIndex().isAccepted()&&opponentUser.getIndex().isAccepted()){
                                                Toast.makeText(MainActivity.this,"You can fight",Toast.LENGTH_LONG).show();
                                                mHandler.removeCallbacks(checkMyOpponentAndMove);
                                                Intent intent = new Intent(MainActivity.this,MultiplayerActivity.class);
                                                startActivity(intent);



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
                                                        mHandler.removeCallbacks(checkMyOpponentAndMove);
                                                        Intent intent = new Intent(MainActivity.this,MultiplayerActivity.class);
                                                        startActivity(intent);

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
            redDotMultiplayerIV.setVisibility(View.GONE);
            multiplayerBtn.setClickable(false);
            accountBtn.setBackgroundResource(R.drawable.account_box);

            logIn=false;
        }
    }


    public void singleGame(View view) {
        if(logIn) {
            mHandler.removeCallbacks(checkMyOpponentAndMove);
        }
        Intent intent = new Intent(getApplicationContext(),ChooseGameLevel.class);
        startActivity(intent);
    }



    public void onClickSignIn(View view) {
        mHandler.removeCallbacks(checkMyOpponentAndMove);
        Intent intent = new Intent(getApplicationContext(),SignInActivity.class);
        startActivity(intent);
        finish();
    }


    public void ranking(View view) {
        mHandler.removeCallbacks(checkMyOpponentAndMove);
        Intent intent = new Intent(getApplicationContext(),Scores.class);
        startActivity(intent);

    }

    private Runnable checkMyOpponentAndMove = new Runnable() {
        @Override
        public void run() {
            databaseReferenceMy.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    user = dataSnapshot.getValue(User.class);
                    if(!user.getIndex().getOpponent().equals("")&&!user.getIndex().isAccepted()) {
                        redDotMultiplayerIV.setVisibility(View.VISIBLE);
                        multiplayerBtn.setText("ACCEPT INVITATION");
                        mHandler.postDelayed(checkMyOpponentAndMove, deelay);
                    }else if(!user.getIndex().getGameIndex().equals("")){
                        databaseReferenceBattle = firebaseDatabase.getReference("Battle").child(user.getIndex().getGameIndex());
                        databaseReferenceBattle.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists()){
                                    String turn = dataSnapshot.child("turn").getValue().toString();
                                    boolean ready = (boolean) dataSnapshot.child("ready").getValue();
                                    if(user.getId().equals(turn)&&ready){
                                        redDotMultiplayerIV.setVisibility(View.VISIBLE);
                                        multiplayerBtn.setText("MY MOVE");
                                        mHandler.postDelayed(checkMyOpponentAndMove,deelay);
                                    }else{

                                        redDotMultiplayerIV.setVisibility(View.GONE);
                                        multiplayerBtn.setText("FIGHT");
                                        mHandler.postDelayed(checkMyOpponentAndMove,deelay);
                                    }
                                }else{
                                    redDotMultiplayerIV.setVisibility(View.GONE);
                                    multiplayerBtn.setText("FIGHT");
                                    mHandler.postDelayed(checkMyOpponentAndMove,deelay);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }else{
                        redDotMultiplayerIV.setVisibility(View.GONE);
                        multiplayerBtn.setText("MULTI PLAYER");
                        mHandler.postDelayed(checkMyOpponentAndMove,deelay);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    };

}



// TODO change buttons styles...
// TODO change notification

