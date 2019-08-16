package com.deepak.task.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.deepak.task.MyApplication;
import com.deepak.task.R;
import com.deepak.task.adapters.MovieListAdapter;
import com.deepak.task.model.MovieDate;
import com.deepak.task.utils.ApiResponse;
import com.deepak.task.utils.ViewModelFactory;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.jakewharton.rxbinding3.widget.RxTextView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {
    @Inject
    ViewModelFactory viewModelFactory;
    MovieListViewModel movieListViewModel;
    @BindView(R.id.editText_search)
    AppCompatEditText editTextSearch;
    @BindView(R.id.list_movies)
    RecyclerView listMovies;
    @BindView(R.id.progress_circular)
    LinearLayoutCompat progressCircular;
    private ArrayList<MovieDate> movieList = new ArrayList<>();
    private ArrayList<MovieDate> tempArrayList = new ArrayList<>();
    Disposable search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ((MyApplication) getApplication()).getAppComponent().doInjection(this);
        movieListViewModel = ViewModelProviders.of(this, viewModelFactory).get(MovieListViewModel.class);
        movieListViewModel.moviesresponse().observe(this, this::consumeResponse);
        movieListViewModel.getMovies();
        search = RxTextView.textChanges(editTextSearch).subscribe(s -> {
            String string = s.toString();
            if (movieList.size() > 0) {
                if (s.length() > 0) {
                    tempArrayList = new ArrayList<>();
                    ArrayList<MovieDate> arrayList = movieList;
                    for (int i = 0; i < arrayList.size(); i++) {
                        try {
                            MovieDate movieObject = arrayList.get(i);
                            string = string.toLowerCase();

                            String title = movieObject.getTitle().toLowerCase();
                            String genre = movieObject.getGenre().toLowerCase();
                            if (genre.contains(string)) {
                                tempArrayList.add(movieObject);
                                continue;
                            }
                            if (title.contains(string)) {
                                tempArrayList.add(movieObject);
                            }

                        } catch (Exception e) {
                            e.getStackTrace();
                        }
                    }
                    setAdapter(tempArrayList);
                } else {
                    tempArrayList = new ArrayList<>();
                    setAdapter(movieList);
                }
            }
        });
    }

    private void setAdapter(ArrayList<MovieDate> movieList) {
        if (movieList.size() > 0) {
            listMovies.setVisibility(View.VISIBLE);

            MovieListAdapter adapter = new MovieListAdapter(MainActivity.this, movieList);
            StaggeredGridLayoutManager mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(
                    3, //number of grid columns
                    GridLayoutManager.VERTICAL);

            listMovies.setLayoutManager(mStaggeredGridLayoutManager);
            listMovies.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } else {
            listMovies.setVisibility(View.GONE);
        }

    }

    private void consumeResponse(ApiResponse apiResponse) {

        switch (apiResponse.status) {

            case LOADING:
                progressCircular.setVisibility(View.VISIBLE);
                listMovies.setVisibility(View.GONE);
                break;

            case SUCCESS:
                progressCircular.setVisibility(View.GONE);
                listMovies.setVisibility(View.VISIBLE);

                renderSuccessResponse(apiResponse.data);
                break;

            case ERROR:
                progressCircular.setVisibility(View.GONE);
                try {
                    String errorResponse = ((HttpException) apiResponse.error).response().errorBody().string();
                    JSONObject jsonObject = new JSONObject(errorResponse);
                    Toast.makeText(this, jsonObject.getString("error"), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.getStackTrace();
                }
                break;

            default:
                break;
        }
    }

    private void renderSuccessResponse(JsonElement response) {
        try {
            JsonObject jsonObject = new JsonParser().parse(response.toString()).getAsJsonObject();
            JsonArray jsonArray = jsonObject.getAsJsonArray("data");
            movieList = new Gson().fromJson(jsonArray.toString(), new TypeToken<List<MovieDate>>() {
            }.getType());
            setAdapter(movieList);

        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        search.dispose();
        super.onDestroy();
    }

}
