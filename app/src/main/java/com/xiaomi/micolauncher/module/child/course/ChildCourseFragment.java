package com.xiaomi.micolauncher.module.child.course;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import com.xiaomi.mico.base.ui.MicoAnimationTouchListener;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.ChildVideo;
import com.xiaomi.micolauncher.api.model.CourseTab;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.common.GlideUtils;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.MicoHandler;
import com.xiaomi.micolauncher.common.base.BaseFragment;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.common.widget.HorizontalRecyclerView;
import com.xiaomi.micolauncher.module.child.childvideo.ChildVideoDataManager;
import com.xiaomi.micolauncher.module.child.course.CourseTabFilterAdapter;
import com.xiaomi.micolauncher.module.child.enent.CourseTabChangeEvent;
import com.xiaomi.micolauncher.module.homepage.activity.ChildCourseActivity;
import com.xiaomi.micolauncher.module.homepage.adapter.BaseAdapter;
import com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment;
import com.xiaomi.micolauncher.skills.video.model.VideoItem;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public class ChildCourseFragment extends BaseHomeFragment {
    public static final String TAG = "ChildCourseFragment";
    private static final long e = TimeUnit.HOURS.toMillis(12);
    private int A;
    private String B;
    private RecyclerView.OnScrollListener C;
    private boolean E;
    private Disposable F;
    private Disposable G;
    TextView a;
    ImageView b;
    LinearLayout c;
    public HorizontalRecyclerView courseTabRecyclerView;
    ImageView d;
    private String f;
    private boolean j;
    private int k;
    private a l;
    private boolean m;
    private ChildCourseAdapter n;
    private CourseTabFilterAdapter o;
    private CourseFilterContentAdapter p;
    private String q;
    private String r;
    private String s;
    private ChildVideo.ItemsBean t;
    private String u;
    private CourseTab v;
    private RecyclerView.ItemDecoration w;
    private RecyclerView.ItemDecoration x;
    private int y;
    private boolean z;
    private List<ChildVideo.BlocksBean> g = new ArrayList();
    private List<ChildVideo.ItemsBean> h = new ArrayList();
    private List<ChildVideo.ItemsBean> i = new ArrayList();
    private boolean D = true;

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
        return R.layout.fragment_child_course;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    public void recordShow() {
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    @SuppressLint({"ClickableViewAccessibility"})
    protected void initViews(View view) {
        this.courseTabRecyclerView = (HorizontalRecyclerView) view.findViewById(R.id.course_tab_recycler_view);
        this.a = (TextView) view.findViewById(R.id.filter_name);
        this.b = (ImageView) view.findViewById(R.id.filter_more);
        this.c = (LinearLayout) view.findViewById(R.id.tab_ll);
        this.d = (ImageView) view.findViewById(R.id.empty_state);
        this.m = true;
        if (ChildCourseActivity.courseFullScreen) {
            this.A = getResources().getDimensionPixelOffset(R.dimen.screen_width) - getResources().getDimensionPixelOffset(R.dimen.status_bar_height);
        } else {
            this.A = getResources().getDimensionPixelOffset(R.dimen.screen_width) - getResources().getDimensionPixelOffset(R.dimen.home_tab_width_child);
        }
        this.B = getResources().getString(R.string.course_recent);
        ViewGroup.LayoutParams layoutParams = this.c.getLayoutParams();
        layoutParams.width = this.A;
        this.c.setLayoutParams(layoutParams);
        this.recyclerView.setHasFixedSize(true);
        this.y = getResources().getDimensionPixelOffset(R.dimen.course_card_margin_left);
        this.w = new RecyclerView.ItemDecoration() { // from class: com.xiaomi.micolauncher.module.child.course.ChildCourseFragment.1
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(@NonNull Rect rect, @NonNull View view2, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.State state) {
                rect.right = ChildCourseFragment.this.y;
            }
        };
        this.x = new RecyclerView.ItemDecoration() { // from class: com.xiaomi.micolauncher.module.child.course.ChildCourseFragment.2
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(@NonNull Rect rect, @NonNull View view2, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.State state) {
                if (recyclerView.getChildLayoutPosition(view2) < 2) {
                    rect.left = ChildCourseFragment.this.y;
                }
                rect.right = ChildCourseFragment.this.y;
                rect.bottom = ChildCourseFragment.this.y;
            }
        };
        this.recyclerView.addItemDecoration(this.w);
        b();
        this.l = new a(this);
        this.a.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.child.course.-$$Lambda$ChildCourseFragment$HDcwdqCsg0MaLPgxoDI4XwOjpcw
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                ChildCourseFragment.this.b(view2);
            }
        });
        this.a.setOnTouchListener(MicoAnimationTouchListener.getInstance());
        this.b.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.child.course.-$$Lambda$ChildCourseFragment$td_v2Vb0oBXmpFiTgNAVJbDoIIQ
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                ChildCourseFragment.this.a(view2);
            }
        });
        this.b.setOnTouchListener(MicoAnimationTouchListener.getInstance());
        this.o = new CourseTabFilterAdapter(getContext(), 0);
        this.courseTabRecyclerView.setAdapter(this.o);
        this.o.a(new CourseTabFilterAdapter.OnFilterItemClickListener() { // from class: com.xiaomi.micolauncher.module.child.course.-$$Lambda$ChildCourseFragment$WozcxGpXFX-y7SFs9_JKKdxmd2k
            @Override // com.xiaomi.micolauncher.module.child.course.CourseTabFilterAdapter.OnFilterItemClickListener
            public final void onFilterClick(int i) {
                ChildCourseFragment.this.b(i);
            }
        });
        this.p = new CourseFilterContentAdapter(getContext());
        final int dimensionPixelOffset = getResources().getDimensionPixelOffset(R.dimen.course_filter_list_divider);
        this.courseTabRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() { // from class: com.xiaomi.micolauncher.module.child.course.ChildCourseFragment.3
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect rect, View view2, RecyclerView recyclerView, RecyclerView.State state) {
                rect.right = dimensionPixelOffset;
            }
        });
        if (this.courseTabRecyclerView.getItemAnimator() != null) {
            ((SimpleItemAnimator) this.courseTabRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(View view) {
        a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(View view) {
        a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(int i) {
        a(i);
        a(this.z);
    }

    private void a() {
        ActivityLifeCycleManager.startActivityQuietly(new Intent(this.context, CourseTabSelectActivity.class));
    }

    private void b() {
        this.C = new RecyclerView.OnScrollListener() { // from class: com.xiaomi.micolauncher.module.child.course.ChildCourseFragment.4
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(RecyclerView recyclerView, int i) {
                super.onScrollStateChanged(recyclerView, i);
                if (i == 0) {
                    ChildCourseFragment.this.m = true;
                    GlideUtils.resumeRequests(ChildCourseFragment.this.context);
                } else {
                    ChildCourseFragment.this.m = false;
                    GlideUtils.pauseRequests(ChildCourseFragment.this.context);
                }
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (linearLayoutManager != null && recyclerView.getAdapter() != null) {
                    int itemCount = recyclerView.getAdapter().getItemCount();
                    int findLastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
                    int childCount = recyclerView.getChildCount();
                    if (i == 0 && findLastVisibleItemPosition == itemCount - 1 && childCount > 0 && ChildCourseFragment.this.j) {
                        ChildCourseFragment.this.D = false;
                        if (ChildVideo.ItemsBean.TYPE_GRID.equals(ChildCourseFragment.this.q)) {
                            ChildCourseFragment.this.k = 0;
                            ChildCourseFragment.this.g();
                        } else if (ChildVideo.ItemsBean.TYPE_LIST.equals(ChildCourseFragment.this.q)) {
                            ChildCourseFragment.this.f();
                        }
                    }
                }
            }

            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                super.onScrolled(recyclerView, i, i2);
            }
        };
        this.recyclerView.addOnScrollListener(this.C);
    }

    private void a(int i) {
        this.t = this.h.get(i);
        this.z = TextUtils.isEmpty(this.q) || !this.q.equals(this.t.getType());
        this.q = this.t.getType();
        this.r = this.t.getRequestUrl();
        L.course.i("current filter item title is %s  url is ", this.t.getTitle(), this.t.getRequestUrl());
        KidCourseDataManager.getManager().setSubTab(this.t.getTitle());
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x001c, code lost:
        if (r1.equals(com.xiaomi.micolauncher.api.model.ChildVideo.ItemsBean.TYPE_LIST) == false) goto L_0x002a;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void a(boolean r6) {
        /*
            r5 = this;
            r0 = 1
            r5.D = r0
            java.lang.String r1 = r5.q
            int r2 = r1.hashCode()
            r3 = 518990123(0x1eef292b, float:2.5322136E-20)
            r4 = 0
            if (r2 == r3) goto L_0x001f
            r3 = 519130755(0x1ef14e83, float:2.554934E-20)
            if (r2 == r3) goto L_0x0015
            goto L_0x002a
        L_0x0015:
            java.lang.String r2 = "type_list"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x002a
            goto L_0x002b
        L_0x001f:
            java.lang.String r0 = "type_grid"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x002a
            r0 = r4
            goto L_0x002b
        L_0x002a:
            r0 = -1
        L_0x002b:
            switch(r0) {
                case 0: goto L_0x0078;
                case 1: goto L_0x0030;
                default: goto L_0x002e;
            }
        L_0x002e:
            goto L_0x00aa
        L_0x0030:
            if (r6 == 0) goto L_0x0056
            androidx.recyclerview.widget.RecyclerView r6 = r5.recyclerView
            com.xiaomi.micolauncher.module.child.course.CourseFilterContentAdapter r0 = r5.p
            r6.setAdapter(r0)
            com.xiaomi.micolauncher.module.music.swiperefresh.BaseGridLayoutManager r6 = new com.xiaomi.micolauncher.module.music.swiperefresh.BaseGridLayoutManager
            android.content.Context r0 = r5.getContext()
            r1 = 2
            r6.<init>(r0, r1, r4, r4)
            androidx.recyclerview.widget.RecyclerView r0 = r5.recyclerView
            r0.setLayoutManager(r6)
            androidx.recyclerview.widget.RecyclerView r6 = r5.recyclerView
            androidx.recyclerview.widget.RecyclerView$ItemDecoration r0 = r5.w
            r6.removeItemDecoration(r0)
            androidx.recyclerview.widget.RecyclerView r6 = r5.recyclerView
            androidx.recyclerview.widget.RecyclerView$ItemDecoration r0 = r5.x
            r6.addItemDecoration(r0)
        L_0x0056:
            java.util.List<com.xiaomi.micolauncher.api.model.ChildVideo$ItemsBean> r6 = r5.i
            r6.clear()
            com.xiaomi.micolauncher.module.child.course.CourseFilterContentAdapter r6 = r5.p
            java.util.List<com.xiaomi.micolauncher.api.model.ChildVideo$ItemsBean> r0 = r5.i
            r6.setData(r0)
            java.lang.String r6 = r5.B
            com.xiaomi.micolauncher.api.model.ChildVideo$ItemsBean r0 = r5.t
            java.lang.String r0 = r0.getTitle()
            boolean r6 = r6.equals(r0)
            if (r6 == 0) goto L_0x0074
            r5.d()
            goto L_0x00aa
        L_0x0074:
            r5.f()
            goto L_0x00aa
        L_0x0078:
            if (r6 == 0) goto L_0x009d
            androidx.recyclerview.widget.RecyclerView r6 = r5.recyclerView
            com.xiaomi.micolauncher.module.child.course.ChildCourseAdapter r0 = r5.n
            r6.setAdapter(r0)
            androidx.recyclerview.widget.RecyclerView r6 = r5.recyclerView
            androidx.recyclerview.widget.LinearLayoutManager r0 = new androidx.recyclerview.widget.LinearLayoutManager
            android.content.Context r1 = r5.getContext()
            r0.<init>(r1, r4, r4)
            r6.setLayoutManager(r0)
            androidx.recyclerview.widget.RecyclerView r6 = r5.recyclerView
            androidx.recyclerview.widget.RecyclerView$ItemDecoration r0 = r5.x
            r6.removeItemDecoration(r0)
            androidx.recyclerview.widget.RecyclerView r6 = r5.recyclerView
            androidx.recyclerview.widget.RecyclerView$ItemDecoration r0 = r5.w
            r6.addItemDecoration(r0)
        L_0x009d:
            r5.c()
            r5.g()
            android.widget.ImageView r6 = r5.d
            r0 = 8
            r6.setVisibility(r0)
        L_0x00aa:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.micolauncher.module.child.course.ChildCourseFragment.a(boolean):void");
    }

    private void c() {
        this.k = 0;
        this.f = ChildVideoDataManager.MI_TV_URL_PREFIX + this.u;
        this.g.clear();
        this.n.notifyDataSetChanged();
    }

    private void d() {
        addToDisposeBag(ChildVideoDataManager.getManager().loadCourseListFromDb().flatMap($$Lambda$PMcQipigtANKXr_LQnPxZn2Bmc.INSTANCE).map($$Lambda$ChildCourseFragment$fhRo6IHVOtrQERrLWYU1BrDUDkU.INSTANCE).toList().toObservable().subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.child.course.-$$Lambda$ChildCourseFragment$t1GMZDqJBIYdFLiCdeVIwa-t64c
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ChildCourseFragment.this.d((List) obj);
            }
        }, $$Lambda$ChildCourseFragment$_bX5VEEvl9g_gU6RaChCHhrRua8.INSTANCE));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ ChildVideo.ItemsBean a(VideoItem videoItem) throws Exception {
        return new ChildVideo.ItemsBean().convertFromSource(videoItem);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void d(List list) throws Exception {
        this.i.addAll(list);
        e();
        this.p.setData(this.i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(Throwable th) throws Exception {
        L.course.e("ChildCourseFragment loadCourseHistory failed", th);
    }

    private void e() {
        if (ContainerUtil.isEmpty(this.i)) {
            this.d.setVisibility(0);
        } else {
            this.d.setVisibility(8);
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    public void loadData() {
        this.E = false;
        this.D = true;
        loadDataFromSever();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void f() {
        this.r = a(this.r);
        L.course.i("load filter content  %s  %s", this.t.getTitle(), this.r);
        addToDisposeBag(KidCourseDataManager.getManager().loadCategoryContent(this.r, this.D).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.child.course.-$$Lambda$ChildCourseFragment$vSLqxjriQZHUfmcTIneRfpRHD-w
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ChildCourseFragment.this.a((ChildVideo.BlocksBean) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(ChildVideo.BlocksBean blocksBean) throws Exception {
        if (!TextUtils.isEmpty(blocksBean.getMeta().getMore())) {
            this.j = true;
            if (this.D) {
                KidCourseDataManager.getManager().addBlockCache(this.r, blocksBean);
            }
            this.r = blocksBean.getMeta().getMore();
            L.course.i("loadFilterContent  more  url  %s", this.r);
        } else {
            this.j = false;
        }
        this.i.addAll(blocksBean.getItems());
        e();
        this.p.setData(this.i);
    }

    private String a(String str) {
        return str.replace("pageno=0&", "");
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment, com.xiaomi.micolauncher.common.base.BaseFragment
    @NonNull
    public BaseFragment.EventBusFragmentMode getEventBusRegisterMode() {
        return BaseFragment.EventBusFragmentMode.WHOLE_LIFE;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCourseTabChangeEvent(CourseTabChangeEvent courseTabChangeEvent) {
        this.o.setDefaultPosition();
        if (!ChildVideo.ItemsBean.TYPE_GRID.equals(this.q)) {
            this.recyclerView.setAdapter(this.n);
            this.recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), 0, false));
            this.recyclerView.removeItemDecoration(this.x);
            this.recyclerView.addItemDecoration(this.w);
        }
        loadData();
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        this.n.startMessages();
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    protected boolean hasData() {
        return this.E;
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    protected void loadDataFromSever() {
        i();
        this.G = KidCourseDataManager.getManager().getCourseTabList().subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.child.course.-$$Lambda$ChildCourseFragment$YJ0b3Y4LlcTpFCKU9gK4RAvtKLU
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ChildCourseFragment.this.c((List) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void c(List list) throws Exception {
        this.E = true;
        this.v = (CourseTab) list.get(SystemSetting.getKeySelectCourseTabIndex());
        ChildVideoDataManager.getManager().setCurrentCourseTab(this.v);
        L.course.i("current course tab name %s  filterId %s  contentId %s", this.v.getName(), this.v.getFilterId(), this.v.getContentId());
        KidCourseDataManager.getManager().setCurrentTab(this.v);
        this.s = this.v.getFilterId();
        this.u = this.v.getContentId();
        this.a.setText(this.v.getName());
        this.b.setVisibility(0);
        this.f = ChildVideoDataManager.MI_TV_URL_PREFIX + this.u;
        h();
        if (this.m) {
            this.g.clear();
            this.n.notifyDataSetChanged();
            this.k = 0;
        }
        g();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void g() {
        i();
        int i = this.k;
        if (i < 3) {
            this.k = i + 1;
            this.F = KidCourseDataManager.getManager().getRecommendData(this.f, this.D).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.child.course.-$$Lambda$ChildCourseFragment$paZqxRDLGiPSAYnbg6MK9sebL0k
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ChildCourseFragment.this.a((ChildVideo) obj);
                }
            }, $$Lambda$ChildCourseFragment$yzUW6KC5ttbTf7Cm0G2g9q77bM.INSTANCE);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(ChildVideo childVideo) throws Exception {
        this.g.addAll(childVideo.getBlocks());
        L.course.i("course video block size  from remote %d", Integer.valueOf(this.g.size()));
        this.n.setData(this.g);
        if (TextUtils.isEmpty(childVideo.getMeta().getMore()) || childVideo.getMeta().getMore().contains(ChildVideo.ItemsBean.TYPE_FILTER)) {
            this.j = false;
            return;
        }
        this.j = true;
        if (this.D) {
            KidCourseDataManager.getManager().addChildVideoCache(this.f, childVideo);
        }
        this.f = childVideo.getMeta().getMore();
        g();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Throwable th) throws Exception {
        L.course.e("get course video error ", th);
    }

    private void h() {
        addToDisposeBag(KidCourseDataManager.getManager().getVideoTypeFromRemote(this.s).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.child.course.-$$Lambda$ChildCourseFragment$rE5vrr3hHL8LmulYPowwqmRXlDI
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ChildCourseFragment.this.b((List) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(List list) throws Exception {
        this.h.clear();
        this.h.addAll(list);
        this.o.a(a(this.h));
        a(0);
    }

    private List<ChildVideo.ItemsBean> a(List<ChildVideo.ItemsBean> list) {
        ChildVideo.ItemsBean itemsBean = new ChildVideo.ItemsBean();
        itemsBean.setTitle(getString(R.string.course_recommend));
        itemsBean.setType(ChildVideo.ItemsBean.TYPE_GRID);
        ChildVideo.ItemsBean itemsBean2 = new ChildVideo.ItemsBean();
        itemsBean2.setTitle(this.B);
        list.add(0, itemsBean);
        list.add(1, itemsBean2);
        return list;
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        this.n.removeMessages();
    }

    /* loaded from: classes3.dex */
    private static class a extends MicoHandler {
        private WeakReference<ChildCourseFragment> a;

        @Override // com.xiaomi.micolauncher.common.MicoHandler
        public String getLogTag() {
            return ChildCourseFragment.TAG;
        }

        a(ChildCourseFragment childCourseFragment) {
            this.a = new WeakReference<>(childCourseFragment);
        }

        @Override // com.xiaomi.micolauncher.common.MicoHandler
        public void processMessage(Message message) {
            final ChildCourseFragment childCourseFragment = this.a.get();
            if (childCourseFragment != null) {
                childCourseFragment.getClass();
                ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.module.child.course.-$$Lambda$nCLH9QWtPPl4QUEGf88B-36u7A0
                    @Override // java.lang.Runnable
                    public final void run() {
                        ChildCourseFragment.this.loadData();
                    }
                });
            }
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.fragment.BaseHomeFragment
    protected BaseAdapter<? extends RecyclerView.ViewHolder> createAdapter() {
        this.n = new ChildCourseAdapter(this.context);
        return this.n;
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onHiddenChanged(boolean z) {
        super.onHiddenChanged(z);
        if (z) {
            this.n.removeMessages();
        } else {
            this.n.startMessages();
        }
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this.l.removeCallbacksAndMessages(null);
        KidCourseDataManager.getManager().setCurrentTab(null);
    }

    private void i() {
        Disposable disposable = this.F;
        if (disposable != null && !disposable.isDisposed()) {
            this.F.dispose();
        }
        Disposable disposable2 = this.G;
        if (disposable2 != null && !disposable2.isDisposed()) {
            this.G.dispose();
        }
    }
}
