package com.sergey.zhuravlev.pgu.schedule.model;

public enum DayOfWeek {

    MONDAY("Понедельник"),
    TUESDAY("Вторник"),
    WEDNESDAY("Среда"),
    THURSDAY("Четверг"),
    FRIDAY("Пятница"),
    SATURDAY("Суббота");

    private String title;

    DayOfWeek(final String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
