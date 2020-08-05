package com.elegion.test.behancer.utils;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.elegion.test.behancer.adapters.ProjectsAdapter;
import com.elegion.test.behancer.data.model.custom_data.ProjectLive;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CustomBindingAdapter {

    @BindingAdapter("bind:imageUrl")
    public static void loadImage(ImageView imageView, String urlImage){
        Picasso.with(imageView.getContext()).load(urlImage).transform(new CircleTransform()).into(imageView);
    }

    @BindingAdapter({"bind:data", "bind:clickHandler", "bind:username"})
    public static void configureRecyclerView(RecyclerView recyclerView, List<ProjectLive> listProjects,
                                             ProjectsAdapter.OnItemClickListener onItemClickListener, String username){

        if ((username != null) && (listProjects != null)){
            List<ProjectLive> tmpListProjects = new ArrayList<>();
            for(int i = 0; i < listProjects.size(); i++){
                if (listProjects.get(i).getOwners().get(0).getUsername().equals(username)){
                    tmpListProjects.add(listProjects.get(i));
                }
            }
            listProjects = tmpListProjects;
        }
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
