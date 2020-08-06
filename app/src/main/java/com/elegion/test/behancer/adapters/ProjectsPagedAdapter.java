package com.elegion.test.behancer.adapters;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;

import com.elegion.test.behancer.adapters.holder.ProjectsHolder;
import com.elegion.test.behancer.data.model.custom_data.ProjectLive;
import com.elegion.test.behancer.databinding.ProjectItemBinding;

public class ProjectsPagedAdapter extends PagedListAdapter<ProjectLive, ProjectsHolder> {

    private OnItemClickListener mOnItemClickListener;

    public ProjectsPagedAdapter(OnItemClickListener onItemClickListener) {
        super(projectsCallback);
        mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ProjectsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ProjectsHolder(ProjectItemBinding.inflate(inflater, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectsHolder holder, int position) {
        holder.bind(getItem(position), mOnItemClickListener);
    }

    private static final DiffUtil.ItemCallback<ProjectLive> projectsCallback = new DiffUtil.ItemCallback<ProjectLive>() {
        @Override
        public boolean areItemsTheSame(@NonNull ProjectLive oldItem, @NonNull ProjectLive newItem) {
            return oldItem.getProject().getId() == newItem.getProject().getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull ProjectLive oldItem, @NonNull ProjectLive newItem) {
            return oldItem.getProject().getName().equals(newItem.getProject().getName());
        }
    };
}
