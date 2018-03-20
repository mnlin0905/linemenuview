package com.mnlin.linemenuview.presenter;


import com.mnlin.linemenuview.base.BasePresenter;
import com.mnlin.linemenuview.contract.TestActivityContract;
import com.mnlin.linemenuview.activity.TestActivityActivity;

import javax.inject.Inject;

/**
 * function---- TestActivityPresenter
 * <p>
 * Created(Gradle default create) by MNLIN on 2018/03/15 12:30:51 (+0000).
 */
public class TestActivityPresenter extends BasePresenter<TestActivityActivity> implements TestActivityContract.Presenter{
    @Inject
    public TestActivityPresenter() {}

}