package fr.michot.projet_android.repository;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import fr.michot.projet_android.MainActivity;
import fr.michot.projet_android.api.SportmonksApi;
import fr.michot.projet_android.api.SportmonksClient;
import fr.michot.projet_android.db.PlayerDAO;
import fr.michot.projet_android.db.PlayerRoomDatabase;
import fr.michot.projet_android.model.Player;
import fr.michot.projet_android.model.PlayerRoom;
import fr.michot.projet_android.model.Players;
import fr.michot.projet_android.model.Teams;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlayerRepository {

    private final PlayerDAO playerDao;
    private final SportmonksApi sportmonksApi;
    private LiveData<List<PlayerRoom>> listPlayersRoom;
    private final LiveData<List<PlayerRoom>> favoritePlayersRoom;
    private final MutableLiveData<Players> playerListResponse = new MutableLiveData<>();
    private final MutableLiveData<Teams> teamsListResponse = new MutableLiveData<>();
    private final long FRESH_TIMEOUT = TimeUnit.DAYS.toMillis(1);

    public PlayerRepository(Application application) {
        PlayerRoomDatabase db = PlayerRoomDatabase.getDatabase(application);
        playerDao = db.playerDAO();
        sportmonksApi = SportmonksClient.createService(SportmonksApi.class);
        favoritePlayersRoom = playerDao.getFavoritePlayers();
        int countFav = playerDao.getCountFavoritePlayers();
        MainActivity.setFavorites(countFav);
        listPlayersRoom = playerDao.getAlphabetizedPlayers();
        getPlayers();
    }

    public LiveData<List<PlayerRoom>> getListPlayersRoom() {
        return listPlayersRoom;
    }

    public LiveData<List<PlayerRoom>> getFavoritePlayersRoom() {
        return favoritePlayersRoom;
    }

    public void insert(PlayerRoom player) {
        PlayerRoomDatabase.databaseWriteExecutor.execute(() -> {
            playerDao.update(player);
        });
    }

    public void remove(PlayerRoom player) {
        PlayerRoomDatabase.databaseWriteExecutor.execute(() -> {
            playerDao.update(player);
        });
    }

    public void update(PlayerRoom player) {
        PlayerRoomDatabase.databaseWriteExecutor.execute(() -> {
            playerDao.update(player);
        });
    }

    public void delete(PlayerRoom player) {
        PlayerRoomDatabase.databaseWriteExecutor.execute(() -> {
            playerDao.delete(player);
        });
    }

    public void updatePlayer(int id){
        PlayerRoomDatabase.databaseWriteExecutor.execute(() -> {
            playerDao.updatePlayer(id);
        });
    }

    public void getPlayers(){
        sportmonksApi.getPlayers(MainActivity.getCurrentFlag(), "DMHz5yp62GdCQzCw6XYLTDsBo1Z2bwyLhkHr85HNYpYvAiYmKmJcEegjhvbn")
                .enqueue(new Callback<Players>() {
            @Override
            public void onResponse(@NonNull Call<Players> call, @NonNull Response<Players> response) {
                if (response.isSuccessful()) {
                    playerListResponse.setValue(response.body());
                    SportmonksAPItoPlayerRoom(playerListResponse.getValue());
                    listPlayersRoom = playerDao.getAlphabetizedPlayers();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Players> call, @NonNull Throwable t) {
                playerListResponse.setValue(null);
            }
        });
    }

    public void SportmonksAPItoPlayerRoom(Players players) {
        List<PlayerRoom> dbPlayers = new ArrayList<>();
        for(Player player : players.getPlayers()) {

            // Récupération des clubs des joueurs via une requete à l'API
            // Suite aux nombres important de requete pour recupérer les clubs des joueurs nous avons décider de retirer cette fonctionnalité pour éviter de bloquer l'API.
            //if (player.getTeamId() != null) player.setTeamName(getTeamNameByID(player.getTeamId()));

            dbPlayers.add(new PlayerRoom(player));
        }
        PlayerRoomDatabase.databaseWriteExecutor.execute(() -> {
            playerDao.insertAll(dbPlayers);
        });
    }

    public String getTeamNameByID(int id) {
        final boolean[] requestAPI = {false};

        sportmonksApi.getTeam(id, "DMHz5yp62GdCQzCw6XYLTDsBo1Z2bwyLhkHr85HNYpYvAiYmKmJcEegjhvbn")
                .enqueue(new Callback<Teams>() {
                    @Override
                    public void onResponse(@NonNull Call<Teams> call, @NonNull Response<Teams> response) {
                        requestAPI[0] = true;
                        if (response.isSuccessful()) {
                            teamsListResponse.setValue(response.body());
                        }else{
                            teamsListResponse.getValue().getTeam().setName("Aucun club");
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Teams> call, @NonNull Throwable t) {
                        teamsListResponse.setValue(null);
                    }
                });

        if (requestAPI[0]) {
            return teamsListResponse.getValue().getTeam().getName();
        } else {
            return "Aucun club";
        }
    }

    public void clearlistPlayersRoom(){
        playerDao.deleteAll();
    }

}
