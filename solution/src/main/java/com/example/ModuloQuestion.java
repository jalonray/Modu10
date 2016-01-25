package com.example;

import java.util.ArrayList;

/**
 * Created by Mr.Ray on 16/1/12.
 */
public class ModuloQuestion {
    private int level;
    private String modu;
    private ArrayList<String> map;
    private ArrayList<String> pieces;

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public void setModu(String modu) {
        this.modu = modu;
    }

    public String getModu() {
        return modu;
    }

    public void setMap(ArrayList<String> map) {
        this.map = (ArrayList<String>) map.clone();
    }

    public ArrayList<String> getMap() {
        return map;
    }

    public void setPieces(ArrayList<String> pieces) {
        this.pieces = (ArrayList<String>) pieces.clone();
    }

    public ArrayList<String> getPieces() {
        return pieces;
    }
}
