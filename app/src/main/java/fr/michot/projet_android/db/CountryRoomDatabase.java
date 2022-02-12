package fr.michot.projet_android.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import fr.michot.projet_android.model.CountryRoom;

@Database(entities = {CountryRoom.class}, version = 1, exportSchema = false)
public abstract class CountryRoomDatabase  extends RoomDatabase {

    public abstract CountryDAO countryDAO();

    private static volatile CountryRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static CountryRoomDatabase getDatabase(final Context context) {
        if(INSTANCE == null) {
            synchronized (CountryRoomDatabase.class) {
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), CountryRoomDatabase.class, "country_database").build();
                }
            }
        }

        return INSTANCE;
    }
}
