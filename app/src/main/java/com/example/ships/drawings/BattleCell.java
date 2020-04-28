package com.example.ships.drawings;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.ships.R;

public class BattleCell extends Drawable {

    private Context context;
    private final Paint paint;
    private final int square;

    public BattleCell(Context context, int square){
        this.square=square;
        this.paint=new Paint();
        this.context=context;

    }


    @Override
    public void draw(@NonNull Canvas canvas) {
        Rect rect = new Rect(0,0,square,square);

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(ContextCompat.getColor(context, R.color.WHITE));
        canvas.drawRect(rect,paint);

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(square/10);
        paint.setColor(ContextCompat.getColor(context, R.color.pen));
        canvas.drawRect(rect,paint);


    }

    @Override
    public void setAlpha(int alpha) {
        paint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        paint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}
