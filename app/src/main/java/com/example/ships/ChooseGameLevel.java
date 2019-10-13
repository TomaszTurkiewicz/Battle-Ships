package com.example.ships;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;


public class ChooseGameLevel extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_game_level);
    }

    public void tooEasyGame(View view) {
        GameDifficulty.getInstance().setLevel(0);
        Intent intent = new Intent(getApplicationContext(), GameBattle.class);
        startActivity(intent);
        finish();
    }


    public void normalGame(View view){
        GameDifficulty.getInstance().setLevel(2);
     Intent intent = new Intent(getApplicationContext(), GameBattle.class);
     startActivity(intent);
     finish();
}

    public void expertGame(View view) {
        GameDifficulty.getInstance().setLevel(3);
        Intent intent = new Intent(getApplicationContext(), GameBattle.class);
        startActivity(intent);
        finish();
    }
}
