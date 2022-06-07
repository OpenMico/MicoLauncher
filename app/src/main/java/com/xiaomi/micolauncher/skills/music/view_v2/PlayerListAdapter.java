package com.xiaomi.micolauncher.skills.music.view_v2;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.Remote;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.stat.PlaybackTrackHelper;
import com.xiaomi.micolauncher.skills.music.OnPlayListItemClickListener;
import com.xiaomi.micolauncher.skills.music.controller.CollectionManager;
import com.xiaomi.micolauncher.skills.music.model.MusicHelper;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/* loaded from: classes3.dex */
public class PlayerListAdapter extends RecyclerView.Adapter<ListItemViewHolder> {
    private int b;
    private int c;
    private OnPlayListItemClickListener e;
    private HashSet<Integer> f;
    private List<Remote.Response.TrackData> a = new ArrayList();
    private long d = -1;

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public ListItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ListItemViewHolder listItemViewHolder = new ListItemViewHolder(viewGroup.getContext(), LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.player_list_item_v2, viewGroup, false));
        listItemViewHolder.setOnItemClickListener(this.e);
        return listItemViewHolder;
    }

    public void onBindViewHolder(@NonNull ListItemViewHolder listItemViewHolder, int i) {
        listItemViewHolder.refresh(this.a.get(i), this.c, i == this.b, listItemViewHolder.getLayoutPosition());
        a(this.a.get(i), i);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.a.size();
    }

    public void refresh(Remote.Response.PlayerStatus playerStatus, int i) {
        if (playerStatus.extra_track_list != null) {
            boolean z = false;
            L.player.d("PlayerListAdapter refresh currentIndex=%d screenExtendIndex=%d", Integer.valueOf(this.b), Integer.valueOf(i));
            if (playerStatus.media_type != this.c || this.b != i || a(playerStatus.list_id) || playerStatus.extra_track_list == null || !ContainerUtil.equal(this.a, playerStatus.extra_track_list)) {
                z = true;
            }
            if (z) {
                this.c = playerStatus.media_type;
                this.d = playerStatus.list_id;
                this.a.clear();
                this.a.addAll(playerStatus.extra_track_list);
                this.b = i;
                notifyDataSetChanged();
                CollectionManager.getInstance().loadPlayListLoveStatus(playerStatus);
            }
        }
    }

    public void setOnItemClickListener(OnPlayListItemClickListener onPlayListItemClickListener) {
        this.e = onPlayListItemClickListener;
    }

    private boolean a(long j) {
        return (this.d > 0 || j > 0) && this.d != j;
    }

    /* loaded from: classes3.dex */
    public static class ListItemViewHolder extends RecyclerView.ViewHolder {
        TextView a;
        TextView b;
        View c;
        View d;
        View e;
        private int f = -1;
        private boolean g;
        private Remote.Response.TrackData h;
        private Context i;
        private OnPlayListItemClickListener j;

        public ListItemViewHolder(Context context, View view) {
            super(view);
            this.i = context;
            View findViewById = view.findViewById(R.id.item);
            this.a = (TextView) findViewById.findViewById(R.id.song_name);
            this.b = (TextView) findViewById.findViewById(R.id.artist);
            this.c = findViewById.findViewById(R.id.music_mv_tag);
            this.d = findViewById.findViewById(R.id.vip_tag);
            this.e = findViewById.findViewById(R.id.station_lock);
            findViewById.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.skills.music.view_v2.PlayerListAdapter.ListItemViewHolder.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    ListItemViewHolder.this.a();
                }
            });
        }

        public void refresh(Remote.Response.TrackData trackData, int i, boolean z, int i2) {
            this.g = z;
            this.h = trackData;
            this.a.setText(a(trackData));
            this.b.setText(a(trackData, i));
            this.b.setSelected(z);
            this.a.setEllipsize(z ? TextUtils.TruncateAt.MARQUEE : TextUtils.TruncateAt.END);
            this.a.setSelected(z);
            this.a.setTextColor(z ? ContextCompat.getColor(this.i, R.color.music_lrc_highlight_color) : ContextCompat.getColor(this.i, R.color.music_song_name_color));
            this.d.setVisibility(trackData.isVipSong() ? 0 : 8);
            this.e.setVisibility(trackData.stationIsLocked() ? 0 : 8);
            if (trackData.mvStream == null || !trackData.mvStream.isValid()) {
                this.c.setVisibility(8);
                this.c.setOnClickListener(null);
            } else {
                this.c.setVisibility(0);
                this.c.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.skills.music.view_v2.PlayerListAdapter.ListItemViewHolder.2
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        ListItemViewHolder.this.b();
                    }
                });
            }
            if (!MusicHelper.isPlayingSong(i)) {
                this.a.setMaxWidth(this.i.getResources().getDimensionPixelSize(R.dimen.player_v2_radio_list_max_width));
            } else if ((this.d.getVisibility() == 0 && this.c.getVisibility() == 8) || (this.d.getVisibility() == 8 && this.c.getVisibility() == 0)) {
                this.a.setMaxWidth(this.i.getResources().getDimensionPixelSize(R.dimen.player_v2_list_song_max_width_1));
            } else if (this.d.getVisibility() == 0 && this.c.getVisibility() == 0) {
                this.a.setMaxWidth(this.i.getResources().getDimensionPixelSize(R.dimen.player_v2_list_song_max_width_2));
            } else {
                this.a.setMaxWidth(this.i.getResources().getDimensionPixelSize(R.dimen.player_v2_list_song_max_width));
            }
            this.f = i2;
        }

        public void setOnItemClickListener(OnPlayListItemClickListener onPlayListItemClickListener) {
            this.j = onPlayListItemClickListener;
        }

        void a() {
            OnPlayListItemClickListener onPlayListItemClickListener = this.j;
            if (onPlayListItemClickListener != null) {
                onPlayListItemClickListener.onItemClick(this.h, this.f, this.g);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b() {
            Remote.Response.TrackData trackData = this.h;
            if (trackData != null && trackData.mvStream != null) {
                MusicHelper.playMV(this.i, this.h);
            }
        }

        private String a(Remote.Response.TrackData trackData, int i) {
            String listSubTitle = trackData.getListSubTitle(i);
            return TextUtils.isEmpty(listSubTitle) ? "" : listSubTitle;
        }

        private String a(Remote.Response.TrackData trackData) {
            return TextUtils.isEmpty(trackData.title) ? this.i.getString(R.string.music_tone) : trackData.title;
        }
    }

    private void a(Remote.Response.TrackData trackData, int i) {
        if (trackData.originId != null && !a(i)) {
            PlaybackTrackHelper.postPlayListExpose(trackData, i);
            b(i);
        }
    }

    private boolean a(int i) {
        HashSet<Integer> hashSet = this.f;
        if (hashSet != null) {
            return hashSet.contains(Integer.valueOf(i));
        }
        return false;
    }

    private void b(int i) {
        if (this.f == null) {
            this.f = new HashSet<>();
        }
        this.f.add(Integer.valueOf(i));
    }
}
