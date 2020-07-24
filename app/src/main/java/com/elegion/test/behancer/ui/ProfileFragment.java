package com.elegion.test.behancer.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.elegion.test.behancer.Navigation.RoutingFragment;
import com.elegion.test.behancer.R;
import com.elegion.test.behancer.data.Storage;
import com.elegion.test.behancer.databinding.ProfileBinding;
import com.elegion.test.behancer.utils.BaseRefreshViewModelFactory;
import com.elegion.test.behancer.view_model.ProfileViewModel;


public class ProfileFragment extends Fragment {

    public static final String USERNAME = "USERNAME";

    private ProfileViewModel mProfileViewModel;

    private RoutingFragment mRouting;
    private String mUsername;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Storage storage = ((Storage.StorageOwner) context).obtainStorage();
        mRouting = (RoutingFragment) context;

        if (getArguments() != null) mUsername = getArguments().getString(USERNAME);
        else mUsername = "";
        BaseRefreshViewModelFactory factory = new BaseRefreshViewModelFactory(storage, mUsername);
        mProfileViewModel = ViewModelProviders.of(this, factory).get(ProfileViewModel.class);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ProfileBinding binding = ProfileBinding.inflate(inflater, container, false);
        binding.setViewModelProfile(mProfileViewModel);
        binding.setLifecycleOwner(this);
        getActivity().setTitle(mUsername);
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
}
