package com.sergey.zhuravlev.pgu.schedule.model;

public class Classwork {

    private final WeekPeriod weekPeriod;
    private final DayOfWeek dayOfWeek;
    private final ClassworkPeriod period;
    private final Group group;
    private final String classwork;
    private final String teacher;
    private final String audience;

    public Classwork(WeekPeriod weekPeriod, DayOfWeek dayOfWeek, ClassworkPeriod period, Group group, String classwork, String teacher, String audience) {
        this.weekPeriod = weekPeriod;
        this.dayOfWeek = dayOfWeek;
        this.period = period;
        this.group = group;
        this.classwork = classwork;
        this.teacher = teacher;
        this.audience = audience;
    }

    public WeekPeriod getWeekPeriod() {
        return weekPeriod;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public ClassworkPeriod getPeriod() {
        return period;
    }

    public Group getGroup() {
        return group;
    }

    public String getClasswork() {
        return classwork;
    }

    public String getTeacher() {
        return teacher;
    }

    public String getAudience() {
        return audience;
    }

}
