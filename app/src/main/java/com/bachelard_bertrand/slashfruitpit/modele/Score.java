package com.example.anbertrand1.myapplication.modele;


public class Score {
    private int score;
    public int getScore(){return score;}
    public void setScore(int score){this.score = score;}

    public Score(){
        score = 0;
    }


    @Override
    public boolean equals(Object obj) {
        if(obj != null && (this.getClass() != obj.getClass())){
            return false;
        }
        Score score = (Score) obj;
        return this.getScore() == score.getScore();
    }

}
