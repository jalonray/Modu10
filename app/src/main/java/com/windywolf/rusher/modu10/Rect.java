package com.windywolf.rusher.modu10;

/**
 * Created by Mr.Ray on 16/1/20.
 */
public class Rect {
    public int left;
    public int top;
    public int right;
    public int bottom;
    public Rect(){

    }
    public Rect(int left, int top, int right, int bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }
    public void set(int left, int top, int right, int bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }
    public boolean contains(Rect other) {
        return isRect() &&
                other.isRect() &&
                left <= other.left &&
                top <= other.top &&
                right >= other.right &&
                bottom >= other.bottom;
    }

    public boolean contains(int x, int y) {
        return isRect() &&
                left <= x && right >= x &&
                top <= y && bottom >= y;
    }

    public boolean equals(Rect other) {
        return isRect() &&
                other.isRect() &&
                left == other.left &&
                top == other.top &&
                right == other.right &&
                bottom == other.bottom;
    }

    public void offset(int x, int y) {
        left += x;
        right += x;
        top += y;
        bottom += y;
    }
    public boolean isRect() {
        return left <= right && top <= bottom;
    }

    public void output() {
        System.out.println(left + "," + top + "," + right + "," + bottom);
    }
}
