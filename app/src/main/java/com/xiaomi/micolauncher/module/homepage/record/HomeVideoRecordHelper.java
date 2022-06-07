package com.xiaomi.micolauncher.module.homepage.record;

import com.xiaomi.micolauncher.Launcher;
import com.xiaomi.micolauncher.common.stat.StatUtils;
import com.xiaomi.micolauncher.common.track.EventType;
import com.xiaomi.micolauncher.common.track.ExtendJsonWrapper;
import com.xiaomi.micolauncher.common.track.TrackStat;
import com.xiaomi.micolauncher.common.track.TrackWidget;
import com.xiaomi.micolauncher.module.homepage.bean.VideoData;

/* loaded from: classes3.dex */
public class HomeVideoRecordHelper {
    public static final String KEY_VIDEO_PAGE_SHOW = "video_page_show";

    public static void recordHomeVideo(EventType eventType, String str, int i, int i2, VideoData videoData) {
        ExtendJsonWrapper obtain = ExtendJsonWrapper.obtain();
        obtain.setDialogOrigin("soundbox");
        obtain.setOffset(i2);
        obtain.setTab(str);
        obtain.setTabOffset(i);
        obtain.setAudioId(videoData.getAudioId());
        obtain.setCpName(videoData.getCp());
        obtain.setCpId(videoData.getCpId());
        obtain.setMediaType(videoData.getMediaType());
        TrackStat.simpleCountEvent(TrackWidget.HOMEPAGE_V2, eventType, obtain);
    }

    public static void recordHomeByMi(VideoData videoData, boolean z, String str) {
        if (z) {
            StatUtils.recordCountEvent(Launcher.KEY_ENTER_HOMEPAGE, KEY_VIDEO_PAGE_SHOW, "category", str);
        }
    }
}
