package com.paipayales.app.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;


import com.paipayales.app.db.entity.Sensor;

import java.util.List;

@Dao
public interface SensorDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Sensor sensor);

    @Update
    void update(Sensor sensor);

    @Update
    void updateSensors(List<Sensor> sensors);

    @Delete
    void delete(Sensor sensor);

    @Delete
    void deleteSensors(List<Sensor> sensors);

    @Query("DELETE FROM sensor_table")
    void deleteAll();

    @Query("SELECT * FROM sensor_table WHERE id_device = :id_device")
    LiveData<Sensor> getSensorByDeviceId(int id_device);
}
