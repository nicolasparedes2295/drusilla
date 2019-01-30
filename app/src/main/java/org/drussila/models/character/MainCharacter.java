package org.drussila.models.character;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import org.drussila.models.GameObject;
import org.drussila.models.GameObjects.Object;
import org.drussila.models.GameSurface;

import java.util.ArrayList;
import java.util.List;

public class MainCharacter extends GameObject {

    private static final int ROW_TOP_TO_BOTTOM = 0;
    private static final int ROW_RIGHT_TO_LEFT = 1;
    private static final int ROW_LEFT_TO_RIGHT = 2;
    private static final int ROW_BOTTOM_TO_TOP = 3;
    private static final int ROW_DEATH = 7;

    // Row index of Image are being used.
    private int rowUsing = 0;
    private Character character;
    private int colUsing;
    private int speedWalk = 0;
    private List<Object> objectsCodenadas=new ArrayList<Object>();

    private Bitmap[] leftToRights;
    private Bitmap[] rightToLefts;
    private Bitmap[] topToBottoms;
    private Bitmap[] bottomToTops;
    private Bitmap[] deathChar;

    // Velocity of game character (pixel/millisecond)
    public static final float VELOCITY = 0.1f;

    private int movingVectorX = 0;
    private int movingVectorY = 0;
    private int vectorX=0;
    private int vectorY=0;

    private long lastDrawNanoTime =-1;

    private GameSurface gameSurface;

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public MainCharacter(GameSurface gameSurface, Bitmap image, int x, int y) {
        super(image, 8, 12, x, y);
        character = new Character(10,15,10,10,10,10);
        this.gameSurface= gameSurface;

        this.topToBottoms = new Bitmap[3]; // 3
        this.rightToLefts = new Bitmap[3]; // 3
        this.leftToRights = new Bitmap[3]; // 3
        this.bottomToTops = new Bitmap[3]; // 3
        this.deathChar = new Bitmap[3];
        //walk char effect
        for(int col = 0; col< 3; col++ ) {
            this.topToBottoms[col] = this.createSubImageAt(ROW_TOP_TO_BOTTOM, col);
            this.rightToLefts[col]  = this.createSubImageAt(ROW_RIGHT_TO_LEFT, col);
            this.leftToRights[col] = this.createSubImageAt(ROW_LEFT_TO_RIGHT, col);
            this.bottomToTops[col]  = this.createSubImageAt(ROW_BOTTOM_TO_TOP, col);
        }
        //death char effect
        for(int col = 0;col <3; col++){
            this.deathChar[col] = this.createSubImageAt(7,col+3);
        }
    }

    public Bitmap[] getMoveBitmaps()  {
        switch (rowUsing)  {
            case ROW_BOTTOM_TO_TOP:
                return  this.bottomToTops;
            case ROW_LEFT_TO_RIGHT:
                return this.leftToRights;
            case ROW_RIGHT_TO_LEFT:
                return this.rightToLefts;
            case ROW_TOP_TO_BOTTOM:
                return this.topToBottoms;
            case ROW_DEATH:
                return this.deathChar;
            default:
                return null;
        }
    }

    public Bitmap getCurrentMoveBitmap()  {
        Bitmap[] bitmaps = this.getMoveBitmaps();
        return bitmaps[this.colUsing];
    }

    public void update()  {
        boolean objetosValidos = ValidarObjetos();
        int betweenX = vectorX-x;
        int betweenY = vectorY-y;
        boolean a=movingVectorX!=0&&movingVectorY!=0;
        boolean b=!isBetween(betweenX,-60,60);
        boolean c=!isBetween(betweenY,-40,40);
        if ((b||c)&&a&&objetosValidos){
        this.colUsing++;}

        if(colUsing >= 3)  {
            this.colUsing =0;
        }
        // Current time in nanoseconds
        long now = System.nanoTime();

        // Never once did draw.
        if(lastDrawNanoTime==-1) {
            lastDrawNanoTime= now;
        }
        // Change nanoseconds to milliseconds (1 nanosecond = 1000000 milliseconds).

        if(character.isRunning())
        {speedWalk=500000;}else{speedWalk=1000000;}
        int deltaTime = (int) ((now - lastDrawNanoTime)/ speedWalk );
        /*System.out.println("Valor x= " + x + "|");
        System.out.print("Valor y= " + y);
        System.out.println("Valor vector x= " + vectorX + "|");
        System.out.print("Valor vector y= " + vectorY);
        System.out.println("Valor dif x= " + betweenX);
        System.out.print("Valor dif y= " + betweenY);
        // Distance moves
        System.out.println("Bool x= "+b);
        System.out.print("Bool y= "+c);
        */
        float distance = VELOCITY * deltaTime;
        double movingVectorLength = Math.sqrt(movingVectorX* movingVectorX + movingVectorY*movingVectorY);
        int oldx=x;
        int oldy=y;
        if((b||c)&&!character.isDeath()&&objetosValidos) {
            this.x = x + (int) (distance * movingVectorX / movingVectorLength);
            this.y = y + (int) (distance * movingVectorY / movingVectorLength);
        }else{
            character.setRunning(false);
        }
        character.setHealth(DescreaseHealthEfect(this.character.getHealth(),1,oldx,oldy));
        //System.out.println(character.getHealth().toString());
        // When the game's character touches the edge of the screen, then change direction
        if(character.getHealth()<1){
            character.setDeath(true);
        }
        if(this.x < 0 )  {
            this.x = 0;
            this.movingVectorX = - this.movingVectorX;
        } else if(this.x > this.gameSurface.getWidth() -width)  {
            this.x= this.gameSurface.getWidth()-width;
            this.movingVectorX = - this.movingVectorX;
        }

        if(this.y < 0 )  {
            this.y = 0;
            this.movingVectorY = - this.movingVectorY;
        } else if(this.y > this.gameSurface.getHeight()- height)  {
            this.y= this.gameSurface.getHeight()- height;
            this.movingVectorY = - this.movingVectorY ;
        }

        // rowUsing
        if(!character.isDeath()){
        if( movingVectorX > 0 )  {
            if(movingVectorY > 0 && Math.abs(movingVectorX) < Math.abs(movingVectorY)) {
                this.rowUsing = ROW_TOP_TO_BOTTOM;
            }else if(movingVectorY < 0 && Math.abs(movingVectorX) < Math.abs(movingVectorY)) {
                this.rowUsing = ROW_BOTTOM_TO_TOP;
            }else  {
                this.rowUsing = ROW_LEFT_TO_RIGHT;
            }
        } else {
            if(movingVectorY > 0 && Math.abs(movingVectorX) < Math.abs(movingVectorY)) {
                this.rowUsing = ROW_TOP_TO_BOTTOM;
            }else if(movingVectorY < 0 && Math.abs(movingVectorX) < Math.abs(movingVectorY)) {
                this.rowUsing = ROW_BOTTOM_TO_TOP;
            }else  {
                this.rowUsing = ROW_RIGHT_TO_LEFT;
            }
        }
    }else{
            this.rowUsing = ROW_DEATH;
        }
    }

    public void draw(Canvas canvas)  {
        Bitmap bitmap = this.getCurrentMoveBitmap();
        canvas.drawBitmap(bitmap,x, y, null);
        // Last draw time.
        this.lastDrawNanoTime= System.nanoTime();
    }

    public void setMovingVector(int movingVectorX, int movingVectorY)  {
        this.movingVectorX= movingVectorX;
        this.movingVectorY = movingVectorY;
    }

    public static boolean isBetween(int x, int lower, int upper) {
        return lower <= x && x <= upper;
    }

    public Double DescreaseHealthEfect(Double health, int powerEffect, int x, int y){

        if(this.x!=x || this.y!=y) {
            if(character.isRunning()){
                health = health - (powerEffect*0.14);
            }else{
                health = health - (powerEffect*0.07);
            }
        }

        return health;
    }
    public void setVectorX(int vectorX) {
        this.vectorX = vectorX;
    }

    public void setVectorY(int vectorY) {
        this.vectorY = vectorY;
    }
    public boolean ValidarObjetos(){
        boolean retorno = true;
        for(int i=0; i<objectsCodenadas.size();i++){
        int betweenX=objectsCodenadas.get(i).getX()-x;
        int betweenY=objectsCodenadas.get(i).getY()-y;

            retorno =!isBetween(betweenX,-80,80)||!isBetween(betweenY,-80,80);

        }
        return retorno;
    }

    public List<Object> getObjectsCodenadas() {
        return objectsCodenadas;
    }

    public void setObjectsCodenadas(List<Object> objectsCodenadas) {
        this.objectsCodenadas = objectsCodenadas;
    }
}