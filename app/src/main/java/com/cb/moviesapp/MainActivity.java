package com.cb.moviesapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cb.moviesapp.Adapter.FilmListAdapter;
import com.cb.moviesapp.Domain.FilmItem;
import com.cb.moviesapp.Domain.ListFilm;
import com.cb.moviesapp.databinding.ActivityMainBinding;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {
    RecyclerView.Adapter adapterNewMovies;
    RecyclerView.Adapter adapterUpcomming;
    RequestQueue mRequestQueue;
    StringRequest mStringRequest, mStringRequest2;
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
        sendRequest();
        sendRequest2();
    }
    private void initView(){
        binding.rcNewMovies.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.rcUpcommingMovies.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }
    private void sendRequest(){
        mRequestQueue = Volley.newRequestQueue(this);
        binding.loading1.setVisibility(View.VISIBLE);
        mStringRequest = new StringRequest(Request.Method.GET, "https://moviesapi.ir/api/v1/movies?page=1",
                s -> {
                    Gson gson = new Gson();
                    binding.loading1.setVisibility(View.GONE);
                    ListFilm items = gson.fromJson(s, ListFilm.class);
                    adapterNewMovies = new FilmListAdapter(items);
                    binding.rcNewMovies.setAdapter(adapterNewMovies);
                },
                volleyError -> {
                    Log.d("sendRequests", volleyError.toString());
                    binding.loading1.setVisibility(View.GONE);
        });
        mStringRequest.setRetryPolicy(new DefaultRetryPolicy(
                6000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        );
        mRequestQueue.add(mStringRequest);

    }
    private void sendRequest2(){
        mRequestQueue = Volley.newRequestQueue(this);
        binding.loading2.setVisibility(View.VISIBLE);
        mStringRequest2 = new StringRequest(Request.Method.GET, "https://moviesapi.ir/api/v1/movies?page=3",
                s -> {
                    Gson gson = new Gson();
                    binding.loading2.setVisibility(View.GONE);
                    ListFilm items = gson.fromJson(s, ListFilm.class);
                    adapterUpcomming = new FilmListAdapter(items);
                    binding.rcUpcommingMovies.setAdapter(adapterUpcomming);
                },
                volleyError -> {
                    binding.loading2.setVisibility(View.GONE);
                });
        mRequestQueue.add(mStringRequest2);


    }
}