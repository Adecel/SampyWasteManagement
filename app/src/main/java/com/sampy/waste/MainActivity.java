package com.sampy.waste;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerJobs;
    Button btnAddJob;
    List<Job> jobList;
    JobAdapter adapter;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerJobs = findViewById(R.id.recyclerJobs);
        btnAddJob = findViewById(R.id.btnAddJob);

        db = Room.databaseBuilder(
                getApplicationContext(),
                AppDatabase.class,
                "sampy-db"
        ).allowMainThreadQueries().build();

        jobList = db.jobDao().getAllJobs();

        adapter = new JobAdapter(jobList);
        recyclerJobs.setLayoutManager(new LinearLayoutManager(this));
        recyclerJobs.setAdapter(adapter);

        btnAddJob.setOnClickListener(v -> {
            LayoutInflater inflater = LayoutInflater.from(this);
            View dialogView = inflater.inflate(R.layout.dialog_add_job, null);

            EditText etAddress = dialogView.findViewById(R.id.etAddress);
            EditText etDriver = dialogView.findViewById(R.id.etDriver);

            new AlertDialog.Builder(this)
                    .setTitle("Add New Job")
                    .setView(dialogView)
                    .setPositiveButton("Add", (dialog, which) -> {
                        String address = etAddress.getText().toString();
                        String driver = etDriver.getText().toString();

                        if (!address.isEmpty() && !driver.isEmpty()) {
                            Job newJob = new Job(address, "Pending", driver);
                            
                            // Save to database
                            db.jobDao().insert(newJob);
                            
                            // Update UI
                            jobList.add(newJob);
                            adapter.notifyItemInserted(jobList.size() - 1);
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });
    }
}
