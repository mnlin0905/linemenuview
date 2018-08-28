package com.mnlin.linemenuview.activity

import android.support.design.widget.AppBarLayout
import android.view.View
import android.view.View.OVER_SCROLL_ALWAYS
import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Route
import com.knowledge.mnlin.linemenuview.*
import com.mnlin.linemenuview.R
import com.mnlin.linemenuview.arouter.ARouterConst
import com.mnlin.linemenuview.base.BaseActivity
import com.mnlin.linemenuview.contract.TestActivityContract
import com.mnlin.linemenuview.presenter.TestActivityPresenter
import org.jetbrains.anko.*

/**
 * function---- TestKotlinActivity
 *
 *
 * Created(Gradle default create) by MNLIN on 2018/03/15 12:30:51 (+0000).
 */
@Route(path = ARouterConst.Activity_TestKotlinActivity)
class TestKotlinActivity : BaseActivity<TestActivityPresenter>(), TestActivityContract.View, LineMenuListener {

    override fun getContentOrViewId(): Int {
        //间距
        val dp16 = dimen(R.dimen.view_padding_margin_16dp)
        val dp12 = dimen(R.dimen.view_padding_margin_12dp)

        //统一规格
        val common_padding_bg: LineMenuView.() -> Unit = {
            horizontalPadding = dp16
            backgroundColorResource = R.color.white_background_5
        }

        verticalLayout {
            //toolbar
            include<AppBarLayout>(R.layout.layout_top_bar)

            //scroll_view
            scrollView {
                overScrollMode = OVER_SCROLL_ALWAYS
                isVerticalScrollBarEnabled = false

                //布局
                verticalLayout {
                    bottomPadding = dp16
                    topPadding = dp16

                    //无插件
                    var a = lmv_none(menuText = "无插件") {
                        setCalculation(2)

                        setOnClickListener(object : LineMenuListener {
                            override fun performClickLeft(v: TextView): Boolean {
                                showToast("我被点击了左边的控件,并且返回了false让performSelf方法得以执行")
                                return false
                            }

                            override fun performClickRight(v: View): Boolean {
                                //这里mLmvFirst属于无插件形式，右侧的范围极小，很难点击到
                                showToast("我被点击了右边的控件,并且阻止了performSelf方法的执行")
                                return true
                            }

                            override fun performSelf(v: LineMenuView) {
                                showToast("只有点击左边我才会执行")
                            }
                        })
                    }.lparams(width = matchParent) { topMargin = dp12 }

                    //文本形式
                    lmv_text(menuText = "文本形式", briefText = "简要信息").lparams(width = matchParent) { topMargin = dp12 }

                    //文本大小/颜色/改变
                    lmv_text(menuText = "文本大小/颜色/改变", menuTextSizeRes = R.dimen.text_size_large_18sp, menuTextColorRes = R.color.yellow,
                            briefText = "简要信息", briefTextColorRes = R.color.blue, briefTextSize = dimen(R.dimen.text_size_10sp)).lparams(width = matchParent) { topMargin = dp12 }

                    //带上箭头形式
                    lmv_text(menuText = "带上箭头形式",
                            briefText = "简要信息", briefBadgeRes = R.mipmap.mobile_black, briefNavigation = dispatchGetDrawable(R.drawable.icon_arrow_right))
                            .lparams(width = matchParent) { topMargin = dp12 }

                    //带icon的简要信息,且信息太长需要一直滚动滚动滚动滚动滚动滚动滚动滚动滚动滚动
                    lmv_text(menuText = "带icon的简要信息,且信息太长需要一直滚动滚动滚动滚动滚动滚动滚动滚动滚动滚动", menuIconRes = R.mipmap.mobile_blue,
                            briefText = "简要信息", briefBadgeRes = R.mipmap.mobile_black, briefNavigation = dispatchGetDrawable(R.drawable.icon_arrow_right))
                            .lparams(width = matchParent) { topMargin = dp12 }

                    //切换模式
                    lmv_transition(menuText = "切换模式",
                            transition = true).lparams(width = matchParent) { topMargin = dp12 }

                    //选中/未选中模式
                    lmv_select(menuText = "选中/未选中模式",
                            select = false).lparams(width = matchParent) { topMargin = dp12 }

                    //radio显示模式
                    lmv_radio(menuText = "选中/未选中模式",
                            radio = true).lparams(width = matchParent) { topMargin = dp12 }

                    //radio显示模式
                    lmv_switch(menuText = "switch显示模式",
                            switch = true).lparams(width = matchParent) { topMargin = dp12;leftMargin = dp16;rightMargin = dp16 }
                }.lparams(width = matchParent).applyRecursively {
                    if (it is LineMenuView) {
                        it.common_padding_bg()
                    }
                }
            }.lparams(width = matchParent, height = matchParent)
        }

        return 0
    }

    override fun injectSelf() {
        activityComponent.inject(this)
    }

    /**
     * @param v 被点击到的v;此时应该是该view自身:LineMenuView
     */
    override fun performSelf(v: LineMenuView) {
        when (v.getTag(LMVConfigs.TAG_POSITION) as Int) {
        //因为第一个LineMenuView设置了LineMenuView_for_calculation为off，表示不计数，因此会是-1，且不会影响它后面LineMenuView的序号
        //但是，因为开始时候给mLmvFirst重新设定了onClickListener方法，因此点击它时根本不会进入该方法（performSelf内）

            0 -> showToast("文本形式：位置 0").empty(comment = "文本形式")
            1 -> showToast("可改变字体颜色大小的文本形式: 位置 1").empty(comment = "可改变字体颜色大小的文本形式")
            2 -> showToast("带箭头、badge图标的形式 : 位置 2").empty(comment = "带箭头（navigation）、badge图标的形式")
            3 -> showToast("带箭头、icon、badge，且menu信息滚动的形式: 位置 3").empty(comment = "带箭头、icon、badge，且menu信息滚动的形式")
            4 -> empty(comment = "transition模式").also {
                showToast("transition模式: 位置 4")
                v.transition = !v.transition
            }
            5 -> empty(comment = "select模式").also {
                showToast("select模式: 位置 5")
                v.rightSelect = !v.rightSelect
            }
            6 -> empty(comment = "radio模式").also {
                showToast("radio模式: 位置 6")
                v.radio = !v.radio
            }
            7 -> empty(comment = "switch_模式").also {
                showToast("switch_模式: 位置 7")
                v.switch = !v.switch
            }
        }
    }
}

/**
 * 注释器
 */
inline fun <T> T.empty(comment: String): T {
    return this
}