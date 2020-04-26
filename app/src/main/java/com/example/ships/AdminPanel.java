package com.example.ships;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class AdminPanel extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
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
        setContentView(R.layout.activity_admin_panel);

        // go to main menu if not me
        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            if(Objects.equals(FirebaseAuth.getInstance().getCurrentUser().getEmail(), "tturkiewicz83@gmail.com")){
                //do nothing
            }else{
                Intent intent = new Intent(AdminPanel.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        }else{
            Intent intent = new Intent(AdminPanel.this,MainActivity.class);
            startActivity(intent);
            finish();
        }


        
    }
}
