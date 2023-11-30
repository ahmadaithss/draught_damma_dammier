package damme.jeu.com.jeudamme.models;

import android.app.Activity;

import java.util.ArrayList;

public class Pion{
	private int pionState;
	private int posX;
	private int PosY;
	private int num;
	private boolean damier;
	private PionColor pionColor;
	private ArrayList<CasePlateau> possibilities;

	public Pion(PionColor pionColor,int posX,int posY){
		this.pionColor=pionColor;
		this.posX=posX;
		this.PosY=posY;
		possibilities=new ArrayList<>();
	}
	public int getPionState(){
		return pionState;
	}
	public void setPionState(int pionState){
		this.pionState=pionState;
	}
	public int getPosX() {
		return posX;
	}
	public void setPosX(int posX) {
		this.posX = posX;
	}
	public int getPosY() {
		return PosY;
	}
	public void setPosY(int posY) {
		PosY = posY;
	}
    public int getNum(){
		return this.num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	@Override
	public boolean equals(Object obj) {
		Pion p=(Pion)obj;
		return p.getPosX()==this.getPosX() && p.getPosY()==this.getPosY() || (p.getNum() == this.num );

	}

	public boolean isDamier() {
		return damier;
	}

	public void setDamier(boolean damier) {
		this.damier = damier;
	}

	public PionColor getPionColor() {
		return pionColor;
	}

	public void setPionColor(PionColor pionColor) {
		this.pionColor = pionColor;
	}

	public String toString(){
		return "( "+posX+" , "+PosY+" )";
	}
}
