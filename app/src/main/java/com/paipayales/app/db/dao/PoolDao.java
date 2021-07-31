package com.paipayales.app.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.paipayales.app.db.entity.Pool;

import java.util.List;

@Dao
public interface PoolDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Pool pool);

    @Update
    void update(Pool pool);

    @Update
    void updatePools(List<Pool> pools);

    @Delete
    void delete(Pool pool);

    @Delete
    void deletePools(List<Pool> pools);

    @Query("DELETE FROM pool_table")
    void deleteAll();

    @Query("SELECT * FROM pool_table ORDER BY name ASC")
    LiveData<List<Pool>> getPoolsAlphabetically();

    @Query("SELECT * FROM pool_table ORDER BY numeric_code ASC")
    LiveData<List<Pool>> getPoolsByNumericCode();
}
