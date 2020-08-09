package com.elegion.test.behancer.di.providers;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.elegion.test.behancer.data.Storage;
import com.elegion.test.behancer.data.api.BehanceApi;
import com.elegion.test.behancer.utils.BaseRefreshViewModelFactory;
import com.elegion.test.behancer.view_model.ProfileViewModel;
import com.elegion.test.behancer.view_model.UserProjectsViewModel;

import javax.inject.Inject;
import javax.inject.Provider;

public class UserProjectsViewModelProvider implements Provider<UserProjectsViewModel> {

    private Storage mStorage;
    private BehanceApi mApi;
    private Fragment mFragment;

    @Inject
    public UserProjectsViewModelProvider(Storage storage, BehanceApi api, Fragment fragment){
        mStorage = storage;
        mApi = api;
        mFragment = fragment;
    }

    @Override
    public UserProjectsViewModel get() {
        BaseRefreshViewModelFactory factory = new BaseRefreshViewModelFactory(mStorage, mApi);
        return ViewModelProviders.of(mFragment, factory).get(UserProjectsViewModel.class);
    }
}
