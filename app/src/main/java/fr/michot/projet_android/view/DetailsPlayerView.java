package fr.michot.projet_android.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

import java.util.concurrent.atomic.AtomicBoolean;

import fr.michot.projet_android.MainActivity;
import fr.michot.projet_android.R;
import fr.michot.projet_android.model.PlayerRoom;
import fr.michot.projet_android.viewmodel.PlayersViewModel;
import fr.michot.projet_android.viewmodel.PlayersViewModelFactory;

public class DetailsPlayerView extends AppCompatActivity {

    private PlayerRoom player;
    public TextView nameTextView;
    public TextView clubNameTextView;
    public TextView nationalityTextView;
    public TextView weightTextView;
    public TextView heightTextView;
    public TextView birthplaceTextView;
    public TextView birthdateTextView;
    public TextView birthcountryTextView;
    public ImageView playerImage;
    public Button buttonFavorite;

    @SuppressLint({"NonConstantResourceId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_details);
        buttonFavorite = findViewById(R.id.buttonFavorite);

        PlayersViewModelFactory factory = PlayersViewModelFactory.getInstance();
        PlayersViewModel viewModel = new ViewModelProvider(this, factory).get(PlayersViewModel.class);

        initView();
        Intent i = getIntent();

        if(i != null){
            player = i.getParcelableExtra("player");
            setPlayerFields();
        }

        AtomicBoolean flagremove = new AtomicBoolean(false);

        buttonFavorite.setOnClickListener(view -> {
            player.setFavorite(!player.isFavorite());


            if (player.isFavorite()) {
                buttonFavorite.setText("Retirer le joueur des favoris");
                MainActivity.incrementFavorites();
                viewModel.insert(player);
                flagremove.set(false);
            } else {
                buttonFavorite.setText("Ajouter le joueur aux favoris");
                MainActivity.decrementFavorites();
                flagremove.set(true);
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavBarDetails);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.back_details) {
                super.onBackPressed();
                if (flagremove.get()) {
                    if (!MainActivity.currentFlag.equals(player.getCountryId().toString())) {
                        viewModel.delete(player);
                    } else {
                        viewModel.remove(player);
                    }
                }
            }
            return false;
        });

    }

    @SuppressLint("SetTextI18n")
    private void setPlayerFields() {
        nameTextView.setText(player.getFullname());
        // Suite aux nombres important de requete pour recupérer les clubs des joueurs nous avons décider de retirer cette fonctionnalité pour éviter de bloquer l'API.
        //clubNameTextView.setText("Club : " + player.getTeamName());
        clubNameTextView.setText("Club : " + player.getTeamId());
        nationalityTextView.setText("Nationalité : " + player.getNationality());
        weightTextView.setText("Poids : " + player.getWeight());
        heightTextView.setText("Taille : " + player.getHeight());
        birthplaceTextView.setText("Lieu de naissance : " + player.getBirthplace());
        birthdateTextView.setText("Date de naissance : " + player.getBirthdate());
        birthcountryTextView.setText("Pays de naissance : " + player.getBirthcountry());
        if (player.isFavorite()){
            buttonFavorite.setText("Retirer le joueur des favoris");
        }else{
            buttonFavorite.setText("Ajouter le joueur aux favoris");
        }
        Picasso.get().load(player.getImagePath()).into(this.playerImage);
    }

    public void initView() {
        nameTextView = findViewById(R.id.nameTextView);
        clubNameTextView = findViewById(R.id.clubNameTextView);
        nationalityTextView = findViewById(R.id.nationalityTextView);
        weightTextView = findViewById(R.id.weightTextView);
        heightTextView = findViewById(R.id.heightTextView);
        birthplaceTextView = findViewById(R.id.birthplaceTextView);
        birthdateTextView = findViewById(R.id.birthdateTextView);
        birthcountryTextView = findViewById(R.id.birthcountryTextView);
        playerImage = findViewById(R.id.playerImage);
    }
}
