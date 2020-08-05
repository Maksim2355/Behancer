package com.elegion.test.behancer.view_model;

import androidx.lifecycle.LiveData;

import com.elegion.test.behancer.BuildConfig;
import com.elegion.test.behancer.common.BaseRefreshViewModel;
import com.elegion.test.behancer.data.Storage;
import com.elegion.test.behancer.data.model.custom_data.ProjectLive;
import com.elegion.test.behancer.data.model.project.ProjectResponse;
import com.elegion.test.behancer.utils.ApiUtils;

import java.util.List;

import io.reactivex.schedulers.Schedulers;

public class UserProjectsViewModel extends BaseRefreshViewModel {

    private LiveData<List<ProjectLive>> mProjectsUser;
    private String mUsername;

    public UserProjectsViewModel(Storage storage, String username){
        mStorage = storage;
        mUsername = username;
        mProjectsUser = mStorage.getProjectLive();
        update();
    }

    @Override
    public void update() {
        mDisposable = ApiUtils.getApiService().getProjects(BuildConfig.API_QUERY)
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



    public LiveData<List<ProjectLive>> getProjectsUser() {
        return mProjectsUser;
    }

    public String getUsername(){
        return mUsername;
    }

}
