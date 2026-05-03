package com.sampy.waste;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;
@Dao
public interface JobDao {

    @Insert
    void insert(Job job);

    @Query("SELECT * FROM Job")
    List<Job> getAllJobs();

    @Query("SELECT COUNT(*) FROM Job")
    int getJobCount();

    @Query("SELECT COUNT(*) FROM Job WHERE dateTime LIKE :dateQuery")
    int getTodayJobCount(String dateQuery);
}
