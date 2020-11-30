package com.longf.lib_common.mvvm;

import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;

import com.longf.lib_common.adapter.BaseAdapter;
import com.longf.lib_common.mvvm.viewmodel.BaseRefreshViewModel;
import com.longf.lib_common.util.log.KLog;
import com.longf.lib_refresh.DaisyRefreshLayout;

public abstract class BaseMvvmRefreshFragment<T, V extends ViewDataBinding, VM extends BaseRefreshViewModel> extends BaseMvvmFragment<V, VM> {
    protected DaisyRefreshLayout mRefreshLayout;
    protected BaseAdapter.OnItemClickListener mItemClickListener;
    protected BaseAdapter.OnItemLongClickListener mOnItemLongClickListener;
    @Override
    public void initCommonView(View view) {
        super.initCommonView(view);
        initRefreshView();
    }

    @Override
    protected void initBaseViewObservable() {
        super.initBaseViewObservable();
        initBaseViewRefreshObservable();
    }

    private void initBaseViewRefreshObservable() {

        mViewModel.getUCRefresh().getAutoRefresLiveEvent().observe(this, new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
                autoLoadData();
            }
        });
        mViewModel.getUCRefresh().getStopRefresLiveEvent().observe(this, new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
                stopRefresh();
            }
        });
        mViewModel.getUCRefresh().getStopLoadMoreLiveEvent().observe(this, new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
                stopLoadMore();
            }
        });
    }

    public abstract DaisyRefreshLayout getRefreshLayout();

    public void initRefreshView() {
        mRefreshLayout = getRefreshLayout();
    }

    public void stopRefresh() {
        mRefreshLayout.setRefreshing(false);
    }

    public void stopLoadMore() {
        mRefreshLayout.setLoadMore(false);
    }

    public void autoLoadData() {
        KLog.v("MYTAG", "autoLoadData start...");
        if (mRefreshLayout != null) {
            KLog.v("MYTAG", "autoLoadData1 start...");
            mRefreshLayout.autoRefresh();
        }
    }
    public void setItemClickListener(BaseAdapter.OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public void setOnItemLongClickListener(BaseAdapter.OnItemLongClickListener onItemLongClickListener) {
        mOnItemLongClickListener = onItemLongClickListener;
    }
}