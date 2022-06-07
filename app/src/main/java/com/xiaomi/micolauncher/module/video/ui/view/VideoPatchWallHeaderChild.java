package com.xiaomi.micolauncher.module.video.ui.view;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.common.view.base.AvoidFastDoubleClickViewOnClickListener;
import com.xiaomi.micolauncher.module.video.manager.VideoStatHelper;
import com.xiaomi.micolauncher.module.video.ui.VideoGroupListActivity;
import com.xiaomi.micolauncher.module.video.ui.VideoRecentListActivity;

/* loaded from: classes3.dex */
public class VideoPatchWallHeaderChild extends LinearLayout {
    ImageView a;
    ImageView b;
    private Context c;

    public VideoPatchWallHeaderChild(Context context) {
        this(context, null);
    }

    public VideoPatchWallHeaderChild(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public VideoPatchWallHeaderChild(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.c = context;
        a();
    }

    private void a() {
        View.inflate(this.c, R.layout.view_video_patch_wall_header_child, this);
        this.a = (ImageView) findViewById(R.id.recent_list);
        this.b = (ImageView) findViewById(R.id.category_list);
        b();
    }

    private void b() {
        this.a.setOnClickListener(new AvoidFastDoubleClickViewOnClickListener() { // from class: com.xiaomi.micolauncher.module.video.ui.view.VideoPatchWallHeaderChild.1
            @Override // com.xiaomi.micolauncher.common.view.base.AvoidFastDoubleClickViewOnClickListener
            public void onAvoidFastDoubleClick(View view) {
                ActivityLifeCycleManager.startActivityQuietly(new Intent(VideoPatchWallHeaderChild.this.c, VideoRecentListActivity.class));
                VideoStatHelper.recordVideoRecommendClick(VideoStatHelper.VideoVal.VIDEO_RECENT);
            }
        });
        this.b.setOnClickListener(new AvoidFastDoubleClickViewOnClickListener() { // from class: com.xiaomi.micolauncher.module.video.ui.view.VideoPatchWallHeaderChild.2
            @Override // com.xiaomi.micolauncher.common.view.base.AvoidFastDoubleClickViewOnClickListener
            public void onAvoidFastDoubleClick(View view) {
                ActivityLifeCycleManager.startActivityQuietly(new Intent(VideoPatchWallHeaderChild.this.c, VideoGroupListActivity.class));
                VideoStatHelper.recordVideoRecommendClick(VideoStatHelper.VideoVal.VIDEO_CATEGORY);
            }
        });
    }
}
