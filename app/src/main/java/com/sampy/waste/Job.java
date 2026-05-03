package com.sampy.waste;

public class Job {

    private String address;
    private String status;
    private String driver;

    public Job(String address, String status, String driver) {
        this.address = address;
        this.status = status;
        this.driver = driver;
    }

    public String getAddress() { return address; }
    public String getStatus() { return status; }
    public String getDriver() { return driver; }
}