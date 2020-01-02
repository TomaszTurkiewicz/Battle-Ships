package com.example.ships;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    //TODO dokończyć logowanie przez googla

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



        if(!loggedIn){
            deleteUser.setVisibility(View.GONE);
            userLogout.setVisibility(View.GONE);
        }else{
            userID = firebaseUser.getUid();
        }

        deleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(SignInActivity.this);
                builder.setTitle("DELETE ACCOUNT");
                builder.setMessage("Do you really want to delete your account?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        databaseReference.child(userID).removeValue();
                        firebaseUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(SignInActivity.this,"Account Deleted",Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });

                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();








                    }
        });

        userLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
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
