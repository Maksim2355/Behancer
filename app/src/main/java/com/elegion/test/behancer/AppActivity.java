package com.elegion.test.behancer;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.elegion.test.behancer.Navigation.RoutingFragment;


public class AppActivity extends AppCompatActivity implements RoutingFragment {

    private NavController navController;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        AppDelegate.getInstance().initActivityScope(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navController = Navigation.findNavController(this, R.id.nav_host_fr_container);
    }

    @Override
    public void startScreen(int destination, Bundle bundle) {
        navController.navigate(destination, bundle);
    }

    @Override
    public void popBackStack() {
        navController.popBackStack();
    }

    @Override
    protected void onDestroy() {
        AppDelegate.getInstance().closeActivityScope();
        super.onDestroy();
    }


}
