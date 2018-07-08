package com.example.shared_parking;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ParkingSpaceDao {
    @Query("SELECT * FROM parkingspace")
    List<ParkingSpace> getAll();

    @Query("SELECT * FROM parkingspace WHERE user_id == (:userId)")
    List<ParkingSpace> getAllFromUser(int userId);

    @Query("SELECT * FROM parkingspace WHERE id == (:id)")
    ParkingSpace getWithId(int id);

    @Insert
    void insertParkingSpace(ParkingSpace parkingSpace);
}
