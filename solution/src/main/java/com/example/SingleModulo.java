package com.example;

/**
 * Created by Mr.Ray on 16/1/12.
 */
public class SingleModulo {

    private int[][] blocks;

    public void setBlocks(int[][] blocks) {
        this.blocks = (int[][]) blocks.clone();
    }

    public void setBlocks(String blockSource) {
        if (blockSource == null || blockSource.equals("")) {
            return;
        }
        String[] blockRows = blockSource.split(",");
        blocks = new int[blockRows.length][blockRows[0].length()];
        for (int i = 0; i < blockRows.length; i++) {
            String blockRow = blockRows[i];
            for (int j = 0; j < blockRow.length(); j++) {
                blocks[i][j] = blockRow.charAt(j) == 'X' ? 1 : 0;
            }
        }
    }

    public int[][] getBlocks() {
        return blocks;
    }

    public void output() {
        for (int[] x : blocks) {
            for (int y : x) {
                System.out.print(y == 1 ? 'X' : ' ');
            }
            System.out.println();
        }
    }
}
