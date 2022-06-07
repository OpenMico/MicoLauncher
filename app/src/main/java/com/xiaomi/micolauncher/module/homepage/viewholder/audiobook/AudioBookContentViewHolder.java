package com.xiaomi.micolauncher.module.homepage.viewholder.audiobook;

import android.view.View;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.Station;
import com.xiaomi.micolauncher.common.track.EventType;
import com.xiaomi.micolauncher.common.track.TrackWidget;
import com.xiaomi.micolauncher.common.view.BaseViewHolder;
import com.xiaomi.micolauncher.module.homepage.AudiobookStatUtils;
import com.xiaomi.micolauncher.module.homepage.bean.AudiobookContent;
import com.xiaomi.micolauncher.module.homepage.bean.Order;
import com.xiaomi.micolauncher.module.homepage.view.AudioBookContentView;
import java.util.List;

/* loaded from: classes3.dex */
public class AudioBookContentViewHolder extends BaseViewHolder {
    AudioBookContentView a;
    private int b;
    private List<String> c;

    public AudioBookContentViewHolder(View view) {
        super(view);
        this.a = (AudioBookContentView) view.findViewById(R.id.contentview);
    }

    @Override // com.xiaomi.micolauncher.common.view.BaseViewHolder
    protected void initViewInMain() {
        AudioBookContentView audioBookContentView = this.a;
        if (audioBookContentView != null) {
            audioBookContentView.initInMain();
        }
    }

    public void setBlackList(List<String> list) {
        this.c = list;
    }

    public void bind(Station.Item item, TrackWidget trackWidget) {
        this.a.setBlackList(this.c);
        this.a.setData(item, trackWidget, this.b);
        if (trackWidget == TrackWidget.STATION_DISCOVER_CATEGORY_RECOMMEND) {
            AudiobookStatUtils.recordCategoryRecommend(item, this.b, EventType.EXPOSE);
        }
    }

    public void bind(AudiobookContent audiobookContent, TrackWidget trackWidget) {
        if (trackWidget != null) {
            this.a.setData(audiobookContent, trackWidget, this.b);
            if (trackWidget == TrackWidget.STATION_DISCOVER_CATEGORY_RECOMMEND) {
                AudiobookStatUtils.recordCategoryRecommend(audiobookContent, this.b, EventType.EXPOSE);
            }
            if (trackWidget == TrackWidget.STATION_DISCOVER_RECOMMEND) {
                AudiobookStatUtils.recordPromotionRecommend(audiobookContent, this.b, EventType.EXPOSE);
                return;
            }
            return;
        }
        this.a.setData(audiobookContent, trackWidget, this.b);
    }

    public void bind(Order.OrderInfo orderInfo) {
        this.a.setBlackList(this.c);
        this.a.setData(orderInfo, this.b);
    }

    public void setGroudResource(int i) {
        this.b = i;
        this.a.setResourceIndex(i);
    }
}
