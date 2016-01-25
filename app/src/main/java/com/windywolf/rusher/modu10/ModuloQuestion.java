package com.windywolf.rusher.modu10;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;

/**
 * Created by Mr.Ray on 16/1/12.
 */
public class ModuloQuestion {
    private int level;
    private int modu;
    private ArrayList<String> map;
    private ArrayList<String> pieces;
    
    public ModuloQuestion() {}
    
    public ModuloQuestion(String rootString) {
        // parse json string
        JSONObject root = JSONObject.parseObject(rootString);

        // set question
        setLevel(root.getInteger("level"));
        setModu(root.getString("modu"));

        ArrayList<String> arrayBuffer = new ArrayList<>();
        for (Object object : root.getJSONArray("map")) {
            arrayBuffer.add((String) object);
        }
        setMap(arrayBuffer);

        arrayBuffer.clear();
        for (Object object : root.getJSONArray("pieces")) {
            arrayBuffer.add((String) object);
        }
        setPieces(arrayBuffer);
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public void setModu(String modu) {
        this.modu = Integer.parseInt(modu);
    }

    public int getModu() {
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
