package com.sampy.waste;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Job {

    @PrimaryKey(autoGenerate = true)
    public int id;
    private String address;
    private String status; // e.g., "Completed", "Pending", "Missed"
    private String driver;
    private String wasteType; // e.g., "Residual", "Recyclable"
    private String dateTime;  // e.g., "May 20, 2025 8:00 AM"

    public Job(String address, String status, String driver, String wasteType, String dateTime) {
        this.address = address;
        this.status = status;
        this.driver = driver;
        this.wasteType = wasteType;
        this.dateTime = dateTime;
    }

    public int getId() { return id; }
    public String getAddress() { return address; }
    public String getStatus() { return status; }
    public String getDriver() { return driver; }
    public String getWasteType() { return wasteType; }
    public String getDateTime() { return dateTime; }

    public void setStatus(String status) { this.status = status; }
}
