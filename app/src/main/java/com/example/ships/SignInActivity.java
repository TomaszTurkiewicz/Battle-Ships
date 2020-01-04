package com.example.ships;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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

public class SignInActivity extends AppCompatActivity {
    private Button userLogout;
    private FirebaseAuth firebaseAuth;
    private Button deleteUser;
    private String userID;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private boolean loggedIn;
    static final int GOOGLE_SIGN = 123;
    private Button login_google;
    //TODO dokończyć logowanie przez googla zakładanie bazy danych dla nowego użytkownika
    private GoogleSignInClient mGoogleSignInClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        userLogout=findViewById(R.id.logout);
        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser firebaseUser;
        deleteUser = findViewById(R.id.deleteUser);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("User");
        firebaseUser = firebaseAuth.getCurrentUser();

        loggedIn = firebaseAuth.getCurrentUser()!= null&&firebaseAuth.getCurrentUser().isEmailVerified();

        login_google = findViewById(R.id.loginGoogle);
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
        }else{
            userID = firebaseUser.getUid();
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
            firebaseAuth.signOut();
            Intent intent = new Intent(SignInActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }


    void SignInGoogle(){
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

//TODO zmienić main activity aby inaczej wyświetlało kto zalogowany i przycisk account
//TODO rejestracje przez Gmaila
//TODO Rejestracja przez Facebook
//TODO animacja
//TODO wymyślić jak zrobić grę na dwie osoby
