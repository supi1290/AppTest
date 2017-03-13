package scodun.com.a06soccer.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.view.ActionMode;
import android.view.ActionMode.Callback;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;


import scodun.com.a06soccer.R;
import scodun.com.a06soccer.misc.Database;
import scodun.com.a06soccer.misc.Match;
import scodun.com.a06soccer.misc.Player;
import scodun.com.a06soccer.misc.Stat;

/**
 * Created by Raphael Burgstaller on 10/03/2017.
 */

public class UserActivity extends AppCompatActivity implements View.OnLongClickListener,Callback {


    private Player player;
    private Database db;
    private Spinner spMatches;
    private ActionMode mActionMode=null;
    private TextView goalDefault, goalPenalty,goalHead,goalHeadsnow,goalOwn,nutmeg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        try {
            db= Database.newInstance();
            getAllViews();
            registrateEventhandlers();
            player=db.getPlayer(this.getIntent().getExtras().getInt("player"));
            getSupportActionBar().setTitle(player.getName());
            fillMatches();

        }catch(Exception E){
            E.printStackTrace();
        }
    }

    private void getAllViews(){
    spMatches=(Spinner)this.findViewById(R.id.spMatches);
        goalDefault=(TextView)this.findViewById(R.id.stat_goaldefault);
        goalHead=(TextView)this.findViewById(R.id.stat_goalhead);
        goalHeadsnow=(TextView)this.findViewById(R.id.stat_goalheadsnow);
        goalOwn=(TextView)this.findViewById(R.id.stat_goalown);
        goalPenalty=(TextView)this.findViewById(R.id.stat_goalpenalty);
        nutmeg=(TextView)this.findViewById(R.id.stat_nutmeg);

    }
    private void registrateEventhandlers(){
        spMatches.setOnLongClickListener(this);
        spMatches.setLongClickable(true);

    }
    private void fillMatches(){
        ArrayAdapter<Match> adapterRegions =new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,db.getMatchesforplayer(player.getId()));
        adapterRegions.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMatches.setAdapter(adapterRegions);
        if(spMatches.getSelectedItem()!=null) {
            Stat t = ((Match) spMatches.getSelectedItem()).getStatForPlayer(player.getId());
            if (t != null) {
                goalHeadsnow.setText(String.valueOf(t.getGoalHeadSnow()));
                goalHead.setText(String.valueOf(t.getGoalHead()));
                goalOwn.setText(String.valueOf(t.getGoalOwn()));
                goalPenalty.setText(String.valueOf(t.getgoalPenalty()));
                goalDefault.setText(String.valueOf(t.getGoalDefault()));
                nutmeg.setText(String.valueOf(t.getNutmeg()));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


            Intent intent = new Intent(this, ProfileActivity.class);
            intent.putExtra("player", player.getId());
            startActivity(intent);


        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onLongClick(View v) {
        boolean retValue =false;
        if(mActionMode==null){
            mActionMode =this.startActionMode(this);
            retValue=true;
        }
        return retValue;

    }
    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        fillMatches();
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        MenuInflater inflater =mode.getMenuInflater();
        inflater.inflate(R.menu.menu_matches,menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        Boolean retVal=false;
                    Intent intent = new Intent(this, MatchActivity.class);
                    intent.putExtra("match", ((Match) spMatches.getSelectedItem()).getId());
                    startActivity(intent);
                    retVal = true;
                    mode.finish();
        return retVal;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
    mActionMode=null;
    }
}
