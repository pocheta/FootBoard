package fr.michot.projet_android.view;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fr.michot.projet_android.R;
import fr.michot.projet_android.adapters.SearchPlayerAdapter;
import fr.michot.projet_android.model.PlayerRoom;
import fr.michot.projet_android.viewmodel.PlayersViewModel;
import fr.michot.projet_android.viewmodel.PlayersViewModelFactory;

public class SearchPlayerFragment extends Fragment {

    public static final String TEXT_IN_SEARCHBAR = "TextSearchView";

    private SearchView searchView;
    private SearchPlayerAdapter adapter;
    private PlayersViewModel viewModel;
    private List<PlayerRoom> players = new ArrayList<>();
    private String textSearchView;

    public SearchPlayerFragment() {}

    public static SearchPlayerFragment newInstance() {
        return new SearchPlayerFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_player, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        PlayersViewModelFactory factory = PlayersViewModelFactory.getInstance();
        PlayersViewModelFactory.initFactory(getActivity().getApplication());
        viewModel = new ViewModelProvider(requireActivity(), factory).get(PlayersViewModel.class);

        initRecyclerView();
        observeData();
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = getView().findViewById(R.id.recyclerViewSearch);
        searchView = getView().findViewById(R.id.searchPlayerText);
        RecyclerView.LayoutManager layoutManager;

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            layoutManager = new GridLayoutManager(getActivity(),2 );
        else
            layoutManager = new LinearLayoutManager(getActivity());

        adapter = new SearchPlayerAdapter(viewModel, players);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(true);
        searchView.setIconifiedByDefault(false);
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                textSearchView = newText;
                if(searchView.getWidth()>0)
                    adapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    private void observeData() {
        viewModel.getPlayerList().observe(getViewLifecycleOwner(), list -> {
            this.players = new ArrayList<>(list);
            adapter.updatePlayers(players);
            if(list.size()!=0){
                getView().findViewById(R.id.progressBar).setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null)
            searchView.setQuery(textSearchView, false);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(TEXT_IN_SEARCHBAR, textSearchView);
    }
}
