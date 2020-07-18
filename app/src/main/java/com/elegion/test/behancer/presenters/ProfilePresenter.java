package com.elegion.test.behancer.presenters;

import com.elegion.test.behancer.data.Storage;
import com.elegion.test.behancer.utils.ApiUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ProfilePresenter extends BasePresenter<ProfileView> {

    private final Storage mStorage;

    public ProfilePresenter(Storage mStorage) {
        this.mStorage = mStorage;
    }

    public void getProfile(String mUsername){
        mCompositeDisposable.add(ApiUtils.getApiService().getUserInfo(mUsername)
                .subscribeOn(Schedulers.io())
                .doOnSuccess(mStorage::insertUser)
                .onErrorReturn(throwable ->
                        ApiUtils.NETWORK_EXCEPTIONS.contains(throwable.getClass()) ?
                                mStorage.getUser(mUsername) :
                                null)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> getViewState().showRefresh())
                .doFinally(() -> getViewState().hideRefresh())
                .subscribe(
                        response -> getViewState().bind(response.getUser()),
                        throwable -> getViewState().showError()
                ));
    }

    public void openUserProjects(String user){
        getViewState().openUserWorks(user);
    }




}
