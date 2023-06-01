package com.example.tpfinal;

public class Partie {
    private Pile pileIncrementeLeft, pileIncrementeRight, pileDecrementeLeft, pileDecrementeRight, pileCartes, pileCartesEnMain;
    private int score;



    public Partie(){
        pileCartes = new Pile();
        pileCartesEnMain = new Pile();
        pileIncrementeLeft = new Pile();
        pileIncrementeRight = new Pile();
        pileDecrementeLeft = new Pile();
        pileDecrementeRight = new Pile();
        score = 0;

        pileCartes.generatePile();
        pileCartes.shufflePile();

        populateCartesEnMain();
    }

    public Pile getPileIncrementeLeft() {
        return pileIncrementeLeft;
    }

    public Pile getPileIncrementeRight() {
        return pileIncrementeRight;
    }

    public Pile getPileDecrementeLeft() {
        return pileDecrementeLeft;
    }

    public Pile getPileDecrementeRight() {
        return pileDecrementeRight;
    }

    public Pile getPileCartes() {
        return pileCartes;
    }

    public Pile getPileCartesEnMain() { return pileCartesEnMain; }

    public int getScore() { return score; }

    public boolean verifyMove(Pile pile, Carte selectedCarte){
        if(pile.isEmpty()){
            return true;
        }
        else if(pile.equals(pileIncrementeLeft) || pile.equals(pileIncrementeRight)){
            if(selectedCarte.getNumber() > pile.getTopCarte().getNumber() || pile.getTopCarte().getNumber() - 10 == selectedCarte.getNumber()){
                rePopulateCartesEnMain();
                return true;
            }
        }
        else{
            if(selectedCarte.getNumber() < pile.getTopCarte().getNumber() || pile.getTopCarte().getNumber() + 10 == selectedCarte.getNumber()){
                rePopulateCartesEnMain();
                return true;
            }
        }
        return false;
    }

    public void populateCartesEnMain(){
        for(int i = 1; i <= 8; i++){
            pileCartesEnMain.addCarte(pileCartes.getTopCarte());
            pileCartes.popCarte();
        }
    }

    public Carte getCarteByNumber(Pile pile, int num){
        for(int i = 0; i < pile.getSize(); i++){
            if(pile.getCarte(i).getNumber() == num)
                return pile.getCarte(i);
        }
        return null;
    }

    public void removeCarteFromPile(Pile pile, int num){
        for(int i = 0; i < pile.getSize(); i++){
            if(pile.getCarte(i).getNumber() == num)
                pile.removeCarte(i);
        }
    }

    public void rePopulateCartesEnMain(){
        if(pileCartesEnMain.getSize() <= 6) {
            for (int i = 0; i < 2; i++) {
                pileCartesEnMain.addCarte(pileCartes.getTopCarte());
                pileCartes.popCarte();
            }
        }
    }

    //calcul de score
    public void addScore(int secondsPast, Pile pile){
        int difference, index1, index2 = 0;
        if(pile.getSize() <= 1){
            index1 = 0;
            index2 = 0;
        }
        else{
            index1 = pile.getSize() - 1;
            index2 = pile.getSize() - 2;
        }

        int preAbs =pile.getCarte(index1).getNumber() - pile.getCarte(index2).getNumber();

        if(preAbs == - 10){
            if(pile == pileIncrementeLeft || pile == pileIncrementeRight) {
                score += 4000;
                return;
            }
        }
        else if(preAbs == 10){
            if(pile == pileDecrementeLeft || pile == pileDecrementeRight){
                score += 4000;
                return;
            }
        }
        difference = Math.abs(preAbs);
        if(difference == 0){
            difference = 1;
        }
        if(secondsPast == 0){
            secondsPast = 1;
        }

        score += (10 * (200/difference)) + (10 * (200/secondsPast));
    }

    //verifie si la partie est termine
    public boolean isFinished(){
        if(pileCartes.getSize() + pileCartesEnMain.getSize() == 0){
            return true;
        }
        else{
            for(int i = 0; i < pileCartesEnMain.getSize(); i++){
                if(verifyMove(pileDecrementeLeft, pileCartesEnMain.getCarte(i)))
                    return false;
                else if(verifyMove(pileDecrementeRight, pileCartesEnMain.getCarte(i)))
                    return false;
                else if(verifyMove(pileIncrementeLeft, pileCartesEnMain.getCarte(i)))
                    return false;
                else if(verifyMove(pileIncrementeRight, pileCartesEnMain.getCarte(i)))
                    return false;

            }

            return true;
        }

    }

}
