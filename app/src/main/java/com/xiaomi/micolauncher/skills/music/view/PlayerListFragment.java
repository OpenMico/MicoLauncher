package com.xiaomi.micolauncher.skills.music.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.elvishew.xlog.XLog;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.Remote;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.base.BaseFragment;
import com.xiaomi.micolauncher.common.widget.CustomDialog;
import com.xiaomi.micolauncher.common.widget.itemdecoration.ItemDecorationHelper;
import com.xiaomi.micolauncher.module.music.utils.MusicStatHelper;
import com.xiaomi.micolauncher.skills.music.OnPlayListItemClickListener;
import com.xiaomi.micolauncher.skills.music.PlayerApi;
import com.xiaomi.micolauncher.skills.music.controller.CollectionManager;
import com.xiaomi.micolauncher.skills.music.controller.LocalPlayerManager;
import com.xiaomi.micolauncher.skills.music.model.MusicHelper;
import com.xiaomi.micolauncher.skills.music.model.PlayerEvent;
import com.xiaomi.micolauncher.skills.music.view_v2.PlayerActivityV2;
import com.xiaomi.micolauncher.skills.music.vip.BookSinglePurchaseActivity;
import com.xiaomi.micolauncher.skills.music.vip.BookVipActivity;
import com.xiaomi.micolauncher.skills.music.vip.MusicVipActivity;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public class PlayerListFragment extends BaseFragment implements LocalPlayerManager.MetadataChangedCallback {
    RecyclerView a;
    private b b;
    private Remote.Response.PlayerStatus c;
    private CustomDialog d;
    private boolean e;

    @Override // com.xiaomi.micolauncher.skills.music.controller.LocalPlayerManager.MetadataChangedCallback
    public void onPlayerStateChanged(Remote.Response.PlayerStatus playerStatus) {
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public View onCreateView(@NonNull LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_player_list, (ViewGroup) null);
        this.a = (RecyclerView) inflate.findViewById(R.id.listview);
        this.b = new b();
        this.a.setAdapter(this.b);
        this.a.setLayoutManager(new LinearLayoutManager(getContext()));
        this.a.addItemDecoration(ItemDecorationHelper.listBottomDivider(getContext(), R.color.music_list_divider, 0, 0, null));
        if (!EventBusRegistry.getEventBus().isRegistered(this)) {
            EventBusRegistry.getEventBus().register(this);
        }
        LocalPlayerManager.getInstance().registerCallback(this);
        this.b.a(new OnPlayListItemClickListener() { // from class: com.xiaomi.micolauncher.skills.music.view.-$$Lambda$PlayerListFragment$wY_0Wi0wguFU9h1J5UTUokLsIbg
            @Override // com.xiaomi.micolauncher.skills.music.OnPlayListItemClickListener
            public final void onItemClick(Remote.Response.TrackData trackData, int i, boolean z) {
                PlayerListFragment.this.a(trackData, i, z);
            }
        });
        return inflate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Remote.Response.TrackData trackData, int i, boolean z) {
        if (trackData.isAlbumUnPay()) {
            showBookVipDialog(getContext(), trackData);
        } else if (trackData.isStationItemUnPay()) {
            showSingleBookVipDialog(getContext(), trackData, i);
        } else {
            if (trackData.isSongPreview()) {
                a(getContext());
            }
            if (!z) {
                PlayerApi.playByIndex(i);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseFragment
    public void onActivate() {
        super.onActivate();
        a(LocalPlayerManager.getInstance().getPlayerStatus());
        MusicStatHelper.recordPlayerShow(MusicStatHelper.PlayerPage.PLAYLIST);
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        a();
        EventBusRegistry.getEventBus().unregister(this);
        LocalPlayerManager.getInstance().unregisterCallback(this);
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.LocalPlayerManager.MetadataChangedCallback
    public void onMediaMetadataChanged(Remote.Response.PlayerStatus playerStatus) {
        XLog.d("PlayerListFragment onMediaMetadataChanged");
        if (this.mActivated) {
            a(playerStatus);
        }
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.LocalPlayerManager.MetadataChangedCallback
    public void onMediaListChanged(Remote.Response.PlayerStatus playerStatus) {
        if (this.mActivated) {
            a(playerStatus);
        }
    }

    private void a(Remote.Response.PlayerStatus playerStatus) {
        if (playerStatus != null) {
            if (playerStatus.extra_track_list != null) {
                int i = playerStatus.play_song_detail == null ? -1 : playerStatus.play_song_detail.screenExtend.index;
                this.b.a(playerStatus, i);
                if (this.b.getItemCount() > i) {
                    this.a.scrollToPosition(i);
                }
            }
            this.c = playerStatus;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static class b extends RecyclerView.Adapter<a> {
        private List<Remote.Response.TrackData> a;
        private int b;
        private int c;
        private long d;
        private OnPlayListItemClickListener e;

        private b() {
            this.a = new ArrayList();
            this.d = -1L;
        }

        @NonNull
        /* renamed from: a */
        public a onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            a aVar = new a(viewGroup.getContext(), LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.player_list_item, viewGroup, false));
            aVar.a(this.e);
            return aVar;
        }

        /* renamed from: a */
        public void onBindViewHolder(@NonNull a aVar, int i) {
            aVar.a(this.a.get(i), this.c, i == this.b, i);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            return this.a.size();
        }

        public void a(Remote.Response.PlayerStatus playerStatus, int i) {
            if (playerStatus.extra_track_list != null) {
                if (playerStatus.media_type != this.c || this.b != i || a(playerStatus.list_id) || playerStatus.extra_track_list == null || !ContainerUtil.equal(this.a, playerStatus.extra_track_list)) {
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

        private boolean a(long j) {
            return (this.d > 0 || j > 0) && this.d != j;
        }

        public void a(OnPlayListItemClickListener onPlayListItemClickListener) {
            this.e = onPlayListItemClickListener;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public static class a extends RecyclerView.ViewHolder {
        ImageView a;
        TextView b;
        TextView c;
        ImageView d;
        ImageView e;
        View f;
        private int g = -1;
        private int h;
        private int i;
        private boolean j;
        private Remote.Response.TrackData k;
        private Context l;
        private OnPlayListItemClickListener m;

        public a(Context context, View view) {
            super(view);
            this.l = context;
            this.a = (ImageView) view.findViewById(R.id.playing_indicator);
            this.b = (TextView) view.findViewById(R.id.song_name);
            this.c = (TextView) view.findViewById(R.id.artist);
            this.d = (ImageView) view.findViewById(R.id.music_mv_tag);
            this.e = (ImageView) view.findViewById(R.id.vip_tag);
            this.f = view.findViewById(R.id.station_lock);
            this.h = (int) context.getResources().getDimension(R.dimen.iconTextFont);
            this.i = (int) context.getResources().getDimension(R.dimen.minimalTextFont);
        }

        public void a(Remote.Response.TrackData trackData, int i, boolean z, int i2) {
            this.j = z;
            this.k = trackData;
            this.a.setVisibility(z ? 0 : 8);
            if (Hardware.isBigScreen()) {
                this.b.setText(a(trackData));
                this.c.setText(a(trackData, i));
                this.c.setSelected(z);
                this.b.setEllipsize(z ? TextUtils.TruncateAt.MARQUEE : TextUtils.TruncateAt.END);
                this.b.setSelected(z);
            } else {
                this.b.setText(a(trackData, i, z));
                this.b.setEllipsize(z ? TextUtils.TruncateAt.MARQUEE : TextUtils.TruncateAt.END);
                this.b.setSelected(z);
            }
            this.e.setVisibility(trackData.isVipSong() ? 0 : 8);
            this.f.setVisibility(trackData.stationIsLocked() ? 0 : 8);
            if (trackData.mvStream == null || !trackData.mvStream.isValid()) {
                this.d.setVisibility(8);
                this.d.setOnClickListener(null);
            } else {
                this.d.setVisibility(0);
                this.d.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.skills.music.view.PlayerListFragment.a.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        a.this.a();
                    }
                });
            }
            this.g = i2;
        }

        public void a(OnPlayListItemClickListener onPlayListItemClickListener) {
            this.m = onPlayListItemClickListener;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a() {
            Remote.Response.TrackData trackData = this.k;
            if (trackData != null && trackData.mvStream != null) {
                MusicHelper.playMV(this.l, this.k);
            }
        }

        private String a(Remote.Response.TrackData trackData, int i) {
            String listSubTitle = trackData.getListSubTitle(i);
            return TextUtils.isEmpty(listSubTitle) ? "" : listSubTitle;
        }

        private String a(Remote.Response.TrackData trackData) {
            return TextUtils.isEmpty(trackData.title) ? this.l.getString(R.string.music_tone) : trackData.title;
        }

        private CharSequence a(Remote.Response.TrackData trackData, int i, boolean z) {
            String a = a(trackData, i);
            if (TextUtils.isEmpty(trackData.title)) {
                return this.l.getString(R.string.music_tone);
            }
            String format = String.format("%s  %s", trackData.title, a);
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(format);
            spannableStringBuilder.setSpan(new ForegroundColorSpan(Color.parseColor((TextUtils.isEmpty(trackData.cpOrigin) || TextUtils.isEmpty(trackData.cpID)) ? "#80FFFFFF" : "#FFFFFFFF")), 0, trackData.title.length(), 33);
            spannableStringBuilder.setSpan(new AbsoluteSizeSpan(this.h), 0, trackData.title.length(), 33);
            spannableStringBuilder.setSpan(new ForegroundColorSpan(Color.parseColor(z ? "#FFFFFFFF" : "#80FFFFFF")), trackData.title.length() + 1, format.length(), 33);
            spannableStringBuilder.setSpan(new AbsoluteSizeSpan(this.i), trackData.title.length() + 1, format.length(), 33);
            return spannableStringBuilder;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnPlayIndexChange(PlayerEvent.OnPlayIndexChange onPlayIndexChange) {
        if (!ContainerUtil.isOutOfBound(onPlayIndexChange.index, this.c.extra_track_list)) {
            Remote.Response.TrackData trackData = this.c.extra_track_list.get(onPlayIndexChange.index);
            if (trackData.isAlbumUnPay()) {
                showBookVipDialog(getContext(), trackData);
            } else if (trackData.isStationItemUnPay()) {
                showSingleBookVipDialog(getContext(), trackData, onPlayIndexChange.index);
            } else if (!trackData.isSongPreview()) {
                a();
            }
        }
    }

    public void showBookVipDialog(final Context context, final Remote.Response.TrackData trackData) {
        if (!this.e) {
            this.d = new CustomDialog.Builder().setTitleResId(R.string.book_vip).setMessageResId(R.string.book_vip_tip).setPositiveResId(R.string.book_vip_ok).setNegativeResId(R.string.book_vip_no).setPositiveOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.skills.music.view.-$$Lambda$PlayerListFragment$LAdylGfDywYq9FQc0eg0-lrJU7I
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PlayerListFragment.this.a(context, trackData, view);
                }
            }).setCanceledOnTouchOutside(false).setNegativeOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.skills.music.view.-$$Lambda$PlayerListFragment$VMaiXabzHXafYmC6ZbYKcQ7gbSQ
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PlayerListFragment.this.c(view);
                }
            }).setHasNegativeButton(true).build();
            this.d.show();
            this.e = true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Context context, Remote.Response.TrackData trackData, View view) {
        this.e = false;
        Intent intent = new Intent(context, BookVipActivity.class);
        intent.putExtra(PlayerActivityV2.EXTRA_PLAYER_STATUS, Remote.Response.PlayingData.from(trackData));
        ActivityLifeCycleManager.startActivityQuietly(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void c(View view) {
        this.e = false;
    }

    public void showSingleBookVipDialog(final Context context, final Remote.Response.TrackData trackData, final int i) {
        if (!this.e) {
            this.d = new CustomDialog.Builder().setTitleResId(R.string.book_vip).setMessageResId(R.string.book_vip_tip).setPositiveResId(R.string.book_vip_ok).setNegativeResId(R.string.book_vip_no).setPositiveOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.skills.music.view.-$$Lambda$PlayerListFragment$2ir1jRd4VJuGAv38MxRcjMSvDdc
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PlayerListFragment.this.a(context, trackData, i, view);
                }
            }).setCanceledOnTouchOutside(false).setNegativeOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.skills.music.view.-$$Lambda$PlayerListFragment$nHkjX9IifqiBRtKqx6GinuCJ8Vg
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PlayerListFragment.this.b(view);
                }
            }).setHasNegativeButton(true).build();
            this.d.show();
            this.e = true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Context context, Remote.Response.TrackData trackData, int i, View view) {
        this.e = false;
        Intent intent = new Intent(context, BookSinglePurchaseActivity.class);
        intent.putExtra(BookSinglePurchaseActivity.EXTRA_BOOK_DATA, trackData);
        intent.putExtra(BookSinglePurchaseActivity.EXTRA_BOOK_INDEX, i);
        ActivityLifeCycleManager.startActivityQuietly(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(View view) {
        this.e = false;
    }

    private void a(final Context context) {
        if (!this.e) {
            L.pay.i("show vip dialog");
            this.d = new CustomDialog.Builder().setTitleResId(R.string.qq_vip).setMessageResId(R.string.qq_vip_tip).setPositiveResId(R.string.qq_vip_ok).setNegativeResId(R.string.qq_vip_no).setPositiveOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.skills.music.view.-$$Lambda$PlayerListFragment$ZscCstyu20ZnalhOwqw_ED5iN6s
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PlayerListFragment.this.a(context, view);
                }
            }).setNegativeOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.skills.music.view.-$$Lambda$PlayerListFragment$udZH0qX-Y0cHGvhINdZMxjukHIk
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PlayerListFragment.this.a(view);
                }
            }).setCanceledOnTouchOutside(false).setHasNegativeButton(true).build();
            this.d.show();
            this.e = true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Context context, View view) {
        this.e = false;
        ActivityLifeCycleManager.startActivityQuietly(new Intent(context, MusicVipActivity.class));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(View view) {
        this.e = false;
    }

    private void a() {
        if (this.d != null) {
            L.pay.i("dismiss vip dialog");
            this.d.dismiss();
            this.e = false;
        }
    }
}
