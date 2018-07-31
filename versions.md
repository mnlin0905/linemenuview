# LineMenuView更新记录

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