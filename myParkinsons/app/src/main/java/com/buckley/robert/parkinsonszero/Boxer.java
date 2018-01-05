package com.buckley.robert.parkinsonszero;

import java.util.ArrayList;

/**
 * Created by Robert on 3/12/2016.
 */
public class Boxer {
    ArrayList<DataSet> d;
    public Boxer(ArrayList<DataSet> d){
        this.d = d;
    }
    public ArrayList<DataSet> getD(){
        return d;
    }
    public void setD(ArrayList<DataSet> d){
        this.d = d;
    }
}
