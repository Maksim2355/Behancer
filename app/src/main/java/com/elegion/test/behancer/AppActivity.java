package com.elegion.test.behancer;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.elegion.test.behancer.Navigation.RoutingFragment;
import com.elegion.test.behancer.data.Storage;



public class AppActivity extends AppCompatActivity implements Storage.StorageOwner, RoutingFragment {

    private NavController navController;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navController = Navigation.findNavController(this, R.id.nav_host_fr_container);
    }

    @Override
    public Storage obtainStorage() {
        return ((AppDelegate) getApplicationContext()).getStorage();
    }


    @Override
    public void startScreen(int destination, Bundle bundle) {
        navController.navigate(destination, bundle);
    }

    @Override
    public void popBackStack() {
        navController.popBackStack();
    }
}
