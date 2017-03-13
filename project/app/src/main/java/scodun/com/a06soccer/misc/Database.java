package scodun.com.a06soccer.misc;

import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Proxy;
import java.net.URI;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Created by Raphael Burgstaller on 10/03/2017.
 */

public class Database {
    private static Database database =null;
    private TreeMap<Integer,Player> players= new TreeMap<>();
    private TreeMap<Integer, Match> matches=new TreeMap<>();
    private int idofhighestMatches,idofhigestPlayers;

    private Database() throws Exception{
        fillData();
        refreshUsers();
    }

    public static Database newInstance() throws Exception{
        if(database==null)
            database=new Database();
        return database;
    }

    private void fillData(){
        fillPlayers();
        fillMatches();
    }
    public void addMatch(){
        matches.put(idofhighestMatches+1,new Match(idofhighestMatches+1));
        idofhighestMatches++;
    }
    private void fillPlayers(){
        ArrayList<Position>pos=new ArrayList<>();
        pos.add(Position.FORWARD);
        players.put(0,new Player(0,"Franz Gurkenschießer"));
        players.put(1,new Player(1,"Hugo Hupfbaum"));
        players.put(2,new Player(2,"Saltoheinz Lauf"));
        players.put(3,new Player(3,"Speedy Ortner",pos));
        players.put(4,new Player(4,"Hulk Ballkicker",pos));
        players.put(5,new Captain(5,"Armin Adminner",pos,"admin"));
        idofhigestPlayers=5;
    }
    private void fillMatches(){
        matches.put(matches.size(),new Match(matches.size()));
        matches.get(0).getT1().add(new Stat(players.get(2)));
        idofhighestMatches=0;
    }
    public void removeMatch(int id){
        matches.remove(id);
    }
    public ArrayList<Player> getPlayers(){
        ArrayList<Player>temp=new ArrayList<>(players.values());
        ArrayList<Player>temp2=new ArrayList<>(players.values());

        for(Player p:temp){
            if(p.isInactive)
                temp2.remove(p);
        }
        return temp2;
    }
    public Player getPlayer(int _id){
        return players.get(_id);
    }
    public ArrayList<Match> getMatches(){
        return new ArrayList<Match>(matches.values());
    }
    public Match getMatch(int _id){
        return matches.get(_id);
    }
    public ArrayList<Match> getMatchesforplayer(Integer _id){
        ArrayList<Match>temp=new ArrayList<>();
        for(int i=0;i<matches.size();i++){
            for(Stat s:matches.get(i).getT1()){
                if(s.getPlayer().equals(players.get(_id)))
                    temp.add(matches.get(i));
            }
            for(Stat s:matches.get(i).getT2()){
                if(s.getPlayer().equals(players.get(_id)))
                    temp.add(matches.get(i));
            }


        }
        return temp;
    }
    public void addPlayer(String _name){
        players.put(idofhigestPlayers+1,new Player(idofhigestPlayers+1,_name));
        idofhigestPlayers++;

    }
    public void disablePlayer(int _id){
        players.get(_id).setInactive(true);
    }

    private void refreshUsers() {
        try
        {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            URI uri = new URI("http://192.168.193.139:6466/Service1.svc");

            HttpGet httpget = new HttpGet(uri + "/GetPlayers");
            httpget.setHeader("Accept", "application/json");
            httpget.setHeader("Content-type", "application/json; charset=utf-8");

            HttpResponse response = httpClient.execute(httpget);
            HttpEntity responseEntity = response.getEntity();

            long intCount = responseEntity.getContentLength();
            char[] buffer = new char[(int)intCount];
            InputStream stream = responseEntity.getContent();
            InputStreamReader reader = new InputStreamReader(stream, "UTF-8");
            try
            {
                reader.read(buffer);
                String str = new String(buffer);

            }

            catch (IOException e)
            {
                e.printStackTrace();
            }
            stream.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
