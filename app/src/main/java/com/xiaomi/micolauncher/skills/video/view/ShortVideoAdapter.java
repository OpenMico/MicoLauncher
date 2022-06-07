package com.xiaomi.micolauncher.skills.video.view;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.GlideUtils;
import com.xiaomi.micolauncher.common.track.EventType;
import com.xiaomi.micolauncher.common.track.VideoTrackHelper;
import com.xiaomi.micolauncher.skills.video.VideoPlayerApi;
import com.xiaomi.micolauncher.skills.video.model.VideoItem;
import com.xiaomi.micolauncher.skills.video.model.VideoModel;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes3.dex */
public class ShortVideoAdapter extends RecyclerView.Adapter<a> {
    private LinearLayoutManager a;
    private List<VideoItem> b;

    public void update() {
        List<VideoItem> playList = VideoModel.getInstance().getPlayList();
        if (ContainerUtil.hasData(playList)) {
            this.b = new ArrayList(playList);
        }
        notifyDataSetChanged();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NotNull
    public a onCreateViewHolder(@NotNull ViewGroup viewGroup, int i) {
        return new a(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.short_video_item, viewGroup, false));
    }

    public void onBindViewHolder(@NotNull a aVar, int i) {
        List<VideoItem> list = this.b;
        if (list != null && i < list.size()) {
            aVar.a(this.b.get(i), i);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        List<VideoItem> list = this.b;
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            this.a = (LinearLayoutManager) layoutManager;
        }
    }

    private Object a(int i) {
        if (ContainerUtil.isOutOfBound(i, this.b)) {
            return null;
        }
        return this.b.get(i);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public static class a extends RecyclerView.ViewHolder {
        private TextView a;
        private TextView b;
        private ImageView c;
        private ImageView d;
        private ImageView e;
        private ImageView f;
        private View g;
        private int h;
        private Context i;
        private int j;
        private int k;
        private int l;
        private VideoItem m;

        a(View view) {
            super(view);
            this.i = view.getContext();
            this.a = (TextView) view.findViewById(R.id.video_title);
            this.b = (TextView) view.findViewById(R.id.video_title_down);
            this.c = (ImageView) view.findViewById(R.id.video_image);
            this.d = (ImageView) view.findViewById(R.id.video_playing);
            this.e = (ImageView) view.findViewById(R.id.video_title_down_shade);
            this.f = (ImageView) view.findViewById(R.id.video_vip);
            this.g = view.findViewById(R.id.item_root);
            this.g.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.skills.video.view.ShortVideoAdapter.a.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    a.this.a();
                }
            });
            this.j = (int) this.i.getResources().getDimension(R.dimen.size_20dp);
            this.k = (int) this.i.getResources().getDimension(R.dimen.size_160dp);
            this.l = (int) this.i.getResources().getDimension(R.dimen.size_102dp);
        }

        void a(VideoItem videoItem, int i) {
            this.h = i;
            this.m = videoItem;
            if (!TextUtils.isEmpty(videoItem.getHorizontalImage())) {
                GlideUtils.bindImageViewWithRoundCorners(this.i, videoItem.getHorizontalImage(), this.c, this.k, this.l, this.j, R.drawable.bg_short_video_item_black, R.drawable.bg_short_video_item_black);
                GlideUtils.bindImageView(this.i, (int) R.drawable.bg_short_video_item_selected, this.d);
                if (!"mitv".equals(videoItem.getCp()) || !TextUtils.isEmpty(videoItem.getTitle())) {
                    this.b.setText(videoItem.getTitle());
                } else {
                    this.b.setText(this.i.getString(R.string.child_video_ci, Integer.valueOf(videoItem.getCi())));
                }
                this.b.setVisibility(0);
                this.a.setVisibility(8);
                this.e.setVisibility(0);
            } else {
                this.a.setText(videoItem.getTitle());
                this.a.setVisibility(0);
                this.e.setVisibility(8);
                this.b.setVisibility(8);
                GlideUtils.bindImageView(this.i, (int) R.drawable.bg_short_video_item_black, this.c);
                GlideUtils.bindImageView(this.i, (int) R.drawable.bg_short_video_item_blue, this.d);
            }
            if (VideoModel.getInstance().isPlayingIndex(i)) {
                this.d.setVisibility(0);
            } else {
                this.d.setVisibility(8);
            }
            if (videoItem.isFee()) {
                this.f.setVisibility(0);
            } else {
                this.f.setVisibility(8);
            }
        }

        void a() {
            VideoPlayerApi.playByIndex(this.h);
            VideoTrackHelper.postFloatingTrack(this.m, EventType.CLICK, this.h);
        }
    }

    public void notifyExpose() {
        LinearLayoutManager linearLayoutManager = this.a;
        if (linearLayoutManager != null) {
            int findLastVisibleItemPosition = this.a.findLastVisibleItemPosition();
            for (int findFirstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition(); findFirstVisibleItemPosition < findLastVisibleItemPosition; findFirstVisibleItemPosition++) {
                VideoTrackHelper.postFloatingTrack((VideoItem) a(findFirstVisibleItemPosition), EventType.EXPOSE, findFirstVisibleItemPosition);
            }
        }
    }
}
