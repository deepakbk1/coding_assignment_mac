package com.deepak.task.di;

import com.deepak.task.ui.movielist.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, UtilsModule.class})

public interface AppComponent {

    void doInjection(MainActivity mainActivity);

}
