package com.elegion.test.behancer.view_model;

import android.view.View;

import androidx.lifecycle.MutableLiveData;

import com.elegion.test.behancer.common.BaseRefreshViewModel;
import com.elegion.test.behancer.data.Storage;
import com.elegion.test.behancer.data.model.user.User;
import com.elegion.test.behancer.utils.ApiUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ProfileViewModel extends BaseRefreshViewModel {

    private MutableLiveData<User> mUser = new MutableLiveData<>();

    private MutableLiveData<Boolean> mIsGoUserProjects = new MutableLiveData<>(false);
    private View.OnClickListener mOnBtnWorksListClickListener = v -> {mIsGoUserProjects.postValue(true);};


    private String mUsername;


    public ProfileViewModel(Storage storage, String username) {
        mStorage = storage;
        mUsername = username;
        update();
    }


    @Override
    public void update() {
        mDisposable = ApiUtils.getApiService().getUserInfo(mUsername)
                .subscribeOn(Schedulers.io())
                .doOnSuccess(mStorage::insertUser)
                .onErrorReturn(throwable ->
                        ApiUtils.NETWORK_EXCEPTIONS.contains(throwable.getClass()) ?
                                mStorage.getUser(mUsername) :
                                null)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> mIsLoading.postValue(true))
                .doFinally(() -> mIsLoading.postValue(false))
                .subscribe(
                        response -> {
                            mIsListVisible.postValue(true);
                            mUser.postValue(response.getUser());
                        },
                        throwable -> mIsListVisible.postValue(false));
    }


    public void dispatchIsGoUserProjectsFragment(){
        mIsGoUserProjects.postValue(false);
    }

    public MutableLiveData<User> getUser()
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
