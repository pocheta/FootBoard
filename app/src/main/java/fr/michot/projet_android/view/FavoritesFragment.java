package fr.michot.projet_android.view;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fr.michot.projet_android.R;
import fr.michot.projet_android.adapters.FavoritesAdapter;
import fr.michot.projet_android.model.PlayerRoom;
import fr.michot.projet_android.viewmodel.PlayersViewModel;
import fr.michot.projet_android.viewmodel.PlayersViewModelFactory;

public class FavoritesFragment extends Fragment {
    private static List<PlayerRoom> favoriteList =  new ArrayList<>();
    private TextView playerFavoritetextView;
    private PlayersViewModel viewModel;
    private FavoritesAdapter adapter;


    public FavoritesFragment() {}

    public static FavoritesFragment newInstance() {
        return new FavoritesFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        PlayersViewModelFactory factory = PlayersViewModelFactory.getInstance();
        viewModel = new ViewModelProvider(requireActivity(), factory).get(PlayersViewModel.class);

        RecyclerView recyclerView = getView().findViewById(R.id.recyclerViewFavorite);
        RecyclerView.LayoutManager layoutManager;

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            layoutManager = new GridLayoutManager(getActivity(), 2);
        }else {
            layoutManager = new LinearLayoutManager(getActivity());
        }

        adapter = new FavoritesAdapter(viewModel, favoriteList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        playerFavoritetextView = getView().findViewById(R.id.playerFavoritetextView);
        updateFavoriteList();

    }

    private void updateFavoriteList() {
        viewModel.getFavoriteList().observe(getViewLifecycleOwner(), list -> {
            if (list.size() == 0){
                playerFavoritetextView.setVisibility(View.VISIBLE);
            }else{
                playerFavoritetextView.setVisibility(View.INVISIBLE);
            }
            favoriteList = list;
            adapter.updateFavorite(favoriteList);
        });
    }

    public void clearList() {
        for (int i = 0; i < favoriteList.size(); i++) {
            adapter.deleteItem(i);
        }
        updateFavoriteList();
    }
}
