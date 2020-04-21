package com.example.ships;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Shader;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.example.ships.classes.RoundedCornerBitmap;
import com.example.ships.classes.TileDrawable;
import com.example.ships.classes.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class SettingsActivity extends AppCompatActivity {

    private int flags;
    private ImageButton leave;
    private ConstraintLayout mainLayout;
    private ImageView photo;
    private User user = new User();
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private TextView userNameTextView;
    private EditText newNameEditText;
    private Button saveName, chooseNewPhoto, uploadNewPhoto;
    private ImageView newPhoto;
    private Uri imguri, resultUri;
    private Bitmap bitmap, resizeBitmap;
    private int square;
    private CheckBox fbPhoto, fbName;



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
        userNameTextView=findViewById(R.id.userNameSettingsActivity);
        newNameEditText=findViewById(R.id.newUserNameSettingsActivity);
        saveName=findViewById(R.id.saveNewNameSettingsActivity);
        newPhoto=findViewById(R.id.new_photo_settings_activity);
        chooseNewPhoto=findViewById(R.id.chooseNewPhotoSettingsActivity);
        uploadNewPhoto=findViewById(R.id.uploadNewPhotoSettingsActivity);
        fbPhoto=findViewById(R.id.checkbox_fb_photo);
        fbName=findViewById(R.id.checkbox_fb_name);

        SharedPreferences sp = getSharedPreferences("VALUES", Activity.MODE_PRIVATE);
        square = sp.getInt("square",-1);
        int screenHeight = sp.getInt("width",-1);
        int screenWidth = sp.getInt("height",-1);
        int screenHeightOffSet = sp.getInt("widthOffSet",-1);
        int screenWidthOffSet = sp.getInt("heightOffSet",-1);

        mainLayout.setBackground(new TileDrawable(getDrawable(R.drawable.background_x), Shader.TileMode.REPEAT,square));

        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(2*square,2*square);
        ConstraintLayout.LayoutParams params1 = new ConstraintLayout.LayoutParams(4*square,4*square);
        ConstraintLayout.LayoutParams params2 = new ConstraintLayout.LayoutParams(11*square,2*square);
        ConstraintLayout.LayoutParams params3 = new ConstraintLayout.LayoutParams(11*square,2*square);
        ConstraintLayout.LayoutParams params4 = new ConstraintLayout.LayoutParams(4*square,2*square);
        ConstraintLayout.LayoutParams params5 = new ConstraintLayout.LayoutParams(4*square,4*square);
        ConstraintLayout.LayoutParams params6 = new ConstraintLayout.LayoutParams(8*square,2*square);
        ConstraintLayout.LayoutParams params7 = new ConstraintLayout.LayoutParams(8*square,2*square);
        ConstraintLayout.LayoutParams params8 = new ConstraintLayout.LayoutParams(16*square,3*square);
        ConstraintLayout.LayoutParams params9 = new ConstraintLayout.LayoutParams(16*square,3*square);

        leave.setLayoutParams(params);
        photo.setLayoutParams(params1);
        userNameTextView.setLayoutParams(params2);
        userNameTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,square);
        newNameEditText.setLayoutParams(params3);
        newNameEditText.setTextSize(TypedValue.COMPLEX_UNIT_PX,square);
        saveName.setLayoutParams(params4);
        saveName.setTextSize(TypedValue.COMPLEX_UNIT_PX,square);
        newPhoto.setLayoutParams(params5);
        newPhoto.setBackgroundResource(R.drawable.account_box_grey);
        chooseNewPhoto.setLayoutParams(params6);
        chooseNewPhoto.setTextSize(TypedValue.COMPLEX_UNIT_PX,square);
        uploadNewPhoto.setLayoutParams(params7);
        uploadNewPhoto.setTextSize(TypedValue.COMPLEX_UNIT_PX,square);
        uploadNewPhoto.setVisibility(View.GONE);
        fbPhoto.setLayoutParams(params8);
        fbPhoto.setTextSize(TypedValue.COMPLEX_UNIT_PX,square);
        fbName.setLayoutParams(params9);
        fbName.setTextSize(TypedValue.COMPLEX_UNIT_PX,square);

        ConstraintSet set = new ConstraintSet();
        set.clone(mainLayout);

        set.connect(leave.getId(),ConstraintSet.TOP,mainLayout.getId(),ConstraintSet.TOP,screenHeight-screenHeightOffSet-3*square);
        set.connect(leave.getId(),ConstraintSet.LEFT,mainLayout.getId(),ConstraintSet.LEFT,square);

        set.connect(photo.getId(),ConstraintSet.TOP,mainLayout.getId(),ConstraintSet.TOP,square);
        set.connect(photo.getId(),ConstraintSet.LEFT,mainLayout.getId(),ConstraintSet.LEFT,screenWidth-screenWidthOffSet-5*square);

        set.connect(userNameTextView.getId(),ConstraintSet.TOP,mainLayout.getId(),ConstraintSet.TOP,2*square);
        set.connect(userNameTextView.getId(),ConstraintSet.LEFT,mainLayout.getId(),ConstraintSet.LEFT,square);

        set.connect(newNameEditText.getId(),ConstraintSet.TOP,mainLayout.getId(),ConstraintSet.TOP,6*square);
        set.connect(newNameEditText.getId(),ConstraintSet.LEFT,mainLayout.getId(),ConstraintSet.LEFT,square);

        set.connect(saveName.getId(),ConstraintSet.TOP,photo.getId(),ConstraintSet.BOTTOM,square);
        set.connect(saveName.getId(),ConstraintSet.LEFT,newNameEditText.getId(),ConstraintSet.RIGHT,square);

        set.connect(newPhoto.getId(),ConstraintSet.TOP,newNameEditText.getId(),ConstraintSet.BOTTOM,3*square);
        set.connect(newPhoto.getId(),ConstraintSet.LEFT,mainLayout.getId(),ConstraintSet.LEFT,square);

        set.connect(chooseNewPhoto.getId(),ConstraintSet.TOP,newNameEditText.getId(),ConstraintSet.BOTTOM,2*square);
        set.connect(chooseNewPhoto.getId(),ConstraintSet.LEFT,newPhoto.getId(),ConstraintSet.RIGHT,2*square);

        set.connect(uploadNewPhoto.getId(),ConstraintSet.TOP,chooseNewPhoto.getId(),ConstraintSet.BOTTOM,2*square);
        set.connect(uploadNewPhoto.getId(),ConstraintSet.LEFT,chooseNewPhoto.getId(),ConstraintSet.LEFT,0);

        set.connect(fbPhoto.getId(),ConstraintSet.TOP,newPhoto.getId(),ConstraintSet.BOTTOM,2*square);
        set.connect(fbPhoto.getId(),ConstraintSet.LEFT,mainLayout.getId(),ConstraintSet.LEFT,square);

        set.connect(fbName.getId(),ConstraintSet.TOP,fbPhoto.getId(),ConstraintSet.BOTTOM,2*square);
        set.connect(fbName.getId(),ConstraintSet.LEFT,mainLayout.getId(),ConstraintSet.LEFT,square);


        set.applyTo(mainLayout);


        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("User").child(firebaseUser.getUid());
        storageReference = FirebaseStorage.getInstance().getReference("profile_picture").child(firebaseUser.getUid());

        final long SIZE=1024*1024;
        storageReference.getBytes(SIZE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bm = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                photo.setImageBitmap(new RoundedCornerBitmap(bm,square/2).getRoundedCornerBitmap());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                photo.setBackgroundResource(R.drawable.account_box_grey);
            }
        });

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    user=dataSnapshot.getValue(User.class);
                    userNameTextView.setText(user.getName());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        saveName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newName = newNameEditText.getText().toString().trim();
                if(!TextUtils.isEmpty(newName)){
                user.setName(newName);
                databaseReference.setValue(user);
                userNameTextView.setText(user.getName());
                Toast.makeText(SettingsActivity.this,"NAME SAVED",Toast.LENGTH_LONG).show();
                }
            }
        });

        chooseNewPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fileChooser();
            }
        });

        uploadNewPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(resizeBitmap!=null){
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    resizeBitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
                    byte[] data = baos.toByteArray();
                    storageReference.putBytes(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            photo.setImageBitmap(new RoundedCornerBitmap(resizeBitmap,square/2).getRoundedCornerBitmap());
                            newPhoto.setImageResource(R.drawable.account_box_grey);
                            uploadNewPhoto.setVisibility(View.GONE);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SettingsActivity.this,"TRY ONCE AGAIN",Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

    }

    private void fileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null){

            imguri=data.getData();

            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(this);
        }
        if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if(resultCode==RESULT_OK){
          //      resultUri = result.getUri();
                resultUri = result.getUri();
                bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),resultUri);
                    resizeBitmap=null;
                    resizeBitmap = Bitmap.createScaledBitmap(bitmap,200,200,false);
                    newPhoto.setImageBitmap(new RoundedCornerBitmap(resizeBitmap,square/2).getRoundedCornerBitmap());
                    uploadNewPhoto.setVisibility(View.VISIBLE);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
                }
       //         newPhoto.setImageBitmap(bitmap);


            }else if(resultCode==CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
            Exception error = result.getError();
            Toast.makeText(this,error.getMessage(),Toast.LENGTH_LONG).show();
            }
        }
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
// TODO setImageResource everywhere instead of setBackgroundResource...