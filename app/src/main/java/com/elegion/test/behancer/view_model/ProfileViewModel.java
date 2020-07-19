package com.elegion.test.behancer.view_model;

import android.view.View;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.ViewModel;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.elegion.test.behancer.data.Storage;
import com.elegion.test.behancer.data.model.user.User;
import com.elegion.test.behancer.utils.ApiUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ProfileViewModel extends ViewModel {

    private Disposable mDisposable;
    private Storage mStorage;
    private String mUsername;


    private ObservableBoolean mIsLoading = new ObservableBoolean(false);
    private ObservableBoolean mIsListVisible = new ObservableBoolean(false);

    private User mUser;
    private View.OnClickListener mOnBtnWorksListClickListener;



    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = this::getProjects;

    private void getProjects() {
        mDisposable = ApiUtils.getApiService().getUserInfo(mUsername)
                .subscribeOn(Schedulers.io())
                .doOnSuccess(mStorage::insertUser)
                .onErrorReturn(throwable ->
                        ApiUtils.NETWORK_EXCEPTIONS.contains(throwable.getClass()) ?
                                mStorage.getUser(mUsername) :
                                null)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> mIsLoading.set(true))
                .doFinally(() -> mIsLoading.set(false))
                .subscribe(
                        response -> {
                            mIsListVisible.set(true);

                        },
                        throwable -> mIsListVisible.set(false)
                );
    }


    public ProfileViewModel(Storage mStorage, View.OnClickListener mOnBtnWorksListClickListener) {
        this.mStorage = mStorage;
        this.mOnBtnWorksListClickListener = mOnBtnWorksListClickListener;
    }

    public void getProjects(String mUsername){
        mDisposable = ApiUtils.getApiService().getUserInfo(mUsername)
                .subscribeOn(Schedulers.io())
                .doOnSuccess(mStorage::insertUser)
                .onErrorReturn(throwable ->
                        ApiUtils.NETWORK_EXCEPTIONS.contains(throwable.getClass()) ?
                                mStorage.getUser(mUsername) :
                                null)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> mIsLoading.set(true))
                .doFinally(() -> mIsLoading.set(false))
                .subscribe(
                        response -> {
                            mIsListVisible.set(true);

                        },
                        throwable -> mIsListVisible.set(false)
                );
    }



    public ObservableBoolean getIsLoading() {
        return mIsLoading;
    }

    public ObservableBoolean getIsListVisible() {
        return mIsListVisible;
    }

    public User getUser() {
        return mUser;
    }

    public View.OnClickListener getOnBtnWorksListClickListener() {
        return mOnBtnWorksListClickListener;
    }

    public SwipeRefreshLayout.OnRefreshListener getOnRefreshListener() {
        return mOnRefreshListener;
    }

    public void dispatchDetach(){
        mStorage = null;
        mDisposable = null;
    }
}
