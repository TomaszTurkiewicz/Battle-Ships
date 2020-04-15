package com.example.ships;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Shader;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ships.adapters.RecyclerViewAdapter;
import com.example.ships.classes.Ranking;
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

    private Ranking ranking;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private int numberOfUsers;
    List<User> list = new ArrayList<>();
    private ProgressDialog progressDialog;
    private ConstraintLayout mainLayout;
    private LinearLayout linearLayout;
    private int square;
    private String userId;
    private ImageButton leave;
    private RecyclerView recyclerView;
    private boolean logIn;


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

        for(int i = 0; i<linearLayout.getChildCount(); i++){
            TextView tv = (TextView)linearLayout.getChildAt(i);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,square);
        }

        ConstraintSet set = new ConstraintSet();

        set.clone(mainLayout);
        set.connect(leave.getId(), ConstraintSet.TOP,mainLayout.getId(),ConstraintSet.TOP,mariginTop);
        set.connect(leave.getId(),ConstraintSet.LEFT,mainLayout.getId(),ConstraintSet.LEFT,square);

        set.applyTo(mainLayout);



        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference(getString(R.string.firebasepath_user));
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating ranking...");
        progressDialog.show();
        initRanking();

    }


    private void initRanking(){

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                numberOfUsers = (int) dataSnapshot.getChildrenCount();
                ranking = new Ranking(numberOfUsers);

                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    User user = postSnapshot.getValue(User.class);
                    list.add(user);
                }




                for(int i=0;i<list.size();i++){
                    ranking.addUsers(list.get(i),i);
                }
                ranking.sortRanking();

                initRecyclerView();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void initRecyclerView(){
        progressDialog.dismiss();
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this,ranking,square,userId);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void leaveScoreOnClick(View view) {
        finish();
    }
}
