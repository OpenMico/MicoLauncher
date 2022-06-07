package com.xiaomi.micolauncher.module.video.ui.fragment;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import com.elvishew.xlog.Logger;
import com.xiaomi.mico.base.utils.ToastUtil;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.Video;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.base.BaseFragment;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.module.music.swiperefresh.BaseGridLayoutManager;
import com.xiaomi.micolauncher.module.video.adapter.VideoListSquareAdapter;
import com.xiaomi.micolauncher.module.video.manager.VideoDataManager;
import com.xiaomi.micolauncher.skills.video.model.VideoItem;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class VideoListFragment extends BaseFragment {
    public static final String CATEGORY_HISTORY = "HISTORY";
    public static final String EXTRA_CATEGORY_ID = "EXTRA_CATEGORY_ID";
    public static final String EXTRA_CATEGORY_NAME = "EXTRA_CATEGORY_NAME";
    public static final String EXTRA_CATEGORY_ORIGIN = "EXTRA_CATEGORY_ORIGIN";
    public static final String EXTRA_CATEGORY_TYPE = "EXTRA_CATEGORY_TYPE";
    private RecyclerView a;
    protected VideoListSquareAdapter adapter;
    private ImageView b;
    private long c;
    private String d;
    private String e;
    private String f;
    private List<Object> g = new ArrayList();
    private Context h;

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        this.h = context;
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_video_list, viewGroup, false);
        a();
        a(inflate);
        b();
        return inflate;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseFragment
    public void onActivate() {
        super.onActivate();
        VideoListSquareAdapter videoListSquareAdapter = this.adapter;
        if (videoListSquareAdapter != null) {
            videoListSquareAdapter.notifyExpose();
        }
    }

    private void a() {
        if (getArguments() != null) {
            this.c = getArguments().getLong("EXTRA_CATEGORY_ID");
            this.d = getArguments().getString("EXTRA_CATEGORY_NAME");
            this.e = getArguments().getString("EXTRA_CATEGORY_TYPE");
            this.f = getArguments().getString("EXTRA_CATEGORY_ORIGIN");
        }
    }

    private void a(View view) {
        this.a = (RecyclerView) view.findViewById(R.id.recycler_view);
        this.b = (ImageView) view.findViewById(R.id.empty_view);
        final int i = Hardware.isBigScreen() ? 3 : 2;
        this.a.setLayoutManager(new BaseGridLayoutManager(this.h, i, 0, false));
        this.adapter = new VideoListSquareAdapter(this.h);
        this.a.setAdapter(this.adapter);
        this.adapter.setCategory(this.e);
        this.a.addItemDecoration(new RecyclerView.ItemDecoration() { // from class: com.xiaomi.micolauncher.module.video.ui.fragment.VideoListFragment.1
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect rect, View view2, RecyclerView recyclerView, RecyclerView.State state) {
                if (recyclerView.getChildLayoutPosition(view2) < i) {
                    rect.left = 12;
                }
                rect.right = 12;
                rect.bottom = 12;
            }
        });
    }

    private void b() {
        if ("HISTORY".equals(this.d)) {
            d();
        } else {
            c();
        }
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        if ("HISTORY".equals(this.d)) {
            d();
        }
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void setUserVisibleHint(boolean z) {
        super.setUserVisibleHint(z);
        VideoListSquareAdapter videoListSquareAdapter = this.adapter;
        if (videoListSquareAdapter != null) {
            videoListSquareAdapter.setUserVisibleHint(z);
        }
    }

    private void c() {
        Logger logger = L.launcherVideo;
        logger.i("Video List Fragment, start load data " + this.e);
        addToDisposeBag(VideoDataManager.getManager().loadCategoryContent2(this.c, this.d, this.e).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.video.ui.fragment.-$$Lambda$VideoListFragment$npkU2IeNcQ7ZJ4ZNYk5r93t7sCU
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                VideoListFragment.this.a((Video.Block) obj);
            }
        }, $$Lambda$VideoListFragment$FaqkCdJAPJOeqpIBCYyP0VlBztw.INSTANCE));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Video.Block block) throws Exception {
        if (ContainerUtil.isEmpty(block.itemList)) {
            L.childmode.e("VideoListFragment$loadData VideoDataManager.getManager().loadCategoryContent onSuccess list is Empty");
            return;
        }
        this.g.clear();
        for (Video.Item item : block.itemList) {
            VideoItem videoItem = new VideoItem(item);
            videoItem.setPlayLength(videoItem.getPlayLength() / 60);
            this.g.add(videoItem);
        }
        this.adapter.setDataList(this.g);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(Throwable th) throws Exception {
        ToastUtil.showToast((int) R.string.music_list_failed_toast);
        L.childmode.e("VideoListFragment$loadData VideoDataManager.getManager().loadCategoryContent failed", th);
    }

    private void d() {
        addToDisposeBag(VideoDataManager.getManager().loadVideoListFromDB(this.f).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.video.ui.fragment.-$$Lambda$VideoListFragment$sbNOTtIjquj3THDYt5wlkk39av8
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                VideoListFragment.this.a((List) obj);
            }
        }, $$Lambda$VideoListFragment$2vD0z3l3lB8dymoAZLkb9GeHXrU.INSTANCE));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(List list) throws Exception {
        if (!ContainerUtil.isEmpty(list)) {
            this.g.clear();
            this.g.addAll(list);
            this.adapter.setDataList(this.g);
            this.b.setVisibility(8);
            return;
        }
        this.g.clear();
        this.adapter.setDataList(this.g);
        this.b.setVisibility(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Throwable th) throws Exception {
        L.api.e(th);
    }
}
