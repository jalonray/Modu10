package com.windywolf.rusher.modu10;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import java.util.HashMap;

/**
 * Created: Mr.Ray
 * Date: 16/1/11.
 * Info: 可拖动的条块
 */
public class GameSingleModulo extends View {
    private int xSize;
    private int ySize;
    private HashMap<Point, Integer> moduloCode = new HashMap<>();
    private HashMap<Point, View> moduloBlocks = new HashMap<>();
    private int singleSize;
    private int actualLeft;
    private int actualTop;
    private View block;

    public GameSingleModulo(Context context) {
        super(context);
    }

    public GameSingleModulo(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GameSingleModulo(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        for (Point point : moduloCode.keySet()) {
            block = moduloBlocks.get(point);
            block.setBackgroundColor(moduloCode.get(point) == 'X' ?
                    getResources().getColor(R.color.DeepPurple400) :
                    Color.TRANSPARENT);
            block.layout(actualLeft + point.x * singleSize,
                    actualTop + point.y * singleSize,
                    actualLeft + point.x * singleSize + singleSize,
                    actualTop + point.y * singleSize + singleSize);
        }
    }

    public void setModuloCode(String moduloSource) {
        if (TextUtils.isEmpty(moduloSource)) {
            return;
        }
        String[] modulos = moduloSource.split(",");
        xSize = modulos.length;
        ySize = modulos[0].length();
        for (int i = 0; i < modulos.length; i++) {
            for (int j = 0; j < modulos[i].length(); j++) {
                Point point = new Point(i, j);
                moduloCode.put(point, modulos[i].charAt(j) == 'X' ? 1 : 0);
                moduloBlocks.put(point, new View(getContext()));
            }
        }
        int width = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        int height = getMinimumHeight() - getPaddingTop() - getPaddingBottom();
        singleSize = Math.min(width / xSize, height / ySize);
        actualLeft = (width - singleSize * xSize) / 2;
        actualTop = (width - singleSize * ySize) / 2;
        for (View view : moduloBlocks.values()) {
            view.setMinimumWidth(singleSize);
            view.setMinimumHeight(singleSize);
        }
    }
}
