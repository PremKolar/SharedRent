package com.example.sharedrent.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharedrent.models.Tenant;
import com.example.sharedrent.database.SharedRentViewModel;
import com.example.sharedrent.databinding.TextRowItemBinding;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.TenantViewHolder> {

    private List<Tenant> localDataSet;
    private SharedRentViewModel mSharedRentViewModel;

    // i need this in the callbacks to update the tenant tables on the DB
    public void setSharedViewModel(SharedRentViewModel mSharedRentViewModel) {
        this.mSharedRentViewModel = mSharedRentViewModel;
    }

    public static class TenantViewHolder extends RecyclerView.ViewHolder {

        private TextRowItemBinding binding;

        public TenantViewHolder(TextRowItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Tenant tenant) {
            binding.setTenant(tenant);
            binding.executePendingBindings();
        }
    }

    public CustomAdapter(List<Tenant> dataSet) {
        localDataSet = dataSet;
    }

    @Override
    public void onBindViewHolder(@NonNull TenantViewHolder holder, int position) {
        Tenant tenant = localDataSet.get(position);
        holder.bind(tenant);
    }

    public TenantViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        TextRowItemBinding itemBinding = TextRowItemBinding.inflate(layoutInflater, parent, false);
        TenantViewHolder holder = new TenantViewHolder(itemBinding);

        itemBinding.editableIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tenant tenant = localDataSet.get(holder.getLayoutPosition());
                tenant.setIncomeByString(((AppCompatEditText) v).getText().toString());
                mSharedRentViewModel.updateTenant(tenant);
                mSharedRentViewModel.refresh();
                notifyDataSetChanged();
            }
        });

        itemBinding.editableArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tenant tenant = localDataSet.get(holder.getLayoutPosition());
                tenant.setLivingAreaByString(((AppCompatEditText) v).getText().toString());
                mSharedRentViewModel.updateTenant(tenant);
                mSharedRentViewModel.refresh();
                notifyDataSetChanged();
            }
        });

        return holder;
    }

    public void deleteItem(int position) {
        mSharedRentViewModel.deleteTenant(localDataSet.get(position));
        notifyItemRemoved(position);
    }

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