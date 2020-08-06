package com.elegion.test.behancer.data.database;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.elegion.test.behancer.data.model.custom_data.ProjectLive;
import com.elegion.test.behancer.data.model.project.Owner;
import com.elegion.test.behancer.data.model.project.Project;
import com.elegion.test.behancer.data.model.user.User;

import java.util.List;

@Dao
public interface BehanceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertProjects(List<Project> projects);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOwners(List<Owner> owners);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(User user);

    @Query("select * from project")
    LiveData<List<ProjectLive>> getProjectLive();

    @Query("select * from user where username = :username")
    LiveData<User> getUserLiveByName(String username);

    @Query("select * from project")
    DataSource.Factory<Integer, ProjectLive> getProjectLivePaged();

    @Query("select * from project")
    List<Project> getProjects();

    @Query("select * from owner where project_id = :projectId")
    List<Owner> getOwnersFromProject(int projectId);

    @Query("select * from user where username = :username")
    User getUserByName(String username);

}
