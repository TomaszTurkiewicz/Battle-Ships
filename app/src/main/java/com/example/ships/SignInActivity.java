package com.example.ships;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {
    private Button userLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        userLogout=findViewById(R.id.logout);

        userLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
    }

    public void onClickEmailAndPasswordLogin(View view) {
        Intent intent = new Intent(getApplicationContext(),EmailAndPassLogIn.class);
        startActivity(intent);
        finish();
    }
}
//TODO rejestracja z podwójnym mailem i password plus username
//TODO forgot password
//TODO zmienić main activity aby inaczej wyświetlało kto zalogowany i przycisk account
//TODO rejestracje przez Gmaila
//TODO Rejestracja przez Facebook
//TODO usuwanie konta
//TODO animacja
//TODO inaczej zrobić pętlę gry (opóźnienie)
