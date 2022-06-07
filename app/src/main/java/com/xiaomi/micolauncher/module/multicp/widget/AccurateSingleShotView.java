package com.xiaomi.micolauncher.module.multicp.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.track.EventType;
import com.xiaomi.micolauncher.common.track.VideoTrackHelper;
import com.xiaomi.micolauncher.common.utils.UiUtils;
import com.xiaomi.micolauncher.databinding.LayoutAccurateShotSingleCpBinding;
import com.xiaomi.micolauncher.module.multicp.adapter.ContentProviderAdapter;
import com.xiaomi.micolauncher.module.multicp.events.CollapseEvent;
import com.xiaomi.micolauncher.module.multicp.widget.ContentProviderBar;
import com.xiaomi.micolauncher.skills.video.model.VideoContentProvider;
import com.xiaomi.micolauncher.skills.video.model.VideoItem;
import com.xiaomi.micolauncher.skills.video.model.VideoProcessHelper;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class AccurateSingleShotView extends ConstraintLayout {
    private LayoutAccurateShotSingleCpBinding a;
    private ContentProviderAdapter b;
    private final List<VideoContentProvider> c;
    private VideoItem d;

    public AccurateSingleShotView(Context context) {
        this(context, null);
    }

    public AccurateSingleShotView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public AccurateSingleShotView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.c = new ArrayList();
        a(context);
    }

    private void a(final Context context) {
        this.a = (LayoutAccurateShotSingleCpBinding) DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.layout_accurate_shot_single_cp, this, true);
        if (this.b == null) {
            this.b = new ContentProviderAdapter(this.c);
            this.b.setOnCpClickedListener(new ContentProviderBar.OnCpClickedListener() { // from class: com.xiaomi.micolauncher.module.multicp.widget.-$$Lambda$AccurateSingleShotView$rlohJIA4C9yIJiM4OvrdLeE-B7Q
                @Override // com.xiaomi.micolauncher.module.multicp.widget.ContentProviderBar.OnCpClickedListener
                public final void onCpChanged(VideoContentProvider videoContentProvider) {
                    AccurateSingleShotView.this.a(context, videoContentProvider);
                }
            });
        }
        this.a.rvPlaySource.setLayoutManager(new LinearLayoutManager(context, 0, false));
        final int dimensionPixelOffset = context.getResources().getDimensionPixelOffset(R.dimen.mc21dp);
        this.a.rvPlaySource.addItemDecoration(new RecyclerView.ItemDecoration() { // from class: com.xiaomi.micolauncher.module.multicp.widget.AccurateSingleShotView.1
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(@NonNull Rect rect, @NonNull View view, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.State state) {
                super.getItemOffsets(rect, view, recyclerView, state);
                rect.right = dimensionPixelOffset;
            }
        });
        this.a.rvPlaySource.setAdapter(this.b);
        this.a.collapseView.setOnClickListener($$Lambda$AccurateSingleShotView$ic41PuZqS8BY3yyqtjf6Z4ka3Q8.INSTANCE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Context context, VideoContentProvider videoContentProvider) {
        this.d.setCp(videoContentProvider.name);
        this.d.setCpType(videoContentProvider.name);
        this.d.setVip(!videoContentProvider.isFree);
        this.d.setMediaId(videoContentProvider.cpId);
        this.d.setAudioId(videoContentProvider.audioId);
        this.a.vipMark.setVisibility(this.d.isVip() ? 0 : 8);
        L.multicp.d("AccurateSingleShotView setOnCpClickedListener videoItem is %s", Gsons.getGson().toJson(this.d));
        VideoProcessHelper.playVideo(context, this.d);
        EventBusRegistry.getEventBus().post(new CollapseEvent(true));
        a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(View view) {
        if (!UiUtils.isFastClick()) {
            EventBusRegistry.getEventBus().post(new CollapseEvent(true));
        }
    }

    private void a() {
        ThreadUtil.getComputationThreadPool().submit(new Runnable() { // from class: com.xiaomi.micolauncher.module.multicp.widget.-$$Lambda$AccurateSingleShotView$kKcux3EU-JFtqQe0EME7zECOaoQ
            @Override // java.lang.Runnable
            public final void run() {
                AccurateSingleShotView.this.b();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b() {
        VideoTrackHelper.postSearchResultTrack(EventType.CLICK, this.d, 0, "voice", null, VideoTrackHelper.PAGE_SUPERNATANT, VideoTrackHelper.FLOAT_SINGLE, "play");
    }

    public void updateData(@NonNull VideoItem videoItem) {
        this.d = videoItem;
        this.a.setData(videoItem);
        this.c.clear();
        this.c.addAll(videoItem.getCpList());
        this.b.notifyDataSetChanged();
    }

    @Override // android.view.View
    public boolean performClick() {
        return super.performClick();
    }
}
