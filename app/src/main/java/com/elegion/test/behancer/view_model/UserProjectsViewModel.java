package com.elegion.test.behancer.view_model;

import androidx.lifecycle.MutableLiveData;

import com.elegion.test.behancer.adapters.ProjectsAdapter;
import com.elegion.test.behancer.common.BaseRefreshViewModel;
import com.elegion.test.behancer.data.Storage;
import com.elegion.test.behancer.data.model.project.Project;
import com.elegion.test.behancer.utils.ApiUtils;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class UserProjectsViewModel extends BaseRefreshViewModel {

    private MutableLiveData<List<Project>> mProjectsUser = new MutableLiveData<>();
    private String mUsername;

    public UserProjectsViewModel(Storage storage, String username){
        mStorage = storage;
        mUsername = username;
        update();
    }

    @Override
    public void update() {
        mDisposable =  ApiUtils.getApiService().getUserProjectsInfo(mUsername)
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
                            response.getProjects();
                            mProjectsUser.postValue(response.getProjects());
                        },
                        throwable -> mIsListVisible.postValue(false)
                );
    }



    public MutableLiveData<List<Project>> getProjectsUser() {
        return mProjectsUser;
    }

}
