package com.elegion.test.behancer.adapters.holder;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.elegion.test.behancer.R;
import com.elegion.test.behancer.adapters.ProjectsAdapter;
import com.elegion.test.behancer.data.model.project.Project;
import com.elegion.test.behancer.databinding.ProjectItemBinding;
import com.elegion.test.behancer.utils.DateUtils;
import com.elegion.test.behancer.view_model.ProjectsItemViewModel;
import com.squareup.picasso.Picasso;

/**
 * Created by Vladislav Falzan.
 */

public class ProjectsHolder extends RecyclerView.ViewHolder {

    private ProjectItemBinding mBinding;

    public ProjectsHolder(ProjectItemBinding itemBinding) {
        super(itemBinding.getRoot());
        mBinding = itemBinding;
    }

    public void bind(Project item, ProjectsAdapter.OnItemClickListener onItemClickListener) {
        mBinding.setProjectsItem(new ProjectsItemViewModel(item));
        mBinding.setOnItemClickListener(onItemClickListener);
        mBinding.executePendingBindings();
    }
}
