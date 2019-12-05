package util;

import board.Board;
import pieces.Piece;
import ui.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;
import java.util.List;
import java.util.Observable;

public class GameModel extends Observable {

    private GameFrame gameFrame;
    private BoardPanel boardPanel;
    private TimerPanel timerPanel;
    private ControlPanel controlPanel;
    private MoveHistoryPanel moveHistoryPanel;

    private Piece.Color color;

    private Timer whiteTimer;
    private Timer blackTimer;

    public GameModel() {
        initialize();
    }

    private void initialize() {
        initializeTimers();
        initializeUIComponents();
    }

    public void onMoveRequest(char originFile, int originRank, char destinationFile, int destinationRank) {
        onLocalMoveRequest(originFile, originRank, destinationFile, destinationRank);
    }

    private void onLocalMoveRequest(char originFile, int originRank, char destinationFile, int destinationRank) {
        Move move = new Move(originFile, originRank, destinationFile, destinationRank);
        if (MoveValidator.validateMove(move)) {
            executeMove(move);
        }
    }

    private void executeMove(Move move) {
        MoveLogger.addMove(move);
        Board.executeMove(move);
        moveHistoryPanel.printMove(move);
        boardPanel.executeMove(move);
        color = move.getPiece().getColor();
        switchTimer(move);
        if (MoveValidator.isCheckMove(move)) {
            if (MoveValidator.isCheckMate(move)) {
                stopTimer();
                gameFrame.showCheckmateDialog();
            } else {
                gameFrame.showCheckDialog();
            }
        }
    }

    public Piece queryPiece(char file, int rank) {
        return Board.getSquare(file, rank).getCurrentPiece();
    }

    private void initializeUIComponents() {
        boardPanel = new BoardPanel(this);
        timerPanel = new TimerPanel(this);
        controlPanel = new ControlPanel(this);
        moveHistoryPanel = new MoveHistoryPanel(this);
        gameFrame = new GameFrame(this);
    }

    private void initializeTimers() {
        whiteTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timerPanel.whiteTimerTikTok();
            }
        });
        blackTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timerPanel.blackTimerTikTok();
            }
        });
    }

    public void setColor(Piece.Color color) {
        MoveValidator.setColor(color);
    }

    public List<MoveLogger.MoveRound> getMoveList() {
        return MoveLogger.getMoveList();
    }

    public Piece.Color getColor() {
        return color;
    }

    public String getHistory() {
        return moveHistoryPanel.getHistory();
    }

    public void setHistory(String x) {
        moveHistoryPanel.setHistory(x);
    }

    public Time getWhiteTime() {
        return timerPanel.getWhiteTime();
    }

    public Time getBlackTime() {
        return timerPanel.getBlackTime();
    }

    public void setWhiteTime(Time x) {
        timerPanel.setWhiteTime(x);
    }

    public void setBlackTime(Time x) {
        timerPanel.setBlackTime(x);
    }

    public void loadTimer(Piece.Color color) {
        if(color == Piece.Color.WHITE){
            whiteTimer.stop();
            blackTimer.start();
        }
        else if(color == Piece.Color.BLACK){
            blackTimer.stop();
            whiteTimer.start();
        }
    }


    private void switchTimer(Move move) {
        if(move.getPiece().getColor() == Piece.Color.WHITE){
            whiteTimer.stop();
            blackTimer.start();
        }
        else if(move.getPiece().getColor() == Piece.Color.BLACK){
            blackTimer.stop();
            whiteTimer.start();
        }
        /*
        TODO-timer
            start and stop whiteTimer and blackTimer
         */
    }

    private void stopTimer() {
        whiteTimer.stop();
        blackTimer.stop();
        // TODO-timer: stop timers
    }

    public BoardPanel getBoardPanel() {
        return boardPanel;
    }

    public TimerPanel getTimerPanel() {
        return timerPanel;
    }

    public ControlPanel getControlPanel() {
        return controlPanel;
    }

    public MoveHistoryPanel getMoveHistoryPanel() {
        return moveHistoryPanel;
    }

}
