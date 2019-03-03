package com.sergey.zhuravlev.pgu.schedule.model;

public enum DayOfWeek {

    MONDAY("Понедельник"),
    TUESDAY("Вторник"),
    WEDNESDAY("Среда"),
    THURSDAY("Четверг"),
    FRIDAY("Пятница"),
    SATURDAY("Суббота");

    private String string;

    DayOfWeek(final String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }
}
