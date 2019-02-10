package com.sergey.zhuravlev.pgu.schedule.model;

import android.os.Build;
import android.support.annotation.RequiresApi;


import java.util.ArrayList;
import java.util.Collection;

public class Schedule {

    private Collection<Classwork> rawSchedule;

    public Schedule() {
        this.rawSchedule = new ArrayList<>();
    }

    public void addClasswork(DayOfWeek dayOfWeek, ClassworkPeriod period, String classwork, Group group) {
        rawSchedule.add(new Classwork(dayOfWeek, period, group, classwork));
    }

    public Collection<Classwork> getRawSchedule() {
        return rawSchedule;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public String toString() {
        return rawSchedule.stream().map(Classwork::toString).reduce("", (c1, c2) -> c1 + '\n' + c2);
    }

}
