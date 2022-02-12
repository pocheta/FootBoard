package fr.michot.projet_android.view;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fr.michot.projet_android.R;
import fr.michot.projet_android.adapters.CountryAdapter;
import fr.michot.projet_android.model.CountryRoom;
import fr.michot.projet_android.viewmodel.CountryViewModel;
import fr.michot.projet_android.viewmodel.CountryViewModelFactory;

public class CountryFragment extends Fragment {

    private CountryAdapter adapter;
    private CountryViewModel viewModel;
    private List<CountryRoom> countries = new ArrayList<>();

    public CountryFragment() {}

    public static CountryFragment newInstance() {
        return new CountryFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_country, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CountryViewModelFactory factory = CountryViewModelFactory.getInstance();
        CountryViewModelFactory.initFactory(getActivity().getApplication());
        viewModel = new ViewModelProvider(requireActivity(), factory).get(CountryViewModel.class);

        initRecyclerView();
        observeData();
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = getView().findViewById(R.id.recyclerViewCountry);
        RecyclerView.LayoutManager layoutManager;

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            layoutManager = new GridLayoutManager(getActivity(),6 );
        else
            layoutManager = new GridLayoutManager(getActivity(),3);

        adapter = new CountryAdapter(countries);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(true);
//        layoutManager.
    }

    private void observeData() {
        viewModel.getCountryList().observe(getViewLifecycleOwner(), list -> {
            this.countries = new ArrayList<>(list);
            adapter.updateCountries(countries);
            if(list.size()!=0){
                getView().findViewById(R.id.CountryProgressBar).setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

}
