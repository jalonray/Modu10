package com.windywolf.rusher.modu10;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by Mr.Ray on 16/1/22.
 */
public class Calculator {
    public static int[] getPutCoordinates(GameMap map, SingleModulo singleModulo, int x, int y) {
        int[] coordinates = new int[2];
        int rows = map.getHeight() - singleModulo.getHeight();
        int columns = map.getWidth() - singleModulo.getWidth();
        Rect mapRect = map.getRect();
        Rect moduloRect = singleModulo.getRect();
        for (int i = 0; i <= rows; i++) {
            for (int j = 0; j <= columns; j++) {
                moduloRect.offset(j, i);
                if (mapRect.contains(moduloRect) && moduloRect.contains(y, x)) {
                    coordinates[0] = i;
                    coordinates[1] = j;
                    return coordinates;
                }
                moduloRect.offset(-j, -i);
            }
        }
        return coordinates;
    }

    public static int dp2px(float value, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, context.getResources().getDisplayMetrics());
    }
}
