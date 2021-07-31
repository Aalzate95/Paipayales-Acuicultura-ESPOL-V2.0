package com.paipayales.app.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.paipayales.app.db.entity.Measurement;

import java.util.List;

@Dao
public interface MeasurementDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Measurement measurement);

    @Update
    void update(Measurement measurement);

    @Update
    void updateSensors(List<Measurement> measurements);

    @Delete
    void delete(Measurement measurement);

    @Delete
    void deleteSensors(List<Measurement> measurements);

    @Query("DELETE FROM measurement_table")
    void deleteAll();

    @Query("SELECT * FROM measurement_table WHERE id_sensor = :id_sensor")
    LiveData<Measurement> getMeasurementBySensorId(int id_sensor);
}
