package com.knowledge.mnlin.linemenuview

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.annotation.*
import android.support.annotation.Dimension.PX
import android.util.TypedValue
import android.view.View
import android.view.ViewManager
import android.widget.TextView
import org.jetbrains.anko.custom.ankoView
import org.jetbrains.anko.dimen

/**************************************
 * function :  添加anko控制
 *
 * Created on 2018/8/27  15:31
 * @author mnlin
 **************************************/

/**
 * 获取drawable
 */
private fun getDrawable(ctx: Context, @DrawableRes res: Int): Drawable {
    return if (Build.VERSION.SDK_INT >= 21) {
        ctx.getDrawable(res)
    } else {
        ctx.resources.getDrawable(res)
    }
}

/**
 * 获取color
 */
@ColorInt
private fun getColor(ctx: Context, @ColorRes res: Int): Int {
    return if (Build.VERSION.SDK_INT >= 23) {
        ctx.getColor(res)
    } else {
        ctx.resources.getColor(res)
    }
}

/**
 * 相同的添加menu
 */
private fun ViewManager.lmv(menuText: String? = null, menuIcon: Drawable? = null, @DrawableRes menuIconRes: Int? = null, @Dimension(unit = PX) menuTextSize: Int? = null, @DimenRes menuTextSizeRes: Int? = null, @ColorInt menuTextColor: Int? = null, @ColorRes menuTextColorRes: Int? = null, init: (LineMenuView.() -> Unit) = {}, pluginInt: LineMenuView.(ctx: Context) -> Unit): LineMenuView {
    return ankoView({ ctx ->
        LineMenuView(ctx, null, 0).apply {
            this.menuText = menuText
            menuIcon?.let {
                setIcon(it)
            }
            menuIconRes?.let {
                setIcon(getDrawable(ctx, it))
            }
            menuTextSize?.let {
                setMenuTextSize(TypedValue.COMPLEX_UNIT_PX, it)
            }
            menuTextSizeRes?.let {
                setMenuTextSize(TypedValue.COMPLEX_UNIT_PX, ctx.dimen(it))
            }
            menuTextColor?.let {
                setMenuTextColor(it)
            }
            menuTextColorRes?.let {
                setMenuTextColor(getColor(ctx, it))
            }

            //初始化插件
            pluginInt(ctx)
        }
    }, 0, init)
}

/**
 * 无插件
 */
fun ViewManager.lmv_none(menuText: String? = null, menuIcon: Drawable? = null, @DrawableRes menuIconRes: Int? = null, @Dimension(unit = PX) menuTextSize: Int? = null, @DimenRes menuTextSizeRes: Int? = null, @ColorInt menuTextColor: Int? = null, @ColorRes menuTextColorRes: Int? = null, init: (LineMenuView.() -> Unit) = {}): LineMenuView {
    return this.lmv(menuText, menuIcon, menuIconRes, menuTextSize, menuTextSizeRes, menuTextColor, menuTextColorRes, init) {}
}

/**
 * 默认为text形式
 */
fun ViewManager.lmv_text(
        //menu参数部分
        menuText: String? = null, menuIcon: Drawable? = null, @DrawableRes menuIconRes: Int? = null, @Dimension(unit = PX) menuTextSize: Int? = null, @DimenRes menuTextSizeRes: Int? = null, @ColorInt menuTextColor: Int? = null, @ColorRes menuTextColorRes: Int? = null,

        //brief参数图标部分
        briefBadge: Drawable? = null, @DrawableRes briefBadgeRes: Int? = null, briefNavigation: Drawable? = null, @DrawableRes briefNavigationRes: Int? = null,

        //brief参数字体部分
        briefText: String? = null, @Dimension(unit = PX) briefTextSize: Int? = null, @DimenRes briefTextSizeRes: Int? = null, @ColorInt briefTextColor: Int? = null, @ColorRes briefTextColorRes: Int? = null,

        //自定义初始化方法
        init: (LineMenuView.() -> Unit) = {}): LineMenuView {

    return this.lmv(menuText, menuIcon, menuIconRes, menuTextSize, menuTextSizeRes, menuTextColor, menuTextColorRes, init) { ctx ->
        setPlugin(1)
        this.briefText = briefText

        briefBadge?.let {
            setBadge(it)
        }
        briefBadgeRes?.let {
            setBadge(getDrawable(ctx, it))
        }
        briefNavigation?.let {
            setNavigation(it)
        }
        briefNavigationRes?.let {
            setNavigation(getDrawable(ctx, it))
        }
        briefTextSize?.let {
            setBriefSize(TypedValue.COMPLEX_UNIT_PX, it)
        }
        briefTextSizeRes?.let {
            setBriefSize(TypedValue.COMPLEX_UNIT_PX, ctx.dimen(it))
        }
        briefTextColor?.let {
            setBriefColor(it)
        }
        briefTextColorRes?.let {
            setBriefColor(getColor(ctx, it))
        }
    }
}

/**
 * 默认为switch形式
 */
fun ViewManager.lmv_switch(
        //menu参数部分
        menuText: String? = null, menuIcon: Drawable? = null, @DrawableRes menuIconRes: Int? = null, @Dimension(unit = PX) menuTextSize: Int? = null, @DimenRes menuTextSizeRes: Int? = null, @ColorInt menuTextColor: Int? = null, @ColorRes menuTextColorRes: Int? = null,

        //switch参数,true表示开
        switch: Boolean = false,

        //自定义初始化方法
        init: (LineMenuView.() -> Unit) = {}): LineMenuView {

    return this.lmv(menuText, menuIcon, menuIconRes, menuTextSize, menuTextSizeRes, menuTextColor, menuTextColorRes, init) {
        setPlugin(2)
        this.switch = switch
    }
}

/**
 * 默认为radio形式
 */
fun ViewManager.lmv_radio(
        //menu参数部分
        menuText: String? = null, menuIcon: Drawable? = null, @DrawableRes menuIconRes: Int? = null, @Dimension(unit = PX) menuTextSize: Int? = null, @DimenRes menuTextSizeRes: Int? = null, @ColorInt menuTextColor: Int? = null, @ColorRes menuTextColorRes: Int? = null,

        //radio参数,true表示开
        radio: Boolean = false,

        //自定义初始化方法
        init: (LineMenuView.() -> Unit) = {}): LineMenuView {

    return this.lmv(menuText, menuIcon, menuIconRes, menuTextSize, menuTextSizeRes, menuTextColor, menuTextColorRes, init) {
        setPlugin(3)
        this.radio = radio
    }
}

/**
 * 默认为select形式
 */
fun ViewManager.lmv_select(
        //menu参数部分
        menuText: String? = null, menuIcon: Drawable? = null, @DrawableRes menuIconRes: Int? = null, @Dimension(unit = PX) menuTextSize: Int? = null, @DimenRes menuTextSizeRes: Int? = null, @ColorInt menuTextColor: Int? = null, @ColorRes menuTextColorRes: Int? = null,

        //select参数,true表示开
        select: Boolean = false,

        //自定义初始化方法
        init: (LineMenuView.() -> Unit) = {}): LineMenuView {
    return this.lmv(menuText, menuIcon, menuIconRes, menuTextSize, menuTextSizeRes, menuTextColor, menuTextColorRes, init) {
        setPlugin(4)
        this.rightSelect = rightSelect
    }
}

/**
 * 默认为transition形式
 */
fun ViewManager.lmv_transition(
        //menu参数部分
        menuText: String? = null, menuIcon: Drawable? = null, @DrawableRes menuIconRes: Int? = null, @Dimension(unit = PX) menuTextSize: Int? = null, @DimenRes menuTextSizeRes: Int? = null, @ColorInt menuTextColor: Int? = null, @ColorRes menuTextColorRes: Int? = null,

        //transition参数,true表示开
        transition: Boolean = false,

        //自定义初始化方法
        init: (LineMenuView.() -> Unit) = {}): LineMenuView {

    return this.lmv(menuText, menuIcon, menuIconRes, menuTextSize, menuTextSizeRes, menuTextColor, menuTextColorRes, init) {
        setPlugin(5)
        this.transition = transition
    }
}

/**
 * function :  设置监听器快捷功能 -- 点击自身
 *
 * Created on 2018/8/27  20:17
 * @author mnlin
 */
inline fun LineMenuView.setClickSelfListener(crossinline clickSelf: (LineMenuView) -> Unit) {
    setOnClickListener(object : LineMenuListener {
        override fun performSelf(lmv: LineMenuView) {
            clickSelf(lmv)
        }
    })
}

/**
 * function :  设置监听器快捷功能 -- 点击左侧
 *
 * Created on 2018/8/27  20:17
 * @author mnlin
 */
inline fun LineMenuView.setClickLeftListener(crossinline clickLeft: (TextView) -> Boolean) {
    setOnClickListener(object : LineMenuListener {
        override fun performClickLeft(tv: TextView): Boolean {
            return clickLeft(tv)
        }
    })
}

/**
 * function :  设置监听器快捷功能 -- 点击右侧
 *
 * Created on 2018/8/27  20:17
 * @author mnlin
 */
inline fun LineMenuView.setClickRightListener(crossinline clickRight: (View) -> Boolean) {
    setOnClickListener(object : LineMenuListener {
        override fun performClickRight(v: View): Boolean {
            return clickRight(v)
        }
    })
}


