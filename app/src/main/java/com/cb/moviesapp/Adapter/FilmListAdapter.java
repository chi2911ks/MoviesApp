package com.cb.moviesapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cb.moviesapp.Activity.DetailActivity;
import com.cb.moviesapp.Domain.Datum;
import com.cb.moviesapp.Domain.ListFilm;
import com.cb.moviesapp.R;

public class FilmListAdapter extends RecyclerView.Adapter<FilmListAdapter.ViewHolder> {
    ListFilm items;
    Context context;

    public FilmListAdapter(ListFilm items) {
        this.items = items;
    }

    @NonNull
    @Override
    public FilmListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View inflate = LayoutInflater.from(context).inflate(R.layout.viewholder_film, parent, false);
        return new FilmListAdapter.ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull FilmListAdapter.ViewHolder holder, int position) {
        Datum data = items.getData().get(position);
        holder.titleTxt.setText(data.getTitle());
        holder.scoreTxt.setText(data.getImdbRating());
        Glide.with(context).load(data.getPoster()).into(holder.pic);
        holder.itemView.setOnClickListener(v->{
            Intent deltailItent = new Intent(context, DetailActivity.class);
            deltailItent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            deltailItent.putExtra("id", data.getId());
            context.startActivity(deltailItent);
        });
    }

    @Override
    public int getItemCount() {
        return items.getData().size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView titleTxt, scoreTxt;
        ImageView pic;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTxt = itemView.findViewById(R.id.titleTxt);
            scoreTxt = itemView.findViewById(R.id.scoreTxt);
            pic = itemView.findViewById(R.id.pic);
        }
    }
}
