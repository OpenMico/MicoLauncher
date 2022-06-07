package com.xiaomi.micolauncher.module.video.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import com.xiaomi.micolauncher.api.model.Video;
import com.xiaomi.micolauncher.module.music.bean.Header;
import com.xiaomi.micolauncher.module.music.bean.TitleType;
import com.xiaomi.micolauncher.module.music.listener.BindLifecycleListener;
import com.xiaomi.micolauncher.module.video.manager.VideoDataManager;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class VideoPatchWallListView extends LinearLayout implements BindLifecycleListener {
    private List<VideoPatchWallSquareView> a;
    private boolean b;
    private Context c;

    public VideoPatchWallListView(Context context) {
        this(context, null);
    }

    public VideoPatchWallListView(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public VideoPatchWallListView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.a = new ArrayList();
        this.b = false;
        this.c = context;
    }

    public void setDataList(List<Object> list) {
        if (list != null && list.size() > 0) {
            removeAllViews();
            this.a.clear();
            this.b = false;
            a(list);
        }
    }

    public void addDataList(List<Object> list) {
        if (list != null && list.size() > 0) {
            a(list);
        }
    }

    private void a(List<Object> list) {
        for (Object obj : list) {
            if (obj instanceof Header) {
                if (VideoDataManager.getManager().isChildMode()) {
                    addView(new VideoPatchWallHeaderChild(this.c));
                } else {
                    addView(new VideoPatchWallHeader(this.c));
                }
            } else if (obj instanceof Video.Block) {
                VideoPatchWallSquareView videoPatchWallSquareView = new VideoPatchWallSquareView(this.c);
                this.a.add(videoPatchWallSquareView);
                videoPatchWallSquareView.setPatchBlockData((Video.Block) obj, this.b, TitleType.LEFT);
                this.b = true;
                addView(videoPatchWallSquareView);
            }
        }
    }

    @Override // com.xiaomi.micolauncher.module.music.listener.BindLifecycleListener
    public void onResume() {
        for (VideoPatchWallSquareView videoPatchWallSquareView : this.a) {
            if (videoPatchWallSquareView != null && (videoPatchWallSquareView instanceof BindLifecycleListener)) {
                videoPatchWallSquareView.onResume();
            }
        }
    }

    @Override // com.xiaomi.micolauncher.module.music.listener.BindLifecycleListener
    public void onPause() {
        for (VideoPatchWallSquareView videoPatchWallSquareView : this.a) {
            if (videoPatchWallSquareView != null && (videoPatchWallSquareView instanceof BindLifecycleListener)) {
                videoPatchWallSquareView.onPause();
            }
        }
    }
}
