package com.elegion.test.behancer.common;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public abstract class RefreshFragment extends Fragment
        implements SwipeRefreshLayout.OnRefreshListener, RefreshOwner {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RefreshOwner mRefreshOwner;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSwipeRefreshLayout = getSwipeRefreshLayout(view);
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    protected abstract SwipeRefreshLayout getSwipeRefreshLayout(View view);

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mRefreshOwner = this;
    }

    @Override
    public void setRefreshState(boolean refreshing) {
        mSwipeRefreshLayout.post(() -> mSwipeRefreshLayout.setRefreshing(refreshing));
    }

    @Override
    public void onDetach() {
        mRefreshOwner = null;
        super.onDetach();
    }
}
