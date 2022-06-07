package com.xiaomi.micolauncher.module.video.childmode.bean;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.Station;
import com.xiaomi.micolauncher.module.main.FadeInCardView;
import com.xiaomi.micolauncher.module.music.utils.MusicStatHelper;
import com.xiaomi.micolauncher.skills.music.PlayerApi;
import com.xiaomi.micolauncher.skills.music.model.api.Music;
import com.xiaomi.micolauncher.skills.video.model.VideoItem;
import com.xiaomi.micolauncher.skills.video.model.VideoModel;
import com.xiaomi.micolauncher.skills.video.view.VideoDetailActivity;
import java.util.Collections;

/* loaded from: classes3.dex */
public class RecentPlayItem implements FadeInCardView.FadeInCardItem {
    public static final String ITEM_TYPE_CHILD_SONG = "childSong";
    public static final String ITEM_TYPE_CHILD_STORY = "childStory";
    private final String a;
    private final String b;
    private final int c;
    private String d;
    private int e;
    private ItemClickHandler f;

    /* loaded from: classes3.dex */
    public interface ItemClickHandler {
        void handleItemClick();
    }

    @Override // com.xiaomi.micolauncher.module.main.FadeInCardView.FadeInCardItem
    public String getCardAction() {
        return "";
    }

    public RecentPlayItem(final Context context, Music.Song song) {
        this.c = R.drawable.music;
        this.a = song.name;
        this.d = song.coverURL;
        this.b = ITEM_TYPE_CHILD_SONG;
        this.f = new ItemClickHandler() { // from class: com.xiaomi.micolauncher.module.video.childmode.bean.-$$Lambda$RecentPlayItem$C5q7Y849YNVW2vkDsCKyTeh6gLE
            @Override // com.xiaomi.micolauncher.module.video.childmode.bean.RecentPlayItem.ItemClickHandler
            public final void handleItemClick() {
                RecentPlayItem.a(context);
            }
        };
        this.e = R.drawable.music_title;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Context context) {
        PlayerApi.playRecently(context);
        MusicStatHelper.recordMusicClick(MusicStatHelper.MusicVal.MUSIC_RECENT);
    }

    public RecentPlayItem(final Station.Item item, final Context context) {
        this.c = R.drawable.story;
        this.d = item.getCover();
        this.a = item.getTitle();
        this.b = ITEM_TYPE_CHILD_STORY;
        this.f = new ItemClickHandler() { // from class: com.xiaomi.micolauncher.module.video.childmode.bean.-$$Lambda$RecentPlayItem$D-UOK3IO8rTPU8a6NXVNTmRdQOU
            @Override // com.xiaomi.micolauncher.module.video.childmode.bean.RecentPlayItem.ItemClickHandler
            public final void handleItemClick() {
                RecentPlayItem.a(Station.Item.this, context);
            }
        };
        this.e = R.drawable.story_title;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Station.Item item, Context context) {
        if (!ContainerUtil.isEmpty(item.getId())) {
            PlayerApi.playStation(context, item.getId(), item.getOrigin(), item.getTypeId(), true);
        }
    }

    public RecentPlayItem(final VideoItem videoItem, final Context context) {
        this.c = R.drawable.cartoon;
        this.a = context.getResources().getString(R.string.cartoon_title);
        this.b = "childCartoon";
        this.f = new ItemClickHandler() { // from class: com.xiaomi.micolauncher.module.video.childmode.bean.-$$Lambda$RecentPlayItem$e-PIZpdNNsU5CGwlBdV35U9MpSU
            @Override // com.xiaomi.micolauncher.module.video.childmode.bean.RecentPlayItem.ItemClickHandler
            public final void handleItemClick() {
                RecentPlayItem.a(VideoItem.this, context);
            }
        };
        this.e = R.drawable.cartoon_title;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(VideoItem videoItem, Context context) {
        if (videoItem != null && !TextUtils.isEmpty(videoItem.getMediaId())) {
            VideoModel.getInstance().setRecommendationList(Collections.singletonList(videoItem), null);
            Intent intent = new Intent(context, VideoDetailActivity.class);
            intent.putExtra(VideoModel.KEY_VIDEO_ITEM_ID, videoItem.getId());
            context.startActivity(intent);
        }
    }

    public RecentPlayItem(String str, String str2, int i, String str3, int i2, ItemClickHandler itemClickHandler) {
        this.a = str;
        this.b = str2;
        this.c = i;
        this.d = str3;
        this.e = i2;
        this.f = itemClickHandler;
    }

    @Override // com.xiaomi.micolauncher.module.main.FadeInCardView.FadeInCardItem
    public String getImgUrl() {
        return this.d;
    }

    @Override // com.xiaomi.micolauncher.module.main.FadeInCardView.FadeInCardItem
    public String getTitle() {
        return this.a;
    }

    @Override // com.xiaomi.micolauncher.module.main.FadeInCardView.FadeInCardItem
    public String getCardType() {
        return this.b;
    }

    @Override // com.xiaomi.micolauncher.module.main.FadeInCardView.FadeInCardItem
    public int getBgResId() {
        return this.c;
    }

    @Override // com.xiaomi.micolauncher.module.main.FadeInCardView.FadeInCardItem
    public int getTypeResId() {
        return this.e;
    }

    @Override // com.xiaomi.micolauncher.module.main.FadeInCardView.FadeInCardItem
    public void handleItemClick(Context context) {
        this.f.handleItemClick();
    }
}
