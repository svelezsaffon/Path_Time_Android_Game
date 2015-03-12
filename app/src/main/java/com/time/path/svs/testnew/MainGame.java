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
import android.view.animation.AlphaAnimation;
import android.widget.ActionMenuView;
import android.widget.Button;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.os.Vibrator;
import android.media.MediaPlayer;
import android.widget.ImageView;
import android.graphics.drawable.Drawable;


import com.time.path.svs.testnew.Game_Logic.DeadException;
import com.time.path.svs.testnew.Game_Logic.GameLogic;
import com.time.path.svs.testnew.Settings.settings;
import com.time.path.svs.testnew.UI.Board_Logic.Board;
import com.time.path.svs.testnew.Game_Logic.pair;

import java.io.IOException;

public class MainGame extends ActionBarActivity {

    public final static String BOARD_SIZE="path.time.board.size";

    ProgressBar bar=null;

    private int boardSize;

    Button startButton=null;

    private int col_row;

    Board board=null;

    private GameLogic logic;

    private Vibrator vibrator;

    private boolean vibrateModeOn;

    private boolean soundModeOn;

    private MediaPlayer bumpSound;

    private MediaPlayer deadSound;

    private ImageView healtBar[];


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


        this.logic=GameLogic.getInstance(this.col_row);

        this.logic.reset();

        this.board=new Board(this.col_row,this.col_row);

        LinearLayout board_layout=(LinearLayout) findViewById(R.id.board_layout);

        this.vibrator = (Vibrator) this.getSystemService(this.VIBRATOR_SERVICE);

        this.vibrateModeOn = settings.getInstance().vibrate();

        this.soundModeOn=settings.getInstance().sound();


        this.prepareSounds();


        this.initLifes();


        int id=0;
        for(int i=0;i<this.col_row;i++){

            LinearLayout aux_layout=new LinearLayout(this);

            for(int j=0;j<this.col_row;j++,id++){

                this.board.newButton(i,j,this);

                this.board.setId(i,j,id);

                //this.board.setBackGroundColor(i,j,Color.DKGRAY);


                this.board.setOnClickListener(i,j,new View.OnClickListener() {
                    public void onClick(View viw) {
                        clickedBoardButton(viw);
                    }
                });

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                params.weight=1;

                aux_layout.addView(this.board.getButton(i,j),params);


            }

            board_layout.addView(aux_layout);
        }

        this.addDangers();

    }

    private void updateLifes(){
        int pos=this.logic.remainingLifes();
        Drawable drawable = getResources().getDrawable(R.drawable.empty_heart);
        this.healtBar[pos].setImageDrawable(drawable);
    }


    private void initLifes(){
        LinearLayout aux_layout=new LinearLayout(this);

        this.healtBar=new ImageView[5];

        for(int i=0;i<5;i++) {
            this.healtBar[i]=new ImageView(this);
            Drawable drawable = getResources().getDrawable(R.drawable.heart_icon);
            this.healtBar[i].setImageDrawable(drawable);
            aux_layout.addView(this.healtBar[i]);
        }

        LinearLayout board_layout=(LinearLayout) findViewById(R.id.healt_layout);
        board_layout.addView(aux_layout);
    }


    private void prepareSounds(){
        this.bumpSound=MediaPlayer.create(this,R.raw.short_bump);
        this.deadSound=MediaPlayer.create(this,R.raw.die_sound);
    }



    public void addDangers(){
        pair<Integer> []positions=this.logic.generateDangerPositions();

        for(int i=0;i<positions.length;i++){
            this.board.makeSkullDanger(positions[i].first,positions[i].second);
        }
    }



    public void showToast(String message,int time){
        Toast.makeText(this, message, time).show();
    }


    public void generateBoard(){

        this.board.reinitialize();
        int id=0;
        for(int i=0;i<this.col_row;i++){

            for(int j=0;j<this.col_row;j++,id++){

                this.board.setId(i,j,id);

                this.board.setBackGroundColor(i,j,Color.DKGRAY);

                this.board.setOnClickListener(i,j,new View.OnClickListener() {
                    public void onClick(View viw) {
                        clickedBoardButton(viw);
                    }
                });

            }

        }
    }



    public void clickedBoardButton(View view){

        if(!this.logic.isPlayerDead()) {

            int row = view.getId() / this.col_row;

            int col = view.getId() % this.col_row;

            if (this.board.click(row, col)) {

                if (this.board.isDanger(row, col)) {


                    try {

                        this.logic.hitDanger();

                        this.showToast(getString(R.string.click_danger), Toast.LENGTH_SHORT);

                        this.vibrate(Toast.LENGTH_SHORT);

                        this.makeBumpSound();

                    } catch (DeadException e) {

                        this.showToast(getString(R.string.die_message), Toast.LENGTH_LONG);

                        this.vibrate(Toast.LENGTH_LONG);

                        this.makeDeadSound();

                        e.printStackTrace();

                    }

                    this.updateLifes();
                    this.board.removeDanger(row, col);
                }

            } else {
                this.showToast(getString(R.string.invalid_move), Toast.LENGTH_SHORT);
            }

        }else{
            this.showToast(getString(R.string.player_is_dead),Toast.LENGTH_LONG);
        }


    }

    private void makeBumpSound() {
        if(this.soundModeOn==true) {
            this.bumpSound.start();
        }else{
            System.out.println("Sound effects are off");
        }
    }

    private void makeDeadSound(){
        if(this.soundModeOn==true) {
            this.deadSound.start();
        }else{
            System.out.println("Sound effects are off");
        }
    }



    public void checkBoard(){
        //Do calculation of score
    }



    public void vibrate(int length){
        if(this.vibrateModeOn) {
            if (this.vibrator.hasVibrator()) {
                this.vibrator.vibrate(length);
            } else {
                System.out.println("No vibrator on this device");
            }

        }else{
            System.out.println("Vibration systems of");
        }
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
            startButton.setEnabled(false);
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

            checkBoard();
            startButton.setEnabled(true);

        }

    }








}
