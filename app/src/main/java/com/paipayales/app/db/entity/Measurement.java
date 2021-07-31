package com.paipayales.app.db.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Date;

@Entity(tableName = "measurement_table")
public class Measurement {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int id_measurement;

    @NonNull
    public int id_sensor;

    @NonNull
    public Double value;

    @NonNull
    public String timestamp;

    @NonNull
    public String received_timestamp;

}
