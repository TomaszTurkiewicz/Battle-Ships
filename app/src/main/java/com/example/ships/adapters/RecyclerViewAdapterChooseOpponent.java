package com.example.ships.adapters;

import android.content.Context;
import android.graphics.Shader;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ships.R;
import com.example.ships.classes.Ranking;
import com.example.ships.classes.TileDrawable;

public class RecyclerViewAdapterChooseOpponent extends RecyclerView.Adapter<RecyclerViewAdapterChooseOpponent.VH> {

    private Ranking mRanking;
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

    public RecyclerViewAdapterChooseOpponent(Context mContext, Ranking mRanking,int square, String userID) {
        this.mRanking = mRanking;
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
        holder.usernameScore.setText(mRanking.getRanking(position).getName());
        holder.noOfGamesScore.setText(String.valueOf(mRanking.getRanking(position).getNoOfGames()));
        holder.scoreScore.setText(String.valueOf(mRanking.getRanking(position).getScore()));
        if(mRanking.getRanking(position).getId().equals(userId)||
        !mRanking.getRanking(position).getIndex().getOpponent().isEmpty()){
            ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT,0);
            holder.parentLayout.setLayoutParams(params);


        }

    }

    @Override
    public int getItemCount() {
        return mRanking.getRanking().length;
    }

    public class VH extends RecyclerView.ViewHolder{
        TextView positionScore;
        TextView usernameScore;
        TextView noOfGamesScore;
        TextView scoreScore;
        LinearLayout parentLayout;


        public VH(@NonNull View itemView) {
            super(itemView);
            positionScore = itemView.findViewById(R.id.positionScoreChooseOpponent);
            usernameScore = itemView.findViewById(R.id.usernameScoreChooseOpponent);
            noOfGamesScore = itemView.findViewById(R.id.noOfGamesScoreChooseOpponent);
            scoreScore = itemView.findViewById(R.id.scoreScoreChooseOpponent);
            parentLayout = itemView.findViewById(R.id.parent_layout_choose_opponent);


            ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT,2*square);
            parentLayout.setLayoutParams(params);
            parentLayout.setBackground(new TileDrawable(mContext.getDrawable(R.drawable.background_x), Shader.TileMode.REPEAT,square));
            positionScore.setTextSize(TypedValue.COMPLEX_UNIT_PX,square);
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
