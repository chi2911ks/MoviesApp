package com.cb.moviesapp.Activity;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.cb.moviesapp.Adapter.ImageListAdapter;
import com.cb.moviesapp.Domain.FilmItem;
import com.cb.moviesapp.R;
import com.cb.moviesapp.databinding.ActivityDetailBinding;
import com.google.gson.Gson;

public class DetailActivity extends AppCompatActivity {
    String id;
    StringRequest mStringRequest;
    RequestQueue mRequestQueue;
    ActivityDetailBinding binding;

    RecyclerView.Adapter adapterImages;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        id = getIntent().getSerializableExtra("id").toString();
        binding.rcImagesMovie.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.backImg.setOnClickListener(v->finish());
        sendRequest();
    }
    private void sendRequest(){
        mRequestQueue = Volley.newRequestQueue(this);
        binding.detailLoading.setVisibility(View.VISIBLE);
        binding.scrollView3.setVisibility(View.GONE);
        mStringRequest = new StringRequest(Request.Method.GET, "https://moviesapi.ir/api/v1/movies/"+id,
                s -> {
                    binding.scrollView3.setVisibility(View.VISIBLE);
                    binding.detailLoading.setVisibility(View.GONE);
                    Gson gson = new Gson();
                    FilmItem item = gson.fromJson(s, FilmItem.class);
                    binding.movieNameTxt.setText(item.getTitle());
                    binding.movieTimeTxt.setText(item.getRuntime());
                    binding.movieDateTxt.setText(item.getReleased());
                    binding.movieSummaryTxt.setText(item.getPlot());
                    binding.movieActionInfo.setText(item.getActors());
                    binding.movieRateTxt.setText(item.getImdbRating());
                    Glide.with(this).load(item.getPoster()).into(binding.posterNormalIm);
                    Glide.with(this).load(item.getPoster()).into(binding.posterBigIm);
                    adapterImages = new ImageListAdapter(item.getImages());
                    binding.rcImagesMovie.setAdapter(adapterImages);
                },
                error -> {
                    binding.detailLoading.setVisibility(View.GONE);
        });
        mRequestQueue.add(mStringRequest);
    }
}