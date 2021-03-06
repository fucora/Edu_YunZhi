package com.android.renly.edu_yunzhi.UI;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import com.android.renly.edu_yunzhi.Utils.UIUtils;


public class BatchRadioButton extends android.support.v7.widget.AppCompatRadioButton {

    private boolean haveBatch = false;
    private int BADGE_SIZE = 3;
    private Paint paint_badge = new Paint();

    public BatchRadioButton(Context context) {
        super(context);
        init(context);
    }

    public BatchRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BatchRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context) {
        BADGE_SIZE = UIUtils.dp2px(3);

        paint_badge.setColor(Color.WHITE);
        paint_badge.setStyle(Paint.Style.FILL);
        paint_badge.setAntiAlias(true);
    }

    public void setState(boolean batch) {
        // TODO 有bug暂时不做了
        //if (haveBatch == batch) return;
        //haveBatch = batch;
        //invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (haveBatch) {
            int centx = getWidth() - BADGE_SIZE * 2;
            int centy = BADGE_SIZE * 2;
            canvas.drawCircle(centx, centy, BADGE_SIZE, paint_badge);
        }
    }
}

