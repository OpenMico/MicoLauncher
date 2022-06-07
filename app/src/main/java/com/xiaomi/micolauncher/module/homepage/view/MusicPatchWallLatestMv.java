package com.xiaomi.micolauncher.module.homepage.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.PatchWall;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.utils.X2CWrapper;
import com.xiaomi.micolauncher.module.music.view.MusicPatchWallHeader;
import java.util.List;

/* loaded from: classes3.dex */
public class MusicPatchWallLatestMv extends FrameLayout {
    MusicPatchWallLatestMvItem[] a;
    MusicPatchWallHeader b;

    public MusicPatchWallLatestMv(Context context) {
        this(context, null);
    }

    public MusicPatchWallLatestMv(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public MusicPatchWallLatestMv(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.a = new MusicPatchWallLatestMvItem[3];
        a();
    }

    public void initInMain() {
        for (MusicPatchWallLatestMvItem musicPatchWallLatestMvItem : this.a) {
            musicPatchWallLatestMvItem.initInMain();
        }
        this.b.initInMain();
    }

    private void a() {
        X2CWrapper.inflate(getContext(), (int) R.layout.latest_music_mv, this);
        this.a[0] = (MusicPatchWallLatestMvItem) findViewById(R.id.latest_music_mv1);
        this.a[1] = (MusicPatchWallLatestMvItem) findViewById(R.id.latest_music_mv2);
        this.a[2] = (MusicPatchWallLatestMvItem) findViewById(R.id.latest_music_mv3);
        this.b = (MusicPatchWallHeader) findViewById(R.id.music_select);
    }

    public void setPatchBlockData(PatchWall.Block block) {
        List<PatchWall.Item> list = block.items;
        if (list != null) {
            for (int i = 0; i < 3 && i < list.size(); i++) {
                this.a[i].setDataBlockItem(list.get(i));
            }
            return;
        }
        L.base.w("items is null!");
    }
}
