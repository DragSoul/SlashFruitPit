package com.example.anbertrand1.myapplication.modele;


import static java.lang.Math.random;


public class Blade extends Mobile{

    private int gamePropotionIncrementation;

    public Blade(int ressource){
        setX(0);
        setY(0);
        setSpeedX((int)(10 + random()*15));
        setSpeedY(0); //N'est jamais utilisé

        setRatioScreenW(6);
        setRatioScreenH(4);

        setRessource(ressource);

        setGameIncrementation(0);
        gamePropotionIncrementation = 2;
    }

    public int getGamePropotionIncrementation() {
        return gamePropotionIncrementation;
    }
}
