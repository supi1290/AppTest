package scodun.com.a06soccer.activitys;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;


import scodun.com.a06soccer.R;
import scodun.com.a06soccer.misc.Database;
import scodun.com.a06soccer.misc.Match;
import scodun.com.a06soccer.misc.Player;
import scodun.com.a06soccer.misc.Stat;

/**
 * Created by Raphael Burgstaller on 10/03/2017.
 */

public class MatchUpdateActivity extends AppCompatActivity implements View.OnClickListener{

    TextView dp;
    ArrayList<Player> t1=new ArrayList<>();
    ArrayList<Player> t2=new ArrayList<>();
    private Match match;
    private Database db;
    private Date dirtyDate;
    private FloatingActionButton changeDate;
    private LinearLayout container;
    private TextView goalst1,goalst2;
    ArrayList<Player>playerlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matchupdate);
        try {
            db= Database.newInstance();
        }catch(Exception E){
        }
            getAllViews();
            registrateEventhandlers();
            match=db.getMatch(this.getIntent().getExtras().getInt("match"));
            getSupportActionBar().setTitle("Update Match: "+match.toString());
            for(Stat s:match.getT1())
                t1.add(s.getPlayer());
        for(Stat s:match.getT2())
            t2.add(s.getPlayer());
            filltable();
        dirtyDate=match.getDate();



    }

    private void filltable(){
        container.removeAllViews();
        playerlist=new ArrayList<>(db.getPlayers());
        CheckBox tempBox;
        int id=0;
        for(Player p: playerlist){
            tempBox=new CheckBox(this);

            id++;
            if(t1.contains(p))
                tempBox.setText(p.getName()+ " :Team 1");
            else if(t2.contains(p))
                tempBox.setText(p.getName()+ " :Team 2");
            else
                tempBox.setText(p.getName());
            tempBox.setId(p.getId());
            container.addView(tempBox);
        }

        goalst1.setText(String.valueOf(match.getGoalsT1()));
        goalst2.setText(String.valueOf(match.getGoalsT2()));
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        dp.setText(dateFormat.format(match.getDate()));





    }
    private void getAllViews(){
        dp=(TextView) this.findViewById(R.id.DateViewer);
        changeDate=(FloatingActionButton)this.findViewById(R.id.changeDatebtn);
        container=(LinearLayout)this.findViewById(R.id.playerStorage);
        goalst1=(TextView)this.findViewById(R.id.goalst1);
        goalst2=(TextView)this.findViewById(R.id.goalst2);

    }
    private void registrateEventhandlers(){
        changeDate.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        showDialog(DATE_DIALOG_ID);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_optionmatchupdate,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.optionitemteam1: {
                int childcount=container.getChildCount();
                for(int i=0;i<childcount;i++){
                    CheckBox v=(CheckBox)container.getChildAt(i);
                    Player p= db.getPlayer(v.getId());
                    if(v.isChecked()){
                        if(t2.contains(p)){
                            t2.remove(p);
                        }if(!t1.contains(p)){
                            t1.add(p);
                        }
                    }
                }
                filltable();
                break;
            }
            case R.id.optionitemteam2: {
                int childcount=container.getChildCount();
                for(int i=0;i<childcount;i++){
                    CheckBox v=(CheckBox)container.getChildAt(i);
                    Player p= db.getPlayer(v.getId());
                    if(v.isChecked()){
                        if(t1.contains(p)){
                            t1.remove(p);
                        }
                        if(!t2.contains(p)){
                            t2.add(p);
                        }
                    }
                }
                filltable();
                break;
            }
            case R.id.optionitemunassigned: {
                int childcount=container.getChildCount();
                for(int i=0;i<childcount;i++){
                    CheckBox v=(CheckBox)container.getChildAt(i);
                    Player p= db.getPlayer(v.getId());
                    if(v.isChecked()){
                        if(t1.contains(p)){
                            t1.remove(p);
                        }
                        if(t2.contains(p)){
                            t2.remove(p);
                        }
                    }
                }
                filltable();
                break;
            }
            case R.id.optionitemsave: {
                ArrayList<Stat>temp=new ArrayList<>();
                for(Player p: t1){
                    temp.add(new Stat(p));
                }
                match.setT1(temp);
                temp=new ArrayList<>();
                for(Player p: t2){
                    temp.add(new Stat(p));
                }
                match.setT2(temp);
                match.setDate(dirtyDate);
                if(goalst1.getText()!=null) {
                    match.setGoalsT1(Integer.parseInt(goalst1.getText().toString()));
                    match.setGoalsT2(Integer.parseInt(goalst2.getText().toString()));
                }
                break;
            }
            case R.id.optionitemshuffle: {
                int childcount=container.getChildCount();
                Random r=new Random();
                for(int i=0;i<childcount;i++){
                    r=new Random();
                    CheckBox v=(CheckBox)container.getChildAt(i);
                    Player p= db.getPlayer(v.getId());
                        if(!t2.contains(p)&&!t1.contains(p)){
                            if(r.nextBoolean())
                                t2.add(p);
                            else
                                t1.add(p);
                        }
                }
                filltable();
                break;
            }
        }
        return true;
    }

    static final int DATE_DIALOG_ID = 0;
    int mYear,mMonth,mDay;
    String selectedDate;


    @Override
    protected Dialog onCreateDialog(int id) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(match.getDate());
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this,
                        mDateSetListener,
                        mYear, mMonth, mDay);
        }
        return null;
    }


    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {


                public void onDateSet(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {
                    mYear = year;
                    mMonth = monthOfYear;
                    mDay = dayOfMonth;
                    Calendar cal = Calendar.getInstance();
                    cal.set(year,monthOfYear,dayOfMonth);
                    dirtyDate=cal.getTime();
                    DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
                    dp.setText(dateFormat.format(dirtyDate));
                }


            };




}
