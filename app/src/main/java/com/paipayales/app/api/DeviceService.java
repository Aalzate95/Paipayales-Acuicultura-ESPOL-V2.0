package com.paipayales.app.api;

import androidx.lifecycle.LiveData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DeviceService {

    @GET("measurement")
    Call<List<MeasurementResponse>> getAllMeasurements();

    @GET("measurement")
    Call<List<MeasurementResponse>> getAllMeasurements_test();

    @GET("measurement/{id}")
    Call<List<MeasurementResponse>> getMeasurementByPoolId(@Path("id") String poolId);

    @GET("configuration")
    Call<Object> getConfiguration();

    @GET("configuration/reset")
    Call<Object> resetConfiguration();

    @GET("device/start")
    Call<Object> startSensing(@Query("pool_id") String poolId);

    @GET("device/cancel")
    Call<Object> stopSensing();
}
