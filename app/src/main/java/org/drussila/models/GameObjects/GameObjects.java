package org.drussila.models.GameObjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import org.drussila.models.GameObject;
import org.drussila.models.GameSurface;

public class GameObjects extends GameObject {
    private static final int ROW_OBJECT = 0;
    private Bitmap[] Object;

    private int rowUsing = 0;
    private int colUsing;
    private long lastDrawNanoTime =-1;

    private GameSurface gameSurface;

    public GameObjects(GameSurface gameSurface, Bitmap image, int x, int y) {
        super(image, 1, 1, x, y);
        this.gameSurface= gameSurface;
        this.Object = new Bitmap[colCount];
        for(int col = 0; col< colCount; col++ ) {
            this.Object[col] = this.createSubImageAt(ROW_OBJECT, col);
        }
    }
    public Bitmap[] getMoveBitmaps()  {
        switch (rowUsing)  {
            case ROW_OBJECT:
                return  this.Object;
            default:
                return null;
        }
    }
    public Bitmap getCurrentMoveBitmap()  {
        Bitmap[] bitmaps = this.getMoveBitmaps();
        return bitmaps[this.colUsing];
    }


    public void update()  {
            this.rowUsing = ROW_OBJECT;
    }

    public void draw(Canvas canvas)  {
        Bitmap bitmap = this.getCurrentMoveBitmap();
        canvas.drawBitmap(bitmap,x, y, null);
        // Last draw time.
        this.lastDrawNanoTime= System.nanoTime();
    }
}
