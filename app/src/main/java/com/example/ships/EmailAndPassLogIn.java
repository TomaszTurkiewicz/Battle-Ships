package com.example.ships;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class EmailAndPassLogIn extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button loginBtn;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_and_pass_log_in);


        email = findViewById(R.id.emailEditTextLogIn);
        password = findViewById(R.id.passwordEditTextLogIn);
        loginBtn = findViewById(R.id.LogInBtnLogIn);

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        loginBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login() {

        progressDialog.setMessage("Logging ...");
        progressDialog.show();

        String email_val = email.getText().toString().trim();
        String password_val = password.getText().toString().trim();

        if (!TextUtils.isEmpty(email_val) && !TextUtils.isEmpty(password_val)){

            firebaseAuth.signInWithEmailAndPassword(email_val, password_val)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                if(firebaseAuth.getCurrentUser().isEmailVerified()){
                                    startActivity(new Intent(EmailAndPassLogIn.this, MainActivity.class));
                                    progressDialog.dismiss();
                                    finish();
                                }else{
                                    Toast.makeText(EmailAndPassLogIn.this,"Please verify your email address",
                                            Toast.LENGTH_LONG).show();
                                    progressDialog.dismiss();
                                }
                            } else {
                                Toast.makeText(EmailAndPassLogIn.this, task.getException().getMessage(),
                                        Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                            }
                        }
                    });
        }
        else{
            Toast.makeText(EmailAndPassLogIn.this,"Registered unsuccessful: Fields empty",
                    Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
        }
    }

    public void goToSignIn(View view) {
        startActivity(new Intent(EmailAndPassLogIn.this, EmailAndPassSignIn.class));
        finish();
    }
}

