package com.example.ships;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Shader;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.TypedValue;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.ships.adapters.RecyclerViewAdapterChooseOpponent;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChooseOpponent extends AppCompatActivity {

    private Ranking ranking;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private int numberOfUsers;
    private List<User> list= new ArrayList<>();;
    private ProgressDialog progressDialog;
    private String userID;
    private User me = new User();
    private boolean accepted;
    private Handler mHandler = new Handler();
    private int deelay = 1000;
    private String NOTIFICATION_TITLE;
    private String NOTIFICATION_MESSAGE;
    private String TOPIC;
    private String TAG= "NOTIFICATION TAG";
    private String FCM_API="https://fcm.googleapis.com/fcm/send";
    private String serverKey= "key=" + "AAAAUhITVm0:APA91bGLIOR5L7HQyh64ejoejk-nQFBWP9RxDqtzzjoSXCmROqs7JO_uDDyuW5VuTfJBxtKY_RG8q5_CnpKJsN3qHtVvgiAkuDM2J9T68mk0LzKCcRKgRbj3DQ-A1a8uzZ07wz8OlirQ";
    private String contentType= "application/json";
    private int square;
    private ConstraintLayout mainLayout;
    private LinearLayout linearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_choose_opponent);
        mainLayout=findViewById(R.id.constraintLayoutChooseOpponentActivity);
        linearLayout=findViewById(R.id.LinearLayoutActivityChooseOpponent);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        userID = firebaseUser.getUid();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference(getString(R.string.firebasepath_user));
        databaseReference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                me = dataSnapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating ranking...");
        progressDialog.show();
        initRanking();
        accepted=false;
        checkMyOpponentIndex.run();
    }

    private Runnable checkMyOpponentIndex = new Runnable() {
        @Override
        public void run() {
                  databaseReference.child(userID).child("index").child("opponent").addListenerForSingleValueEvent(new ValueEventListener() {
                      @Override
                      public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                          if(!dataSnapshot.getValue().equals("")){
                              mHandler.removeCallbacks(checkMyOpponentIndex);
                              finish();
                          }else{
                           mHandler.postDelayed(checkMyOpponentIndex,deelay);
                          }
                      }
                      @Override
                      public void onCancelled(@NonNull DatabaseError databaseError) {
                      }
                  });
        }
    };

    private void initRanking() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                numberOfUsers = (int) dataSnapshot.getChildrenCount();
                ranking = new Ranking(numberOfUsers);
                list.clear();
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
        RecyclerViewAdapterChooseOpponent adapter = new RecyclerViewAdapterChooseOpponent(this,ranking,square,userID);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setOnItemClickListener(position -> invite(position));
    }
    private void invite(int position) {
        if(!ranking.getRanking(position).getId().equals(userID)){
            String opponentID = ranking.getRanking(position).getId();
            databaseReference.child(userID).child("index").child("opponent").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(!dataSnapshot.getValue().equals("")){
                        mHandler.removeCallbacks(checkMyOpponentIndex);
                        finish();
                    }
                    else{
                        databaseReference.child(opponentID).child("index").child("opponent").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                                if(!dataSnapshot1.getValue().equals("")){
                                    initRanking();
                                }else{


                                                TOPIC = "/topics/"+ opponentID;

                                                NOTIFICATION_TITLE = "Invitation from: "+ me.getName();
                      //                          NOTIFICATION_MESSAGE = me.getName();

                                                JSONObject notification = new JSONObject();
                                                JSONObject notificationBody = new JSONObject();

                                                try{
                                                    notificationBody.put("title",NOTIFICATION_TITLE);
                      //                              notificationBody.put("message",NOTIFICATION_MESSAGE);

                                                    notification.put("to",TOPIC);
                                                    notification.put("notification",notificationBody);
                                                } catch (JSONException e){
                                                    Log.e(TAG,"onCreate: "+e.getMessage());
                                                }
                                                sendNotification(notification);


                                            accepted=true;
                                            databaseReference.child(userID).child(getString(R.string.firebasepath_index)).child(getString(R.string.firebasepath_opponent)).setValue(opponentID);
                                            databaseReference.child(userID).child(getString(R.string.firebasepath_index)).child(getString(R.string.firebasepath_accepted)).setValue(accepted);
                                            databaseReference.child(opponentID).child(getString(R.string.firebasepath_index)).child(getString(R.string.firebasepath_opponent)).setValue(userID);
                                    mHandler.removeCallbacks(checkMyOpponentIndex);
                                            finish();



                                        }

                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }else;
    }

    private void sendNotification(JSONObject notification) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(FCM_API, notification,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG, "onResponse: " + response.toString());

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ChooseOpponent.this, "Request error", Toast.LENGTH_LONG).show();
                        Log.i(TAG, "onErrorResponse: Didn't work");
                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", serverKey);
                params.put("Content-Type", contentType);
                return params;
            }
        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);



        }


}

// TODO invitation with dialog builder (confirmation)