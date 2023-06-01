package com.example.tpfinal;

import java.util.ArrayList;
import java.util.Collections;

public class Pile {
    private final int NUMCARTES = 97;
    private ArrayList<Carte> listeCarte;


    //constructeurs
    public Pile(){
        this.listeCarte = new ArrayList<Carte>();
    }

    public Pile(Carte carte){
        this.listeCarte.add(carte);
    }

    public Pile(ArrayList<Carte> listeCarte){
        this.listeCarte = listeCarte;
    }


    //generer une pile
    public void generatePile(){
        for(int i = 1; i <= NUMCARTES; i++){
            listeCarte.add(new Carte(i));
        }
    }

    public void removeCarte(int index){
        listeCarte.remove(index);
    }

    public void shufflePile(){
        Collections.shuffle(listeCarte);
    }

    public Carte getTopCarte(){
        return listeCarte.get(listeCarte.size() - 1);
    }

    public Carte getCarte(int index){ return listeCarte.get(index);}

    public void addCarte(Carte carte){
        listeCarte.add(carte);
    }

    public void popCarte(){
        listeCarte.remove(listeCarte.size() - 1);
    }

    public boolean isEmpty(){
        return listeCarte.isEmpty();
    }

    public int getSize() { return listeCarte.size(); }


}
