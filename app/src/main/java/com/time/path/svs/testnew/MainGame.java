package com.time.path.svs.testnew;

import android.app.ActionBar;
import android.content.Intent;

import android.graphics.Color;

import android.graphics.PorterDuff;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ActionMenuView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;



public class MainGame extends ActionBarActivity {

    public final static String BOARD_SIZE="path.time.board.size";

    ProgressBar bar=null;


    private int boardSize;


    Button startButton=null;


    private int col_row;

    Button [][]board=null;

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

        this.col_row=(int)Math.sqrt(this.boardSize);



        this.board=new Button[this.col_row][this.col_row];


        LinearLayout board_layout=(LinearLayout) findViewById(R.id.board_layout);

        int id=0;
        for(int i=0;i<this.col_row;i++){

            LinearLayout aux_layout=new LinearLayout(this);

            for(int j=0;j<this.col_row;j++,id++){

                this.board[i][j]=new Button(this);
                this.board[i][j].setText(i+","+j);
                this.board[i][j].setBackgroundColor(Color.BLACK);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                params.weight=1;

                aux_layout.addView(this.board[i][j],params);

                this.board[i][j].setId(id);


                this.board[i][j].setOnClickListener(new View.OnClickListener(){
                    public  void onClick(View viw){
                        clickedBoardButton(viw);
                    }
                });



            }

            board_layout.addView(aux_layout);
        }


    }


    public void clickedBoardButton(View view){

        System.out.println(view.getId());


        int row= view.getId() / this.col_row;

        int col=view.getId() % this.col_row;



        this.board[row][col].setBackgroundColor(Color.RED);




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

        private int startime;

        public GameCountDown(int startTime,int interval){
            super(startTime,interval);
            this.startime=startTime;
        }

        @Override
        public void onTick(long millisUntilFinished) {



            bar.setProgress((int)millisUntilFinished);


            double progress=((double)bar.getProgress()/(double)this.startime);


            if(progress>=0.80  && progress <=1.0){

                bar.getProgressDrawable().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);

            }else if(progress>=0.50 && progress <0.80){

                bar.getProgressDrawable().setColorFilter(Color.CYAN, PorterDuff.Mode.SRC_IN);

            }else if(progress>=0.30 && progress <0.50){

                bar.getProgressDrawable().setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_IN);

            }else if(progress>=0.10  && progress <0.30){

                bar.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
            }



        }

        @Override
        public void onFinish() {

            bar.setProgress(0);

        }

    }








}
