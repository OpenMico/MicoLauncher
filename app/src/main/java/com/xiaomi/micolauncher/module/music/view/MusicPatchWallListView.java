package com.xiaomi.micolauncher.module.music.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.PatchWall;
import com.xiaomi.micolauncher.module.music.bean.Header;
import com.xiaomi.micolauncher.module.music.bean.TitleType;
import java.util.List;

/* loaded from: classes3.dex */
public class MusicPatchWallListView extends LinearLayout {
    private boolean a;
    private Context b;

    public MusicPatchWallListView(@NonNull Context context) {
        this(context, null);
    }

    public MusicPatchWallListView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public MusicPatchWallListView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.a = false;
        this.b = context;
    }

    public void setDataList(List<Object> list) {
        if (list != null && list.size() > 0) {
            removeAllViews();
            this.a = false;
            a(list);
        }
    }

    public void addDataList(List<Object> list) {
        if (list != null && list.size() > 0) {
            a(list);
        }
    }

    private void a(List<Object> list) {
        View view;
        for (Object obj : list) {
            if (obj instanceof Header) {
                addView(new MusicPatchWallHeader(this.b));
            } else if (obj instanceof PatchWall.Block) {
                PatchWall.Block block = (PatchWall.Block) obj;
                if (block == null || block.blockUiType == null || block.blockUiType.name == null) {
                    View musicPatchWallSquareView = new MusicPatchWallSquareView(this.b);
                    ((MusicPatchWallSquareView) musicPatchWallSquareView).setPatchBlockData(block, this.a, TitleType.LEFT);
                    view = musicPatchWallSquareView;
                } else {
                    view = a(block);
                }
                if (Hardware.isBigScreen()) {
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -1);
                    layoutParams.rightMargin = getResources().getDimensionPixelSize(R.dimen.recommend_margin);
                    addView(view, layoutParams);
                } else {
                    addView(view);
                }
            }
        }
    }

    private int a(int i) {
        return getResources().getDimensionPixelSize(i);
    }

    private View a(PatchWall.Block block, boolean z) {
        MusicPatchWallCircleView musicPatchWallCircleView = new MusicPatchWallCircleView(this.b);
        musicPatchWallCircleView.setBackgroundResource(R.drawable.bg_setting_black);
        musicPatchWallCircleView.setPadding(a(R.dimen.circle_card_padding_horizontal), a(R.dimen.circle_card_padding_top), a(R.dimen.circle_card_padding_horizontal), a(R.dimen.circle_card_padding_bottom));
        musicPatchWallCircleView.setPatchBlockData(block, z);
        return musicPatchWallCircleView;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x00ff, code lost:
        if (r0.equals(com.xiaomi.micolauncher.api.model.PatchWall.BLOCK_GRID_QQ_MUSIC_GROUP) != false) goto L_0x012b;
     */
    /* JADX WARN: Code restructure failed: missing block: B:7:0x001e, code lost:
        if (r0.equals(com.xiaomi.micolauncher.api.model.PatchWall.BLOCK_GRID_QQ_MUSIC_GROUP) != false) goto L_0x0068;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private android.view.View a(com.xiaomi.micolauncher.api.model.PatchWall.Block r9) {
        /*
            Method dump skipped, instructions count: 446
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.micolauncher.module.music.view.MusicPatchWallListView.a(com.xiaomi.micolauncher.api.model.PatchWall$Block):android.view.View");
    }
}
