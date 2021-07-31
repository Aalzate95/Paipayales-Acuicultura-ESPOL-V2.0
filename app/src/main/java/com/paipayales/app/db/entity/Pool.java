package com.paipayales.app.db.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "pool_table")
public class Pool {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int id_pool;

    @NonNull
    public String name;

    @NonNull
    public String numeric_code;

    public Pool(@NonNull String name, @NonNull String numeric_code) {
        this.name = name;
        this.numeric_code = numeric_code;
    }
}
