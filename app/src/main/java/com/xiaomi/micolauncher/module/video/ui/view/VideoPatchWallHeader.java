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
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.common.view.base.AvoidFastDoubleClickViewOnClickListener;
import com.xiaomi.micolauncher.module.video.manager.VideoDataManager;
import com.xiaomi.micolauncher.module.video.manager.VideoStatHelper;
import com.xiaomi.micolauncher.module.video.ui.VideoCategoryListActivity;
import com.xiaomi.micolauncher.module.video.ui.VideoGroupListActivity;
import com.xiaomi.micolauncher.module.video.ui.VideoRecentListActivity;
import com.xiaomi.micolauncher.module.video.ui.view.VideoPatchWallHeader;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

/* loaded from: classes3.dex */
public class VideoPatchWallHeader extends LinearLayout {
    ImageView a;
    ImageView b;
    ImageView c;
    ImageView d;
    private Context e;

    public VideoPatchWallHeader(Context context) {
        this(context, null);
    }

    public VideoPatchWallHeader(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public VideoPatchWallHeader(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.e = context;
        a();
    }

    private void a() {
        View.inflate(this.e, R.layout.view_video_patch_wall_header, this);
        this.a = (ImageView) findViewById(R.id.tv_list);
        this.b = (ImageView) findViewById(R.id.movie_list);
        this.c = (ImageView) findViewById(R.id.recent_list);
        this.d = (ImageView) findViewById(R.id.category_list);
        b();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaomi.micolauncher.module.video.ui.view.VideoPatchWallHeader$1  reason: invalid class name */
    /* loaded from: classes3.dex */
    public class AnonymousClass1 extends AvoidFastDoubleClickViewOnClickListener {
        AnonymousClass1() {
        }

        @Override // com.xiaomi.micolauncher.common.view.base.AvoidFastDoubleClickViewOnClickListener
        public void onAvoidFastDoubleClick(View view) {
            final String string = VideoPatchWallHeader.this.e.getString(R.string.video_category_type_tv);
            VideoDataManager.getManager().loadCategoryIdByName(string).subscribeOn(MicoSchedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.video.ui.view.-$$Lambda$VideoPatchWallHeader$1$Y4q0BlRA_5Bh3ZYN_OQk1FNH6VY
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    VideoPatchWallHeader.AnonymousClass1.this.a(string, (Long) obj);
                }
            });
            VideoStatHelper.recordVideoRecommendClick(VideoStatHelper.VideoVal.VIDEO_TELEPLAY);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void a(String str, Long l) throws Exception {
            VideoCategoryListActivity.openVideoCategoryListActivity(VideoPatchWallHeader.this.e, l.longValue(), str, str);
        }
    }

    private void b() {
        this.a.setOnClickListener(new AnonymousClass1());
        this.b.setOnClickListener(new AnonymousClass2());
        this.c.setOnClickListener(new AvoidFastDoubleClickViewOnClickListener() { // from class: com.xiaomi.micolauncher.module.video.ui.view.VideoPatchWallHeader.3
            @Override // com.xiaomi.micolauncher.common.view.base.AvoidFastDoubleClickViewOnClickListener
            public void onAvoidFastDoubleClick(View view) {
                ActivityLifeCycleManager.startActivityQuietly(new Intent(VideoPatchWallHeader.this.e, VideoRecentListActivity.class));
                VideoStatHelper.recordVideoRecommendClick(VideoStatHelper.VideoVal.VIDEO_RECENT);
            }
        });
        this.d.setOnClickListener(new AvoidFastDoubleClickViewOnClickListener() { // from class: com.xiaomi.micolauncher.module.video.ui.view.VideoPatchWallHeader.4
            @Override // com.xiaomi.micolauncher.common.view.base.AvoidFastDoubleClickViewOnClickListener
            public void onAvoidFastDoubleClick(View view) {
                ActivityLifeCycleManager.startActivityQuietly(new Intent(VideoPatchWallHeader.this.e, VideoGroupListActivity.class));
                VideoStatHelper.recordVideoRecommendClick(VideoStatHelper.VideoVal.VIDEO_CATEGORY);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaomi.micolauncher.module.video.ui.view.VideoPatchWallHeader$2  reason: invalid class name */
    /* loaded from: classes3.dex */
    public class AnonymousClass2 extends AvoidFastDoubleClickViewOnClickListener {
        AnonymousClass2() {
        }

        @Override // com.xiaomi.micolauncher.common.view.base.AvoidFastDoubleClickViewOnClickListener
        public void onAvoidFastDoubleClick(View view) {
            final String string = VideoPatchWallHeader.this.e.getString(R.string.video_category_type_film);
            VideoDataManager.getManager().loadCategoryIdByName(string).subscribeOn(MicoSchedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.video.ui.view.-$$Lambda$VideoPatchWallHeader$2$c33Cm0sXUoGKaEMbg5XCWoVdXFM
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    VideoPatchWallHeader.AnonymousClass2.this.a(string, (Long) obj);
                }
            });
            VideoStatHelper.recordVideoRecommendClick(VideoStatHelper.VideoVal.VIDEO_MOVIE);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void a(String str, Long l) throws Exception {
            VideoCategoryListActivity.openVideoCategoryListActivity(VideoPatchWallHeader.this.e, l.longValue(), str, str);
        }
    }
}
