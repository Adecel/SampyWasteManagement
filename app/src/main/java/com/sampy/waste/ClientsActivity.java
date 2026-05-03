package com.sampy.waste;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class ClientsActivity extends AppCompatActivity {

    private RecyclerView recyclerClients;
    private ClientAdapter adapter;
    private List<Client> clientList;
    private DrawerLayout drawerLayout;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clients);

        // Initialize Database
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
                startActivity(new Intent(ClientsActivity.this, MainActivity.class));
                finish();
            } else if (id == R.id.nav_logs) {
                startActivity(new Intent(ClientsActivity.this, LogsActivity.class));
                finish();
            } else if (id == R.id.nav_settings) {
                Toast.makeText(this, "Settings clicked", Toast.LENGTH_SHORT).show();
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });

        // Initialize Views
        recyclerClients = findViewById(R.id.recyclerClients);
        Button btnAddClient = findViewById(R.id.btnAddClient);

        // Load Clients
        clientList = db.clientDao().getAllClients();
        adapter = new ClientAdapter(clientList);
        recyclerClients.setLayoutManager(new LinearLayoutManager(this));
        recyclerClients.setAdapter(adapter);

        // Add Client Click
        btnAddClient.setOnClickListener(v -> showAddClientDialog());
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout != null && drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void showAddClientDialog() {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_client, null);
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
                
                // Refresh List
                clientList.clear();
                clientList.addAll(db.clientDao().getAllClients());
                adapter.notifyDataSetChanged();
                
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
