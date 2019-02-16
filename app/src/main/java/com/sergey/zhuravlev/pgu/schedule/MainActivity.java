package com.sergey.zhuravlev.pgu.schedule;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sergey.zhuravlev.pgu.schedule.exception.ParseScheduleException;
import com.sergey.zhuravlev.pgu.schedule.model.Schedule;
import com.sergey.zhuravlev.pgu.schedule.parser.ScheduleParser;
import com.sergey.zhuravlev.pgu.schedule.preprocessor.WordLoader;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final int DOC_SELECT_CODE = 1;

    private TextView schedule;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        System.setProperty("org.apache.poi.javax.xml.stream.XMLInputFactory", "com.fasterxml.aalto.stax.InputFactoryImpl");
        System.setProperty("org.apache.poi.javax.xml.stream.XMLOutputFactory", "com.fasterxml.aalto.stax.OutputFactoryImpl");
        System.setProperty("org.apache.poi.javax.xml.stream.XMLEventFactory", "com.fasterxml.aalto.stax.EventFactoryImpl");

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        button = findViewById(R.id.make_choise);
        schedule = findViewById(R.id.schedule);

        button.setOnClickListener(event-> {
            Intent intent = new Intent().setType("application/vnd.openxmlformats-officedocument.wordprocessingml.document").setAction(Intent.ACTION_GET_CONTENT);
            this.startActivityForResult(Intent.createChooser(intent, "Select a doc"), DOC_SELECT_CODE);
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == DOC_SELECT_CODE && resultCode == RESULT_OK) {
            try {
                ContentResolver resolver = this.getContentResolver();
                Uri selectedFile = data.getData();
                if (selectedFile == null) throw new NullPointerException();
                WordLoader loader = new WordLoader();
                Schedule schedule = ScheduleParser.parse(loader.loadDocument(resolver.openInputStream(selectedFile)));
                this.schedule.setText(schedule.toString());
            } catch (IOException e) {
                Toast.makeText(this, "ERROR WHEN OPEN " + e.getMessage(), Toast.LENGTH_LONG).show();
            } catch (ParseScheduleException e) {
                Toast.makeText(this, "ERROR WHEN PARSE " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

}
