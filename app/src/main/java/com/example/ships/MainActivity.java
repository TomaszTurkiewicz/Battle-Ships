package com.example.ships;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public void randomGame(View view) {

        Intent intent = new Intent(getApplicationContext(),ChooseGameLevel.class);
        startActivity(intent);
    }

    public void notRandomGame(View view) {
        Intent intent = new Intent(getApplicationContext(),CreateBattleField.class);
        startActivity(intent);
    }

    public void networkGame(View view) {
    }

    public void bluetooth(View view) {
    }
}
