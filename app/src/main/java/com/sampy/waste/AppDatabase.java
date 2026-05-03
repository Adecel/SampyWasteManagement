package com.sampy.waste;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Job.class, Client.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {
    public abstract JobDao jobDao();
    public abstract ClientDao clientDao();
}