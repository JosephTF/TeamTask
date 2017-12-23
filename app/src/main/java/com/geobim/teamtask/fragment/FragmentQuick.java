package com.geobim.teamtask.fragment;

import com.geobim.teamtask.R;
import com.geobim.teamtask.base.ui.BaseFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;

import butterknife.ButterKnife;

/**
 * 快速创建
 * Created by Administrator on 2017/12/13.
 */

public class FragmentQuick extends BaseFragment implements OnClickListener {
    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVariables();
        initView();
    }

    private void initVariables() {
    }

    private void initView() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.fragment_quick, null);
        ButterKnife.bind(this, view);
        mContentView.addView(view);
    }

    @Override
    public void onClick(View view) {

    }
}
