package com.sergey.zhuravlev.pgu.schedule.model;

public class Group {

    private String name;
    private int year; //20**

    public Group(String name, int year) {
        this.name = name;
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Группа " + name + " 20" + year + "г.";
    }
}
