package com.mnlin.linemenuview.dagger.component;

import com.mnlin.linemenuview.dagger.scope.PerFragment;
import com.mnlin.linemenuview.dagger.module.FragmentModule;

import dagger.Component;

/**
 * 功能----碎片组件,用于注入dagger
 * <p>
 * Created by MNLIN on 2017/9/23.
 */
@PerFragment
@Component(modules = FragmentModule.class, dependencies = ApplicationComponent.class)
public interface FragmentComponent {
}
