package com.example.tpfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Menu extends AppCompatActivity {
    Button buttonStartGame, buttonHighScore;
    GestionBD database;
    int score;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        buttonStartGame = findViewById(R.id.buttonStartGame);
        buttonHighScore = findViewById(R.id.buttonHighScore);

        Ecouteur ec = new Ecouteur();

        buttonStartGame.setOnClickListener(ec);
        buttonHighScore.setOnClickListener(ec);

        database = database.getInstance(this);
        database.ouvrirBD();
        score = -1;

        Intent received = getIntent();
        if(received != null){
            date = received.getStringExtra("date");
            score = received.getIntExtra("score", -1);
        }
    }

    private class Ecouteur implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            if(view == buttonStartGame){
                if(score > 0){
                    database.ajouterScore(score, date);
                }
                Intent in = new Intent(Menu.this, ActivityJeu.class);
                startActivity(in);
            }
            else if(view == buttonHighScore){
                Intent in = new Intent(Menu.this, ActivityHighScore.class);
                in.putExtra("origin", "menu");
                startActivity(in);
            }
        }
    }
}