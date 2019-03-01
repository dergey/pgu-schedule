package com.sergey.zhuravlev.pgu.schedule;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.sergey.zhuravlev.pgu.schedule.adapter.ScheduleAdapter;
import com.sergey.zhuravlev.pgu.schedule.exception.ParseScheduleException;
import com.sergey.zhuravlev.pgu.schedule.model.Schedule;
import com.sergey.zhuravlev.pgu.schedule.parser.ScheduleParser;
import com.sergey.zhuravlev.pgu.schedule.preprocessor.WordLoader;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final int DOC_SELECT_CODE = 1;

    private ScheduleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        System.setProperty("org.apache.poi.javax.xml.stream.XMLInputFactory", "com.fasterxml.aalto.stax.InputFactoryImpl");
        System.setProperty("org.apache.poi.javax.xml.stream.XMLOutputFactory", "com.fasterxml.aalto.stax.OutputFactoryImpl");
        System.setProperty("org.apache.poi.javax.xml.stream.XMLEventFactory", "com.fasterxml.aalto.stax.EventFactoryImpl");

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewPager viewPager = findViewById(R.id.view_pager);
        adapter = new ScheduleAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
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
                adapter.setSchedule(schedule);
                Toast.makeText(this, "OK", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                Toast.makeText(this, "ERROR WHEN OPEN " + e.getMessage(), Toast.LENGTH_LONG).show();
            } catch (ParseScheduleException e) {
                Toast.makeText(this, "ERROR WHEN PARSE " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_open) {
            Intent intent = new Intent().setType("application/vnd.openxmlformats-officedocument.wordprocessingml.document").setAction(Intent.ACTION_GET_CONTENT);
            this.startActivityForResult(Intent.createChooser(intent, "Select a doc"), DOC_SELECT_CODE);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
