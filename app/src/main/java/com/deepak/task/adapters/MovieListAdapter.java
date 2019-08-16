package com.deepak.task.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.deepak.task.R;
import com.deepak.task.model.MovieDate;
import com.deepak.task.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.annotations.NonNull;

/**
 * Created by deepakpurohit on 15,August,2019
 */
public class MovieListAdapter extends
        RecyclerView.Adapter<MovieListAdapter.MyViewHolder> {
    ArrayList<MovieDate> movieArrayList;
    Activity activity;

    public MovieListAdapter(Activity bookingActivity, ArrayList<MovieDate> movieArrayList) {
        this.activity = bookingActivity;
        this.movieArrayList = movieArrayList;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        MovieDate objmovie = movieArrayList.get(position);
        Utils.getPixelsFromDPsHeight(activity, 200, holder.image);
        Utils.getPixelsFromDPsTextView(activity, 13, holder.genre);
        Utils.getPixelsFromDPsTextView(activity, 14, holder.title);
        Utils.getPixelsFromDPsTextView(activity, 13, holder.year);
        holder.genre.setText(objmovie.getGenre());
        holder.title.setText(objmovie.getTitle());
        holder.year.setText(objmovie.getYear());
        Picasso.get().load(objmovie.getPoster()).error(R.drawable.noimg).into(holder.image);

    }

    @Override
    public int getItemCount() {
        return movieArrayList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image)
        AppCompatImageView image;
        @BindView(R.id.genre)
        AppCompatTextView genre;
        @BindView(R.id.title)
        AppCompatTextView title;
        @BindView(R.id.year)
        AppCompatTextView year;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
