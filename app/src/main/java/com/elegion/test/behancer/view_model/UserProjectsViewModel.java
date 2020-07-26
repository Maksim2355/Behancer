package com.elegion.test.behancer.view_model;

import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import com.elegion.test.behancer.common.BaseRefreshViewModel;
import com.elegion.test.behancer.data.Storage;
import com.elegion.test.behancer.data.model.custom_projects.ProjectLive;
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
        mProjectsUser = storage.getProjectsPaged();
        update();
    }


    @Override
    public void update() {
        mDisposable =  ApiUtils.getApiService().getUserProjectsInfo(mUsername)
                .map(ProjectResponse::getProjects)
                .doOnSubscribe(disposable -> mIsLoading.postValue(true))
                .doFinally(() -> mIsLoading.postValue(false))
                .doOnSuccess(response -> mIsListVisible.postValue(true))
                .subscribeOn(Schedulers.io())
                .subscribe(
                        response -> { mStorage.insertProjects(response); },
                        throwable -> {
                            List<ProjectLive> value = mProjectsUser.getValue();
                            boolean state = (value == null) || (value.size() == 0);
                            mIsListVisible.postValue(!state);
                        }
                );

    }



    public LiveData<PagedList<ProjectLive>> getProjectsUser() {
        return mProjectsUser;
    }

}
