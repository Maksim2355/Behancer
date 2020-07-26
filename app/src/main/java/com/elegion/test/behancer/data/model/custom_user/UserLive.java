package com.elegion.test.behancer.data.model.custom_user;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.elegion.test.behancer.data.model.user.Image;
import com.elegion.test.behancer.data.model.user.User;

public class UserLive {

    @Embedded
    private User mUser;

    @Relation(entity = Image.class, entityColumn = "user_id", parentColumn = "id")
    private Image mImage;

    public User getUser() {
        return mUser;
    }

    public void setUser(User mUser) {
        this.mUser = mUser;
    }

    public Image getImage() {
        return mImage;
    }

    public void setImage(Image mImage) {
        this.mImage = mImage;
    }
}
