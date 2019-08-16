package com.deepak.task.ui;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.deepak.task.utils.ApiResponse;
import com.deepak.task.utils.Repository;
import com.deepak.task.utils.ViewModelFactory;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by deepakpurohit on 15,August,2019
 */
public class MovieListViewModel extends ViewModel {
    private Repository repository;
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<ApiResponse> responseLiveData = new MutableLiveData<>();
    @Inject
    ViewModelFactory viewModelFactory;

    public MovieListViewModel(Repository repository) {
        this.repository = repository;
    }

    public MutableLiveData<ApiResponse> moviesresponse() {
        return responseLiveData;
    }

    public void getMovies() {

        disposables.add(repository.getMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> responseLiveData.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> responseLiveData.setValue(ApiResponse.success(result)),
                        throwable -> responseLiveData.setValue(ApiResponse.error(throwable))
                ));

    }

    @Override
    protected void onCleared() {
        disposables.clear();
    }
}