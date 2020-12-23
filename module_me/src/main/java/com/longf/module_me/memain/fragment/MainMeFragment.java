package com.longf.module_me.memain.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.longf.lib_api.entity.MainMeEntity;
import com.longf.lib_api.entity.MainMeSettingEntity;
import com.longf.lib_common.mvp.BaseRefreshFragment;
import com.longf.lib_common.util.LinkUtils;
import com.longf.module_me.R;
import com.longf.module_me.memain.adapter.MainMineAdapter;
import com.longf.module_me.memain.contract.MainMeContract;
import com.longf.module_me.memain.model.MainMeModel;
import com.longf.module_me.memain.presenter.MainMePresenter;

import java.util.ArrayList;
import java.util.List;

import static com.longf.lib_api.RetrofitManager.mContext;

public class MainMeFragment extends BaseRefreshFragment<MainMeModel, MainMeContract.View<MainMeEntity>,
        MainMePresenter, MainMeEntity> implements MainMeContract.View<MainMeEntity> {
    private RecyclerView mRecyclerView;
    private MainMineAdapter mMainMineAdapter;
    private ArrayList<MainMeSettingEntity.DataBean> mList = new ArrayList();
    private View mHeaderOneView;
    private View mFootOneView;
    private TextView mTvVersion;
    private boolean flag = false;

    public static MainMeFragment newInstance() {
        return new MainMeFragment();
    }

    @Override
    protected int onBindRreshLayout() {
        return R.id.refview_me_list;
    }

    @Override
    public void injectPresenter() {
        if (mPresenter == null) {
            mPresenter = new MainMePresenter(getActivity(), MainMeFragment.this, new MainMeModel(getActivity()));
        }
    }

    @Override
    public int onBindLayout() {
        return R.layout.fragment_me_main;
    }

    @Override
    public void initView(View view) {
        mRefreshLayout.setEnableLoadMore(false);
        mRecyclerView = view.findViewById(R.id.recview_me_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mHeaderOneView = LayoutInflater.from(getActivity()).inflate(R.layout.item_list_me_header, null);
        mFootOneView = LayoutInflater.from(getActivity()).inflate(R.layout.item_list_me_footer, null);
        mTvVersion = mFootOneView.findViewById(R.id.tv_version_info);

        mTvVersion.setText("white night");
    }

    @Override
    public void initData() {
        autoLoadData();
    }

    @Override
    public String getToolbarTitle() {
        return null;
    }

    @Override
    public void refreshData(List data) {

    }

    @Override
    public void loadMoreData(List data) {

    }

    @Override
    public void onRefreshEvent() {
        flag = true;
        mPresenter.getMainMeSettingData();
    }

    @Override
    public void onLoadMoreEvent() {

    }

    @Override
    public void onAutoLoadEvent() {
        mPresenter.getMainMeSettingData();
    }

    @Override
    public void refreshData(MainMeEntity mainMeEntity) {

    }

    @Override
    public void loadList(MainMeSettingEntity mainMeSettingEntity) {
        //多维数组转为单数组
        List<MainMeSettingEntity.DataBean> fullMyItem = new ArrayList<>();
        if (mainMeSettingEntity.getList() == null) {
            return;
        }
        //获得分组数
        int size = mainMeSettingEntity.getList().size();
        for (int i = 0; i < size; i++) {
            List<MainMeSettingEntity.DataBean> innerList = mainMeSettingEntity.getList().get(i);
            int count = innerList.size();
            for (int j = 0; j < count; j++) {
                MainMeSettingEntity.DataBean myNewItem = innerList.get(j);
                myNewItem.setGroup_id(i + 1);
                fullMyItem.add(myNewItem);
            }
        }
        mList.clear();
        mList.addAll(fullMyItem);
        if(mMainMineAdapter == null){
            mMainMineAdapter = new MainMineAdapter(R.layout.item_list_me_main, mList);
            mMainMineAdapter.addHeaderView(mHeaderOneView);
            mMainMineAdapter.addFooterView(mFootOneView);
            mRecyclerView.setAdapter(mMainMineAdapter);
        }else {
            mMainMineAdapter.setNewInstance(mList);
        }

        mMainMineAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                LinkUtils.parse(getContext(), mList.get(position).getUrl(), mList.get(position).getTitle());
            }
        });

        if(flag){
            mTvVersion.setText("sssss");
        }
    }
}
