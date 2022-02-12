package fr.michot.projet_android.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Countries {

    @SerializedName("data")
    @Expose
    private final List<Country> countries = null;

    public List<Country> getCountries() {
        return countries;
    }
}
