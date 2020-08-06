package com.elegion.test.behancer.utils;

import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.elegion.test.behancer.adapters.OnItemClickListener;
import com.elegion.test.behancer.adapters.ProjectsAdapter;
import com.elegion.test.behancer.common.BaseRefreshViewModel;
import com.elegion.test.behancer.data.Storage;
import com.elegion.test.behancer.data.model.project.Project;
import com.elegion.test.behancer.view_model.ProfileViewModel;
import com.elegion.test.behancer.view_model.ProjectsListViewModel;
import com.elegion.test.behancer.view_model.UserProjectsViewModel;

public class BaseRefreshViewModelFactory extends ViewModelProvider.NewInstanceFactory {


    //TODO Сделать адекватное наименование констант view model, которые производит данная фабрика
    private static final class ChildBaseRefreshViewModels {
        public static final String PROJECTS_VIEW_MODEL_NAME = "ProjectsListViewModel";
        public static final String PROFILE_VIEW_MODEL_NAME = "ProfileViewModel";
        public static final String USER_PROJECTS_VIEW_MODEL_NAME = "UserProjectsViewModel";
    }

    private Storage mStorage;
    private OnItemClickListener mOnItemClickListener;

    private Button.OnClickListener mOnBtnClickListener;
    private String mUsername;

    public BaseRefreshViewModelFactory(Storage storage) {
        this.mStorage = storage;
    }

    public BaseRefreshViewModelFactory(Storage storage, String username) {
        this.mStorage = storage;
        this.mUsername = username;
    }


    //TODO() Сделать нормальный кейс и решить проблему с выходным значением default
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        String simpleName = modelClass.getSimpleName();
        switch (simpleName){
            case ChildBaseRefreshViewModels.PROFILE_VIEW_MODEL_NAME: {
                return (T) new ProfileViewModel(mStorage, mUsername);
            }case ChildBaseRefreshViewModels.PROJECTS_VIEW_MODEL_NAME: {
                return (T) new ProjectsListViewModel(mStorage);
            }
            default:return (T) new UserProjectsViewModel(mStorage, mUsername);

        }
    }
}
