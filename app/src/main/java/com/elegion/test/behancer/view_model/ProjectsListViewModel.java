package com.elegion.test.behancer.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.elegion.test.behancer.BuildConfig;
import com.elegion.test.behancer.adapters.ProjectsAdapter;
import com.elegion.test.behancer.common.BaseRefreshViewModel;
import com.elegion.test.behancer.data.Storage;
import com.elegion.test.behancer.data.model.project.Project;
import com.elegion.test.behancer.utils.ApiUtils;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ProjectsListViewModel extends BaseRefreshViewModel {


    private MutableLiveData<List<Project>> mProjects = new MutableLiveData<>();

    private MutableLiveData<String> mUsername = new MutableLiveData<>();

    private ProjectsAdapter.OnItemClickListener mOnItemClickListener = username -> mUsername.postValue(username);


    public ProjectsListViewModel(Storage storage){
        mStorage = storage;
        update();
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

    public LiveData<String> getUserClick() {
        return mUsername;
    }

    public void dispatchUsername(){
        mUsername.postValue("");
    }

    public MutableLiveData<List<Project>> getProjects() {
        return mProjects;
    }

    public ProjectsAdapter.OnItemClickListener getOnItemClickListener() {
        return mOnItemClickListener;
    }

}
