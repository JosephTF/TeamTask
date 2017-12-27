package com.geobim.teamtask.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.assistne.icondottextview.IconDotTextView;
import com.geobim.teamtask.R;
import com.geobim.teamtask.util.statusbar.StatusBarUtil;

/**
 * 快速创建Activity
 * Created by Administrator on 2017/12/26.
 */

public class QuickCreateActvity extends BaseActivity implements OnClickListener {
    private Context mContext;
    private IconDotTextView idtv_person, idtv_material, idtv_order, idtv_pin, idtv_rectification, idtv_more;
    private ImageView iv_close;

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_quickcreate);
        StatusBarUtil.setColorNoTranslucent(this, Color.parseColor("#1A89FD"));//状态栏半透明
        idtv_person = (IconDotTextView) findViewById(R.id.idtv_quick_person);
        idtv_material = (IconDotTextView) findViewById(R.id.idtv_quick_material);
        idtv_order = (IconDotTextView) findViewById(R.id.idtv_quick_order);
        idtv_pin = (IconDotTextView) findViewById(R.id.idtv_quick_pin);
        idtv_rectification = (IconDotTextView) findViewById(R.id.idtv_quick_rectification);
        idtv_more = (IconDotTextView) findViewById(R.id.idtv_quick_more);
        iv_close = (ImageView) findViewById(R.id.iv_quick_close);
        idtv_person.setOnClickListener(this);
        idtv_material.setOnClickListener(this);
        idtv_order.setOnClickListener(this);
        idtv_pin.setOnClickListener(this);
        idtv_rectification.setOnClickListener(this);
        idtv_more.setOnClickListener(this);
        iv_close.setOnClickListener(this);
    }

    @Override
    protected void loadData() {
        showAnim();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.idtv_quick_person:
                break;
            case R.id.idtv_quick_material:
                break;
            case R.id.idtv_quick_order:
                break;
            case R.id.idtv_quick_pin:
                break;
            case R.id.idtv_quick_rectification:
                break;
            case R.id.idtv_quick_more:
                break;
            case R.id.iv_quick_close:
                hideAnim();
                finish();
                break;
        }
    }

    private void showAnim() {
        Animation itemin = AnimationUtils.loadAnimation(this, R.anim.anim_quick_itemin);
        idtv_person.startAnimation(itemin);//设置按钮运行该动画效果
        idtv_material.startAnimation(itemin);//设置按钮运行该动画效果
        idtv_order.startAnimation(itemin);//设置按钮运行该动画效果
        idtv_pin.startAnimation(itemin);//设置按钮运行该动画效果
        idtv_rectification.startAnimation(itemin);//设置按钮运行该动画效果
        idtv_more.startAnimation(itemin);//设置按钮运行该动画效果
        Animation close = AnimationUtils.loadAnimation(this,R.anim.anim_quick_close);
        iv_close.startAnimation(close);
    }

    private void hideAnim() {
        Animation itemout = AnimationUtils.loadAnimation(this, R.anim.anim_quick_itemout);
        idtv_person.startAnimation(itemout);//设置按钮运行该动画效果
        idtv_material.startAnimation(itemout);//设置按钮运行该动画效果
        idtv_order.startAnimation(itemout);//设置按钮运行该动画效果
        idtv_pin.startAnimation(itemout);//设置按钮运行该动画效果
        idtv_rectification.startAnimation(itemout);//设置按钮运行该动画效果
        idtv_more.startAnimation(itemout);//设置按钮运行该动画效果
    }

    @Override
    public void finish() {
        super.finish();
    }
}
