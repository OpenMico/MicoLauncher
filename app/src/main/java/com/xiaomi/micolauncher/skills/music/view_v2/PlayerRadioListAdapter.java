package com.xiaomi.micolauncher.skills.music.view_v2;

import android.content.Context;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import com.airbnb.lottie.LottieAnimationView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xiaomi.ai.api.Template;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.GlideUtils;
import com.xiaomi.micolauncher.common.L;
import java.util.List;

/* loaded from: classes3.dex */
public class PlayerRadioListAdapter extends BaseQuickAdapter<Template.StationItem, BaseViewHolder> {
    int a;
    int b;
    private Context c;
    private int d;
    private boolean e;
    private int f;

    public PlayerRadioListAdapter(@Nullable List<Template.StationItem> list, Context context) {
        super(R.layout.item_audio_book_more_list, list);
        this.c = context;
        this.a = ContextCompat.getColor(context, R.color.music_lrc_highlight_color);
        this.b = ContextCompat.getColor(context, R.color.music_song_artist_color);
        this.f = context.getResources().getDimensionPixelSize(R.dimen.audio_book_list_icon_size);
    }

    public void setPlayStatus(boolean z) {
        this.e = z;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void convert(@NonNull BaseViewHolder baseViewHolder, Template.StationItem stationItem) {
        baseViewHolder.setText(R.id.audio_book_name, stationItem.getAlbumName());
        baseViewHolder.setText(R.id.audio_book_album, stationItem.getChineseName());
        baseViewHolder.setText(R.id.audio_book_type, stationItem.getCategory().get());
        baseViewHolder.setVisible(R.id.audio_book_num, stationItem.getEpisodeNum() > 0);
        baseViewHolder.setText(R.id.audio_book_num, stationItem.getEpisodeNum() + "集");
        baseViewHolder.setVisible(R.id.audio_book_vip, stationItem.isPayment());
        int i = this.f;
        GlideUtils.bindImageViewWithRoundUseContext(this.c, stationItem.getAlbumCover().get().getSources().get(0).getUrl(), (ImageView) baseViewHolder.getView(R.id.audio_book_cover), 21, R.drawable.player_book_empty, i, i);
        if (baseViewHolder.getAdapterPosition() == this.d) {
            L.player.i("stations isPlaying %b", Boolean.valueOf(this.e));
            baseViewHolder.setTextColor(R.id.audio_book_name, this.a);
            baseViewHolder.getView(R.id.playing).setVisibility(0);
            ((LottieAnimationView) baseViewHolder.getView(R.id.playing)).cancelAnimation();
            ((LottieAnimationView) baseViewHolder.getView(R.id.playing)).setRepeatCount(-1);
            ((LottieAnimationView) baseViewHolder.getView(R.id.playing)).setAnimation("playing/playing.json");
            if (this.e) {
                ((LottieAnimationView) baseViewHolder.getView(R.id.playing)).playAnimation();
            } else {
                ((LottieAnimationView) baseViewHolder.getView(R.id.playing)).pauseAnimation();
            }
            baseViewHolder.getView(R.id.mask).setVisibility(0);
            return;
        }
        baseViewHolder.setTextColor(R.id.audio_book_name, this.b);
        baseViewHolder.getView(R.id.playing).setVisibility(4);
        baseViewHolder.getView(R.id.mask).setVisibility(8);
    }

    public void setSelectPosition(int i) {
        int i2 = this.d;
        if (i2 != i) {
            notifyItemChanged(i2);
            this.d = i;
            notifyItemChanged(this.d);
        }
    }

    public int getSelectPosition() {
        return this.d;
    }
}
