package com.example.anbertrand1.myapplication.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.example.anbertrand1.myapplication.R;


public class FenetreMenu extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ma_premiere_fenetre);
    }

    public void game(View view){
        Intent intent = new Intent(this, FenetreGame.class);
        EditText name = findViewById(R.id.joueurName);
        intent.putExtra("PlayerName",String.valueOf(name.getText()));
        startActivity(intent);
    }

    public void rule(View view){
        Intent intent = new Intent(this, FenetreRegle.class);
        startActivity(intent);
    }
}
