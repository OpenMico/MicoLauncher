package com.xiaomi.micolauncher.module.music.base;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.module.music.adapter.MusicListSquareAdapter;
import com.xiaomi.micolauncher.module.music.bean.SquareItem;
import com.xiaomi.micolauncher.module.music.swiperefresh.BaseGridLayoutManager;
import com.xiaomi.micolauncher.module.music.swiperefresh.RecyclerViewScrollManager;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public abstract class BaseMusicListActivity extends BaseMusicActivity {
    protected static final int DEFAULT_SIZE = 8;
    private int a;
    protected MusicListSquareAdapter adapter;
    private TextView b;
    private ImageView c;
    protected RecyclerView recyclerView;
    protected List<Object> dataList = new ArrayList();
    private RecyclerViewScrollManager.OnScrollLocationChangeListener d = new RecyclerViewScrollManager.OnScrollLocationChangeListener() { // from class: com.xiaomi.micolauncher.module.music.base.BaseMusicListActivity.1
        @Override // com.xiaomi.micolauncher.module.music.swiperefresh.RecyclerViewScrollManager.OnScrollLocationChangeListener
        public int getEarlyToBottomItemCount() {
            return 10;
        }

        @Override // com.xiaomi.micolauncher.module.music.swiperefresh.RecyclerViewScrollManager.OnScrollLocationChangeListener
        public void onBottomWhenScrollDragging(RecyclerView recyclerView) {
        }

        @Override // com.xiaomi.micolauncher.module.music.swiperefresh.RecyclerViewScrollManager.OnScrollLocationChangeListener
        public void onTopWhenScrollIdle(RecyclerView recyclerView) {
        }

        @Override // com.xiaomi.micolauncher.module.music.swiperefresh.RecyclerViewScrollManager.OnScrollLocationChangeListener
        public void onBottomWhenScrollIdle(RecyclerView recyclerView) {
            BaseMusicListActivity.this.autoPaging();
        }
    };

    protected abstract void autoPaging();

    protected abstract String getTitleMessage();

    protected abstract void loadData();

    @Override // com.xiaomi.micolauncher.module.music.base.BaseMusicActivity, com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_music_list);
        a();
        loadData();
        buildInitData();
    }

    private void a() {
        this.a = (int) getResources().getDimension(R.dimen.space_music_list_item);
        this.b = (TextView) findViewById(R.id.category_title);
        this.c = (ImageView) findViewById(R.id.category_back_iv);
        this.recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        BaseGridLayoutManager baseGridLayoutManager = new BaseGridLayoutManager(this, 2, 0, false);
        this.recyclerView.setLayoutManager(baseGridLayoutManager);
        this.adapter = new MusicListSquareAdapter(this);
        this.recyclerView.setAdapter(this.adapter);
        baseGridLayoutManager.setOnScrollLocationChangeListener(this.d);
        baseGridLayoutManager.getRecyclerViewScrollManager().registerScrollListener(this.recyclerView);
        this.recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() { // from class: com.xiaomi.micolauncher.module.music.base.BaseMusicListActivity.2
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
                rect.left = BaseMusicListActivity.this.a;
                rect.bottom = BaseMusicListActivity.this.a;
            }
        });
        this.b.setText(getTitleMessage());
        this.c.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.music.base.-$$Lambda$BaseMusicListActivity$4O-XavN7hHAkyaCWXc51qo9M5Ag
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                BaseMusicListActivity.this.a(view);
            }
        });
    }

    public /* synthetic */ void a(View view) {
        finish();
    }

    protected void buildInitData() {
        for (int i = 0; i < 8; i++) {
            this.dataList.add(new SquareItem(2));
        }
        this.adapter.setDataList(this.dataList);
    }
}
