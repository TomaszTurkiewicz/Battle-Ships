package com.example.ships;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Shader;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.ships.classes.FightIndex;
import com.example.ships.classes.RoundedCornerBitmap;
import com.example.ships.classes.SinglePlayerMatch;
import com.example.ships.classes.TileDrawable;
import com.example.ships.classes.UpdateHelper;
import com.example.ships.classes.User;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements UpdateHelper.OnUpdateNeededListener{

    private TextView userName;
    private TextView loggedIn;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReferenceMy, databaseReferenceOpponent,databaseReferenceBattle;
    private Button multiplayerBtn, singlePlayerBtn, ranking;
    private ConstraintLayout constraintLayout;
    private String userID;
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
    private int flags;
    private String providerId;
    private boolean loggedInWithFacebook;
    private StorageReference storageReference;
    private String facebookUserId="";
    private String facebookName="";
    private boolean syncFBName=false;
    private boolean syncFBPhoto = false;
    private boolean alertDialogInUse = false;
    private final String APP_ID = "ca-app-pub-6785166837488087~8005002708";
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
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




        loggedInWithFacebook=false;

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
        leave=findViewById(R.id.leaveMainActivity);
        mAdView = findViewById(R.id.adViewMainActivityBaner);
        leave.setBackgroundResource(R.drawable.back);
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
        ConstraintLayout.LayoutParams params7 = new ConstraintLayout.LayoutParams(8*square,2*square);
        ConstraintLayout.LayoutParams params3 = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ConstraintLayout.LayoutParams params4 = new ConstraintLayout.LayoutParams(3*square,3*square);
        ConstraintLayout.LayoutParams params5 = new ConstraintLayout.LayoutParams(square,square);
        ConstraintLayout.LayoutParams params6 = new ConstraintLayout.LayoutParams(2*square,2*square);
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

        set.clone(constraintLayout);
        set.connect(singlePlayerBtn.getId(),ConstraintSet.TOP,constraintLayout.getId(),ConstraintSet.TOP,7*square);
        set.connect(singlePlayerBtn.getId(),ConstraintSet.LEFT,constraintLayout.getId(),ConstraintSet.LEFT,3*square);

        set.connect(multiplayerBtn.getId(),ConstraintSet.TOP,constraintLayout.getId(),ConstraintSet.TOP,7*square);
        set.connect(multiplayerBtn.getId(),ConstraintSet.LEFT,constraintLayout.getId(),ConstraintSet.LEFT,width-widthOffSet-15*square);

        set.connect(ranking.getId(),ConstraintSet.TOP,constraintLayout.getId(),ConstraintSet.TOP,height-heightOffSet-5*square);
        set.connect(ranking.getId(),ConstraintSet.LEFT,constraintLayout.getId(),ConstraintSet.LEFT,width-widthOffSet-9*square);

        set.connect(loggedIn.getId(),ConstraintSet.TOP,constraintLayout.getId(),ConstraintSet.TOP,square);
        set.connect(loggedIn.getId(),ConstraintSet.LEFT,constraintLayout.getId(),ConstraintSet.LEFT,square);

        set.connect(accountBtn.getId(),ConstraintSet.TOP,constraintLayout.getId(),ConstraintSet.TOP,square);
        set.connect(accountBtn.getId(),ConstraintSet.LEFT,constraintLayout.getId(),ConstraintSet.LEFT,width-widthOffSet-4*square);

        set.connect(redDotMultiplayerIV.getId(),ConstraintSet.BOTTOM,multiplayerBtn.getId(),ConstraintSet.BOTTOM,3*square-square/2);
        set.connect(redDotMultiplayerIV.getId(),ConstraintSet.LEFT,multiplayerBtn.getId(),ConstraintSet.LEFT,12*square-square/2);

        set.connect(leave.getId(),ConstraintSet.TOP,constraintLayout.getId(),ConstraintSet.TOP,height-heightOffSet-5*square);
        set.connect(leave.getId(),ConstraintSet.LEFT,constraintLayout.getId(),ConstraintSet.LEFT,square);

        set.connect(mAdView.getId(),ConstraintSet.BOTTOM,constraintLayout.getId(),ConstraintSet.BOTTOM,0);
        set.connect(mAdView.getId(),ConstraintSet.LEFT,constraintLayout.getId(),ConstraintSet.LEFT,0);

        set.applyTo(constraintLayout);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });


        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        if(firebaseUser != null){
            providerId="";
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
            loggedIn.setText("Logged in: ");
            userID = firebaseUser.getUid();
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReferenceMy=firebaseDatabase.getReference("User").child(userID);
            storageReference= FirebaseStorage.getInstance().getReference("profile_picture").child(userID);
            for(UserInfo profile : firebaseUser.getProviderData()) {
                if (profile.getProviderId().contains("facebook.com")) {
                    loggedInWithFacebook=true;
                    facebookUserId = profile.getUid();
                    facebookName = profile.getDisplayName();
                }
            }
            databaseReferenceMy.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (dataSnapshot.exists()) {
                        user=dataSnapshot.getValue(User.class);
                        if(loggedInWithFacebook){

                            SharedPreferences spfb = getSharedPreferences(user.getId()+"FACEBOOK", Activity.MODE_PRIVATE);
                            syncFBPhoto=spfb.getBoolean("photo",false);
                            syncFBName=spfb.getBoolean("name",false);

                            if(syncFBPhoto){
                                String photoUrl = "https://graph.facebook.com/" + facebookUserId + "/picture?width=200&height=200";
                                DownloadFacebookImage downloadFacebookImage = new DownloadFacebookImage();
                                downloadFacebookImage.execute(photoUrl);
                            }else{
                                final long SIZE=1024*1024;
                                storageReference.getBytes(SIZE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                    @Override
                                    public void onSuccess(byte[] bytes) {
                                        Bitmap bm = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                                        accountBtn.setImageBitmap(new RoundedCornerBitmap(bm,3*square).getRoundedCornerBitmap());
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        accountBtn.setImageResource(R.drawable.account_box_red_pen);
                                    }
                                });
                            }

                            if(syncFBName) {
                                user.setName(facebookName);
                                userName.setText(user.getName());
                                databaseReferenceMy.setValue(user);
                                ready=true;
                            }
                            else{
                                userName.setText(user.getName());
                                ready=true;
                            }

                        }else{
                            final long SIZE=1024*1024;
                            storageReference.getBytes(SIZE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                @Override
                                public void onSuccess(byte[] bytes) {
                                    Bitmap bm = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                                    accountBtn.setImageBitmap(new RoundedCornerBitmap(bm,3*square).getRoundedCornerBitmap());
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    accountBtn.setImageResource(R.drawable.account_box_red_pen);
                                }
                            });
                            userName.setText(user.getName());
                            ready=true;
                        }


                    } else {
                        user.setId(userID);
                        user.setName("ANONYMOUS");
                        user.setNoOfGames(0);
                        user.setScore(0);
                        user.setIndex(new FightIndex());
                        user.setSinglePlayerMatch(new SinglePlayerMatch());
                        databaseReferenceMy.setValue(user);
                        if(loggedInWithFacebook){

                            SharedPreferences spfb = getSharedPreferences(user.getId()+"FACEBOOK", Activity.MODE_PRIVATE);
                            syncFBPhoto=spfb.getBoolean("photo",false);
                            syncFBName=spfb.getBoolean("name",false);

                            if(syncFBPhoto){
                                String photoUrl = "https://graph.facebook.com/" + facebookUserId + "/picture?width=200&height=200";
                                DownloadFacebookImage downloadFacebookImage = new DownloadFacebookImage();
                                downloadFacebookImage.execute(photoUrl);
                            }else{
                                accountBtn.setImageResource(R.drawable.account_box_red_pen);
                            }


                            if(syncFBName){
                                user.setName(facebookName);
                                userName.setText(user.getName());
                                databaseReferenceMy.setValue(user);
                                ready=true;
                            }

                            else{
                                userName.setText(user.getName());
                                ready=true;
                            }

                        }else{
                            final long SIZE=1024*1024;
                            storageReference.getBytes(SIZE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                @Override
                                public void onSuccess(byte[] bytes) {
                                    Bitmap bm = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                                    accountBtn.setImageBitmap(new RoundedCornerBitmap(bm,3*square).getRoundedCornerBitmap());
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    accountBtn.setImageResource(R.drawable.account_box_red_pen);
                                }
                            });
                            userName.setText(user.getName());
                            ready=true;
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }


        UpdateHelper.with(this).onUpdateNeeded(this).check();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(logIn){
            databaseReferenceMy.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    user=dataSnapshot.getValue(User.class);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            checkMyOpponentAndMove.run();
            multiplayerBtn.setVisibility(View.VISIBLE);
            multiplayerBtn.setClickable(true);
            multiplayerBtn.setOnClickListener(v->{
                if(alertDialogInUse){
                 // do nothing
                }else {
                    if (ready) {
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
                                                    if (user.getIndex().isAccepted() && opponentUser.getIndex().isAccepted() || !user.getIndex().getGameIndex().equals("")) {
                                                        Toast.makeText(MainActivity.this, "You can fight", Toast.LENGTH_LONG).show();
                                                        mHandler.removeCallbacks(checkMyOpponentAndMove);
                                                        Intent intent = new Intent(MainActivity.this, MultiplayerActivity.class);
                                                        startActivity(intent);
                                                    } else if (user.getIndex().isAccepted() && !opponentUser.getIndex().isAccepted()) {
                                                        Toast.makeText(MainActivity.this, "You invited him", Toast.LENGTH_LONG).show();
                                                        alertDialogInUse=true;
                                                        android.app.AlertDialog.Builder mBuilder = new android.app.AlertDialog.Builder(MainActivity.this);
                                                        View mView = getLayoutInflater().inflate(R.layout.alert_dialog_with_two_buttons_and_picture, null);
                                                        mBuilder.setView(mView);
                                                        android.app.AlertDialog dialog = mBuilder.create();
                                                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                                        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
                                                        dialog.getWindow().getDecorView().setSystemUiVisibility(flags);
                                                        dialog.setCancelable(false);
                                                        dialog.setCanceledOnTouchOutside(false);
                                                        TextView title = mView.findViewById(R.id.alert_dialog_title_layout_with_two_buttons_and_picture);
                                                        ImageView photo = mView.findViewById(R.id.alert_dialog_photo_layout_with_two_buttons_and_picture);
                                                        TextView message = mView.findViewById(R.id.alert_dialog_message_layout_with_two_buttons_and_picture);
                                                        Button negativeButton = mView.findViewById(R.id.alert_dialog_left_button_layout_with_two_buttons_and_picture);
                                                        Button positiveButton = mView.findViewById(R.id.alert_dialog_right_button_layout_with_two_buttons_and_picture);
                                                        title.setText("Waiting");
                                                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(2 * square, 2 * square);
                                                        photo.setLayoutParams(params);
                                                        StorageReference sr = FirebaseStorage.getInstance().getReference("profile_picture").child(opponentUser.getId());

                                                        final long SIZE = 1024 * 1024;
                                                        sr.getBytes(SIZE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                                            @Override
                                                            public void onSuccess(byte[] bytes) {
                                                                Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                                                photo.setImageBitmap(new RoundedCornerBitmap(bm, 2 * square).getRoundedCornerBitmap());
                                                            }
                                                        }).addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                photo.setImageResource(R.drawable.account_box_grey);
                                                            }
                                                        });
                                                        message.setText("Do you want to wait for accept from: " + "\n" + opponentUser.getName() + "?");
                                                        negativeButton.setText("NO");
                                                        negativeButton.setOnClickListener(v12 -> {
                                                            dialog.dismiss();
                                                            user.getIndex().setOpponent("");
                                                            user.getIndex().setAccepted(false);
                                                            opponentUser.getIndex().setOpponent("");
                                                            opponentUser.getIndex().setAccepted(false);
                                                            databaseReferenceMy.setValue(user);
                                                            databaseReferenceOpponent.setValue(opponentUser);
                                                            alertDialogInUse=false;
                                                        });
                                                        positiveButton.setText("YES");
                                                        positiveButton.setOnClickListener(v1 -> {
                                                            dialog.dismiss();
                                                            alertDialogInUse=false;
                                                        });
                                                        dialog.show();

                                                    } else if (!user.getIndex().isAccepted() && opponentUser.getIndex().isAccepted()) {
                                                        Toast.makeText(MainActivity.this, "You have to accept", Toast.LENGTH_LONG).show();
                                                        alertDialogInUse=true;
                                                        android.app.AlertDialog.Builder mBuilder = new android.app.AlertDialog.Builder(MainActivity.this);
                                                        View mView = getLayoutInflater().inflate(R.layout.alert_dialog_with_two_buttons_and_picture, null);
                                                        mBuilder.setView(mView);
                                                        android.app.AlertDialog dialog = mBuilder.create();
                                                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                                        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
                                                        dialog.getWindow().getDecorView().setSystemUiVisibility(flags);
                                                        dialog.setCancelable(false);
                                                        dialog.setCanceledOnTouchOutside(false);
                                                        TextView title = mView.findViewById(R.id.alert_dialog_title_layout_with_two_buttons_and_picture);
                                                        ImageView photo = mView.findViewById(R.id.alert_dialog_photo_layout_with_two_buttons_and_picture);
                                                        TextView message = mView.findViewById(R.id.alert_dialog_message_layout_with_two_buttons_and_picture);
                                                        Button negativeButton = mView.findViewById(R.id.alert_dialog_left_button_layout_with_two_buttons_and_picture);
                                                        Button positiveButton = mView.findViewById(R.id.alert_dialog_right_button_layout_with_two_buttons_and_picture);
                                                        title.setText("Accepting");

                                                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(2 * square, 2 * square);
                                                        photo.setLayoutParams(params);
                                                        StorageReference sr = FirebaseStorage.getInstance().getReference("profile_picture").child(opponentUser.getId());

                                                        final long SIZE = 1024 * 1024;
                                                        sr.getBytes(SIZE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                                            @Override
                                                            public void onSuccess(byte[] bytes) {
                                                                Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                                                photo.setImageBitmap(new RoundedCornerBitmap(bm, 2 * square).getRoundedCornerBitmap());
                                                            }
                                                        }).addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                photo.setImageResource(R.drawable.account_box_grey);
                                                            }
                                                        });


                                                        message.setText("Do you want to fight with: " + "\n" + opponentUser.getName() + "?");
                                                        negativeButton.setText("NO");
                                                        negativeButton.setOnClickListener(v12 -> {
                                                            dialog.dismiss();
                                                            TOPIC = "/topics/" + user.getIndex().getOpponent();
                                                            NOTIFICATION_TITLE = user.getName() + " rejected your invitation";
                                                            JSONObject notification = new JSONObject();
                                                            JSONObject notificationBody = new JSONObject();
                                                            try {
                                                                notificationBody.put("title", NOTIFICATION_TITLE);
                                                                notification.put("to", TOPIC);
                                                                notification.put("notification", notificationBody);
                                                            } catch (JSONException e) {
                                                                Log.e(TAG, "onCreate: " + e.getMessage());
                                                            }
                                                            sendNotification(notification);
                                                            user.getIndex().setOpponent("");
                                                            opponentUser.getIndex().setOpponent("");
                                                            opponentUser.getIndex().setAccepted(false);
                                                            databaseReferenceMy.setValue(user);
                                                            databaseReferenceOpponent.setValue(opponentUser);
                                                            alertDialogInUse=false;
                                                        });
                                                        positiveButton.setText("YES");
                                                        positiveButton.setOnClickListener(v1 -> {
                                                            dialog.dismiss();
                                                            alertDialogInUse=false;
                                                            user.getIndex().setAccepted(true);
                                                            user.getIndex().setGameIndex(opponentUser.getId() + user.getId());
                                                            opponentUser.getIndex().setGameIndex(opponentUser.getId() + user.getId());
                                                            databaseReferenceMy.setValue(user);
                                                            databaseReferenceOpponent.setValue(opponentUser);
                                                            mHandler.removeCallbacks(checkMyOpponentAndMove);
                                                            TOPIC = "/topics/" + user.getIndex().getOpponent();
                                                            NOTIFICATION_TITLE = user.getName() + " accepted your invitation";
                                                            JSONObject notification = new JSONObject();
                                                            JSONObject notificationBody = new JSONObject();
                                                            try {
                                                                notificationBody.put("title", NOTIFICATION_TITLE);
                                                                notification.put("to", TOPIC);
                                                                notification.put("notification", notificationBody);
                                                            } catch (JSONException e) {
                                                                Log.e(TAG, "onCreate: " + e.getMessage());
                                                            }
                                                            sendNotification(notification);
                                                            Intent intent = new Intent(MainActivity.this, MultiplayerActivity.class);
                                                            startActivity(intent);
                                                        });
                                                        dialog.show();
                                                    }
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
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });


                    }
                }
            });
        }else{
            loggedIn.setText("niezalogowany");
            multiplayerBtn.setVisibility(View.GONE);
            redDotMultiplayerIV.setVisibility(View.GONE);
            multiplayerBtn.setClickable(false);
            accountBtn.setBackgroundResource(R.drawable.account_box);

        }
    }

    @Override
    protected void onPause() {
        mHandler.removeCallbacks(checkMyOpponentAndMove);
        super.onPause();
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
        if(alertDialogInUse){
            //do nothon
        }else {

            if (logIn) {

                if (ready) {
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
            } else {
                Intent intent = new Intent(getApplicationContext(), ChooseGameLevel.class);
                startActivity(intent);
            }
        }

    }



    public void onClickSignIn(View view) {
        if(alertDialogInUse){
            // do nothing
        }else {
            mHandler.removeCallbacks(checkMyOpponentAndMove);
            Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
            startActivity(intent);
            finish();
        }
    }


    public void ranking(View view) {
        if(alertDialogInUse){
            // do nothing
        }else {
            mHandler.removeCallbacks(checkMyOpponentAndMove);
            Intent intent = new Intent(getApplicationContext(), Scores.class);
            startActivity(intent);
        }
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
        if(alertDialogInUse){
            // do nothing
        }else {
            finish();
        }
    }

    @Override
    public void onUpdateNeeded(String updateUrl) {

        alertDialogInUse=true;
        android.app.AlertDialog.Builder mBuilder = new android.app.AlertDialog.Builder(MainActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.alert_dialog_with_two_buttons,null);
        mBuilder.setView(mView);
        android.app.AlertDialog dialog = mBuilder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        dialog.getWindow().getDecorView().setSystemUiVisibility(flags);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        TextView title = mView.findViewById(R.id.alert_dialog_title_layout_with_two_buttons);
        TextView message = mView.findViewById(R.id.alert_dialog_message_layout_with_two_buttons);
        Button negativeButton = mView.findViewById(R.id.alert_dialog_left_button_layout_with_two_buttons);
        Button positiveButton = mView.findViewById(R.id.alert_dialog_right_button_layout_with_two_buttons);
        title.setText("NEW VERSION AVAILABLE");
        message.setText("Please update");
        negativeButton.setText("NO, THANKS");
        negativeButton.setOnClickListener(v12 -> {
            alertDialogInUse=false;
            dialog.dismiss();
            mHandler.postDelayed(checkMyOpponentAndMove,deelay);
        });
        positiveButton.setText("UPDATE");
        positiveButton.setOnClickListener(v1 -> {
            alertDialogInUse=false;
            dialog.dismiss();
            mHandler.removeCallbacks(checkMyOpponentAndMove);
            redirectStore(updateUrl);
        });
        dialog.show();
    }

    private void redirectStore(String updateUrl){
        final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(updateUrl));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    private class DownloadFacebookImage extends AsyncTask<String, Void, Bitmap>{

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if(bitmap!=null){
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
                byte[] data = baos.toByteArray();
                storageReference.putBytes(data).addOnSuccessListener(taskSnapshot -> {
                    accountBtn.setImageBitmap(new RoundedCornerBitmap(bitmap,3*square).getRoundedCornerBitmap());
                }).addOnFailureListener(e -> {
                    accountBtn.setImageResource(R.drawable.account_box_red_pen);
                });
            }else{
                accountBtn.setImageResource(R.drawable.account_box_red_pen);
            }
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            String photoPath = params[0];
            try {
                URL url = new URL(photoPath);
                HttpURLConnection c = (HttpURLConnection)url.openConnection();
                c.setDoInput(true);
                c.connect();
                InputStream is = c.getInputStream();
                Bitmap img;
                img = BitmapFactory.decodeStream(is);
                is.close();
                c.disconnect();
                return img;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }

        }
    }
}



// TODO last activity time for all users.
// TODO maybe some training... or info...
// TODO test on every virtual devices
// TODO admobs
// TODO upload to GooglePlay