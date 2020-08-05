package com.elegion.test.behancer.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.elegion.test.behancer.BuildConfig;
import com.elegion.test.behancer.adapters.ProjectsAdapter;
import com.elegion.test.behancer.common.BaseRefreshViewModel;
import com.elegion.test.behancer.data.Storage;
import com.elegion.test.behancer.data.model.custom_data.ProjectLive;
import com.elegion.test.behancer.data.model.project.Project;
import com.elegion.test.behancer.data.model.project.ProjectResponse;
import com.elegion.test.behancer.data.model.user.User;
import com.elegion.test.behancer.utils.ApiUtils;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ProjectsListViewModel extends BaseRefreshViewModel {


    private LiveData<List<ProjectLive>> mProjects;

    private MutableLiveData<String> mUsername = new MutableLiveData<>();

    private ProjectsAdapter.OnItemClickListener mOnItemClickListener = username -> mUsername.postValue(username);


    public ProjectsListViewModel(Storage storage){
        mStorage = storage;
        mProjects = storage.getProjectLive();
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
                            List<ProjectLive> projects = mProjects.getValue();
                            if((projects != null) && (projects.size() != 0)) mIsListVisible.postValue(true);
                        });
    }

    public LiveData<String> getUserClick() {
        return mUsername;
    }

    public void dispatchUsername(){
        mUsername.postValue("");
    }

    public LiveData<List<ProjectLive>> getProjects() {
        return mProjects;
    }

    public ProjectsAdapter.OnItemClickListener getOnItemClickListener() {
        return mOnItemClickListener;
    }

}
