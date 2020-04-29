package com.example.ships;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.example.ships.classes.BattleField;
import com.example.ships.classes.GameDifficulty;
import com.example.ships.classes.SinglePlayerMatch;
import com.example.ships.classes.TileDrawable;
import com.example.ships.classes.User;
import com.example.ships.drawings.Background;
import com.example.ships.drawings.BattleCell;
import com.example.ships.drawings.BattleCellGreenBackground;
import com.example.ships.drawings.BattleCellHiddenBackground;
import com.example.ships.drawings.BattleCellRedBackground;
import com.example.ships.drawings.ShipBlueGreenBackground;
import com.example.ships.drawings.ShipBlueRedBackground;
import com.example.ships.drawings.ShipBlueWhiteBackground;
import com.example.ships.drawings.ShipGreyHiddenBackground;
import com.example.ships.drawings.ShipGreyWhiteBackground;
import com.example.ships.drawings.ShipLightGreyWhiteBackground;
import com.example.ships.drawings.ShipRedHiddenBackground;
import com.example.ships.drawings.ShipRedRedBackground;
import com.example.ships.drawings.ShipRedWhiteBackground;
import com.example.ships.drawings.ShipRedWhiteBackgroundGreyStroke;
import com.example.ships.drawings.WaterBlueGreenBackground;
import com.example.ships.drawings.WaterBlueHiddenBackground;
import com.example.ships.drawings.WaterBlueRedBackground;
import com.example.ships.drawings.WaterBlueWhiteBackground;
import com.example.ships.drawings.WaterLightGreyWhiteBackground;
import com.example.ships.singletons.BattleFieldPlayerOneSingleton;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

public class GameBattle extends AppCompatActivity implements View.OnTouchListener {

    private Handler mHandler = new Handler();
    private BattleField battleFieldMeActivityRandomGame = new BattleField();
    private BattleField battleFieldOpponentActivityRandomGame = new BattleField();
    private SinglePlayerMatch singlePlayerMatch = new SinglePlayerMatch();
    private ConstraintLayout mainLayout;

    private GridLayout layoutOpponent, layoutMy;

    private int height, width;

    private int battleFieldOpponent[][] = new int[10][10];
    private final static int BATTLE_CELL = 0;
    private final static int WATER = 1;
    private final static int SHIP_RED = 2;
    private final static int SHIP_BROWN = 3;

    private int shipFourMastsCounter = 0;
    private int shipThreeMastsCounterFirst = 0;
    private int shipThreeMastsCounterSecond = 0;
    private int shipTwoMastsCounterFirst = 0;
    private int shipTwoMastsCounterSecond = 0;
    private int shipTwoMastsCounterThird = 0;
    private int shipOneMastsCounterFirst = 0;
    private int shipOneMastsCounterSecond = 0;
    private int shipOneMastsCounterThird = 0;
    private int shipOneMastsCounterFourth = 0;
    private boolean myTurn;
    private int level;
    private boolean newShoot;
    private int positionI;
    private int positionJ;
    private int direction;
    private int x;
    private int y;
    private boolean enableTouchListener;
    private boolean loggedIn;
    private long noOfGames;
    private long score;
    private int deelay = 1000;
    private LinearLayout linearLayoutLettersMy, linearLayoutNumbersMy, linearLayoutLettersOpponent, linearLayoutNumbersOpponent;
    private int[] locationLayout = new int[2];
    private LinearLayout fourMasts, threeMastsFirst, threeMastsSecond, twoMastsFirst, twoMastsSecond, twoMastsThird, oneMastsFirst, oneMastsSecond, oneMastsThird, oneMastsFourth;
    private User user = new User();
    private boolean alertDialogFlag = false;


    private ArrayList<Integer> ShootTable = new ArrayList<>();

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String userID;
    private TextView tv;
    private ImageButton surrender, leave, soundBtn;
    private int marginTop;
    private int marginLeft;
    private int marginLeftForShips;
    private int marginDown;
    private TextView userName, opponentName;
    private int flags;
    private MediaPlayer explosionSound, waterSplashSound, hornSound, bubblesSound, shoutYaySound;
    private boolean soundOn;
    private int square;
    private TextView borderLine1, borderLine2, borderLine3, borderLine4, borderLine5, borderLine6, borderLine7, borderLine8;
    private InterstitialAd interstitialAd;

    @SuppressLint("ClickableViewAccessibility")
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

        setContentView(R.layout.activity_random_game_battle);
        mainLayout = findViewById(R.id.randomGameActivityLayout);
        surrender = findViewById(R.id.surrenderSinglePlayer);
        surrender.setBackgroundResource(R.drawable.grid_off);
        leave = findViewById(R.id.leaveSinglePlayer);
        leave.setBackgroundResource(R.drawable.back);
        layoutMy = findViewById(R.id.gridLayoutBattleMy);
        linearLayoutLettersMy = findViewById(R.id.LinearLayoutGameBattleActivityLettersMy);
        linearLayoutNumbersMy = findViewById(R.id.LinearLayoutGameBattleActivityNumbersMy);
        layoutOpponent = findViewById(R.id.gridLayoutBattleOpponent);
        linearLayoutLettersOpponent = findViewById(R.id.linearLayoutSingleBattleLetterOpponent);
        linearLayoutNumbersOpponent = findViewById(R.id.linearLayoutSingleBattleNumbersOpponent);
        fourMasts = findViewById(R.id.linearLayoutSingleBattleShipFourMasts);
        threeMastsFirst = findViewById(R.id.linearLayoutSingleBattleShipThreeMastsFirst);
        threeMastsSecond = findViewById(R.id.linearLayoutSingleBattleShipThreeMastsSecond);
        twoMastsFirst = findViewById(R.id.linearLayoutSingleBattleShipTwoMastsFirst);
        twoMastsSecond = findViewById(R.id.linearLayoutSingleBattleShipTwoMastsSecond);
        twoMastsThird = findViewById(R.id.linearLayoutSingleBattleShipTwoMastsThird);
        oneMastsFirst = findViewById(R.id.linearLayoutSingleBattleShipOneMastsFirst);
        oneMastsSecond = findViewById(R.id.linearLayoutSingleBattleShipOneMastsSecond);
        oneMastsThird = findViewById(R.id.linearLayoutSingleBattleShipOneMastsThird);
        oneMastsFourth = findViewById(R.id.linearLayoutSingleBattleShipOneMastsFourth);
        userName = findViewById(R.id.userNameGameBattle);
        opponentName = findViewById(R.id.opponentNameGameBattle);
        soundBtn = findViewById(R.id.soundSinglePlayer);
        soundBtn.setBackgroundResource(R.drawable.sound);
        borderLine1=findViewById(R.id.borderLine1);
        borderLine2=findViewById(R.id.borderLine2);
        borderLine3=findViewById(R.id.borderLine3);
        borderLine4=findViewById(R.id.borderLine4);
        borderLine5=findViewById(R.id.borderLine5);
        borderLine6=findViewById(R.id.borderLine6);
        borderLine7=findViewById(R.id.borderLine7);
        borderLine8=findViewById(R.id.borderLine8);
        SharedPreferences sp = getSharedPreferences("VALUES", Activity.MODE_PRIVATE);
        square = sp.getInt("square", -1);
        int screenWidth = sp.getInt("width", -1);
        int screenHeight = sp.getInt("height", -1);
        int screenWidthOffSet = sp.getInt("widthOffSet", -1);
        int screenHeightOffSet = sp.getInt("heightOffSet", -1);
        float textSize = (square * 9) / 10;

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
        ConstraintLayout.LayoutParams params18 = new ConstraintLayout.LayoutParams(10 * square, 2 * square);
        ConstraintLayout.LayoutParams params19 = new ConstraintLayout.LayoutParams(10 * square, 2 * square);
        ConstraintLayout.LayoutParams params20 = new ConstraintLayout.LayoutParams(2 * square, 2 * square);
        ConstraintLayout.LayoutParams params21 = new ConstraintLayout.LayoutParams(10 * square+square/20, square/20);
        ConstraintLayout.LayoutParams params23 = new ConstraintLayout.LayoutParams(10 * square+square/20, square/20);
        ConstraintLayout.LayoutParams params22 = new ConstraintLayout.LayoutParams(square/20, 10*square+square/20);
        ConstraintLayout.LayoutParams params24 = new ConstraintLayout.LayoutParams(square/20, 10*square+square/20);
        ConstraintLayout.LayoutParams params25 = new ConstraintLayout.LayoutParams(10 * square+square/20, square/20);
        ConstraintLayout.LayoutParams params27 = new ConstraintLayout.LayoutParams(10 * square+square/20, square/20);
        ConstraintLayout.LayoutParams params26 = new ConstraintLayout.LayoutParams(square/20, 10*square+square/20);
        ConstraintLayout.LayoutParams params28 = new ConstraintLayout.LayoutParams(square/20, 10*square+square/20);

        surrender.setLayoutParams(params);
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
        soundBtn.setLayoutParams(params20);
        borderLine1.setLayoutParams(params21);
        borderLine2.setLayoutParams(params22);
        borderLine3.setLayoutParams(params23);
        borderLine4.setLayoutParams(params24);
        borderLine5.setLayoutParams(params25);
        borderLine6.setLayoutParams(params26);
        borderLine7.setLayoutParams(params27);
        borderLine8.setLayoutParams(params28);

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

        for(int i=0;i<4;i++){
            TextView tv = (TextView) fourMasts.getChildAt(i);
            tv.setBackground(new BattleCell(GameBattle.this,square));
        }
        for(int i=0;i<3;i++){
            TextView tv = (TextView) threeMastsFirst.getChildAt(i);
            tv.setBackground(new BattleCell(GameBattle.this,square));
        }
        for(int i=0;i<3;i++){
            TextView tv = (TextView) threeMastsSecond.getChildAt(i);
            tv.setBackground(new BattleCell(GameBattle.this,square));
        }
        for(int i=0;i<2;i++){
            TextView tv = (TextView) twoMastsFirst.getChildAt(i);
            tv.setBackground(new BattleCell(GameBattle.this,square));
        }
        for(int i=0;i<2;i++){
            TextView tv = (TextView) twoMastsSecond.getChildAt(i);
            tv.setBackground(new BattleCell(GameBattle.this,square));
        }
        for(int i=0;i<2;i++){
            TextView tv = (TextView) twoMastsThird.getChildAt(i);
            tv.setBackground(new BattleCell(GameBattle.this,square));
        }
        TextView tv1 = (TextView) oneMastsFirst.getChildAt(0);
        tv1.setBackground(new BattleCell(GameBattle.this,square));

        TextView tv2 = (TextView) oneMastsSecond.getChildAt(0);
        tv2.setBackground(new BattleCell(GameBattle.this,square));

        TextView tv3 = (TextView) oneMastsThird.getChildAt(0);
        tv3.setBackground(new BattleCell(GameBattle.this,square));

        TextView tv4 = (TextView) oneMastsFourth.getChildAt(0);
        tv4.setBackground(new BattleCell(GameBattle.this,square));

        marginTop = 4 * square;
        marginLeft = screenWidth - screenWidthOffSet - 13 * square;
        marginDown = screenHeight - screenHeightOffSet - 2 * square;

        if (((screenWidth - screenWidthOffSet) / square) % 2 == 0) {
            marginLeftForShips = (screenWidth - screenWidthOffSet) / 2 - 15 * square;
        } else {
            marginLeftForShips = (screenWidth - screenWidthOffSet - square) / 2 - 15 * square;
        }

        ConstraintSet set = new ConstraintSet();
        set.clone(mainLayout);
        set.connect(surrender.getId(), ConstraintSet.TOP, mainLayout.getId(), ConstraintSet.TOP, square);
        set.connect(surrender.getId(), ConstraintSet.LEFT, mainLayout.getId(), ConstraintSet.LEFT, square);

        set.connect(soundBtn.getId(), ConstraintSet.TOP, surrender.getId(), ConstraintSet.BOTTOM, 4*square);
        set.connect(soundBtn.getId(), ConstraintSet.LEFT, mainLayout.getId(), ConstraintSet.LEFT, square);

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

        set.connect(userName.getId(), ConstraintSet.BOTTOM, layoutMy.getId(), ConstraintSet.TOP, square);
        set.connect(userName.getId(), ConstraintSet.LEFT, layoutMy.getId(), ConstraintSet.LEFT, 0);

        set.connect(opponentName.getId(), ConstraintSet.BOTTOM, layoutOpponent.getId(), ConstraintSet.TOP, square);
        set.connect(opponentName.getId(), ConstraintSet.LEFT, layoutOpponent.getId(), ConstraintSet.LEFT, 0);

        set.applyTo(mainLayout);

        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getString(R.string.admob_big));
        interstitialAd.loadAd(new AdRequest.Builder().build());

        enableTouchListener = false;
        initializeTable(ShootTable);

        SharedPreferences spsound = getSharedPreferences("Sound", Activity.MODE_PRIVATE);
        soundOn=spsound.getBoolean("sound",false);
        updateSoundButton();
        height = square;
        width = square;

        layoutOpponent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                layoutOpponent.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                layoutOpponent.getLocationOnScreen(locationLayout);

            }
        });
        layoutOpponent.setOnTouchListener(this);


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        if (firebaseAuth.getCurrentUser() != null) {
            String providerId = "";
            for (UserInfo profile : firebaseAuth.getCurrentUser().getProviderData()) {
                providerId = providerId + " " + profile.getProviderId();
            }

            if (providerId.contains("facebook.com") || providerId.contains("google.com")) {
                loggedIn = true;
            } else {
                if (firebaseAuth.getCurrentUser().isEmailVerified()) {
                    loggedIn = true;
                } else {
                    loggedIn = false;
                }
            }
        }
        if (loggedIn) {
            userID = firebaseAuth.getCurrentUser().getUid();
            databaseReference = firebaseDatabase.getReference("User").child(userID);
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    user = dataSnapshot.getValue(User.class);
                    boolean savedGame = user.getSinglePlayerMatch().isGame();
                    userName.setText(user.getName());
                    opponentName.setText("PHONE");
                    if (savedGame) {
                        try {
                            for (int i = 0; i < 10; i++) {
                                for (int j = 0; j < 10; j++) {
                                    battleFieldMeActivityRandomGame.makeShip(i, j, user.getSinglePlayerMatch().getBattleFieldListMy().get(10 * i + j));
                                }
                            }
                            for (int i = 0; i < 10; i++) {
                                for (int j = 0; j < 10; j++) {
                                    battleFieldOpponentActivityRandomGame.makeShip(i, j, user.getSinglePlayerMatch().getBattleFieldListOpponent().get(10 * i + j));
                                }
                            }
                            myTurn = user.getSinglePlayerMatch().isMyTurn();
                            level = user.getSinglePlayerMatch().getDifficulty();
                            positionI = user.getSinglePlayerMatch().getPositionI();
                            positionJ = user.getSinglePlayerMatch().getPositionJ();
                            newShoot = user.getSinglePlayerMatch().isNewShoot();
                            direction = user.getSinglePlayerMatch().getDirection();
                            x = user.getSinglePlayerMatch().getX();
                            y = user.getSinglePlayerMatch().getY();
                            updateBattleFieldFromSaved();
                            for (int i = 0; i < 10; i++) {
                                for (int j = 0; j < 10; j++) {
                                    if (battleFieldOpponentActivityRandomGame.getBattleField(i, j).isShip() && battleFieldOpponentActivityRandomGame.getBattleField(i, j).isHit()) {

                                        updateCounters(battleFieldOpponentActivityRandomGame.getBattleField(i, j).getNumberOfMasts(), battleFieldOpponentActivityRandomGame.getBattleField(i, j).getShipNumber(), false);
                                    }
                                }
                            }
                            showCounters();
                            hideBattleFiledAvailablePlayerOne();
                            hideBattleFiledAvailablePlayerTwo();
                        }
                        catch (Exception e){
                            user.setSinglePlayerMatch(new SinglePlayerMatch());
                            databaseReference.setValue(user);
                            finish();
                        }
                    } else {
                        if (GameDifficulty.getInstance().getRandom()) {
                            battleFieldMeActivityRandomGame.createFleet();
                        } else {
                            battleFieldMeActivityRandomGame = BattleFieldPlayerOneSingleton.getInstance().readBattleField();
                        }

                        battleFieldOpponentActivityRandomGame.createFleet();
                        level = GameDifficulty.getInstance().getLevel();
                        noOfGames = (Long) dataSnapshot.child("noOfGames").getValue();
                        noOfGames = noOfGames + 1;
                        newShoot = true;
                        databaseReference.child("noOfGames").setValue(noOfGames);
                        hideBattleFiledAvailablePlayerOne();
                        hideBattleFiledAvailablePlayerTwo();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        } else {

            if (GameDifficulty.getInstance().getRandom()) {
                battleFieldMeActivityRandomGame.createFleet();
            } else {
                battleFieldMeActivityRandomGame = BattleFieldPlayerOneSingleton.getInstance().readBattleField();
            }
            level=GameDifficulty.getInstance().getLevel();

            battleFieldOpponentActivityRandomGame.createFleet();
            hideBattleFiledAvailablePlayerOne();
            hideBattleFiledAvailablePlayerTwo();
            newShoot = true;
            userName.setText("ME");
            opponentName.setText("PHONE");
        }
        game.run();
    }

    private void updateBattleFieldFromSaved() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (battleFieldOpponentActivityRandomGame.getBattleField(i, j).isShip() && battleFieldOpponentActivityRandomGame.getBattleField(i, j).isHit()) {
                    battleFieldOpponent[i][j] = SHIP_RED;
                } else if (!battleFieldOpponentActivityRandomGame.getBattleField(i, j).isShip() && battleFieldOpponentActivityRandomGame.getBattleField(i, j).isHit()) {
                    battleFieldOpponent[i][j] = WATER;
                } else {
                    battleFieldOpponent[i][j] = BATTLE_CELL;
                }
            }
        }
    }

    private void initializeTable(ArrayList<Integer> shootTable) {
        for (int i = 0; i < 100; i++) {
            shootTable.add(i);
        }
    }

    private boolean zatopiony2(int noOfMasts, int shipNo) {
        int counter = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (battleFieldMeActivityRandomGame.battleField[i][j].getNumberOfMasts() == noOfMasts
                        && battleFieldMeActivityRandomGame.battleField[i][j].getShipNumber() == shipNo
                        && battleFieldMeActivityRandomGame.battleField[i][j].isHit()) {
                    counter++;
                } else ;
            }
        }
        return counter == noOfMasts;
    }

    @Override
    protected void onPause() {
        mHandler.removeCallbacks(game);
        super.onPause();
    }


    //TODO wyczyścić kod

    private Runnable game = new Runnable() {
        @Override
        public void run() {
            play();

        }
    };

    private void play() {
        if (!myWin() && !battleFieldMeActivityRandomGame.allShipsHit()) //game
        {
            battle();
        } else if (myWin() && !battleFieldMeActivityRandomGame.allShipsHit())      // allShipsHit player
        {hornSound=MediaPlayer.create(GameBattle.this,R.raw.big_horn);
        if(soundOn) {
            hornSound.start();
        }
            if (loggedIn) {
                updateRanking();

            }
            alertDialogFlag = true;

            android.app.AlertDialog.Builder mBuilder = new android.app.AlertDialog.Builder(GameBattle.this);
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
            message.setText("YOU WIN");
            positiveButton.setText("OK");
            positiveButton.setOnClickListener(v1 -> {
                stopAllSounds();
                dialog.dismiss();
                if(interstitialAd.isLoaded()){
                    interstitialAd.show();
                }else{
                    Log.d("TAG", "Admob wasn't loaded yet");
                }
                finish();
            });
            dialog.show();

        } else if (!myWin() && battleFieldMeActivityRandomGame.allShipsHit())     // allShipsHit computer
        {
            bubblesSound=MediaPlayer.create(GameBattle.this, R.raw.bubbles);
            if(soundOn) {
                bubblesSound.start();
            }
            if (loggedIn) {

                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        user = dataSnapshot.getValue(User.class);
                        user.setSinglePlayerMatch(new SinglePlayerMatch());
                        databaseReference.setValue(user);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
            alertDialogFlag = true;
            android.app.AlertDialog.Builder mBuilder = new android.app.AlertDialog.Builder(GameBattle.this);
            View mView = getLayoutInflater().inflate(R.layout.alert_dialog_with_one_button_and_field_red, null);
            mBuilder.setView(mView);
            android.app.AlertDialog dialog = mBuilder.create();
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
            dialog.getWindow().getDecorView().setSystemUiVisibility(flags);
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            TextView title = mView.findViewById(R.id.alert_dialog_title_layout_one_button_and_field_red);
            TextView message = mView.findViewById(R.id.alert_dialog_message_layout_one_button_and_field_red);
            GridLayout field = mView.findViewById(R.id.alert_dialog_linear_gridlayout_layout_one_button_and_field_red);
            Button positiveButton = mView.findViewById(R.id.alert_dialog_button_layout_one_button_and_field_red);


            title.setText("SORRY");
            message.setText("Maybe next time");
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
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(10*square,10*square);
            params.setMargins(square,square,square,square);
            field.setLayoutParams(params);

            for(int i =0;i<10;i++){
                for(int j=0;j<10;j++){
                    if(battleFieldOpponentActivityRandomGame.getBattleField(i,j).isShip()){
                        if(battleFieldOpponentActivityRandomGame.getBattleField(i,j).isHit()){
                            field.getChildAt(10*i+j).setBackground(new ShipLightGreyWhiteBackground(getApplicationContext(),square));
                        }else{
                            field.getChildAt(10*i+j).setBackground(new ShipRedWhiteBackgroundGreyStroke(getApplicationContext(),square));
                        }
                    }else {
                        if(battleFieldOpponentActivityRandomGame.getBattleField(i,j).isHit()){
                            field.getChildAt(10*i+j).setBackground(new WaterLightGreyWhiteBackground(getApplicationContext(),square));
                        }else{
                            field.getChildAt(10*i+j).setBackground(new Background(getApplicationContext(),square));
                        }
                    }
                }
            }
            dialog.show();


        } else ;
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

    private void updateRanking() {

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                score = user.getScore();
                addPoints();
                user.setScore((int) score);
                user.setSinglePlayerMatch(new SinglePlayerMatch());
                databaseReference.setValue(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void addPoints() {

        if (level == 0) {
            score = score + 1;
        } else if (level == 2) {
            score = score + 10;
        } else if (level == 3) {
            score = score + 100;
        } else ;

    }

    public void battle() {

        if (myTurn) {
            if (loggedIn) {
                singlePlayerMatch.setGame(true);
                singlePlayerMatch.setMyTurn(myTurn);
                singlePlayerMatch.setDifficulty(level);
                singlePlayerMatch.setBattleFieldListMyFromArray(battleFieldMeActivityRandomGame);
                singlePlayerMatch.setBattleFieldListOpponentFromArray(battleFieldOpponentActivityRandomGame);
                singlePlayerMatch.setPositionI(positionI);
                singlePlayerMatch.setPositionJ(positionJ);
                singlePlayerMatch.setNewShoot(newShoot);
                singlePlayerMatch.setDirection(direction);
                singlePlayerMatch.setX(x);
                singlePlayerMatch.setY(y);
                databaseReference.child("singlePlayerMatch").setValue(singlePlayerMatch);
            }

            pokazStatki();

            hideBattleFiledAvailablePlayerOne();
            enableTouchListener = true;
            userName.setTextColor(getColor(R.color.pen));
            opponentName.setTextColor(getColor(R.color.pen_red));
        } else {
            userName.setTextColor(getColor(R.color.pen_red));
            opponentName.setTextColor(getColor(R.color.pen));
            hideBattleFiledAvailablePlayerTwo();
            showBattleFieldAvailablePlayerOne();
            shoot();
            mHandler.postDelayed(game, deelay);
        }
    }

    private void pokazStatki() {

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (battleFieldOpponent[i][j] == SHIP_BROWN) {
                    layoutOpponent.getChildAt(i * 10 + j).setBackground(new ShipRedWhiteBackground(GameBattle.this,square));
                } else if (battleFieldOpponent[i][j] == SHIP_RED) {
                    layoutOpponent.getChildAt(i * 10 + j).setBackground(new ShipBlueWhiteBackground(GameBattle.this,square));
                } else if (battleFieldOpponent[i][j] == WATER) {
                    layoutOpponent.getChildAt(i * 10 + j).setBackground(new WaterBlueWhiteBackground(GameBattle.this,square));
                } else if (battleFieldOpponent[i][j] == BATTLE_CELL) {
                    layoutOpponent.getChildAt(i * 10 + j).setBackground(new BattleCell(GameBattle.this,square));
                   // layoutOpponent.getChildAt(i * 10 + j).setBackground(getDrawable(R.drawable.battle_cell_x));
                } else ;

            }
        }

    }

    private void showBattleFieldAvailablePlayerOne() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                //jest statek i został trafiony
                if (battleFieldMeActivityRandomGame.getBattleField(i, j).isShip()
                        && battleFieldMeActivityRandomGame.getBattleField(i, j).isHit()) {
                    displayShipCell((TextView) layoutMy.getChildAt(10 * i + j));
                }

                // woda i została trafiony
                else if (!battleFieldMeActivityRandomGame.getBattleField(i, j).isShip()
                        && battleFieldMeActivityRandomGame.getBattleField(i, j).isHit()) {
                    displayWaterCell((TextView) layoutMy.getChildAt(10 * i + j));
                }

                // jest statek i nie został trafiony
                else if (battleFieldMeActivityRandomGame.getBattleField(i, j).isShip()
                        && !battleFieldMeActivityRandomGame.getBattleField(i, j).isHit()) {
                    displayWidmoShip((TextView) layoutMy.getChildAt(10 * i + j));
                }
                // nie ma statku i nie został trafiony
                else if (!battleFieldMeActivityRandomGame.getBattleField(i, j).isShip()
                        && !battleFieldMeActivityRandomGame.getBattleField(i, j).isHit()) {
                    displayBattleCell((TextView) layoutMy.getChildAt(10 * i + j));
                } else ;
            }
        }
    }

    private void displayBattleCell(TextView textView) {
    textView.setBackground(new BattleCell(GameBattle.this,square));
    //    textView.setBackground(getResources().getDrawable(R.drawable.battle_cell_x));
    }

    private void displayWidmoShip(TextView textView) {
        textView.setBackground(new ShipGreyWhiteBackground(GameBattle.this,square));
    }

    private void displayWaterCell(TextView textView) {
        textView.setBackground(new WaterBlueWhiteBackground(GameBattle.this,square));
    }

    private void displayShipCell(TextView textView) {
        textView.setBackground(new ShipRedWhiteBackground(GameBattle.this,square));
    }

    private void displayShipCellHidden(TextView textView) {
        textView.setBackground(new ShipRedHiddenBackground(GameBattle.this,square));
    }

    private void displayWaterCellHidden(TextView textView) {
        textView.setBackground(new WaterBlueHiddenBackground(GameBattle.this,square));
    }

    private void displayWidmoShipHidden(TextView textView) {
        textView.setBackground(new ShipGreyHiddenBackground(GameBattle.this,square));
    }

    private void displayBattleCellHidden(TextView textView) {
        textView.setBackground(new BattleCellHiddenBackground(GameBattle.this,square));
    }

    private void hideBattleFiledAvailablePlayerOne() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                //jest statek i został trafiony
                if (battleFieldMeActivityRandomGame.getBattleField(i, j).isShip()
                        && battleFieldMeActivityRandomGame.getBattleField(i, j).isHit()) {
                    displayShipCellHidden((TextView) layoutMy.getChildAt(10 * i + j));
                }

                // woda i została trafiony
                else if (!battleFieldMeActivityRandomGame.getBattleField(i, j).isShip()
                        && battleFieldMeActivityRandomGame.getBattleField(i, j).isHit()) {
                    displayWaterCellHidden((TextView) layoutMy.getChildAt(10 * i + j));
                }

                // jest statek i nie został trafiony
                else if (battleFieldMeActivityRandomGame.getBattleField(i, j).isShip()
                        && !battleFieldMeActivityRandomGame.getBattleField(i, j).isHit()) {
                    displayWidmoShipHidden((TextView) layoutMy.getChildAt(10 * i + j));
                }
                // nie ma statku i nie został trafiony
                else if (!battleFieldMeActivityRandomGame.getBattleField(i, j).isShip()
                        && !battleFieldMeActivityRandomGame.getBattleField(i, j).isHit()) {
                    displayBattleCellHidden((TextView) layoutMy.getChildAt(10 * i + j));
                } else ;
            }
        }

    }

    private void hideBattleFiledAvailablePlayerTwo() {

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (battleFieldOpponent[i][j] == SHIP_BROWN) {
                    layoutOpponent.getChildAt(i * 10 + j).setBackground(new ShipRedHiddenBackground(GameBattle.this,square));
                } else if (battleFieldOpponent[i][j] == SHIP_RED) {
                    layoutOpponent.getChildAt(i * 10 + j).setBackground(new ShipRedHiddenBackground(GameBattle.this,square));
                } else if (battleFieldOpponent[i][j] == WATER) {
                    layoutOpponent.getChildAt(i * 10 + j).setBackground(new WaterBlueHiddenBackground(GameBattle.this,square));
                } else {
                    layoutOpponent.getChildAt(i * 10 + j).setBackground(new BattleCellHiddenBackground(GameBattle.this,square));
                }

            }
        }

    }

    private void shoot() {
        if (newShoot) {
            Random random = new Random();
            int shoot = random.nextInt(ShootTable.size());
            int i = ShootTable.get(shoot) / 10;
            int j = ShootTable.get(shoot) % 10;
            if (battleFieldMeActivityRandomGame.getBattleField(i, j).isHit()) {
                ShootTable.remove(Integer.valueOf(i * 10 + j));
                shoot();
            } else {
                if (level == 3 && checkCell(i, j)) {
                    ShootTable.remove(Integer.valueOf(i * 10 + j));
                    shoot();
                } else {
                    if (battleFieldMeActivityRandomGame.getBattleField(i, j).isShip()) {
                        displayShipCellHit((TextView) layoutMy.getChildAt(10 * i + j));
                        ShootTable.remove(Integer.valueOf(i * 10 + j));
                        battleFieldMeActivityRandomGame.battleField[i][j].setHit(true);
                        if ((level == 2 || level == 3) && !zatopiony2(battleFieldMeActivityRandomGame.getBattleField(i, j).getNumberOfMasts(), battleFieldMeActivityRandomGame.getBattleField(i, j).getShipNumber())) {
                            positionI = i;
                            positionJ = j;
                            newShoot = false;
                            direction = 1;
                            x = i - 1;
                        } else ;

                    } else {
                        displayWaterCellHit((TextView) layoutMy.getChildAt(10 * i + j));
                        ShootTable.remove(Integer.valueOf(i * 10 + j));
                        battleFieldMeActivityRandomGame.battleField[i][j].setHit(true);
                        myTurn = true;

                    }
                }
            }
        } else {
            dobijShip(positionI, positionJ);
        }

    }

    private void displayWaterCellHit(TextView textView) {
        textView.setBackground(new WaterBlueRedBackground(GameBattle.this,square));
    }

    private void displayShipCellHit(TextView textView) {
        textView.setBackground(new ShipRedRedBackground(GameBattle.this,square));
    }

    private boolean checkCell(int i, int j) {
        for (int i1 = 0; i1 < 10; i1++) {
            for (int j1 = 0; j1 < 10; j1++) {
                battleFieldMeActivityRandomGame.battleField[i1][j1].setShipHit(
                        battleFieldMeActivityRandomGame.battleField[i1][j1].isShip() &&
                                battleFieldMeActivityRandomGame.battleField[i1][j1].isHit());
            }
        }


        if (i == 0 && j == 0) {
            if (battleFieldMeActivityRandomGame.battleField[i][j + 1].getIsShipHit() ||
                    battleFieldMeActivityRandomGame.battleField[i + 1][j].getIsShipHit() ||
                    battleFieldMeActivityRandomGame.battleField[i + 1][j + 1].getIsShipHit()) {
                return true;
            } else {
                return false;
            }
        } else if ((i > 0) && (i < 9) && j == 0) {
            if (battleFieldMeActivityRandomGame.battleField[i][j + 1].getIsShipHit() ||
                    battleFieldMeActivityRandomGame.battleField[i + 1][j].getIsShipHit() ||
                    battleFieldMeActivityRandomGame.battleField[i + 1][j + 1].getIsShipHit() ||
                    battleFieldMeActivityRandomGame.battleField[i - 1][j].getIsShipHit() ||
                    battleFieldMeActivityRandomGame.battleField[i - 1][j + 1].getIsShipHit()) {
                return true;
            } else {
                return false;
            }
        } else if (i == 9 && j == 0) {
            if (battleFieldMeActivityRandomGame.battleField[i][j + 1].getIsShipHit() ||
                    battleFieldMeActivityRandomGame.battleField[i - 1][j].getIsShipHit() ||
                    battleFieldMeActivityRandomGame.battleField[i - 1][j + 1].getIsShipHit()) {
                return true;
            } else {
                return false;
            }

        } else if (i == 0 && ((j > 0) && (j < 9))) {
            if (battleFieldMeActivityRandomGame.battleField[i][j - 1].getIsShipHit() ||
                    battleFieldMeActivityRandomGame.battleField[i][j + 1].getIsShipHit() ||
                    battleFieldMeActivityRandomGame.battleField[i + 1][j - 1].getIsShipHit() ||
                    battleFieldMeActivityRandomGame.battleField[i + 1][j].getIsShipHit() ||
                    battleFieldMeActivityRandomGame.battleField[i + 1][j + 1].getIsShipHit()) {
                return true;
            } else {
                return false;
            }

        } else if (((i > 0) && (i < 9)) && ((j > 0) && (j < 9))) {
            if (battleFieldMeActivityRandomGame.battleField[i][j - 1].getIsShipHit() ||
                    battleFieldMeActivityRandomGame.battleField[i][j + 1].getIsShipHit() ||
                    battleFieldMeActivityRandomGame.battleField[i + 1][j - 1].getIsShipHit() ||
                    battleFieldMeActivityRandomGame.battleField[i + 1][j].getIsShipHit() ||
                    battleFieldMeActivityRandomGame.battleField[i + 1][j + 1].getIsShipHit() ||
                    battleFieldMeActivityRandomGame.battleField[i - 1][j - 1].getIsShipHit() ||
                    battleFieldMeActivityRandomGame.battleField[i - 1][j].getIsShipHit() ||
                    battleFieldMeActivityRandomGame.battleField[i - 1][j + 1].getIsShipHit()) {
                return true;
            } else {
                return false;
            }

        } else if (i == 9 && ((j > 0) && (j < 9))) {
            if (battleFieldMeActivityRandomGame.battleField[i][j - 1].getIsShipHit() ||
                    battleFieldMeActivityRandomGame.battleField[i][j + 1].getIsShipHit() ||
                    battleFieldMeActivityRandomGame.battleField[i - 1][j - 1].getIsShipHit() ||
                    battleFieldMeActivityRandomGame.battleField[i - 1][j].getIsShipHit() ||
                    battleFieldMeActivityRandomGame.battleField[i - 1][j + 1].getIsShipHit()) {
                return true;
            } else {
                return false;
            }

        } else if (i == 0 && j == 9) {
            if (battleFieldMeActivityRandomGame.battleField[i][j - 1].getIsShipHit() ||
                    battleFieldMeActivityRandomGame.battleField[i + 1][j - 1].getIsShipHit() ||
                    battleFieldMeActivityRandomGame.battleField[i + 1][j].getIsShipHit()) {
                return true;
            } else {
                return false;
            }

        } else if (((i > 0) && (i < 9)) && (j == 9)) {
            if (battleFieldMeActivityRandomGame.battleField[i][j - 1].getIsShipHit() ||
                    battleFieldMeActivityRandomGame.battleField[i + 1][j - 1].getIsShipHit() ||
                    battleFieldMeActivityRandomGame.battleField[i + 1][j].getIsShipHit() ||
                    battleFieldMeActivityRandomGame.battleField[i - 1][j - 1].getIsShipHit() ||
                    battleFieldMeActivityRandomGame.battleField[i - 1][j].getIsShipHit()) {
                return true;
            } else {
                return false;
            }

        } else if ((i == 9) && (j == 9)) {
            if (battleFieldMeActivityRandomGame.battleField[i][j - 1].getIsShipHit() ||
                    battleFieldMeActivityRandomGame.battleField[i - 1][j - 1].getIsShipHit() ||
                    battleFieldMeActivityRandomGame.battleField[i - 1][j].getIsShipHit()) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    private void dobijShip(int i, int j) {
        if (direction == 1) {
            if (x >= 0) {
                if (!battleFieldMeActivityRandomGame.battleField[x][j].isHit()) {
                    if (x == 0) {
                        if (battleFieldMeActivityRandomGame.battleField[x][j].isShip()) {
                            displayShipCellHit((TextView) layoutMy.getChildAt(10 * x + j));
                            //            displayShipCellHit(TextViewArrayActivityRandomGameMe, x, j);
                            ShootTable.remove(Integer.valueOf(x * 10 + j));
                            battleFieldMeActivityRandomGame.battleField[x][j].setHit(true);
                            if (zatopiony2(battleFieldMeActivityRandomGame.getBattleField(i, j).getNumberOfMasts(), battleFieldMeActivityRandomGame.getBattleField(i, j).getShipNumber())) {
                                newShoot = true;
                            } else {
                                x = x - 1;
                            }
                        } else {
                            displayWaterCellHit((TextView) layoutMy.getChildAt(10 * x + j));
                            //          displayWaterCellHit(TextViewArrayActivityRandomGameMe, x, j);
                            ShootTable.remove(Integer.valueOf(x * 10 + j));
                            battleFieldMeActivityRandomGame.battleField[x][j].setHit(true);
                            myTurn = true;
                            direction = 2;
                            x = i + 1;
                        }
                    } else {
                        if (j == 0) {
                            if ((battleFieldMeActivityRandomGame.battleField[x - 1][j].isHit() &&
                                    battleFieldMeActivityRandomGame.battleField[x - 1][j].isShip()) ||
                                    (battleFieldMeActivityRandomGame.battleField[x - 1][j + 1].isHit() &&
                                            battleFieldMeActivityRandomGame.battleField[x - 1][j + 1].isShip())) {
                                direction = 2;
                                x = i + 1;
                            } else {
                                if (battleFieldMeActivityRandomGame.battleField[x][j].isShip()) {
                                    displayShipCellHit((TextView) layoutMy.getChildAt(10 * x + j));
                                    //            displayShipCellHit(TextViewArrayActivityRandomGameMe, x, j);
                                    ShootTable.remove(Integer.valueOf(x * 10 + j));
                                    battleFieldMeActivityRandomGame.battleField[x][j].setHit(true);
                                    if (zatopiony2(battleFieldMeActivityRandomGame.getBattleField(i, j).getNumberOfMasts(), battleFieldMeActivityRandomGame.getBattleField(i, j).getShipNumber())) {
                                        newShoot = true;
                                    } else {
                                        x = x - 1;
                                    }
                                } else {
                                    displayWaterCellHit((TextView) layoutMy.getChildAt(10 * x + j));
//                                    displayWaterCellHit(TextViewArrayActivityRandomGameMe, x, j);
                                    ShootTable.remove(Integer.valueOf(x * 10 + j));
                                    battleFieldMeActivityRandomGame.battleField[x][j].setHit(true);
                                    myTurn = true;
                                    direction = 2;
                                    x = i + 1;
                                }
                            }

                        } else if (j == 9) {
                            if ((battleFieldMeActivityRandomGame.battleField[x - 1][j - 1].isHit() &&
                                    battleFieldMeActivityRandomGame.battleField[x - 1][j - 1].isShip()) ||
                                    (battleFieldMeActivityRandomGame.battleField[x - 1][j].isHit() &&
                                            battleFieldMeActivityRandomGame.battleField[x - 1][j].isShip())) {
                                direction = 2;
                                x = i + 1;
                            } else {
                                if (battleFieldMeActivityRandomGame.battleField[x][j].isShip()) {
                                    displayShipCellHit((TextView) layoutMy.getChildAt(10 * x + j));
                                    //                        displayShipCellHit(TextViewArrayActivityRandomGameMe, x, j);
                                    ShootTable.remove(Integer.valueOf(x * 10 + j));
                                    battleFieldMeActivityRandomGame.battleField[x][j].setHit(true);
                                    if (zatopiony2(battleFieldMeActivityRandomGame.getBattleField(i, j).getNumberOfMasts(), battleFieldMeActivityRandomGame.getBattleField(i, j).getShipNumber())) {
                                        newShoot = true;
                                    } else {
                                        x = x - 1;
                                    }
                                } else {
                                    displayWaterCellHit((TextView) layoutMy.getChildAt(10 * x + j));
                                    //                        displayWaterCellHit(TextViewArrayActivityRandomGameMe, x, j);
                                    ShootTable.remove(Integer.valueOf(x * 10 + j));
                                    battleFieldMeActivityRandomGame.battleField[x][j].setHit(true);
                                    myTurn = true;
                                    direction = 2;
                                    x = i + 1;
                                }
                            }
                        } else {
                            if ((battleFieldMeActivityRandomGame.battleField[x - 1][j - 1].isHit() &&
                                    battleFieldMeActivityRandomGame.battleField[x - 1][j - 1].isShip()) ||
                                    (battleFieldMeActivityRandomGame.battleField[x - 1][j].isHit() &&
                                            battleFieldMeActivityRandomGame.battleField[x - 1][j].isShip()) ||
                                    (battleFieldMeActivityRandomGame.battleField[x - 1][j + 1].isHit() &&
                                            battleFieldMeActivityRandomGame.battleField[x - 1][j + 1].isShip())) {
                                direction = 2;
                                x = i + 1;
                            } else {
                                if (battleFieldMeActivityRandomGame.battleField[x][j].isShip()) {
                                    displayShipCellHit((TextView) layoutMy.getChildAt(10 * x + j));
                                    //                                displayShipCellHit(TextViewArrayActivityRandomGameMe, x, j);
                                    ShootTable.remove(Integer.valueOf(x * 10 + j));
                                    battleFieldMeActivityRandomGame.battleField[x][j].setHit(true);
                                    if (zatopiony2(battleFieldMeActivityRandomGame.getBattleField(i, j).getNumberOfMasts(), battleFieldMeActivityRandomGame.getBattleField(i, j).getShipNumber())) {
                                        newShoot = true;
                                    } else {
                                        x = x - 1;
                                    }
                                } else {
                                    displayWaterCellHit((TextView) layoutMy.getChildAt(10 * x + j));
                                    //                                   displayWaterCellHit(TextViewArrayActivityRandomGameMe, x, j);
                                    ShootTable.remove(Integer.valueOf(x * 10 + j));
                                    battleFieldMeActivityRandomGame.battleField[x][j].setHit(true);
                                    myTurn = true;
                                    direction = 2;
                                    x = i + 1;
                                }
                            }

                        }
                    }
                } else {
                    direction = 2;
                    x = i + 1;
                }
            } else {
                direction = 2;
                x = i + 1;
            }
        } else if (direction == 2) {
            if (x <= 9) {
                if (!battleFieldMeActivityRandomGame.battleField[x][j].isHit()) {
                    if (x == 9) {
                        if (battleFieldMeActivityRandomGame.battleField[x][j].isShip()) {
                            displayShipCellHit((TextView) layoutMy.getChildAt(10 * x + j));
//                            displayShipCellHit(TextViewArrayActivityRandomGameMe,x,j);
                            ShootTable.remove(Integer.valueOf(x * 10 + j));
                            battleFieldMeActivityRandomGame.battleField[x][j].setHit(true);
                            if (zatopiony2(battleFieldMeActivityRandomGame.getBattleField(i, j).getNumberOfMasts(), battleFieldMeActivityRandomGame.getBattleField(i, j).getShipNumber())) {
                                newShoot = true;
                            } else {
                                x = x + 1;
                            }
                        } else {
                            displayWaterCellHit((TextView) layoutMy.getChildAt(10 * x + j));
//                            displayWaterCellHit(TextViewArrayActivityRandomGameMe,x,j);
                            ShootTable.remove(Integer.valueOf(x * 10 + j));
                            battleFieldMeActivityRandomGame.battleField[x][j].setHit(true);
                            myTurn = true;
                            direction = 3;
                            y = j - 1;
                        }
                    } else {
                        if (j == 0) {
                            if ((battleFieldMeActivityRandomGame.battleField[x + 1][j].isHit() &&
                                    battleFieldMeActivityRandomGame.battleField[x + 1][j].isShip()) ||
                                    (battleFieldMeActivityRandomGame.battleField[x + 1][j + 1].isHit() &&
                                            battleFieldMeActivityRandomGame.battleField[x + 1][j + 1].isShip())) {
                                direction = 3;
                                y = j - 1;
                            } else {
                                if (battleFieldMeActivityRandomGame.battleField[x][j].isShip()) {
                                    displayShipCellHit((TextView) layoutMy.getChildAt(10 * x + j));
                                    //            displayShipCellHit(TextViewArrayActivityRandomGameMe,x,j);
                                    ShootTable.remove(Integer.valueOf(x * 10 + j));
                                    battleFieldMeActivityRandomGame.battleField[x][j].setHit(true);
                                    if (zatopiony2(battleFieldMeActivityRandomGame.getBattleField(i, j).getNumberOfMasts(), battleFieldMeActivityRandomGame.getBattleField(i, j).getShipNumber())) {
                                        newShoot = true;
                                    } else {
                                        x = x + 1;
                                    }
                                } else {
                                    displayWaterCellHit((TextView) layoutMy.getChildAt(10 * x + j));
//                                    displayWaterCellHit(TextViewArrayActivityRandomGameMe,x,j);
                                    ShootTable.remove(Integer.valueOf(x * 10 + j));
                                    battleFieldMeActivityRandomGame.battleField[x][j].setHit(true);
                                    myTurn = true;
                                    direction = 3;
                                    y = j - 1;
                                }
                            }
                        } else if (j == 9) {
                            if ((battleFieldMeActivityRandomGame.battleField[x + 1][j].isHit() &&
                                    battleFieldMeActivityRandomGame.battleField[x + 1][j].isShip()) ||
                                    (battleFieldMeActivityRandomGame.battleField[x + 1][j - 1].isHit() &&
                                            battleFieldMeActivityRandomGame.battleField[x + 1][j - 1].isShip())) {
                                direction = 3;
                                y = j - 1;
                            } else {
                                if (battleFieldMeActivityRandomGame.battleField[x][j].isShip()) {
                                    displayShipCellHit((TextView) layoutMy.getChildAt(10 * x + j));
//                                    displayShipCellHit(TextViewArrayActivityRandomGameMe, x, j);
                                    ShootTable.remove(Integer.valueOf(x * 10 + j));
                                    battleFieldMeActivityRandomGame.battleField[x][j].setHit(true);
                                    if (zatopiony2(battleFieldMeActivityRandomGame.getBattleField(i, j).getNumberOfMasts(), battleFieldMeActivityRandomGame.getBattleField(i, j).getShipNumber())) {
                                        newShoot = true;
                                    } else {
                                        x = x + 1;
                                    }
                                } else {
                                    displayWaterCellHit((TextView) layoutMy.getChildAt(10 * x + j));
//                                    displayWaterCellHit(TextViewArrayActivityRandomGameMe, x, j);
                                    ShootTable.remove(Integer.valueOf(x * 10 + j));
                                    battleFieldMeActivityRandomGame.battleField[x][j].setHit(true);
                                    myTurn = true;
                                    direction = 3;
                                    y = j - 1;
                                }
                            }
                        } else {
                            if ((battleFieldMeActivityRandomGame.battleField[x + 1][j - 1].isHit() &&
                                    battleFieldMeActivityRandomGame.battleField[x + 1][j - 1].isShip()) ||
                                    (battleFieldMeActivityRandomGame.battleField[x + 1][j].isHit() &&
                                            battleFieldMeActivityRandomGame.battleField[x + 1][j].isShip()) ||
                                    (battleFieldMeActivityRandomGame.battleField[x + 1][j + 1].isHit() &&
                                            battleFieldMeActivityRandomGame.battleField[x + 1][j + 1].isShip())) {
                                direction = 3;
                                y = j - 1;
                            } else {
                                if (battleFieldMeActivityRandomGame.battleField[x][j].isShip()) {
                                    displayShipCellHit((TextView) layoutMy.getChildAt(10 * x + j));
//                                    displayShipCellHit(TextViewArrayActivityRandomGameMe,x,j);
                                    ShootTable.remove(Integer.valueOf(x * 10 + j));
                                    battleFieldMeActivityRandomGame.battleField[x][j].setHit(true);
                                    if (zatopiony2(battleFieldMeActivityRandomGame.getBattleField(i, j).getNumberOfMasts(), battleFieldMeActivityRandomGame.getBattleField(i, j).getShipNumber())) {
                                        newShoot = true;
                                    } else {
                                        x = x + 1;
                                    }
                                } else {
                                    displayWaterCellHit((TextView) layoutMy.getChildAt(10 * x + j));
//                                    displayWaterCellHit(TextViewArrayActivityRandomGameMe,x,j);
                                    ShootTable.remove(Integer.valueOf(x * 10 + j));
                                    battleFieldMeActivityRandomGame.battleField[x][j].setHit(true);
                                    myTurn = true;
                                    direction = 3;
                                    y = j - 1;
                                }
                            }
                        }
                    }
                } else {
                    direction = 3;
                    y = j - 1;
                }
            } else {
                direction = 3;
                y = j - 1;
            }
        } else if (direction == 3) {
            if (y >= 0) {
                if (!battleFieldMeActivityRandomGame.battleField[i][y].isHit()) {
                    if (y == 0) {
                        if (battleFieldMeActivityRandomGame.battleField[i][y].isShip()) {
                            displayShipCellHit((TextView) layoutMy.getChildAt(10 * i + y));
//                            displayShipCellHit(TextViewArrayActivityRandomGameMe, i, y);
                            ShootTable.remove(Integer.valueOf(i * 10 + y));
                            battleFieldMeActivityRandomGame.battleField[i][y].setHit(true);
                            if (zatopiony2(battleFieldMeActivityRandomGame.getBattleField(i, j).getNumberOfMasts(), battleFieldMeActivityRandomGame.getBattleField(i, j).getShipNumber())) {
                                newShoot = true;
                            } else {
                                y = y - 1;
                            }
                        } else {
                            displayWaterCellHit((TextView) layoutMy.getChildAt(10 * i + y));
//                            displayWaterCellHit(TextViewArrayActivityRandomGameMe, i, y);
                            ShootTable.remove(Integer.valueOf(i * 10 + y));
                            battleFieldMeActivityRandomGame.battleField[i][y].setHit(true);
                            myTurn = true;
                            direction = 4;
                            y = j + 1;
                        }
                    } else {
                        if (i == 0) {
                            if ((battleFieldMeActivityRandomGame.battleField[i][y - 1].isShip() &&
                                    battleFieldMeActivityRandomGame.battleField[i][y - 1].isHit()) ||
                                    (battleFieldMeActivityRandomGame.battleField[i + 1][y - 1].isShip() &&
                                            battleFieldMeActivityRandomGame.battleField[i + 1][y - 1].isHit())) {
                                direction = 4;
                                y = j + 1;
                            } else {
                                if (battleFieldMeActivityRandomGame.battleField[i][y].isShip()) {
                                    displayShipCellHit((TextView) layoutMy.getChildAt(10 * i + y));
                                    //                           displayShipCellHit(TextViewArrayActivityRandomGameMe, i, y);
                                    ShootTable.remove(Integer.valueOf(i * 10 + y));
                                    battleFieldMeActivityRandomGame.battleField[i][y].setHit(true);
                                    if (zatopiony2(battleFieldMeActivityRandomGame.getBattleField(i, j).getNumberOfMasts(), battleFieldMeActivityRandomGame.getBattleField(i, j).getShipNumber())) {
                                        newShoot = true;
                                    } else {
                                        y = y - 1;
                                    }
                                } else {
                                    displayWaterCellHit((TextView) layoutMy.getChildAt(10 * i + y));
//                                    displayWaterCellHit(TextViewArrayActivityRandomGameMe, i, y);
                                    ShootTable.remove(Integer.valueOf(i * 10 + y));
                                    battleFieldMeActivityRandomGame.battleField[i][y].setHit(true);
                                    myTurn = true;
                                    direction = 4;
                                    y = j + 1;
                                }
                            }
                        } else if (i == 9) {
                            if ((battleFieldMeActivityRandomGame.battleField[i][y - 1].isShip() &&
                                    battleFieldMeActivityRandomGame.battleField[i][y - 1].isHit()) ||
                                    (battleFieldMeActivityRandomGame.battleField[i - 1][y - 1].isShip() &&
                                            battleFieldMeActivityRandomGame.battleField[i - 1][y - 1].isHit())) {
                                direction = 4;
                                y = j + 1;
                            } else {
                                if (battleFieldMeActivityRandomGame.battleField[i][y].isShip()) {
                                    displayShipCellHit((TextView) layoutMy.getChildAt(10 * i + y));
//                                    displayShipCellHit(TextViewArrayActivityRandomGameMe, i, y);
                                    ShootTable.remove(Integer.valueOf(i * 10 + y));
                                    battleFieldMeActivityRandomGame.battleField[i][y].setHit(true);
                                    if (zatopiony2(battleFieldMeActivityRandomGame.getBattleField(i, j).getNumberOfMasts(), battleFieldMeActivityRandomGame.getBattleField(i, j).getShipNumber())) {
                                        newShoot = true;
                                    } else {
                                        y = y - 1;
                                    }
                                } else {
                                    displayWaterCellHit((TextView) layoutMy.getChildAt(10 * i + y));
//                                    displayWaterCellHit(TextViewArrayActivityRandomGameMe, i, y);
                                    ShootTable.remove(Integer.valueOf(i * 10 + y));
                                    battleFieldMeActivityRandomGame.battleField[i][y].setHit(true);
                                    myTurn = true;
                                    direction = 4;
                                    y = j + 1;
                                }
                            }
                        } else {
                            if ((battleFieldMeActivityRandomGame.battleField[i - 1][y - 1].isShip() &&
                                    battleFieldMeActivityRandomGame.battleField[i - 1][y - 1].isHit()) ||
                                    (battleFieldMeActivityRandomGame.battleField[i][y - 1].isShip() &&
                                            battleFieldMeActivityRandomGame.battleField[i][y - 1].isHit()) ||
                                    (battleFieldMeActivityRandomGame.battleField[i + 1][y - 1].isShip() &&
                                            battleFieldMeActivityRandomGame.battleField[i + 1][y - 1].isHit())) {
                                direction = 4;
                                y = j + 1;
                            } else {
                                if (battleFieldMeActivityRandomGame.battleField[i][y].isShip()) {
                                    displayShipCellHit((TextView) layoutMy.getChildAt(10 * i + y));
//                                    displayShipCellHit(TextViewArrayActivityRandomGameMe, i, y);
                                    ShootTable.remove(Integer.valueOf(i * 10 + y));
                                    battleFieldMeActivityRandomGame.battleField[i][y].setHit(true);
                                    if (zatopiony2(battleFieldMeActivityRandomGame.getBattleField(i, j).getNumberOfMasts(), battleFieldMeActivityRandomGame.getBattleField(i, j).getShipNumber())) {
                                        newShoot = true;
                                    } else {
                                        y = y - 1;
                                    }
                                } else {
                                    displayWaterCellHit((TextView) layoutMy.getChildAt(10 * i + y));
//                                    displayWaterCellHit(TextViewArrayActivityRandomGameMe, i, y);
                                    ShootTable.remove(Integer.valueOf(i * 10 + y));
                                    battleFieldMeActivityRandomGame.battleField[i][y].setHit(true);
                                    myTurn = true;
                                    direction = 4;
                                    y = j + 1;
                                }
                            }
                        }

                    }
                } else {
                    direction = 4;
                    y = j + 1;
                }
            } else {
                direction = 4;
                y = j + 1;
            }
        } else if (direction == 4) {
            if (y <= 9) {
                if (!battleFieldMeActivityRandomGame.battleField[i][y].isHit()) {
                    if (y == 9) {
                        if (battleFieldMeActivityRandomGame.battleField[i][y].isShip()) {
                            displayShipCellHit((TextView) layoutMy.getChildAt(10 * i + y));
//                            displayShipCellHit(TextViewArrayActivityRandomGameMe,i,y);
                            ShootTable.remove(Integer.valueOf(i * 10 + y));
                            battleFieldMeActivityRandomGame.battleField[i][y].setHit(true);
                            if (zatopiony2(battleFieldMeActivityRandomGame.getBattleField(i, j).getNumberOfMasts(), battleFieldMeActivityRandomGame.getBattleField(i, j).getShipNumber())) {
                                newShoot = true;
                            } else {
                                y = y + 1;
                            }
                        } else {
                            displayWaterCellHit((TextView) layoutMy.getChildAt(10 * i + y));
//                            displayWaterCellHit(TextViewArrayActivityRandomGameMe,i,y);
                            ShootTable.remove(Integer.valueOf(i * 10 + y));
                            battleFieldMeActivityRandomGame.battleField[i][y].setHit(true);
                            myTurn = true;
                            direction = 5;
                        }
                    } else {
                        if (i == 0) {
                            if ((battleFieldMeActivityRandomGame.battleField[i][y + 1].isShip() &&
                                    battleFieldMeActivityRandomGame.battleField[i][y + 1].isHit()) ||
                                    (battleFieldMeActivityRandomGame.battleField[i + 1][y + 1].isShip() &&
                                            battleFieldMeActivityRandomGame.battleField[i + 1][y + 1].isHit())) {
                                direction = 5;
                            } else {
                                if (battleFieldMeActivityRandomGame.battleField[i][y].isShip()) {
                                    displayShipCellHit((TextView) layoutMy.getChildAt(10 * i + y));
//                                     displayShipCellHit(TextViewArrayActivityRandomGameMe,i,y);
                                    ShootTable.remove(Integer.valueOf(i * 10 + y));
                                    battleFieldMeActivityRandomGame.battleField[i][y].setHit(true);
                                    if (zatopiony2(battleFieldMeActivityRandomGame.getBattleField(i, j).getNumberOfMasts(), battleFieldMeActivityRandomGame.getBattleField(i, j).getShipNumber())) {
                                        newShoot = true;
                                    } else {
                                        y = y + 1;
                                    }
                                } else {
                                    displayWaterCellHit((TextView) layoutMy.getChildAt(10 * i + y));
//                                     displayWaterCellHit(TextViewArrayActivityRandomGameMe,i,y);
                                    ShootTable.remove(Integer.valueOf(i * 10 + y));
                                    battleFieldMeActivityRandomGame.battleField[i][y].setHit(true);
                                    myTurn = true;
                                    direction = 5;
                                }

                            }

                        } else if (i == 9) {
                            if ((battleFieldMeActivityRandomGame.battleField[i][y + 1].isShip() &&
                                    battleFieldMeActivityRandomGame.battleField[i][y + 1].isHit()) ||
                                    (battleFieldMeActivityRandomGame.battleField[i - 1][y + 1].isShip() &&
                                            battleFieldMeActivityRandomGame.battleField[i - 1][y + 1].isHit())) {
                                direction = 5;
                            } else {
                                if (battleFieldMeActivityRandomGame.battleField[i][y].isShip()) {
                                    displayShipCellHit((TextView) layoutMy.getChildAt(10 * i + y));
//                                     displayShipCellHit(TextViewArrayActivityRandomGameMe,i,y);
                                    ShootTable.remove(Integer.valueOf(i * 10 + y));
                                    battleFieldMeActivityRandomGame.battleField[i][y].setHit(true);
                                    if (zatopiony2(battleFieldMeActivityRandomGame.getBattleField(i, j).getNumberOfMasts(), battleFieldMeActivityRandomGame.getBattleField(i, j).getShipNumber())) {
                                        newShoot = true;
                                    } else {
                                        y = y + 1;
                                    }
                                } else {
                                    displayWaterCellHit((TextView) layoutMy.getChildAt(10 * i + y));
//                                     displayWaterCellHit(TextViewArrayActivityRandomGameMe,i,y);
                                    ShootTable.remove(Integer.valueOf(i * 10 + y));
                                    battleFieldMeActivityRandomGame.battleField[i][y].setHit(true);
                                    myTurn = true;
                                    direction = 5;
                                }

                            }

                        } else {
                            if ((battleFieldMeActivityRandomGame.battleField[i + 1][y + 1].isShip() &&
                                    battleFieldMeActivityRandomGame.battleField[i + 1][y + 1].isHit()) ||
                                    (battleFieldMeActivityRandomGame.battleField[i][y + 1].isShip() &&
                                            battleFieldMeActivityRandomGame.battleField[i][y + 1].isHit()) ||
                                    (battleFieldMeActivityRandomGame.battleField[i - 1][y + 1].isShip() &&
                                            battleFieldMeActivityRandomGame.battleField[i - 1][y + 1].isHit())) {
                                direction = 5;
                            } else {
                                if (battleFieldMeActivityRandomGame.battleField[i][y].isShip()) {
                                    displayShipCellHit((TextView) layoutMy.getChildAt(10 * i + y));
//                                     displayShipCellHit(TextViewArrayActivityRandomGameMe,i,y);
                                    ShootTable.remove(Integer.valueOf(i * 10 + y));
                                    battleFieldMeActivityRandomGame.battleField[i][y].setHit(true);
                                    if (zatopiony2(battleFieldMeActivityRandomGame.getBattleField(i, j).getNumberOfMasts(), battleFieldMeActivityRandomGame.getBattleField(i, j).getShipNumber())) {
                                        newShoot = true;
                                    } else {
                                        y = y + 1;
                                    }
                                } else {
                                    displayWaterCellHit((TextView) layoutMy.getChildAt(10 * i + y));
//                                     displayWaterCellHit(TextViewArrayActivityRandomGameMe,i,y);
                                    ShootTable.remove(Integer.valueOf(i * 10 + y));
                                    battleFieldMeActivityRandomGame.battleField[i][y].setHit(true);
                                    myTurn = true;
                                    direction = 5;
                                }

                            }


                        }
                    }
                } else {
                    direction = 5;
                }
            } else {
                direction = 5;
            }
        } else
            newShoot = true;
    }

    private void displayBattleFieldActivityRandomGamePlayerOne(GridLayout gridLayout, BattleField battleFieldPlayerOneActivityRandomGame) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (battleFieldPlayerOneActivityRandomGame.getBattleField(i, j).isShip()) {
                    gridLayout.getChildAt(10 * i + j).setBackground(new ShipGreyWhiteBackground(GameBattle.this,square));
                }
            }
        }
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (enableTouchListener&&!alertDialogFlag) {
            final int X = (int) event.getRawX();
            final int Y = (int) event.getRawY();
            int xOnTouch = (X - locationLayout[0]) / width;
            int yOnTouch = (Y - locationLayout[1]) / height;
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:

                    if (battleFieldOpponent[yOnTouch][xOnTouch] == BATTLE_CELL) {
                        for (int i = 0; i < 10; i++) {
                            for (int j = 0; j < 10; j++) {
                                if (xOnTouch == j || yOnTouch == i) {
                                    if (battleFieldOpponent[i][j] == BATTLE_CELL) {
                                        tv = (TextView) layoutOpponent.getChildAt(10 * i + j);
                                        tv.setBackground(new BattleCellGreenBackground(GameBattle.this,square));
                                    } else if (battleFieldOpponent[i][j] == WATER) {
                                        tv = (TextView) layoutOpponent.getChildAt(10 * i + j);
                                        tv.setBackground(new WaterBlueGreenBackground(GameBattle.this,square));
                                    } else {
                                        tv = (TextView) layoutOpponent.getChildAt(10 * i + j);
                                        tv.setBackground(new ShipBlueGreenBackground(GameBattle.this,square));
                                    }
                                }
                            }
                        }
                    } else {
                        for (int i = 0; i < 10; i++) {
                            for (int j = 0; j < 10; j++) {
                                if (xOnTouch == j || yOnTouch == i) {
                                    if (battleFieldOpponent[i][j] == BATTLE_CELL) {
                                        tv = (TextView) layoutOpponent.getChildAt(10 * i + j);
                                        tv.setBackground(new BattleCellRedBackground(GameBattle.this,square));
                                    } else if (battleFieldOpponent[i][j] == WATER) {
                                        tv = (TextView) layoutOpponent.getChildAt(10 * i + j);
                                        tv.setBackground(new WaterBlueRedBackground(GameBattle.this,square));
                                    } else {
                                        tv = (TextView) layoutOpponent.getChildAt(10 * i + j);
                                        tv.setBackground(new ShipBlueRedBackground(GameBattle.this,square));
                                    }


                                }
                            }
                        }
                    }
                    break;

                case MotionEvent.ACTION_MOVE:

                    if (xOnTouch >= 0 && xOnTouch <= 9 && yOnTouch >= 0 && yOnTouch <= 9) {
                        pokazStatki();
                        if (battleFieldOpponent[yOnTouch][xOnTouch] == BATTLE_CELL) {
                            for (int i = 0; i < 10; i++) {
                                for (int j = 0; j < 10; j++) {
                                    if (xOnTouch == j || yOnTouch == i) {
                                        if (battleFieldOpponent[i][j] == BATTLE_CELL) {
                                            tv = (TextView) layoutOpponent.getChildAt(10 * i + j);
                                            tv.setBackground(new BattleCellGreenBackground(GameBattle.this,square));
                                        } else if (battleFieldOpponent[i][j] == WATER) {
                                            tv = (TextView) layoutOpponent.getChildAt(10 * i + j);
                                            tv.setBackground(new WaterBlueGreenBackground(GameBattle.this,square));
                                        } else {
                                            tv = (TextView) layoutOpponent.getChildAt(10 * i + j);
                                            tv.setBackground(new ShipBlueGreenBackground(GameBattle.this,square));
                                        }
                                    }
                                }
                            }
                        } else {
                            for (int i = 0; i < 10; i++) {
                                for (int j = 0; j < 10; j++) {
                                    if (xOnTouch == j || yOnTouch == i) {
                                        if (battleFieldOpponent[i][j] == BATTLE_CELL) {
                                            tv = (TextView) layoutOpponent.getChildAt(10 * i + j);
                                            tv.setBackground(new BattleCellRedBackground(GameBattle.this,square));
                                        } else if (battleFieldOpponent[i][j] == WATER) {
                                            tv = (TextView) layoutOpponent.getChildAt(10 * i + j);
                                            tv.setBackground(new WaterBlueRedBackground(GameBattle.this,square));
                                        } else {
                                            tv = (TextView) layoutOpponent.getChildAt(10 * i + j);
                                            tv.setBackground(new ShipBlueRedBackground(GameBattle.this,square));
                                        }

                                    }
                                }
                            }
                        }
                    } else {
                        pokazStatki();
                    }
                    break;
                case MotionEvent.ACTION_UP:

                    if (xOnTouch >= 0 && xOnTouch <= 9 && yOnTouch >= 0 && yOnTouch <= 9) {
                        if (battleFieldOpponent[yOnTouch][xOnTouch] == BATTLE_CELL) {


                            hitCell(yOnTouch, xOnTouch);


                        } else
                            pokazStatki();
                    } else ;

                    break;
            }
        } else ;
        layoutOpponent.invalidate();
        return true;
    }

    private void hitCell(int x1, int y1) {
        explosionSound = MediaPlayer.create(this,R.raw.explosion);
        waterSplashSound = MediaPlayer.create(this,R.raw.water_splash);
        battleFieldOpponentActivityRandomGame.getBattleField(x1, y1).setHit(true);
        if (battleFieldOpponentActivityRandomGame.getBattleField(x1, y1).isShip()) {
            battleFieldOpponent[x1][y1] = SHIP_RED;
            if(soundOn) {
                explosionSound.start();
            }
            updateCounters(battleFieldOpponentActivityRandomGame.getBattleField(x1, y1).getNumberOfMasts(),
                    battleFieldOpponentActivityRandomGame.getBattleField(x1, y1).getShipNumber(),true);
            showCounters();
            if (myWin()) {
                mHandler.postDelayed(game, deelay);
            } else {
                if (loggedIn) {
                    singlePlayerMatch.setGame(true);
                    singlePlayerMatch.setMyTurn(myTurn);
                    singlePlayerMatch.setDifficulty(level);
                    singlePlayerMatch.setBattleFieldListMyFromArray(battleFieldMeActivityRandomGame);
                    singlePlayerMatch.setBattleFieldListOpponentFromArray(battleFieldOpponentActivityRandomGame);
                    singlePlayerMatch.setPositionI(positionI);
                    singlePlayerMatch.setPositionJ(positionJ);
                    singlePlayerMatch.setNewShoot(newShoot);
                    singlePlayerMatch.setDirection(direction);
                    singlePlayerMatch.setX(x);
                    singlePlayerMatch.setY(y);
                    databaseReference.child("singlePlayerMatch").setValue(singlePlayerMatch);
                }
            }
            pokazStatki();
        } else {
            battleFieldOpponent[x1][y1] = WATER;
            if(soundOn) {
                waterSplashSound.start();
            }
            pokazStatki();
            myTurn = false;
            enableTouchListener = false;
            if (loggedIn) {
                singlePlayerMatch.setGame(true);
                singlePlayerMatch.setMyTurn(myTurn);
                singlePlayerMatch.setDifficulty(level);
                singlePlayerMatch.setBattleFieldListMyFromArray(battleFieldMeActivityRandomGame);
                singlePlayerMatch.setBattleFieldListOpponentFromArray(battleFieldOpponentActivityRandomGame);
                singlePlayerMatch.setPositionI(positionI);
                singlePlayerMatch.setPositionJ(positionJ);
                singlePlayerMatch.setNewShoot(newShoot);
                singlePlayerMatch.setDirection(direction);
                singlePlayerMatch.setX(x);
                singlePlayerMatch.setY(y);
                databaseReference.child("singlePlayerMatch").setValue(singlePlayerMatch);
            }
            mHandler.postDelayed(game, deelay);
        }

    }

    private void showCounters() {
        if (level == 0) {
            if (!(shipFourMastsCounter == 0)) {
                for (int i = 0; i < shipFourMastsCounter; i++) {
                    fourMasts.getChildAt(i).setBackground(new ShipRedWhiteBackground(GameBattle.this,square));
                }
            }
            if (!(shipThreeMastsCounterFirst == 0)) {
                for (int i = 0; i < shipThreeMastsCounterFirst; i++) {
                    threeMastsFirst.getChildAt(i).setBackground(new ShipRedWhiteBackground(GameBattle.this,square));
                }
            }
            if (!(shipThreeMastsCounterSecond == 0)) {
                for (int i = 0; i < shipThreeMastsCounterSecond; i++) {
                    threeMastsSecond.getChildAt(i).setBackground(new ShipRedWhiteBackground(GameBattle.this,square));
                }
            }
            if (!(shipTwoMastsCounterFirst == 0)) {
                for (int i = 0; i < shipTwoMastsCounterFirst; i++) {
                    twoMastsFirst.getChildAt(i).setBackground(new ShipRedWhiteBackground(GameBattle.this,square));
                }
            }
            if (!(shipTwoMastsCounterSecond == 0)) {
                for (int i = 0; i < shipTwoMastsCounterSecond; i++) {
                    twoMastsSecond.getChildAt(i).setBackground(new ShipRedWhiteBackground(GameBattle.this,square));
                }
            }
            if (!(shipTwoMastsCounterThird == 0)) {
                for (int i = 0; i < shipTwoMastsCounterThird; i++) {
                    twoMastsThird.getChildAt(i).setBackground(new ShipRedWhiteBackground(GameBattle.this,square));
                }
            }
            if (!(shipOneMastsCounterFirst == 0)) {
                oneMastsFirst.getChildAt(0).setBackground(new ShipRedWhiteBackground(GameBattle.this,square));
            }
            if (!(shipOneMastsCounterSecond == 0)) {
                oneMastsSecond.getChildAt(0).setBackground(new ShipRedWhiteBackground(GameBattle.this,square));
            }
            if (!(shipOneMastsCounterThird == 0)) {
                oneMastsThird.getChildAt(0).setBackground(new ShipRedWhiteBackground(GameBattle.this,square));
            }
            if (!(shipOneMastsCounterFourth == 0)) {
                oneMastsFourth.getChildAt(0).setBackground(new ShipRedWhiteBackground(GameBattle.this,square));
            }
        } else {
            if (shipFourMastsCounter == 4) {
                for (int i = 0; i < shipFourMastsCounter; i++) {
                    fourMasts.getChildAt(i).setBackground(new ShipRedWhiteBackground(GameBattle.this,square));
                }
            }
            if (shipThreeMastsCounterFirst == 3) {
                for (int i = 0; i < shipThreeMastsCounterFirst; i++) {
                    threeMastsFirst.getChildAt(i).setBackground(new ShipRedWhiteBackground(GameBattle.this,square));
                }
            }
            if (shipThreeMastsCounterSecond == 3) {
                for (int i = 0; i < shipThreeMastsCounterSecond; i++) {
                    threeMastsSecond.getChildAt(i).setBackground(new ShipRedWhiteBackground(GameBattle.this,square));
                }
            }
            if (shipTwoMastsCounterFirst == 2) {
                for (int i = 0; i < shipTwoMastsCounterFirst; i++) {
                    twoMastsFirst.getChildAt(i).setBackground(new ShipRedWhiteBackground(GameBattle.this,square));
                }
            }
            if (shipTwoMastsCounterSecond == 2) {
                for (int i = 0; i < shipTwoMastsCounterSecond; i++) {
                    twoMastsSecond.getChildAt(i).setBackground(new ShipRedWhiteBackground(GameBattle.this,square));
                }
            }
            if (shipTwoMastsCounterThird == 2) {
                for (int i = 0; i < shipTwoMastsCounterThird; i++) {
                    twoMastsThird.getChildAt(i).setBackground(new ShipRedWhiteBackground(GameBattle.this,square));
                }
            }
            if (shipOneMastsCounterFirst == 1) {
                oneMastsFirst.getChildAt(0).setBackground(new ShipRedWhiteBackground(GameBattle.this,square));
            }
            if (shipOneMastsCounterSecond == 1) {
                oneMastsSecond.getChildAt(0).setBackground(new ShipRedWhiteBackground(GameBattle.this,square));
            }
            if (shipOneMastsCounterThird == 1) {
                oneMastsThird.getChildAt(0).setBackground(new ShipRedWhiteBackground(GameBattle.this,square));
            }
            if (shipOneMastsCounterFourth == 1) {
                oneMastsFourth.getChildAt(0).setBackground(new ShipRedWhiteBackground(GameBattle.this,square));
            }
        }

    }

    private void updateCounters(int numberOfMasts, int shipNumber, boolean sound) {
        int number = 10 * numberOfMasts + shipNumber;
        shoutYaySound=MediaPlayer.create(GameBattle.this,R.raw.shout_yay);
        switch (number) {
            case 41:
                shipFourMastsCounter++;
                if (shipFourMastsCounter == 4) {
                    updateBattleField(numberOfMasts, shipNumber);
                    if(soundOn&&sound) {
                        shoutYaySound.start();
                    }
                }
                break;
            case 31:
                shipThreeMastsCounterFirst++;
                if (shipThreeMastsCounterFirst == 3) {
                    updateBattleField(numberOfMasts, shipNumber);
                    if(soundOn&&sound) {
                        shoutYaySound.start();
                    }
                }
                break;
            case 32:
                shipThreeMastsCounterSecond++;
                if (shipThreeMastsCounterSecond == 3) {
                    updateBattleField(numberOfMasts, shipNumber);
                    if(soundOn&&sound) {
                        shoutYaySound.start();
                    }
                }
                break;
            case 21:
                shipTwoMastsCounterFirst++;
                if (shipTwoMastsCounterFirst == 2) {
                    updateBattleField(numberOfMasts, shipNumber);
                    if(soundOn&&sound) {
                        shoutYaySound.start();
                    }
                }
                break;
            case 22:
                shipTwoMastsCounterSecond++;
                if (shipTwoMastsCounterSecond == 2) {
                    updateBattleField(numberOfMasts, shipNumber);
                    if(soundOn&&sound) {
                        shoutYaySound.start();
                    }
                }
                break;
            case 23:
                shipTwoMastsCounterThird++;
                if (shipTwoMastsCounterThird == 2) {
                    updateBattleField(numberOfMasts, shipNumber);
                    if(soundOn&&sound) {
                        shoutYaySound.start();
                    }
                }
                break;
            case 11:
                shipOneMastsCounterFirst++;
                if (shipOneMastsCounterFirst == 1) {
                    updateBattleField(numberOfMasts, shipNumber);
                    if(soundOn&&sound) {
                        shoutYaySound.start();
                    }
                }
                break;
            case 12:
                shipOneMastsCounterSecond++;
                if (shipOneMastsCounterSecond == 1) {
                    updateBattleField(numberOfMasts, shipNumber);
                    if(soundOn&&sound) {
                        shoutYaySound.start();
                    }
                }
                break;
            case 13:
                shipOneMastsCounterThird++;
                if (shipOneMastsCounterThird == 1) {
                    updateBattleField(numberOfMasts, shipNumber);
                    if(soundOn&&sound) {
                        shoutYaySound.start();
                    }
                }
                break;
            case 14:
                shipOneMastsCounterFourth++;
                if (shipOneMastsCounterFourth == 1) {
                    updateBattleField(numberOfMasts, shipNumber);
                    if(soundOn&&sound) {
                        shoutYaySound.start();
                    }
                }
                break;
            default:
        }


    }

    private void updateBattleField(int numberOfMasts, int shipNumber) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (battleFieldOpponentActivityRandomGame.getBattleField(i, j).getShipNumber() == shipNumber &&
                        battleFieldOpponentActivityRandomGame.getBattleField(i, j).getNumberOfMasts() == numberOfMasts) {
                    battleFieldOpponent[i][j] = SHIP_BROWN;
                }
            }
        }
    }

    private boolean myWin() {
        return counterSum() == 20;
    }

    private int counterSum() {
        return shipFourMastsCounter +
                shipThreeMastsCounterFirst +
                shipThreeMastsCounterSecond +
                shipTwoMastsCounterFirst +
                shipTwoMastsCounterSecond +
                shipTwoMastsCounterThird +
                shipOneMastsCounterFirst +
                shipOneMastsCounterSecond +
                shipOneMastsCounterThird +
                shipOneMastsCounterFourth;
    }

    public void surrenderSinglePlayer(View view) {
        if (alertDialogFlag) {
            // do nothing
        } else {
            if (loggedIn) {
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);
                        int minusPoints;
                        if (level == 0) {
                            minusPoints = 1;
                        } else if (level == 2) {
                            minusPoints = 10;
                        } else if (level == 3) {
                            minusPoints = 100;
                        } else
                            minusPoints = 0;
                        alertDialogFlag = true;
                        android.app.AlertDialog.Builder mBuilder = new android.app.AlertDialog.Builder(GameBattle.this);
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
                        message.setText("Do you want to surrender game?" + "\n" + "You will lose " + minusPoints + " points");
                        negativeButton.setText("NO");
                        negativeButton.setOnClickListener(v12 -> {
                            alertDialogFlag = false;
                            dialog.dismiss();
                            mHandler.postDelayed(game, deelay);
                        });
                        positiveButton.setText("YES");
                        positiveButton.setOnClickListener(v1 -> {
                            stopAllSounds();
                            dialog.dismiss();
                            int myScore = user.getScore();
                            myScore = myScore - minusPoints;
                            user.setScore(myScore);
                            user.setSinglePlayerMatch(new SinglePlayerMatch());
                            databaseReference.setValue(user);
                            finish();
                        });
                        dialog.show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });

            } else {
                alertDialogFlag = true;
                android.app.AlertDialog.Builder mBuilder = new android.app.AlertDialog.Builder(GameBattle.this);
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
                message.setText("Do you want to surrender game?");
                negativeButton.setText("NO");
                negativeButton.setOnClickListener(v12 -> {
                    alertDialogFlag = false;
                    dialog.dismiss();
                    mHandler.postDelayed(game, deelay);
                });
                positiveButton.setText("YES");
                positiveButton.setOnClickListener(v1 -> {
                    stopAllSounds();
                    dialog.dismiss();
                    finish();
                });
                dialog.show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        leaveGame();
    }

    private void leaveGame() {
        if(alertDialogFlag) {
        // do nothing
        }else{
        if (loggedIn) {
            singlePlayerMatch.setGame(true);
            singlePlayerMatch.setMyTurn(myTurn);
            singlePlayerMatch.setDifficulty(level);
            singlePlayerMatch.setBattleFieldListMyFromArray(battleFieldMeActivityRandomGame);
            singlePlayerMatch.setBattleFieldListOpponentFromArray(battleFieldOpponentActivityRandomGame);
            singlePlayerMatch.setPositionI(positionI);
            singlePlayerMatch.setPositionJ(positionJ);
            singlePlayerMatch.setNewShoot(newShoot);
            singlePlayerMatch.setDirection(direction);
            singlePlayerMatch.setX(x);
            singlePlayerMatch.setY(y);
        }
        alertDialogFlag = true;
        android.app.AlertDialog.Builder mBuilder = new android.app.AlertDialog.Builder(GameBattle.this);
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
        message.setText("Do you want to go back to main menu?");
        negativeButton.setText("NO");
        negativeButton.setOnClickListener(v12 -> {
            alertDialogFlag = false;
            dialog.dismiss();
            mHandler.postDelayed(game, deelay);
        });
        positiveButton.setText("YES");
        positiveButton.setOnClickListener(v1 -> {
            stopAllSounds();
            dialog.dismiss();
            if (loggedIn) {
                databaseReference.child("singlePlayerMatch").setValue(singlePlayerMatch);
            }
            finish();
        });
        dialog.show();
    }
}
    public void leaveSingleGameOnClick(View view) {
        leaveGame();
    }

    public void soundSinglePlayer(View view) {
        if(alertDialogFlag){
            // do nothong
        }else {
            soundOn = !soundOn;
            SharedPreferences sp = getSharedPreferences("Sound", Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean("sound", soundOn);
            editor.commit();
            updateSoundButton();
        }
    }

    private void updateSoundButton(){
        if(soundOn){
            soundBtn.setImageResource(R.color.transparent);
        }else{
            soundBtn.setImageResource(R.drawable.disable);
            stopAllSounds();
        }
    }
}
