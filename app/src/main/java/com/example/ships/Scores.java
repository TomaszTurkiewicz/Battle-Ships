package com.example.ships;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Shader;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ships.adapters.RecyclerViewAdapter;
import com.example.ships.classes.Ranking;
import com.example.ships.classes.TileDrawable;
import com.example.ships.classes.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_scores);
        mainLayout=findViewById(R.id.constraintLayoutScoreActivity);
        linearLayout=findViewById(R.id.LinearLayoutActivityScores);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if(firebaseUser != null && firebaseUser.isEmailVerified()){
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

        mainLayout.setBackground(new TileDrawable(getDrawable(R.drawable.background_x), Shader.TileMode.REPEAT,square));

        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT,2*square);

        linearLayout.setLayoutParams(params);

        for(int i = 0; i<linearLayout.getChildCount(); i++){
            TextView tv = (TextView)linearLayout.getChildAt(i);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,square);
        }



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
        RecyclerView recyclerView = findViewById(R.id.recyclerViewScores);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this,ranking,square,userId);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
