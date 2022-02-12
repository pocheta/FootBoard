package fr.michot.projet_android.api;

import fr.michot.projet_android.model.Countries;
import fr.michot.projet_android.model.Players;
import fr.michot.projet_android.model.Teams;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SportmonksApi {

    @GET("countries/{country_id}/players")
    Call<Players> getPlayers(@Path("country_id") String countryId, @Query("api_token") String apiToken);

    @GET("teams/{team_id}")
    Call<Teams> getTeam(@Path("team_id") Integer teamId, @Query("api_token") String apiToken);

    @GET("countries")
    Call<Countries> getCountry(@Query("api_token") String apiToken);
}
