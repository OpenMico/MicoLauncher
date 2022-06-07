package com.xiaomi.micolauncher.module.station;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.Station;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.base.BaseActivity;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.module.music.bean.Header;
import com.xiaomi.micolauncher.module.music.view.MyHorizontalScrollView;
import com.xiaomi.micolauncher.module.station.manager.StationDataManager;
import com.xiaomi.micolauncher.module.station.ui.StationPatchWallListView;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class StationPatchWallActivity extends BaseActivity {
    private MyHorizontalScrollView a;
    private StationPatchWallListView b;
    private List<Station.Category> c = new ArrayList();
    private List<Object> d = new ArrayList();
    private boolean e;
    private int f;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_station_patch_wall);
        a();
        b();
        c();
        scheduleToClose(TimeUnit.MINUTES.toMillis(1L));
    }

    private void a() {
        this.a = (MyHorizontalScrollView) findViewById(R.id.scroll_view);
        this.b = (StationPatchWallListView) findViewById(R.id.list_view);
        this.a.setOnScrollListener(new MyHorizontalScrollView.OnScrollListener() { // from class: com.xiaomi.micolauncher.module.station.-$$Lambda$StationPatchWallActivity$3sqLJ1jk47PP31J5ObC7n9YEDmU
            @Override // com.xiaomi.micolauncher.module.music.view.MyHorizontalScrollView.OnScrollListener
            public final void onScroll(MyHorizontalScrollView myHorizontalScrollView) {
                StationPatchWallActivity.this.a(myHorizontalScrollView);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(MyHorizontalScrollView myHorizontalScrollView) {
        d();
    }

    private void b() {
        this.d.add(new Header());
        Station.Block block = new Station.Block();
        block.itemList = new ArrayList();
        for (int i = 0; i < 6; i++) {
            block.itemList.add(new Station.Item());
        }
        this.d.add(block);
        this.b.setDataList(this.d);
    }

    private void c() {
        StationDataManager.getManager().loadCategoryList().subscribeOn(MicoSchedulers.io()).flatMap(new Function() { // from class: com.xiaomi.micolauncher.module.station.-$$Lambda$StationPatchWallActivity$crFYuRxekoZ43UYKK5zELNKmZrM
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                ObservableSource a;
                a = StationPatchWallActivity.this.a((List) obj);
                return a;
            }
        }).observeOn(MicoSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.station.-$$Lambda$StationPatchWallActivity$x8RnqvQoO2v96Em-kUQbdCBJ6DI
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                StationPatchWallActivity.this.b((Station.Block) obj);
            }
        }, $$Lambda$StationPatchWallActivity$fzEHLSoeZJNTHFywqO0UV8gWlU8.INSTANCE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ ObservableSource a(List list) throws Exception {
        this.c.addAll(list);
        this.f = 0;
        Station.Category category = this.c.get(this.f);
        return StationDataManager.getManager().loadCategoryContent(category.getCategoryId(), category.getCategoryName());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(Station.Block block) throws Exception {
        this.f++;
        this.d.clear();
        this.d.add(new Header());
        this.d.add(block);
        this.b.setDataList(this.d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(Throwable th) throws Exception {
        L.childmode.e("StationPatchWallActivity$loadData  StationDataManager.getManager().loadCategoryList() failed:", th);
    }

    private void d() {
        if (this.f < this.c.size() && !this.e) {
            this.e = true;
            Station.Category category = this.c.get(this.f);
            StationDataManager.getManager().loadCategoryContent(category.getCategoryId(), category.getCategoryName()).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.station.-$$Lambda$StationPatchWallActivity$r8purpSY-gRxTlcc8VOY1mXu-QA
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    StationPatchWallActivity.this.a((Station.Block) obj);
                }
            }, new Consumer() { // from class: com.xiaomi.micolauncher.module.station.-$$Lambda$StationPatchWallActivity$HvZQkguoCwgL8-Ma2q4KLY4IW7c
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    StationPatchWallActivity.this.a((Throwable) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Station.Block block) throws Exception {
        this.e = false;
        this.f++;
        this.b.addDataList(Arrays.asList(block));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Throwable th) throws Exception {
        this.e = false;
        L.childmode.e("StationPatchWallActivity$autoPaging  StationDataManager.getManager().loadCategoryList() failed:", th);
    }
}
