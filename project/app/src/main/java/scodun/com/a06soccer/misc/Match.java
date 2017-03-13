package scodun.com.a06soccer.misc;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeMap;


/**
 * Created by Raphael Burgstaller on 10/03/2017.
 */

public class Match {
    private int id;
    private ArrayList<Stat> t1=new ArrayList<>();
    private ArrayList<Stat> t2=new ArrayList<>();
    private int goalsT1,goalsT2;
    private Date date;

    public Match(int _id){
        date=new Date();
        id=_id;
    }

    public ArrayList<Stat> getT1() {
        return t1;
    }

    public void setT1(ArrayList<Stat> t1) {
        this.t1 = t1;
    }

    public ArrayList<Stat> getT2() {
        return t2;
    }

    public void setT2(ArrayList<Stat> t2) {
        this.t2 = t2;
    }

    public int getGoalsT1() {
        return goalsT1;
    }

    public void setGoalsT1(int goalsT1) {
        this.goalsT1 = goalsT1;
    }

    public int getGoalsT2() {
        return goalsT2;
    }

    public void setGoalsT2(int goalsT2) {
        this.goalsT2 = goalsT2;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }


    public Stat getStatForPlayer(int _id){
        Stat retVal=null;
        for(Stat t:t1){
            if(t.getPlayer().getId()==_id)
                retVal=t;
        }
        for(Stat t:t2){
            if(t.getPlayer().getId()==_id)
                retVal=t;
        }
        return retVal;
    }

    @Override
    public String toString() {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

        return dateFormat.format(date)+", "+goalsT1+":"+goalsT2;
    }
}
