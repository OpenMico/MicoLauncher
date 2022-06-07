package com.xiaomi.micolauncher.module.homepage.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.GlideUtils;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.base.BaseFragment;
import com.xiaomi.micolauncher.common.widget.HorizontalRefreshLayout;
import com.xiaomi.micolauncher.module.TabViewPresenter;
import com.xiaomi.micolauncher.module.homepage.adapter.BaseAdapter;
import com.xiaomi.micolauncher.module.recommend.RecommendEvent;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public abstract class BaseHomeFragment extends BaseFragment {
    public static final int PAY_LOAD = 0;
    private TabViewPresenter a;
    private BaseAdapter<? extends RecyclerView.ViewHolder> b;
    public Activity context;
    protected boolean isFlinged;
    protected boolean isNotifyed;
    public RecyclerView.OnScrollListener onScrollListener;
    public RecyclerView recyclerView;
    protected TabViewPresenter.OnRefreshDataListener refreshDataListener;
    public HorizontalRefreshLayout refreshLayout;

    protected abstract BaseAdapter<? extends RecyclerView.ViewHolder> createAdapter();

    protected void getArgumentsData() {
    }

    protected boolean hasData() {
        return false;
    }

    protected void initViews(View view) {
    }

    protected abstract int itemViewCacheSize();

    protected abstract int layoutId();

    protected abstract void loadData();

    protected void loadDataFromSever() {
    }

    protected void onScrolled() {
    }

    public void onTimeTickUpdate() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public abstract void recordShow();

    public void refreshData() {
    }

    protected void startAnimation() {
    }

    protected void stopAnimation() {
    }

    public void setTabViewPresenter(TabViewPresenter tabViewPresenter) {
        this.a = tabViewPresenter;
    }

    public BaseHomeFragment setOnRefreshDataListener(TabViewPresenter.OnRefreshDataListener onRefreshDataListener) {
        this.refreshDataListener = onRefreshDataListener;
        return this;
    }

    public void init(Context context) {
        this.context = (Activity) context;
    }

    public BaseAdapter<? extends RecyclerView.ViewHolder> getAdapter() {
        return this.b;
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        L.homepage.d("BaseHomeFragment onCreateView ");
        getArgumentsData();
        View inflateView = inflateView(layoutInflater, viewGroup);
        loadData();
        recordShow();
        return inflateView;
    }

    public String identifier() {
        return getClass().getSimpleName();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onApiInitFinish(RecommendEvent.ApiInited apiInited) {
        if (isAdded() && !isHidden() && !hasData()) {
            loadDataFromSever();
        }
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment
    @NonNull
    public BaseFragment.EventBusFragmentMode getEventBusRegisterMode() {
        return BaseFragment.EventBusFragmentMode.WHOLE_LIFE;
    }

    protected View inflateView(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        View inflate = layoutInflater.inflate(layoutId(), viewGroup, false);
        Context context = inflate.getContext();
        this.refreshLayout = (HorizontalRefreshLayout) inflate.findViewById(R.id.refresh_layout);
        this.recyclerView = (RecyclerView) inflate.findViewById(R.id.home_recycler_view);
        RecyclerView recyclerView = this.recyclerView;
        if (recyclerView != null) {
            recyclerView.setItemViewCacheSize(itemViewCacheSize());
            this.recyclerView.setHasFixedSize(true);
            this.recyclerView.setLayoutManager(initLayoutManager());
            this.recyclerView.setItemAnimator(null);
        }
        this.b = createAdapter();
        BaseAdapter<? extends RecyclerView.ViewHolder> baseAdapter = this.b;
        if (baseAdapter != null) {
            this.recyclerView.setAdapter(baseAdapter);
        }
        initViews(inflate);
        addScrollListener(context);
        return inflate;
    }

    protected RecyclerView.LayoutManager initLayoutManager() {
        return new LinearLayoutManager(getContext(), 0, false);
    }

    protected void addScrollListener(final Context context) {
        if (this.recyclerView != null) {
            this.onScrollListener = new RecyclerView.OnScrollListener() { // from class: com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment.1
                @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
                public void onScrollStateChanged(RecyclerView recyclerView, int i) {
                    super.onScrollStateChanged(recyclerView, i);
                    if (BaseHomeFragment.this.b != null) {
                        if (i == 0) {
                            if (BaseHomeFragment.this.isFlinged && !BaseHomeFragment.this.isNotifyed) {
                                BaseHomeFragment.this.b.setScrolling(false);
                                BaseHomeFragment.this.b.notifyItemRangeChanged(2, BaseHomeFragment.this.b.getItemCount() - 2);
                                BaseHomeFragment.this.isNotifyed = true;
                            }
                            GlideUtils.resumeRequests(context);
                            BaseHomeFragment.this.startAnimation();
                        } else if (i == 2) {
                            GlideUtils.pauseRequests(context);
                            if (!BaseHomeFragment.this.isFlinged) {
                                BaseHomeFragment.this.b.setScrolling(true);
                                BaseHomeFragment.this.isFlinged = true;
                            }
                            BaseHomeFragment.this.stopAnimation();
                            L.homepage.d("adapter is : %s", BaseHomeFragment.this.b);
                        } else {
                            GlideUtils.pauseRequests(context);
                            BaseHomeFragment.this.stopAnimation();
                        }
                    }
                }

                @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
                public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                    super.onScrolled(recyclerView, i, i2);
                    BaseHomeFragment.this.onScrolled();
                }
            };
            this.recyclerView.addOnScrollListener(this.onScrollListener);
        }
    }

    protected void setAdapter(BaseAdapter baseAdapter) {
        RecyclerView recyclerView = this.recyclerView;
        if (recyclerView != null) {
            this.b = baseAdapter;
            recyclerView.setAdapter(baseAdapter);
        }
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = (Activity) context;
    }

    @Override // androidx.fragment.app.Fragment
    public void onDetach() {
        super.onDetach();
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        RecyclerView recyclerView = this.recyclerView;
        if (recyclerView != null) {
            recyclerView.removeOnScrollListener(this.onScrollListener);
            this.recyclerView.setLayoutManager(null);
            this.recyclerView.setAdapter(null);
            this.recyclerView.getRecycledViewPool().clear();
        }
        this.recyclerView = null;
    }
}
