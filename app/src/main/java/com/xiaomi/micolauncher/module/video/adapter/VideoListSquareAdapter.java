package com.xiaomi.micolauncher.module.video.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.target.Target;
import com.xiaomi.mico.common.DisplayUtils;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.common.GlideUtils;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.track.EventType;
import com.xiaomi.micolauncher.common.track.VideoTrackHelper;
import com.xiaomi.micolauncher.module.music.bean.Footer;
import com.xiaomi.micolauncher.module.music.bean.Header;
import com.xiaomi.micolauncher.module.music.swiperefresh.BaseRecyclerViewHolder;
import com.xiaomi.micolauncher.module.music.view.MusicPatchWallFooter;
import com.xiaomi.micolauncher.module.music.view.MusicPatchWallTitle;
import com.xiaomi.micolauncher.skills.video.model.VideoItem;
import com.xiaomi.micolauncher.skills.video.model.VideoModel;
import com.xiaomi.micolauncher.skills.video.model.VideoProcessHelper;
import com.xiaomi.micolauncher.skills.video.view.VideoDetailActivity;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes3.dex */
public class VideoListSquareAdapter extends RecyclerView.Adapter<BaseRecyclerViewHolder> {
    private Context a;
    private CenterCrop b = new CenterCrop();
    private RoundedCorners c;
    private String d;
    private List<Object> e;
    private GridLayoutManager f;
    private boolean g;

    public VideoListSquareAdapter(Context context) {
        this.a = context;
        this.c = new RoundedCorners(DisplayUtils.dip2px(context, 16.0f));
    }

    public void setCategory(String str) {
        this.d = str;
    }

    public void setDataList(List<Object> list) {
        this.e = list;
        notifyDataSetChanged();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        List<Object> list = this.e;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() { // from class: com.xiaomi.micolauncher.module.video.adapter.VideoListSquareAdapter.1
                @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
                public int getSpanSize(int i) {
                    int itemViewType = VideoListSquareAdapter.this.getItemViewType(i);
                    if (itemViewType == 0 || itemViewType == 1) {
                        return gridLayoutManager.getSpanCount();
                    }
                    return 1;
                }
            });
            this.f = gridLayoutManager;
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        Object obj = this.e.get(i);
        if (obj == null) {
            return 2;
        }
        if (obj instanceof Header) {
            return 0;
        }
        return obj instanceof Footer ? 1 : 2;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public BaseRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        switch (i) {
            case 0:
                view = new MusicPatchWallTitle(this.a);
                break;
            case 1:
                view = new MusicPatchWallFooter(this.a);
                break;
            case 2:
                view = LayoutInflater.from(this.a).inflate(R.layout.view_video_square, viewGroup, false);
                break;
            default:
                view = null;
                break;
        }
        BaseRecyclerViewHolder baseRecyclerViewHolder = new BaseRecyclerViewHolder(view);
        baseRecyclerViewHolder.setViewHolderType(i);
        return baseRecyclerViewHolder;
    }

    public void onBindViewHolder(@NonNull BaseRecyclerViewHolder baseRecyclerViewHolder, final int i) {
        Object obj = this.e.get(i);
        if (obj != null) {
            int itemViewType = baseRecyclerViewHolder.getItemViewType();
            if (itemViewType == 0) {
                Header header = (Header) obj;
                this.d = header.title;
                ((MusicPatchWallTitle) baseRecyclerViewHolder.itemView).setupView(header.title);
            } else if (itemViewType != 2) {
                L.video.e("Unexpected view holder type");
            } else {
                final VideoItem videoItem = (VideoItem) obj;
                View view = baseRecyclerViewHolder.itemView;
                ImageView imageView = (ImageView) view.findViewById(R.id.image_iv);
                TextView textView = (TextView) view.findViewById(R.id.title_tv);
                String horizontalImageUrl = videoItem.getHorizontalImageUrl();
                if (TextUtils.isEmpty(horizontalImageUrl)) {
                    horizontalImageUrl = videoItem.getImageUrl();
                }
                GlideUtils.bindImageView(imageView, horizontalImageUrl, (int) R.drawable.video_card_view_default, new MultiTransformation(this.b, this.c), (Target<Bitmap>) null);
                textView.setText(videoItem.getTitle());
                view.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.video.adapter.-$$Lambda$VideoListSquareAdapter$M06MmTHeRyygRoiN4TtZJOna7lY
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view2) {
                        VideoListSquareAdapter.this.a(videoItem, i, view2);
                    }
                });
                if (this.g) {
                    VideoTrackHelper.postLongVideoTrack(EventType.EXPOSE, videoItem, i, this.d);
                }
            }
        }
    }

    public /* synthetic */ void a(VideoItem videoItem, int i, View view) {
        if (!TextUtils.isEmpty(videoItem.getMediaId())) {
            VideoModel.getInstance().setRecommendationList(Arrays.asList(videoItem), null);
            if (Hardware.isBigScreen()) {
                VideoProcessHelper.playVideo(this.a, videoItem);
            } else {
                Intent intent = new Intent(this.a, VideoDetailActivity.class);
                intent.putExtra(VideoModel.KEY_VIDEO_ITEM_ID, videoItem.getId());
                ActivityLifeCycleManager.startActivityQuietly(intent);
            }
            VideoTrackHelper.postLongVideoTrack(EventType.CLICK, videoItem, i, this.d);
        }
    }

    public void setUserVisibleHint(boolean z) {
        this.g = z;
    }

    private Object a(int i) {
        if (i >= getItemCount()) {
            return null;
        }
        return this.e.get(i);
    }

    public void notifyExpose() {
        GridLayoutManager gridLayoutManager = this.f;
        if (gridLayoutManager != null) {
            int findLastVisibleItemPosition = this.f.findLastVisibleItemPosition();
            for (int findFirstVisibleItemPosition = gridLayoutManager.findFirstVisibleItemPosition(); findFirstVisibleItemPosition < findLastVisibleItemPosition; findFirstVisibleItemPosition++) {
                VideoTrackHelper.postLongVideoTrack(EventType.EXPOSE, (VideoItem) a(findFirstVisibleItemPosition), findFirstVisibleItemPosition, this.d);
            }
        }
    }
}
