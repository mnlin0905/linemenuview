package com.knowledge.mnlin.linemenuview;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.AttributeSet;

/**
 * Created by admin on 2017/4/28.
 * <p>
 * 可以实现跑马灯效果的TextView
 */

public class MTextView extends AppCompatTextView {
    public MTextView(Context context) {
        this(context, null);
    }

    public MTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //设置跑马灯效果
        setEllipsize(TextUtils.TruncateAt.MARQUEE);
        setSingleLine();
        setMarqueeRepeatLimit(-1);
    }

    @Override
    public boolean isFocused() {
        return true;
    }
}
