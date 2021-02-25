package com.example.anbertrand1.myapplication.modele;

import java.util.ArrayList;


public interface Observable {
    ArrayList<Observer> observers = new ArrayList<>();
    void attachObserver(Observer o);
    void detachObserver(Observer o);
    void notifyObservers();
}
