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

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

private Ranking mRanking;
private Context mContext;
private int square;
private String userId;

    public RecyclerViewAdapter(Context mContext, Ranking mRanking, int square, String userId) {
        this.mRanking = mRanking;
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
        holder.usernameScore.setText(mRanking.getRanking(position).getName());
        holder.noOfGamesScore.setText(String.valueOf(mRanking.getRanking(position).getNoOfGames()));
        holder.scoreScore.setText(String.valueOf(mRanking.getRanking(position).getScore()));

        if(mRanking.getRanking(position).getId().equals(userId)){
            holder.positionScore.setTextColor(mContext.getColor(R.color.pen_red));
            holder.usernameScore.setTextColor(mContext.getColor(R.color.pen_red));
            holder.noOfGamesScore.setTextColor(mContext.getColor(R.color.pen_red));
            holder.scoreScore.setTextColor(mContext.getColor(R.color.pen_red));
        }

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

            ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT,2*square);
            parentLayout.setLayoutParams(params);
            parentLayout.setBackground(new TileDrawable(mContext.getDrawable(R.drawable.background_x), Shader.TileMode.REPEAT,square));
            positionScore.setTextSize(TypedValue.COMPLEX_UNIT_PX,square);
            usernameScore.setTextSize(TypedValue.COMPLEX_UNIT_PX,square);
            noOfGamesScore.setTextSize(TypedValue.COMPLEX_UNIT_PX,square);
            scoreScore.setTextSize(TypedValue.COMPLEX_UNIT_PX,square);
        }
    }
}
