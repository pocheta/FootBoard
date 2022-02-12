package fr.michot.projet_android.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;


import fr.michot.projet_android.MainActivity;
import fr.michot.projet_android.R;
import fr.michot.projet_android.api.SportmonksApi;
import fr.michot.projet_android.model.PlayerRoom;
import fr.michot.projet_android.view.DetailsPlayerView;
import fr.michot.projet_android.viewmodel.PlayersViewModel;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {

    private final PlayersViewModel viewModel;
    private List<PlayerRoom> favoriteList;

    public FavoritesAdapter(PlayersViewModel viewModel, List<PlayerRoom> list) {
        this.viewModel = viewModel;
        this.favoriteList = list;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateFavorite(List<PlayerRoom> list) {
        favoriteList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavoritesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favoriteplayer_item, parent, false);
        return new FavoritesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesAdapter.ViewHolder holder, int position) {
        holder.display(favoriteList.get(position));
    }

    @Override
    public int getItemCount() {
        return favoriteList.size();
    }

    public void deleteItem(int position) {
        favoriteList.get(position).setFavorite(false);
        if (!MainActivity.currentFlag.equals(favoriteList.get(position).getCountryId().toString())){
            viewModel.delete(favoriteList.get(position));
        }else{
            viewModel.remove(favoriteList.get(position));
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private PlayerRoom player;
        public final TextView nameTextView;
        public final TextView clubNameTextView;
        public final TextView nationalityTextView;
        public final TextView descriptionTextView;
        public final ImageView favoriteImage;
        public final ImageView playerImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.nameTextView = itemView.findViewById(R.id.nameTextView);
            this.clubNameTextView = itemView.findViewById(R.id.clubNameTextView);
            this.nationalityTextView = itemView.findViewById(R.id.nationalityTextView);
            this.descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            this.favoriteImage = itemView.findViewById(R.id.favoriteImage);
            this.playerImage = itemView.findViewById(R.id.playerImage);
            this.favoriteImage.setOnClickListener(v -> {
                player.setFavorite(!player.isFavorite());

                if (player.isFavorite()) {
                    MainActivity.incrementFavorites();
                    viewModel.insert(player);
                } else {
                    MainActivity.decrementFavorites();
                    if (!MainActivity.currentFlag.equals(player.getCountryId().toString())){
                        viewModel.delete(player);
                    }else{
                        viewModel.remove(player);
                    }
                }
            });

            itemView.setOnClickListener(v -> {
                Context context = v.getContext();
                Intent intent = new Intent(context, DetailsPlayerView.class);
                intent.putExtra("player", player);
                context.startActivity(intent);
            });
        }

        @SuppressLint("SetTextI18n")
        public void display(PlayerRoom player) {
            this.player = player;
            nameTextView.setText(player.getLastname() + " " + player.getFirstname());
            // Suite aux nombres important de requete pour recupérer les clubs des joueurs nous avons décider de retirer cette fonctionnalité pour éviter de bloquer l'API.
            //clubNameTextView.setText("Club : " + player.getTeamName() + " ");
            clubNameTextView.setText("Club : " + player.getTeamId() + " ");
            nationalityTextView.setText("Nationalité : " + player.getNationality());
            descriptionTextView.setText("Date de naissance : " + player.getBirthdate());
            if(player.getImagePath() != null && !player.getImagePath().isEmpty()){
                Picasso.get().load(player.getImagePath()).into(this.playerImage);
            } else {
                this.playerImage.setImageResource(R.drawable.ic_search_24dp);
            }
            if(player.isFavorite())
                favoriteImage.setImageResource(R.drawable.ic_lover);
            else
                favoriteImage.setImageResource(R.drawable.ic_like);
        }
    }
}
