package com.example.ships.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Shader;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ships.R;
import com.example.ships.classes.RoundedCornerBitmap;
import com.example.ships.classes.TileDrawable;
import com.example.ships.classes.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

private List<User> list;
private Context mContext;
private int square;
private String userId;

    public RecyclerViewAdapter(Context mContext, List<User> list, int square, String userId) {
        this.list=list;
        this.mContext = mContext;
        this.square = square;
        this.userId = userId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_scores,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.positionScore.setText(String.valueOf(position+1));
        StorageReference sr = FirebaseStorage.getInstance().getReference("profile_picture").child(list.get(position).getId());
        final long SIZE = 1024*1024;
        sr.getBytes(SIZE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bm = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                holder.photo.setImageBitmap(new RoundedCornerBitmap(bm,2*square).getRoundedCornerBitmap());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                holder.photo.setImageResource(R.drawable.account_box_grey);
            }
        });




        holder.usernameScore.setText(list.get(position).getName());
        holder.noOfGamesScore.setText(String.valueOf(list.get(position).getNoOfGames()));
        holder.scoreScore.setText(String.valueOf(list.get(position).getScore()));

        if(list.get(position).getId().equals(userId)){
            holder.positionScore.setTextColor(mContext.getColor(R.color.pen_red));
            holder.usernameScore.setTextColor(mContext.getColor(R.color.pen_red));
            holder.noOfGamesScore.setTextColor(mContext.getColor(R.color.pen_red));
            holder.scoreScore.setTextColor(mContext.getColor(R.color.pen_red));
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

    TextView positionScore;
    TextView usernameScore;
    TextView noOfGamesScore;
    TextView scoreScore;
    ImageView photo;
    LinearLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            positionScore = itemView.findViewById(R.id.positionScore);
            photo = itemView.findViewById(R.id.photoScore);
            usernameScore = itemView.findViewById(R.id.usernameScore);
            noOfGamesScore = itemView.findViewById(R.id.noOfGamesScore);
            scoreScore = itemView.findViewById(R.id.scoreScore);
            parentLayout = itemView.findViewById(R.id.parent_layout);

            ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT,2*square);
            LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(2*square,2*square);
            parentLayout.setLayoutParams(params);
            parentLayout.setBackground(new TileDrawable(mContext.getDrawable(R.drawable.background_x), Shader.TileMode.REPEAT,square));
            positionScore.setTextSize(TypedValue.COMPLEX_UNIT_PX,square);
            photo.setLayoutParams(params1);
            usernameScore.setTextSize(TypedValue.COMPLEX_UNIT_PX,square);
            noOfGamesScore.setTextSize(TypedValue.COMPLEX_UNIT_PX,square);
            scoreScore.setTextSize(TypedValue.COMPLEX_UNIT_PX,square);
        }
    }
}
