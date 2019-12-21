package com.example.ships;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity {
    private Button userLogout;
    private FirebaseAuth firebaseAuth;
    Button deleteUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        userLogout=findViewById(R.id.logout);
        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        deleteUser = (Button)findViewById(R.id.deleteUser);


        if(firebaseUser==null){
            deleteUser.setVisibility(View.GONE);
        }

        deleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(SignInActivity.this,"Account Deleted",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        userLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
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
//TODO wymyślić jak zrobić grę na dwie osoby
