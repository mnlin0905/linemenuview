# LineMenuView更新记录

# V_2.0.0

 * 语言切换为kotlin
 * setMenuColor方法删除,可使用setMenuTextColor方法
 * [MTextView](linemenuview/src/main/java/com/knowledge/mnlin/linemenuview/MTextView.java)类只针对包内可见
 * LineMenuListener监听器添加注解(适应kotlin调用),同时修改performClickRight方法参数(TextView ->  View),接口不再是内部类
 * 移除 setBrief(brief: Int) 方法,避免与setBrief(brief: String?)混淆
 * 移除 getBrief方法(事实上并不经常使用),改用 getBriefText()
 * 获取position时使用的id与layout等默认资源名,转移到**LMVConfigs类**中,方便进行全局修改
 * 添加anko使用,(lmv_***)系列,源码位于**[AnkoCreate.kt](linemenuview/src/main/java/com/knowledge/mnlin/linemenuview/AnkoCreate.kt)**
 
    * lmv_none 适用于无插件情况
    * lmv_text 适用于 text 插件形式
    * lmv_switch 适用于 switch 形式
    * lmv_radio 适用于 radio 形式
    * lmv_select 适用于 select 形式
    * lmv_transition 适用于 transition 形式
    
 * 添加 setClickSelfListener , setClickLeftListener , setClickRightListener 方法用于快速注册监听器,但多次调用会相互覆盖(源码位于**[AnkoCreate](linemenuview/src/main/java/com/knowledge/mnlin/linemenuview/AnkoCreate.kt)**中)
 * 若像以前一般使用java+xml方式使用该库,则可参考 [TestJavaActivity源码](app/src/main/java/com/mnlin/linemenuview/activity/TestJavaActivity.java)
 * 若使用anko方法使用该库,则可参考 [TestKotlinActivity源码](app/src/main/java/com/mnlin/linemenuview/activity/TestKotlinActivity.kt)

# V_1.1.0

 * 修改id命名方式,防止在复杂布局中findViewById时混乱
 
    * tv_menu -> tv_menu_line_menu_view
    * tv_brief -> tv_brief_info_line_menu_view  
    * sc_switch -> sc_switch_line_menu_view
    * rb_check -> rb_check_line_menu_view
    * iv_image -> iv_image_line_menu_view
    * icon_open -> icon_open_close_line_menu_view
 
# V_1.0.6

 * 针对 menu 和 brief 位置文本添加get方法
 * 当xml中 LineMenuView_for_calculation 为 off 时, 默认不再自动绑定 onClickListener
 
# V_1.0.5
 
 * 设定 LineMenuListener 中所有方法为 default 类型,提供默认实现,不强制要求实现
 
# V_1.*.*

 * 未记录(功能可查看博客内容)