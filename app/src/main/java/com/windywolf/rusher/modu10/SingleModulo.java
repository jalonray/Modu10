package com.windywolf.rusher.modu10;

/**
 * Created by Mr.Ray on 16/1/12.
 */
public class SingleModulo {

    public static int EMPTY_BLOCK = 0;
    public static int FILLED_BLOCK = 1;

    private int[][] blocks;

    public SingleModulo() {

    }

    public SingleModulo(int[][] blocks) {
        setBlocks(blocks);
    }

    public SingleModulo(String blockSource) {
        setBlocks(blockSource);
    }

    public void setBlocks(int[][] blocks) {
        this.blocks = new int[blocks.length][blocks[0].length];
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[0].length; j++) {
                this.blocks[i][j] = blocks[i][j];
            }
        }
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

    public SingleModulo cloneModulo() {
        return new SingleModulo(blocks);
    }

    public int[][] getBlocks() {
        return blocks;
    }

    public int getWidth() {
        return blocks[0].length;
    }

    public int getHeight() {
        return blocks.length;
    }

    public Rect getRect() {
        return new Rect(0, 0, blocks[0].length - 1, blocks.length - 1);
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
