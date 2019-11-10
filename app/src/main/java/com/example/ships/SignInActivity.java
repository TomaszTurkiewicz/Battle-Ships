package com.example.ships;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class SignInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
    }

    public void onClickEmailAndPasswordLogin(View view) {
        Intent intent = new Intent(getApplicationContext(),EmailAndPasswordSignIn.class);
        startActivity(intent);
    }
}
