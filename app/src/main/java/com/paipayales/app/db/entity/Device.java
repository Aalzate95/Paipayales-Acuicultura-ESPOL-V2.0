package com.paipayales.app.db.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "device_table")
public class Device {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int id_device;

    @NonNull
    public int id_pool;

    @NonNull
    public String host_ip;

}
