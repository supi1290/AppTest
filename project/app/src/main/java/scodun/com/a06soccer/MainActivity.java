package scodun.com.a06soccer;

import android.content.Intent;
import android.graphics.Paint;
import android.view.View.OnClickListener;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TextView;
import android.widget.Toast;

import scodun.com.a06soccer.activitys.AdminActivity;
import scodun.com.a06soccer.activitys.UserActivity;
import scodun.com.a06soccer.misc.Captain;
import scodun.com.a06soccer.misc.Database;
import scodun.com.a06soccer.misc.Player;

public class MainActivity extends AppCompatActivity implements OnItemSelectedListener, OnClickListener {

    private Spinner spPlayers;
    private Database db;
    private EditText captainPasswordField;
    private TextView passwordText;
    private FloatingActionButton logInButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
try {
    db = Database.newInstance();

    getAllViews();
    registrateEventhandlers();
    fillSpinners();
}catch (Exception e){

}


    }
    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        fillSpinners();
    }

    private void getAllViews(){
        spPlayers=(Spinner)this.findViewById(R.id.spPlayers);
        captainPasswordField=(EditText)this.findViewById(R.id.captainPasswordInput);
        passwordText=(TextView)this.findViewById(R.id.passwordText);
        logInButton=(FloatingActionButton)this.findViewById(R.id.logInButton);
    }
    private void registrateEventhandlers(){
        spPlayers.setOnItemSelectedListener(this);
        logInButton.setOnClickListener(this);
    }
    private void fillSpinners(){
        ArrayAdapter<Player> adapterRegions =new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,db.getPlayers());
        adapterRegions.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPlayers.setAdapter(adapterRegions);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(spPlayers.getSelectedItem().getClass()== Captain.class){
            captainPasswordField.setVisibility(View.VISIBLE);
            passwordText.setVisibility(View.VISIBLE);
        }else{
            captainPasswordField.setVisibility(View.INVISIBLE);
            passwordText.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {


        if(spPlayers.getSelectedItem().getClass()!= Captain.class) {
            Intent intent = new Intent(this, UserActivity.class);
            intent.putExtra("player", ((Player) spPlayers.getSelectedItem()).getId());
            startActivity(intent);
        }
        else if(0==captainPasswordField.getText().toString().compareTo(((Captain)spPlayers.getSelectedItem()).getPassword())){
            Intent intent = new Intent(this, AdminActivity.class);
            intent.putExtra("player", ((Player) spPlayers.getSelectedItem()).getId());
            startActivity(intent);
        }
    }
}
