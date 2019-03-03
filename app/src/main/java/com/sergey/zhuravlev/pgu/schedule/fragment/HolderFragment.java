package com.sergey.zhuravlev.pgu.schedule.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.sergey.zhuravlev.pgu.schedule.R;

public class HolderFragment extends Fragment {

    Button button;
    Spinner sp1, sp2;
    TextView displayData;

    String names[] = {"17-ГЕО","18-ГЕО","17-АРХ","18-АРХ"};
    String record;

    ArrayAdapter <String> adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.holder_fragment, container, false);

        sp1 = view.findViewById(R.id.spinner2);
        adapter = new ArrayAdapter<String>(this.getContext(),android.R.layout.simple_expandable_list_item_1,names);

        displayData = view.findViewById(R.id.display_result);

        sp1.setAdapter(adapter);
        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                record = names[position];

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        button = view.findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayData.setText(record);
            }
        });
        return view;
    }
}
