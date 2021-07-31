package com.paipayales.app;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.paipayales.app.db.AppRoomDatabase;
import com.paipayales.app.db.dao.PoolDao;
import com.paipayales.app.db.entity.Pool;

import java.util.List;

public class DataRepository {

    private PoolDao mPoolDao;
    private LiveData<List<Pool>> mAllPools;

    public DataRepository(Application application) {
        AppRoomDatabase db = AppRoomDatabase.getDatabase(application);
        mPoolDao = db.poolDao();
        mAllPools = mPoolDao.getPoolsByNumericCode();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<Pool>> getAllPools() {
        return mAllPools;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void insert(Pool pool) {
        AppRoomDatabase.databaseWriteExecutor.execute(() -> {
            mPoolDao.insert(pool);
        });
    }

}
