package com.xiaomi.micolauncher.module.homepage.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.PatchWall;
import com.xiaomi.micolauncher.common.utils.X2CWrapper;

/* loaded from: classes3.dex */
public class MusicPatchWallHotView extends FrameLayout {
    HotMusicItem a;
    HotMusicItem b;

    public MusicPatchWallHotView(Context context) {
        this(context, null);
    }

    public MusicPatchWallHotView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public MusicPatchWallHotView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        a();
    }

    private void a() {
        X2CWrapper.inflate(getContext(), (int) R.layout.hot_music_list, this);
        this.a = (HotMusicItem) findViewById(R.id.hottest_item);
        this.b = (HotMusicItem) findViewById(R.id.newest_item);
    }

    public void initInMain() {
        this.a.initInMain();
        this.b.initInMain();
    }

    public void setPatchBlockData(PatchWall.Block block) {
        this.a.setDataList(block, 0);
        this.b.setDataList(block, 1);
    }
}
