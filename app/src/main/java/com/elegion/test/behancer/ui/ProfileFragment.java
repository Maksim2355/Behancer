package com.elegion.test.behancer.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.elegion.test.behancer.Navigation.RoutingFragment;
import com.elegion.test.behancer.R;
import com.elegion.test.behancer.data.Storage;
import com.elegion.test.behancer.view_model.ProfileViewModel;


public class ProfileFragment extends Fragment {

    public static final String USERNAME = "USERNAME";

    private ProfileViewModel mProfileViewModel;

    private Storage mStorage;
    private RoutingFragment mRouting;
    private String mUsername;

    private Button.OnClickListener mOnBtnWorksListClickListener = v -> {
        Bundle bundle = new Bundle();
        bundle.putString("USER", mUsername);
        mRouting.startScreen(R.id.action_profileFragment_to_userProjectsFragment, bundle);
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Storage.StorageOwner) {
            mStorage = ((Storage.StorageOwner) context).obtainStorage();
            mRouting = (RoutingFragment)getActivity();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null) mUsername = getArguments().getString(USERNAME);
        if (getActivity() != null) getActivity().setTitle(mUsername);
        mProfileViewModel = new ProfileViewModel(mStorage, mOnBtnWorksListClickListener);
    }

}
