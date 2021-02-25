package com.example.anbertrand1.myapplication.modele;

import java.util.ArrayList;


public abstract class MediatorAbs {

    private ContextGame contextG;
    public ContextGame getContextG() {return contextG;}

    protected void setContextG(ContextGame contextG) {this.contextG=contextG;}

    protected void moveFruitBombWithCollisionDetection(Mobile m){}

    protected void moveBladeWithCollisionDetection(Blade b, float z,boolean isLast){}

    protected boolean isTouchingMobile(Mobile m, float x, float y) {return false;}

    protected void movingCollisionBladeWithFruits(Blade b) {}

    public void reinitIfTouchingFruitBomb(float x, float y){}

    public void stopBladeWave(){}

    /**
     * Moves the element with collision detection, if needed
     */
    public void moveElementsWithCollisionDetection(){
        moveFruitsBombsWithCollisionDetection();
        if (getContextG().getIsBladeWave()){
            moveBladesAxeX();
        }
    }

    /**
     * Moves fruits and bombs with collision detection
     */
    private void moveFruitsBombsWithCollisionDetection() {
        ArrayList<Mobile> tabFruit = contextG.getTabFruitBomb();
        for (int i = 0; i < tabFruit.size(); i++) {
            Mobile m = tabFruit.get(i);
            moveFruitBombWithCollisionDetection(m);
        }
    }

    /**
     * Moves all blades from the contextGame on the X axis
     */
    private void moveBladesAxeX(){
        ArrayList<Mobile> tabBlades = getContextG().getTabBlade();
        for(int i=0;i<tabBlades.size();i++){
            Blade b =(Blade) tabBlades.get(i);
            b.setX(b.getX() + b.getSpeedX());
            //On vérifie la collision entre un fruit/bomb et une blade ici,
            //car ce déplacement est toujours appelé (Et non pas relatif à l'accéléromètre comme de déplacement sur Y)
            movingCollisionBladeWithFruits(b);
        }
    }

    /**
     * Moves all blades from the contextGame on the Y axis, according to the accelerometer value
     * @param z The accelerometer value
     */
    public void moveBlades(float z) {
        ArrayList<Mobile> tabBlade = getContextG().getTabBlade();
        for (int i = 0; i < tabBlade.size(); i++) {
            Blade b = (Blade)tabBlade.get(i);
            if(i == tabBlade.size()-1) {
                moveBladeWithCollisionDetection(b, z,true);
            }else {
                moveBladeWithCollisionDetection(b, z,false);
            }
        }
    }
}
