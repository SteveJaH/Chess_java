package util;

import board.Board;
import com.sun.source.tree.WhileLoopTree;
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

    public static void setColor(Piece.Color color){
        if(color == Piece.Color.WHITE){
            currentMoveColor = Piece.Color.BLACK;
        }
        else{
            currentMoveColor = Piece.Color.WHITE;
        }
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
            for (j = 1; j <= 8; j++) {
               if (Board.getSquare(i, j).getCurrentPiece() != null)
                   if (Board.getSquare(i, j).getCurrentPiece().getColor() == Piece.Color.WHITE) {
                       whites.add(Board.getSquare(i, j).getCurrentPiece());
                   }
            }
        }
        return whites;
   }

    public static List<location> whiteLocations() {
        char i;
        int j;
        List<location> whiteLocation = new ArrayList<location>();

        for(i='a'; i<='h'; i+=1) {
            for (j = 1; j <= 8; j++) {
                if (Board.getSquare(i, j).getCurrentPiece() != null)
                    if (Board.getSquare(i, j).getCurrentPiece().getColor() == Piece.Color.WHITE) {
                        location location = new location(i, j);
                        whiteLocation.add(location);
                    }
            }
        }
        return whiteLocation;
    }

    public static List<location> whiteKingLocations() {
        char i;
        int j;
        List<location> whiteKingLocation = new ArrayList<location>();

        for(i='a'; i<='h'; i+=1) {
            for (j = 1; j <= 8; j++) {
                if (Board.getSquare(i, j).getCurrentPiece() != null)
                    if (Board.getSquare(i, j).getCurrentPiece().getColor() == Piece.Color.WHITE) {
                        if(Board.getSquare(i, j).getCurrentPiece().getType() == Piece.Type.KING){
                            location location = new location(i, j);
                            whiteKingLocation.add(location);
                        }
                    }
            }
        }
        return whiteKingLocation;
    }

    public static List<Piece> blackTeams() {
        char i;
        int j;
        List<Piece> blacks = new ArrayList<Piece>();
        List<location> blackLocation = new ArrayList<location>();

        for(i='a'; i<='h'; i+=1) {
            for (j = 1; j <= 8; j++) {
                if (Board.getSquare(i, j).getCurrentPiece() != null)
                    if (Board.getSquare(i, j).getCurrentPiece().getColor() == Piece.Color.BLACK) {
                        blacks.add(Board.getSquare(i, j).getCurrentPiece());
                    }
            }
        }
        return blacks;
    }

    public static List<location> blackLocations() {
        char i;
        int j;
        List<location> blackLocation = new ArrayList<location>();

        for(i='a'; i<='h'; i+=1) {
            for (j = 1; j <= 8; j++) {
                if (Board.getSquare(i, j).getCurrentPiece() != null)
                    if (Board.getSquare(i, j).getCurrentPiece().getColor() == Piece.Color.BLACK) {
                        location location = new location(i, j);
                        blackLocation.add(location);
                    }
            }
        }
        return blackLocation;
    }

    public static List<location> blackKingLocations() {
        char i;
        int j;
        List<location> blackKingLocation = new ArrayList<location>();

        for(i='a'; i<='h'; i+=1) {
            for (j = 1; j <= 8; j++) {
                if (Board.getSquare(i, j).getCurrentPiece() != null)
                    if (Board.getSquare(i, j).getCurrentPiece().getColor() == Piece.Color.BLACK) {
                        if(Board.getSquare(i, j).getCurrentPiece().getType().equals(Piece.Type.KING)){
                            location location = new location(i, j);
                            blackKingLocation.add(location);
                        }
                    }
            }
        }
        return blackKingLocation;
    }

   public static boolean isCheckMove(Move move) {
        Move tmp_move;
        int k;
        List <Piece> whitePieces = whiteTeams();
        List <Piece> blackPieces = blackTeams();
        List <location> whiteLocations = whiteLocations();
        List <location> blackLocations = blackLocations();
        List <location> whiteKingLocations = whiteKingLocations();
        List <location> blackKingLocations = blackKingLocations();

        // TODO-check
       if(whiteKingLocations!=null && blackKingLocations!=null) {
           for (k = 0; k < whitePieces.size(); k++) {
               tmp_move = new Move(whitePieces.get(k), whiteLocations.get(k).getx(), whiteLocations.get(k).gety(), blackKingLocations.get(0).getx(), blackKingLocations.get(0).gety());
               if (validateClearPath(tmp_move) && whitePieces.get(k).validateMove(tmp_move)) {
                   return true;
               }
           }

           for (k = 0; k < blackPieces.size(); k++) {
               tmp_move = new Move(blackPieces.get(k), blackLocations.get(k).getx(), blackLocations.get(k).gety(), whiteKingLocations.get(0).getx(), whiteKingLocations.get(0).gety());
               if (validateClearPath(tmp_move) && blackPieces.get(k).validateMove(tmp_move)) {
                   return true;
               }
           }
       }

       /* for(k=0; k<whitePieces.size();k++){
            for(i='a'; i<='h'; i+=1) {
                for (j = 1; j < 8; j++) {
                    tmp_move = new Move(whitePieces.get(k), whiteLocations.get(k).getx(), whiteLocations.get(k).gety(), i, j);
                    if (validateClearPath(tmp_move) && tmp_move.getPiece().validateMove(tmp_move)) {
                        if(tmp_move.getCapturedPiece()!=null) {
                            if(tmp_move.getCapturedPiece().getType()== Piece.Type.KING) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        //}

       for(k=0; k<blackPieces.size();k++){
           for(i='a'; i<='h'; i+=1) {
               for (j = 1; j < 8; j++) {
                   tmp_move = new Move(blackPieces.get(k), blackLocations.get(k).getx(), blackLocations.get(k).gety(), i, j);
                   if (validateClearPath(tmp_move) && tmp_move.getPiece().validateMove(tmp_move)) {
                       if(tmp_move.getCapturedPiece()!=null) {
                           if(tmp_move.getCapturedPiece().getType()== Piece.Type.KING ) {
                               return true;
                           }
                       }
                   }
               }
           }
       }*/
        return false;
    }

    public static boolean isCheckMate(Move move) {

        Move tmp_move, tmp_move2;
        char i;
        int j, k;
        boolean white, black;


        List <Piece> whitePieces = whiteTeams();
        List <Piece> blackPieces = blackTeams();
        List <location> whiteLocations = whiteLocations();
        List <location> blackLocations = blackLocations();

        if(move.getPiece().getColor()== Piece.Color.WHITE) {
            for (k = 0; k < blackPieces.size(); k++) {
                for (i = 'a'; i <= 'h'; i += 1) {
                    for (j = 1; j <= 8; j += 1) {
                        tmp_move = new Move(blackPieces.get(k), blackLocations.get(k).getx(), blackLocations.get(k).gety(), i, j);
                        if (validateClearPath(tmp_move) && blackPieces.get(k).validateMove(tmp_move)) {
                            //MoveLogger.addMove(tmp_move);
                            Piece resurrection = tmp_move.getCapturedPiece();
                            Board.executeMove(tmp_move);
                            System.out.println(tmp_move.getInfo());
                            try {
                                if (!isCheckMove(tmp_move)) {
                                    System.out.println(tmp_move.getInfo());
                                    Board.executeMove_back(tmp_move, resurrection);
                                    System.out.println("1");
                                    return false;
                                }
                            }catch (Exception IndexOutofBoundsException){
                                System.out.println(tmp_move.getInfo());
                                Board.executeMove_back(tmp_move, resurrection);
                                return true;
                            }
                            Board.executeMove_back(tmp_move, resurrection);
                        }
                    }
                }
            }
        }

        else {
            for (k = 0; k < whitePieces.size(); k++) {
                for (i = 'a'; i <= 'h'; i += 1) {
                    for (j = 1; j <= 8; j += 1) {
                        tmp_move = new Move(whitePieces.get(k), whiteLocations.get(k).getx(), whiteLocations.get(k).gety(), i, j);
                        tmp_move2 = new Move(whitePieces.get(k), i, j, whiteLocations.get(k).getx(), whiteLocations.get(k).gety());
                        if (validateClearPath(tmp_move) && whitePieces.get(k).validateMove(tmp_move)) {
                            Piece resurrection = tmp_move.getCapturedPiece();
                            Board.executeMove(tmp_move);
                            System.out.println(tmp_move.getInfo());
                            try {
                                if (!isCheckMove(tmp_move)) {
                                    System.out.println(tmp_move.getInfo());
                                    Board.executeMove_back(tmp_move, resurrection);
                                    System.out.println("2");
                                    return false;
                                }
                            }catch (Exception IndexOutofBoundsException){
                                System.out.println(tmp_move.getInfo());
                                Board.executeMove_back(tmp_move, resurrection);
                                return true;
                            }
                            Board.executeMove_back(tmp_move, resurrection);
                        }
                    }
                }
            }
        }

        /*
        if(move.getPiece().getColor()== Piece.Color.WHITE){
            for(k=0; k<whitePieces_tmp.size();k++){
                if(whiteLocations_tmp.get(k).getx()==move.getOriginFile() && whiteLocations_tmp.get(k).gety()==move.getOriginRank()){
                    whiteLocations_tmp.get(k).setx(move.getDestinationFile());
                    whiteLocations_tmp.get(k).sety(move.getDestinationRank());
                    System.out.println("hihi");
                    if(move.getCapturedPiece()!=null){
                        tmp_loc = new location(move.getDestinationFile(), move.getDestinationRank());
                        blackPieces_tmp.remove(move.getCapturedPiece());
                        blackLocations_tmp.remove(tmp_loc);
                    }
                }
            }



        }

        else {
            for (k = 0; k < blackPieces_tmp.size(); k++) {
                if (blackLocations_tmp.get(k).getx() == move.getOriginFile() && blackLocations_tmp.get(k).gety() == move.getOriginRank()) {
                    blackLocations_tmp.get(k).setx(move.getDestinationFile());
                    blackLocations_tmp.get(k).sety(move.getDestinationRank());
                    if (move.getCapturedPiece() != null) {
                        tmp_loc = new location(move.getDestinationFile(), move.getDestinationRank());
                        whitePieces_tmp.remove(move.getCapturedPiece());
                        whiteLocations_tmp.remove(tmp_loc);
                    }
                }
            }
            System.out.println("hihissssssssssss");
            //outer:

        }*/



        /*
        if i do a move, we check isCheckMate.
        I am white, I do move now.
        It it checkmate?
        I moved white object, and its black's turn.
        I want to catch black king. maybe my move is checkmove,


         */

        /*

        Move tmp_move, tmp_move2;
        char i;
        int j, k, l;
        int p, q, a, b;
        a=0;
        b=0;
        List <Piece> whitePieces = whiteTeams();
        List <Piece> blackPieces = blackTeams();
        List <location> whiteLocations = whiteLocations();
        List <location> blackLocations = blackLocations();
        List <location> whiteKingLocations = whiteKingLocations();
        List <location> blackKingLocations = blackKingLocations();

        List <location> whiteLocations_tmp = whiteLocations();
        List <location> blackLocations_tmp = blackLocations();
        List <location> whiteKingLocations_tmp = whiteKingLocations();
        List <location> blackKingLocations_tmp = blackKingLocations();

        if(move.getPiece().getColor() == Piece.Color.WHITE){
            //move blacks, check all moves, if all moves causes checkmove; now white moves, so we have to check white blocks can reach to black king after black's move
            for(k=0; k<blackPieces.size();k++){
                a=0;
                b=0;
                for(i='a'; i<='h'; i+=1) {
                    for (j = 1; j <= 8; j++) {
                        tmp_move = new Move(blackPieces.get(k), blackLocations.get(k).getx(), blackLocations.get(k).gety(), i, j);
                        if (validateClearPath(tmp_move) && tmp_move.getPiece().validateMove(tmp_move)) {
                            //execute move
                            //blackLocations_tmp.get(k).setx(i);
                            //blackLocations_tmp.get(k).sety(j);
                            System.out.println("Hiiiii");

                            p=0;
                            q=0;
                            for(l=0; l<whitePieces.size(); l++) {
                                tmp_move2 = new Move(whitePieces.get(l), whiteLocations.get(l).getx(), whiteLocations.get(l).gety(), blackKingLocations.get(0).getx(), blackKingLocations.get(0).gety());
                                if(validateClearPath(tmp_move2) && whitePieces.get(l).validateMove(tmp_move2)) {
                                    p=p+1;
                                    if(isCheckMove(tmp_move2)){
                                        q=q+1;
                                    }
                                }
                            }

                            if(p==q) {
                                a=a+1;
                            }
                            b=b+1;

                        }
                    }
                }
            }
            if(a==b)
                return true;
        }*/
        /*
        else {
            for(k=0; k<whitePieces.size();k++){
                for(i='a'; i<='h'; i+=1) {
                    for (j = 1; j <= 8; j++) {
                        System.out.println("HI");
                        System.out.println(whitePieces.size());
                        System.out.println(k);
                        System.out.println(i);
                        System.out.println(j);
                        tmp_move = new Move(whitePieces.get(k), whiteLocations.get(k).getx(), whiteLocations.get(k).gety(), i, j);
                        if (validateClearPath(tmp_move) && tmp_move.getPiece().validateMove(tmp_move)) {
                            for(l=0; l<whitePieces.size();l++){
                                tmp_move2 = new Move(whitePieces.get(l), whiteLocations.get(l).getx(), whiteLocations.get(l).gety(), blackKingLocations.get(0).getx(), blackKingLocations.get(0).gety());
                                if(validateClearPath(tmp_move2) && whitePieces.get(l).validateMove(tmp_move2)) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }*/

        // TODO-check
        System.out.println("3");
        return true; //false
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
                if(move.getDestinationFileAsInt()>move.getOriginFileAsInt()&&move.getDestinationRank()==move.getOriginRank()){
                    for(i= (char) (move.getOriginFile()+1); i<move.getDestinationFile() ; i++){
                        tmp_move = new Move(move.getOriginFile(), move.getOriginRank(), i, move.getDestinationRank());
                        if (tmp_move.getCapturedPiece() != null) {
                            return false;
                        }
                    }
                }
                else if(move.getDestinationRank()>move.getOriginRank()&&move.getDestinationFileAsInt()==move.getOriginFileAsInt()){
                    for(j=move.getOriginRank()+1; j<move.getDestinationRank() ; j++){
                        tmp_move = new Move(move.getOriginFile(), move.getOriginRank(), move.getDestinationFile(), j);
                        if (tmp_move.getCapturedPiece() != null) {
                            return false;
                        }
                    }
                }
                else if(move.getDestinationFileAsInt()<move.getOriginFileAsInt()&&move.getDestinationRank()==move.getOriginRank()){
                    for(i= (char) (move.getOriginFile()-1); i>move.getDestinationFile() ; i--){
                        tmp_move = new Move(move.getOriginFile(), move.getOriginRank(), i, move.getDestinationRank());
                        if (tmp_move.getCapturedPiece() != null) {
                            return false;
                        }
                    }
                }
                else if(move.getDestinationRank()<move.getOriginRank()&&move.getDestinationFileAsInt()==move.getOriginFileAsInt()){
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
                else if(move.getDestinationFileAsInt()<move.getOriginFileAsInt()&&move.getDestinationRank()<move.getOriginRank()){
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
