package org.drussila.models;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import org.drussila.R;
import org.drussila.models.GameObjects.GameObjects;
import org.drussila.models.GameObjects.Object;
import org.drussila.models.character.MainCharacter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameSurface extends SurfaceView implements SurfaceHolder.Callback {
    static final int MAX_DURATION_TOUCH = 200;
    private long startTime=0;
    Random rn = new Random();
    private GameThread gameThread;

    private MainCharacter chibi1;
    private GameObjects barril;
    public GameSurface(Context context)  {
        super(context);

        // Make Game Surface focusable so it can handle events. .
        this.setFocusable(true);

        // Sét callback.
        this.getHolder().addCallback(this);
    }

    public void update()  {
        this.barril.update();
        Object object = new Object();
        object.setX(barril.getX());
        object.setY(barril.getY());
        List<Object> objects = new ArrayList<Object>();
        objects.add(object);
        chibi1.setObjectsCodenadas(objects);

        this.chibi1.update();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - startTime <= MAX_DURATION_TOUCH) {
                this.chibi1.getCharacter().setRunning(true);
            }
            int x=  (int)event.getX();
            int y = (int)event.getY();
            chibi1.setVectorX(x);
            chibi1.setVectorY(y);
            int movingVectorX =x-  this.chibi1.getX() ;
            int movingVectorY =y-  this.chibi1.getY() ;

            this.chibi1.setMovingVector(movingVectorX,movingVectorY);
            startTime = System.currentTimeMillis();
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
        this.barril.draw(canvas);
        this.chibi1.draw(canvas);
    }

    // Implements method of SurfaceHolder.Callback
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Bitmap chibiBitmap1 = BitmapFactory.decodeResource(this.getResources(), R.drawable.ctejh);
        Bitmap barrilBitmap1 = BitmapFactory.decodeResource(this.getResources(), R.drawable.barril);
        this.chibi1 = new MainCharacter(this,chibiBitmap1,getWidth()/2,getHeight()/2);
        this.barril = new GameObjects(this,barrilBitmap1,rn.nextInt(getWidth() + 1),rn.nextInt(getHeight() + 1));
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