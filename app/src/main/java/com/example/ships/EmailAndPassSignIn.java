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

    private EditText username;
    private EditText email;
    private EditText emailCofirm;
    private EditText password;
    private EditText passwordConfirm;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReferenceUser;
    private User user;
    private int numberOfUsers;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_and_pass_sign_in);

        username = findViewById(R.id.Username);
        email = findViewById(R.id.EmailSignIn);
        emailCofirm = findViewById(R.id.EmailSignInConfirm);
        password = findViewById(R.id.passwordSignIn);
        passwordConfirm = findViewById(R.id.passwordSignInConfirm);
        Button signup = findViewById(R.id.SignInBtnSignIn);
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReferenceUser =firebaseDatabase.getReference("User");


        user = new User();
        if (firebaseAuth.getCurrentUser() != null) {
            if(firebaseAuth.getCurrentUser().isEmailVerified()){
                startActivity(new Intent(EmailAndPassSignIn.this, MainActivity.class));
                finish();
            }

        }


            signup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startRegistering();

            }
        });



    }


    private void startRegistering() {



        final String email_val = email.getText().toString().trim();
        String password_val = password.getText().toString().trim();
        final String username_val = username.getText().toString().trim();
        String emailConfirm_val = emailCofirm.getText().toString().trim();
        String passwordConfirm_val = passwordConfirm.getText().toString().trim();
        boolean emailsEqual;
        boolean passwordEqual;

        if(!TextUtils.isEmpty(email_val)&&
                !TextUtils.isEmpty(emailConfirm_val)&&
                !TextUtils.isEmpty(password_val) &&
                !TextUtils.isEmpty(passwordConfirm_val) &&
                !TextUtils.isEmpty(username_val)){

            emailsEqual=email_val.equals(emailConfirm_val);
            passwordEqual=password_val.equals(passwordConfirm_val);

            if(emailsEqual&&passwordEqual) {

                progressDialog.setMessage("Registering ...");
                progressDialog.show();

                firebaseAuth.createUserWithEmailAndPassword(email_val, password_val)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                final String userID = firebaseAuth.getCurrentUser().getUid();
                                if (task.isSuccessful()) {

                                    firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                createUserInDatabase(username_val, email_val, userID);
                                                Toast.makeText(EmailAndPassSignIn.this, "Registered successfully. Please check your email for verification",
                                                        Toast.LENGTH_LONG).show();


                                                email.setText("");
                                                password.setText("");
                                                progressDialog.dismiss();
                                                Intent intent = new Intent(getApplicationContext(), EmailAndPassLogIn.class);
                                                startActivity(intent);
                                                finish();

                                            } else {
                                                Toast.makeText(EmailAndPassSignIn.this, task.getException().getMessage(),
                                                        Toast.LENGTH_LONG).show();
                                                progressDialog.dismiss();
                                            }
                                        }
                                    });
                                } else {
                                    Toast.makeText(EmailAndPassSignIn.this, task.getException().getMessage(),
                                            Toast.LENGTH_LONG).show();
                                    progressDialog.dismiss();
                                }
                            }
                        });
            }
            else{
                if(!emailsEqual){
                    Toast.makeText(EmailAndPassSignIn.this, "Check Emails!!!",
                            Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(EmailAndPassSignIn.this, "Check Passwords!!!",
                            Toast.LENGTH_LONG).show();
                }
            }
        }else{
            Toast.makeText(EmailAndPassSignIn.this,"Registered unsuccessful: Fields empty",
                    Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
        }
    }

    private void createUserInDatabase(final String username_val, final String email_address, final String userId) {



        databaseReferenceUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                numberOfUsers= (int) dataSnapshot.getChildrenCount();
                setValue(username_val, email_address, numberOfUsers+1,userId);
                databaseReferenceUser.child(userId).setValue(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void setValue(String username_val, String email, int position, String userID){
        user.setId(userID);
        user.setName(username_val);
        user.setEmail(email);
        user.setNoOfGames(0);
        user.setScore(0);
        user.setPosition(position);
    }

}