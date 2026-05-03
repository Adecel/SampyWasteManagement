package com.sampy.waste;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Job.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract JobDao jobDao();
}