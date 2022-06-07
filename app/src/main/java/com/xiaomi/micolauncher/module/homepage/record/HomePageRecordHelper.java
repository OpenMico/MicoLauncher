package com.xiaomi.micolauncher.module.homepage.record;

import com.xiaomi.micolauncher.api.model.MainPage;
import com.xiaomi.micolauncher.common.track.EventType;
import com.xiaomi.micolauncher.common.track.ExtendJsonWrapper;
import com.xiaomi.micolauncher.common.track.TrackConstant;
import com.xiaomi.micolauncher.common.track.TrackStat;
import com.xiaomi.micolauncher.common.track.TrackWidget;
import com.xiaomi.micolauncher.skills.video.model.VideoItem;

/* loaded from: classes3.dex */
public class HomePageRecordHelper {
    public static final String AREA_A = "A";
    public static final String AREA_B = "B";
    public static final String AREA_C = "C";
    public static final String AREA_D = "D";
    public static final String AREA_E = "E";
    public static final String AREA_F = "F";
    public static final String MEDIA_TYPE_MUSIC = "music";
    public static final String MEDIA_TYPE_MUSIC_MV = "music_mv";
    public static final String MEDIA_TYPE_NEWS = "news";
    public static final String MEDIA_TYPE_STATION = "station";

    public static void recordLongVideoShow(MainPage.LongVideo longVideo, String str, int i) {
        a(EventType.EXPOSE, Integer.toString(longVideo.getId()), longVideo.getCp(), longVideo.getCpId(), "longvideo", str, i);
    }

    public static void recordShortVideoShow(VideoItem videoItem, String str, int i) {
        a(EventType.EXPOSE, videoItem.getMetaId(), videoItem.getCp(), videoItem.getMetaId(), "shortvideo", str, i);
    }

    public static void recordLongVideoClick(MainPage.LongVideo longVideo, String str, int i) {
        a(EventType.CLICK, Integer.toString(longVideo.getId()), longVideo.getCp(), longVideo.getCpId(), "longvideo", str, i);
    }

    public static void recordShortVideoClick(VideoItem videoItem, String str, int i) {
        a(EventType.CLICK, videoItem.getMetaId(), videoItem.getCp(), videoItem.getMediaId(), "shortvideo", str, i);
    }

    private static void a(EventType eventType, String str, String str2, String str3, String str4, String str5, int i) {
        ExtendJsonWrapper obtain = ExtendJsonWrapper.obtain();
        obtain.setMetaId(str);
        obtain.setCpName(str2);
        obtain.setCpId(str3);
        obtain.setResourceId(str3);
        obtain.setFrom(TrackConstant.RECOMMEND);
        obtain.setMediaType(str4);
        obtain.setImpArea(str5);
        if (eventType == EventType.CLICK) {
            obtain.setClickType(TrackConstant.CLICK_TYPE_MANUAL);
        }
        obtain.setOffset(i);
        TrackStat.simpleCountEvent(TrackWidget.HOMEPAGE, eventType, obtain);
    }
}
