package fr.michot.projet_android.repository;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import fr.michot.projet_android.api.SportmonksApi;
import fr.michot.projet_android.api.SportmonksClient;
import fr.michot.projet_android.db.CountryDAO;
import fr.michot.projet_android.db.CountryRoomDatabase;
import fr.michot.projet_android.model.Countries;
import fr.michot.projet_android.model.Country;
import fr.michot.projet_android.model.CountryRoom;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CountryRepository {
    private final CountryDAO countryDAO;
    private final SportmonksApi sportmonksApi;
    private LiveData<List<CountryRoom>> listCountryRoom;
    private final MutableLiveData<Countries> countryListResponse = new MutableLiveData<>();
    private final long FRESH_TIMEOUT = TimeUnit.DAYS.toMillis(1);

    public CountryRepository(Application application) {
        CountryRoomDatabase db = CountryRoomDatabase.getDatabase(application);
        countryDAO = db.countryDAO();
        sportmonksApi = SportmonksClient.createService(SportmonksApi.class);
        listCountryRoom = countryDAO.getAlphabetizedCountry();
        getCountry();
    }

    public LiveData<List<CountryRoom>> getListCountryRoom() {
        return listCountryRoom;
    }

    public void insert(CountryRoom country) {
        CountryRoomDatabase.databaseWriteExecutor.execute(() -> {
            countryDAO.update(country);
        });
    }

    public void delete(CountryRoom country) {
        CountryRoomDatabase.databaseWriteExecutor.execute(() -> {
            countryDAO.update(country);
        });
    }

    public void getCountry(){
        sportmonksApi.getCountry("DMHz5yp62GdCQzCw6XYLTDsBo1Z2bwyLhkHr85HNYpYvAiYmKmJcEegjhvbn")
                .enqueue(new Callback<Countries>() {
                    @Override
                    public void onResponse(@NonNull Call<Countries> call, @NonNull Response<Countries> response) {
                        if (response.isSuccessful()) {
                            countryListResponse.setValue(response.body());
                            SportmonksAPItoCountryRoom(countryListResponse.getValue());
                            listCountryRoom = countryDAO.getAlphabetizedCountry();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Countries> call, @NonNull Throwable t) { countryListResponse.setValue(null); }
                });
    }

    public void SportmonksAPItoCountryRoom(Countries countrys) {
        List<CountryRoom> dbCountries = new ArrayList<>();
        for(Country country : countrys.getCountries()) {
            if (country.getImagePath() != null ) dbCountries.add(new CountryRoom(country));
        }
        CountryRoomDatabase.databaseWriteExecutor.execute(() -> {
            countryDAO.insertAll(dbCountries);
        });
    }
}

