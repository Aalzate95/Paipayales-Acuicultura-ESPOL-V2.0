package com.paipayales.app.viewmodel;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import androidx.preference.PreferenceManager;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.paipayales.app.DataRepository;
import com.paipayales.app.R;
import com.paipayales.app.api.ApiClient;
import com.paipayales.app.api.DeviceService;
import com.paipayales.app.api.MeasurementResponse;
import com.paipayales.app.db.entity.Measurement;
import com.paipayales.app.db.entity.Pool;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MeasurementViewModel extends AndroidViewModel {

    private String TAG = this.getClass().getName();

    private DataRepository mRepository;
    private DeviceService mDefaultDeviceService;

    // private String mBaseUrl = "http://192.168.1.1/v1/";
    private String mDefaultBaseUrl = "https://1e435f52-b1c3-4fd3-a14b-5bdf0c9d59f6.mock.pstmn.io";

    private MutableLiveData<List<MeasurementResponse>> mAllMeasurements;

    public MeasurementViewModel(Application application) {
        super(application);

        mDefaultDeviceService = ApiClient.getClient(mDefaultBaseUrl).create(DeviceService.class);
    }

    public MutableLiveData<List<MeasurementResponse>> getAllMeasurements() {
        if (mAllMeasurements == null){
            mAllMeasurements = new MutableLiveData<List<MeasurementResponse>>();
        }
        return mAllMeasurements;
    }

    public void loadMeasurementsFromServer(Context context, TextView lblEstado, Button btnSync) {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        String url = prefs.getString("hostUrl", mDefaultBaseUrl);

        DeviceService deviceService = ApiClient.getClient(url).create(DeviceService.class);

        Call<List<MeasurementResponse>> call = deviceService.getAllMeasurements();

        call.enqueue(new Callback<List<MeasurementResponse>>() {
            @Override
            public void onResponse(Call<List<MeasurementResponse>> call, Response<List<MeasurementResponse>> response) {
                Log.d("TAG", "Response = " + response.message() );

                mAllMeasurements.setValue(response.body());
                lblEstado.setText("Estado: OK");
                btnSync.setEnabled(true);
                btnSync.setBackground(context.getApplicationContext().getDrawable(R.drawable.btn_action));

            }

            @Override
            public void onFailure(Call<List<MeasurementResponse>> call, Throwable t) {
                Log.d(TAG, "Response = " + t.getMessage());
                lblEstado.setText("Estado: ERROR");
                btnSync.setEnabled(true);
                btnSync.setBackground(context.getApplicationContext().getDrawable(R.drawable.btn_action));
            }
        });
    }

    public void loadMeasuremenstFromServerByPoolId(Context context, Pool pool, TextView lblEstado, Button btnSync){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        String url = prefs.getString("hostUrl", mDefaultBaseUrl);

        DeviceService deviceService = ApiClient.getClient(url).create(DeviceService.class);

        Call<List<MeasurementResponse>> call = deviceService.getMeasurementByPoolId(pool.numeric_code);

        call.enqueue(new Callback<List<MeasurementResponse>>() {
            @Override
            public void onResponse(Call<List<MeasurementResponse>> call, Response<List<MeasurementResponse>> response) {
                Log.d("TAG", "Response = " + response.message() );

                mAllMeasurements.setValue(response.body());
                lblEstado.setText("Estado: OK");
                btnSync.setEnabled(true);
                btnSync.setBackground(context.getApplicationContext().getDrawable(R.drawable.btn_action));
            }

            @Override
            public void onFailure(Call<List<MeasurementResponse>> call, Throwable t) {
                Log.d(TAG, "Response = " + t.getMessage());
                lblEstado.setText("Estado: ERROR");
                btnSync.setEnabled(true);
                btnSync.setBackground(context.getApplicationContext().getDrawable(R.drawable.btn_action));
            }
        });
    }

}
