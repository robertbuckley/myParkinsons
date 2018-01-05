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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Survey extends AppCompatActivity {
    CheckBox family, male, female, other, adhd, add, admed, depressed, factory, mptp, antipsychotics, drugs, nicotine, narcolepsy, famnarcolepsy, skydive;
    Button submitform;
    EditText bones, age, since;
    String id;
    Firebase f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);
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
        initialize();
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
            AlertDialog purp = new AlertDialog.Builder(Survey.this).setMessage("I aim to crowd source research on the Parkinson's tremor and ultimately perform machine learning algorithms on the collected data to identify overall trends with the shake. These trends could allow the app to accurately guess who has Parkinson’s disease based upon the shake measured in a user or the data could give other insights into the disease as a whole. Another functionality of the app is for those with Parkinson's to track the worsening of their shake overtime, which again, is another opportunity for research. ").setTitle("Purpose").setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            }).create();
            purp.show();
        }
        if (id == R.id.privacy) {
            AlertDialog priv = new AlertDialog.Builder(Survey.this).setMessage("Please keep in mind that this app is for research. By using the app, you have agreed to having your data used in studies and other scholarly pursuits.").setTitle("Privacy").setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            }).create();
            priv.show();
        }

        return super.onOptionsItemSelected(item);
    }
    public void formSubmitted(View view){
        try {
            Form form = new Form(family.isChecked(), male.isChecked(), female.isChecked(), other.isChecked(), adhd.isChecked(), add.isChecked(), admed.isChecked(), depressed.isChecked(), factory.isChecked(), mptp.isChecked(),
                    antipsychotics.isChecked(), drugs.isChecked(), nicotine.isChecked(), narcolepsy.isChecked(), famnarcolepsy.isChecked(), skydive.isChecked(),
                    Integer.parseInt(bones.getText().toString()), Integer.parseInt(age.getText().toString()), Integer.parseInt(since.getText().toString()), id);
            f.push().setValue(form);
            Toast.makeText(Survey.this, "Thank you", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, MainActivity.class));
        }
        catch(Exception e){
            Toast.makeText(Survey.this,"Please fill out entire form", Toast.LENGTH_LONG).show();
        }

    }
    public void initialize(){
        submitform = (Button)findViewById(R.id.submitform);
        skydive = (CheckBox)findViewById(R.id.skydive);
        famnarcolepsy = (CheckBox)findViewById(R.id.famnarcolepsy);
        narcolepsy =(CheckBox)findViewById(R.id.narcolepsy);
        nicotine = (CheckBox)findViewById(R.id.nicotine);
        drugs = (CheckBox)findViewById(R.id.drugs);
        antipsychotics = (CheckBox)findViewById(R.id.antipsychotics);
        mptp = (CheckBox)findViewById(R.id.mptp);
        factory = (CheckBox)findViewById(R.id.factory);
        depressed = (CheckBox)findViewById(R.id.depressed);
        admed =  (CheckBox)findViewById(R.id.admed);
        add = (CheckBox)findViewById(R.id.add);
        adhd = (CheckBox)findViewById(R.id.adhd);
        other = (CheckBox)findViewById(R.id.other);
        female = (CheckBox)findViewById(R.id.female);
        male =(CheckBox)findViewById(R.id.male);
        family = (CheckBox)findViewById(R.id.family);
        since = (EditText)findViewById(R.id.since);
        age = (EditText)findViewById(R.id.age);
        bones = (EditText)findViewById(R.id.bones);
        Firebase.setAndroidContext(this);
        f = new Firebase("https://parkinsons2.firebaseio.com");
    }
}
