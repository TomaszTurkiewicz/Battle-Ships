package com.example.ships;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Shader;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridLayout;
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
import com.example.ships.classes.BattleFieldForDataBase;
import com.example.ships.classes.FightIndex;
import com.example.ships.classes.GameDifficulty;
import com.example.ships.classes.RoundedCornerBitmap;
import com.example.ships.classes.TileDrawable;
import com.example.ships.classes.User;
import com.example.ships.classes.Winner;
import com.example.ships.drawings.BattleCell;
import com.example.ships.drawings.BattleCellGreenBackground;
import com.example.ships.drawings.BattleCellHiddenBackground;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import static android.view.View.GONE;

public class MultiplayerActivity extends AppCompatActivity implements View.OnTouchListener{

    private static String TAG = "NOTIFICATION TAG";
    private static String FCM_API="https://fcm.googleapis.com/fcm/send";
    private static String TOPIC;
    private static String NOTIFICATION_MESSAGE;
    private static String NOTIFICATION_TITLE;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReferenceMy, databaseReferenceFight, databaseReferenceOpponent;
    private String userID;
    private User user = new User();
    private BattleFieldForDataBase battleFieldForDataBaseMy = new BattleFieldForDataBase();
    private BattleFieldForDataBase battleFieldForDataBaseOpponent = new BattleFieldForDataBase();
    private Handler mHandler = new Handler();
    private Handler mHandler2 = new Handler();
    private int deelay = 1000;
    private int shipFourMastsCounter,
            shipThreeMastsCounterFirst, shipThreeMastsCounterSecond,
            shipTwoMastsCounterFirst, shipTwoMastsCounterSecond, shipTwoMastsCounterThird,
            shipOneMastsCounterFirst, shipOneMastsCounterSecond, shipOneMastsCounterThird, shipOneMastsCounterFourth;
    private boolean battleFieldsSet;
    private boolean playersShown;
    private TextView turnTextView;
    private ImageButton surrenderButton, leave, soundButton;
    private boolean enableTouchListener;
    private GridLayout layoutOpponent, layoutMy;

    private int height, width;
    int[]locationLayout = new int [2];
    private int battleFieldOpponent[][] = new int[10][10];
    private final static int BATTLE_CELL = 0;
    private final static int WATER = 1;
    private final static int SHIP_RED = 2;
    private final static int SHIP_BROWN = 3;
    private TextView tv;
    private String serverKey= "key=" + "AAAAUhITVm0:APA91bGLIOR5L7HQyh64ejoejk-nQFBWP9RxDqtzzjoSXCmROqs7JO_uDDyuW5VuTfJBxtKY_RG8q5_CnpKJsN3qHtVvgiAkuDM2J9T68mk0LzKCcRKgRbj3DQ-A1a8uzZ07wz8OlirQ";
    private String contentType= "application/json";
    private boolean battleFieldUpToDate;
    private ConstraintLayout mainLayout;
    private LinearLayout linearLayoutLettersMy, linearLayoutNumbersMy, linearLayoutLettersOpponent, linearLayoutNumbersOpponent;
    private int marginTop;
    private int marginLeft;
    private LinearLayout fourMasts,threeMastsFirst, threeMastsSecond, twoMastsFirst,twoMastsSecond,twoMastsThird,oneMastsFirst,oneMastsSecond,oneMastsThird,oneMastsFourth;
    private int marginLeftForShips;
    private int marginDown;
    private TextView userName, opponentName;
    private int flags;
    private ImageView userPhoto, opponentPhoto;
    private int square;
    private boolean runChecking;
    private String opponentNameString;
    private boolean fight;
    private boolean alertDialogFlag = false;
    private boolean soundOn;
    private MediaPlayer explosionSound, waterSplashSound, hornSound, bubblesSound, shoutYaySound;
    private TextView borderLine1, borderLine2, borderLine3, borderLine4, borderLine5, borderLine6, borderLine7, borderLine8;
    private InterstitialAd interstitialAd;
    private List<Integer> lastMove = new ArrayList<>();
    private List<Integer> opponentLastMove = new ArrayList<>();

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
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {

            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                    decorView.setSystemUiVisibility(flags);
                }
            }
        });

        setContentView(R.layout.activity_multiplayer);
        mainLayout = findViewById(R.id.multiplayerActivityLayout);
        layoutMy = findViewById(R.id.gridLayoutMultiplayerBattleMy);
        layoutOpponent = findViewById(R.id.gridLayoutMultiplayerBattleOpponent);
        linearLayoutLettersMy = findViewById(R.id.linearLayoutMultiplayerActivityLettersMy);
        linearLayoutLettersOpponent = findViewById(R.id.linearLayoutMultiplayerActivityLettersOpponent);
        linearLayoutNumbersMy = findViewById(R.id.linearLayoutMultiplayerActivityNumbersMy);
        linearLayoutNumbersOpponent = findViewById(R.id.linearLayoutMultiplayerActivityNumbersOpponent);
        fourMasts = findViewById(R.id.linearLayoutMultiplayerShipFourMasts);
        threeMastsFirst = findViewById(R.id.linearLayoutMultiplayerShipThreeMastsFirst);
        threeMastsSecond = findViewById(R.id.linearLayoutMultiplayerShipThreeMastsSecond);
        twoMastsFirst = findViewById(R.id.linearLayoutMultiplayerShipTwoMastsFirst);
        twoMastsSecond = findViewById(R.id.linearLayoutMultiplayerShipTwoMastsSecond);
        twoMastsThird = findViewById(R.id.linearLayoutMultiplayerShipTwoMastsThird);
        oneMastsFirst = findViewById(R.id.linearLayoutMultiplayerShipOneMastsFirst);
        oneMastsSecond = findViewById(R.id.linearLayoutMultiplayerShipOneMastsSecond);
        oneMastsThird = findViewById(R.id.linearLayoutMultiplayerShipOneMastsThird);
        oneMastsFourth = findViewById(R.id.linearLayoutMultiplayerShipOneMastsFourth);
        surrenderButton = findViewById(R.id.surrenderMultiplayer);
        surrenderButton.setBackgroundResource(R.drawable.grid_off);
        turnTextView = findViewById(R.id.turn);
        turnTextView.setVisibility(GONE);
        leave = findViewById(R.id.leaveMultiPlayer);
        leave.setBackgroundResource(R.drawable.back);
        soundButton=findViewById(R.id.soundMultiplayerPlayer);
        soundButton.setBackgroundResource(R.drawable.sound);
        userName = findViewById(R.id.userNameMultiplayer);
        opponentName = findViewById(R.id.opponentNameMultiplayer);
        userPhoto = findViewById(R.id.user_photo_multiplayer);
        opponentPhoto = findViewById(R.id.opponent_photo_multiplayer);
        borderLine1=findViewById(R.id.borderLine1multi);
        borderLine2=findViewById(R.id.borderLine2multi);
        borderLine3=findViewById(R.id.borderLine3multi);
        borderLine4=findViewById(R.id.borderLine4multi);
        borderLine5=findViewById(R.id.borderLine5multi);
        borderLine6=findViewById(R.id.borderLine6multi);
        borderLine7=findViewById(R.id.borderLine7multi);
        borderLine8=findViewById(R.id.borderLine8multi);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        userID = firebaseUser.getUid();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReferenceMy = firebaseDatabase.getReference("User").child(userID);
        surrenderButton.setVisibility(GONE);
        enableTouchListener = false;
        battleFieldsSet = false;
        battleFieldUpToDate = false;
        playersShown = false;
        runChecking=false;
        fight=true;
        lastMove.clear();
        opponentLastMove.clear();

        SharedPreferences sp = getSharedPreferences("VALUES", Activity.MODE_PRIVATE);
        square = sp.getInt("square", -1);
        int screenWidth = sp.getInt("width", -1);
        int screenHeight = sp.getInt("height", -1);
        int screenWidthOffSet = sp.getInt("widthOffSet", -1);
        int screenHeightOffSet = sp.getInt("heightOffSet", -1);
        float textSize = (square * 9) / 10;
        marginTop = 4 * square;
        marginLeft = screenWidth - screenWidthOffSet - 13 * square;
        mainLayout.setBackground(new TileDrawable(getDrawable(R.drawable.background_x), Shader.TileMode.REPEAT, square));


        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(2 * square, 2 * square);
        ConstraintLayout.LayoutParams params1 = new ConstraintLayout.LayoutParams(10 * square, 10 * square);
        ConstraintLayout.LayoutParams params2 = new ConstraintLayout.LayoutParams(10 * square, square);
        ConstraintLayout.LayoutParams params3 = new ConstraintLayout.LayoutParams(square, 10 * square);
        ConstraintLayout.LayoutParams params4 = new ConstraintLayout.LayoutParams(10 * square, 10 * square);
        ConstraintLayout.LayoutParams params5 = new ConstraintLayout.LayoutParams(10 * square, square);
        ConstraintLayout.LayoutParams params6 = new ConstraintLayout.LayoutParams(square, 10 * square);
        ConstraintLayout.LayoutParams params7 = new ConstraintLayout.LayoutParams(4 * square, square);
        ConstraintLayout.LayoutParams params8 = new ConstraintLayout.LayoutParams(3 * square, square);
        ConstraintLayout.LayoutParams params9 = new ConstraintLayout.LayoutParams(3 * square, square);
        ConstraintLayout.LayoutParams params10 = new ConstraintLayout.LayoutParams(2 * square, square);
        ConstraintLayout.LayoutParams params11 = new ConstraintLayout.LayoutParams(2 * square, square);
        ConstraintLayout.LayoutParams params12 = new ConstraintLayout.LayoutParams(2 * square, square);
        ConstraintLayout.LayoutParams params13 = new ConstraintLayout.LayoutParams(square, square);
        ConstraintLayout.LayoutParams params14 = new ConstraintLayout.LayoutParams(square, square);
        ConstraintLayout.LayoutParams params15 = new ConstraintLayout.LayoutParams(square, square);
        ConstraintLayout.LayoutParams params16 = new ConstraintLayout.LayoutParams(square, square);
        ConstraintLayout.LayoutParams params17 = new ConstraintLayout.LayoutParams(2 * square, 2 * square);
        ConstraintLayout.LayoutParams params18 = new ConstraintLayout.LayoutParams(8 * square, 2 * square);
        ConstraintLayout.LayoutParams params19 = new ConstraintLayout.LayoutParams(8 * square, 2 * square);
        ConstraintLayout.LayoutParams params20 = new ConstraintLayout.LayoutParams(2 * square, 2 * square);
        ConstraintLayout.LayoutParams params21 = new ConstraintLayout.LayoutParams(2 * square, 2 * square);
        ConstraintLayout.LayoutParams params22 = new ConstraintLayout.LayoutParams(2 * square, 2 * square);
        ConstraintLayout.LayoutParams params31 = new ConstraintLayout.LayoutParams(10 * square+square/20, square/20);
        ConstraintLayout.LayoutParams params33 = new ConstraintLayout.LayoutParams(10 * square+square/20, square/20);
        ConstraintLayout.LayoutParams params32 = new ConstraintLayout.LayoutParams(square/20, 10*square+square/20);
        ConstraintLayout.LayoutParams params34 = new ConstraintLayout.LayoutParams(square/20, 10*square+square/20);
        ConstraintLayout.LayoutParams params35 = new ConstraintLayout.LayoutParams(10 * square+square/20, square/20);
        ConstraintLayout.LayoutParams params37 = new ConstraintLayout.LayoutParams(10 * square+square/20, square/20);
        ConstraintLayout.LayoutParams params36 = new ConstraintLayout.LayoutParams(square/20, 10*square+square/20);
        ConstraintLayout.LayoutParams params38 = new ConstraintLayout.LayoutParams(square/20, 10*square+square/20);

        borderLine1.setLayoutParams(params31);
        borderLine2.setLayoutParams(params32);
        borderLine3.setLayoutParams(params33);
        borderLine4.setLayoutParams(params34);
        borderLine5.setLayoutParams(params35);
        borderLine6.setLayoutParams(params36);
        borderLine7.setLayoutParams(params37);
        borderLine8.setLayoutParams(params38);
        surrenderButton.setLayoutParams(params);
        layoutMy.setLayoutParams(params1);
        linearLayoutLettersMy.setLayoutParams(params2);
        linearLayoutNumbersMy.setLayoutParams(params3);
        layoutOpponent.setLayoutParams(params4);
        linearLayoutLettersOpponent.setLayoutParams(params5);
        linearLayoutNumbersOpponent.setLayoutParams(params6);
        fourMasts.setLayoutParams(params7);
        threeMastsFirst.setLayoutParams(params8);
        threeMastsSecond.setLayoutParams(params9);
        twoMastsFirst.setLayoutParams(params10);
        twoMastsSecond.setLayoutParams(params11);
        twoMastsThird.setLayoutParams(params12);
        oneMastsFirst.setLayoutParams(params13);
        oneMastsSecond.setLayoutParams(params14);
        oneMastsThird.setLayoutParams(params15);
        oneMastsFourth.setLayoutParams(params16);
        leave.setLayoutParams(params17);
        userName.setLayoutParams(params18);
        userName.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        opponentName.setLayoutParams(params19);
        opponentName.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        userPhoto.setLayoutParams(params20);
        opponentPhoto.setLayoutParams(params21);
        soundButton.setLayoutParams(params22);

        for (int i = 0; i < 10; i++) {
            TextView tv = (TextView) linearLayoutLettersMy.getChildAt(i);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        }
        for (int i = 0; i < 10; i++) {
            TextView tv = (TextView) linearLayoutNumbersMy.getChildAt(i);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        }
        for (int i = 0; i < 10; i++) {
            TextView tv = (TextView) linearLayoutLettersOpponent.getChildAt(i);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        }
        for (int i = 0; i < 10; i++) {
            TextView tv = (TextView) linearLayoutNumbersOpponent.getChildAt(i);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        }

        marginDown = screenHeight - screenHeightOffSet - 2 * square;

        if (((screenWidth - screenWidthOffSet) / square) % 2 == 0) {
            marginLeftForShips = (screenWidth - screenWidthOffSet) / 2 - 15 * square;
        } else {
            marginLeftForShips = (screenWidth - screenWidthOffSet - square) / 2 - 15 * square;
        }

        for(int i=0;i<4;i++){
            TextView tv = (TextView) fourMasts.getChildAt(i);
            tv.setBackground(new BattleCell(MultiplayerActivity.this,square));
        }
        for(int i=0;i<3;i++){
            TextView tv = (TextView) threeMastsFirst.getChildAt(i);
            tv.setBackground(new BattleCell(MultiplayerActivity.this,square));
        }
        for(int i=0;i<3;i++){
            TextView tv = (TextView) threeMastsSecond.getChildAt(i);
            tv.setBackground(new BattleCell(MultiplayerActivity.this,square));
        }
        for(int i=0;i<2;i++){
            TextView tv = (TextView) twoMastsFirst.getChildAt(i);
            tv.setBackground(new BattleCell(MultiplayerActivity.this,square));
        }
        for(int i=0;i<2;i++){
            TextView tv = (TextView) twoMastsSecond.getChildAt(i);
            tv.setBackground(new BattleCell(MultiplayerActivity.this,square));
        }
        for(int i=0;i<2;i++){
            TextView tv = (TextView) twoMastsThird.getChildAt(i);
            tv.setBackground(new BattleCell(MultiplayerActivity.this,square));
        }
        TextView tv1 = (TextView) oneMastsFirst.getChildAt(0);
        tv1.setBackground(new BattleCell(MultiplayerActivity.this,square));

        TextView tv2 = (TextView) oneMastsSecond.getChildAt(0);
        tv2.setBackground(new BattleCell(MultiplayerActivity.this,square));

        TextView tv3 = (TextView) oneMastsThird.getChildAt(0);
        tv3.setBackground(new BattleCell(MultiplayerActivity.this,square));

        TextView tv4 = (TextView) oneMastsFourth.getChildAt(0);
        tv4.setBackground(new BattleCell(MultiplayerActivity.this,square));

        ConstraintSet set = new ConstraintSet();
        set.clone(mainLayout);

        set.connect(surrenderButton.getId(), ConstraintSet.TOP, mainLayout.getId(), ConstraintSet.TOP, square);
        set.connect(surrenderButton.getId(), ConstraintSet.LEFT, mainLayout.getId(), ConstraintSet.LEFT, square);

        set.connect(soundButton.getId(), ConstraintSet.TOP, mainLayout.getId(), ConstraintSet.TOP, 7*square);
        set.connect(soundButton.getId(), ConstraintSet.LEFT, mainLayout.getId(), ConstraintSet.LEFT, square);

        set.connect(layoutMy.getId(), ConstraintSet.TOP, mainLayout.getId(), ConstraintSet.TOP, 4 * square);
        set.connect(layoutMy.getId(), ConstraintSet.LEFT, mainLayout.getId(), ConstraintSet.LEFT, 5 * square);

        set.connect(borderLine1.getId(),ConstraintSet.BOTTOM, layoutMy.getId(),ConstraintSet.TOP,0);
        set.connect(borderLine1.getId(),ConstraintSet.LEFT, layoutMy.getId(),ConstraintSet.LEFT,0);

        set.connect(borderLine2.getId(),ConstraintSet.TOP, borderLine1.getId(),ConstraintSet.TOP,0);
        set.connect(borderLine2.getId(),ConstraintSet.RIGHT, borderLine1.getId(),ConstraintSet.LEFT,0);

        set.connect(borderLine3.getId(),ConstraintSet.TOP, borderLine2.getId(),ConstraintSet.BOTTOM,0);
        set.connect(borderLine3.getId(),ConstraintSet.LEFT, borderLine2.getId(),ConstraintSet.LEFT,0);

        set.connect(borderLine4.getId(),ConstraintSet.BOTTOM, borderLine3.getId(),ConstraintSet.BOTTOM,0);
        set.connect(borderLine4.getId(),ConstraintSet.LEFT, borderLine3.getId(),ConstraintSet.RIGHT,0);

        set.connect(linearLayoutLettersMy.getId(), ConstraintSet.BOTTOM, layoutMy.getId(), ConstraintSet.TOP, 0);
        set.connect(linearLayoutLettersMy.getId(), ConstraintSet.LEFT, layoutMy.getId(), ConstraintSet.LEFT, 0);

        set.connect(linearLayoutNumbersMy.getId(), ConstraintSet.TOP, layoutMy.getId(), ConstraintSet.TOP, 0);
        set.connect(linearLayoutNumbersMy.getId(), ConstraintSet.RIGHT, layoutMy.getId(), ConstraintSet.LEFT, 0);

        set.connect(layoutOpponent.getId(), ConstraintSet.TOP, mainLayout.getId(), ConstraintSet.TOP, marginTop);
        set.connect(layoutOpponent.getId(), ConstraintSet.LEFT, mainLayout.getId(), ConstraintSet.LEFT, marginLeft);

        set.connect(borderLine5.getId(),ConstraintSet.BOTTOM, layoutOpponent.getId(),ConstraintSet.TOP,0);
        set.connect(borderLine5.getId(),ConstraintSet.LEFT, layoutOpponent.getId(),ConstraintSet.LEFT,0);

        set.connect(borderLine6.getId(),ConstraintSet.TOP, borderLine5.getId(),ConstraintSet.TOP,0);
        set.connect(borderLine6.getId(),ConstraintSet.RIGHT, borderLine5.getId(),ConstraintSet.LEFT,0);

        set.connect(borderLine7.getId(),ConstraintSet.TOP, borderLine6.getId(),ConstraintSet.BOTTOM,0);
        set.connect(borderLine7.getId(),ConstraintSet.LEFT, borderLine6.getId(),ConstraintSet.LEFT,0);

        set.connect(borderLine8.getId(),ConstraintSet.BOTTOM, borderLine7.getId(),ConstraintSet.BOTTOM,0);
        set.connect(borderLine8.getId(),ConstraintSet.LEFT, borderLine7.getId(),ConstraintSet.RIGHT,0);

        set.connect(linearLayoutLettersOpponent.getId(), ConstraintSet.BOTTOM, layoutOpponent.getId(), ConstraintSet.TOP, 0);
        set.connect(linearLayoutLettersOpponent.getId(), ConstraintSet.LEFT, layoutOpponent.getId(), ConstraintSet.LEFT, 0);

        set.connect(linearLayoutNumbersOpponent.getId(), ConstraintSet.TOP, layoutOpponent.getId(), ConstraintSet.TOP, 0);
        set.connect(linearLayoutNumbersOpponent.getId(), ConstraintSet.RIGHT, layoutOpponent.getId(), ConstraintSet.LEFT, 0);

        set.connect(fourMasts.getId(), ConstraintSet.TOP, mainLayout.getId(), ConstraintSet.TOP, marginDown);
        set.connect(fourMasts.getId(), ConstraintSet.LEFT, mainLayout.getId(), ConstraintSet.LEFT, marginLeftForShips);

        set.connect(threeMastsFirst.getId(), ConstraintSet.TOP, fourMasts.getId(), ConstraintSet.TOP, 0);
        set.connect(threeMastsFirst.getId(), ConstraintSet.LEFT, fourMasts.getId(), ConstraintSet.RIGHT, square);

        set.connect(threeMastsSecond.getId(), ConstraintSet.TOP, threeMastsFirst.getId(), ConstraintSet.TOP, 0);
        set.connect(threeMastsSecond.getId(), ConstraintSet.LEFT, threeMastsFirst.getId(), ConstraintSet.RIGHT, square);

        set.connect(twoMastsFirst.getId(), ConstraintSet.TOP, threeMastsSecond.getId(), ConstraintSet.TOP, 0);
        set.connect(twoMastsFirst.getId(), ConstraintSet.LEFT, threeMastsSecond.getId(), ConstraintSet.RIGHT, square);

        set.connect(twoMastsSecond.getId(), ConstraintSet.TOP, twoMastsFirst.getId(), ConstraintSet.TOP, 0);
        set.connect(twoMastsSecond.getId(), ConstraintSet.LEFT, twoMastsFirst.getId(), ConstraintSet.RIGHT, square);

        set.connect(twoMastsThird.getId(), ConstraintSet.TOP, twoMastsSecond.getId(), ConstraintSet.TOP, 0);
        set.connect(twoMastsThird.getId(), ConstraintSet.LEFT, twoMastsSecond.getId(), ConstraintSet.RIGHT, square);

        set.connect(oneMastsFirst.getId(), ConstraintSet.TOP, twoMastsThird.getId(), ConstraintSet.TOP, 0);
        set.connect(oneMastsFirst.getId(), ConstraintSet.LEFT, twoMastsThird.getId(), ConstraintSet.RIGHT, square);

        set.connect(oneMastsSecond.getId(), ConstraintSet.TOP, oneMastsFirst.getId(), ConstraintSet.TOP, 0);
        set.connect(oneMastsSecond.getId(), ConstraintSet.LEFT, oneMastsFirst.getId(), ConstraintSet.RIGHT, square);

        set.connect(oneMastsThird.getId(), ConstraintSet.TOP, oneMastsSecond.getId(), ConstraintSet.TOP, 0);
        set.connect(oneMastsThird.getId(), ConstraintSet.LEFT, oneMastsSecond.getId(), ConstraintSet.RIGHT, square);

        set.connect(oneMastsFourth.getId(), ConstraintSet.TOP, oneMastsThird.getId(), ConstraintSet.TOP, 0);
        set.connect(oneMastsFourth.getId(), ConstraintSet.LEFT, oneMastsThird.getId(), ConstraintSet.RIGHT, square);

        set.connect(leave.getId(), ConstraintSet.TOP, mainLayout.getId(), ConstraintSet.TOP, marginDown - 2 * square);
        set.connect(leave.getId(), ConstraintSet.LEFT, mainLayout.getId(), ConstraintSet.LEFT, square);

        set.connect(userPhoto.getId(), ConstraintSet.BOTTOM, layoutMy.getId(), ConstraintSet.TOP, square);
        set.connect(userPhoto.getId(), ConstraintSet.LEFT, layoutMy.getId(), ConstraintSet.LEFT, 0);

        set.connect(userName.getId(), ConstraintSet.BOTTOM, layoutMy.getId(), ConstraintSet.TOP, square);
        set.connect(userName.getId(), ConstraintSet.LEFT, userPhoto.getId(), ConstraintSet.RIGHT, 0);

        set.connect(opponentPhoto.getId(), ConstraintSet.BOTTOM, layoutOpponent.getId(), ConstraintSet.TOP, square);
        set.connect(opponentPhoto.getId(), ConstraintSet.LEFT, layoutOpponent.getId(), ConstraintSet.LEFT, 0);

        set.connect(opponentName.getId(), ConstraintSet.BOTTOM, layoutOpponent.getId(), ConstraintSet.TOP, square);
        set.connect(opponentName.getId(), ConstraintSet.LEFT, opponentPhoto.getId(), ConstraintSet.RIGHT, 0);

        set.applyTo(mainLayout);

        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getString(R.string.admob_big));
        interstitialAd.loadAd(new AdRequest.Builder().build());

        height = square;
        width = square;

        SharedPreferences spsound = getSharedPreferences("Sound", Activity.MODE_PRIVATE);
        soundOn=spsound.getBoolean("sound",false);
        updateSoundButton();

        layoutOpponent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                layoutOpponent.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                layoutOpponent.getLocationOnScreen(locationLayout);
            }
        });
        layoutOpponent.setOnTouchListener(this);

        game.run();
        checkWinner.run();

    }

    private Runnable game = new Runnable() {
        @Override
        public void run() {
            if(!playersShown){
                showPlayers();
            }else{
                createFieldsandPlay();
            }
        }
    };

    private void createFieldsandPlay() {
        if(!battleFieldsSet){
            createFields();

        }else{
            if(fight) {
                fight();
            }else;
        }
    }

    private void fight(){
        databaseReferenceFight.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String turn = (String)dataSnapshot.child("turn").getValue();
                if(user.getId().equals(turn)){
                    // my move
                    userName.setTextColor(getColor(R.color.pen));
                    opponentName.setTextColor(getColor(R.color.pen_red));
                    lastMove.clear();
                    battleFieldForDataBaseMy = dataSnapshot.child(user.getId()).getValue(BattleFieldForDataBase.class);
                    battleFieldForDataBaseMy.listToField();

                    opponentLastMove=battleFieldForDataBaseMy.getLastMove();

                    showOpponentBattleField();
                    hideBattleFiledAvailableMy();
                    enableTouchListener=true;


                }else{
                    // not my move
                    userName.setTextColor(getColor(R.color.pen_red));
                    opponentName.setTextColor(getColor(R.color.pen));

                    battleFieldForDataBaseMy = dataSnapshot.child(user.getId()).getValue(BattleFieldForDataBase.class);
                    battleFieldForDataBaseMy.listToField();
                    opponentLastMove=battleFieldForDataBaseMy.getLastMove();
                    hideBattleFieldOpponent();
                    showMyBattleField();
                    mHandler.postDelayed(game, deelay);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void showMyBattleField() {
        for(int i =0;i<10;i++){
            for(int j = 0; j<10;j++){
                //jest statek i został trafiony
                if(battleFieldForDataBaseMy.showBattleField().getBattleField(i,j).isShip()
                        &&battleFieldForDataBaseMy.showBattleField().getBattleField(i,j).isHit()){
                    displayShipCell((TextView) layoutMy.getChildAt(10*i+j));
                }

                // woda i została trafiony
                else if(!battleFieldForDataBaseMy.showBattleField().getBattleField(i,j).isShip()
                        &&battleFieldForDataBaseMy.showBattleField().getBattleField(i,j).isHit()){
                    displayWaterCell((TextView) layoutMy.getChildAt(10*i+j));
                }

                // jest statek i nie został trafiony
                else if(battleFieldForDataBaseMy.showBattleField().getBattleField(i,j).isShip()
                        &&!battleFieldForDataBaseMy.showBattleField().getBattleField(i,j).isHit()){
                    displayWidmoShip((TextView) layoutMy.getChildAt(10*i+j));
                }
                // nie ma statku i nie został trafiony
                else if(!battleFieldForDataBaseMy.showBattleField().getBattleField(i,j).isShip()
                        &&!battleFieldForDataBaseMy.showBattleField().getBattleField(i,j).isHit()){
                    displayBattleCell((TextView) layoutMy.getChildAt(10*i+j));
                }
                else;
            }
        }
        if(opponentLastMove!=null){
            for(int i=0; i<opponentLastMove.size();i++){
                int move = opponentLastMove.get(i);
                if(battleFieldForDataBaseMy.getBattleFieldList().get(move).isShip()){
                    displayShipHit((TextView)layoutMy.getChildAt(move));
                }else{
                    displayWaterHit((TextView)layoutMy.getChildAt(move));
                }
            }
        }
    }

    private void displayWaterHit(TextView childAt) {
        childAt.setBackground(getResources().getDrawable(R.drawable.water_cell_x_red_field));
    }

    private void displayShipHit(TextView childAt) {

        childAt.setBackground(getResources().getDrawable(R.drawable.ship_cell_x_red_field));
    }

    private void displayWidmoShip(TextView textView) {
        textView.setBackground(getResources().getDrawable(R.drawable.battle_cell_widmo_ship_x));
    }

    private void displayWaterCell(TextView textView) {
        textView.setBackground(getResources().getDrawable(R.drawable.water_cell_x));
    }

    private void displayBattleCell(TextView textView) {
        textView.setBackground(new BattleCell(MultiplayerActivity.this,square));
    }

    private void displayShipCell(TextView textView) {
        textView.setBackground(getResources().getDrawable(R.drawable.battle_cell_ship_sunk_x));
    }

    private void createFields() {
        databaseReferenceFight.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists()){
                    // create database
                    databaseReferenceFight.child("turn").setValue(user.getIndex().getOpponent());
                    databaseReferenceFight.child("winner").setValue(new Winner());
                    databaseReferenceFight.child("ready").setValue(false);
                    Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
                    long timeInMili = calendar.getTimeInMillis();
                    databaseReferenceFight.child("time").setValue(timeInMili);
                    askForCreatingGame();
                }else{
                    // read if it is set
                    runChecking=true;
                    boolean ready = (boolean) dataSnapshot.child("ready").getValue();
                    if(!ready){

                        if(!dataSnapshot.child(user.getId()).exists()){
                            // stwórz moją planszę
                            askForCreatingGame();

                        }else{
                            // plansza jest stworzona teraz difficulty
                            if(!battleFieldUpToDate){
                                battleFieldForDataBaseMy = dataSnapshot.child(user.getId()).getValue(BattleFieldForDataBase.class);
                                battleFieldForDataBaseMy.listToField();
                                battleFieldUpToDate=true;
                            }
                            hideBattleFiledAvailableMy();
                            if(dataSnapshot.child(user.getId()).child("difficulty").child("set").getValue().equals(false)){
                                chooseDifficulty();
                            }else{
                                // ustaw surrender, textview i sprawdzaj przeciwnika planszę
                                surrenderButton.setVisibility(View.VISIBLE);
                                turnTextView.setText("WAITING FOR "+opponentNameString);
                                turnTextView.setVisibility(View.VISIBLE);

                                if(dataSnapshot.child(user.getIndex().getOpponent()).exists()){
                                    // plansza przeciwnika istnieje to spradz
                                    boolean opponentReady = (boolean) dataSnapshot.child(user.getIndex().getOpponent()).child("difficulty").child("set").getValue();
                                    if(opponentReady){
                                        // przeciwnik gotowy
                                        battleFieldForDataBaseOpponent = dataSnapshot.child(user.getIndex().getOpponent()).getValue(BattleFieldForDataBase.class);
                                        battleFieldForDataBaseOpponent.listToField();
                                        battleFieldForDataBaseMy.listToField();
                                        turnTextView.setVisibility(GONE);
                                        battleFieldsSet=true;
                                        initializeOpponentArrayBattleField();
                                        checkShipCounters();
                                        hideBattleFieldOpponent();
                                        updateShipsHit();
                                        databaseReferenceFight.child("ready").setValue(true);
                                        mHandler.postDelayed(game, deelay);

                                    }else{
                                        // przeciwnik jeszcze nie gotowy
                                        mHandler.postDelayed(game,deelay);
                                    }
                                }else{
                                    // nie istnieje jeszcze to wykonaj pętlę

                                    mHandler.postDelayed(game,deelay);
                                }

                            }
                        }
                    }else{
                        // zczytaj z bazy danych i rozpocznij grę
                        battleFieldForDataBaseOpponent = dataSnapshot.child(user.getIndex().getOpponent()).getValue(BattleFieldForDataBase.class);
                        battleFieldForDataBaseOpponent.listToField();
                        lastMove=battleFieldForDataBaseOpponent.getLastMove();
                        battleFieldForDataBaseMy = dataSnapshot.child(user.getId()).getValue(BattleFieldForDataBase.class);
                        battleFieldForDataBaseMy.listToField();
                        surrenderButton.setVisibility(View.VISIBLE);
                        battleFieldsSet=true;
                        hideBattleFiledAvailableMy();
                        initializeOpponentArrayBattleField();
                        checkShipCounters();
                        hideBattleFieldOpponent();
                        updateShipsHit();
                        mHandler.postDelayed(game,deelay);
                    }
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
                    updateCounters(numberOfMasts,shipNumber,false);
                }
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

    private void chooseDifficulty() {
        alertDialogFlag=true;
        android.app.AlertDialog.Builder mBuilder = new android.app.AlertDialog.Builder(MultiplayerActivity.this);
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
        title.setText("Difficulty");
        message.setText("choose game difficulty");
        negativeButton.setText("EASY");
        negativeButton.setOnClickListener(v12 -> {
            dialog.dismiss();
            alertDialogFlag=false;
            battleFieldForDataBaseMy.getDifficulty().setEasy(true);
            battleFieldForDataBaseMy.getDifficulty().setSet(true);
            databaseReferenceFight.child(user.getId()).child("difficulty").setValue(battleFieldForDataBaseMy.getDifficulty());
            mHandler.postDelayed(game,deelay);
        });
        positiveButton.setText("NORMAL");
        positiveButton.setOnClickListener(v1 -> {
            dialog.dismiss();
            alertDialogFlag=false;
            battleFieldForDataBaseMy.getDifficulty().setEasy(false);
            battleFieldForDataBaseMy.getDifficulty().setSet(true);
            databaseReferenceFight.child(user.getId()).child("difficulty").setValue(battleFieldForDataBaseMy.getDifficulty());
            mHandler.postDelayed(game,deelay);
        });
        dialog.show();
    }

    private void askForCreatingGame() {
        alertDialogFlag=true;
        android.app.AlertDialog.Builder mBuilder = new android.app.AlertDialog.Builder(MultiplayerActivity.this);
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
        title.setText("CREATE BATTLE FIELD");
        message.setText("How would you like to do it?");
        negativeButton.setText("RANDOM");
        negativeButton.setOnClickListener(v12 -> {
            dialog.dismiss();
            alertDialogFlag=false;
            battleFieldForDataBaseMy.create();
            battleFieldUpToDate=true;
            databaseReferenceFight.child(user.getId()).setValue(battleFieldForDataBaseMy);
            int noOfGames = user.getNoOfGames();
            noOfGames = noOfGames+1;
            user.setNoOfGames(noOfGames);
            databaseReferenceMy.setValue(user);
            mHandler.postDelayed(game,deelay);
        });
        positiveButton.setText("BY MYSELF");
        positiveButton.setOnClickListener(v1 -> {
            mHandler2.removeCallbacks(checkWinner);
            mHandler.removeCallbacks(game);
            dialog.dismiss();
            GameDifficulty.getInstance().setMultiplayerMode(true);
            Intent intent = new Intent(MultiplayerActivity.this, CreateBattleField.class);
            startActivity(intent);
            finish();
        });
        dialog.show();
    }

    private void showPlayers() {
        hideBattleFieldOpponent();
        hideBattleFiledAvailableMy();
        databaseReferenceMy.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                userName.setText(user.getName());
                StorageReference sr = FirebaseStorage.getInstance().getReference("profile_picture").child(user.getId());
                final long SIZE = 1024*1024;
                sr.getBytes(SIZE).addOnSuccessListener(bytes -> {
                    Bitmap bm = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                    userPhoto.setImageBitmap(new RoundedCornerBitmap(bm,2*square).getRoundedCornerBitmap());
                }).addOnFailureListener(e -> userPhoto.setImageResource(R.drawable.account_box_grey));
                if(user.getIndex().getOpponent().isEmpty()){
                    finish();
                }else{
                    databaseReferenceOpponent=firebaseDatabase.getReference("User").child(user.getIndex().getOpponent());
                    databaseReferenceFight = firebaseDatabase.getReference("Battle").child(user.getIndex().getGameIndex());
                    databaseReferenceOpponent.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                User opponent = dataSnapshot.getValue(User.class);
                                opponentName.setText(opponent.getName());
                                opponentNameString=opponent.getName();
                                StorageReference sr = FirebaseStorage.getInstance().getReference("profile_picture").child(opponent.getId());
                                final long SIZE = 1024*1024;
                                sr.getBytes(SIZE).addOnSuccessListener(bytes -> {
                                    Bitmap bm = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                                    opponentPhoto.setImageBitmap(new RoundedCornerBitmap(bm,2*square).getRoundedCornerBitmap());
                                    playersShown=true;
                                    mHandler.postDelayed(game,deelay);
                                }).addOnFailureListener(e -> {
                                    opponentPhoto.setImageResource(R.drawable.account_box_grey);
                                    playersShown=true;
                                    mHandler.postDelayed(game,deelay);
                                });
                            }else{
                                FightIndex fightIndex = new FightIndex();
                                user.setIndex(fightIndex);
                                databaseReferenceMy.setValue(user);
                                finish();
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

    private void hideBattleFieldOpponent() {
        for(int i =0;i<10;i++){
            for(int j = 0; j<10;j++){
                if(battleFieldOpponent[i][j]==SHIP_BROWN){
                    layoutOpponent.getChildAt(i*10+j).setBackground(getDrawable(R.drawable.battle_cell_ship_sunk_x_hidden));
                }
                else if(battleFieldOpponent[i][j]==SHIP_RED){
                    layoutOpponent.getChildAt(i*10+j).setBackground(getDrawable(R.drawable.battle_cell_ship_sunk_x_hidden));
                }else if(battleFieldOpponent[i][j]==WATER){
                    layoutOpponent.getChildAt(i*10+j).setBackground(getDrawable(R.drawable.water_cell_x_hidden));
                }else{
                    layoutOpponent.getChildAt(i*10+j).setBackground(new BattleCellHiddenBackground(MultiplayerActivity.this,square));
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
                    displayShipCellHidden((TextView) layoutMy.getChildAt(10*i+j));
                }

                // woda i została trafiony
                else if(!battleFieldForDataBaseMy.showBattleField().getBattleField(i,j).isShip()
                        && battleFieldForDataBaseMy.showBattleField().getBattleField(i,j).isHit()){
                    displayWaterCellHidden((TextView) layoutMy.getChildAt(10*i+j));
                }

                // jest statek i nie został trafiony
                else if(battleFieldForDataBaseMy.showBattleField().getBattleField(i,j).isShip()
                        &&!battleFieldForDataBaseMy.showBattleField().getBattleField(i,j).isHit()){
                    displayWidmoShipHidden((TextView) layoutMy.getChildAt(10*i+j));
                }
                // nie ma statku i nie został trafiony
                else if(!battleFieldForDataBaseMy.showBattleField().getBattleField(i,j).isShip()
                        &&!battleFieldForDataBaseMy.showBattleField().getBattleField(i,j).isHit()){
                    displayBattleCellHidden((TextView) layoutMy.getChildAt(10*i+j));
                }

                else;
            }
        }

        if(opponentLastMove!=null){
            for(int i=0; i<opponentLastMove.size();i++){
                int move = opponentLastMove.get(i);
                if(battleFieldForDataBaseMy.getBattleFieldList().get(move).isShip()){
                    displayShipHit((TextView)layoutMy.getChildAt(move));
                }else{
                    displayWaterHit((TextView)layoutMy.getChildAt(move));
                }
            }
        }
    }

    private void displayShipCellHidden(TextView TextView){
        TextView.setBackground(getResources().getDrawable(R.drawable.battle_cell_ship_sunk_x_hidden));
    }

    private void displayWaterCellHidden(TextView TextView){
        TextView.setBackground(getResources().getDrawable(R.drawable.water_cell_x_hidden));
    }

    private void displayWidmoShipHidden(TextView TextView){
        TextView.setBackground(getResources().getDrawable(R.drawable.battle_cell_widmo_ship_x_hidden));
    }

    private void displayBattleCellHidden(TextView TextView){
        TextView.setBackground(new BattleCellHiddenBackground(MultiplayerActivity.this,square));
    }

    private Runnable checkWinner = new Runnable() {
        @Override
        public void run() {
            if(runChecking){
                databaseReferenceFight.child("winner").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            Winner w = dataSnapshot.getValue(Winner.class);
                            if(w.getWinner().equals("")){
                                 mHandler2.postDelayed(checkWinner,deelay);
                            }else{
                                fight=false;
                                mHandler.removeCallbacks(game);
                                mHandler2.removeCallbacks(checkWinner);
                                enableTouchListener=false;
                                leave.setVisibility(GONE);
                                surrenderButton.setVisibility(GONE);
                                showWinnerDialod(w);
                            }
                        }else{
                            mHandler2.postDelayed(checkWinner,deelay);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }else{
                mHandler2.postDelayed(checkWinner,deelay);
            }
        }
    };

    private void showWinnerDialod(Winner w) {

        if(w.getWinner().equals(user.getIndex().getOpponent())){
            // przegrałem
            alertDialogFlag=true;
            if(soundOn){
                bubblesSound=MediaPlayer.create(MultiplayerActivity.this,R.raw.bubbles);
                bubblesSound.start();
            }
            android.app.AlertDialog.Builder mBuilder = new android.app.AlertDialog.Builder(MultiplayerActivity.this);
            View mView = getLayoutInflater().inflate(R.layout.alert_dialog_with_one_button_and_field_red,null);
            mBuilder.setView(mView);
            android.app.AlertDialog dialog = mBuilder.create();
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
            dialog.getWindow().getDecorView().setSystemUiVisibility(flags);
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            TextView title = mView.findViewById(R.id.alert_dialog_title_layout_one_button_and_field_red);
            TextView message = mView.findViewById(R.id.alert_dialog_message_layout_one_button_and_field_red);
            Button positiveButton = mView.findViewById(R.id.alert_dialog_button_layout_one_button_and_field_red);
            GridLayout field = mView.findViewById(R.id.alert_dialog_linear_gridlayout_layout_one_button_and_field_red);
            title.setText("SORRY");
            message.setText("Maybe next time");
            positiveButton.setText("OK");
            positiveButton.setOnClickListener(v1 -> {

                if(interstitialAd.isLoaded()){
                    interstitialAd.show();
                }else{
                    Log.d("TAG", "Admob wasn't loaded yet");
                }

                user.setIndex(new FightIndex());
                databaseReferenceMy.setValue(user);
                databaseReferenceFight.removeValue();
                stopAllSounds();
                dialog.dismiss();
                finish();
            });
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(8*square,8*square);
            params.setMargins(square,square,square,square);
            field.setLayoutParams(params);

            for(int i =0;i<10;i++){
                for(int j=0;j<10;j++){
                    if(battleFieldForDataBaseOpponent.showBattleField().getBattleField(i,j).isShip()){
                        if(battleFieldForDataBaseOpponent.showBattleField().getBattleField(i,j).isHit()){
                            field.getChildAt(10*i+j).setBackgroundResource(R.drawable.ship_x_hit);
                        }else{
                            field.getChildAt(10*i+j).setBackgroundResource(R.drawable.ship_x_not_hit);
                        }
                    }else {
                        if(battleFieldForDataBaseOpponent.showBattleField().getBattleField(i,j).isHit()){
                            field.getChildAt(10*i+j).setBackgroundResource(R.drawable.water_cell_x_grey);
                        }else{
                            field.getChildAt(10*i+j).setBackgroundResource(R.drawable.background_x);
                        }
                    }
                }
            }
            dialog.show();
        }else {
            // wygrałem (bo się poddał - sprawdź czy out off date)
            if (w.isSurrendered() && w.isOutOfDate()) {
                //poddał się ale za długo czekał
                user.setIndex(new FightIndex());
                databaseReferenceMy.setValue(user);
                databaseReferenceFight.removeValue();
                alertDialogFlag=true;
                android.app.AlertDialog.Builder mBuilder = new android.app.AlertDialog.Builder(MultiplayerActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.alert_dialog_with_one_button_red, null);
                mBuilder.setView(mView);
                android.app.AlertDialog dialog = mBuilder.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
                dialog.getWindow().getDecorView().setSystemUiVisibility(flags);
                dialog.setCancelable(false);
                dialog.setCanceledOnTouchOutside(false);
                TextView title = mView.findViewById(R.id.alert_dialog_title_layout_one_button_red);
                TextView message = mView.findViewById(R.id.alert_dialog_message_layout_one_button_red);
                Button positiveButton = mView.findViewById(R.id.alert_dialog_button_layout_one_button_red);
                title.setText("SORRY");
                message.setText("YOUR OPPONENT HAS LEFT");
                positiveButton.setText("OK");
                positiveButton.setOnClickListener(v1 -> {

                    if(interstitialAd.isLoaded()){
                        interstitialAd.show();
                    }else{
                        Log.d("TAG", "Admob wasn't loaded yet");
                    }

                    dialog.dismiss();
                    finish();
                });
                dialog.show();


            } else if (w.isSurrendered() && !w.isOutOfDate()) {
                // poddał się ale doliczam sobie punkty
                int score = user.getScore();


                if(battleFieldForDataBaseMy.getDifficulty().isSet()){

                if (battleFieldForDataBaseMy.getDifficulty().isEasy()) {
                    score = score + 25;
                    } else {
                    score = score + 50;
                    }
                }


                user.setScore(score);
                user.setIndex(new FightIndex());
                databaseReferenceMy.setValue(user);
                databaseReferenceFight.removeValue();
                alertDialogFlag=true;
                if(soundOn){
                    hornSound=MediaPlayer.create(MultiplayerActivity.this,R.raw.big_horn);
                    hornSound.start();
                }
                android.app.AlertDialog.Builder mBuilder = new android.app.AlertDialog.Builder(MultiplayerActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.alert_dialog_with_one_button_green, null);
                mBuilder.setView(mView);
                android.app.AlertDialog dialog = mBuilder.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
                dialog.getWindow().getDecorView().setSystemUiVisibility(flags);
                dialog.setCancelable(false);
                dialog.setCanceledOnTouchOutside(false);
                TextView title = mView.findViewById(R.id.alert_dialog_title_layout_one_button_green);
                TextView message = mView.findViewById(R.id.alert_dialog_message_layout_one_button_green);
                Button positiveButton = mView.findViewById(R.id.alert_dialog_button_layout_one_button_green);
                title.setText("CONGRATULATION");
                message.setText("YOUR OPPONENT HAS SURRENDERED GAME");
                positiveButton.setText("OK");
                positiveButton.setOnClickListener(v1 -> {
                    stopAllSounds();
                    if(interstitialAd.isLoaded()){
                        interstitialAd.show();
                    }else{
                        Log.d("TAG", "Admob wasn't loaded yet");
                    }

                    dialog.dismiss();
                    finish();
                });
                dialog.show();
            }else;
        }

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(enableTouchListener&&!alertDialogFlag) {
            final int X = (int) event.getRawX();
            final int Y = (int) event.getRawY();
            int x = (X - locationLayout[0]) / width;
            int y = (Y - locationLayout[1]) / height;
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    if(battleFieldOpponent[y][x]==BATTLE_CELL){
                        for (int i = 0; i < 10; i++) {
                            for (int j = 0; j < 10; j++) {
                                if (x == j || y == i) {
                                    if(battleFieldOpponent[i][j]==BATTLE_CELL){
                                        tv = (TextView) layoutOpponent.getChildAt(10*i+j);
                                        tv.setBackground(new BattleCellGreenBackground(MultiplayerActivity.this,square));
                                    }else if(battleFieldOpponent[i][j]==WATER){
                                        tv = (TextView) layoutOpponent.getChildAt(10*i+j);
                                        tv.setBackground(getDrawable(R.drawable.water_cell_x_green_field));
                                    }else{
                                        tv = (TextView) layoutOpponent.getChildAt(10*i+j);
                                        tv.setBackground(getDrawable(R.drawable.ship_cell_x_green_field));
                                    }
                                }
                            }
                        }
                    }else {
                        for (int i = 0; i < 10; i++) {
                            for (int j = 0; j < 10; j++) {
                                if (x == j || y == i) {
                                    if(battleFieldOpponent[i][j]==BATTLE_CELL){
                                        tv = (TextView) layoutOpponent.getChildAt(10*i+j);
                                        tv.setBackground(getDrawable(R.drawable.battle_cell_x_red_field));
                                    }else if(battleFieldOpponent[i][j]==WATER){
                                        tv = (TextView) layoutOpponent.getChildAt(10*i+j);
                                        tv.setBackground(getDrawable(R.drawable.water_cell_x_red_field));
                                    }else{
                                        tv = (TextView) layoutOpponent.getChildAt(10*i+j);
                                        tv.setBackground(getDrawable(R.drawable.ship_cell_x_red_field));
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
                                            tv = (TextView) layoutOpponent.getChildAt(10*i+j);
                                            tv.setBackground(new BattleCellGreenBackground(MultiplayerActivity.this,square));
                                        }else if(battleFieldOpponent[i][j]==WATER){
                                            tv = (TextView) layoutOpponent.getChildAt(10*i+j);
                                            tv.setBackground(getDrawable(R.drawable.water_cell_x_green_field));
                                        }else{
                                            tv = (TextView) layoutOpponent.getChildAt(10*i+j);
                                            tv.setBackground(getDrawable(R.drawable.ship_cell_x_green_field));
                                        }
                                    }
                                }
                            }
                        }else {
                            for (int i = 0; i < 10; i++) {
                                for (int j = 0; j < 10; j++) {
                                    if (x == j || y == i) {
                                        if(battleFieldOpponent[i][j]==BATTLE_CELL){
                                            tv = (TextView) layoutOpponent.getChildAt(10*i+j);
                                            tv.setBackground(getDrawable(R.drawable.battle_cell_x_red_field));
                                        }else if(battleFieldOpponent[i][j]==WATER){
                                            tv = (TextView) layoutOpponent.getChildAt(10*i+j);
                                            tv.setBackground(getDrawable(R.drawable.water_cell_x_red_field));
                                        }else{
                                            tv = (TextView) layoutOpponent.getChildAt(10*i+j);
                                            tv.setBackground(getDrawable(R.drawable.ship_cell_x_red_field));
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
        layoutOpponent.invalidate();
        return true;

    }

    private void hitCell(int x, int y) {
        battleFieldForDataBaseOpponent.showBattleField().getBattleField(x,y).setHit(true);
        lastMove.add(10*x+y);
        battleFieldForDataBaseOpponent.setLastMove(lastMove);
        databaseReferenceFight.child(user.getIndex().getOpponent()).setValue(battleFieldForDataBaseOpponent);

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        long time = calendar.getTimeInMillis();
        databaseReferenceFight.child(user.getId()).child("time").setValue(time);


        if(battleFieldForDataBaseOpponent.showBattleField().getBattleField(x,y).isShip()){
            if(soundOn){
                explosionSound=MediaPlayer.create(MultiplayerActivity.this, R.raw.explosion);
                explosionSound.start();
            }
            battleFieldOpponent[x][y]=SHIP_RED;
            updateCounters(battleFieldForDataBaseOpponent.showBattleField().getBattleField(x,y).getNumberOfMasts(),
                    battleFieldForDataBaseOpponent.showBattleField().getBattleField(x,y).getShipNumber(),true);
            updateShipsHit();
            showOpponentBattleField();
            if(myWin()){
                if(soundOn){
                    hornSound=MediaPlayer.create(MultiplayerActivity.this,R.raw.big_horn);
                    hornSound.start();
                }
                mHandler.removeCallbacks(game);
                mHandler2.removeCallbacks(checkWinner);
                Winner winner=new Winner();
                winner.setWinner(user.getId());
                databaseReferenceFight.child("winner").setValue(winner);
                int score = user.getScore();
                if(battleFieldForDataBaseMy.getDifficulty().isEasy()){
                    score = score+25;
                }else{
                    score = score+50;
                }
                user.setScore(score);
                user.setIndex(new FightIndex());
                databaseReferenceMy.setValue(user);
                alertDialogFlag=true;
                android.app.AlertDialog.Builder mBuilder = new android.app.AlertDialog.Builder(MultiplayerActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.alert_dialog_with_one_button_green,null);
                mBuilder.setView(mView);
                android.app.AlertDialog dialog = mBuilder.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
                dialog.getWindow().getDecorView().setSystemUiVisibility(flags);
                dialog.setCancelable(false);
                dialog.setCanceledOnTouchOutside(false);
                TextView title = mView.findViewById(R.id.alert_dialog_title_layout_one_button_green);
                TextView message = mView.findViewById(R.id.alert_dialog_message_layout_one_button_green);;
                Button positiveButton = mView.findViewById(R.id.alert_dialog_button_layout_one_button_green);
                title.setText("CONGRATULATION");
                message.setText("YOU WIN");
                positiveButton.setText("OK");
                positiveButton.setOnClickListener(v1 -> {
                    stopAllSounds();

                    if(interstitialAd.isLoaded()){
                        interstitialAd.show();
                    }else{
                        Log.d("TAG", "Admob wasn't loaded yet");
                    }

                    dialog.dismiss();
                    finish();
                });
                dialog.show();
            }
        }else{
            battleFieldOpponent[x][y]=WATER;
            if(soundOn){
                waterSplashSound=MediaPlayer.create(MultiplayerActivity.this, R.raw.water_splash);
                waterSplashSound.start();
            }
            showOpponentBattleField();
            enableTouchListener=false;
            databaseReferenceFight.child("turn").setValue(user.getIndex().getOpponent());

            opponentLastMove.clear();
            databaseReferenceFight.child(user.getId()).child("lastMove").setValue(opponentLastMove);

            TOPIC = "/topics/"+ user.getIndex().getOpponent();
            NOTIFICATION_TITLE = "Your move";
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
            mHandler.postDelayed(game,deelay);
        }
    }

    public void surrenderMultiplayer(View view) {
        if (alertDialogFlag) {
            // do nothing
        } else {
            mHandler.removeCallbacks(game);
            mHandler2.removeCallbacks(checkWinner);
            final boolean[] outOfTime = {false};
            final int[] hours = {0};
            final int[] minutes = {0};
            Winner w = new Winner();
            w.setWinner(user.getIndex().getOpponent());
            w.setSurrendered(true);

            databaseReferenceFight.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        long timeEnd;
                        long timeStart;
                        if (dataSnapshot.child(user.getIndex().getOpponent()).exists()) {
                            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
                            timeEnd = calendar.getTimeInMillis();
                            timeStart = (long) dataSnapshot.child(user.getIndex().getOpponent()).child("time").getValue();
                            outOfTime[0] = timeEnd - timeStart > 86400000;
                        } else {
                            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
                            timeEnd = calendar.getTimeInMillis();
                            timeStart = (long) dataSnapshot.child("time").getValue();
                            outOfTime[0] = timeEnd - timeStart > 86400000;
                        }
                        hours[0] = (((int) timeEnd - (int) timeStart) / 3600000);
                        minutes[0] = (((int) timeEnd - (int) timeStart) - hours[0] * 3600000) / 60000;
                    }
                    final int[] myScore = {user.getScore()};
                    int myScoreMinus;

                    if (!outOfTime[0]) {
                        if (battleFieldForDataBaseMy.getDifficulty().isEasy()) {
                            myScoreMinus = 25;
                        } else {
                            myScoreMinus = 50;
                        }
                    } else {
                        myScoreMinus = 0;
                        w.setOutOfDate(true);
                    }
                    alertDialogFlag = true;
                    android.app.AlertDialog.Builder mBuilder = new android.app.AlertDialog.Builder(MultiplayerActivity.this);
                    View mView = getLayoutInflater().inflate(R.layout.alert_dialog_with_two_buttons, null);
                    mBuilder.setView(mView);
                    android.app.AlertDialog dialog = mBuilder.create();
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
                    dialog.getWindow().getDecorView().setSystemUiVisibility(flags);
                    dialog.setCancelable(false);
                    dialog.setCanceledOnTouchOutside(false);
                    TextView title = mView.findViewById(R.id.alert_dialog_title_layout_with_two_buttons);
                    TextView message = mView.findViewById(R.id.alert_dialog_message_layout_with_two_buttons);
                    Button negativeButton = mView.findViewById(R.id.alert_dialog_left_button_layout_with_two_buttons);
                    Button positiveButton = mView.findViewById(R.id.alert_dialog_right_button_layout_with_two_buttons);
                    title.setText("Leaving game");
                    String outOfTimeString = "";
                    if (outOfTime[0]) {
                        outOfTimeString = "last move was more then 24 hour ago";
                    } else {
                        outOfTimeString = "last move was " + hours[0] + "h:" + minutes[0] + "m ago";
                    }
                    message.setText(outOfTimeString + "\n" + "Do you want to quit game?" + "\n" + "You will lose " + myScoreMinus + " points");
                    negativeButton.setText("NO");
                    negativeButton.setOnClickListener(v12 -> {
                        alertDialogFlag = false;
                        dialog.dismiss();
                        mHandler.postDelayed(game, deelay);
                        mHandler2.postDelayed(checkWinner, deelay);
                    });
                    positiveButton.setText("YES");
                    positiveButton.setOnClickListener(v1 -> {
                        dialog.dismiss();
                        mHandler.removeCallbacks(game);
                        mHandler2.removeCallbacks(checkWinner);

                        TOPIC = "/topics/"+ user.getIndex().getOpponent();
                        NOTIFICATION_TITLE = user.getName()+" surrender game";
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

                        myScore[0] = myScore[0] - myScoreMinus;
                        user.setScore(myScore[0]);
                        user.setIndex(new FightIndex());
                        databaseReferenceMy.setValue(user);
                        databaseReferenceFight.child("winner").setValue(w);



                        finish();
                    });
                    dialog.show();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }
    }

    private void updateShipsHit() {

        if(shipFourMastsCounter==0){
        }else{
            if(battleFieldForDataBaseMy.getDifficulty().isEasy()){
                for(int i=0;i<shipFourMastsCounter;i++){
                    fourMasts.getChildAt(i).setBackground(getDrawable(R.drawable.battle_cell_ship_sunk_x));
                }
            }else{
                if(shipFourMastsCounter==4){
                    for(int i=0;i<shipFourMastsCounter;i++){
                        fourMasts.getChildAt(i).setBackground(getDrawable(R.drawable.battle_cell_ship_sunk_x));
                    }
                }
            }
        }

        if(shipThreeMastsCounterFirst==0){
        }else{
            if(battleFieldForDataBaseMy.getDifficulty().isEasy()){
                for(int i=0;i<shipThreeMastsCounterFirst;i++){
                    threeMastsFirst.getChildAt(i).setBackground(getDrawable(R.drawable.battle_cell_ship_sunk_x));
                }
            }else{
                if(shipThreeMastsCounterFirst==3){
                    for(int i=0;i<shipThreeMastsCounterFirst;i++){
                        threeMastsFirst.getChildAt(i).setBackground(getDrawable(R.drawable.battle_cell_ship_sunk_x));
                    }
                }
            }
        }

        if(shipThreeMastsCounterSecond==0){
        }else{
            if(battleFieldForDataBaseMy.getDifficulty().isEasy()){
                for(int i=0;i<shipThreeMastsCounterSecond;i++){
                    threeMastsSecond.getChildAt(i).setBackground(getDrawable(R.drawable.battle_cell_ship_sunk_x));
                }
            }else{
                if(shipThreeMastsCounterSecond==3){
                    for(int i=0;i<shipThreeMastsCounterSecond;i++){
                        threeMastsSecond.getChildAt(i).setBackground(getDrawable(R.drawable.battle_cell_ship_sunk_x));
                    }
                }
            }
        }

        if(shipTwoMastsCounterFirst==0){
        }else{
            if(battleFieldForDataBaseMy.getDifficulty().isEasy()){
                for(int i=0;i<shipTwoMastsCounterFirst;i++){
                    twoMastsFirst.getChildAt(i).setBackground(getDrawable(R.drawable.battle_cell_ship_sunk_x));
                }
            }else{
                if(shipTwoMastsCounterFirst==2){
                    for(int i=0;i<shipTwoMastsCounterFirst;i++){
                        twoMastsFirst.getChildAt(i).setBackground(getDrawable(R.drawable.battle_cell_ship_sunk_x));
                    }
                }
            }
        }

        if(shipTwoMastsCounterSecond==0){
        }else{
            if(battleFieldForDataBaseMy.getDifficulty().isEasy()){
                for(int i=0;i<shipTwoMastsCounterSecond;i++){
                    twoMastsSecond.getChildAt(i).setBackground(getDrawable(R.drawable.battle_cell_ship_sunk_x));
                }
            }else{
                if(shipTwoMastsCounterSecond==2){
                    for(int i=0;i<shipTwoMastsCounterSecond;i++){
                        twoMastsSecond.getChildAt(i).setBackground(getDrawable(R.drawable.battle_cell_ship_sunk_x));
                    }
                }
            }
        }

        if(shipTwoMastsCounterThird==0){
        }else{
            if(battleFieldForDataBaseMy.getDifficulty().isEasy()){
                for(int i=0;i<shipTwoMastsCounterThird;i++){
                    twoMastsThird.getChildAt(i).setBackground(getDrawable(R.drawable.battle_cell_ship_sunk_x));
                }
            }else{
                if(shipTwoMastsCounterThird==2){
                    for(int i=0;i<shipTwoMastsCounterThird;i++){
                        twoMastsThird.getChildAt(i).setBackground(getDrawable(R.drawable.battle_cell_ship_sunk_x));
                    }
                }
            }
        }

        if(shipOneMastsCounterFirst==0){
        }else{
            oneMastsFirst.getChildAt(0).setBackground(getDrawable(R.drawable.battle_cell_ship_sunk_x));
        }

        if(shipOneMastsCounterSecond==0){
        }else{
            oneMastsSecond.getChildAt(0).setBackground(getDrawable(R.drawable.battle_cell_ship_sunk_x));
        }

        if(shipOneMastsCounterThird==0){
        }else{
            oneMastsThird.getChildAt(0).setBackground(getDrawable(R.drawable.battle_cell_ship_sunk_x));
        }

        if(shipOneMastsCounterFourth==0){
        }else{
            oneMastsFourth.getChildAt(0).setBackground(getDrawable(R.drawable.battle_cell_ship_sunk_x));
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

    private void updateCounters(int numberOfMasts, int shipNumber, boolean sound) {
        int number = 10*numberOfMasts+shipNumber;
        shoutYaySound=MediaPlayer.create(MultiplayerActivity.this,R.raw.shout_yay);

        switch (number){
            case 41:
                shipFourMastsCounter++;
                if(shipFourMastsCounter==4){
                    updateBattleField(numberOfMasts,shipNumber);
                    if(soundOn&&sound){
                        shoutYaySound.start();
                    }
                }
                break;
            case 31:
                shipThreeMastsCounterFirst++;
                if(shipThreeMastsCounterFirst==3){
                    updateBattleField(numberOfMasts,shipNumber);
                    if(soundOn&&sound){
                        shoutYaySound.start();
                    }
                }
                break;
            case 32:
                shipThreeMastsCounterSecond++;
                if(shipThreeMastsCounterSecond==3){
                    updateBattleField(numberOfMasts,shipNumber);
                    if(soundOn&&sound){
                        shoutYaySound.start();
                    }
                }
                break;
            case 21:
                shipTwoMastsCounterFirst++;
                if(shipTwoMastsCounterFirst==2){
                    updateBattleField(numberOfMasts,shipNumber);
                    if(soundOn&&sound){
                        shoutYaySound.start();
                    }
                }
                break;
            case 22:
                shipTwoMastsCounterSecond++;
                if(shipTwoMastsCounterSecond==2){
                    updateBattleField(numberOfMasts,shipNumber);
                    if(soundOn&&sound){
                        shoutYaySound.start();
                    }
                }
                break;
            case 23:
                shipTwoMastsCounterThird++;
                if(shipTwoMastsCounterThird==2){
                    updateBattleField(numberOfMasts,shipNumber);
                    if(soundOn&&sound){
                        shoutYaySound.start();
                    }
                }
                break;
            case 11:
                shipOneMastsCounterFirst++;
                if(shipOneMastsCounterFirst==1){
                    updateBattleField(numberOfMasts,shipNumber);
                    if(soundOn&&sound){
                        shoutYaySound.start();
                    }
                }
                break;
            case 12:
                shipOneMastsCounterSecond++;
                if(shipOneMastsCounterSecond==1){
                    updateBattleField(numberOfMasts,shipNumber);
                    if(soundOn&&sound){
                        shoutYaySound.start();
                    }
                }
                break;
            case 13:
                shipOneMastsCounterThird++;
                if(shipOneMastsCounterThird==1){
                    updateBattleField(numberOfMasts,shipNumber);
                    if(soundOn&&sound){
                        shoutYaySound.start();
                    }
                }
                break;
            case 14:
                shipOneMastsCounterFourth++;
                if(shipOneMastsCounterFourth==1){
                    updateBattleField(numberOfMasts,shipNumber);
                    if(soundOn&&sound){
                        shoutYaySound.start();
                    }
                }
                break;
            default:
        }
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

    private boolean myWin(){
        return counterSum()==20;
    }

    private void showOpponentBattleField() {
        for(int i =0;i<10;i++){
            for(int j = 0; j<10;j++){
                if(battleFieldOpponent[i][j]==SHIP_BROWN){
                    layoutOpponent.getChildAt(i*10+j).setBackground(getDrawable(R.drawable.battle_cell_ship_sunk_x));
                }
                else if(battleFieldOpponent[i][j]==SHIP_RED){
                    layoutOpponent.getChildAt(i*10+j).setBackground(getDrawable(R.drawable.battle_cell_ship_normal_x));
                }else if(battleFieldOpponent[i][j]==WATER){
                    layoutOpponent.getChildAt(i*10+j).setBackground(getDrawable(R.drawable.water_cell_x));
                }else if(battleFieldOpponent[i][j]==BATTLE_CELL){
                    layoutOpponent.getChildAt(i*10+j).setBackground(new BattleCell(MultiplayerActivity.this,square));
                }else;

            }
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
                        Toast.makeText(MultiplayerActivity.this, "Request error", Toast.LENGTH_LONG).show();
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

    public void leaveMultiplayerOnClick(View view) {
        leaveGame();
    }

    @Override
    public void onBackPressed() {
        leaveGame();
    }

    private void leaveGame() {
        if(alertDialogFlag){
            // do nothing
        }else{
        alertDialogFlag=true;
        android.app.AlertDialog.Builder mBuilder = new android.app.AlertDialog.Builder(MultiplayerActivity.this);
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
        title.setText("Leaving game");
        message.setText("Do you want to go back to main menu?");
        negativeButton.setText("NO");
        negativeButton.setOnClickListener(v12 -> {
            alertDialogFlag=false;
            dialog.dismiss();
            mHandler.postDelayed(game,deelay);
        });
        positiveButton.setText("YES");
        positiveButton.setOnClickListener(v1 -> {
            dialog.dismiss();
            mHandler.removeCallbacks(game);
            mHandler2.removeCallbacks(checkWinner);
            finish();
        });
        dialog.show();
        }
    }

    private void updateSoundButton(){
        if(soundOn){
            soundButton.setImageResource(R.color.transparent);
        }else{
            soundButton.setImageResource(R.drawable.disable);
            stopAllSounds();
        }
    }

    public void soundMultiplayerPlayer(View view) {
        if (alertDialogFlag) {
            // do nothing
        } else {
            soundOn = !soundOn;
            SharedPreferences sp = getSharedPreferences("Sound", Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean("sound", soundOn);
            editor.commit();
            updateSoundButton();
        }
    }

    private void stopAllSounds() {
        try{
            if(explosionSound!=null){
                if(explosionSound.isPlaying()){
                    explosionSound.stop();
                }
                explosionSound.release();
                explosionSound=null;
            }
            if(waterSplashSound!=null){
                if(waterSplashSound.isPlaying()){
                    waterSplashSound.stop();
                }
                waterSplashSound.release();
                waterSplashSound=null;
            }
            if(hornSound!=null){
                if(hornSound.isPlaying()){
                    hornSound.stop();
                }
                hornSound.release();
                hornSound=null;
            }
            if(bubblesSound!=null){
                if(bubblesSound.isPlaying()){
                    bubblesSound.stop();
                }
                bubblesSound.release();
                bubblesSound=null;
            }
            if(shoutYaySound!=null){
                if(shoutYaySound.isPlaying()){
                    shoutYaySound.stop();
                }
                shoutYaySound.release();
                shoutYaySound=null;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

