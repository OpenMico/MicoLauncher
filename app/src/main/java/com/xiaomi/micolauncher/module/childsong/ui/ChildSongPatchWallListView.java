package com.xiaomi.micolauncher.module.childsong.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.api.model.PatchWall;
import com.xiaomi.micolauncher.module.music.bean.Header;
import com.xiaomi.micolauncher.module.music.bean.TitleType;
import java.util.List;

/* loaded from: classes3.dex */
public class ChildSongPatchWallListView extends LinearLayout {
    private boolean a = false;

    public ChildSongPatchWallListView(Context context) {
        super(context);
    }

    public ChildSongPatchWallListView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public ChildSongPatchWallListView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public void setDataList(List<Object> list) {
        if (!ContainerUtil.isEmpty(list)) {
            removeAllViews();
            this.a = false;
            inflateDataList(list);
        }
    }

    public void addDataList(List<Object> list) {
        if (!ContainerUtil.isEmpty(list)) {
            inflateDataList(list);
        }
    }

    public void inflateDataList(List<Object> list) {
        for (Object obj : list) {
            if (obj instanceof Header) {
                addView(new ChildSongPatchWallHeader(getContext()));
            } else if (obj instanceof PatchWall.Category.Block) {
                ChildSongPatchWallSquareView childSongPatchWallSquareView = new ChildSongPatchWallSquareView(getContext());
                childSongPatchWallSquareView.setPatchBlockData((PatchWall.Category.Block) obj, this.a, TitleType.LEFT);
                this.a = true;
                addView(childSongPatchWallSquareView);
            }
        }
    }
}
