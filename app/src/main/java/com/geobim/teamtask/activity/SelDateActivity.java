package com.geobim.teamtask.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.geobim.teamtask.R;
import com.geobim.teamtask.ui.dialog.SelectDateDialog;
import com.geobim.teamtask.util.statusbar.StatusBarUtil;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 选择日期
 */
public class SelDateActivity extends BaseActivity {

    private int YEAR, MONTH, DAY;

    @Bind(R.id.rl_end_date)
    RelativeLayout rl_end_date;
    @Bind(R.id.rl_start_date)
    RelativeLayout rl_start_date;
    @Bind(R.id.tv_end_value)
    TextView tv_end_value;
    @Bind(R.id.tv_start_value)
    TextView tv_start_value;
    @Bind(R.id.ib_country_back)
    ImageButton ib_country_back;
    @Bind(R.id.tv_confirm)
    TextView tv_confirm;

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_sel_date);
        ButterKnife.bind(this);
        StatusBarUtil.setTranslucent(this, 0);//状态栏半透明
    }

    @Override
    protected void loadData() {
        Calendar calendar = Calendar.getInstance();
        YEAR = calendar.get(Calendar.YEAR);
        MONTH = calendar.get(Calendar.MONTH);
        DAY = calendar.get(Calendar.DAY_OF_MONTH);
    }


    @OnClick({R.id.rl_end_date, R.id.rl_start_date, R.id.tv_confirm, R.id.ib_country_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_start_date:
                showSelectDateDialog(true);
                break;
            case R.id.rl_end_date:
                showSelectDateDialog(false);
                break;
            case R.id.tv_confirm:
                finish();
                break;
            case R.id.ib_country_back:
                finish();
                break;
        }
    }

    private void showSelectDateDialog(final boolean isStart) {
        SelectDateDialog mSelectDateDialog = new SelectDateDialog(this);
        mSelectDateDialog.setOnClickListener(new SelectDateDialog.OnClickListener() {
            @Override
            public boolean onSure(int mYear, int mMonth, int mDay, long time) {
                YEAR = mYear;
                MONTH = mMonth;
                DAY = mDay;
                if (isStart){
                    tv_start_value.setText(mYear + "-" + mMonth + "-" + mDay);
                }else{
                    tv_end_value.setText(mYear + "-" + mMonth + "-" + mDay);
                }
                Toast.makeText(SelDateActivity.this, mYear + "-" + mMonth + "-" + mDay, Toast.LENGTH_SHORT).show();
                return false;
            }
            @Override
            public boolean onCancel() {
                return false;
            }
        });
        mSelectDateDialog.show(YEAR, MONTH, DAY);
    }
}
