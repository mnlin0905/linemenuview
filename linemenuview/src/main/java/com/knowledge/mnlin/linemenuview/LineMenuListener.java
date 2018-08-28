package com.knowledge.mnlin.linemenuview;


import android.view.View;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

/**
 * Created on 2018/8/27  15:12
 * function : 控件监听
 *
 * @author mnlin
 */
public interface LineMenuListener {
    /**
     * @param v 被点击到的v;此时应该是该view自身:LineMenuView
     */
    default void performSelf(@NotNull LineMenuView lmv) {

    }

    /**
     * 点击左侧文本
     *
     * @param v 被点击到的v;此时应该是左侧的TextView
     * @return 是否消费该点击事件, 如果返回true, 则performSelf将不会被调用
     */
    @NotNull
    default boolean performClickLeft(@NotNull TextView tv) {
        return false;
    }

    /**
     * 注：该放置主要针对 text 插件设计，但即便是其他插件模式，也可以通过 v.getTag()方法获取到位置信息
     *
     * @param v 被点击到的v;此时应该是右侧的TextView
     * @return 是否消费该点击事件, 如果返回true, 则performSelf将不会被调用
     */
    @NotNull
    default boolean performClickRight(@NotNull View v) {
        return false;
    }
}
