package org.drussila.models;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import org.drussila.R;
import org.drussila.models.character.ChibiCharacter;

public class GameSurface extends SurfaceView implements SurfaceHolder.Callback {


    private GameThread gameThread;

    private ChibiCharacter chibi1;

    public GameSurface(Context context)  {
        super(context);

        // Make Game Surface focusable so it can handle events. .
        this.setFocusable(true);

        // Sét callback.
        this.getHolder().addCallback(this);
    }

    public void update()  {
        this.chibi1.update();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            int x=  (int)event.getX();
            int y = (int)event.getY();
            chibi1.setVectorX(x);
            chibi1.setVectorY(y);
            int movingVectorX =x-  this.chibi1.getX() ;
            int movingVectorY =y-  this.chibi1.getY() ;

            this.chibi1.setMovingVector(movingVectorX,movingVectorY);
            return true;
        }

        if (event.getAction() == MotionEvent.ACTION_UP) {
            return true;
        }
        return false;
    }

    @Override
    public void draw(Canvas canvas)  {
        super.draw(canvas);

        this.chibi1.draw(canvas);
    }

    // Implements method of SurfaceHolder.Callback
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Bitmap chibiBitmap1 = BitmapFactory.decodeResource(this.getResources(), R.drawable.ctejh1);
        this.chibi1 = new ChibiCharacter(this,chibiBitmap1,getWidth()/2,getHeight()/2);

        this.gameThread = new GameThread(this,holder);
        this.gameThread.setRunning(true);
        this.gameThread.start();
    }

    // Implements method of SurfaceHolder.Callback
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    // Implements method of SurfaceHolder.Callback
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry= true;
        while(retry) {
            //try {
                this.gameThread.setRunning(false);
                // Parent thread must wait until the end of GameThread.
                //this.gameThread.join();

            retry= false;
        }
    }

}