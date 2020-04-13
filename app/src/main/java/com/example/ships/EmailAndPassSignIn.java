package com.example.ships;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Shader;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.example.ships.classes.FightIndex;
import com.example.ships.classes.SinglePlayerMatch;
import com.example.ships.classes.TileDrawable;
import com.example.ships.classes.User;
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
    private Button signup;
    private ConstraintLayout mainLayout;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_email_and_pass_sign_in);
        mainLayout = findViewById(R.id.constraintLayoutEmailAndPasswordSignInActivity);
        username = findViewById(R.id.Username);
        email = findViewById(R.id.EmailSignIn);
        emailCofirm = findViewById(R.id.EmailSignInConfirm);
        password = findViewById(R.id.passwordSignIn);
        passwordConfirm = findViewById(R.id.passwordSignInConfirm);
        signup = findViewById(R.id.SignInBtnSignIn);

        SharedPreferences sp = getSharedPreferences("VALUES", Activity.MODE_PRIVATE);
        int square = sp.getInt("square",-1);
        int screenHeight = sp.getInt("width",-1);
        int screenWidth = sp.getInt("height",-1);
        int screenHeightOffSet = sp.getInt("widthOffSet",-1);
        int screenWidthOffSet = sp.getInt("heightOffSet",-1);
        int width = screenWidth-screenWidthOffSet-4*square;
        int marginLeft = 2*square;
        int textSize = square*9/10;
        int signInTopMargin = screenHeight-screenHeightOffSet-5*square;

        mainLayout.setBackground(new TileDrawable(getDrawable(R.drawable.background_x), Shader.TileMode.REPEAT,square));

        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(width,3*square);
        ConstraintLayout.LayoutParams params1 = new ConstraintLayout.LayoutParams(width,3*square);
        ConstraintLayout.LayoutParams params2 = new ConstraintLayout.LayoutParams(width,3*square);
        ConstraintLayout.LayoutParams params3 = new ConstraintLayout.LayoutParams(width,3*square);
        ConstraintLayout.LayoutParams params4 = new ConstraintLayout.LayoutParams(width,3*square);
        ConstraintLayout.LayoutParams params5 = new ConstraintLayout.LayoutParams(width,3*square);

        username.setLayoutParams(params);
        username.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);

        email.setLayoutParams(params1);
        email.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);

        emailCofirm.setLayoutParams(params2);
        emailCofirm.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);

        password.setLayoutParams(params3);
        password.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);

        passwordConfirm.setLayoutParams(params4);
        passwordConfirm.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);

        signup.setLayoutParams(params5);
        signup.setTextSize(TypedValue.COMPLEX_UNIT_PX,square);

        ConstraintSet set = new ConstraintSet();
        set.clone(mainLayout);

        set.connect(username.getId(),ConstraintSet.TOP,mainLayout.getId(),ConstraintSet.TOP,2*square);
        set.connect(username.getId(),ConstraintSet.LEFT,mainLayout.getId(),ConstraintSet.LEFT,marginLeft);

        set.connect(email.getId(),ConstraintSet.TOP,username.getId(),ConstraintSet.BOTTOM,2*square);
        set.connect(email.getId(),ConstraintSet.LEFT,mainLayout.getId(),ConstraintSet.LEFT,marginLeft);

        set.connect(emailCofirm.getId(),ConstraintSet.TOP,email.getId(),ConstraintSet.BOTTOM,2*square);
        set.connect(emailCofirm.getId(),ConstraintSet.LEFT,mainLayout.getId(),ConstraintSet.LEFT,marginLeft);

        set.connect(password.getId(),ConstraintSet.TOP,emailCofirm.getId(),ConstraintSet.BOTTOM,2*square);
        set.connect(password.getId(),ConstraintSet.LEFT,mainLayout.getId(),ConstraintSet.LEFT,marginLeft);

        set.connect(passwordConfirm.getId(),ConstraintSet.TOP,password.getId(),ConstraintSet.BOTTOM,2*square);
        set.connect(passwordConfirm.getId(),ConstraintSet.LEFT,mainLayout.getId(),ConstraintSet.LEFT,marginLeft);

        set.connect(signup.getId(),ConstraintSet.TOP,mainLayout.getId(),ConstraintSet.TOP,signInTopMargin);
        set.connect(signup.getId(),ConstraintSet.LEFT,mainLayout.getId(),ConstraintSet.LEFT,marginLeft);

        set.applyTo(mainLayout);


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


            signup.setOnClickListener(view -> startRegistering());



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
                        .addOnCompleteListener(task -> {

                            final String userID = firebaseAuth.getCurrentUser().getUid();
                            if (task.isSuccessful()) {

                                firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {
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
                                        Toast.makeText(EmailAndPassSignIn.this, task1.getException().getMessage(),
                                                Toast.LENGTH_LONG).show();
                                        progressDialog.dismiss();
                                    }
                                });
                            } else {
                                Toast.makeText(EmailAndPassSignIn.this, task.getException().getMessage(),
                                        Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
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
                setValue(username_val, email_address,userId);
                databaseReferenceUser.child(userId).setValue(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void setValue(String username_val, String email, String userID){
        user.setId(userID);
        user.setName(username_val);
        user.setEmail(email);
        user.setNoOfGames(0);
        user.setScore(0);
        user.setIndex(new FightIndex());
        user.setSinglePlayerMatch(new SinglePlayerMatch());
    }

}