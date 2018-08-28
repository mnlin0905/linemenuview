# LineMenuView
[![License](https://img.shields.io/aur/license/yaourt.svg)](http://www.gnu.org/licenses/gpl-3.0.html)
[![Download](https://api.bintray.com/packages/lovingning/maven/linemenuview/images/download.svg) ](https://bintray.com/lovingning/maven/linemenuview/_latestVersion)


> 版本信息说明, 见: **[versions.md](versions.md)**

> 对于框架实现原理，可参考博客：[LineMenuView使用及原理](http://blog.csdn.net/lovingning/article/details/79624457)

## 一、引入依赖
在项目**build.gradle**中添加依赖：

```
    compile 'com.knowledge.mnlin:linemenuview:latest.release'
```

## 二、 xml中添加布局文件或使用anko布局

### 1 使用xml布局+java代码进行开发

java代码可参照:[TestJavaActivity.java](app/src/main/java/com/mnlin/linemenuview/activity/TestJavaActivity.java)
布局文件可参照: [activity_test_activity.xml](app/src/main/res/layout/activity_test_activity.xml)

取布局文件中一部分做示例:

```
    <com.knowledge.mnlin.linemenuview.LineMenuView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="@dimen/view_padding_margin_12dp"
        app:LineMenuView_badge="@mipmap/mobile_black"
        app:LineMenuView_navigation="@drawable/icon_arrow_right"
        android:background="@color/white_background_5"
        android:paddingEnd="@dimen/view_padding_margin_16dp"
        android:paddingStart="@dimen/view_padding_margin_16dp"
        app:LineMenuView_brief="简要信息"
        app:LineMenuView_icon="@mipmap/mobile_blue"
        app:LineMenuView_menu="带icon的简要信息,且信息太长需要一直滚动滚动滚动滚动滚动滚动滚动滚动滚动滚动"
        app:LineMenuView_plugin="text"/>
    
    <com.knowledge.mnlin.linemenuview.LineMenuView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="@dimen/view_padding_margin_12dp"
        android:background="@color/white_background_5"
        android:paddingEnd="@dimen/view_padding_margin_16dp"
        android:paddingStart="@dimen/view_padding_margin_16dp"
        app:LineMenuView_menu="切换模式"
        app:LineMenuView_plugin="transition"
        app:LineMenuView_transition="on"/>
```

这里如果不需要主动设置效果，则并**不需要**定义id属性；

LineMenuView自定义了很多属性值，具体属性值表示含义可以参考博客；这里列出所有属性值：

```
    <!--单行菜单对应的参数：switch状态、menu文本、icon图标等-->
    <declare-styleable name="LineMenuView">
        <attr name="LineMenuView_plugin" format="enum">
            <enum name="none" value="0"/>
            <enum name="text" value="1"/>
            <enum name="switch_" value="2"/>
            <enum name="radio" value="3"/>
            <enum name="select" value="4"/>
            <enum name="transition" value="5"/>
        </attr>
        <attr name="LineMenuView_switch" format="enum">
            <enum name="off" value="0"/>
            <enum name="on" value="1"/>
        </attr>
        <!--选中/未选中-->
        <attr name="LineMenuView_radio" format="enum">
            <enum name="off" value="0"/>
            <enum name="on" value="1"/>
        </attr>
        <!--开/关-->
        <attr name="LineMenuView_transition" format="enum">
            <enum name="off" value="0"/>
            <enum name="on" value="1"/>
        </attr>
        
        <!--用于计算,default表示默认:只有在visible时才会纳入计算;on表示纳入计算,即便是不可见状态;off表示不纳入计算,即使是可见状态-->
        <attr name="LineMenuView_for_calculation" format="enum">
            <enum name="bypassed" value="0"/>
            <enum name="on" value="1"/>
            <enum name="off" value="2"/>
        </attr>

        <!--brief部分-->
        <attr name="LineMenuView_badge" format="reference"/>
        <attr name="LineMenuView_navigation" format="reference"/>
        <attr name="LineMenuView_brief" format="string"/>
        <attr name="LineMenuView_brief_text_color" format="color"/>
        <attr name="LineMenuView_brief_text_size" format="dimension"/>

        <!--menu部分-->
        <attr name="LineMenuView_icon" format="reference"/>
        <attr name="LineMenuView_menu" format="string"/>
        <attr name="LineMenuView_menu_text_color" format="color"/>
        <attr name="LineMenuView_menu_text_size" format="dimension"/>
    </declare-styleable>
```

### 2 使用anko进行开发

首先需要做好kotlin+anko环境配置,具体可参考网上教程

因为anko不需要xml文件进行配合,所以只需要参考kotlin文件即可:java代码可参照:[TestKotlinActivity.kt](app/src/main/java/com/mnlin/linemenuview/activity/TestKotlinActivity.kt)

使用anko时,根据需要的插件类型,可以调用以下的方法:

```
    * lmv_none 适用于无插件情况
    * lmv_text 适用于 text 插件形式
    * lmv_switch 适用于 switch 形式
    * lmv_radio 适用于 radio 形式
    * lmv_select 适用于 select 形式
    * lmv_transition 适用于 transition 形式
```
    
以lmv_text为例,一个如此布局的控件:

```
     <com.knowledge.mnlin.linemenuview.LineMenuView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginTop="@dimen/view_padding_margin_12dp"
            android:background="@color/white_background_5"
            android:paddingEnd="@dimen/view_padding_margin_16dp"
            android:paddingStart="@dimen/view_padding_margin_16dp"
            app:LineMenuView_brief="简要信息"
            app:LineMenuView_brief_text_color="@color/blue"
            app:LineMenuView_brief_text_size="@dimen/text_size_10sp"
            app:LineMenuView_menu="文本大小/颜色/改变"
            app:LineMenuView_menu_text_color="@color/yellow"
            app:LineMenuView_menu_text_size="@dimen/text_size_large_18sp"
            app:LineMenuView_plugin="text"/>
```

可以等效的使用kotlin代替:

```
    //文本大小/颜色/改变
    lmv_text(menuText = "文本大小/颜色/改变", menuTextSizeRes = R.dimen.text_size_large_18sp, menuTextColorRes = R.color.yellow,
            briefText = "简要信息", briefTextColorRes = R.color.blue, briefTextSize = dimen(R.dimen.text_size_10sp)) {
        //自定义初始化操作
    }.lparams(width = matchParent) { topMargin = dp12 }
```

**lmv_text方法的"第一行"参数表示menu的配置,"第二行"参数表示text插件的配置,所有的参数都可不填**

## 三、在Activity中实现LineMenuListener接口

```
    public class TestActivityActivity Activity implements LineMenuView.LineMenuListener {
    
        // ...其他逻辑
    
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
        public boolean performClickRight(TextView v) {
            return false;
        }
    
        /**
         * @param v 被点击到的v;此时应该是该view自身:LineMenuView
         */
        @Override
        public void performSelf(LineMenuView v) {
            int position = ((int) v.getTag(LineMenuView.TAG_POSITION));
            switch (position) {
               //...
            }
        }
    }
```

例如在performSelf方法中，已经通过tag获取到了被点击的控件所在的位置，直接进行逻辑判断处理即可。


经过以上处理方式,可以看到效果图:

`
    ([TestKotlinActivity.kt](app/src/main/java/com/mnlin/linemenuview/activity/TestKotlinActivity.kt)和[TestJavaActivity.java](app/src/main/java/com/mnlin/linemenuview/activity/TestJavaActivity.java))两种方式显示的效果基本是相同的
`

![](public/20180320105048787.gif)


## 四、自定义布局

自定义布局比较方便，直接修改静态属性值，改变布局文件即可。现在使用的layout和id定义在了[LMVConfigs.java](linemenuview/src/main/java/com/knowledge/mnlin/linemenuview/LMVConfigs.java)中

如果使用xml+java方法开发,需要将原有的xml文件进行拷贝(布局中控件id不可修改)，然后再修改，保持整体结构和id值不要变化。




