package com.elegion.test.behancer.ui;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.elegion.test.behancer.Navigation.RoutingFragment;
import com.elegion.test.behancer.R;
import com.elegion.test.behancer.common.PresenterFragment;
import com.elegion.test.behancer.data.model.user.User;
import com.elegion.test.behancer.presenters.ProfilePresenter;
import com.elegion.test.behancer.utils.DateUtils;
import com.elegion.test.behancer.common.RefreshOwner;
import com.elegion.test.behancer.common.Refreshable;
import com.elegion.test.behancer.data.Storage;
import com.elegion.test.behancer.views.ProfileView;
import com.squareup.picasso.Picasso;



public class ProfileFragment extends PresenterFragment implements Refreshable, ProfileView {

    public static final String USERNAME = "USERNAME";

    private RefreshOwner mRefreshOwner;
    private View mErrorView;
    private View mProfileView;
    private String mUsername;
    private Storage mStorage;

    private RoutingFragment mRouting;

    private ImageView mProfileImage;
    private TextView mProfileName;
    private TextView mProfileCreatedOn;
    private TextView mProfileLocation;
    private Button mViewWorksBtn;
    private User mUser;


    @InjectPresenter
    ProfilePresenter mProfilePresenter;

    @ProvidePresenter
    ProfilePresenter providePresenter(){ return new ProfilePresenter(mStorage); }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mStorage = context instanceof Storage.StorageOwner ? ((Storage.StorageOwner) context).obtainStorage() : null;
        mRefreshOwner = context instanceof RefreshOwner ? (RefreshOwner) context : null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mErrorView = view.findViewById(R.id.errorView);
        mProfileView = view.findViewById(R.id.view_profile);
        mViewWorksBtn = view.findViewById(R.id.goUserProjects_btn);
        mProfileImage = view.findViewById(R.id.iv_profile);
        mProfileName = view.findViewById(R.id.tv_display_name_details);
        mProfileCreatedOn = view.findViewById(R.id.tv_created_on_details);
        mProfileLocation = view.findViewById(R.id.tv_location_details);

        mRouting = (RoutingFragment)getActivity();

        mViewWorksBtn.setOnClickListener(v -> mProfilePresenter.openUserProjects(mUser.getUsername()));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null) {
            mUsername = getArguments().getString(USERNAME);
        }
        if (getActivity() != null) {
            getActivity().setTitle(mUsername);
        }
        mProfileView.setVisibility(View.VISIBLE);
        onRefreshData();
    }

    @Override
    public void onRefreshData() {
       mProfilePresenter.getProfile(mUsername);
    }



    @Override
    public void bind(User user) {
        mErrorView.setVisibility(View.GONE);
        mProfileView.setVisibility(View.VISIBLE);
        mUser = user;
        Picasso.with(getContext())
                .load(user.getImage().getPhotoUrl())
                .fit()
                .into(mProfileImage);
        mProfileName.setText(user.getDisplayName());
        mProfileCreatedOn.setText(DateUtils.format(user.getCreatedOn()));
        mProfileLocation.setText(user.getLocation());
    }

    @Override
    public void openUserWorks(String username) {
        Bundle bundle = new Bundle();
        bundle.putString("USER", username);
        mRouting.startScreen(R.id.action_profileFragment_to_userProjectsFragment, bundle);
    }

    @Override
    protected ProfilePresenter getPresenter() {
        return mProfilePresenter;
    }

    @Override
    public void onDetach() {
        mStorage = null;
        mRefreshOwner = null;
        super.onDetach();
    }

    @Override
    public void showRefresh() {
        mRefreshOwner.setRefreshState(true);
    }

    @Override
    public void hideRefresh() {
        mRefreshOwner.setRefreshState(false);
    }

    @Override
    public void showError() {
        mErrorView.setVisibility(View.VISIBLE);
        mProfileView.setVisibility(View.GONE);
    }
}
