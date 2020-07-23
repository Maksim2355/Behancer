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
import androidx.recyclerview.widget.RecyclerView;

import com.elegion.test.behancer.Navigation.RoutingFragment;
import com.elegion.test.behancer.R;
import com.elegion.test.behancer.adapters.ProjectsAdapter;
import com.elegion.test.behancer.common.BaseRefreshViewModel;
import com.elegion.test.behancer.data.Storage;
import com.elegion.test.behancer.databinding.UserProjectsBinding;
import com.elegion.test.behancer.utils.BaseRefreshViewModelFactory;
import com.elegion.test.behancer.view_model.UserProjectsViewModel;


public class UserProjectsFragment extends Fragment {

    public static final String USERNAME = "USERNAME";

    private UserProjectsViewModel mUserProjectsViewModel;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Storage storage = ((Storage.StorageOwner) context).obtainStorage();

        String username = getArguments().getString(USERNAME);

        BaseRefreshViewModelFactory factory = new BaseRefreshViewModelFactory(storage, username);

        mUserProjectsViewModel = ViewModelProviders.of(this, factory).get(UserProjectsViewModel.class);

    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        UserProjectsBinding binding = UserProjectsBinding.inflate(inflater, container, false);
        binding.setUserProjectsViewModel(mUserProjectsViewModel);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }


}