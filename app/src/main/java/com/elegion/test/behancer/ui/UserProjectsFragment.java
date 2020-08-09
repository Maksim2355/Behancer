package com.elegion.test.behancer.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.elegion.test.behancer.databinding.UserProjectsBinding;
import com.elegion.test.behancer.di.FragmentModule;
import com.elegion.test.behancer.di.ScopeLifecycle;
import com.elegion.test.behancer.di.TreeScope;
import com.elegion.test.behancer.view_model.UserProjectsViewModel;

import javax.inject.Inject;

import toothpick.Scope;
import toothpick.Toothpick;


public class UserProjectsFragment extends Fragment implements ScopeLifecycle {

    public static final String USERNAME = "USERNAME";
    public static final String ID_FRAGMENT = "_UserProjectsFragment";

    @Inject
    UserProjectsViewModel mUserProjectsViewModel;

    @Override
    public void onAttach(Context context) {
        initScope();
        super.onAttach(context);
        String username;
        if (getArguments() != null)username = getArguments().getString(USERNAME);
        else username = "";
        mUserProjectsViewModel.setUsername(username);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        UserProjectsBinding binding = UserProjectsBinding.inflate(inflater, container, false);
        binding.setUserProjectsViewModel(mUserProjectsViewModel);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

    @Override
    public void onDetach() {
        closeScope();
        super.onDetach();
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