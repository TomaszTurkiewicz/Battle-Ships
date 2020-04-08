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
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.example.ships.classes.GameDifficulty;
import com.example.ships.classes.TileDrawable;


public class ChooseGameLevel extends AppCompatActivity {
    private ConstraintLayout constraintLayout;
    private Button easyRandom,normalRandom, expertRandom, easyNotRandom, normalNotRandom, expertNotRandom;
    private TextView breakLine, randomGame, notRandomGame;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
            marginEnd = width-widthOffSet-10*square;
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


        constraintLayout.setBackground(new TileDrawable(getDrawable(R.drawable.background_x), Shader.TileMode.REPEAT,square));

        ConstraintSet set = new ConstraintSet();
        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(6*square,2*square);
        ConstraintLayout.LayoutParams params1 = new ConstraintLayout.LayoutParams(6*square,2*square);
        ConstraintLayout.LayoutParams params2 = new ConstraintLayout.LayoutParams(6*square,2*square);
        ConstraintLayout.LayoutParams params4 = new ConstraintLayout.LayoutParams(6*square,2*square);
        ConstraintLayout.LayoutParams params5 = new ConstraintLayout.LayoutParams(6*square,2*square);
        ConstraintLayout.LayoutParams params6 = new ConstraintLayout.LayoutParams(6*square,2*square);
        ConstraintLayout.LayoutParams params3 = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,lineWidth);
        ConstraintLayout.LayoutParams params7 = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ConstraintLayout.LayoutParams params8 = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        easyRandom.setLayoutParams(params);
        expertRandom.setLayoutParams(params1);
        normalRandom.setLayoutParams(params2);
        breakLine.setLayoutParams(params3);
        easyNotRandom.setLayoutParams(params4);
        normalNotRandom.setLayoutParams(params5);
        expertNotRandom.setLayoutParams(params6);
        randomGame.setLayoutParams(params7);
        notRandomGame.setLayoutParams(params8);
        notRandomGame.setTextSize(TypedValue.COMPLEX_UNIT_PX,2*square);
        randomGame.setTextSize(TypedValue.COMPLEX_UNIT_PX,2*square);

        set.clone(constraintLayout);

        set.connect(easyRandom.getId(),ConstraintSet.TOP,constraintLayout.getId(),ConstraintSet.TOP,marginTopUpper);
        set.connect(easyRandom.getId(),ConstraintSet.LEFT,constraintLayout.getId(),ConstraintSet.LEFT,marginStart);

        set.connect(expertRandom.getId(),ConstraintSet.TOP,constraintLayout.getId(),ConstraintSet.TOP,marginTopUpper);
        set.connect(expertRandom.getId(),ConstraintSet.LEFT,constraintLayout.getId(),ConstraintSet.LEFT,marginEnd);

        set.connect(normalRandom.getId(),ConstraintSet.TOP,constraintLayout.getId(),ConstraintSet.TOP,marginTopUpper);
        set.connect(normalRandom.getId(),ConstraintSet.LEFT,constraintLayout.getId(),ConstraintSet.LEFT,marginMiddle);

        set.connect(breakLine.getId(),ConstraintSet.TOP,constraintLayout.getId(),ConstraintSet.TOP,middleHeight-lineWidth/2);
        set.connect(breakLine.getId(),ConstraintSet.LEFT,constraintLayout.getId(),ConstraintSet.LEFT,0);

        set.connect(easyNotRandom.getId(),ConstraintSet.TOP,constraintLayout.getId(),ConstraintSet.TOP,marginTopLower);
        set.connect(easyNotRandom.getId(),ConstraintSet.LEFT,constraintLayout.getId(),ConstraintSet.LEFT,marginStart);

        set.connect(normalNotRandom.getId(),ConstraintSet.TOP,constraintLayout.getId(),ConstraintSet.TOP,marginTopLower);
        set.connect(normalNotRandom.getId(),ConstraintSet.LEFT,constraintLayout.getId(),ConstraintSet.LEFT,marginMiddle);

        set.connect(expertNotRandom.getId(),ConstraintSet.TOP,constraintLayout.getId(),ConstraintSet.TOP,marginTopLower);
        set.connect(expertNotRandom.getId(),ConstraintSet.LEFT,constraintLayout.getId(),ConstraintSet.LEFT,marginEnd);

        set.connect(randomGame.getId(),ConstraintSet.TOP,constraintLayout.getId(),ConstraintSet.TOP,2*square);
        set.connect(randomGame.getId(),ConstraintSet.LEFT,constraintLayout.getId(),ConstraintSet.LEFT,horizontalMiddle-15*square);

        set.connect(notRandomGame.getId(),ConstraintSet.TOP,constraintLayout.getId(),ConstraintSet.TOP,2*square+middleHeight);
        set.connect(notRandomGame.getId(),ConstraintSet.LEFT,constraintLayout.getId(),ConstraintSet.LEFT,horizontalMiddle-16*square);

        set.applyTo(constraintLayout);
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
}
