package fr.michot.projet_android.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Teams {

    @SerializedName("data")
    @Expose
    private final Team team = null;

    public Team getTeam() {
        return team;
    }
}
