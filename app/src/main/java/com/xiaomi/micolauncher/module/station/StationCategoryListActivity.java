package com.xiaomi.micolauncher.module.station;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.mico.base.utils.ToastUtil;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.ApiConstants;
import com.xiaomi.micolauncher.api.ApiManager;
import com.xiaomi.micolauncher.api.model.ChildStory;
import com.xiaomi.micolauncher.api.model.ChildVideo;
import com.xiaomi.micolauncher.api.model.IListItem;
import com.xiaomi.micolauncher.api.model.Station;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.base.BaseActivity;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.module.child.childstory.ChildStoryDataManager;
import com.xiaomi.micolauncher.module.child.childvideo.ChildVideoDataManager;
import com.xiaomi.micolauncher.module.music.swiperefresh.BaseGridLayoutManager;
import com.xiaomi.micolauncher.module.station.adapter.StationListSquareAdapter;
import com.xiaomi.micolauncher.module.video.childmode.ChildModeManager;
import com.xiaomi.micolauncher.skills.music.controller.LocalPlayerManager;
import com.xiaomi.micolauncher.skills.video.model.VideoItem;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes3.dex */
public class StationCategoryListActivity extends BaseActivity {
    public static final String EXTRA_STYLE_KID = "style_kid";
    public static final String EXTRA_URL = "url";
    public static final String KID_TYPE = "kid";
    public static final int MAX_PAGE_SIZE = 50;
    public static final String TYPE_STATION_COLLECT = "station_collect";
    public static final String TYPE_STATION_MORE = "station_more";
    public static final String TYPE_STATION_RECENT = "station_recent";
    public static final String TYPE_VIDEO_MORE = "video_more";
    public static final String TYPE_VIDEO_RECENT = "video_recent";
    String a;
    private String b;
    protected List<String> blackList;
    private String c;
    private RecyclerView d;
    private ImageView e;
    private StationListSquareAdapter f;
    private List<IListItem> g;
    private List<IListItem> h = new ArrayList();
    private TextView i;
    private int j;
    private int k;
    private ImageView l;
    private ImageView m;
    private TextView n;
    private boolean o;
    private String p;
    private boolean q;
    private boolean r;

    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_video_category_list);
        a();
        this.j = getResources().getDimensionPixelSize(R.dimen.size_32dp);
        this.k = getResources().getDimensionPixelSize(R.dimen.common_video_list_item_left_margin);
        b();
        c();
        d();
        scheduleToClose(TimeUnit.MINUTES.toMillis(1L));
    }

    private void a() {
        if (getIntent() != null) {
            this.b = getIntent().getStringExtra("type");
            this.c = getIntent().getStringExtra("title");
            this.o = getIntent().getBooleanExtra(EXTRA_STYLE_KID, true);
            this.p = getIntent().getStringExtra("url");
            this.a = getIntent().getStringExtra("block_id");
        }
        if (!ApiConstants.isPrevEnv()) {
            return;
        }
        if (TextUtils.isEmpty(this.p)) {
            ToastUtil.showToast(this.c + "id  " + this.a, 5000);
            return;
        }
        ToastUtil.showToast(this.c + "地址为  " + this.p.substring(21, 25), 5000);
    }

    private void b() {
        this.d = (RecyclerView) findViewById(R.id.recycler_view);
        this.e = (ImageView) findViewById(R.id.empty_view);
        boolean z = false;
        this.d.setLayoutManager(new BaseGridLayoutManager(this, 2, 0, false));
        if (TYPE_VIDEO_RECENT.equals(this.b) || TYPE_VIDEO_MORE.equals(this.b)) {
            this.f = new StationListSquareAdapter(this, 1003);
        } else {
            this.f = new StationListSquareAdapter(this, 1000);
        }
        this.d.setAdapter(this.f);
        this.d.addItemDecoration(new RecyclerView.ItemDecoration() { // from class: com.xiaomi.micolauncher.module.station.StationCategoryListActivity.1
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
                if (recyclerView.getChildLayoutPosition(view) < 2) {
                    rect.left = StationCategoryListActivity.this.k;
                }
                rect.right = StationCategoryListActivity.this.j;
                rect.bottom = StationCategoryListActivity.this.j;
            }
        });
        View findViewById = findViewById(R.id.rootView);
        if (ChildModeManager.getManager().isChildMode() || this.o) {
            z = true;
        }
        this.o = z;
        if (!Hardware.isBigScreen() || !this.o) {
            findViewById.setBackgroundColor(getColor(R.color.color_000000));
        } else {
            findViewById.setBackgroundColor(getColor(R.color.child_mode_bg_color));
            this.l = (ImageView) findViewById(R.id.classify_star_before);
            this.m = (ImageView) findViewById(R.id.classify_star_after);
            this.n = (TextView) findViewById(R.id.empty_text);
        }
        this.i = (TextView) findViewById(R.id.tvTitle);
        TextView textView = this.i;
        if (textView != null) {
            textView.setText(this.c);
        }
        this.d.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.xiaomi.micolauncher.module.station.StationCategoryListActivity.2
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(@NotNull RecyclerView recyclerView, int i) {
                GridLayoutManager gridLayoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
                if (gridLayoutManager != null && recyclerView.getAdapter() != null) {
                    int itemCount = recyclerView.getAdapter().getItemCount();
                    int findLastVisibleItemPosition = gridLayoutManager.findLastVisibleItemPosition();
                    int childCount = recyclerView.getChildCount();
                    if (i == 0 && findLastVisibleItemPosition == itemCount - 1 && childCount > 0 && StationCategoryListActivity.this.q) {
                        StationCategoryListActivity.this.d();
                    }
                }
            }
        });
    }

    private void c() {
        this.g = new ArrayList();
        for (int i = 0; i < 6; i++) {
            this.g.add(new Station.Item());
        }
        this.f.setDataList(this.g);
        this.r = true;
    }

    public void d() {
        if (ApiManager.isInited()) {
            String str = this.b;
            char c = 65535;
            switch (str.hashCode()) {
                case -1618053991:
                    if (str.equals(TYPE_VIDEO_MORE)) {
                        c = 4;
                        break;
                    }
                    break;
                case -141126784:
                    if (str.equals(TYPE_STATION_MORE)) {
                        c = 2;
                        break;
                    }
                    break;
                case -38256993:
                    if (str.equals(TYPE_VIDEO_RECENT)) {
                        c = 3;
                        break;
                    }
                    break;
                case 174626079:
                    if (str.equals(TYPE_STATION_COLLECT)) {
                        c = 1;
                        break;
                    }
                    break;
                case 1949581254:
                    if (str.equals(TYPE_STATION_RECENT)) {
                        c = 0;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                    addToDisposeBag(loadBlackList(this).flatMap(new Function() { // from class: com.xiaomi.micolauncher.module.station.-$$Lambda$StationCategoryListActivity$U0HBhmuc2b0dR1Fm9nleq_d7V9o
                        @Override // io.reactivex.functions.Function
                        public final Object apply(Object obj) {
                            ObservableSource g;
                            g = StationCategoryListActivity.this.g((List) obj);
                            return g;
                        }
                    }).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.station.-$$Lambda$StationCategoryListActivity$0TDhS9cfZ_q6Ywz3QhTt1V2Xze4
                        @Override // io.reactivex.functions.Consumer
                        public final void accept(Object obj) {
                            StationCategoryListActivity.this.f((List) obj);
                        }
                    }, $$Lambda$StationCategoryListActivity$EGT2L_8DeTjIU3Q_AN2TPJHJf10.INSTANCE));
                    return;
                case 1:
                    addToDisposeBag(loadBlackList(this).flatMap($$Lambda$StationCategoryListActivity$3ULIJ3OqRszcof0Kb1iQyNzYltU.INSTANCE).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.station.-$$Lambda$StationCategoryListActivity$2I9dc3on7egk4BcFsRiCiBRAYN8
                        @Override // io.reactivex.functions.Consumer
                        public final void accept(Object obj) {
                            StationCategoryListActivity.this.d((List) obj);
                        }
                    }, $$Lambda$StationCategoryListActivity$uhkfWyffavEakuO902V6ri5jcM.INSTANCE));
                    return;
                case 2:
                    if (getIntent() != null) {
                        addToDisposeBag(ApiManager.displayService.getChildStoryBlock(Long.parseLong(this.a), 0).flatMap($$Lambda$StationCategoryListActivity$5pM_QeIzab8vw8rDYEUhK13ctY.INSTANCE).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.station.-$$Lambda$StationCategoryListActivity$a2ZgWmePl11WKx_IncOU84Wps8o
                            @Override // io.reactivex.functions.Consumer
                            public final void accept(Object obj) {
                                StationCategoryListActivity.this.c((List) obj);
                            }
                        }, $$Lambda$StationCategoryListActivity$muycga_7L8TmqKUIo_FA1foPxSQ.INSTANCE));
                        return;
                    }
                    return;
                case 3:
                    addToDisposeBag(ChildVideoDataManager.getManager().loadVideoListFromDb().flatMap($$Lambda$PMcQipigtANKXr_LQnPxZn2Bmc.INSTANCE).map($$Lambda$StationCategoryListActivity$wgVlOjecURWDlyU9ySc5Ub1Zk0.INSTANCE).toList().toObservable().subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.station.-$$Lambda$StationCategoryListActivity$z_H33Ddst1kXgyc_wmC0_VPjRmM
                        @Override // io.reactivex.functions.Consumer
                        public final void accept(Object obj) {
                            StationCategoryListActivity.this.b((List) obj);
                        }
                    }, new Consumer() { // from class: com.xiaomi.micolauncher.module.station.-$$Lambda$StationCategoryListActivity$p9izqlZiHv9NNqmpMa-mOpyb-Iw
                        @Override // io.reactivex.functions.Consumer
                        public final void accept(Object obj) {
                            StationCategoryListActivity.this.a((Throwable) obj);
                        }
                    }));
                    return;
                case 4:
                    addToDisposeBag(ChildVideoDataManager.getManager().loadCategoryContent(this.p.replace("pageno=0&", "")).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.station.-$$Lambda$StationCategoryListActivity$LP-EBYbcIlIzTqWDe8jOQn_dLug
                        @Override // io.reactivex.functions.Consumer
                        public final void accept(Object obj) {
                            StationCategoryListActivity.this.a((ChildVideo.BlocksBean) obj);
                        }
                    }));
                    return;
                default:
                    return;
            }
        }
    }

    public /* synthetic */ ObservableSource g(List list) throws Exception {
        ChildStoryDataManager.getManager().setBlackList(list);
        this.blackList = list;
        return ApiManager.minaService.getStationHistory(Hardware.current(this).getName(), 50, "kid");
    }

    public /* synthetic */ void f(List list) throws Exception {
        this.h.addAll(list);
        a(this.h);
    }

    public static /* synthetic */ void d(Throwable th) throws Exception {
        L.childmode.e("StationCategoryListActivity$loadData ApiManager.minaService.getStationHistory failed:", th);
    }

    public static /* synthetic */ ObservableSource e(List list) throws Exception {
        ChildStoryDataManager.getManager().setBlackList(list);
        return ApiManager.minaService.getStationCollectedList("kid");
    }

    public /* synthetic */ void d(List list) throws Exception {
        this.h.addAll(list);
        a(this.h);
    }

    public static /* synthetic */ void c(Throwable th) throws Exception {
        L.childmode.e("StationCategoryListActivity$loadData ApiManager.minaService.getStationCollectedList failed", th);
    }

    public static /* synthetic */ ObservableSource a(ChildStory.BlocksBean blocksBean) throws Exception {
        return Observable.fromIterable(blocksBean.getItems()).map($$Lambda$StationCategoryListActivity$F3X6FvmKHeQ6OvwRvmYnReJxPIw.INSTANCE).toList().toObservable();
    }

    public static /* synthetic */ Station.Item a(ChildStory.BlocksBean.ItemsBean itemsBean) throws Exception {
        return new Station.Item().convertFromSource(itemsBean);
    }

    public /* synthetic */ void c(List list) throws Exception {
        this.h.addAll(list);
        a(this.h);
    }

    public static /* synthetic */ void b(Throwable th) throws Exception {
        L.childmode.e("StationCategoryListActivity$loadData ApiManager.displayService.getChildStoryBlock failed", th);
    }

    public static /* synthetic */ ChildVideo.ItemsBean a(VideoItem videoItem) throws Exception {
        return new ChildVideo.ItemsBean().convertFromSource(videoItem);
    }

    public /* synthetic */ void b(List list) throws Exception {
        this.h.addAll(list);
        a(this.h);
    }

    public /* synthetic */ void a(Throwable th) throws Exception {
        a((List<IListItem>) null);
        L.childmode.e("StationCategoryListActivity$loadData ChildVideoDataManager.loadVideoHistory failed", th);
    }

    public /* synthetic */ void a(ChildVideo.BlocksBean blocksBean) throws Exception {
        if (!TextUtils.isEmpty(blocksBean.getMeta().getMore())) {
            this.q = true;
            this.p = blocksBean.getMeta().getMore();
            L.childContent.i("load more video  url  %s", this.p);
        } else {
            this.q = false;
        }
        if (this.r) {
            this.g.clear();
            this.r = false;
        }
        this.g.addAll(blocksBean.getItems());
        this.f.setDataList(this.g);
    }

    private void a(List<IListItem> list) {
        this.g.clear();
        L.childContent.i("list size %d", Integer.valueOf(ContainerUtil.getSize(list)));
        if (ContainerUtil.isEmpty(list)) {
            this.e.setVisibility(0);
            if (Hardware.isBigScreen() && ChildModeManager.getManager().isChildMode()) {
                this.i.setVisibility(8);
                this.l.setVisibility(8);
                this.m.setVisibility(8);
                this.n.setVisibility(0);
            }
        } else {
            this.g.addAll(list);
        }
        this.f.setDataList(this.g);
    }

    private static void a(Context context, String str, String str2, boolean z) {
        Intent intent = new Intent(context, StationCategoryListActivity.class);
        intent.putExtra("title", str);
        intent.putExtra("type", str2);
        intent.putExtra(EXTRA_STYLE_KID, z);
        ActivityLifeCycleManager.startActivityQuietly(intent);
    }

    public static void openStationWithTitleRecent(Context context, boolean z) {
        a(context, context.getResources().getString(R.string.video_recent_list_title), TYPE_STATION_RECENT, z);
    }

    public static void openStationWithTitleCollection(Context context, boolean z) {
        a(context, context.getResources().getString(R.string.collection_title), TYPE_STATION_COLLECT, z);
    }

    public static void openStationWithBlock(Context context, ChildStory.BlocksBean blocksBean) {
        if (blocksBean != null) {
            Intent intent = new Intent(context, StationCategoryListActivity.class);
            intent.putExtra("title", blocksBean.getTitle());
            intent.putExtra("type", TYPE_STATION_MORE);
            intent.putExtra("block_id", blocksBean.getId());
            intent.putExtra(EXTRA_STYLE_KID, true);
            ActivityLifeCycleManager.startActivityQuietly(intent);
        }
    }

    public static void openStationWithVideoRecent(Context context) {
        a(context, context.getResources().getString(R.string.video_recent_list_title), TYPE_VIDEO_RECENT, true);
    }

    public static void openStationWithVideoMore(Context context, String str, String str2) {
        Intent intent = new Intent(context, StationCategoryListActivity.class);
        intent.putExtra("title", str);
        intent.putExtra("type", TYPE_VIDEO_MORE);
        intent.putExtra(EXTRA_STYLE_KID, true);
        intent.putExtra("url", str2);
        ActivityLifeCycleManager.startActivityQuietly(intent);
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseActivity
    @NonNull
    public BaseActivity.EventBusRegisterMode getEventBusRegisterMode() {
        return BaseActivity.EventBusRegisterMode.WHOLE_LIFE;
    }

    public Observable<List<String>> loadBlackList(Context context) {
        return LocalPlayerManager.getInstance().loadBlackList();
    }
}
