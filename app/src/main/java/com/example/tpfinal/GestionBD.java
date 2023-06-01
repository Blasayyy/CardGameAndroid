package com.example.tpfinal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Vector;


public class GestionBD extends SQLiteOpenHelper {
    //instance unique de la classe Singleton GestionBD
    private static GestionBD instance;
    private SQLiteDatabase database;


    // méthode de base pour un Singleton
    public static GestionBD getInstance( Context contexte )
    {
        if ( instance == null )
            instance = new GestionBD(contexte);
        return instance;
    }

    private GestionBD( Context context) {
        super(context, "database", null, 1);
        // je me connecte à la BD tout de suite
    }

    public void ajouterScore(int score, String date){
        ContentValues cv = new ContentValues();
        cv.put("Score", score);
        cv.put("Date", date);
        database.insert("Scores", null, cv);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL("CREATE TABLE Scores ( _id INTEGER PRIMARY KEY AUTOINCREMENT, Score INTEGER,  Date TEXT);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS Scores");
        onCreate(database);
    }

    public void ouvrirBD() {
        database = this.getWritableDatabase();
    }

    public void fermerBD() {
        database.close();
    }

    public ArrayList<String> getTop10(){
        ArrayList<String> top10Scores = new ArrayList();
        int counter = 0;
        Cursor cursor = database.rawQuery("SELECT Score, Date FROM Scores ORDER BY Score DESC", null);
        while(cursor.moveToNext() && counter < 10){
            String score = cursor.getString(0) + "   :    " + cursor.getString(1);
            top10Scores.add(score);
            counter++;
        }
        return top10Scores;
    }


}
