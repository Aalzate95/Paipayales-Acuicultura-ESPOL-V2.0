package com.paipayales.app.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.paipayales.app.DataRepository;
import com.paipayales.app.db.entity.Pool;

import java.util.List;

public class PoolViewModel extends AndroidViewModel {

    private DataRepository mRepository;

    private LiveData<List<Pool>> mAllPools;

    public PoolViewModel(Application application) {
        super(application);
        mRepository = new DataRepository(application);
        mAllPools = mRepository.getAllPools();
    }

    public LiveData<List<Pool>> getAllPools() {
        return mAllPools;
    }

    public void insert(Pool pool) {
        mRepository.insert(pool);
    }

}
