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

public class RecyclerViewAdapterChooseOpponent extends RecyclerView.Adapter<RecyclerViewAdapterChooseOpponent.VH> {

    private List<User> list;
    private Context mContext;
    private String userId;
    private OnItemClickListener mListener;
    private int square;

    public interface OnItemClickListener{
        void inviteGamer(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener=listener;
    }

    public RecyclerViewAdapterChooseOpponent(Context mContext, List<User> list,int square, String userID) {
        this.list=list;
        this.mContext = mContext;
        this.userId = userID;
        this.square = square;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_choose_opponent,parent,false);
        VH holder = new VH(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {

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
        if(list.get(position).getId().equals(userId)||
        !list.get(position).getIndex().getOpponent().isEmpty()){
            ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT,0);
            holder.parentLayout.setLayoutParams(params);


        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class VH extends RecyclerView.ViewHolder{
        TextView positionScore;
        TextView usernameScore;
        TextView noOfGamesScore;
        TextView scoreScore;
        ImageView photo;
        LinearLayout parentLayout;


        public VH(@NonNull View itemView) {
            super(itemView);
            positionScore = itemView.findViewById(R.id.positionScoreChooseOpponent);
            photo = itemView.findViewById(R.id.photoChooseOpponent);
            usernameScore = itemView.findViewById(R.id.usernameScoreChooseOpponent);
            noOfGamesScore = itemView.findViewById(R.id.noOfGamesScoreChooseOpponent);
            scoreScore = itemView.findViewById(R.id.scoreScoreChooseOpponent);
            parentLayout = itemView.findViewById(R.id.parent_layout_choose_opponent);


            ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT,2*square);
            LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(2*square,2*square);
            parentLayout.setLayoutParams(params);
            parentLayout.setBackground(new TileDrawable(mContext.getDrawable(R.drawable.background_x), Shader.TileMode.REPEAT,square));
            positionScore.setTextSize(TypedValue.COMPLEX_UNIT_PX,square);
            photo.setLayoutParams(params1);
            usernameScore.setTextSize(TypedValue.COMPLEX_UNIT_PX,square);
            noOfGamesScore.setTextSize(TypedValue.COMPLEX_UNIT_PX,square);
            scoreScore.setTextSize(TypedValue.COMPLEX_UNIT_PX,square);

            parentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            mListener.inviteGamer(position);
                        }
                    }
                }
            });
        }
    }


}
