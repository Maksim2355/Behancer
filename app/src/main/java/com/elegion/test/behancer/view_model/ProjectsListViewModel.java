package com.elegion.test.behancer.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PagedList;

import com.elegion.test.behancer.BuildConfig;
import com.elegion.test.behancer.adapters.OnItemClickListener;
import com.elegion.test.behancer.adapters.ProjectsAdapter;
import com.elegion.test.behancer.common.BaseRefreshViewModel;
import com.elegion.test.behancer.data.Storage;
import com.elegion.test.behancer.data.api.BehanceApi;
import com.elegion.test.behancer.data.model.custom_data.ProjectLive;
import com.elegion.test.behancer.data.model.project.Project;
import com.elegion.test.behancer.data.model.project.ProjectResponse;
import com.elegion.test.behancer.data.model.user.User;
import com.elegion.test.behancer.utils.ApiUtils;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ProjectsListViewModel extends BaseRefreshViewModel {


    private LiveData<PagedList<ProjectLive>> mProjects;

    private MutableLiveData<String> mUsername = new MutableLiveData<>();

    private OnItemClickListener mOnItemClickListener = username -> mUsername.postValue(username);

    private BehanceApi mApi;

    @Inject
    public ProjectsListViewModel(Storage storage, BehanceApi api){
        mStorage = storage;
        mApi = api;
        mProjects = storage.getProjectsLivePaged();
        update();
    }


    @Override
    public void update() {
        mDisposable = mApi.getProjects(BuildConfig.API_QUERY)
                .map(ProjectResponse::getProjects)
                .doOnSubscribe(disposable -> mIsLoading.postValue(true))
                .doFinally(() -> mIsLoading.postValue(false))
                .doOnSuccess(projects -> mIsListVisible.postValue(true))
                .subscribeOn(Schedulers.io())
                .subscribe(
                        response -> {
                            mStorage.insertProjects(response);
                        },
                        throwable -> {
                            mIsListVisible.postValue(false);
                            List<ProjectLive> projects = mProjects.getValue();
                            if((projects != null) && (projects.size() != 0)) mIsListVisible.postValue(true);
                        });
    }

    public LiveData<String> getUserClick() {
        return mUsername;
    }


    public LiveData<PagedList<ProjectLive>> getProjects() {
        return mProjects;
    }

    public OnItemClickListener getOnItemClickListener() {
        return mOnItemClickListener;
    }

    public void dispatchUsername(){
        mUsername.postValue("");
    }

}
