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
        return validateMove(move, false);
    } //false actually

    public static boolean validateMove(Move move, boolean ignoreColorCheck) {

        // check for out of bounds
        if (move.getDestinationFile() < 'a' || move.getDestinationFile() > 'h'
                || move.getDestinationRank() < 1 || move.getDestinationRank() > 8) {
            return false;
        }

        // check for valid origin
        if (move.getPiece() == null) {
            return false;
        }

        // check for valid color
        if (!move.getPiece().getColor().equals(currentMoveColor) && !ignoreColorCheck) {
            return false;
        }

        // check for valid destination
        if (move.getCapturedPiece() != null) {
            if (move.getPiece().getColor().equals(move.getCapturedPiece().getColor())) {
                return false;
            }
        }

        // check for piece rule
        if (!move.getPiece().validateMove(move)) {
            return false;
        }

        // check for clear path
        if (!validateClearPath(move)) {
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
        System.out.println("here");
        List<Piece> piecesWhite;
        List<Piece> piecesBlack;

        piecesWhite = PieceSet.getPieces(Piece.Color.WHITE);
        piecesBlack = PieceSet.getPieces(Piece.Color.BLACK);
        PieceSet pieceInstance = PieceSet.getInstance(); //???????????????????
        piecesBlack = pieceInstance.getPieces(Piece.Color.WHITE);

        System.out.println(piecesBlack.size());

        switch (move.getPiece().getType()) {
            case PAWN:
                return true;
            case ROOK:
                /*for(Piece one_piece : piecesBlack){

                }*/
                if(move.getDestinationRank()==move.getOriginRank()||move.getDestinationFile()==move.getOriginFile())
                    return true;
                break;
            case BISHOP:
                if(move.getDestinationFileAsInt()-move.getOriginFileAsInt()==move.getDestinationRank()-move.getOriginRank())
                    return true;
                break;
        }


        return false; //////initially false dssa;nf;kjsdn;kfjsadb;jfkbasd;kfbk;sdabf !!!!!!!!!!!To test
    }

}
