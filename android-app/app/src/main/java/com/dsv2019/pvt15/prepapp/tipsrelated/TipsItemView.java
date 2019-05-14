package com.dsv2019.pvt15.prepapp.tipsrelated;


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

public class TipsItemView extends LinearLayout {

    Paint paint = new Paint();

    public TipsItemView(Context context) {
        super(context);
    }

    public TipsItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TipsItemView(Context context, SingleTips SingleTips) {
        super(context);
        setWillNotDraw(false);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(6);
        setWeightSum(1);

        TextView tipsSummary = new TextView(context);
        tipsSummary.append(SingleTips.toString());
        styleTextView(tipsSummary);
        addView(tipsSummary);

        ImageView buttonImage = new ImageView(context);
        styleImageView(buttonImage);
        addView(buttonImage);
    }

    private void styleTextView(TextView tv) {
        tv.setPadding(40, 20, 20, 20);
        tv.setTypeface(tv.getTypeface(), Typeface.BOLD);
        tv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, (float) 0.8));
    }

    private void styleImageView(ImageView iv) {
        iv.setImageResource(R.drawable.arrow_icon);
        iv.setPadding(0, 0, 20, 0);
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
