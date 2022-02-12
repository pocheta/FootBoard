package fr.michot.projet_android.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import fr.michot.projet_android.model.PlayerRoom;

@Database(entities = {PlayerRoom.class}, version = 1, exportSchema = false)
public abstract class PlayerRoomDatabase extends RoomDatabase {

    public abstract PlayerDAO playerDAO();

    private static volatile PlayerRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static PlayerRoomDatabase getDatabase(final Context context) {
        if(INSTANCE == null) {
            synchronized (PlayerRoomDatabase.class) {
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), PlayerRoomDatabase.class, "player_database").allowMainThreadQueries().build();
                }
            }
        }

        return INSTANCE;
    }
}
