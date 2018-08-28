package com.knowledge.mnlin.linemenuview

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.TransitionDrawable
import android.support.annotation.ColorInt
import android.support.annotation.IntRange
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SwitchCompat
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.knowledge.mnlin.linemenuview.LMVConfigs.*
import java.util.*


/**
 * function : 自定义横向的menu菜单
 *
 * Created on 2018/8/27  18:08
 * @author mnlin
 */
open class LineMenuView
@JvmOverloads constructor(context: Context,
                          private val attrs: AttributeSet? = null,
                          defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), Cloneable, View.OnClickListener {

    /**
     * 手指点击的xy位置
     */
    private var clickX: Float = 0.toFloat()
    private var clickY: Float = 0.toFloat()

    /**
     * 左侧菜单按钮
     * 右侧消息菜单
     */
    private var mTvMenu: TextView
    private var mTvBriefInfo: TextView
    private var mScSwitch: SwitchCompat
    private var mRbCheck: RadioButton
    private var mIvImage: ImageView
    private var mIconOpenClose: ImageView

    private var listener: LineMenuListener? = null

    /**
     * true表示on状态
     */
    private var _transition: Boolean = false

    /**
     * 是否纳入计算体系
     *
     *
     * 0 不处理
     * 1 纳入
     * 2 不纳入
     */
    private var calculation: Int = 0

    /**
     * 如果child的calculation为on,则纳入统计
     * 如果child的calculation为default,且child可见,则纳入统计
     * 否则不会纳入统计
     *
     * @return 返回当前LineMenuView所在的兄弟布局(同一个viewParent)
     */
    fun friendsWithSelf(): List<LineMenuView> {
        val childs = LinkedList<LineMenuView>()

        val parent = parent as ViewGroup?
        if (parent == null) {
            childs.add(this)
        } else {
            val childCount = parent.childCount
            var child: View
            for (i in 0 until childCount) {
                child = parent.getChildAt(i)
                if (child is LineMenuView && (child.calculation == 1 || child.calculation == 0 && child.getVisibility() == View.VISIBLE)) {
                    childs.add(child)
                }
            }
        }
        return childs
    }

    /**
     * 所有可见不可见的child都会被纳入统计
     *
     * @return 返回当前LineMenuView所在的兄弟布局(同一个viewParent)
     */
    fun friendsEveryOne(): List<LineMenuView> {
        val childs = LinkedList<LineMenuView>()

        val parent = parent as ViewGroup?
        if (parent == null) {
            childs.add(this)
        } else {
            val childCount = parent.childCount
            var child: View
            for (i in 0 until childCount) {
                child = parent.getChildAt(i)
                if (child is LineMenuView) {
                    childs.add(child)
                }
            }
        }
        return childs
    }

    init {
        //加载布局
        View.inflate(context, LAYOUT_SELF, this)
        setParam()
        super.setOnClickListener(this)
        mTvMenu = findViewById(R.id.tv_menu_line_menu_view)
        mTvBriefInfo = findViewById(R.id.tv_brief_info_line_menu_view)
        mScSwitch = findViewById(R.id.sc_switch_line_menu_view)
        mRbCheck = findViewById(R.id.rb_check_line_menu_view)
        mIvImage = findViewById(R.id.iv_image_line_menu_view)
        mIconOpenClose = findViewById(R.id.icon_open_close_line_menu_view)

        //处理逻辑
        initData()
    }


    /**
     * 设置类自身必要的参数
     */
    protected open fun setParam() {
        gravity = Gravity.CENTER_VERTICAL
        orientation = LinearLayout.HORIZONTAL
        if (minimumHeight <= 0) {
            //只有获知最小高度未赋值时,才进行处理
            minimumHeight = resources.getDimensionPixelSize(R.dimen.prefer_view_height_48dp)
        }
    }

    /**
     * 处理xml属性
     */
    private fun initData() {
        val params = context.obtainStyledAttributes(attrs, R.styleable.LineMenuView)

        //查看当前是否需要显示switch,默认显示为off状态
        //查看当前icon资源文件，如果未设定则默认不显示
        //获取menu需要显示的内容
        var resId: Int
        for (i in params.indexCount - 1 downTo 0) {
            resId = params.getIndex(i)
            if (resId == R.styleable.LineMenuView_LineMenuView_plugin) {
                setPlugin(params.getInt(resId, 0))

                //当transition处于显示状态,进行显示/隐藏处理
            } else if (resId == R.styleable.LineMenuView_LineMenuView_transition) {
                val switchValue = params.getInt(resId, 0)
                when (switchValue) {
                    1 -> {
                        val drawable = mIconOpenClose.drawable as TransitionDrawable
                        drawable.isCrossFadeEnabled = true
                        _transition = true
                        drawable.startTransition(0)
                    }
                }

                //当switch处于显示状态,进行显示/隐藏处理
            } else if (resId == R.styleable.LineMenuView_LineMenuView_switch) {
                val switchValue = params.getInt(resId, 0)
                when (switchValue) {
                    1 -> {
                        mScSwitch.isChecked = true
                    }
                    0 -> {
                        mScSwitch.isChecked = false
                    }
                }

                //是否纳入计算体系
            } else if (resId == R.styleable.LineMenuView_LineMenuView_for_calculation) {
                calculation = params.getInt(resId, 0)

                //当radio处于显示状态,进行显示/隐藏处理
            } else if (resId == R.styleable.LineMenuView_LineMenuView_radio) {
                val radioValue = params.getInt(resId, 0)
                when (radioValue) {
                    1 -> mRbCheck.isChecked = true
                    0 -> mRbCheck.isChecked = false
                }

                //处理badge图标
            } else if (resId == R.styleable.LineMenuView_LineMenuView_badge) {
                val d = params.getDrawable(resId)
                if (d != null) {
                    d.setBounds(0, 0, Math.min(d.minimumWidth, MAX_PICTURE_SIZE), Math.min(d.minimumHeight, MAX_PICTURE_SIZE))
                    val compoundDrawables = mTvBriefInfo.compoundDrawables
                    mTvBriefInfo.setCompoundDrawables(d, compoundDrawables[1], compoundDrawables[2], compoundDrawables[3])
                }

                //处理icon图标
            } else if (resId == R.styleable.LineMenuView_LineMenuView_icon) {
                val d = params.getDrawable(resId)
                if (d != null) {
                    d.setBounds(0, 0, Math.min(d.minimumWidth, MAX_PICTURE_SIZE), Math.min(d.minimumHeight, MAX_PICTURE_SIZE))
                    val compoundDrawables = mTvMenu.compoundDrawables
                    mTvMenu.setCompoundDrawables(d, compoundDrawables[1], compoundDrawables[2], compoundDrawables[3])
                }

                //处理navigation图标
            } else if (resId == R.styleable.LineMenuView_LineMenuView_navigation) {
                val d = params.getDrawable(resId)
                if (d != null) {
                    d.setBounds(0, 0, Math.min(d.minimumWidth, MAX_PICTURE_SIZE), Math.min(d.minimumHeight, MAX_PICTURE_SIZE))
                    val compoundDrawables = mTvBriefInfo.compoundDrawables
                    mTvBriefInfo.setCompoundDrawables(compoundDrawables[0], compoundDrawables[1], d, compoundDrawables[3])
                }

                //处理menu标题
            } else if (resId == R.styleable.LineMenuView_LineMenuView_menu) {
                mTvMenu.text = params.getString(resId)

                //处理brief标题
            } else if (resId == R.styleable.LineMenuView_LineMenuView_brief) {
                mTvBriefInfo.text = params.getString(resId)

                //处理menu标题
            } else if (resId == R.styleable.LineMenuView_LineMenuView_menu_text_color) {
                mTvMenu.setTextColor(params.getColorStateList(resId))

                //处理brief标题
            } else if (resId == R.styleable.LineMenuView_LineMenuView_brief_text_color) {
                mTvBriefInfo.setTextColor(params.getColorStateList(resId))

                //处理menu标题
            } else if (resId == R.styleable.LineMenuView_LineMenuView_menu_text_size) {
                mTvMenu.setTextSize(TypedValue.COMPLEX_UNIT_PX, params.getDimensionPixelSize(resId, mTvMenu.textSize.toInt()).toFloat())

                //处理brief标题
            } else if (resId == R.styleable.LineMenuView_LineMenuView_brief_text_size) {
                mTvBriefInfo.setTextSize(TypedValue.COMPLEX_UNIT_PX, params.getDimensionPixelSize(resId, mTvMenu.textSize.toInt()).toFloat())
            }
        }

        //如果发现右侧的文本drawable设置了两个,则判断文字是否存在,不存在则将drawablePadding设置一半
        val compoundDrawables = mTvBriefInfo.compoundDrawables
        val count = (if (compoundDrawables[0] == null) 0 else 1) + if (compoundDrawables[0] == null) 0 else 1
        if (count > 1) {
            mTvBriefInfo.compoundDrawablePadding = mTvBriefInfo.compoundDrawablePadding / 2
        }

        mIvImage.drawable.setVisible(false, false)

        params.recycle()

        /**
         * 延迟调用set监听
         */
        post {
            //如果当前view所在的context对象声明了该接口,那么就直接进行绑定,如果未纳入计数体系,则不会自动添加listener
            if (context is LineMenuListener && setListenerIsSelf() && calculation != 2 && this.listener == null) {
                setOnClickListener(context as LineMenuListener)
            }
        }
    }

    /**
     * 是否设置onClickLisener为自身this
     *
     * @return true表示设置
     */
    protected open fun setListenerIsSelf(): Boolean {
        return true
    }

    ///////////////////////////////////////// 设置属性值方法-begin

    /**
     * 设置是否纳入计数
     *
     * 0:默认操作,由系统自行判断
     * 1:确认纳入计数体系,即便控件自身不可见
     * 2:确认不纳入计数体系,此时系统默认不设置监听器(需要及早调用,否则可不能不起作用)
     */
    fun setCalculation(@IntRange(from = 0, to = 2) calculation: Int) {
        this.calculation = calculation
    }

    /**
     * @param plugin 0 表示不显示任何插件
     * 1 表示显示textView
     * 2 表示显示switch
     * 3 表示显示radio
     * 4 表示select
     * 5 表示transition
     */
    fun setPlugin(plugin: Int): LineMenuView {
        mTvBriefInfo.visibility = if (plugin == 1) View.VISIBLE else View.GONE
        mScSwitch.visibility = if (plugin == 2) View.VISIBLE else View.GONE
        mRbCheck.visibility = if (plugin == 3) View.VISIBLE else View.GONE
        mIvImage.visibility = if (plugin == 4) View.VISIBLE else View.GONE
        mIconOpenClose.visibility = if (plugin == 5) View.VISIBLE else View.GONE
        return this
    }

    fun setIcon(d: Drawable?): LineMenuView {
        if (d != null) {
            d.setBounds(0, 0, Math.min(d.minimumWidth, MAX_PICTURE_SIZE), Math.min(d.minimumHeight, MAX_PICTURE_SIZE))
            val compoundDrawables = mTvMenu.compoundDrawables
            mTvMenu.setCompoundDrawables(d, compoundDrawables[1], compoundDrawables[2], compoundDrawables[3])
        }
        return this
    }

    fun setMenuTextColor(color: Int): LineMenuView {
        mTvMenu.setTextColor(color)
        return this
    }

    fun setMenuTextSize(unit: Int, size: Int): LineMenuView {
        mTvMenu.setTextSize(unit, size.toFloat())
        return this
    }

    fun setBriefSize(unit: Int, size: Int): LineMenuView {
        mTvBriefInfo.setTextSize(unit, size.toFloat())
        return this
    }

    fun setNavigation(drawable: Drawable): LineMenuView {
        val compoundDrawables = mTvBriefInfo.compoundDrawables
        mTvBriefInfo.setCompoundDrawables(compoundDrawables[0], compoundDrawables[1], drawable, compoundDrawables[3])
        return this
    }

    fun setBadge(drawable: Drawable): LineMenuView {
        val compoundDrawables = mTvBriefInfo.compoundDrawables
        drawable.setBounds(0, 0, MAX_PICTURE_SIZE, MAX_PICTURE_SIZE)
        mTvBriefInfo.setCompoundDrawables(drawable, compoundDrawables[1], compoundDrawables[2], compoundDrawables[3])
        return this
    }

    fun setBriefColor(@ColorInt color: Int): LineMenuView {
        mTvBriefInfo.setTextColor(color)
        return this
    }

    /**
     * 后端变量
     *
     * switch切换状态
     * radio状态
     * select状态
     * transition状态
     *
     * menuText 文本信息
     * briefText 文本
     */
    var switch: Boolean
        get() = mScSwitch.isChecked
        set(value) {
            mScSwitch.isChecked = value
        }

    var radio: Boolean
        get() = mRbCheck.isChecked
        set(value) {
            mRbCheck.isChecked = value
        }

    var rightSelect: Boolean
        get() = mIvImage.visibility == View.VISIBLE
        set(value) {
            mIvImage.visibility = if (value) View.VISIBLE else View.INVISIBLE
        }

    var transition: Boolean
        get() = _transition
        set(value) {
            if (value != _transition) {
                val drawable = mIconOpenClose.drawable as TransitionDrawable
                drawable.isCrossFadeEnabled = true
                if (value) {
                    drawable.startTransition(DEFAULT_ANIMATOR_TIME.toInt())
                } else {
                    drawable.reverseTransition(DEFAULT_ANIMATOR_TIME.toInt())
                }
                _transition = value
            }
        }

    var menuText: String?
        get() = mTvMenu.text.toString()
        set(value) {
            mTvMenu.text = value
        }

    var briefText: String?
        get() = mTvBriefInfo.text.toString()
        set(value) {
            mTvBriefInfo.text = value
        }

    //////////////////////////////////////////设置属性值方法-end

    /**
     * 如果当前有对自身的监听事件
     * 则在手指触摸屏幕时就保存手指坐标
     */
    override fun onTouchEvent(event: MotionEvent): Boolean {
        //若监听了自身view,则记录手指抬起时的坐标
        if (listener != null && event.action == MotionEvent.ACTION_UP) {
            clickX = event.x
            clickY = event.y
        }
        return super.onTouchEvent(event)
    }

    /**
     * 拦截子类的事件处理
     */
    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return true
    }

    /**
     * 处理点击事件,如果LineMenuView没有被外部重写clickListener接口,则可以判断是右侧还是左侧的文字相应点击事件
     *
     *
     * 如果是放在AdapterView中的话,可以在onItemClick时,通过performClick方法让onclick得以执行,当然,可以在此之前通过setTag(int key)方法将position信息传递过来,这样就可以实现具体的点击事件处理
     *
     *
     * 点击判断是以X轴坐标来判断的,可以通过重写onClick方法实现不同的逻辑
     */
    override fun onClick(v: View) {
        //如果没有被监听,则不做任何处理
        if (listener == null) {
            return
        }

        //判断手指抬起时的位置,由此来判断是由左侧或者右侧的文本相应点击事件
        val dividerX = mTvMenu.right
        val consume: Boolean
        val setPosition = setTagPosition(parent as ViewGroup)
        val position = friendsWithSelf().indexOf(this)
        if (clickX <= dividerX) {
            mTvMenu.setTag(TAG_POSITION, if (setPosition) position else -1)
            consume = listener!!.performClickLeft(mTvMenu)
        } else {
            mTvBriefInfo.setTag(TAG_POSITION, if (setPosition) position else -1)
            consume = listener!!.performClickRight(mTvBriefInfo)
        }

        //如果都没有被消费,则默认由performSelf来处理
        if (!consume) {
            this.setTag(TAG_POSITION, if (setPosition) position else -1)
            listener!!.performSelf(this)
        }
    }

    /**
     * 是否有必须在tag中设置位置信息
     *
     *
     * 如果为true那么可以通过getTag方法获取到当前this在父布局中所处的位置
     * 如果为false，那么位置就是-1（避免真正使用时的空指针异常）
     *
     * @param parent 父布局
     * @return true表示有必要
     */
    private fun setTagPosition(parent: ViewGroup?): Boolean {
        return parent != null && parent !is AdapterView<*> && parent !is RecyclerView
    }

    /**
     * 屏蔽点击事件的设置,自身会处理点击事件
     *
     *
     * 该方法不可调用,因为调用也不会有任何操作
     */
    @Deprecated(message = "当前不能直接设置onClick监听", replaceWith = ReplaceWith(expression = "performSelf()", imports = arrayOf("com.knowledge.mnlin.linemenuview.LineMenuListener")))
    override fun setOnClickListener(onClickListener: View.OnClickListener?) {
        throw UnsupportedOperationException("不支持设置OnClick监听")
    }

    /**
     * 重写父类的onclick处理方法
     */
    public fun setOnClickListener(listener: LineMenuListener?) {
        this.listener = listener
    }
}


