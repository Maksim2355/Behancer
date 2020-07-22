package com.elegion.test.behancer.view_model;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.elegion.test.behancer.common.BaseViewModel;
import com.elegion.test.behancer.data.Storage;
import com.elegion.test.behancer.data.model.user.User;
import com.elegion.test.behancer.utils.ApiUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ProfileViewModel extends BaseViewModel {

    private MutableLiveData<User> mUser = new MutableLiveData<>();
    private View.OnClickListener mOnBtnWorksListClickListener;


    public ProfileViewModel(Storage mStorage, View.OnClickListener mOnBtnWorksListClickListener, String username) {
        this.mStorage = mStorage;
        this.mOnBtnWorksListClickListener = mOnBtnWorksListClickListener;
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

    public MutableLiveData<User> getUser()
    {
        return mUser;
    }

    public View.OnClickListener getOnBtnWorksListClickListener() {
        return mOnBtnWorksListClickListener;
    }

}
