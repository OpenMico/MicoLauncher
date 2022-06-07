package com.xiaomi.micolauncher.module.child.childvideo;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.ApiManager;
import com.xiaomi.micolauncher.api.model.ChildVideo;
import com.xiaomi.micolauncher.api.service.mitv.ChildVideoService;
import com.xiaomi.micolauncher.common.GlideUtils;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.MicoHandler;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.module.homepage.adapter.BaseAdapter;
import com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment;
import com.xiaomi.micolauncher.module.recommend.RecommendEvent;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public class ChildVideoFragment extends BaseHomeFragment {
    public static final String TAG = "ChildVideoFragment";
    private static final long a = TimeUnit.HOURS.toMillis(12);
    private static final long b = TimeUnit.SECONDS.toMillis(2);
    private ChildVideoAdapter d;
    private boolean f;
    private int g;
    private a h;
    private boolean i;
    private boolean j;
    private Disposable k;
    private Disposable l;
    private String c = ChildVideoService.FIRST_URL;
    private List<ChildVideo.BlocksBean> e = new ArrayList();

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    public String identifier() {
        return TAG;
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    protected int itemViewCacheSize() {
        return 4;
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    protected int layoutId() {
        return R.layout.fragment_child_video;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    public void recordShow() {
    }

    public static ChildVideoFragment newInstance(boolean z) {
        ChildVideoFragment childVideoFragment = new ChildVideoFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("is_from_home", z);
        childVideoFragment.setArguments(bundle);
        return childVideoFragment;
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    protected void getArgumentsData() {
        if (getArguments() != null) {
            this.j = getArguments().getBoolean("is_from_home");
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    protected void initViews(View view) {
        this.i = true;
        this.recyclerView.setHasFixedSize(true);
        final int dimensionPixelOffset = getResources().getDimensionPixelOffset(this.j ? R.dimen.child_story_card_margin_left_from_home : R.dimen.child_story_card_margin_left);
        this.recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() { // from class: com.xiaomi.micolauncher.module.child.childvideo.ChildVideoFragment.1
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect rect, View view2, RecyclerView recyclerView, RecyclerView.State state) {
                rect.right = dimensionPixelOffset;
            }
        });
        this.onScrollListener = new RecyclerView.OnScrollListener() { // from class: com.xiaomi.micolauncher.module.child.childvideo.ChildVideoFragment.2
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(RecyclerView recyclerView, int i) {
                super.onScrollStateChanged(recyclerView, i);
                if (i == 0) {
                    ChildVideoFragment.this.i = true;
                    GlideUtils.resumeRequests(ChildVideoFragment.this.context);
                } else {
                    ChildVideoFragment.this.i = false;
                    GlideUtils.pauseRequests(ChildVideoFragment.this.context);
                }
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (linearLayoutManager != null && recyclerView.getAdapter() != null) {
                    int itemCount = recyclerView.getAdapter().getItemCount();
                    int findLastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
                    int childCount = recyclerView.getChildCount();
                    if (i == 0 && findLastVisibleItemPosition == itemCount - 1 && childCount > 0 && ChildVideoFragment.this.f) {
                        ChildVideoFragment.this.g = 0;
                        ChildVideoFragment.this.a();
                    }
                }
            }

            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                super.onScrolled(recyclerView, i, i2);
            }
        };
        this.recyclerView.addOnScrollListener(this.onScrollListener);
        this.h = new a(this);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    public void loadData() {
        if (ApiManager.isInited()) {
            if (this.i) {
                this.e.clear();
                this.d.notifyDataSetChanged();
                ChildVideoAdapter childVideoAdapter = this.d;
                if (childVideoAdapter != null) {
                    childVideoAdapter.setHasAddAgeBlock(false);
                    this.d.setHasAddTypeBlock(false);
                }
                this.c = ChildVideoService.FIRST_URL;
                this.g = 0;
                a();
            }
            ChildVideoDataManager.getManager().updateVipStatus(ChildVideoDataManager.PCODE_ERTONG);
            this.h.sendEmptyMessageDelayed(0, a);
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onApiInitFinish(RecommendEvent.ApiInited apiInited) {
        loadData();
        ChildVideoDataManager.getManager().loadVideoTypeFromRemote();
        b();
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        if (ApiManager.isInited()) {
            this.d.startMessages();
            ChildVideoDataManager.getManager().loadVideoTypeFromRemote();
            b();
        }
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        this.d.removeMessages();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static class a extends MicoHandler {
        private WeakReference<ChildVideoFragment> a;

        @Override // com.xiaomi.micolauncher.common.MicoHandler
        public String getLogTag() {
            return ChildVideoFragment.TAG;
        }

        a(ChildVideoFragment childVideoFragment) {
            this.a = new WeakReference<>(childVideoFragment);
        }

        @Override // com.xiaomi.micolauncher.common.MicoHandler
        public void processMessage(Message message) {
            final ChildVideoFragment childVideoFragment = this.a.get();
            if (childVideoFragment != null) {
                childVideoFragment.getClass();
                ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.module.child.childvideo.-$$Lambda$CZVmWVxzOGUneofvUrPP2JmolWE
                    @Override // java.lang.Runnable
                    public final void run() {
                        ChildVideoFragment.this.loadData();
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a() {
        c();
        int i = this.g;
        if (i < 3) {
            this.g = i + 1;
            this.k = ChildVideoDataManager.getManager().loadVideoDataFromRemote(this.c).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.child.childvideo.-$$Lambda$ChildVideoFragment$xbHFt1ZTwBipaGm8sxv9gFa1r6s
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ChildVideoFragment.this.a((ChildVideo) obj);
                }
            }, $$Lambda$ChildVideoFragment$hZB9YKNGK63HQOw7XTDlSWEzLKU.INSTANCE);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(ChildVideo childVideo) throws Exception {
        this.e.addAll(childVideo.getBlocks());
        L.childContent.i("child video block size  from remote %d", Integer.valueOf(this.e.size()));
        this.d.setData(this.e);
        if (TextUtils.isEmpty(childVideo.getMeta().getMore()) || childVideo.getMeta().getMore().contains(ChildVideo.ItemsBean.TYPE_FILTER)) {
            this.f = false;
            return;
        }
        this.f = true;
        this.c = childVideo.getMeta().getMore();
        a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Throwable th) throws Exception {
        L.childContent.e("get child video error ", th);
    }

    private void b() {
        Disposable disposable = this.l;
        if (disposable != null && !disposable.isDisposed()) {
            this.l.dispose();
        }
        this.h.postDelayed(new Runnable() { // from class: com.xiaomi.micolauncher.module.child.childvideo.-$$Lambda$ChildVideoFragment$UvzOKcGuN2QbbU-zmpiXJuOqVYs
            @Override // java.lang.Runnable
            public final void run() {
                ChildVideoFragment.this.d();
            }
        }, b);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void d() {
        this.l = ChildVideoDataManager.getManager().getVideoHistoryCover().subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.child.childvideo.-$$Lambda$ChildVideoFragment$qQ5h5cmQsllBrmOuiNn0Ax_3tck
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ChildVideoFragment.this.a((String) obj);
            }
        });
        addToDisposeBag(this.l);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(String str) throws Exception {
        if (!TextUtils.isEmpty(str)) {
            L.childContent.i("load video cover data %s", str);
            Bundle bundle = new Bundle();
            bundle.putString("cover_payloads", str);
            this.d.notifyItemChanged(0, bundle);
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    protected BaseAdapter<? extends RecyclerView.ViewHolder> createAdapter() {
        this.d = new ChildVideoAdapter(this.context, this.j);
        return this.d;
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onHiddenChanged(boolean z) {
        super.onHiddenChanged(z);
        ChildVideoAdapter childVideoAdapter = this.d;
        if (childVideoAdapter != null) {
            if (z) {
                childVideoAdapter.removeMessages();
            } else {
                childVideoAdapter.startMessages();
            }
        }
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this.h.removeCallbacksAndMessages(null);
        c();
    }

    private void c() {
        Disposable disposable = this.k;
        if (disposable != null && !disposable.isDisposed()) {
            this.k.dispose();
        }
    }
}
