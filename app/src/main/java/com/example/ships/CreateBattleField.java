package com.example.ships;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Shader;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.example.ships.classes.BattleField;
import com.example.ships.classes.BattleFieldForDataBase;
import com.example.ships.classes.GameDifficulty;
import com.example.ships.classes.PointIJ;
import com.example.ships.classes.TileDrawable;
import com.example.ships.classes.User;
import com.example.ships.singletons.BattleFieldPlayerOneSingleton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class CreateBattleField extends AppCompatActivity {

    private BattleField battleFieldPlayerCreateBattleFieldActivity = new BattleField();
    private BattleFieldForDataBase battleFieldForDataBase = new BattleFieldForDataBase();

    private TextView FourMastsCounter;
    private TextView ThreeMastsCounter;
    private TextView TwoMastsCounter;
    private TextView OneMastsCounter;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReferenceMy, databaseReferenceFight;
    private int fourMastsCounter;
    private int threeMastsCounter1;
    private int threeMastsCounter2;
    private int twoMastsCounter1;
    private int twoMastsCounter2;
    private int twoMastsCounter3;
    private int oneMastsCounter1;
    private int oneMastsCounter2;
    private int oneMastsCounter3;
    private int oneMastsCounter4;
    int leftFourMasts;
    int leftThreeMasts;
    int leftTwoMasts;
    int leftOneMasts;
    int choosenShip;
    int shipNumberFlag;
    PointIJ firstPointMastsShip = new PointIJ();
    PointIJ secondPointMastsShip = new PointIJ();
    int shipNumber;
    private Button startButton;
    private boolean multiplayerMode;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private String userID;
    private String gameIndex;
    private Handler mHandler = new Handler();
    private int deelay = 1000;
    private User user = new User();
    private ImageButton delete, reset;
    private Button backButton;
    private int noOfGames;
    private ConstraintLayout constraintLayout;
    private GridLayout gridLayout;
    private LinearLayout linearLayoutLetters, linearLayoutNumbers;
    private LinearLayout linearLayoutFourMasts, linearLayoutThreeMasts, linearLayoutTwoMasts, linearLayoutOneMasts;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_battle_field);
        multiplayerMode= GameDifficulty.getInstance().isMultiplayerMode();
        FourMastsCounter = (TextView)findViewById(R.id.fourMastsCounterTextView);
        ThreeMastsCounter = (TextView)findViewById(R.id.threeMastsCounterTextView);
        TwoMastsCounter = (TextView)findViewById(R.id.twoMastsCounterTextView);
        OneMastsCounter = (TextView)findViewById(R.id.oneMastsCounterTextView);
        delete = findViewById(R.id.deleteButton);
        reset = findViewById(R.id.resetButton);
        backButton = findViewById(R.id.backButton);
        constraintLayout=findViewById(R.id.createBattleFieldActivityLayout);
        gridLayout=findViewById(R.id.tableLayoutPlayerBattleFieldCreateBattleField);
        linearLayoutLetters=findViewById(R.id.LinearLayoutCreateBattleFieldActivityLetters);
        linearLayoutNumbers=findViewById(R.id.LinearLayoutCreateBattleFieldActivityNumbers);
        reset.setBackgroundResource(R.drawable.reset);
        delete.setBackgroundResource(R.drawable.delete);
        startButton=findViewById(R.id.playButton);
        linearLayoutFourMasts=findViewById(R.id.linearLayoutFourMastsShip);
        linearLayoutThreeMasts=findViewById(R.id.linearLayoutThreeMastsShip);
        linearLayoutTwoMasts=findViewById(R.id.linearLayoutTwoMastsShip);
        linearLayoutOneMasts=findViewById(R.id.linearLayoutOneMastsShip);

        SharedPreferences sp = getSharedPreferences("VALUES", Activity.MODE_PRIVATE);
        int square = sp.getInt("square",-1);
        int width = sp.getInt("width",-1);
        int height = sp.getInt("height",-1);
        int widthOffSet = sp.getInt("widthOffSet",-1);
        int heightOffSet = sp.getInt("heightOffSet",-1);
        float textSize = (square*9)/10;
        int rightEnd = width-widthOffSet;


        constraintLayout.setBackground(new TileDrawable(getDrawable(R.drawable.background_x), Shader.TileMode.REPEAT,square));

        ConstraintSet set = new ConstraintSet();
        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(10*square,10*square);
        ConstraintLayout.LayoutParams params1 = new ConstraintLayout.LayoutParams(10*square,square);
        ConstraintLayout.LayoutParams params2 = new ConstraintLayout.LayoutParams(square,10*square);
        ConstraintLayout.LayoutParams params3 = new ConstraintLayout.LayoutParams(6*square,2*square);
        ConstraintLayout.LayoutParams params4 = new ConstraintLayout.LayoutParams(2*square,2*square);
        ConstraintLayout.LayoutParams params5 = new ConstraintLayout.LayoutParams(2*square,2*square);
        ConstraintLayout.LayoutParams params6 = new ConstraintLayout.LayoutParams(4*square,square);
        ConstraintLayout.LayoutParams params7 = new ConstraintLayout.LayoutParams(3*square,square);
        ConstraintLayout.LayoutParams params8 = new ConstraintLayout.LayoutParams(2*square,square);
        ConstraintLayout.LayoutParams params9 = new ConstraintLayout.LayoutParams(square,square);
        ConstraintLayout.LayoutParams params10 = new ConstraintLayout.LayoutParams(square,square);
        ConstraintLayout.LayoutParams params11 = new ConstraintLayout.LayoutParams(square,square);
        ConstraintLayout.LayoutParams params12 = new ConstraintLayout.LayoutParams(square,square);
        ConstraintLayout.LayoutParams params13 = new ConstraintLayout.LayoutParams(square,square);
        ConstraintLayout.LayoutParams params14 = new ConstraintLayout.LayoutParams(square,square);
        ConstraintLayout.LayoutParams params15 = new ConstraintLayout.LayoutParams(square,square);
        ConstraintLayout.LayoutParams params16 = new ConstraintLayout.LayoutParams(square,square);
        ConstraintLayout.LayoutParams params17 = new ConstraintLayout.LayoutParams(square,square);
        ConstraintLayout.LayoutParams params18 = new ConstraintLayout.LayoutParams(square,square);
        ConstraintLayout.LayoutParams params19 = new ConstraintLayout.LayoutParams(square,square);
        ConstraintLayout.LayoutParams params20 = new ConstraintLayout.LayoutParams(14 * square,2 * square);

        gridLayout.setLayoutParams(params);
        linearLayoutLetters.setLayoutParams(params1);
        linearLayoutNumbers.setLayoutParams(params2);
        backButton.setLayoutParams(params3);
        backButton.setTextSize(TypedValue.COMPLEX_UNIT_PX,square);
        reset.setLayoutParams(params4);
        delete.setLayoutParams(params5);
        linearLayoutFourMasts.setLayoutParams(params6);
        linearLayoutThreeMasts.setLayoutParams(params7);
        linearLayoutTwoMasts.setLayoutParams(params8);
        linearLayoutOneMasts.setLayoutParams(params9);

        FourMastsCounter.setLayoutParams(params10);
        ThreeMastsCounter.setLayoutParams(params14);
        TwoMastsCounter.setLayoutParams(params17);
        OneMastsCounter.setLayoutParams(params19);
        startButton.setLayoutParams(params20);
        startButton.setTextSize(TypedValue.COMPLEX_UNIT_PX,square);
        FourMastsCounter.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
        ThreeMastsCounter.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
        TwoMastsCounter.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
        OneMastsCounter.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
        for(int i=0;i<10;i++){
            TextView tv = (TextView) linearLayoutLetters.getChildAt(i);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
        }
        for(int i=0;i<10;i++){
            TextView tv = (TextView) linearLayoutNumbers.getChildAt(i);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
        }


        set.clone(constraintLayout);

        set.connect(gridLayout.getId(),ConstraintSet.TOP,constraintLayout.getId(),ConstraintSet.TOP,4*square);
        set.connect(gridLayout.getId(),ConstraintSet.LEFT,constraintLayout.getId(),ConstraintSet.LEFT,5*square);

        set.connect(linearLayoutLetters.getId(),ConstraintSet.BOTTOM,gridLayout.getId(),ConstraintSet.TOP,0);
        set.connect(linearLayoutLetters.getId(),ConstraintSet.LEFT,gridLayout.getId(),ConstraintSet.LEFT,0);

        set.connect(linearLayoutNumbers.getId(),ConstraintSet.TOP,gridLayout.getId(),ConstraintSet.TOP,0);
        set.connect(linearLayoutNumbers.getId(),ConstraintSet.RIGHT,gridLayout.getId(),ConstraintSet.LEFT,0);

        set.connect(backButton.getId(),ConstraintSet.TOP,constraintLayout.getId(),ConstraintSet.TOP,square);
        set.connect(backButton.getId(),ConstraintSet.LEFT,constraintLayout.getId(),ConstraintSet.LEFT,rightEnd-7*square);

        set.connect(reset.getId(),ConstraintSet.TOP,constraintLayout.getId(),ConstraintSet.TOP,square);
        set.connect(reset.getId(),ConstraintSet.LEFT,constraintLayout.getId(),ConstraintSet.LEFT,rightEnd-11*square);

        set.connect(delete.getId(),ConstraintSet.TOP,constraintLayout.getId(),ConstraintSet.TOP,square);
        set.connect(delete.getId(),ConstraintSet.LEFT,constraintLayout.getId(),ConstraintSet.LEFT,rightEnd-15*square);

        set.connect(linearLayoutFourMasts.getId(),ConstraintSet.TOP,constraintLayout.getId(),ConstraintSet.TOP,6*square);
        set.connect(linearLayoutFourMasts.getId(),ConstraintSet.LEFT,constraintLayout.getId(),ConstraintSet.LEFT,rightEnd-13*square);

        set.connect(FourMastsCounter.getId(),ConstraintSet.TOP,linearLayoutFourMasts.getId(),ConstraintSet.TOP,0);
        set.connect(FourMastsCounter.getId(),ConstraintSet.RIGHT,linearLayoutFourMasts.getId(),ConstraintSet.LEFT,0);

        set.connect(linearLayoutThreeMasts.getId(),ConstraintSet.TOP,constraintLayout.getId(),ConstraintSet.TOP,10*square);
        set.connect(linearLayoutThreeMasts.getId(),ConstraintSet.LEFT,constraintLayout.getId(),ConstraintSet.LEFT,rightEnd-13*square);

        set.connect(ThreeMastsCounter.getId(),ConstraintSet.TOP,linearLayoutThreeMasts.getId(),ConstraintSet.TOP,0);
        set.connect(ThreeMastsCounter.getId(),ConstraintSet.RIGHT,linearLayoutThreeMasts.getId(),ConstraintSet.LEFT,0);

        set.connect(linearLayoutTwoMasts.getId(),ConstraintSet.TOP,constraintLayout.getId(),ConstraintSet.TOP,10*square);
        set.connect(linearLayoutTwoMasts.getId(),ConstraintSet.LEFT,constraintLayout.getId(),ConstraintSet.LEFT,rightEnd-6*square);

        set.connect(TwoMastsCounter.getId(),ConstraintSet.TOP,linearLayoutTwoMasts.getId(),ConstraintSet.TOP,0);
        set.connect(TwoMastsCounter.getId(),ConstraintSet.RIGHT,linearLayoutTwoMasts.getId(),ConstraintSet.LEFT,0);

        set.connect(linearLayoutOneMasts.getId(),ConstraintSet.TOP,constraintLayout.getId(),ConstraintSet.TOP,6*square);
        set.connect(linearLayoutOneMasts.getId(),ConstraintSet.LEFT,constraintLayout.getId(),ConstraintSet.LEFT,rightEnd-5*square);

        set.connect(OneMastsCounter.getId(),ConstraintSet.TOP,linearLayoutOneMasts.getId(),ConstraintSet.TOP,0);
        set.connect(OneMastsCounter.getId(),ConstraintSet.RIGHT,linearLayoutOneMasts.getId(),ConstraintSet.LEFT,0);

        set.connect(startButton.getId(),ConstraintSet.TOP,constraintLayout.getId(),ConstraintSet.TOP,14*square);
        set.connect(startButton.getId(),ConstraintSet.LEFT,constraintLayout.getId(),ConstraintSet.LEFT,rightEnd-15*square);

        set.applyTo(constraintLayout);

        if(multiplayerMode){
            firebaseAuth = FirebaseAuth.getInstance();
            firebaseUser = firebaseAuth.getCurrentUser();
            userID = firebaseUser.getUid();
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReferenceMy = firebaseDatabase.getReference("User").child(userID);
            databaseReferenceMy.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    gameIndex = dataSnapshot.child("index").child("gameIndex").getValue().toString();
                    databaseReferenceFight = firebaseDatabase.getReference("Battle").child(gameIndex);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
            checkGameIndex.run();


        }

            updateScreen();

    }

    private void disableOneMastsTextView(){
        setGrey((TextView) linearLayoutOneMasts.getChildAt(0));
        linearLayoutOneMasts.setClickable(false);
        choosenShip=0;
    }

    private void disableTwoMastsTextView(){
        setGrey((TextView) linearLayoutTwoMasts.getChildAt(0));
        setGrey((TextView) linearLayoutTwoMasts.getChildAt(1));
        linearLayoutTwoMasts.setClickable(false);

        choosenShip=0;
    }

    private void disableThreeMastsTextView(){
        setGrey((TextView) linearLayoutThreeMasts.getChildAt(0));
        setGrey((TextView) linearLayoutThreeMasts.getChildAt(1));
        setGrey((TextView) linearLayoutThreeMasts.getChildAt(2));
        linearLayoutThreeMasts.setClickable(false);

        choosenShip=0;
    }

    private void disableFourMastsTextView(){
        setGrey((TextView) linearLayoutFourMasts.getChildAt(0));
        setGrey((TextView) linearLayoutFourMasts.getChildAt(1));
        setGrey((TextView) linearLayoutFourMasts.getChildAt(2));
        setGrey((TextView) linearLayoutFourMasts.getChildAt(3));
        linearLayoutFourMasts.setClickable(false);

        choosenShip=0;
    }

    private void updateScreen(){
        checkShipsOnBattleField(battleFieldPlayerCreateBattleFieldActivity);
        checkLeftShips();
        updateTextViewCounters();
        updateTextViewCountersShips();
        updateClicableBattleField();
        updateStartButton();
    }

    private void updateStartButton() {
        if(leftFourMasts==0&&leftThreeMasts==0&&leftTwoMasts==0&&leftOneMasts==0){
            startButton.setClickable(true);
            startButton.setVisibility(View.VISIBLE);
            if(multiplayerMode){
                for(int i=0; i<10;i++){
                    for(int j=0; j<10; j++){
                        battleFieldForDataBase.showBattleField().makeShip(i,j,battleFieldPlayerCreateBattleFieldActivity.getBattleField(i,j));
                    }
                }
                battleFieldForDataBase.fieldToList();
                battleFieldForDataBase.setCreated(true);
                noOfGames = user.getNoOfGames();
                noOfGames = noOfGames+1;
            }

        }
        else{
            startButton.setClickable(false);
            startButton.setVisibility(View.GONE);

        }
    }

//    private void updateBattleField() {
//        for(int i=0;i<10;i++){
//            for(int j=0;j<10;j++){
//                if(battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].isShip()){
//                    setShipColor(TextViewArrayActivityCreateBattleField,i,j);
//                }else{
//                    setNoShipColor(TextViewArrayActivityCreateBattleField,i,j);
//                }
//            }
//        }
//    }

    private void setNoShipColor(TextView textView) {
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            textView.setBackgroundDrawable(getResources().getDrawable(R.drawable.battle_cell_x));
        } else {
            textView.setBackground(getResources().getDrawable(R.drawable.battle_cell_x));
        }
    }

    private void setShipColor(TextView textView) {
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            textView.setBackgroundDrawable(getResources().getDrawable(R.drawable.battle_cell_ship_sunk_x));
        } else {
            textView.setBackground(getResources().getDrawable(R.drawable.battle_cell_ship_sunk_x));
        }
    }

    private void setGreyColor(TextView textView) {
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            textView.setBackgroundDrawable(getResources().getDrawable(R.drawable.battle_cell_x_hidden));
        } else {
            textView.setBackground(getResources().getDrawable(R.drawable.battle_cell_x_hidden));
        }
    }

    private void updateTextViewCountersShips() {
        updateTextViewFourMastsShipCounter();
        updateTextViewThreeMastsShipCounter();
        updateTextViewTwoMastsShipCounter();
        updateTextViewOneMastsShipCounter();
    }

    private void updateTextViewOneMastsShipCounter() {
        if(leftOneMasts==0){
            disableOneMastsTextView();
        }else{
            enableOneMastsTextView();
        }

    }

    private void enableOneMastsTextView() {
        setBlank((TextView) linearLayoutOneMasts.getChildAt(0));
        linearLayoutOneMasts.setClickable(true);
    }

    private void updateTextViewTwoMastsShipCounter() {
        if(leftTwoMasts==0){
            disableTwoMastsTextView();
        }else{
            enableTwoMastsTextView();
        }
    }

    private void enableTwoMastsTextView() {
        setBlank((TextView) linearLayoutTwoMasts.getChildAt(0));
        setBlank((TextView) linearLayoutTwoMasts.getChildAt(1));
        linearLayoutTwoMasts.setClickable(true);
    }

    private void updateTextViewThreeMastsShipCounter() {
        if(leftThreeMasts==0){
            disableThreeMastsTextView();
        }else{
            enableThreeMastsTextView();
        }
    }

    private void enableThreeMastsTextView() {

        if(choosenShip==3){
        setRed((TextView) linearLayoutThreeMasts.getChildAt(0));
        setRed((TextView) linearLayoutThreeMasts.getChildAt(1));
        setRed((TextView) linearLayoutThreeMasts.getChildAt(2));
        linearLayoutThreeMasts.setClickable(true);
    }else{
            setBlank((TextView) linearLayoutThreeMasts.getChildAt(0));
            setBlank((TextView) linearLayoutThreeMasts.getChildAt(1));
            setBlank((TextView) linearLayoutThreeMasts.getChildAt(2));
            linearLayoutThreeMasts.setClickable(true);
        }
    }

    private void updateTextViewFourMastsShipCounter() {
        if(leftFourMasts==0){
            disableFourMastsTextView();
        }else{
            enableFourMastsTextView();
        }
    }

    private void enableFourMastsTextView() {
        setBlank((TextView) linearLayoutFourMasts.getChildAt(0));
        setBlank((TextView) linearLayoutFourMasts.getChildAt(1));
        setBlank((TextView) linearLayoutFourMasts.getChildAt(2));
        setBlank((TextView) linearLayoutFourMasts.getChildAt(3));
        linearLayoutFourMasts.setClickable(true);

    }

    private void setBlank (TextView TextView){
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            TextView.setBackgroundDrawable(getResources().getDrawable(R.drawable.battle_cell_x));
        } else {
            TextView.setBackground(getResources().getDrawable(R.drawable.battle_cell_x));
        }
    }

    private void setRed (TextView TextView){
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            TextView.setBackgroundDrawable(getResources().getDrawable(R.drawable.battle_cell_ship_normal_x));
        } else {
            TextView.setBackground(getResources().getDrawable(R.drawable.battle_cell_ship_normal_x));
        }
    }

    private void setGrey (TextView TextView){
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            TextView.setBackgroundDrawable(getResources().getDrawable(R.drawable.battle_cell_widmo_ship_x));
        } else {
            TextView.setBackground(getResources().getDrawable(R.drawable.battle_cell_widmo_ship_x));
        }
    }

    private void checkLeftShips() {
        leftFourMasts=1-(fourMastsShipBoolean() ? 1 : 0);
        leftThreeMasts=2-((threeMastsShip1Boolean()?1:0)+(threeMastsShip2Boolean()?1:0));
        leftTwoMasts=3-((twoMastsShip1Boolean()?1:0)+(twoMastsShip2Boolean()?1:0)+(twoMastsShip3Boolean()?1:0));
        leftOneMasts=4-((oneMastsShip1Boolean()?1:0)+(oneMastsShip2Boolean()?1:0)+(oneMastsShip3Boolean()?1:0)+(oneMastsShip4Boolean()?1:0));
    }

    private void updateTextViewCounters() {
        FourMastsCounter.setText(String.valueOf(leftFourMasts));
        ThreeMastsCounter.setText(String.valueOf(leftThreeMasts));
        TwoMastsCounter.setText(String.valueOf(leftTwoMasts));
        OneMastsCounter.setText(String.valueOf(leftOneMasts));

    }

    private void checkShipsOnBattleField(BattleField battleFieldPlayerCreateBattleFieldActivity) {
        fourMastsCounter=0;
        threeMastsCounter1=0;
        threeMastsCounter2=0;
        twoMastsCounter1=0;
        twoMastsCounter2=0;
        twoMastsCounter3=0;
        oneMastsCounter1=0;
        oneMastsCounter2=0;
        oneMastsCounter3=0;
        oneMastsCounter4=0;
        for(int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                if(battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].isShip()){
                    if(battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].getNumberOfMasts()==4){
                        fourMastsCounter++;
                    }
                    else if(battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].getNumberOfMasts()==3&&
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].getShipNumber()==1){
                        threeMastsCounter1++;
                    }
                    else if(battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].getNumberOfMasts()==3&&
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].getShipNumber()==2){
                        threeMastsCounter2++;
                    }
                    else if(battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].getNumberOfMasts()==2&&
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].getShipNumber()==1){
                        twoMastsCounter1++;
                    }
                    else if(battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].getNumberOfMasts()==2&&
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].getShipNumber()==2){
                        twoMastsCounter2++;
                    }
                    else if(battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].getNumberOfMasts()==2&&
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].getShipNumber()==3){
                        twoMastsCounter3++;
                    }
                    else if(battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].getNumberOfMasts()==1&&
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].getShipNumber()==1){
                        oneMastsCounter1++;
                    }
                    else if(battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].getNumberOfMasts()==1&&
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].getShipNumber()==2){
                        oneMastsCounter2++;
                    }
                    else if(battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].getNumberOfMasts()==1&&
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].getShipNumber()==3){
                        oneMastsCounter3++;
                    }
                    else if(battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].getNumberOfMasts()==1&&
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].getShipNumber()==4){
                        oneMastsCounter4++;
                    }else;
                }else;
            }
        }
    }








    public void onClickBack(View view) {
        finish();
    }

    public void onClickStartGame(View view) {
        if(multiplayerMode){
            databaseReferenceFight.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        mHandler.removeCallbacks(checkGameIndex);
                        databaseReferenceFight.child(userID).setValue(battleFieldForDataBase);
                        user.setNoOfGames(noOfGames);
                        databaseReferenceMy.setValue(user);
                        Intent intent = new Intent(getApplicationContext(), MultiplayerActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        mHandler.removeCallbacks(checkGameIndex);
                        finish();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }else {
            BattleFieldPlayerOneSingleton.getInstance().storeBattleField(battleFieldPlayerCreateBattleFieldActivity);
            Intent intent = new Intent(getApplicationContext(), GameBattle.class);
            startActivity(intent);
            finish();
        }
    }

    public void onClickputOneMastsShip(View view) {
        deleteUncomplitedShips();
        choosenShip=1;
        checkShipsOnBattleField(battleFieldPlayerCreateBattleFieldActivity);
        if(!oneMastsShip1Boolean()){
            shipNumberFlag=1;
        }
        else{
            if(!oneMastsShip2Boolean()){
                shipNumberFlag=2;
            }
            else{
                if(!oneMastsShip3Boolean()){
                    shipNumberFlag=3;
                }else{
                    if(!oneMastsShip4Boolean()){
                        shipNumberFlag=4;
                    }
                }
            }
        }
        updateActiveTextViewFourMastsShipCounter(choosenShip);
    }

    public void onClickputTwoMastsShip(View view) {
        deleteUncomplitedShips();
        choosenShip=2;
        checkShipsOnBattleField(battleFieldPlayerCreateBattleFieldActivity);
        if(!twoMastsShip1Boolean()){
            shipNumberFlag=1;
        }
        else{
            if(!twoMastsShip2Boolean()){
                shipNumberFlag=2;
            }else{
                if(!twoMastsShip3Boolean()){
                    shipNumberFlag=3;
                }
            }
        }
        updateActiveTextViewFourMastsShipCounter(choosenShip);
    }

    public void onClickputThreeMastsShip(View view) {
        deleteUncomplitedShips();
        choosenShip=3;
        checkShipsOnBattleField(battleFieldPlayerCreateBattleFieldActivity);
        if(!threeMastsShip1Boolean()){
            shipNumberFlag=1;

        }else{
            if(!threeMastsShip2Boolean()){
                shipNumberFlag=2;
            }
            else;
        }
        updateActiveTextViewFourMastsShipCounter(choosenShip);
    }

    public void onClickputFourMastsShip(View view) {
        deleteUncomplitedShips();
        choosenShip=4;
        shipNumberFlag=1;
        updateActiveTextViewFourMastsShipCounter(choosenShip);
    }

    private void updateActiveTextViewFourMastsShipCounter(int choosen) {
        if( choosen==4){
            activeFourMasts();
        } else if (choosen==3){
            activeThreeMasts();
        }else if (choosen==2){
            activeTwoMasts();
        }else if (choosen==1){
            activeOneMasts();
        }else if(choosen==5){
            activeNone();
        }
    }

    private void activeNone() {
        if(leftOneMasts!=0){
            setBlankOneMasts();
        }
        if(leftFourMasts!=0){
            setBlankFourMasts();
        }else;
        if(leftThreeMasts!=0){
            setBlankThreeMasts();
        }else;
        if(leftTwoMasts!=0){
            setBlankTwoMasts();
        }else;
    }

    private void activeOneMasts() {
        setRedOneMasts();
        if(leftFourMasts!=0){
            setBlankFourMasts();
        }else;
        if(leftThreeMasts!=0){
            setBlankThreeMasts();
        }else;
        if(leftTwoMasts!=0){
            setBlankTwoMasts();
        }else;
    }

    private void setRedOneMasts() {
        setRed((TextView) linearLayoutOneMasts.getChildAt(0));
    }

    private void activeTwoMasts() {
        setRedTwoMasts();
        if(leftFourMasts!=0){
            setBlankFourMasts();
        }else;
        if(leftThreeMasts!=0){
            setBlankThreeMasts();
        }else;
        if(leftOneMasts!=0){
            setBlankOneMasts();
        }
    }

    private void setRedTwoMasts() {
        setRed((TextView) linearLayoutTwoMasts.getChildAt(0));
        setRed((TextView) linearLayoutTwoMasts.getChildAt(1));
    }

    private void activeThreeMasts() {
        setRedThreeMasts();
        if(leftFourMasts!=0){
            setBlankFourMasts();
        }else;
        if (leftTwoMasts!=0){
            setBlankTwoMasts();
        }else;
        if (leftOneMasts!=0){
            setBlankOneMasts();
        }else;
    }

    private void setBlankFourMasts() {
        setBlank((TextView) linearLayoutFourMasts.getChildAt(0));
        setBlank((TextView) linearLayoutFourMasts.getChildAt(1));
        setBlank((TextView) linearLayoutFourMasts.getChildAt(2));
        setBlank((TextView) linearLayoutFourMasts.getChildAt(3));
    }

    private void setRedThreeMasts() {
        setRed((TextView) linearLayoutThreeMasts.getChildAt(0));
        setRed((TextView) linearLayoutThreeMasts.getChildAt(1));
        setRed((TextView) linearLayoutThreeMasts.getChildAt(2));
    }

    private void activeFourMasts() {
        setRedFourMasts();
        if(leftThreeMasts!=0){
            setBlankThreeMasts();
        }else;
        if(leftTwoMasts!=0){
            setBlankTwoMasts();
        }else;
        if(leftOneMasts!=0){
            setBlankOneMasts();
        }else;
    }

    private void setBlankOneMasts() {
        setBlank((TextView) linearLayoutOneMasts.getChildAt(0));
    }

    private void setBlankTwoMasts() {
        setBlank((TextView) linearLayoutTwoMasts.getChildAt(0));
        setBlank((TextView) linearLayoutTwoMasts.getChildAt(1));
    }

    private void setBlankThreeMasts() {
        setBlank((TextView) linearLayoutThreeMasts.getChildAt(0));
        setBlank((TextView) linearLayoutThreeMasts.getChildAt(1));
        setBlank((TextView) linearLayoutThreeMasts.getChildAt(2));
    }

    private void setRedFourMasts() {
        setRed((TextView) linearLayoutFourMasts.getChildAt(0));
        setRed((TextView) linearLayoutFourMasts.getChildAt(1));
        setRed((TextView) linearLayoutFourMasts.getChildAt(2));
        setRed((TextView) linearLayoutFourMasts.getChildAt(3));
    }

    public void putShip(int i, int j, int numberOfMasts){
        if(numberOfMasts==1||numberOfMasts==2||numberOfMasts==3||numberOfMasts==4){
            if(numberOfMasts==4){
                checkShipsOnBattleField(battleFieldPlayerCreateBattleFieldActivity);
                if(fourMastsCounter==0){
                    battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setNumberOfMasts(numberOfMasts);
                    battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShipNumber(1);
                    battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShip(true);
                    firstPointMastsShip.setI(i);
                    firstPointMastsShip.setJ(j);
                    checkShipsOnBattleField(battleFieldPlayerCreateBattleFieldActivity);
                    setShipColor((TextView) gridLayout.getChildAt(10*i+j));
                    gridLayout.getChildAt(10*i+j).setClickable(false);
                    //                    updateBattleField();
                }else if(fourMastsCounter==1){
                    if(conditionIJ(i,j,numberOfMasts)) {
                        battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setNumberOfMasts(numberOfMasts);
                        battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShipNumber(1);
                        battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShip(true);
                        secondPointMastsShip.setI(i);
                        secondPointMastsShip.setJ(j);
                        checkShipsOnBattleField(battleFieldPlayerCreateBattleFieldActivity);
                        setShipColor((TextView) gridLayout.getChildAt(10*i+j));
                        gridLayout.getChildAt(10*i+j).setClickable(false);
//                        updateBattleField();
                    }
                }
                else if (fourMastsCounter==2){
                    if(firstPointMastsShip.getI()== secondPointMastsShip.getI()){
                        if(conditionI(i,j,numberOfMasts)) {
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setNumberOfMasts(numberOfMasts);
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShipNumber(1);
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShip(true);
                            checkShipsOnBattleField(battleFieldPlayerCreateBattleFieldActivity);
                            setShipColor((TextView) gridLayout.getChildAt(10*i+j));
                            gridLayout.getChildAt(10*i+j).setClickable(false);
                            //                            updateBattleField();
                        }
                    }else if(firstPointMastsShip.getJ()== secondPointMastsShip.getJ()){
                        if(conditionJ(i,j,numberOfMasts)) {
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setNumberOfMasts(numberOfMasts);
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShipNumber(1);
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShip(true);
                            checkShipsOnBattleField(battleFieldPlayerCreateBattleFieldActivity);
                            setShipColor((TextView) gridLayout.getChildAt(10*i+j));
                            gridLayout.getChildAt(10*i+j).setClickable(false);
//                            updateBattleField();

                        }
                    }
                }
                else if(fourMastsCounter==3){
                    if(firstPointMastsShip.getI()== secondPointMastsShip.getI()){
                        if(conditionI(i,j,numberOfMasts)) {
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setNumberOfMasts(numberOfMasts);
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShipNumber(1);
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShip(true);
                            updateScreen();
                            updateActiveTextViewFourMastsShipCounter(5);
                            choosenShip=0;
  //                          updateClicableBattleField();

                        }
                    }else if(firstPointMastsShip.getJ()== secondPointMastsShip.getJ()){
                        if(conditionJ(i,j,numberOfMasts)) {
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setNumberOfMasts(numberOfMasts);
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShipNumber(1);
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShip(true);
                            updateScreen();
                            updateActiveTextViewFourMastsShipCounter(5);
                            choosenShip=0;
 //                           updateClicableBattleField();
                        }
                    }

                }
                else;
            }
            else if(numberOfMasts==3){
                if (shipNumberFlag == 1) {
                    if(threeMastsCounter1==0){
                        battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setNumberOfMasts(numberOfMasts);
                        battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShipNumber(shipNumberFlag);
                        battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShip(true);
                        firstPointMastsShip.setI(i);
                        firstPointMastsShip.setJ(j);
                        checkShipsOnBattleField(battleFieldPlayerCreateBattleFieldActivity);
                        setShipColor((TextView) gridLayout.getChildAt(10*i+j));
                        gridLayout.getChildAt(10*i+j).setClickable(false);
//                        updateBattleField();
                    }
                    else if(threeMastsCounter1==1){
                        if(conditionIJ(i,j,numberOfMasts)) {
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setNumberOfMasts(numberOfMasts);
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShipNumber(shipNumberFlag);
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShip(true);
                            secondPointMastsShip.setI(i);
                            secondPointMastsShip.setJ(j);
                            checkShipsOnBattleField(battleFieldPlayerCreateBattleFieldActivity);
                            setShipColor((TextView) gridLayout.getChildAt(10*i+j));
                            gridLayout.getChildAt(10*i+j).setClickable(false);
//                            updateBattleField();
                        }
                    }else{
                        if(firstPointMastsShip.getI()== secondPointMastsShip.getI()){
                            if(conditionI(i,j,numberOfMasts)) {
                                battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setNumberOfMasts(numberOfMasts);
                                battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShipNumber(shipNumberFlag);
                                battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShip(true);
                                updateScreen();
                                updateActiveTextViewFourMastsShipCounter(5);
                                choosenShip=0;
   //                             updateClicableBattleField();
                            }
                        }else if(firstPointMastsShip.getJ()== secondPointMastsShip.getJ()){
                            if(conditionJ(i,j,numberOfMasts)) {
                                battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setNumberOfMasts(numberOfMasts);
                                battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShipNumber(shipNumberFlag);
                                battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShip(true);
                                updateScreen();
                                updateActiveTextViewFourMastsShipCounter(5);
                                choosenShip=0;
   //                             updateClicableBattleField();
                            }
                        }

                    }
                }else if(shipNumberFlag==2) {
                    if (threeMastsCounter2 == 0) {
                        battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setNumberOfMasts(numberOfMasts);
                        battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShipNumber(shipNumberFlag);
                        battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShip(true);
                        firstPointMastsShip.setI(i);
                        firstPointMastsShip.setJ(j);
                        checkShipsOnBattleField(battleFieldPlayerCreateBattleFieldActivity);
                        setShipColor((TextView) gridLayout.getChildAt(10*i+j));
                        gridLayout.getChildAt(10*i+j).setClickable(false);
                        //                        updateBattleField();
                    } else if (threeMastsCounter2 == 1) {
                        if (conditionIJ(i, j, numberOfMasts)) {
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setNumberOfMasts(numberOfMasts);
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShipNumber(shipNumberFlag);
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShip(true);
                            secondPointMastsShip.setI(i);
                            secondPointMastsShip.setJ(j);
                            checkShipsOnBattleField(battleFieldPlayerCreateBattleFieldActivity);
                            setShipColor((TextView) gridLayout.getChildAt(10*i+j));
                            gridLayout.getChildAt(10*i+j).setClickable(false);
 //                           updateBattleField();
                        }
                    } else {
                        if (firstPointMastsShip.getI() == secondPointMastsShip.getI()) {
                            if (conditionI(i, j, numberOfMasts)) {
                                battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setNumberOfMasts(numberOfMasts);
                                battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShipNumber(shipNumberFlag);
                                battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShip(true);
                                updateScreen();
                                updateActiveTextViewFourMastsShipCounter(5);
                                choosenShip=0;
    //                            updateClicableBattleField();
                            }
                        } else if (firstPointMastsShip.getJ() == secondPointMastsShip.getJ()) {
                            if (conditionJ(i, j, numberOfMasts)) {
                                battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setNumberOfMasts(numberOfMasts);
                                battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShipNumber(shipNumberFlag);
                                battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShip(true);
                                updateScreen();
                                updateActiveTextViewFourMastsShipCounter(5);
                                choosenShip=0;
      //                          updateClicableBattleField();
                            }
                        }
                    }
                }
            }
            else if(numberOfMasts==2){
                if(shipNumberFlag==1){

                    if(twoMastsCounter1==0){
                        battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setNumberOfMasts(numberOfMasts);
                        battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShipNumber(shipNumberFlag);
                        battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShip(true);
                        checkShipsOnBattleField(battleFieldPlayerCreateBattleFieldActivity);
                        setShipColor((TextView) gridLayout.getChildAt(10*i+j));
                        gridLayout.getChildAt(10*i+j).setClickable(false);
 //                       updateBattleField();
                    }
                    else {
                        if (conditionIJ(i, j, numberOfMasts)) {
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setNumberOfMasts(numberOfMasts);
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShipNumber(shipNumberFlag);
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShip(true);
                            updateScreen();
                            updateActiveTextViewFourMastsShipCounter(5);
                            choosenShip=0;
//                            updateClicableBattleField();
                        }
                    }

                }else if(shipNumberFlag==2){

                    if(twoMastsCounter2==0){
                        battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setNumberOfMasts(numberOfMasts);
                        battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShipNumber(shipNumberFlag);
                        battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShip(true);
                        checkShipsOnBattleField(battleFieldPlayerCreateBattleFieldActivity);
                        setShipColor((TextView) gridLayout.getChildAt(10*i+j));
                        gridLayout.getChildAt(10*i+j).setClickable(false);
 //                       updateBattleField();
                    }
                    else {
                        if (conditionIJ(i, j, numberOfMasts)) {
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setNumberOfMasts(numberOfMasts);
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShipNumber(shipNumberFlag);
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShip(true);
                            updateScreen();
                            updateActiveTextViewFourMastsShipCounter(5);
                            choosenShip=0;
 //                           updateClicableBattleField();
                        }
                    }

                }else if(shipNumberFlag==3){

                    if(twoMastsCounter3==0){
                        battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setNumberOfMasts(numberOfMasts);
                        battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShipNumber(shipNumberFlag);
                        battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShip(true);
                        checkShipsOnBattleField(battleFieldPlayerCreateBattleFieldActivity);
                        setShipColor((TextView) gridLayout.getChildAt(10*i+j));
                        gridLayout.getChildAt(10*i+j).setClickable(false);
 //                       updateBattleField();
                    }
                    else {
                        if (conditionIJ(i, j, numberOfMasts)) {
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setNumberOfMasts(numberOfMasts);
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShipNumber(shipNumberFlag);
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShip(true);
                            updateScreen();
                            updateActiveTextViewFourMastsShipCounter(5);
                            choosenShip=0;
 //                           updateClicableBattleField();
                        }
                    }

                }else;
            }
            else if(numberOfMasts==1){

                battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setNumberOfMasts(numberOfMasts);
                battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShipNumber(shipNumberFlag);
                battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShip(true);
                updateScreen();
                updateActiveTextViewFourMastsShipCounter(5);
                choosenShip=0;
 //               updateClicableBattleField();
            }
            else;
        }else if(numberOfMasts==5){
            makeFieldZero(i,j);
            deleteUncomplitedShips();
        }
    }

    private void updateClicableBattleField() {
        for(int i=0;i<10;i++){
            for(int j=0;j<10;j++){

                if(i==0&&j==0){
                    if(battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].isShip()||
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i+1][j].isShip()||
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j+1].isShip()||
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i+1][j+1].isShip()){
                        gridLayout.getChildAt(10*i+j).setClickable(false);
                        if(battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].isShip()){
                        setShipColor((TextView) gridLayout.getChildAt(10*i+j));
                        }else{
                            setGreyColor((TextView) gridLayout.getChildAt(10*i+j));
                        }
                    } else{
                        setNoShipColor((TextView) gridLayout.getChildAt(10*i+j));
                        gridLayout.getChildAt(10*i+j).setClickable(true);

                    }
                }
                else if((i > 0) && (i < 9) && j == 0){
                    if(battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].isShip()||
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i-1][j].isShip()||
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i+1][j].isShip()||
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j+1].isShip()||
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i-1][j+1].isShip()||
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i+1][j+1].isShip()){
                        gridLayout.getChildAt(10*i+j).setClickable(false);
                        if(battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].isShip()){
                            setShipColor((TextView) gridLayout.getChildAt(10*i+j));
                        }else{
                            setGreyColor((TextView) gridLayout.getChildAt(10*i+j));
                        }
                    }else{
                        setNoShipColor((TextView) gridLayout.getChildAt(10*i+j));
                        gridLayout.getChildAt(10*i+j).setClickable(true);
                    }
                }
                else if(i==9&&j==0){
                    if(battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].isShip()||
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i-1][j].isShip()||
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j+1].isShip()||
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i-1][j+1].isShip()){
                        gridLayout.getChildAt(10*i+j).setClickable(false);
                        if(battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].isShip()){
                            setShipColor((TextView) gridLayout.getChildAt(10*i+j));
                        }else{
                            setGreyColor((TextView) gridLayout.getChildAt(10*i+j));
                        }
                    }else{
                        setNoShipColor((TextView) gridLayout.getChildAt(10*i+j));
                        gridLayout.getChildAt(10*i+j).setClickable(true);
                    }
                }
                else if (i == 0 && ((j > 0) && (j < 9))){
                    if(battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].isShip()||
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i+1][j].isShip()||
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j+1].isShip()||
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i+1][j+1].isShip()||
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j-1].isShip()||
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i+1][j-1].isShip()){
                        gridLayout.getChildAt(10*i+j).setClickable(false);
                        if(battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].isShip()){
                            setShipColor((TextView) gridLayout.getChildAt(10*i+j));
                        }else{
                            setGreyColor((TextView) gridLayout.getChildAt(10*i+j));
                        }
                    }else{
                        setNoShipColor((TextView) gridLayout.getChildAt(10*i+j));
                        gridLayout.getChildAt(10*i+j).setClickable(true);
                    }
                }
                else if (((i > 0) && (i < 9)) && ((j > 0) && (j < 9))){
                    if(battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].isShip()||
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i-1][j].isShip()||
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i+1][j].isShip()||
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j+1].isShip()||
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i-1][j+1].isShip()||
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i+1][j+1].isShip()||
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j-1].isShip()||
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i-1][j-1].isShip()||
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i+1][j-1].isShip()){
                        gridLayout.getChildAt(10*i+j).setClickable(false);
                        if(battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].isShip()){
                            setShipColor((TextView) gridLayout.getChildAt(10*i+j));
                        }else{
                            setGreyColor((TextView) gridLayout.getChildAt(10*i+j));
                        }
                    }else{
                        setNoShipColor((TextView) gridLayout.getChildAt(10*i+j));
                        gridLayout.getChildAt(10*i+j).setClickable(true);
                    }
                }
                else if (i == 9 && ((j > 0) && (j < 9))) {
                    if(battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].isShip()||
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i-1][j].isShip()||
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j+1].isShip()||
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i-1][j+1].isShip()||
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j-1].isShip()||
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i-1][j-1].isShip()){
                        gridLayout.getChildAt(10*i+j).setClickable(false);
                        if(battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].isShip()){
                            setShipColor((TextView) gridLayout.getChildAt(10*i+j));
                        }else{
                            setGreyColor((TextView) gridLayout.getChildAt(10*i+j));
                        }
                    }else{
                        setNoShipColor((TextView) gridLayout.getChildAt(10*i+j));
                        gridLayout.getChildAt(10*i+j).setClickable(true);
                    }
                }
                else if (i == 0 && j == 9) {
                    if(battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].isShip()||
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i+1][j].isShip()||
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j-1].isShip()||
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i+1][j-1].isShip()){
                        gridLayout.getChildAt(10*i+j).setClickable(false);
                        if(battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].isShip()){
                            setShipColor((TextView) gridLayout.getChildAt(10*i+j));
                        }else{
                            setGreyColor((TextView) gridLayout.getChildAt(10*i+j));
                        }
                    }else{
                        setNoShipColor((TextView) gridLayout.getChildAt(10*i+j));
                        gridLayout.getChildAt(10*i+j).setClickable(true);
                    }
                }
                else if (((i > 0) && (i < 9)) && (j == 9)) {
                    if(battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].isShip()||
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i-1][j].isShip()||
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i+1][j].isShip()||
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j-1].isShip()||
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i-1][j-1].isShip()||
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i+1][j-1].isShip()){
                        gridLayout.getChildAt(10*i+j).setClickable(false);
                        if(battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].isShip()){
                            setShipColor((TextView) gridLayout.getChildAt(10*i+j));
                        }else{
                            setGreyColor((TextView) gridLayout.getChildAt(10*i+j));
                        }
                    }else{
                        setNoShipColor((TextView) gridLayout.getChildAt(10*i+j));
                        gridLayout.getChildAt(10*i+j).setClickable(true);
                    }
                }
                else if ((i == 9) && (j == 9)){
                    if(battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].isShip()||
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i-1][j].isShip()||
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j-1].isShip()||
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i-1][j-1].isShip()){
                        gridLayout.getChildAt(10*i+j).setClickable(false);
                        if(battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].isShip()){
                            setShipColor((TextView) gridLayout.getChildAt(10*i+j));
                        }else{
                            setGreyColor((TextView) gridLayout.getChildAt(10*i+j));
                        }
                    }else{
                        setNoShipColor((TextView) gridLayout.getChildAt(10*i+j));
                        gridLayout.getChildAt(10*i+j).setClickable(true);
                    }
                }
                else;
            }
        }
    }

    public boolean conditionIJ (int i, int j, int numberOfMasts) {
        if(i==0&&j==0){
            return battleFieldPlayerCreateBattleFieldActivity.battleField[i+1][j].getNumberOfMasts()==numberOfMasts||
                    battleFieldPlayerCreateBattleFieldActivity.battleField[i][j+1].getNumberOfMasts()==numberOfMasts;
        }
        else if((i > 0) && (i < 9) && j == 0){
           return battleFieldPlayerCreateBattleFieldActivity.battleField[i-1][j].getNumberOfMasts()==numberOfMasts||
                    battleFieldPlayerCreateBattleFieldActivity.battleField[i+1][j].getNumberOfMasts()==numberOfMasts||
                    battleFieldPlayerCreateBattleFieldActivity.battleField[i][j+1].getNumberOfMasts()==numberOfMasts;
        }
        else if(i==9&&j==0){
          return  battleFieldPlayerCreateBattleFieldActivity.battleField[i-1][j].getNumberOfMasts()==numberOfMasts||
                    battleFieldPlayerCreateBattleFieldActivity.battleField[i][j+1].getNumberOfMasts()==numberOfMasts;
        } else if (i == 0 && ((j > 0) && (j < 9))){
            return battleFieldPlayerCreateBattleFieldActivity.battleField[i+1][j].getNumberOfMasts()==numberOfMasts||
                    battleFieldPlayerCreateBattleFieldActivity.battleField[i][j-1].getNumberOfMasts()==numberOfMasts||
                    battleFieldPlayerCreateBattleFieldActivity.battleField[i][j+1].getNumberOfMasts()==numberOfMasts;
        }
         else if (((i > 0) && (i < 9)) && ((j > 0) && (j < 9))){
             return battleFieldPlayerCreateBattleFieldActivity.battleField[i-1][j].getNumberOfMasts()==numberOfMasts||
                     battleFieldPlayerCreateBattleFieldActivity.battleField[i+1][j].getNumberOfMasts()==numberOfMasts||
                     battleFieldPlayerCreateBattleFieldActivity.battleField[i][j-1].getNumberOfMasts()==numberOfMasts||
                     battleFieldPlayerCreateBattleFieldActivity.battleField[i][j+1].getNumberOfMasts()==numberOfMasts;
        }
         else if (i == 9 && ((j > 0) && (j < 9))) {
            return battleFieldPlayerCreateBattleFieldActivity.battleField[i-1][j].getNumberOfMasts()==numberOfMasts||
                    battleFieldPlayerCreateBattleFieldActivity.battleField[i][j-1].getNumberOfMasts()==numberOfMasts||
                    battleFieldPlayerCreateBattleFieldActivity.battleField[i][j+1].getNumberOfMasts()==numberOfMasts;
        }
         else if (i == 0 && j == 9) {
            return battleFieldPlayerCreateBattleFieldActivity.battleField[i+1][j].getNumberOfMasts()==numberOfMasts||
                    battleFieldPlayerCreateBattleFieldActivity.battleField[i][j-1].getNumberOfMasts()==numberOfMasts;
        }
         else if (((i > 0) && (i < 9)) && (j == 9)) {
            return battleFieldPlayerCreateBattleFieldActivity.battleField[i-1][j].getNumberOfMasts()==numberOfMasts||
                    battleFieldPlayerCreateBattleFieldActivity.battleField[i+1][j].getNumberOfMasts()==numberOfMasts||
                    battleFieldPlayerCreateBattleFieldActivity.battleField[i][j-1].getNumberOfMasts()==numberOfMasts;
        }
         else if ((i == 9) && (j == 9)){
             return battleFieldPlayerCreateBattleFieldActivity.battleField[i-1][j].getNumberOfMasts()==numberOfMasts||
                     battleFieldPlayerCreateBattleFieldActivity.battleField[i][j-1].getNumberOfMasts()==numberOfMasts;
        }

        return false;
    }

    public boolean conditionI (int i, int j, int numberOfMasts) {
        if(j==0){
            return battleFieldPlayerCreateBattleFieldActivity.battleField[i][j+1].getNumberOfMasts()==numberOfMasts;
        }
        else if ((j > 0) && (j < 9)){
            return battleFieldPlayerCreateBattleFieldActivity.battleField[i][j-1].getNumberOfMasts()==numberOfMasts||
                    battleFieldPlayerCreateBattleFieldActivity.battleField[i][j+1].getNumberOfMasts()==numberOfMasts;
        }
        else if (j == 9) {
            return battleFieldPlayerCreateBattleFieldActivity.battleField[i][j-1].getNumberOfMasts()==numberOfMasts;
        }
        return false;
    }

    public boolean conditionJ (int i, int j, int numberOfMasts) {
        if(i==0){
            return battleFieldPlayerCreateBattleFieldActivity.battleField[i+1][j].getNumberOfMasts()==numberOfMasts;
        }
        else if(i > 0 && i < 9){
            return battleFieldPlayerCreateBattleFieldActivity.battleField[i-1][j].getNumberOfMasts()==numberOfMasts||
                    battleFieldPlayerCreateBattleFieldActivity.battleField[i+1][j].getNumberOfMasts()==numberOfMasts;
        }
        else if(i==9){
            return  battleFieldPlayerCreateBattleFieldActivity.battleField[i-1][j].getNumberOfMasts()==numberOfMasts;
        }
        return false;
    }

    public void clickCreateBattleField_1x1(View view) {
        putShip(0,0,choosenShip);
    }

    public void clickCreateBattleField_1x2(View view) {
        putShip(0,1,choosenShip);
    }

    public void clickCreateBattleField_1x3(View view) {
        putShip(0,2,choosenShip);
    }

    public void clickCreateBattleField_1x4(View view) {
        putShip(0,3,choosenShip);
    }

    public void clickCreateBattleField_1x5(View view) {
        putShip(0,4,choosenShip);
    }

    public void clickCreateBattleField_1x6(View view) {
        putShip(0,5,choosenShip);
    }

    public void clickCreateBattleField_1x7(View view) {
        putShip(0,6,choosenShip);
    }

    public void clickCreateBattleField_1x8(View view) {
        putShip(0,7,choosenShip);
    }

    public void clickCreateBattleField_1x9(View view) {
        putShip(0,8,choosenShip);
    }

    public void clickCreateBattleField_1x10(View view) {
        putShip(0,9,choosenShip);
    }

    public void clickCreateBattleField_2x1(View view) {
        putShip(1,0,choosenShip);
    }

    public void clickCreateBattleField_2x2(View view) {
        putShip(1,1,choosenShip);
    }

    public void clickCreateBattleField_2x3(View view) {
        putShip(1,2,choosenShip);
    }

    public void clickCreateBattleField_2x4(View view) {
        putShip(1,3,choosenShip);
    }

    public void clickCreateBattleField_2x5(View view) {
        putShip(1,4,choosenShip);
    }

    public void clickCreateBattleField_2x6(View view) {
        putShip(1,5,choosenShip);
    }

    public void clickCreateBattleField_2x7(View view) {
        putShip(1,6,choosenShip);
    }

    public void clickCreateBattleField_2x8(View view) {
        putShip(1,7,choosenShip);
    }

    public void clickCreateBattleField_2x9(View view) {
        putShip(1,8,choosenShip);
    }

    public void clickCreateBattleField_2x10(View view) {
        putShip(1,9,choosenShip);
    }

    public void clickCreateBattleField_3x1(View view) {
        putShip(2,0,choosenShip);
    }

    public void clickCreateBattleField_3x2(View view) {
        putShip(2,1,choosenShip);
    }

    public void clickCreateBattleField_3x3(View view) {
        putShip(2,2,choosenShip);
    }

    public void clickCreateBattleField_3x4(View view) {
        putShip(2,3,choosenShip);
    }

    public void clickCreateBattleField_3x5(View view) {
        putShip(2,4,choosenShip);
    }

    public void clickCreateBattleField_3x6(View view) {
        putShip(2,5,choosenShip);
    }

    public void clickCreateBattleField_3x7(View view) {
        putShip(2,6,choosenShip);
    }

    public void clickCreateBattleField_3x8(View view) {
        putShip(2,7,choosenShip);
    }

    public void clickCreateBattleField_3x9(View view) {
        putShip(2,8,choosenShip);
    }

    public void clickCreateBattleField_3x10(View view) {
        putShip(2,9,choosenShip);
    }

    public void clickCreateBattleField_4x1(View view) {
        putShip(3,0,choosenShip);
    }

    public void clickCreateBattleField_4x2(View view) {
        putShip(3,1,choosenShip);
    }

    public void clickCreateBattleField_4x3(View view) {
        putShip(3,2,choosenShip);
    }

    public void clickCreateBattleField_4x4(View view) {
        putShip(3,3,choosenShip);
    }

    public void clickCreateBattleField_4x5(View view) {
        putShip(3,4,choosenShip);
    }

    public void clickCreateBattleField_4x6(View view) {
        putShip(3,5,choosenShip);
    }

    public void clickCreateBattleField_4x7(View view) {
        putShip(3,6,choosenShip);
    }

    public void clickCreateBattleField_4x8(View view) {
        putShip(3,7,choosenShip);
    }

    public void clickCreateBattleField_4x9(View view) {
        putShip(3,8,choosenShip);
    }

    public void clickCreateBattleField_4x10(View view) {
        putShip(3,9,choosenShip);
    }

    public void clickCreateBattleField_5x1(View view) {
        putShip(4,0,choosenShip);
    }

    public void clickCreateBattleField_5x2(View view) {
        putShip(4,1,choosenShip);
    }

    public void clickCreateBattleField_5x3(View view) {
        putShip(4,2,choosenShip);
    }

    public void clickCreateBattleField_5x4(View view) {
        putShip(4,3,choosenShip);
    }

    public void clickCreateBattleField_5x5(View view) {
        putShip(4,4,choosenShip);
    }

    public void clickCreateBattleField_5x6(View view) {
        putShip(4,5,choosenShip);
    }

    public void clickCreateBattleField_5x7(View view) {
        putShip(4,6,choosenShip);
    }

    public void clickCreateBattleField_5x8(View view) {
        putShip(4,7,choosenShip);
    }

    public void clickCreateBattleField_5x9(View view) {
        putShip(4,8,choosenShip);
    }

    public void clickCreateBattleField_5x10(View view) {
        putShip(4,9,choosenShip);
    }

    public void clickCreateBattleField_6x1(View view) {
        putShip(5,0,choosenShip);
    }

    public void clickCreateBattleField_6x2(View view) {
        putShip(5,1,choosenShip);
    }

    public void clickCreateBattleField_6x3(View view) {
        putShip(5,2,choosenShip);
    }

    public void clickCreateBattleField_6x4(View view) {
        putShip(5,3,choosenShip);
    }

    public void clickCreateBattleField_6x5(View view) {
        putShip(5,4,choosenShip);
    }

    public void clickCreateBattleField_6x6(View view) {
        putShip(5,5,choosenShip);
    }

    public void clickCreateBattleField_6x7(View view) {
        putShip(5,6,choosenShip);
    }

    public void clickCreateBattleField_6x8(View view) {
        putShip(5,7,choosenShip);
    }

    public void clickCreateBattleField_6x9(View view) {
        putShip(5,8,choosenShip);
    }

    public void clickCreateBattleField_6x10(View view) {
        putShip(5,9,choosenShip);
    }

    public void clickCreateBattleField_7x1(View view) {
        putShip(6,0,choosenShip);
    }

    public void clickCreateBattleField_7x2(View view) {
        putShip(6,1,choosenShip);
    }

    public void clickCreateBattleField_7x3(View view) {
        putShip(6,2,choosenShip);
    }

    public void clickCreateBattleField_7x4(View view) {
        putShip(6,3,choosenShip);
    }

    public void clickCreateBattleField_7x5(View view) {
        putShip(6,4,choosenShip);
    }

    public void clickCreateBattleField_7x6(View view) {
        putShip(6,5,choosenShip);
    }

    public void clickCreateBattleField_7x7(View view) {
        putShip(6,6,choosenShip);
    }

    public void clickCreateBattleField_7x8(View view) {
        putShip(6,7,choosenShip);
    }

    public void clickCreateBattleField_7x9(View view) {
        putShip(6,8,choosenShip);
    }

    public void clickCreateBattleField_7x10(View view) {
        putShip(6,9,choosenShip);
    }

    public void clickCreateBattleField_8x1(View view) {
        putShip(7,0,choosenShip);
    }

    public void clickCreateBattleField_8x2(View view) {
        putShip(7,1,choosenShip);
    }

    public void clickCreateBattleField_8x3(View view) {
        putShip(7,2,choosenShip);
    }

    public void clickCreateBattleField_8x4(View view) {
        putShip(7,3,choosenShip);
    }

    public void clickCreateBattleField_8x5(View view) {
        putShip(7,4,choosenShip);
    }

    public void clickCreateBattleField_8x6(View view) {
        putShip(7,5,choosenShip);
    }

    public void clickCreateBattleField_8x7(View view) {
        putShip(7,6,choosenShip);
    }

    public void clickCreateBattleField_8x8(View view) {
        putShip(7,7,choosenShip);
    }

    public void clickCreateBattleField_8x9(View view) {
        putShip(7,8,choosenShip);
    }

    public void clickCreateBattleField_8x10(View view) {
        putShip(7,9,choosenShip);
    }

    public void clickCreateBattleField_9x1(View view) {
        putShip(8,0,choosenShip);
    }

    public void clickCreateBattleField_9x2(View view) {
        putShip(8,1,choosenShip);
    }

    public void clickCreateBattleField_9x3(View view) {
        putShip(8,2,choosenShip);
    }

    public void clickCreateBattleField_9x4(View view) {
        putShip(8,3,choosenShip);
    }

    public void clickCreateBattleField_9x5(View view) {
        putShip(8,4,choosenShip);
    }

    public void clickCreateBattleField_9x6(View view) {
        putShip(8,5,choosenShip);
    }

    public void clickCreateBattleField_9x7(View view) {
        putShip(8,6,choosenShip);
    }

    public void clickCreateBattleField_9x8(View view) {
        putShip(8,7,choosenShip);
    }

    public void clickCreateBattleField_9x9(View view) {
        putShip(8,8,choosenShip);
    }

    public void clickCreateBattleField_9x10(View view) {
        putShip(8,9,choosenShip);
    }

    public void clickCreateBattleField_10x1(View view) {
        putShip(9,0,choosenShip);
    }

    public void clickCreateBattleField_10x2(View view) {
        putShip(9,1,choosenShip);
    }

    public void clickCreateBattleField_10x3(View view) {
        putShip(9,2,choosenShip);
    }

    public void clickCreateBattleField_10x4(View view) {
        putShip(9,3,choosenShip);
    }

    public void clickCreateBattleField_10x5(View view) {
        putShip(9,4,choosenShip);
    }

    public void clickCreateBattleField_10x6(View view) {
        putShip(9,5,choosenShip);
    }

    public void clickCreateBattleField_10x7(View view) {
        putShip(9,6,choosenShip);
    }

    public void clickCreateBattleField_10x8(View view) {
        putShip(9,7,choosenShip);
    }

    public void clickCreateBattleField_10x9(View view) {
        putShip(9,8,choosenShip);
    }

    public void clickCreateBattleField_10x10(View view) {
        putShip(9,9,choosenShip);
    }

    private boolean fourMastsShipBoolean(){
        return fourMastsCounter==4;
    }

    private boolean threeMastsShip1Boolean(){
        return threeMastsCounter1==3;
    }

    private boolean threeMastsShip2Boolean(){
        return threeMastsCounter2==3;
    }

    private boolean twoMastsShip1Boolean(){
        return twoMastsCounter1==2;
    }

    private boolean twoMastsShip2Boolean(){
        return twoMastsCounter2==2;
    }

    private boolean twoMastsShip3Boolean(){
        return twoMastsCounter3==2;
    }

    private boolean oneMastsShip1Boolean(){
        return oneMastsCounter1==1;
    }

    private boolean oneMastsShip2Boolean(){
        return oneMastsCounter2==1;
    }

    private boolean oneMastsShip3Boolean(){
        return oneMastsCounter3==1;
    }

    private boolean oneMastsShip4Boolean(){
        return oneMastsCounter4==1;
    }

    public void onClickReset(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(CreateBattleField.this);
        builder.setCancelable(true);
        builder.setTitle("RESETING");
        builder.setMessage("Do you want to reset ships?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                for(int i=0;i<10;i++){
                    for(int j=0;j<10;j++){
                        battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setNumberOfMasts(0);
                        battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShipNumber(0);
                        battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShip(false);
                        gridLayout.getChildAt(10*i+j).setClickable(true);
                    }
                }
                choosenShip=0;
                updateScreen();


            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // do nothing
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    public void deleteUncomplitedShips(){
        checkShipsOnBattleField(battleFieldPlayerCreateBattleFieldActivity);
        if(fourMastsCounter>0&&fourMastsCounter<4){
            for(int i=0;i<10;i++){
                for(int j=0;j<10;j++){
                    if(battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].isShip()&&
                    battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].getNumberOfMasts()==4){
                        makeFieldZero(i,j);
                    }
                }
            }
        }else;
        if(threeMastsCounter1>0&&threeMastsCounter1<3){
            for(int i=0;i<10;i++){
                for(int j=0;j<10;j++){
                    if(battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].isShip()&&
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].getNumberOfMasts()==3&&
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].getShipNumber()==1){
                        makeFieldZero(i,j);
                    }
                }
            }
        }else;
        if(threeMastsCounter2>0&&threeMastsCounter2<3){
            for(int i=0;i<10;i++){
                for(int j=0;j<10;j++){
                    if(battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].isShip()&&
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].getNumberOfMasts()==3&&
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].getShipNumber()==2){
                        makeFieldZero(i,j);
                    }
                }
            }
        }else;
        if(twoMastsCounter1==1){
            for(int i=0;i<10;i++){
                for(int j=0;j<10;j++){
                    if(battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].isShip()&&
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].getNumberOfMasts()==2&&
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].getShipNumber()==1){
                        makeFieldZero(i,j);
                    }
                }
            }
        }else;
        if(twoMastsCounter2==1){
            for(int i=0;i<10;i++){
                for(int j=0;j<10;j++){
                    if(battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].isShip()&&
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].getNumberOfMasts()==2&&
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].getShipNumber()==2){
                        makeFieldZero(i,j);
                    }
                }
            }
        }else;
        if(twoMastsCounter3==1){
            for(int i=0;i<10;i++){
                for(int j=0;j<10;j++){
                    if(battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].isShip()&&
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].getNumberOfMasts()==2&&
                            battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].getShipNumber()==3){
                        makeFieldZero(i,j);
                    }
                }
            }
        }else;
       updateScreen();
    }

    private void makeFieldZero(int i, int j) {
        battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setNumberOfMasts(0);
        battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShipNumber(0);
        battleFieldPlayerCreateBattleFieldActivity.battleField[i][j].setShip(false);
    }

    public void onClickDelete(View view) {
        choosenShip=5;
        for(int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                gridLayout.getChildAt(10*i+j).setClickable(true);
            }
        }
    }
    private Runnable checkGameIndex = new Runnable() {
        @Override
        public void run() {
            databaseReferenceMy.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    user = dataSnapshot.getValue(User.class);
                    if(user.getIndex().getGameIndex().isEmpty()){
                        mHandler.removeCallbacks(checkGameIndex);
                        finish();
                    }else{
                        mHandler.postDelayed(checkGameIndex,deelay);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }
    };
}

// TODO set linearlayouts clickable not textviews