package com.example.ships.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ships.R;
import com.example.ships.classes.Ranking;

public class RecyclerViewAdapterChooseOpponent extends RecyclerView.Adapter<RecyclerViewAdapterChooseOpponent.VH> {

    private Ranking mRanking;
    private Context mContext;
    String userId;

    public RecyclerViewAdapterChooseOpponent(Context mContext, Ranking mRanking, String userID) {
        this.mRanking = mRanking;
        this.mContext = mContext;
        this.userId = userID;
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
        if(mRanking.getRanking(position).getId().equals(userId)){
            holder.parentLayout.setBackgroundResource(R.color.RED);
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
        }
    }


}
