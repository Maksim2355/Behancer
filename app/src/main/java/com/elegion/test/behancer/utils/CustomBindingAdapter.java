package com.elegion.test.behancer.utils;

import android.os.Build;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.databinding.BindingAdapter;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.elegion.test.behancer.adapters.OnItemClickListener;
import com.elegion.test.behancer.adapters.ProjectsPagedAdapter;
import com.elegion.test.behancer.data.model.custom_data.ProjectLive;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomBindingAdapter {

    @BindingAdapter("bind:imageUrl")
    public static void loadImage(ImageView imageView, String urlImage){
        Picasso.with(imageView.getContext()).load(urlImage).transform(new CircleTransform()).into(imageView);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @BindingAdapter({"bind:data", "bind:clickHandler", "bind:username"})
    public static void configureRecyclerView(RecyclerView recyclerView, PagedList<ProjectLive> listProjects,
                                             OnItemClickListener onItemClickListener, String username){

        //Todo натворил фигню
        if ((username != null) && (listProjects != null)){

        }

        ProjectsPagedAdapter projectsAdapter = new ProjectsPagedAdapter(onItemClickListener);
        projectsAdapter.submitList(listProjects);

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
