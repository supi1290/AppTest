package scodun.com.a06soccer.activitys;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import scodun.com.a06soccer.R;
import scodun.com.a06soccer.misc.Database;
import scodun.com.a06soccer.misc.Match;
import scodun.com.a06soccer.misc.Player;
import scodun.com.a06soccer.misc.Stat;

/**
 * Created by Raphael Burgstaller on 10/03/2017.
 */

public class AdminActivity extends AppCompatActivity implements View.OnLongClickListener,ActionMode.Callback, AdapterView.OnItemSelectedListener{

    private View longClickedView;
    private Player player;
    private Database db;
    private ActionMode mActionMode=null;
    private Spinner spPlayers,spMatches;
    private String m_Text = "";
    private TextView goalHeadsnow,goalHead,goalOwn,goalPenalty,goalDefault,nutmeg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        try {
            db= Database.newInstance();
            getAllViews();
            registrateEventhandlers();
            player=db.getPlayer(this.getIntent().getExtras().getInt("player"));
            getSupportActionBar().setTitle(player.getName());
            fillSpinners();


        }catch(Exception E){
        }
    }

    private void getAllViews(){
        spPlayers=(Spinner)this.findViewById(R.id.spPlayeradmin);
        spMatches=(Spinner)this.findViewById(R.id.spMatchesAdmin);
        goalHeadsnow=(TextView)this.findViewById(R.id.admin_goalheadsnow);
        goalHead=(TextView)this.findViewById(R.id.admin_goalhead);
        goalOwn=(TextView)this.findViewById(R.id.admin_goalown);
        goalPenalty=(TextView)this.findViewById(R.id.admin_goalpenalty);
        goalDefault=(TextView)this.findViewById(R.id.admin_goaldefault);
        nutmeg=(TextView)this.findViewById(R.id.admin_nutmeg);
    }
    private void registrateEventhandlers(){
        spPlayers.setOnLongClickListener(this);
        spPlayers.setLongClickable(true);
        spMatches.setOnLongClickListener(this);
        spMatches.setLongClickable(true);
        spMatches.setOnItemSelectedListener(this);
        spPlayers.setOnItemSelectedListener(this);
    }
    private void fillSpinners(){
        ArrayAdapter<Player> adapterRegions =new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,db.getPlayers());
        adapterRegions.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPlayers.setAdapter(adapterRegions);



       fillMatches();

        fillStats();

    }
    private void fillMatches(){


        ArrayAdapter<Match> adapterRegions2 =new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,db.getMatches());
        adapterRegions2
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMatches.setAdapter(adapterRegions2);}

    private void fillStats(){
        if(spMatches.getSelectedItem()!=null) {
            Stat t = ((Match) spMatches.getSelectedItem()).getStatForPlayer(((Player)spPlayers.getSelectedItem()).getId());
            if (t != null) {
                ((ConstraintLayout)this.findViewById(R.id.statcontainer)).setVisibility(View.VISIBLE);
                goalHeadsnow.setText(String.valueOf(t.getGoalHeadSnow()));
                goalHead.setText(String.valueOf(t.getGoalHead()));
                goalOwn.setText(String.valueOf(t.getGoalOwn()));
                goalPenalty.setText(String.valueOf(t.getgoalPenalty()));
                goalDefault.setText(String.valueOf(t.getGoalDefault()));
                nutmeg.setText(String.valueOf(t.getNutmeg()));
            }else{
                ((ConstraintLayout)this.findViewById(R.id.statcontainer)).setVisibility(View.INVISIBLE);
            }
        }else{
            ((ConstraintLayout)this.findViewById(R.id.statcontainer)).setVisibility(View.INVISIBLE);
        }
    }


    @Override
    public boolean onLongClick(View v) {
        boolean retValue =false;
        if(mActionMode==null){
            longClickedView=v;
            mActionMode =this.startActionMode(this);
            retValue=true;

        }
        return retValue;

    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        if(longClickedView.getId()==spPlayers.getId()) {
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.menu_adminplayer, menu);
        }else{
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.menu_adminmatch, menu);
        }
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }
    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        fillSpinners();
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        Boolean retVal=false;
        if(item.getItemId()==R.id.Profilebtn){
            Intent intent = new Intent(this, ProfileActivity.class);
            intent.putExtra("player", ((Player)spPlayers.getSelectedItem()).getId());
            startActivity(intent);
        }
        if(item.getItemId()==R.id.addplayerbtn){
           inputDialogName();
        }
        if(item.getItemId()==R.id.removeplayerbtn){
            deleteDialog(((Player)spPlayers.getSelectedItem()).getId());
        }
        if(item.getItemId()==R.id.Match_view){
            Intent intent = new Intent(this, MatchActivity.class);
            intent.putExtra("match", ((Match)spMatches.getSelectedItem()).getId());
            startActivity(intent);
        }
        if(item.getItemId()==R.id.match_update){
            Intent intent = new Intent(this, MatchUpdateActivity.class);
            intent.putExtra("match", ((Match)spMatches.getSelectedItem()).getId());
            startActivity(intent);
        }
        if(item.getItemId()==R.id.match_add){
            db.addMatch();
            fillMatches();
        }
        if(item.getItemId()==R.id.Match_remove){
            db.removeMatch(((Match)spMatches.getSelectedItem()).getId());
            fillMatches();
        }
        mode.finish();
        return retVal;
    }
    private void inputDialogName(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Input Name");

        final EditText input = new EditText(this);

        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                m_Text = input.getText().toString();
                db.addPlayer(m_Text);
                fillSpinners();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void deleteDialog(final int id){
          boolean retVal=false;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Are you sure?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                db.disablePlayer(id);
                fillSpinners();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        mActionMode=null;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_optionadmin,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.optionshowstatistics: {
                    fillStats();
                break;
            }
            case R.id.optionupdatestatistics: {
                    Stat t=((Match)spMatches.getSelectedItem()).getStatForPlayer(((Player)spPlayers.getSelectedItem()).getId());
                if(!goalDefault.getText().toString().equals(""))
                t.setGoalDefault(Integer.parseInt(goalDefault.getText().toString()));
                if(!goalHead.getText().toString().equals(""))
                t.setGoalHead(Integer.parseInt(goalHead.getText().toString()));
                if(!goalHeadsnow.getText().toString().equals(""))
                t.setGoalHeadSnow(Integer.parseInt(goalHeadsnow.getText().toString()));
                if(!goalOwn.getText().toString().equals(""))
                t.setGoalOwn(Integer.parseInt(goalOwn.getText().toString()));
                if(!goalPenalty.getText().toString().equals(""))
                t.setgoalPenalty(Integer.parseInt(goalPenalty.getText().toString()));
                if(!nutmeg.getText().toString().equals(""))
                t.setNutmeg(Integer.parseInt(nutmeg.getText().toString()));


                break;
            }
        }
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getItemAtPosition(position)==spPlayers.getSelectedItem())
            fillMatches();
        fillStats();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        fillStats();
    }
}


