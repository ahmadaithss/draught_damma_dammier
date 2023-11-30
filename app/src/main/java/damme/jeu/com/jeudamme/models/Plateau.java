package damme.jeu.com.jeudamme.models;

import android.graphics.Color;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class Plateau {
    private Pion[][] tabPions;
    private int numberOfBlack;
    private int getNumberOfRed;
    private int nbPions;
    private int size;
    private boolean priseRequired=false;
    public static CasePlateau[][] casePlateau;
    public static HashMap<CasePlateau, ArrayList<Pion>> pionsToDelete;
    private int player;
    public static ArrayList deletedPionsPossibilities;

    public Plateau(int nbPions, int parentWidth) {
        this.nbPions = nbPions;
        numberOfBlack=0;
        getNumberOfRed=0;
        this.tabPions = new Pion[this.nbPions][this.nbPions];
        this.casePlateau = new CasePlateau[this.nbPions][this.nbPions];
        pionsToDelete = new HashMap<>();
        this.initPlateau();
        this.initPlateau(parentWidth);
        deletedPionsPossibilities = new ArrayList();
    }

    public void initPlateau() {
        initPlateauPlayer();
    }

    public void resetPlateau() {
        this.tabPions = new Pion[this.nbPions][this.nbPions];
        numberOfBlack=0;
        getNumberOfRed=0;
        this.initPlateau();
        this.resetColors();
    }

    public void initPlateauPlayer() {
        for (int i = 0; i < nbPions; i++) {
            for (int j = 0; j < nbPions; j++) {
                if ((i + j) % 2 == 0) {
                    if (i < ((nbPions / 2) - 1)) {
                        this.tabPions[i][j] = new Pion(PionColor.RED, i, j);
                        getNumberOfRed++;
                    } else {
                        if ((i + j) % 2 == 0 && i > nbPions / 2) {
                            tabPions[i][j] = new Pion(PionColor.BLACK, i, j);
                            numberOfBlack++;
                        }
                    }

                }
            }
        }
    }

    public CasePlateau[][] getCasePlateau() {
        return this.casePlateau;
    }

    public void setCasePlateau(CasePlateau[][] casePlateau) {
        this.casePlateau = casePlateau;
    }

    public void showPlateau() {
		/*for(int i=0;i<this.nbPions;i++){
			
			System.out.print("\t");
		}*/
        System.out.println(Arrays.deepToString(tabPions));
        for (int i = 0; i < this.nbPions; i++) {
            for (int j = 0; j < nbPions; j++) {
                if (this.tabPions[i][j] != null) {
                    System.out.print(this.tabPions[i][j].getPionState() + "(" + i + "," + j + ")" + "- \t");
                } else {
                    System.out.print(0 + " (" + i + "," + j + ")" + "- \t");
                }
            }
            System.out.println();
        }
    }

    private void initPlateau(int parentWidth) {
        casePlateau = new CasePlateau[this.nbPions][this.nbPions];
        //Pion[][] tabPions=plateau.getTabPions();
        this.size = parentWidth / this.nbPions;
        //Log.d("sizedim",size+"");
        int left = 0;
        int top = 0;
        int right = size;
        int bottom = size;
        int number = 1;

        for (int i = 0; i < this.nbPions; i++) {

            for (int j = 0; j < this.nbPions; j++) {
                if ((i + j) % 2 == 0) {
                    this.casePlateau[i][j] = new CasePlateau(left, right, top, bottom, Color.rgb(217, 162, 97), false);

                    //drawPaint.setColor(Color.BLACK);
                } else {
                    // drawPaint.setColor(Color.YELLOW);
                    this.casePlateau[i][j] = new CasePlateau(left, right, top, bottom, Color.rgb(143, 80, 27), false);
                }
                this.casePlateau[i][j].setPosX(i);
                this.casePlateau[i][j].setPosY(j);
                this.casePlateau[i][j].setNum(number);
                //canvas.drawRect(left,top,right,bottom,drawPaint);
                left += size;
                right += size;
                number++;
            }
            top += size;
            bottom += size;
            left = 0;

        }
    }

    public void resetColors() {
        for (int i = 0; i < this.nbPions; i++) {
            for (int j = 0; j < this.nbPions; j++) {
                if ((i + j) % 2 == 0) {
                    this.casePlateau[i][j].setColor(Color.rgb(217, 162, 97));
                } else {
                    this.casePlateau[i][j].setColor(Color.rgb(143, 80, 27));
                }
            }
        }
    }

    public CasePlateau getSelectedCase(int x, int y) {
        for (int i = 0; i < nbPions; i++) {

            for (int j = 0; j < nbPions; j++) {
                if (casePlateau[i][j].selected(x, y, size)) {
                    // casePlateau[i][j].setColor(Color.RED);
                    return casePlateau[i][j];
                }
            }

        }
        return null;
    }

    public int isInPossibilities(ArrayList<CasePlateau> possibilities, int x, int y) {
        return possibilities.indexOf(casePlateau[x][y]);
    }

    public void resetPrev() {
        for (int i = 0; i < this.nbPions; i++) {
            for (int j = 0; j < this.nbPions; j++) {
                casePlateau[i][j].setPrev(null);
            }
        }
    }

    // delete pions
    public void deletePions(int posX, int posY, int nPosX, int nPosY) {
        CasePlateau casePSelected = casePlateau[nPosX][nPosY];
        CasePlateau casePSelectedPrev = casePSelected.getPrev();
        int i = 1;
        while (casePSelectedPrev != null) {
            if(tabPions[casePSelectedPrev.getPosX()][casePSelectedPrev.getPosY()] != null){
                if(PionColor.BLACK.equals(tabPions[casePSelectedPrev.getPosX()][casePSelectedPrev.getPosY()].getPionColor())){
                    numberOfBlack--;
                }else if (PionColor.RED.equals(tabPions[casePSelectedPrev.getPosX()][casePSelectedPrev.getPosY()].getPionColor())){
                    getNumberOfRed--;
                }
            }
            tabPions[casePSelectedPrev.getPosX()][casePSelectedPrev.getPosY()] = null;
            if (casePSelectedPrev.getNum() == casePlateau[posX][posY].getNum()) break;
            casePSelectedPrev = casePSelectedPrev.getPrev();
            i++;
        }
        resetPrev();
    }

    public CasePlateau getCasePlateauFromNum(int n) {
        for (int i = 0; i < casePlateau.length; i++) {
            for (int j = 0; j < casePlateau[i].length; j++) {
                if (casePlateau[i][j].getNum() == n) {
                    return casePlateau[i][j];
                }
            }
        }
        return null;
    }

    public void damierRightPossibilities(ArrayList<CasePlateau> casePossible,int i,int j,Pion originePion,PionColor pionToEat,boolean deep){
        //partie droite
        if (i + 1 < this.nbPions && j + 1 < this.nbPions) {
            //case vide a droite
            if (tabPions[i + 1][j + 1] == null && !deep) {
                casePossible.add(casePlateau[i + 1][j + 1]);
                casePlateau[i + 1][j + 1].setColor(Color.rgb(15, 137, 216));
            }

         }
    }


    public void rightPossibilities(ArrayList<CasePlateau> casePossible, int i, int j,boolean deep, Pion originePion) {

        PionColor state = null;
        if (PionColor.BLACK.equals(originePion.getPionColor())) {
            state = PionColor.RED;
        }else if (PionColor.RED.equals(originePion.getPionColor())) {
            state=PionColor.BLACK;
        }
        if (PionColor.RED.equals(originePion.getPionColor()) || (PionColor.BLACK.equals(originePion.getPionColor()) && originePion.isDamier())) {
            //partie droite
            if (i + 1 < this.nbPions && j + 1 < this.nbPions) {
                //case vide a droite
                if (tabPions[i + 1][j + 1] == null) {
                    casePossible.add(casePlateau[i + 1][j + 1]);
                    casePlateau[i + 1][j + 1].setColor(Color.rgb(15, 137, 216));

                }else if (tabPions[i + 1][j + 1] != null) {
                    int k = 1;
                    //case contient un pion de joueur 1
                    while (i + k < this.nbPions && j + k < this.nbPions) {
                        if (tabPions[i + k][j + k] != null) {
                            if (tabPions[i + k][j + k].getPionColor().equals(state)) {
                                casePlateau[i + k][j + k].setPrev(casePlateau[i + k - 1][j + k - 1]);
                                // definir la liste des possibilites
                                if (i + k + 1 < this.nbPions && j + k + 1 < this.nbPions) {

                                    if (this.tabPions[i + k + 1][j + k + 1] == null) {

                                        casePlateau[i + k + 1][j + k + 1].setColor(Color.rgb(15, 137, 216));
                                        casePossible.add(casePlateau[i + k + 1][j + k + 1]);
                                        casePlateau[i + k + 1][j + k + 1].setPrev(casePlateau[i + k][j + k]);
                                        /** ajouter les element a supprimer**/
                                        casePlateau[originePion.getPosX()][originePion.getPosY()].getListAsupprimer().add(casePlateau[i + k][j + k].getNum());
                                        Log.i("liste a supprimer(" + i + "," + j + ")", casePlateau[i][j].getListAsupprimer() + "");
                                        if (i + k + 2 < this.nbPions) {
                                            // besoin de changement
                                            // tester s'il ya des autres possibilities a gauche
                                            if (tabPions[i + (k + 2)][j + k] != null) {
                                                if (tabPions[i + k + 2][j + k].getPionColor().equals(state)) {
                                                    casePlateau[i + k + 2][j + k].setPrev(casePlateau[i + k + 1][j + k + 1]);
                                                    leftPossibilities(casePossible, i + (k + 1), j + (k + 1), true, originePion);
                                                }
                                            }
                                            if (j + k + 1 < this.nbPions && j + k + 2 < this.nbPions) {
                                                if (this.tabPions[i + k + 1][j + k + 1] != null || this.tabPions[i + k + 2][j + k + 2] == null) {
                                                    break;
                                                }
                                            }
                                        }
                                        //pour le damier
                                        if (i + k - 1 >= 0 && j + k + 2 < this.nbPions) {
                                            if (tabPions[i + k][j + k + 2] != null) {
                                                if (tabPions[i + k][j + k + 2].getPionColor().equals(state)) {
                                                    casePlateau[i + k][j + k + 2].setPrev(casePlateau[i + k + 1][j + k + 1]);
                                                    //Log.i("pion : " + casePlateau[i + k][j + k ].getNum(), " next : " + casePlateau[i + k][j + k ].getNext().getNum());
                                                    leftPossibilities(casePossible, i + k + 1, j + k + 1,  true, originePion);
                                                }
                                            }
                                        }

                                    } else break;


                                }


                            } else break;
                        }

                        k++;
                    }

                }
            }
        }
        if (PionColor.BLACK.equals(originePion.getPionColor()) || (PionColor.RED.equals(originePion.getPionColor()) && originePion.isDamier())) {
            if (i - 1 >= 0 && j - 1 >= 0) {

                //case vide a droite
                if (tabPions[i - 1][j - 1] == null) {
                    casePossible.add(casePlateau[i - 1][j - 1]);
                    casePlateau[i - 1][j - 1].setColor(Color.rgb(15, 137, 216));

                }else if (tabPions[i - 1][j - 1] != null) {
                    int k = 1;
                    //case contient un pion de joueur 1
                    while (i - k >= 0 && j - k >= 0) {
                        if (tabPions[i - k][j - k] != null) {

                            if (tabPions[i - k][j - k].getPionColor().equals(state)) {
                                casePlateau[i - k][j - k].setPrev(casePlateau[i - k + 1][j - k + 1]);
                                //Log.i("prev for " + casePlateau[i-k][j-k].getNum(), " is : " + casePlateau[i][j].getPrev().getNum());
                                if (i - k - 1 >= 0 && j - k - 1 >= 0) {
                                    // definir la liste des possibilites
                                    if (this.tabPions[i - k - 1][j - k - 1] == null) {

                                        casePlateau[i - k - 1][j - k - 1].setColor(Color.rgb(15, 137, 216));
                                        casePossible.add(casePlateau[i - k - 1][j - k - 1]);
                                        casePlateau[i - k - 1][j - k - 1].setPrev(casePlateau[i - k][j - k]);
                                        //Log.i("prev for " + casePlateau[i-k-1][j-k-1].getNum(), " is : " + casePlateau[i-k][j-k].getPrev().getNum());

                                        //casePlateau[i-k][j-k].setNext(casePlateau[i-k-1][j-k-1]);
                                        //Log.i("pion (not deep) : " + casePlateau[i-k][j-k].getNum(), " next : " + casePlateau[i-k][j-k].getNext().getNum());
                                        /** ajouter les element a supprimer**/
                                        casePlateau[originePion.getPosX()][originePion.getPosY()].getListAsupprimer().add(casePlateau[i - k][j - k].getNum());
                                        Log.i("liste a supprimer(" + i + "," + j + ")", casePlateau[i][j].getListAsupprimer() + "");
                                        if (i - k - 2 >= 0 && i - k - 1 >= 0 && j - k >= 0) {
                                            // tester s'il ya des autres possibilites a gauche
                                            if (tabPions[i - k - 2][j - k] != null) {
                                                if (tabPions[i - k - 2][j - k].getPionColor().equals(state)) {
                                                    casePlateau[i - k - 2][j - k].setPrev(casePlateau[i - k - 1][j - k - 1]);
                                                    //Log.i("prev for " + casePlateau[i-k-2][j-k].getNum(), " is : " + casePlateau[i-k-1][j-k-1].getPrev().getNum());
                                                    //Log.i("pion : " + casePlateau[i-k][j-k].getNum(), " next : " + casePlateau[i-k][j-k].getNext().getNum());
                                                    leftPossibilities(casePossible, i - k - 1, j - k - 1,  true, originePion);
                                                }
                                            }
                                            if (j - k - 1 >= 0 && j - k - 2 >= 0) {
                                                if (this.tabPions[i - k - 1][j - k - 1] != null || this.tabPions[i - k - 2][j - k - 2] == null) {
                                                    break;
                                                }
                                            }
                                        }

                                        // pour le damier
                                        if (i - k >= 0 && j - k - 2 >= 0) {
                                            //Log.i("tested poss","["+(i-k)+","+(j-k-2)+"]");
                                            if (tabPions[i - k][j - k - 2] != null) {
                                                if (tabPions[i - k][j - k - 2].getPionColor().equals(state)) {
                                                    casePlateau[i - k][j - k - 2].setPrev(casePlateau[i - k - 1][j - k - 1]);
                                                    //Log.i("prev for " + casePlateau[i-k][j-k-2].getNum(), " is : " + casePlateau[i-k-1][j-k-1].getPrev().getNum());
                                                    //Log.i("pion : " + casePlateau[i - k][j - k ].getNum(), " next : " + casePlateau[i - k][j - k ].getNext().getNum());
                                                    leftPossibilities(casePossible, i - k - 1, j - k - 1, true, originePion);
                                                }
                                            }
                                        }
                                    } else break;

                                }

                            } else break;
                        }

                        k++;
                    }

                }
            }
        }
    }

    public void leftPossibilities(ArrayList<CasePlateau> casePossible, int i, int j, boolean deep, Pion originePion) {

        PionColor state = null;
        if (PionColor.BLACK.equals(originePion.getPionColor())) {
            state = PionColor.RED;
        }else if (PionColor.RED.equals(originePion.getPionColor())) {
            state=PionColor.BLACK;
        }
        if (PionColor.RED.equals(originePion.getPionColor()) || (PionColor.BLACK.equals(originePion.getPionColor()) && originePion.isDamier())) {
            int posX=i;
            int posY=j;
            int lastPositionX=posX;
            int lastPositionY=posY;
            if (posX+1< this.nbPions && posY-1 >= 0) {
                if (tabPions[posX+1][posY-1] == null) {
                    casePlateau[posX+1][posY-1].setColor(Color.rgb(15, 137, 216));
                    casePossible.add(casePlateau[posX+1][posY-1]);

                    if(originePion.isDamier()){

                        while (posX+1<this.nbPions && posY-1>=0 && tabPions[posX][posY]==null){
                            casePlateau[posX+1][posY-1].setColor(Color.rgb(15, 137, 216));
                            casePossible.add(casePlateau[posX+1][posY-1]);
                            lastPositionX=posX;
                            lastPositionY=posY;
                            posX++;
                            posY--;
                        }
                    }
                }
                if(originePion.isDamier()){
                    //pour recuperer la dernier position !=null
                    i=lastPositionX;
                    j=lastPositionY;
                }
                if (tabPions[i+1][j-1] != null) {
                     int k = 1;
                    //case contient un pion de joueur 1
                    while (i+k< this.nbPions && j-k >= 0) {
                        if (tabPions[i+k][j-k] != null) {
                            if (tabPions[i+k][j-k].getPionColor().equals(state)) {
                                casePlateau[i+k][j-k].setPrev(casePlateau[i + k - 1][j - k + 1]);
                                if (i + k + 1 < this.nbPions && j - k - 1 >= 0) {
                                    if (this.tabPions[i + k + 1][j - k - 1] == null) {
                                        casePlateau[i + k + 1][j - k - 1].setColor(Color.rgb(15, 137, 216));
                                        casePossible.add(casePlateau[i + k + 1][j - k - 1]);
                                        casePlateau[i + k + 1][j - k - 1].setPrev(casePlateau[i + k][j - k]);
                                        /** ajouter les element a supprimer**/
                                        casePlateau[originePion.getPosX()][originePion.getPosY()].getListAsupprimer().add(casePlateau[i + k][j - k].getNum());
                                        Log.i("liste a supprimer(" + i + "," + j + ")", casePlateau[i][j].getListAsupprimer() + "");
                                        if (j - k - 1 >= 0 && i + k + 2 < this.nbPions) {
                                            // tester s'il ya des autres possibilities a droite
                                            if (tabPions[i + k + 2][j - k] != null) {
                                                if (tabPions[i + k + 2][j - k].getPionColor().equals(state)) {
                                                    casePlateau[i + k + 2][j - k].setPrev(casePlateau[i + k + 1][j - k - 1]);
                                                    rightPossibilities(casePossible, i + (k + 1), j - k - 1, true, originePion);
                                                }
                                            }
                                            if (i - k + 1 >= 0 && j - k - 1 >= 0 && i + k + 2 < this.nbPions && j - k - 2 >= 0) {
                                                if (this.tabPions[i - k + 1][j - k - 1] != null || this.tabPions[i + k + 2][j - k - 2] == null) {
                                                    break;
                                                }
                                            }
                                        }

                                        //pour le damier
                                        if (i + k - 1 >= 0 && j - k - 2 >= 0) {

                                            if (tabPions[i + k - 1][j - k - 2] != null) {
                                                if (tabPions[i + k - 1][j - k - 2].getPionColor().equals(state)) {
                                                    casePlateau[i + k - 1][j - k - 2].setPrev(casePlateau[i + k + 1][j - k - 1]);
                                                    rightPossibilities(casePossible, i + k, j - k - 1, true, originePion);
                                                }
                                            }
                                        }
                                    } else break;

                                }


                            } else break;
                        }

                        k++;
                    }
                }
            }
        }
        if (PionColor.BLACK.equals(originePion.getPionColor()) || (PionColor.RED.equals(originePion.getPionColor()) && originePion.isDamier())) {
            if (i - 1 >= 0 && j + 1 < this.nbPions) {
                if (tabPions[i - 1][j + 1] == null) {
                    casePlateau[i - 1][j + 1].setColor(Color.rgb(15, 137, 216));
                    casePossible.add(casePlateau[i - 1][j + 1]);
                }else if (tabPions[i - 1][j + 1] != null) {
                    int k = 1;
                    //case contient un pion de joueur 1
                    while (i - k >= 0 && j + k < this.nbPions) {
                        if (tabPions[i - k][j + k] != null) {

                            if (tabPions[i - k][j + k].getPionColor().equals(state)) {
                                casePlateau[i - k][j + k].setPrev(casePlateau[i - k + 1][j + k - 1]);
                                if (i - k - 1 >= 0 && j + k + 1 < this.nbPions) {
                                    if (this.tabPions[i - k - 1][j + k + 1] == null) {
                                        casePlateau[i - k - 1][j + k + 1].setColor(Color.rgb(15, 137, 216));
                                        casePossible.add(casePlateau[i - k - 1][j + k + 1]);
                                        casePlateau[i - k - 1][j + k + 1].setPrev(casePlateau[i - k][j + k]);
                                        /** ajouter les element a supprimer**/
                                        casePlateau[originePion.getPosX()][originePion.getPosY()].getListAsupprimer().add(casePlateau[i - k][j + k].getNum());
                                        Log.i("liste a supprimer(" + i + "," + j + ")", casePlateau[i][j].getListAsupprimer() + "");
                                        if (i - k - 2 >= 0 && j + k + 1 < this.nbPions) {

                                            // tester s'il ya des autres possibilities a droite
                                            if (tabPions[i - k - 2][j + k] != null) {
                                                if (tabPions[i - k - 2][j + k].getPionColor().equals(state)) {
                                                    casePlateau[i - k - 2][j + k].setPrev(casePlateau[i - k - 1][j + k + 1]);
                                                    rightPossibilities(casePossible, i - k - 1, j + (k + 1), true, originePion);
                                                }
                                            }
                                            // ajouter
                                            if (i - k - 1 >= 0 && j + k + 2 < this.nbPions) {
                                                if (this.tabPions[i - k - 1][j + k + 1] != null || this.tabPions[i - k - 2][j + k + 2] == null) {
                                                    break;
                                                }
                                            }
                                        }
                                        if (i - k >= 0 && j + k + 2 < this.nbPions) {
                                            if (tabPions[i - k][j + k + 2] != null) {
                                                if (tabPions[i - k][j + k + 2].getPionColor().equals(state)) {
                                                    casePlateau[i - k][j + k + 2].setPrev(casePlateau[i - k - 1][j + k + 1]);
                                                    leftPossibilities(casePossible, i, j + k,  true, originePion);
                                                }
                                            }
                                        }

                                    } else break;


                                }

                            } else break;
                        }

                        k++;
                    }
                }
            }
        }
    }

    // get possibilities and pions to delete
    public ArrayList<CasePlateau> getPossibilities(int i, int j, Pion originePion) {
        ArrayList<CasePlateau> casePossible = new ArrayList<>();
        //tester s'il s'agit pas d'une case vide
        if (tabPions[i][j] != null) {
            // partie droite
            rightPossibilities(casePossible, i, j, false, originePion);
            // partie gauchue
            leftPossibilities(casePossible, i, j,  false, originePion);
        }
        return casePossible;
    }

    public int[] deplacerPion(Pion p, int Ni, int Nj) {
        if (PionColor.RED.equals(p.getPionColor())) {
            if (Ni == this.nbPions - 1) {
                p.setDamier(true);
            }
        }
        if (PionColor.BLACK.equals(p.getPionColor())) {
            if (Ni == 0) {
                p.setDamier(true);
            }
        }
        tabPions[p.getPosX()][p.getPosY()] = null;
        p.setPosX(Ni);
        p.setPosY(Nj);
        tabPions[Ni][Nj] = p;
        deletePions(p.getPosX(),p.getPosY(),Ni,Nj);
        return evaluate();
    }

    private int[] evaluate(){
        return new int[]{numberOfBlack,getNumberOfRed};
    }

    private boolean hasMorePossibilities(PionColor pionColor){
        for(int i=0;i<tabPions.length;i++){
            for (int j=0;j<tabPions.length;j++){
                if(tabPions[i][j]!=null && pionColor.equals(tabPions[i][j].getPionColor())){
                    List<CasePlateau> liste=getPossibilities(i,j,tabPions[i][j]);
                    if(liste.size()>0){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public Pion choosePion(int x, int y) {
        if (tabPions[x][y] != null) {
            return tabPions[x][y];
        } else
            return null;
    }

    public Pion[][] getTabPions() {
        return tabPions;
    }

    public void setTabPions(Pion[][] tabPions) {
        this.tabPions = tabPions;
    }

    public int getDimension() {
        return this.nbPions;
    }

    public int getNumberOfBlack() {
        return numberOfBlack;
    }

    public int getGetNumberOfRed() {
        return getNumberOfRed;
    }
}
