package com.sampy.waste;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import java.util.List;

public class LogsActivity extends AppCompatActivity {

    private RecyclerView recyclerLogs;
    private JobAdapter adapter;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logs);

        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "sampy-db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        recyclerLogs = findViewById(R.id.recyclerLogs);
        recyclerLogs.setLayoutManager(new LinearLayoutManager(this));

        List<Job> allJobs = db.jobDao().getAllJobs();
        adapter = new JobAdapter(allJobs);
        recyclerLogs.setAdapter(adapter);
    }
}
