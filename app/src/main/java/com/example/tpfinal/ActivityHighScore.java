package com.example.tpfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Vector;

public class ActivityHighScore extends AppCompatActivity {
    GestionBD database;
    ListView scoreList;
    ArrayList<String> scoreAL;
    Button buttonBack;
    Ecouteur ec;
    String origin;
    Intent in;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        database = database.getInstance(this);
        database.ouvrirBD();

        buttonBack = findViewById(R.id.buttonBack);
        scoreList = findViewById(R.id.listScores);
        scoreAL = database.getTop10();

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, scoreAL);
        scoreList.setAdapter(adapter);

        ec = new Ecouteur();
        buttonBack.setOnClickListener(ec);

        origin = getIntent().getStringExtra("origin");
        in = new Intent(ActivityHighScore.this, Menu.class);
    }

    public class Ecouteur implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            if(view == buttonBack){

                if(origin.equals("menu"))
                    finish();
                else
                    startActivity(in);
            }
        }
    }
}