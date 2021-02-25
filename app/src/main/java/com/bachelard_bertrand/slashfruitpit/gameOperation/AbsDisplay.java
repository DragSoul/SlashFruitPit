package com.example.anbertrand1.myapplication.gameOperation;

import android.graphics.Canvas;

import com.example.anbertrand1.myapplication.modele.ContextGame;
import com.example.anbertrand1.myapplication.modele.Mobile;

import java.util.ArrayList;

public abstract class AbsDisplay {

    /**
     * Draws the game
     * @param canvas
     * @param contextG The context of the game
     */
    public void drawContextGame(Canvas canvas, ContextGame contextG){
        ArrayList<Mobile> tabFruit = contextG.getTabFruitBomb();
        drawMobiles(canvas,tabFruit);
        if(contextG.getIsBladeWave()) {
            ArrayList<Mobile> tabBlade = contextG.getTabBlade();
            drawMobiles(canvas, tabBlade);
        }
    }

    protected void drawMobiles(Canvas canvas, ArrayList<Mobile> tabMobile){}

    /**
     * Initialize positions of all Mobiles
     * @param cG
     * @param wScreen
     */
    private void initPositionsMobiles(ContextGame cG, int wScreen){
        initPositionsBlades(cG,wScreen);
        initPositionsFruitsBombs(cG,wScreen);
    }


    protected void initPositionsFruitsBombs(ContextGame cG, int wScreen) {}
    protected void initPositionsBlades(ContextGame cG, int wScreen) {}

    /**
     * Resizes all draws according to the screen
     * @param wScreen screen width
     * @param hScreen screen height
     * @param contextG
     */
    public void initResizeDraws(int wScreen, int hScreen, ContextGame contextG){
        // wScreen et hScreen sont la largeur et la hauteur de l'écran en pixel
        // on sauve ces informations dans le contexte, car elles serviront
        // à détecter les collisions sur les bords de l'écran
        contextG.setWEcran(wScreen);
        contextG.setHEcran(hScreen);

        initPositionsMobiles(contextG,wScreen);
        initResizeMobiles(wScreen,hScreen,contextG);
    }

    protected void initResizeMobiles(int wScreen,int hScreen, ContextGame contextG) {}

}
