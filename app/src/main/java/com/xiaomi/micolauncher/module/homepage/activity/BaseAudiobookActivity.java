package com.xiaomi.micolauncher.module.homepage.activity;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.base.BaseActivity;
import com.xiaomi.micolauncher.module.homepage.adapter.SubAudiobookContentAdapter;

/* loaded from: classes3.dex */
public abstract class BaseAudiobookActivity extends BaseActivity {
    public static final String KEY_TITLE = "key_title";
    private boolean a;
    protected SubAudiobookContentAdapter adapter;
    private RecyclerView.OnScrollListener b = new RecyclerView.OnScrollListener() { // from class: com.xiaomi.micolauncher.module.homepage.activity.BaseAudiobookActivity.1
        @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
        public void onScrollStateChanged(RecyclerView recyclerView, int i) {
            super.onScrollStateChanged(recyclerView, i);
            if (!BaseAudiobookActivity.this.a && BaseAudiobookActivity.this.adapter != null) {
                if (i == 0) {
                    BaseAudiobookActivity.this.adapter.setScrolling(false);
                    BaseAudiobookActivity.this.adapter.notifyItemRangeChanged(2, BaseAudiobookActivity.this.adapter.getItemCount() - 2);
                    BaseAudiobookActivity.this.a = true;
                } else if (i == 2) {
                    BaseAudiobookActivity.this.adapter.setScrolling(true);
                }
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
        public void onScrolled(@NonNull RecyclerView recyclerView, int i, int i2) {
            BaseAudiobookActivity.this.autoPaging();
        }
    };
    RecyclerView e;

    protected void autoPaging() {
    }

    protected abstract int layoutId();

    protected abstract void loadData();

    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView(layoutId());
        initView();
        loadData();
        setDefaultScheduleDuration();
    }

    public void initView() {
        this.e = (RecyclerView) findViewById(R.id.recycler_view);
        this.e.setLayoutManager(new GridLayoutManager(this, 2, 0, false) { // from class: com.xiaomi.micolauncher.module.homepage.activity.BaseAudiobookActivity.2
            @Override // androidx.recyclerview.widget.GridLayoutManager, androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
            public boolean supportsPredictiveItemAnimations() {
                return false;
            }

            @Override // androidx.recyclerview.widget.GridLayoutManager, androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
            public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
                try {
                    super.onLayoutChildren(recycler, state);
                } catch (IndexOutOfBoundsException e) {
                    L.audiobook.e("onLayoutChildren exception : %s", e);
                }
            }
        });
        this.e.setItemViewCacheSize(4);
        this.e.addOnScrollListener(this.b);
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        this.e.setAdapter(adapter);
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        SubAudiobookContentAdapter subAudiobookContentAdapter = this.adapter;
        if (subAudiobookContentAdapter != null) {
            subAudiobookContentAdapter.clear();
            this.adapter = null;
        }
        this.b = null;
        this.e = null;
    }
}
