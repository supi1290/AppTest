package scodun.com.a06soccer.misc;

/**
 * Created by Raphael Burgstaller on 11/03/2017.
 */

public class Stat {
        private Player player;
        private Position position;
        private int goalDefault=0,GoalPenalty=0,GoalHead=0,GoalHeadSnow=0,GoalOwn=0,Nutmeg=0;

        public Stat(Player p){
            player=p;
        }

    public Stat(Player player, Position position, int goalDefault, int goalPenalty, int goalHead, int goalHeadSnow, int goalOwn, int nutmeg) {
        this.player = player;
        this.position = position;
        this.goalDefault = goalDefault;
        GoalPenalty = goalPenalty;
        GoalHead = goalHead;
        GoalHeadSnow = goalHeadSnow;
        GoalOwn = goalOwn;
        Nutmeg = nutmeg;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public int getGoalDefault() {
        return goalDefault;
    }

    public void setGoalDefault(int goalDefault) {
        this.goalDefault = goalDefault;
    }

    public int getgoalPenalty() {
        return GoalPenalty;
    }

    public void setgoalPenalty(int goalPenalty) {
        GoalPenalty = goalPenalty;
    }

    public int getGoalHead() {
        return GoalHead;
    }

    public void setGoalHead(int goalHead) {
        GoalHead = goalHead;
    }

    public int getGoalHeadSnow() {
        return GoalHeadSnow;
    }

    public void setGoalHeadSnow(int goalHeadSnow) {
        GoalHeadSnow = goalHeadSnow;
    }

    public int getGoalOwn() {
        return GoalOwn;
    }

    public void setGoalOwn(int goalOwn) {
        GoalOwn = goalOwn;
    }

    public int getNutmeg() {
        return Nutmeg;
    }

    public void setNutmeg(int nutmeg) {
        Nutmeg = nutmeg;
    }
}
