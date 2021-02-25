package com.example.anbertrand1.myapplication.view;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.anbertrand1.myapplication.R;
import com.example.anbertrand1.myapplication.modele.Observable;
import com.example.anbertrand1.myapplication.modele.Observer;


public class FenetreGame extends FragmentActivity implements Observer {

    private TextView scoreActuel;
    private MediaPlayer media;
    private GameView gv;
    private boolean isPause=false;
    private boolean isSound = true;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fenetre_game);

        media = MediaPlayer.create(this, R.raw.nci);

        Button b = findViewById(R.id.pause);
        b.bringToFront();
        Button b2 = findViewById(R.id.sound);
        b2.bringToFront();

        scoreActuel = findViewById(R.id.score);
        scoreActuel.setText(getApplicationContext().getString(R.string.score,'0'));
        gv = findViewById(R.id.GameV);
        gv.getContextGame().attachObserver(this);

        TextView player = findViewById(R.id.joueur);
        Intent intent = getIntent();
        gv.getContextGame().setCurrentPlayersName(intent.getStringExtra("PlayerName"));
        player.setText(getApplicationContext().getString(R.string.playerName,gv.getContextGame().getCurrentPlayersName()));
    }

    /**
     * Starts or stops the sound
     * @param view
     */
    public void soundClick(View view){
        if(!isPause) {
            Button b = (Button) view;

            if (media.isPlaying()) {
                media.pause();
                b.setBackgroundResource(android.R.drawable.ic_lock_silent_mode);
                isSound=false;
            } else {
                media.start();
                b.setBackgroundResource(android.R.drawable.ic_lock_silent_mode_off);
                isSound = true;
            }
        }
    }

    /**
     * Starts or stops the game
     * @param v
     */
    public void pauseClick(View v){
        Button b = (Button) v;
        if(!isPause) {
            media.pause();

            gv.getGameLoopThread().setRunning(false);
            b.setBackgroundResource(android.R.drawable.ic_media_play);
            isPause = true;
        }else{
            if(isSound) {
                media.start();
            }

            gv.getGameLoopThread().setRunning(true);
            b.setBackgroundResource(android.R.drawable.ic_media_pause);
            isPause = false;
            gv.reinitThread();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        media.start();
    }


    @Override
    public void onPause() {
        media.pause();
        super.onPause();
    }


    @Override
    public void update(Observable o) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                scoreActuel.setText(getApplicationContext().getString(R.string.score, String.valueOf(gv.getContextGame().getScoreActuelInt())));
            }
        });
    }
}
