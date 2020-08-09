package com.elegion.test.behancer.di.providers;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.elegion.test.behancer.data.Storage;
import com.elegion.test.behancer.data.api.BehanceApi;
import com.elegion.test.behancer.utils.BaseRefreshViewModelFactory;
import com.elegion.test.behancer.view_model.ProjectsListViewModel;

import javax.inject.Inject;
import javax.inject.Provider;

public class ProjectsListViewModelProvider implements Provider<ProjectsListViewModel> {

    private Storage mStorage;
    private BehanceApi mApi;
    private Fragment mFragment;

    @Inject
    public ProjectsListViewModelProvider(Storage storage, BehanceApi api, Fragment fragment){
        mStorage = storage;
        mApi = api;
        mFragment = fragment;
    }

    @Override
    public ProjectsListViewModel get() {
        BaseRefreshViewModelFactory factory = new BaseRefreshViewModelFactory(mStorage, mApi);
        return ViewModelProviders.of(mFragment, factory).get(ProjectsListViewModel.class);
    }
}
