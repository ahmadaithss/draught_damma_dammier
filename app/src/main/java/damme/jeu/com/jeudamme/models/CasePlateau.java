package damme.jeu.com.jeudamme.models;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by ahmaddev on 31/03/2018.
 */

public class CasePlateau implements  Comparable{
    private int left;
    private int right;
    private int top;
    private int bottom;
    private int color;
    private boolean occupe;
    private int selected;
    private int posX;
    private int posY;
    private int num;
    private TreeSet<Integer> listAsupprimer;
    private CasePlateau prev;
    private CasePlateau next;

    public CasePlateau getPrev() {
        return prev;
    }

    public void setPrev(CasePlateau prev) {
        this.prev = prev;
    }

    public CasePlateau getNext() {
        return next;
    }

    public void setNext(CasePlateau next) {
        this.next = next;
    }

    public CasePlateau(int left, int right, int top, int bottom, int color, boolean occupe) {
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
        this.color = color;
        this.occupe = occupe;
        listAsupprimer=new TreeSet<>();
    }

    public TreeSet<Integer> getListAsupprimer() {
        return listAsupprimer;
    }

    public void setListAsupprimer(TreeSet<Integer> listAsupprimer) {
        this.listAsupprimer = listAsupprimer;
    }

    public boolean selected(int mousex, int mousey, int size)
    {
        if(mousex>=left&&mousex<=(size+left)&& mousey>=top && mousey<=(size+top))
            return true;
        else
            return false;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public boolean isOccupe() {
        return occupe;
    }

    public void setOccupe(boolean occupe) {
        this.occupe = occupe;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public int getBottom() {
        return bottom;
    }

    public void setBottom(int bottom) {
        this.bottom = bottom;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }
    public String toString(){
        return "( "+posX+" , "+posY+" )";
    }

    @Override
    public boolean equals(Object obj) {
        CasePlateau casePlateau=(CasePlateau)obj;
        return casePlateau.getPosX()==this.getPosX() && casePlateau.getPosY()==this.getPosY();
    }

    @Override
    public int compareTo(@NonNull Object o) {
        CasePlateau caseP=(CasePlateau) o;
        if (this.num==caseP.getNum())
            return 1;
        return 0;
    }
}
