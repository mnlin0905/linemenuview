package com.mnlin.linemenuview.dagger.component;

import com.mnlin.linemenuview.activity.TestActivityActivity;
import com.mnlin.linemenuview.dagger.module.ActivityModule;
import com.mnlin.linemenuview.dagger.scope.PerActivity;

import dagger.Component;

/**
 * 功能----activity组件,提供清单文件
 * <p>
 * Created by MNLIN on 2017/9/22.
 */
@PerActivity
@Component(modules = ActivityModule.class,dependencies = ApplicationComponent.class)
public interface ActivityComponent {
    void inject(TestActivityActivity testActivityActivity);
}
