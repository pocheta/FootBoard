package fr.michot.projet_android.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import fr.michot.projet_android.repository.CountryRepository;

public class CountryViewModelFactory implements ViewModelProvider.Factory {

    private static CountryRepository countryRepository;
    private CountryViewModelFactory() {}
    private static final CountryViewModelFactory INSTANCE = new CountryViewModelFactory();

    public static CountryViewModelFactory getInstance() {
        return INSTANCE;
    }

    public static void initFactory(Application app) {
        if(countryRepository == null) {
            countryRepository = new CountryRepository(app);
        }
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(CountryViewModel.class)) {
            return (T) new CountryViewModel(countryRepository);
        } else {
            throw new IllegalArgumentException("Uknown Class");
        }
    }
}
