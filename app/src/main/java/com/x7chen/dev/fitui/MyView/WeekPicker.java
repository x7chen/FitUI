package com.x7chen.dev.fitui.MyView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.x7chen.dev.fitui.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/12/1.
 */
public class WeekPicker extends View {
    boolean[] week_select = {false, false, false, false, false, false, false};
    int ITEM_WIDTH = 80;
    int ITEM_HEIGHT = ITEM_WIDTH;
    int ITEM_SPACE = 30;
    int VIEW_PADDING = 10;
    int ITEM_PADDING = 10;
    int ITEM_TEXT_SIZE = 60;
    ArrayList<ItemArea> itemAreas = new ArrayList<>(7);

    private void itemAreaInit() {
        itemAreas.clear();
        for (int i = 0; i < 7; i++) {
            ItemArea itemArea = new ItemArea();
            itemArea.left = VIEW_PADDING + i * (ITEM_WIDTH + ITEM_SPACE);
            itemArea.top = VIEW_PADDING;
            itemArea.right = VIEW_PADDING + i * (ITEM_WIDTH + ITEM_SPACE) + ITEM_WIDTH;
            itemArea.bottom = VIEW_PADDING + ITEM_HEIGHT;
            itemAreas.add(itemArea);
        }
    }

    public WeekPicker(Context context) {
        super(context);
        itemAreaInit();
    }

    public WeekPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        itemAreaInit();
    }

    public WeekPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        itemAreaInit();
    }

    public boolean[] getWeeks() {
        return week_select;
    }
    public void setWeeks(boolean[] weeks){
        week_select = weeks;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        String[] weeks = {"一", "二", "三", "四", "五", "六", "日"};
        Paint mPaint = new Paint();
        for (int w = 0; w < 7; w++) {
            if (week_select[w]) {
                //画填充色
                mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
                mPaint.setColor(getResources().getColor(R.color.colorAccent));
//                canvas.drawRect(itemAreas.get(w).left, itemAreas.get(w).top, itemAreas.get(w).right, itemAreas.get(w).bottom, mPaint);
                mPaint.setAntiAlias(true);
                canvas.drawCircle((itemAreas.get(w).left+itemAreas.get(w).right)/2,(itemAreas.get(w).top+itemAreas.get(w).bottom)/2,ITEM_HEIGHT/2,mPaint);
                //画汉字
                mPaint.setStyle(Paint.Style.FILL);
                mPaint.setTextSize(ITEM_TEXT_SIZE);
                mPaint.setColor(Color.WHITE);
                canvas.drawText(weeks[w], itemAreas.get(w).left + ITEM_PADDING, itemAreas.get(w).bottom - ITEM_PADDING * 2, mPaint);
            } else {
                //画边框
                mPaint.setStyle(Paint.Style.STROKE);
                mPaint.setStrokeWidth(2);
                mPaint.setColor(Color.GRAY);
                mPaint.setAntiAlias(true);
//                canvas.drawRect(itemAreas.get(w).left, itemAreas.get(w).top, itemAreas.get(w).right, itemAreas.get(w).bottom, mPaint);
                canvas.drawCircle((itemAreas.get(w).left + itemAreas.get(w).right) / 2, (itemAreas.get(w).top + itemAreas.get(w).bottom)/2,ITEM_HEIGHT/2,mPaint);
                //画汉字
                mPaint.setStyle(Paint.Style.FILL);
                mPaint.setTextSize(ITEM_TEXT_SIZE);
                mPaint.setColor(Color.GRAY);
                canvas.drawText(weeks[w], itemAreas.get(w).left + ITEM_PADDING, itemAreas.get(w).bottom - ITEM_PADDING * 2, mPaint);
            }
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        widthMeasureSpec = MeasureSpec.getMode(widthMeasureSpec) + VIEW_PADDING * 2 + ITEM_WIDTH * 7 + ITEM_SPACE * 6;
        heightMeasureSpec = MeasureSpec.getMode(heightMeasureSpec) + VIEW_PADDING * 2 + ITEM_HEIGHT;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float positionX, positionY;
        positionX = event.getX();
        positionY = event.getY();

        if ((itemAreas.get(0).top - (ITEM_SPACE / 2) < positionY) && (itemAreas.get(0).bottom + (ITEM_SPACE / 2) > positionY))
            for (ItemArea itemArea : itemAreas) {
                if ((itemArea.left - (ITEM_SPACE / 2) < positionX) && (itemArea.right + (ITEM_SPACE / 2) > positionX)) {
                    week_select[itemAreas.indexOf(itemArea)] = !week_select[itemAreas.indexOf(itemArea)];
                    invalidate();
                }
            }

        Log.i("View P:", String.valueOf(positionX) + ":" + String.valueOf(positionY));
        return super.onTouchEvent(event);

    }

    class ItemArea {
        public float left;
        public float top;
        public float right;
        public float bottom;
    }
}
