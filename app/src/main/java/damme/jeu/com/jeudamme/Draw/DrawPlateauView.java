package damme.jeu.com.jeudamme.Draw;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import damme.jeu.com.jeudamme.MainActivity;
import damme.jeu.com.jeudamme.R;
import damme.jeu.com.jeudamme.models.CasePlateau;
import damme.jeu.com.jeudamme.models.Pion;
import damme.jeu.com.jeudamme.models.PionColor;
import damme.jeu.com.jeudamme.models.Plateau;
import damme.jeu.com.jeudamme.models.Player;

/**
 * Created by ahmaddev on 31/03/2018.
 */

public class DrawPlateauView extends View {
    private Paint drawPaint;
    private Plateau plateau;
    private int parentWidth;
    private Pion selectedPion;
    private Player lastPlayed;
    private Player player1;
    private Player player2;
    private ArrayList<CasePlateau> possibilities;


    public DrawPlateauView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.player1=new Player("player1",1,PionColor.BLACK);
        this.player2=new Player("player2",2,PionColor.RED);

        this.lastPlayed=player1;
        setFocusable(true);
        setFocusableInTouchMode(true);
        setupPaint();

    }

    public void setupPaint(){
        drawPaint=new Paint();

        //drawPaint.setColor(paintColor);
        //drawPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawPlateau(canvas);

    }

    public Plateau getPlateau() {
        return plateau;
    }

    private void drawPlateau(Canvas canvas){
        CasePlateau[][] casePlateau=plateau.getCasePlateau();
        Pion[][] pionTab=plateau.getTabPions();
        float cx = 0;
        float cy=0;
        int size=(this.parentWidth/plateau.getDimension());
        for(int i=0;i<plateau.getDimension();i++){
            for(int j=0;j<plateau.getDimension();j++){
                Log.d("color : ",casePlateau[i][j].getColor()+"");
                drawPaint.setColor(casePlateau[i][j].getColor());
                canvas.drawRect(casePlateau[i][j].getLeft(),casePlateau[i][j].getTop(),casePlateau[i][j].getRight(),casePlateau[i][j].getBottom(),drawPaint);
                Log.d("coord : ",casePlateau[i][j].getLeft()+","+casePlateau[i][j].getRight()+","+casePlateau[i][j].getTop()+","+casePlateau[i][j].getBottom());
                drawPions(pionTab[i][j],casePlateau[i][j],size,canvas,cx,cy);

            }
        }
    }

    private void drawPions(Pion pion,CasePlateau casePlateau,int size,Canvas canvas,float cx,float cy){
        if(pion!=null){
            Bitmap icon=BitmapFactory.decodeResource(this.getResources(), R.drawable.pion2);;
            if(PionColor.RED.equals(pion.getPionColor())){
                icon = BitmapFactory.decodeResource(this.getResources(), R.drawable.pion2);
            }
            if (pion.isDamier() && PionColor.RED.equals(pion.getPionColor())){
                icon = BitmapFactory.decodeResource(this.getResources(), R.drawable.pion2damier);
            }
            if(PionColor.BLACK.equals(pion.getPionColor())){
                icon = BitmapFactory.decodeResource(this.getResources(), R.drawable.pion1);
            }
            if (PionColor.BLACK.equals(pion.getPionColor())&& pion.isDamier()){
                icon = BitmapFactory.decodeResource(this.getResources(), R.drawable.pion1damier);
            }
            int scale=size-((size*20)/100);
            Bitmap bitmap=Bitmap.createScaledBitmap(icon,scale,scale,false);
            cx=casePlateau.getLeft()+((size*10)/100);
            cy=casePlateau.getTop()+((size*10)/100);
            canvas.drawBitmap(bitmap,cx,cy,drawPaint);
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        this.parentWidth = MeasureSpec.getSize(widthMeasureSpec);
       // this.parentHeight = MeasureSpec.getSize(heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        plateau=new Plateau(8,this.parentWidth);
        MainActivity.numberOfRedPions.setText(" "+plateau.getGetNumberOfRed());
        MainActivity.numberOfBlackPions.setText(" "+plateau.getNumberOfBlack());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x=(int)event.getX();
        int y=(int)event.getY();
        // Checks for the event that occurs
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                     CasePlateau caseP=plateau.getSelectedCase(x,y);
                    if(caseP!=null) {
                        Log.i(" Case numero",caseP.getNum()+" ");
                    plateau.resetColors();
                    int posX=caseP.getPosX();
                    int posY=caseP.getPosY();
                    Pion pion = plateau.choosePion(posX, posY);
                    if (pion != null){
                        if(!this.lastPlayed.getPionColor().equals(pion.getPionColor()) ) {
                            this.possibilities = plateau.getPossibilities(posX, posY,pion);
                        }
                        this.selectedPion = pion;
                    } else {
                            if (!this.possibilities.isEmpty()) {
                                int index = plateau.isInPossibilities(possibilities,posX, posY);
                                if (index >= 0) {
                                    int[] eval=plateau.deplacerPion(selectedPion, posX, posY);
                                    MainActivity.numberOfBlackPions.setText(" "+eval[0]);
                                    MainActivity.numberOfRedPions.setText(" "+eval[1]);
                                    possibilities.clear();
                                    if(eval[0]==0){
                                        Toast.makeText(getContext(),"Red win",Toast.LENGTH_SHORT).show();
                                    }
                                    if(eval[1]==0){
                                        Toast.makeText(getContext(),"Black win",Toast.LENGTH_SHORT).show();
                                    }
                                    plateau.resetColors();
                                    this.lastPlayed=this.lastPlayed.equals(this.player1) ? this.player2 : this.player1;
                                }
                                this.possibilities.clear();

                            }
                        }
                }
                invalidate();
                return true;
            case MotionEvent.ACTION_MOVE:
                break;
            default:
                return false;
        }
        // Force a view to draw again
        postInvalidate();
        return true;
    }
}
