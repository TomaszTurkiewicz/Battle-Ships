package com.example.ships;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Shader;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.ships.classes.GameDifficulty;
import com.example.ships.classes.TileDrawable;


public class ChooseGameLevel extends AppCompatActivity {
    private ConstraintLayout constraintLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_game_level);
        constraintLayout=findViewById(R.id.chooseGameLevelActivityLayout);

        SharedPreferences sp = getSharedPreferences("VALUES", Activity.MODE_PRIVATE);
        int square = sp.getInt("square",-1);


        constraintLayout.setBackground(new TileDrawable(getDrawable(R.drawable.background_x), Shader.TileMode.REPEAT,square));



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
