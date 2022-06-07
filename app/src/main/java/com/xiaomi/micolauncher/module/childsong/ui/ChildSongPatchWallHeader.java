package com.xiaomi.micolauncher.module.childsong.ui;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.module.childsong.ChildSongApiHelper;
import com.xiaomi.micolauncher.module.childsong.ChildSongGroupListActivity;
import com.xiaomi.micolauncher.module.music.utils.MusicStatHelper;
import com.xiaomi.micolauncher.skills.music.PlayerApi;
import com.xiaomi.micolauncher.skills.music.controller.LoopType;

/* loaded from: classes3.dex */
public class ChildSongPatchWallHeader extends LinearLayout {
    ImageView a;
    ImageView b;
    ImageView c;

    public ChildSongPatchWallHeader(Context context) {
        this(context, null);
    }

    public ChildSongPatchWallHeader(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ChildSongPatchWallHeader(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        a();
    }

    private void a() {
        View.inflate(getContext(), R.layout.view_child_song_patch_wall_header, this);
        b();
        ChildSongApiHelper.getInstance().loadBabyLikeListUrl();
    }

    private void b() {
        this.a = (ImageView) findViewById(R.id.recent_list);
        this.b = (ImageView) findViewById(R.id.baby_like);
        this.c = (ImageView) findViewById(R.id.all_list);
        this.a.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.childsong.ui.-$$Lambda$ChildSongPatchWallHeader$6EasbFDzVoY02r6msWEDk4Mg0DI
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ChildSongPatchWallHeader.this.c(view);
            }
        });
        this.b.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.childsong.ui.-$$Lambda$ChildSongPatchWallHeader$ZK7RVL5a9-TFThMFlweBsgaa_7Q
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ChildSongPatchWallHeader.this.b(view);
            }
        });
        this.c.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.childsong.ui.-$$Lambda$ChildSongPatchWallHeader$4ife-4HJ34mqFA63lAovJuysFiQ
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ChildSongPatchWallHeader.this.a(view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void c(View view) {
        PlayerApi.playRecently(getContext());
        MusicStatHelper.recordMusicClick(MusicStatHelper.MusicVal.MUSIC_RECENT);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(View view) {
        String babyLikeListUrl = ChildSongApiHelper.getInstance().getBabyLikeListUrl();
        if (!ContainerUtil.isEmpty(babyLikeListUrl)) {
            String queryParameter = Uri.parse(babyLikeListUrl).getQueryParameter("id");
            if (!ContainerUtil.isEmpty(queryParameter)) {
                PlayerApi.playSheet(getContext(), queryParameter, LoopType.SHUFFLE);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(View view) {
        ChildSongGroupListActivity.openChildSongGroupListActivity(getContext(), 0L);
    }
}
