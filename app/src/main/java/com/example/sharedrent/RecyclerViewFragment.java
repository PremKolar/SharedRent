/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.sharedrent;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharedrent.database.SharedRentViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewFragment extends Fragment {
    private static final String TAG = "RecyclerViewFragment";
    protected RecyclerView mRecyclerView;
    protected CustomAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected List<Tenant> mTenantsDataset;
    private SharedRentViewModel mSharedRentViewModel;
    private View rootView;
    private int _callbackcheck = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: 23.11.20 room database!

        mSharedRentViewModel = new ViewModelProvider(this).get(SharedRentViewModel.class);
        mSharedRentViewModel.init(getActivity().getApplication());

        final Observer<List<Flat>> flatObserver = new Observer<List<Flat>>() {
            @Override
            public void onChanged(@Nullable final List<Flat> newList) {
                mSharedRentViewModel.tryToSetCurrentFlat();
                initDataset();
            }
        };
        mSharedRentViewModel.getAllFlats.observe(this,flatObserver);

        final Observer<List<Tenant>> tenantObserver = new Observer<List<Tenant>>() {
            @Override
            public void onChanged(@Nullable final List<Tenant> newList) {
                initDataset();
            }
        };
        mSharedRentViewModel.getAllTenantsRaw.observe(this,tenantObserver);

        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.recycler_view_frag, container, false);
        rootView.setTag(TAG);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        setRecyclerViewLayoutManager();
        mAdapter = new CustomAdapter(mTenantsDataset);
        mAdapter.setSharedViewModel(mSharedRentViewModel);
        mRecyclerView.setAdapter(mAdapter);

        FloatingActionButton addTenantButton = (FloatingActionButton) rootView.findViewById(R.id.addTenantFloatingButton);
        addTenantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getNameForAndAddNewTenant();
            }
        });

        Spinner spinner = rootView.findViewById(R.id.spinnerForFlats);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String selection = parent.getItemAtPosition(position).toString();
                mSharedRentViewModel.setCurrentFlatByName(selection);
                if(++_callbackcheck > 1) {
                    initDataset();
                }
            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent){ }
        });

        EditText edit_rent = rootView.findViewById(R.id.edit_flatsRent);
        edit_rent.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String input = edit_rent.getText().toString();
                if(mSharedRentViewModel.tryToSetCurrentRentByUserInput(input)){
                    // failed
                }
//                initDataset();
            }
        });

        EditText edit_area = rootView.findViewById(R.id.edit_flatsSpace);
        edit_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = edit_area.getText().toString();
                if(mSharedRentViewModel.tryToSetCurrentLivingAreaByUserInput(input)){
                    // failed
                }
//                initDataset();
            }
        });

        return rootView;
    }

    private void getNameForAndAddNewTenant() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Name of new tenant");
        final EditText input = new EditText(getContext());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setPositiveButton("add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String nameForNewTenant = input.getText().toString();
                mSharedRentViewModel.addTenantWithName(nameForNewTenant);
//                initDataset();
            }
        });
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    public void setRecyclerViewLayoutManager() {
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    private void initDataset() {
        setReyclerView();
        setFlatRow();
    }

    private void setReyclerView() {
        mTenantsDataset = mSharedRentViewModel.makeTenantList();
        mAdapter.setData(mTenantsDataset);
    }

    private void setFlatRow() {
        if (!mSharedRentViewModel.ready()) return;
        setSpinnerToCurrentFlat();
        setFlatsRentInUI();
        setFlatsSpaceInUI();
    }

    private void setFlatsSpaceInUI() {
        EditText et = rootView.findViewById(R.id.edit_flatsSpace);
        String rent = mSharedRentViewModel.getCurrentLivingspaceFormatted();
        et.setText(rent);
    }
    private void setFlatsRentInUI() {
        EditText et = rootView.findViewById(R.id.edit_flatsRent);
        String rent = mSharedRentViewModel.getCurrentRentFormatted();
        et.setText(rent);
    }

    private void setSpinnerToCurrentFlat() {
        Spinner spinner = rootView.findViewById(R.id.spinnerForFlats);
        Flat currentFlat = mSharedRentViewModel.getCurrentFlat();
        ArrayList<String> labels = mSharedRentViewModel.getAllFlatsNames();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, labels);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        _callbackcheck = 0;
        spinner.setAdapter(dataAdapter);
        if (currentFlat == null) return;
        if (spinner.getSelectedItem() == null) return;
        if (spinner.getSelectedItem().toString().equals(currentFlat.name)) return;
        int spinnerPosition = dataAdapter.getPosition(currentFlat.name);
        spinner.setSelection(spinnerPosition);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main_frag_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_new_flat:
                addNewFlat();
            default:
                // TODO: 27.11.20
        }
        return true;
    }


    // TODO: 27.11.20 refactor!!!
    private void addNewFlat() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Name of new flat");
        final EditText input = new EditText(getContext());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setPositiveButton("add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String nameForNewFlat = input.getText().toString();
                mSharedRentViewModel.addFlatWithName(nameForNewFlat);
//                initDataset();
            }
        });
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }



}
