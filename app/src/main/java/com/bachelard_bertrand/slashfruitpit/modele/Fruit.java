package com.example.anbertrand1.myapplication.modele;

import static java.lang.Math.random;


public class Fruit extends Mobile {

    public Fruit(int ressource)
    {
        setX(0);
        setY(0); // position de départ

        //initialisation de la vitesse avec un random pour que les fruits aient des trajectoires différentes
        setSpeedY((int)(10 + random()*15));
        setSpeedX(0);

        setRatioScreenH(6);
        setRatioScreenW(14);

        setGameIncrementation(20);

        setRessource(ressource);
    }

}
