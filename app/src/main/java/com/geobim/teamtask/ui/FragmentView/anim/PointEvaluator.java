package com.geobim.teamtask.ui.FragmentView.anim;

import android.animation.TypeEvaluator;

import com.geobim.teamtask.ui.FragmentView.entity.IndicatorPoint;

/**
 * 估值器  用来确定在动画过程中每时每刻动画的具体值，左右回弹动画
 * 根据左偏幅和右偏幅求出当前动画进程所对应的回弹幅度，然后新建一个Point对象返回
 */
public class PointEvaluator implements TypeEvaluator<IndicatorPoint> {
    @Override
    public IndicatorPoint evaluate(float fraction, IndicatorPoint startValue, IndicatorPoint endValue) {
        float left = startValue.left + fraction * (endValue.left - startValue.left);
        float right = startValue.right + fraction * (endValue.right - startValue.right);
        IndicatorPoint point = new IndicatorPoint();
        point.left = left;
        point.right = right;
        point.right = right;
        return point;
    }
}