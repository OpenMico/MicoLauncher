package com.xiaomi.micolauncher.module.video.childmode.bean;

import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.PatchWall;
import com.xiaomi.micolauncher.api.model.Station;
import com.xiaomi.micolauncher.common.schema.SchemaManager;
import com.xiaomi.micolauncher.module.main.FadeInCardView;
import com.xiaomi.micolauncher.skills.music.PlayerApi;
import com.xiaomi.micolauncher.skills.video.model.IqiyiModel;
import com.xiaomi.micolauncher.skills.video.model.RecommendCartoon;
import com.xiaomi.micolauncher.skills.video.model.ThirdPartyAppProxy;

/* loaded from: classes3.dex */
public class RecommendPlayItem implements FadeInCardView.FadeInCardItem {
    private String a;
    private String b;
    private String c;
    private String d;
    private int e;
    private int f;
    private ItemClickHandler g;

    /* loaded from: classes3.dex */
    public interface ItemClickHandler {
        void handleItemClick(Context context);
    }

    @Override // com.xiaomi.micolauncher.module.main.FadeInCardView.FadeInCardItem
    public String getCardAction() {
        return "";
    }

    public RecommendPlayItem(PatchWall.Item item) {
        this.e = R.drawable.music;
        this.b = item.getItemImageUrl();
        this.c = item.title;
        this.d = RecentPlayItem.ITEM_TYPE_CHILD_SONG;
        this.a = item.target;
        this.g = new ItemClickHandler() { // from class: com.xiaomi.micolauncher.module.video.childmode.bean.-$$Lambda$RecommendPlayItem$N2BpBTrsYKm4Z8KwIDHnxxfSvBc
            @Override // com.xiaomi.micolauncher.module.video.childmode.bean.RecommendPlayItem.ItemClickHandler
            public final void handleItemClick(Context context) {
                RecommendPlayItem.this.a(context);
            }
        };
        this.f = R.drawable.music_title;
    }

    public /* synthetic */ void a(Context context) {
        if (!TextUtils.isEmpty(this.a)) {
            SchemaManager.handleSchema(context, this.a);
        }
    }

    public RecommendPlayItem(Station.Item item) {
        this.e = R.drawable.story;
        this.b = item.getCover();
        this.c = item.getTitle();
        this.d = RecentPlayItem.ITEM_TYPE_CHILD_STORY;
        final String id = item.getId();
        final String origin = item.getOrigin();
        final int typeId = item.getTypeId();
        this.g = new ItemClickHandler() { // from class: com.xiaomi.micolauncher.module.video.childmode.bean.-$$Lambda$RecommendPlayItem$7wInsmUPK66rHv_-Wb8KqcoraZY
            @Override // com.xiaomi.micolauncher.module.video.childmode.bean.RecommendPlayItem.ItemClickHandler
            public final void handleItemClick(Context context) {
                RecommendPlayItem.a(id, origin, typeId, context);
            }
        };
        this.f = R.drawable.story_title;
    }

    public static /* synthetic */ void a(String str, String str2, int i, Context context) {
        if (!ContainerUtil.isEmpty(str)) {
            PlayerApi.playStation(context, str, str2, i);
        }
    }

    public RecommendPlayItem(final RecommendCartoon recommendCartoon) {
        this.e = R.drawable.cartoon;
        this.b = recommendCartoon.getImageUrl();
        this.c = recommendCartoon.title;
        this.d = "childCartoon";
        this.g = new ItemClickHandler() { // from class: com.xiaomi.micolauncher.module.video.childmode.bean.-$$Lambda$RecommendPlayItem$AHm6l2kQkDuGe3kzFkMQfAJHUrI
            @Override // com.xiaomi.micolauncher.module.video.childmode.bean.RecommendPlayItem.ItemClickHandler
            public final void handleItemClick(Context context) {
                RecommendPlayItem.a(RecommendCartoon.this, context);
            }
        };
        this.f = R.drawable.cartoon_title;
    }

    public static /* synthetic */ void a(RecommendCartoon recommendCartoon, Context context) {
        ThirdPartyAppProxy.getInstance().startVideo(context, "com.qiyi.video.speaker", IqiyiModel.getStartVideoIntentUri(recommendCartoon.action, true));
    }

    @Override // com.xiaomi.micolauncher.module.main.FadeInCardView.FadeInCardItem
    public void handleItemClick(Context context) {
        this.g.handleItemClick(context);
    }

    @Override // com.xiaomi.micolauncher.module.main.FadeInCardView.FadeInCardItem
    public String getImgUrl() {
        return this.b;
    }

    @Override // com.xiaomi.micolauncher.module.main.FadeInCardView.FadeInCardItem
    public String getTitle() {
        return this.c;
    }

    @Override // com.xiaomi.micolauncher.module.main.FadeInCardView.FadeInCardItem
    public String getCardType() {
        return this.d;
    }

    @Override // com.xiaomi.micolauncher.module.main.FadeInCardView.FadeInCardItem
    public int getBgResId() {
        return this.e;
    }

    @Override // com.xiaomi.micolauncher.module.main.FadeInCardView.FadeInCardItem
    public int getTypeResId() {
        return this.f;
    }

    public String getTarget() {
        return this.a;
    }
}
