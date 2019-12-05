package util;

import java.io.Serializable;
import java.sql.Time;

public class time implements Serializable {
    private Time whiteTime;
    private Time blackTime;

    public time(Time x, Time y) {
        this.whiteTime = x;
        this.blackTime = y;
    }

    public Time getwhiteTime() {
        return whiteTime;
    }

    public Time getblackTime() {
        return blackTime;
    }

    public void setWhiteTime(Time x) {
        whiteTime = x;
    }

    public void setBlackTime(Time y) {
        blackTime = y;
    }
}
