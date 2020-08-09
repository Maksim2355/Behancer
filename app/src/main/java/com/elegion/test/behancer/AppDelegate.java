package com.elegion.test.behancer;

import android.app.Application;

import com.elegion.test.behancer.Navigation.RoutingFragment;
import com.elegion.test.behancer.di.ActivityModule;
import com.elegion.test.behancer.di.AppModule;
import com.elegion.test.behancer.di.NetworkModule;
import com.elegion.test.behancer.di.TreeScope;

import toothpick.Scope;
import toothpick.Toothpick;
import toothpick.configuration.Configuration;

public class AppDelegate extends Application {

    private static AppDelegate instance;

    private Scope mAppScope;
    private Scope mActivityScope;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        disabledReflection();

        mAppScope = Toothpick.openScope(TreeScope.APP_SCOPE)
                    .installModules(new AppModule(this), new NetworkModule());
    }

    public Scope initActivityScope(RoutingFragment routing) {
        if (mActivityScope == null){
            mActivityScope = Toothpick.openScopes(TreeScope.APP_SCOPE, TreeScope.ACTIVITY_SCOPE).installModules(
                    new ActivityModule(routing)
            );
        }
        return mActivityScope;
    }

    public void closeActivityScope() {
        mActivityScope = null;
        Toothpick.closeScope(TreeScope.ACTIVITY_SCOPE);
    }


    public Scope getAppScope(){
        return mAppScope;
    }

    public Scope getActivityScope() {return mActivityScope;}

    public static AppDelegate getInstance(){
        return instance;
    }

//    private void disabledReflection(){
//        Toothpick.setConfiguration(Configuration.forProduction().disableReflection());
//        MemberInjectorRegistryLocator.setRootRegistry(
//                new com.elegion.test.behancer.MemberInjectorRegistry());
//
//        FactoryRegistryLocator.setRootRegistry(
//                new com.elegion.test.behancer.FactoryRegistry());
//    }
}
