package com.sampy.waste;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Client {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;
    public String address;
    public String contactPerson;
    public String contactNumber;
}