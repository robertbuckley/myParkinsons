package com.buckley.robert.parkinsonszero;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements SensorEventListener{
    Button start, viewData;
    TextView time;
    Sensor accelerometer;
    SensorManager sm;
    ImageView iconView;
    public static ArrayList<Float[]> accels;
    public static ArrayList<Float[]> vels;
    public static ArrayList<Float[]> dists;
    public static ArrayList<Float> userGraph;
    public static ArrayList<Float> totalDists;
    public static int total;
    boolean currentlyTiming;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FileOutputStream fos = null;
        File file = null;
        try {
            openFileInput("id.txt");
        } catch (FileNotFoundException e) {
            try {
                String uniqueID = UUID.randomUUID().toString();
                file = getFilesDir();
                fos = openFileOutput("id.txt", Context.MODE_PRIVATE);
                fos.write(uniqueID.getBytes());
                fos.close();
            } catch (IOException i) {
                e.printStackTrace();
            }
            finally{
                try {
                    fos.close();
                } catch (IOException j) {
                    e.printStackTrace();
                }
            }
        }
        initialize();
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
        if (id == R.id.purpose) {
            AlertDialog purp = new AlertDialog.Builder(MainActivity.this).setMessage("I aim to crowd source research on the Parkinson's tremor and ultimately perform machine learning algorithms on the collected data to identify overall trends with the shake. These trends could allow the app to accurately guess who has Parkinson’s disease based upon the shake measured in a user or the data could give other insights into the disease as a whole. Another functionality of the app is for those with Parkinson's to track the worsening of their shake overtime, which again, is another opportunity for research. ").setTitle("Purpose").setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            }).create();
            purp.show();
        }
        if (id == R.id.privacy) {
            AlertDialog priv = new AlertDialog.Builder(MainActivity.this).setMessage("Please keep in mind that this app is for research. By using the app, you have agreed to having your data used in studies and other scholarly pursuits.").setTitle("Privacy").setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            }).create();
            priv.show();
        }

        return super.onOptionsItemSelected(item);
    }
    public void initialize(){
        start = (Button)findViewById(R.id.button);
        viewData = (Button)findViewById(R.id.viewData);
        time = (TextView)findViewById(R.id.time);
        sm = (SensorManager)getSystemService(SENSOR_SERVICE);
        accels = new ArrayList<Float[]>();
        userGraph = new ArrayList<Float>();
        totalDists = new ArrayList<Float>();
        vels = new ArrayList<Float[]>();
        dists = new ArrayList<Float[]>();
        vels.add(new Float[]{0F,0F,0F});
        dists.add(new Float[]{0F,0F,0F});
        total = 0;
        accelerometer = sm.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        sm.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_FASTEST);
        currentlyTiming = false;
        iconView = (ImageView) findViewById(R.id.iconView);
        iconView.setImageResource(R.drawable.icon);
        iconView.setAlpha(55);
    }
    public void clicked(View view){
        if (currentlyTiming == false) {
            currentlyTiming = true;
            new CountDownTimer(10000, 1000) {
                public void onTick(long millisUntilFinished) {
                    time.setText("Seconds left: " + millisUntilFinished / 1000);
                }
                public void onFinish() {
                    currentlyTiming = false;
                    Intent intent = new Intent(MainActivity.this, Graph.class);
                    startActivity(intent);
                }
            }.start();
        }
    }
    public void viewDataClicked(View view){
        startActivity(new Intent(this, ViewData.class));
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        //0 = left/right, 1 = up/down, 2 = forward/backb (when holding up)
        if(currentlyTiming) {
            accels.add(new Float[]{event.values[0], event.values[1], event.values[2]});
            userGraph.add((float)(Math.sqrt( (Math.abs(event.values[0]) * Math.abs(event.values[0])) + (Math.abs(event.values[1]) * Math.abs(event.values[1])) + (Math.abs(event.values[2]) * Math.abs(event.values[2])))));
            totalDists.add((float) (Math.sqrt((Math.abs(dists.get(dists.size() - 1)[0]) * Math.abs(dists.get(dists.size() - 1)[0])) + (Math.abs(dists.get(dists.size() - 1)[1]) * Math.abs(dists.get(dists.size() - 1)[1])) + (Math.abs(dists.get(dists.size() - 1)[2]) * Math.abs(dists.get(dists.size() - 1)[2])))));
            vels.add(new Float[]{ vels.get(vels.size() - 1)[0] + accels.get(accels.size() - 1)[0], vels.get(vels.size() - 1)[1] + accels.get(accels.size() - 1)[1], vels.get(vels.size() - 1)[2] + accels.get(accels.size() - 1)[2] });
            dists.add(new Float[]{  dists.get(dists.size() - 1)[0] + vels.get(vels.size() - 1)[0],dists.get(dists.size() - 1)[1] + vels.get(vels.size() - 1)[1] , dists.get(dists.size() - 1)[2] + vels.get(vels.size() - 1)[2]});
            System.out.println("Measurement Taken!");
            total += userGraph.get(userGraph.size() - 1);
        }
        if(!currentlyTiming) {
            time.setText("Start the timer");
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
    @Override
    protected void onResume(){
        super.onResume();
        initialize();
    }
}
