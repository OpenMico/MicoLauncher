package com.xiaomi.micolauncher.module.music.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.PatchWall;
import com.xiaomi.micolauncher.module.music.base.BasePatchWallBlockView;
import com.xiaomi.micolauncher.module.music.bean.SquareItem;
import com.xiaomi.micolauncher.module.music.bean.TitleType;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public class MusicPatchWallSquareView extends LinearLayout {
    View a;
    TextView b;
    BasePatchWallBlockView c;
    private Context d;

    public MusicPatchWallSquareView(Context context) {
        this(context, null);
    }

    public MusicPatchWallSquareView(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public MusicPatchWallSquareView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.d = context;
        a();
    }

    private void a() {
        View.inflate(this.d, R.layout.view_music_patch_wall_square, this);
        this.a = findViewById(R.id.title_view);
        this.b = (TextView) findViewById(R.id.title_tv);
        this.c = (BasePatchWallBlockView) findViewById(R.id.block_view);
    }

    public void setPatchBlockData(PatchWall.Block block) {
        setPatchBlockData(block, true, TitleType.LEFT);
    }

    public void setDefaultResId(int i) {
        this.c.setDefaultResId(i);
    }

    public void setPatchBlockData(PatchWall.Block block, boolean z, TitleType titleType) {
        if (!(block == null || block.items == null || block.items.size() <= 0)) {
            if (z) {
                this.a.setVisibility(0);
            }
            this.b.setText(block.title);
            ArrayList arrayList = new ArrayList();
            for (PatchWall.Item item : block.items) {
                arrayList.add(new SquareItem(item));
            }
            this.c.setFixFirstLocation(!z);
            this.c.setDataList(arrayList, z, titleType, block.title);
        }
    }
}
