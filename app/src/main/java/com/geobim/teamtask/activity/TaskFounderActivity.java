package com.geobim.teamtask.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.geobim.teamtask.R;
import com.geobim.teamtask.ui.CountryListView.CharacterParserUtil;
import com.geobim.teamtask.ui.CountryListView.CountryComparator;
import com.geobim.teamtask.ui.CountryListView.CountrySortAdapter;
import com.geobim.teamtask.ui.CountryListView.CountrySortModel;
import com.geobim.teamtask.ui.CountryListView.GetCountryNameSort;
import com.geobim.teamtask.ui.CountryListView.SideBar;
import com.geobim.teamtask.util.statusbar.StatusBarUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 任务创建人
 * Created by Administrator on 2018/01/02.
 */

public class TaskFounderActivity extends BaseActivity implements View.OnClickListener {
    //	TAG标识
    private final static String TAG = "CountryActivity";
    //	所有国家列表数据
    private List<CountrySortModel> mAllCountryList;
    //	搜索框
    private EditText country_edt_search;
    //	国家列表
    private ListView country_lv_countryList;
    //	返回按钮、搜索按钮和清空按钮
    private ImageButton ib_country_back, ib_country_search, ib_country_searchclear;
    //	搜索界面
    private RelativeLayout rl_country_search;
    //	适配器
    private CountrySortAdapter adapter;
    //	右边栏索引
    private SideBar sideBar;
    //	索引字母展示,标题栏标题
    private TextView dialog, tv_title;
    //	国家排序
    private CountryComparator pinyinComparator;
    //	取首字母及模糊匹配查询
    private GetCountryNameSort countryChangeUtil;
    //	汉字转换为拼音
    private CharacterParserUtil characterParserUtil;

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_task_founder);
        StatusBarUtil.setTranslucent(TaskFounderActivity.this, 0);//状态栏半透明
        country_edt_search =(EditText) findViewById(R.id.et_country_search);
        country_lv_countryList = (ListView) findViewById(R.id.country_lv_list);
        ib_country_back =(ImageButton) findViewById(R.id.ib_country_back);
        ib_country_search = (ImageButton)findViewById(R.id.ib_country_search);
        ib_country_searchclear =(ImageButton) findViewById(R.id.ib_country_searchclear);
        rl_country_search = (RelativeLayout) findViewById(R.id.rl_register_search);
        tv_title = (TextView) findViewById(R.id.tv_country_title);
        dialog = (TextView)findViewById(R.id.country_dialog);
        sideBar =(SideBar) findViewById(R.id.country_sidebar);
        ib_country_back.setOnClickListener(this);
        ib_country_search.setOnClickListener(this);
        ib_country_searchclear.setOnClickListener(this);
        sideBar.setTextView(dialog);

        mAllCountryList = new ArrayList<CountrySortModel>();
        pinyinComparator = new CountryComparator();
        countryChangeUtil = new GetCountryNameSort();
        characterParserUtil = new CharacterParserUtil();

        // 将联系人进行排序，按照A~Z的顺序
        Collections.sort(mAllCountryList, pinyinComparator);
        adapter = new CountrySortAdapter(this, mAllCountryList);
        country_lv_countryList.setAdapter(adapter);
    }

    @Override
    protected void loadData() {
        setListener();
        getCountryList();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_country_back:
                finish();
                break;
            case R.id.ib_country_search:
                if (rl_country_search.getVisibility() != View.VISIBLE) {
                    Animation title_fadeout = AnimationUtils.loadAnimation(TaskFounderActivity.this, R.anim.anim_fade_out);
                    tv_title.startAnimation(title_fadeout);
                    tv_title.setVisibility(View.GONE);
                    Animation search_open = AnimationUtils.loadAnimation(TaskFounderActivity.this, R.anim.anim_country_search_open);
                    rl_country_search.startAnimation(search_open);
                    rl_country_search.setVisibility(View.VISIBLE);
                } else {
                    Animation openAnim = AnimationUtils.loadAnimation(TaskFounderActivity.this, R.anim.anim_country_search_close);
                    rl_country_search.startAnimation(openAnim);
                    rl_country_search.setVisibility(View.GONE);
                    Animation title_fadein = AnimationUtils.loadAnimation(TaskFounderActivity.this, R.anim.anim_fade_in);
                    tv_title.startAnimation(title_fadein);
                    tv_title.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.ib_country_searchclear:
                country_edt_search.setText("");
                Collections.sort(mAllCountryList, pinyinComparator);
                adapter.updateListView(mAllCountryList);
                break;
        }
    }

    /****
     * 添加监听
     */
    private void setListener() {
        //搜索栏监听
        country_edt_search.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String searchContent = country_edt_search.getText().toString();
                if (searchContent.equals("")) {
                    ib_country_searchclear.setVisibility(View.INVISIBLE);
                } else {
                    ib_country_searchclear.setVisibility(View.VISIBLE);
                }

                if (searchContent.length() > 0) {
                    // 按照输入内容进行匹配
                    ArrayList<CountrySortModel> fileterList = (ArrayList<CountrySortModel>) countryChangeUtil
                            .search(searchContent, mAllCountryList);

                    adapter.updateListView(fileterList);
                } else {
                    adapter.updateListView(mAllCountryList);
                }
                country_lv_countryList.setSelection(0);
            }
        });

        // SideBar监听
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                // 该字母首次出现的位置
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    country_lv_countryList.setSelection(position);
                }
            }
        });

        // 列表项点击监听
        country_lv_countryList.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long arg3) {
                String countryName = null;
                String countryNumber = null;
                String searchContent = country_edt_search.getText().toString();

                if (searchContent.length() > 0) {
                    // 按照输入内容进行匹配
                    ArrayList<CountrySortModel> fileterList = (ArrayList<CountrySortModel>) countryChangeUtil.search(searchContent, mAllCountryList);
                    countryName = fileterList.get(position).countryName;
                    countryNumber = fileterList.get(position).countryNumber;
                } else {
                    // 点击后返回
                    countryName = mAllCountryList.get(position).countryName;
                    countryNumber = mAllCountryList.get(position).countryNumber;
                }
                Intent intent = new Intent(TaskFounderActivity.this, TaskSearchActivity.class);
                intent.putExtra("countryName", countryName);
                intent.putExtra("countryNumber", countryNumber);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }

    /**
     * 获取国家列表
     */
    private void getCountryList() {
        String[] countryList = getResources().getStringArray(R.array.country_person_list_ch);

        for (int i = 0, length = countryList.length; i < length; i++) {
            String country = countryList[i];

            String countryName = country;
            String countrySortKey = characterParserUtil.getSelling(countryName);
            CountrySortModel countrySortModel = new CountrySortModel(countryName, "0",
                    countrySortKey);
            String sortLetter = countryChangeUtil.getSortLetterBySortKey(countrySortKey);
            if (sortLetter == null) {
                sortLetter = countryChangeUtil.getSortLetterBySortKey(countryName);
            }

            countrySortModel.sortLetters = sortLetter;
            mAllCountryList.add(countrySortModel);

        }

        Collections.sort(mAllCountryList, pinyinComparator);
        adapter.updateListView(mAllCountryList);
        Log.i(TAG, "国家个数" + mAllCountryList.size());
    }
}