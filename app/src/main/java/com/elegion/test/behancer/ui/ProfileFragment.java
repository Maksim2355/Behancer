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
import com.elegion.test.behancer.databinding.ProfileBinding;
import com.elegion.test.behancer.di.FragmentModule;
import com.elegion.test.behancer.di.ScopeLifecycle;
import com.elegion.test.behancer.di.TreeScope;
import com.elegion.test.behancer.view_model.ProfileViewModel;

import javax.inject.Inject;
import javax.inject.Provider;

import toothpick.ProvidesSingletonInScope;
import toothpick.Scope;
import toothpick.Toothpick;


public class ProfileFragment extends Fragment implements ScopeLifecycle {

    public static final String USERNAME = "USERNAME";
    public static final String ID_FRAGMENT = "_ProfileFragment";

    @Inject
    ProfileViewModel mProfileViewModel;

    @Inject
    RoutingFragment mRouting;

    private String mUsername;

    @Override
    public void onAttach(Context context) {
        initScope();
        super.onAttach(context);
        if (getArguments() != null) mUsername = getArguments().getString(USERNAME);
        else mUsername = "";
        mProfileViewModel.setUsername(mUsername);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ProfileBinding binding = ProfileBinding.inflate(inflater, container, false);
        binding.setViewModelProfile(mProfileViewModel);
        binding.setLifecycleOwner(this);
        requireActivity().setTitle(mUsername);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mProfileViewModel.getIsGoUserProjects().observe(getViewLifecycleOwner(),
                aBoolean -> {
                    if (aBoolean){
                        mProfileViewModel.dispatchIsGoUserProjectsFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString(USERNAME, mUsername);
                        mRouting.startScreen(R.id.action_profileFragment_to_userProjectsFragment, bundle);
                    }
        });
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
