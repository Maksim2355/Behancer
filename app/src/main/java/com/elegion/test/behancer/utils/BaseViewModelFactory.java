package com.elegion.test.behancer.utils;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.elegion.test.behancer.adapters.ProjectsAdapter;
import com.elegion.test.behancer.common.BaseViewModel;
import com.elegion.test.behancer.data.Storage;

public class BaseViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private Storage mStorage;
    private ProjectsAdapter.OnItemClickListener mOnItemClickListener;



    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return new BaseViewModel();
    }
}
