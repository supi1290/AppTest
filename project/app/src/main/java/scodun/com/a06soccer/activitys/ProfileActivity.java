package scodun.com.a06soccer.activitys;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import java.util.ArrayList;

import scodun.com.a06soccer.R;
import scodun.com.a06soccer.misc.Database;
import scodun.com.a06soccer.misc.Player;
import scodun.com.a06soccer.misc.Position;

/**
 * Created by Raphael Burgstaller on 10/03/2017.
 */

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private FloatingActionButton savebutton,resetbutton;
    private CheckBox CBgoalie,CBdefender,CBMidfield,CBforward;
    private Player player;
    private Database db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        try {
            db= Database.newInstance();
            getAllViews();
            registrateEventhandlers();
            player=db.getPlayer(this.getIntent().getExtras().getInt("player"));
            getSupportActionBar().setTitle("Profile of: "+player.getName());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            setPositions();

        }catch(Exception E){
        }
    }

    private void setPositions(){
        if(player.getPositions().contains(Position.GOALIE))
            CBgoalie.setChecked(true);
        else
            CBgoalie.setChecked(false);
        if(player.getPositions().contains(Position.DEFENDER))
            CBdefender.setChecked(true);
        else
            CBdefender.setChecked(false);
        if(player.getPositions().contains(Position.FORWARD))
            CBforward.setChecked(true);
        else
            CBforward.setChecked(false);
        if(player.getPositions().contains(Position.MIDFIELDER))
            CBMidfield.setChecked(true);
        else
            CBMidfield.setChecked(false);



    }
    private void getAllViews(){
        savebutton=(FloatingActionButton)this.findViewById(R.id.btnSave);
        resetbutton=(FloatingActionButton)this.findViewById(R.id.btnreset);
        CBdefender=(CheckBox)this.findViewById(R.id.checkDefender);
        CBforward=(CheckBox)this.findViewById(R.id.checkForward);
        CBgoalie=(CheckBox)this.findViewById(R.id.checkGoalie);
        CBMidfield=(CheckBox)this.findViewById(R.id.checkMidfielder);
    }
    private void registrateEventhandlers(){
        savebutton.setOnClickListener(this);
        resetbutton.setOnClickListener(this);


    }
    @Override
    public void onClick(View v) {
        if(v.equals(savebutton)){
            ArrayList<Position>temp=new ArrayList<>();
            if(CBdefender.isChecked())
                temp.add(Position.DEFENDER);
            if(CBMidfield.isChecked())
                temp.add(Position.MIDFIELDER);
            if(CBforward.isChecked())
                temp.add(Position.FORWARD);
            if(CBgoalie.isChecked())
                temp.add(Position.GOALIE);
            player.setPositions(temp);

            Toast.makeText(this,"Saved",Toast.LENGTH_SHORT).show();
        }
        if(v.equals(resetbutton)){
        setPositions();
            Toast.makeText(this,"Reseted",Toast.LENGTH_SHORT).show();
        }
    }


}
