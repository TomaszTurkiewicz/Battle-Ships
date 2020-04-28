package com.example.ships;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ships.adapters.RecyclerViewAdapter;
import com.example.ships.classes.TileDrawable;
import com.example.ships.classes.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class Scores extends AppCompatActivity {


    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private int numberOfUsers;
    List<User> list = new ArrayList<>();
    private ConstraintLayout mainLayout;
    private LinearLayout linearLayout;
    private int square;
    private String userId;
    private ImageButton leave;
    private RecyclerView recyclerView;
    private boolean logIn;
    private ProgressBar progressBar;
    private int progressBarMax, progressBarProgress;
    private Handler handler = new Handler();



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
        setContentView(R.layout.activity_scores);
        mainLayout=findViewById(R.id.constraintLayoutScoreActivity);
        linearLayout=findViewById(R.id.LinearLayoutActivityScores);
        recyclerView=findViewById(R.id.recyclerViewScores);
        leave=findViewById(R.id.leaveScore);
        leave.setBackgroundResource(R.drawable.back);
        progressBar=findViewById(R.id.scoreProgressBar);


        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();


        if(firebaseUser != null){
            String providerId="";
            for(UserInfo profile : firebaseUser.getProviderData()){
                providerId = providerId+" "+profile.getProviderId();
            }

            if(providerId.contains("facebook.com")||providerId.contains("google.com")){
                logIn=true;
            }else{
                if(firebaseUser.isEmailVerified()){
                    logIn=true;
                }else{
                    logIn=false;
                }
            }
        }

        if(logIn){
            userId = firebaseUser.getUid();
        }else{
            userId="";
        }

        SharedPreferences sp = getSharedPreferences("VALUES", Activity.MODE_PRIVATE);
        square = sp.getInt("square",-1);
        int screenWidth = sp.getInt("width",-1);
        int screenHeight = sp.getInt("height",-1);
        int screenWidthOffSet = sp.getInt("widthOffSet",-1);
        int screenHeightOffSet = sp.getInt("heightOffSet",-1);
        int mariginTop = screenHeight-screenHeightOffSet-3*square;
        mainLayout.setBackground(new TileDrawable(getDrawable(R.drawable.background_x), Shader.TileMode.REPEAT,square));

        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT,2*square);
        ConstraintLayout.LayoutParams params1 = new ConstraintLayout.LayoutParams(2*square,2*square);

        linearLayout.setLayoutParams(params);
        leave.setLayoutParams(params1);
        int textSize = 8*square/10;


        for(int i = 0; i<linearLayout.getChildCount(); i++){
            TextView tv = (TextView)linearLayout.getChildAt(i);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
        }

        ConstraintSet set = new ConstraintSet();

        set.clone(mainLayout);
        set.connect(leave.getId(), ConstraintSet.TOP,mainLayout.getId(),ConstraintSet.TOP,mariginTop);
        set.connect(leave.getId(),ConstraintSet.LEFT,mainLayout.getId(),ConstraintSet.LEFT,square);

        set.applyTo(mainLayout);

        progressBarProgress=0;
        Drawable progressDrawable = progressBar.getProgressDrawable().mutate();
        progressDrawable.setColorFilter(getColor(R.color.pen_red), PorterDuff.Mode.SRC_IN);
        progressBar.setProgressDrawable(progressDrawable);



        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference(getString(R.string.firebasepath_user));
        recyclerView.setVisibility(View.GONE);
        initRanking();


    }


    private void initRanking(){

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                numberOfUsers = (int) dataSnapshot.getChildrenCount();
                progressBarMax=2*numberOfUsers+1;
                progressBar.setMax(progressBarMax);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (progressBarProgress<progressBarMax){
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setProgress(progressBarProgress);
                                }
                            });
                            try{
                                Thread.sleep(300);
                            }catch (InterruptedException e){
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();


                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    User user = postSnapshot.getValue(User.class);
                    list.add(user);
                    progressBarProgress++;
                }

                User tmp;
                boolean sort;
                do{
                    sort=false;
                    for(int i=list.size()-1;i>0;i--){
                        if(list.get(i).getScore()>list.get(i-1).getScore()){
                            tmp=list.get(i);
                            list.set(i,list.get(i-1));
                            list.set(i-1,tmp);
                            sort=true;
                        }
                    }
                    progressBarProgress++;
                }while (sort);

                initRecyclerView();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void initRecyclerView(){
        progressBarProgress=progressBarMax;
        recyclerView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this,list,square,userId);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void leaveScoreOnClick(View view) {
        finish();
    }

}
