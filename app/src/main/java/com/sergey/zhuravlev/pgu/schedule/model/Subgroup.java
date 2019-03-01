package com.sergey.zhuravlev.pgu.schedule.model;

public class Subgroup extends Group {

    private Integer subgroup;

    public Subgroup(String name, int year, int subgroup) {
        super(name, year);
        this.subgroup = subgroup;
    }

    public Integer getSubgroup() {
        return subgroup;
    }

}
