package com.windywolf.rusher.modu10;

import java.util.ArrayList;

/**
 * Created by Mr.Ray on 16/1/20.
 */
public class ResultCode {
    private ArrayList<ModuloProb> moduloProbs = new ArrayList<>();
    private int[] result = null;
    private int maxWidth = 0;
    private int maxHeight = 0;

    public ResultCode(GameMap gameMap, ArrayList<SingleModulo> allModulos){
        // get map width & height
        int mapHeight = gameMap.getMap().length;
        int mapWidth = gameMap.getMap()[0].length;
        for (int i = 0; i < allModulos.size(); i++) {
            moduloProbs.add(new ModuloProb(
                    mapHeight - allModulos.get(i).getBlocks().length + 1,
                    mapWidth - allModulos.get(i).getBlocks()[0].length + 1,
                    allModulos.get(i).getBlocks()[0].length,
                    allModulos.get(i).getBlocks().length));
        }
        result = new int[allModulos.size() * 2];
        maxWidth = mapWidth;
        maxHeight = mapHeight;
    }

    public boolean inc() {
        int i = 0;
        while(moduloProbs.get(i).inc()) {
            i++;
            if (i == moduloProbs.size()) {
                return true;
            }
        }
        return false;
    }

    public int[] getResultCode() {
        int i = 0;
        for (ModuloProb moduloProb : moduloProbs) {
            result[i++] = moduloProb.rowIndex;
            result[i++] = moduloProb.columnIndex;
        }
        return result;
    }

    public Rect getCoverArea() {
        Rect area = new Rect(maxWidth, maxHeight, 0, 0);
        for (ModuloProb moduloProb : moduloProbs) {
//            moduloProb.coverArea.output();
            area.set(Math.min(area.left, moduloProb.coverArea.left),
                    Math.min(area.top, moduloProb.coverArea.top),
                    Math.max(area.right, moduloProb.coverArea.right),
                    Math.max(area.bottom, moduloProb.coverArea.bottom));
        }
//        System.out.print("cover area: ");
//        area.output();
        return area;
    }

    private static class ModuloProb{
        public int rows = 0;
        public int columns = 0;
        public int rowIndex = 0;
        public int columnIndex = 0;
        public int width = 0;
        public int height = 0;
        public Rect coverArea = new Rect();
        public ModuloProb(int rows, int columns, int width, int height) {
            this.rows = rows;
            this.columns = columns;
            this.width = width;
            this.height = height;
            coverArea.set(0, 0, width - 1, height - 1);
        }
        public boolean inc(){
            boolean result = false;
            rowIndex++;
            if (rowIndex >= rows) {
                rowIndex = 0;
                columnIndex++;
            }
            if (columnIndex >= columns) {
                columnIndex = 0;
                result = true;
            }
            coverArea.set(columnIndex, rowIndex, columnIndex + width - 1, rowIndex + height - 1);
            return result;
        }
    }
}
