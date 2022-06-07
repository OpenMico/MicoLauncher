package com.xiaomi.micolauncher.module.multicp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.BaseRequestOptions;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.GlideUtils;
import com.xiaomi.micolauncher.common.track.EventType;
import com.xiaomi.micolauncher.common.track.VideoTrackHelper;
import com.xiaomi.micolauncher.common.utils.UiUtils;
import com.xiaomi.micolauncher.databinding.ItemAccurateShotMultiResultsCpsBinding;
import com.xiaomi.micolauncher.module.multicp.events.CollapseEvent;
import com.xiaomi.micolauncher.module.multicp.widget.ContentProviderBar;
import com.xiaomi.micolauncher.skills.video.model.VideoContentProvider;
import com.xiaomi.micolauncher.skills.video.model.VideoItem;
import com.xiaomi.micolauncher.skills.video.model.VideoProcessHelper;
import java.util.List;

/* loaded from: classes3.dex */
public class AccurateMultiShotAdapter extends RecyclerView.Adapter<a> {
    private final List<VideoItem> a;

    public AccurateMultiShotAdapter(@NonNull List<VideoItem> list) {
        this.a = list;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public a onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new a(ItemAccurateShotMultiResultsCpsBinding.inflate(LayoutInflater.from(viewGroup.getContext())));
    }

    public void onBindViewHolder(@NonNull final a aVar, int i) {
        final VideoItem a2 = a(i);
        if (a2 != null) {
            aVar.a.setItem(a2);
            aVar.a.contentProviderBar.updateData(a2.getCpList());
            aVar.a.getRoot().setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.multicp.adapter.-$$Lambda$AccurateMultiShotAdapter$ckgqX5v-EgoIJqAk4hsX5vEXg3o
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    AccurateMultiShotAdapter.this.a(a2, aVar, view);
                }
            });
            aVar.a.contentProviderBar.setOnCpClickedListener(new ContentProviderBar.OnCpClickedListener() { // from class: com.xiaomi.micolauncher.module.multicp.adapter.-$$Lambda$AccurateMultiShotAdapter$s0ao45LPYroTsWlT7FVQlsoE9I4
                @Override // com.xiaomi.micolauncher.module.multicp.widget.ContentProviderBar.OnCpClickedListener
                public final void onCpChanged(VideoContentProvider videoContentProvider) {
                    AccurateMultiShotAdapter.this.a(a2, aVar, videoContentProvider);
                }
            });
            a(aVar.getLayoutPosition(), a2, EventType.EXPOSE, (String) null);
        }
    }

    public /* synthetic */ void a(VideoItem videoItem, @NonNull a aVar, View view) {
        if (!UiUtils.isFastClick()) {
            if (!videoItem.isPlaying2()) {
                VideoProcessHelper.playVideo(view.getContext(), videoItem);
                EventBusRegistry.getEventBus().post(new CollapseEvent(true));
            }
            a(aVar.getLayoutPosition(), videoItem, EventType.CLICK, "play");
        }
    }

    public /* synthetic */ void a(VideoItem videoItem, @NonNull a aVar, VideoContentProvider videoContentProvider) {
        videoItem.setCp(videoContentProvider.name);
        videoItem.setCpType(videoContentProvider.name);
        videoItem.setVip(!videoContentProvider.isFree);
        videoItem.setMediaId(videoContentProvider.cpId);
        videoItem.setAudioId(videoContentProvider.audioId);
        aVar.a.vipMark.setVisibility(videoItem.isVip() ? 0 : 8);
        VideoProcessHelper.playVideo(aVar.a.getRoot().getContext(), videoItem);
        EventBusRegistry.getEventBus().post(new CollapseEvent(true));
        a(aVar.getLayoutPosition(), videoItem, EventType.CLICK, "play");
    }

    private void a(final int i, final VideoItem videoItem, final EventType eventType, final String str) {
        ThreadUtil.getComputationThreadPool().submit(new Runnable() { // from class: com.xiaomi.micolauncher.module.multicp.adapter.-$$Lambda$AccurateMultiShotAdapter$YF9UvxfTyzzBXb7s9YjPNQvewk8
            @Override // java.lang.Runnable
            public final void run() {
                VideoTrackHelper.postSearchResultTrack(EventType.this, videoItem, i, "voice", null, VideoTrackHelper.PAGE_SUPERNATANT, VideoTrackHelper.FLOAT_MULTI, str);
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.a.size();
    }

    private VideoItem a(int i) {
        if (!ContainerUtil.hasData(this.a) || i > this.a.size() - 1) {
            return null;
        }
        return this.a.get(i);
    }

    /* loaded from: classes3.dex */
    public static class a extends RecyclerView.ViewHolder {
        ItemAccurateShotMultiResultsCpsBinding a;

        public a(@NonNull ItemAccurateShotMultiResultsCpsBinding itemAccurateShotMultiResultsCpsBinding) {
            super(itemAccurateShotMultiResultsCpsBinding.getRoot());
            this.a = itemAccurateShotMultiResultsCpsBinding;
        }
    }

    @BindingAdapter(requireAll = false, value = {"coverUrl", "isSingleCp"})
    public static void bindCover(ImageView imageView, String str, boolean z) {
        Glide.with(imageView).load(str).apply((BaseRequestOptions<?>) GlideUtils.getDefaultRequestOptions().transform(new CenterCrop(), new RoundedCorners(25))).into(imageView);
    }
}
