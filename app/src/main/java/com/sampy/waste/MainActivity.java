package com.sampy.waste;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView tvClientCount, tvCollectionCount, tvLogCount, tvTodayCount;
    private View cardClients, cardCollections, cardLogs;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Initialize Database
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "sampy-db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

        // 2. Initialize Views
        initViews();

        // 3. Load Dashboard Data
        refreshDashboard();
    }

    private void initViews() {
        tvClientCount = findViewById(R.id.tvClientCount);
        tvCollectionCount = findViewById(R.id.tvCollectionCount);
        tvLogCount = findViewById(R.id.tvLogCount);
        tvTodayCount = findViewById(R.id.tvTodayCount);
        cardClients = findViewById(R.id.cardClients);
        cardCollections = findViewById(R.id.cardCollections);
        cardLogs = findViewById(R.id.cardLogs);
        RecyclerView recyclerRecentActivity = findViewById(R.id.recyclerRecentActivity);
        FloatingActionButton btnAddClientMain = findViewById(R.id.btnAddClientMain);

        // Setup Recent Activity RecyclerView
        List<Job> recentJobs = db.jobDao().getAllJobs();
        JobAdapter adapter = new JobAdapter(recentJobs);
        recyclerRecentActivity.setLayoutManager(new LinearLayoutManager(this));
        recyclerRecentActivity.setAdapter(adapter);

        // Setup FAB Click (Add Client)
        btnAddClientMain.setOnClickListener(v -> showAddClientDialog());

        // Navigation
        cardClients.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ClientsActivity.class);
            startActivity(intent);
        });

        cardCollections.setOnClickListener(v -> {
            // Placeholder for now, maybe same as logs
            Toast.makeText(this, "Collections Clicked", Toast.LENGTH_SHORT).show();
        });

        cardLogs.setOnClickListener(v -> {
            // Placeholder for logs activity if separate
            Toast.makeText(this, "Logs Clicked", Toast.LENGTH_SHORT).show();
        });
    }

    private void refreshDashboard() {
        int clientCount = db.clientDao().getClientCount();
        int jobCount = db.jobDao().getJobCount();

        // Get Today's date in a format compatible with Job.dateTime (simple approach)
        String today = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(new Date());
        int todayCount = db.jobDao().getTodayJobCount("%" + today + "%");

        tvClientCount.setText(String.valueOf(clientCount));
        tvCollectionCount.setText(String.valueOf(jobCount));
        tvLogCount.setText(String.valueOf(jobCount));
        tvTodayCount.setText(String.valueOf(todayCount));
    }

    private void showAddClientDialog() {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_client, null);
        
        // Removed the explicit style that was causing the error
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(dialogView)
                .create();

        EditText etName = dialogView.findViewById(R.id.etClientName);
        EditText etAddress = dialogView.findViewById(R.id.etAddress);
        EditText etContactPerson = dialogView.findViewById(R.id.etContactPerson);
        EditText etContactNumber = dialogView.findViewById(R.id.etContactNumber);
        Button btnSave = dialogView.findViewById(R.id.btnSave);
        Button btnCancel = dialogView.findViewById(R.id.btnCancel);
        ImageButton btnClose = dialogView.findViewById(R.id.btnClose);

        btnSave.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String address = etAddress.getText().toString().trim();
            
            if (!name.isEmpty() && !address.isEmpty()) {
                Client client = new Client();
                client.name = name;
                client.address = address;
                client.contactPerson = etContactPerson.getText().toString();
                client.contactNumber = etContactNumber.getText().toString();

                db.clientDao().insert(client);
                refreshDashboard();
                dialog.dismiss();
                Toast.makeText(this, "Client Added!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Please enter Name and Address", Toast.LENGTH_SHORT).show();
            }
        });

        btnCancel.setOnClickListener(v -> dialog.dismiss());
        btnClose.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }
}
