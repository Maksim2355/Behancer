package com.elegion.test.behancer.data.model.custom_data;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.elegion.test.behancer.data.model.project.Owner;
import com.elegion.test.behancer.data.model.project.Project;

import java.util.List;

public class ProjectLive {

    @Embedded
    private Project mProject;

    @Relation(parentColumn = "id", entityColumn = "project_id")
    private List<Owner> mOwners;

    public Project getProject() {
        return mProject;
    }

    public void setProject(Project mProject) {
        this.mProject = mProject;
    }

    public List<Owner> getOwners() {
        return mOwners;
    }

    public void setOwners(List<Owner> mOwners) {
        this.mOwners = mOwners;
    }
}
