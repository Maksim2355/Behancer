package com.elegion.test.behancer.di;


import com.elegion.test.behancer.Navigation.RoutingFragment;

import toothpick.config.Module;

public class ActivityModule extends Module {

    public ActivityModule(RoutingFragment routing){
        bind(RoutingFragment.class).toInstance(routing);
    }

}
