package com.xiaomi.micolauncher.module.homepage.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.asynclayoutinflater.view.AsyncLayoutInflater;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.ChildVideo;
import com.xiaomi.micolauncher.api.model.IBlockBean;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.utils.UiUtils;
import com.xiaomi.micolauncher.module.child.BaseChildHolder;
import com.xiaomi.micolauncher.module.child.childstory.holder.StoryCommonHolder;
import com.xiaomi.micolauncher.module.child.childvideo.holder.ChildVideoCommonHolder;
import com.xiaomi.micolauncher.module.homepage.viewholder.homekids.HomeKidsAppHolder;
import com.xiaomi.micolauncher.module.homepage.viewholder.homekids.HomeKidsBannerHolder;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class HomeKidsAdapter extends BaseAdapter<BaseChildHolder> {
    public static final int TYPE_BANNER = 0;
    private List<IBlockBean> a;
    private final Context b;
    private final List<BaseChildHolder> c = new ArrayList();
    private final SparseArray<List<View>> d = new SparseArray<>();
    private final List<View> e = new ArrayList();
    private final List<View> f = new ArrayList();
    private final List<View> g = new ArrayList();
    private final int h;
    private final int i;
    private final ViewGroup j;

    public HomeKidsAdapter(Context context, RecyclerView recyclerView) {
        this.b = context;
        this.j = recyclerView;
        this.h = UiUtils.getSize(context, R.dimen.child_video_small_card_width);
        this.i = UiUtils.getSize(context, R.dimen.child_video_small_card_height);
        a();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public BaseChildHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (i == 0) {
            return b(viewGroup);
        }
        switch (i) {
            case 2:
                if (ContainerUtil.hasData(this.d.get(2))) {
                    HomeKidsAppHolder homeKidsAppHolder = new HomeKidsAppHolder(this.b, this.d.get(2).get(0));
                    this.d.get(2).remove(0);
                    return homeKidsAppHolder;
                }
                L.childContent.i("do not found async app view");
                return c(viewGroup);
            case 3:
            case 4:
                if (ContainerUtil.hasData(this.d.get(3))) {
                    StoryCommonHolder storyCommonHolder = new StoryCommonHolder(this.b, this.d.get(3).get(0), true);
                    this.d.get(3).remove(0);
                    return storyCommonHolder;
                }
                L.childContent.i("do not found async story view");
                return d(viewGroup);
            default:
                if (ContainerUtil.hasData(this.d.get(1))) {
                    ChildVideoCommonHolder childVideoCommonHolder = new ChildVideoCommonHolder(this.b, this.d.get(1).get(0), this.h, this.i, true);
                    L.childContent.i("use found async video view");
                    this.d.get(1).remove(0);
                    return childVideoCommonHolder;
                }
                L.childContent.i("do not found async video view");
                return a(viewGroup);
        }
    }

    private ChildVideoCommonHolder a(ViewGroup viewGroup) {
        Context context = this.b;
        return new ChildVideoCommonHolder(context, LayoutInflater.from(context).inflate(R.layout.item_home_child_video_common, viewGroup, false), this.h, this.i, true);
    }

    private HomeKidsBannerHolder b(ViewGroup viewGroup) {
        Context context = this.b;
        return new HomeKidsBannerHolder(context, LayoutInflater.from(context).inflate(R.layout.item_home_kid_banner, viewGroup, false));
    }

    private HomeKidsAppHolder c(ViewGroup viewGroup) {
        Context context = this.b;
        return new HomeKidsAppHolder(context, LayoutInflater.from(context).inflate(R.layout.item_home_kid_app, viewGroup, false));
    }

    private StoryCommonHolder d(ViewGroup viewGroup) {
        Context context = this.b;
        return new StoryCommonHolder(context, LayoutInflater.from(context).inflate(R.layout.item_home_child_story_common, viewGroup, false), true);
    }

    public void onBindViewHolder(BaseChildHolder baseChildHolder, int i) {
        if (ContainerUtil.hasData(this.a)) {
            if (baseChildHolder instanceof ChildVideoCommonHolder) {
                ((ChildVideoCommonHolder) baseChildHolder).setBackground(i);
            }
            if (baseChildHolder instanceof StoryCommonHolder) {
                ((StoryCommonHolder) baseChildHolder).setBackground(i);
            }
            baseChildHolder.setBlocksBean(this.a.get(i));
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return ContainerUtil.getSize(this.a);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.adapter.BaseAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        List<IBlockBean> list = this.a;
        if (list == null) {
            return 0;
        }
        IBlockBean iBlockBean = list.get(i);
        String blockType = iBlockBean.getBlockType();
        char c = 65535;
        int hashCode = blockType.hashCode();
        if (hashCode != 96801) {
            if (hashCode != 3536149) {
                if (hashCode != 109770997) {
                    if (hashCode == 112202875 && blockType.equals("video")) {
                        c = 0;
                    }
                } else if (blockType.equals(IBlockBean.BLOCK_TYPE_STORY)) {
                    c = 3;
                }
            } else if (blockType.equals(IBlockBean.BLOCK_TYPE_SONG)) {
                c = 2;
            }
        } else if (blockType.equals("app")) {
            c = 1;
        }
        switch (c) {
            case 0:
                return TextUtils.equals(iBlockBean.getUiType(), ChildVideo.BLOCK_BANNER) ? 0 : 1;
            case 1:
                return 2;
            case 2:
                return 4;
            case 3:
                return 3;
            default:
                return 1;
        }
    }

    public void setData(List<IBlockBean> list) {
        if (ContainerUtil.hasData(list)) {
            this.a = list;
            notifyDataSetChanged();
        }
    }

    public void onViewAttachedToWindow(BaseChildHolder baseChildHolder) {
        this.c.add(baseChildHolder);
    }

    public void onViewDetachedFromWindow(BaseChildHolder baseChildHolder) {
        this.c.remove(baseChildHolder);
        baseChildHolder.removeAllMessages();
    }

    public void removeMessages() {
        for (BaseChildHolder baseChildHolder : this.c) {
            baseChildHolder.removeAllMessages();
        }
    }

    public void startMessages() {
        for (BaseChildHolder baseChildHolder : this.c) {
            baseChildHolder.startAllMessages();
        }
    }

    @SuppressLint({"InflateParams"})
    private void a() {
        for (int i = 0; i < 5; i++) {
            new AsyncLayoutInflater(this.b).inflate(R.layout.item_home_child_video_common, this.j, new AsyncLayoutInflater.OnInflateFinishedListener() { // from class: com.xiaomi.micolauncher.module.homepage.adapter.-$$Lambda$HomeKidsAdapter$mMzMj5KmHmJfgfXsTWNp5CEWbSk
                @Override // androidx.asynclayoutinflater.view.AsyncLayoutInflater.OnInflateFinishedListener
                public final void onInflateFinished(View view, int i2, ViewGroup viewGroup) {
                    HomeKidsAdapter.this.c(view, i2, viewGroup);
                }
            });
        }
        for (int i2 = 0; i2 < 3; i2++) {
            new AsyncLayoutInflater(this.b).inflate(R.layout.item_home_child_story_common, this.j, new AsyncLayoutInflater.OnInflateFinishedListener() { // from class: com.xiaomi.micolauncher.module.homepage.adapter.-$$Lambda$HomeKidsAdapter$tDav-9igAUyZ2D3YDAzdmimswLo
                @Override // androidx.asynclayoutinflater.view.AsyncLayoutInflater.OnInflateFinishedListener
                public final void onInflateFinished(View view, int i3, ViewGroup viewGroup) {
                    HomeKidsAdapter.this.b(view, i3, viewGroup);
                }
            });
        }
        new AsyncLayoutInflater(this.b).inflate(R.layout.item_home_kid_app, this.j, new AsyncLayoutInflater.OnInflateFinishedListener() { // from class: com.xiaomi.micolauncher.module.homepage.adapter.-$$Lambda$HomeKidsAdapter$YFD_ZbRg2RHB_k8sF6hXWbKiRFI
            @Override // androidx.asynclayoutinflater.view.AsyncLayoutInflater.OnInflateFinishedListener
            public final void onInflateFinished(View view, int i3, ViewGroup viewGroup) {
                HomeKidsAdapter.this.a(view, i3, viewGroup);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void c(View view, int i, ViewGroup viewGroup) {
        this.e.add(view);
        this.d.put(1, this.e);
        L.childContent.i("async add video views");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(View view, int i, ViewGroup viewGroup) {
        this.f.add(view);
        this.d.put(3, this.f);
        L.childContent.i("async add story views");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(View view, int i, ViewGroup viewGroup) {
        this.g.add(view);
        this.d.put(2, this.g);
        L.childContent.i("async add app views");
    }
}
