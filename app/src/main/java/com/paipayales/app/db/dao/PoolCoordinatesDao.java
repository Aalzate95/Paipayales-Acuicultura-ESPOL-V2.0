package com.paipayales.app.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.paipayales.app.db.entity.PoolCoordinates;

import java.util.List;

@Dao
public interface PoolCoordinatesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(PoolCoordinates coordinates);

    @Update
    void update(PoolCoordinates coordinates);

    @Update
    void updateCoordinates(List<PoolCoordinates> coordinates);

    @Delete
    void delete(PoolCoordinates coordinates);

    @Delete
    void deleteCoordinates(List<PoolCoordinates> coordinates);

    @Query("DELETE FROM pool_coordinates_table")
    void deleteAll();

    @Query("SELECT * FROM pool_coordinates_table WHERE id_pool = :id_pool")
    LiveData<PoolCoordinates> getCoordinatesByPoolId(int id_pool);
}
