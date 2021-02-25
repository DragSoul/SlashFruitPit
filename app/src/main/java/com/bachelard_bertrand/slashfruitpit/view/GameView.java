package com.example.anbertrand1.myapplication.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import com.example.anbertrand1.myapplication.gameOperation.AbsDisplay;
import com.example.anbertrand1.myapplication.modele.ContextGame;
import com.example.anbertrand1.myapplication.gameOperation.DisplayGame;
import com.example.anbertrand1.myapplication.gameOperation.GameEngine;
import com.example.anbertrand1.myapplication.modele.MediatorAbs;
import com.example.anbertrand1.myapplication.modele.MediatorGame;
import com.example.anbertrand1.myapplication.modele.Mobile;

import java.util.ArrayList;

import static android.content.Context.SENSOR_SERVICE;


public class GameView extends SurfaceView implements SurfaceHolder.Callback, SensorEventListener {

    private GameEngine gameLoopThread;
    private MediatorAbs gameMediator;
    private AbsDisplay disp;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;

    private Display mDisplay;
    private float mSensorX;

    private int startWave=0; //@valeur du du timestamp dès le début de la bladeWave

    public GameEngine getGameLoopThread() {
        return gameLoopThread;
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs,0);
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs,defStyleAttr);
    }

    public GameView(Context context) {
        super(context);
        init(null,0);
    }

    public ContextGame getContextGame(){return gameMediator.getContextG();}
    public MediatorAbs getGameMediator() {return gameMediator;}

    private void init(AttributeSet attrs, int defStyle) {

        getHolder().addCallback(this);
        disp = new DisplayGame();
        gameLoopThread = new GameEngine(this);

        ArrayList<Mobile> tabFruit = new ArrayList<>();
        ArrayList<Mobile> tabBlade = new ArrayList<>();

        ContextGame cG = new ContextGame(this.getContext(),tabFruit,tabBlade,getWidth(),getHeight());
        cG.chargerContextGame(10);
        gameMediator = new MediatorGame(cG);


        mSensorManager = (SensorManager)cG.getmContext().getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        WindowManager window = (WindowManager)cG.getmContext().getSystemService(Context.WINDOW_SERVICE);
        mDisplay = window.getDefaultDisplay();
    }

    /**
     * Reinitializes the current thread
     * @see GameEngine
     */
    public void reinitThread(){
        if(!gameLoopThread.isAlive()) {
            gameLoopThread = new GameEngine(this);
            gameLoopThread.setRunning(true);
            getGameLoopThread().start();
        }
    }

    /**
     * Draws the contextGame
     * @param canvas
     */
    public void doDraw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        ContextGame cG = gameMediator.getContextG();
        disp.drawContextGame(canvas,cG);
    }

    @Override
    public SurfaceHolder getHolder() {
        return super.getHolder();
    }

    /**
     * Update instances that are drawn
     */
    public void update() {
        gameMediator.moveElementsWithCollisionDetection();
    }


    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        // création du processus GameLoopThread si cela n'est pas fait
        if(gameLoopThread.getState()==Thread.State.TERMINATED) {
            gameLoopThread=new GameEngine(this);
        }
        gameLoopThread.setRunning(true);
        gameLoopThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int w, int h) {
        // redimensionnement des objets suivant la taille de l'écran
        ContextGame cG = gameMediator.getContextG();
        disp.initResizeDraws(w,h,cG);

        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        mSensorManager.unregisterListener(this);
        gameLoopThread.setRunning(false);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(getGameLoopThread().isRunning()) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    Log.i("APPLI_MOVE", "Down");
                    break;
                case MotionEvent.ACTION_MOVE:
                    Log.i("APPLI_MOVE", String.valueOf(event.getY()));
                    gameMediator.reinitIfTouchingFruitBomb(event.getX(), event.getY());
                    break;
                default:
                    return false;
            }
        }
        return true;
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER)
            return;
        float currentTime = 0;
        //L'accéléromètre étant là où est le mode par défault (portait/landscape)
        //Et l'activité étant bloquée en mode lanscape
        switch (mDisplay.getRotation()) {
            case Surface.ROTATION_0:
               mSensorX = event.values[1];
                break;
            case Surface.ROTATION_90:
                mSensorX = event.values[0];
                break;
            case Surface.ROTATION_180:
                mSensorX = -event.values[1];
                break;
            case Surface.ROTATION_270:
                mSensorX = -event.values[0];
                break;
        }

       if(gameMediator.getContextG().getIsBladeWave()){

           long newTimestamp = (event.timestamp)/(10*10*10*10*10*10*10*10); //Nouveau temps écoulé (en nanosecondes initialement)


           if(startWave == 0){ //On indique que la wave commence
               startWave =(int) newTimestamp % 1000;
               return;
           }

           currentTime = (int) newTimestamp % 1000;

           // startWave+100%1000 équivaut à 10 secondes après startWave
           if(((startWave+100)%1000) == currentTime ||
                   ((startWave+100)%1000) == currentTime + 1 ||
                   ((startWave+100)%1000) == currentTime -1){ //Au bout d'environ 10 secondes, on arrete et on réinitialise
               gameMediator.stopBladeWave();
               startWave = 0;
               return;
           }

           gameMediator.moveBlades(mSensorX); //Move axeY des Blades + Collisions
       }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
