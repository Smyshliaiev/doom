package com.pager.doom.smyshliaiev.anton.doompager;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity  {

    private DirectionManager mDirectionManager = new DirectionManager();
    private TextView mTextInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextInfo = (TextView) findViewById(R.id.textView);
        updateCurrentInfo();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void onClickChoosePic(View view){

    }

    public void onClickMakePhoto(View view){

    }

    public void onClickGoForward(View view){
        mDirectionManager.moveFrw();
        updateCurrentInfo();
    }

    public void onClickTurnLeft(View view){
        mDirectionManager.turnLeft();
        updateCurrentInfo();
    }

    public void onClickTurnRight(View view){
        mDirectionManager.turnRight();
        updateCurrentInfo();
    }

    public void onClickGoBack(View view) {
        mDirectionManager.moveBack();
        updateCurrentInfo();
    }

    private void updateCurrentInfo(){
        mTextInfo.setText("(" + mDirectionManager.getX()+ ", " + mDirectionManager.getY() + ") " + mDirectionManager.getDir());
    }
}
