package com.xiaomi.micolauncher.module.video.manager;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.ArrayMap;
import androidx.annotation.VisibleForTesting;
import com.elvishew.xlog.Logger;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.stat.StatUtils;
import com.xiaomi.micolauncher.common.track.EventType;
import com.xiaomi.micolauncher.common.track.ExtendJsonWrapper;
import com.xiaomi.micolauncher.common.track.TrackConstant;
import com.xiaomi.micolauncher.common.track.TrackStat;
import com.xiaomi.micolauncher.common.track.TrackWidget;
import com.xiaomi.micolauncher.module.video.childmode.ChildModeManager;
import com.xiaomi.micolauncher.skills.video.model.VideoItem;
import com.xiaomi.micolauncher.skills.video.model.VideoModel;
import com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.GKidAppCommandProcessor;
import com.xiaomi.micolauncher.skills.video.model.videocommandprocessing.MangguoAppCommandProcessor;
import java.util.Arrays;
import java.util.Map;

/* loaded from: classes3.dex */
public class VideoStatHelper {
    private static final String CATEGORY = "video";
    private static final Map<String, VideoVal> PACKAGE_TO_VIDEO_VAL = new ArrayMap();
    public static final String PARAM_KEY_CATEGORY = "category";
    public static final String PARAM_KEY_CHILD_MODE = "childMode";

    /* loaded from: classes3.dex */
    public enum VideoKey {
        VIDEO_RECOMMEND_CLICK,
        VIDEO_SEARCH_SHOW,
        VIDEO_SEARCH_CLICK
    }

    /* loaded from: classes3.dex */
    public enum VideoVal {
        VIDEO_TELEPLAY,
        VIDEO_MOVIE,
        VIDEO_RECENT,
        VIDEO_CATEGORY,
        PLAY,
        EPISODE,
        IQIYI,
        TENCENT_VIDEO,
        YOUKU,
        BILIBILI,
        MANGGUO,
        YY,
        TIKTOK,
        GKID,
        MV,
        NEWS,
        BAIKE,
        SHORT_VIDEO,
        OTHER,
        MICO
    }

    static {
        PACKAGE_TO_VIDEO_VAL.put("tv.danmaku.bili", VideoVal.BILIBILI);
        PACKAGE_TO_VIDEO_VAL.put("com.qiyi.video.speaker", VideoVal.IQIYI);
        PACKAGE_TO_VIDEO_VAL.put(GKidAppCommandProcessor.PACKAGE_NAME_GKID, VideoVal.GKID);
        PACKAGE_TO_VIDEO_VAL.put(MangguoAppCommandProcessor.PACKAGE_NAME_MANGGUO, VideoVal.MANGGUO);
        PACKAGE_TO_VIDEO_VAL.put("com.ss.android.ugc.aweme", VideoVal.TIKTOK);
        PACKAGE_TO_VIDEO_VAL.put("com.youku.iot", VideoVal.YOUKU);
        PACKAGE_TO_VIDEO_VAL.put("com.duowan.yytv", VideoVal.YY);
        PACKAGE_TO_VIDEO_VAL.put("com.tencent.qqlive.audiobox", VideoVal.TENCENT_VIDEO);
        PACKAGE_TO_VIDEO_VAL.put("com.xiaomi.micolauncher", VideoVal.MICO);
    }

    public static VideoVal packageNameToVideoVal(String str) {
        return PACKAGE_TO_VIDEO_VAL.get(str);
    }

    public static StatisAppKey getStatisAppKeyOfPackage(String str) {
        return new StatisAppKey(str);
    }

    private static String getVideoKey(VideoKey videoKey) {
        return videoKey.name().toLowerCase();
    }

    @VisibleForTesting
    public static String getVideoVal(VideoVal videoVal) {
        return videoVal.name().toLowerCase();
    }

    public static void recordVideoRecommendClick(VideoVal videoVal) {
        recordWithCommonArgs(VideoKey.VIDEO_RECOMMEND_CLICK, "category", getVideoVal(videoVal));
    }

    public static void recordVideoRecommendClick(String str) {
        if (ContainerUtil.hasData(str)) {
            recordWithCommonArgs(VideoKey.VIDEO_RECOMMEND_CLICK, "category", str);
        }
    }

    public static void recordVideoSearchClick() {
        recordWithCommonArgs(VideoKey.VIDEO_SEARCH_CLICK, new String[0]);
    }

    public static void recordVideoPageShow(VideoKey videoKey) {
        recordWithCommonArgs(videoKey, new String[0]);
    }

    private static void recordWithCommonArgs(VideoKey videoKey, String... strArr) {
        L.statistics.d("record count event %s, %s", videoKey, Arrays.asList(strArr));
        ArrayMap arrayMap = new ArrayMap();
        if (ContainerUtil.getSize(strArr) % 2 == 0) {
            int length = strArr.length;
            for (int i = 0; i < length; i += 2) {
                arrayMap.put(strArr[i], strArr[i + 1]);
            }
        }
        arrayMap.put(PARAM_KEY_CHILD_MODE, String.valueOf(ChildModeManager.getManager().isChildMode()));
        StatUtils.recordCountEvent("video", getVideoKey(videoKey), arrayMap);
        L.statistics.v("record with common args %s", videoKey);
    }

    public static void postExposeTrack(VideoItem videoItem, EventType eventType) {
        ExtendJsonWrapper obtain = ExtendJsonWrapper.obtain();
        if (videoItem != null) {
            obtain.setOriginId(videoItem.originDialogId);
            obtain.setDialogId(videoItem.originDialogId);
            obtain.setCategory(videoItem.getCategory());
            obtain.setCpId(videoItem.getCpIdForLog());
            obtain.setCpName(videoItem.getCpForLog());
            obtain.setAudioId(videoItem.getAudioIdForLog());
            if (videoItem.originDialogId != null) {
                obtain.setDialogOrigin("voice");
            } else {
                obtain.setDialogOrigin("soundbox");
            }
            obtain.setLogExtension(videoItem.getLogForLog());
        }
        if (VideoModel.getInstance().isLongVideo(videoItem)) {
            obtain.setMediaType(TrackConstant.MEDIA_TYPE_LONG_VIDEO);
        } else {
            obtain.setMediaType(videoItem.getMediaTypeForLog());
        }
        TrackStat.simpleCountEvent(TrackWidget.VIDEO_PLAY_PAGE, eventType, obtain);
    }

    public static void postExposeTrack(Context context, String str, EventType eventType) {
        ExtendJsonWrapper.obtain().setDialogId(VideoModel.getInstance().getRequestId());
        VideoItem videoItem = new VideoItem();
        videoItem.setCp(getStatisAppKeyOfPackage(str).getKeyForStatis(context));
        postExposeTrack(videoItem, eventType);
    }

    /* loaded from: classes3.dex */
    public static class StatisAppKey {
        private final String packageName;
        private static final ArrayMap<String, StatisAppKey> PACKAGE_NAME_TO_STATIS_APP_KEY = new ArrayMap<>(12);
        private static final ArrayMap<String, String> PACKAGE_NAME_TO_KEY = new ArrayMap<>(12);

        private StatisAppKey(String str) {
            this.packageName = str;
        }

        public static StatisAppKey get(String str) {
            synchronized (PACKAGE_NAME_TO_STATIS_APP_KEY) {
                StatisAppKey statisAppKey = PACKAGE_NAME_TO_STATIS_APP_KEY.get(str);
                if (statisAppKey != null) {
                    return statisAppKey;
                }
                StatisAppKey statisAppKey2 = new StatisAppKey(str);
                PACKAGE_NAME_TO_STATIS_APP_KEY.put(str, statisAppKey2);
                return statisAppKey2;
            }
        }

        public String getKeyForStatis(Context context) {
            String charSequence;
            synchronized (PACKAGE_NAME_TO_KEY) {
                String str = PACKAGE_NAME_TO_KEY.get(this.packageName);
                if (str != null) {
                    return str;
                }
                VideoVal packageNameToVideoVal = VideoStatHelper.packageNameToVideoVal(this.packageName);
                if (packageNameToVideoVal != null) {
                    charSequence = VideoStatHelper.getVideoVal(packageNameToVideoVal);
                } else {
                    try {
                        CharSequence applicationLabel = context.getPackageManager().getApplicationLabel(context.getPackageManager().getApplicationInfo(this.packageName, 0));
                        if (applicationLabel == null) {
                            return this.packageName;
                        }
                        charSequence = applicationLabel.toString();
                    } catch (PackageManager.NameNotFoundException e) {
                        Logger logger = L.video;
                        logger.e("failed to get package info " + this.packageName, e);
                        return "";
                    }
                }
                synchronized (PACKAGE_NAME_TO_KEY) {
                    PACKAGE_NAME_TO_KEY.put(this.packageName, charSequence);
                }
                L.video.d("stat key for %s is %s", this.packageName, charSequence);
                return charSequence;
            }
        }
    }
}
