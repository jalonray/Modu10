package com.windywolf.rusher.modu10;

import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by Mr.Ray on 16/1/21.
 */
public class SingleModuloView extends FrameLayout {

    /**
     * 每个方块的边长
     */
    private int singleSize;
    /**
     * 缩略图中每个方块的边长
     */
    private int smallSize;
    /**
     * 实际绘制的左边距
     */
    private int actualLeft;
    /**
     * 实际绘制的上边距
     */
    private int actualTop;
    /**
     * 记录所有方块的View
     */
    private SparseArray<View> blockArray = new SparseArray<>();
    /**
     * 记录 modulo 数据
     */
    private SingleModulo singleModulo;
    /**
     * 显示的方块和背景方块的颜色
     */
    private int[] blockColors = {R.color.Orange400, R.color.DeepOrange500};
    /**
     * 当前的地图
     */
    private GameMap gameMap = null;
    /**
     * 当前放置位置，注：放置位置和点击的坐标系并不相同。
     */
    private int rowIndex = 0;
    private int columnIndex = 0;
    /**
     * 是否已经被放置到了地图上
     */
    private boolean isPut = false;

    public SingleModuloView(Context context) {
        super(context);
    }

    public SingleModuloView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SingleModuloView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 计算 View 大小，计算出实际的绘制位置和每个方块的大小
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        int height = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
        int xSize = singleModulo.getBlocks()[0].length;
        int ySize = singleModulo.getBlocks().length;
        singleSize = Math.min(width / xSize, height / ySize);

        xSize = gameMap.getMap()[0].length;
        ySize = gameMap.getMap().length;
        smallSize = Math.min(width / xSize, height / ySize);
        int actualWidth = smallSize * xSize;
        int actualHeight = smallSize * ySize;
        actualLeft = (width - actualWidth) / 2;
        actualTop = (height - actualHeight) / 2;
    }

    /**
     * 依次绘制每个方块，保证每个方块大小一致，有颜色区分
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (singleModulo == null || gameMap == null) {
            return;
        }

        View block;
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).setVisibility(GONE);
        }
        // 放置过后需要显示放置之后的缩略图，而未放置时仅显示模块本身
        if (isPut) {    // 放置后，显示缩略图，
            int[][] map = singleModulo.getBlocks();
            int width = gameMap.getWidth();
            int height = gameMap.getHeight();
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    block = getChildAt(getIndex(i, j));
                    block.setVisibility(VISIBLE);
//                    int emptyColor = getResources().getColor(blockColors[SingleModulo.EMPTY_BLOCK]);
//                    int filledColor = getResources().getColor(blockColors[SingleModulo.FILLED_BLOCK]);
//                    block.setBackgroundColor(emptyColor);
                    int row = i - rowIndex;
                    int column = j - columnIndex;
                    if (row < map.length &&
                            row >= 0 &&
                            column < map[0].length &&
                            column >= 0) {
                        block.setBackgroundColor(getResources().getColor(blockColors[map[row][column]]));
                    } else {
                        block.setBackgroundColor(getResources().getColor(blockColors[SingleModulo.EMPTY_BLOCK]));
                    }
                    block.layout(actualLeft + j * smallSize + 2,
                            actualTop + i * smallSize + 2,
                            actualLeft + j * smallSize + smallSize - 2,
                            actualTop + i * smallSize + smallSize - 2);
                }
            }
        } else {        // 未放置，仅需显示模块中的方块即可
            int[][] map = singleModulo.getBlocks();
            int height = singleModulo.getHeight();
            int width = singleModulo.getWidth();
            int drawLeft = (getMeasuredWidth() - getPaddingLeft() - getPaddingRight() - singleSize * width) / 2;
            int drawTop = (getMeasuredHeight() - getPaddingTop() - getPaddingBottom() - singleSize * height) / 2;
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (map[i][j] == SingleModulo.FILLED_BLOCK) {
                        block = getChildAt(getIndex(i, j));
                        block.setVisibility(VISIBLE);
                        block.setBackgroundColor(getResources().getColor(blockColors[SingleModulo.FILLED_BLOCK]));
                        block.layout(drawLeft + j * singleSize + 2,
                                drawTop + i * singleSize + 2,
                                drawLeft + j * singleSize + singleSize - 2,
                                drawTop + i * singleSize + singleSize - 2);
                    }
                }
            }
        }
    }

    /**
     * 设置模块数据
     *
     * @param modulo 模块对象
     */
    public void setSingleModulo(SingleModulo modulo) {
        if (modulo == null) {
            return;
        }
        singleModulo = modulo.cloneModulo();
    }

    /**
     * 设置模块数据
     *
     * @param blockSource 模块的 json 字符串
     */
    public void setModuloCode(String blockSource) {
        if (blockSource == null) {
            return;
        }
        singleModulo = new SingleModulo(blockSource);
    }

    /**
     * 应当克隆 GameMap，而非直接复制。
     *
     * @param gameMap
     */
    public void setGameMap(GameMap gameMap) {
        this.gameMap = gameMap.cloneMap();
        initViewDate();
    }

    /**
     * 放置本模块到对应的位置中
     * @param rowIndex 放置行号
     * @param columnIndex 防止列号
     */
    public void putAt(int rowIndex, int columnIndex) {
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
        isPut = true;
        requestLayout();
    }

    /**
     * 取消本模块的放置
     */
    public void canelPut() {
        isPut = false;
        requestLayout();
    }

    /**
     * 初始化，对每个方块都初始化一个view，便于控制颜色等
     */
    private void initViewDate() {
        blockArray.clear();

        for (int i = 0; i < gameMap.getHeight(); i++) {
            for (int j = 0; j < gameMap.getWidth(); j++) {
                View view = new View(getContext());
                blockArray.put(getIndex(i, j), view);
                addView(view, getIndex(i, j));
            }
        }
    }

    /**
     * 通过方块的坐标获取其对应的线性下标，从左到右、从上到下依次编号
     *
     * @param row    行号
     * @param column 列号
     * @return 下标
     */
    private int getIndex(int row, int column) {
        return row * gameMap.getWidth() + column;
    }
}
