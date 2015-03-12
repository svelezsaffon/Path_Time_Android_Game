package com.time.path.svs.testnew.Game_Logic;

import java.util.Random;

/**
 * Created by santiago on 3/12/15.
 */
public class GameLogic {



    private static GameLogic instance=null;

    private int level;

    private int board_size;

    private int lifes;

    private int coins;

    private static final int DEFAULT_LIFES=5;

    private static final int DEFAULT_START_LEVEL=1;

    private boolean isDead;

    private GameLogic(int size){
        this.level=DEFAULT_START_LEVEL;
        this.board_size=size;
        this.lifes=DEFAULT_LIFES;
        this.coins=0;
        this.isDead=false;
    }


    public void reset() {
        this.level=DEFAULT_START_LEVEL;
        this.lifes=DEFAULT_LIFES;
        this.isDead=false;
    }


    public static GameLogic getInstance(int size){
        if(instance==null){
            instance=new GameLogic(size);
        }

        return instance;
    }


    public int getLevel(){
        return this.level;
    }


    public void advanceLevele(){
        this.level=this.level+1;
    }


    public void gotNewCoin() throws NewLevelException{
        this.coins=this.coins+1;
        int aux = (this.coins / 10)+1;


        if(aux>this.level){
            this.level=aux;
            throw new NewLevelException(this.level);
        }

        this.level=aux;

    }


    public int remainingLifes(){
        return this.lifes;
    }

    public void hitDanger() throws DeadException{
        this.lifes--;
        if(this.lifes<=0){
            this.isDead=true;
            throw new DeadException();
        }
    }

    public boolean isPlayerDead(){
        return this.isDead;
    }


    private int generateNumberOfDangers(){

        Random rand= new Random();

        int randomNum = (rand.nextInt((this.board_size-1) + 1)+1) * this.level;

        return randomNum;

    }


    public pair<Integer>[] generateDangerPositions(){
        int dangers=this.generateNumberOfDangers();

        pair<Integer> []ret=new pair[dangers];


        Random colrand= new Random();
        Random rowrand= new Random();

        for(int i=0;i<dangers;i++){

            int col = (colrand.nextInt((this.board_size - 0) + 1) + 0)%this.board_size;
            int row = (rowrand.nextInt((this.board_size - 0) + 1) + 0)%this.board_size;

            ret[i]=new pair<Integer>(col,row);

        }


        return ret;

    }



}

























