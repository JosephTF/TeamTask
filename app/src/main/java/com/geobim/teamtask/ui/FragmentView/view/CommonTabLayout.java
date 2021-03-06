package com.geobim.teamtask.ui.FragmentView.view;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.geobim.teamtask.R;
import com.geobim.teamtask.ui.FragmentView.anim.PointEvaluator;
import com.geobim.teamtask.ui.FragmentView.entity.IndicatorPoint;
import com.geobim.teamtask.ui.FragmentView.listener.CustomTabEntity;
import com.geobim.teamtask.ui.FragmentView.listener.OnTabSelectListener;
import com.geobim.teamtask.ui.FragmentView.utils.FragmentChangeManager;
import com.geobim.teamtask.ui.FragmentView.utils.UnreadMsgUtils;
import com.geobim.teamtask.ui.FragmentView.widget.MsgView;

import java.util.ArrayList;

/**
 * 没有继承HorizontalScrollView不能滑动,对于ViewPager无依赖
 */
public class CommonTabLayout extends FrameLayout implements ValueAnimator.AnimatorUpdateListener {
    private Context mContext;
    private ArrayList<CustomTabEntity> mTabEntitys = new ArrayList<>();
    private LinearLayout mTabsContainer;
    private int mCurrentTab;    //选中Tab的position
    private int mLastTab;
    private int mTabCount;
    private IndicatorPoint mCurrentP = new IndicatorPoint();
    private IndicatorPoint mLastP = new IndicatorPoint();
    /**
     * 用于绘制显示器
     */
    private Rect mIndicatorRect = new Rect();
    private GradientDrawable mIndicatorDrawable = new GradientDrawable();

    private Paint mRectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mDividerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mTrianglePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Path mTrianglePath = new Path();


    private boolean mIsFirstDraw = true;
    /**
     * indicator
     */
    private int mIndicatorColor;
    private float mIndicatorHeight;
    private float mIndicatorWidth;
    private float mIndicatorMarginLeft;
    private float mIndicatorMarginTop;
    private float mIndicatorMarginRight;
    private float mIndicatorMarginBottom;
    private float mIndicatorCornerRadius;
    private int mIndicatorGravity;
    private int mIndicatorStyle = STYLE_NORMAL;
    private long mIndicatorAnimDuration;
    private boolean mIndicatorAnimEnable;
    private boolean mIndicatorBounceEnable;
    private static final int STYLE_NORMAL = 0;
    private static final int STYLE_TRIANGLE = 1;
    private static final int STYLE_BLOCK = 2;
    /**
     * underline
     */
    private int mUnderlineColor;
    private float mUnderlineHeight;
    private int mUnderlineGravity;

    /**
     * divider
     */
    private int mDividerColor;
    private float mDividerWidth;
    private float mDividerPadding;

    /**
     * tab
     */
    private float mTabPadding;
    private boolean mTabSpaceEqual;
    private float mTabWidth;

    /**
     * title
     */
    private float mTextsize;
    private int mTextSelectColor;
    private int mTextUnselectColor;
    private boolean mTextBold;
    private boolean mTextAllCaps;

    /**
     * icon
     */
    private float mIconWidth;
    private float mIconHeight;
    private boolean mIconVisible;
    private int mIconGravity;
    private float mIconMargin;

    private int mHeight;

    /**
     * anim
     */
    private ValueAnimator mValueAnimator;
    private OvershootInterpolator mInterpolator = new OvershootInterpolator(1.5f);

    private FragmentChangeManager mFragmentChangeManager;

    public CommonTabLayout(Context context) {
        this(context, null, 0);
    }

    public CommonTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);//重写onDraw方法,需要调用这个方法来清除flag
        setClipChildren(false);
        setClipToPadding(false);

        this.mContext = context;
        mTabsContainer = new LinearLayout(context);
        addView(mTabsContainer);

        obtainAttributes(context, attrs);

        //get layout_height
        String height = attrs.getAttributeValue("http://schemas.android.com/apk/res/android", "layout_height");

        //create ViewPager
        if (height.equals(ViewGroup.LayoutParams.MATCH_PARENT + "")) {
        } else if (height.equals(ViewGroup.LayoutParams.WRAP_CONTENT + "")) {
        } else {
            int[] systemAttrs = {android.R.attr.layout_height};
            TypedArray a = context.obtainStyledAttributes(attrs, systemAttrs);
            mHeight = a.getDimensionPixelSize(0, ViewGroup.LayoutParams.WRAP_CONTENT);
            a.recycle();
        }

        mValueAnimator = ValueAnimator.ofObject(new PointEvaluator(), mLastP, mCurrentP);
        mValueAnimator.addUpdateListener(this);
    }

    /**
     * 获取属性参数
     * @param context
     * @param attrs
     */
    private void obtainAttributes(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CommonTabLayout);
        mIndicatorColor = ta.getColor(R.styleable.CommonTabLayout_tl_indicator_color, Color.parseColor(mIndicatorStyle == STYLE_BLOCK ? "#4B6A87" : "#ffffff"));
        mIndicatorHeight = ta.getDimension(R.styleable.CommonTabLayout_tl_indicator_height, dp2px(mIndicatorStyle == STYLE_TRIANGLE ? 4 : (mIndicatorStyle == STYLE_BLOCK ? -1 : 2)));
        mIndicatorWidth = ta.getDimension(R.styleable.CommonTabLayout_tl_indicator_width, dp2px(mIndicatorStyle == STYLE_TRIANGLE ? 10 : -1));
        mIndicatorMarginLeft = ta.getDimension(R.styleable.CommonTabLayout_tl_indicator_margin_left, dp2px(0));
        mIndicatorMarginTop = ta.getDimension(R.styleable.CommonTabLayout_tl_indicator_margin_top, dp2px(mIndicatorStyle == STYLE_BLOCK ? 7 : 0));
        mIndicatorMarginRight = ta.getDimension(R.styleable.CommonTabLayout_tl_indicator_margin_right, dp2px(0));
        mIndicatorMarginBottom = ta.getDimension(R.styleable.CommonTabLayout_tl_indicator_margin_bottom, dp2px(mIndicatorStyle == STYLE_BLOCK ? 7 : 0));
        mIndicatorCornerRadius = ta.getDimension(R.styleable.CommonTabLayout_tl_indicator_corner_radius, dp2px(mIndicatorStyle == STYLE_BLOCK ? -1 : 0));
        mIndicatorGravity = ta.getInt(R.styleable.CommonTabLayout_tl_indicator_gravity, Gravity.BOTTOM);
        mIndicatorStyle = ta.getInt(R.styleable.CommonTabLayout_tl_indicator_style, 0);
        mIndicatorAnimEnable = ta.getBoolean(R.styleable.CommonTabLayout_tl_indicator_anim_enable, true);
        mIndicatorAnimDuration = ta.getInt(R.styleable.CommonTabLayout_tl_indicator_anim_duration, -1);
        mIndicatorBounceEnable = ta.getBoolean(R.styleable.CommonTabLayout_tl_indicator_bounce_enable, true);

        mUnderlineColor = ta.getColor(R.styleable.CommonTabLayout_tl_underline_color, Color.parseColor("#ffffff"));
        mUnderlineHeight = ta.getDimension(R.styleable.CommonTabLayout_tl_underline_height, dp2px(0));
        mUnderlineGravity = ta.getInt(R.styleable.CommonTabLayout_tl_underline_gravity, Gravity.BOTTOM);

        mDividerColor = ta.getColor(R.styleable.CommonTabLayout_tl_divider_color, Color.parseColor("#ffffff"));
        mDividerWidth = ta.getDimension(R.styleable.CommonTabLayout_tl_divider_width, dp2px(0));
        mDividerPadding = ta.getDimension(R.styleable.CommonTabLayout_tl_divider_padding, dp2px(12));

        mTabPadding = ta.getDimension(R.styleable.CommonTabLayout_tl_tab_padding, mTabSpaceEqual || mTabWidth > 0 ? dp2px(0) : dp2px(10));
        mTabSpaceEqual = ta.getBoolean(R.styleable.CommonTabLayout_tl_tab_space_equal, true);
        mTabWidth = ta.getDimension(R.styleable.CommonTabLayout_tl_tab_width, dp2px(-1));

        mTextsize = ta.getDimension(R.styleable.CommonTabLayout_tl_textsize, sp2px(13f));
        mTextSelectColor = ta.getColor(R.styleable.CommonTabLayout_tl_textSelectColor, Color.parseColor("#ffffff"));
        mTextUnselectColor = ta.getColor(R.styleable.CommonTabLayout_tl_textUnselectColor, Color.parseColor("#AAffffff"));
        mTextBold = ta.getBoolean(R.styleable.CommonTabLayout_tl_textBold, false);
        mTextAllCaps = ta.getBoolean(R.styleable.CommonTabLayout_tl_textAllCaps, false);

        mIconWidth = ta.getDimension(R.styleable.CommonTabLayout_tl_iconWidth, dp2px(0));
        mIconHeight = ta.getDimension(R.styleable.CommonTabLayout_tl_iconHeight, dp2px(0));
        mIconVisible = ta.getBoolean(R.styleable.CommonTabLayout_tl_iconVisible, true);
        mIconGravity = ta.getInt(R.styleable.CommonTabLayout_tl_iconGravity, Gravity.TOP);
        mIconMargin = ta.getDimension(R.styleable.CommonTabLayout_tl_iconMargin, dp2px(2.5f));
        ta.recycle();
    }

    /**
     * 设置选项卡TAB数据
     * @param tabEntitys
     */
    public void setTabData(ArrayList<CustomTabEntity> tabEntitys) {
        if (tabEntitys == null || tabEntitys.size() == 0) {
            throw new IllegalStateException("TabEntitys can not be NULL or EMPTY !");
        }

        this.mTabEntitys.clear();
        this.mTabEntitys.addAll(tabEntitys);

        notifyDataSetChanged();
    }

    /**
     * 关联数据支持同时切换fragments
     */
    public void setTabData(ArrayList<CustomTabEntity> tabEntitys, FragmentActivity fa, int containerViewId, ArrayList<Fragment> fragments) {
        mFragmentChangeManager = new FragmentChangeManager(fa.getSupportFragmentManager(), containerViewId, fragments);
        setTabData(tabEntitys);
    }

    /**
     * 更新数据
     */
    public void notifyDataSetChanged() {
        mTabsContainer.removeAllViews();
        this.mTabCount = mTabEntitys.size();
        View tabView;
        for (int i = 0; i < mTabCount; i++) {
            if (mIconGravity == Gravity.LEFT) {
                tabView = View.inflate(mContext, R.layout.tablayout_tab_left, null);
            } else if (mIconGravity == Gravity.RIGHT) {
                tabView = View.inflate(mContext, R.layout.tablayout_tab_right, null);
            } else if (mIconGravity == Gravity.BOTTOM) {
                tabView = View.inflate(mContext, R.layout.tablayout_tab_bottom, null);
            } else {
                tabView = View.inflate(mContext, R.layout.tablayout_tab_top, null);
            }

            tabView.setTag(i);
            addTab(i, tabView);
        }

        updateTabStyles();
    }

    /**
     * 创建并添加tab
     */
    private void addTab(final int position, View tabView) {
        TextView tv_tab_title = tabView.findViewById(R.id.tv_tab_title);
        tv_tab_title.setText(mTabEntitys.get(position).getTabTitle());
        ImageView iv_tab_icon = tabView.findViewById(R.id.iv_tab_icon);
        iv_tab_icon.setImageResource(mTabEntitys.get(position).getTabUnselectedIcon());
        //第三个Tab隐藏文字，只显示Icon
        if (position == 2) {
            tv_tab_title.setVisibility(View.GONE);
            iv_tab_icon.setScaleType(ImageView.ScaleType.CENTER);
            iv_tab_icon.setPadding(5, 5, 5, 5);
            iv_tab_icon.setLayoutParams(new ViewGroup.LayoutParams(MarginLayoutParams.MATCH_PARENT, MarginLayoutParams.MATCH_PARENT));
        }
        tabView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (Integer) v.getTag();
                if (mCurrentTab != position) {
                    setCurrentTab(position);
                    if (mListener != null) {
                        mListener.onTabSelect(position);
                    }
                } else {
                    if (mListener != null) {
                        mListener.onTabReselect(position);
                    }
                }
            }
        });

        /** 每一个Tab的布局参数 */
        LinearLayout.LayoutParams lp_tab = mTabSpaceEqual ?
                new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1.0f) :
                new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        if (mTabWidth > 0) {
            lp_tab = new LinearLayout.LayoutParams((int) mTabWidth, LayoutParams.MATCH_PARENT);
        }
        mTabsContainer.addView(tabView, position, lp_tab);
    }

    /**
     * 更新Tab外观
     */
    private void updateTabStyles() {
        for (int i = 0; i < mTabCount; i++) {
            View tabView = mTabsContainer.getChildAt(i);
            tabView.setPadding((int) mTabPadding, 0, (int) mTabPadding, 0);
            TextView tv_tab_title =  tabView.findViewById(R.id.tv_tab_title);
            tv_tab_title.setTextColor(i == mCurrentTab ? mTextSelectColor : mTextUnselectColor);
            tv_tab_title.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextsize);
//            tv_tab_title.setPadding((int) mTabPadding, 0, (int) mTabPadding, 0);
            if (mTextAllCaps) {
                tv_tab_title.setText(tv_tab_title.getText().toString().toUpperCase());
            }

            if (mTextBold) {
                tv_tab_title.getPaint().setFakeBoldText(mTextBold);
            }

            ImageView iv_tab_icon =  tabView.findViewById(R.id.iv_tab_icon);
            if (mIconVisible) {
                iv_tab_icon.setVisibility(View.VISIBLE);
                CustomTabEntity tabEntity = mTabEntitys.get(i);
                iv_tab_icon.setImageResource(i == mCurrentTab ? tabEntity.getTabSelectedIcon() : tabEntity.getTabUnselectedIcon());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        mIconWidth <= 0 ? LinearLayout.LayoutParams.MATCH_PARENT : (int) mIconWidth,
                        mIconHeight <= 0 ? LinearLayout.LayoutParams.MATCH_PARENT : (int) mIconHeight);
                if (mIconGravity == Gravity.LEFT) {
                    lp.rightMargin = (int) mIconMargin;
                } else if (mIconGravity == Gravity.RIGHT) {
                    lp.leftMargin = (int) mIconMargin;
                } else if (mIconGravity == Gravity.BOTTOM) {
                    lp.topMargin = (int) mIconMargin;
                } else {
                    lp.bottomMargin = (int) mIconMargin;
                }

                iv_tab_icon.setLayoutParams(lp);
            } else {
                iv_tab_icon.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 更新Tab全部选项卡视图
     * @param position
     */
    private void updateTabSelection(int position) {
        for (int i = 0; i < mTabCount; ++i) {
            View tabView = mTabsContainer.getChildAt(i);
            final boolean isSelect = i == position;
            TextView tab_title =  tabView.findViewById(R.id.tv_tab_title);
            ImageView iv_tab_icon = tabView.findViewById(R.id.iv_tab_icon);
            CustomTabEntity tabEntity = mTabEntitys.get(i);
            tab_title.setTextColor(isSelect ? mTextSelectColor : mTextUnselectColor);
            iv_tab_icon.setImageResource(isSelect ? tabEntity.getTabSelectedIcon() : tabEntity.getTabUnselectedIcon());
        }
    }

    private void calcOffset() {
        final View currentTabView = mTabsContainer.getChildAt(this.mCurrentTab);
        mCurrentP.left = currentTabView.getLeft();
        mCurrentP.right = currentTabView.getRight();

        final View lastTabView = mTabsContainer.getChildAt(this.mLastTab);
        mLastP.left = lastTabView.getLeft();
        mLastP.right = lastTabView.getRight();

        if (mLastP.left == mCurrentP.left && mLastP.right == mCurrentP.right) {
            invalidate();
        } else {
            mValueAnimator.setObjectValues(mLastP, mCurrentP);
            if (mIndicatorBounceEnable) {
                mValueAnimator.setInterpolator(mInterpolator);
            }

            if (mIndicatorAnimDuration < 0) {
                mIndicatorAnimDuration = mIndicatorBounceEnable ? 500 : 250;
            }
            mValueAnimator.setDuration(mIndicatorAnimDuration);
            mValueAnimator.start();
        }
    }

    private void calcIndicatorRect() {
        View currentTabView = mTabsContainer.getChildAt(this.mCurrentTab);
        float left = currentTabView.getLeft();
        float right = currentTabView.getRight();

        mIndicatorRect.left = (int) left;
        mIndicatorRect.right = (int) right;

        if (mIndicatorWidth < 0) {   //indicatorWidth小于0时,原jpardogo's PagerSlidingTabStrip

        } else {//indicatorWidth大于0时,圆角矩形以及三角形
            float indicatorLeft = currentTabView.getLeft() + (currentTabView.getWidth() - mIndicatorWidth) / 2;

            mIndicatorRect.left = (int) indicatorLeft;
            mIndicatorRect.right = (int) (mIndicatorRect.left + mIndicatorWidth);
        }
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        View currentTabView = mTabsContainer.getChildAt(this.mCurrentTab);
        IndicatorPoint p = (IndicatorPoint) animation.getAnimatedValue();
        mIndicatorRect.left = (int) p.left;
        mIndicatorRect.right = (int) p.right;

        if (mIndicatorWidth < 0) {   //indicatorWidth小于0时,原jpardogo's PagerSlidingTabStrip

        } else {//indicatorWidth大于0时,圆角矩形以及三角形
            float indicatorLeft = p.left + (currentTabView.getWidth() - mIndicatorWidth) / 2;

            mIndicatorRect.left = (int) indicatorLeft;
            mIndicatorRect.right = (int) (mIndicatorRect.left + mIndicatorWidth);
        }
        invalidate();
    }

    /**
     * 绘制VIEW
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (isInEditMode() || mTabCount <= 0) {
            return;
        }

        int height = getHeight();
        int paddingLeft = getPaddingLeft();
        // draw divider
        if (mDividerWidth > 0) {
            mDividerPaint.setStrokeWidth(mDividerWidth);
            mDividerPaint.setColor(mDividerColor);
            for (int i = 0; i < mTabCount - 1; i++) {
                View tab = mTabsContainer.getChildAt(i);
                canvas.drawLine(paddingLeft + tab.getRight(), mDividerPadding, paddingLeft + tab.getRight(), height - mDividerPadding, mDividerPaint);
            }
        }

        // draw underline
        if (mUnderlineHeight > 0) {
            mRectPaint.setColor(mUnderlineColor);
            if (mUnderlineGravity == Gravity.BOTTOM) {
                canvas.drawRect(paddingLeft, height - mUnderlineHeight, mTabsContainer.getWidth() + paddingLeft, height, mRectPaint);
            } else {
                canvas.drawRect(paddingLeft, 0, mTabsContainer.getWidth() + paddingLeft, mUnderlineHeight, mRectPaint);
            }
        }

        //draw indicator line
        if (mIndicatorAnimEnable) {
            if (mIsFirstDraw) {
                mIsFirstDraw = false;
                calcIndicatorRect();
            }
        } else {
            calcIndicatorRect();
        }

        if (mIndicatorStyle == STYLE_TRIANGLE) {
            if (mIndicatorHeight > 0) {
                mTrianglePaint.setColor(mIndicatorColor);
                mTrianglePath.reset();
                mTrianglePath.moveTo(paddingLeft + mIndicatorRect.left, height);
                mTrianglePath.lineTo(paddingLeft + mIndicatorRect.left / 2 + mIndicatorRect.right / 2, height - mIndicatorHeight);
                mTrianglePath.lineTo(paddingLeft + mIndicatorRect.right, height);
                mTrianglePath.close();
                canvas.drawPath(mTrianglePath, mTrianglePaint);
            }
        } else if (mIndicatorStyle == STYLE_BLOCK) {
            if (mIndicatorHeight < 0) {
                mIndicatorHeight = height - mIndicatorMarginTop - mIndicatorMarginBottom;
            } else {

            }
            if (mIndicatorHeight > 0) {
                if (mIndicatorCornerRadius < 0 || mIndicatorCornerRadius > mIndicatorHeight / 2) {
                    mIndicatorCornerRadius = mIndicatorHeight / 2;
                }

                mIndicatorDrawable.setColor(mIndicatorColor);
                mIndicatorDrawable.setBounds(paddingLeft + (int) mIndicatorMarginLeft + mIndicatorRect.left,
                        (int) mIndicatorMarginTop, (int) (paddingLeft + mIndicatorRect.right - mIndicatorMarginRight),
                        (int) (mIndicatorMarginTop + mIndicatorHeight));
                mIndicatorDrawable.setCornerRadius(mIndicatorCornerRadius);
                mIndicatorDrawable.draw(canvas);
            }
        } else {
               /* mRectPaint.setColor(mIndicatorColor);
                calcIndicatorRect();
                canvas.drawRect(getPaddingLeft() + mIndicatorRect.left, getHeight() - mIndicatorHeight,
                        mIndicatorRect.right + getPaddingLeft(), getHeight(), mRectPaint);*/
            if (mIndicatorHeight > 0) {
                mIndicatorDrawable.setColor(mIndicatorColor);
                if (mIndicatorGravity == Gravity.BOTTOM) {
                    mIndicatorDrawable.setBounds(paddingLeft + (int) mIndicatorMarginLeft + mIndicatorRect.left,
                            height - (int) mIndicatorHeight - (int) mIndicatorMarginBottom,
                            paddingLeft + mIndicatorRect.right - (int) mIndicatorMarginRight,
                            height - (int) mIndicatorMarginBottom);
                } else {
                    mIndicatorDrawable.setBounds(paddingLeft + (int) mIndicatorMarginLeft + mIndicatorRect.left,
                            (int) mIndicatorMarginTop,
                            paddingLeft + mIndicatorRect.right - (int) mIndicatorMarginRight,
                            (int) mIndicatorHeight + (int) mIndicatorMarginTop);
                }
                mIndicatorDrawable.setCornerRadius(mIndicatorCornerRadius);
                mIndicatorDrawable.draw(canvas);
            }
        }
    }

    /**
     * Setter & Getter
     */
    //当前Tab选项卡的position
    public void setCurrentTab(int currentTab) {
        mLastTab = this.mCurrentTab;
        this.mCurrentTab = currentTab;
        updateTabSelection(currentTab);
        if (mFragmentChangeManager != null) {
            mFragmentChangeManager.setFragments(currentTab);
        }
        if (mIndicatorAnimEnable) {
            calcOffset();
        } else {
            invalidate();
        }
    }

    public int getCurrentTab() {
        return mCurrentTab;
    }

    //滑块颜色
    public void setIndicatorColor(int indicatorColor) {
        this.mIndicatorColor = indicatorColor;
        invalidate();
    }

    public int getIndicatorColor() {
        return mIndicatorColor;
    }

    //滑块高度
    public void setIndicatorHeight(float indicatorHeight) {
        this.mIndicatorHeight = dp2px(indicatorHeight);
        invalidate();
    }

    public float getIndicatorHeight() {
        return mIndicatorHeight;
    }

    //滑块宽度
    public void setIndicatorWidth(float indicatorWidth) {
        this.mIndicatorWidth = dp2px(indicatorWidth);
        invalidate();
    }

    public float getIndicatorWidth() {
        return mIndicatorWidth;
    }

    //滑块Margin值
    public void setIndicatorMargin(float indicatorMarginLeft, float indicatorMarginTop,
                                   float indicatorMarginRight, float indicatorMarginBottom) {
        this.mIndicatorMarginLeft = dp2px(indicatorMarginLeft);
        this.mIndicatorMarginTop = dp2px(indicatorMarginTop);
        this.mIndicatorMarginRight = dp2px(indicatorMarginRight);
        this.mIndicatorMarginBottom = dp2px(indicatorMarginBottom);
        invalidate();
    }

    public float getIndicatorMarginLeft() {
        return mIndicatorMarginLeft;
    }

    public float getIndicatorMarginTop() {
        return mIndicatorMarginTop;
    }

    public float getIndicatorMarginRight() {
        return mIndicatorMarginRight;
    }

    public float getIndicatorMarginBottom() {
        return mIndicatorMarginBottom;
    }

    //滑块圆角值
    public void setIndicatorCornerRadius(float indicatorCornerRadius) {
        this.mIndicatorCornerRadius = dp2px(indicatorCornerRadius);
        invalidate();
    }

    public float getIndicatorCornerRadius() {
        return mIndicatorCornerRadius;
    }

    //滑块在导航栏的顶部或底部
    public void setIndicatorGravity(int indicatorGravity) {
        this.mIndicatorGravity = indicatorGravity;
        invalidate();
    }

    public int getmIndicatorGravity() {
        return mIndicatorGravity;
    }


    //滑块样式 0常规|1三角形|2背景色块
    public void setIndicatorStyle(int indicatorStyle) {
        this.mIndicatorStyle = indicatorStyle;
        invalidate();
    }

    public int getIndicatorStyle() {
        return mIndicatorStyle;
    }

    //滑块是否支持动画
    public void setIndicatorAnimEnable(boolean indicatorAnimEnable) {
        this.mIndicatorAnimEnable = indicatorAnimEnable;
    }

    public boolean isIndicatorAnimEnable() {
        return mIndicatorAnimEnable;
    }

    //滑块动画时间
    public void setIndicatorAnimDuration(long indicatorAnimDuration) {
        this.mIndicatorAnimDuration = indicatorAnimDuration;
    }

    public long getIndicatorAnimDuration() {
        return mIndicatorAnimDuration;
    }

    //滑块是否支持动画回弹效果
    public void setIndicatorBounceEnable(boolean indicatorBounceEnable) {
        this.mIndicatorBounceEnable = indicatorBounceEnable;
    }

    public boolean isIndicatorBounceEnable() {
        return mIndicatorBounceEnable;
    }

    //底部下划线颜色
    public void setUnderlineColor(int underlineColor) {
        this.mUnderlineColor = underlineColor;
        invalidate();
    }

    public int getUnderlineColor() {
        return mUnderlineColor;
    }

    //底部下划线高度
    public void setUnderlineHeight(float underlineHeight) {
        this.mUnderlineHeight = dp2px(underlineHeight);
        invalidate();
    }

    public float getUnderlineHeight() {
        return mUnderlineHeight;
    }

    //下划线顶部还是底部 48：TOP，80：BOTTOM
    public void setUnderlineGravity(int underlineGravity) {
        this.mUnderlineGravity = underlineGravity;
        invalidate();
    }

    public int getmUnderlineGravity() {
        return mUnderlineGravity;
    }

    //选项卡之间分割线颜色
    public void setDividerColor(int dividerColor) {
        this.mDividerColor = dividerColor;
        invalidate();
    }

    public int getDividerColor() {
        return mDividerColor;
    }

    //选项卡之间分割线宽度
    public void setDividerWidth(float dividerWidth) {
        this.mDividerWidth = dp2px(dividerWidth);
        invalidate();
    }

    public float getDividerWidth() {
        return mDividerWidth;
    }

    //选项卡之间分割线边距
    public void setDividerPadding(float dividerPadding) {
        this.mDividerPadding = dp2px(dividerPadding);
        invalidate();
    }

    public float getDividerPadding() {
        return mDividerPadding;
    }

    //Tab选项卡边距
    public void setTabPadding(float tabPadding) {
        this.mTabPadding = dp2px(tabPadding);
        updateTabStyles();
    }

    public float getTabPadding() {
        return mTabPadding;
    }

    //Tab选项卡大小是否等分
    public void setTabSpaceEqual(boolean tabSpaceEqual) {
        this.mTabSpaceEqual = tabSpaceEqual;
        updateTabStyles();
    }

    public boolean isTabSpaceEqual() {
        return mTabSpaceEqual;
    }

    //Tab选项卡宽度
    public void setTabWidth(float tabWidth) {
        this.mTabWidth = dp2px(tabWidth);
        updateTabStyles();
    }

    public float getTabWidth() {
        return mTabWidth;
    }

    //文字大小
    public void setTextsize(float textsize) {
        this.mTextsize = sp2px(textsize);
        updateTabStyles();
    }

    public float getTextsize() {
        return mTextsize;
    }

    //文字选中颜色
    public void setTextSelectColor(int textSelectColor) {
        this.mTextSelectColor = textSelectColor;
        updateTabStyles();
    }

    public int getTextSelectColor() {
        return mTextSelectColor;
    }

    //文字未选中颜色
    public void setTextUnselectColor(int textUnselectColor) {
        this.mTextUnselectColor = textUnselectColor;
        updateTabStyles();
    }

    public int getTextUnselectColor() {
        return mTextUnselectColor;
    }

    //文字加粗
    public void setTextBold(boolean textBold) {
        this.mTextBold = textBold;
        updateTabStyles();
    }

    public boolean isTextBold() {
        return mTextBold;
    }

    //文字全部大写
    public void setTextAllCaps(boolean textAllCaps) {
        this.mTextAllCaps = textAllCaps;
        updateTabStyles();
    }

    public boolean isTextAllCaps() {
        return mTextAllCaps;
    }

    //icon宽度
    public void setIconWidth(float iconWidth) {
        this.mIconWidth = dp2px(iconWidth);
        updateTabStyles();
    }

    public float getIconWidth() {
        return mIconWidth;
    }

    //icon高度
    public void setIconHeight(float iconHeight) {
        this.mIconHeight = dp2px(iconHeight);
        updateTabStyles();
    }

    public float getIconHeight() {
        return mIconHeight;
    }

    //icon是否可见
    public void setIconVisible(boolean iconVisible) {
        this.mIconVisible = iconVisible;
        updateTabStyles();
    }

    public boolean isIconVisible() {
        return mIconVisible;
    }

    //icon显示位置
    public void setIconGravity(int iconGravity) {
        this.mIconGravity = iconGravity;
        notifyDataSetChanged();
    }

    public int getIconGravity() {
        return mIconGravity;
    }

    //icon与文字间距
    public void setIconMargin(float iconMargin) {
        this.mIconMargin = dp2px(iconMargin);
        updateTabStyles();
    }

    public float getIconMargin() {
        return mIconMargin;
    }

    /**
     * 获取选项卡数目
     *
     * @return
     */
    public int getTabCount() {
        return mTabCount;
    }

    /**
     * 获取选项卡图标
     *
     * @param tab
     * @return
     */
    public ImageView getIconView(int tab) {
        View tabView = mTabsContainer.getChildAt(tab);
        ImageView iv_tab_icon = tabView.findViewById(R.id.iv_tab_icon);
        return iv_tab_icon;
    }

    /**
     * 获取选项卡文字
     *
     * @param tab
     * @return
     */
    public TextView getTitleView(int tab) {
        View tabView = mTabsContainer.getChildAt(tab);
        TextView tv_tab_title = tabView.findViewById(R.id.tv_tab_title);
        return tv_tab_title;
    }

    /**
     * 显示未读消息
     *
     * @param position 显示位置
     * @param num      小于等于0显示红点,大于0显示数字,大于99显示99+
     */
    public void showMsg(int position, int num) {
        //防止参数越界
        if (position >= mTabCount) {
            position = mTabCount - 1;
        }

        View tabView = mTabsContainer.getChildAt(position);
        MsgView tipView = tabView.findViewById(R.id.rtv_msg_tip);
        if (tipView != null) {
            UnreadMsgUtils.show(tipView, num);
            SparseArray<Boolean> mInitSetMap = new SparseArray<>();
            if (mInitSetMap.get(position) != null && mInitSetMap.get(position)) {
                return;
            }

            if (!mIconVisible) {
                setMsgMargin(position, 2, 2);
            } else {
                setMsgMargin(position, 0,
                        mIconGravity == Gravity.LEFT || mIconGravity == Gravity.RIGHT ? 4 : 0);
            }

            mInitSetMap.put(position, true);
        }
    }

    /**
     * 隐藏未读信息
     *
     * @param position 隐藏选项卡的位置
     */
    public void hideMsg(int position) {
        if (position >= mTabCount) {
            position = mTabCount - 1;
        }

        View tabView = mTabsContainer.getChildAt(position);
        MsgView tipView = (MsgView) tabView.findViewById(R.id.rtv_msg_tip);
        if (tipView != null) {
            tipView.setVisibility(View.GONE);
        }
    }

    /**
     * 设置红点偏移
     * 1.控件为固定高度:参照点为tab内容的右上角
     * 2.控件高度不固定(WRAP_CONTENT):参照点为tab内容的右上角,此时高度已是红点的最高显示范围,所以这时bottomPadding其实就是topPadding
     */
    public void setMsgMargin(int position, float leftPadding, float bottomPadding) {
        if (position >= mTabCount) {
            position = mTabCount - 1;
        }
        View tabView = mTabsContainer.getChildAt(position);
        MsgView tipView = (MsgView) tabView.findViewById(R.id.rtv_msg_tip);
        if (tipView != null) {
            TextView tv_tab_title = (TextView) tabView.findViewById(R.id.tv_tab_title);
            Paint mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);    //未读数目字体
            mTextPaint.setTextSize(mTextsize);
            float textWidth = mTextPaint.measureText(tv_tab_title.getText().toString());
            float textHeight = mTextPaint.descent() - mTextPaint.ascent();
            MarginLayoutParams lp = (MarginLayoutParams) tipView.getLayoutParams();

            float iconH = mIconHeight;
            float margin = 0;
            if (mIconVisible) {
                if (iconH <= 0) {
                    iconH = mContext.getResources().getDrawable(mTabEntitys.get(position).getTabSelectedIcon()).getIntrinsicHeight();
                }
                margin = mIconMargin;
            }

            if (mIconGravity == Gravity.TOP || mIconGravity == Gravity.BOTTOM) {
                lp.leftMargin = dp2px(leftPadding);
                lp.topMargin = mHeight > 0 ? (int) (mHeight - textHeight - iconH - margin) / 2 - dp2px(bottomPadding) : dp2px(bottomPadding);
            } else {
                lp.leftMargin = dp2px(leftPadding);
                lp.topMargin = mHeight > 0 ? (int) (mHeight - Math.max(textHeight, iconH)) / 2 - dp2px(bottomPadding) : dp2px(bottomPadding);
            }

            tipView.setLayoutParams(lp);
        }
    }

    /**
     * 获取MsgView
     * 当前类只提供了少许设置未读消息属性的方法,可以通过该方法获取MsgView对象从而各种设置
     */
    public MsgView getMsgView(int position) {
        if (position >= mTabCount) {
            position = mTabCount - 1;
        }
        View tabView = mTabsContainer.getChildAt(position);
        MsgView tipView = (MsgView) tabView.findViewById(R.id.rtv_msg_tip);
        return tipView;
    }

    private OnTabSelectListener mListener;

    public void setOnTabSelectListener(OnTabSelectListener listener) {
        this.mListener = listener;
    }


    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("instanceState", super.onSaveInstanceState());
        bundle.putInt("mCurrentTab", mCurrentTab);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            mCurrentTab = bundle.getInt("mCurrentTab");
            state = bundle.getParcelable("instanceState");
            if (mCurrentTab != 0 && mTabsContainer.getChildCount() > 0) {
                updateTabSelection(mCurrentTab);
            }
        }
        super.onRestoreInstanceState(state);
    }

    /**
     * View DP转PX
     */
    protected int dp2px(float dp) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }


    /**
     * 文字 SP转PX
     */
    protected int sp2px(float sp) {
        final float scale = this.mContext.getResources().getDisplayMetrics().scaledDensity;
        return (int) (sp * scale + 0.5f);
    }
}
