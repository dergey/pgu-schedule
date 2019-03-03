package com.sergey.zhuravlev.pgu.schedule.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

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
        this.clear();
        for (Group group : schedule.getGroups()) {
            this.tabs.add(GroupFragment.getInstance(context, group, schedule.getGroupClassworks(group)));
        }
        this.notifyDataSetChanged();
    }

    public void clear() {
        for (int i = 0; i < tabs.size(); i++) {
            this.destroyItem(null, i, tabs.get(i));
        }
        tabs.clear();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (position >= getCount()) {
            FragmentManager manager = ((Fragment) object).getFragmentManager();
            FragmentTransaction trans = manager.beginTransaction();
            trans.remove((Fragment) object);
            trans.commit();
        }
    }

    @Override
    public Fragment getItem(int position) {
        return tabs.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs.get(position).getGroup().getName().substring(0, 3) + "-" + tabs.get(position).getGroup().getYear();
    }

    @Override
    public int getCount() {
        return tabs.size();
    }
}
