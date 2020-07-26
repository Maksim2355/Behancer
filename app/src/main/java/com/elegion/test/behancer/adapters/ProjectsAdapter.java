package com.elegion.test.behancer.adapters;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elegion.test.behancer.R;
import com.elegion.test.behancer.adapters.holder.ProjectsHolder;
import com.elegion.test.behancer.data.model.custom_projects.ProjectLive;
import com.elegion.test.behancer.data.model.project.Project;
import com.elegion.test.behancer.databinding.ProjectItemBinding;

import java.util.ArrayList;
import java.util.List;


public class ProjectsAdapter extends PagedListAdapter<ProjectLive, ProjectsHolder> {

    private final OnItemClickListener mOnItemClickListener;

    public ProjectsAdapter(OnItemClickListener onItemClickListener) {
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
        ProjectLive project = getItem(position);
        if(project != null)holder.bind(getItem(position), mOnItemClickListener);
    }

    public interface OnItemClickListener {
        void onItemClick(String username);
    }
    private static final DiffUtil.ItemCallback<ProjectLive> projectsCallback = new DiffUtil.ItemCallback<ProjectLive>() {
        @Override
        public boolean areItemsTheSame(@NonNull ProjectLive oldItem, @NonNull ProjectLive newItem) {
            return oldItem.getProject().getId() == newItem.getProject().getId();
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull ProjectLive oldItem, @NonNull ProjectLive newItem) {
            return oldItem.equals(newItem);
        }
    };
}
