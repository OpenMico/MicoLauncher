package com.xiaomi.micolauncher.common.track;

import android.text.TextUtils;
import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.Common;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.skills.video.model.VideoItem;
import com.xiaomi.micolauncher.skills.video.model.VideoMode;

/* loaded from: classes3.dex */
public class VideoTrackHelper {
    public static final String FLOAT_MULTI = "multi";
    public static final String FLOAT_SINGLE = "one";
    public static final String LENGTH_TYPE_LONG = "LONG";
    public static final String PAGE_COMMON = "common";
    public static final String PAGE_SUPERNATANT = "supernatant";
    public static final String STATUS_HAVE = "have";
    public static final String STATUS_NOT_HAVE = "not_have";
    public static final String SWITCH_TYPE_AUTO_SWITCH = "auto_switch";
    public static final String SWITCH_TYPE_DIRECT_PLAY = "direct_play";
    public static final String SWITCH_TYPE_FORWARD = "fast_forward";
    public static final String SWITCH_TYPE_LIST_SWITCH = "medialist_switch";
    public static final String SWITCH_TYPE_MAN_SWITCH = "man_switch";
    public static final String SWITCH_TYPE_PAUSE = "pause";
    public static final String SWITCH_TYPE_PLAY = "play";
    public static final String SWITCH_TYPE_REWIND = "fast_rewind";
    public String dialogId;
    public int duration;
    public String originId;
    public int position;
    public long startPlayTime;
    public VideoItem videoItem;
    public VideoMode videoMode;

    public void setVideoMode(VideoMode videoMode) {
        this.videoMode = videoMode;
    }

    public VideoTrackHelper(VideoMode videoMode) {
        this.videoMode = videoMode;
    }

    public void postTrack(String str) {
        ExtendJsonWrapper obtain = ExtendJsonWrapper.obtain();
        VideoItem videoItem = this.videoItem;
        if (videoItem != null) {
            obtain.setOriginId(videoItem.getOriginDialogIdForLog());
            String str2 = this.dialogId;
            if (str2 == null) {
                str2 = "";
            }
            obtain.setDialogId(str2);
            obtain.setDialogOrigin(c(this.videoItem));
            obtain.setCpName(this.videoItem.getCpForLog());
            obtain.setCpId(this.videoItem.getCpIdForLog());
            obtain.setLogExtension(this.videoItem.getLogForLog());
            obtain.setAudioId(this.videoItem.getAudioIdForLog());
            obtain.setMediaType(b(this.videoItem));
        }
        obtain.setSwitchType(str);
        obtain.setPlayStartTime(this.startPlayTime);
        obtain.setPlayEndTime(System.currentTimeMillis());
        obtain.setPosition(this.position);
        obtain.setDuration(this.duration);
        TrackStat.simpleCountEvent(TrackWidget.VIDEO_PLAY_PAGE, EventType.PLAY, obtain, false);
    }

    public static void postFloatingTrack(VideoItem videoItem, EventType eventType, int i) {
        if (videoItem != null) {
            ExtendJsonWrapper obtain = ExtendJsonWrapper.obtain();
            if (videoItem.originDialogId != null) {
                obtain.setDialogOrigin("voice");
                obtain.setDialogId(videoItem.originDialogId);
                obtain.setOriginId(videoItem.originDialogId);
            } else {
                obtain.setDialogOrigin("soundbox");
                obtain.setDialogId("");
                obtain.setOriginId("");
            }
            obtain.setCpName(videoItem.getCpForLog());
            obtain.setCpId(videoItem.getCpIdForLog());
            obtain.setLogExtension(videoItem.getLogForLog());
            obtain.setAudioId(videoItem.getAudioId());
            obtain.setMediaType(a(videoItem));
            obtain.setOffset(i);
            TrackStat.simpleCountEvent(TrackWidget.VIDEO_FLOATING_VIDEO, eventType, obtain);
        }
    }

    public static void postSearchResultTrack(EventType eventType, VideoItem videoItem, int i, String str, String str2, String str3, String str4, String str5) {
        if (videoItem != null) {
            ExtendJsonWrapper obtain = ExtendJsonWrapper.obtain();
            if (videoItem.originDialogId != null) {
                obtain.setDialogId(videoItem.originDialogId);
            }
            obtain.setCpName(videoItem.getCpForLog());
            obtain.setCpId(videoItem.getCpIdForLog());
            obtain.setLogExtension(videoItem.getLogForLog());
            obtain.setMediaType(a(videoItem));
            obtain.setOffset(i);
            obtain.setLogExtension(videoItem.getLogForLog());
            obtain.setAudioId(videoItem.getAudioId());
            if (str != null) {
                obtain.setClickType(str);
            }
            if (!TextUtils.isEmpty(str2)) {
                obtain.setTab(str2);
            }
            if (!TextUtils.isEmpty(str3)) {
                obtain.setPageType(str3);
            }
            if (!TextUtils.isEmpty(str4)) {
                obtain.setFloatType(str4);
            }
            if (!TextUtils.isEmpty(str5)) {
                obtain.setSwitchType("play");
            }
            obtain.setProduct(videoItem.isVip() ? STATUS_HAVE : STATUS_NOT_HAVE);
            TrackStat.simpleCountEvent(TrackWidget.SEARCH_RESULT_PAGE, eventType, obtain);
        }
    }

    public static void postLongVideoPlayTrack(VideoItem videoItem, int i, String str) {
        if (videoItem != null) {
            ExtendJsonWrapper obtain = ExtendJsonWrapper.obtain();
            obtain.setOriginId(videoItem.getOriginDialogIdForLog());
            obtain.setDialogId(videoItem.getOriginDialogIdForLog());
            obtain.setDialogOrigin(d(videoItem));
            obtain.setCpName(videoItem.getCpForLog());
            obtain.setCpId(videoItem.getCpIdForLog());
            obtain.setLogExtension(videoItem.getLogForLog());
            obtain.setAudioId(videoItem.getAudioId());
            obtain.setMediaType(a(videoItem));
            obtain.setSwitchType(str);
            obtain.setPlayStartTime(System.currentTimeMillis());
            obtain.setPosition(i);
            obtain.setDuration(videoItem.getDurationInMin());
            obtain.setProduct(videoItem.isVip() ? STATUS_HAVE : STATUS_NOT_HAVE);
            TrackStat.simpleCountEvent(TrackWidget.VIDEO_PLAY_PAGE, EventType.PLAY, obtain, false);
        }
    }

    public static void postLongVideoTrack(EventType eventType, VideoItem videoItem, int i, String str) {
        if (videoItem != null) {
            ExtendJsonWrapper obtain = ExtendJsonWrapper.obtain();
            obtain.setTab(str);
            obtain.setCpName(videoItem.getCp());
            obtain.setCpId(videoItem.getMediaId());
            obtain.setMediaType(TrackConstant.MEDIA_TYPE_LONG_VIDEO);
            obtain.setOffset(i);
            TrackStat.simpleCountEvent(TrackWidget.LONGVIDEO_RECOMMEND_PAGE, eventType, obtain);
        }
    }

    public void setOriginId(String str) {
        this.originId = str;
    }

    public void setDialogId(String str) {
        this.dialogId = str;
    }

    public void setStartPlayTime() {
        this.startPlayTime = System.currentTimeMillis();
    }

    public void setPosition(int i) {
        this.position = i;
    }

    public void setDuration(int i) {
        this.duration = i;
    }

    public void setVideoItem(VideoItem videoItem) {
        this.videoItem = videoItem;
    }

    private static String a(VideoItem videoItem) {
        if (videoItem == null || TextUtils.isEmpty(videoItem.videoMediaType)) {
            return null;
        }
        return videoItem.videoMediaType;
    }

    private String b(VideoItem videoItem) {
        if (videoItem != null && !TextUtils.isEmpty(videoItem.videoMediaType)) {
            return videoItem.videoMediaType;
        }
        if (this.videoMode == VideoMode.MITV_SERIAL || this.videoMode == VideoMode.SERIAL) {
            return Common.VideoMediaType.LONG_VIDEO.name().toLowerCase();
        }
        if (this.videoMode == VideoMode.MV || this.videoMode == VideoMode.RECOMMEND || this.videoMode == VideoMode.SHORT_VIDEO_RECOMMEND) {
            return Common.VideoMediaType.SHORT_VIDEO.name().toLowerCase();
        }
        return null;
    }

    private String c(VideoItem videoItem) {
        return videoItem.originDialogId != null ? "voice" : "soundbox";
    }

    private static String d(VideoItem videoItem) {
        return videoItem.originDialogId != null ? "voice" : "soundbox";
    }

    public void monitorLog(String str) {
        VideoItem videoItem = this.videoItem;
        if (videoItem != null) {
            String str2 = "";
            String str3 = "";
            if (videoItem != null) {
                str2 = videoItem.getVideoUrl();
                str3 = this.videoItem.getTitle();
            }
            L.monitor.i("dialog_id=%s,player_name=%s,action=%s,audio_id=%s,title=%s,play_url=%s", this.videoItem.originDialogId, AIApiConstants.VideoPlayer.NAME, str, "", str3, str2);
        }
    }
}
