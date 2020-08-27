package com.shaddicard.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.shaddicard.R;
import com.shaddicard.adapter.CandidateAdapter;
import com.shaddicard.model.MasterData;
import com.shaddicard.model.Results;
import com.shaddicard.repositories.network.ApiManager;
import com.shaddicard.repositories.database.DatabaseClient;
import com.shaddicard.viewmodel.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    private MainActivityViewModel mMainActivityViewModel;
    RecyclerView rv_candidate;
    ArrayList<MasterData> masterList = new ArrayList<>();
    CandidateAdapter candidateAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv_candidate = findViewById(R.id.rv_candidate);

        mMainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        mMainActivityViewModel.init(getApplicationContext());


        mMainActivityViewModel.getMasterData().observe(this, new Observer<List<MasterData>>() {
            @Override
            public void onChanged(@Nullable List<MasterData> data) {
                masterList.addAll(data);
                candidateAdapter.notifyDataSetChanged();
            }
        });
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        if (candidateAdapter == null) {
            candidateAdapter = new CandidateAdapter(MainActivity.this, masterList);
            rv_candidate.setLayoutManager(new LinearLayoutManager(this));
            rv_candidate.setAdapter(candidateAdapter);
            rv_candidate.setItemAnimator(new DefaultItemAnimator());
            rv_candidate.setNestedScrollingEnabled(true);
        } else {
            candidateAdapter.notifyDataSetChanged();
        }
    }

}
