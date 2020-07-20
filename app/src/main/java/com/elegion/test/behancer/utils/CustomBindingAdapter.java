package com.elegion.test.behancer.utils;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.elegion.test.behancer.adapters.ProjectsAdapter;
import com.elegion.test.behancer.data.model.project.Project;
import com.elegion.test.behancer.data.model.user.Image;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomBindingAdapter {

    @BindingAdapter("bind:imageUrl")
    public static void loadImage(ImageView imageView, String urlImage){
        Picasso.with(imageView.getContext()).load(urlImage).into(imageView);
    }

    @BindingAdapter({"bind:data", "bind:clickHandler"})
    public static void configureRecyclerView(RecyclerView recyclerView, List<Project> listProjects,
                                             ProjectsAdapter.OnItemClickListener onItemClickListener){
        ProjectsAdapter projectsAdapter = new ProjectsAdapter(listProjects, onItemClickListener);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(projectsAdapter);
    }

    @BindingAdapter({"bind:refreshState", "bind:onRefreshListener"})
    public static void configureSwipeRefreshLayout(SwipeRefreshLayout swipeRefreshLayout, boolean isLoading,
                                                   SwipeRefreshLayout.OnRefreshListener onRefreshListener){
        swipeRefreshLayout.setOnRefreshListener(onRefreshListener);
        swipeRefreshLayout.post(() -> swipeRefreshLayout.setRefreshing(isLoading));
    }


    @BindingAdapter("bind:createdOn")
    public static void setDateInCorrectFormat(TextView textView, long createdOn){
        textView.setText(DateUtils.format(createdOn));
    }

}
