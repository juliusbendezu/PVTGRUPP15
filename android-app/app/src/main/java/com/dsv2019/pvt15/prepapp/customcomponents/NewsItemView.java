package com.dsv2019.pvt15.prepapp.customcomponents;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dsv2019.pvt15.prepapp.R;
import com.dsv2019.pvt15.prepapp.models.NewsItem;

public class NewsItemView extends LinearLayout {

    Paint paint = new Paint();

    public NewsItemView(Context context) {
        super(context);
    }

    public NewsItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NewsItemView(Context context, NewsItem newsItem) {
        super(context);
        setWillNotDraw(false);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(7);
        setWeightSum(1);

        TextView newsSummaryTV = new TextView(context);
        newsSummaryTV.append(newsItem.getSummary());
        styleTextView(newsSummaryTV);
        addView(newsSummaryTV);

        ImageView buttonImage = new ImageView(context);
        styleImageView(buttonImage);
        addView(buttonImage);

        setPadding(10, 10, 10, 10);

    }

    private void styleTextView(TextView tv) {
        tv.setPadding(40, 20, 20, 20);
        tv.setTypeface(tv.getTypeface(), Typeface.BOLD);
        tv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, (float) 0.8));
    }

    private void styleImageView(ImageView iv) {
        iv.setImageResource(R.drawable.arrow_icon);
        iv.setPadding(50, 0, 70, 0);
        iv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT, (float) 0.2));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //Drawing the border
        canvas.drawLine(0, 0, getWidth(), 0, paint); //TOP
        //canvas.drawLine(0, getHeight(), getWidth(), getHeight(), paint); //BOTTOM
        //canvas.drawLine(getWidth(), 0, getWidth(), getHeight(), paint); //RIGHT
        //canvas.drawLine(0, 0,0, getHeight(), paint); //LEFT
    }
}

