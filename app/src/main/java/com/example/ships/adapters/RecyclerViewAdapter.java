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

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

private Ranking mRanking;
private Context mContext;

    public RecyclerViewAdapter(Context mContext, Ranking mRanking) {
        this.mRanking = mRanking;
        this.mContext = mContext;
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
        holder.usernameScore.setText(mRanking.getRanking(position).getName());
        holder.noOfGamesScore.setText(String.valueOf(mRanking.getRanking(position).getNoOfGames()));
        holder.scoreScore.setText(String.valueOf(mRanking.getRanking(position).getScore()));

    }

    @Override
    public int getItemCount() {
        return mRanking.getRanking().length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

    TextView positionScore;
    TextView usernameScore;
    TextView noOfGamesScore;
    TextView scoreScore;
    LinearLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            positionScore = itemView.findViewById(R.id.positionScore);
            usernameScore = itemView.findViewById(R.id.usernameScore);
            noOfGamesScore = itemView.findViewById(R.id.noOfGamesScore);
            scoreScore = itemView.findViewById(R.id.scoreScore);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
