package com.elegion.test.behancer.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.elegion.test.behancer.Navigation.RoutingFragment;
import com.elegion.test.behancer.R;
import com.elegion.test.behancer.data.Storage;
import com.elegion.test.behancer.databinding.ProjectsListBinding;
import com.elegion.test.behancer.utils.BaseRefreshViewModelFactory;
import com.elegion.test.behancer.view_model.ProjectsListViewModel;


public class ProjectsFragment extends Fragment {

    public static final String USERNAME = "USERNAME";


    private ProjectsListViewModel mProjectsListViewModel;

    private RoutingFragment mRoutingFragment;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mRoutingFragment = (RoutingFragment) context;

        Storage storage = ((Storage.StorageOwner) context).obtainStorage();
        BaseRefreshViewModelFactory factory = new BaseRefreshViewModelFactory(storage);
        mProjectsListViewModel = ViewModelProviders.of(this, factory).get(ProjectsListViewModel.class);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ProjectsListBinding binding = ProjectsListBinding.inflate(inflater, container, false);
        binding.setViewModelProjectsList(mProjectsListViewModel);
        binding.setLifecycleOwner(this);
        requireActivity().setTitle("Projects");
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        mProjectsListViewModel.getUserClick()
                .observe(getViewLifecycleOwner(),
                        s -> {
            Bundle bundle = new Bundle();
            bundle.putString(USERNAME, s);
            mRoutingFragment.startScreen(R.id.profileFragment, bundle);
        });
        super.onActivityCreated(savedInstanceState);
    }


}

