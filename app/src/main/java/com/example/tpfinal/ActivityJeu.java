package com.example.tpfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class ActivityJeu extends AppCompatActivity {
    TextView upArrowLeft, upArrowRight, downArrowLeft, downArrowRight, pileTopLeft, pileTopRight, pileBottomLeft, pileBottomRight, carte1, carte2, carte3, carte4, carte5, carte6, carte7, carte8, score, cardsLeft;
    LinearLayout mainContainer, mainRow1, mainRow2, conteneur, conteneur2, topLeftContainer, topRightContainer, bottomLeftContainer, bottomRightContainer;
    Chronometer time;
    Partie partie;
    Button buttonStart, buttonMenu, buttonUndo;
    Boolean partieCourante;
    GestionBD database;
    int moveCounter;
    long previousTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //connecter l'interface
        time = findViewById(R.id.time);
        score = findViewById(R.id.Score);
        cardsLeft = findViewById(R.id.cardsLeft);

        buttonMenu = findViewById(R.id.buttonMenu);
        buttonStart = findViewById(R.id.buttonStart);
        buttonUndo = findViewById(R.id.buttonUndo);

        upArrowLeft = findViewById(R.id.upArrowLeft);
        upArrowRight = findViewById(R.id.upArrowRight);
        downArrowLeft = findViewById(R.id.downArrowLeft);
        downArrowRight = findViewById(R.id.downArrowRight);
        pileTopLeft = findViewById(R.id.pileTopLeft);
        pileTopRight = findViewById(R.id.pileTopRight);
        pileBottomLeft = findViewById(R.id.pileBottomLeft);
        pileBottomRight = findViewById(R.id.pileBottomRight);
        topLeftContainer = findViewById(R.id.topLeftContainer);
        topRightContainer = findViewById(R.id.topRightContainer);
        bottomLeftContainer = findViewById(R.id.bottomLeftContainer);
        bottomRightContainer = findViewById(R.id.bottomRightContainer);

        mainContainer = findViewById(R.id.mainContainer);
        mainRow1 = findViewById(R.id.mainRow1);
        mainRow2 = findViewById(R.id.mainRow2);
        carte1 = findViewById(R.id.carte1);
        carte2 = findViewById(R.id.carte2);
        carte3 = findViewById(R.id.carte3);
        carte4 = findViewById(R.id.carte4);
        carte5 = findViewById(R.id.carte5);
        carte6 = findViewById(R.id.carte6);
        carte7 = findViewById(R.id.carte7);
        carte8 = findViewById(R.id.carte8);

        partieCourante = false;

        //creation de l'ecouteur
        Ecouteur ec = new Ecouteur();

        //set l'ecouteur sur les Buttons
        buttonStart.setOnClickListener(ec);
        buttonMenu.setOnClickListener(ec);
        buttonUndo.setOnClickListener(ec);

        //set l'ecouteur sur les LinearLayouts et les TextViews liees aux cartes
        for(int i = 0; i < 4; i++){
            conteneur = (LinearLayout) mainRow1.getChildAt(i);
            conteneur2 = (LinearLayout) mainRow2.getChildAt(i);

            conteneur.getChildAt(0).setOnTouchListener(ec);
            conteneur2.getChildAt(0).setOnTouchListener(ec);
        }


        //set l'ecouteur sur les piles ou on va mettre les cartes
        pileTopRight.setOnDragListener(ec);
        pileTopLeft.setOnDragListener(ec);
        pileBottomRight.setOnDragListener(ec);
        pileBottomLeft.setOnDragListener(ec);


        resetPartie();

        //ouvrir base de donnees
        database = database.getInstance(this);
        database.ouvrirBD();


    }

    //fonction qui met a jour les backgrounds des cartes selon leur valeur
    public void updateCardBackground(TextView t, int num){
        Drawable carte00 = getResources().getDrawable(R.drawable.carte_00);
        Drawable carte10 = getResources().getDrawable(R.drawable.carte_10);
        Drawable carte20 = getResources().getDrawable(R.drawable.carte_20);
        Drawable carte30 = getResources().getDrawable(R.drawable.carte_30);
        Drawable carte40 = getResources().getDrawable(R.drawable.carte_40);
        Drawable carte50 = getResources().getDrawable(R.drawable.carte_50);
        Drawable carte60 = getResources().getDrawable(R.drawable.carte_60);
        Drawable carte70 = getResources().getDrawable(R.drawable.carte_70);
        Drawable carte80 = getResources().getDrawable(R.drawable.carte_80);
        Drawable carte90 = getResources().getDrawable(R.drawable.carte_90);
        if(num < 10)
            t.setBackground(carte00);
        else if(num < 20)
            t.setBackground(carte10);
        else if(num < 30)
            t.setBackground(carte20);
        else if(num < 40)
            t.setBackground(carte30);
        else if(num < 50)
            t.setBackground(carte40);
        else if(num < 60)
            t.setBackground(carte50);
        else if(num < 70)
            t.setBackground(carte60);
        else if(num < 80)
            t.setBackground(carte70);
        else if(num < 90)
            t.setBackground(carte80);
        else if(num < 100)
            t.setBackground(carte90);
    }

    //fonction qui reset la partie
    public void resetPartie(){
        Drawable normal = getResources().getDrawable(R.drawable.rectangle_gris);
        partie = new Partie();
        afficherScoreCards();
        long elapsedRealtime = SystemClock.elapsedRealtime();
        time.setBase(elapsedRealtime);
        time.start();
        previousTime = 0;
        moveCounter = 0;
        for(int i = 0; i < 4; i++){

            conteneur = (LinearLayout) mainRow1.getChildAt(i);
            conteneur2 = (LinearLayout) mainRow2.getChildAt(i);


            TextView numberContainer = (TextView) conteneur.getChildAt(0);
            TextView numberContainer2 = (TextView) conteneur2.getChildAt(0);

            numberContainer.setVisibility(View.VISIBLE);
            numberContainer2.setVisibility(View.VISIBLE);

            String num = Integer.toString(partie.getPileCartesEnMain().getCarte(i).getNumber());
            String num2 = Integer.toString(partie.getPileCartesEnMain().getCarte(i+4).getNumber());

            numberContainer.setText(num);
            numberContainer2.setText(num2);

            updateCardBackground(numberContainer, Integer.parseInt(num));
            updateCardBackground(numberContainer2, Integer.parseInt(num2));

        }

        pileBottomLeft.setBackground(normal);
        pileBottomLeft.setText(null);
        pileBottomRight.setBackground(normal);
        pileBottomRight.setText(null);
        pileTopLeft.setBackground(normal);
        pileTopLeft.setText(null);
        pileTopRight.setBackground(normal);
        pileTopRight.setText(null);

        partieCourante = true;
    }

    //fonction pour remettre deux cartes dans la main cote Vue
    public void repopulateHand(int number1, int number2){
        int numUsed = 0;
        for (int i = 0; i < 4; i++) {
            conteneur = (LinearLayout) mainRow1.getChildAt(i);
            conteneur2 = (LinearLayout) mainRow2.getChildAt(i);
            String num1 = Integer.toString(number1);
            String num2 = Integer.toString(number2);

            if (conteneur.getChildAt(0).getVisibility() == View.INVISIBLE) {
                TextView carte = (TextView) conteneur.getChildAt(0);
                carte.setVisibility(View.VISIBLE);

                if(numUsed == 0) {
                    carte.setText(num1);
                    numUsed++;
                    updateCardBackground(carte, number1);
                }
                else {
                    carte.setText(num2);
                    updateCardBackground(carte, number2);
                }

            }

            if (conteneur2.getChildAt(0).getVisibility() == View.INVISIBLE) {
                TextView carte = (TextView) conteneur2.getChildAt(0);
                carte.setVisibility(View.VISIBLE);

                if(numUsed == 0) {
                    carte.setText(num1);
                    numUsed++;
                    updateCardBackground(carte, number1);
                }
                else {
                    carte.setText(num2);
                    updateCardBackground(carte, number2);
                }
            }
        }

    }

    //fonction pour afficher le score et le nombre de cartes qui restent
    public void afficherScoreCards(){
        score.setText("Score:\n" + Integer.toString(partie.getScore()));
        cardsLeft.setText("Cards Left:\n" + Integer.toString(partie.getPileCartes().getSize() + partie.getPileCartesEnMain().getSize()));
    }

    //fonction pour calculer le temps passe dans le chronmetre en secondes
    public int getTimeElapsed(long previousTime){
        long timeElapsed =  SystemClock.elapsedRealtime() - time.getBase();
        timeElapsed /= 1000;
        int seconds = (int) timeElapsed;
        return seconds;
    }

    private class Ecouteur implements View.OnTouchListener, View.OnDragListener, View.OnClickListener {

        View carte = null;
        Pile pileAddedTo = null;
        boolean moveVerified = false;
        boolean enteredDrop = false;
        int bsCounter = 0;

        @Override
        public boolean onDrag(View source, DragEvent event) {

            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_ENTERED:
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    break;
                case DragEvent.ACTION_DROP:

                    //Infos sur carte et pile
                    TextView destination = (TextView)source;
                    carte = (View) event.getLocalState();
                    TextView carteTextView = (TextView) carte;

                    //Get numeros de la carte en main en int
                    int numeroCarte = Integer.parseInt(((TextView) carte).getText().toString());

                    //Modele
                    Carte carteMoved = partie.getCarteByNumber(partie.getPileCartesEnMain(), numeroCarte);
                    if(destination == pileTopLeft){
                        if(partie.verifyMove(partie.getPileIncrementeLeft(), carteMoved)) {
                            moveVerified = true;
                            partie.getPileIncrementeLeft().addCarte(carteMoved);
                            pileAddedTo = partie.getPileIncrementeLeft();
                        }
                    }
                    else if(destination == pileTopRight){
                        if(partie.verifyMove(partie.getPileIncrementeRight(), carteMoved)) {
                            moveVerified = true;
                            partie.getPileIncrementeRight().addCarte(carteMoved);
                            pileAddedTo = partie.getPileIncrementeRight();
                        }
                    }
                    else if(destination == pileBottomLeft){
                        if(partie.verifyMove(partie.getPileDecrementeLeft(), carteMoved)) {
                            moveVerified = true;
                            partie.getPileDecrementeLeft().addCarte(carteMoved);
                            pileAddedTo = partie.getPileDecrementeLeft();
                            System.out.println("xd");
                        }
                    }
                    else if(destination == pileBottomRight){
                        if(partie.verifyMove(partie.getPileDecrementeRight(), carteMoved)) {
                            moveVerified = true;
                            partie.getPileDecrementeRight().addCarte(carteMoved);
                            pileAddedTo = partie.getPileDecrementeRight();
                            System.out.println("hi");
                        }
                    }

                    if(moveVerified){
                        partie.removeCarteFromPile(partie.getPileCartesEnMain(), numeroCarte);
                        partie.rePopulateCartesEnMain();
                        moveCounter++;

                        partie.addScore(getTimeElapsed(previousTime), pileAddedTo);
                        previousTime = SystemClock.elapsedRealtime() - time.getBase();


                        if(partie.isFinished()){
                            Intent in = new Intent(ActivityJeu.this, ActivityFin.class);
                            GregorianCalendar date = new GregorianCalendar();
                            String dateString = (date.get(Calendar.DAY_OF_MONTH) + "-" + date.get(Calendar.MONTH) + "-" + date.get(Calendar.YEAR));
                            in.putExtra("date", dateString);
                            in.putExtra("score", partie.getScore());
                            startActivity(in);
                        }

                        //Vue
                        afficherScoreCards();
                        carte.setVisibility(View.INVISIBLE);

                        destination.setBackground(carte.getBackground());
                        destination.setText(carteTextView.getText());
                        if(moveCounter == 2) {
                            repopulateHand(partie.getPileCartesEnMain().getCarte(7).getNumber(), partie.getPileCartesEnMain().getCarte(6).getNumber());
                            moveCounter = 0;
                        }
                        moveVerified = false;

                    }
                    else{
                        carte.setVisibility(View.VISIBLE);
                    }
                    enteredDrop = true;
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    bsCounter++;
                    if(bsCounter == 4){
                        if(!enteredDrop) {
                            carte = (View) event.getLocalState();
                            carte.setVisibility(View.VISIBLE);

                        }
                        else
                            enteredDrop = false;
                        bsCounter = 0;
                    }
                    break;

            }
            return true;
        }
        @Override
        public boolean onTouch(View source, MotionEvent event) {
            //l'ombre qu'on va transporter d'une colonne a l'autre
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(source);
            int sdkVersion = Build.VERSION.SDK_INT;
            if (sdkVersion <= 24) {
                source.startDrag(null, shadowBuilder, source, View.DRAG_FLAG_OPAQUE);
            } else {
                source.startDragAndDrop(null, shadowBuilder, source, View.DRAG_FLAG_OPAQUE);
            }
            source.setVisibility(View.INVISIBLE); //pour cacher le jeton tant qu'il n'est pas dropped ailleurs
            return true;
        }

        @Override
        public void onClick(View source) {
            if (source == buttonStart) {
                resetPartie();
            }
            else if(source == buttonMenu){
                GregorianCalendar date = new GregorianCalendar();
                String dateString = (date.get(Calendar.DAY_OF_MONTH) + "-" + date.get(Calendar.MONTH) + "-" + date.get(Calendar.YEAR));
                Intent in = new Intent(ActivityJeu.this, Menu.class);
                in.putExtra("date", dateString);
                in.putExtra("score", partie.getScore());
                startActivity(in);
            }
        }
    }
}

