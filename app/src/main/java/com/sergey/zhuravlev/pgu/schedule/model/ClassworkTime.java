package com.sergey.zhuravlev.pgu.schedule.model;

public enum ClassworkTime {

    FIRST_CLASSWORK ("8.30-10.00"),
    SECOND_CLASSWORK ("10.15-11.45"),
    THIRD_CLASSWORK ("12.15-13.45"),
    FOURTH_CLASSWORK ("14.15-15.45"),
    FIFTH_CLASSWORK ("16.00-17.30"),
    SIXTH_CLASSWORK ("17.45-19.15"),
    SEVENTH_CLASSWORK ("19.25-20.50");

    private String string;

    ClassworkTime(final String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }
}
