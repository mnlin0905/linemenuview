package com.mnlin.linemenuview.dagger.module;

import android.view.ViewGroup;

import com.mnlin.linemenuview.base.BaseFragment;
import com.mnlin.linemenuview.dagger.scope.PerFragment;
import com.mnlin.linemenuview.base.BaseActivity;

import dagger.Module;
import dagger.Provides;

/**
 * 功能----碎片实例提供器
 * <p>
 * Created by MNLIN on 2017/9/23.
 */
@PerFragment
@Module
public class FragmentModule {
    private BaseFragment baseFragment;

    public FragmentModule(BaseFragment baseFragment) {
        this.baseFragment = baseFragment;
    }

    /**
     * 为fragment提供上下文
     */
    @Provides
    @PerFragment
    BaseActivity provideBaseActivity(){
        return ((BaseActivity) baseFragment.getActivity());
    }

    /**
     * 为fragment设定根部局
     */
    @Provides
    @PerFragment
    ViewGroup provideViewGroup(){
        return ((ViewGroup) baseFragment.getView());
    }
}
