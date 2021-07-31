package com.paipayales.app;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.paipayales.app.api.MeasurementResponse;
import com.paipayales.app.db.entity.Pool;
import com.paipayales.app.viewmodel.MeasurementViewModel;
import com.paipayales.app.viewmodel.PoolViewModel;

import java.util.ArrayList;
import java.util.List;

public class DataActivity extends AppCompatActivity {


    List<Pool> poolsList;
    private PoolViewModel mPoolViewModel;
    private MeasurementViewModel mMeasurementViewModel;
    private Spinner mDropdown;
    private Button btnSync;
    private TextView lblEstado;
    private ArrayList<Pool> mPoolListInSpinner;
    private Pool mSelectedPoolInSpinner;
    private TableLayout table_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        table_data = (TableLayout) findViewById(R.id.table_data);

        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Mediciones");
        }

        btnSync = findViewById(R.id.btn_Sync);

        lblEstado= findViewById(R.id.lbl_estado);

        mDropdown = findViewById(R.id.list_Piscinas);

        mPoolListInSpinner = new ArrayList<Pool>();

        mPoolViewModel = new ViewModelProvider(this).get(PoolViewModel.class);

        mPoolViewModel.getAllPools().observe(this, new Observer<List<Pool>>() {
            @Override
            public void onChanged(@Nullable final List<Pool> pools) {

                poolsList = pools;

                mPoolListInSpinner = new ArrayList<Pool>();
                ArrayList<String> itemsList = new ArrayList<String>();
                itemsList.add("TODOS");

                for (Pool pool : pools) {
                    mPoolListInSpinner.add(pool);
                    itemsList.add(pool.name);
                }

                String[] items = new String[itemsList.size()];
                itemsList.toArray(items);

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, items);

                mDropdown.setAdapter(adapter);
            }
        });

        // Seleccionar "TODOS" por defecto
        mDropdown.setSelection(0);
        mSelectedPoolInSpinner = null;

        mMeasurementViewModel = new ViewModelProvider(this).get(MeasurementViewModel.class);

        mMeasurementViewModel.getAllMeasurements().observe(this, getObserver());


        setSpinnerOnItemSelectedListener();
        setSyncButtonListener();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }


    private void setSyncButtonListener() {
        btnSync.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                lblEstado.setText("Estado: Cargando");
                btnSync.setEnabled(false);
                btnSync.setBackground(getDrawable(R.drawable.btn_action_disabled));

                if (mSelectedPoolInSpinner == null) {
                    mMeasurementViewModel.loadMeasurementsFromServer(v.getContext(), lblEstado, btnSync);
                } else {
                    mMeasurementViewModel.loadMeasuremenstFromServerByPoolId(v.getContext(), mSelectedPoolInSpinner, lblEstado, btnSync);
                }
            }
        });
    }

    private void setSpinnerOnItemSelectedListener() {
        mDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    mSelectedPoolInSpinner = null;
                } else {
                    mSelectedPoolInSpinner = mPoolListInSpinner.get(position - 1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mSelectedPoolInSpinner = null;
            }
        });
    }

//    private void updateGraphs() {
//        LiveData<List<MeasurementResponse>> measurements = mMeasurementViewModel.getAllMeasurements();
//
//        // Parametrizar valores maximos
//
//        if (measurements != null) {
//            if (!measurements.getValue().isEmpty()) {
//                MeasurementResponse obj = measurements.getValue().get(0);
//
//                graph_oxigen.setProgress((int) ((obj.getParams().get(1).getValue() / 50.0) * 100));
//                graph_ph.setProgress((int) ((obj.getParams().get(2).getValue() / 10) * 100));
//                graph_temp.setProgress((int) ((obj.getParams().get(0).getValue() / 50.0) * 100));
//
//            }
//        }
//    }


    private void updateDataTable() {
        LiveData<List<MeasurementResponse>> measurements = mMeasurementViewModel.getAllMeasurements();

        // Parametrizar valores maximos

        if (measurements != null) {
            if (!measurements.getValue().isEmpty()) {
                List<MeasurementResponse> objs = measurements.getValue();
                table_data.removeAllViews();
                TableRow rowHead = (TableRow) LayoutInflater.from(this).inflate(R.layout.head_row, null);
                table_data.addView(rowHead);

                for (int i = 0; i < objs.size(); i++) {
                    MeasurementResponse measurement = objs.get(i);


                    for (int k = 0; k < poolsList.size(); k++) {

                        Pool pool = poolsList.get(k);

                        if (pool.numeric_code.equals(measurement.getPoolId().toString())) {
                            //[temp,ox,ph]
                            //ox,ph,temp
                            // Log.d("TAG"+k, "PoolMeasurement = " + pool.numeric_code + ":" + pool.name + ":" + measurement.getParams().get(1).getValue() + ":" + measurement.getParams().get(2).getValue() + ":" + measurement.getParams().get(0).getValue());


                            addRowData(pool, measurement);


                        }


                    }

                }


            }
        }
    }

    private Observer<List<MeasurementResponse>> getObserver() {

        return new Observer<List<MeasurementResponse>>() {
            @Override
            public void onChanged(@Nullable List<MeasurementResponse> measurements) {
                String toastMsg = getResources().getString(R.string.sync_measurements_error);

                if (measurements != null) {
                    if (!measurements.isEmpty()) {

                        toastMsg = getResources().getString(R.string.sync_measurements_success)
                                + " Total: " + measurements.size();

                        // updateGraphs();
                        updateDataTable();
                    } else {
                        toastMsg = getResources().getString(R.string.sync_measurements_empty);
                    }
                }

                Toast.makeText(
                        getApplicationContext(),
                        toastMsg,
                        Toast.LENGTH_SHORT).show();
            }
        };
    }

    private void addRowData(Pool piscina, MeasurementResponse measurement) {

        List<String> args = new ArrayList<String>();
        args.add(piscina.name);
        args.add(measurement.getParams().get(1).getValue().toString());
        args.add(measurement.getParams().get(2).getValue().toString());
        args.add(measurement.getParams().get(0).getValue().toString());


//[temp,ox,ph]
        //ox,ph,temp
        // Inflate your row "template" and fill out the fields.
        TableRow row = (TableRow) LayoutInflater.from(this).inflate(R.layout.data_row, null);

        ((TextView) row.findViewById(R.id.txt_PiscinaName)).setText(args.get(0));
        ((TextView) row.findViewById(R.id.txt_Ox)).setText(args.get(1) + " mg/l");
        ((TextView) row.findViewById(R.id.txt_Ph)).setText(args.get(2));
        ((TextView) row.findViewById(R.id.txt_Temp)).setText(args.get(3) + " °C");

        if (!measurement.getParams().get(1).getAlert().equals("OK")) {
            ((TextView) row.findViewById(R.id.txt_Ox)).setBackgroundColor(ContextCompat.getColor(this, R.color.alert));
        }

        if (!measurement.getParams().get(2).getAlert().equals("OK")) {
            ((TextView) row.findViewById(R.id.txt_Ph)).setBackgroundColor(ContextCompat.getColor(this, R.color.alert));
        }

        if (!measurement.getParams().get(0).getAlert().equals("OK")) {
            ((TextView) row.findViewById(R.id.txt_Temp)).setBackgroundColor(ContextCompat.getColor(this, R.color.alert));
        }


        row.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DataDetail.class);
                intent.putExtra("name", args.get(0));
                intent.putExtra("id", 1);

                String[] ox = {measurement.getParams().get(1).getValue().toString(), measurement.getParams().get(1).getAlert()};
                intent.putExtra("ox", ox);

                String[] ph = {measurement.getParams().get(2).getValue().toString(), measurement.getParams().get(2).getAlert()};
                intent.putExtra("ph", ph);

                String[] temp = {measurement.getParams().get(0).getValue().toString(), measurement.getParams().get(0).getAlert()};
                intent.putExtra("temp", temp);

                intent.putExtra("date", measurement.getCreatedAt());
                startActivity(intent);

            }
        });

        table_data.addView(row);
    }
}