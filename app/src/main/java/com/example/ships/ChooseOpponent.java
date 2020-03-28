package com.example.ships;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ships.adapters.RecyclerViewAdapterChooseOpponent;
import com.example.ships.classes.Ranking;
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

public class ChooseOpponent extends AppCompatActivity {

    private Ranking ranking;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private int numberOfUsers;
    List<User> list = new ArrayList<>();
    private ProgressDialog progressDialog;
    private String userID;
    private boolean accepted;
//TODO change textview invitation for layout invitation with buttons
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_opponent);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        userID = firebaseUser.getUid();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference(getString(R.string.firebasepath_user));
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating ranking...");
        progressDialog.show();
        initRanking();
        accepted=false;
    }

    private void initRanking() {

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

    private void initRecyclerView() {
        progressDialog.dismiss();
        RecyclerView recyclerView = findViewById(R.id.recyclerViewChooseOpponent);
        RecyclerViewAdapterChooseOpponent adapter = new RecyclerViewAdapterChooseOpponent(this,ranking,userID);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setOnItemClickListener(position -> invite(position));
    }

    private void invite(int position) {
        if(!list.get(position).getId().equals(userID)){
            String opponentID = list.get(position).getId();
            accepted=true;
            databaseReference.child(userID).child(getString(R.string.firebasepath_index)).child(getString(R.string.firebasepath_opponent)).setValue(opponentID);
            databaseReference.child(userID).child(getString(R.string.firebasepath_index)).child(getString(R.string.firebasepath_accepted)).setValue(accepted);
            databaseReference.child(opponentID).child(getString(R.string.firebasepath_index)).child(getString(R.string.firebasepath_opponent)).setValue(userID);
        }else;
    }
}
