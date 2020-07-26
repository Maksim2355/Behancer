package com.elegion.test.behancer.data.model.custom_projects;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.elegion.test.behancer.data.model.project.Owner;
import com.elegion.test.behancer.data.model.project.Project;

import java.util.List;

public class ProjectLive {

    @Embedded
    private Project project;

    @Relation(entity = Owner.class, entityColumn = "project_id" , parentColumn = "id")
    private List<Owner> owners;


    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<Owner> getOwners() {
        return owners;
    }

    public void setOwners(List<Owner> owners) {
        this.owners = owners;
    }
}
