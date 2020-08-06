package com.elegion.test.behancer.adapters.diff_callback;

import androidx.recyclerview.widget.DiffUtil;

import com.elegion.test.behancer.data.model.custom_data.ProjectLive;

import java.util.List;

public class ProjectDiffUtilCallback extends DiffUtil.Callback {

    private final List<ProjectLive> mOldList;
    private final List<ProjectLive> mNewList;

    public ProjectDiffUtilCallback(List<ProjectLive> oldList, List<ProjectLive> newList) {
        mOldList = oldList;
        mNewList = newList;
    }


    @Override
    public int getOldListSize() {
        return mOldList.size();
    }

    @Override
    public int getNewListSize() {
        return mNewList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldList.get(oldItemPosition).getProject().getId() == mNewList.get(newItemPosition).getProject().getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldList.get(oldItemPosition).getProject().getName().equals(mNewList.get(newItemPosition).getProject().getName());
    }
}
