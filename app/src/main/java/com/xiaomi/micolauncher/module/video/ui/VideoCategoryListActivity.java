package com.xiaomi.micolauncher.module.video.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.Video;
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
public class VideoCategoryListActivity extends BaseActivity {
    private long a;
    private String b;
    private String c;
    private RecyclerView d;
    private VideoListSquareAdapter e;
    private List<Object> f;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_video_category_list);
        a();
        b();
        c();
        d();
        scheduleToClose(TimeUnit.MINUTES.toMillis(1L));
    }

    private void a() {
        if (getIntent() != null) {
            this.a = getIntent().getLongExtra("EXTRA_CATEGORY_ID", 0L);
            this.b = getIntent().getStringExtra("EXTRA_CATEGORY_NAME");
            this.c = getIntent().getStringExtra("EXTRA_CATEGORY_TYPE");
        }
    }

    private void b() {
        this.d = (RecyclerView) findViewById(R.id.recycler_view);
        this.d.setLayoutManager(new BaseGridLayoutManager(this, 2, 0, false));
        this.e = new VideoListSquareAdapter(this);
        this.d.setAdapter(this.e);
        this.d.addItemDecoration(new RecyclerView.ItemDecoration() { // from class: com.xiaomi.micolauncher.module.video.ui.VideoCategoryListActivity.1
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
                rect.right = 12;
                rect.bottom = 12;
            }
        });
    }

    private void c() {
        this.f = new ArrayList();
        this.f.add(new Header(this.b));
        for (int i = 0; i < 4; i++) {
            this.f.add(new VideoItem());
        }
        this.e.setDataList(this.f);
    }

    private void d() {
        VideoDataManager.getManager().loadCategoryContent(this.a, this.b, this.c).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.video.ui.-$$Lambda$VideoCategoryListActivity$0nilat8EHMR_VmOL3EzUhzEOB2A
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                VideoCategoryListActivity.this.a((Video.Block) obj);
            }
        }, $$Lambda$VideoCategoryListActivity$9aq4qDbi3LWYOZSfFoi6pBkKk1k.INSTANCE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Video.Block block) throws Exception {
        if (!(block == null || block.itemList == null)) {
            this.f.clear();
            this.f.add(new Header(this.b));
            for (Video.Item item : block.itemList) {
                this.f.add(new VideoItem(item));
            }
            this.e.setDataList(this.f);
        }
    }

    public static void openVideoCategoryListActivity(Context context, long j, String str, String str2) {
        Intent intent = new Intent(context, VideoCategoryListActivity.class);
        intent.putExtra("EXTRA_CATEGORY_ID", j);
        intent.putExtra("EXTRA_CATEGORY_NAME", str);
        intent.putExtra("EXTRA_CATEGORY_TYPE", str2);
        context.startActivity(intent);
    }
}
