package com.time.path.svs.testnew.UI.Board_Logic;

import android.widget.Button;

/**
 * Created by santiago on 3/11/15.
 */
public class BoardSlot {

    public Button button;


    private boolean has_dangers;

    private boolean has_coin;

    public BoardSlot(){
        this.has_dangers=false;
    }


    public void makeDangers(){
        this.has_dangers=true;
    }

    public boolean isDanger(){
        return this.has_dangers;
    }

    public void removeDanger(){
        this.has_dangers=false;
    }


    public void makeCoin(){
        this.has_coin=true;
    }

    public boolean isCoin(){
        return this.has_coin;
    }

    public void removeCoin(){
        this.has_coin=false;
    }



}
