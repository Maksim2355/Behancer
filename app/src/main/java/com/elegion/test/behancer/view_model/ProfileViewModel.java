package com.elegion.test.behancer.view_model;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.elegion.test.behancer.common.BaseRefreshViewModel;
import com.elegion.test.behancer.data.Storage;
import com.elegion.test.behancer.data.model.user.User;
import com.elegion.test.behancer.data.model.user.UserResponse;
import com.elegion.test.behancer.utils.ApiUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ProfileViewModel extends BaseRefreshViewModel {

    private LiveData<User> mUser;

    private MutableLiveData<Boolean> mIsGoUserProjects = new MutableLiveData<>(false);
    private View.OnClickListener mOnBtnWorksListClickListener = v -> {mIsGoUserProjects.postValue(true);};


    private String mUsername;


    public ProfileViewModel(Storage storage, String username) {
        mStorage = storage;
        mUsername = username;
        mUser = storage.getUserLiveByName(mUsername);
        update();
    }


    @Override
    public void update() {
        mDisposable = ApiUtils.getApiService().getUserInfo(mUsername)
                .map(UserResponse::getUser)
                .doOnSubscribe(disposable -> mIsLoading.postValue(true))
                .doFinally(() -> mIsLoading.postValue(false))
                .doOnSuccess(user -> mIsListVisible.postValue(true))
                .subscribeOn(Schedulers.io())
                .subscribe(
                        response -> {mStorage.insertUser(response);},
                        throwable -> {
                            mIsListVisible.postValue(false);
                            User user = mUser.getValue();
                            if(user != null) mIsListVisible.postValue(true);
                        });
    }


    public void dispatchIsGoUserProjectsFragment(){
        mIsGoUserProjects.postValue(false);
    }

    public LiveData<User> getUser()
    {
        return mUser;
    }

    public View.OnClickListener getOnBtnWorksListClickListener() {
        return mOnBtnWorksListClickListener;
    }

    public MutableLiveData<Boolean> getIsGoUserProjects() {
        return mIsGoUserProjects;
    }

}
