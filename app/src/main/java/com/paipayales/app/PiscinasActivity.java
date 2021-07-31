package com.paipayales.app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.paipayales.app.adapter.PoolListAdapter;
import com.paipayales.app.db.entity.Pool;
import com.paipayales.app.viewmodel.PoolViewModel;

import java.util.List;

public class PiscinasActivity extends AppCompatActivity {

    private PoolViewModel mPoolViewModel;
    private FloatingActionButton fabAddPool;
    private Context context;

    public static final int NEW_POOL_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piscinas);
        context = getApplicationContext();

        fabAddPool = (FloatingActionButton) findViewById(R.id.fab_addPool);

        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Piscinas");
        }

        btns_Envents();

        // Inicializar RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final PoolListAdapter adapter = new PoolListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mPoolViewModel = new ViewModelProvider(this).get(PoolViewModel.class);

        mPoolViewModel.getAllPools().observe(this, new Observer<List<Pool>>() {
            @Override
            public void onChanged(@Nullable final List<Pool> pools) {
                adapter.setPools(pools);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_POOL_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            String name = data.getExtras().getString("EXTRA_POOL_NAME");
            String numericCode = data.getExtras().getString("EXTRA_POOL_NUMERIC_CODE");

            Pool pool = new Pool(name, numericCode);
            mPoolViewModel.insert(pool);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }

    private void btns_Envents() {
        fabAddPool.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PiscinaActivity.class);
                startActivityForResult(intent, NEW_POOL_ACTIVITY_REQUEST_CODE);
            }
        });

    }
}