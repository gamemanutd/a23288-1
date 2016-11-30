package com.egco428.a23288;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Locale;
import java.util.Random;

public class SignUpPage extends AppCompatActivity implements SensorEventListener {

    public SensorManager sensorManager;
    public long lastUpdate;
    public UserDataSource db;
    EditText laTi;
    EditText longTi;
    EditText uName;
    EditText pWord;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.back_signup);

        ImageButton backSingup = (ImageButton)findViewById(R.id.back2);
        backSingup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        lastUpdate = System.currentTimeMillis();

        db = new UserDataSource(this);
        db.open();
    }

    public void SignUpData(View view) {
        uName = (EditText) findViewById(R.id.edit_user);
        pWord = (EditText) findViewById(R.id.edit_pass);
        laTi = (EditText) findViewById(R.id.edit_lat);
        longTi = (EditText) findViewById(R.id.edit_long);

        String getuName = uName.getText().toString();
        String getpWord = pWord.getText().toString();
        Double getlaTi = Double.parseDouble(laTi.getText().toString());
        Double getlongTi = Double.parseDouble(longTi.getText().toString());

        db.createData(getuName, getpWord, getlaTi, getlongTi);
        db.close();

        finish();
    }



    public void onSensorChanged(SensorEvent event){
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            getAccelerometer(event);
        }
    }

    private void getAccelerometer(SensorEvent event) {
        float[] values = event.values;

        float x = values[0];
        float y = values[1];
        float z = values[2];

        float accelationSquareRoot = (x * x + y * y + z * z)
                / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);
        long actualTime = System.currentTimeMillis();

        if (accelationSquareRoot > 3) {
            if (actualTime - lastUpdate < 600) {
                return;
            }
            lastUpdate = actualTime;
            random1();
        }
    }

    public void randomBtn(View view){
        random1();
    }

    public void random1(){

        laTi = (EditText) findViewById(R.id.edit_lat);
        longTi = (EditText) findViewById(R.id.edit_long);
        Random r = new Random();

        double randomLati = -85.000000 + (85.000000 - (-85.000000)) * r.nextDouble();
        randomLati =Double.parseDouble(new DecimalFormat("##.######").format(randomLati));
        laTi.setText(String.valueOf(randomLati));

        Random g = new Random();
        double randomLongi = -179.999989 + (179.999989 - (-179.999989)) * g.nextDouble();
        randomLongi =Double.parseDouble(new DecimalFormat("##.######").format(randomLongi));
        longTi.setText(String.valueOf(randomLongi));
    }


    public  void onAccuracyChanged(Sensor sensor,int accuracy){

    }

    @Override
    protected  void onResume(){
        super.onResume();
        sensorManager.registerListener
                (this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }
    @Override
    protected void onPause(){
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}


