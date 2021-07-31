package com.paipayales.app.db.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "pool_coordinates_table")
public class PoolCoordinates {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int id_coordinates;

    @NonNull
    public int id_pool;

    @NonNull
    public Double latitude;

    @NonNull
    public Double longitude;

}
