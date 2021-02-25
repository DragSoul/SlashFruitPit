package com.example.anbertrand1.myapplication.modele;

import static java.lang.Math.random;

public class Bomb extends Mobile {


    public Bomb(int ressource){
        setX(0);
        setY(0);

        setSpeedY((int)(10 + random()*15));
        setSpeedX(0);

        setRatioScreenH(6);
        setRatioScreenW(14);

        setRessource(ressource);

        setGameIncrementation(-40);
    }
}
