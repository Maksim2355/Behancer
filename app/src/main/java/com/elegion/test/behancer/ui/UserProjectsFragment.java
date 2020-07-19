package com.elegion.test.behancer.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.elegion.test.behancer.Navigation.RoutingFragment;
import com.elegion.test.behancer.R;
import com.elegion.test.behancer.adapters.ProjectsAdapter;
import com.elegion.test.behancer.common.RefreshOwner;
import com.elegion.test.behancer.data.Storage;
import com.elegion.test.behancer.views.ProjectsView;


public class UserProjectsFragment extends Fragment {

    private static final String USER_TAG = "USER";
    private String mUser;

    private RecyclerView mRecyclerView;
    private RefreshOwner mRefreshOwner;
    private View mErrorView;
    private Storage mStorage;
    private ProjectsAdapter mProjectsAdapter;

    private RoutingFragment routing;



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Storage.StorageOwner) {
            mStorage = ((Storage.StorageOwner) context).obtainStorage();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mRecyclerView = view.findViewById(R.id.recycler);
        mUser = getArguments().getString(USER_TAG);
        mErrorView = view.findViewById(R.id.errorView);
        routing = (RoutingFragment) getActivity();
    }

    @Override
    protected SwipeRefreshLayout getSwipeRefreshLayout(View view) {
        return view.findViewById(R.id.refreshUserProjects);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() != null) getActivity().setTitle(R.string.projects);

        mProjectsAdapter = new ProjectsAdapter(null);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mProjectsAdapter);

        onRefresh();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_projects, container, false);
    }

    @Override
    public void onDetach() {
        mStorage = null;
        super.onDetach();
    }

    @Override
    public void onRefresh() {

    }

//    @Override
//    public void onRefreshData() {
//        mPresenter.getProjects(mUser);
//    }
//
//    @Override
//    public void showRefresh() {
//        mRefreshOwner.setRefreshState(true);
//    }
//
//    @Override
//    public void hideRefresh() {
//        mRefreshOwner.setRefreshState(false);
//    }
//
//    @Override
//    public void showError() {
//        mErrorView.setVisibility(View.VISIBLE);
//        mRecyclerView.setVisibility(View.GONE);
//    }
//
//    @Override
//    public void showProjects(@NonNull List<Project> projects) {
//        mErrorView.setVisibility(View.GONE);
//        mRecyclerView.setVisibility(View.VISIBLE);
//        mProjectsAdapter.addData(projects, true);
//    }
//
//    @Override
//    public void openProfileFragment(@NonNull String username) {
//
//    }
//
//    @Override
//    public void onRefresh() {
//
//    }
}