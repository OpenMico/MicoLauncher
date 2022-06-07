package com.xiaomi.micolauncher.module.homepage;

import com.xiaomi.micolauncher.api.model.Station;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.track.EventType;
import com.xiaomi.micolauncher.common.track.ExtendJsonWrapper;
import com.xiaomi.micolauncher.common.track.TrackConstant;
import com.xiaomi.micolauncher.common.track.TrackStat;
import com.xiaomi.micolauncher.common.track.TrackWidget;
import com.xiaomi.micolauncher.module.homepage.bean.AudiobookContent;

/* loaded from: classes3.dex */
public class AudiobookStatUtils {
    public static void recordPromotion1(AudiobookContent audiobookContent, EventType eventType) {
        L.homepage.i("AudiobookStatUtils有声书1区 ： book : %s , %s", audiobookContent.getTitle(), eventType.name());
        a(audiobookContent.getId(), audiobookContent.getCpAlbumId(), -1, TrackWidget.STATION_DISCOVER_PROMOTION_1, eventType);
    }

    public static void recordPromotion2(AudiobookContent audiobookContent, int i, EventType eventType) {
        L.homepage.i("AudiobookStatUtils有声书2区 ： book : %s , %s ,position : %d", audiobookContent.getTitle(), eventType.name(), Integer.valueOf(i));
        a(audiobookContent.getId(), audiobookContent.getCpAlbumId(), i, TrackWidget.STATION_DISCOVER_PROMOTION_2, eventType);
    }

    public static void recordPromotionRecommend(AudiobookContent audiobookContent, int i, EventType eventType) {
        L.homepage.i("AudiobookStatUtils有声书推荐区 ： book : %s , %s,position : %d", audiobookContent.getTitle(), eventType.name(), Integer.valueOf(i));
        a(audiobookContent.getId(), audiobookContent.getCpAlbumId(), i, TrackWidget.STATION_DISCOVER_RECOMMEND, eventType);
    }

    public static void recordCategoryRecommend(Station.Item item, int i, EventType eventType) {
        L.homepage.i("AudiobookStatUtils有声书分类 ： item : %s , %s", item.getTitle(), eventType.name());
        a(item.getId(), item.getAlbumId(), i, TrackWidget.STATION_DISCOVER_CATEGORY_RECOMMEND, eventType);
    }

    public static void recordCategoryRecommend(AudiobookContent audiobookContent, int i, EventType eventType) {
        L.homepage.i("AudiobookStatUtils有声书分类 ： book : %s , %s", audiobookContent.getTitle(), eventType.name());
        a(audiobookContent.getId(), audiobookContent.getCpAlbumId(), i, TrackWidget.STATION_DISCOVER_CATEGORY_RECOMMEND, eventType);
    }

    public static void recordFunctionModule(EventType eventType, String str) {
        L.homepage.i("AudiobookStatUtils有声书发现页 event type : %s,elementId : %s", eventType.name(), str);
        ExtendJsonWrapper obtain = ExtendJsonWrapper.obtain();
        obtain.setElementId(str);
        TrackStat.simpleCountEvent(TrackWidget.STATION_DISCOVER_FUNCTION_MODULE, eventType, obtain);
    }

    private static void a(String str, String str2, int i, TrackWidget trackWidget, EventType eventType) {
        ExtendJsonWrapper obtain = ExtendJsonWrapper.obtain();
        obtain.setResourceId(str2);
        if (i >= 0) {
            obtain.setOffset(i);
        }
        L.homepage.i("AudiobookStatUtils有声书 ： metaId : %s , resourceId : %s , position : %d", str, str2, Integer.valueOf(i));
        if (eventType == EventType.CLICK) {
            obtain.setClickType(TrackConstant.CLICK_TYPE_MANUAL);
        }
        TrackStat.simpleCountEvent(trackWidget, eventType, obtain);
    }
}
