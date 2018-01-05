package com.buckley.robert.parkinsonszero;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.firebase.client.Firebase;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Graph extends AppCompatActivity {
    GraphView graph;
    DataPoint[] graphData;
    LineGraphSeries<DataPoint> graphSeries;
    Button oSubmit, pSubmit, discard;
    Firebase f;
    String id;
    SimpleDateFormat s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        try {
            FileInputStream fis = openFileInput("id.txt");
            int read = -1;
            StringBuffer buffer = new StringBuffer();
            while((read = fis.read())!= -1){
                buffer.append(read);
            }
            id = buffer.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(id);
        gInitialize();
    }
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
            AlertDialog purp = new AlertDialog.Builder(Graph.this).setMessage("I aim to crowd source research on the Parkinson's tremor and ultimately perform machine learning algorithms on the collected data to identify overall trends with the shake. These trends could allow the app to accurately guess who has Parkinson’s disease based upon the shake measured in a user or the data could give other insights into the disease as a whole. Another functionality of the app is for those with Parkinson's to track the worsening of their shake overtime, which again, is another opportunity for research. ").setTitle("Purpose").setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            }).create();
            purp.show();
        }
        if (id == R.id.privacy) {
            AlertDialog priv = new AlertDialog.Builder(Graph.this).setMessage("Please keep in mind that this app is for research. By using the app, you have agreed to having your data used in studies and other scholarly pursuits.").setTitle("Privacy").setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            }).create();
            priv.show();
        }

        return super.onOptionsItemSelected(item);
    }
    public void gInitialize(){
        graph = (GraphView)findViewById(R.id.graph);
        graphData = new DataPoint[MainActivity.userGraph.size()];
        for(int i = 0; i < graphData.length; ++i){
            graphData[i] = new DataPoint(i, MainActivity.userGraph.get(i));
        }
        graphSeries = new LineGraphSeries<DataPoint>(graphData);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMaxX(1000);
        graph.addSeries(graphSeries);
        pSubmit= (Button) findViewById(R.id.pSubmit);
        discard = (Button)findViewById(R.id.discard);
        oSubmit= (Button) findViewById(R.id.oSubmit);
        Firebase.setAndroidContext(this);
        f = new Firebase("https://parkinsons.firebaseio.com");
        s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    public void oClicked(View view){
        List<DataSet> data = new ArrayList<DataSet>();
        List<List<DataSet>> dataSets;
        Date now = new Date();
        now.setDate(5);
        f.push().setValue(new DataSet(MainActivity.accels, id, false, now,MainActivity.total, now.getYear() * 365 + (now.getMonth() + 1) * 30 + now.getDate()));
        System.out.println();
        startActivity(new Intent(this, MainActivity.class));
    }

    public void pClicked(View view){
        List<DataSet> data = new ArrayList<DataSet>();
        List<List<DataSet>> dataSets;
        Date now = new Date();
        f.push().setValue(new DataSet(MainActivity.accels, id, false, now, MainActivity.total, now.getYear() * 365 + (now.getMonth() + 1) * 30 + now.getDate()));
        startActivity(new Intent(this, MainActivity.class));
    }

    public void discard(View view){startActivity(new Intent(this, MainActivity.class));
    }
}
