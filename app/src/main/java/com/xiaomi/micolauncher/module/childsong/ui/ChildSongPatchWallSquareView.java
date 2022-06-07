package com.xiaomi.micolauncher.module.childsong.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.PatchWall;
import com.xiaomi.micolauncher.module.music.base.BasePatchWallBlockView;
import com.xiaomi.micolauncher.module.music.bean.TitleType;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public class ChildSongPatchWallSquareView extends LinearLayout {
    View a;
    TextView b;
    BasePatchWallBlockView c;

    public ChildSongPatchWallSquareView(Context context) {
        this(context, null);
    }

    public ChildSongPatchWallSquareView(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ChildSongPatchWallSquareView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        a();
    }

    private void a() {
        View.inflate(getContext(), R.layout.view_station_patch_wall_square, this);
        this.a = findViewById(R.id.title_view);
        this.b = (TextView) findViewById(R.id.title_tv);
        this.c = (BasePatchWallBlockView) findViewById(R.id.block_view);
    }

    public void setPatchBlockData(PatchWall.Category.Block block, boolean z, TitleType titleType) {
        if (block != null && !ContainerUtil.isEmpty(block.itemList)) {
            if (z) {
                this.a.setVisibility(0);
            }
            this.b.setText(block.categoryName);
            ArrayList arrayList = new ArrayList();
            arrayList.addAll(block.itemList);
            this.c.setDisableRefresh(true);
            this.c.setDataList(arrayList, z, titleType);
        }
    }
}
