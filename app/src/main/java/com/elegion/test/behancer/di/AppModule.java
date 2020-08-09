package com.elegion.test.behancer.di;

import androidx.room.Room;

import com.elegion.test.behancer.AppDelegate;
import com.elegion.test.behancer.data.Storage;
import com.elegion.test.behancer.data.database.BehanceDatabase;

import toothpick.config.Module;

public class AppModule extends Module {

    private final AppDelegate mApp;

    public AppModule(AppDelegate app){
        mApp = app;
        bind(AppDelegate.class).toInstance(app);
        bind(Storage.class).toInstance(provideStorage());
    }

    private Storage provideStorage() {
        final BehanceDatabase database = Room.databaseBuilder(mApp, BehanceDatabase.class, "behance_database")
                .fallbackToDestructiveMigration()
                .build();
        return new Storage(database.getBehanceDao());
    }

}
