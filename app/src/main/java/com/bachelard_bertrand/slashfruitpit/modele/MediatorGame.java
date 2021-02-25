package com.example.anbertrand1.myapplication.modele;

import android.support.annotation.Nullable;

import java.util.ArrayList;

import static java.lang.Math.abs;
import static java.lang.Math.random;


public class MediatorGame extends MediatorAbs{

    private float oldX =0; //Paramètre servant à calculer le changement de l'axe de l'acceleromètre
                        //Ainsi, les Blades bougent en fonction du changement et non pas de comment on tient le téléphone

    public MediatorGame(ContextGame contextG){ setContextG(contextG); }

    /**
     * Moves this mobile on the Y axis
     * @param mobile
     */
    @Override
    protected void moveFruitBombWithCollisionDetection(Mobile mobile){
        float mobileY = mobile.getY();
        int mobileSpeedY = mobile.getSpeedY();

        // on incrémente seulement la coordonnée X, car il n'y a pas de déplacement horizontal
        mobile.setY(mobileY + mobileSpeedY);

        // si y dépasse la hauteur de l'écran, on le fait recommencer
        if(mobileY+mobile.getHeight() > getContextG().getHEcran()) {
           reinitFruitBomb(mobile);
        }
    }

    /**
     * Reinitializes a fruit or bomb on top of the screen, with collision
     * detection with other Mobiles from the contextGame
     * @param mobile
     */
    private void reinitFruitBomb(Mobile mobile){
        //On lui donne une nouvelle vitesse
        mobile.setSpeedY((int)(10 + random()*15));
        //Pour ne pas que les Fruits et Bomb ne se superposent à leur réinitialisation
        movingCollisionMobileWithFruitsBombs(mobile);
    }

    /**
     * Reinitializes the touched fruit or bomb
     * @param x
     * @param y
     */
    @Override
    public void reinitIfTouchingFruitBomb(float x, float y){
        ArrayList<Mobile> tabFruit = getContextG().getTabFruitBomb();
        for (int i = 0; i < tabFruit.size(); i++) {
            Mobile m = tabFruit.get(i);
            if(isTouchingMobile(m,x,y)) {
                incrementScoreMobile(m,null);
                //On réinitialise le mobile en faisant attention à ce qu'il ne se mette pas
                //par dessus un autre mobile à son initialisation
                reinitFruitBomb(m);
            }
        }
    }

    /**
     * Tells if the coordinates (x,y) are touching the Mobile m
     * @param m
     * @param x
     * @param y
     * @return
     */
    @Override
    protected boolean isTouchingMobile(Mobile m, float x, float y ){
        int xM = m.getX();
        float yM = m.getY();
        if(x >= xM && x <= xM + m.getWidth() && y >= yM && y <= yM + m.getHeight()){
            return true;
        }
        return false;
    }

    /**
     * Reinitializes positions of a Mobile (here fruit or bomb)
     * @param mobile
     */
    private void movingCollisionMobileWithFruitsBombs(Mobile mobile){
        mobile.setX((int)(random()*(getContextG().getWEcran()-mobile.getWidth())));
        mobile.setY(-50);
        while (isMobileTouchingOneOfTheFruitBomb(mobile)){
            mobile.setX((int)(random()*(getContextG().getWEcran()-mobile.getWidth())));
            mobile.setY(-50);
        }
    }

    /**
     * Tells if the mobile is touching one of the Fruit or Bomb in the context
     * @param mobile1
     * @return
     */
    private boolean isMobileTouchingOneOfTheFruitBomb(Mobile mobile1){
        ArrayList<Mobile> tabFruit = getContextG().getTabFruitBomb();
        for (int i = 0; i < tabFruit.size(); i++) {
            Mobile fruit2 = tabFruit.get(i);
            if(isMobileTouchingMobile(mobile1,fruit2)){
                return true;
            }
        }
        return false;
    }

    /**
     * Tells if mobile1 is touching mobbile2
     * @param mobile1
     * @param mobile2
     * @return
     */
    private boolean isMobileTouchingMobile(Mobile mobile1,Mobile mobile2){
        int X1 = mobile1.getX();
        int X2 = mobile2.getX();
        float Y1 = mobile1.getY();
        float Y2 = mobile2.getY();

        boolean RightX = (X1 >= X2 && X1 <= X2 + mobile2.getWidth());
        boolean LeftX = (X1 + mobile1.getWidth() >= X2 && X1 + mobile1.getWidth() <= X2 +mobile2.getWidth());
        boolean TopY = (Y1 + mobile1.getHeight() >= Y2 && Y1 + mobile1.getHeight() <= Y2 + mobile2.getHeight());
        boolean BottomY = (Y1 >= Y2 && Y1 <= Y2 + mobile2.getHeight());
        if(mobile1 != mobile2){
            if( (RightX && TopY) || (RightX && BottomY) || (LeftX && TopY) || (LeftX && BottomY)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Increments the score, according to the touched mobile and the possible touching mobile
     * @param mTouched
     * @param bTouching
     */
    private void incrementScoreMobile(Mobile mTouched,@Nullable Blade bTouching){
        //Si c'est une blade qui a touché le fruit/bomb
        if(bTouching != null){
            //Si une blade tape un fruit => point de fruit* proportion(2) (20)
            if(mTouched.getClass().toString().contains("Fruit")) {
                getContextG().setScoreActuelInt(getContextG().getScoreActuelInt() + mTouched.getGameIncrementation() * bTouching.getGamePropotionIncrementation());
                return;
            }
        }
        getContextG().setScoreActuelInt(getContextG().getScoreActuelInt() + mTouched.getGameIncrementation());
        //On ne lance possibilement la vague que si on vient de toucher un fruit
        if(mTouched.getClass().toString().contains("Fruit")) {
            startBladeWaveIfNecessary();
        }
    }

    /**
     * Sets the boolean IsBladeWave from contextGame to true if necessary
     */
    private void startBladeWaveIfNecessary() {
        if ((getContextG().getScoreActuelInt() % 200 == 0 || getContextG().getScoreActuelInt() % 210 == 0 )
                && getContextG().getScoreActuelInt() != 0) {
            getContextG().setIsBladeWave(true);
        }
    }

    /**
     * Stops the BladeWave and reinitializes the blades
     */
    @Override
    public void stopBladeWave() {
        getContextG().setIsBladeWave(false);
        ArrayList<Mobile> tabBlade = getContextG().getTabBlade();
        for(int i=0;i < tabBlade.size();i++){
            Blade b = (Blade) tabBlade.get(i);
            reinitBlade(b);
        }
    }

    /**
     * Moves the Blade b on the Y axis, according to the parameter x
     * @see public void moveBlades(float z)
     * @param b
     * @param x
     * @param isLast
     */
    @Override
    protected void moveBladeWithCollisionDetection(Blade b,float x,boolean isLast) {
        ContextGame contextG = getContextG();

        int X = b.getX();
        float Y = b.getY();

        //Soit on déplace les blades tous les %temps%, soit on les déplaces uniquement s'il y a une modification significative
        //Ici, nous avons choisi la modification significative
        //Ce qui permet également que le déplacement soit indépendant de la position du téléphone
        float secx;

        secx = x - oldX;
        if(abs(secx) >= 0.05) {
            b.setY(b.getY() + secx * 80);
            if (isLast) { //Ne changer que lorsque toutes les Blades ont executé le changement
                oldX = x;
            }
        }

        //Si la blade a traversé l'écran, on recommence
        if(X > contextG.getWEcran()){
            reinitBlade(b);
            return;
        }

        //On empêche la blade de partir trop bas ou trop haut de l'écran
        if(Y + b.getHeight() >= contextG.getHEcran()){
            b.setY(b.getY()-2);
            return;
        }
        if(Y < -5){
            b.setY(0);
            return;
        }
    }

    /**
     * reinitializes the Blade b's position
     * @param b
     */
    private void reinitBlade(Blade b){
        b.setX(0);
        b.setY((float)(random()*(getContextG().getHEcran()-b.getHeight())));
        b.setSpeedX((int)(10 + random()*15));
    }

    /**
     * Checks the collision between Blade b and all the other Mobiles from contextGame
     * @see private void moveBladesAxeX
     * @see private void isTouchingMobile
     * @param b
     */
    @Override
    protected void movingCollisionBladeWithFruits(Blade b){
        ContextGame contextG = getContextG();
        for(int i = 0; i<contextG.getTabFruitBomb().size(); i++){
            Mobile m = contextG.getTabFruitBomb().get(i);
            //On vérifie la collision en trois points sur le devant de la blade
            if(isTouchingMobile(m,b.getX()+b.getWidth(),b.getY())
                    || isTouchingMobile(m,b.getX() + b.getWidth(),b.getY() + b.getWidth()/2)
                    || isTouchingMobile(m, b.getX() + b.getWidth(),b.getY() + b.getWidth())){

                incrementScoreMobile(m,b);
                reinitFruitBomb(m);
            }
        }
    }
}
