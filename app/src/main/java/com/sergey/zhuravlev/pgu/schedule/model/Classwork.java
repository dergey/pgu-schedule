package com.sergey.zhuravlev.pgu.schedule.model;

public class Classwork {

    private final DayOfWeek dayOfWeek;
    private final ClassworkPeriod period;
    private final String group;
    private final String classwork;

    public Classwork(DayOfWeek dayOfWeek, ClassworkPeriod period, String group, String classwork) {
        this.dayOfWeek = dayOfWeek;
        this.period = period;
        this.group = group;
        this.classwork = classwork;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public ClassworkPeriod getPeriod() {
        return period;
    }

    public String getGroup() {
        return group;
    }

    public String getClasswork() {
        return classwork;
    }

    @Override
    public String toString() {
        return "Classwork{" +
                "dayOfWeek=" + dayOfWeek +
                ", period=" + period.getPeriod() +
                ", group='" + group + '\'' +
                ", classwork='" + classwork + '\'' +
                '}';
    }
}
