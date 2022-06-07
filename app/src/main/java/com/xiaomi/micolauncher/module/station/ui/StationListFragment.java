package com.xiaomi.micolauncher.module.station.ui;

import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.ChildVideo;
import com.xiaomi.micolauncher.api.model.IListItem;
import com.xiaomi.micolauncher.api.model.Station;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.base.BaseFragment;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.module.child.childvideo.ChildVideoDataManager;
import com.xiaomi.micolauncher.module.music.swiperefresh.BaseGridLayoutManager;
import com.xiaomi.micolauncher.module.station.adapter.StationListSquareAdapter;
import com.xiaomi.micolauncher.module.station.manager.StationDataManager;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes3.dex */
public class StationListFragment extends BaseFragment {
    public static final int CATEGORY_TYPE_OTHER = 1000;
    public static final int CATEGORY_TYPE_VIDEO = 1001;
    public static final String EXTRA_CATEGORY_ID = "EXTRA_CATEGORY_ID";
    public static final String EXTRA_CATEGORY_NAME = "EXTRA_CATEGORY_NAME";
    public static final String EXTRA_CATEGORY_TYPE = "EXTRA_CATEGORY_TYPE";
    public static final String EXTRA_CATEGORY_URL = "EXTRA_CATEGORY_URL";
    List<IListItem> a;
    private StationListSquareAdapter b;
    private RecyclerView c;
    private long d;
    private int e;
    private String f;
    private String g;
    private int h;
    private int i;
    private boolean j;
    private String k;

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_video_list, viewGroup, false);
        this.h = inflate.getContext().getResources().getDimensionPixelSize(R.dimen.common_video_list_item_margin);
        this.a = new ArrayList();
        a();
        a(inflate);
        b();
        return inflate;
    }

    private void a() {
        if (getArguments() != null) {
            this.d = getArguments().getLong("EXTRA_CATEGORY_ID");
            this.f = getArguments().getString("EXTRA_CATEGORY_NAME");
            this.e = getArguments().getInt("EXTRA_CATEGORY_TYPE", 1000);
            this.i = 1001;
            if (this.e == 1001) {
                this.g = getArguments().getString(EXTRA_CATEGORY_URL);
                this.k = this.g;
                this.i = 1002;
            }
        }
    }

    private void a(View view) {
        this.c = (RecyclerView) view.findViewById(R.id.recycler_view);
        this.c.setLayoutManager(new BaseGridLayoutManager(getContext(), 2, 0, false));
        this.b = new StationListSquareAdapter(getContext(), this.i);
        this.c.setAdapter(this.b);
        this.b.setCategoryName(this.f);
        this.c.addItemDecoration(new RecyclerView.ItemDecoration() { // from class: com.xiaomi.micolauncher.module.station.ui.StationListFragment.1
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect rect, View view2, RecyclerView recyclerView, RecyclerView.State state) {
                if (recyclerView.getChildLayoutPosition(view2) < 2) {
                    rect.left = StationListFragment.this.h;
                }
                rect.right = StationListFragment.this.h;
                rect.bottom = StationListFragment.this.h;
            }
        });
        this.c.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.xiaomi.micolauncher.module.station.ui.StationListFragment.2
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(@NotNull RecyclerView recyclerView, int i) {
                GridLayoutManager gridLayoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
                if (gridLayoutManager != null && recyclerView.getAdapter() != null) {
                    int itemCount = recyclerView.getAdapter().getItemCount();
                    int findLastVisibleItemPosition = gridLayoutManager.findLastVisibleItemPosition();
                    int childCount = recyclerView.getChildCount();
                    if (i == 0 && findLastVisibleItemPosition == itemCount - 1 && childCount > 0 && StationListFragment.this.j) {
                        StationListFragment.this.b();
                    }
                }
            }
        });
    }

    public void b() {
        L.childContent.i("begin load child video  %s  %s", this.f, this.k);
        if (this.e == 1001) {
            addToDisposeBag(ChildVideoDataManager.getManager().loadCategoryContent(this.k.replace("pageno=0&", "")).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.station.ui.-$$Lambda$StationListFragment$0UlxGUG9AdnXjI-DfSdsKJMfmOY
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    StationListFragment.this.a((ChildVideo.BlocksBean) obj);
                }
            }));
        } else {
            addToDisposeBag(StationDataManager.getManager().loadCategoryContent(this.d, this.f).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.station.ui.-$$Lambda$StationListFragment$Iezch7D9wG7Jvo7zEGVejAaGRtc
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    StationListFragment.this.a((Station.Block) obj);
                }
            }));
        }
    }

    public /* synthetic */ void a(ChildVideo.BlocksBean blocksBean) throws Exception {
        if (!TextUtils.isEmpty(blocksBean.getMeta().getMore())) {
            this.j = true;
            this.k = blocksBean.getMeta().getMore();
            L.childContent.i("loadCategoryContent  more  url  %s", this.k);
        } else {
            this.j = false;
        }
        this.a.addAll(blocksBean.getItems());
        this.b.setDataList(this.a);
    }

    public /* synthetic */ void a(Station.Block block) throws Exception {
        if (block != null) {
            this.a.addAll(block.itemList);
            this.b.setDataList(this.a);
        }
    }
}
