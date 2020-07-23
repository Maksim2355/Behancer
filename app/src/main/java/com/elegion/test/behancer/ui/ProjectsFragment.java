package com.elegion.test.behancer.ui;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elegion.test.behancer.Navigation.RoutingFragment;
import com.elegion.test.behancer.R;
import com.elegion.test.behancer.adapters.ProjectsAdapter;
import com.elegion.test.behancer.data.Storage;
import com.elegion.test.behancer.databinding.ProjectsListBinding;
import com.elegion.test.behancer.utils.BaseRefreshViewModelFactory;
import com.elegion.test.behancer.view_model.ProjectsListViewModel;


public class ProjectsFragment extends Fragment {


    private ProjectsListViewModel mProjectsListViewModel;
    private RoutingFragment routing;

    private ProjectsAdapter.OnItemClickListener mOnItemClickListener = username -> {
        Bundle bundle = new Bundle();
        bundle.putString("USERNAME", username);
        routing.startScreen(R.id.action_projectsFragment_to_profileFragment, bundle);
    };



    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        routing = (RoutingFragment) getActivity();
        Storage storage = ((Storage.StorageOwner) context).obtainStorage();

        BaseRefreshViewModelFactory factory = new BaseRefreshViewModelFactory(storage, mOnItemClickListener);
        mProjectsListViewModel = ViewModelProviders.of(this, factory).get(ProjectsListViewModel.class);
    }

    @Nullable
    @Override
   public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ProjectsListBinding binding = ProjectsListBinding.inflate(inflater, container, false);
        binding.setViewModelProjectsList(mProjectsListViewModel);
        binding.setLifecycleOwner(this);
        getActivity().setTitle("Projects");
        return binding.getRoot();
    }

}
