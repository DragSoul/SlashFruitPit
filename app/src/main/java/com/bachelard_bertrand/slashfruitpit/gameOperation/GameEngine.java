package com.example.anbertrand1.myapplication.gameOperation;

import android.graphics.Canvas;

import com.example.anbertrand1.myapplication.view.GameView;


public class GameEngine extends Thread {
    private final static int FRAMES_PER_SECOND = 30;
    private final static int SKIP_TICKS = 1000 / FRAMES_PER_SECOND;

    private final GameView view; // l'objet SurfaceView que nous verrons plus bas
    private boolean running = false; // état du thread, en cours ou non
    public boolean isRunning(){return running;}

    public GameEngine(GameView view) {this.view = view;}

    public void setRunning(boolean run) {
        running = run;
    }

    @Override
    public void run()
    {
        // déclaration des temps de départ et de pause
        long startTime;
        long sleepTime;

        // boucle tant que running est vrai
        // il devient faux par setRunning(false), notamment lors de l'arrêt de l'application
        // cf : surfaceDestroyed() dans GameView
        while (running)
        {
            // horodatage actuel
            startTime = System.currentTimeMillis();

            // mise à jour du déplacement des ojets dans GameView.update()
            synchronized (view.getHolder()) {view.update();}

            // Rendu de l'image, tout en vérrouillant l'accès car nous
            // y accédons à partir d'un processus distinct
            Canvas c = null;
            try {
                c = view.getHolder().lockCanvas();
                if(c != null) {
                    synchronized (view.getHolder()) {
                        view.doDraw(c);
                    }
                }
            }
            finally
            {
                if (c != null) {view.getHolder().unlockCanvasAndPost(c);}
            }

            // Calcul du temps de pause, et pause si nécessaire
            // afin de ne réaliser le travail ci-dessus que X fois par secondes
            sleepTime = SKIP_TICKS-(System.currentTimeMillis() - startTime);
            try {
                if (sleepTime >= 0) {sleep(sleepTime);}
            }
            catch (Exception e) {
            }
        }
    }
}
