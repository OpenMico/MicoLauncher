package com.xiaomi.micolauncher.module.homepage.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.application.MicoApplication;
import com.xiaomi.micolauncher.common.DebugHelper;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.utils.HolderCacheManager;
import com.xiaomi.micolauncher.common.utils.ViewHolderFactory;
import com.xiaomi.micolauncher.common.utils.X2CWrapper;
import com.xiaomi.micolauncher.common.view.BaseViewHolder;
import com.xiaomi.micolauncher.common.widget.HorizontalRecyclerView;
import com.xiaomi.micolauncher.module.homepage.viewholder.audiobook.AudioBookContentViewHolder;
import com.xiaomi.micolauncher.module.homepage.viewholder.audiobook.AudiobookBannerViewHolder;
import com.xiaomi.micolauncher.module.homepage.viewholder.audiobook.AudiobookLatestViewHolder;
import com.xiaomi.micolauncher.module.homepage.viewholder.audiobook.AudiobookRefreshViewHolder;
import com.xiaomi.micolauncher.module.homepage.viewholder.music.BaseMusicViewHolder;
import com.xiaomi.micolauncher.module.homepage.viewholder.music.MusicCategoryViewHolder;
import com.xiaomi.micolauncher.module.homepage.viewholder.music.MusicCircleViewHolder;
import com.xiaomi.micolauncher.module.homepage.viewholder.music.MusicEmptyHolder;
import com.xiaomi.micolauncher.module.homepage.viewholder.music.MusicHotViewHolder;
import com.xiaomi.micolauncher.module.homepage.viewholder.music.MusicLikeViewHolder;
import com.xiaomi.micolauncher.module.homepage.viewholder.music.MusicMvViewHolder;
import com.xiaomi.micolauncher.module.homepage.viewholder.music.MusicRecommendViewHolder;
import com.xiaomi.miplay.mylibrary.mirror.MirrorControl;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes3.dex */
public class EntertainmentHolderCacheManager {
    private ConcurrentHashMap<Integer, BaseMusicViewHolder> a;
    private LinkedList<AudioBookContentViewHolder> b;
    private ConcurrentHashMap<Integer, BaseViewHolder> c;
    private HorizontalRecyclerView d;
    private ViewHolderFactory e;

    /* loaded from: classes3.dex */
    public static class a {
        private static final EntertainmentHolderCacheManager a = new EntertainmentHolderCacheManager();
    }

    public static EntertainmentHolderCacheManager getManager() {
        return a.a;
    }

    private EntertainmentHolderCacheManager() {
        this.a = new ConcurrentHashMap<>();
        this.b = new LinkedList<>();
        this.c = new ConcurrentHashMap<>();
        this.e = new ViewHolderFactory() { // from class: com.xiaomi.micolauncher.module.homepage.view.-$$Lambda$EntertainmentHolderCacheManager$NWhR9Ih2P62Th1x3OyJ-I4P7HAw
            @Override // com.xiaomi.micolauncher.common.utils.ViewHolderFactory
            public final Object createViewHolder() {
                AudioBookContentViewHolder c;
                c = EntertainmentHolderCacheManager.this.c();
                return c;
            }
        };
        a(MicoApplication.getApp());
    }

    public BaseMusicViewHolder getMusicViewHolder(int i) {
        BaseMusicViewHolder baseMusicViewHolder;
        BaseMusicViewHolder remove = this.a.remove(Integer.valueOf(i));
        if (remove != null) {
            remove.initInMain();
            return remove;
        }
        switch (i) {
            case 0:
                baseMusicViewHolder = new MusicLikeViewHolder(a(R.layout.music_find_like));
                break;
            case 1:
                baseMusicViewHolder = new MusicMvViewHolder(a(R.layout.music_find_mv));
                break;
            case 2:
            case 3:
            case 4:
                baseMusicViewHolder = new MusicRecommendViewHolder(a(R.layout.recommend_layout));
                break;
            case 5:
            case 6:
            case 7:
                baseMusicViewHolder = new MusicCircleViewHolder(a(R.layout.music_radio_layout), i);
                break;
            case 8:
                baseMusicViewHolder = new MusicHotViewHolder(a(R.layout.music_find_hot));
                break;
            case 9:
            case 10:
            case 11:
                baseMusicViewHolder = new MusicCircleViewHolder(a(R.layout.music_singer_layout), i);
                break;
            case 12:
            case 13:
            case 14:
                baseMusicViewHolder = new MusicCategoryViewHolder(a(R.layout.music_category_layout));
                break;
            case 15:
                baseMusicViewHolder = new MusicEmptyHolder(a(R.layout.auth_status_layout));
                break;
            default:
                baseMusicViewHolder = new MusicLikeViewHolder(a(R.layout.music_find_like));
                break;
        }
        baseMusicViewHolder.initInMain();
        return baseMusicViewHolder;
    }

    public void initMusicView() {
        long beginTiming = DebugHelper.beginTiming();
        this.a.put(0, new MusicLikeViewHolder(a(R.layout.music_find_like)));
        this.a.put(1, new MusicMvViewHolder(a(R.layout.music_find_mv)));
        this.a.put(2, new MusicRecommendViewHolder(a(R.layout.recommend_layout)));
        this.a.put(3, new MusicRecommendViewHolder(a(R.layout.recommend_layout)));
        this.a.put(4, new MusicRecommendViewHolder(a(R.layout.recommend_layout)));
        this.a.put(5, new MusicCircleViewHolder(a(R.layout.music_radio_layout), 5));
        this.a.put(6, new MusicCircleViewHolder(a(R.layout.music_radio_layout), 6));
        this.a.put(7, new MusicCircleViewHolder(a(R.layout.music_radio_layout), 7));
        this.a.put(8, new MusicHotViewHolder(a(R.layout.music_find_hot)));
        this.a.put(9, new MusicCircleViewHolder(a(R.layout.music_singer_layout), 9));
        this.a.put(10, new MusicCircleViewHolder(a(R.layout.music_singer_layout), 10));
        this.a.put(11, new MusicCircleViewHolder(a(R.layout.music_singer_layout), 11));
        this.a.put(12, new MusicCategoryViewHolder(a(R.layout.music_category_layout)));
        this.a.put(13, new MusicCategoryViewHolder(a(R.layout.music_category_layout)));
        this.a.put(14, new MusicCategoryViewHolder(a(R.layout.music_category_layout)));
        DebugHelper.endTiming(beginTiming, "init music view", new Object[0]);
    }

    public void initAudiobookView() {
        L.homepage.d("init audiobook view start");
        b();
        a();
        L.homepage.d("init audiobook view finish");
    }

    private void a() {
        this.c.put(100000, new AudiobookLatestViewHolder(b(R.layout.audiobooks_latest_item)));
        this.c.put(Integer.valueOf((int) MirrorControl.DISPLAY_INFO_IS_UDP), new AudiobookBannerViewHolder(a(R.layout.audiobooks_banner_item)));
        this.c.put(200000, new AudiobookRefreshViewHolder(a(R.layout.audiobook_footer_layout)));
    }

    public /* synthetic */ AudioBookContentViewHolder c() {
        return new AudioBookContentViewHolder(a(R.layout.audiobook_item_for_main));
    }

    private void b() {
        HolderCacheManager.prepareViewHolders(8, this.b, this.e);
    }

    public AudioBookContentViewHolder fetchAudiobookContentViewHolder(boolean z) {
        if (z) {
            return (AudioBookContentViewHolder) HolderCacheManager.fetchViewHolder(this.b, this.e);
        }
        AudioBookContentViewHolder audioBookContentViewHolder = new AudioBookContentViewHolder(a(R.layout.audiobook_item));
        audioBookContentViewHolder.initInMain();
        return audioBookContentViewHolder;
    }

    public BaseViewHolder fetchAudiobookViewHolder(Context context, int i) {
        BaseViewHolder remove = this.c.remove(Integer.valueOf(i));
        if (remove != null) {
            return remove;
        }
        if (i == 100000) {
            return new AudiobookLatestViewHolder(b(R.layout.audiobooks_latest_item));
        }
        if (i == 100001) {
            return new AudiobookBannerViewHolder(a(R.layout.audiobooks_banner_item));
        }
        return new AudiobookRefreshViewHolder(a(R.layout.audiobook_footer_layout));
    }

    private void a(Context context) {
        if (this.d == null) {
            this.d = new HorizontalRecyclerView(context);
        }
    }

    private View a(int i) {
        return X2CWrapper.inflate(this.d.getContext(), i, (ViewGroup) this.d, false);
    }

    private View b(int i) {
        return X2CWrapper.inflateNoX2C(this.d.getContext(), i, (ViewGroup) this.d, false);
    }

    public void clearMusicViewHolder() {
        this.a.clear();
    }

    public void clearBookViewHolder() {
        this.b.clear();
        this.c.clear();
    }
}
