package com.elegion.test.behancer.utils;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.elegion.test.behancer.adapters.ProjectsAdapter;
import com.elegion.test.behancer.data.model.project.Project;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomBindingAdapter {

    @BindingAdapter("bind:imageUrl")
    public static void loadImage(ImageView imageView, String urlImage){
        Picasso.with(imageView.getContext()).load(urlImage).into(imageView);
    }

    @BindingAdapter("bind")
    public static void configureRecyclerView(RecyclerView recyclerView, List<Project> listProjects,
                                             ProjectsAdapter.OnItemClickListener onItemClickListener){
        ProjectsAdapter projectsAdapter = new ProjectsAdapter(onItemClickListener);
        projectsAdapter.addData();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mProjectsAdapter);

    }
}
