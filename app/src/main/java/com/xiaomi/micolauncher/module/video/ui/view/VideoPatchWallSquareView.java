package com.xiaomi.micolauncher.module.video.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.Video;
import com.xiaomi.micolauncher.module.music.base.BasePatchWallBlockView;
import com.xiaomi.micolauncher.module.music.bean.TitleType;
import com.xiaomi.micolauncher.module.music.listener.BindLifecycleListener;
import com.xiaomi.micolauncher.module.video.manager.VideoDataManager;
import com.xiaomi.micolauncher.skills.video.model.VideoItem;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public class VideoPatchWallSquareView extends LinearLayout implements BindLifecycleListener {
    View a;
    TextView b;
    BasePatchWallBlockView c;
    private Context d;

    public VideoPatchWallSquareView(Context context) {
        this(context, null);
    }

    public VideoPatchWallSquareView(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public VideoPatchWallSquareView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.d = context;
        a();
    }

    private void a() {
        View.inflate(this.d, R.layout.view_video_patch_wall_square, this);
        this.a = findViewById(R.id.title_view);
        this.b = (TextView) findViewById(R.id.title_tv);
        this.c = (BasePatchWallBlockView) findViewById(R.id.block_view);
    }

    public void setPatchBlockData(Video.Block block, boolean z, TitleType titleType) {
        if (!(block == null || block.itemList == null || block.itemList.size() <= 0)) {
            if (z) {
                this.a.setVisibility(0);
            }
            this.b.setText(block.categoryType);
            ArrayList arrayList = new ArrayList();
            for (Video.Item item : block.itemList) {
                arrayList.add(new VideoItem(item));
            }
            if (VideoDataManager.getManager().isChildMode()) {
                this.c.setDisableRefresh(true);
            }
            this.c.setDataList(arrayList, z, titleType, block.categoryType);
        }
    }

    @Override // com.xiaomi.micolauncher.module.music.listener.BindLifecycleListener
    public void onResume() {
        BasePatchWallBlockView basePatchWallBlockView = this.c;
        if (basePatchWallBlockView != null && (basePatchWallBlockView instanceof BindLifecycleListener)) {
            basePatchWallBlockView.onResume();
        }
    }

    @Override // com.xiaomi.micolauncher.module.music.listener.BindLifecycleListener
    public void onPause() {
        BasePatchWallBlockView basePatchWallBlockView = this.c;
        if (basePatchWallBlockView != null && (basePatchWallBlockView instanceof BindLifecycleListener)) {
            basePatchWallBlockView.onPause();
        }
    }
}
