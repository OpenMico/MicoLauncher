package com.xiaomi.micolauncher.module.childsong;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.PatchWall;
import com.xiaomi.micolauncher.common.base.BaseFragment;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.module.music.swiperefresh.BaseGridLayoutManager;
import com.xiaomi.micolauncher.module.music.swiperefresh.RecyclerViewScrollManager;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public class ChildSongListFragment extends BaseFragment {
    public static final String EXTRA_CATEGORY_ID = "EXTRA_CATEGORY_ID";
    public static final String EXTRA_CATEGORY_NAME = "EXTRA_CATEGORY_NAME";
    private ChildSongListSquareAdapter a;
    private long b;
    private String c;
    private int d;
    private int e;

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_video_list, viewGroup, false);
        this.e = inflate.getContext().getResources().getDimensionPixelSize(R.dimen.common_video_list_item_margin);
        a();
        a(inflate);
        b();
        return inflate;
    }

    private void a() {
        if (getArguments() != null) {
            this.b = getArguments().getLong("EXTRA_CATEGORY_ID");
            this.c = getArguments().getString("EXTRA_CATEGORY_NAME");
        }
    }

    private void a(View view) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        BaseGridLayoutManager baseGridLayoutManager = new BaseGridLayoutManager(getContext(), 2, 0, false);
        recyclerView.setLayoutManager(baseGridLayoutManager);
        this.a = new ChildSongListSquareAdapter(getContext());
        recyclerView.setAdapter(this.a);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() { // from class: com.xiaomi.micolauncher.module.childsong.ChildSongListFragment.1
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect rect, View view2, RecyclerView recyclerView2, RecyclerView.State state) {
                if (recyclerView2.getChildLayoutPosition(view2) < 2) {
                    rect.left = ChildSongListFragment.this.e;
                }
                rect.right = ChildSongListFragment.this.e;
                rect.bottom = ChildSongListFragment.this.e;
            }
        });
        baseGridLayoutManager.setOnScrollLocationChangeListener(new RecyclerViewScrollManager.OnScrollLocationChangeListener() { // from class: com.xiaomi.micolauncher.module.childsong.ChildSongListFragment.2
            @Override // com.xiaomi.micolauncher.module.music.swiperefresh.RecyclerViewScrollManager.OnScrollLocationChangeListener
            public int getEarlyToBottomItemCount() {
                return 10;
            }

            @Override // com.xiaomi.micolauncher.module.music.swiperefresh.RecyclerViewScrollManager.OnScrollLocationChangeListener
            public void onBottomWhenScrollDragging(RecyclerView recyclerView2) {
            }

            @Override // com.xiaomi.micolauncher.module.music.swiperefresh.RecyclerViewScrollManager.OnScrollLocationChangeListener
            public void onTopWhenScrollIdle(RecyclerView recyclerView2) {
            }

            @Override // com.xiaomi.micolauncher.module.music.swiperefresh.RecyclerViewScrollManager.OnScrollLocationChangeListener
            public void onBottomWhenScrollIdle(RecyclerView recyclerView2) {
                ChildSongListFragment.this.c();
            }
        });
        baseGridLayoutManager.getRecyclerViewScrollManager().registerScrollListener(recyclerView);
    }

    private void b() {
        this.d = 1;
        addToDisposeBag(ChildSongApiHelper.getInstance().loadCategoryContent(this.b, this.c).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.childsong.-$$Lambda$ChildSongListFragment$juTQJnWWiYGkSNK_BYZz7p9-oBc
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ChildSongListFragment.this.a((PatchWall.Category.Block) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(PatchWall.Category.Block block) throws Exception {
        if (block != null) {
            this.a.setDataList(new ArrayList(block.itemList));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c() {
        addToDisposeBag(ChildSongApiHelper.getInstance().a(this.b, this.c, this.d).observeOn(MicoSchedulers.mainThread()).subscribeOn(MicoSchedulers.io()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.childsong.-$$Lambda$ChildSongListFragment$oW38vQeVSFP-FrMM0L4j0pNSztI
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ChildSongListFragment.this.a((PatchWall.Category) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(PatchWall.Category category) throws Exception {
        this.d++;
        this.a.addDataList(category.items);
    }
}
