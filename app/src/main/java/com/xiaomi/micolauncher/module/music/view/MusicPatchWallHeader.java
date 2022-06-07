package com.xiaomi.micolauncher.module.music.view;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import com.xiaomi.mico.base.utils.ToastUtil;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.common.schema.SchemaManager;
import com.xiaomi.micolauncher.common.schema.handler.HomepageSchemaHandler;
import com.xiaomi.micolauncher.common.ui.MicoAnimConfigurator4EntertainmentAndApps;
import com.xiaomi.micolauncher.common.view.AnimLifecycleManager;
import com.xiaomi.micolauncher.common.view.base.AvoidFastDoubleClickViewOnClickListener;
import com.xiaomi.micolauncher.module.music.MusicFavoriteListActivity;
import com.xiaomi.micolauncher.module.music.utils.MusicStatHelper;
import com.xiaomi.micolauncher.module.music.view.MusicPatchWallHeader;
import com.xiaomi.micolauncher.skills.music.PlayerApi;
import com.xiaomi.micolauncher.skills.music.controller.ChannelManager;
import com.xiaomi.micolauncher.skills.music.controller.LoopType;
import io.reactivex.functions.Consumer;

/* loaded from: classes3.dex */
public class MusicPatchWallHeader extends FrameLayout {
    ImageView a;
    ImageView b;
    ImageView c;
    ImageView d;

    public MusicPatchWallHeader(Context context) {
        this(context, null);
    }

    public MusicPatchWallHeader(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public MusicPatchWallHeader(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        a(context);
    }

    private void a(final Context context) {
        View.inflate(context, R.layout.view_music_patch_wall_header, this);
        this.a = (ImageView) findViewById(R.id.personalize_list);
        this.b = (ImageView) findViewById(R.id.love_list);
        this.c = (ImageView) findViewById(R.id.recent_list);
        this.d = (ImageView) findViewById(R.id.favorite_list);
        this.a.setOnClickListener(new AvoidFastDoubleClickViewOnClickListener() { // from class: com.xiaomi.micolauncher.module.music.view.MusicPatchWallHeader.1
            @Override // com.xiaomi.micolauncher.common.view.base.AvoidFastDoubleClickViewOnClickListener
            public void onAvoidFastDoubleClick(View view) {
                SchemaManager.handleSchema(MusicPatchWallHeader.this.getContext(), HomepageSchemaHandler.PATH_INDIVIDUAL_RADIO);
                MusicStatHelper.recordMusicClick(MusicStatHelper.MusicVal.MUSIC_PRIVATE);
            }
        });
        this.b.setOnClickListener(new AnonymousClass2());
        this.c.setOnClickListener(new AvoidFastDoubleClickViewOnClickListener() { // from class: com.xiaomi.micolauncher.module.music.view.MusicPatchWallHeader.3
            @Override // com.xiaomi.micolauncher.common.view.base.AvoidFastDoubleClickViewOnClickListener
            public void onAvoidFastDoubleClick(View view) {
                PlayerApi.playRecently(MusicPatchWallHeader.this.getContext());
                MusicStatHelper.recordMusicClick(MusicStatHelper.MusicVal.MUSIC_RECENT);
            }
        });
        this.d.setOnClickListener(new AvoidFastDoubleClickViewOnClickListener() { // from class: com.xiaomi.micolauncher.module.music.view.MusicPatchWallHeader.4
            @Override // com.xiaomi.micolauncher.common.view.base.AvoidFastDoubleClickViewOnClickListener
            public void onAvoidFastDoubleClick(View view) {
                context.startActivity(new Intent(context, MusicFavoriteListActivity.class));
                MusicStatHelper.recordMusicClick(MusicStatHelper.MusicVal.MUSIC_COLLECTION);
            }
        });
    }

    /* renamed from: com.xiaomi.micolauncher.module.music.view.MusicPatchWallHeader$2 */
    /* loaded from: classes3.dex */
    public class AnonymousClass2 extends AvoidFastDoubleClickViewOnClickListener {
        AnonymousClass2() {
            MusicPatchWallHeader.this = r1;
        }

        @Override // com.xiaomi.micolauncher.common.view.base.AvoidFastDoubleClickViewOnClickListener
        public void onAvoidFastDoubleClick(View view) {
            ChannelManager.getInstance().loadDefaultChannelId().subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.music.view.-$$Lambda$MusicPatchWallHeader$2$5AthZpqu1hbdpQc3okJoca6Fm_o
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    MusicPatchWallHeader.AnonymousClass2.this.a((Long) obj);
                }
            }, $$Lambda$MusicPatchWallHeader$2$XzBKheVWPIvMVcKJB2zVH_l1Ts.INSTANCE);
            MusicStatHelper.recordMusicClick(MusicStatHelper.MusicVal.MUSIC_LIKE);
        }

        public /* synthetic */ void a(Long l) throws Exception {
            PlayerApi.playSheet(MusicPatchWallHeader.this.getContext(), String.valueOf(l), 0, LoopType.SHUFFLE);
        }

        public static /* synthetic */ void a(Throwable th) throws Exception {
            ToastUtil.showToast((int) R.string.music_love_list_play_failed);
            L.base.e("loadDefaultChannelId", th);
        }
    }

    public void initInMain() {
        if (Hardware.isBigScreen()) {
            AnimLifecycleManager.repeatOnAttach(this.a, MicoAnimConfigurator4EntertainmentAndApps.get());
            AnimLifecycleManager.repeatOnAttach(this.b, MicoAnimConfigurator4EntertainmentAndApps.get());
            AnimLifecycleManager.repeatOnAttach(this.c, MicoAnimConfigurator4EntertainmentAndApps.get());
            AnimLifecycleManager.repeatOnAttach(this.d, MicoAnimConfigurator4EntertainmentAndApps.get());
        }
    }
}
