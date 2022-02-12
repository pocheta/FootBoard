package fr.michot.projet_android.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import fr.michot.projet_android.model.CountryRoom;
import fr.michot.projet_android.repository.CountryRepository;

public class CountryViewModel extends ViewModel {

    private final CountryRepository countryRepository;
    private final LiveData<List<CountryRoom>> countryList;

    public CountryViewModel(CountryRepository cr) {
        countryRepository = cr;
        countryList = countryRepository.getListCountryRoom();
    }

    public LiveData<List<CountryRoom>> getCountryList() { return countryList; }

    public void insert(CountryRoom country) {
        countryRepository.insert(country);
    }

    public void delete(CountryRoom country) {
        countryRepository.delete(country);
    }

}
