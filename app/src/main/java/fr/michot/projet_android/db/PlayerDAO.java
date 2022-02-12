package fr.michot.projet_android.db;

import                      androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import fr.michot.projet_android.model.PlayerRoom;

@Dao
public interface PlayerDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(PlayerRoom playerRoom);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(List<PlayerRoom> playerRoom);

    @Update
    void update(PlayerRoom playerRoom);

    @Query("DELETE FROM player_table WHERE player_id = :id AND favorite")
    void updatePlayer(int id);

    @Delete
    void delete(PlayerRoom playerRoom);

    @Query("DELETE FROM player_table WHERE NOT favorite")
    void deleteAll();

    @Query("SELECT * FROM player_table ORDER BY lastname ASC")
    LiveData<List<PlayerRoom>> getAlphabetizedPlayers();

    @Query("SELECT * FROM player_table WHERE favorite ORDER BY lastname ASC")
    LiveData<List<PlayerRoom>> getFavoritePlayers();

    @Query("SELECT COUNT(*) FROM player_table WHERE favorite")
    int getCountFavoritePlayers();

}
