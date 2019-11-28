package util;

import board.Board;
import pieces.King;
import pieces.Piece;
import pieces.PieceSet;

import java.util.ArrayList;
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
        //return true;
    }

   public static List<Piece> whiteTeams() {
        char i;
        int j;
        List<Piece> whites = new ArrayList<Piece>();

        for(i='a'; i<='h'; i+=1) {
            for (j = 1; j < 8; j++) {
               if (Board.getSquare(i, j).getCurrentPiece() != null)
                   if (Board.getSquare(i, j).getCurrentPiece().getColor() == Piece.Color.WHITE) {
                       whites.add(Board.getSquare(i, j).getCurrentPiece());
                   }
            }
        }
        return whites;
   }

    public static List<Piece> blackTeams() {
        char i;
        int j;
        List<Piece> blacks = new ArrayList<Piece>();
        List<Integer, Integer> blackLocation = new ArrayList<Piece>();

        for(i='a'; i<='h'; i+=1) {
            for (j = 1; j < 8; j++) {
                if (Board.getSquare(i, j).getCurrentPiece() != null)
                    if (Board.getSquare(i, j).getCurrentPiece().getColor() == Piece.Color.BLACK) {
                        blacks.add(Board.getSquare(i, j).getCurrentPiece());
                    }
            }
        }
        return blacks;
    }

   public static boolean isCheckMove(Move move) {
        Move tmp_move;
        char i;
        int j;
        List <Piece> whitePieces = whiteTeams();
        // TODO-check
        if(move.getPiece().getColor() == Piece.Color.BLACK){
            for(Piece one_piece : whitePieces){
                tmp_move = new Move(one_piece, )
            }
        }

        for(i='a'; i<='h'; i+=1){
            for(j=1 ; j<8; j++){
                tmp_move = new Move(move.getDestinationFile(), move.getDestinationRank(), i, j);
                if (validateClearPath(tmp_move) && move.getPiece().validateMove(tmp_move)) {
                    if(tmp_move.getCapturedPiece()!=null) {
                        if(tmp_move.getCapturedPiece().getType()== Piece.Type.KING) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static boolean isCheckMate(Move move) {
        // TODO-check
        return false;
    }

    private static boolean validateClearPath(Move move) {
        // TODO-movement
        List<Piece> piecesWhite;
        List<Piece> piecesBlack;

        piecesWhite = PieceSet.getPieces(Piece.Color.WHITE);
        piecesBlack = PieceSet.getPieces(Piece.Color.BLACK);
        PieceSet pieceInstance = PieceSet.getInstance(); //???????????????????
        piecesBlack = pieceInstance.getPieces(Piece.Color.BLACK);

        //System.out.println(piecesBlack.size());

        Move tmp_move;
        char i;
        int j;

        switch (move.getPiece().getType()) {
            case PAWN:
                return true;
            case ROOK:
                if(move.getDestinationFileAsInt()>move.getOriginFileAsInt()){
                    for(i= (char) (move.getOriginFile()+1); i<move.getDestinationFile() ; i++){
                        tmp_move = new Move(move.getOriginFile(), move.getOriginRank(), i, move.getDestinationRank());
                        if (tmp_move.getCapturedPiece() != null) {
                            return false;
                        }
                    }
                }
                else if(move.getDestinationRank()>move.getOriginRank()){
                    for(j=move.getOriginRank()+1; j<move.getDestinationRank() ; j++){
                        tmp_move = new Move(move.getOriginFile(), move.getOriginRank(), move.getDestinationFile(), j);
                        if (tmp_move.getCapturedPiece() != null) {
                            return false;
                        }
                    }
                }
                else if(move.getDestinationFileAsInt()<move.getOriginFileAsInt()){
                    for(i= (char) (move.getOriginFile()-1); i>move.getDestinationFile() ; i--){
                        tmp_move = new Move(move.getOriginFile(), move.getOriginRank(), i, move.getDestinationRank());
                        if (tmp_move.getCapturedPiece() != null) {
                            return false;
                        }
                    }
                }
                else if(move.getDestinationRank()<move.getOriginRank()){
                    for(j=move.getOriginRank()-1; j>move.getDestinationRank() ; j--){
                        tmp_move = new Move(move.getOriginFile(), move.getOriginRank(), move.getDestinationFile(), j);
                        if (tmp_move.getCapturedPiece() != null) {
                            return false;
                        }
                    }
                }
                return true;
                /*for(Piece one_piece : piecesBlack){

                }*/
                /*
                PieceSet.isthere(3, 'c');
                if(move.getDestinationRank()==move.getOriginRank()||move.getDestinationFile()==move.getOriginFile())
                    return true;
                 */
            case KNIGHT:
                return true;
            case BISHOP:
                if(move.getDestinationFileAsInt()>move.getOriginFileAsInt()&&move.getDestinationRank()>move.getOriginRank()){
                    for(i= (char) (move.getOriginFile()+1), j=move.getOriginRank()+1; i<move.getDestinationFile() && j<move.getDestinationRank() ; i++, j++){
                        tmp_move = new Move(move.getOriginFile(), move.getOriginRank(), i, j);
                        if (tmp_move.getCapturedPiece() != null) {
                            return false;
                        }
                    }
                }
                else if(move.getDestinationFileAsInt()>move.getOriginFileAsInt()&&move.getDestinationRank()<move.getOriginRank()){
                    for(i= (char) (move.getOriginFile()+1), j=move.getOriginRank()-1; i<move.getDestinationFile() && j>move.getDestinationRank() ; i++, j--){
                        tmp_move = new Move(move.getOriginFile(), move.getOriginRank(), i, j);
                        if (tmp_move.getCapturedPiece() != null) {
                            return false;
                        }
                    }
                }
                else if(move.getDestinationFileAsInt()<move.getOriginFileAsInt()&&move.getDestinationRank()>move.getOriginRank()){
                    for(i= (char) (move.getOriginFile()-1), j=move.getOriginRank()+1; i>move.getDestinationFile() && j<move.getDestinationRank() ; i--, j++){
                        tmp_move = new Move(move.getOriginFile(), move.getOriginRank(), i, j);
                        if (tmp_move.getCapturedPiece() != null) {
                            return false;
                        }
                    }
                }
                if(move.getDestinationFileAsInt()<move.getOriginFileAsInt()&&move.getDestinationRank()<move.getOriginRank()){
                    for(i= (char) (move.getOriginFile()-1), j=move.getOriginRank()-1; i>move.getDestinationFile() && j>move.getDestinationRank() ; i--, j--){
                        tmp_move = new Move(move.getOriginFile(), move.getOriginRank(), i, j);
                        if (tmp_move.getCapturedPiece() != null) {
                            return false;
                        }
                    }
                }
                return true;
            case QUEEN:
                if(move.getDestinationFileAsInt()>move.getOriginFileAsInt()){
                    for(i= (char) (move.getOriginFile()+1); i<move.getDestinationFile() ; i++){
                        tmp_move = new Move(move.getOriginFile(), move.getOriginRank(), i, move.getDestinationRank());
                        if (tmp_move.getCapturedPiece() != null) {
                            return false;
                        }
                    }
                }
                else if(move.getDestinationRank()>move.getOriginRank()){
                    for(j=move.getOriginRank()+1; j<move.getDestinationRank() ; j++){
                        tmp_move = new Move(move.getOriginFile(), move.getOriginRank(), move.getDestinationFile(), j);
                        if (tmp_move.getCapturedPiece() != null) {
                            return false;
                        }
                    }
                }
                else if(move.getDestinationFileAsInt()<move.getOriginFileAsInt()){
                    for(i= (char) (move.getOriginFile()-1); i>move.getDestinationFile() ; i--){
                        tmp_move = new Move(move.getOriginFile(), move.getOriginRank(), i, move.getDestinationRank());
                        if (tmp_move.getCapturedPiece() != null) {
                            return false;
                        }
                    }
                }
                else if(move.getDestinationRank()<move.getOriginRank()){
                    for(j=move.getOriginRank()-1; j>move.getDestinationRank() ; j--){
                        tmp_move = new Move(move.getOriginFile(), move.getOriginRank(), move.getDestinationFile(), j);
                        if (tmp_move.getCapturedPiece() != null) {
                            return false;
                        }
                    }
                }
                if(move.getDestinationFileAsInt()>move.getOriginFileAsInt()&&move.getDestinationRank()>move.getOriginRank()){
                    for(i= (char) (move.getOriginFile()+1), j=move.getOriginRank()+1; i<move.getDestinationFile() && j<move.getDestinationRank() ; i++, j++){
                        tmp_move = new Move(move.getOriginFile(), move.getOriginRank(), i, j);
                        if (tmp_move.getCapturedPiece() != null) {
                            return false;
                        }
                    }
                }
                else if(move.getDestinationFileAsInt()>move.getOriginFileAsInt()&&move.getDestinationRank()<move.getOriginRank()){
                    for(i= (char) (move.getOriginFile()+1), j=move.getOriginRank()-1; i<move.getDestinationFile() && j>move.getDestinationRank() ; i++, j--){
                        tmp_move = new Move(move.getOriginFile(), move.getOriginRank(), i, j);
                        if (tmp_move.getCapturedPiece() != null) {
                            return false;
                        }
                    }
                }
                else if(move.getDestinationFileAsInt()<move.getOriginFileAsInt()&&move.getDestinationRank()>move.getOriginRank()){
                    for(i= (char) (move.getOriginFile()-1), j=move.getOriginRank()+1; i>move.getDestinationFile() && j<move.getDestinationRank() ; i--, j++){
                        tmp_move = new Move(move.getOriginFile(), move.getOriginRank(), i, j);
                        if (tmp_move.getCapturedPiece() != null) {
                            return false;
                        }
                    }
                }
                if(move.getDestinationFileAsInt()<move.getOriginFileAsInt()&&move.getDestinationRank()<move.getOriginRank()){
                    for(i= (char) (move.getOriginFile()-1), j=move.getOriginRank()-1; i>move.getDestinationFile() && j>move.getDestinationRank() ; i--, j--){
                        tmp_move = new Move(move.getOriginFile(), move.getOriginRank(), i, j);
                        if (tmp_move.getCapturedPiece() != null) {
                            return false;
                        }
                    }
                }
                return true;
            case KING:
                return true;
        }


        return false; //////initially false dssa;nf;kjsdn;kfjsadb;jfkbasd;kfbk;sdabf !!!!!!!!!!!To test
    }

}
