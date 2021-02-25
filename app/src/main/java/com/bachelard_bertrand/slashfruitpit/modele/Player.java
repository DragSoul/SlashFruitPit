package com.example.anbertrand1.myapplication.modele;


public class Player {

    private String nom;
    public String getNom() {return nom;}

    public Player(String nom) {
        this.nom = nom;
    }

    @Override
    public boolean equals(Object obj) {
        if( obj != null && (this.getClass() != obj.getClass())){
            return false;
        }
        Player player = (Player) obj;
        return player.getNom().equals(player.getNom());
    }
}
