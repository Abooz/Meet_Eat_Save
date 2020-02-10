package com.example.meeteatsave;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdsRecyclerViewAdapter extends RecyclerView.Adapter<AdsRecyclerViewAdapter.UsersAdapterVh> implements Filterable {

    private Context context;
    private SelectedUser selectedUser;

    private ArrayList<Ads> adsList;
    private ArrayList<Ads> getAdsList;

    public AdsRecyclerViewAdapter(ArrayList<Ads> ads, SelectedUser selectedUser) {
        this.adsList = ads;
        this.getAdsList = ads;
        this.selectedUser = selectedUser;
    }

    @NonNull
    @Override
    public AdsRecyclerViewAdapter.UsersAdapterVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();

        return new UsersAdapterVh(LayoutInflater.from(context).inflate(R.layout.row_users, null));
    }

    @Override
    public void onBindViewHolder(@NonNull AdsRecyclerViewAdapter.UsersAdapterVh holder, int position) {

        Ads ads = adsList.get(position);

        String foodArt = ads.getFoodArt();
        String city = ads.getCity();
        String prefix = ads.getCity().substring(0, 1);

        holder.tvUsername.setText(foodArt);
        holder.tvCity.setText(city);
        holder.tvPrefix.setText(prefix);

    }

    @Override
    public int getItemCount() {
        return adsList.size();
    }

    @Override
    public Filter getFilter() {

        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();

                if (charSequence == null | charSequence.length() == 0) {
                    filterResults.count = getAdsList.size();
                    filterResults.values = getAdsList;

                } else {
                    String searchChr = charSequence.toString().toLowerCase();

                    ArrayList<Ads> resultData = new ArrayList<>();

                    for (Ads ads : getAdsList) {
                        if (ads.getCity().toLowerCase().contains(searchChr)) {
                            resultData.add(ads);
                        }
                    }
                    filterResults.count = resultData.size();
                    filterResults.values = resultData;

                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

                adsList = (ArrayList<Ads>) filterResults.values;
                notifyDataSetChanged();

            }
        };
        return filter;
    }


    public interface SelectedUser {

        void selectedUser(Ads ads);

    }

    public class UsersAdapterVh extends RecyclerView.ViewHolder {

        private TextView tvPrefix;
        private TextView tvUsername;
        private TextView tvCity;
        private ImageView imIcon;

        public UsersAdapterVh(@NonNull View itemView) {
            super(itemView);
            tvPrefix = itemView.findViewById(R.id.prefix);
            tvUsername = itemView.findViewById(R.id.username);
            tvCity = itemView.findViewById(R.id.city);
            imIcon = itemView.findViewById(R.id.imageView);

            itemView.setOnClickListener(view -> selectedUser.selectedUser(adsList.get(getAdapterPosition())));


        }

    }

}
