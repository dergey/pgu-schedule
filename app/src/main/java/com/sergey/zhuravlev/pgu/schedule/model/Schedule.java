package com.sergey.zhuravlev.pgu.schedule.model;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class Schedule {

    private final Date updateTime;
    private final Collection<Classwork> data;

    public Schedule(Collection<Classwork> rawSchedule, Date updateTime) {
        this.data = rawSchedule;
        this.updateTime = updateTime;
    }

    public Schedule(Collection<Classwork> rawSchedule) {
        this(rawSchedule, new Date());
    }

    public List<Classwork> getClasswork(DayOfWeek dayOnWeek, Group group) {
        List<Classwork> classworks = new ArrayList<>();
        for (Classwork classwork : data) {
            if (dayOnWeek.equals(classwork.getDayOfWeek())
                    && classwork.getGroup().equals(group)) {
                classworks.add(classwork);
            }
        }
        return classworks;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public String toString() {
        return data.stream().map(Classwork::toString).reduce("", (c1, c2) -> c1 + '\n' + c2);
    }

}
