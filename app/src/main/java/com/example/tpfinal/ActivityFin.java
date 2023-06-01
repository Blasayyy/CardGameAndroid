package com.example.tpfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ActivityFin extends AppCompatActivity {
    TextView score;
    Button buttonOK;
    Ecouteur ec;
    GestionBD database;
    int scoreVal;
    String date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fin);

        score = findViewById(R.id.score);
        buttonOK = findViewById(R.id.buttonOK);

        database = database.getInstance(this);
        database.ouvrirBD();

        scoreVal = getIntent().getIntExtra("score", 0);
        date = getIntent().getStringExtra("date");

        ec = new Ecouteur();
        score.setText(Integer.toString(scoreVal));
        buttonOK.setOnClickListener(ec);
    }

    public class Ecouteur implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            if(view == buttonOK){
                database.ajouterScore(scoreVal, date);
                Intent in = new Intent(ActivityFin.this, ActivityHighScore.class);
                in.putExtra("origin", "fin");
                startActivity(in);
            }
        }
    }
}