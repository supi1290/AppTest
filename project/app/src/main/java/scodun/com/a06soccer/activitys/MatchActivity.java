package scodun.com.a06soccer.activitys;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import scodun.com.a06soccer.R;
import scodun.com.a06soccer.misc.Database;
import scodun.com.a06soccer.misc.Match;
import scodun.com.a06soccer.misc.Player;
import scodun.com.a06soccer.misc.Stat;

/**
 * Created by Raphael Burgstaller on 10/03/2017.
 */

public class MatchActivity extends AppCompatActivity {

    LinearLayout t1;
    LinearLayout t2;
    private Match match;
    private Database db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);
        try {
            db= Database.newInstance();
        }catch(Exception E){
        }
            getAllViews();
            registrateEventhandlers();
            match=db.getMatch(this.getIntent().getExtras().getInt("match"));
            getSupportActionBar().setTitle("Match: "+match.toString());

            filltable();


    }

    private void filltable(){
        TextView txtview=new TextView(this);
        t1.removeAllViews();
        t2.removeAllViews();
        txtview.setText("Team 1");
        txtview.setBackgroundColor(Color.LTGRAY);
        txtview.setGravity(Gravity.CENTER_HORIZONTAL);
        t1.addView(txtview);
        txtview=new TextView(this);
        txtview.setText("Team 2");
        txtview.setGravity(Gravity.CENTER_HORIZONTAL);
        txtview.setBackgroundColor(Color.LTGRAY);
        t2.addView(txtview);

        for(Stat p:match.getT1()){
            txtview=new TextView(this);
            txtview.setText(p.getPlayer().getName());
            t1.addView(txtview);

        }
        for(Stat p:match.getT2()){
            txtview=new TextView(this);
            txtview.setText(p.getPlayer().getName());
            t2.addView(txtview);

        }

    }
    private void getAllViews(){
        t1=(LinearLayout)this.findViewById(R.id.t1players);
        t2=(LinearLayout)this.findViewById(R.id.t2players);



    }
    private void registrateEventhandlers(){

    }


}
