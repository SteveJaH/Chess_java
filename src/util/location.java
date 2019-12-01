package util;

import java.io.Serializable;

public class location implements Serializable {
    public char a;
    public int b;

    public location(char x, int y) {
        this.a = x;
        this.b = y;
    }

    public char getx() {
        return a;
    }

    public int gety() {
        return b;
    }

    public void setx(char x) {
        a = x;
    }

    public void sety(int y) {
        b = y;
    }
}
