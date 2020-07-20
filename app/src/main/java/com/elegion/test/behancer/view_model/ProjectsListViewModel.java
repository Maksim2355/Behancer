package com.elegion.test.behancer.view_model;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.ViewModel;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.elegion.test.behancer.BuildConfig;
import com.elegion.test.behancer.adapters.ProjectsAdapter;
import com.elegion.test.behancer.data.Storage;
import com.elegion.test.behancer.data.model.project.Project;
import com.elegion.test.behancer.utils.ApiUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ProjectsListViewModel extends ViewModel {

    private Disposable mDisposable;
    private Storage mStorage;

    private ObservableBoolean mIsLoading = new ObservableBoolean(false);
    private ObservableBoolean mIsListVisible = new ObservableBoolean(false);
    private ObservableArrayList<Project> mProjects = new ObservableArrayList<>();

    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = this::loadProjects;


    private ProjectsAdapter.OnItemClickListener mOnItemClickListener;


    public ProjectsListViewModel(Storage storage, ProjectsAdapter.OnItemClickListener mOnItemClickListener) {
        this.mStorage = storage;
        this.mOnItemClickListener = mOnItemClickListener;
    }


    public void loadProjects() {
        mDisposable = ApiUtils.getApiService().getProjects(BuildConfig.API_QUERY)
                        .subscribeOn(Schedulers.io())
                        .doOnSuccess(mStorage::insertProjects)
                        .onErrorReturn(throwable ->
                                ApiUtils.NETWORK_EXCEPTIONS.contains(throwable.getClass()) ? mStorage.getProjects() : null)
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(disposable -> mIsLoading.set(true))
                        .doFinally(() -> mIsLoading.set(false))
                        .subscribe(
                                response -> {
                                    mIsListVisible.set(true);
                                    mProjects.addAll(response.getProjects());
                                },
                                throwable -> mIsListVisible.set(false));
    }

    public ObservableBoolean getIsLoading() {
        return mIsLoading;
    }

    public ObservableBoolean getIsListVisible() {
        return mIsListVisible;
    }

    public ObservableArrayList<Project> getProjects() {
        return mProjects;
    }

    public ProjectsAdapter.OnItemClickListener getOnItemClickListener() {
        return mOnItemClickListener;
    }

    public SwipeRefreshLayout.OnRefreshListener getOnRefreshListener() {
        return mOnRefreshListener;
    }


    public void dispatchDetach(){
        mStorage = null;
        mDisposable = null;
    }

}
