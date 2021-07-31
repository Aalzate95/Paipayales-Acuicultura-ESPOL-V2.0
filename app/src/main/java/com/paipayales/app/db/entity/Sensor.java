package com.paipayales.app.db.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "sensor_table")
public class Sensor {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int id_sensor;

    @NonNull
    public int id_device;

    @NonNull
    public String type;

    @NonNull
    public String unit;
}
