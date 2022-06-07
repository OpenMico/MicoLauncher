package com.xiaomi.micolauncher.module.child.childstory;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.ChildStory;
import com.xiaomi.micolauncher.common.GlideUtils;
import com.xiaomi.micolauncher.common.MicoHandler;
import com.xiaomi.micolauncher.common.base.BaseFragment;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.module.child.childstory.ChildStoryFragment;
import com.xiaomi.micolauncher.module.homepage.adapter.BaseAdapter;
import com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class ChildStoryFragment extends BaseHomeFragment {
    public static final String TAG = "ChildStoryFragment";
    private static final long g = TimeUnit.HOURS.toMillis(12);
    private ChildStoryAdapter a;
    private boolean b;
    private Disposable c;
    private Disposable d;
    private Disposable e;
    private Disposable f;
    private Handler h = new AnonymousClass3(ThreadUtil.getWorkHandler().getLooper());

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
        return R.layout.fragment_child_story;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    public void recordShow() {
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    protected void initViews(View view) {
        this.onScrollListener = new RecyclerView.OnScrollListener() { // from class: com.xiaomi.micolauncher.module.child.childstory.ChildStoryFragment.1
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(RecyclerView recyclerView, int i) {
                super.onScrollStateChanged(recyclerView, i);
                if (i == 0) {
                    GlideUtils.resumeRequests(ChildStoryFragment.this.context);
                } else {
                    GlideUtils.pauseRequests(ChildStoryFragment.this.context);
                }
            }

            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                super.onScrolled(recyclerView, i, i2);
            }
        };
        this.recyclerView.addOnScrollListener(this.onScrollListener);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    public void loadData() {
        this.b = false;
        this.recyclerView.removeOnScrollListener(this.onScrollListener);
        this.recyclerView.setHasFixedSize(true);
        final int dimensionPixelOffset = getResources().getDimensionPixelOffset(R.dimen.child_story_card_margin_left);
        this.recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() { // from class: com.xiaomi.micolauncher.module.child.childstory.ChildStoryFragment.2
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
                rect.right = dimensionPixelOffset;
            }
        });
        a();
        loadDataFromSever();
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    protected void loadDataFromSever() {
        b();
        c();
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    protected boolean hasData() {
        return this.b;
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        this.a.startMessages();
        c();
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        this.a.removeMessages();
    }

    /* renamed from: com.xiaomi.micolauncher.module.child.childstory.ChildStoryFragment$3  reason: invalid class name */
    /* loaded from: classes3.dex */
    class AnonymousClass3 extends MicoHandler {
        @Override // com.xiaomi.micolauncher.common.MicoHandler
        public String getLogTag() {
            return ChildStoryFragment.TAG;
        }

        AnonymousClass3(Looper looper) {
            super(looper);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void a() {
            ChildStoryFragment.this.b();
        }

        @Override // com.xiaomi.micolauncher.common.MicoHandler
        public void processMessage(Message message) {
            ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.module.child.childstory.-$$Lambda$ChildStoryFragment$3$NN2wYYnZTPLfP5OnAEFMOMHABBM
                @Override // java.lang.Runnable
                public final void run() {
                    ChildStoryFragment.AnonymousClass3.this.a();
                }
            });
        }
    }

    private void a() {
        d();
        this.d = ChildStoryDataManager.getManager().a().subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.child.childstory.-$$Lambda$ChildStoryFragment$ipaM7sHa8rlKyBzdCMzI531SK7Q
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ChildStoryFragment.this.b((ChildStory) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(ChildStory childStory) throws Exception {
        ((ChildStoryAdapter) getAdapter()).setData(childStory);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        d();
        this.h.sendEmptyMessageDelayed(0, g);
        Observable<ChildStory> loadStoryDataFromRemote = ChildStoryDataManager.getManager().loadStoryDataFromRemote(0, 0);
        if (loadStoryDataFromRemote != null) {
            this.b = true;
            this.c = loadStoryDataFromRemote.subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.child.childstory.-$$Lambda$ChildStoryFragment$jLyr9m81KLEaPiYMZmIqoJmTMYM
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ChildStoryFragment.this.a((ChildStory) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(ChildStory childStory) throws Exception {
        ((ChildStoryAdapter) getAdapter()).setData(childStory);
    }

    private void c() {
        Disposable disposable = this.f;
        if (disposable != null && !disposable.isDisposed()) {
            this.f.dispose();
        }
        Disposable disposable2 = this.e;
        if (disposable2 != null && !disposable2.isDisposed()) {
            this.e.dispose();
        }
        Observable<String> b = ChildStoryDataManager.getManager().b();
        if (b != null) {
            this.e = b.subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.child.childstory.-$$Lambda$ChildStoryFragment$qfiXblaDpZer0V__plCsJrAli9U
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ChildStoryFragment.this.b((String) obj);
                }
            });
            addToDisposeBag(this.e);
        }
        Observable<String> a = ChildStoryDataManager.getManager().a(getContext());
        if (a != null) {
            this.f = a.subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.child.childstory.-$$Lambda$ChildStoryFragment$6miG7ubIHfETo9BzFGNKTSoq6eE
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ChildStoryFragment.this.a((String) obj);
                }
            });
            addToDisposeBag(this.f);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(String str) throws Exception {
        Bundle bundle = new Bundle();
        bundle.putInt("cover_payloads", 1);
        this.a.notifyItemChanged(0, bundle);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(String str) throws Exception {
        if (!TextUtils.isEmpty(str)) {
            Bundle bundle = new Bundle();
            bundle.putInt("cover_payloads", 0);
            this.a.notifyItemChanged(0, bundle);
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    protected BaseAdapter<? extends RecyclerView.ViewHolder> createAdapter() {
        this.a = new ChildStoryAdapter(this.context);
        return this.a;
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment, com.xiaomi.micolauncher.common.base.BaseFragment
    @NonNull
    public BaseFragment.EventBusFragmentMode getEventBusRegisterMode() {
        return BaseFragment.EventBusFragmentMode.NO_AUTO_REGISTER;
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onHiddenChanged(boolean z) {
        super.onHiddenChanged(z);
        if (z) {
            this.a.removeMessages();
        } else {
            this.a.startMessages();
        }
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this.h.removeCallbacksAndMessages(null);
        d();
    }

    private void d() {
        Disposable disposable = this.c;
        if (disposable != null && !disposable.isDisposed()) {
            this.c.dispose();
        }
        Disposable disposable2 = this.d;
        if (disposable2 != null && !disposable2.isDisposed()) {
            this.d.dispose();
        }
    }
}
