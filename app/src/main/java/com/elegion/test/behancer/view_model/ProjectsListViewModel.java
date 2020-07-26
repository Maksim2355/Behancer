package com.elegion.test.behancer.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PagedList;

import com.elegion.test.behancer.BuildConfig;
import com.elegion.test.behancer.adapters.ProjectsAdapter;
import com.elegion.test.behancer.common.BaseRefreshViewModel;
import com.elegion.test.behancer.data.Storage;
import com.elegion.test.behancer.data.model.custom_projects.ProjectLive;
import com.elegion.test.behancer.data.model.project.ProjectResponse;
import com.elegion.test.behancer.utils.ApiUtils;

import java.util.List;

import io.reactivex.schedulers.Schedulers;

public class ProjectsListViewModel extends BaseRefreshViewModel {

    private LiveData<PagedList<ProjectLive>> mProjects;

    private MutableLiveData<String> mUsername = new MutableLiveData<>();

    private ProjectsAdapter.OnItemClickListener mOnItemClickListener = username -> {
        if (username != null) mUsername.postValue(username);
    };


    public ProjectsListViewModel(Storage storage){
        mStorage = storage;
        mProjects = storage.getProjectsPaged();
        update();
    }


    @Override
    public void update() {
        mDisposable = ApiUtils.getApiService().getProjects(BuildConfig.API_QUERY)
                .map(ProjectResponse::getProjects)
                .doOnSubscribe(disposable -> mIsLoading.postValue(true))
                .doFinally(() -> mIsLoading.postValue(false))
                .doOnSuccess(response -> mIsListVisible.postValue(true))
                .subscribeOn(Schedulers.io())
                .subscribe(
                        response -> {
                            mStorage.insertProjects(response);
                        },
                        throwable -> {
                            mIsListVisible.postValue(false);
                            List<ProjectLive> value = mProjects.getValue();
                            boolean state = (value == null) || (value.size() == 0);
                            mIsListVisible.postValue(!state);
                        });
    }



    public LiveData<String> getUserClick() {
        return mUsername;
    }

    public void dispatchUsername(){
        mUsername.postValue("");
    }

    public LiveData<PagedList<ProjectLive>> getProjects() {
        return mProjects;
    }

    public ProjectsAdapter.OnItemClickListener getOnItemClickListener() {
        return mOnItemClickListener;
    }

}
