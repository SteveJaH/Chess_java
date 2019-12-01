package pieces;

import util.Move;

public class Pawn extends Piece {

    public Pawn(Color color) {
        super(color);
        this.type = Type.PAWN;
    }

    @Override
    public boolean validateMove(Move move) {
        // executeMove or capture

        if(move.getPiece().getColor()==Piece.Color.WHITE){
            if(move.getCapturedPiece() == null){
                if(move.getOriginRank()==2){
                    if((move.getDestinationRank()-move.getOriginRank()==2 || move.getDestinationRank()-move.getOriginRank()==1)&&move.getDestinationFileAsInt()==move.getOriginFileAsInt())
                        return true;
                }
                //8 if it goes to end !!!!!!!
                else{
                    if(move.getDestinationRank()-move.getOriginRank()==1 && move.getDestinationFileAsInt()==move.getOriginFileAsInt())
                        return true;
                }
            }
            else if(move.getCapturedPiece() != null){
                if(move.getDestinationRank()-move.getOriginRank()==1 && Math.abs(move.getDestinationFileAsInt()-move.getOriginFileAsInt())==1){
                    return true;
                }
            }
        }
        else{
            if(move.getCapturedPiece() == null){
                if(move.getOriginRank()==7){
                    if((move.getDestinationRank()-move.getOriginRank()==-2 || move.getDestinationRank()-move.getOriginRank()==-1)&&move.getDestinationFileAsInt()==move.getOriginFileAsInt())
                        return true;
                }
                else{
                    if(move.getDestinationRank()-move.getOriginRank()==-1 && move.getDestinationFileAsInt()==move.getOriginFileAsInt())
                        return true;
                }
            }
            else if(move.getCapturedPiece() != null){
                if(move.getDestinationRank()-move.getOriginRank()==-1 && Math.abs(move.getDestinationFileAsInt()-move.getOriginFileAsInt())==1){
                    return true;
                }
            }
        }


        // all other cases
        return false;
    }

}
