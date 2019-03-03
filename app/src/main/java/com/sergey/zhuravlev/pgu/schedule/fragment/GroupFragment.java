package com.sergey.zhuravlev.pgu.schedule.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sergey.zhuravlev.pgu.schedule.R;
import com.sergey.zhuravlev.pgu.schedule.adapter.ClassworkAdapter;
import com.sergey.zhuravlev.pgu.schedule.model.Classwork;
import com.sergey.zhuravlev.pgu.schedule.model.Group;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GroupFragment extends Fragment {

    private static final int LAYOUT = R.layout.group_fragment;

    protected Context context;
    protected View view;

    private List<Classwork> data = new ArrayList<>();

    private ClassworkAdapter adapter;

    private Group group;

    public GroupFragment() {
    }

    public static GroupFragment getInstance(Context context, Group group, Collection<Classwork> classworks) {
        Bundle args = new Bundle();
        GroupFragment fragment = new GroupFragment();
        fragment.setArguments(args);
        fragment.setContext(context);
        fragment.setClassworks(classworks);
        fragment.setGroup(group);
        return fragment;
    }

    private void setClassworks(Collection<Classwork> classworks) {
        data.clear();
        data.addAll(classworks);
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        adapter = new ClassworkAdapter(data, context);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
        return view;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @Override
    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

}
