package com.elegion.test.behancer.di.providers;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.elegion.test.behancer.data.Storage;
import com.elegion.test.behancer.data.api.BehanceApi;
import com.elegion.test.behancer.utils.BaseRefreshViewModelFactory;
import com.elegion.test.behancer.view_model.ProfileViewModel;

import javax.inject.Inject;
import javax.inject.Provider;

public class ProfileViewModelProvider implements Provider<ProfileViewModel> {

    private Storage mStorage;
    private BehanceApi mApi;
    private Fragment mFragment;

    @Inject
    public ProfileViewModelProvider(Storage storage, BehanceApi api, Fragment fragment){
        mStorage = storage;
        mApi = api;
        mFragment = fragment;
    }

    @Override
    public ProfileViewModel get() {
        BaseRefreshViewModelFactory factory = new BaseRefreshViewModelFactory(mStorage, mApi);
        return ViewModelProviders.of(mFragment, factory).get(ProfileViewModel.class);
    }
}