package com.paipayales.app.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.paipayales.app.db.entity.Device;
import com.paipayales.app.db.entity.Measurement;
import com.paipayales.app.db.entity.Pool;
import com.paipayales.app.db.entity.PoolCoordinates;
import com.paipayales.app.db.dao.PoolDao;
import com.paipayales.app.db.entity.Sensor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Pool.class,
        PoolCoordinates.class,
        Sensor.class,
        Device.class,
        Measurement.class,},
        version = 1, exportSchema = false)
public abstract class AppRoomDatabase extends RoomDatabase {

    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    private static volatile AppRoomDatabase INSTANCE;

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                PoolDao dao = INSTANCE.poolDao();
                dao.deleteAll();

                // Data de ejemplo
                Pool pool = new Pool("Piscina A", "10000");
                dao.insert(pool);
                pool = new Pool("Piscina B", "20000");
                dao.insert(pool);
            });
        }
    };

    public static AppRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppRoomDatabase.class, "app_database")
                            //.addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract PoolDao poolDao();

}
