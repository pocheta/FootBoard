package fr.michot.projet_android.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import fr.michot.projet_android.MainActivity;
import fr.michot.projet_android.R;
import fr.michot.projet_android.api.SportmonksApi;
import fr.michot.projet_android.model.PlayerRoom;
import fr.michot.projet_android.view.DetailsPlayerView;
import fr.michot.projet_android.viewmodel.PlayersViewModel;

public class SearchPlayerAdapter extends RecyclerView.Adapter<SearchPlayerAdapter.ViewHolder> implements Filterable {

    private List<PlayerRoom> players;
    private List<PlayerRoom> playerListFull;
    private final PlayersViewModel viewModel;

    public SearchPlayerAdapter(PlayersViewModel viewModel, List<PlayerRoom> players) {
        this.viewModel = viewModel;
        this.players = players;
        this.playerListFull = new ArrayList<>(players);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updatePlayers(List<PlayerRoom> p) {
        playerListFull = new ArrayList<>(p);
        players = p;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.searchplayer_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchPlayerAdapter.ViewHolder holder, int position) {
        holder.display(players.get(position));
    }

    @Override
    public int getItemCount() {
        return players == null ? 0 : players.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                List<PlayerRoom> filteredList = new ArrayList<>();
                String charString = charSequence.toString().toLowerCase();
                if (charString.isEmpty()) {
                    filteredList.addAll(playerListFull);
                } else {
                    for (PlayerRoom b : playerListFull) {
                        if(b.getFullname().toLowerCase().trim().contains(charString)) {
                            filteredList.add(b);
                        }
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                players.clear();
                players.addAll((List) filterResults.values);
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private PlayerRoom player;
        public final TextView nameTextView;
        public final TextView clubNameTextView;
        public final TextView nationalityTextView;
        public final ImageView favoriteImage;
        public final ImageView playerImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.nameTextView = itemView.findViewById(R.id.nameTextView);
            this.clubNameTextView = itemView.findViewById(R.id.clubNameTextView);
            this.nationalityTextView = itemView.findViewById(R.id.nationalityTextView);
            this.favoriteImage = itemView.findViewById(R.id.favoriteImage);
            this.playerImage = itemView.findViewById(R.id.playerImage);
            this.favoriteImage.setOnClickListener(v -> {
                player.setFavorite(!player.isFavorite());

                if (player.isFavorite()) {
                    MainActivity.incrementFavorites();
                    viewModel.insert(player);
                } else {
                    MainActivity.decrementFavorites();
                    if (!MainActivity.currentFlag.equals(this.player.getCountryId().toString())){
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

        public void display(PlayerRoom player) {
            this.player = player;
            nameTextView.setText(player.getLastname() + " " + player.getFirstname());
            // Suite aux nombres important de requete pour recupérer les clubs des joueurs nous avons décider de retirer cette fonctionnalité pour éviter de bloquer l'API.
            //clubNameTextView.setText("Club : " + player.getTeamName() + " ");
            clubNameTextView.setText("Club : " + player.getTeamId() + " ");
            nationalityTextView.setText("Nationalité : " + player.getNationality());
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
