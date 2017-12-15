package com.geobim.teamtask.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.geobim.teamtask.R;
import com.geobim.teamtask.base.ui.BaseFragment;

import butterknife.ButterKnife;

/**
 * 个人中心界面
 *
 */
public class FragmentMe extends BaseFragment {
    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.fragment_me, null);
        ButterKnife.bind(this, view);
        mContentView.addView(view);

        initView();
    }

    private void initView() {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
