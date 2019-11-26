package util;

import board.Board;
import pieces.Piece;

public class Move {

    private Piece piece;
    private Piece capturedPiece;
    private char originFile;
    private int originRank;
    private char destinationFile;
    private int destinationRank;

    public Move(char originFile, int originRank, char destinationFile, int destinationRank) {
        this.piece = Board.getSquare(originFile, originRank).getCurrentPiece();
        this.capturedPiece = Board.getSquare(destinationFile, destinationRank).getCurrentPiece();
        this.originFile = originFile;
        this.originRank = originRank;
        this.destinationFile = destinationFile;
        this.destinationRank = destinationRank;
    }

    public Move(Piece piece, char originFile, int originRank, char destinationFile, int destinationRank) {
        this.piece = piece;
        this.capturedPiece = Board.getSquare(destinationFile, destinationRank).getCurrentPiece();
        this.originFile = originFile;
        this.originRank = originRank;
        this.destinationFile = destinationFile;
        this.destinationRank = destinationRank;
    }

    public Move(Piece piece, Piece capturedPiece, char originFile, int originRank, char destinationFile, int destinationRank) {
        this.piece = piece;
        this.capturedPiece = capturedPiece;
        this.originFile = originFile;
        this.originRank = originRank;
        this.destinationFile = destinationFile;
        this.destinationRank = destinationRank;
    }

    public Piece getPiece() {
        return piece;
    }

    public Piece getCapturedPiece() {
        return capturedPiece;
    }

    public char getOriginFile() {
        return originFile;
    }

    public int getOriginRank() {
        return originRank;
    }

    public char getDestinationFile() {
        return destinationFile;
    }

    public int getDestinationRank() {
        return destinationRank;
    }

    public int getOriginFileAsInt() {
        int a=0;
        switch (originFile) {
            case 'a':
                a=1;
                break;
            case 'b':
                a=2;
                break;
            case 'c':
                a=3;
                break;
            case 'd':
                a=4;
                break;
            case 'e':
                a=5;
                break;
            case 'f':
                a=6;
                break;
            case 'g':
                a=7;
                break;
            case 'h':
                a=8;
                break;
        }
        return a;
    }

    public int getDestinationFileAsInt() {
        int a=0;
        switch (destinationFile) {
            case 'a':
                a=1;
                break;
            case 'b':
                a=2;
                break;
            case 'c':
                a=3;
                break;
            case 'd':
                a=4;
                break;
            case 'e':
                a=5;
                break;
            case 'f':
                a=6;
                break;
            case 'g':
                a=7;
                break;
            case 'h':
                a=8;
                break;
        }
        return a;
    }
}

