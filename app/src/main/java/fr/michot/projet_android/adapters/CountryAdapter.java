package fr.michot.projet_android.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
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
import fr.michot.projet_android.model.CountryRoom;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder> implements Filterable {

    private Context context;
    private List<CountryRoom> countries;

    public CountryAdapter(List<CountryRoom> countries) {
        this.countries = countries;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateCountries(List<CountryRoom> c) {
        countries = c;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CountryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.country_grid_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryAdapter.ViewHolder holder, int position) {
        holder.display(countries.get(position));
    }

    @Override
    public int getItemCount() {
        return countries == null ? 0 : countries.size();
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CountryRoom country;
        public final TextView countryNameTextView;
        public final ImageView countryFlagImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.countryNameTextView = itemView.findViewById(R.id.CountryNameTextView);
            this.countryFlagImage = itemView.findViewById(R.id.CountryFlagImageView);

            itemView.setOnClickListener(v -> {
                MainActivity.setCurrentFlag(this.country.getId().toString());
                MainActivity.goback(context);
            });

        }

        public void display(CountryRoom country) {
            this.country = country;
            countryNameTextView.setText(country.getName());
            if (country.getImagePath() != null && !country.getImagePath().isEmpty()) {
                Picasso.get().load(country.getImagePath()).into(this.countryFlagImage);
            }
        }
    }
}