package com.sergey.zhuravlev.pgu.schedule.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.sergey.zhuravlev.pgu.schedule.fragment.GroupFragment;
import com.sergey.zhuravlev.pgu.schedule.model.Group;
import com.sergey.zhuravlev.pgu.schedule.model.Schedule;

import java.util.ArrayList;
import java.util.List;

public class ScheduleAdapter extends FragmentPagerAdapter {

    private final List<GroupFragment> tabs;
    private final Context context;

    private Schedule schedule;

    public ScheduleAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
        this.tabs = new ArrayList<>();
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
//        if (!tabs.isEmpty()) throw new Exception("Cant load 2 step");
        for (Group group : schedule.getGroups()) {
            this.tabs.add(GroupFragment.getInstance(context, group, schedule.getGroupClassworks(group)));
        }
        this.notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return tabs.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return String.valueOf(position + 1);
    }

    @Override
    public int getCount() {
        return tabs.size();
    }
}
