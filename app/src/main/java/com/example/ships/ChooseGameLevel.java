package com.example.ships;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Shader;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.example.ships.classes.GameDifficulty;
import com.example.ships.classes.TileDrawable;
import com.google.firebase.auth.FirebaseAuth;


public class ChooseGameLevel extends AppCompatActivity {
    private ConstraintLayout constraintLayout;
    private Button easyRandom,normalRandom, expertRandom, easyNotRandom, normalNotRandom, expertNotRandom;
    private TextView breakLine, randomGame, notRandomGame;
    private ImageButton leave;
    private TextView randomOnePoint, randomTenPoints, randomHundredPoints,notRandomOnePoint, notRandomTenPoints, notRandomHundredPoints;
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
        setContentView(R.layout.activity_choose_game_level);
        constraintLayout=findViewById(R.id.chooseGameLevelActivityLayout);
        easyRandom=findViewById(R.id.tooEasyRandom);
        normalRandom=findViewById(R.id.normalRandom);
        expertRandom=findViewById(R.id.expertRandom);
        easyNotRandom=findViewById(R.id.tooEasyNotRandom);
        normalNotRandom=findViewById(R.id.normalNotRandom);
        expertNotRandom=findViewById(R.id.expertNotRandom);
        breakLine=findViewById(R.id.breakLine);
        randomGame=findViewById(R.id.randomGameTextView);
        notRandomGame=findViewById(R.id.notRandomGameTextView);
        leave = findViewById(R.id.leaveChoseGameLevelActivity);
        leave.setBackgroundResource(R.drawable.back);
        randomOnePoint=findViewById(R.id.random_one_point);
        randomTenPoints=findViewById(R.id.random_ten_points);
        randomHundredPoints=findViewById(R.id.random_hundred_points);
        notRandomOnePoint=findViewById(R.id.not_random_one_point);
        notRandomTenPoints=findViewById(R.id.not_random_ten_point);
        notRandomHundredPoints=findViewById(R.id.not_random_hundred_point);

        SharedPreferences sp = getSharedPreferences("VALUES", Activity.MODE_PRIVATE);
        int square = sp.getInt("square",-1);
        int width = sp.getInt("width",-1);
        int height = sp.getInt("height",-1);
        int widthOffSet = sp.getInt("widthOffSet",-1);
        int heightOffSet = sp.getInt("heightOffSet",-1);
        int horizontalMiddle;
        int marginStart = 4*square;
        int marginTopUpper;
        int marginTopLower;
        boolean evenHorizontal = checkEven(width,widthOffSet,square);
        int marginEnd;
        if(evenHorizontal){
            marginEnd = width-widthOffSet-8*square;
            horizontalMiddle=(width-widthOffSet)/2;
        }else{
            marginEnd = width-widthOffSet-11*square;
            horizontalMiddle = (width-widthOffSet-square)/2;
        }
        int marginMiddle = (marginStart+marginEnd)/2;
        int lineWidth = square/10;

        int middleHeight;
        boolean evenVertical = checkEven(height,heightOffSet,square);
        if(evenVertical){
            middleHeight=(height-heightOffSet)/2;
        }else{
            middleHeight=(height-heightOffSet-square)/2;
        }
        marginTopUpper=5*square;
        marginTopLower=middleHeight+marginTopUpper;

        float smallText = square*8/10;


        constraintLayout.setBackground(new TileDrawable(getDrawable(R.drawable.background_x), Shader.TileMode.REPEAT,square));

        ConstraintSet set = new ConstraintSet();
        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(6*square,2*square);
        ConstraintLayout.LayoutParams params1 = new ConstraintLayout.LayoutParams(6*square,2*square);
        ConstraintLayout.LayoutParams params2 = new ConstraintLayout.LayoutParams(6*square,2*square);
        ConstraintLayout.LayoutParams params4 = new ConstraintLayout.LayoutParams(6*square,2*square);
        ConstraintLayout.LayoutParams params5 = new ConstraintLayout.LayoutParams(6*square,2*square);
        ConstraintLayout.LayoutParams params6 = new ConstraintLayout.LayoutParams(6*square,2*square);
        ConstraintLayout.LayoutParams params10 = new ConstraintLayout.LayoutParams(6*square,square);
        ConstraintLayout.LayoutParams params11 = new ConstraintLayout.LayoutParams(6*square,square);
        ConstraintLayout.LayoutParams params12 = new ConstraintLayout.LayoutParams(6*square,square);
        ConstraintLayout.LayoutParams params13 = new ConstraintLayout.LayoutParams(6*square,square);
        ConstraintLayout.LayoutParams params14 = new ConstraintLayout.LayoutParams(6*square,square);
        ConstraintLayout.LayoutParams params15 = new ConstraintLayout.LayoutParams(6*square,square);
        ConstraintLayout.LayoutParams params3 = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,lineWidth);
        ConstraintLayout.LayoutParams params7 = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ConstraintLayout.LayoutParams params8 = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ConstraintLayout.LayoutParams params9 = new ConstraintLayout.LayoutParams(2*square,2*square);

        easyRandom.setLayoutParams(params);
        expertRandom.setLayoutParams(params1);
        normalRandom.setLayoutParams(params2);
        breakLine.setLayoutParams(params3);
        easyNotRandom.setLayoutParams(params4);
        normalNotRandom.setLayoutParams(params5);
        expertNotRandom.setLayoutParams(params6);
        randomGame.setLayoutParams(params7);
        notRandomGame.setLayoutParams(params8);
        leave.setLayoutParams(params9);
        easyRandom.setTextSize(TypedValue.COMPLEX_UNIT_PX,square);
        normalRandom.setTextSize(TypedValue.COMPLEX_UNIT_PX,square);
        expertRandom.setTextSize(TypedValue.COMPLEX_UNIT_PX,square);
        easyNotRandom.setTextSize(TypedValue.COMPLEX_UNIT_PX,square);
        normalNotRandom.setTextSize(TypedValue.COMPLEX_UNIT_PX,square);
        expertNotRandom.setTextSize(TypedValue.COMPLEX_UNIT_PX,square);
        notRandomGame.setTextSize(TypedValue.COMPLEX_UNIT_PX,2*square);
        randomGame.setTextSize(TypedValue.COMPLEX_UNIT_PX,2*square);
        randomOnePoint.setLayoutParams(params10);
        randomOnePoint.setTextSize(TypedValue.COMPLEX_UNIT_PX,smallText);
        randomTenPoints.setLayoutParams(params11);
        randomTenPoints.setTextSize(TypedValue.COMPLEX_UNIT_PX,smallText);
        randomHundredPoints.setLayoutParams(params12);
        randomHundredPoints.setTextSize(TypedValue.COMPLEX_UNIT_PX,smallText);
        notRandomOnePoint.setLayoutParams(params13);
        notRandomOnePoint.setTextSize(TypedValue.COMPLEX_UNIT_PX,smallText);
        notRandomTenPoints.setLayoutParams(params14);
        notRandomTenPoints.setTextSize(TypedValue.COMPLEX_UNIT_PX,smallText);
        notRandomHundredPoints.setLayoutParams(params15);
        notRandomHundredPoints.setTextSize(TypedValue.COMPLEX_UNIT_PX,smallText);
        set.clone(constraintLayout);

        set.connect(easyRandom.getId(),ConstraintSet.TOP,constraintLayout.getId(),ConstraintSet.TOP,marginTopUpper);
        set.connect(easyRandom.getId(),ConstraintSet.LEFT,constraintLayout.getId(),ConstraintSet.LEFT,marginStart);

        set.connect(randomOnePoint.getId(),ConstraintSet.TOP,easyRandom.getId(),ConstraintSet.BOTTOM,0);
        set.connect(randomOnePoint.getId(),ConstraintSet.LEFT,easyRandom.getId(),ConstraintSet.LEFT,0);

        set.connect(expertRandom.getId(),ConstraintSet.TOP,constraintLayout.getId(),ConstraintSet.TOP,marginTopUpper);
        set.connect(expertRandom.getId(),ConstraintSet.LEFT,constraintLayout.getId(),ConstraintSet.LEFT,marginEnd);

        set.connect(randomHundredPoints.getId(),ConstraintSet.TOP,expertRandom.getId(),ConstraintSet.BOTTOM,0);
        set.connect(randomHundredPoints.getId(),ConstraintSet.LEFT,expertRandom.getId(),ConstraintSet.LEFT,0);

        set.connect(normalRandom.getId(),ConstraintSet.TOP,constraintLayout.getId(),ConstraintSet.TOP,marginTopUpper);
        set.connect(normalRandom.getId(),ConstraintSet.LEFT,constraintLayout.getId(),ConstraintSet.LEFT,marginMiddle);

        set.connect(randomTenPoints.getId(),ConstraintSet.TOP,normalRandom.getId(),ConstraintSet.BOTTOM,0);
        set.connect(randomTenPoints.getId(),ConstraintSet.LEFT,normalRandom.getId(),ConstraintSet.LEFT,0);

        set.connect(breakLine.getId(),ConstraintSet.TOP,constraintLayout.getId(),ConstraintSet.TOP,middleHeight-lineWidth/2);
        set.connect(breakLine.getId(),ConstraintSet.LEFT,constraintLayout.getId(),ConstraintSet.LEFT,0);

        set.connect(easyNotRandom.getId(),ConstraintSet.TOP,constraintLayout.getId(),ConstraintSet.TOP,marginTopLower);
        set.connect(easyNotRandom.getId(),ConstraintSet.LEFT,constraintLayout.getId(),ConstraintSet.LEFT,marginStart);

        set.connect(notRandomOnePoint.getId(),ConstraintSet.TOP,easyNotRandom.getId(),ConstraintSet.BOTTOM,0);
        set.connect(notRandomOnePoint.getId(),ConstraintSet.LEFT,easyNotRandom.getId(),ConstraintSet.LEFT,0);

        set.connect(normalNotRandom.getId(),ConstraintSet.TOP,constraintLayout.getId(),ConstraintSet.TOP,marginTopLower);
        set.connect(normalNotRandom.getId(),ConstraintSet.LEFT,constraintLayout.getId(),ConstraintSet.LEFT,marginMiddle);

        set.connect(notRandomTenPoints.getId(),ConstraintSet.TOP,normalNotRandom.getId(),ConstraintSet.BOTTOM,0);
        set.connect(notRandomTenPoints.getId(),ConstraintSet.LEFT,normalNotRandom.getId(),ConstraintSet.LEFT,0);

        set.connect(expertNotRandom.getId(),ConstraintSet.TOP,constraintLayout.getId(),ConstraintSet.TOP,marginTopLower);
        set.connect(expertNotRandom.getId(),ConstraintSet.LEFT,constraintLayout.getId(),ConstraintSet.LEFT,marginEnd);

        set.connect(notRandomHundredPoints.getId(),ConstraintSet.TOP,expertNotRandom.getId(),ConstraintSet.BOTTOM,0);
        set.connect(notRandomHundredPoints.getId(),ConstraintSet.LEFT,expertNotRandom.getId(),ConstraintSet.LEFT,0);

        set.connect(randomGame.getId(),ConstraintSet.TOP,constraintLayout.getId(),ConstraintSet.TOP,2*square);
        set.connect(randomGame.getId(),ConstraintSet.LEFT,constraintLayout.getId(),ConstraintSet.LEFT,horizontalMiddle-15*square);

        set.connect(notRandomGame.getId(),ConstraintSet.TOP,constraintLayout.getId(),ConstraintSet.TOP,2*square+middleHeight);
        set.connect(notRandomGame.getId(),ConstraintSet.LEFT,constraintLayout.getId(),ConstraintSet.LEFT,horizontalMiddle-16*square);

        set.connect(leave.getId(),ConstraintSet.TOP,constraintLayout.getId(),ConstraintSet.TOP,height-heightOffSet-3*square);
        set.connect(leave.getId(),ConstraintSet.LEFT,constraintLayout.getId(),ConstraintSet.LEFT,square);

        set.applyTo(constraintLayout);

    if(FirebaseAuth.getInstance().getCurrentUser()!=null){
        randomOnePoint.setVisibility(View.VISIBLE);
        randomTenPoints.setVisibility(View.VISIBLE);
        randomHundredPoints.setVisibility(View.VISIBLE);
        notRandomOnePoint.setVisibility(View.VISIBLE);
        notRandomTenPoints.setVisibility(View.VISIBLE);
        notRandomHundredPoints.setVisibility(View.VISIBLE);
    }else{
        randomOnePoint.setVisibility(View.GONE);
        randomTenPoints.setVisibility(View.GONE);
        randomHundredPoints.setVisibility(View.GONE);
        notRandomOnePoint.setVisibility(View.GONE);
        notRandomTenPoints.setVisibility(View.GONE);
        notRandomHundredPoints.setVisibility(View.GONE);
    }



    }

    private boolean checkEven(int full, int offSet, int square) {
        int x = ((full-offSet)/square)%2;
        return x==0;
    }

    public void tooEasyGameRandom(View view) {
        GameDifficulty.getInstance().setLevel(0);
        GameDifficulty.getInstance().setRandom(true);
        Intent intent = new Intent(getApplicationContext(), GameBattle.class);
        startActivity(intent);
        finish();
    }

    public void normalGameRandom(View view) {
        GameDifficulty.getInstance().setLevel(2);
        GameDifficulty.getInstance().setRandom(true);
        Intent intent = new Intent(getApplicationContext(), GameBattle.class);
        startActivity(intent);
        finish();
    }

    public void expertGameRandom(View view) {
        GameDifficulty.getInstance().setLevel(3);
        GameDifficulty.getInstance().setRandom(true);
        Intent intent = new Intent(getApplicationContext(), GameBattle.class);
        startActivity(intent);
        finish();
    }

    public void tooEasyGameNotRandom(View view) {
        GameDifficulty.getInstance().setLevel(0);
        GameDifficulty.getInstance().setRandom(false);
        GameDifficulty.getInstance().setMultiplayerMode(false);
        Intent intent = new Intent(getApplicationContext(),CreateBattleField.class);
        startActivity(intent);
        finish();
    }

    public void normalGameNotRandom(View view) {
        GameDifficulty.getInstance().setLevel(2);
        GameDifficulty.getInstance().setRandom(false);
        GameDifficulty.getInstance().setMultiplayerMode(false);
        Intent intent = new Intent(getApplicationContext(),CreateBattleField.class);
        startActivity(intent);
        finish();
    }

    public void expertGameNotRandom(View view) {
        GameDifficulty.getInstance().setLevel(3);
        GameDifficulty.getInstance().setRandom(false);
        GameDifficulty.getInstance().setMultiplayerMode(false);
        Intent intent = new Intent(getApplicationContext(),CreateBattleField.class);
        startActivity(intent);
        finish();
    }

    public void leaveChoseGameLevelActivity(View view) {
        finish();
    }
}
