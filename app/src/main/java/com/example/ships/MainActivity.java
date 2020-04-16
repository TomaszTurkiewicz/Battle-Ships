package com.example.ships;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.graphics.Shader;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.util.Log;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.ships.classes.FightIndex;
import com.example.ships.classes.SinglePlayerMatch;
import com.example.ships.classes.TileDrawable;
import com.example.ships.classes.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


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
    private ImageButton accountBtn, leave;
    private ImageView redDotMultiplayerIV;
    private Handler mHandler = new Handler();
    private int deelay = 1000;
    private boolean logIn;
    private int square;
    private boolean ready=false;
    private static String TAG = "NOTIFICATION TAG";
    private static String FCM_API="https://fcm.googleapis.com/fcm/send";
    private static String TOPIC;
    private static String NOTIFICATION_MESSAGE;
    private static String NOTIFICATION_TITLE;
    private String serverKey= "key=" + "AAAAUhITVm0:APA91bGLIOR5L7HQyh64ejoejk-nQFBWP9RxDqtzzjoSXCmROqs7JO_uDDyuW5VuTfJBxtKY_RG8q5_CnpKJsN3qHtVvgiAkuDM2J9T68mk0LzKCcRKgRbj3DQ-A1a8uzZ07wz8OlirQ";
    private String contentType= "application/json";
    private TextView provider;

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

        setContentView(R.layout.activity_main);


        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();


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
        leave=findViewById(R.id.leaveMainActivity);
        leave.setBackgroundResource(R.drawable.back);
        provider=findViewById(R.id.provider);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        int devWidth = width/32;
        int devHeight = height/18;

        if(devWidth>devHeight){
            square=devHeight;
        }else{
            square=devWidth;
        }
        int widthOffSet = width%square;
        int heightOffSet = height%square;

        SharedPreferences sp = getSharedPreferences("VALUES", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("square",square);
        editor.putInt("width",width);
        editor.putInt("height",height);
        editor.putInt("widthOffSet",widthOffSet);
        editor.putInt("heightOffSet",heightOffSet);
        editor.commit();



        constraintLayout.setBackground(new TileDrawable(getDrawable(R.drawable.background_x), Shader.TileMode.REPEAT,square));

        ConstraintSet set = new ConstraintSet();

        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(12*square,3*square);
        ConstraintLayout.LayoutParams params1 = new ConstraintLayout.LayoutParams(12*square,3*square);
        ConstraintLayout.LayoutParams params2 = new ConstraintLayout.LayoutParams(8*square,2*square);
        ConstraintLayout.LayoutParams params3 = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ConstraintLayout.LayoutParams params4 = new ConstraintLayout.LayoutParams(3*square,3*square);
        ConstraintLayout.LayoutParams params5 = new ConstraintLayout.LayoutParams(square,square);
        ConstraintLayout.LayoutParams params6 = new ConstraintLayout.LayoutParams(2*square,2*square);
        ConstraintLayout.LayoutParams params7 = new ConstraintLayout.LayoutParams(10*square,2*square);
        loggedIn.setLayoutParams(params3);
        loggedIn.setTextSize(TypedValue.COMPLEX_UNIT_PX,square);
        userName.setTextSize(TypedValue.COMPLEX_UNIT_PX,square);
        singlePlayerBtn.setLayoutParams(params);
        singlePlayerBtn.setTextSize(TypedValue.COMPLEX_UNIT_PX,square);
        multiplayerBtn.setLayoutParams(params1);
        multiplayerBtn.setTextSize(TypedValue.COMPLEX_UNIT_PX,square);
        ranking.setLayoutParams(params2);
        ranking.setTextSize(TypedValue.COMPLEX_UNIT_PX,square);
        accountBtn.setLayoutParams(params4);
        redDotMultiplayerIV.setLayoutParams(params5);
        leave.setLayoutParams(params6);
        provider.setLayoutParams(params7);

        set.clone(constraintLayout);
        set.connect(singlePlayerBtn.getId(),ConstraintSet.TOP,constraintLayout.getId(),ConstraintSet.TOP,7*square);
        set.connect(singlePlayerBtn.getId(),ConstraintSet.LEFT,constraintLayout.getId(),ConstraintSet.LEFT,3*square);

        set.connect(multiplayerBtn.getId(),ConstraintSet.TOP,constraintLayout.getId(),ConstraintSet.TOP,7*square);
        set.connect(multiplayerBtn.getId(),ConstraintSet.LEFT,constraintLayout.getId(),ConstraintSet.LEFT,width-widthOffSet-15*square);

        set.connect(ranking.getId(),ConstraintSet.TOP,constraintLayout.getId(),ConstraintSet.TOP,height-heightOffSet-4*square);
        set.connect(ranking.getId(),ConstraintSet.LEFT,constraintLayout.getId(),ConstraintSet.LEFT,width-widthOffSet-9*square);

        set.connect(loggedIn.getId(),ConstraintSet.TOP,constraintLayout.getId(),ConstraintSet.TOP,square);
        set.connect(loggedIn.getId(),ConstraintSet.LEFT,constraintLayout.getId(),ConstraintSet.LEFT,square);

        set.connect(accountBtn.getId(),ConstraintSet.TOP,constraintLayout.getId(),ConstraintSet.TOP,square);
        set.connect(accountBtn.getId(),ConstraintSet.LEFT,constraintLayout.getId(),ConstraintSet.LEFT,width-widthOffSet-4*square);

        set.connect(redDotMultiplayerIV.getId(),ConstraintSet.BOTTOM,multiplayerBtn.getId(),ConstraintSet.BOTTOM,3*square-square/2);
        set.connect(redDotMultiplayerIV.getId(),ConstraintSet.LEFT,multiplayerBtn.getId(),ConstraintSet.LEFT,12*square-square/2);

        set.connect(leave.getId(),ConstraintSet.TOP,constraintLayout.getId(),ConstraintSet.TOP,height-heightOffSet-3*square);
        set.connect(leave.getId(),ConstraintSet.LEFT,constraintLayout.getId(),ConstraintSet.LEFT,square);

        set.connect(provider.getId(),ConstraintSet.TOP,constraintLayout.getId(),ConstraintSet.TOP,4*square);
        set.connect(provider.getId(),ConstraintSet.LEFT,constraintLayout.getId(),ConstraintSet.LEFT,3*square);

        set.applyTo(constraintLayout);

    }

    @Override
    protected void onStart() {
        super.onStart();
 //       if(firebaseUser != null && firebaseUser.isEmailVerified()){

        if(firebaseUser != null){
            String providerId="";
            for(UserInfo profile : firebaseUser.getProviderData()){
                providerId = providerId+" "+profile.getProviderId();
            }

            if(providerId.contains("facebook.com")||providerId.contains("google.com")){
                logIn=true;
            }else{
                if(firebaseUser.isEmailVerified()){
                    logIn=true;
                }else{
                    logIn=false;
                }
            }
        }



        if(logIn){
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
                        ready=true;


                    } else {

                        userName.setText(firebaseUser.getEmail());
                        user.setId(userID);
                        user.setName(firebaseUser.getEmail());
                        user.setEmail(firebaseUser.getEmail());
                        user.setNoOfGames(0);
                        user.setScore(0);
                        user.setIndex(new FightIndex());
                        user.setSinglePlayerMatch(new SinglePlayerMatch());
                        databaseReferenceMy.setValue(user);
                        ready=true;

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

                if(ready) {


                    databaseReferenceMy.addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if (dataSnapshot.exists()) {
                                user = dataSnapshot.getValue(User.class);

                                if (user.getIndex().getOpponent().isEmpty()) {
                                    mHandler.removeCallbacks(checkMyOpponentAndMove);
                                    Intent intent = new Intent(getApplicationContext(), ChooseOpponent.class);
                                    startActivity(intent);


                                } else {
                                    databaseReferenceOpponent = firebaseDatabase.getReference("User").child(user.getIndex().getOpponent());
                                    databaseReferenceOpponent.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                            if (dataSnapshot.exists()) {
                                                opponentUser = dataSnapshot.getValue(User.class);
                                                if (user.getIndex().isAccepted() && opponentUser.getIndex().isAccepted()) {
                                                    Toast.makeText(MainActivity.this, "You can fight", Toast.LENGTH_LONG).show();
                                                    mHandler.removeCallbacks(checkMyOpponentAndMove);
                                                    Intent intent = new Intent(MainActivity.this, MultiplayerActivity.class);
                                                    startActivity(intent);


                                                } else if (user.getIndex().isAccepted() && !opponentUser.getIndex().isAccepted()) {
                                                    Toast.makeText(MainActivity.this, "You invited him", Toast.LENGTH_LONG).show();


                                                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                                    builder.setCancelable(true);
                                                    builder.setTitle("Waiting");
                                                    builder.setMessage("Do you want to wait for accept from: " + "\n" + opponentUser.getName());
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


                                                } else if (!user.getIndex().isAccepted() && opponentUser.getIndex().isAccepted()) {
                                                    Toast.makeText(MainActivity.this, "You have to accept", Toast.LENGTH_LONG).show();

                                                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                                    builder.setCancelable(true);
                                                    builder.setTitle("Accepting");
                                                    builder.setMessage("Do you want to fight with: " + "\n" + opponentUser.getName());
                                                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            user.getIndex().setAccepted(true);
                                                            user.getIndex().setGameIndex(opponentUser.getId() + user.getId());
                                                            opponentUser.getIndex().setGameIndex(opponentUser.getId() + user.getId());
                                                            databaseReferenceMy.setValue(user);
                                                            databaseReferenceOpponent.setValue(opponentUser);
                                                            mHandler.removeCallbacks(checkMyOpponentAndMove);


                                                            TOPIC = "/topics/"+ user.getIndex().getOpponent();

                                                            NOTIFICATION_TITLE = user.getName()+ " accepted your invitation";
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

                                                            Intent intent = new Intent(MainActivity.this, MultiplayerActivity.class);
                                                            startActivity(intent);

                                                        }
                                                    });
                                                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {



                                                            TOPIC = "/topics/"+ user.getIndex().getOpponent();

                                                            NOTIFICATION_TITLE = user.getName()+ " rejected your invitation";
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

                                                            user.getIndex().setOpponent("");
                                                            opponentUser.getIndex().setOpponent("");
                                                            opponentUser.getIndex().setAccepted(false);
                                                            databaseReferenceMy.setValue(user);
                                                            databaseReferenceOpponent.setValue(opponentUser);


                                                        }
                                                    });
                                                    AlertDialog dialog = builder.create();
                                                    dialog.show();
                                                } else ;
                                            } else {
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


                }


            });



        }else{
            loggedIn.setText("niezalogowany");
            userName.setClickable(false);
            multiplayerBtn.setVisibility(View.GONE);
            redDotMultiplayerIV.setVisibility(View.GONE);
            multiplayerBtn.setClickable(false);
            accountBtn.setBackgroundResource(R.drawable.account_box);

        }
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
                        Toast.makeText(MainActivity.this, "Request error", Toast.LENGTH_LONG).show();
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


    public void singleGame(View view) {
        if(logIn) {
            if(ready) {
                mHandler.removeCallbacks(checkMyOpponentAndMove);
                boolean savedGame = user.getSinglePlayerMatch().isGame();

                if (savedGame) {
                    Intent intent = new Intent(getApplicationContext(), GameBattle.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getApplicationContext(), ChooseGameLevel.class);
                    startActivity(intent);
                }

            }
        }else{
        Intent intent = new Intent(getApplicationContext(),ChooseGameLevel.class);
        startActivity(intent);
        }
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
                    if(dataSnapshot.exists()){

                    user = dataSnapshot.getValue(User.class);
                    if(!user.getIndex().getOpponent().equals("")&&!user.getIndex().isAccepted()) {
                        redDotMultiplayerIV.setVisibility(View.VISIBLE);
                        multiplayerBtn.setText("ACCEPT");
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
                    }else{
                        mHandler.postDelayed(checkMyOpponentAndMove,deelay);
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    };

    public void leaveMainMenuOnClick(View view) {
        finish();
    }
}







// TODO change progress dialog in score and choose opponent...
// TODO customize alert dialogs


