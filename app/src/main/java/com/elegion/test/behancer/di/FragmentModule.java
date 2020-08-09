package com.elegion.test.behancer.di;

import androidx.fragment.app.Fragment;

import com.elegion.test.behancer.di.providers.ProfileViewModelProvider;
import com.elegion.test.behancer.di.providers.ProjectsListViewModelProvider;
import com.elegion.test.behancer.di.providers.UserProjectsViewModelProvider;
import com.elegion.test.behancer.view_model.ProfileViewModel;
import com.elegion.test.behancer.view_model.ProjectsListViewModel;
import com.elegion.test.behancer.view_model.UserProjectsViewModel;

import toothpick.config.Module;

public class FragmentModule extends Module {

    public FragmentModule(Fragment fragment){
        bind(Fragment.class).toInstance(fragment);

        bind(ProfileViewModel.class).toProvider(ProfileViewModelProvider.class).providesSingleton();
        bind(ProjectsListViewModel.class).toProvider(ProjectsListViewModelProvider.class).providesSingleton();
        bind(UserProjectsViewModel.class).toProvider(UserProjectsViewModelProvider.class).providesSingleton();
    }

}
