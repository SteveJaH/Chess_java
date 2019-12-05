package ui;

import board.Board;
import pieces.Piece;
import pieces.PieceSet;
import util.*;
import ui.TimerPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class ControlPanel extends JPanel implements Serializable, Observer { //Observer

    private GameModel gameModel;
    private TimerPanel timerPanel;

    private void initializeUIComponents() {
        timerPanel = new TimerPanel(gameModel);

    }

    private JButton undoButton;
    private JButton saveButton;
    private JButton loadButton;

    public ControlPanel(GameModel gameModel) {
        this.gameModel = gameModel;
        initialize();
        gameModel.addObserver(this);
    }

    private void initialize() {
        initializeUIComponents();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setLayout(new GridLayout(0, 1));

        undoButton = new JButton("Request Undo");
        undoButton.setEnabled(true);
        saveButton = new JButton("Save Game");
        saveButton.setEnabled(true);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                State state = new State();
                List<Piece> pieces = StatePieces();
                List<location> locations = StateLocations();
                List<MoveLogger.MoveRound> moveHistory;
                Piece.Color color = gameModel.getColor();
                String history = gameModel.getHistory();
                moveHistory = gameModel.getMoveList();
                System.out.println(history);
                Time a = gameModel.getWhiteTime();
                Time b = gameModel.getBlackTime();
                time time = new time(a, b);
                state.setTimes(time);
                //state.setInput(1);
                state.setPieces(pieces);
                state.setLocations(locations);
                state.setHistory(history);
                state.setColor(color);
                //state.setMoveList(moveHistory);
                String path = "G:\\My Drive\\GRun\\Chess_java\\src\\Data.dat";
                ObjectOutputStream objOut;
                FileOutputStream fileOut;
                try{
                    fileOut = new FileOutputStream(path);
                    objOut = new ObjectOutputStream (fileOut);
                    objOut.writeObject(state);
                    //gameFrame.showSaveDialog();

                    if (objOut!=null){
                        objOut.close();
                    }
                    else if (fileOut!=null){
                        fileOut.close();
                    }
                }catch (IOException e1){
                    e1.printStackTrace();
                }
                /*
                TestData data = new TestData();
data.setName ("test1");
data.setNumber("1001");

String path = "c:/test/testData.dat";

ObjectOutputStream objOut = null;
FileOutputStream fileOut = null;
try{
fileOut = new FileOutputStream(path);
objOut = new ObjectOutputStream (fileOut);
objOut.writeObject(data);
}finally{
if (objOut!=null){
objOut.close();
}
else if (fileOut!=null){
fileOut.close();
}
}
                 */
            }
        });
        loadButton = new JButton("Load Game");
        loadButton.setEnabled(true);
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                State data = null;
                ObjectInputStream objInputStream = null;
                FileInputStream inputStream = null;
                try{
                    inputStream = new FileInputStream("G:\\My Drive\\GRun\\Chess_java\\src\\Data.dat");
                    objInputStream = new ObjectInputStream (inputStream);

                    data = (State)objInputStream.readObject();
                    objInputStream.close();

                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                }finally{
                    if (objInputStream != null){
                        try{objInputStream.close();}catch (Exception e1){}
                    }
                    else if (inputStream != null){
                        try{inputStream.close();}catch (Exception e1){}
                    }
                }

                //System.out.println(data.getPieces().get(0).getType());
                //System.out.println(data.getTimes().getblackTime());
                gameModel.setBlackTime(data.getTimes().getblackTime());
                gameModel.loadTimer(data.getColor());
                gameModel.setColor(data.getColor());
                gameModel.setHistory(data.getHistory());
                Board.loadPieceSet(data.getPieces(), data.getLocations());
                PieceSet.loadPieceSet(data.getPieces());
                BoardPanel.loadPieceSet(data.getPieces(), data.getLocations());
            }
        });

        this.add(undoButton);
        this.add(saveButton);
        this.add(loadButton);
        this.setPreferredSize(new Dimension(300, 200));
    }

    public static List<Piece> StatePieces() {
        char i;
        int j;
        java.util.List<Piece> whites = new ArrayList<Piece>();

        for(i='a'; i<='h'; i+=1) {
            for (j = 1; j <= 8; j++) {
                if (Board.getSquare(i, j).getCurrentPiece() != null)
                    whites.add(Board.getSquare(i, j).getCurrentPiece());
            }
        }
        return whites;
    }

    public static List<location> StateLocations() {
        char i;
        int j;
        List<location> whiteLocation = new ArrayList<location>();

        for(i='a'; i<='h'; i+=1) {
            for (j = 1; j <= 8; j++) {
                if (Board.getSquare(i, j).getCurrentPiece() != null) {
                    location location = new location(i, j);
                    whiteLocation.add(location);
                }
            }
        }
        return whiteLocation;
    }

    @Override
    public void update(Observable o, Object arg) {

    }

}
