package com.elegion.test.behancer.data.model.project;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Vladislav Falzan.
 */
@Entity(foreignKeys = @ForeignKey(
        entity = Project.class,
        parentColumns = "id",
        childColumns = "project_id"
))
public class Owner implements Serializable {

    @PrimaryKey
    @ColumnInfo(name = "id")
    private int mId;

    @ColumnInfo(name = "username")
    @SerializedName("username")
    private String mUsername;

    @ColumnInfo(name = "project_id")
    private int mProjectId;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(@NonNull String username) {
        mUsername = username;
    }

    public int getProjectId() {
        return mProjectId;
    }

    public void setProjectId(int projectId) {
        mProjectId = projectId;
    }
}
