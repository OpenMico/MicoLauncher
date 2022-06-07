package com.xiaomi.micolauncher.module.homepage.view;

import android.content.Context;
import android.media.MediaMetadata;
import android.support.v4.media.MediaMetadataCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.core.app.NotificationCompat;
import com.xiaomi.mico.base.ui.RxViewHelp;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.PatchWall;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.GlideUtils;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.schema.SchemaManager;
import com.xiaomi.micolauncher.common.schema.handler.HomepageSchemaHandler;
import com.xiaomi.micolauncher.common.ui.MicoAnimConfigurator4EntertainmentAndApps;
import com.xiaomi.micolauncher.common.utils.UiUtils;
import com.xiaomi.micolauncher.common.view.AnimLifecycleManager;
import com.xiaomi.micolauncher.module.homepage.event.MusicPlayEvent;
import com.xiaomi.micolauncher.module.homepage.manager.ChildSongDataManager;
import com.xiaomi.micolauncher.module.music.utils.MusicStatHelper;
import com.xiaomi.micolauncher.skills.music.PlayerApi;
import com.xiaomi.micolauncher.skills.music.controller.LocalPlayerManager;
import com.xiaomi.micolauncher.skills.music.controller.LoopType;
import io.reactivex.functions.Consumer;
import java.util.Calendar;
import java.util.List;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public class MusicPatchWallLikeView extends RelativeLayout {
    private static final int[] i = {R.drawable.music_like_0, R.drawable.music_like_1, R.drawable.music_like_2, R.drawable.music_like_3, R.drawable.music_like_4, R.drawable.music_like_5, R.drawable.music_like_6, R.drawable.music_like_7, R.drawable.music_like_8, R.drawable.music_like_9};
    ImageView[] a;
    ImageView b;
    ImageView c;
    ImageView d;
    ImageView e;
    ImageView f;
    ImageView g;
    private PatchWall.Block h;
    private Context j;

    public MusicPatchWallLikeView(Context context) {
        this(context, null);
    }

    public MusicPatchWallLikeView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public MusicPatchWallLikeView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.a = new ImageView[6];
        this.j = context;
        a();
        EventBusRegistry.getEventBus().register(this);
    }

    private void a() {
        View.inflate(this.j, R.layout.guess_you_like_music, this);
        this.a[0] = (ImageView) findViewById(R.id.like_img_1);
        this.a[1] = (ImageView) findViewById(R.id.like_img_2);
        this.a[2] = (ImageView) findViewById(R.id.like_img_3);
        this.a[3] = (ImageView) findViewById(R.id.like_img_4);
        this.a[4] = (ImageView) findViewById(R.id.like_img_5);
        this.a[5] = (ImageView) findViewById(R.id.like_img_6);
        this.b = (ImageView) findViewById(R.id.date_ten_month_iv);
        this.c = (ImageView) findViewById(R.id.date_single_month_iv);
        this.d = (ImageView) findViewById(R.id.date_ten_day_iv);
        this.e = (ImageView) findViewById(R.id.date_single_day_iv);
        this.f = (ImageView) findViewById(R.id.like_bg);
        this.g = (ImageView) findViewById(R.id.iv_play_state);
    }

    public void initInMain() {
        AnimLifecycleManager.repeatOnAttach(this.g, MicoAnimConfigurator4EntertainmentAndApps.get());
        RxViewHelp.debounceClicksWithOneSeconds(this.g).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.view.-$$Lambda$MusicPatchWallLikeView$mow2LAHqz7QbUb3ctX41Pvrt6Cw
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MusicPatchWallLikeView.this.a(obj);
            }
        });
        final int i2 = 0;
        while (true) {
            ImageView[] imageViewArr = this.a;
            if (i2 < imageViewArr.length) {
                AnimLifecycleManager.repeatOnAttach(imageViewArr[i2], MicoAnimConfigurator4EntertainmentAndApps.get());
                RxViewHelp.debounceClicksWithOneSeconds(this.a[i2]).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.view.-$$Lambda$MusicPatchWallLikeView$80T8AFW379nMGOgPEzL7bK0GdgI
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        MusicPatchWallLikeView.this.a(i2, obj);
                    }
                });
                i2++;
            } else {
                return;
            }
        }
    }

    public /* synthetic */ void a(Object obj) throws Exception {
        PlayerApi.playPatchwallBlock(getContext(), NotificationCompat.CATEGORY_RECOMMENDATION, 0L, LoopType.SHUFFLE.ordinal(), -1);
        MusicStatHelper.recordMusicClick(this.j.getResources().getString(R.string.recommend_music_list), this.j.getResources().getString(R.string.recommend_today_music));
    }

    public /* synthetic */ void a(int i2, Object obj) throws Exception {
        if (LocalPlayerManager.getInstance().isBlack(this.h.items.get(i2).title)) {
            SchemaManager.handleSchema(this.j, HomepageSchemaHandler.PATH_BLACKLIST);
            return;
        }
        if (ContainerUtil.hasData(this.h.songList)) {
            PlayerApi.playMusicList(this.j, this.h.songList, i2);
        } else {
            PlayerApi.playPatchwallBlock(getContext(), "radio", Long.parseLong(ChildSongDataManager.ID_CHILD_SONG_RECOMMEND), LoopType.SHUFFLE.ordinal(), i2);
        }
        MusicStatHelper.recordMusicClick(this.j.getResources().getString(R.string.recommend_music_list), this.j.getResources().getString(R.string.recommend_today_music));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMusicPlayed(MusicPlayEvent musicPlayEvent) {
        MediaMetadata mediaMetadata = musicPlayEvent.metadata;
        int i2 = musicPlayEvent.playerStatus.status;
        boolean z = true;
        if (!NotificationCompat.CATEGORY_RECOMMENDATION.equals(mediaMetadata.getString(MediaMetadataCompat.METADATA_KEY_DISPLAY_DESCRIPTION)) || i2 != 1) {
            z = false;
        }
        GlideUtils.bindImageView(this.j, z ? R.drawable.music_daily_pause : R.drawable.music_daily_play, this.g);
    }

    private void b() {
        Calendar instance = Calendar.getInstance();
        int i2 = instance.get(5);
        this.d.setImageResource(i[i2 / 10]);
        this.d.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        this.e.setImageResource(i[i2 % 10]);
        this.e.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        int i3 = instance.get(2) + 1;
        this.b.setImageResource(i[i3 / 10]);
        this.b.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        this.c.setImageResource(i[i3 % 10]);
        this.c.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
    }

    public void setPatchBlockData(PatchWall.Block block) {
        int i2;
        Context context;
        b();
        this.h = block;
        List<PatchWall.Item> list = block.items;
        for (int i3 = 0; i3 < this.a.length; i3++) {
            long j = i3;
            if (ContainerUtil.isOutOfBound(j, list) || ContainerUtil.isOutOfBound(j, this.a)) {
                L.homepage.e("Error : setPatchBlockData, index of out of bound. index %s, items %s, image views %s", Integer.valueOf(i3), Integer.valueOf(ContainerUtil.getSize(list)), Integer.valueOf(ContainerUtil.getSize(this.a)));
                return;
            }
            if (i3 == 0) {
                context = this.j;
                i2 = R.dimen.recommend_big_img_size;
            } else {
                context = this.j;
                i2 = R.dimen.recommend_small_img_size;
            }
            int size = UiUtils.getSize(context, i2);
            GlideUtils.bindImageViewWithRoundCorners(getContext(), list.get(i3).images.poster.url, this.a[i3], UiUtils.getSize(this.j, R.dimen.entertainment_small_icon_corner_radius), R.drawable.app_icon_placeholder, size, size);
        }
        if (ContainerUtil.isEmpty(list)) {
            L.homepage.e("Error : setPatchBlockData, items is empty.");
        } else {
            GlideUtils.bindImageView(getContext(), this.f, list.get(0).images.poster.url, UiUtils.getSize(this.j, R.dimen.recommend_layout_width), UiUtils.getSize(this.j, R.dimen.recommend_layout_height));
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (EventBusRegistry.getEventBus().isRegistered(this)) {
            EventBusRegistry.getEventBus().unregister(this);
        }
    }
}
