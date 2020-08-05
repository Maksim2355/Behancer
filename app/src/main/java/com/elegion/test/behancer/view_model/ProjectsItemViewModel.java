package com.elegion.test.behancer.view_model;

import androidx.lifecycle.ViewModel;

import com.elegion.test.behancer.data.model.custom_data.ProjectLive;
import com.elegion.test.behancer.utils.DateUtils;

public class ProjectsItemViewModel extends ViewModel {

    private static final int FIRST_OWNER_INDEX = 0;

    private String mImageUrl;
    private String mName;
    private String mUsername;
    private String mPublishedOn;

    public ProjectsItemViewModel(ProjectLive project) {
        this.mImageUrl = project.getProject().getCover().getPhotoUrl();
        this.mName = project.getProject().getName();
        if (project.getOwners().size() != 0) this.mUsername = project.getOwners().get(FIRST_OWNER_INDEX).getUsername();
        this.mPublishedOn = DateUtils.format(project.getProject().getPublishedOn());
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
