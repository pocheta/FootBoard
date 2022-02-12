package fr.michot.projet_android.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import fr.michot.projet_android.MainActivity;
import fr.michot.projet_android.repository.PlayerRepository;

public class PlayersViewModelFactory implements ViewModelProvider.Factory {

    private static PlayerRepository playerRepository;
    private PlayersViewModelFactory() {}
    private static final PlayersViewModelFactory INSTANCE = new PlayersViewModelFactory();

    public static PlayersViewModelFactory getInstance() {
        return INSTANCE;
    }

    public static void initFactory(Application app) {
        if(playerRepository == null || !MainActivity.currentFlag.equals(MainActivity.newFlag)) {
            if (!MainActivity.currentFlag.equals(MainActivity.newFlag)) {
                MainActivity.currentFlag = MainActivity.newFlag;
                playerRepository.clearlistPlayersRoom();
            }
            playerRepository = new PlayerRepository(app);
        }
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(PlayersViewModel.class)) {
            return (T) new PlayersViewModel(playerRepository);
        } else {
            throw new IllegalArgumentException("Uknown Class");
        }
    }
}
