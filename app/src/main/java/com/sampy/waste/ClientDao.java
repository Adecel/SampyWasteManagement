package com.sampy.waste;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface ClientDao {
    @Insert
    void insert(Client client);

    @Query("SELECT * FROM Client ORDER BY id DESC")
    List<Client> getAllClients();

    @Query("SELECT COUNT(*) FROM Client")
    int getClientCount();
}
