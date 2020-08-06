package com.elegion.test.behancer.view_model;

import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import com.elegion.test.behancer.BuildConfig;
import com.elegion.test.behancer.common.BaseRefreshViewModel;
import com.elegion.test.behancer.data.Storage;
import com.elegion.test.behancer.data.model.custom_data.ProjectLive;
import com.elegion.test.behancer.data.model.project.ProjectResponse;
import com.elegion.test.behancer.utils.ApiUtils;

import java.util.List;

import io.reactivex.schedulers.Schedulers;

public class UserProjectsViewModel extends BaseRefreshViewModel {

    private LiveData<PagedList<ProjectLive>> mProjectsUser;
    private String mUsername;

    public UserProjectsViewModel(Storage storage, String username){
        mStorage = storage;
        mUsername = username;
        mProjectsUser = mStorage.getProjectsLivePaged();
        update();
    }

    @Override
    public void update() {
        mDisposable = ApiUtils.getApiService().getUserProjectsInfo(mUsername)
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
                            List<ProjectLive> projects = mProjectsUser.getValue();
                            if((projects != null) && (projects.size() != 0)) mIsListVisible.postValue(true);
                        });
    }



    public LiveData<PagedList<ProjectLive>> getProjectsUser() {
        return mProjectsUser;
    }

    public String getUsername(){
        return mUsername;
    }

}
