package com.sampy.waste;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class LogsActivity extends AppCompatActivity {

    private RecyclerView recyclerLogs;
    private JobAdapter adapter;
    private DrawerLayout drawerLayout;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logs);

        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "sampy-db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

        // Setup Toolbar & Drawer
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_dashboard) {
                startActivity(new Intent(LogsActivity.this, MainActivity.class));
                finish();
            } else if (id == R.id.nav_clients) {
                startActivity(new Intent(LogsActivity.this, ClientsActivity.class));
                finish();
            } else if (id == R.id.nav_settings) {
                Toast.makeText(this, "Settings clicked", Toast.LENGTH_SHORT).show();
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });

        recyclerLogs = findViewById(R.id.recyclerLogs);
        recyclerLogs.setLayoutManager(new LinearLayoutManager(this));

        List<Job> allJobs = db.jobDao().getAllJobs();
        adapter = new JobAdapter(allJobs);
        recyclerLogs.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout != null && drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
