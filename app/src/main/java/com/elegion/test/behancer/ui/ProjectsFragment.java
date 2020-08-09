package com.elegion.test.behancer.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.elegion.test.behancer.Navigation.RoutingFragment;
import com.elegion.test.behancer.R;
import com.elegion.test.behancer.databinding.ProjectsListBinding;
import com.elegion.test.behancer.di.FragmentModule;
import com.elegion.test.behancer.di.ScopeLifecycle;
import com.elegion.test.behancer.di.TreeScope;
import com.elegion.test.behancer.view_model.ProjectsListViewModel;

import javax.inject.Inject;

import toothpick.Scope;
import toothpick.Toothpick;


public class ProjectsFragment extends Fragment implements ScopeLifecycle {

    public static final String USERNAME = "USERNAME";
    public static final String ID_FRAGMENT = "_ProjectsFragment";

    @Inject
    ProjectsListViewModel mProjectsListViewModel;

    @Inject
    RoutingFragment mRoutingFragment;


    @Override
    public void onAttach(@NonNull Context context) {
        initScope();
        super.onAttach(context);
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
                .observe(getViewLifecycleOwner(), s -> {
                    if (!s.equals("")){
                        mProjectsListViewModel.dispatchUsername();
                        Bundle bundle = new Bundle();
                        bundle.putString(USERNAME, s);
                        mRoutingFragment.startScreen(R.id.profileFragment, bundle);
                    }
        });
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        closeScope();
    }

    @Override
    public void initScope() {
        Scope frScope = Toothpick.openScopes(TreeScope.ACTIVITY_SCOPE, TreeScope.FRAGMENT_SCOPE + ID_FRAGMENT)
                .installModules(new FragmentModule(this));
        Toothpick.inject(this, frScope);
    }

    @Override
    public void closeScope() {
        Toothpick.closeScope(TreeScope.FRAGMENT_SCOPE + ID_FRAGMENT);
    }
}

