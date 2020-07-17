package com.elegion.test.behancer;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.MvpAppCompatFragment;
import com.elegion.test.behancer.Navigation.RoutingFragment;
import com.elegion.test.behancer.common.RefreshOwner;
import com.elegion.test.behancer.common.Refreshable;
import com.elegion.test.behancer.data.Storage;



public class AppActivity extends MvpAppCompatActivity
        implements Storage.StorageOwner,
        SwipeRefreshLayout.OnRefreshListener, RefreshOwner, RoutingFragment {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private NavController navController;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSwipeRefreshLayout = findViewById(R.id.refresher);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        navController = Navigation.findNavController(this, R.id.nav_host_fr_container);
    }

    @Override
    public Storage obtainStorage() {
        return ((AppDelegate) getApplicationContext()).getStorage();
    }


    @Override
    public void onRefresh() {
        MvpAppCompatFragment fragment =  (MvpAppCompatFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment_container);
        if (fragment instanceof Refreshable) {
            ((Refreshable) fragment).onRefreshData();
        } else {
            setRefreshState(false);
        }
    }

    @Override
    public void setRefreshState(boolean refreshing) {
        mSwipeRefreshLayout.post(() -> mSwipeRefreshLayout.setRefreshing(refreshing));
    }

    @Override
    public void startScreen(int destination, Bundle bundle) {
        navController.navigate(destination, bundle);
    }

    @Override
    public void popBackStack() {
        navController.popBackStack();
    }
}
