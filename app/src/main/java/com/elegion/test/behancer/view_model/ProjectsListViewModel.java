package com.elegion.test.behancer.view_model;

import androidx.lifecycle.MutableLiveData;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.elegion.test.behancer.BuildConfig;
import com.elegion.test.behancer.adapters.ProjectsAdapter;
import com.elegion.test.behancer.common.BaseViewModel;
import com.elegion.test.behancer.data.Storage;
import com.elegion.test.behancer.data.model.project.Project;
import com.elegion.test.behancer.utils.ApiUtils;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ProjectsListViewModel extends BaseViewModel {


    private MutableLiveData<List<Project>> mProjects = new MutableLiveData<>();

    private ProjectsAdapter.OnItemClickListener mOnItemClickListener;


    public ProjectsListViewModel(Storage storage, ProjectsAdapter.OnItemClickListener mOnItemClickListener) {
        this.mStorage = storage;
        this.mOnItemClickListener = mOnItemClickListener;
    }


    @Override
    protected SwipeRefreshLayout.OnRefreshListener getRefreshListener() {
        return this::update;
    }

    @Override
    public void update() {
        mDisposable = ApiUtils.getApiService().getProjects(BuildConfig.API_QUERY)
                .subscribeOn(Schedulers.io())
                .doOnSuccess(mStorage::insertProjects)
                .onErrorReturn(throwable ->
                        ApiUtils.NETWORK_EXCEPTIONS.contains(throwable.getClass()) ? mStorage.getProjects() : null)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> mIsLoading.postValue(true))
                .doFinally(() -> mIsLoading.postValue(false))
                .subscribe(
                        response -> {
                            mIsListVisible.postValue(true);
                            mProjects.postValue(response.getProjects());
                        },
                        throwable -> mIsListVisible.postValue(false));
    }
}
