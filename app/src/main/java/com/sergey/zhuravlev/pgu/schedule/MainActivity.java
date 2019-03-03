package com.sergey.zhuravlev.pgu.schedule;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
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
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.sergey.zhuravlev.pgu.schedule.adapter.ScheduleAdapter;
import com.sergey.zhuravlev.pgu.schedule.exception.ParseScheduleException;
import com.sergey.zhuravlev.pgu.schedule.model.Schedule;
import com.sergey.zhuravlev.pgu.schedule.parser.ScheduleParser;
import com.sergey.zhuravlev.pgu.schedule.preprocessor.WordLoader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private static final String DOCX_MIME_TYPE = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";

    private static final int DOC_SELECT_CODE = 1;

    private ProgressBar progressBar;
    private ScheduleAdapter adapter;

    private class ScheduleParseTask extends AsyncTask<InputStream, Void, Schedule> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        @RequiresApi(api = Build.VERSION_CODES.N)
        protected Schedule doInBackground(InputStream... inputStreams) {
            try {
                WordLoader loader = new WordLoader();
                return ScheduleParser.parse(loader.loadDocument(inputStreams[0]));
            } catch (ParseScheduleException e) {
                Toast.makeText(MainActivity.this, "ERROR WHEN PARSE " + e.getMessage(), Toast.LENGTH_LONG).show();
                return null;
            } catch (IOException e) {
                Toast.makeText(MainActivity.this, "ERROR WHEN OPEN " + e.getMessage(), Toast.LENGTH_LONG).show();
                return null;
            }
        }

        @Override
        protected void onCancelled() {
            progressBar.setVisibility(View.GONE);
        }

        @Override
        protected void onPostExecute(Schedule schedule) {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(MainActivity.this, "OK", Toast.LENGTH_LONG).show();
            adapter.setSchedule(schedule);
        }

    }

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

        progressBar = findViewById(R.id.app_bar_progress_bar);
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

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == DOC_SELECT_CODE && resultCode == RESULT_OK) {
            setProgressBarIndeterminateVisibility(true);
            ContentResolver resolver = this.getContentResolver();
            Uri selectedFile = data.getData();
            if (selectedFile == null) throw new NullPointerException();
            try {
                ScheduleParseTask scheduleParseTask = new ScheduleParseTask();
                scheduleParseTask.execute(resolver.openInputStream(selectedFile));
            } catch (FileNotFoundException e) {
                Toast.makeText(MainActivity.this, "ERROR WHEN OPEN " + e.getMessage(), Toast.LENGTH_LONG).show();
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
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_open) {
            Intent intent = new Intent().setType(DOCX_MIME_TYPE).setAction(Intent.ACTION_GET_CONTENT);
            this.startActivityForResult(Intent.createChooser(intent, "Select a doc"), DOC_SELECT_CODE);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
