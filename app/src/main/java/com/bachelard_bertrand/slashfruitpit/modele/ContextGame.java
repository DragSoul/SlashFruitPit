package com.example.anbertrand1.myapplication.modele;

import android.content.Context;

import com.example.anbertrand1.myapplication.R;

import java.util.ArrayList;


public class ContextGame implements Observable {

    private ArrayList<Mobile> tabFruitBomb;
    private ArrayList<Mobile> tabBlade;
    private int wEcran,hEcran;
    private Context mContext;
    private Score scoreActuel;
    private Player currentPlayer;
    private boolean isBladeWave = false;


    public ArrayList<Mobile> getTabFruitBomb(){return tabFruitBomb;}
    public  ArrayList<Mobile> getTabBlade(){return tabBlade;}
    public int getWEcran(){
        return wEcran;
    }
    public int getHEcran(){
        return hEcran;
    }
    public Context getmContext() {return mContext;}
    public void setWEcran(int wEcran){
        this.wEcran = wEcran;
    }
    public void setHEcran(int hEcran){
        this.hEcran = hEcran;
    }
    public void setScoreActuelInt(int scoreActuel){
        this.scoreActuel.setScore(scoreActuel);
        notifyObservers();
    }
    public int getScoreActuelInt() {return  scoreActuel.getScore();}
    public Score getScoreActuel() {return scoreActuel;}
    public Player getCurrentPlayer() {return currentPlayer;}
    public boolean getIsBladeWave() {return isBladeWave;}
    void setIsBladeWave(boolean isBladeWave) {this.isBladeWave = isBladeWave;}

    public ContextGame(Context mContext,ArrayList<Mobile> tabFruitBomb,ArrayList<Mobile> tabBlade, int wEcran,int hEcran){
        super();
        this.tabFruitBomb = tabFruitBomb;
        this.tabBlade = tabBlade;
        this.wEcran = wEcran;
        this.hEcran = hEcran;
        this.mContext = mContext;
        scoreActuel = new Score();
    }

    public void setCurrentPlayersName(String name){
        if(name == null || name.isEmpty()){
            name = "Toto"; //Default player's name
        }
        currentPlayer = new Player(name);
    }

    public String getCurrentPlayersName(){
        if(currentPlayer == null){
            currentPlayer = new Player("Toto");
        }
        return currentPlayer.getNom();
    }

    public void chargerContextGame(int nb){
        int j;
        int nbBomb = nb*3/7; //43%
        nb = nb - nbBomb;
        for (j = 0; j < nb/3; j++) {
            Fruit fruit = new Fruit(R.drawable.banana_draw);
            tabFruitBomb.add(fruit);
        }
        for (j = j; j < nb*2/3; j++) {
            Fruit fruit = new Fruit(R.drawable.cherry_draw);
            tabFruitBomb.add(fruit);
        }
        for (j = j ; j < nb; j++) {
            Fruit fruit = new Fruit(R.drawable.strawberry_draw);
            tabFruitBomb.add(fruit);
        }

        for (j=0;j<nbBomb/2;j++){
            Bomb bomb = new Bomb(R.drawable.gun2);
            tabFruitBomb.add(bomb);
        }
        for(j=j;j<nbBomb;j++){
            Bomb bomb = new Bomb(R.drawable.rose);
            tabFruitBomb.add(bomb);
        }

        for(int i = 0; i < 3;i++){
            Blade blade = new Blade(R.drawable.saber2);
            tabBlade.add(blade);
        }
    }


    @Override
    public void attachObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void detachObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer o : observers){
            o.update(this);
        }
    }
}
