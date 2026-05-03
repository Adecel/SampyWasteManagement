package com.sampy.waste;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Job {

    @PrimaryKey(autoGenerate = true)
    public int id;
    private String address;
    private String status;
    private String driver;

    public Job(String address, String status, String driver) {
        this.address = address;
        this.status = status;
        this.driver = driver;
    }

    public int getId() { return id; }

    public String getAddress() { return address; }
    public String getStatus() { return status; }
    public String getDriver() { return driver; }

    public void setStatus(String status) {
        this.status = status;
    }
}