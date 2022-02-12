package fr.michot.projet_android.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Players {
    @SerializedName("data")
    @Expose
    private final List<Player> players = null;

    public List<Player> getPlayers() {
        return players;
    }

}
