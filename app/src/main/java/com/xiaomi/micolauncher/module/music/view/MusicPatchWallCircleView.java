package com.xiaomi.micolauncher.module.music.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.PatchWall;
import com.xiaomi.micolauncher.module.music.base.BasePatchWallBlockView;
import com.xiaomi.micolauncher.module.music.bean.SquareItem;
import com.xiaomi.micolauncher.module.music.bean.TitleType;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public class MusicPatchWallCircleView extends LinearLayout {
    TextView a;
    BasePatchWallBlockView b;
    View c;
    private Context d;

    public MusicPatchWallCircleView(Context context) {
        this(context, null);
    }

    public MusicPatchWallCircleView(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public MusicPatchWallCircleView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.d = context;
        a();
    }

    private void a() {
        View.inflate(this.d, R.layout.view_music_patch_wall_circle, this);
        this.a = (TextView) findViewById(R.id.title_tv);
        this.b = (BasePatchWallBlockView) findViewById(R.id.block_view);
        this.c = findViewById(R.id.title_view);
    }

    public void setDefaultResId(int i) {
        this.b.setDefaultResId(i);
    }

    public void setPatchBlockData(PatchWall.Block block, boolean z) {
        this.b.setShowPlayIcon(z);
        setPatchBlockData(block);
    }

    public void setPatchBlockData(PatchWall.Block block) {
        if (!(block == null || block.items == null || block.items.size() <= 0)) {
            this.a.setText(block.title);
            if (Hardware.isBigScreen()) {
                this.c.setVisibility(8);
            }
            ArrayList arrayList = new ArrayList();
            for (PatchWall.Item item : block.items) {
                arrayList.add(new SquareItem(item));
            }
            this.b.setDataList(arrayList, true, Hardware.isBigScreen() ? TitleType.BIG_CIRCLE : TitleType.CENTER, block.title);
        }
    }
}
