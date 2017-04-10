package com.weimob.web.loan.Utils;

import android.content.Context;
import android.content.res.Resources;

/**
 * Created by kevin.tian on 2017/3/31.
 */

public class Util {
    /**
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }

    public static boolean isEmpty(String paramStr) {
        if ("".equals(paramStr)||paramStr == null) {
            return true;
        }
        return false;
    }
}
