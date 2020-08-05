package com.elegion.test.behancer.data.model.user;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;


@Entity
public class Image {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "image_id")
    private int mId;

    @ColumnInfo(name = "photo_url")
    @SerializedName("230")
    private String mPhotoUrl;

    public int getId() {
        return mId;
    }

    public void setId(int id) { mId = id; }

    public String getPhotoUrl() {
        return mPhotoUrl;
    }

    public void setPhotoUrl(@Nullable String photoUrl) {
        mPhotoUrl = photoUrl;
    }

}
