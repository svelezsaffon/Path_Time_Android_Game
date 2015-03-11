package com.time.path.svs.testnew;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;


public class MainGame extends ActionBarActivity {

    public final static String BOARD_SIZE="path.time.board.size";

    ProgressBar bar=null;


    private int boardSize;


    Button startButton=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game);



        this.startVariables();
    }

    private void startVariables() {
        this.bar=(ProgressBar) findViewById(R.id.bar_time_left);
        this.startButton=(Button) findViewById(R.id.button_start_game);


        Intent intent=getIntent();

        this.boardSize=intent.getExtras().getInt(MainGame.BOARD_SIZE);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void startGame(View view){

        this.bar.setIndeterminate(false);



        GameCountDown timer=new GameCountDown(30000, 1000);

        this.bar.setMax(30000);

        timer.start();




    }




    private class GameCountDown extends CountDownTimer{


        public GameCountDown(int startTime,int interval){
            super(startTime,interval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

            bar.setProgress((int)millisUntilFinished);
            System.out.println("Time is ticking "+bar.getProgress());

        }

        @Override
        public void onFinish() {


        }

    }








}
