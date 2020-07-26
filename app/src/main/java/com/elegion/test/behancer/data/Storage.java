package com.elegion.test.behancer.data;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.elegion.test.behancer.data.database.BehanceDao;
import com.elegion.test.behancer.data.model.custom_projects.ProjectLive;
import com.elegion.test.behancer.data.model.custom_user.UserLive;
import com.elegion.test.behancer.data.model.project.Owner;
import com.elegion.test.behancer.data.model.project.Project;
import com.elegion.test.behancer.data.model.project.ProjectResponse;
import com.elegion.test.behancer.data.model.user.Image;
import com.elegion.test.behancer.data.model.user.User;
import com.elegion.test.behancer.data.model.user.UserResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vladislav Falzan.
 */

public class Storage {

    public static final int PAGE_SIZE = 30;
    private BehanceDao mBehanceDao;

    public Storage(BehanceDao behanceDao) {
        mBehanceDao = behanceDao;
    }

    public void insertProjects(ProjectResponse response) {
        insertProjects(response.getProjects());
    }

    public LiveData<List<ProjectLive>> getProjectsLive(){
        return mBehanceDao.getProjectsLive();
    }

    private List<Owner> getOwners(List<Project> projects) {
        List<Owner> owners = new ArrayList<>();
        for (int i = 0; i < projects.size(); i++) {
            Owner owner = projects.get(i).getOwners().get(0);
            owner.setId(i);
            owner.setProjectId(projects.get(i).getId());
            owners.add(owner);
        }
        return owners;
    }


    public void insertProjects(List<Project> projects){
        mBehanceDao.insertProjects(projects);
        List<Owner> owners = getOwners(projects);
        mBehanceDao.clearOwnerTable();
        mBehanceDao.insertOwners(owners);
    }

    public ProjectResponse getProjects() {
        List<Project> projects = mBehanceDao.getProjects();
        for (Project project : projects) {
            project.setOwners(mBehanceDao.getOwnersFromProject(project.getId()));
        }
        ProjectResponse response = new ProjectResponse();
        response.setProjects(projects);
        return response;
    }

    public void insertUser(User user) {
        Image image = user.getImage();
        image.setId(user.getId());
        image.setUserId(user.getId());

        mBehanceDao.insertUser(user);
        mBehanceDao.insertImage(image);
    }

    public LiveData<UserLive> getUserLive(String username){
        return mBehanceDao.getUserLiveByName(username);
    }

    public List<ProjectLive> getProjectLiveNoReactive(){
        return mBehanceDao.getProjectsLiveNoReactive();
    }

    public LiveData<PagedList<ProjectLive>> getProjectsPaged(){
        return new LivePagedListBuilder<>(mBehanceDao.getProjectsLivePaged(), PAGE_SIZE).build();
    }

    public UserResponse getUser(String username) {
        User user = mBehanceDao.getUserByName(username);
        Image image = mBehanceDao.getImageFromUser(user.getId());
        user.setImage(image);
        UserResponse response = new UserResponse();
        response.setUser(user);
        return response;
    }



    public interface StorageOwner {
        Storage obtainStorage();
    }

}
