package com.buckley.robert.parkinsonszero;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Robert on 3/12/2016.
 */
public class DataSet {
    private ArrayList<Float[]> accels;
    private String id;
    private boolean parkinsons;
    private Date date;
    private int totalDay;
    private int result;
    public DataSet(ArrayList<Float[]> accels, String id, Boolean parkinsons, Date date, int result, int totalDay){
        this.accels = accels;
        this.id = id;
        this.parkinsons = parkinsons;
        this.date = date;
        this.result = result;
        this.totalDay = totalDay;
    }
    public DataSet(){

    }
    public ArrayList<Float[]> getAccels() {return accels;}
    public String getId() {return id;}
    public boolean isParkinsons() {return parkinsons;}
    public Date getDate() {return date;}
    public int getResult() {return result;}
    public int getTotalDay(){return totalDay;}


}
