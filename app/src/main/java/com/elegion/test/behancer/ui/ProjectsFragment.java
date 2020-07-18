package com.elegion.test.behancer.ui;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elegion.test.behancer.Navigation.RoutingFragment;
import com.elegion.test.behancer.R;
import com.elegion.test.behancer.adapters.ProjectsAdapter;
import com.elegion.test.behancer.common.RefreshFragment;
import com.elegion.test.behancer.data.Storage;
import com.elegion.test.behancer.view_model.ProjectsListViewModel;


public class ProjectsFragment extends RefreshFragment
        implements ProjectsAdapter.OnItemClickListener  {


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
        if (context instanceof Storage.StorageOwner) {
            Storage storage = ((Storage.StorageOwner) context).obtainStorage();
            mProjectsListViewModel = new ProjectsListViewModel(storage, mOnItemClickListener);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_projects, container, false);
    }

    @Override
    protected SwipeRefreshLayout getSwipeRefreshLayout(View view) {
        return view.findViewById(R.id.refresherProjects);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() != null) { getActivity().setTitle(R.string.projects); }

        onRefresh();
    }


    @Override
    public void onItemClick(String username) {

    }

    @Override
    public void onRefresh() {

    }

}
