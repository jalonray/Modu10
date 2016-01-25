package com.example;


import java.util.ArrayList;

/**
 * Created by Mr.Ray on 16/1/12.
 */
public class GameMap {
    private int[][] map;
    private int modu;

    public void setMap(int[][] map) {
        this.map = new int[map.length][map[0].length];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                this.map[i][j] = map[i][j];
            }
        }
    }

    public void setMap(ArrayList<String> mapSource) {
        if (mapSource == null || mapSource.size() == 0) {
            return;
        }
        map = new int[mapSource.size()][mapSource.get(0).length()];
        for (int i = 0; i < mapSource.size(); i++) {
            String mapRow = mapSource.get(i);
            for (int j = 0; j < mapRow.length(); j++) {
                map[i][j] = mapRow.charAt(j) - '0';
            }
        }
    }

    public int[][] getMap() {
        return map;
    }

    public void setModu(int modu) {
        this.modu = modu;
    }

    public int getModu() {
        return modu;
    }

    public void putModulo(int x, int y, SingleModulo modulo) {
        for (int i = x; i < modulo.getBlocks().length + x; i++) {
            for (int j = y; j < modulo.getBlocks()[0].length + y; j++) {
                int iAxis = i - x;
                int jAxis = j - y;
                map[i][j] = (map[i][j] + modulo.getBlocks()[iAxis][jAxis]);
                if (map[i][j] >= modu) {
                    map[i][j] = 0;
                }
            }
        }
    }

    public void output() {
        for (int[] x : map){
            for (int y : x) {
                System.out.print(y);
            }
            System.out.println();
        }
    }

    public GameMap cloneMap() {
        GameMap newMap = new GameMap();
        ArrayList<String> mapCode = new ArrayList<>();
        for (int[] x : map) {
            StringBuilder buffer = new StringBuilder("");
            for (int y : x) {
                buffer.append(y);
            }
            mapCode.add(buffer.toString());
        }
        newMap.setMap(mapCode);
        newMap.setModu(modu);
        return newMap;
    }

    public Rect getValueableArea(){
        Rect area = new Rect(map.length, map[0].length, 0, 0);
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] != 0) {
                    area.left = Math.min(j, area.left);
                    area.top = Math.min(i, area.top);
                    area.right = Math.max(j, area.right);
                    area.bottom = Math.max(i, area.bottom);
                }
            }
        }
//        System.out.print("map area: ");
//        area.output();
        return area;
    }

}
