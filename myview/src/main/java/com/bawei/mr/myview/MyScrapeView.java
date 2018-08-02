package com.bawei.mr.myview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * author:Created by WangZhiQiang on 2018/8/2.
 */
public class MyScrapeView extends View {

    //被覆盖的内容图片
    Bitmap bfgBackground;
    //用来当做覆盖的图层
    Bitmap fgBackground;
    //用来当做覆盖的图层的画布
    Canvas canvas;
    //划开路径的画笔
    Paint pathpaint;
    //手指刮开的路径
    Path path;
    //用来当做覆盖用的图层的文字画笔
    Paint contentPaint;
    //文字信息
    String content = "刮刮看咯~";


    public MyScrapeView(Context context) {
        super(context);
        init();
    }


    public MyScrapeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        //初始化刮开路径的画笔
        pathpaint = new Paint();
        pathpaint.setAlpha(0);
        pathpaint.setStyle(Paint.Style.STROKE);
        pathpaint.setStrokeWidth(50);
        //显示下面一层的图片
        pathpaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        pathpaint.setStrokeJoin(Paint.Join.ROUND);
        pathpaint.setStrokeCap(Paint.Cap.ROUND);

        //初始化手指划开的路径
        path = new Path();

        //初始化被覆盖的内容bitmap
        bfgBackground = BitmapFactory.decodeResource(getResources(), R.drawable.ay);
        //初始化用来当做覆盖用的bitmap
        fgBackground = Bitmap.createBitmap(bfgBackground.getWidth(), bfgBackground.getHeight(), Bitmap.Config.ARGB_8888);

        //初始化画布
        canvas = new Canvas(fgBackground);

        //初始化内容画笔
        contentPaint = new Paint();
        contentPaint.setColor(Color.WHITE);
        contentPaint.setTextSize(100);
        contentPaint.setStrokeWidth(20);

        //设置用来当做覆盖用的图层颜色为灰色（上面一层的颜色）
        canvas.drawColor(Color.GRAY);
        //在上面一层显示的文字
        canvas.drawText(content, canvas.getWidth() / 4, canvas.getHeight() / 2, contentPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //清空画笔
                path.reset();
                //原点移动到手指的触摸点
                path.moveTo(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(event.getX(), event.getY());
                break;
        }

        //模拟刮来效果
        canvas.drawPath(path, pathpaint);
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //绘制两个图层
        canvas.drawBitmap(bfgBackground, 0, 0, null);
        canvas.drawBitmap(fgBackground, 0, 0, null);
    }
}
