package com.xiaomi.micolauncher.module.video.ui;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.elvishew.xlog.XLog;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.Video;
import com.xiaomi.micolauncher.common.base.BaseActivity;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.module.music.bean.Header;
import com.xiaomi.micolauncher.module.music.listener.BindLifecycleListener;
import com.xiaomi.micolauncher.module.music.view.MyHorizontalScrollView;
import com.xiaomi.micolauncher.module.video.manager.VideoDataManager;
import com.xiaomi.micolauncher.module.video.ui.view.VideoPatchWallListView;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class VideoPatchWallActivity extends BaseActivity {
    private MyHorizontalScrollView a;
    private VideoPatchWallListView b;
    private List<Video.Category> c = new ArrayList();
    private List<Object> d = new ArrayList();
    private boolean e;
    private int f;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_video_patch_wall);
        a();
        c();
        b();
        scheduleToClose(TimeUnit.MINUTES.toMillis(1L));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        VideoPatchWallListView videoPatchWallListView = this.b;
        if (videoPatchWallListView != null && (videoPatchWallListView instanceof BindLifecycleListener)) {
            videoPatchWallListView.onResume();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        VideoPatchWallListView videoPatchWallListView = this.b;
        if (videoPatchWallListView != null && (videoPatchWallListView instanceof BindLifecycleListener)) {
            videoPatchWallListView.onPause();
        }
    }

    private void a() {
        this.a = (MyHorizontalScrollView) findViewById(R.id.scroll_view);
        this.b = (VideoPatchWallListView) findViewById(R.id.list_view);
        this.a.setOnScrollListener(new MyHorizontalScrollView.OnScrollListener() { // from class: com.xiaomi.micolauncher.module.video.ui.-$$Lambda$VideoPatchWallActivity$MCuA9djjOLVf8GxIoXvndEcOK_w
            @Override // com.xiaomi.micolauncher.module.music.view.MyHorizontalScrollView.OnScrollListener
            public final void onScroll(MyHorizontalScrollView myHorizontalScrollView) {
                VideoPatchWallActivity.this.a(myHorizontalScrollView);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(MyHorizontalScrollView myHorizontalScrollView) {
        d();
    }

    private void b() {
        this.d.add(new Header());
        Video.Block block = new Video.Block();
        block.itemList = new ArrayList();
        for (int i = 0; i < 4; i++) {
            block.itemList.add(new Video.Item());
        }
        this.d.add(block);
        this.b.setDataList(this.d);
    }

    private void c() {
        VideoDataManager.getManager().loadCategoryList().subscribeOn(MicoSchedulers.io()).flatMap(new Function() { // from class: com.xiaomi.micolauncher.module.video.ui.-$$Lambda$VideoPatchWallActivity$z3SFOgjgCYJKRA5A8R1iUewNNfo
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                ObservableSource a;
                a = VideoPatchWallActivity.this.a((List) obj);
                return a;
            }
        }).observeOn(MicoSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.video.ui.-$$Lambda$VideoPatchWallActivity$NhZ4ISmZSmCs02B-xpnRoDMGWRw
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                VideoPatchWallActivity.this.b((Video.Block) obj);
            }
        }, $$Lambda$VideoPatchWallActivity$Gxfe8__Z13efQ8ifDEt6D5Stxj0.INSTANCE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ ObservableSource a(List list) throws Exception {
        this.c.add(new Video.Category(6L, Video.Category.ANY_CATEGORY_TYPE));
        this.c.addAll(list);
        this.f = 0;
        Video.Category category = this.c.get(this.f);
        return VideoDataManager.getManager().loadCategoryContent(category != null ? category.id : 0L, category != null ? category.category : "", category != null ? category.type : "");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(Video.Block block) throws Exception {
        this.f++;
        this.d.clear();
        this.d.add(new Header());
        this.d.add(block);
        this.b.setDataList(this.d);
    }

    private void d() {
        if (this.f < this.c.size() && !this.e) {
            this.e = true;
            Video.Category category = this.c.get(this.f);
            VideoDataManager.getManager().loadCategoryContent(category != null ? category.id : 0L, category != null ? category.category : "", category != null ? category.type : "").subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.video.ui.-$$Lambda$VideoPatchWallActivity$cTHx3ZPQKdYxP4FHYsZ0ZLYtex4
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    VideoPatchWallActivity.this.a((Video.Block) obj);
                }
            }, new Consumer() { // from class: com.xiaomi.micolauncher.module.video.ui.-$$Lambda$VideoPatchWallActivity$i7M2f6c9uIuOveAXRTFoRQgG_CA
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    VideoPatchWallActivity.this.a((Throwable) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Video.Block block) throws Exception {
        this.e = false;
        this.f++;
        this.b.addDataList(Arrays.asList(block));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Throwable th) throws Exception {
        this.e = false;
        XLog.e(th);
    }
}
