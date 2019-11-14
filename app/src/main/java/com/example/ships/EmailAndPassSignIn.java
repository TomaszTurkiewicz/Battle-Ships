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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EmailAndPassSignIn extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button signup;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_and_pass_sign_in);

        email = findViewById(R.id.EmailSignIn);
        password = findViewById(R.id.passwordSignIn);
        signup = findViewById(R.id.SignInBtnSignIn);
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("User");
        user = new User();

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
                                            createUserInDatabase();//TODO sprawdzić dlaczego nie działa!!!
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

    private void createUserInDatabase() {

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                setValue();
                databaseReference.child("Pierwszy").setValue(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void setValue(){
        user.setName("Pierwszy");
        user.setEmail(email.getText().toString().trim());
        user.setNoOfGames(0);
        user.setScore(0);
    }

}