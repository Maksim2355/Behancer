package com.elegion.test.behancer.data;

import androidx.lifecycle.LiveData;

import com.elegion.test.behancer.data.database.BehanceDao;
import com.elegion.test.behancer.data.model.custom_data.ProjectLive;
import com.elegion.test.behancer.data.model.project.Owner;
import com.elegion.test.behancer.data.model.project.Project;
import com.elegion.test.behancer.data.model.project.ProjectResponse;
import com.elegion.test.behancer.data.model.user.Image;
import com.elegion.test.behancer.data.model.user.User;
import com.elegion.test.behancer.data.model.user.UserResponse;

import java.util.ArrayList;
import java.util.List;


public class Storage {

    private BehanceDao mBehanceDao;

    public Storage(BehanceDao behanceDao) {
        mBehanceDao = behanceDao;
    }

    public LiveData<List<ProjectLive>> getProjectLive(){
        return mBehanceDao.getProjectLive();
    }

    public LiveData<User> getUserLiveByName(String username){
        return mBehanceDao.getUserLiveByName(username);
    }

    public void insertProjects(List<Project> project) {
        mBehanceDao.insertProjects(project);
        mBehanceDao.insertOwners(getOwners(project));

    }

    public void insertUser(User response) {
        mBehanceDao.insertUser(response);
    }

    private List<Owner> getOwners(List<Project> projects) {
        List<Owner> owners = new ArrayList<>();
        for (int i = 0; i < projects.size(); i++) {
            Owner owner = projects.get(i).getOwners().get(0);
            owner.setProjectId(projects.get(i).getId());
            owners.add(owner);
        }
        return owners;
    }

    //deprecated
    public ProjectResponse getProjects() {
        List<Project> projects = mBehanceDao.getProjects();
        for (Project project : projects) {
            project.setOwners(mBehanceDao.getOwnersFromProject(project.getId()));
        }
        ProjectResponse response = new ProjectResponse();
        response.setProjects(projects);
        return response;
    }


    //deprecated
    public UserResponse getUser(String username) {
        User user = mBehanceDao.getUserByName(username);
        UserResponse response = new UserResponse();
        response.setUser(user);
        return response;
    }

    public interface StorageOwner {
        Storage obtainStorage();
    }

}
