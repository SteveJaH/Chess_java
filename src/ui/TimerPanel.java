package ui;

import util.GameModel;

import javax.swing.*;
import java.awt.*;
import java.sql.Time;
import java.time.LocalTime;
import java.util.Observable;
import java.util.Observer;

public class TimerPanel extends JPanel implements Observer {

    private GameModel gameModel;
    private Time whiteTime;
    private Time blackTime;
    private LocalTime whiteLocal;
    private LocalTime blackLocal;
    //private Calendar whiteCalendar;
    //private Calendar blackCalendar;

    private JPanel displayPanel;
    private JPanel whiteTimerPanel;
    private JPanel whiteTimerDigitsPanel;
    private JLabel whiteTimerDigitsLabel;
    private JPanel whiteTimerStatusPanel;
    private JPanel blackTimerPanel;
    private JPanel blackTimerDigitsPanel;
    private JLabel blackTimerDigitsLabel;
    private JPanel blackTimerStatusPanel;

    public TimerPanel(GameModel gameModel) {
        super(new BorderLayout());
        this.gameModel = gameModel;
        whiteTime = Time.valueOf("00:00:00");
        blackTime = Time.valueOf("00:00:00");
        //whiteCalendar.setTime((date) whiteTime);
        initialize();
        gameModel.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {

    }

    public void whiteTimerTikTok() {
        whiteLocal = whiteTime.toLocalTime();
        whiteLocal = whiteLocal.plusSeconds(1);
        whiteTime = Time.valueOf(String.valueOf(whiteLocal.getHour())+':'+String.valueOf(whiteLocal.getMinute())+':'+String.valueOf(whiteLocal.getSecond()));
        whiteTimerDigitsLabel.setText(whiteTime.toString());
        //whiteCalendar.add(Calendar.SECOND, 1);
        //whiteTime = Time.valueOf(String.valueOf(whiteCalendar.getTime()));
        whiteTimerStatusPanel.setVisible(true);
        blackTimerStatusPanel.setVisible(false);
        /*
        TODO-timer
            Update whiteTime
            Update whiteDigitsLabel
            Show whiteTimerStatusPanel
            Blind blackTimerStatusPanel
         */
    }

    public Time getWhiteTime(){
        System.out.println(whiteTime);
        return whiteTime;
    }

    public void setWhiteTime(Time x){
        whiteTime = x;
    }

    public Time getBlackTime(){
        return blackTime;
    }

    public void setBlackTime(Time x){
        blackTime = x;
    }

    public void blackTimerTikTok() {
        blackLocal = blackTime.toLocalTime();
        blackLocal = blackLocal.plusSeconds(1);
        blackTime = Time.valueOf(String.valueOf(blackLocal.getHour())+':'+String.valueOf(blackLocal.getMinute())+':'+String.valueOf(blackLocal.getSecond()));
        //blackTime = Time.valueOf(blackLocal.toString());
        blackTimerDigitsLabel.setText(blackTime.toString());

        whiteTimerStatusPanel.setVisible(false);
        blackTimerStatusPanel.setVisible(true);
        // TODO-timer: same with whiteTimerTikTok
    }

    private void initialize() {
        whiteTimerDigitsLabel = new JLabel(whiteTime.toString());
        whiteTimerDigitsLabel.setFont(whiteTimerDigitsLabel.getFont().deriveFont(48f));
        whiteTimerDigitsPanel = new JPanel();
        whiteTimerDigitsPanel.add(whiteTimerDigitsLabel);
        whiteTimerStatusPanel = new JPanel();
        whiteTimerStatusPanel.setBackground(Color.WHITE);
        whiteTimerPanel = new JPanel(new BorderLayout());
        whiteTimerPanel.add(whiteTimerDigitsPanel, BorderLayout.LINE_START);
        whiteTimerPanel.add(whiteTimerStatusPanel, BorderLayout.CENTER);
        whiteTimerPanel.setBorder(BorderFactory.createTitledBorder("White"));

        blackTimerDigitsLabel = new JLabel(blackTime.toString());
        blackTimerDigitsLabel.setFont(blackTimerDigitsLabel.getFont().deriveFont(48f));
        blackTimerDigitsPanel = new JPanel();
        blackTimerDigitsPanel.add(blackTimerDigitsLabel);
        blackTimerStatusPanel = new JPanel();
        blackTimerStatusPanel.setBackground(Color.BLACK);
        blackTimerPanel = new JPanel(new BorderLayout());
        blackTimerPanel.add(blackTimerDigitsPanel, BorderLayout.LINE_START);
        blackTimerPanel.add(blackTimerStatusPanel, BorderLayout.CENTER);
        blackTimerPanel.setBorder(BorderFactory.createTitledBorder("Black"));

        displayPanel = new JPanel(new GridLayout(2, 1));
        displayPanel.add(whiteTimerPanel);
        displayPanel.add(blackTimerPanel);

        this.add(displayPanel, BorderLayout.CENTER);
        this.setPreferredSize(new Dimension(300, 200));
    }

}
