package com.example.ships;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Shader;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.example.ships.classes.TileDrawable;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

public class SignInActivity extends AppCompatActivity {
    private Button userLogout;
    private FirebaseAuth firebaseAuth;
    private Button deleteUser, loginEmail;
    private String userID;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private boolean loggedIn;
    static final int GOOGLE_SIGN = 123;
    private Button login_google, login_facebook;
    private GoogleSignInClient mGoogleSignInClient;
    private ProgressDialog progressDialog;
    private ConstraintLayout mainLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_in);
        userLogout=findViewById(R.id.logout);
        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser firebaseUser;
        deleteUser = findViewById(R.id.deleteUser);
        login_google = findViewById(R.id.loginGoogle);
        mainLayout = findViewById(R.id.constraintLayoutSignInActivity);
        loginEmail = findViewById(R.id.emailAndPassword);
        login_facebook = findViewById(R.id.loginFacebbok);
        SharedPreferences sp = getSharedPreferences("VALUES", Activity.MODE_PRIVATE);
        int square = sp.getInt("square",-1);
        int screenHeight = sp.getInt("width",-1);
        int screenWidth = sp.getInt("height",-1);
        int screenHeightOffSet = sp.getInt("widthOffSet",-1);
        int screenWidthOffSet = sp.getInt("heightOffSet",-1);
        int width = screenWidth-screenWidthOffSet-4*square;
        int marginLeft = 2*square;



        mainLayout.setBackground(new TileDrawable(getDrawable(R.drawable.background_x), Shader.TileMode.REPEAT,square));

        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(width,3*square);
        ConstraintLayout.LayoutParams params1 = new ConstraintLayout.LayoutParams(width,3*square);
        ConstraintLayout.LayoutParams params2 = new ConstraintLayout.LayoutParams(width,3*square);
        ConstraintLayout.LayoutParams params3 = new ConstraintLayout.LayoutParams(width,3*square);
        ConstraintLayout.LayoutParams params4 = new ConstraintLayout.LayoutParams(width,3*square);
        ConstraintLayout.LayoutParams params5 = new ConstraintLayout.LayoutParams(width,3*square);


        loginEmail.setLayoutParams(params);
        loginEmail.setTextSize(TypedValue.COMPLEX_UNIT_PX,square);

        login_google.setLayoutParams(params1);
        login_google.setTextSize(TypedValue.COMPLEX_UNIT_PX,square);

        userLogout.setLayoutParams(params2);
        userLogout.setTextSize(TypedValue.COMPLEX_UNIT_PX,square);

        login_facebook.setLayoutParams(params3);
        login_facebook.setTextSize(TypedValue.COMPLEX_UNIT_PX,square);

        userLogout.setLayoutParams(params4);
        userLogout.setTextSize(TypedValue.COMPLEX_UNIT_PX,square);

        deleteUser.setLayoutParams(params5);
        deleteUser.setTextSize(TypedValue.COMPLEX_UNIT_PX,square);

        ConstraintSet set = new ConstraintSet();
        set.clone(mainLayout);

        set.connect(loginEmail.getId(),ConstraintSet.TOP,mainLayout.getId(),ConstraintSet.TOP,5*square);
        set.connect(loginEmail.getId(),ConstraintSet.LEFT,mainLayout.getId(),ConstraintSet.LEFT,marginLeft);

        set.connect(login_google.getId(),ConstraintSet.TOP,loginEmail.getId(),ConstraintSet.BOTTOM,5*square);
        set.connect(login_google.getId(),ConstraintSet.LEFT,mainLayout.getId(),ConstraintSet.LEFT,marginLeft);

        set.connect(login_facebook.getId(),ConstraintSet.TOP,login_google.getId(),ConstraintSet.BOTTOM,5*square);
        set.connect(login_facebook.getId(),ConstraintSet.LEFT,mainLayout.getId(),ConstraintSet.LEFT,marginLeft);

        set.connect(userLogout.getId(),ConstraintSet.TOP,mainLayout.getId(),ConstraintSet.TOP,5*square);
        set.connect(userLogout.getId(),ConstraintSet.LEFT,mainLayout.getId(),ConstraintSet.LEFT,marginLeft);

        set.connect(deleteUser.getId(),ConstraintSet.TOP,userLogout.getId(),ConstraintSet.BOTTOM,10*square);
        set.connect(deleteUser.getId(),ConstraintSet.LEFT,mainLayout.getId(),ConstraintSet.LEFT,marginLeft);


        set.applyTo(mainLayout);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("User");
        firebaseUser = firebaseAuth.getCurrentUser();
        progressDialog = new ProgressDialog(this);

        loggedIn = firebaseAuth.getCurrentUser()!= null&&firebaseAuth.getCurrentUser().isEmailVerified();


        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions
                .Builder()
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        login_google.setOnClickListener(v -> SignInGoogle());


        if(!loggedIn){
            deleteUser.setVisibility(View.GONE);
            userLogout.setVisibility(View.GONE);
            loginEmail.setVisibility(View.VISIBLE);
            login_google.setVisibility(View.VISIBLE);
            login_facebook.setVisibility(View.VISIBLE);
        }else{
            userID = firebaseUser.getUid();
            deleteUser.setVisibility(View.VISIBLE);
            userLogout.setVisibility(View.VISIBLE);
            loginEmail.setVisibility(View.GONE);
            login_google.setVisibility(View.GONE);
            login_facebook.setVisibility(View.GONE);

        }

        deleteUser.setOnClickListener(v -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(SignInActivity.this);
            builder.setTitle("DELETE ACCOUNT");
            builder.setMessage("Do you really want to delete your account?");
            builder.setPositiveButton("YES", (dialog, which) -> {
                dialog.dismiss();
                databaseReference.child(userID).removeValue();

                firebaseUser.delete().addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        FirebaseMessaging.getInstance().unsubscribeFromTopic(userID);
                        Toast.makeText(SignInActivity.this,"Account Deleted",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                });
            });
            builder.setNegativeButton("NO", (dialog, which) -> dialog.dismiss());
            AlertDialog alert = builder.create();
            alert.show();
                });

        userLogout.setOnClickListener(view -> {


            FirebaseMessaging.getInstance().unsubscribeFromTopic(userID);

            firebaseAuth.signOut();

            Intent intent = new Intent(SignInActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }


    void SignInGoogle(){
        progressDialog.setMessage("Signing in ...");
        progressDialog.show();
        Intent signIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signIntent,GOOGLE_SIGN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GOOGLE_SIGN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try{

                GoogleSignInAccount account = task.getResult(ApiException.class);
                if(account!=null) firebaseAuthWithGoogle(account);

            }catch (ApiException e){
                e.printStackTrace();
            }


        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        Log.d("TAG", "firebaseAuthWithGoogle: "+account.getId());

        AuthCredential credential = GoogleAuthProvider
                .getCredential(account.getIdToken(),null);

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {

                    if(task.isSuccessful()){
                        progressDialog.dismiss();

                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                        String userID = firebaseUser.getUid();
                        FirebaseMessaging.getInstance().subscribeToTopic(userID);

                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Log.d("TAG","signin failure", task.getException());
                        Toast.makeText(this, "Signin Failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void onClickEmailAndPasswordLogin(View view) {
        Intent intent = new Intent(getApplicationContext(),EmailAndPassLogIn.class);
        startActivity(intent);
        finish();
    }

}
// TODO change progressDialog na progressBar
//TODO zmienić main activity aby inaczej wyświetlało kto zalogowany i przycisk account
//TODO Rejestracja przez Facebook
//TODO animacja
