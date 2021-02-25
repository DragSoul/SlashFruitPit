package com.example.anbertrand1.myapplication.gameOperation;

import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;

import com.example.anbertrand1.myapplication.modele.ContextGame;
import com.example.anbertrand1.myapplication.modele.Mobile;

import java.util.ArrayList;
import java.util.Iterator;

import static java.lang.Math.random;


public class DisplayGame extends AbsDisplay{

    /**
     * Initializes positions of the Blade array
     * @param cG
     * @param wScreen
     */
    @Override
    protected void initPositionsBlades(ContextGame cG,int wScreen){
        ArrayList<Mobile> tabBlade = cG.getTabBlade();
        for (Mobile blade : tabBlade) {
            blade.setX(-50);
            blade.setY((int)(random()*(wScreen-blade.getWidth())));
        }
    }

    /**
     * Initializes positions of the FruitBomb array
     * @param cG
     * @param wScreen
     */
    @Override
    protected void initPositionsFruitsBombs(ContextGame cG,int wScreen){
        ArrayList<Mobile> tabFruitBomb = cG.getTabFruitBomb();
        for (Mobile mobile: tabFruitBomb) {
            mobile.setX((int)(random()*(wScreen-mobile.getWidth())));
            mobile.setY(-50);
        }
    }

    /**
     * Draws a list of Mobile
     * @param canvas
     * @param tabMobile
     */
    @Override
    protected void drawMobiles(Canvas canvas, ArrayList<Mobile> tabMobile){
        Iterator i = tabMobile.iterator();
        while (i.hasNext()) {
            Mobile m = (Mobile) i.next();
            drawMobile(canvas,m);
        }
    }

    /**
     * Draws a Mobile
     * @param canvas
     * @param mobile
     */
    private void drawMobile(Canvas canvas, Mobile mobile){
        BitmapDrawable img = mobile.getImg();
        if(img==null) {return;}
        canvas.drawBitmap(img.getBitmap(), mobile.getX(), mobile.getY(), null);
    }

    /**
     * Resizes Mobiles in contextGame according to the screen
     * @param wScreen
     * @param hScreen
     * @param cG
     */
    @Override
    protected void initResizeMobiles(int wScreen, int hScreen, ContextGame cG){
        ArrayList<Mobile> tabBlade = cG.getTabBlade();
        for (int j = 0; j < tabBlade.size(); j++) {
            Mobile b = tabBlade.get(j);
            initResizeMobile(wScreen,hScreen,b,cG);
        }
        ArrayList<Mobile> tabFruitBomb = cG.getTabFruitBomb();
        for (int j = 0; j < tabFruitBomb.size(); j++) {
            Mobile m = tabFruitBomb.get(j);
            initResizeMobile(wScreen,hScreen,m,cG);
        }
    }

    /**
     * Resizes a Mobile in contextGame according to the screen
     * @param wScreen
     * @param hScreen
     * @param mobile
     * @param contextG
     */
    private void initResizeMobile(int wScreen,int hScreen,Mobile mobile, ContextGame contextG){

        mobile.setWidth(wScreen/mobile.getRatioScreenW());
        mobile.setHeight(hScreen/mobile.getRatioScreenH());

        mobile.setImg(mobile.setImage(contextG.getmContext(), mobile.getRessource(),mobile.getWidth(),mobile.getHeight()));
    }
}
