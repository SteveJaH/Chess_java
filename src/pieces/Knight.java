package pieces;

import util.Move;

public class Knight extends Piece {

    public Knight(Color color) {
        super(color);
        this.type = Type.KNIGHT;
    }

    @Override
    public boolean validateMove(Move move) {
        // executeMove or capture
        if ((move.getCapturedPiece() == null)
                || (move.getCapturedPiece() != null
                    && !move.getPiece().getColor().equals(move.getCapturedPiece().getColor()))) {
            // along file
                if(Math.abs(move.getDestinationFileAsInt()-move.getOriginFileAsInt())+Math.abs(move.getDestinationRank()-move.getOriginRank())==3&&Math.abs(move.getDestinationFileAsInt()-move.getOriginFileAsInt())!=0&&Math.abs(move.getDestinationRank()-move.getOriginRank())!=0){
                    return true;
            }
        }

        // all other cases
        return false;
    }

}
