package com.sergey.zhuravlev.pgu.schedule.model;

public class Classwork {

    private final WeekPeriod weekPeriod;
    private final WeekColor weekColor;
    private final DayOfWeek dayOfWeek;
    private final ClassworkTime time;
    private final Group group;
    private final String classwork;
    private final String teacher;
    private final String audience;

    public Classwork(WeekPeriod weekPeriod, WeekColor weekColor, DayOfWeek dayOfWeek, ClassworkTime time, Group group, String classwork, String teacher, String audience) {
        this.weekPeriod = weekPeriod;
        this.weekColor = weekColor;
        this.dayOfWeek = dayOfWeek;
        this.time = time;
        this.group = group;
        this.classwork = classwork;
        this.teacher = teacher;
        this.audience = audience;
    }

    public WeekPeriod getWeekPeriod() {
        return weekPeriod;
    }

    public WeekColor getWeekColor() {
        return weekColor;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public ClassworkTime getTime() {
        return time;
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
