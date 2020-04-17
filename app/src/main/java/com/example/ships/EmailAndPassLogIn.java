package com.example.ships;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Shader;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.example.ships.classes.TileDrawable;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;

public class EmailAndPassLogIn extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button loginBtn;
    private Button resendEmail;
    private Button forgotPassword;
    private Button signIn;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private String password_val;
    private String email_val;
    private ConstraintLayout mainLayout;
    private ImageButton leave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        getWindow().getDecorView().setSystemUiVisibility(flags);
        final View decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener(){

            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if((visibility&View.SYSTEM_UI_FLAG_FULLSCREEN)==0){
                    decorView.setSystemUiVisibility(flags);
                }
            }
        });
        setContentView(R.layout.activity_email_and_pass_log_in);
        mainLayout = findViewById(R.id.constraintLayoutEmailAndPassLogIn);
        email = findViewById(R.id.emailEditTextLogIn);
        password = findViewById(R.id.passwordEditTextLogIn);
        loginBtn = findViewById(R.id.LogInBtnLogIn);
        resendEmail = findViewById(R.id.resendVerificationEmailBtnLogIn);
        forgotPassword = findViewById(R.id.forgotPasswordBtnLogIn);
        signIn = findViewById(R.id.signInBtnLogIn);
        leave=findViewById(R.id.leaveEmailAndPasswordLogInActivity);
        leave.setBackgroundResource(R.drawable.back);
        SharedPreferences sp = getSharedPreferences("VALUES", Activity.MODE_PRIVATE);
        int square = sp.getInt("square",-1);
        int screenHeight = sp.getInt("width",-1);
        int screenWidth = sp.getInt("height",-1);
        int screenHeightOffSet = sp.getInt("widthOffSet",-1);
        int screenWidthOffSet = sp.getInt("heightOffSet",-1);
        int width = screenWidth-screenWidthOffSet-4*square;
        int marginLeft = 2*square;
        int textSize = square*9/10;
        int signInTopMargin = screenHeight-screenHeightOffSet-7*square;

        mainLayout.setBackground(new TileDrawable(getDrawable(R.drawable.background_x), Shader.TileMode.REPEAT,square));

        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(width,3*square);
        ConstraintLayout.LayoutParams params1 = new ConstraintLayout.LayoutParams(width,3*square);
        ConstraintLayout.LayoutParams params2 = new ConstraintLayout.LayoutParams(width,3*square);
        ConstraintLayout.LayoutParams params3 = new ConstraintLayout.LayoutParams(width,3*square);
        ConstraintLayout.LayoutParams params4 = new ConstraintLayout.LayoutParams(width,3*square);
        ConstraintLayout.LayoutParams params5 = new ConstraintLayout.LayoutParams(width,3*square);
        ConstraintLayout.LayoutParams params6 = new ConstraintLayout.LayoutParams(2*square,2*square);

        email.setLayoutParams(params);
        email.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);

        password.setLayoutParams(params1);
        password.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);

        loginBtn.setLayoutParams(params2);
        loginBtn.setTextSize(TypedValue.COMPLEX_UNIT_PX,square);

        resendEmail.setLayoutParams(params3);
        resendEmail.setTextSize(TypedValue.COMPLEX_UNIT_PX,square);

        forgotPassword.setLayoutParams(params4);
        forgotPassword.setTextSize(TypedValue.COMPLEX_UNIT_PX,square);

        signIn.setLayoutParams(params5);
        signIn.setTextSize(TypedValue.COMPLEX_UNIT_PX,square);

        leave.setLayoutParams(params6);

        ConstraintSet set = new ConstraintSet();
        set.clone(mainLayout);

        set.connect(email.getId(),ConstraintSet.TOP,mainLayout.getId(),ConstraintSet.TOP,2*square);
        set.connect(email.getId(),ConstraintSet.LEFT,mainLayout.getId(),ConstraintSet.LEFT,marginLeft);

        set.connect(password.getId(),ConstraintSet.TOP,email.getId(),ConstraintSet.BOTTOM,2*square);
        set.connect(password.getId(),ConstraintSet.LEFT,mainLayout.getId(),ConstraintSet.LEFT,marginLeft);

        set.connect(loginBtn.getId(),ConstraintSet.TOP,password.getId(),ConstraintSet.BOTTOM,2*square);
        set.connect(loginBtn.getId(),ConstraintSet.LEFT,mainLayout.getId(),ConstraintSet.LEFT,marginLeft);

        set.connect(forgotPassword.getId(),ConstraintSet.TOP,loginBtn.getId(),ConstraintSet.BOTTOM,2*square);
        set.connect(forgotPassword.getId(),ConstraintSet.LEFT,mainLayout.getId(),ConstraintSet.LEFT,marginLeft);

        set.connect(resendEmail.getId(),ConstraintSet.TOP,loginBtn.getId(),ConstraintSet.BOTTOM,2*square);
        set.connect(resendEmail.getId(),ConstraintSet.LEFT,mainLayout.getId(),ConstraintSet.LEFT,marginLeft);

        set.connect(signIn.getId(),ConstraintSet.TOP,mainLayout.getId(),ConstraintSet.TOP,signInTopMargin);
        set.connect(signIn.getId(),ConstraintSet.LEFT,mainLayout.getId(),ConstraintSet.LEFT,marginLeft);

        set.connect(leave.getId(),ConstraintSet.TOP,mainLayout.getId(),ConstraintSet.TOP,screenHeight-screenHeightOffSet-3*square);
        set.connect(leave.getId(),ConstraintSet.LEFT,mainLayout.getId(),ConstraintSet.LEFT,square);

        set.applyTo(mainLayout);

        firebaseAuth = FirebaseAuth.getInstance();


        progressDialog = new ProgressDialog(this);

        resendEmail.setVisibility(View.GONE);

        loginBtn.setOnClickListener(v -> login());





        resendEmail.setOnClickListener(v -> {
            if(firebaseAuth.getCurrentUser()!=null){
                firebaseAuth.getCurrentUser().reload();
                if(!firebaseAuth.getCurrentUser().isEmailVerified()){
                    firebaseAuth.getCurrentUser().sendEmailVerification();
                    Toast.makeText(EmailAndPassLogIn.this,"Email Sent!!!",
                            Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(EmailAndPassLogIn.this,"Your email has been verified! You can log in now",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void login() {

        progressDialog.setMessage("Logging ...");
        progressDialog.show();

        email_val = email.getText().toString().trim();
        password_val = password.getText().toString().trim();

        if (!TextUtils.isEmpty(email_val) && !TextUtils.isEmpty(password_val)){

            firebaseAuth.signInWithEmailAndPassword(email_val, password_val)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            if(firebaseAuth.getCurrentUser().isEmailVerified()){
                                SharedPreferences sp = getSharedPreferences(email_val, Activity.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putString("password",password_val);
                                editor.commit();
                                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                                String userID = firebaseUser.getUid();
                                FirebaseMessaging.getInstance().subscribeToTopic(userID);
                                startActivity(new Intent(EmailAndPassLogIn.this, MainActivity.class));
                                progressDialog.dismiss();
                                finish();
                            }else{

                                forgotPassword.setVisibility(View.GONE);
                                resendEmail.setVisibility(View.VISIBLE);
                                Toast.makeText(EmailAndPassLogIn.this,"Please verify your email address",
                                        Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                            }
                        } else {
                            Toast.makeText(EmailAndPassLogIn.this, task.getException().getMessage(),
                                    Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                    });
        }
        else{
            Toast.makeText(EmailAndPassLogIn.this,"Logging unsuccessful: Fields empty",
                    Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
        }
    }

    public void goToSignIn(View view) {
        startActivity(new Intent(EmailAndPassLogIn.this, EmailAndPassSignIn.class));
        finish();
    }

    public void goToForgotPasswordActivity(View view) {
        startActivity(new Intent(EmailAndPassLogIn.this, ForgotPassword.class));
        finish();

    }

    public void leaveEmailAndPasswordLogInActivityOnClick(View view) {
        goBackToMainMenu();
    }
    @Override
    public void onBackPressed() {
        goBackToMainMenu();
    }

    private void goBackToMainMenu() {
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
    }
}

