package com.xiaomi.micolauncher.module.homepage.viewholder.new_home;

import android.content.Context;
import android.view.ViewGroup;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.application.MicoApplication;
import com.xiaomi.micolauncher.common.utils.HolderCacheManager;
import com.xiaomi.micolauncher.common.utils.ViewHolderFactory;
import com.xiaomi.micolauncher.common.utils.X2CWrapper;
import com.xiaomi.micolauncher.common.widget.HorizontalRecyclerView;
import java.util.LinkedList;

/* loaded from: classes3.dex */
public class VideoViewHoldersCache {
    private static final VideoViewHoldersCache a = new VideoViewHoldersCache();
    private HorizontalRecyclerView f;
    private LinkedList<EmptyHistoryHolder> b = new LinkedList<>();
    private LinkedList<VideoCategoryViewHolder> c = new LinkedList<>();
    private LinkedList<VideoRecommendAppViewHolder> d = new LinkedList<>();
    private final LinkedList<VideoViewHolder> e = new LinkedList<>();
    private ViewHolderFactory g = new ViewHolderFactory() { // from class: com.xiaomi.micolauncher.module.homepage.viewholder.new_home.-$$Lambda$VideoViewHoldersCache$31khJuS-4roBGSgsaG5i-133bz8
        @Override // com.xiaomi.micolauncher.common.utils.ViewHolderFactory
        public final Object createViewHolder() {
            Object a2;
            a2 = VideoViewHoldersCache.this.a();
            return a2;
        }
    };

    public VideoViewHoldersCache() {
        a(MicoApplication.getApp());
    }

    public static VideoViewHoldersCache getInstance() {
        return a;
    }

    public void initVideoHolder() {
        prepareVideoViewHolders(this.f);
        prepareEmpty(this.f);
        prepareCategoryHolder(this.f);
        prepareRecommendAppHolder(this.f);
    }

    private void a(Context context) {
        if (this.f == null) {
            this.f = new HorizontalRecyclerView(context);
        }
    }

    public static /* synthetic */ EmptyHistoryHolder g(ViewGroup viewGroup) {
        return new EmptyHistoryHolder(X2CWrapper.inflate(viewGroup.getContext(), (int) R.layout.recent_empty_layout, viewGroup, false));
    }

    public void prepareEmpty(final ViewGroup viewGroup) {
        HolderCacheManager.prepareViewHolders(1, this.b, new ViewHolderFactory() { // from class: com.xiaomi.micolauncher.module.homepage.viewholder.new_home.-$$Lambda$VideoViewHoldersCache$gZf11BQYhpYSr4qrIJ7dGyWR1Oc
            @Override // com.xiaomi.micolauncher.common.utils.ViewHolderFactory
            public final Object createViewHolder() {
                EmptyHistoryHolder g;
                g = VideoViewHoldersCache.g(viewGroup);
                return g;
            }
        });
    }

    public static /* synthetic */ EmptyHistoryHolder f(ViewGroup viewGroup) {
        return new EmptyHistoryHolder(X2CWrapper.inflate(viewGroup.getContext(), (int) R.layout.recent_empty_layout, viewGroup, false));
    }

    public EmptyHistoryHolder fetchEmptyHistoryHolder(final ViewGroup viewGroup) {
        return (EmptyHistoryHolder) HolderCacheManager.fetchViewHolder(this.b, new ViewHolderFactory() { // from class: com.xiaomi.micolauncher.module.homepage.viewholder.new_home.-$$Lambda$VideoViewHoldersCache$z2xzCy2hdeGvGF74EHXFNMO1TO0
            @Override // com.xiaomi.micolauncher.common.utils.ViewHolderFactory
            public final Object createViewHolder() {
                EmptyHistoryHolder f;
                f = VideoViewHoldersCache.f(viewGroup);
                return f;
            }
        });
    }

    public static /* synthetic */ VideoRecommendAppViewHolder e(ViewGroup viewGroup) {
        return new VideoRecommendAppViewHolder(X2CWrapper.inflateNoX2C(viewGroup.getContext(), (int) R.layout.item_video_recommend_app, viewGroup, false));
    }

    public void prepareRecommendAppHolder(final ViewGroup viewGroup) {
        HolderCacheManager.prepareViewHolders(1, this.d, new ViewHolderFactory() { // from class: com.xiaomi.micolauncher.module.homepage.viewholder.new_home.-$$Lambda$VideoViewHoldersCache$76PPBIbZbUG-XO882_f2RYB_w5E
            @Override // com.xiaomi.micolauncher.common.utils.ViewHolderFactory
            public final Object createViewHolder() {
                VideoRecommendAppViewHolder e;
                e = VideoViewHoldersCache.e(viewGroup);
                return e;
            }
        });
    }

    public static /* synthetic */ VideoRecommendAppViewHolder d(ViewGroup viewGroup) {
        return new VideoRecommendAppViewHolder(X2CWrapper.inflateNoX2C(viewGroup.getContext(), (int) R.layout.item_video_recommend_app, viewGroup, false));
    }

    public VideoRecommendAppViewHolder fetchRecommendAppHolder(final ViewGroup viewGroup) {
        return (VideoRecommendAppViewHolder) HolderCacheManager.fetchViewHolder(this.d, new ViewHolderFactory() { // from class: com.xiaomi.micolauncher.module.homepage.viewholder.new_home.-$$Lambda$VideoViewHoldersCache$GCnO-15y0S1dnLbg8KOUDQKOSVQ
            @Override // com.xiaomi.micolauncher.common.utils.ViewHolderFactory
            public final Object createViewHolder() {
                VideoRecommendAppViewHolder d;
                d = VideoViewHoldersCache.d(viewGroup);
                return d;
            }
        });
    }

    public static /* synthetic */ VideoCategoryViewHolder c(ViewGroup viewGroup) {
        return new VideoCategoryViewHolder(X2CWrapper.inflate(viewGroup.getContext(), (int) R.layout.video_category_item, viewGroup, false));
    }

    public void prepareCategoryHolder(final ViewGroup viewGroup) {
        HolderCacheManager.prepareViewHolders(1, this.c, new ViewHolderFactory() { // from class: com.xiaomi.micolauncher.module.homepage.viewholder.new_home.-$$Lambda$VideoViewHoldersCache$Tftl9Za1x1HoNWQHmD1Uhze5Fl8
            @Override // com.xiaomi.micolauncher.common.utils.ViewHolderFactory
            public final Object createViewHolder() {
                VideoCategoryViewHolder c;
                c = VideoViewHoldersCache.c(viewGroup);
                return c;
            }
        });
    }

    public static /* synthetic */ VideoCategoryViewHolder b(ViewGroup viewGroup) {
        return new VideoCategoryViewHolder(X2CWrapper.inflate(viewGroup.getContext(), (int) R.layout.video_category_item, viewGroup, false));
    }

    public VideoCategoryViewHolder fetchCategoryHolder(final ViewGroup viewGroup) {
        return (VideoCategoryViewHolder) HolderCacheManager.fetchViewHolder(this.c, new ViewHolderFactory() { // from class: com.xiaomi.micolauncher.module.homepage.viewholder.new_home.-$$Lambda$VideoViewHoldersCache$g6bqbF8m1XfB5dzvvV59Z3Zt4B0
            @Override // com.xiaomi.micolauncher.common.utils.ViewHolderFactory
            public final Object createViewHolder() {
                VideoCategoryViewHolder b;
                b = VideoViewHoldersCache.b(viewGroup);
                return b;
            }
        });
    }

    public static /* synthetic */ VideoViewHolder a(ViewGroup viewGroup) {
        return new VideoViewHolder(X2CWrapper.inflateNoX2C(viewGroup.getContext(), (int) R.layout.video_item, viewGroup, false));
    }

    public void prepareVideoViewHolders(final ViewGroup viewGroup) {
        HolderCacheManager.prepareViewHolders(8, this.e, new ViewHolderFactory() { // from class: com.xiaomi.micolauncher.module.homepage.viewholder.new_home.-$$Lambda$VideoViewHoldersCache$CODt78045JW8oYEyBElnsx28ZQo
            @Override // com.xiaomi.micolauncher.common.utils.ViewHolderFactory
            public final Object createViewHolder() {
                VideoViewHolder a2;
                a2 = VideoViewHoldersCache.a(viewGroup);
                return a2;
            }
        });
    }

    public /* synthetic */ Object a() {
        return new VideoViewHolder(X2CWrapper.inflateNoX2C(this.f.getContext(), (int) R.layout.video_item, (ViewGroup) this.f, false));
    }

    public VideoViewHolder fetchVideoViewHolder(ViewGroup viewGroup) {
        return (VideoViewHolder) HolderCacheManager.fetchViewHolder(this.e, this.g);
    }

    public void clearVideoViewHolder() {
        this.b.clear();
        this.c.clear();
        this.d.clear();
        this.e.clear();
    }
}
