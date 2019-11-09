package util;

import board.Board;
import pieces.Piece;
import pieces.PieceSet;

import java.util.List;

public class MoveValidator {

    private static MoveValidator ourInstance = new MoveValidator();

    public static MoveValidator getInstance() {
        return ourInstance;
    }

    private MoveValidator() {
        currentMoveColor = Piece.Color.WHITE;
    }

    private static Piece.Color currentMoveColor;

    public static boolean validateMove(Move move) {
        return validateMove(move, true);
    } //false actually

    public static boolean validateMove(Move move, boolean ignoreColorCheck) {

        // check for out of bounds
        if (move.getDestinationFile() < 'a' || move.getDestinationFile() > 'h'
                || move.getDestinationRank() < 1 || move.getDestinationRank() > 8) {
            return false;
        }

        // check for valid origin
        if (move.getPiece() == null) {
            System.out.println("1");
            return false;
        }

        // check for valid color
        if (!move.getPiece().getColor().equals(currentMoveColor) && !ignoreColorCheck) {
            System.out.println("2");
            return false;
        }

        // check for valid destination
        if (move.getCapturedPiece() != null) {
            if (move.getPiece().getColor().equals(move.getCapturedPiece().getColor())) {
                System.out.println("3");
                return false;
            }
        }

        // check for piece rule
        if (!move.getPiece().validateMove(move)) {
            System.out.println("4");
            return false;
        }

        // check for clear path
        if (!validateClearPath(move)) {
            System.out.println("5");
            return false;
        }

        currentMoveColor = currentMoveColor.equals(Piece.Color.WHITE) ? Piece.Color.BLACK : Piece.Color.WHITE;
        return true;
        //return true; //dsaknd;kfajn;kbjs;kdjsbf;asdjbf;ksjb
    }

    public static boolean isCheckMove(Move move) {
        // TODO-check
        return false;
    }

    public static boolean isCheckMate(Move move) {
        // TODO-check
        return false;
    }

    private static boolean validateClearPath(Move move) {
        // TODO-movement

        switch (move.getPiece().getType()) {
            case PAWN:
                if(move.getPiece().getColor()==Piece.Color.WHITE){
                    if(move.getOriginRank()==2){
                        if(move.getDestinationRank()-move.getOriginRank()==2 || move.getDestinationRank()-move.getOriginRank()==1)
                            return true;
                    }
                    //8 if it goes to end !!!!!!!
                    else{
                        if(move.getDestinationRank()-move.getOriginRank()==1)
                            return true;
                    }
                }
                else{
                    if(move.getOriginRank()==7){
                        if(move.getDestinationRank()-move.getOriginRank()==-2 || move.getDestinationRank()-move.getOriginRank()==-1)
                            return true;
                    }
                    //8 if it goes to end !!!!!!!
                    else{
                        if(move.getDestinationRank()-move.getOriginRank()==-1)
                            return true;
                    }
                }
        }

        return false;
    }

}
