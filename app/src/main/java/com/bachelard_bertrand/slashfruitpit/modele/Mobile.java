package com.example.anbertrand1.myapplication.modele;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;


public abstract class Mobile {
    private BitmapDrawable img=null;
    private int x;
    private float y;
    private int width;
    private int height;
    private int speedX;
    private int speedY;
    private int ratioScreenW;
    private int RatioScreenH;
    private int ressource;
    private int gameIncrementation;

    /**
     * Transforms a resource in a BitmapDrawable
     * @param c context of the application
     * @param ressource
     * @param w image's widht
     * @param h image's height
     * @return
     */
    public BitmapDrawable setImage(final Context c, final int ressource, final int w, final int h)
    {
        Drawable dr = c.getResources().getDrawable(ressource);
        Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
        return new BitmapDrawable(c.getResources(), Bitmap.createScaledBitmap(bitmap, w, h, true));
    }

    public BitmapDrawable getImg() {return img;}
    public void setImg(BitmapDrawable img) {this.img = img;}


    public int getX(){return x;}
    public void setX(int x) {this.x =x;}


    public float getY(){return y;}
    public void setY(float y) {this.y=y;}


    public void setWidth(int width) {this.width = width;}
    public int getWidth(){return width;}


    public int getHeight() {return  height;}
    public void setHeight(int height) {this.height = height;}


    public int getSpeedX(){return speedX;}
    public void setSpeedX(int speedX){this.speedX = speedX;}

    public int getSpeedY(){
        return speedY;
    }
    public void setSpeedY(int speedY) {this.speedY=speedY;}

    public int getRatioScreenW() {return ratioScreenW;}
    public void setRatioScreenW(int ratioScreenW) {this.ratioScreenW = ratioScreenW;}

    public int getRatioScreenH() {return RatioScreenH;}
    public void setRatioScreenH(int ratioScreenH) {RatioScreenH = ratioScreenH;}

    public int getRessource() {return ressource;}
    public void setRessource(int ressource) {this.ressource = ressource;}

    public int getGameIncrementation() {return gameIncrementation;}
    public void setGameIncrementation(int gameIncrementation) {this.gameIncrementation = gameIncrementation;}

}
