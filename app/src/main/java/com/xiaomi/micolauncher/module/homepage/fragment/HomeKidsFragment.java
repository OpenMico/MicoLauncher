package com.xiaomi.micolauncher.module.homepage.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.ApiManager;
import com.xiaomi.micolauncher.api.model.AppBlock;
import com.xiaomi.micolauncher.api.model.ChildStory;
import com.xiaomi.micolauncher.api.model.ChildVideo;
import com.xiaomi.micolauncher.api.model.IBlockBean;
import com.xiaomi.micolauncher.api.model.PatchWall;
import com.xiaomi.micolauncher.common.GlideUtils;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.MicoHandler;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.module.child.childstory.ChildStoryDataManager;
import com.xiaomi.micolauncher.module.child.childvideo.ChildVideoDataManager;
import com.xiaomi.micolauncher.module.childsong.ChildSongApiHelper;
import com.xiaomi.micolauncher.module.homepage.adapter.BaseAdapter;
import com.xiaomi.micolauncher.module.homepage.adapter.HomeKidsAdapter;
import com.xiaomi.micolauncher.module.homepage.event.ResetScrollViewPositionEvent;
import com.xiaomi.micolauncher.module.homepage.fragment.EntertainmentFragment;
import com.xiaomi.micolauncher.module.homepage.fragment.HomeKidsFragment;
import com.xiaomi.micolauncher.module.video.childmode.ChildModeManager;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public class HomeKidsFragment extends BaseHomeFragment implements EntertainmentFragment.FragmentRefreshListener {
    public static final String TAG = "HomeKidsFragment";
    private static final long b = TimeUnit.DAYS.toMillis(1);
    private static final String[] c = {"历史国学", "哄睡童话", "科普百科"};
    private static final int[] d = {3, 7, 9};
    private static final int[] e = {0, 1, 4, 6, 8, 10};
    ChildStory.BlocksBean a;
    private String f;
    private HomeKidsAdapter g;
    private List<ChildStory.BlocksBean> i;
    private ChildStory k;
    private int l;
    private boolean m;
    private boolean n;
    private Disposable o;
    private final List<IBlockBean> h = new ArrayList();
    private final List<ChildVideo.BlocksBean> j = new ArrayList();
    private final Handler p = new AnonymousClass3(ThreadUtil.getWorkHandler().getLooper());

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    public String identifier() {
        return TAG;
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    protected int itemViewCacheSize() {
        return 1;
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    protected int layoutId() {
        return R.layout.fragment_home_kids;
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    public void recordShow() {
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    public void init(Context context) {
        super.init(context);
        c();
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    public void loadData() {
        this.f = "/tv/lean/fl/in?id=2463";
        this.l = 0;
        this.j.clear();
        d();
        this.n = false;
        if (ApiManager.isInited()) {
            loadDataFromSever();
        }
        this.p.sendEmptyMessageDelayed(0, b);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    protected boolean hasData() {
        return this.n;
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    protected void loadDataFromSever() {
        a();
        loadMusicData();
        ChildVideoDataManager.getManager().updateVipStatus(ChildVideoDataManager.PCODE_ERTONG);
    }

    @SuppressLint({"CheckResult"})
    private void a() {
        addToDisposeBag(Observable.zip(ChildVideoDataManager.getManager().loadVideoDataFromRemote(this.f), ChildStoryDataManager.getManager().loadStoryDataFromRemote(0, 0), new BiFunction() { // from class: com.xiaomi.micolauncher.module.homepage.fragment.-$$Lambda$HomeKidsFragment$wddJxNwsY20fz0WaYZUfuXQwhj0
            @Override // io.reactivex.functions.BiFunction
            public final Object apply(Object obj, Object obj2) {
                List a;
                a = HomeKidsFragment.this.a((ChildVideo) obj, (ChildStory) obj2);
                return a;
            }
        }).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.fragment.-$$Lambda$HomeKidsFragment$oGz4EEfz5oETNUZdjcHOYQvvVho
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                HomeKidsFragment.this.b((List) obj);
            }
        }));
    }

    public /* synthetic */ List a(ChildVideo childVideo, ChildStory childStory) throws Exception {
        this.k = childStory;
        this.i = new ArrayList();
        e();
        a(childVideo);
        return this.h;
    }

    public /* synthetic */ void b(List list) throws Exception {
        HomeKidsAdapter homeKidsAdapter = this.g;
        if (homeKidsAdapter != null) {
            homeKidsAdapter.notifyDataSetChanged();
        }
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        this.g.removeMessages();
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    protected void initViews(View view) {
        this.onScrollListener = new RecyclerView.OnScrollListener() { // from class: com.xiaomi.micolauncher.module.homepage.fragment.HomeKidsFragment.1
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(RecyclerView recyclerView, int i) {
                super.onScrollStateChanged(recyclerView, i);
                if (i == 0) {
                    GlideUtils.resumeRequests(HomeKidsFragment.this.context);
                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    if (linearLayoutManager != null && recyclerView.getAdapter() != null) {
                        int itemCount = recyclerView.getAdapter().getItemCount();
                        int findLastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
                        int childCount = recyclerView.getChildCount();
                        if (i == 0 && findLastVisibleItemPosition == itemCount - 1 && childCount > 0 && HomeKidsFragment.this.m) {
                            HomeKidsFragment.this.l = 0;
                            HomeKidsFragment.this.b();
                            return;
                        }
                        return;
                    }
                    return;
                }
                GlideUtils.pauseRequests(HomeKidsFragment.this.context);
            }

            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                super.onScrolled(recyclerView, i, i2);
            }
        };
        this.recyclerView.addOnScrollListener(this.onScrollListener);
        this.recyclerView.setHasFixedSize(true);
        final int dimensionPixelOffset = getResources().getDimensionPixelOffset(R.dimen.home_kids_card_margin_left);
        this.recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() { // from class: com.xiaomi.micolauncher.module.homepage.fragment.HomeKidsFragment.2
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect rect, View view2, RecyclerView recyclerView, RecyclerView.State state) {
                rect.right = dimensionPixelOffset;
            }
        });
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        this.g.startMessages();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaomi.micolauncher.module.homepage.fragment.HomeKidsFragment$3 */
    /* loaded from: classes3.dex */
    public class AnonymousClass3 extends MicoHandler {
        @Override // com.xiaomi.micolauncher.common.MicoHandler
        public String getLogTag() {
            return HomeKidsFragment.TAG;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass3(Looper looper) {
            super(looper);
            HomeKidsFragment.this = r1;
        }

        @Override // com.xiaomi.micolauncher.common.MicoHandler
        public void processMessage(Message message) {
            if (HomeKidsFragment.this.h != null) {
                HomeKidsFragment.this.h.clear();
            }
            ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.module.homepage.fragment.-$$Lambda$HomeKidsFragment$3$shaInGzxFYgMa555-aNOGcpXBkM
                @Override // java.lang.Runnable
                public final void run() {
                    HomeKidsFragment.AnonymousClass3.this.a();
                }
            });
        }

        public /* synthetic */ void a() {
            HomeKidsFragment.this.loadData();
        }
    }

    public void b() {
        g();
        int i = this.l;
        if (i < 1) {
            this.l = i + 1;
            Observable<ChildVideo> loadVideoDataFromRemote = ChildVideoDataManager.getManager().loadVideoDataFromRemote(this.f);
            if (loadVideoDataFromRemote != null) {
                this.n = true;
                this.o = loadVideoDataFromRemote.subscribeOn(MicoSchedulers.io()).flatMap(new Function<ChildVideo, ObservableSource<List<IBlockBean>>>() { // from class: com.xiaomi.micolauncher.module.homepage.fragment.HomeKidsFragment.4
                    /* renamed from: a */
                    public ObservableSource<List<IBlockBean>> apply(@NonNull ChildVideo childVideo) throws Exception {
                        HomeKidsFragment.this.a(childVideo);
                        return Observable.just(HomeKidsFragment.this.h);
                    }
                }).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.fragment.-$$Lambda$HomeKidsFragment$SMsJY1DES56bm24V0PmzegnRxY0
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        HomeKidsFragment.this.a((List) obj);
                    }
                }, $$Lambda$HomeKidsFragment$6ezr042gozp8XByNGW5eiZ9aTY.INSTANCE);
            }
        }
    }

    public /* synthetic */ void a(List list) throws Exception {
        HomeKidsAdapter homeKidsAdapter = this.g;
        if (homeKidsAdapter != null) {
            homeKidsAdapter.notifyDataSetChanged();
        }
        if (this.m) {
            b();
        }
    }

    public static /* synthetic */ void a(Throwable th) throws Exception {
        L.childContent.e("get child video error ", th);
    }

    public void a(ChildVideo childVideo) {
        this.j.addAll(childVideo.getBlocks());
        for (int size = ContainerUtil.getSize(this.j) - ContainerUtil.getSize(childVideo.getBlocks()); size < ContainerUtil.getSize(this.j); size++) {
            if (ContainerUtil.isOutOfBound(size, e)) {
                this.h.add(this.j.get(size));
            } else if (!ContainerUtil.isOutOfBound(e[size], this.h)) {
                this.h.set(e[size], this.j.get(size));
            } else {
                return;
            }
        }
        if (TextUtils.isEmpty(childVideo.getMeta().getMore()) || childVideo.getMeta().getMore().contains(ChildVideo.ItemsBean.TYPE_FILTER)) {
            this.m = false;
            return;
        }
        this.m = true;
        this.f = childVideo.getMeta().getMore();
    }

    public void loadMusicData() {
        addToDisposeBag(ChildSongApiHelper.getInstance().loadCategoryContent(3200L, "全部").subscribeOn(MicoSchedulers.io()).flatMap(new Function<PatchWall.Category.Block, ObservableSource<ChildStory.BlocksBean>>() { // from class: com.xiaomi.micolauncher.module.homepage.fragment.HomeKidsFragment.5
            /* renamed from: a */
            public ObservableSource<ChildStory.BlocksBean> apply(@NonNull PatchWall.Category.Block block) throws Exception {
                HomeKidsFragment.this.n = true;
                if (block != null) {
                    ArrayList<PatchWall.Item> arrayList = new ArrayList(block.itemList);
                    ArrayList arrayList2 = new ArrayList();
                    HomeKidsFragment.this.a = new ChildStory.BlocksBean();
                    HomeKidsFragment.this.a.setBlockType(IBlockBean.BLOCK_TYPE_SONG);
                    HomeKidsFragment.this.a.setTitle(HomeKidsFragment.this.getString(R.string.home_kid_song_block_title));
                    for (PatchWall.Item item : arrayList) {
                        ChildStory.BlocksBean.ItemsBean itemsBean = new ChildStory.BlocksBean.ItemsBean();
                        itemsBean.setTitle(item.title);
                        itemsBean.setImageUrl(item.images.poster.url);
                        itemsBean.setTarget(item.getItemTarget());
                        itemsBean.setId(item.getItemId());
                        arrayList2.add(itemsBean);
                    }
                    HomeKidsFragment.this.a.setItems(arrayList2);
                }
                return Observable.just(HomeKidsFragment.this.a);
            }
        }).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.fragment.-$$Lambda$HomeKidsFragment$AOLUn2NnGNCsClA9MamfkHJubHE
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                HomeKidsFragment.this.a((ChildStory.BlocksBean) obj);
            }
        }));
    }

    public /* synthetic */ void a(ChildStory.BlocksBean blocksBean) throws Exception {
        f();
    }

    private void c() {
        ChildVideo.BlocksBean blocksBean = new ChildVideo.BlocksBean(ChildVideo.BLOCK_BANNER);
        ChildVideo.BlocksBean blocksBean2 = new ChildVideo.BlocksBean("block_grid_button");
        ChildVideo.BlocksBean blocksBean3 = new ChildVideo.BlocksBean("block_grid_button");
        ChildVideo.BlocksBean blocksBean4 = new ChildVideo.BlocksBean("block_grid_button");
        ChildVideo.BlocksBean blocksBean5 = new ChildVideo.BlocksBean("block_grid_button");
        ChildVideo.BlocksBean blocksBean6 = new ChildVideo.BlocksBean("block_grid_button");
        AppBlock appBlock = new AppBlock();
        appBlock.setTitle(getString(R.string.home_kid_app_block_title));
        ChildStory.BlocksBean blocksBean7 = new ChildStory.BlocksBean(true);
        ChildStory.BlocksBean blocksBean8 = new ChildStory.BlocksBean(true);
        ChildStory.BlocksBean blocksBean9 = new ChildStory.BlocksBean(true);
        ChildStory.BlocksBean blocksBean10 = new ChildStory.BlocksBean(true);
        this.h.add(blocksBean);
        this.h.add(blocksBean2);
        this.h.add(appBlock);
        this.h.add(blocksBean7);
        this.h.add(blocksBean3);
        this.h.add(blocksBean10);
        this.h.add(blocksBean4);
        this.h.add(blocksBean8);
        this.h.add(blocksBean5);
        this.h.add(blocksBean9);
        this.h.add(blocksBean6);
    }

    private void d() {
        List<IBlockBean> list = this.h;
        if (list == null || list.size() == 0) {
            c();
        }
        this.g.setData(this.h);
    }

    private void e() {
        for (ChildStory.BlocksBean blocksBean : this.k.getBlocks()) {
            if (ContainerUtil.contains(c, blocksBean.getTitle())) {
                this.i.add(blocksBean);
            }
        }
        int size = ContainerUtil.getSize(this.i);
        for (int i = 0; i < size && !ContainerUtil.isOutOfBound(d[i], this.h); i++) {
            this.h.set(d[i], this.i.get(i));
        }
    }

    private void f() {
        if (!ContainerUtil.isOutOfBound(5L, this.h)) {
            this.h.set(5, this.a);
            this.g.notifyItemChanged(5);
            L.childContent.i("add block index %d", 5);
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    protected BaseAdapter<? extends RecyclerView.ViewHolder> createAdapter() {
        this.g = new HomeKidsAdapter(this.context, this.recyclerView);
        return this.g;
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onHiddenChanged(boolean z) {
        super.onHiddenChanged(z);
        if (z) {
            this.g.removeMessages();
        } else {
            this.g.startMessages();
        }
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this.p.removeCallbacksAndMessages(null);
        ChildSongApiHelper.getInstance().release();
    }

    private void g() {
        Disposable disposable = this.o;
        if (disposable != null && !disposable.isDisposed()) {
            this.o.dispose();
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.EntertainmentFragment.FragmentRefreshListener
    public void onRefresh() {
        if (hasData() && this.recyclerView != null && this.recyclerView.getAdapter() != null) {
            this.recyclerView.getAdapter().notifyDataSetChanged();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void resetRecyclerViewPosition(ResetScrollViewPositionEvent resetScrollViewPositionEvent) {
        if (!ChildModeManager.getManager().isChildMode() && resetScrollViewPositionEvent.getEventType() == 1 && this.recyclerView != null) {
            this.recyclerView.scrollToPosition(0);
        }
    }
}
