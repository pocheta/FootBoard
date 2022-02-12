package fr.michot.projet_android.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import fr.michot.projet_android.model.PlayerRoom;
import fr.michot.projet_android.repository.PlayerRepository;

public class PlayersViewModel extends ViewModel {

    private final PlayerRepository playerRepository;
    private final LiveData<List<PlayerRoom>> playerList;
    public final LiveData<List<PlayerRoom>> favoriteList;

    public PlayersViewModel(PlayerRepository pr) {
        playerRepository = pr;
        playerList = playerRepository.getListPlayersRoom();
        favoriteList = playerRepository.getFavoritePlayersRoom();
    }

    public LiveData<List<PlayerRoom>> getPlayerList() { return playerList; }

    public LiveData<List<PlayerRoom>> getFavoriteList() {
        return favoriteList; }

    public void insert(PlayerRoom player) {
        playerRepository.insert(player);
    }

    public void remove(PlayerRoom player) {
        playerRepository.remove(player);
    }

    public void delete(PlayerRoom player) {
        playerRepository.delete(player);
    }


}
