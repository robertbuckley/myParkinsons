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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class ViewData extends AppCompatActivity{
    Firebase f;
    String id = "";
    ArrayList<DataSet> results;
    GraphView history;
    DataPoint[] graphData;
    LineGraphSeries<DataPoint> graphSeries;
    TextView dates;
    ArrayList<GroupedResult> totals;
    ProgressBar pb;
    Button takeSurvey;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        try {
            FileInputStream fis = openFileInput("id.txt");
            int read = -1;
            StringBuffer buffer = new StringBuffer();
            while ((read = fis.read()) != -1) {
                buffer.append(read);
            }
            id = buffer.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        results = new ArrayList<DataSet>();
        history = (GraphView) findViewById(R.id.history);
        history.getViewport().setYAxisBoundsManual(true);
        dates = (TextView) findViewById(R.id.dates);
        totals = new ArrayList<GroupedResult>();

        Firebase.setAndroidContext(this);
        f = new Firebase("https://parkinsons.firebaseio.com");
        //*insert to delete all entries and reset the database*
        //f.removeValue(null);
            pb = (ProgressBar) findViewById(R.id.progressBar);
            pb.setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Query qF = f.orderByChild("id").equalTo(id);
                qF.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        pb.setVisibility(View.VISIBLE);
                        DataSet current = dataSnapshot.getValue(DataSet.class);
                        results.add(current);
                        totals = new ArrayList<GroupedResult>();
                        results = sort(results);
                        for (int i = 0; i < results.size(); ++i) {
                            System.out.println(results.get(i).getTotalDay());
                        }
                        if (results.size() >= 3) {
                            totals.add(new GroupedResult(results.get(0).getResult(), 1, results.get(0).getTotalDay()));
                            for (int i = 1; i < results.size(); ++i) {
                                if (results.get(i).getTotalDay() == results.get(i - 1).getTotalDay()) {
                                    totals.get(totals.size() - 1).total += results.get(i).getResult();
                                    totals.get(totals.size() - 1).number++;
                                } else {
                                    totals.add(new GroupedResult(results.get(i).getResult(), 1, results.get(i).getTotalDay()));
                                }
                            }
                            graphData = new DataPoint[totals.size()];
                            for (int i = 0; i < totals.size(); ++i) {
                                graphData[i] = new DataPoint(totals.get(i).day, totals.get(i).total / totals.get(i).number);
                            }
                            history.getViewport().setXAxisBoundsManual(true);
                            history.getViewport().setYAxisBoundsManual(true);
                            StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(history);
                            staticLabelsFormatter.setHorizontalLabels(new String[]{(results.get(0).getDate().getMonth() + 1 + "/" + results.get(0).getDate().getDate() + "/" + (results.get(0).getDate().getYear() - 100))
                                    , results.get(results.size() - 1).getDate().getMonth() + 1 + "/" + results.get(results.size() - 1).getDate().getDate() + "/" + (results.get(results.size() - 1).getDate().getYear() - 100)});
                            history.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
                            System.out.println(results.get(0).getDate().getMonth() + 1 + "/" + results.get(0).getDate().getDate() + "/" + (results.get(0).getDate().getYear() - 100));
                            history.getViewport().setMaxY((5000));
                            history.getViewport().setMinX(totals.get(0).day);
                            history.getViewport().setMaxX(totals.get(totals.size() - 1).day);
                            graphSeries = new LineGraphSeries<DataPoint>(graphData);
                            for (int i = 0; i < totals.size(); ++i) {
                                System.out.println(totals.get(i).total + "  " + totals.get(i).number);
                            }
                            history.removeAllSeries();
                            history.addSeries(graphSeries);
                            pb.setVisibility(View.INVISIBLE);
                        }
                    }
                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }
                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }
                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }
                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                        Toast toaast = Toast.makeText(ViewData.this, "Connection failed", Toast.LENGTH_LONG);
                    }
                });
            }
        }).start();

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
            AlertDialog purp = new AlertDialog.Builder(ViewData.this).setMessage("I aim to crowd source research on the Parkinson's tremor and ultimately perform machine learning algorithms on the collected data to identify overall trends with the shake. These trends could allow the app to accurately guess who has Parkinson’s disease based upon the shake measured in a user or the data could give other insights into the disease as a whole. Another functionality of the app is for those with Parkinson's to track the worsening of their shake overtime, which again, is another opportunity for research. ").setTitle("Purpose").setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            }).create();
            purp.show();
        }
        if (id == R.id.privacy) {
            AlertDialog priv = new AlertDialog.Builder(ViewData.this).setMessage("Please keep in mind that this app is for research. By using the app, you have agreed to having your data used in studies and other scholarly pursuits.").setTitle("Privacy").setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            }).create();
            priv.show();
        }

        return super.onOptionsItemSelected(item);
    }
    public void startSurvery(View view){
        startActivity(new Intent(this, Survey.class));
    }
    public ArrayList<DataSet> sort(ArrayList<DataSet> list) {
        DataSet temp;
        for (int j = list.size() - 1; j > 0; j--) {
            for (int i = 0; i < j; i++) {
                if(list.get(i).getTotalDay() > list.get(i + 1).getTotalDay()) {
                    temp = list.get(i);
                    list.set(i, list.get(i + 1));
                    list.set(i + 1, temp);
                }
            }
        }
        return list;
    }
}
