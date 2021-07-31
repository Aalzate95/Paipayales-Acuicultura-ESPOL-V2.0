package com.paipayales.app.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.paipayales.app.db.entity.Device;

import java.util.List;

@Dao
public interface DeviceDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Device device);

    @Update
    void update(Device device);

    @Update
    void updateDevices(List<Device> devices);

    @Delete
    void delete(Device device);

    @Delete
    void deleteDevices(List<Device> devices);

    @Query("DELETE FROM device_table")
    void deleteAll();

    @Query("SELECT * FROM device_table WHERE id_pool = :id_pool")
    LiveData<Device> getDeviceByPoolId(int id_pool);
}
