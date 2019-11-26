package pieces;

import util.Move;

public class King extends Piece {

    public King(Color color) {
        super(color);
        this.type = Type.KING;
    }

    @Override
    public boolean validateMove(Move move) {
        // executeMove or capture
        if ((move.getCapturedPiece() == null)
                || (move.getCapturedPiece() != null
                    && !move.getPiece().getColor().equals(move.getCapturedPiece().getColor()))) {
            // along file
            if(Math.abs(move.getDestinationRank()-move.getOriginRank())<=1 && Math.abs(move.getDestinationFileAsInt()-move.getOriginFileAsInt())<=1){
                return true;
            }
        }

        // all other cases
        return false;
    }

}
