package com.example.ships;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Shader;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.example.ships.classes.TileDrawable;
import com.example.ships.classes.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class SettingsActivity extends AppCompatActivity {

    private int flags;
    private ImageButton leave;
    private ConstraintLayout mainLayout;
    private ImageView photo;
    private User user = new User();
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;


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
        setContentView(R.layout.activity_settings);
        leave=findViewById(R.id.leaveSettingsActivity);
        leave.setBackgroundResource(R.drawable.back);
        mainLayout=findViewById(R.id.settingsActivityLayout);
        photo=findViewById(R.id.photo_settings_activity);

        SharedPreferences sp = getSharedPreferences("VALUES", Activity.MODE_PRIVATE);
        int square = sp.getInt("square",-1);
        int screenHeight = sp.getInt("width",-1);
        int screenWidth = sp.getInt("height",-1);
        int screenHeightOffSet = sp.getInt("widthOffSet",-1);
        int screenWidthOffSet = sp.getInt("heightOffSet",-1);

        mainLayout.setBackground(new TileDrawable(getDrawable(R.drawable.background_x), Shader.TileMode.REPEAT,square));

        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(2*square,2*square);
        ConstraintLayout.LayoutParams params1 = new ConstraintLayout.LayoutParams(4*square,4*square);

        leave.setLayoutParams(params);
        photo.setLayoutParams(params1);

        ConstraintSet set = new ConstraintSet();
        set.clone(mainLayout);

        set.connect(leave.getId(),ConstraintSet.TOP,mainLayout.getId(),ConstraintSet.TOP,screenHeight-screenHeightOffSet-3*square);
        set.connect(leave.getId(),ConstraintSet.LEFT,mainLayout.getId(),ConstraintSet.LEFT,square);

        set.connect(photo.getId(),ConstraintSet.TOP,mainLayout.getId(),ConstraintSet.TOP,square);
        set.connect(photo.getId(),ConstraintSet.LEFT,mainLayout.getId(),ConstraintSet.LEFT,screenWidth-screenWidthOffSet-5*square);

        set.applyTo(mainLayout);


        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("User").child(firebaseUser.getUid());
        storageReference = FirebaseStorage.getInstance().getReference("profile_picture").child(firebaseUser.getUid());

        final long SIZE=1024*1024;
        storageReference.getBytes(SIZE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bm = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                photo.setImageBitmap(bm);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                photo.setBackgroundResource(R.drawable.account_box_grey);
            }
        });




    }


    @Override
    public void onBackPressed() {
        backToMainMenu();
    }

    public void leaveSettingsActivityOnClick(View view) {
        backToMainMenu();
    }

    private void backToMainMenu(){
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
    }
}
//TODO finish settings activity