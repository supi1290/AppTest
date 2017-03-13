package scodun.com.a06soccer.misc;

import java.util.ArrayList;

/**
 * Created by Raphael Burgstaller on 10/03/2017.
 */

public class Captain extends Player {
    private String password;
    public Captain(int _id, String _name, ArrayList<Position> _positions, String _password) {
        super(_id, _name, _positions);
        password=_password;
    }

    public Captain(int id,String _name,String _password) {
        super(id,_name);
        password=_password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
