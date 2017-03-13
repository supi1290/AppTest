package scodun.com.a06soccer.misc;

import java.util.ArrayList;

/**
 * Created by Raphael Burgstaller on 10/03/2017.
 */

public class Player {
    private String name;
    private ArrayList<Position> positions=new ArrayList<>();
    int id;
    boolean isInactive;

    public Player(int _id,String _name, ArrayList<Position> _position){
        id=_id;
        name=_name;
        positions=_position;
    }
    public Player(int _id,String _name){
        name=_name;
        id=_id;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Position> getPositions() {
        return positions;
    }

    public void setPositions(ArrayList<Position> positions) {
        this.positions = positions;
    }

    public int getId() {
        return id;
    }

    public boolean isInactive() {
        return isInactive;
    }

    public void setInactive(boolean inactive) {
        isInactive = inactive;
    }
}
