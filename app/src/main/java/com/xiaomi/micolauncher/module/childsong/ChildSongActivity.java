package com.xiaomi.micolauncher.module.childsong;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.GuardedBy;
import com.elvishew.xlog.XLog;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.Music;
import com.xiaomi.micolauncher.api.model.PatchWall;
import com.xiaomi.micolauncher.api.model.Station;
import com.xiaomi.micolauncher.common.base.BaseActivity;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.module.childsong.ui.ChildSongPatchWallListView;
import com.xiaomi.micolauncher.module.music.bean.Header;
import com.xiaomi.micolauncher.module.music.view.MyHorizontalScrollView;
import com.xiaomi.micolauncher.skills.music.view.QqMusicLoginStatusPresenter;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class ChildSongActivity extends BaseActivity {
    private ChildSongPatchWallListView a;
    private boolean d;
    @GuardedBy("categoryDataLock")
    private int e;
    private View g;
    @GuardedBy("categoryDataLock")
    private List<Music.Category> b = new ArrayList();
    private List<Object> c = new ArrayList();
    private final Object f = new Object();

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_child_song);
        a();
        b();
        c();
        scheduleToClose(TimeUnit.MINUTES.toMillis(1L));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        ChildSongApiHelper.getInstance().release();
    }

    private void a() {
        this.a = (ChildSongPatchWallListView) findViewById(R.id.list_view);
        this.g = findViewById(R.id.qq_expired_view);
        ((MyHorizontalScrollView) findViewById(R.id.scroll_view)).setOnScrollListener(new MyHorizontalScrollView.OnScrollListener() { // from class: com.xiaomi.micolauncher.module.childsong.-$$Lambda$ChildSongActivity$SzjU_6twylOzqvdxCbItm7dvoXg
            @Override // com.xiaomi.micolauncher.module.music.view.MyHorizontalScrollView.OnScrollListener
            public final void onScroll(MyHorizontalScrollView myHorizontalScrollView) {
                ChildSongActivity.this.a(myHorizontalScrollView);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(MyHorizontalScrollView myHorizontalScrollView) {
        d();
    }

    private void b() {
        this.c.add(new Header());
        Station.Block block = new Station.Block();
        block.itemList = new ArrayList();
        for (int i = 0; i < 6; i++) {
            block.itemList.add(new Station.Item());
        }
        this.c.add(block);
        this.a.setDataList(this.c);
    }

    private void c() {
        addToDisposeBag(ChildSongApiHelper.getInstance().loadCategoryList().subscribeOn(MicoSchedulers.io()).flatMap(new Function() { // from class: com.xiaomi.micolauncher.module.childsong.-$$Lambda$ChildSongActivity$DUfV5FiuGV7pTHK5TaqSyQLvYus
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                ObservableSource a;
                a = ChildSongActivity.this.a((List) obj);
                return a;
            }
        }).observeOn(MicoSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.childsong.-$$Lambda$ChildSongActivity$UQ032d4F-UttXtu-UdGtRHeWSlI
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ChildSongActivity.this.b((PatchWall.Category.Block) obj);
            }
        }, new Consumer() { // from class: com.xiaomi.micolauncher.module.childsong.-$$Lambda$ChildSongActivity$Zla_3PQ9qgcg9THxKH6QSmRBDGY
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ChildSongActivity.this.b((Throwable) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ ObservableSource a(List list) throws Exception {
        Music.Category category;
        synchronized (this.f) {
            this.b.addAll(list);
            this.e = 0;
            category = this.b.get(this.e);
        }
        return ChildSongApiHelper.getInstance().loadCategoryContent(category.getCategoryId(), category.getCategoryName());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(PatchWall.Category.Block block) throws Exception {
        synchronized (this.f) {
            this.e++;
            this.c.clear();
            this.c.add(new Header());
            this.c.add(block);
            this.a.setDataList(this.c);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(Throwable th) throws Exception {
        new QqMusicLoginStatusPresenter(this.g, this.a, new QqMusicLoginStatusPresenter.OnCancelBtnClickListener() { // from class: com.xiaomi.micolauncher.module.childsong.-$$Lambda$ChildSongActivity$qZMUURbiiandk1lULZiDVdvcbeo
            @Override // com.xiaomi.micolauncher.skills.music.view.QqMusicLoginStatusPresenter.OnCancelBtnClickListener
            public final void onCancelBtnClick() {
                ChildSongActivity.this.e();
            }
        }).showLoginStatus(th);
        XLog.e("ChildSongActivity$loadData ChildSongApiHelper.getInstance().loadCategoryList failed:", th);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void e() {
        finish();
    }

    private void d() {
        boolean z;
        Music.Category category;
        synchronized (this.f) {
            z = this.e < this.b.size();
        }
        if (z && !this.d) {
            this.d = true;
            synchronized (this.f) {
                category = this.b.get(this.e);
            }
            addToDisposeBag(ChildSongApiHelper.getInstance().loadCategoryContent(category.getCategoryId(), category.getCategoryName()).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.childsong.-$$Lambda$ChildSongActivity$XSjVPuGYGMwAIBjiOBKrvC_QDk0
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ChildSongActivity.this.a((PatchWall.Category.Block) obj);
                }
            }, new Consumer() { // from class: com.xiaomi.micolauncher.module.childsong.-$$Lambda$ChildSongActivity$cPW1Coh-Mdw5fCuRB7RvlKij8nY
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ChildSongActivity.this.a((Throwable) obj);
                }
            }));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(PatchWall.Category.Block block) throws Exception {
        this.d = false;
        synchronized (this.f) {
            this.e++;
        }
        this.a.addDataList(Collections.singletonList(block));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Throwable th) throws Exception {
        this.d = false;
        XLog.e("ChildSongActivity$autoPaging ChildSongApiHelper.getInstance().loadCategoryContent failed:", th);
    }
}
