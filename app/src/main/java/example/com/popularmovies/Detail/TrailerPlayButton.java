package example.com.popularmovies.Detail;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import example.com.popularmovies.R;

/**
 * Created by WSIM on 4/10/2016.
 */

public class TrailerPlayButton extends View {


    Paint btnInnerPaint;

    public TrailerPlayButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }



    private void init() {



        btnInnerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        btnInnerPaint.setStyle(Paint.Style.FILL);
        btnInnerPaint.setColor(getContext().getResources().getColor(R.color.colorAccent));
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Path trianglePath = new Path();
        trianglePath.moveTo(0,0);
        trianglePath.lineTo(getWidth(),getHeight()/2);
        trianglePath.lineTo(0,getHeight());
        trianglePath.lineTo(0,0);
        trianglePath.close();
        canvas.drawPath(trianglePath,btnInnerPaint);




    }
}
