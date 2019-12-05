package util;

import board.Board;
import pieces.Piece;

import java.io.Serializable;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class State implements Serializable {
    public int number;
    public time Times;
    public List<Piece> Pieces;
    public List<location> Locations;
    //public List<MoveLogger.MoveRound> moveHistory;
    public String moveHistory;
    public Piece.Color color;

    public void setColor(Piece.Color color) {
        this.color = color;
    }

    public Piece.Color getColor() {
        return color;
    }

    public void setInput(int input){
        number+= input;
    }

    public void setTimes(time time) {
        this.Times = time;
    }

    public time getTimes() {
        return Times;
    }

    public void setHistory(String x) {
        this.moveHistory = x;
    }

    public String getHistory() {
        return moveHistory;
    }

    /*
    public void setMoveList(List<MoveLogger.MoveRound> x) {
        this.moveHistory = x;
    }

    public List<MoveLogger.MoveRound> getMoveList() {
        return moveHistory;
    }
    */

    public int getInput() {
        return number;
    }

    public void setPieces(List<Piece> input){
        Pieces = input;
    }

    public List<Piece> getPieces() {
        return Pieces;
    }

    public void setLocations(List<location> input) {
        Locations = input;
    }

    public List<location> getLocations() {
        return Locations;
    }
}
