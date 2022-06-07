package com.xiaomi.micolauncher.skills.video.view;

import android.content.Context;
import android.graphics.Rect;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.elvishew.xlog.Logger;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.GlideUtils;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.track.EventType;
import com.xiaomi.micolauncher.common.track.TrackConstant;
import com.xiaomi.micolauncher.common.track.VideoTrackHelper;
import com.xiaomi.micolauncher.common.utils.UiUtils;
import com.xiaomi.micolauncher.module.child.childvideo.ChildVideoDataManager;
import com.xiaomi.micolauncher.module.child.util.ChildHelper;
import com.xiaomi.micolauncher.module.multicp.widget.ContentProviderBar;
import com.xiaomi.micolauncher.skills.video.model.VideoContentProvider;
import com.xiaomi.micolauncher.skills.video.model.VideoItem;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes3.dex */
public class RecommendationVideoAdapter extends RecyclerView.Adapter<a> {
    private List<VideoItem> a;
    private final OnItemClickListener b;
    private int c;
    private String d;

    /* loaded from: classes3.dex */
    public interface OnItemClickListener {
        void onItemClick(VideoItem videoItem);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public static class a extends RecyclerView.ViewHolder {
        View a;
        ImageView b;
        TextView c;
        TextView d;
        ImageView e;
        ContentProviderBar f;

        a(View view) {
            super(view);
            this.a = view.findViewById(R.id.layoutRecommendation);
            this.b = (ImageView) view.findViewById(R.id.imageViewIcon);
            this.c = (TextView) view.findViewById(R.id.textViewTitleVideoTitle);
            this.d = (TextView) view.findViewById(R.id.textViewTitleVideoIndex);
            this.e = (ImageView) view.findViewById(R.id.vip_mark);
            this.f = (ContentProviderBar) view.findViewById(R.id.widget_cps_bar);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public RecommendationVideoAdapter(Context context, List<VideoItem> list, OnItemClickListener onItemClickListener) {
        this.c = 4;
        if (list != null) {
            this.a = new ArrayList(list);
        }
        this.b = onItemClickListener;
        this.c = (int) context.getResources().getDimension(R.dimen.recommendation_video_radius);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NotNull
    public a onCreateViewHolder(@NotNull ViewGroup viewGroup, int i) {
        return new a(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.video_item_recommendation, viewGroup, false));
    }

    public void onBindViewHolder(@NotNull final a aVar, final int i) {
        final VideoItem videoItem = this.a.get(i);
        aVar.b.setImageDrawable(null);
        GlideUtils.bindImageView(aVar.b, videoItem.getRecommendVerticalImage(), aVar.b.getWidth(), aVar.b.getHeight());
        aVar.c.setText(videoItem.getTitle());
        if (TextUtils.isEmpty(videoItem.getRating())) {
            aVar.d.setVisibility(4);
        } else {
            aVar.d.setVisibility(0);
            aVar.d.setText(videoItem.getRating());
        }
        aVar.b.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.skills.video.view.-$$Lambda$RecommendationVideoAdapter$UBvSGoyaj1cv_cUh1FLmPUnXScc
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                RecommendationVideoAdapter.this.a(videoItem, i, view);
            }
        });
        a(aVar, videoItem);
        aVar.f.updateData(videoItem.getCpList());
        aVar.f.setOnCpClickedListener(new ContentProviderBar.OnCpClickedListener() { // from class: com.xiaomi.micolauncher.skills.video.view.-$$Lambda$RecommendationVideoAdapter$HnDMpyPqasebqA2Zgi75ZvVtn-4
            @Override // com.xiaomi.micolauncher.module.multicp.widget.ContentProviderBar.OnCpClickedListener
            public final void onCpChanged(VideoContentProvider videoContentProvider) {
                RecommendationVideoAdapter.this.a(videoItem, aVar, i, videoContentProvider);
            }
        });
        Logger logger = L.video;
        logger.d("onBindViewHolder, position:" + i);
        VideoTrackHelper.postSearchResultTrack(EventType.EXPOSE, videoItem, i, null, this.d, VideoTrackHelper.PAGE_COMMON, null, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(VideoItem videoItem, int i, View view) {
        if (this.b != null && !UiUtils.isFastClick()) {
            VideoTrackHelper.postSearchResultTrack(EventType.CLICK, videoItem, i, TrackConstant.CLICK_TYPE_MANUAL, this.d, VideoTrackHelper.PAGE_COMMON, null, "play");
            this.b.onItemClick(videoItem);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(VideoItem videoItem, @NotNull a aVar, int i, VideoContentProvider videoContentProvider) {
        videoItem.setCp(videoContentProvider.name);
        videoItem.setCpType(videoContentProvider.name);
        videoItem.setVip(!videoContentProvider.isFree);
        videoItem.setMediaId(videoContentProvider.cpId);
        videoItem.setAudioId(videoContentProvider.audioId);
        a(aVar, videoItem);
        OnItemClickListener onItemClickListener = this.b;
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(videoItem);
            VideoTrackHelper.postSearchResultTrack(EventType.CLICK, videoItem, i, TrackConstant.CLICK_TYPE_MANUAL, this.d, VideoTrackHelper.PAGE_COMMON, null, "play");
        }
    }

    private void a(@NotNull a aVar, VideoItem videoItem) {
        if (aVar.e == null) {
            return;
        }
        if (!videoItem.isMiTV() || !videoItem.isVip() || !ChildVideoDataManager.PCODE_ERTONG.equals(videoItem.getpCode())) {
            aVar.e.setVisibility(8);
            return;
        }
        aVar.e.setImageResource(ChildHelper.getVipRes(videoItem.getpCode()));
        aVar.e.setVisibility(0);
    }

    public void setRecommendationVideoItems(List<VideoItem> list) {
        this.a.clear();
        this.a.addAll(list);
        notifyDataSetChanged();
    }

    public void setCurrentTab(String str) {
        this.d = str;
    }

    public String getCurrentTab() {
        return this.d;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        List<VideoItem> list = this.a;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    /* loaded from: classes3.dex */
    public static class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private final int a;
        private final int b;

        /* JADX INFO: Access modifiers changed from: package-private */
        public SpacesItemDecoration(int i, int i2) {
            this.a = i;
            this.b = i2;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
        public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
            if (recyclerView.getChildAdapterPosition(view) == 0) {
                rect.right = 20;
                rect.left = 0;
            } else if (recyclerView.getChildAdapterPosition(view) == this.a - 1) {
                rect.left = 20;
                rect.right = 0;
            } else {
                rect.left = 20;
                rect.right = 20;
            }
        }
    }
}
