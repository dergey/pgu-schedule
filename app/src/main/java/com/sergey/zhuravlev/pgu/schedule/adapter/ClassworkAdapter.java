package com.sergey.zhuravlev.pgu.schedule.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sergey.zhuravlev.pgu.schedule.R;
import com.sergey.zhuravlev.pgu.schedule.model.Classwork;

import java.util.List;

public class ClassworkAdapter extends RecyclerView.Adapter<ClassworkAdapter.ClassworkHolderView> {

    static class ClassworkHolderView extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView title, time, teacher, audience;

        ClassworkHolderView(View itemView) {
            super(itemView);
            itemView.getContext();
            itemView.setOnClickListener(this);
            title = itemView.findViewById(R.id.classwork_item_title);
            teacher = itemView.findViewById(R.id.classwork_item_teacher);
            time = itemView.findViewById(R.id.classwork_item_time);
            audience = itemView.findViewById(R.id.classwork_item_audience);
        }

        void load(Classwork classwork) {
            title.setText(classwork.getClasswork());
            teacher.setVisibility(classwork.getTeacher() == null ? View.GONE : View.VISIBLE);
            teacher.setText(classwork.getTeacher());
            time.setText(classwork.getPeriod().getPeriod());
            audience.setText(classwork.getAudience());
        }

        @Override
        public void onClick(View v) {
            //TODO
        }

    }

    private Context context;
    private List<Classwork> data;

    public ClassworkAdapter(List<Classwork> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public ClassworkHolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.classwork_item, parent, false);
        return new ClassworkHolderView(view);
    }

    @Override
    public void onBindViewHolder(final ClassworkHolderView holder, final int position) {
        holder.load(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
