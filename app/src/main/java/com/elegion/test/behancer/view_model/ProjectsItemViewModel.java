package com.elegion.test.behancer.view_model;

import androidx.lifecycle.ViewModel;
import com.elegion.test.behancer.data.model.project.Project;
import com.elegion.test.behancer.utils.DateUtils;

public class ProjectsItemViewModel extends ViewModel {

    private static final int FIRST_OWNER_INDEX = 0;

    private String mImageUrl;
    private String mName;
    private String mUsername;
    private String mPublishedOn;

    public ProjectsItemViewModel(Project project) {
        this.mImageUrl = project.getCover().getPhotoUrl();
        this.mName = project.getName();
        this.mUsername = project.getOwners().get(FIRST_OWNER_INDEX).getUsername();
        this.mPublishedOn = DateUtils.format(project.getPublishedOn());
    }


    public String getImageUrl() {
        return mImageUrl;
    }

    public String getName() {
        return mName;
    }

    public String getUsername() {
        return mUsername;
    }

    public String getPublishedOn() {
        return mPublishedOn;
    }
}
