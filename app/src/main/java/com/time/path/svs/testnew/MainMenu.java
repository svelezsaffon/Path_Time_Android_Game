package com.time.path.svs.testnew;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.content.SharedPreferences;


import com.time.path.svs.testnew.Settings.settings;




public class MainMenu extends ActionBarActivity {


    RadioButton []radios=new RadioButton[4];

    RadioGroup RBgroup=null;


    private settings appsettings;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);


        startVariables();



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        if(requestCode==1) {
            System.out.println("Name=" + this.appsettings.getPlayerName());
            System.out.println("Vibrate=" +this.appsettings.vibrate());
            System.out.println("Animate=" +this.appsettings.animate());

        }


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


    /*
    *METHODS ADDED BY SANTIAGO VELEZ SAFFON
     */



    public void startVariables(){

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        settings.create_first(sharedPrefs);

        this.appsettings=settings.getInstance();


        this.radios[0]=(RadioButton) findViewById(R.id.rbeasy);
        this.radios[1]=(RadioButton) findViewById(R.id.rbmedium);
        this.radios[2]=(RadioButton) findViewById(R.id.rbhigh);
        this.radios[3]=(RadioButton) findViewById(R.id.rbpro);
        this.RBgroup=(RadioGroup) findViewById(R.id.rbglevels);
        this.RBgroup.check(R.id.rbmedium);

    }



    public void startSettings(View view){
        Intent startGActivity=new Intent(this,SettingsActivity.class);
        startActivityForResult(startGActivity,1);
    }

    public void initGameActivity(View view){



        int size=16;
        switch(this.RBgroup.getCheckedRadioButtonId()){

            case R.id.rbeasy: {
                size=9;
                break;
            }
            case R.id.rbmedium: {
                size=16;
                break;
            }
            case R.id.rbhigh:{
                size=36;
                break;
            }
            case R.id.rbpro:{
                size=7*7;
                break;
            }


        }


        Intent startGActivity=new Intent(this,MainGame.class);
        startGActivity.putExtra(MainGame.BOARD_SIZE,size);
        startActivity(startGActivity);


    }




}
