package com.paipayales.app;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.google.android.material.textfield.TextInputEditText;

public class DeviceActivity extends AppCompatActivity {

    private Button mBtnSavePreferences;
    private TextInputEditText mTxtHostUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Dispositivo");
        }

        mBtnSavePreferences = findViewById(R.id.btn_save_device_preferences);
        mTxtHostUrl = findViewById(R.id.txt_host_url);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        mTxtHostUrl.setText(preferences.getString("hostUrl", "http://192.168.1.1/v1/"));

        addSaveButtonOnClickListener();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    private void addSaveButtonOnClickListener() {
        mBtnSavePreferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String toastMsg = "Ocurrio un error";

                /* Si no termina en '/' -> concatenar '/' al final. Retrofit lo requiere o salta
                una excepcion al intentar instanciar el Servicio con esa URL */
                String hostUrl = mTxtHostUrl.getText().toString().trim();

                if (!hostUrl.isEmpty()){

                    // Obtener el ultimo caracter
                    String lastChar = hostUrl.substring(hostUrl.length() - 1);

                    hostUrl = lastChar.equalsIgnoreCase("/") ? hostUrl: hostUrl + "/";

                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = preferences.edit();

                    editor.putString("hostUrl", hostUrl);
                    editor.commit();

                    mTxtHostUrl.clearFocus();

                    toastMsg = "Configuracion Registrada";

                } else {
                    toastMsg = "Ingrese una URL";
                }

                Toast.makeText(
                        getApplicationContext(),
                        toastMsg,
                        Toast.LENGTH_LONG).show();
            }
        });

    }
}