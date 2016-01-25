package com.windywolf.rusher.modu10;

/**
 * Created by Mr.Ray on 16/1/20.
 */
public class RectF {
    public float left;
    public float top;
    public float right;
    public float bottom;
    public RectF(){

    }
    public RectF(float left, float top, float right, float bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }
    public void set(float left, float top, float right, float bottom) {
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

    public boolean contains(float x, float y) {
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

    public void offset(float x, float y) {
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
