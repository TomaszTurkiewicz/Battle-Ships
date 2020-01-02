package com.example.ships;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        final EditText emailEditText = findViewById(R.id.emailForgotPassword);
        Button resetPassword = findViewById(R.id.forgotPasswordButton);
        firebaseAuth = FirebaseAuth.getInstance();


        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.sendPasswordResetEmail(emailEditText.getText().toString().trim())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                              if(task.isSuccessful()){
                                  Toast.makeText(ForgotPassword.this,
                                          "Password send to your email", Toast.LENGTH_LONG).show();
                              }
                              else{
                                  Toast.makeText(ForgotPassword.this,
                                          task.getException().getMessage(), Toast.LENGTH_LONG).show();
                              }
                            }
                        });
            }
        });


    }
}
