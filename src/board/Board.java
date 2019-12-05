package board;

import pieces.*;
import util.Core;
import util.Move;

import java.util.Iterator;
import java.util.List;

public class Board {

    public static final int DIMENSION = 8;

    private static Square[][] grid = new Square[DIMENSION][DIMENSION];

    private static Board boardInstance = new Board();

    public static Board getInstance() {
        return boardInstance;
    }

    private Board() {
        initialize();
    }

    public static void initialize() {
        initializeSquares();
        initializePieces();
    }

    public static Square getSquare(char file, int rank) {
        file = Character.toLowerCase(file);
        if (file < 'a' || file > 'h' || rank < 1 || rank > 8) {
            return null;
        } else {
            return grid[file - 'a'][rank - 1];
        }
    }

    public static void executeMove(Move move) {
        Square originSquare = getSquare(move.getOriginFile(), move.getOriginRank());
        Square destinationSquare = getSquare(move.getDestinationFile(), move.getDestinationRank());
        if (destinationSquare.getCurrentPiece() != null) {
            destinationSquare.getCurrentPiece().setCapture(true);
            PieceSet.addCapturedPiece(destinationSquare.getCurrentPiece());
        }
        destinationSquare.setCurrentPiece(originSquare.getCurrentPiece());
        originSquare.setCurrentPiece(null);
    }

    public static void executeMove_back(Move move, Piece resurrection) {
        Square originSquare = getSquare(move.getDestinationFile(), move.getDestinationRank());
        Square destinationSquare = getSquare(move.getOriginFile(), move.getOriginRank());
        if (resurrection != null) {
            resurrection.setCapture(false);
            PieceSet.minusCapturedPiece(resurrection);
        }
        destinationSquare.setCurrentPiece(originSquare.getCurrentPiece());
        originSquare.setCurrentPiece(resurrection);
    }



    private static void initializeSquares() {
        for (int i = 0; i < DIMENSION; i++) {
            for (int j = 0; j < DIMENSION; j++) {
                grid[i][j] = new Square();
            }
        }
    }

    public static void loadPieceSet(List<Piece> pieces, List<util.location> locations) {
        initializeSquares();
        Iterator<Piece> whiteBishopsIterator = PieceSet.getPieces(Piece.Color.WHITE, Piece.Type.BISHOP).iterator();
        Iterator<Piece> blackBishopsIterator = PieceSet.getPieces(Piece.Color.BLACK, Piece.Type.BISHOP).iterator();
        Iterator<Piece> whiteKingsIterator = PieceSet.getPieces(Piece.Color.WHITE, Piece.Type.KING).iterator();
        Iterator<Piece> blackKingsIterator = PieceSet.getPieces(Piece.Color.BLACK, Piece.Type.KING).iterator();
        Iterator<Piece> whiteKnightsIterator = PieceSet.getPieces(Piece.Color.WHITE, Piece.Type.KNIGHT).iterator();
        Iterator<Piece> blackKnightsIterator = PieceSet.getPieces(Piece.Color.BLACK, Piece.Type.KNIGHT).iterator();
        Iterator<Piece> whitePawnsIterator = PieceSet.getPieces(Piece.Color.WHITE, Piece.Type.PAWN).iterator();
        Iterator<Piece> blackPawnsIterator = PieceSet.getPieces(Piece.Color.BLACK, Piece.Type.PAWN).iterator();
        Iterator<Piece> whiteQueensIterator = PieceSet.getPieces(Piece.Color.WHITE, Piece.Type.QUEEN).iterator();
        Iterator<Piece> blackQueensIterator = PieceSet.getPieces(Piece.Color.BLACK, Piece.Type.QUEEN).iterator();
        Iterator<Piece> whiteRooksIterator = PieceSet.getPieces(Piece.Color.WHITE, Piece.Type.ROOK).iterator();
        Iterator<Piece> blackRooksIterator = PieceSet.getPieces(Piece.Color.BLACK, Piece.Type.ROOK).iterator();

        for(int i=0; i<pieces.size(); i++) {
            switch (pieces.get(i).getColor()) {
                case WHITE:
                    switch (pieces.get(i).getType()){
                        case KING:
                            getSquare(locations.get(i).getx(), locations.get(i).gety()).setCurrentPiece(whiteKingsIterator.next());
                            break;
                        case ROOK:
                            getSquare(locations.get(i).getx(), locations.get(i).gety()).setCurrentPiece(whiteRooksIterator.next());
                            break;
                        case BISHOP:
                            getSquare(locations.get(i).getx(), locations.get(i).gety()).setCurrentPiece(whiteBishopsIterator.next());
                            break;
                        case QUEEN:
                            getSquare(locations.get(i).getx(), locations.get(i).gety()).setCurrentPiece(whiteQueensIterator.next());
                            break;
                        case KNIGHT:
                            getSquare(locations.get(i).getx(), locations.get(i).gety()).setCurrentPiece(whiteKnightsIterator.next());
                            break;
                        case PAWN:
                            getSquare(locations.get(i).getx(), locations.get(i).gety()).setCurrentPiece(whitePawnsIterator.next());
                            break;
                    }
                    break;
                case BLACK:
                    switch (pieces.get(i).getType()){
                        case KING:
                            getSquare(locations.get(i).getx(), locations.get(i).gety()).setCurrentPiece(blackKingsIterator.next());
                            break;
                        case ROOK:
                            getSquare(locations.get(i).getx(), locations.get(i).gety()).setCurrentPiece(blackRooksIterator.next());
                            break;
                        case BISHOP:
                            getSquare(locations.get(i).getx(), locations.get(i).gety()).setCurrentPiece(blackBishopsIterator.next());
                            break;
                        case QUEEN:
                            getSquare(locations.get(i).getx(), locations.get(i).gety()).setCurrentPiece(blackQueensIterator.next());
                            break;
                        case KNIGHT:
                            getSquare(locations.get(i).getx(), locations.get(i).gety()).setCurrentPiece(blackKnightsIterator.next());
                            break;
                        case PAWN:
                            getSquare(locations.get(i).getx(), locations.get(i).gety()).setCurrentPiece(blackPawnsIterator.next());
                            break;
                    }
                    break;
            }
        }

    }

    private static void initializePieces() {
        /*
        TODO-piece
            Initialize pieces on board
            Check following code to implement other pieces
            Highly recommended to use same template!
         */
        // rooks
        Iterator<Piece> whiteBishopsIterator = PieceSet.getPieces(Piece.Color.WHITE, Piece.Type.BISHOP).iterator();
        Iterator<Piece> blackBishopsIterator = PieceSet.getPieces(Piece.Color.BLACK, Piece.Type.BISHOP).iterator();
        Iterator<Piece> whiteKingsIterator = PieceSet.getPieces(Piece.Color.WHITE, Piece.Type.KING).iterator();
        Iterator<Piece> blackKingsIterator = PieceSet.getPieces(Piece.Color.BLACK, Piece.Type.KING).iterator();
        Iterator<Piece> whiteKnightsIterator = PieceSet.getPieces(Piece.Color.WHITE, Piece.Type.KNIGHT).iterator();
        Iterator<Piece> blackKnightsIterator = PieceSet.getPieces(Piece.Color.BLACK, Piece.Type.KNIGHT).iterator();
        Iterator<Piece> whitePawnsIterator = PieceSet.getPieces(Piece.Color.WHITE, Piece.Type.PAWN).iterator();
        Iterator<Piece> blackPawnsIterator = PieceSet.getPieces(Piece.Color.BLACK, Piece.Type.PAWN).iterator();
        Iterator<Piece> whiteQueensIterator = PieceSet.getPieces(Piece.Color.WHITE, Piece.Type.QUEEN).iterator();
        Iterator<Piece> blackQueensIterator = PieceSet.getPieces(Piece.Color.BLACK, Piece.Type.QUEEN).iterator();
        Iterator<Piece> whiteRooksIterator = PieceSet.getPieces(Piece.Color.WHITE, Piece.Type.ROOK).iterator();
        Iterator<Piece> blackRooksIterator = PieceSet.getPieces(Piece.Color.BLACK, Piece.Type.ROOK).iterator();

        getSquare('a', 1).setCurrentPiece(whiteRooksIterator.next());
        getSquare('h', 1).setCurrentPiece(whiteRooksIterator.next());
        getSquare('a', 8).setCurrentPiece(blackRooksIterator.next());
        getSquare('h', 8).setCurrentPiece(blackRooksIterator.next());
        getSquare('b', 1).setCurrentPiece(whiteKnightsIterator.next());
        getSquare('g', 1).setCurrentPiece(whiteKnightsIterator.next());
        getSquare('b', 8).setCurrentPiece(blackKnightsIterator.next());
        getSquare('g', 8).setCurrentPiece(blackKnightsIterator.next());
        getSquare('c', 1).setCurrentPiece(whiteBishopsIterator.next());
        getSquare('f', 1).setCurrentPiece(whiteBishopsIterator.next());
        getSquare('c', 8).setCurrentPiece(blackBishopsIterator.next());
        getSquare('f', 8).setCurrentPiece(blackBishopsIterator.next());
        getSquare('d', 1).setCurrentPiece(whiteQueensIterator.next());
        getSquare('d', 8).setCurrentPiece(blackQueensIterator.next());
        getSquare('e', 1).setCurrentPiece(whiteKingsIterator.next());
        getSquare('e', 8).setCurrentPiece(blackKingsIterator.next());
        getSquare('a', 2).setCurrentPiece(whitePawnsIterator.next());
        getSquare('b', 2).setCurrentPiece(whitePawnsIterator.next());
        getSquare('c', 2).setCurrentPiece(whitePawnsIterator.next());
        getSquare('d', 2).setCurrentPiece(whitePawnsIterator.next());
        getSquare('e', 2).setCurrentPiece(whitePawnsIterator.next());
        getSquare('f', 2).setCurrentPiece(whitePawnsIterator.next());
        getSquare('g', 2).setCurrentPiece(whitePawnsIterator.next());
        getSquare('h', 2).setCurrentPiece(whitePawnsIterator.next());
        getSquare('a', 7).setCurrentPiece(blackPawnsIterator.next());
        getSquare('b', 7).setCurrentPiece(blackPawnsIterator.next());
        getSquare('c', 7).setCurrentPiece(blackPawnsIterator.next());
        getSquare('d', 7).setCurrentPiece(blackPawnsIterator.next());
        getSquare('e', 7).setCurrentPiece(blackPawnsIterator.next());
        getSquare('f', 7).setCurrentPiece(blackPawnsIterator.next());
        getSquare('g', 7).setCurrentPiece(blackPawnsIterator.next());
        getSquare('h', 7).setCurrentPiece(blackPawnsIterator.next());

    }
}
