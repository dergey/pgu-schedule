package com.sergey.zhuravlev.pgu.schedule.model;

import android.util.Log;

import com.sergey.zhuravlev.pgu.schedule.exception.ParseScheduleException;
import com.sergey.zhuravlev.pgu.schedule.exception.WeekPeriodException;
import com.sergey.zhuravlev.pgu.schedule.exception.WeekPeriodNotFound;
import com.sergey.zhuravlev.pgu.schedule.exception.WeekPeriodNullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WeekPeriod {

    private static final Pattern weekPeriod = Pattern.compile("^([0-9]+)-([0-9]+)н\\.?");

    private final int startWeek;
    private final int endWeek;

    public WeekPeriod(int startWeek, int endWeek) {
        this.startWeek = startWeek;
        this.endWeek = endWeek;
    }

    public static WeekPeriod of(String s) throws WeekPeriodException {
        if (s == null) throw new WeekPeriodNullable();
        Matcher matcher = weekPeriod.matcher(s);
        if (!matcher.find()) throw new WeekPeriodNotFound("Week period not found in string " + s);
        return new WeekPeriod(
                Integer.valueOf(matcher.group(1)),
                Integer.valueOf(matcher.group(2))
        );
    }

    public int getStartWeek() {
        return startWeek;
    }

    public int getEndWeek() {
        return endWeek;
    }

    @Override
    public String toString() {
        return startWeek + "-" + endWeek;
    }
}
