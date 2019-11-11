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

public class EmailAndPassSignIn extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button signup;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_and_pass_sign_in);

        email = findViewById(R.id.EmailSignIn);
        password = findViewById(R.id.passwordSignIn);
        signup = findViewById(R.id.SignInBtnSignIn);


        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);


        signup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startRegistering();

            }
        });

    }

    private void startRegistering() {

        progressDialog.setMessage("Registering ...");
        progressDialog.show();

        String email_val = email.getText().toString().trim();
        String password_val = password.getText().toString().trim();

        if(!TextUtils.isEmpty(email_val)&&!TextUtils.isEmpty(password_val)){

            firebaseAuth.createUserWithEmailAndPassword(email_val, password_val)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){

                                firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(EmailAndPassSignIn.this,"Registered successfully. Please check your email for verification",
                                                    Toast.LENGTH_LONG).show();
                                            email.setText("");
                                            password.setText("");
                                            progressDialog.dismiss();
                                            Intent intent = new Intent(getApplicationContext(),EmailAndPassLogIn.class);
                                            startActivity(intent);
                                            finish();

                                        }else{
                                            Toast.makeText(EmailAndPassSignIn.this,task.getException().getMessage(),
                                                    Toast.LENGTH_LONG).show();
                                            progressDialog.dismiss();
                                        }

                                    }
                                });



                            }else{
                                Toast.makeText(EmailAndPassSignIn.this,task.getException().getMessage(),
                                        Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                            }

                        }
                    });
        }else{
            Toast.makeText(EmailAndPassSignIn.this,"Registered unsuccessful: Fields empty",
                    Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
        }
    }

}