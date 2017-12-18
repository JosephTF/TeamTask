package com.geobim.teamtask.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.geobim.teamtask.R;
import com.geobim.teamtask.activity.MainActivity;
import com.geobim.teamtask.activity.MeActivity;
import com.geobim.teamtask.base.ui.BaseFragment;
import android.view.View.OnClickListener;
import butterknife.ButterKnife;

/**
 * 个人中心界面
 *
 */
public class FragmentMe extends BaseFragment implements OnClickListener{
    private View view;
    private RelativeLayout rl_userdetail;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.fragment_me, null);
        ButterKnife.bind(this, view);
        mContentView.addView(view);
        rl_userdetail = view.findViewById(R.id.rl_me_userdetail);
        rl_userdetail.setOnClickListener(this);
        initView();
    }

    private void initView() {
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rl_me_userdetail:
                Intent intent = new Intent(getContext(), MeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                getContext().startActivity(intent);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


}
