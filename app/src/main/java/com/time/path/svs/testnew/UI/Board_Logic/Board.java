package com.time.path.svs.testnew.UI.Board_Logic;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;

import com.time.path.svs.testnew.R;
import com.time.path.svs.testnew.Settings.settings;

/**
 * Created by santiago on 3/11/15.
 */
public class Board {

    private BoardSlot [][]fullBoard;



    private int x,y,rows,cols;

    settings appsettings;


    private boolean vibrate;

    private boolean animate;



    public Button getButton(int i, int j){
        if(i<this.fullBoard.length && j<this.fullBoard[i].length){
            return this.fullBoard[i][j].button;
        }

        return null;
    }

    public Board(int rows,int cols){

        this.rows=rows;
        this.cols=cols;

        this.fullBoard=new BoardSlot[rows][cols];
        this.x=0;
        this.y=0;

        this.appsettings=settings.getInstance();

        this.vibrate=this.appsettings.vibrate();
        this.animate=this.appsettings.animate();

    }


    public void reinitialize(){
        this.x=0;
        this.y=0;
    }



    public void setText(int i,int j,String text){
        this.getButton(i,j).setText(text);
    }

    public void newButton(int i,int j,Context thiscon){

        this.fullBoard[i][j]=new BoardSlot();

        this.fullBoard[i][j].button=new Button(thiscon);
    }


    public void setOnClickListener(int i,int j,View.OnClickListener listenr){
        this.getButton(i,j).setOnClickListener(listenr);
    }

    public void setId(int i,int j, int id){
        this.getButton(i,j).setId(id);
    }

    public void setBackGroundImage(int i,int j,int imageId){
        this.getButton(i,j).setBackgroundResource(imageId);
    }

    public void setBackGroundColor(int i,int j,int color){
        this.getButton(i,j).setBackgroundColor(color);
    }


    public boolean click(int i,int j){
        if(this.isClickable(i,j)){



            if(this.x!=i && this.y!=j) {

                //We move diagonally and we dont have images for this :(

            }else if(this.x==i){
                //we move horizontally, either left or right
                if(j<this.y){
                    this.setBackGroundImage(i,j,R.drawable.left_arrow);
                }else{
                    this.setBackGroundImage(i,j,R.drawable.right_arrow);
                }

            }else{
                if(i<this.x){
                    this.setBackGroundImage(i,j,R.drawable.up_arrow);
                }else{
                    this.setBackGroundImage(i,j,R.drawable.down_arrow);
                }

            }


            if(this.animate) {

                AlphaAnimation mAnimation = new AlphaAnimation(1, 0);
                mAnimation.setDuration(100);
                mAnimation.setRepeatCount(AlphaAnimation.INFINITE);
                mAnimation.setRepeatMode(AlphaAnimation.REVERSE);
                this.getButton(i, j).startAnimation(mAnimation);

            }

            this.setLastPositionClick(i,j);

            return true;
        }


        return false;
    }


    public void setLastPositionClick(int i,int j){
        this.x=i;
        this.y=j;
    }


    public boolean isDanger(int i,int j){
        return this.fullBoard[i][j].isDanger();
    }

    public boolean isClickable(int i,int j){
        boolean ret=false;

        if(this.x==i && this.y==j){
            return false;
        }

        if(i+1==this.x || i-1==this.x || this.x==i){
            if(j+1==this.y || j-1==this.y || this.y==j){
                ret=true;
            }
        }

        return ret;
    }



    private void makeSlotDanger(int i,int j,int image){

        this.fullBoard[i][j].makeDangers();
        this.setBackGroundImage(i,j,image);
    }

    public void makeSkullDanger(int i,int j){
        this.makeSlotDanger(i,j,R.drawable.skull);
    }

    public void makeHazardDanger(int i,int j){
        this.makeSlotDanger(i,j,R.drawable.hazard);
    }



}
