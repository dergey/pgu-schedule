package com.sergey.zhuravlev.pgu.schedule.utils;

import android.graphics.Color;

public class Utils {

    public static String filterString(String input, String acceptedChars) {
        input = input.toLowerCase();
        StringBuilder resultBuilder = new StringBuilder();
        char[] chars = input.toCharArray();
        for (char aChar : chars) {
            if (acceptedChars.indexOf(aChar) == -1) continue;
            resultBuilder.append(aChar);
        }
        return resultBuilder.toString();
    }

    public static int hex2Rgb(String wordHexColor) {
        return Color.argb(255,
                Integer.valueOf(wordHexColor.substring(0, 2), 16),
                Integer.valueOf(wordHexColor.substring(2, 4), 16),
                Integer.valueOf(wordHexColor.substring(4, 6), 16));
    }

}
