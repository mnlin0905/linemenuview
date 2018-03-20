package com.mnlin.linemenuview.dagger.component;

import com.mnlin.linemenuview.retrofit.HttpInterface;
import com.mnlin.linemenuview.base.BaseApplication;
import com.mnlin.linemenuview.dagger.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * 功能----应用的组件
 * <p>
 * Created by MNLIN on 2017/9/22.
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(BaseApplication application);

    HttpInterface initHttpInterface();
}
