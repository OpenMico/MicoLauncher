package com.xiaomi.micolauncher.module.video.ui;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.mico.base.utils.ToastUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.base.BaseActivity;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.module.music.bean.Header;
import com.xiaomi.micolauncher.module.music.swiperefresh.BaseGridLayoutManager;
import com.xiaomi.micolauncher.module.video.adapter.VideoListSquareAdapter;
import com.xiaomi.micolauncher.module.video.manager.VideoDataManager;
import com.xiaomi.micolauncher.skills.video.model.VideoItem;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class VideoRecentListActivity extends BaseActivity {
    private RecyclerView a;
    private ImageView b;
    private VideoListSquareAdapter c;
    private List<Object> d;

    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_video_category_list);
        a();
        b();
        c();
        scheduleToClose(TimeUnit.MINUTES.toMillis(1L));
    }

    private void a() {
        this.a = (RecyclerView) findViewById(R.id.recycler_view);
        this.b = (ImageView) findViewById(R.id.empty_view);
        this.a.setLayoutManager(new BaseGridLayoutManager(this, 2, 0, false));
        this.c = new VideoListSquareAdapter(this);
        this.a.setAdapter(this.c);
        this.a.addItemDecoration(new RecyclerView.ItemDecoration() { // from class: com.xiaomi.micolauncher.module.video.ui.VideoRecentListActivity.1
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
                rect.right = 12;
                rect.bottom = 12;
            }
        });
    }

    private void b() {
        this.d = new ArrayList();
        this.d.add(new Header(getString(R.string.video_recent_list_title)));
        for (int i = 0; i < 4; i++) {
            this.d.add(new VideoItem());
        }
        this.c.setDataList(this.d);
    }

    private void c() {
        VideoDataManager.getManager().loadVideoListFromDB("iqiyi").subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.video.ui.-$$Lambda$VideoRecentListActivity$ocz8oyAPgSGjjxKL-hgGdBKHVN8
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                VideoRecentListActivity.this.a((List) obj);
            }
        }, $$Lambda$VideoRecentListActivity$ySWZ9hj1as6QjBd4wCbZDsxiUM.INSTANCE);
    }

    public /* synthetic */ void a(List list) throws Exception {
        if (list == null || list.size() <= 0) {
            ToastUtil.showToast((int) R.string.video_recent_list_empty_toast);
            this.d.clear();
            this.c.setDataList(this.d);
            this.b.setVisibility(0);
            return;
        }
        this.d.clear();
        this.d.add(new Header(getString(R.string.video_recent_list_title)));
        this.d.addAll(list);
        this.c.setDataList(this.d);
    }
}
