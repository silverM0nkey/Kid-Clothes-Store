package com.happybaby.kcs.dagger;

import com.happybaby.kcs.models.interactors.ShoppingCartInteractor;

import dagger.Component;

@Component (modules={ContextModule.class})
public interface AppComponent {

    void inject(ShoppingCartInteractor interactor);
}