package com.mnlin.linemenuview.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.knowledge.mnlin.linemenuview.LineMenuListener;
import com.knowledge.mnlin.linemenuview.LineMenuView;
import com.mnlin.linemenuview.R;
import com.mnlin.linemenuview.arouter.ARouterConst;
import com.mnlin.linemenuview.base.BaseActivity;
import com.mnlin.linemenuview.contract.TestActivityContract;
import com.mnlin.linemenuview.presenter.TestActivityPresenter;

import butterknife.BindView;

import static com.knowledge.mnlin.linemenuview.LMVConfigs.TAG_POSITION;

/**
 * function---- TestKotlinActivity
 * <p>
 * Created(Gradle default create) by MNLIN on 2018/03/15 12:30:51 (+0000).
 */
@Route(path = ARouterConst.Activity_TestJavaActivity)
public class TestJavaActivity extends BaseActivity<TestActivityPresenter> implements TestActivityContract.View, LineMenuListener {

    @BindView(R.id.lmv_first)
    LineMenuView mLmvFirst;

    @Override
    protected int getContentOrViewId() {
        return R.layout.activity_test_activity;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        //第一个LineMenuView设置了LineMenuView_for_calculation为off,表示不计入计算内,虽然被点击的话在perform***方法中也会执行,但那是无法获取到位置信息的
        mLmvFirst.setOnClickListener(new LineMenuListener() {
            @Override
            public boolean performClickLeft(TextView v) {
                showToast("我被点击了左边的控件,并且返回了false让performSelf方法得以执行");
                return false;
            }

            @Override
            public boolean performClickRight(View v) {
                //这里mLmvFirst属于无插件形式，右侧的范围极小，很难点击到
                showToast("我被点击了右边的控件,并且阻止了performSelf方法的执行");
                return true;
            }

            @Override
            public void performSelf(LineMenuView v) {
                showToast("只有点击左边我才会执行");
            }
        });
    }

    @Override
    protected void injectSelf() {
        activityComponent.inject(this);
    }

    /**
     * 点击左侧文本
     *
     * @param v 被点击到的v;此时应该是左侧的TextView
     * @return 是否消费该点击事件, 如果返回true, 则performSelf将不会被调用
     */
    @Override
    public boolean performClickLeft(TextView v) {
        return false;
    }

    /**
     * @param v 被点击到的v;此时应该是右侧的TextView
     * @return 是否消费该点击事件, 如果返回true, 则performSelf将不会被调用
     */
    @Override
    public boolean performClickRight(View v) {
        return false;
    }

    /**
     * @param v 被点击到的v;此时应该是该view自身:LineMenuView
     */
    @Override
    public void performSelf(LineMenuView v) {
        int position = ((int) v.getTag(TAG_POSITION));
        switch (position) {
            //因为第一个LineMenuView设置了LineMenuView_for_calculation为off，表示不计数，因此会是-1，且不会影响它后面LineMenuView的序号
            //但是，因为开始时候给mLmvFirst重新设定了onClickListener方法，因此点击它时根本不会进入该方法（performSelf内）

            case 0://文本形式
                showToast("文本形式：位置 0");
                break;
            case 1://可改变字体颜色大小的文本形式
                showToast("可改变字体颜色大小的文本形式: 位置 1");
                break;
            case 2://带箭头（navigation）、badge图标的形式
                showToast("带箭头、badge图标的形式 : 位置 2");
                break;
            case 3://带箭头、icon、badge，且menu信息滚动的形式
                showToast("带箭头、icon、badge，且menu信息滚动的形式: 位置 3");
                break;
            case 4://transition模式
                showToast("transition模式: 位置 4");
                v.setTransition(!v.getTransition());
                break;
            case 5://select模式
                showToast("select模式: 位置 5");
                v.setRightSelect(!v.getRightSelect());
                break;
            case 6://radio模式
                showToast("radio模式: 位置 6");
                v.setRadio(!v.getRadio());
                break;
            case 7://switch_模式
                showToast("switch_模式: 位置 7");
                v.setSwitch(!v.getSwitch());
                break;
        }
    }
}