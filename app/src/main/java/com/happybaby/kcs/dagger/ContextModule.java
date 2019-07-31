package com.happybaby.kcs.dagger;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
class ContextModule {

    Context context;

    ContextModule(Context context) {
        this.context = context;
    }

    @Provides
    Context providesContext() {
        return context;
    }
}