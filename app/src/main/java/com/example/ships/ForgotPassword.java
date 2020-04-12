package com.example.ships;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Shader;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.example.ships.classes.TileDrawable;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private EditText emailEditText;
    private Button resetPassword;
    private ConstraintLayout mainLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_forgot_password);
        mainLayout = findViewById(R.id.constraintLayoutForgotPasswordActivity);
        emailEditText = findViewById(R.id.emailForgotPassword);
        resetPassword = findViewById(R.id.forgotPasswordButton);

        SharedPreferences sp = getSharedPreferences("VALUES", Activity.MODE_PRIVATE);
        int square = sp.getInt("square",-1);
        int screenHeight = sp.getInt("width",-1);
        int screenWidth = sp.getInt("height",-1);
        int screenHeightOffSet = sp.getInt("widthOffSet",-1);
        int screenWidthOffSet = sp.getInt("heightOffSet",-1);
        int width = screenWidth-screenWidthOffSet-4*square;
        int marginLeft = 2*square;
        int textSize = square*9/10;

        mainLayout.setBackground(new TileDrawable(getDrawable(R.drawable.background_x), Shader.TileMode.REPEAT,square));

        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(width,3*square);
        ConstraintLayout.LayoutParams params1 = new ConstraintLayout.LayoutParams(width,3*square);
        emailEditText.setLayoutParams(params);
        emailEditText.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);

        resetPassword.setLayoutParams(params1);
        resetPassword.setTextSize(TypedValue.COMPLEX_UNIT_PX,square);

        ConstraintSet set = new ConstraintSet();
        set.clone(mainLayout);

        set.connect(emailEditText.getId(),ConstraintSet.TOP,mainLayout.getId(),ConstraintSet.TOP,3*square);
        set.connect(emailEditText.getId(),ConstraintSet.LEFT,mainLayout.getId(),ConstraintSet.LEFT,marginLeft);

        set.connect(resetPassword.getId(),ConstraintSet.TOP,emailEditText.getId(),ConstraintSet.BOTTOM,3*square);
        set.connect(resetPassword.getId(),ConstraintSet.LEFT,mainLayout.getId(),ConstraintSet.LEFT,marginLeft);

        set.applyTo(mainLayout);


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
// TODO empty email field!!!