package com.example.sharedrent;

import android.content.Context;
import android.graphics.Movie;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharedrent.databinding.TextRowItemBinding;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.TenantViewHolder> {

//    private String[] localDataSet;
    private List<Tenant> localDataSet;
//    private View thisView;

    public static class TenantViewHolder extends RecyclerView.ViewHolder {
        // If your layout file is something_awesome.xml then your binding class will be SomethingAwesomeBinding
        // Since our layout file is item_movie.xml, our auto generated binding class is ItemMovieBinding
        private TextRowItemBinding binding;

        //Define a constructor taking a ItemMovieBinding as its parameter
        public TenantViewHolder(TextRowItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        /**
         * We will use this function to bind instance of Movie to the row
         */
        public void bind(Tenant tenant) {
            binding.setTenant(tenant);
            binding.executePendingBindings();

        }
    }


    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    public CustomAdapter(List<Tenant> dataSet) {
        localDataSet = dataSet;
    }


    @Override
    public void onBindViewHolder(@NonNull TenantViewHolder holder, int position) {
        Tenant tenant = localDataSet.get(position);
        holder.bind(tenant);
    }

    // Create new views (invoked by the layout manager)
    public TenantViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        TextRowItemBinding itemBinding = TextRowItemBinding.inflate(layoutInflater, parent, false);
        TenantViewHolder holder = new TenantViewHolder(itemBinding);


        itemBinding.editableIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tenant tenant = localDataSet.get(holder.getLayoutPosition());
                tenant.setIncomeByString(((AppCompatEditText) v).getText().toString());
                notifyDataSetChanged();
            }
        });

        itemBinding.editableArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tenant tenant = localDataSet.get(holder.getLayoutPosition());
                tenant.setLivingAreaByString(((AppCompatEditText) v).getText().toString());
                notifyDataSetChanged();
            }
        });

        return holder;
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet == null ? 0 : localDataSet.size();
    }



    public void setData(List<Tenant> data){
        if (data != null) {
            this.localDataSet = data;
        }else{
            this.localDataSet = this.localDataSet == null ? null : this.localDataSet.subList(0,0);
        }
        notifyDataSetChanged();
    }

}
