package com.windywolf.rusher.modu10;

import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;

/**
 * Created: Mr.Ray
 * Date: 16/1/11.
 * Info: Modu10 的地图 View
 */
public class GameMapView extends FrameLayout {
    private int singleSize;
    private int actualLeft;
    private int actualTop;
    private SparseArray<View> blockArray = new SparseArray<>();
    private View block;
    private GameMap currentGameMap;
    private GameMap oldGameMap;
    private int currentIndex = 0;
    private int[] resultCode;
    private ArrayList<SingleModulo> modulos = new ArrayList<>();
    private int[] blockColors = {R.color.DeepPurple400, R.color.cyan300};
    private OnModuloChangeListener changeListener = null;
    private RectF touchArea = new RectF();

    public GameMapView(Context context) {
        super(context);
    }

    public GameMapView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GameMapView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (currentGameMap == null) {
            return;
        }
        int[][] map = currentGameMap.getMap();
        int height = currentGameMap.getHeight();
        int width = currentGameMap.getWidth();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                block = getChildAt(getIndex(i, j));
                block.setBackgroundColor(getResources().getColor(blockColors[map[i][j]]));
                block.layout(actualLeft + j * singleSize + 2,
                        actualTop + i * singleSize + 2,
                        actualLeft + j * singleSize + singleSize - 2,
                        actualTop + i * singleSize + singleSize - 2);
            }
        }
    }

    public void setGameMap(GameMap gameMap) {
        if (gameMap == null) {
            return;
        }
        currentGameMap = gameMap.cloneMap();
        oldGameMap = gameMap.cloneMap();
        initViewDate();
    }

    public void setMapCode(ArrayList<String> mapSource) {
        if (mapSource == null) {
            return;
        }
        currentGameMap = new GameMap(mapSource);
        oldGameMap = currentGameMap.cloneMap();
        initViewDate();
    }

    public void setModu(int modu) {
        currentGameMap.setModu(modu);
        oldGameMap.setModu(modu);
    }

    public void setModulos(ArrayList<SingleModulo> modulos) {
        this.modulos = modulos;
        resultCode = new int[modulos.size() * 2];
    }

    public void undo() {
        if (currentIndex == 0) {
            return;
        }
        currentIndex--;
        currentGameMap.removeModulo(resultCode[currentIndex * 2], resultCode[currentIndex * 2 + 1], modulos.get(currentIndex));
        oldGameMap.setMap(currentGameMap.getMap());
        if (changeListener != null) {
            changeListener.remove(this, currentIndex);
        }
        requestLayout();
    }

    public boolean checkWin() {
        return currentGameMap.checkWin();
    }

    public void setChangeListener(OnModuloChangeListener changeListener) {
        this.changeListener = changeListener;
    }

    public int[] getResultCode() {
        return resultCode;
    }

    public void clearData() {
        removeAllViews();
        currentGameMap = null;
        oldGameMap = null;
        changeListener = null;
        blockArray.clear();
        modulos.clear();
        resultCode = null;
        currentIndex = 0;
    }

    private void initViewDate() {
        blockArray.clear();

        int width = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        int height = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
        int xSize = currentGameMap.getMap()[0].length;
        int ySize = currentGameMap.getMap().length;
        singleSize = Math.min(width / xSize, height / ySize);
        int actualWidth = singleSize * xSize;
        int actualHeight = singleSize * ySize;
        actualLeft = (width - actualWidth) / 2;
        actualTop = (height - actualHeight) / 2;
        touchArea.set(actualLeft, actualTop, actualLeft + singleSize * xSize, actualTop + singleSize * ySize);

        for (int i = 0; i < currentGameMap.getHeight(); i++) {
            for (int j = 0; j < currentGameMap.getWidth(); j++) {
                View view = new View(getContext());
                blockArray.put(getIndex(i, j), view);
                addView(view, getIndex(i, j));
            }
        }
        requestLayout();
    }

    private int getIndex(int row, int column) {
        return row * currentGameMap.getWidth() + column;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (currentGameMap == null || currentIndex == modulos.size() || !touchArea.contains(event.getX(), event.getY())) {
            return super.onTouchEvent(event);
        }
        int x = getBlockX(event.getX());
        int y = getBlockY(event.getY());
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                putBlock(y, x, true);
                break;
            case MotionEvent.ACTION_UP:
                putBlock(y, x, false);
                break;
        }
        return true;
    }

    private int getBlockX(float x) {
        return (int) (x - actualLeft) / singleSize;
    }

    private int getBlockY(float y) {
        return (int) (y - actualTop) / singleSize;
    }

    private void putBlock(int x, int y, boolean isTrying) {
        int [] coordinates = Calculator.getPutCoordinates(currentGameMap, modulos.get(currentIndex), x, y);
        if (isTrying) {
            currentGameMap.setMap(oldGameMap.getMap());
            currentGameMap.putModulo(coordinates[0], coordinates[1], modulos.get(currentIndex));
        } else {
            oldGameMap.setMap(currentGameMap.getMap());
            resultCode[currentIndex * 2] = coordinates[0];
            resultCode[currentIndex * 2 + 1] = coordinates[1];
            if (changeListener != null) {
                changeListener.put(this, currentIndex);
            }
            currentIndex++;
        }
        requestLayout();
    }

    public interface OnModuloChangeListener {
        void put(GameMapView gameMapView, int currentIndex);
        void remove(GameMapView gameMapView, int currentIndex);
    }
}
