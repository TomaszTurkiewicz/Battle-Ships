package com.example.ships;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Shader;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.example.ships.classes.FightIndex;
import com.example.ships.classes.TileDrawable;
import com.example.ships.classes.User;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Arrays;

public class SignInActivity extends AppCompatActivity {
    private Button userLogout;
    private FirebaseAuth firebaseAuth;
    private Button deleteUser, loginEmail;
    private FirebaseUser firebaseUser;
    private String userID;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference, databaseReferenceFight;
    private boolean loggedIn;
    static final int GOOGLE_SIGN = 123;
    private Button login_google;
    private Button login_facebook;
    private GoogleSignInClient mGoogleSignInClient;
    private ProgressDialog progressDialog;
    private ConstraintLayout mainLayout;
    private ImageButton leave;
    private User me = new User();
    private FightIndex fightIndex = new FightIndex();
    private boolean multiplayer = false;
    private CallbackManager mCallbackManager;
    private static final String TAG = "FACELOG";
    private int a;
    private boolean loginWithFacebook = false;
    private AuthCredential credentialFacebook;
    private int flags;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
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
        setContentView(R.layout.activity_sign_in);
        userLogout=findViewById(R.id.logout);
        leave=findViewById(R.id.leaveSignInActivity);
        leave.setBackgroundResource(R.drawable.back);
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
        ConstraintLayout.LayoutParams params6 = new ConstraintLayout.LayoutParams(2*square,2*square);


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

        leave.setLayoutParams(params6);

        ConstraintSet set = new ConstraintSet();
        set.clone(mainLayout);

        set.connect(loginEmail.getId(),ConstraintSet.TOP,mainLayout.getId(),ConstraintSet.TOP,5*square);
        set.connect(loginEmail.getId(),ConstraintSet.LEFT,mainLayout.getId(),ConstraintSet.LEFT,marginLeft);

        set.connect(login_google.getId(),ConstraintSet.TOP,loginEmail.getId(),ConstraintSet.BOTTOM,5*square);
        set.connect(login_google.getId(),ConstraintSet.LEFT,mainLayout.getId(),ConstraintSet.LEFT,marginLeft);

        set.connect(login_facebook.getId(),ConstraintSet.TOP,login_google.getId(),ConstraintSet.BOTTOM,5*square);
        set.connect(login_facebook.getId(),ConstraintSet.LEFT,mainLayout.getId(),ConstraintSet.LEFT,marginLeft);

        set.connect(deleteUser.getId(),ConstraintSet.TOP,mainLayout.getId(),ConstraintSet.TOP,3*square);
        set.connect(deleteUser.getId(),ConstraintSet.LEFT,mainLayout.getId(),ConstraintSet.LEFT,marginLeft);

        set.connect(userLogout.getId(),ConstraintSet.TOP,deleteUser.getId(),ConstraintSet.BOTTOM,12*square);
        set.connect(userLogout.getId(),ConstraintSet.LEFT,mainLayout.getId(),ConstraintSet.LEFT,marginLeft);

        set.connect(leave.getId(),ConstraintSet.TOP,mainLayout.getId(),ConstraintSet.TOP,screenHeight-screenHeightOffSet-3*square);
        set.connect(leave.getId(),ConstraintSet.LEFT,mainLayout.getId(),ConstraintSet.LEFT,square);

        set.applyTo(mainLayout);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("User");
        databaseReferenceFight=firebaseDatabase.getReference("Battle");
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        progressDialog = new ProgressDialog(this);

        if(firebaseUser != null){
            String providerId="";
            for(UserInfo profile : firebaseUser.getProviderData()){
                providerId = providerId+" "+profile.getProviderId();
            }

            if(providerId.contains("facebook.com")||providerId.contains("google.com")){
                loggedIn=true;
            }else{
                if(firebaseUser.isEmailVerified()){
                    loggedIn=true;
                }else{
                    loggedIn=false;
                }
            }
        }

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
            databaseReference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        me = dataSnapshot.getValue(User.class);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            deleteUser.setVisibility(View.VISIBLE);
            userLogout.setVisibility(View.VISIBLE);
            loginEmail.setVisibility(View.GONE);
            login_google.setVisibility(View.GONE);
            login_facebook.setVisibility(View.GONE);
        }

        deleteUser.setOnClickListener(v -> {
            databaseReference.child(userID).child("index").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        fightIndex = dataSnapshot.getValue(FightIndex.class);
                        if(!fightIndex.getOpponent().equals("")){
                            multiplayer=true;
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });

            AlertDialog.Builder mBuilder = new AlertDialog.Builder(SignInActivity.this);
            View mView = getLayoutInflater().inflate(R.layout.alert_dialog_with_two_buttons,null);
            mBuilder.setView(mView);
            AlertDialog dialog = mBuilder.create();
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
            dialog.getWindow().getDecorView().setSystemUiVisibility(flags);
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            TextView title = mView.findViewById(R.id.alert_dialog_title_layout_with_two_buttons);
            TextView message = mView.findViewById(R.id.alert_dialog_message_layout_with_two_buttons);
            Button negativeButton = mView.findViewById(R.id.alert_dialog_left_button_layout_with_two_buttons);
            Button positiveButton = mView.findViewById(R.id.alert_dialog_right_button_layout_with_two_buttons);
            title.setText("DELETE ACCOUNT");
            message.setText("Do you really want to delete your account?");
            negativeButton.setText("NO");
            negativeButton.setOnClickListener(v12 -> dialog.dismiss());
            positiveButton.setText("YES");
            positiveButton.setOnClickListener(v1 -> {
                dialog.dismiss();
                firebaseUser.delete().addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        databaseReference.child(userID).removeValue();
                        if(multiplayer){
                            FightIndex fightIndex1 = new FightIndex();
                            databaseReference.child(fightIndex.getOpponent()).child("index").setValue(fightIndex1);
                            if(!fightIndex.getGameIndex().equals("")){
                                databaseReferenceFight.child(fightIndex.getGameIndex()).removeValue();
                            }
                        }
                        FirebaseMessaging.getInstance().unsubscribeFromTopic(userID);
                        Toast.makeText(SignInActivity.this,"Account Deleted",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(SignInActivity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
            });
            dialog.show();
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

        mCallbackManager = CallbackManager.Factory.create();
        updateWithToken(AccessToken.getCurrentAccessToken());
        LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
            }
        });
        login_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logInWithReadPermissions(SignInActivity.this,Arrays.asList("email","public_profile"));
            }
        });
    }

    private void updateWithToken(AccessToken currentAccessToken) {
        if(currentAccessToken!=null){
            LoginManager.getInstance().logOut();
        }
    }

    private void handleFacebookAccessToken(AccessToken token) {
        loginWithFacebook=true;
        Log.d(TAG, "handleFacebookAccessToken:" + token);
        credentialFacebook = FacebookAuthProvider.getCredential(token.getToken());
            firebaseAuth.signInWithCredential(credentialFacebook)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithCredential:success");
                                firebaseUser = firebaseAuth.getCurrentUser();
                                String userID = firebaseUser.getUid();
                                FirebaseMessaging.getInstance().subscribeToTopic(userID);
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                                //               updateUI(user);
                            }
                            else if (!task.isSuccessful() && task.getException() instanceof FirebaseAuthUserCollisionException) {
                                FirebaseAuthUserCollisionException exception =
                                        (FirebaseAuthUserCollisionException) task.getException();
                                String emailException = exception.getEmail();
                                String error = exception.getErrorCode();

                                if(error.equals("ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL")) {
                                    firebaseAuth.fetchSignInMethodsForEmail(emailException).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                                            if(task.isSuccessful()){

                                                if(task.getResult().getSignInMethods().contains(GoogleAuthProvider.PROVIDER_ID)) {
                                                    SignInGoogle();

                                                }
                                                else if(task.getResult().getSignInMethods().contains(EmailAuthProvider.EMAIL_PASSWORD_SIGN_IN_METHOD)){
                                                        SharedPreferences sp = getSharedPreferences(emailException,Activity.MODE_PRIVATE);
                                                        String password = sp.getString("password",null);
                                                        if(password==null){

                                                            Intent intent = new Intent(getApplicationContext(),EmailAndPassLogIn.class);
                                                            startActivity(intent);
                                                            finish();
                                                        }else {
                                                            firebaseAuth.signInWithEmailAndPassword(emailException, password).addOnCompleteListener(task1 -> {
                                                                if (task1.isSuccessful()) {
                                                                    firebaseUser = firebaseAuth.getCurrentUser();
                                                                    String userID = firebaseUser.getUid();
                                                                    FirebaseMessaging.getInstance().subscribeToTopic(userID);
                                                                    firebaseUser.linkWithCredential(credentialFacebook).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                                                            if (task.isSuccessful()) {
                                                                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                                                startActivity(intent);
                                                                                finish();
                                                                            }
                                                                        }
                                                                    });
                                                                } else {


                                                                }
                                                                ;
                                                            });
                                                        }
                                                }else;
                                            }
                                        }
                                    });
                                }
                                else{
                                    Toast.makeText(SignInActivity.this, error, Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
    }

    void SignInGoogle(){
        progressDialog.setMessage("Signing in ...");
        progressDialog.show();
        Intent signIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signIntent,GOOGLE_SIGN);
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        Log.d("TAG", "firebaseAuthWithGoogle: "+account.getId());

        AuthCredential credential = GoogleAuthProvider
                .getCredential(account.getIdToken(),null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {

                    if(task.isSuccessful()){
                        progressDialog.dismiss();

                        if(!loginWithFacebook) {


                            firebaseUser = firebaseAuth.getCurrentUser();
                            String userID = firebaseUser.getUid();
                            FirebaseMessaging.getInstance().subscribeToTopic(userID);

                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();

                        }else{
                            firebaseUser = firebaseAuth.getCurrentUser();
                            String userID = firebaseUser.getUid();
                            FirebaseMessaging.getInstance().subscribeToTopic(userID);
                            firebaseUser.linkWithCredential(credentialFacebook).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                        }


                    }else{
                        Log.d("TAG","signin failure", task.getException());
                        Toast.makeText(this, "Signin Failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
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

    public void onClickEmailAndPasswordLogin(View view) {
        Intent intent = new Intent(getApplicationContext(),EmailAndPassLogIn.class);
        startActivity(intent);
        finish();
    }

    public void leaveSignInActivityOnClick(View view) {
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


// TODO change progressDialog na progressBar

