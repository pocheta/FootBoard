package fr.michot.projet_android.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import fr.michot.projet_android.model.CountryRoom;

@Dao
public interface CountryDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(CountryRoom countryRoom);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(List<CountryRoom> countryRooms);

    @Update
    void update(CountryRoom countryRoom);

    @Delete
    void delete(CountryRoom countryRoom);

    @Query("SELECT * FROM country_table ORDER BY name ASC")
    LiveData<List<CountryRoom>> getAlphabetizedCountry();
}
