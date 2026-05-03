package com.sampy.waste;

import static com.sampy.waste.R.*;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerJobs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerJobs = findViewById(R.id.recyclerJobs);

        List<Job> jobList = new ArrayList<>();

        // Sample data
        jobList.add(new Job("123 Main Street", "Pending", "John"));
        jobList.add(new Job("45 Green Road", "Collected", "Mike"));
        jobList.add(new Job("78 Sunset Ave", "Missed", "Sarah"));

        JobAdapter adapter = new JobAdapter(jobList);

        recyclerJobs.setLayoutManager(new LinearLayoutManager(this));
        recyclerJobs.setAdapter(adapter);
    }
}