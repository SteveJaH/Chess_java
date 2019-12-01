package util;

import board.Board;
import pieces.Piece;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class State implements Serializable {
    public int number;
    public List<Piece> Pieces;
    public List<location> Locations;


    public void setInput(int input){
        number+= input;
    }

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
