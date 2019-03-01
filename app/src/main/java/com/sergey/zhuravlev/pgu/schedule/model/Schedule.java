package com.sergey.zhuravlev.pgu.schedule.model;

import android.os.Build;
import android.support.annotation.RequiresApi;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Schedule {

    private Collection<Classwork> classworks;
    private List<Group> groups;

    public Schedule() {
        this.classworks = new ArrayList<>();
        this.groups = new ArrayList<>();
    }

    public void addClasswork(DayOfWeek dayOfWeek, ClassworkPeriod period, String classwork, Group group, String audience) {
        if (!groups.contains(group)) groups.add(group);
        classworks.add(new Classwork(dayOfWeek, period, group, classwork, audience));
    }

    public List<Group> getGroups() {
        return groups;
    }

    public Collection<Classwork> getGroupClassworks(Group group) {
        Collection<Classwork> result = new ArrayList<>();
        for (Classwork classwork : classworks) {
            if (group.equals(classwork.getGroup()))
                result.add(classwork);
        }
        return result;
    }

    public Collection<Classwork> getClassworks() {
        return classworks;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public String toString() {
        return classworks.stream().map(Classwork::toString).reduce("", (c1, c2) -> c1 + '\n' + c2);
    }

}
